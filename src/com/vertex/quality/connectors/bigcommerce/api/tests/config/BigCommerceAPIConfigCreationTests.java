package com.vertex.quality.connectors.bigcommerce.api.tests.config;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceConfigResource;
import com.vertex.quality.connectors.bigcommerce.api.pojos.BigCommerceValidConfigResource;
import com.vertex.quality.connectors.bigcommerce.api.tests.base.BigCommerceAPIBaseTest;
import io.restassured.response.Response;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

/**
 * tests of the config endpoint's method for adding configurations to the connector
 *
 * @author ssalisbury
 */

public class BigCommerceAPIConfigCreationTests extends BigCommerceAPIBaseTest {
    /**
     * tests that a new configuration can be added to the connector
     */
    @Test(groups = {"bigCommerce_apiDisabled", "bigCommerce_config"})
    public void createConfigTest() {
        DateTime creationDate = new DateTime(2012, 10, 31, 11, 59, 59, 999);
        final String creationDateString = creationDate.toString();

        BigCommerceConfigResource newConfig = BigCommerceValidConfigResource
                .builder()
                .companyCode("Co123")
                .dateAdded(creationDateString)
                .username("person")
                .password("ace246")
                .storeId("178")
                .storeName("rando-store")
                .storeApiUrl("https://api.bigcommerce.com/stores/178")
                .storeClientId("dagmgi189r1sgra398131ewaf989")
                .storeClientSecret("GERAG3539AGxww9SG246")
                .storeToken("53aewIWEA15664asewHAWE2ewads")
                .build();

        Response response = apiUtil.sendConfigPostRequest(newConfig);
        apiUtil.assertStatus(response, ResponseCodes.SUCCESS);
    }
}
