package com.vertex.quality.connectors.tradeshift.tests.api;

import com.vertex.quality.connectors.tradeshift.tests.api.base.RequestMappingBaseTest;
import com.vertex.quality.connectors.tradeshift.tests.api.base.keywords.InvoiceKeywords;
import com.vertex.quality.connectors.tradeshift.tests.api.base.keywords.MessageLogKeywords;
import com.vertex.quality.connectors.tradeshift.tests.api.base.keywords.TSUtilities;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class TradeshiftAPIBillingInvoiceTests{
    private TSUtilities utils = new TSUtilities();
    private InvoiceKeywords invoices = new InvoiceKeywords();
    private MessageLogKeywords messages = new MessageLogKeywords();
    private String TENANT_ID = utils.getTENANT_ID();

    /**
     * Posts an invoice ordered in Lansdale PA with a
     * billing address in Philadelphia PA
     *
     * CTRADESHI-518
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void billToPhiladelphiaInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/billing/billToPhiladelphiaPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("66df760c-e84d-42bd-afd6-1008edc303e0",TENANT_ID);
        messages.compare(payload,"<AdministrativeDestination taxAreaId=\"391013000\">\n" +
                "        <StreetAddress1>2119 N 18th St</StreetAddress1>\n" +
                "        <City>Philadelphia</City>\n" +
                "        <MainDivision>PA</MainDivision>\n" +
                "        <PostalCode>19121</PostalCode>\n" +
                "        <Country>US</Country>\n" +
                "      </AdministrativeDestination>");
        messages.compare(payload,"<SubTotal>100.0</SubTotal>\n" +
                "    <Total>108.0</Total>\n" +
                "    <TotalTax>8.0</TotalTax>");
    }

    /**
     * Posts an invoice ordered in PA with a billing
     * address in TX
     *
     * CTRADESHI-516
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void billToTexasInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/billing/billToTexasPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("68df760c-e54d-42bd-afd6-1009edc303e0",TENANT_ID);
        messages.compare(payload,"<AdministrativeDestination taxAreaId=\"440292570\">\n" +
                "        <StreetAddress1>101 Alamo Plaza</StreetAddress1>\n" +
                "        <City>San Antonio</City>\n" +
                "        <MainDivision>TX</MainDivision>\n" +
                "        <PostalCode>78205</PostalCode>\n" +
                "        <Country>US</Country>\n" +
                "      </AdministrativeDestination>");
        messages.compare(payload,"<SubTotal>100.0</SubTotal>\n" +
                "    <Total>108.25</Total>\n" +
                "    <TotalTax>8.25</TotalTax>");
    }

    /**
     * Posts an invoice ordered in PA with a billing
     * address in IL
     *
     * CTRADESHI-517
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void billToIllinoisInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/billing/billToIllinoisPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("63df760c-e52d-42bd-afd6-1004edc303e0",TENANT_ID);
        messages.compare(payload,"<AdministrativeDestination taxAreaId=\"140318411\">\n" +
                "        <StreetAddress1>600 N Clark St.</StreetAddress1>\n" +
                "        <City>Chicago</City>\n" +
                "        <MainDivision>IL</MainDivision>\n" +
                "        <PostalCode>60610</PostalCode>\n" +
                "        <Country>US</Country>\n" +
                "      </AdministrativeDestination>");
        messages.compare(payload,"<SubTotal>100.0</SubTotal>\n" +
                "    <Total>107.25</Total>\n" +
                "    <TotalTax>7.25</TotalTax>");
    }

    /**
     * Posts an invoice ordered in PA with a billing
     * address in CA
     *
     * CTRADESHI-515
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void billToCaliforniaInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/billing/billToCaliforniaPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("63df780c-e54d-42bd-afd6-1004edc303e0",TENANT_ID);
        messages.compare(payload,"<AdministrativeDestination taxAreaId=\"50371900\">\n" +
                "        <StreetAddress1>5757 Wilshire Blvd.</StreetAddress1>\n" +
                "        <City>Los Angeles</City>\n" +
                "        <MainDivision>CA</MainDivision>\n" +
                "        <PostalCode>90036</PostalCode>\n" +
                "        <Country>US</Country>\n" +
                "      </AdministrativeDestination>");
        messages.compare(payload,"<SubTotal>100.0</SubTotal>\n" +
                "    <Total>109.5</Total>\n" +
                "    <TotalTax>9.5</TotalTax>");
    }

    /**
     * Posts an invoice from a supplier in Philadelphia, PA with a delivery
     * address in King of Prussia, PA
     *
     * CTRADESHI-514
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void billFromPhiladelphiaInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/billing/billFromPhiladelphiaPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("66df710c-e54d-42bd-afd6-1006edc303e1",TENANT_ID);
        messages.compare(payload,"<AdministrativeOrigin taxAreaId=\"391013000\">\n" +
                "        <StreetAddress1>2119 N 18th St</StreetAddress1>\n" +
                "        <City>Philadelphia</City>\n" +
                "        <MainDivision>PA</MainDivision>\n" +
                "        <PostalCode>19121</PostalCode>\n" +
                "        <Country>US</Country>\n" +
                "      </AdministrativeOrigin>");
        messages.compare(payload,"<SubTotal>100.0</SubTotal>\n" +
                "    <Total>108.0</Total>\n" +
                "    <TotalTax>8.0</TotalTax>");
    }

    /**
     * Posts an invoice from a supplier in San Antonio, TX with a delivery
     * address in Bridge City, TX
     *
     * CTRADESHI-512
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void billFromTexasInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/billing/billFromTexasPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("66df720c-e54d-42bd-afd6-1008edc303e1",TENANT_ID);
        messages.compare(payload,"<AdministrativeOrigin taxAreaId=\"440292570\">\n" +
                "        <StreetAddress1>101 Alamo Plaza</StreetAddress1>\n" +
                "        <City>San Antonio</City>\n" +
                "        <MainDivision>TX</MainDivision>\n" +
                "        <PostalCode>78205</PostalCode>\n" +
                "        <Country>US</Country>\n" +
                "      </AdministrativeOrigin>");
        messages.compare(payload,"<SubTotal>100.0</SubTotal>\n" +
                "    <Total>108.25</Total>\n" +
                "    <TotalTax>8.25</TotalTax>");
    }

    /**
     * Posts an invoice from a supplier in Chicago, IL with a delivery
     * address in Peoria, IL
     *
     * CTRADESHI-513
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void billFromIllinoisInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/billing/billFromIllinoisPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("66df750c-e54d-42bd-afd6-1005edc303e1",TENANT_ID);
        messages.compare(payload,"<AdministrativeOrigin taxAreaId=\"140318411\">\n" +
                "        <StreetAddress1>600 N Clark St.</StreetAddress1>\n" +
                "        <City>Chicago</City>\n" +
                "        <MainDivision>IL</MainDivision>\n" +
                "        <PostalCode>60610</PostalCode>\n" +
                "        <Country>US</Country>\n" +
                "      </AdministrativeOrigin>");
        messages.compare(payload,"<SubTotal>100.0</SubTotal>\n" +
                "    <Total>110.25</Total>\n" +
                "    <TotalTax>10.25</TotalTax>");
    }

    /**
     * Posts an invoice from a supplier in Los Angeles, CA with a delivery
     * address in San Diego, CA
     *
     * CTRADESHI-511
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void billFromCaliforniaInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/billing/billFromCaliforniaPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("66df760c-e54d-42bd-afd6-1004edc303e1",TENANT_ID);
        messages.compare(payload,"<AdministrativeOrigin taxAreaId=\"50371900\">\n" +
                "        <StreetAddress1>5757 Wilshire Blvd.</StreetAddress1>\n" +
                "        <City>Los Angeles</City>\n" +
                "        <MainDivision>CA</MainDivision>\n" +
                "        <PostalCode>90036</PostalCode>\n" +
                "        <Country>US</Country>\n" +
                "      </AdministrativeOrigin>");
        messages.compare(payload,"<SubTotal>100.0</SubTotal>\n" +
                "    <Total>107.75</Total>\n" +
                "    <TotalTax>7.75</TotalTax>");
    }
}
