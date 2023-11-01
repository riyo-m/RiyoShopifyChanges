package com.vertex.quality.connectors.tradeshift.tests.api.base;

import com.vertex.quality.connectors.tradeshift.tests.api.base.keywords.TSUtilities;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;


public class RequestMappingBaseTest {

    TSUtilities utils = new TSUtilities();
    /**
     * creates a request mapping
     *
     * @param tenantId
     *
     * @return id of created request mapping
     */
    public String createRequestMapping(String tenantId, String fieldCode, String tradeshiftFieldId) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken()).body("{\n" +
                "    \"tenantId\": \"" +tenantId+"\" ,\n"  +
                "    \"tradeshiftFieldId\": \"" + fieldCode + "\",\n" +
                "    \"fieldId\": \"" + tradeshiftFieldId + "\"\n" +
                "}")
                .post("https://tradeshift.qa.vertexconnectors.com/api/tenants/" + tenantId + "/requestmappings");
        response.then().statusCode(201);

        String id = response.body().asString();

        return id;
    }

    /**
     * creates a request mapping with a duplicate fieldID or source field ID
     *
     * @param tenantId
     * @param fieldCode
     * @param tradeshiftFieldId
     * @param duplicateFieldCode
     *
     * @return the id of the created mapping
     */
    public String createRequestMappingDuplicate(String tenantId, String fieldCode, String tradeshiftFieldId, Boolean duplicateFieldCode) {
        String id = createRequestMapping(tenantId,fieldCode,tradeshiftFieldId);
        if(duplicateFieldCode){
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
            Response response = given().contentType("application/json").header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken()).body("{\n" +
                    "    \"tenantId\": \"" +tenantId+"\" ,\n" +
                    "    \"tradeshiftFieldId\": \"" + fieldCode + "\",\n" +
                    "    \"fieldId\": \"DIVISION\"\n" +
                    "}")
                    .post("https://tradeshift.qa.vertexconnectors.com/api/tenants/" + tenantId + "/requestmappings");
            response.then().statusCode(500);
        }else{
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
            Response response = given().contentType("application/json").header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken()).body("{\n" +
                    "    \"tenantId\": \"" +tenantId+"\" ,\n" +
                    "    \"tradeshiftFieldId\": \"DUPLICATE\",\n" +
                    "    \"fieldId\": \"" + tradeshiftFieldId + "\"\n" +
                    "}")
                    .post("https://tradeshift.qa.vertexconnectors.com/api/tenants/" + tenantId + "/requestmappings");
            response.then().statusCode(500);
        }

        return id;

    }

    /**
     * gets the tradeshift field id from a specified request mapping
     *
     * @param tenantId
     *
     * @return tradeshift field id of a request mapping
     */
    public String getRequestMappingTradeshiftFieldID(String tenantId) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken())
                .get("https://tradeshift.qa.vertexconnectors.com/api/tenants/" + tenantId + "/requestmappings");
        response.then().statusCode(200);

        ArrayList<String> ids = response.body().path("tradeshiftFieldId");

        String id = ids.get(0);

        return id;
    }

    /**
     * gets the vertex field id from a specified request mapping
     *
     * @param tenantId
     *
     * @return vertex field id of a request mapping
     */
    public String getRequestVertexFieldID(String tenantId) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken())
                .get("https://tradeshift.qa.vertexconnectors.com/api/tenants/" + tenantId + "/requestmappings");
        response.then().statusCode(200);

        ArrayList<String> ids = response.body().path("fieldId");

        String id = ids.get(0);

        return id;
    }

    /**
     * deletes specified request mapping; successfully
     *
     * @param tenantId
     * @param requestMappingId
     */
    public void deleteRequestMapping(String tenantId, String requestMappingId) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken())
                .body("{\n" +
                        "    \"id\": \"" + requestMappingId + "\",\n" +
                        "    \"tenantId\":" +tenantId+",\n"  +
                        "    \"fieldId\": \"Taxpayer\",\n" +
                        "    \"tradeshiftFieldId\": \"Mapping2\"\n" +
                        "}").delete("https://tradeshift.qa.vertexconnectors.com/api/tenants/" + tenantId + "/requestmappings/" + requestMappingId);
        response.then().statusCode(204);
    }


}
