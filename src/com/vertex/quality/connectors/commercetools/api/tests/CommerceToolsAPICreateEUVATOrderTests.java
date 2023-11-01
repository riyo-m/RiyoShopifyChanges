package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *CSA-851 : Create Sales order for VAT (EU FR-DE)
 * Ship From: Port de la Bourdonnais, Paris 75007
 * Ship To: Allée du Stade, 13405 Berlin, Germany
 * with line no=1 and quantity=1
 * Country: France 20%
 *
 * @author Mayur.Kumbhar
 *
 */
public class CommerceToolsAPICreateEUVATOrderTests extends CommerceToolAPITestUtilities
{

	/**
	 *This method will create sales order with VAT
	 */
	@Test(groups = {"commercetools_api_regression"})
	public void createSaleOrderforVATTests() {
		validateToken();

		String streetAddress1 = "Port de la Bourdonnais", streetAddress2 = "", mainDivision = "", city = "Paris", postalCode = "75007", country = "FRA";
		Response postAddressResponse = postUsAddress(commerceToolsAccessToken, streetAddress1, streetAddress2, city,
			mainDivision, postalCode, country);
		Response response = createCartRequest(commerceToolsAccessToken);
		JsonPath extractor = response.jsonPath();

		String cartId = extractor.get("id");
		Integer versionString = extractor.get("version");
		Long version = Long.valueOf(versionString);

		String streetName = "Allee du Stade", streetNumber = "", postal = "13405", Shipping_City = "Berlin", Shipping_State = "", Shipping_Country = "DE";
		Response cartResponse = setShippingAddressRequest(commerceToolsAccessToken, cartId, version, streetName, streetNumber,
			postal, Shipping_City, Shipping_State, Shipping_Country);
		JsonPath extractor1 = cartResponse.jsonPath();
		Integer versionString1 = extractor1.get("version");
		try
		{
			Long version1 = Long.valueOf(versionString1);
			long quantity = 1;
			Response addLineResponse = addLineItemRequest(commerceToolsAccessToken, cartId, version1, quantity);
			JsonPath extractor2 = addLineResponse.jsonPath();
			Integer versionString2 = extractor2.get("version");
			Long version2 = Long.valueOf(versionString2);
			Response createOrderResponse = createOrderRequest(commerceToolsAccessToken, cartId, version2);
			assertStatus(createOrderResponse, ResponseCodes.CREATED);
		}catch (Exception e)
		{
			Assert.assertTrue(false, "Extension time out 504 ");
		}
	}
}
