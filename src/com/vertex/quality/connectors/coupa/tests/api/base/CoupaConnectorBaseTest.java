package com.vertex.quality.connectors.coupa.tests.api.base;

import com.vertex.quality.connectors.coupa.tests.utils.CoupaUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CoupaConnectorBaseTest {

    CoupaUtils utils = new CoupaUtils();

    /**
     * Gets version number
     *
     * @return version number
     */
    public String getVersionNumber() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode())
                .get("https://coupa.qa.vertexconnectors.com/api/status");

        response.then().statusCode(200);
        String version = response.body().path("connectorVersion");

        return version;
    }

    /**
     * Gets status of database on system status page
     *
     * @return status
     */
    public String getDatabaseStatus() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode())
                .get("https://coupa.qa.vertexconnectors.com/api/status");

        response.then().statusCode(200);
        String status = response.body().path("databaseStatus");

        return status;
    }
}
