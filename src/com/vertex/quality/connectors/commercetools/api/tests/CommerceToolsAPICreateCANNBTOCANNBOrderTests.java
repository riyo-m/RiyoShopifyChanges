package com.vertex.quality.connectors.commercetools.api.tests;


import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CSA-873 :Create Sales Order with Invoice CANNB to CANNB same Province (HST)
 * this test case sends a generate Token request and then get's the token from the response.
 * then sends postUSAddress request for setting Shipping from address from Canada.
 * then sends an create cart request for the same token and generate cartId and version.
 * sends setShipping address request for setting Shipping To address Canada for same cartId and version.
 * sends addLineItem request for same cartId and version.
 * sends create order request for same cartId and version.
 *
 * @author vivek_kumar
 */


public class CommerceToolsAPICreateCANNBTOCANNBOrderTests extends CommerceToolAPITestUtilities {
    /**
     * Test case for creating Sales order.
     *
     */
    @Test (groups = {"commercetools_api_regression"})
    public void createOrderCANNBNBRequestTest()
	{
		validateToken();

		String streetAddress1 = "Bancroft Point Rd", streetAddress2 = "11", mainDivision = "", city = "Grand Manan",
			postalCode = "NB E5G 4C1", country = "CAN";
		Response postAddressResponse = postUsAddress(commerceToolsAccessToken, streetAddress1, streetAddress2, city,
			mainDivision, postalCode, country);
		Response response = createCartRequest(commerceToolsAccessToken);
		JsonPath extractor = response.jsonPath();
		String cartId = extractor.get("id");
		Integer versionString = extractor.get("version");
		Long version = Long.valueOf(versionString);

		String streetName = "Pettingill Rd", streetNumber = "21", postal = "NB E2E 6B1", Shipping_City = "Quispamsis",
			Shipping_State = "", Shipping_Country = "CA";
		Response cartResponse = setShippingAddressRequest(commerceToolsAccessToken, cartId, version, streetName,
			streetNumber, postal, Shipping_City, Shipping_State, Shipping_Country);
		JsonPath extractor1 = cartResponse.jsonPath();
		try
		{
			Integer versionString1 = extractor1.get("version");
			Long version1 = Long.valueOf(versionString1);
			Response billingResponse = setBillingAddressRequest(commerceToolsAccessToken, cartId, version1, streetName,
				streetNumber, postal, Shipping_City, Shipping_State, Shipping_Country);

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
		}
		catch ( Exception e )
		{
			Assert.assertTrue(false, "Extension time out 504 ");
		}
	}

}
