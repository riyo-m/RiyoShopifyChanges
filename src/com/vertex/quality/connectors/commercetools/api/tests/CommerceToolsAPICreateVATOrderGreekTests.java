package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.commercetools.api.enums.CommerceToolCurrency;
import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *CSA-848 : Create Sale Order for VAT (Greek Territory) and Invoice
 * Ship From: Port de la Bourdonnais, Paris 75007
 * Ship To: Paralia Schoinonta, Analipsi (City) State: Thessalonkiki 859 00, Greece
 * with line no=1 and quantity=1
 * Country: Greece 24%
 *
 * @author Mayur.Kumbhar
 *
 */
public class CommerceToolsAPICreateVATOrderGreekTests extends CommerceToolAPITestUtilities {



	/**
	 *This method will create sales order with VAT for Greek Territory
	 */
	@Test(groups = {"commercetools_api_regression"})
	public void createSaleOrderforGreekTerritoryTests() {
		validateToken();

		Response response = createCartRequest(commerceToolsAccessToken);
		String streetAddress1 = "Port de la", streetAddress2 = "", mainDivision = "", city = "Paris", postalCode = "75007", country = "FRA";
		JsonPath extractor = response.jsonPath();
		String cartId = extractor.get("id");
		Integer versionString = extractor.get("version");
		try
		{
			Long version = Long.valueOf(versionString);
			String streetName = "Paralia Schoinonta", streetNumber = "", postal = "859 00", Shipping_City = "Analipsi", Shipping_State = "", Shipping_Country = "GR";
			Response cartResponse = setShippingAddressRequest(commerceToolsAccessToken, cartId, version, streetName, streetNumber,
				postal, Shipping_City, Shipping_State, Shipping_Country);
			JsonPath extractor1 = cartResponse.jsonPath();
			Integer versionString1 = extractor1.get("version");
			Long version1 = Long.valueOf(versionString1);

			Response billingResponse = setBillingAddressRequest(commerceToolsAccessToken, cartId, version1, streetName, streetNumber,
				postal, Shipping_City, Shipping_State, Shipping_Country);

			JsonPath extractor2 = billingResponse.jsonPath();
			Integer versionString2 = extractor2.get("version");
			Long version2 = Long.valueOf(versionString2);
			long quantity = 1;
			Response addLineResponse = addLineItemRequest(commerceToolsAccessToken, cartId, version2, quantity);
			JsonPath extractor3 = addLineResponse.jsonPath();
			Integer versionString3 = extractor3.get("version");
			Long version3 = Long.valueOf(versionString3);
			Response createOrderResponse = createOrderRequest(commerceToolsAccessToken, cartId, version3);
			assertStatus(createOrderResponse, ResponseCodes.CREATED);
		}catch (Exception e)
		{
			Assert.assertTrue(false, "Extension time out 504 ");
		}

	}
}
