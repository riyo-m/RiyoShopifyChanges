package com.vertex.quality.connectors.dynamics365.sales.tests;

import com.vertex.quality.connectors.dynamics365.sales.pages.*;
import com.vertex.quality.connectors.dynamics365.sales.tests.base.SalesBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * this class represents all the VAT test cases for the Sales Orders
 *
 * @author Vivek Olumbe
 */
public class SalesVATOrdersTests extends SalesBaseTest {

    /**
     * CDCRM-194
     * Create Order with EU origin and US destination
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderEUtoUSTest() {
        String customerAccount = "Test Account";
        String shipFromLocation = "FR Paris";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "6.00";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test","Euro","EUPriceList");
        orderPage.enterShipFromAddress(shipFromLocation);
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("201 Granite Run Drive", "Lancaster", "17601", "USA");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");


        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<PhysicalOrigin taxAreaId=\"802500000\">\n" +
                "                    <StreetAddress1>Port de la Bourdonnais</StreetAddress1>\n" +
                "                    <City>Paris</City>\n" +
                "                    <PostalCode>75007</PostalCode>\n" +
                "                    <Country>France</Country>\n" +
                "                </PhysicalOrigin>"));
        assertTrue(xml.contains("<Destination taxAreaId=\"390710000\">\n" +
                "                    <StreetAddress1>201 Granite Run Dr</StreetAddress1>\n" +
                "                    <City>Lancaster</City>\n" +
                "                    <MainDivision>PA</MainDivision>\n" +
                "                    <PostalCode>17601-6824</PostalCode>\n" +
                "                    <Country>USA</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"Euro\" isoCurrencyCodeAlpha=\"EUR\" isoCurrencyCodeNum=\"978\">1.0</CurrencyConversion>\n" +
                "                </Destination>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78282\">FRANCE</Jurisdiction>\n" +
                "                <CalculatedTax>0.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.0</EffectiveRate>\n" +
                "                <Taxable>100.0</Taxable>\n" +
                "                <Imposition impositionId=\"1\">VAT</Imposition>\n" +
                "                <ImpositionType impositionTypeId=\"19\">VAT</ImpositionType>\n" +
                "                <SellerRegistrationId>FR12345678901</SellerRegistrationId>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"31152\">PENNSYLVANIA</Jurisdiction>\n" +
                "                <CalculatedTax>6.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.06</EffectiveRate>\n" +
                "                <Taxable>100.0</Taxable>\n" +
                "                <Imposition impositionId=\"1\">Sales and Use Tax</Imposition>\n" +
                "                <ImpositionType impositionTypeId=\"1\">General Sales and Use Tax</ImpositionType>"));
        assertTrue(xml.contains("<TotalTax>6.0</TotalTax>"));
    }

    /**
     * CDCRM-195
     * Create Order with US origin and EU destination
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderUStoEUTest() {
        String customerAccount = "FR Account";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "0.00";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("Port de la Bourdonnais", "Paris", "75007", "France");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");


        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<PhysicalOrigin taxAreaId=\"390910000\">\n" +
                "                    <StreetAddress1>2301 Renaissance Blvd 12</StreetAddress1>\n" +
                "                    <City>King of Prussia</City>\n" +
                "                    <PostalCode>19406-2772</PostalCode>\n" +
                "                    <Country>USA</Country>\n" +
                "                </PhysicalOrigin>"));
        assertTrue(xml.contains("<Destination taxAreaId=\"802500000\">\n" +
                "                    <StreetAddress1>Port de la Bourdonnais</StreetAddress1>\n" +
                "                    <City>Paris</City>\n" +
                "                    <PostalCode>75007</PostalCode>\n" +
                "                    <Country>France</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"US Dollar\" isoCurrencyCodeAlpha=\"USD\" isoCurrencyCodeNum=\"840\">1.0</CurrencyConversion>\n" +
                "                </Destination>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78282\">FRANCE</Jurisdiction>\n" +
                "                <CalculatedTax>0.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.0</EffectiveRate>"));
        assertTrue(xml.contains("<TotalTax>0.0</TotalTax>"));
    }

    /**
     * CDCRM-196
     * Create Order with FR origin and DE destination
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderFRtoDETest() {
        String customerAccount = "DE Account";
        String shipFromLocation = "FR Marseille";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "20.00";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test","Euro","EUPriceList");
        orderPage.enterShipFromAddress(shipFromLocation);
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("Allée du Stade", "Berlin", "13405", "DE");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");


        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<TotalTax>20.0</TotalTax>"));
        assertTrue(xml.contains("<PhysicalOrigin taxAreaId=\"802500000\">\n" +
                "                    <StreetAddress1>38-40 Quai de Rive Neuve</StreetAddress1>\n" +
                "                    <City>Marseille</City>\n" +
                "                    <PostalCode>13007</PostalCode>\n" +
                "                    <Country>France</Country>\n" +
                "                </PhysicalOrigin>"));
        assertTrue(xml.contains("<Destination taxAreaId=\"802760000\">\n" +
                "                    <StreetAddress1>Allée du Stade</StreetAddress1>\n" +
                "                    <City>Berlin</City>\n" +
                "                    <PostalCode>13405</PostalCode>\n" +
                "                    <Country>DE</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"Euro\" isoCurrencyCodeAlpha=\"EUR\" isoCurrencyCodeNum=\"978\">1.0</CurrencyConversion>\n" +
                "                </Destination>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78282\">FRANCE</Jurisdiction>\n" +
                "                <CalculatedTax>20.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.2</EffectiveRate>\n" +
                "                <Taxable>100.0</Taxable>\n" +
                "                <Imposition impositionId=\"1\">VAT</Imposition>\n" +
                "                <ImpositionType impositionTypeId=\"19\">VAT</ImpositionType>"));
        assertTrue(xml.contains("<SellerRegistrationId>FR12345678901</SellerRegistrationId>"));
    }

    /**
     * CDCRM-197
     * Create Order with DE origin and DE destination
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderDEtoDETest() {
        String customerAccount = "DE Account";
        String shipFromLocation = "DE Berlin";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "58.90";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test","Euro","EUPriceList");
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("Allée du Stade", "Berlin", "13405", "DE");
        orderPage.clickSave();
        orderPage.enterShipFromAddress(shipFromLocation);

        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.addProduct(productName, "2", null);
        orderPage.enterFreightAmount("10");
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");


        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<TotalTax>58.9</TotalTax>"));
        assertTrue(xml.contains("<PhysicalOrigin taxAreaId=\"802760000\">\n" +
                "                    <StreetAddress1>Oranienstraße 138</StreetAddress1>\n" +
                "                    <City>Berlin</City>\n" +
                "                    <PostalCode>10969</PostalCode>\n" +
                "                    <Country>Germany</Country>\n" +
                "                </PhysicalOrigin>"));
        assertTrue(xml.contains("<Destination taxAreaId=\"802760000\">\n" +
                "                    <StreetAddress1>Allée du Stade</StreetAddress1>\n" +
                "                    <City>Berlin</City>\n" +
                "                    <PostalCode>13405</PostalCode>\n" +
                "                    <Country>DE</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"Euro\" isoCurrencyCodeAlpha=\"EUR\" isoCurrencyCodeNum=\"978\">1.0</CurrencyConversion>\n" +
                "                </Destination>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78283\">GERMANY</Jurisdiction>"));
        assertTrue(xml.contains("<EffectiveRate>0.19</EffectiveRate>"));
        assertTrue(xml.contains("<Imposition impositionId=\"1\">VAT</Imposition>\n" +
                "                <ImpositionType impositionTypeId=\"19\">VAT</ImpositionType>"));
        assertTrue(xml.contains("<SellerRegistrationId>DE 123456789</SellerRegistrationId>"));
    }

    /**
     * CDCRM-198
     * Create Order for UK Island with no tax
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderUKIslandNoTaxTest() {
        String customerAccount = "UK Account";
        String shipFromLocation = "UK Island";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "0.00";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test","Euro","EUPriceList");
        orderPage.enterShipFromAddress(shipFromLocation);
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("Les Dicqs", "Vale", "GY6 8JP", "UK");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");


        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<PhysicalOrigin taxAreaId=\"808260003\">\n" +
                "                    <StreetAddress1>Route Des Bas Courtils</StreetAddress1>\n" +
                "                    <City>St Saviours</City>\n" +
                "                    <PostalCode>GY7 9YF</PostalCode>\n" +
                "                    <Country>UK</Country>\n" +
                "                </PhysicalOrigin>"));
        assertTrue(xml.contains("<Destination taxAreaId=\"808260003\">\n" +
                "                    <StreetAddress1>Les Dicqs</StreetAddress1>\n" +
                "                    <City>Vale</City>\n" +
                "                    <PostalCode>GY6 8JP</PostalCode>\n" +
                "                    <Country>UK</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"Euro\" isoCurrencyCodeAlpha=\"EUR\" isoCurrencyCodeNum=\"978\">1.0</CurrencyConversion>\n" +
                "                </Destination>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"88116\">GUERNSEY</Jurisdiction>\n" +
                "                <CalculatedTax>0.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.0</EffectiveRate>"));
        assertTrue(xml.contains("<TotalTax>0.0</TotalTax>"));
    }


    /**
     * CDCRM-199
     * Create Order for company not registered in Belgium
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderBelgiumUnregisteredCompanyTest() {
        String customerAccount = "BE Account";
        String shipFromLocation = "Belgium";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "0.00";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test","Euro","EUPriceList");
        orderPage.enterShipFromAddress(shipFromLocation);
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("Buchtenstraat 14", "Gent", "9051", "BE");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");


        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<PhysicalOrigin taxAreaId=\"800560000\">\n" +
                "                    <StreetAddress1>Rue Léopold 2</StreetAddress1>\n" +
                "                    <City>La Louvière</City>\n" +
                "                    <PostalCode>7100</PostalCode>\n" +
                "                    <Country>Belgium</Country>\n" +
                "                </PhysicalOrigin>"));
        assertTrue(xml.contains("<Destination taxAreaId=\"800560000\">\n" +
                "                    <StreetAddress1>Buchtenstraat 14</StreetAddress1>\n" +
                "                    <City>Gent</City>\n" +
                "                    <PostalCode>9051</PostalCode>\n" +
                "                    <Country>BE</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"Euro\" isoCurrencyCodeAlpha=\"EUR\" isoCurrencyCodeNum=\"978\">1.0</CurrencyConversion>\n" +
                "                </Destination>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78279\">BELGIUM</Jurisdiction>\n" +
                "                <CalculatedTax>0.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.0</EffectiveRate>\n" +
                "                <Taxable>0.0</Taxable>\n" +
                "                <Imposition impositionId=\"1\">VAT</Imposition>\n" +
                "                <ImpositionType impositionTypeId=\"19\">VAT</ImpositionType>"));
        assertTrue(xml.contains("<TotalTax>0.0</TotalTax>"));
    }

    /**
     * CDCRM-200
     * Create Order for Greek territory
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderGreeceTerritoryTest() {
        String customerAccount = "GR Account";
        String shipFromLocation = "Greece";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "24.00";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test","Euro","EUPriceList");
        orderPage.enterShipFromAddress(shipFromLocation);
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("Platis Gialos", "Mykonos", "846 00", "Greece");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");


        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<TotalTax>24.0</TotalTax>"));
        assertTrue(xml.contains("<PhysicalOrigin taxAreaId=\"803000000\">\n" +
                "                    <StreetAddress1>Paralia Schoinonta</StreetAddress1>\n" +
                "                    <City>Analipsi</City>\n" +
                "                    <PostalCode>859 00</PostalCode>\n" +
                "                    <Country>Greece</Country>\n" +
                "                </PhysicalOrigin>"));
        assertTrue(xml.contains("<Destination taxAreaId=\"803000000\">\n" +
                "                    <StreetAddress1>Platis Gialos</StreetAddress1>\n" +
                "                    <City>Mykonos</City>\n" +
                "                    <PostalCode>846 00</PostalCode>\n" +
                "                    <Country>Greece</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"Euro\" isoCurrencyCodeAlpha=\"EUR\" isoCurrencyCodeNum=\"978\">1.0</CurrencyConversion>\n" +
                "                </Destination>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78284\">GREECE</Jurisdiction>\n" +
                "                <CalculatedTax>24.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.24</EffectiveRate>\n" +
                "                <Taxable>100.0</Taxable>\n" +
                "                <Imposition impositionId=\"1\">VAT</Imposition>\n" +
                "                <ImpositionType impositionTypeId=\"19\">VAT</ImpositionType>"));
        assertTrue(xml.contains("<SellerRegistrationId>GR123456789</SellerRegistrationId>"));
    }
    /**
     * CDCRM-201
     * Create Order with Austria origin and Austria destination
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderAustriaTest() {
        String customerAccount = "AT Account";
        String shipFromLocation = "Austria";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "20.00";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test","Euro","EUPriceList");
        orderPage.enterShipFromAddress(shipFromLocation);
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("Walserstraße 81", "Mittelberg", "6991", "Austria");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");


        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<TotalTax>20.0</TotalTax>"));
        assertTrue(xml.contains("<PhysicalOrigin taxAreaId=\"800400004\">\n" +
                "                    <StreetAddress1>Moosstraße 7</StreetAddress1>\n" +
                "                    <City>Mittelberg</City>\n" +
                "                    <PostalCode>6993</PostalCode>\n" +
                "                    <Country>Austria</Country>\n" +
                "                </PhysicalOrigin>"));
        assertTrue(xml.contains("<Destination taxAreaId=\"800400000\">\n" +
                "                    <StreetAddress1>Walserstraße 81</StreetAddress1>\n" +
                "                    <City>Mittelberg</City>\n" +
                "                    <PostalCode>6991</PostalCode>\n" +
                "                    <Country>Austria</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"Euro\" isoCurrencyCodeAlpha=\"EUR\" isoCurrencyCodeNum=\"978\">1.0</CurrencyConversion>\n" +
                "                </Destination>"));
        assertTrue(xml.contains("<SellerRegistrationId>AT123456789</SellerRegistrationId>"));
    }

    /**
     * CDCRM-208
     * Create Order with China origin and China destination
     * @author Vivek Olumbe
     */
    public void createOrderChinaTest() {
        String customerAccount = "CN Account";
        String shipFromLocation = "China";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "16.00";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test","Euro","EUPriceList");
        orderPage.enterShipFromAddress(shipFromLocation);
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("Zhicheng Rd", "Xiangtan Shi", "411201", "China");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        //TODO: Test ticket says 16%, but receiving 13.5%
        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");

        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<TotalTax>16.0</TotalTax>"));
        assertTrue(xml.contains("<PhysicalOrigin taxAreaId=\"800400004\">\n" +
                "                    <StreetAddress1>E 2nd Ring Rd</StreetAddress1>\n" +
                "                    <City>Changsha Shi</City>\n" +
                "                    <PostalCode>410016</PostalCode>\n" +
                "                    <Country>China</Country>\n" +
                "                </PhysicalOrigin>"));
        assertTrue(xml.contains("<Destination taxAreaId=\"801560001\">\n" +
                "                    <StreetAddress1>Zhicheng Rd</StreetAddress1>\n" +
                "                    <City>Xiangtan Shi</City>\n" +
                "                    <PostalCode>411201</PostalCode>\n" +
                "                    <Country>China</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"Euro\" isoCurrencyCodeAlpha=\"EUR\" isoCurrencyCodeNum=\"978\">1.0</CurrencyConversion>\n" +
                "                </Destination>"));
        //TODO: assertTrue(xml.contains("<SellerRegistrationId></SellerRegistrationId>"));
    }

