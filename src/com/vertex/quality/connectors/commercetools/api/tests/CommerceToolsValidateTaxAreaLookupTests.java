package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/***
 * CSAPCT-217
 * This test is used to Validate the Address Cleansing by validating the Tax Area Lookup Endpoint.
 *
 * @author Mohit.Nirwan
 */

public class CommerceToolsValidateTaxAreaLookupTests extends CommerceToolAPITestUtilities {

    /***
     * Test case for validating the Tax Area Lookup Endpoint
     */
    @Test(groups = { "commercetools_api_regression" })
    public void addressLookupEndpointTest(){
        Response response = addressLookup();
        assertStatus(response, ResponseCodes.SUCCESS);
    }

}
