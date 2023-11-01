package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CSAPCT-281
 * Validate Sales Order with Address Cleansing when we don't enter the postal code
 *
 * @author Mohit.Nirwan
 */

public class CTCValidateSalesOrderAddressCleansingNoZipTests extends CommerceToolAPITestUtilities {

    /**
     * CSAPCT-286
     * Test for validating sales order without the postal code
     */
    @Test(groups = { "CTC_address_Cleansing_SHIP_TO" })
    public void validateSalesOrderAddressCleasningNoZipTest(){

        validateToken();
        Response response = createCartRequest(commerceToolsAccessToken);
        JsonPath extractor = response.jsonPath();
        String cartId = extractor.get("id");
        Integer versionString = extractor.get("version");
        Long version = Long.valueOf(versionString);
        String streetName = "Market St.", streetNumber = "2955", postal = "", Shipping_City = "Philadelphia", Shipping_State = "PA", Shipping_Country = "US";
        Response cartResponse = setShippingAddressRequest(commerceToolsAccessToken, cartId, version, streetName, streetNumber,
                postal, Shipping_City, Shipping_State, Shipping_Country);

        try{
            JsonPath extractor1 = cartResponse.jsonPath();
            Integer versionString1 = extractor1.get("version");
            Long version1 = Long.valueOf(versionString1);
            long quantity = 1;
            Response addLineResponse = addLineItemRequest(commerceToolsAccessToken, cartId, version1, quantity);
            JsonPath extractor2 = addLineResponse.jsonPath();
            Integer versionString2 = extractor2.get("version");
            Long version2 = Long.valueOf(versionString2);
            Response createOrderResponse = createOrderRequest(commerceToolsAccessToken, cartId, version2);
            assertStatus(createOrderResponse, ResponseCodes.CREATED);
        }catch (Exception e){
            Assert.assertTrue(false,"Extension time out 504 ");
        }

    }

}
