package com.vertex.quality.connectors.coupa.tests.api.base;

import com.vertex.quality.connectors.coupa.tests.utils.CoupaUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class CoupaRequestMappingBaseTest {

    CoupaUtils utils = new CoupaUtils();

    /**
     * gets the id of a specified request mapping
     *
     * @return id of created request mapping
     */
    public int getRequestMappingID() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode())
                .get("https://coupa.qa.vertexconnectors.com/api/tenants/1/requestmappings");
        response.then().statusCode(200);

        ArrayList<Integer> ids = response.body().path("id");

        int id = ids.get(0);

        return id;
    }

    /**
     * gets the vertex field id from a specified request mapping
     *
     * @return vertex field id of a request mapping
     */
    public String getRequestVertexFieldID() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode())
                .get("https://coupa.qa.vertexconnectors.com/api/tenants/1/requestmappings");
        response.then().statusCode(200);

        ArrayList<String> ids = response.body().path("fieldId");

        String id = ids.get(0);

        return id;
    }


    /**
     * gets the coupa field id from a specified request mapping
     *
     * @return coupa field id of a request mapping
     */
    public String getRequestMappingCoupaFieldID() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode())
                .get("https://coupa.qa.vertexconnectors.com/api/tenants/1/requestmappings");
        response.then().statusCode(200);

        ArrayList<String> ids = response.body().path("coupaFieldId");

        String id = ids.get(0);

        return id;
    }

    /**
     * creates a request mapping
     *
     * @return id of created request mapping
     */
    public String createRequestMapping(String fieldCode, String coupaFieldId) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode()).body("{\n" +
                "    \"tenantId\": \"1\",\n" +
                "    \"fieldId\": \"" + fieldCode + "\",\n" +
                "    \"coupaFieldId\": \"" + coupaFieldId + "\"\n" +
                "}")
                .post("https://coupa.qa.vertexconnectors.com/api/tenants/1/requestmappings");
        response.then().statusCode(201);

        String id = response.body().asString();

        return id;
    }

    /**
     * creates a request mapping with a duplicate fieldID or source field ID
     */
    public void createRequestMappingDuplicate(String fieldCode, String coupaFieldId) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode()).body("{\n" +
                "    \"tenantId\": \"1\",\n" +
                "    \"fieldId\": \"" + fieldCode + "\",\n" +
                "    \"coupaFieldId\": \"" + coupaFieldId + "\"\n" +
                "}")
                .post("https://coupa.qa.vertexconnectors.com/api/tenants/1/requestmappings");
        response.then().statusCode(500);
    }

    /**
     * deletes specified request mapping; successfully
     */
    public void deleteRequestMapping(String id) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode())
                .body("{\n" +
                        "    \"id\": \"" + id + "\",\n" +
                        "    \"tenantId\": \"1\",\n" +
                        "    \"fieldId\": \"FLEX_CODE_6\",\n" +
                        "    \"coupaFieldId\": \"ACCOUNT_SEGMENT_6\"\n" +
                        "}").delete("https://coupa.qa.vertexconnectors.com/api/tenants/1/requestmappings/" + id);
        response.then().statusCode(204);
    }

    /**
     * deletes specified request mapping; with invalid url
     */
    public void deleteRequestMappingInvalidIDURL(String tenantID, String requestID, String fieldCode, String coupaFieldId) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode())
                .body("{\n" +
                        "    \"id\": \"" + tenantID + "\",\n" +
                        "    \"tenantId\": \"2\",\n" +
                        "    \"fieldId\": \"" + fieldCode + "\",\n" +
                        "    \"coupaFieldId\": \"" + coupaFieldId + "\"\n" +
                        "}").delete("https://coupa.qa.vertexconnectors.com/api/tenants/" + tenantID + "/requestmappings/" + requestID);
        response.then().statusCode(404);
    }
}
