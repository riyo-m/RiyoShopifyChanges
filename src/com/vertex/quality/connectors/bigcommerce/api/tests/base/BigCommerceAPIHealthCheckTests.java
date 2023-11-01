package com.vertex.quality.connectors.bigcommerce.api.tests.base;

import com.vertex.quality.common.enums.ResponseCodes;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * CBC-186
 *Test case for healthcheck of bigcommerce endpoint.
 *
 * @author Mayur-Kumbhar
 */
public class BigCommerceAPIHealthCheckTests extends BigCommerceAPIBaseTest{


    /**
     *this test case will check the bigcommerce endpoint and return status code 200
     * on successful endpoint check.
     */
    @Test(groups = { "bigCommerce_healthCheck","bigCommerce_regression","bigCommerce_smoke" })
    public void sendHealthCheckEndpointTests()
    {
        Response response=apiUtil.healthCheckEndpoint();
        apiUtil.assertStatus(response, ResponseCodes.SUCCESS);
    }
}
