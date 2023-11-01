package com.vertex.quality.connectors.tradeshift.tests.api;

import com.vertex.quality.connectors.tradeshift.tests.api.base.RequestMappingBaseTest;
import com.vertex.quality.connectors.tradeshift.tests.api.base.keywords.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class TradeshiftAPIShippingInvoiceTests {
    private TSUtilities utils = new TSUtilities();
    private InvoiceKeywords invoices = new InvoiceKeywords();
    private MessageLogKeywords messages = new MessageLogKeywords();
    private String TENANT_ID = utils.getTENANT_ID();

    /**
     * Posts an invoice with a buyer in King of Prussia, PA
     * that ships from Philadelphia, PA
     *
     * CTRADESHI-522
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void shipFromPhiladelphiaInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/shipping/shipFromPhiladelphiaPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("67df760c-e54d-42bd-afd6-0008edc303e0",TENANT_ID);
        messages.compare(payload,"<PhysicalOrigin taxAreaId=\"391013000\">\n" +
                "        <StreetAddress1>2119 N 18th St</StreetAddress1>\n" +
                "        <City>Philadelphia</City>\n" +
                "        <MainDivision>PA</MainDivision>\n" +
                "        <PostalCode>19121</PostalCode>\n" +
                "        <Country>US</Country>\n" +
                "      </PhysicalOrigin>");
        messages.compare(payload," <SubTotal>100.0</SubTotal>\n" +
                "    <Total>108.0</Total>\n" +
                "    <TotalTax>8.0</TotalTax>");
    }

    /**
     * Posts an invoice with a buyer in Bridge City, TX that ships from San Antonio, TX
     *
     * CTRADESHI-520
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void shipFromTexasInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/shipping/shipFromTexasPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("68df760c-e54d-42bd-afd6-0010edc303e0",TENANT_ID);
        messages.compare(payload,"<PhysicalOrigin taxAreaId=\"440292570\">\n" +
                "        <StreetAddress1>101 Alamo Plaza</StreetAddress1>\n" +
                "        <City>San Antonio</City>\n" +
                "        <MainDivision>TX</MainDivision>\n" +
                "        <PostalCode>78205</PostalCode>\n" +
                "        <Country>US</Country>\n" +
                "      </PhysicalOrigin>");
        messages.compare(payload,"<SubTotal>100.0</SubTotal>\n" +
                "    <Total>108.25</Total>\n" +
                "    <TotalTax>8.25</TotalTax>");
    }

    /**
     * Posts an invoice with a buyer in Peoria, IL that ships from Chicago, IL
     *
     * CTRADESHI-521
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void shipFromIllinoisInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/shipping/shipFromIllinoisPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("63df760c-e54d-42bd-afd6-0006edc303e0",TENANT_ID);
        messages.compare(payload,"<PhysicalOrigin taxAreaId=\"140318411\">\n" +
                "        <StreetAddress1>646 N Michigan Ave</StreetAddress1>\n" +
                "        <City>Chicago</City>\n" +
                "        <MainDivision>IL</MainDivision>\n" +
                "        <PostalCode>60525</PostalCode>\n" +
                "        <Country>US</Country>\n" +
                "      </PhysicalOrigin>");
        messages.compare(payload," <SubTotal>100.0</SubTotal>\n" +
                "    <Total>110.25</Total>\n" +
                "    <TotalTax>10.25</TotalTax>");
    }

    /**
     * Posts an invoice with a buyer in San Diego, CA
     * that ships from Los Angeles, CA
     *
     * CTRADESHI-519
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void shipFromCaliforniaInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/shipping/shipFromCaliforniaPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("65df760c-e54d-42bd-afd6-1004edc303e0",TENANT_ID);
        messages.compare(payload," <PhysicalOrigin taxAreaId=\"50371900\">\n" +
                "        <StreetAddress1>5757 Wilshire Blvd.</StreetAddress1>\n" +
                "        <City>Los Angeles</City>\n" +
                "        <MainDivision>CA</MainDivision>\n" +
                "        <PostalCode>90036</PostalCode>\n" +
                "        <Country>US</Country>\n" +
                "      </PhysicalOrigin>");
        messages.compare(payload,"<SubTotal>100.0</SubTotal>\n" +
                "    <Total>107.75</Total>\n" +
                "    <TotalTax>7.75</TotalTax>");
    }

    /**
     * Posts an invoice with a buyer in NJ
     * that ships from DE
     *
     * CTRADESHI-528
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void shipToNewJerseyInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/shipping/shipToNewJerseyPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("38df760c-e54d-42bd-afd6-1000edc303e0",TENANT_ID);
        System.out.println(payload);
        messages.compare(payload,"<Destination taxAreaId=\"310072460\">\n" +
                "        <StreetAddress1>2 Oak Ct</StreetAddress1>\n" +
                "        <City>Stratford</City>\n" +
                "        <MainDivision>NJ</MainDivision>\n" +
                "        <PostalCode>08084</PostalCode>\n" +
                "        <Country>US</Country>\n" +
                "      </Destination>");
        messages.compare(payload,"<SubTotal>100.0</SubTotal>\n" +
                "    <Total>106.63</Total>\n" +
                "    <TotalTax>6.63</TotalTax>");
    }

    /**
     * Posts an invoice with a buyer in FL
     * that ships from DE
     *
     * CTRADESHI-529
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void shipToFloridaInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/shipping/shipToFloridaPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("30df760c-e54d-42bd-afd6-1000edc303e0",TENANT_ID);

        messages.compare(payload,"<Destination taxAreaId=\"100950000\">\n" +
                "        <StreetAddress1>6875 Sand Lake Rd</StreetAddress1>\n" +
                "        <City>Orlando</City>\n" +
                "        <MainDivision>FL</MainDivision>\n" +
                "        <PostalCode>32819</PostalCode>\n" +
                "        <Country>US</Country>\n" +
                "      </Destination>");
        messages.compare(payload,"<SubTotal>100.0</SubTotal>\n" +
                "    <Total>106.5</Total>\n" +
                "    <TotalTax>6.5</TotalTax>");
    }

    /**
     * Posts an invoice with a buyer in Juneau, AK
     * that ships from DE
     *
     * CTRADESHI-530
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void shipToAlaskaInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/shipping/shipToAlaskaPayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("31df760c-e54d-42bd-afd6-1000edc303e0",TENANT_ID);
        messages.compare(payload,"<Destination taxAreaId=\"21100030\">\n" +
                "        <StreetAddress1>2285 Trout St</StreetAddress1>\n" +
                "        <City>Juneau</City>\n" +
                "        <MainDivision>AK</MainDivision>\n" +
                "        <PostalCode>99801</PostalCode>\n" +
                "        <Country>US</Country>\n" +
                "      </Destination>");
        messages.compare(payload,"<SubTotal>100.0</SubTotal>\n" +
                "    <Total>105.0</Total>\n" +
                "    <TotalTax>5.0</TotalTax>");
    }

    /**
     * Posts an invoice with a buyer in DE
     * that ships from NJ
     *
     * CTRADESHI-527
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void shipToDelawareInvoiceTest() {
        invoices.postInvoice("resources/jsonfiles/tradeshift/shipping/shipToDelawarePayload.json");
        String payload = messages.getTaxFromPurchaseOrderResponse("39df760c-e54d-42bd-afd6-1000edc303e0",TENANT_ID);
        messages.compare(payload,"<Destination taxAreaId=\"80030070\">\n" +
                "        <StreetAddress1>374 E Main St</StreetAddress1>\n" +
                "        <City>Newark</City>\n" +
                "        <MainDivision>DE</MainDivision>\n" +
                "        <PostalCode>19711</PostalCode>\n" +
                "        <Country>US</Country>\n" +
                "      </Destination>");
        messages.compare(payload,"<SubTotal>100.0</SubTotal>\n" +
                "    <Total>100.0</Total>\n" +
                "    <TotalTax>0.0</TotalTax>");
    }
}