    /**
     * CDCRM-210
     * Create Order with Belize origin and Belize destination
     * @author Vivek Olumbe
     */
    public void createOrderBelizeTest() {
        String customerAccount = "BZ Account";
        String shipFromLocation = "Belize";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "12.50";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.enterShipFromAddress(shipFromLocation);
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("St. Thomas Street", "Belize City", "", "Belize");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");

        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<TotalTax>12.5</TotalTax>"));
        assertTrue(xml.contains("<PhysicalOrigin taxAreaId=\"800840000\">\n" +
                "                    <StreetAddress1>Buena Vista Street</StreetAddress1>\n" +
                "                    <City>San Ignacio</City>\n" +
                "                    <Country>Belize</Country>\n" +
                "                </PhysicalOrigin>"));
        assertTrue(xml.contains("<Destination taxAreaId=\"800840000\">\n" +
                "                    <StreetAddress1>St. Thomas Street</StreetAddress1>\n" +
                "                    <City>Belize City</City>\n" +
                "                    <Country>Belize</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"US Dollar\" isoCurrencyCodeAlpha=\"USD\" isoCurrencyCodeNum=\"840\">1.0</CurrencyConversion>\n" +
                "                </Destination>"));
        //TODO: assertTrue(xml.contains("<SellerRegistrationId></SellerRegistrationId>"));
    }

