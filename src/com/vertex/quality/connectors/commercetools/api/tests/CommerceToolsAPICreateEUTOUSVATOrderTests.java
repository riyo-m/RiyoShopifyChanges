package com.vertex.quality.connectors.commercetools.api.tests;


import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CSA-853...
 * this test case sends a generate Token request and then get's the token from the response.
 * then sends postUSAddress request for setting Shipping from address.
 * then sends an create cart request for the same token and generate cartId and version.
 * sends setShipping address request for setting Shipping To address for same cartId and version.
 * sends addLineItem request for same cartId and version.
 * sends create order request for same cartId and version.
 *
 * @author vivek_kumar
 */

public class CommerceToolsAPICreateEUTOUSVATOrderTests extends CommerceToolAPITestUtilities {

    /**
     * Test case for creating Sales order Invoice request.
     *
     */

    @Test (groups = {"commercetools_api_regression"})
    public void createOrderEUUSVATRequestTest() {
		validateToken();

        String streetAddress1 = "Allée du Stade", streetAddress2 = "13405", mainDivision = "", city = "Berlin", postalCode = "13405", country = "Germany";
        Response postAddressResponse = postUsAddress(commerceToolsAccessToken, streetAddress1, streetAddress2, city,
                mainDivision, postalCode, country);

        Response response = createCartRequest(commerceToolsAccessToken);

        JsonPath extractor = response.jsonPath();
        String cartId = extractor.get("id");
        Integer versionString = extractor.get("version");
        try
		{
			Long version = Long.valueOf(versionString);
			String streetName = "Universal City Plazza", streetNumber = "100", postal = "91608-1002", Shipping_City = "University City", Shipping_State = "CA", Shipping_Country = "US";
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
