package com.vertex.quality.connectors.coupa.tests.api.base.keywords;

import com.vertex.quality.connectors.coupa.tests.api.base.CoupaMessageLogBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CoupaMessageLogKeywords {
    CoupaMessageLogBaseTest logs = new CoupaMessageLogBaseTest();

    /**
     * Gets version number
     *
     * @return version number
     */
    public String getLogs() {
        return logs.getLogs();
    }

    /**
     * Gets gets tax amount from log
     *
     * @return tax amount
     */
    public String getTaxFromLogs(String invoiceNumber) {
        return logs.getTaxFromLogs(invoiceNumber);
    }

    /**
     * Gets invoice verification response for specified invoice number and tenant
     *
     * @return payload for invoice verification response
     */
    public String getTaxFromInvoiceVerificationLog(String invoiceNumber, String tenant) {
        return logs.getTaxFromInvoiceVerificationLog(invoiceNumber,tenant);
    }

    /**
     * Gets invoice verification request for specified invoice number and tenant
     *
     * @return payload for invoice verification request
     */
    public String getTaxFromInvoiceVerificationRequestLog(String invoiceNumber, String tenant) {
        return logs.getTaxFromInvoiceVerificationRequestLog(invoiceNumber,tenant);
    }

    /**
     * Gets tax response for specified invoice number and tenant
     *
     * @return payload for invoice tax response
     */
    public String getTaxFromInvoiceTaxResponse(String invoiceNumber, String tenant) {
        return logs.getTaxFromInvoiceTaxResponse(invoiceNumber,tenant);
    }

    /**
     * Used for negative test, attempts to get logs from a order submitted when logs we were to disabled on that tenant
     */
    public void getTaxFromLogsWhenOff(String invoiceNumber) {
        logs.getTaxFromLogsWhenOff(invoiceNumber);
    }
}
