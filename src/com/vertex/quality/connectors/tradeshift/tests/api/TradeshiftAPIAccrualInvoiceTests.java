package com.vertex.quality.connectors.tradeshift.tests.api;

import com.vertex.quality.connectors.tradeshift.tests.api.base.keywords.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TradeshiftAPIAccrualInvoiceTests {
    private TSUtilities utils = new TSUtilities();
    private InvoiceKeywords invoices = new InvoiceKeywords();
    private MessageLogKeywords messages = new MessageLogKeywords();
    private String TENANT_ID = utils.getTENANT_ID();

    /**
     * Posts an invoice with accruals enabled
     * Vertex tax matches calculated tax
     *
     * CTRADESHI-632
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void accrualEnabledTaxMatchedInvoiceTest() {
        String invoiceNumber = "107e18e2-81d4-11eb-8dcd-0001ac130003";
        invoices.postAccrualTaxMatchedInvoice(true);
        String payload = messages.getTaxFromAccrualResponse(invoiceNumber,TENANT_ID);
        String requisitionPayload = messages.getTaxFromRequisitionResponse(invoiceNumber,TENANT_ID);
        messages.compare(requisitionPayload,"\"DocumentType\" : \"invoice\",");
        messages.compare(requisitionPayload,"\"IsAudited\" : false,");
        messages.compare(payload,"<ChargedTax>11.0</ChargedTax>\n" +
                "    <SubTotal>200.0</SubTotal>\n" +
                "    <Total>200.0</Total>\n" +
                "    <TotalTax>0.0</TotalTax>");
    }

    /**
     * Posts an invoice with accruals enabled
     * Vertex tax differs from calculated tax
     *
     * CTRADESHI-633
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void accrualEnabledTaxUnmatchedInvoiceTest() {
        String invoiceNumber = "911e18e2-81d4-11eb-8dcd-0001ac130003";
        invoices.postAccrualTaxUnmatchedInvoice(true);
        String requisitionPayload = messages.getTaxFromRequisitionResponse(invoiceNumber,TENANT_ID);
        String payload = messages.getTaxFromAccrualResponse(invoiceNumber,TENANT_ID);
        messages.compare(requisitionPayload,"\"DocumentType\" : \"invoice\",");
        messages.compare(requisitionPayload,"\"IsAudited\" : false,");
        messages.compare(payload,"<ChargedTax>1.0</ChargedTax>\n" +
                "    <SubTotal>200.0</SubTotal>\n" +
                "    <Total>200.0</Total>\n" +
                "    <TotalTax>0.0</TotalTax>");
    }

    /**
     * Posts an invoice with accruals disabled
     * Vertex tax matches calculated tax
     *
     * CTRADESHI-634
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void accrualDisabledTaxMatchedInvoiceTest() {
        String invoiceNumber = "887e18e2-81d4-23eb-8dcd-0001ac130003";
        invoices.postAccrualTaxMatchedInvoice(false);
        String payload = messages.getTaxFromAccrualResponse(invoiceNumber,TENANT_ID);
        String requisitionPayload = messages.getTaxFromRequisitionResponse(invoiceNumber, TENANT_ID);
        messages.compare(requisitionPayload,"\"DocumentType\" : \"invoice\",");
        messages.compare(requisitionPayload,"\"IsAudited\" : false,");
        messages.compare(payload,"<ChargedTax>11.0</ChargedTax>\n" +
                "    <SubTotal>200.0</SubTotal>\n" +
                "    <Total>200.0</Total>\n" +
                "    <TotalTax>0.0</TotalTax>");
    }

    /**
     * Posts an invoice with accruals disabled
     * Vertex tax differs from calculated tax
     *
     * CTRADESHI-635
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void accrualDisabledTaxUnmatchedInvoiceTest() {
        String invoiceNumber = "337e12e2-81d4-23eb-8dcd-0000ac130003";
        invoices.postAccrualTaxUnmatchedInvoice(false);
        String payload = messages.getTaxFromAccrualResponse(invoiceNumber,TENANT_ID);
        String requisitionPayload = messages.getTaxFromRequisitionResponse(invoiceNumber,TENANT_ID);
        messages.compare(requisitionPayload,"\"DocumentType\" : \"invoice\",");
        messages.compare(requisitionPayload,"\"IsAudited\" : false,");
        messages.compare(payload,"<ChargedTax>1.0</ChargedTax>\n" +
                "    <SubTotal>200.0</SubTotal>\n" +
                "    <Total>200.0</Total>\n" +
                "    <TotalTax>0.0</TotalTax>");
    }

    /**
     * Posts a credit note with accruals enabled
     * Vertex tax matches calculated tax
     *
     * CTRADESHI-640
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void accrualEnabledTaxMatchedCreditNoteTest() {
        String invoiceNumber = "227e18e2-81d4-11eb-8dcd-0000ac130003";
        invoices.postAccrualTaxMatchedCreditNote(true);
        String payload = messages.getTaxFromAccrualResponse(invoiceNumber,TENANT_ID);
        String requisitionPayload = messages.getTaxFromRequisitionResponse(invoiceNumber,TENANT_ID);
        messages.compare(requisitionPayload,"\"DocumentType\" : \"creditnote\",");
        messages.compare(requisitionPayload,"\"IsAudited\" : false,");
        messages.compare(payload,"<ChargedTax>11.0</ChargedTax>\n" +
                "    <SubTotal>200.0</SubTotal>\n" +
                "    <Total>200.0</Total>\n" +
                "    <TotalTax>0.0</TotalTax>");
    }

    /**
     * Posts a credit note with accruals enabled
     * Vertex tax differs from calculated tax
     *
     * CTRADESHI-641
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void accrualEnabledTaxUnmatchedCreditNoteTest() {
        String invoiceNumber = "221e18e2-81d4-11eb-8dcd-0000ac130003";
        invoices.postAccrualTaxUnmatchedCreditNote(true);
        String requisitionPayload = messages.getTaxFromRequisitionResponse(invoiceNumber,TENANT_ID);
        String payload = messages.getTaxFromAccrualResponse(invoiceNumber,TENANT_ID);
        messages.compare(requisitionPayload,"\"DocumentType\" : \"creditnote\",");
        messages.compare(requisitionPayload,"\"IsAudited\" : false,");
        messages.compare(payload," <ChargedTax>1.0</ChargedTax>\n" +
                "    <SubTotal>200.0</SubTotal>\n" +
                "    <Total>200.0</Total>\n" +
                "    <TotalTax>0.0</TotalTax>");
    }

    /**
     * Posts a credit note with accruals disabled
     * Vertex tax matches calculated tax
     *
     * CTRADESHI-642
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void accrualDisabledTaxMatchedCreditNoteTest() {
        String invoiceNumber = "121e18e2-81d4-11eb-8dcd-0000ac130003";
        invoices.postAccrualTaxMatchedCreditNote(false);
        String payload = messages.getTaxFromAccrualResponse(invoiceNumber,TENANT_ID);
        String requisitionPayload = messages.getTaxFromRequisitionResponse(invoiceNumber, TENANT_ID);
        messages.compare(requisitionPayload,"\"DocumentType\" : \"creditnote\",");
        messages.compare(requisitionPayload,"\"IsAudited\" : false,");
        messages.compare(payload,"<ChargedTax>11.0</ChargedTax>\n" +
                "    <SubTotal>200.0</SubTotal>\n" +
                "    <Total>200.0</Total>\n" +
                "    <TotalTax>0.0</TotalTax>");
    }

    /**
     * Posts a credit note with accruals disabled
     * Vertex tax differs from calculated tax
     *
     * CTRADESHI-643
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void accrualDisabledTaxUnmatchedCreditNoteTest() {
        String invoiceNumber = "321e18e2-81d4-11eb-8dcd-0000ac130003";
        invoices.postAccrualTaxUnmatchedCreditNote(false);
        String payload = messages.getTaxFromAccrualResponse(invoiceNumber,TENANT_ID);
        String requisitionPayload = messages.getTaxFromRequisitionResponse(invoiceNumber,TENANT_ID);
        messages.compare(requisitionPayload,"\"DocumentType\" : \"creditnote\",");
        messages.compare(requisitionPayload,"\"IsAudited\" : false,");
        messages.compare(payload,"<ChargedTax>1.0</ChargedTax>\n" +
                "    <SubTotal>200.0</SubTotal>\n" +
                "    <Total>200.0</Total>\n" +
                "    <TotalTax>0.0</TotalTax>");
    }

    /**
     * Validates that an accrual response log is null
     * when applied to a request with a requisition document type
     *
     * CTRADESHI-647
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void accrualResponseNegativeTest(){
        String invoiceNumber = "06df720c-e54d-22bd-afd6-0006edc303e0";
        invoices.postInvoice("resources/jsonfiles/tradeshift/accruals/requisitionDocTypePayload.json");

        String requisitionPayload = messages.getTaxFromRequisitionResponse(invoiceNumber,TENANT_ID);
        messages.compare(requisitionPayload,"\"DocumentType\" : \"requisition\",");

        String payload = messages.getTaxFromAccrualResponse(invoiceNumber,TENANT_ID);
        assertNull(payload);
    }

    /**
     * Validates that an accrual response log is given
     * for an invoice submitted with a requisition document type
     *
     * CTRADESHI-646
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void accrualLogSubmittedTest(){
        String invoiceNumber = "06df720c-e54d-22bd-afd6-0006edc303e0";
        invoices.postInvoice("resources/jsonfiles/tradeshift/accruals/requisitionDocTypePayload.json");

        String requisitionPayload = messages.getTaxFromRequisitionResponse(invoiceNumber,TENANT_ID);
        messages.compare(requisitionPayload,"\"DocumentType\" : \"requisition\",");

        Boolean correctLogNumber = messages.validateNumberOfLogs(invoiceNumber,TENANT_ID);

        assertTrue(correctLogNumber);
    }

    /**
     * Assures an accrual does not occur on an invoice when
     * charged tax is above vertex calculated tax
     *
     * CTRADESHI-717
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceTaxAboveAccrualThresholdTest(){
        String invoiceNumber = "66df760c-e54d-42bd-afd6-0000edc013e0";
        invoices.postInvoice("resources/jsonfiles/tradeshift/accruals/accrualEnableTaxAboveThreshold.json");

        String requisitionPayload = messages.getTaxFromRequisitionResponse(invoiceNumber,TENANT_ID);
        messages.compare(requisitionPayload," \"Accruals\" : [ {\n" +
                "        \"TaxableAmount\" : {\n" +
                "          \"value\" : 100.0,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        },\n" +
                "        \"TaxAmount\" : {\n" +
                "          \"value\" : 0.0,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        },");

        String payload = messages.getTaxFromAccrualResponse(invoiceNumber,TENANT_ID);
        messages.compare(payload,"<ChargedTax>8.0</ChargedTax>");
        messages.compare(payload," <CalculatedTax>0.0</CalculatedTax>\n" +
                "        <EffectiveRate>0.0</EffectiveRate>\n" +
                "        <Taxable>100.0</Taxable>");
    }

    /**
     * Assures an accrual does not occur on an invoice when
     * charged tax is above vertex calculated tax
     *
     * CTRADESHI-718
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceTaxEqualToAccrualThresholdTest(){
        String invoiceNumber = "62df760c-e54d-42bd-afd6-0001edc004e0";
        invoices.postInvoice("resources/jsonfiles/tradeshift/accruals/accrualEnabledTaxEqualsExpectedPayload.json");

        String requisitionPayload = messages.getTaxFromRequisitionResponse(invoiceNumber,TENANT_ID);
        messages.compare(requisitionPayload," \"Accruals\" : [ {\n" +
                "        \"TaxableAmount\" : {\n" +
                "          \"value\" : 100.0,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        },\n" +
                "        \"TaxAmount\" : {\n" +
                "          \"value\" : 0.0,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        },");

        String payload = messages.getTaxFromAccrualResponse(invoiceNumber,TENANT_ID);
        messages.compare(payload,"<ChargedTax>6.0</ChargedTax>");
        messages.compare(payload,"  <CalculatedTax>0.0</CalculatedTax>\n" +
                "        <EffectiveRate>0.0</EffectiveRate>\n" +
                "        <Taxable>100.0</Taxable>");
    }

    /**
     * Assures an accrual does not occur on an invoice when
     * charged tax is above vertex calculated tax
     *
     * CTRADESHI-719
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceTaxBelowAccrualThresholdTest(){
        String invoiceNumber = "21df760c-e54d-42bd-afd6-0001edc004e0";
        invoices.postInvoice("resources/jsonfiles/tradeshift/accruals/accrualEnabledTaxBelowThresholdPayload.json");

        String requisitionPayload = messages.getTaxFromRequisitionResponse(invoiceNumber,TENANT_ID);
        messages.compare(requisitionPayload," \"Accruals\" : [ {\n" +
                "        \"TaxableAmount\" : {\n" +
                "          \"value\" : 100.0,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        },\n" +
                "        \"TaxAmount\" : {\n" +
                "          \"value\" : 4.0,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        },");

        String payload = messages.getTaxFromAccrualResponse(invoiceNumber,TENANT_ID);
        messages.compare(payload,"<ChargedTax>2.0</ChargedTax>");
        messages.compare(payload,"<CalculatedTax>4.0</CalculatedTax>\n" +
                "        <EffectiveRate>0.06</EffectiveRate>\n" +
                "        <Taxable>100.0</Taxable>");
    }
}
