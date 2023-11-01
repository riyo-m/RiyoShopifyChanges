package com.vertex.quality.connectors.coupa.tests.api.base;

import com.vertex.quality.connectors.coupa.tests.utils.CoupaUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class CoupaTenantBaseTest {

    CoupaUtils utils = new CoupaUtils();

    /**
     * Gets tenants
     */
    public boolean doesTenantExist(String tenantId) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        Response response = given().contentType("application/json").header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode()).get("https://coupa.qa.vertexconnectors.com/api/tenants");
        response.then().statusCode(200);
        ArrayList<String> ids = response.body().path("id");
        for(String id : ids){
            if(id.equals(tenantId)){
                return true;
            }
        }
        return false;
    }

    /**
     * Updates a already created tenant
     */
    public void updateTenant(String id, String url) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode()).body("{\n" +
                "    \"id\": \"1\",\n" +
                "    \"name\": \"Test coupa tenant\",\n" +
                "    \"taxEngineDefaultTaxpayer\": \"connector-coupa-dev\",\n" +
                "    \"taxEngineUrl\": \"" + url + "\"\n" +
                "}").put("https://coupa.qa.vertexconnectors.com/api/tenants/1");
        response.then().statusCode(200);
    }

    /**
     * attempts to update an already create tenant with bad id
     */
    public void updateTenantBadIDTenant(String id, String url) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode()).body("{\n" +
                "    \"id\": \"t\",\n" +
                "    \"name\": \"Test coupa tenant\",\n" +
                "    \"taxEngineDefaultTaxpayer\": \"Test coupa tenant\",\n" +
                "    \"taxEngineUrl\": \"" + url + "\"\n" +
                "}").put("https://coupa.qa.vertexconnectors.com/api/tenants/t");
        response.then().statusCode(404);
    }

    public void createTenant(String id, String url) {
        if(doesTenantExist(id)){

        }else{
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
            Response response = given().contentType("application/json").header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode()).body("{\n" +
                    "    \"id\": \"" + id + "\",\n" +
                    "    \"name\": \"connector-coupa-qa\",\n" +
                    "    \"taxEngineDefaultTaxpayer\": \"connector-coupa-qa\",\n" +
                    "    \"logRetentionDays\": \"2\",\n" +
                    "    \"taxEngineUrl\": \"" + url + "\"\n" +
                    "}").post("https://coupa.qa.vertexconnectors.com/api/tenants");
            response.then().statusCode(201);
        }
    }

    public void createTenantLoggingOff(String url) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode()).body("{\n" +
                "    \"id\": \"test\",\n" +
                "    \"name\": \"Test coupa tenant\",\n" +
                "    \"taxEngineDefaultTaxpayer\": \"Test coupa tenant\",\n" +
                "    \"taxEngineUrl\": \"" + url + "\",\n" +
                "    \"logEnabled\": false\n" +
                "}").post("https://coupa.qa.vertexconnectors.com/api/tenants");
        response.then().statusCode(201);
    }

    public void createTenantBadURL(String url) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode()).body("{\n" +
                "    \"id\": \"test\",\n" +
                "    \"name\": \"Test coupa tenant\",\n" +
                "    \"taxEngineDefaultTaxpayer\": \"Test coupa tenant\",\n" +
                "    \"taxEngineUrl\": \"" + url + "\"\n" +
                "}").post("https://coupa.qa.vertexconnectors.com/api/tenants");
        response.then().statusCode(400);
    }

    public void deleteTenant() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode())
                .delete("https://coupa.qa.vertexconnectors.com/api/tenants/test");
        response.then().statusCode(204);
    }
}
