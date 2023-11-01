package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.commercetools.api.enums.CommerceToolsProductID;
import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CSAPCT-348
 * Test Automation Sales - Credit/Adjustment
 *
 * @author Mayur.Kumbhar
 */
public class CommerceToolsAPIReturnFullOrderWithShippingTests extends CommerceToolAPITestUtilities
{


	/**CSAPCT-352
	 *This test case return full order.
	 */
	@Test(groups = { "commercetools_api_regression" })
	public void returnFullOrderWithShippingTest( )
	{

		String responseMessage = "A duplicate value '\"orderCancel\"' exists for field 'key'";

		validateToken();
		String streetAddress1 = "1270 York Road", streetAddress2 = "", mainDivision = "", city = "Gettysburg", postalCode
			= "17325", country = "US";
		Response postAddressResponse = postUsAddress(commerceToolsAccessToken, streetAddress1, streetAddress2, city,
			mainDivision, postalCode, country);

		Response response = createCartRequest(commerceToolsAccessToken);

		JsonPath createCartPath = response.jsonPath();
		String cartId = createCartPath.get("id");
		Integer versionString = createCartPath.get("version");
		Long version = Long.valueOf(versionString);
		String streetName = "1270 York Road", streetNumber = "", postal = "17325", Shipping_City = "Gettysburg",
			Shipping_State = "PA", Shipping_Country = "US";
		Response cartResponse = setShippingAddressRequest(commerceToolsAccessToken, cartId, version, streetName,
			streetNumber, postal, Shipping_City, Shipping_State, Shipping_Country);
		JsonPath setShippingCartResponse = cartResponse.jsonPath();
		Integer ShippingCartResponseVersion = setShippingCartResponse.get("version");

		try
		{
			Long ShippingCartResponseVersionValue = Long.valueOf(ShippingCartResponseVersion);

			Response billingResponse = setBillingAddressRequest(commerceToolsAccessToken, cartId, ShippingCartResponseVersionValue, streetName,
				streetNumber, postal, Shipping_City, Shipping_State, Shipping_Country);
			JsonPath billingCartResponse = billingResponse.jsonPath();
			Integer BillingCartResponseVersion = billingCartResponse.get("version");
			Long version2 = Long.valueOf(BillingCartResponseVersion);
			long quantity = 1;
			long quantity2 = 5;
			long quantity3 = 2;


			Response addLineResponse = addLineItemRequest(commerceToolsAccessToken, cartId, version2, quantity);
			Response addSecondLineResponse = addLineItemRequest(commerceToolsAccessToken, cartId, version2, quantity2,
				CommerceToolsProductID.TAXABLE_PRODUCT1.commerceToolsProductID);
			Response addThirdLineResponse = addLineItemRequest(commerceToolsAccessToken, cartId, version2, quantity3,
				CommerceToolsProductID.TAXABLE_PRODUCT2.commerceToolsProductID);

			JsonPath lineItemResponse = addLineResponse.jsonPath();
			Integer LineItemResponseVersion = lineItemResponse.get("version");
			Long LineItemVersionValue = Long.valueOf(LineItemResponseVersion);
			Response createOrderResponse = createOrderRequest(commerceToolsAccessToken, cartId, LineItemVersionValue);
			assertStatus(createOrderResponse, ResponseCodes.CREATED);
			Response customResponse = createCustomType(commerceToolsAccessToken);

			JsonPath customTypeResponse = customResponse.jsonPath();
			String errorMessage = customTypeResponse.get("message");

			JsonPath orderResponse = createOrderResponse.jsonPath();
			String orderID = orderResponse.get("id");

			if ( errorMessage.contains(responseMessage) )
			{
				createDriver();
				loginAndCancelOrder(orderID);
			}
		}
		catch ( Exception e )
		{
			Assert.assertTrue(false, "Extension time out 504 ");
		}
	}
}