    /**
     * CDCRM-211
     * Create Order with Costa Rica origin and Colombia destination
     * @author Vivek Olumbe
     */
    public void createOrderCRtoCOTest() {
        String customerAccount = "Colombia Account";
        String shipFromLocation = "Costa Rica";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "0.00";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.enterShipFromAddress(shipFromLocation);
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("Av. 3 Norte # 7-19", "Valle del Cauca", "", "Colombia");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");

        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<TotalTax>0.0</TotalTax>"));
        assertTrue(xml.contains("<PhysicalOrigin taxAreaId=\"801880000\">\n" +
                "                    <StreetAddress1>Av. 14</StreetAddress1>\n" +
                "                    <City>San José</City>\n" +
                "                    <Country>Costa Rica</Country>\n" +
                "                </PhysicalOrigin>"));
        assertTrue(xml.contains("<Destination taxAreaId=\"801700000\">\n" +
                "                    <StreetAddress1>Av. 3 Norte # 7-19</StreetAddress1>\n" +
                "                    <City>Valle del Cauca</City>\n" +
                "                    <Country>Colombia</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"US Dollar\" isoCurrencyCodeAlpha=\"USD\" isoCurrencyCodeNum=\"840\">1.0</CurrencyConversion>\n" +
                "                </Destination>"));
        //TODO: assertTrue(xml.contains("<SellerRegistrationId></SellerRegistrationId>"));
    }

