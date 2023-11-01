package com.vertex.quality.connectors.orocommerce.tests.api;

import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

/**
 * Stores constants & utility functions that are used by many or
 * all tests
 *
 * @author alewis
 */
public class OroAPIBaseTest {

    /**
     * Gets access token for oro commerce api
     *
     * @return string of bearer token
     */
    public String getOroToken() {
        Response responseToken = given().contentType("application/json").body("{\n" +
                "\t\"grant_type\": \"client_credentials\",\n" +
                "\t\"client_id\": \"VLDv0v9yaPhI3Mp7_KV-8yb2pMxFRE-n\",\n" +
                "\t\"client_secret\": \"-nfWMFlPEY6CBpvI2mI_9_Yg_D2i3WZVUOxfwlB_rZGzapMPkj00wMfQmMcAhJKxswkSdQwTGjJtCethjy0pId\"\n" +
                "}").post("https://vertexdev.razoyo.com/oauth2-token");

        JSONObject jsonObject = new JSONObject(responseToken.getBody().asString());
        String accessToken = jsonObject.get("access_token").toString();
        responseToken.then().statusCode(200);

        return accessToken;
    }
}
