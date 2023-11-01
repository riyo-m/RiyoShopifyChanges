package com.vertex.quality.connectors.tradeshift.tests.api.base.keywords;

import com.vertex.quality.connectors.tradeshift.tests.api.base.MessageLogBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class MessageLogKeywords {

    MessageLogBaseTest logs = new MessageLogBaseTest();

    /**
     * Gets the purchase order response log from a submitted invoice
     *
     * @param invoiceNumber
     * @param tenant
     *
     * @return the response from the connector
     * */
    public String getTaxFromPurchaseOrderResponse(String invoiceNumber, String tenant) {
        return logs.getTaxFromPurchaseOrderResponse(invoiceNumber,tenant);
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
        return logs.getTaxFromRequisitionResponse(invoiceNumber,tenant);
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
		return logs.getTaxFromAccrualRequest(invoiceNumber,tenant);
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
        return logs.getTaxFromAccrualResponse(invoiceNumber,tenant);
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
        return logs.validateNumberOfLogs(invoiceNumber,tenant);
    }

    public void compare(String response, String expected){
        assertTrue(logs.compareResponse(response,expected));
    }
}