    /**
     * CDCRM-212
     * Create Order with Jamaica origin and Jamaica destination
     * @author Vivek Olumbe
     */
    public void createOrderJamaicaTest() {
        String customerAccount = "JM Account";
        String shipFromLocation = "Jamaica";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "16.50";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.enterShipFromAddress(shipFromLocation);
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("7 Mile Beach", "Negril", "", "Jamaica");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        //TODO: Test ticket says 16.5%, but receiving 15%
        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");

        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<TotalTax>16.5</TotalTax>"));
        assertTrue(xml.contains("<PhysicalOrigin taxAreaId=\"803880000\">\n" +
                "                    <StreetAddress1>White River Bay</StreetAddress1>\n" +
                "                    <City>Ocho Rios</City>\n" +
                "                    <Country>Jamaica</Country>\n" +
                "                </PhysicalOrigin>"));
        assertTrue(xml.contains("<Destination taxAreaId=\"803880000\">\n" +
                "                    <StreetAddress1>7 Mile Beach</StreetAddress1>\n" +
                "                    <City>Negril</City>\n" +
                "                    <Country>Jamaica</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"US Dollar\" isoCurrencyCodeAlpha=\"USD\" isoCurrencyCodeNum=\"840\">1.0</CurrencyConversion>\n" +
                "                </Destination>"));
        //TODO: assertTrue(xml.contains("<SellerRegistrationId></SellerRegistrationId>"));
    }
}

