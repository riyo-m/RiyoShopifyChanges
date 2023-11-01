package com.vertex.quality.connectors.tradeshift.tests.api.base;

import com.vertex.quality.connectors.tradeshift.tests.api.base.keywords.TSUtilities;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class MessageLogBaseTest {

    TSUtilities utils = new TSUtilities();

    /**
     * Gets the purchase order response log from a submitted invoice
     *
     * @param invoiceNumber
     * @param tenant
     *
     * @return the response from the connector
     * */
    public String getTaxFromPurchaseOrderResponse(String invoiceNumber, String tenant) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken())
                .get("https://tradeshift.qa.vertexconnectors.com/api/tenants/" + tenant + "/messagelogs?from="+getCurrentDateTime()+"&documentNumber=" + invoiceNumber+"&type=PURCHASE_ORDER_RESPONSE");

        response.then().statusCode(200);
        String payload = response.body().path("content.payload[0]");

        return payload;
    }

    /**
     * Gets the requisition response log from a submitted invoice
     *
     * @param invoiceNumber
     * @param tenant
     *
     * @return the response from the connector
     * */
    public String getTaxFromRequisitionResponse(String invoiceNumber, String tenant) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken())
                .get("https://tradeshift.qa.vertexconnectors.com/api/tenants/" + tenant + "/messagelogs?from="+getCurrentDateTime()+"&documentNumber=" +invoiceNumber+ "&type=REQUISITION_RESPONSE");

        response.then().statusCode(200);
        String payload = response.body().path("content.payload[0]");

        return payload;
    }

	/**
	 * Gets the accrual request log from a submitted invoice
	 *
	 * @param invoiceNumber
	 * @param tenant
	 *
	 * @return the request from the connector
	 * */
	public String getTaxFromAccrualRequest(String invoiceNumber, String tenant) {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		Response response = given().header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken())
								   .get("https://tradeshift.qa.vertexconnectors.com/api/tenants/" + tenant + "/messagelogs?from="+getCurrentDateTime()+"&documentNumber=" +invoiceNumber+ "&type=ACCRUAL_REQUEST");

		response.then().statusCode(200);
		String payload = response.body().path("content.payload[0]");

		return payload;
	}

    /**
     * Gets the accrual response log from a submitted invoice
     *
     * @param invoiceNumber
     * @param tenant
     *
     * @return the response from the connector
     * */
    public String getTaxFromAccrualResponse(String invoiceNumber, String tenant) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken())
                .get("https://tradeshift.qa.vertexconnectors.com/api/tenants/" + tenant + "/messagelogs?from="+getCurrentDateTime()+"&documentNumber=" +invoiceNumber+ "&type=ACCRUAL_RESPONSE");

        response.then().statusCode(200);
        String payload = response.body().path("content.payload[0]");

        return payload;
    }

    /**
     * Validates that accrual response logs are created for an invoice
     * regardless of document type
     *
     * @param invoiceNumber
     * @param tenant
     *
     * @return whether the request recieves 4 logs when submitted
     * */
    public Boolean validateNumberOfLogs(String invoiceNumber, String tenant) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().header("Cookie","tradeshiftUserSessionId=" + utils.createAccessToken())
                .get("https://tradeshift.qa.vertexconnectors.com/api/tenants/" + tenant + "/messagelogs?from="+getCurrentDateTime()+"&documentNumber=" +invoiceNumber+ "");

        response.then().statusCode(200);
        ArrayList payload = response.body().path("content.payload");

        return (payload.size() % 4 == 0);
    }

    public Boolean compareResponse(String response, String expected) {
        return response.contains(expected);
    }

    /**
     * gets the current time and subtracts 1 hour
     * then formats to ensure we can get the correct invoice from the api
     *
     * @return the properly formatted date and time minus 1 hour
     */
    public String getCurrentDateTime(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime currentDayAndTime = LocalDateTime.now();
        String dayAndTimeMinus1h = dateFormatter.format(currentDayAndTime.minusHours(1));
        return dayAndTimeMinus1h;
    }
}
