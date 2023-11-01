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
public class CommerceToolsAPIReturnShippingForVATTests extends CommerceToolAPITestUtilities
{

	/**
	 * CSAPCT-353
	 * This test case return partial quantity with same billing and shipping address.
	 */

	@Test(groups = { "commercetools_api_regression" })
	public void returnVATShippingTest( )
	{

		String responseMessage="A duplicate value '\"orderCancel\"' exists for field 'key'";

		validateToken();
		String streetAddress1 = " OranienstraAYe 138", streetAddress2 = "", mainDivision = "", city = "Berlin", postalCode
			= "10969", country = "DE";
		Response postAddressResponse = postUsAddress(commerceToolsAccessToken, streetAddress1, streetAddress2, city,
			mainDivision, postalCode, country);

		Response response = createCartRequest(commerceToolsAccessToken);

		JsonPath createCartPath = response.jsonPath();
		String cartId = createCartPath.get("id");
		Integer versionString = createCartPath.get("version");
		Long version = Long.valueOf(versionString);
		String streetName = "Allee du Stade", streetNumber = "", postal = "13405", Shipping_City = "Berlin",
			Shipping_State = "", Shipping_Country = "DE";
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
			long quantity =1;
			long quantity2=2;
			long quantity3=5;

			Response addLineResponse = addLineItemRequest(commerceToolsAccessToken, cartId, BillingCartResponseVersionValue, quantity);
			Response addSecondLineResponse=addLineItemRequest(commerceToolsAccessToken,cartId,BillingCartResponseVersionValue,quantity2,
				CommerceToolsProductID.TAXABLE_PRODUCT1.commerceToolsProductID);
			Response addThirdLineResponse=addLineItemRequest(commerceToolsAccessToken,cartId,BillingCartResponseVersionValue,quantity3,CommerceToolsProductID.TAXABLE_PRODUCT2.commerceToolsProductID);

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
		String newStreetAddress1 = "Moosstraße 7", newStreetAddress2 = "", newMainDivision = "", newCity = "Mittelberg", newPostalCode
			= "6993", newCountry = "DE";

		Response newCartResponse = createCartRequest(commerceToolsAccessToken);

		JsonPath NewCreateCartPath = newCartResponse.jsonPath();
		String NewCartIdResponse = NewCreateCartPath.get("id");
		Integer NewCartVersionString = NewCreateCartPath.get("version");
		Long NewCartVersionValue = Long.valueOf(NewCartVersionString);
		String newStreetName = "Allee du Stade", newStreetNumber = "", newPostal = "13405", newShipping_City = "Berlin",
			newShipping_State = "", newShipping_Country = "DE";
		Response newShippingCartResponse = setShippingAddressRequest(commerceToolsAccessToken, NewCartIdResponse, NewCartVersionValue, newStreetName,
			newStreetNumber, newPostal, newShipping_City, newShipping_State, newShipping_Country);
		JsonPath newSetShippingCartResponse = newShippingCartResponse.jsonPath();
		Integer NewShippingCartResponseVersion = newSetShippingCartResponse.get("version");

		try
		{
			Long NewShippingCartResponseVersionValue = Long.valueOf(NewShippingCartResponseVersion);

			Response billingResponse = setBillingAddressRequest(commerceToolsAccessToken, NewCartIdResponse, NewShippingCartResponseVersionValue, newStreetName,
				newStreetNumber, newPostal, newShipping_City, newShipping_State, newShipping_Country);
			JsonPath billingCartResponse = billingResponse.jsonPath();
			Integer NewBillingCartResponseVersion = billingCartResponse.get("version");
			Long NewBillingCartResponseVersionValue = Long.valueOf(NewBillingCartResponseVersion);
			long quantity =1;
			long quantity3=5;

			Response addLineResponse = addLineItemRequest(commerceToolsAccessToken, NewCartIdResponse, NewBillingCartResponseVersionValue, quantity);
			Response addThirdLineResponse=addLineItemRequest(commerceToolsAccessToken,cartId,NewBillingCartResponseVersionValue,quantity3,CommerceToolsProductID.TAXABLE_PRODUCT2.commerceToolsProductID);

			JsonPath lineItemResponse = addLineResponse.jsonPath();
			Integer NewLineItemResponseVersion = lineItemResponse.get("version");
			Long NewLineItemResponseVersionValue = Long.valueOf(NewLineItemResponseVersion);
			Response createOrderResponse = createOrderRequest(commerceToolsAccessToken, NewCartIdResponse, NewLineItemResponseVersionValue);
			assertStatus(createOrderResponse, ResponseCodes.CREATED);

		}
		catch ( Exception e )
		{
			Assert.assertTrue(false, "Extension time out 504 ");
		}
	}
}
