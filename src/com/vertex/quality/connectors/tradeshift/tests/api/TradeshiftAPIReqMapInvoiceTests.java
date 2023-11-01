package com.vertex.quality.connectors.tradeshift.tests.api;

import com.vertex.quality.connectors.tradeshift.tests.api.base.RequestMappingBaseTest;
import com.vertex.quality.connectors.tradeshift.tests.api.base.keywords.*;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class TradeshiftAPIReqMapInvoiceTests {

    private TSUtilities utils = new TSUtilities();
    private InvoiceKeywords invoices = new InvoiceKeywords();
    private MessageLogKeywords messages = new MessageLogKeywords();
    private String TENANT_ID = utils.getTENANT_ID();


    /**
     * PA invoice company code PA test
     *
     * CTRADESHI-504
     */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void companyCodeInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/billing/billToPhiladelphiaPayload.json");
    }

    /**
     * Tests invoice submission using a vendor code
     * request mapping
     *
     * CTRADESHI-409
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceVendorCodeTest(){
        invoices.postVendorCodeInvoice();
        String payload = messages.getTaxFromPurchaseOrderResponse("33df345c-e45d-23bd-afd6-0003edc306a9",TENANT_ID);
        messages.compare(payload,"<Vendor>\n" +
                "        <VendorCode>VENDOR_CODE_VALUE</VendorCode>");
    }

    /**
     * Tests invoice submission with a vendor class
     * request mapping
     *
     * CTRADESHI-410
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceVendorClassTest(){
        invoices.postVendorClassInvoice();
        String payload = messages.getTaxFromPurchaseOrderResponse("22df345c-e45d-23bd-afd6-0001edc306a9",TENANT_ID);
        messages.compare(payload,"<Vendor>\n" +
                "        <VendorCode classCode=\"VENDOR_CLASS_VALUE\">8eeec025-1ca2-4fa5-8e0d-532b18ac84db</VendorCode>");
    }

    /**
     * Tests the api response for an invoice submitted with
     * request mappings for flex codes 1-12 and a requisition document type
     *
     * CTRADESHI-415
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void flexCode1through12InvoiceTest(){
        invoices.postFlexCode1through12Invoice("resources/jsonfiles/tradeshift/requestMappings/flexCode1through12Payload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("60df960c-e54d-44bd-afd6-9869edc323e9",TENANT_ID);
        messages.compare(payload,"<FlexibleCodeField fieldId=\"1\">FLEXCODE1</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"2\">FLEXCODE2</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"3\">FLEXCODE3</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"4\">FLEXCODE4</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"5\">FLEXCODE5</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"6\">FLEXCODE6</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"7\">FLEXCODE7</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"8\">FLEXCODE8</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"9\">FLEXCODE9</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"10\">FLEXCODE10</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"11\">FLEXCODE11</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"12\">FLEXCODE12</FlexibleCodeField>");
    }

    /**
     * Tests the api response for an invoice submitted with
     * request mappings for flex codes 13-25 and a requisition document type
     *
     * CTRADESHI-416
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void flexCode13through25InvoiceTest(){
        invoices.postFlexCode13through25Invoice("resources/jsonfiles/tradeshift/requestMappings/flexCode13through25Payload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("50df960c-e54d-44bd-afd6-9869edc323e9",TENANT_ID);
        messages.compare(payload,"<FlexibleCodeField fieldId=\"13\">FLEXCODE13</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"14\">FLEXCODE14</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"15\">FLEXCODE15</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"16\">FLEXCODE16</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"17\">FLEXCODE17</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"18\">FLEXCODE18</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"19\">FLEXCODE19</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"20\">FLEXCODE20</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"21\">FLEXCODE21</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"22\">FLEXCODE22</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"23\">FLEXCODE23</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"24\">FLEXCODE24</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"25\">FLEXCODE25</FlexibleCodeField>");
    }

    /**
     * API response test for an invoice with a cost center request mapping
     *
     * CTRADESHI-417
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceCostCenterTest(){
        invoices.postCostCenterInvoice();
        String payload = messages.getTaxFromPurchaseOrderResponse("02df960c-e54d-44bd-afd6-0000edc323e9",TENANT_ID);
        System.out.println(payload);
        messages.compare(payload,"<LineItem costCenter=\"COST_CENTER\"");
    }

    /**
     * API response test for an invoice with a general ledger account request mapping
     *
     * CTRADESHI-418
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceGLMappingTest(){
        invoices.postGLMappingInvoice();
        String payload = messages.getTaxFromPurchaseOrderResponse("91df960c-e54d-44bd-afd6-9869edc323e9",TENANT_ID);
        messages.compare(payload,"<LineItem generalLedgerAccount=\"GENERAL_LEDGER_ACCOUNT\"");
    }

    /**
     * API response test for an invoice with a department code request mapping
     *
     * CTRADESHI-419
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceDeptCodeMappingTest(){
        invoices.postDeptCodeMappingInvoice();
        String payload = messages.getTaxFromPurchaseOrderResponse("91df962c-e54d-44bd-afd6-9869edc323e9",TENANT_ID);
        messages.compare(payload,"<LineItem departmentCode=\"DEPARTMENT_CODE\"");
    }

    /**
     * API response test for an invoice with a department code request mapping
     *
     * CTRADESHI-422
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoicePurchaseCodeMappingTest(){
        invoices.postPurchaseCodeMappingInvoice();
        String payload = messages.getTaxFromPurchaseOrderResponse("01df760c-e01d-42bd-afd6-0869edc303e0",TENANT_ID);
        messages.compare(payload,"<Purchase>PURCHASE_CODE</Purchase>");
    }

    /**
     * API response test for an invoice with a department code request mapping
     *
     * CTRADESHI-423
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoicePurchaseClassMappingTest(){
        invoices.postPurchaseClassMappingInvoice();
        String payload = messages.getTaxFromPurchaseOrderResponse("99df760c-e01d-42bd-afd6-0869edc303e0",TENANT_ID);
        messages.compare(payload,"<Purchase purchaseClass=\"PURCHASE_CLASS\"></Purchase>");
    }

    /**
     * API response test for an invoice with a usage code request mapping
     *
     * CTRADESHI-500
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceUsageCodeMappingTest(){
        invoices.postUsageCodeMappingInvoice();
        String payload = messages.getTaxFromPurchaseOrderResponse("99ec001c-e01d-42bd-afd6-0002edc303e0",TENANT_ID);
        messages.compare(payload,"<LineItem usage=\"USAGE\"");
    }

    /**
     * API response test for an invoice with a usage class request mapping
     *
     * CTRADESHI-501
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceUsageClassMappingTest(){
        invoices.postUsageClassMappingInvoice();
        String payload = messages.getTaxFromPurchaseOrderResponse("99af760c-e01d-42bd-afd6-0003edc303e0",TENANT_ID);
        messages.compare(payload,"<LineItem usageClass=\"USAGE_CLASS\"");
    }

    /**
     * Tests invoice submission using a UNSPSC code
     * request mapping
     *
     * CTRADESHI-523
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceUNSPSCCodeTest(){
        invoices.postUNSPSCCodeInvoice();
        String payload = messages.getTaxFromPurchaseOrderResponse("93ec001c-e01d-42bd-afd6-0001edc303e0",TENANT_ID);
        messages.compare(payload,"<CommodityCode commodityCodeType=\"UNSPSC\">UNSPSC_CODE</CommodityCode>");
    }
    /**
     * Submits and invoice with an NCM Code request
     * mapping
     *
     * CTRADESHI-524
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceNCMCodeTest(){
        invoices.postNCMCodeInvoice();
        String payload = messages.getTaxFromPurchaseOrderResponse("82ec001c-e01d-42bd-afd6-0001edc303e0",TENANT_ID);
        messages.compare(payload,"<CommodityCode commodityCodeType=\"NCM\">NCM_CODE</CommodityCode>");
    }

    /**
     * Invoice submission with an HSN Code
     * request mapping
     *
     * CTRADESHI-525
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceHSNCodeTest(){
        invoices.postHSNCodeInvoice();
        String payload = messages.getTaxFromPurchaseOrderResponse("87ec001c-e01d-42bd-afd6-0002edc303e0",TENANT_ID);
        messages.compare(payload,"<CommodityCode commodityCodeType=\"HSN\">HSN_CODE</CommodityCode>");
    }

    /**
     * Submits an invoice with a Service Code
     * request mapping
     *
     * CTRADESHI-526
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceServiceCodeTest(){
        invoices.postServiceCodeInvoice();
        String payload = messages.getTaxFromPurchaseOrderResponse("80ec001c-e01d-42bd-afd6-0002edc303e0",TENANT_ID);
        messages.compare(payload,"<CommodityCode commodityCodeType=\"Service\">SERVICE_CODE</CommodityCode>");
    }

    /**
     * Submits an invoice with default UN/ECE codes mapped in OSeries
     * Does not use source code values from the Tradeshift connector
     *
     * CTRADESHI-578
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void defaultUNECEMappedCodeTest(){
        String DOC_ID = "91df922c-e54d-44bd-afd6-0002adc323e9";
        invoices.postDefaultUnEceMappedCodeInvoice();
        String requisitionPayload = messages.getTaxFromRequisitionResponse(DOC_ID,TENANT_ID);
        String purchaseOrderPayload = messages.getTaxFromPurchaseOrderResponse(DOC_ID,TENANT_ID);
        messages.compare(requisitionPayload," \"TaxCategory\" : {\n" +
                "          \"ID\" : {\n" +
                "            \"schemeID\" : \"UN/ECE 5305\",\n" +
                "            \"schemeAgencyID\" : \"6\",\n" +
                "            \"schemeVersionID\" : \"D08B\",\n" +
                "            \"value\" : \"AA\"\n" +
                "          },\n" +
                "          \"Name\" : {\n" +
                "            \"value\" : \"Sales and Use Tax\"\n" +
                "          },\n" +
                "          \"Percent\" : {\n" +
                "            \"value\" : 0.0\n" +
                "          },\n" +
                "          \"TaxScheme\" : {\n" +
                "            \"ID\" : {\n" +
                "              \"schemeID\" : \"UN/ECE 5153 Subset\",\n" +
                "              \"schemeAgencyID\" : \"6\",\n" +
                "              \"schemeVersionID\" : \"D08B\",\n" +
                "              \"value\" : \"VAT\"\n" +
                "            }");
        messages.compare(purchaseOrderPayload,"<AssistedParameters>\n" +
                "          <AssistedParameter paramName=\"tax.taxCode\" phase=\"POST\" ruleName=\"UNECE5305\" originalValue=\"\">D</AssistedParameter>\n" +
                "          <AssistedParameter paramName=\"tax.vertexTaxCode\" phase=\"POST\" ruleName=\"UNECE5153\" originalValue=\"\">ENV</AssistedParameter>\n" +
                "        </AssistedParameters>");
    }

    /**
     * Submits an invoice with default UN/ECE codes not mapped in OSeries
     * Does not use source code values from the Tradeshift connector
     *
     * CTRADESHI-579
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void defaultUNECEUnmappedCodeTest(){
        String DOC_ID = "40df022c-e54d-44bd-afd6-0002edc323e9";
        invoices.postDefaultUnEceUnmappedCodeInvoice();
        String requisitionPayload = messages.getTaxFromRequisitionResponse(DOC_ID,TENANT_ID);
        String purchaseOrderPayload = messages.getTaxFromPurchaseOrderResponse(DOC_ID,TENANT_ID);
        messages.compare(requisitionPayload," \"TaxCategory\" : {\n" +
                "          \"ID\" : {\n" +
                "            \"schemeID\" : \"UN/ECE 5305\",\n" +
                "            \"schemeAgencyID\" : \"6\",\n" +
                "            \"schemeVersionID\" : \"D08B\",\n" +
                "            \"value\" : \"JNC\"\n" +
                "          },\n" +
                "          \"Name\" : {\n" +
                "            \"value\" : \"Sales and Use Tax\"\n" +
                "          },\n" +
                "          \"Percent\" : {\n" +
                "            \"value\" : 0.0\n" +
                "          },\n" +
                "          \"TaxScheme\" : {\n" +
                "            \"ID\" : {\n" +
                "              \"schemeID\" : \"UN/ECE 5153 Subset\",\n" +
                "              \"schemeAgencyID\" : \"6\",\n" +
                "              \"schemeVersionID\" : \"D08B\",\n" +
                "              \"value\" : \"KER\"\n" +
                "            }");
        messages.compare(purchaseOrderPayload,"<AssistedParameters>\n" +
                "          <AssistedParameter paramName=\"tax.taxCode\" phase=\"POST\" ruleName=\"UNECE5305\" originalValue=\"\">D</AssistedParameter>\n" +
                "          <AssistedParameter paramName=\"tax.vertexTaxCode\" phase=\"POST\" ruleName=\"UNECE5153\" originalValue=\"\">ENV</AssistedParameter>\n" +
                "        </AssistedParameters>");
    }

    /**
     * submits and invoice with a UN/ECE flex code that is also a tax type configured in Oseries
     *
     * CTRADESHI-580
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void mappedUNECEFlexCodeTest(){
        String DOC_ID = "30cf912c-e54d-44bd-afd6-0003edc323e9";
        invoices.postMappedUnEceFlexCodeInvoice();
        String requisitionPayload = messages.getTaxFromRequisitionResponse(DOC_ID,TENANT_ID);
        String purchaseOrderPayload = messages.getTaxFromPurchaseOrderResponse(DOC_ID,TENANT_ID);
        messages.compare(requisitionPayload,"\"TaxCategory\" : {\n" +
                "          \"ID\" : {\n" +
                "            \"schemeID\" : \"UN/ECE 5305\",\n" +
                "            \"schemeAgencyID\" : \"6\",\n" +
                "            \"schemeVersionID\" : \"D08B\",\n" +
                "            \"value\" : \"AAM\"\n" +
                "          },\n" +
                "          \"Name\" : {\n" +
                "            \"value\" : \"Sales and Use Tax\"\n" +
                "          },\n" +
                "          \"Percent\" : {\n" +
                "            \"value\" : 0.0\n" +
                "          },\n" +
                "          \"TaxScheme\" : {\n" +
                "            \"ID\" : {\n" +
                "              \"schemeID\" : \"UN/ECE 5153 Subset\",\n" +
                "              \"schemeAgencyID\" : \"6\",\n" +
                "              \"schemeVersionID\" : \"D08B\",\n" +
                "              \"value\" : \"CAP\"\n" +
                "            }");
        messages.compare(purchaseOrderPayload,"<AssistedParameters>\n" +
                "          <AssistedParameter paramName=\"tax.taxCode\" phase=\"POST\" ruleName=\"UNECE5305\" originalValue=\"\">D</AssistedParameter>\n" +
                "          <AssistedParameter paramName=\"tax.vertexTaxCode\" phase=\"POST\" ruleName=\"UNECE5153\" originalValue=\"\">ENV</AssistedParameter>\n" +
                "        </AssistedParameters>");
    }

    /**
     * Submits an invoice with a UN/ECE flex code
     * that is not a code mapped in Oseries
     *
     * CTRADESHI-581
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void unmappedUNECEFlexCodeTest(){
        String DOC_ID = "3dcf912c-e54d-44bd-afd6-0003edc323e9";
        invoices.postUnmappedUnEceFlexCodeInvoice();
        String requisitionPayload = messages.getTaxFromRequisitionResponse(DOC_ID,TENANT_ID);
        String purchaseOrderPayload = messages.getTaxFromPurchaseOrderResponse(DOC_ID,TENANT_ID);
        messages.compare(requisitionPayload,"\"TaxCategory\" : {\n" +
                "          \"ID\" : {\n" +
                "            \"schemeID\" : \"UN/ECE 5305\",\n" +
                "            \"schemeAgencyID\" : \"6\",\n" +
                "            \"schemeVersionID\" : \"D08B\",\n" +
                "            \"value\" : \"JNC\"\n" +
                "          },\n" +
                "          \"Name\" : {\n" +
                "            \"value\" : \"Sales and Use Tax\"\n" +
                "          },\n" +
                "          \"Percent\" : {\n" +
                "            \"value\" : 0.0\n" +
                "          },\n" +
                "          \"TaxScheme\" : {\n" +
                "            \"ID\" : {\n" +
                "              \"schemeID\" : \"UN/ECE 5153 Subset\",\n" +
                "              \"schemeAgencyID\" : \"6\",\n" +
                "              \"schemeVersionID\" : \"D08B\",\n" +
                "              \"value\" : \"KER\"\n" +
                "            }");
        messages.compare(purchaseOrderPayload,"<AssistedParameters>\n" +
                "          <AssistedParameter paramName=\"tax.taxCode\" phase=\"POST\" ruleName=\"UNECE5305\" originalValue=\"\">D</AssistedParameter>\n" +
                "          <AssistedParameter paramName=\"tax.vertexTaxCode\" phase=\"POST\" ruleName=\"UNECE5153\" originalValue=\"\">ENV</AssistedParameter>\n" +
                "        </AssistedParameters>");
    }

    /**
     * submits an invoice where the UN/ECE 5305 and 5153 Code Sources
     * are taken from the the Tax Code and Vertex Tax Code
     *
     * CTRADESHI-582
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void UNECETaxAndVertexTaxCodeTest(){
        String DOC_ID = "31bf022c-e54d-44bd-afd6-0002edc323e9";
        invoices.postUnEceTaxCodeInvoice();
        String requisitionPayload = messages.getTaxFromRequisitionResponse(DOC_ID,TENANT_ID);
        String purchaseOrderPayload = messages.getTaxFromPurchaseOrderResponse(DOC_ID,TENANT_ID);
        System.out.println(requisitionPayload);
        messages.compare(requisitionPayload," \"TaxCategory\" : {\n" +
                "          \"ID\" : {\n" +
                "            \"schemeID\" : \"UN/ECE 5305\",\n" +
                "            \"schemeAgencyID\" : \"6\",\n" +
                "            \"schemeVersionID\" : \"D08B\",\n" +
                "            \"value\" : \"A\"\n" +
                "          },\n" +
                "          \"Name\" : {\n" +
                "            \"value\" : \"Sales and Use Tax\"\n" +
                "          },\n" +
                "          \"Percent\" : {\n" +
                "            \"value\" : 0.0\n" +
                "          },\n" +
                "          \"TaxScheme\" : {\n" +
                "            \"ID\" : {\n" +
                "              \"schemeID\" : \"UN/ECE 5153 Subset\",\n" +
                "              \"schemeAgencyID\" : \"6\",\n" +
                "              \"schemeVersionID\" : \"D08B\",\n" +
                "              \"value\" : \"AAM\"\n" +
                "            }");
        messages.compare(purchaseOrderPayload,"<AssistedParameters>\n" +
                "          <AssistedParameter paramName=\"tax.taxCode\" phase=\"POST\" ruleName=\"UNECE5305\" originalValue=\"\">D</AssistedParameter>\n" +
                "          <AssistedParameter paramName=\"tax.vertexTaxCode\" phase=\"POST\" ruleName=\"UNECE5153\" originalValue=\"\">ENV</AssistedParameter>\n" +
                "        </AssistedParameters>");
    }

    /**
     * submits an invoice where the UN/ECE 5305 and 5153 Code Sources
     * are provided in the connector, but the invoice submitted does not
     * provide those codes
     *
     * the invoice should use default values for these UN/ECE codes
     *
     * CTRADESHI-616
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void UNECEUnmappedSourceCodesNoFlexCodesTest(){
        String DOC_ID = "31bc072c-e54d-44bd-afd6-0003edc323e9" ;
        invoices.postUnmappedUNECESourceCodesInvoice();
        String requisitionPayload = messages.getTaxFromRequisitionResponse(DOC_ID,TENANT_ID);
        String purchaseOrderPayload = messages.getTaxFromPurchaseOrderResponse(DOC_ID,TENANT_ID);
        messages.compare(requisitionPayload," \"TaxCategory\" : {\n" +
                "          \"ID\" : {\n" +
                "            \"schemeID\" : \"UN/ECE 5305\",\n" +
                "            \"schemeAgencyID\" : \"6\",\n" +
                "            \"schemeVersionID\" : \"D08B\",\n" +
                "            \"value\" : \"AA\"\n" +
                "          },\n" +
                "          \"Name\" : {\n" +
                "            \"value\" : \"Sales and Use Tax\"\n" +
                "          },\n" +
                "          \"Percent\" : {\n" +
                "            \"value\" : 0.0\n" +
                "          },\n" +
                "          \"TaxScheme\" : {\n" +
                "            \"ID\" : {\n" +
                "              \"schemeID\" : \"UN/ECE 5153 Subset\",\n" +
                "              \"schemeAgencyID\" : \"6\",\n" +
                "              \"schemeVersionID\" : \"D08B\",\n" +
                "              \"value\" : \"VAT\"\n" +
                "            }");
        messages.compare(purchaseOrderPayload,"<AssistedParameters>\n" +
                "          <AssistedParameter paramName=\"tax.taxCode\" phase=\"POST\" ruleName=\"UNECE5305\" originalValue=\"\">D</AssistedParameter>\n" +
                "          <AssistedParameter paramName=\"tax.vertexTaxCode\" phase=\"POST\" ruleName=\"UNECE5153\" originalValue=\"\">ENV</AssistedParameter>\n" +
                "        </AssistedParameters>");
    }

    /**
     * submits an invoice where the UN/ECE 5305 and 5153 Code Sources
     * are set to flex codes in the connector, a request mapping for those codes
     * is created, but the submitted invoice provides flex
     * codes different than those set in the connector
     *
     * the invoice should use default values for these UN/ECE codes
     *
     * CTRADESHI-617
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void UNECEmappedSourceCodesWrongFlexCodesTest(){
        String DOC_ID = "21ad022c-e54d-44bd-afd6-0002edc323e9" ;
        invoices.postMappedUNECESourceCodesWrongFlexCodesInvoice();
        String requisitionPayload = messages.getTaxFromRequisitionResponse(DOC_ID,TENANT_ID);
        String purchaseOrderPayload = messages.getTaxFromPurchaseOrderResponse(DOC_ID,TENANT_ID);
        messages.compare(requisitionPayload," \"TaxCategory\" : {\n" +
                "          \"ID\" : {\n" +
                "            \"schemeID\" : \"UN/ECE 5305\",\n" +
                "            \"schemeAgencyID\" : \"6\",\n" +
                "            \"schemeVersionID\" : \"D08B\",\n" +
                "            \"value\" : \"AA\"\n" +
                "          },\n" +
                "          \"Name\" : {\n" +
                "            \"value\" : \"Sales and Use Tax\"\n" +
                "          },\n" +
                "          \"Percent\" : {\n" +
                "            \"value\" : 0.0\n" +
                "          },\n" +
                "          \"TaxScheme\" : {\n" +
                "            \"ID\" : {\n" +
                "              \"schemeID\" : \"UN/ECE 5153 Subset\",\n" +
                "              \"schemeAgencyID\" : \"6\",\n" +
                "              \"schemeVersionID\" : \"D08B\",\n" +
                "              \"value\" : \"VAT\"\n" +
                "            }");
        messages.compare(purchaseOrderPayload,"<AssistedParameters>\n" +
                "          <AssistedParameter paramName=\"tax.taxCode\" phase=\"POST\" ruleName=\"UNECE5305\" originalValue=\"\">D</AssistedParameter>\n" +
                "          <AssistedParameter paramName=\"tax.vertexTaxCode\" phase=\"POST\" ruleName=\"UNECE5153\" originalValue=\"\">ENV</AssistedParameter>\n" +
                "        </AssistedParameters>");
    }

    /**
     * Tests the api response for an invoice submitted with
     * request mappings for flex codes 1-12 and an invoice document type
     *
     * CTRADESHI-644
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceDocTypeflexCode1through12InvoiceTest(){
        String invoiceNumber = "10dc960c-e54d-42bd-afd6-0000edc323e9";
        invoices.postFlexCode1through12Invoice("resources/jsonfiles/tradeshift/requestMappings/invoiceDocTypeFlex1through12Payload.json");

        String requisitionPayload = messages.getTaxFromRequisitionResponse(invoiceNumber,TENANT_ID);
        messages.compare(requisitionPayload,"\"DocumentType\" : \"invoice\",");

        String payload = messages.getTaxFromPurchaseOrderResponse(invoiceNumber,TENANT_ID);
        messages.compare(payload,"<FlexibleCodeField fieldId=\"1\">FLEXCODE1</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"2\">FLEXCODE2</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"3\">FLEXCODE3</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"4\">FLEXCODE4</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"5\">FLEXCODE5</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"6\">FLEXCODE6</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"7\">FLEXCODE7</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"8\">FLEXCODE8</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"9\">FLEXCODE9</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"10\">FLEXCODE10</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"11\">FLEXCODE11</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"12\">FLEXCODE12</FlexibleCodeField>");
    }

    /**
     * Tests the api response for an invoice submitted with
     * request mappings for flex codes 13-25 and an invoice document type
     *
     * CTRADESHI-645
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceDocTypeflexCode13through25InvoiceTest(){
        String invoiceNumber = "40af960c-e54d-42bd-afd6-0001edc323e9";
        invoices.postFlexCode13through25Invoice("resources/jsonfiles/tradeshift/requestMappings/invoiceDocTypeFlex13through25Payload.json");

        String requisitionPayload = messages.getTaxFromRequisitionResponse(invoiceNumber,TENANT_ID);
        messages.compare(requisitionPayload,"\"DocumentType\" : \"invoice\",");

        String payload = messages.getTaxFromPurchaseOrderResponse(invoiceNumber,TENANT_ID);
        messages.compare(payload,"<FlexibleCodeField fieldId=\"13\">FLEXCODE13</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"14\">FLEXCODE14</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"15\">FLEXCODE15</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"16\">FLEXCODE16</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"17\">FLEXCODE17</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"18\">FLEXCODE18</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"19\">FLEXCODE19</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"20\">FLEXCODE20</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"21\">FLEXCODE21</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"22\">FLEXCODE22</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"23\">FLEXCODE23</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"24\">FLEXCODE24</FlexibleCodeField>");
        messages.compare(payload,"<FlexibleCodeField fieldId=\"25\">FLEXCODE25</FlexibleCodeField>");
    }
}
