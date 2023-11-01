package com.vertex.quality.connectors.coupa.tests.api.base.keywords;

import com.vertex.quality.connectors.coupa.tests.api.base.CoupaConnectorBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CoupaConnectorKeywords {

    CoupaConnectorBaseTest connector = new CoupaConnectorBaseTest();

    /**
     * Gets version number
     *
     * @return version number
     */
    public String getVersionNumber() {
        return connector.getVersionNumber();
    }

    /**
     * Gets status of database on system status page
     *
     * @return status
     */
    public String getDatabaseStatus() {
        return connector.getDatabaseStatus();
    }
}
