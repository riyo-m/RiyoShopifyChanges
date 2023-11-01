package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.commercetools.api.enums.CommerceToolsProductID;
import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CSAPCT-359:CCT Test Automation - Sales - Support Customer Code and Customer Class fields
 *
 * @author Mayur.Kumbhar
 */
public class CommerceToolsCustomerExemptTests extends CommerceToolAPITestUtilities
{

	/**
	 * CSAPCT-
	 * This test case exempt the product with customer is mapped to exempt customer class
	 */
	@Test(groups = {"commercetools_api_regression"})
	public void commerceToolsExemptCustomerTest() {
		validateToken();
		String streetAddress1 = "5950 Broadway", streetAddress2 = "1820", mainDivision = "", city = "Los Angeles", postalCode = "90030", country = "US";
		Response postAddressResponse = postUsAddress(commerceToolsAccessToken, streetAddress1, streetAddress2, city,
			mainDivision, postalCode, country);

		Response response = createCartRequestforExemptCustomer(commerceToolsAccessToken);

		JsonPath jsonPath = response.jsonPath();
		String cartId = jsonPath.get("id");
		Integer version = jsonPath.get("version");
		Long CartResponseVersionField = Long.valueOf(version);
		String streetName = "5950 Broadway", streetNumber = "", postal = "90030", Shipping_City = "Los Angeles", Shipping_State = "CA", Shipping_Country = "US";
		Response cartResponse = setShippingAddressRequest(commerceToolsAccessToken, cartId, CartResponseVersionField, streetName, streetNumber,
			postal, Shipping_City, Shipping_State, Shipping_Country);
		JsonPath CartResponseExtractor = cartResponse.jsonPath();
		try
		{
			Integer CartResponseVersion = CartResponseExtractor.get("version");
			Long CartResponseVersionValue = Long.valueOf(CartResponseVersion);

			Response billingResponse = setBillingAddressRequest(commerceToolsAccessToken, cartId, CartResponseVersionValue, streetName, streetNumber,
				postal, Shipping_City, Shipping_State, Shipping_Country);

			JsonPath BillingResponseVersion = billingResponse.jsonPath();
			Integer BillingResponseVersionValue = BillingResponseVersion.get("version");
			Long BillingResponseVersionField = Long.valueOf(BillingResponseVersionValue);
			long quantity = 1;
			long quantity2= 5;
			Response addLineResponse = addLineItemRequest(commerceToolsAccessToken, cartId, BillingResponseVersionField, quantity);
			Response addLineItem2=addLineItemRequest(commerceToolsAccessToken,cartId,BillingResponseVersionField,quantity2,
				CommerceToolsProductID.TAXABLE_PRODUCT1.commerceToolsProductID);
			JsonPath LineItemResponseVersion = addLineResponse.jsonPath();
			Integer LineItemVersion = LineItemResponseVersion.get("version");
			Long LineItemVersionValue = Long.valueOf(LineItemVersion);
			Response createOrderResponse = createOrderRequest(commerceToolsAccessToken, cartId, LineItemVersionValue);
			assertStatus(createOrderResponse, ResponseCodes.CREATED);
		}catch ( Exception e )
		{
			Assert.assertTrue(false, "Extension time out 504 ");
		}
	}

}
