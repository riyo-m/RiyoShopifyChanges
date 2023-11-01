package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.commercetools.api.enums.CommerceToolsProductID;
import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CSAPCT-CCT Test Automation - Sales - Support Product Code / Class
 *
 * @author Mayur.Kumbhar
 */
public class CommerceToolsProductExemptionTests extends CommerceToolAPITestUtilities
{

	/**
	 * CSAPCT-354
	 *
	 * This test is to validate the tax on exempt product.
	 * Adding try catch as we are having bug for timeout exception
	 */
	@Test(groups = {"commercetools_api_regression"})
	public void productCodeExemptionTest()
	{

		validateToken();
		String streetAddress1 = "1041 Old Cassatt Rd", streetAddress2 = "", mainDivision = "", city = "Berwyn", postalCode = "19312", country = "US";
		Response postAddressResponse = postUsAddress(commerceToolsAccessToken, streetAddress1, streetAddress2, city,
			mainDivision, postalCode, country);

		Response response = createCartRequest(commerceToolsAccessToken);

		JsonPath extractor = response.jsonPath();
		String cartId = extractor.get("id");
		Integer versionString = extractor.get("version");
		Long version = Long.valueOf(versionString);
		String streetName = "1041 Old Cassatt Rd", streetNumber = "", postal = "19312", Shipping_City = "Berwyn", Shipping_State = "PA", Shipping_Country = "US";
		Response cartResponse = setShippingAddressRequest(commerceToolsAccessToken, cartId, version, streetName, streetNumber,
			postal, Shipping_City, Shipping_State, Shipping_Country);
		JsonPath CartResponseExtract = cartResponse.jsonPath();
		try
		{
			Integer versionString1 = CartResponseExtract.get("version");
			Long CartResponseVersionValue = Long.valueOf(versionString1);

			Response billingResponse = setBillingAddressRequest(commerceToolsAccessToken, cartId, CartResponseVersionValue, streetName, streetNumber,
				postal, Shipping_City, Shipping_State, Shipping_Country);

			JsonPath BillingResponseVersion = billingResponse.jsonPath();
			Integer BillingResponseVersionValue = BillingResponseVersion.get("version");
			Long Billingversion = Long.valueOf(BillingResponseVersionValue);
			long quantity = 1;
			long exemptQuantity=5;
			Response addLineResponse = addLineItemRequest(commerceToolsAccessToken, cartId, Billingversion, quantity);
			Response addExemptLine=addExemptLineItemRequest(commerceToolsAccessToken,cartId,Billingversion,exemptQuantity,
				CommerceToolsProductID.EXEMPT_PRODUCT.commerceToolsProductID);

			JsonPath exemptVersion=addExemptLine.jsonPath();
			Integer ExemptLineItemResponseVersionValue=exemptVersion.get("version");
			Long ExemptVersionValue = Long.valueOf(ExemptLineItemResponseVersionValue);

			JsonPath lineItemResponseVersion = addLineResponse.jsonPath();
			Integer LineItemResponseVersionValue = lineItemResponseVersion.get("version");
			Long LineItem2version = Long.valueOf(LineItemResponseVersionValue);
			Response createOrderResponse = createOrderRequest(commerceToolsAccessToken, cartId, LineItem2version,ExemptVersionValue);
			assertStatus(createOrderResponse, ResponseCodes.CREATED);
		}catch ( Exception e )
		{
			Assert.assertTrue(false, "Extension time out 504 ");
		}
	}

}

