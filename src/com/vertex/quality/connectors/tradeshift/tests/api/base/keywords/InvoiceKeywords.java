package com.vertex.quality.connectors.tradeshift.tests.api.base.keywords;

import com.vertex.quality.connectors.tradeshift.tests.api.base.InvoiceBaseTest;
import com.vertex.quality.connectors.tradeshift.tests.api.base.MessageLogBaseTest;
import com.vertex.quality.connectors.tradeshift.tests.api.base.RequestMappingBaseTest;

public class InvoiceKeywords {
    protected MessageLogBaseTest messages = new MessageLogBaseTest();
    protected RequestMappingBaseTest mappings = new RequestMappingBaseTest();
    protected TenantKeywords tenants = new TenantKeywords();
    protected InvoiceBaseTest invoices = new InvoiceBaseTest();
    /**
     * Posts an invoice to generate a vertex calculated tax
     *
     * */
    public void postInvoice(String filePath) {
        invoices.postInvoice(filePath);
    }

    /**
     * Creates a vendor code request mapping and submits
     * an invoice using that mapping
     * */
    public void postVendorCodeInvoice(){
        invoices.postVendorCodeInvoice();
    }

    /**
     * Creates a vendor class request mapping and submits
     * an invoice using that mapping
     * */
    public void postVendorClassInvoice(){
        invoices.postVendorClassInvoice();
    }

    /**
     * Posts an invoice with request mappings for
     * flex codes 1-12
     *
     * @param filePath the path to the request payload
     * */
    public void postFlexCode1through12Invoice(String filePath){
       invoices.postFlexCode1through12Invoice(filePath);
    }

    /**
     * Posts an invoice with request mappings for
     * flex codes 13-25
     *
     * @param filePath the path to the request payload
     * */
    public void postFlexCode13through25Invoice(String filePath){
        invoices.postFlexCode13through25Invoice(filePath);
    }

    /**
     * Posts an invoice with a cost center request mapping
     * */
    public void postCostCenterInvoice(){
        invoices.postCostCenterInvoice();
    }

    /**
     * Posts an invoice with a general ledger account request mapping
     * */
    public void postGLMappingInvoice(){
        invoices.postGLMappingInvoice();
    }

    /**
     * Posts an invoice with a department code request mapping
     * */
    public void postDeptCodeMappingInvoice(){
        invoices.postDeptCodeMappingInvoice();
    }

    /**
     * Posts an invoice with a purchase code request mapping
     * */
    public void postPurchaseCodeMappingInvoice(){
        invoices.postPurchaseCodeMappingInvoice();
    }

    /**
     * Posts an invoice with a purchase class request mapping
     * */
    public void postPurchaseClassMappingInvoice(){
        invoices.postPurchaseClassMappingInvoice();
    }

    public void postUsageClassMappingInvoice(){
        invoices.postUsageClassMappingInvoice();
    }

    public void postUsageCodeMappingInvoice(){
        invoices.postUsageCodeMappingInvoice();
    }

    /**
     * Posts an invoice with extra charges applied at the header level
     * */
    public void postAccrualTaxUnmatchedInvoice(boolean accrualEnabled){
        invoices.postAccrualTaxUnmatchedInvoice(accrualEnabled);
    }


    /**
     * Posts an invoice with extra charges applied at the header level
     * */
    public void postAccrualTaxMatchedInvoice(boolean accrualEnabled){
        invoices.postAccrualTaxMatchedInvoice(accrualEnabled);
    }

    /**
     * Posts a credit note with extra charges applied at the header level
     * */
    public void postAccrualTaxMatchedCreditNote(boolean accrualEnabled){
        invoices.postAccrualTaxMatchedCreditNote(accrualEnabled);
    }

    /**
     * Posts a credit note with extra charges applied at the header level
     * */
    public void postAccrualTaxUnmatchedCreditNote(boolean accrualEnabled){
        invoices.postAccrualTaxUnmatchedCreditNote(accrualEnabled);
    }

    /**
     * Creates a vendor code request mapping and submits
     * an invoice using that mapping
     * */
    public void postUNSPSCCodeInvoice(){
        invoices.postUNSPSCCodeInvoice();
    }

    /**
     * Creates a vendor code request mapping and submits
     * an invoice using that mapping
     * */
    public void postNCMCodeInvoice(){
        invoices.postNCMCodeInvoice();
    }

    /**
     * Creates a vendor code request mapping and submits
     * an invoice using that mapping
     * */
    public void postHSNCodeInvoice(){
        invoices.postHSNCodeInvoice();
    }

    /**
     * Creates a vendor code request mapping and submits
     * an invoice using that mapping
     * */
    public void postServiceCodeInvoice(){
        invoices.postServiceCodeInvoice();
    }

    /**
     * Posts an invoice with multiple line items
     * */
    public void postMutiLineInvoice(){
        invoices.postMutiLineInvoice();//TODO change this name
    }

    /**
     * Posts an invoice with a default UN/ECE code mapped in Oseries
     * */
    public void postDefaultUnEceMappedCodeInvoice(){
        invoices.postDefaultUnEceMappedCodeInvoice();
    }

    /**
     * Posts an invoice with a default UN/ECE code which isn't mapped in Oseries
     * */
    public void postDefaultUnEceUnmappedCodeInvoice(){
        invoices.postDefaultUnEceUnmappedCodeInvoice();
    }

    /**
     * Posts an invoice with Tax and Vertex Tax UN/ECE codes
     * */
    public void postUnEceTaxCodeInvoice(){
        invoices.postUnEceTaxCodeInvoice();
    }

    /**
     * Posts an invoice with UN/ECE codes mapped to flex codes
     * but the invoice submitted has no flex codes
     * */
    public void postUnmappedUNECESourceCodesInvoice(){
        invoices.postUnmappedUNECESourceCodesInvoice();
    }

    /**
     * Posts an invoice with a UN/ECE code mapped to flex codes 3 and 5,
     * creates request mappings for those codes, but the invoice submitted has flex codes 1 and 2
     * */
    public void postMappedUNECESourceCodesWrongFlexCodesInvoice(){
        invoices.postMappedUNECESourceCodesWrongFlexCodesInvoice();
    }

    /**
     * Posts an invoice with a UN/ECE flex code request mapping that is also mapped in Oseries
     */
    public void postMappedUnEceFlexCodeInvoice(){
        invoices.postMappedUnEceFlexCodeInvoice();
    }

    /**
     * Posts an invoice with a UN/ECE flex code request mapping that is not mapped in Oseries
     * */
    public void postUnmappedUnEceFlexCodeInvoice(){
        invoices.postUnmappedUnEceFlexCodeInvoice();
    }
}
