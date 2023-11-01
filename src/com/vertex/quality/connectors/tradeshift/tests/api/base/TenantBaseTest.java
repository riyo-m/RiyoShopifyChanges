package com.vertex.quality.connectors.tradeshift.tests.api.base;

import com.vertex.quality.connectors.tradeshift.tests.api.base.keywords.TSUtilities;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class TenantBaseTest {
    TSUtilities utils = new TSUtilities();

    /**
     * Attempts to create a duplicate tenant
     *
     * @param id
     * @param url
     * */
    public void createDuplicateTenant(String id, String url) {
        createTenant(id);
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken()).body("{\n" +
                "    \"id\": \"" + id + "\",\n" +
                "    \"name\": \"connector-tradeshift-dev\",\n" +
                "    \"taxEngineDefaultTaxpayer\": \"connector-tradeshift-dev\",\n" +
                "    \"logRetentionDays\": \"2\",\n" +
                "    \"taxEngineUrl\": \"" + url + "\"\n" +
                "}").post("https://tradeshift.qa.vertexconnectors.com/api/tenants");
        response.then().statusCode(422);
    }

    /**
     * Creates a tenant with an invalid url for negative tests
     *
     * @param id
     */
    public void createTenantBadURL(String id) {
        if(doesTenantExist(id)){

        }else{
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
            Response response = given().contentType("application/json").header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken()).body("{\n" +
                    "    \"id\": \"" + id + "\",\n" +
                    "    \"name\": \"connector-tradeshift-dev\",\n" +
                    "    \"taxEngineDefaultTaxpayer\": \"connector-tradeshift-dev\",\n" +
                    "    \"logRetentionDays\": \"2\",\n" +
                    "    \"taxEngineUrl\": \"Bad url\"\n" +
                    "}").post("https://tradeshift.qa.vertexconnectors.com/api/tenants");
            response.then().statusCode(400);
        }
    }

    /**
     * Edits a tradeshift tenant resets the dropdown menu selection if applicable
     *
     * @param tenantId
     * @param name
     * @param taxEngineUrl
     * @param defUnEceCategory
     * @param defUnEceScheme
     * @param accrualEnabled
     * @param accrualAmount
     * @param loggingEnabled
     *
     * {"id":"a0cddd0f-aac0-4abe-ad2d-289c1aa862bb","name":"tradeshift","taxEngineUrl":"https://oseries9-final.vertexconnectors.com/vertex-ws/","taxEngineTenancyType":"SINGLE","taxEngineJournaled":false,"performPartialAccruals":true,"taxEngineDefaultTaxpayer":"connector-tradeshift-qa","taxEngineDefaultTaxCategory":"AA","taxEngineDefaultTaxScheme":"VAT","logEnabled":true,"logRetentionDays":30,"partialAccrualThresholdAmount":"5"}
     * */
    public void editTenant(String tenantId, String name, String taxEngineUrl, String defUnEceCategory, String defUnEceScheme,boolean accrualEnabled, String accrualAmount, boolean loggingEnabled, boolean rawRequestEnabled) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken()).body("{\n" +
                "    \"id\": \"" + tenantId + "\",\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"taxEngineUrl\": \"" + taxEngineUrl + "\",\n" +
                "    \"taxEngineTenancyType\": \"SINGLE\",\n" +
                "    \"taxEngineJournaled\": false,\n" +
                "    \"taxEngineDefaultTaxpayer\": \"connector-tradeshift-qa\",\n" +
                "    \"taxEngineDefaultTaxCategory\": \"" + defUnEceCategory + "\",\n" +
                "    \"taxEngineDefaultTaxScheme\": \"" + defUnEceScheme + "\",\n" +
                "    \"performPartialAccruals\": " + accrualEnabled + ",\n" +
                "    \"partialAccrualThresholdAmount\": \"" + accrualAmount + "\",\n" +
                "    \"logEnabled\": " + loggingEnabled + ",\n" +
                "    \"logRetentionDays\":30,\n" +
                "    \"reqResLogEnabled\": " + rawRequestEnabled + "\n" +
                "}")
                .put("https://tradeshift.qa.vertexconnectors.com/api/tenants/" + tenantId);
        response.then().statusCode(200);
    }

    /**
     * Edits a tradeshift tenant and sets dropdown UN/ECE code
     *
     * @param tenantId
     * @param name
     * @param taxEngineUrl
     * @param defUnEceCategory
     * @param defUnEceScheme
     * @param dropDownCategory
     * @param dropDownScheme
     * @param accrualEnabled
     * @param accrualAmount
     * @param loggingEnabled
     */
    public void editTenant(String tenantId, String name, String taxEngineUrl, String defUnEceCategory, String defUnEceScheme, String dropDownCategory, String dropDownScheme, boolean accrualEnabled, String accrualAmount, boolean loggingEnabled) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken()).body("{\n" +
                "    \"id\": \"" + tenantId + "\",\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"taxEngineUrl\": \"" + taxEngineUrl + "\",\n" +
                "    \"taxEngineTenancyType\": \"SINGLE\",\n" +
                "    \"taxEngineJournaled\": false,\n" +
                "    \"taxEngineDefaultTaxpayer\": \"connector-tradeshift-qa\",\n" +
                "    \"taxEngineDefaultTaxCategory\": \"" + defUnEceCategory + "\",\n" +
                "    \"taxEngineDefaultTaxScheme\": \"" + defUnEceScheme + "\",\n" +
                "    \"taxEngineUnEceTaxCategorySource\": \"" + dropDownCategory + "\",\n" +
                "    \"taxEngineUnEceTaxSchemeSource\": \"" + dropDownScheme + "\",\n" +
                "    \"performPartialAccruals\": " + accrualEnabled + ",\n" +
                "    \"partialAccrualThresholdAmount\": \"" + accrualAmount + "\",\n" +
                "    \"logEnabled\": " + loggingEnabled + ",\n" +
                "    \"logRetentionDays\":30\n" +
                "}")
                .put("https://tradeshift.qa.vertexconnectors.com/api/tenants/" + tenantId);
        response.then().statusCode(200);
    }


    /**
     * Checks if a tenant already exists, if not creates tenant
     */
    public void createTenant(String id) {
        String oseriesURL = utils.getOseriesInstance();

        if(doesTenantExist(id)){

        }else{
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
            Response response = given().contentType("application/json").header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken()).body("{\n" +
                    "    \"id\": \"" + id + "\",\n" +
                    "    \"name\": \"connector-tradeshift-dev\",\n" +
                    "    \"taxEngineDefaultTaxpayer\": \"connector-tradeshift-dev\",\n" +
                    "    \"logRetentionDays\": \"2\",\n" +
                    "    \"taxEngineUrl\": \"" + oseriesURL + "\"\n" +
                    "}").post("https://tradeshift.qa.vertexconnectors.com/api/tenants");
            response.then().statusCode(201);
        }
    }



    /**
     * Gets tenants
     *
     * @param tenantId
     *
     * @return does tenant exist boolean
     */
    public boolean doesTenantExist(String tenantId) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        Response response = given().contentType("application/json").header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken()).get("https://tradeshift.qa.vertexconnectors.com/api/tenants");
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
     * Deletes tenants
     */
    public void deleteTenant(String id) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/json").header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken())
                .delete("https://tradeshift.qa.vertexconnectors.com/api/tenants/"+id);
        response.then().statusCode(204);
    }
}
