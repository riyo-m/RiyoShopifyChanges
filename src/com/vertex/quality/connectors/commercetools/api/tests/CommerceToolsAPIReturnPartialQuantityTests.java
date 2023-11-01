package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
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
public class CommerceToolsAPIReturnPartialQuantityTests extends CommerceToolAPITestUtilities
{

	/**
	 * CSAPCT-350
	 * This test case return partial quantity.
	 */
	@Test(groups = { "commercetools_api_regression" })
	public void returnPartialQuantityTest( )
	{

		String responseMessage="A duplicate value '\"orderCancel\"' exists for field 'key'";

		validateToken();
		String streetAddress1 = "4505 Ridgeside Dr", streetAddress2 = "", mainDivision = "", city = "Dallas", postalCode
			= "75244-4902", country = "US";
		Response postAddressResponse = postUsAddress(commerceToolsAccessToken, streetAddress1, streetAddress2, city,
			mainDivision, postalCode, country);

		Response response = createCartRequest(commerceToolsAccessToken);

		JsonPath createCartPath = response.jsonPath();
		String cartId = createCartPath.get("id");
		Integer versionString = createCartPath.get("version");
		Long version = Long.valueOf(versionString);
		String streetName = "4505 Ridgeside Dr", streetNumber = "", postal = "75244-4902", Shipping_City = "Dallas",
			Shipping_State = "TX", Shipping_Country = "US";
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
			Long BillingCartResponseVersionValue = Long.valueOf(BillingCartResponseVersion);
			long quantity = 5;

			Response addLineResponse = addLineItemRequest(commerceToolsAccessToken, cartId, BillingCartResponseVersionValue, quantity);

			JsonPath lineItemResponse = addLineResponse.jsonPath();
			Integer LineItemResponseVersion = lineItemResponse.get("version");
			Long LineItemResponseVersionValue = Long.valueOf(LineItemResponseVersion);
			Response createOrderResponse = createOrderRequest(commerceToolsAccessToken, cartId, LineItemResponseVersionValue);
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
		String newStreetAddress = "4505 Ridgeside Dr", newStreetAddress2 = "", newMainDivision = "", newCity = "Dallas", newPostalCode
			= "75244-4902", newCountry = "US";
		Response newPostAddressResponse = postUsAddress(commerceToolsAccessToken, streetAddress1, streetAddress2, newCity,
			mainDivision, newPostalCode, country);

		Response newCartResponse = createCartRequest(commerceToolsAccessToken);

		JsonPath createNewCartPath = newCartResponse.jsonPath();
		String newCartId = createNewCartPath.get("id");
		Integer newVersionString = createNewCartPath.get("version");
		Long newVersion = Long.valueOf(newVersionString);
		String NewStreetName = "4505 Ridgeside Dr", NewStreetNumber = "", newPostal = "75244-4902", newShipping_City = "Dallas",
			newShipping_State = "TX", newShipping_Country = "US";
		Response newShippingCartResponse = setShippingAddressRequest(commerceToolsAccessToken, newCartId, version, NewStreetName,
			NewStreetNumber, newPostal, newShipping_City, newShipping_State, newShipping_Country);
		JsonPath newSetShippingCartResponse = newShippingCartResponse.jsonPath();
		Integer NewSetShippingResponseVersion = newSetShippingCartResponse.get("version");

		try
		{
			Long NewSetShippingResponseVersionValue = Long.valueOf(NewSetShippingResponseVersion);

			Response billingResponse = setBillingAddressRequest(commerceToolsAccessToken, newCartId, NewSetShippingResponseVersionValue, NewStreetName,
				NewStreetNumber, newPostal, newShipping_City, newShipping_State, newShipping_Country);
			JsonPath billingCartResponse = billingResponse.jsonPath();
			Integer NewBillingResponseVersion = billingCartResponse.get("version");
			Long NewBillingResponseVersionValue = Long.valueOf(NewBillingResponseVersion);
			long quantity = 5;

			Response addLineResponse = addLineItemRequest(commerceToolsAccessToken, newCartId, NewBillingResponseVersionValue, quantity);

			JsonPath lineItemResponse = addLineResponse.jsonPath();
			Integer NewLineItemResponseVersion = lineItemResponse.get("version");
			Long NewLineItemResponseVersionValue = Long.valueOf(NewLineItemResponseVersion);
			Response createOrderResponse = createOrderRequest(commerceToolsAccessToken, newCartId, NewLineItemResponseVersionValue);
			assertStatus(createOrderResponse, ResponseCodes.CREATED);
		}
		catch ( Exception e )
		{
			Assert.assertTrue(false, "Extension time out 504 ");
		}

	}

}
