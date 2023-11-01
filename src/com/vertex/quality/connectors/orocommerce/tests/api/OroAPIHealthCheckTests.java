package com.vertex.quality.connectors.orocommerce.tests.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.hasToString;

public class OroAPIHealthCheckTests extends OroAPIBaseTest {

}