package com.vertex.quality.connectors.tradeshift.tests.api.base;

import com.vertex.quality.connectors.tradeshift.tests.api.base.keywords.MessageLogKeywords;
import com.vertex.quality.connectors.tradeshift.tests.api.base.keywords.RequestMappingKeywords;
import com.vertex.quality.connectors.tradeshift.tests.api.base.keywords.TSUtilities;
import com.vertex.quality.connectors.tradeshift.tests.api.base.keywords.TenantKeywords;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class InvoiceBaseTest {
    TSUtilities utils = new TSUtilities();
    TenantBaseTest tenants = new TenantBaseTest();
    RequestMappingBaseTest rq = new RequestMappingBaseTest();
    String TENANT_ID = utils.getTENANT_ID();
    /**
     * Posts an invoice to generate a vertex calculated tax
     *
     * */
    public void postInvoice(String filePath) {
        File jsonDataFile = new File(filePath);
        Response response = given().contentType("application/json").header("Authorization", "Basic dHJhZGVzaGlmdC1xYS1hZG1pbjp0cmFkM3NoaWZ0YWRtaW4x").header("Cookie", "tradeshiftUserSessionId=382c7367-ca69-4210-9016-b0e30de075a8")
                .body(jsonDataFile).post("https://tradeshift.qa.vertexconnectors.com/api/taxes/");
        response.then().statusCode(200);
    }

    /**
     * Creates a vendor code request mapping and submits
     * an invoice using that mapping
     * */
    public void postVendorCodeInvoice(){
        String mapping = rq.createRequestMapping(TENANT_ID,"vendorCode","VENDOR_CODE");
        postInvoice("resources/jsonfiles/tradeshift/requestMappings/vendorCodePayload.json");
        rq.deleteRequestMapping(TENANT_ID,mapping);
    }

    /**
     * Creates a vendor class request mapping and submits
     * an invoice using that mapping
     * */
    public void postVendorClassInvoice(){
        String mapping = rq.createRequestMapping(TENANT_ID,"vendorClass","VENDOR_CLASS");
        postInvoice("resources/jsonfiles/tradeshift/requestMappings/vendorClassPayload.json");
        rq.deleteRequestMapping(TENANT_ID,mapping);
    }

    /**
     * Posts an invoice with request mappings for
     * flex codes 1-12
     *
     * @param filePath the path to the request payload
     * */
    public void postFlexCode1through12Invoice(String filePath){
        String mapping1 = rq.createRequestMapping(TENANT_ID,"flexCode1","FLEX_CODE_1");
        String mapping2 = rq.createRequestMapping(TENANT_ID,"flexCode2","FLEX_CODE_2");
        String mapping3 = rq.createRequestMapping(TENANT_ID,"flexCode3","FLEX_CODE_3");
        String mapping4 = rq.createRequestMapping(TENANT_ID,"flexCode4","FLEX_CODE_4");
        String mapping5 = rq.createRequestMapping(TENANT_ID,"flexCode5","FLEX_CODE_5");
        String mapping6 = rq.createRequestMapping(TENANT_ID,"flexCode6","FLEX_CODE_6");
        String mapping7 = rq.createRequestMapping(TENANT_ID,"flexCode7","FLEX_CODE_7");
        String mapping8 = rq.createRequestMapping(TENANT_ID,"flexCode8","FLEX_CODE_8");
        String mapping9 = rq.createRequestMapping(TENANT_ID,"flexCode9","FLEX_CODE_9");
        String mapping10 = rq.createRequestMapping(TENANT_ID,"flexCode10","FLEX_CODE_10");
        String mapping11 = rq.createRequestMapping(TENANT_ID,"flexCode11","FLEX_CODE_11");
        String mapping12 = rq.createRequestMapping(TENANT_ID,"flexCode12","FLEX_CODE_12");

        postInvoice(filePath);

        rq.deleteRequestMapping(TENANT_ID,mapping1);
        rq.deleteRequestMapping(TENANT_ID,mapping2);
        rq.deleteRequestMapping(TENANT_ID,mapping3);
        rq.deleteRequestMapping(TENANT_ID,mapping4);
        rq.deleteRequestMapping(TENANT_ID,mapping5);
        rq.deleteRequestMapping(TENANT_ID,mapping6);
        rq.deleteRequestMapping(TENANT_ID,mapping7);
        rq.deleteRequestMapping(TENANT_ID,mapping8);
        rq.deleteRequestMapping(TENANT_ID,mapping9);
        rq.deleteRequestMapping(TENANT_ID,mapping10);
        rq.deleteRequestMapping(TENANT_ID,mapping11);
        rq.deleteRequestMapping(TENANT_ID,mapping12);
    }

    /**
     * Posts an invoice with request mappings for
     * flex codes 13-25
     *
     * @param filePath the path to the request payload
     * */
    public void postFlexCode13through25Invoice(String filePath){
        String mapping13 = rq.createRequestMapping(TENANT_ID,"flexCode13","FLEX_CODE_13");
        String mapping14 = rq.createRequestMapping(TENANT_ID,"flexCode14","FLEX_CODE_14");
        String mapping15 = rq.createRequestMapping(TENANT_ID,"flexCode15","FLEX_CODE_15");
        String mapping16 = rq.createRequestMapping(TENANT_ID,"flexCode16","FLEX_CODE_16");
        String mapping17 = rq.createRequestMapping(TENANT_ID,"flexCode17","FLEX_CODE_17");
        String mapping18 = rq.createRequestMapping(TENANT_ID,"flexCode18","FLEX_CODE_18");
        String mapping19 = rq.createRequestMapping(TENANT_ID,"flexCode19","FLEX_CODE_19");
        String mapping20 = rq.createRequestMapping(TENANT_ID,"flexCode20","FLEX_CODE_20");
        String mapping21 = rq.createRequestMapping(TENANT_ID,"flexCode21","FLEX_CODE_21");
        String mapping22 = rq.createRequestMapping(TENANT_ID,"flexCode22","FLEX_CODE_22");
        String mapping23 = rq.createRequestMapping(TENANT_ID,"flexCode23","FLEX_CODE_23");
        String mapping24 = rq.createRequestMapping(TENANT_ID,"flexCode24","FLEX_CODE_24");
        String mapping25 = rq.createRequestMapping(TENANT_ID,"flexCode25","FLEX_CODE_25");

        postInvoice(filePath);

        rq.deleteRequestMapping(TENANT_ID,mapping13);
        rq.deleteRequestMapping(TENANT_ID,mapping14);
        rq.deleteRequestMapping(TENANT_ID,mapping15);
        rq.deleteRequestMapping(TENANT_ID,mapping16);
        rq.deleteRequestMapping(TENANT_ID,mapping17);
        rq.deleteRequestMapping(TENANT_ID,mapping18);
        rq.deleteRequestMapping(TENANT_ID,mapping19);
        rq.deleteRequestMapping(TENANT_ID,mapping20);
        rq.deleteRequestMapping(TENANT_ID,mapping21);
        rq.deleteRequestMapping(TENANT_ID,mapping22);
        rq.deleteRequestMapping(TENANT_ID,mapping23);
        rq.deleteRequestMapping(TENANT_ID,mapping24);
        rq.deleteRequestMapping(TENANT_ID,mapping25);
    }

    /**
     * Posts an invoice with a cost center request mapping
     * */
    public void postCostCenterInvoice(){
        String mapping = rq.createRequestMapping(TENANT_ID,"costCenter","COST_CENTER");
        postInvoice("resources/jsonfiles/tradeshift/requestMappings/costCenterMappingPayload.json");
        rq.deleteRequestMapping(TENANT_ID,mapping);
    }

    /**
     * Posts an invoice with a general ledger account request mapping
     * */
    public void postGLMappingInvoice(){
        String mapping = rq.createRequestMapping(TENANT_ID,"glAccount","GENERAL_LEDGER_ACCOUNT");
        postInvoice("resources/jsonfiles/tradeshift/requestMappings/generalLedgerAccountMappingPayload.json");
        rq.deleteRequestMapping(TENANT_ID,mapping);
    }

    /**
     * Posts an invoice with a department code request mapping
     * */
    public void postDeptCodeMappingInvoice(){
        String mapping = rq.createRequestMapping(TENANT_ID,"deptCode","DEPARTMENT_CODE");
        postInvoice("resources/jsonfiles/tradeshift/requestMappings/departmentCodePayload.json");
        rq.deleteRequestMapping(TENANT_ID,mapping);
    }

    /**
     * Posts an invoice with a purchase code request mapping
     * */
    public void postPurchaseCodeMappingInvoice(){
        String mapping = rq.createRequestMapping(TENANT_ID,"purchaseCode","PURCHASE_CODE");
        postInvoice("resources/jsonfiles/tradeshift/requestMappings/purchaseCodeReqMapPayload.json");
        rq.deleteRequestMapping(TENANT_ID,mapping);
    }

    /**
     * Posts an invoice with a purchase class request mapping
     * */
    public void postPurchaseClassMappingInvoice(){
        String mapping = rq.createRequestMapping(TENANT_ID,"purchaseClass","PURCHASE_CLASS");
        postInvoice("resources/jsonfiles/tradeshift/requestMappings/purchaseClassReqMapPayload.json");
        rq.deleteRequestMapping(TENANT_ID,mapping);
    }

    public void postUsageClassMappingInvoice(){
        String mapping = rq.createRequestMapping(TENANT_ID,"usageClass","USAGE_CLASS");
        postInvoice("resources/jsonfiles/tradeshift/requestMappings/usageClassPayload.json");
        rq.deleteRequestMapping(TENANT_ID,mapping);
    }

    public void postUsageCodeMappingInvoice(){
        String mapping = rq.createRequestMapping(TENANT_ID,"usageCode","USAGE");
        postInvoice("resources/jsonfiles/tradeshift/requestMappings/usageCodePayload.json");
        rq.deleteRequestMapping(TENANT_ID,mapping);
    }

    /**
     * Posts an invoice with extra charges applied at the header level
     * */
    public void postAccrualTaxUnmatchedInvoice(boolean accrualEnabled){
        if(accrualEnabled){
            tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","F","VAT",true, "1",true,true);
            postInvoice("resources/jsonfiles/tradeshift/accruals/accrualEnabledTaxUnmatched.json");
        }else{
            tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","F","VAT",false, "",true,true);
            postInvoice("resources/jsonfiles/tradeshift/accruals/accrualDisabledTaxUnmatched.json");
        }
    }


    /**
     * Posts an invoice with extra charges applied at the header level
     * */
    public void postAccrualTaxMatchedInvoice(boolean accrualEnabled){
        if(accrualEnabled){
            tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","F","VAT",true, "11",true,true);
            postInvoice("resources/jsonfiles/tradeshift/accruals/accrualEnabledTaxMatched.json");
        }else{
            tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","F","VAT",false, "",true,true);
            postInvoice("resources/jsonfiles/tradeshift/accruals/accrualDisabledTaxMatched.json");
        }
    }

    /**
     * Posts a credit note with extra charges applied at the header level
     * */
    public void postAccrualTaxMatchedCreditNote(boolean accrualEnabled){
        if(accrualEnabled){
            tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","F","VAT",true, "11",true,true);
            postInvoice("resources/jsonfiles/tradeshift/accruals/accrualEnabledTaxMatchedCreditNotePayload.json");
        }else{
            tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","F","VAT",false, "",true,true);
            postInvoice("resources/jsonfiles/tradeshift/accruals/accrualDisabledTaxMatchedCreditNotePayload.json");
        }
    }

    /**
     * Posts a credit note with extra charges applied at the header level
     * */
    public void postAccrualTaxUnmatchedCreditNote(boolean accrualEnabled){
        if(accrualEnabled){
            tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","F","VAT",true, "1",true,true);
            postInvoice("resources/jsonfiles/tradeshift/accruals/accrualEnabledTaxUnmatchedCreditNotePayload.json");
        }else{
            tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","F","VAT",false, "",true,true);
            postInvoice("resources/jsonfiles/tradeshift/accruals/accrualDisabledTaxUnmatchedCreditNotePayload.json");
        }
    }

    /**
     * Creates a vendor code request mapping and submits
     * an invoice using that mapping
     * */
    public void postUNSPSCCodeInvoice(){
        String mapping = rq.createRequestMapping(TENANT_ID,"unspscCode","UNSPSC_CODE");
        postInvoice("resources/jsonfiles/tradeshift/requestMappings/unspscCodeReqMappingPayload.json");
        rq.deleteRequestMapping(TENANT_ID,mapping);
    }

    /**
     * Creates a vendor code request mapping and submits
     * an invoice using that mapping
     * */
    public void postNCMCodeInvoice(){
        String mapping = rq.createRequestMapping(TENANT_ID,"ncmCode","NCM_CODE");
        postInvoice("resources/jsonfiles/tradeshift/requestMappings/ncmCodeReqMappingPayload.json");
        rq.deleteRequestMapping(TENANT_ID,mapping);
    }

    /**
     * Creates a vendor code request mapping and submits
     * an invoice using that mapping
     * */
    public void postHSNCodeInvoice(){
        String mapping = rq.createRequestMapping(TENANT_ID,"hsnCode","HSN_CODE");
        postInvoice("resources/jsonfiles/tradeshift/requestMappings/hsnCodeReqMappingPayload.json");
        rq.deleteRequestMapping(TENANT_ID,mapping);
    }

    /**
     * Creates a vendor code request mapping and submits
     * an invoice using that mapping
     * */
    public void postServiceCodeInvoice(){
        String mapping = rq.createRequestMapping(TENANT_ID,"serviceCode","SERVICE_CODE");
        postInvoice("resources/jsonfiles/tradeshift/requestMappings/serviceCodeReqMappingPayload.json");
        rq.deleteRequestMapping(TENANT_ID,mapping);
    }

    /**
     * Posts an invoice with multiple line items
     * */
    public void postMutiLineInvoice(){
        File jsonDataFile = new File("resources/jsonfiles/tradeshift/stateSpecificRequests/multiLineShippingPayload.json");

        Response response = given().contentType("application/json").header("Authorization", "Basic dHJhZGVzaGlmdC1xYTp0cmFkM3NoaWZ0YWRtaW4x").header("Cookie", "tradeshiftUserSessionId=382c7367-ca69-4210-9016-b0e30de075a8")
                .body(jsonDataFile).post("https://tradeshift.qa.vertexconnectors.com/api/taxes/");
        response.then().statusCode(200);
    }

    /**
     * Posts an invoice with a default UN/ECE code mapped in Oseries
     * */
    public void postDefaultUnEceMappedCodeInvoice(){
        tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","AA","VAT",false, "",true,true);
        postInvoice("resources/jsonfiles/tradeshift/unece/defaultUnEceMappedCodePayload.json");

    }

    /**
     * Posts an invoice with a default UN/ECE code which isn't mapped in Oseries
     * */
    public void postDefaultUnEceUnmappedCodeInvoice(){
        tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","JNC","KER",false, "", true,true);
        postInvoice("resources/jsonfiles/tradeshift/unece/defaultUnEceCodeUnmappedPayload.json");
    }

    /**
     * Posts an invoice with Tax and Vertex Tax UN/ECE codes
     * */
    public void postUnEceTaxCodeInvoice(){
        tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","F","VAT","TAX_CODE","VERTEX_TAX_CODE",false, "",true);
        postInvoice("resources/jsonfiles/tradeshift/unece/taxCodeUnEcePayload.json");
        tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","AA","VAT",false, "",true,true);
    }

    /**
     * Posts an invoice with UN/ECE codes mapped to flex codes
     * but the invoice submitted has no flex codes
     * */
    public void postUnmappedUNECESourceCodesInvoice(){
        tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","AA","VAT","FLEX_CODE_5","FLEX_CODE_6",false, "",true);
        postInvoice("resources/jsonfiles/tradeshift/unece/unmappedSourceCodesPayload.json");
        tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","AA","VAT",false, "",true,true);
    }

    /**
     * Posts an invoice with a UN/ECE code mapped to flex codes 3 and 5,
     * creates request mappings for those codes, but the invoice submitted has flex codes 1 and 2
     * */
    public void postMappedUNECESourceCodesWrongFlexCodesInvoice(){
        String mapping = rq.createRequestMapping(TENANT_ID,"flexCode5","FLEX_CODE_5");
        String mapping2 = rq.createRequestMapping(TENANT_ID,"flexCode3","FLEX_CODE_3");
        tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","AA","VAT","FLEX_CODE_5","FLEX_CODE_6",false, "",true);
        postInvoice("resources/jsonfiles/tradeshift/unece/mappedSourceCodesWrongFlexCodesPayload.json");
        tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","AA","VAT",false, "",true,true);
        rq.deleteRequestMapping(TENANT_ID,mapping);
        rq.deleteRequestMapping(TENANT_ID,mapping2);
    }

    /**
     * Posts an invoice with a UN/ECE flex code request mapping that is also mapped in Oseries
     */
    public void postMappedUnEceFlexCodeInvoice(){
        tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","F","VAT","FLEX_CODE_5","FLEX_CODE_2",false, "",true);
        String mapping = rq.createRequestMapping(TENANT_ID,"flexCode5","FLEX_CODE_5");
        String mapping2 = rq.createRequestMapping(TENANT_ID,"flexCode2","FLEX_CODE_2");
        postInvoice("resources/jsonfiles/tradeshift/unece/mappedUnEceFlexCodePayload.json");
        tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","AA","VAT",false, "",true,true);
        rq.deleteRequestMapping(TENANT_ID,mapping);
        rq.deleteRequestMapping(TENANT_ID,mapping2);
    }

    /**
     * Posts an invoice with a UN/ECE flex code request mapping that is not mapped in Oseries
     * */
    public void postUnmappedUnEceFlexCodeInvoice(){
        tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","F","VAT","FLEX_CODE_5","FLEX_CODE_2",false, "",true);
        String mapping = rq.createRequestMapping(TENANT_ID,"flexCode5","FLEX_CODE_5");
        String mapping2 = rq.createRequestMapping(TENANT_ID,"flexCode2","FLEX_CODE_2");
        postInvoice("resources/jsonfiles/tradeshift/unece/unmappedUnEceFlexCodePayload.json");
        tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","AA","VAT",false, "",true,true);
        rq.deleteRequestMapping(TENANT_ID,mapping);
        rq.deleteRequestMapping(TENANT_ID,mapping2);
    }
}
