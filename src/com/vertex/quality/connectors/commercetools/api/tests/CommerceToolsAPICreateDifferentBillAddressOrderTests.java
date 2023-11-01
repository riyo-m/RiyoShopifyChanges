package com.vertex.quality.connectors.commercetools.api.tests;


import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CSA-883:Create Sales Order different ship and bill
 * this test case sends a generate Token request and then get's the token from the response.
 * then sends postUSAddress request for setting Shipping from address.
 * then sends an create cart request for the same token and generate cartId and version.
 * sends setShipping address request for setting Shipping To address for same cartId and version.
 * sends addLineItem request for same cartId and version.
 * sends create order request for same cartId and version.
 *
 * @author vivek_kumar
 */

public class CommerceToolsAPICreateDifferentBillAddressOrderTests extends CommerceToolAPITestUtilities {

    /**
     * Test case for creating Sales order Invoice request.
     *
     */
    @Test (groups = {"commercetools_api_regression"})
    public void createOrderUSCANRequestTest() {
		validateToken();

        String streetAddress1 = "York Road", streetAddress2 = "1270", mainDivision = "", city = "Gettysburg", postalCode = "17325", country = "US";
        Response postAddressResponse = postUsAddress(commerceToolsAccessToken, streetAddress1, streetAddress2, city,
                mainDivision, postalCode, country);

        Response response = createCartRequest(commerceToolsAccessToken);

        JsonPath extractor = response.jsonPath();
        String cartId = extractor.get("id");
        Integer versionString = extractor.get("version");
        try
		{
			Long version = Long.valueOf(versionString);
			String streetName = "Broadway", streetNumber = "5950", postal = "90030", Shipping_City = "Los Angeles", Shipping_State = "CA", Shipping_Country = "US";
			Response cartResponse = setShippingAddressRequest(commerceToolsAccessToken, cartId, version, streetName, streetNumber,
				postal, Shipping_City, Shipping_State, Shipping_Country);
			JsonPath extractor1 = cartResponse.jsonPath();
			Integer versionString1 = extractor1.get("version");
			Long version1 = Long.valueOf(versionString1);
			String streetName2 = "N Orange St Wilmington", streetNumber2 = "100", postal1 = "19801", Shipping_City1 = "Wilmington", Shipping_State1 = "DE", Shipping_Country1 = "US";
			Response billingResponse = setBillingAddressRequest(commerceToolsAccessToken, cartId, version1, streetName2, streetNumber2,
				postal1, Shipping_City1, Shipping_State1, Shipping_Country1);

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
