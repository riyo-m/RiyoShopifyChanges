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
public class CommerceToolsAPIReturnPartialOrderWithShippingTests extends CommerceToolAPITestUtilities
{

	/**
	 * CSAPCT-351
	 * This test case return partial quantity with same billing and shipping address.
	 */
	@Test(groups = { "commercetools_api_regression" })
	public void returnPartialOrderWithShippingTest( )
	{
		String responseMessage="A duplicate value '\"orderCancel\"' exists for field 'key'";

		validateToken();
		String streetAddress1 = "5950 Broadway", streetAddress2 = "", mainDivision = "", city = "Los Angeles", postalCode
			= "90030", country = "US";
		Response postAddressResponse = postUsAddress(commerceToolsAccessToken, streetAddress1, streetAddress2, city,
			mainDivision, postalCode, country);

		Response response = createCartRequest(commerceToolsAccessToken);

		JsonPath createCartPath = response.jsonPath();
		String cartId = createCartPath.get("id");
		Integer versionString = createCartPath.get("version");
		Long version = Long.valueOf(versionString);
		String streetName = "5950 Broadway", streetNumber = "", postal = "90030", Shipping_City = "Los Angeles",
			Shipping_State = "CA", Shipping_Country = "US";
		Response cartResponse = setShippingAddressRequest(commerceToolsAccessToken, cartId, version, streetName,
			streetNumber, postal, Shipping_City, Shipping_State, Shipping_Country);
		JsonPath setShippingCartResponse = cartResponse.jsonPath();
		Integer ShippingCartVersion = setShippingCartResponse.get("version");

		try
		{
			Long ShippingCartResponseVersionValue = Long.valueOf(ShippingCartVersion);

			Response billingResponse = setBillingAddressRequest(commerceToolsAccessToken, cartId, ShippingCartResponseVersionValue, streetName,
				streetNumber, postal, Shipping_City, Shipping_State, Shipping_Country);
			JsonPath billingCartResponse = billingResponse.jsonPath();
			Integer BillingCartVersion = billingCartResponse.get("version");
			Long BillingCartVersionValue = Long.valueOf(BillingCartVersion);
			long quantity =1;
			long quantity2=5;
			long quantity3=2;

			Response addLineResponse = addLineItemRequest(commerceToolsAccessToken, cartId, BillingCartVersionValue, quantity);
			Response addSecondLineResponse=addLineItemRequest(commerceToolsAccessToken,cartId,BillingCartVersionValue,quantity2,
				CommerceToolsProductID.TAXABLE_PRODUCT1.commerceToolsProductID);
			Response addThirdLineResponse=addLineItemRequest(commerceToolsAccessToken,cartId,BillingCartVersionValue,quantity3,CommerceToolsProductID.TAXABLE_PRODUCT2.commerceToolsProductID);

			JsonPath lineItemResponse = addLineResponse.jsonPath();
			Integer LineItemVersion = lineItemResponse.get("version");
			Long LineItemVerionValue = Long.valueOf(LineItemVersion);
			Response createOrderResponse = createOrderRequest(commerceToolsAccessToken, cartId, LineItemVerionValue);
			assertStatus(createOrderResponse, ResponseCodes.CREATED);
			Response customResponse=createCustomType(commerceToolsAccessToken);

			JsonPath customTypeResponse=customResponse.jsonPath();
			String errorMessage=customTypeResponse.get("message");

			JsonPath orderResponse=createOrderResponse.jsonPath();
			String orderID=orderResponse.get("id");

			if(errorMessage.contains(responseMessage))
			{
				createDriver();
				loginAndCancelOrder(orderID);
			}
		}
		catch ( Exception e )
		{
			Assert.assertTrue(false, "Extension time out 504 ");
		}

		validateToken();
		String newStreetAddress1 = "5950 Broadway", newStreetAddress2 = "", newMainDivision = "", newCity = "Mittelberg", newPostalCode
			= "90030", newCountry = "US";


		Response newCartResponse = createCartRequest(commerceToolsAccessToken);

		JsonPath newCreateCartPath = newCartResponse.jsonPath();
		String newCartIdResponse = newCreateCartPath.get("id");
		Integer newVersionString = newCreateCartPath.get("version");
		Long newVersion = Long.valueOf(newVersionString);
		String newStreetName = "5950 Broadway", newStreetNumber = "", newPostal = "90030", newShipping_City = "Los Angeles",
			newShipping_State = "CA", newShipping_Country = "US";
		Response newShippingCartResponse = setShippingAddressRequest(commerceToolsAccessToken, newCartIdResponse, newVersion, newStreetName,
			newStreetNumber, newPostal, newShipping_City, newShipping_State, newShipping_Country);
		JsonPath newSetShippingCartResponse = newShippingCartResponse.jsonPath();
		Integer newShippingCartVersionString = newSetShippingCartResponse.get("version");

		try
		{
			Long ShippingCartVersionValue = Long.valueOf(newShippingCartVersionString);

			Response billingResponse = setBillingAddressRequest(commerceToolsAccessToken, newCartIdResponse, ShippingCartVersionValue, newStreetName,
				newStreetNumber, newPostal, newShipping_City, newShipping_State, newShipping_Country);
			JsonPath billingCartResponse = billingResponse.jsonPath();
			Integer BillingCartVersion = billingCartResponse.get("version");
			Long BillingCartVersionValue = Long.valueOf(BillingCartVersion);
			long quantity =1;
			long quantity2=5;

			Response addLineResponse = addLineItemRequest(commerceToolsAccessToken, newCartIdResponse, BillingCartVersionValue, quantity);
			Response addSecondLineResponse=addLineItemRequest(commerceToolsAccessToken,newCartIdResponse,BillingCartVersionValue,quantity2,CommerceToolsProductID.TAXABLE_PRODUCT1.commerceToolsProductID);

			JsonPath lineItemResponse = addLineResponse.jsonPath();
			Integer LineItemResponseVersion = lineItemResponse.get("version");
			Long LineItemResponseVersionValue = Long.valueOf(LineItemResponseVersion);
			Response createOrderResponse = createOrderRequest(commerceToolsAccessToken, newCartIdResponse, LineItemResponseVersionValue);
			assertStatus(createOrderResponse, ResponseCodes.CREATED);

		}
		catch ( Exception e )
		{
			Assert.assertTrue(false, "Extension time out 504 ");
		}

}



}
