package com.vertex.quality.connectors.coupa.tests.api.base;

import com.vertex.quality.connectors.coupa.tests.utils.CoupaUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CoupaInvoiceBaseTest {
    File summaryChargeableXMLDataFile = new File("resources/xmlfiles/coupa/HeaderLevel/coupaHeaderLevelSummaryChargeablePayload.xml");
    File summaryNotChargeableXMLDataFile = new File("resources/xmlfiles/coupa/HeaderLevel/coupaHeaderLevelSummaryNotChargeable.xml");
    File lineLevelNoVendorXMLDataFile = new File("resources/xmlfiles/coupa/LineLevel/coupaLineLevelNoVendorTax.xml");
    File lineLevelWithVendorXMLDataFile = new File("resources/xmlfiles/coupa/LineLevel/coupaLineLevelWithVendorTax.xml");
    File coupaNonPORemitToXMLDataFile = new File("resources/xmlfiles/coupa/HeaderLevel/coupaNonPORemitToPayload.xml");
    File headerLevelNoUserTaxWithMiscCosts = new File("resources/xmlfiles/coupa/HeaderLevel/coupaHeaderLevelNoUserWithMiscCosts.xml");
    File headerExemptVendor = new File("resources/xmlfiles/coupa/HeaderLevel/coupaExemptVendorPayload.xml");
    File segmentCharactersInvoice = new File("resources/xmlfiles/coupa/HeaderLevel/coupaReqMapAccountSegmentChars.xml");
    File segmentNumbersInvoice = new File("resources/xmlfiles/coupa/HeaderLevel/coupaReqMapAccountSegmentNum.xml");
    File segmentSpecialCharactersInvoice = new File("resources/xmlfiles/coupa/HeaderLevel/coupaReqMapAccountSegmentSChar.xml");

    CoupaTenantBaseTest tenant = new CoupaTenantBaseTest();
    CoupaUtils utils = new CoupaUtils();

    /**
     * posts taxes
     *
     * @return the invoice number of this invoice so we can confirm the result
     */
    public String postTaxes(String filePath) {
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        File xmlDataFile = new File(filePath);

        CoupaUtils.changeXMLFileInvoiceNumber(xmlDataFile,invoiceNumber);
        tenant.createTenant("test", utils.getOSERIESURL());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(xmlDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/test/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    /**
     * posts taxes
     *
     * @return the invoice number of this invoice so we can confirm the result
     */
    public String postTaxesDisabledTenant(String filePath) {
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        File xmlDataFile = new File(filePath);
        tenant.createTenantLoggingOff(utils.getOSERIESURL());

        CoupaUtils.changeXMLFileInvoiceNumber(xmlDataFile,invoiceNumber);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(xmlDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/test/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    /**
     * post taxes with bad payload
     */
    public void postBadPayload() {
        File xmlDataFile = new File("resources/xmlfiles/coupa/HeaderLevel/coupaBadPayload.xml");

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(xmlDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/1/taxes");
        response.then().statusCode(400);
    }

    /**
     * post tax with bad header auth
     */
    public void postBadTaxAuth() {
        File xmlDataFile = new File("resources/xmlfiles/coupa/HeaderLevel/coupaBaseXML.xml");
        //createTenant("test",OSERIESURL);
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2U6dmVydGV4").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(xmlDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/1/taxes");
        response.then().statusCode(500);
    }

    /**
     * post tax for invoice with allocation
     */
    public String postAllocationInvoice() {
        File xmlDataFile = new File("resources/xmlfiles/coupa/LineLevel/coupaAllocationPayload.xml");
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(xmlDataFile,invoiceNumber);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(xmlDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/1/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    /**
     * post tax for invoice with department and division mapped to account segment 19, 20
     */
    public String postDepartmentDivisionInvoice(String tenant) {
        File xmlDataFile = new File("resources/xmlfiles/coupa/HeaderLevel/coupaDepartmentDivisionPayload.xml");
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(xmlDataFile,invoiceNumber);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(xmlDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/" + tenant + " /taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    /**
     * posts a multi line non-PO invoice header level invoice
     */
    public String postCoupaNonPOMultiLineHeadInvoice() {
        File xmlDataFile = new File("resources/xmlfiles/coupa/HeaderLevel/coupaNonPOMultiLineHeadInvoicePayload.xml");
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(xmlDataFile,invoiceNumber);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(xmlDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/1/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    public String postCoupaNonPOMultiLineLineInvoice() {
        File xmlDataFile = new File("resources/xmlfiles/coupa/LineLevel/coupaNonPOMultiLineLineInvoicePayload.xml");
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(xmlDataFile,invoiceNumber);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(xmlDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/1/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    public String coupaNonPOMultiLineHeadInvoiceAllocation() {
        File xmlDataFile = new File("resources/xmlfiles/coupa/HeaderLevel/coupaNonPOMultiLineHeadAllocationPayload.xml");
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(xmlDataFile,invoiceNumber);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(xmlDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/1/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    public String coupaNonPOMultiLineLineInvoiceAllocation() {
        File xmlDataFile = new File("resources/xmlfiles/coupa/LineLevel/coupaNonPOMultiLineLineAllocationPayload.xml");
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(xmlDataFile,invoiceNumber);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(xmlDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/1/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    public String flexCodeInvoice() {
        File xmlDataFile = new File("resources/xmlfiles/coupa/HeaderLevel/coupaFlexCodePayload.xml");
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(xmlDataFile,invoiceNumber);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(xmlDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/1/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    public String allFlexCodeInvoice() {
        File xmlDataFile = new File("resources/xmlfiles/coupa/HeaderLevel/coupaAllFlexCodePayload.xml");
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(xmlDataFile,invoiceNumber);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(xmlDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/1/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    /**
     * posts charged tax at line level for an invoice
     */
    public String postChargedTaxLineLevel() {
        File xmlDataFile = new File("resources/xmlfiles/coupa/LineLevel/coupaLineLevelInvoicePayload.xml");
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(xmlDataFile,invoiceNumber);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(xmlDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/1/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    /**
     * posts a weighted invoice with two line items with line level tax
     */
    public String weightedChargesLineLevelInvoice() {
        File xmlDataFile = new File("resources/xmlfiles/coupa/LineLevel/coupaWeightedLineLevelPayload.xml");
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(xmlDataFile,invoiceNumber);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(xmlDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/1/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    public String weightedChargesNonSummaryInvoice() {
        File xmlDataFile = new File("resources/xmlfiles/coupa/LineLevel/coupaWeightedLineLevNonSummaryPayload.xml");
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(xmlDataFile,invoiceNumber);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(xmlDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/1/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    /**
     * Posts header level tax with a summary tax enabled
     * */
    public String postTaxSummaryChargeable(){
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(summaryChargeableXMLDataFile,invoiceNumber);
        tenant.createTenant("test",utils.getOSERIESURL());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(summaryChargeableXMLDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/test/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    /**
     * Posts header level invoice with a summary tax disabled
     * */
    public String postTaxSummaryNotChargeable(){
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(summaryNotChargeableXMLDataFile,invoiceNumber);
        tenant.createTenant("test",utils.getOSERIESURL());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(summaryNotChargeableXMLDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/test/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    /**
     * Posts Line level invoice with no vendor tax
     * */
    public String postLineLevelNoVendor(){
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(lineLevelNoVendorXMLDataFile,invoiceNumber);
        tenant.createTenant("test",utils.getOSERIESURL());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(lineLevelNoVendorXMLDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/test/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    /**
     * Posts Line level invoice with with a vendor tax
     * */
    public String postLineLevelWithVendor(){
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(lineLevelWithVendorXMLDataFile,invoiceNumber);
        tenant.createTenant("test",utils.getOSERIESURL());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(lineLevelWithVendorXMLDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/test/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    /**
     * Posts non-po remit to address xml
     * */
    public String postNonPORemitTo() {
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(coupaNonPORemitToXMLDataFile,invoiceNumber);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(coupaNonPORemitToXMLDataFile).post("https://coupa.qa.vertexconnectors.com/api/tenants/1/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    /**
     * Posts Header Level invoice with with no user entered tax but
     * with shipping, handling, and misc costs
     * */
    public String postHeaderLevelNoUserWithMiscCosts(){
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(headerLevelNoUserTaxWithMiscCosts,invoiceNumber);
        tenant.createTenant("test",utils.getOSERIESURL());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(headerLevelNoUserTaxWithMiscCosts).post("https://coupa.qa.vertexconnectors.com/api/tenants/test/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    /**
     * Submits an invoice with an account segment with a 20 character
     * string for request mapping testing
     *
     * @return the invoice number for verification
     * */
    public String sendSegmentCharactersInvoice(){
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(segmentCharactersInvoice,invoiceNumber);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(segmentCharactersInvoice).post("https://coupa.qa.vertexconnectors.com/api/tenants/1/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    /**
     * Submits an invoice with an account segment with 20 number characters
     * for request mapping testing
     *
     * @return the invoice number for verification
     * */
    public String sendSegmentNumbersInvoice(){
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(segmentNumbersInvoice,invoiceNumber);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(segmentNumbersInvoice).post("https://coupa.qa.vertexconnectors.com/api/tenants/1/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }

    /**
     * Submits an invoice with an account segment with 20 special characters
     * for request mapping testing
     *
     * @return the invoice number for verification
     * */
    public String sendSegmentSCharsInvoice(){
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        CoupaUtils.changeXMLFileInvoiceNumber(segmentSpecialCharactersInvoice,invoiceNumber);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response response = given().contentType("application/xml").header("Authorization","Basic Y291cGFVc2VyOnZlcnRleA==").header("Cookie","coupaUserSessionId=8648c0f9-b340-41d1-80eb-8b9aa9fd8b2e")
                .body(segmentSpecialCharactersInvoice).post("https://coupa.qa.vertexconnectors.com/api/tenants/1/taxes");
        response.then().statusCode(200);

        return invoiceNumber;
    }
}
