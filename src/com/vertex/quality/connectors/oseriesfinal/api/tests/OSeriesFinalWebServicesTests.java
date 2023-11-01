package com.vertex.quality.connectors.oseriesfinal.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.oseriesfinal.api.base.OSeriesFinalAPIBaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class contains all the test cases regarding OSeries final web services
 *
 * @author Rohit.Mogane
 */
public class OSeriesFinalWebServicesTests extends OSeriesFinalAPIBaseTest {

    String refreshCacheInput = null;

    /**
     * test to refresh cache for OSeries web services
     */
    @Test
    public void cacheRefreshTest() {
        refreshCacheInput = getRefreshCacheData();
        Response response = refreshCacheOseries(refreshCacheInput);
        Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS.getResponseCode());
    }
}
