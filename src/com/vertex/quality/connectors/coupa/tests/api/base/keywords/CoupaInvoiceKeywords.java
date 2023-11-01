package com.vertex.quality.connectors.coupa.tests.api.base.keywords;

import com.vertex.quality.connectors.coupa.tests.api.base.CoupaInvoiceBaseTest;
import com.vertex.quality.connectors.coupa.tests.utils.CoupaUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CoupaInvoiceKeywords {
    CoupaInvoiceBaseTest invoice = new CoupaInvoiceBaseTest();

    /**
     * posts taxes
     *
     * @return the invoice number of this invoice so we can confirm the result
     */
    public String postTaxes(String filePath) {
        return invoice.postTaxes(filePath);
    }

    /**
     * posts taxes
     *
     * @return the invoice number of this invoice so we can confirm the result
     */
    public String postTaxesDisabledTenant(String filePath) {
        return invoice.postTaxesDisabledTenant(filePath);
    }

    /**
     * post taxes with bad payload
     */
    public void postBadPayload() {
        invoice.postBadPayload();
    }

    /**
     * post tax with bad header auth
     */
    public void postBadTaxAuth() {
        invoice.postBadTaxAuth();
    }

    /**
     * post tax for invoice with allocation
     */
    public String postAllocationInvoice() {
        return invoice.postAllocationInvoice();
    }

    /**
     * post tax for invoice with department and division mapped to account segment 19, 20
     */
    public String postDepartmentDivisionInvoice(String tenant) {
        return invoice.postDepartmentDivisionInvoice(tenant);
    }

    /**
     * posts a multi line non-PO invoice header level invoice
     */
    public String postCoupaNonPOMultiLineHeadInvoice() {
        return invoice.postCoupaNonPOMultiLineHeadInvoice();
    }

    public String postCoupaNonPOMultiLineLineInvoice() {
        return invoice.postCoupaNonPOMultiLineLineInvoice();
    }

    public String coupaNonPOMultiLineHeadInvoiceAllocation() {
        return invoice.coupaNonPOMultiLineHeadInvoiceAllocation();
    }

    public String coupaNonPOMultiLineLineInvoiceAllocation() {
        return invoice.coupaNonPOMultiLineLineInvoiceAllocation();
    }

    public String flexCodeInvoice() {
        return invoice.flexCodeInvoice();
    }

    public String allFlexCodeInvoice() {
        return invoice.allFlexCodeInvoice();
    }

    /**
     * posts charged tax at line level for an invoice
     */
    public String postChargedTaxLineLevel() {
        return invoice.postChargedTaxLineLevel();
    }

    /**
     * posts a weighted invoice with two line items with line level tax
     */
    public String weightedChargesLineLevelInvoice() {
        return invoice.weightedChargesLineLevelInvoice();
    }

    public String weightedChargesNonSummaryInvoice() {
        return invoice.weightedChargesNonSummaryInvoice();
    }

    /**
     * Posts header level tax with a summary tax enabled
     * */
    public String postTaxSummaryChargeable(){
        return invoice.postTaxSummaryChargeable();
    }

    /**
     * Posts header level invoice with a summary tax disabled
     * */
    public String postTaxSummaryNotChargeable(){
        return invoice.postTaxSummaryNotChargeable();
    }

    /**
     * Posts Line level invoice with no vendor tax
     * */
    public String postLineLevelNoVendor(){
        return invoice.postLineLevelNoVendor();
    }

    /**
     * Posts Line level invoice with with a vendor tax
     * */
    public String postLineLevelWithVendor(){
        return invoice.postLineLevelWithVendor();
    }

    /**
     * Posts non-po remit to address xml
     * */
    public String postNonPORemitTo() {
        return invoice.postNonPORemitTo();
    }

    /**
     * Posts Header Level invoice with with no user entered tax but
     * with shipping, handling, and misc costs
     * */
    public String postHeaderLevelNoUserWithMiscCosts(){
        return invoice.postHeaderLevelNoUserWithMiscCosts();
    }

    /**
     * Submits an invoice with an account segment with a 20 character
     * string for request mapping testing
     *
     * @return the invoice number for verification
     * */
    public String sendSegmentCharactersInvoice(){
        return invoice.sendSegmentCharactersInvoice();
    }

    /**
     * Submits an invoice with an account segment with 20 number characters
     * for request mapping testing
     *
     * @return the invoice number for verification
     * */
    public String sendSegmentNumbersInvoice(){
        return invoice.sendSegmentNumbersInvoice();
    }

    /**
     * Submits an invoice with an account segment with 20 special characters
     * for request mapping testing
     *
     * @return the invoice number for verification
     * */
    public String sendSegmentSCharsInvoice(){
        return invoice.sendSegmentSCharsInvoice();
    }
}
