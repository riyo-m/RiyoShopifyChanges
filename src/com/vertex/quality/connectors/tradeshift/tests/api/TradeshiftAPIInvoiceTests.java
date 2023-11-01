package com.vertex.quality.connectors.tradeshift.tests.api;

import com.vertex.quality.connectors.tradeshift.tests.api.base.keywords.*;
import com.vertex.quality.connectors.tradeshift.utils.TradeshiftUtils;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TradeshiftAPIInvoiceTests {

    protected String TENANT_ID = "a0cddd0f-aac0-4abe-ad2d-289c1aa862bb";
    protected InvoiceKeywords invoices = new InvoiceKeywords();
    protected MessageLogKeywords messages = new MessageLogKeywords();
    protected RequestMappingKeywords mappings = new RequestMappingKeywords();
    protected TenantKeywords tenants = new TenantKeywords();
    protected TSUtilities utils = new TSUtilities();
    /**
     * CA invoice test
     *
     * CTRADESHI-502
     */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void caInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/stateSpecificRequests/californiaPayload.json");
    }

    /**
     * TX invoice test
     *
     * CTRADESHI-503
     */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void txInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/stateSpecificRequests/texasPayload.json");
    }

    /**
     * IL invoice test
     *
     * CTRADESHI-505
     */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void ilInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/stateSpecificRequests/illinoisPayload.json");
    }

    /**
     * Submits an invoice with a multiple line items
     *
     * CTRADESHI-546
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void multiLineInvoiceTest(){
        invoices.postInvoice("resources/jsonfiles/tradeshift/stateSpecificRequests/multiLineShippingPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("77df760c-e84d-42bd-afd6-1008edc303e0",TENANT_ID);
        messages.compare(payload,"<SubTotal>200.0</SubTotal>\n" +
                "    <Total>212.13</Total>\n" +
                "    <TotalTax>12.13</TotalTax>");
        messages.compare(payload," <Destination taxAreaId=\"310072460\">\n" +
                "          <StreetAddress1>2 oak court</StreetAddress1>\n" +
                "          <City>Stratford</City>\n" +
                "          <MainDivision>NJ</MainDivision>\n" +
                "          <PostalCode>08084</PostalCode>\n" +
                "          <Country>US</Country>\n" +
                "        </Destination>");
        messages.compare(payload,"<Destination taxAreaId=\"200010000\">\n" +
                "          <StreetAddress1>1502 Maine St</StreetAddress1>\n" +
                "          <City>Poland</City>\n" +
                "          <MainDivision>ME</MainDivision>\n" +
                "          <PostalCode>04274</PostalCode>\n" +
                "          <Country>US</Country>\n" +
                "        </Destination>");

    }

    /**
     * Submits an invoice with 4 line items for
     * tax calc
     *
     * CTRADESHI-547
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void fourLineInvoiceTest(){
        invoices.postInvoice("resources/jsonfiles/tradeshift/stateSpecificRequests/fourLineItemInvoicePayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("72df760c-e24d-42bd-afd6-1002edc303e0",TENANT_ID);
        messages.compare(payload,"<SubTotal>400.0</SubTotal>\n" +
                "    <Total>425.51</Total>\n" +
                "    <TotalTax>25.51</TotalTax>");
        messages.compare(payload,"<Destination taxAreaId=\"310072460\">\n" +
                "          <StreetAddress1>2 oak court</StreetAddress1>\n" +
                "          <City>Stratford</City>\n" +
                "          <MainDivision>NJ</MainDivision>\n" +
                "          <PostalCode>08084</PostalCode>\n" +
                "          <Country>US</Country>\n" +
                "        </Destination>");
        messages.compare(payload," <Destination taxAreaId=\"200010000\">\n" +
                "          <StreetAddress1>1502 Maine St</StreetAddress1>\n" +
                "          <City>Poland</City>\n" +
                "          <MainDivision>ME</MainDivision>\n" +
                "          <PostalCode>04274</PostalCode>\n" +
                "          <Country>US</Country>\n" +
                "        </Destination>");
        messages.compare(payload,"<Destination taxAreaId=\"290030431\">\n" +
                "          <StreetAddress1>31900 S Las Vegas Blvd</StreetAddress1>\n" +
                "          <City>Primm</City>\n" +
                "          <MainDivision>NV</MainDivision>\n" +
                "          <PostalCode>89019</PostalCode>\n" +
                "          <Country>US</Country>\n" +
                "        </Destination>");
        messages.compare(payload,"<Destination taxAreaId=\"510250020\">\n" +
                "          <StreetAddress1>40 SE Wyoming Blvd</StreetAddress1>\n" +
                "          <City>Casper</City>\n" +
                "          <MainDivision>WY</MainDivision>\n" +
                "          <PostalCode>82609</PostalCode>\n" +
                "          <Country>US</Country>\n" +
                "        </Destination>");
    }

    /**
     * Submits an invoice with a header level state that applies sales tax
     * but with multiple lines from states with no sales tax
     * to ensure there is no tax applied to the invoice
     *
     * CTRADESHI-548
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void multiLineOriginStateTaxInvoiceTest(){
        invoices.postInvoice("resources/jsonfiles/tradeshift/stateSpecificRequests/multiLineNoSalesTaxPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("77df260c-e14d-42bd-afd6-0004edc303e0",TENANT_ID);
        messages.compare(payload,"<SubTotal>200.0</SubTotal>\n" +
                "    <Total>200.0</Total>\n" +
                "    <TotalTax>0.0</TotalTax>");
        messages.compare(payload,"<Buyer>\n" +
                "        <Destination taxAreaId=\"270490160\">\n" +
                "          <StreetAddress1>3060 N Montana Ave</StreetAddress1>\n" +
                "          <City>Helena</City>\n" +
                "          <MainDivision>MT</MainDivision>\n" +
                "          <PostalCode>59601</PostalCode>\n" +
                "          <Country>US</Country>\n" +
                "        </Destination>");
        messages.compare(payload,"<Buyer>\n" +
                "        <Destination taxAreaId=\"80030070\">\n" +
                "          <StreetAddress1>374 E Main St</StreetAddress1>\n" +
                "          <City>Newark</City>\n" +
                "          <MainDivision>DE</MainDivision>\n" +
                "          <PostalCode>19711</PostalCode>\n" +
                "          <Country>US</Country>\n" +
                "        </Destination>");
    }

    /**
     * Submit an invoice with multiple lines, one from a state with a sales tax
     * and one from a state without one
     *
     * CTRADESHI-557
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void multiLineSplitSalesTaxTest(){
        invoices.postInvoice("resources/jsonfiles/tradeshift/stateSpecificRequests/multiLineOneNoTaxStatePayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("47df260c-e14d-42bd-afd6-0000ecc303e0",TENANT_ID);
        messages.compare(payload,"<SubTotal>200.0</SubTotal>\n" +
                "    <Total>207.0</Total>\n" +
                "    <TotalTax>7.0</TotalTax>\n");
        messages.compare(payload,"<Buyer>\n" +
                "        <Destination taxAreaId=\"340210040\">\n" +
                "          <StreetAddress1>35 Hendersonville Rd</StreetAddress1>\n" +
                "          <City>Asheville</City>\n" +
                "          <MainDivision>NC</MainDivision>\n" +
                "          <PostalCode>28803</PostalCode>\n" +
                "          <Country>US</Country>\n" +
                "        </Destination>");
        messages.compare(payload,"<Buyer>\n" +
                "        <Destination taxAreaId=\"80030070\">\n" +
                "          <StreetAddress1>374 E Main St</StreetAddress1>\n" +
                "          <City>Newark</City>\n" +
                "          <MainDivision>DE</MainDivision>\n" +
                "          <PostalCode>19711</PostalCode>\n" +
                "          <Country>US</Country>\n" +
                "        </Destination>");
    }

    /**
     * Submit an invoice with multiple lines, each from an
     * origin sales tax state
     *
     * CTRADESHI-558
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void multiLineOriginSalesTaxTest(){
        invoices.postInvoice("resources/jsonfiles/tradeshift/stateSpecificRequests/multiLineOriginSalesTaxPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("23df360c-e14d-42bd-afd6-0005ecc303e0",TENANT_ID);
        messages.compare(payload,"<SubTotal>200.0</SubTotal>\n" +
                "    <Total>213.1</Total>\n" +
                "    <TotalTax>13.1</TotalTax>\n");
        messages.compare(payload,"<Buyer>\n" +
                "        <Destination taxAreaId=\"390910000\">\n" +
                "          <StreetAddress1>2222 S Valley Forge Rd</StreetAddress1>\n" +
                "          <City>Lansdale</City>\n" +
                "          <MainDivision>PA</MainDivision>\n" +
                "          <PostalCode>19446</PostalCode>\n" +
                "          <Country>US</Country>\n" +
                "        </Destination>");
        messages.compare(payload,"<Buyer>\n" +
                "        <Destination taxAreaId=\"30130050\">\n" +
                "          <StreetAddress1>990 N Arizona</StreetAddress1>\n" +
                "          <City>Chandler</City>\n" +
                "          <MainDivision>AZ</MainDivision>\n" +
                "          <PostalCode>65224</PostalCode>\n" +
                "          <Country>US</Country>\n" +
                "        </Destination>");
    }

    //No tickets yet
    /**
     * Checks that a submitted invoice has a UN/ECE tax Category applied to it
     *
     * CTRADESHI-
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void checkUnEceCategoryCodeTest(){
        invoices.postInvoice("resources/jsonfiles/tradeshift/stateSpecificRequests/californiaPayload.json");
        String payload = messages.getTaxFromRequisitionResponse("06df760c-e54d-22bd-afd6-0002edc303e0",TENANT_ID);
        messages.compare(payload,"\"TaxCategory\" : {\n" +
                "          \"ID\" : {\n" +
                "            \"schemeID\" : \"UN/ECE 5305\",\n" +
                "            \"schemeAgencyID\" : \"6\",\n" +
                "            \"schemeVersionID\" : \"D08B\",\n" +
                "           ");
    }

    /**
     * Checks that a submitted invoice has a UN/ECE tax scheme applied to it
     *
     * CTRADESHI-
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void checkUnEceSchemeCodeTest(){
        invoices.postInvoice("resources/jsonfiles/tradeshift/stateSpecificRequests/texasPayload.json");
        String payload = messages.getTaxFromRequisitionResponse("86df820c-e54d-42bd-afd6-0000edc303e0",TENANT_ID);
        messages.compare(payload,"\"TaxScheme\" : {\n" +
                "            \"ID\" : {\n" +
                "              \"schemeID\" : \"UN/ECE 5153 Subset\",\n" +
                "              \"schemeAgencyID\" : \"6\",\n" +
                "              \"schemeVersionID\" : \"D08B\",\n" +
                "             ");
    }

    /**
     * Checks that a submitted invoice has charges applied to it and that the tax on the charge is correct
     *
     * CTRADESHI-623
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceChargesTest(){
        invoices.postInvoice("resources/jsonfiles/tradeshift/stateSpecificRequests/invoiceChargesPayload.json");
        String payload = messages.getTaxFromRequisitionResponse("34ad022c-e54d-44bd-afd6-0000edc323e9",TENANT_ID);
        messages.compare(payload,
                "      \"ExpectedSellerChargedTax\" : [ {\n" +
                "        \"TaxableAmount\" : {\n" +
                "          \"value\" : 100.0,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        },");
        messages.compare(payload," \"TaxAmount\" : {\n" +
                "          \"value\" : 6.0,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        }");
    }

    /**
     * Checks that a submitted invoice has charges applied to it and that the tax on the charge is correct
     *
     * CTRADESHI-625
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceChargesOriginStateTest(){
        invoices.postInvoice("resources/jsonfiles/tradeshift/stateSpecificRequests/invoiceChargesOriginStatePayload.json");
        String payload = messages.getTaxFromRequisitionResponse("44ad022c-e54d-44bd-afd6-0004edc323e9",TENANT_ID);
        messages.compare(payload,
                "      \"ExpectedSellerChargedTax\" : [ {\n" +
                "        \"TaxableAmount\" : {\n" +
                "          \"value\" : 100.0,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        },\n" +
                "        \"TaxAmount\" : {\n" +
                "          \"value\" : 6.0,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        },");
    }

    /**
     * Checks that a submitted invoice has charges applied to it and that the tax on the charge is correct
     *
     * CTRADESHI-624
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceChargesDestinationStateTest(){
        invoices.postInvoice("resources/jsonfiles/tradeshift/stateSpecificRequests/invoiceChargesDestinationStatePayload.json");
        String payload = messages.getTaxFromRequisitionResponse("14ad022c-e54d-44bd-afd6-0000edc323e9",TENANT_ID);
        messages.compare(payload,
                "      \"ExpectedSellerChargedTax\" : [ {\n" +
                "        \"TaxableAmount\" : {\n" +
                "          \"value\" : 100.0,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        },\n" +
                "        \"TaxAmount\" : {\n" +
                "          \"value\" : 4.75,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        }");
        messages.compare(payload," \"TaxableAmount\" : {\n" +
                "          \"value\" : 100.0,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        },\n" +
                "        \"TaxAmount\" : {\n" +
                "          \"value\" : 2.25,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        },");
    }

    /**
     * Checks that a submitted invoice with charges applied to it
     * gets the correct tax with header level taxation
     *
     * CTRADESHI-626
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void invoiceChargesAllHeaderTaxTest(){
        invoices.postInvoice("resources/jsonfiles/tradeshift/stateSpecificRequests/invoiceChargesAllHeaderTaxPayload.json");
        String payload = messages.getTaxFromRequisitionResponse("14ad222c-e54d-44bd-afd6-0000edc323e9",TENANT_ID);
        messages.compare(payload,
                "      \"ExpectedSellerChargedTax\" : [ {\n" +
                "        \"TaxableAmount\" : {\n" +
                "          \"value\" : 100.0,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        },\n" +
                "        \"TaxAmount\" : {\n" +
                "          \"value\" : 6.0,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        },");
        messages.compare(payload,"\"TaxableAmount\" : {\n" +
                "          \"value\" : 100.0,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        },\n" +
                "        \"TaxAmount\" : {\n" +
                "          \"value\" : 2.0,\n" +
                "          \"currencyId\" : \"USD\"\n" +
                "        },");

    }

    /**
     *
     */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void rawReqResInvoiceTest() {
        tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","AA","VAT",false, "",true, true);
        invoices.postInvoice("resources/jsonfiles/tradeshift/stateSpecificRequests/rawResponseEnabled.json");
        String payload = messages.getTaxFromRequisitionResponse("37442604-2541-2222-2226-000122230321",TENANT_ID);
        messages.compare(payload,"\"RawResponse\" : {\n" +
                "    \"content\"");

    }

	/**
	 * Validates an invoice passed with request fields - "isAudited: true" & "isRetry: false"
	 *
	 * TICKET
	 */
	@Test(groups = { "tradeshift", "tradeshift_api" })
	public void isAuditedNoRetryIndicatorTest() {
		tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","AA","VAT",true, "11",true, true);
		invoices.postInvoice("resources/jsonfiles/tradeshift/accruals/isAuditedNoRetryIndicator.json");
		String payload = messages.getTaxFromAccrualRequest("3a8c1941-b1a2-5f86-8fc5-e43c0131ac11",TENANT_ID);
		messages.compare(payload,"postToJournal=\"true\"");
	}

	/**
	 * Validates an invoice passed with request fields - "isAudited: false" & "isRetry: false"
	 *
	 * TICKET
	 */
	@Test(groups = { "tradeshift", "tradeshift_api" })
	public void notAuditedNoRetryIndicatorTest() {
		tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","AA","VAT",true, "11",true, true);
		invoices.postInvoice("resources/jsonfiles/tradeshift/accruals/notAuditedNoRetryIndicator.json");
		String payload = messages.getTaxFromAccrualRequest("3a8c1941-b1a2-5f86-8fc5-e43c0131ca01",TENANT_ID);
		messages.compare(payload,"postToJournal=\"false\"");
	}

	/**
	 * Validates an invoice passed with request fields -  "isAudited: true" "isRetry: true"
	 *
	 * TICKET
	 */
	@Test(groups = { "tradeshift", "tradeshift_api" })
	public void isAuditedWithRetryIndicatorTest() {
		tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","AA","VAT",true, "11",true, true);
		invoices.postInvoice("resources/jsonfiles/tradeshift/accruals/isAuditedWithRetryIndicator.json");
		String payload = messages.getTaxFromAccrualRequest("3a8c1941-b1a2-5f86-8fc5-e43c0131bb02",TENANT_ID);
		messages.compare(payload,"postToJournal=\"false\"");
	}

	/**
	 * Validates an invoice passed with request fields -  "isAudited: false" "isRetry: true"
	 *
	 * TICKET
	 */
	@Test(groups = { "tradeshift", "tradeshift_api" })
	public void notAuditedWithRetryIndicatorTest() {
		tenants.editTenant(TENANT_ID,"tradeshift","https://oseries9-final.vertexconnectors.com/vertex-ws/","AA","VAT",true, "11",true, true);
		invoices.postInvoice("resources/jsonfiles/tradeshift/accruals/notAuditedWithRetryIndicator.json");
		String payload = messages.getTaxFromAccrualRequest("3a8c1941-b1a2-5f86-8fc5-e43c0131de01",TENANT_ID);
		messages.compare(payload,"postToJournal=\"false\"");
	}
}
