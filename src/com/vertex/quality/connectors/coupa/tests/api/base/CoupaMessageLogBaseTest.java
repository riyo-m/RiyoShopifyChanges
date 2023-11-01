package com.vertex.quality.connectors.coupa.tests.api.base;

import com.vertex.quality.connectors.coupa.tests.utils.CoupaUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CoupaMessageLogBaseTest {

    CoupaUtils utils = new CoupaUtils();

    /**
     * Gets version number
     *
     * @return version number
     */
    public String getLogs() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode())
                .get("https://coupa.qa.vertexconnectors.com/api/tenants/1/messagelogs?from=2020-09-11T01:01:01Z&documentNumber=npo2006290313");

        response.then().statusCode(200);
        String payload = response.body().path("content.payload[0]");

        return payload;
    }

    /**
     * Gets gets tax amount from log
     *
     * @return tax amount
     */
    public String getTaxFromLogs(String invoiceNumber) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode())
                .get("https://coupa.qa.vertexconnectors.com/api/tenants/1/messagelogs?from="+utils.getCurrentDateTime()+"&documentNumber=" + invoiceNumber);

        response.then().statusCode(200);
        String payload = response.body().path("content.payload[0]");

        return payload;
    }

    /**
     * Gets invoice verification response for specified invoice number and tenant
     *
     * @return payload for invoice verification response
     */
    public String getTaxFromInvoiceVerificationLog(String invoiceNumber, String tenant) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode())
                .get("https://coupa.qa.vertexconnectors.com/api/tenants/" + tenant + "/messagelogs?from="+utils.getCurrentDateTime()+"&type=INVOICE_VERIFICATION_RESPONSE&documentNumber=" + invoiceNumber);

        response.then().statusCode(200);
        String payload = response.body().path("content.payload[0]");

        return payload;
    }

    /**
     * Gets invoice verification request for specified invoice number and tenant
     *
     * @return payload for invoice verification request
     */
    public String getTaxFromInvoiceVerificationRequestLog(String invoiceNumber, String tenant) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode())
                .get("https://coupa.qa.vertexconnectors.com/api/tenants/" + tenant + "/messagelogs?from="+utils.getCurrentDateTime()+"&type=INVOICE_VERIFICATION_REQUEST&documentNumber=" + invoiceNumber);

        response.then().statusCode(200);
        String payload = response.body().path("content.payload[0]");

        return payload;
    }

    /**
     * Gets tax response for specified invoice number and tenant
     *
     * @return payload for invoice tax response
     */
    public String getTaxFromInvoiceTaxResponse(String invoiceNumber, String tenant) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode())
                .get("https://coupa.qa.vertexconnectors.com/api/tenants/" + tenant + "/messagelogs?from="+utils.getCurrentDateTime()+"&type=INVOICE_TAX_RESPONSE&documentNumber=" + invoiceNumber);

        response.then().statusCode(200);
        String payload = response.body().path("content.payload[0]");

        return payload;
    }

    /**
     * Used for negative test, attempts to get logs from a order submitted when logs we were to disabled on that tenant
     */
    public void getTaxFromLogsWhenOff(String invoiceNumber) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().header("Cookie","coupaUserSessionId=" + utils.createAccessTokenTestMode())
                .get("https://coupa.qa.vertexconnectors.com/api/tenants/test2/messagelogs?from="+utils.getCurrentDateTime()+"&documentNumber="+invoiceNumber);

        response.then().statusCode(200);
    }
}
