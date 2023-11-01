package com.vertex.quality.connectors.dynamics365.sales.tests;

import com.vertex.quality.connectors.dynamics365.sales.pages.*;
import com.vertex.quality.connectors.dynamics365.sales.tests.base.SalesBaseTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * this class represents all the test cases for the Sales Orders
 *
 * @author Shruti
 */
public class SalesOrdersTests extends SalesBaseTest {


    /**
     * CDCRM-44
     * Create Order with Required Fields Only
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderRequiredFieldsOnlyTest() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String expectedTotalTax = "6.00";
        String quantity = "1";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.fillShipToAddressInfo("201 Granite Run Dr", "Lancaster", "17601", "USA");
        orderPage.enterCustomer(customerAccount);

        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTotalTax, "Total Tax Validation Failed");

        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(orderID);

        assertTrue(xml.contains("<TotalTax>6.0</TotalTax>"));
    }

    /**
     * CDCRM-48
     * Create Order with Line Discount
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderWithLineDiscountTest() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String expectedTotalTax = "6.00";
        String expectedUpdatedTotalTax = "5.40";
        String quantity = "1";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.fillShipToAddressInfo("201 Granite Run Dr", "Lancaster", "17601", "USA");
        orderPage.enterCustomer(customerAccount);

        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTotalTax, "Total Tax Validation Failed");

        orderPage.updateDiscount("10", 1);
        orderPage.clickSave();
        orderPage.clickCalculateTax();

        String updatedTotalTax = orderPage.getTotalTax();
        assertEquals(updatedTotalTax, expectedUpdatedTotalTax, "Total Tax Validation Failed");

        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(orderID);

        assertTrue(xml.contains("<TotalTax>5.4</TotalTax>"));
    }

    /**
     * CDCRM-49
     * Create Order with Discount Percentage
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderWithDiscountPercentTest() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String expectedTotalTax = "5.40";
        String expectedTotalAmount = "95.40";
        String quantity = "1";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.fillShipToAddressInfo("201 Granite Run Dr", "Lancaster", "17601", "USA");
        orderPage.enterCustomer(customerAccount);
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.enterDiscountPercent("10");
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTotalTax, "Total Tax Validation Failed");
        String totalAmount = orderPage.getTotalAmount();
        assertEquals(totalAmount, expectedTotalAmount, "Total Tax Validation Failed");

        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(orderID);

        assertTrue(xml.contains("<TotalTax>5.4</TotalTax>"));
        assertTrue(xml.contains("<Total>95.4</Total>"));
    }

    /**
     * CDCRM-481
     * Create Order shipping to Colorado and verify retail delivery fee
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderWithCORetailDeliveryFeeTest() {
        String customerAccount = "CO Account";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTotalTax = "9.30";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.fillShipToAddressInfo("1575 Space Center Dr", "Colorado Springs", "80915", "USA");
        orderPage.enterCustomer(customerAccount);

        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.enterFreightAmount("10");
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTotalTax, "Total Tax Validation Failed");

        orderPage.clickCalculateTax();
        List<String> taxAmounts = orderPage.getTaxSummariesDetails("Tax Amount");
        assertTrue(taxAmounts.contains("0.28"));

        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(orderID);

        assertTrue(xml.contains("<TotalTax>9.30</TotalTax>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"4220\">COLORADO</Jurisdiction>\n" +
                "                <CalculatedTax>0.27</CalculatedTax>"));
        assertTrue(xml.contains("<Imposition impositionId=\"13\">Retail Delivery Fee</Imposition>"));
    }
    /**
     * CDCRM-133
     * Create Order with Invoice and validate XML
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderWithInvoiceTest() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTotalTax = "6.00";
        List<String> expectedTaxRates = Arrays.asList("0.06000");

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.fillShipToAddressInfo("1000 Airport Blvd", "Pittsburgh", "15231", "USA");
        orderPage.enterCustomer(customerAccount);
        orderPage.clickSave();

        orderPage.addProduct(productName, quantity, null);

        orderPage.clickCalculateTax();
        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTotalTax, "Total Tax Validation Failed");
        List<String> orderTaxRates = orderPage.getTaxSummariesDetails("Tax Rate");
        assertEquals(orderTaxRates, expectedTaxRates, "Tax Rate Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTotalTax, "Total Tax Validation Failed");
        List<String> invoiceTaxRates = invoicePage.getTaxSummariesDetails("Tax Rate");
        assertEquals(invoiceTaxRates, expectedTaxRates, "Tax Rate Validation Failed");

        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);
        assertTrue(xml.contains("<TotalTax>6.0</TotalTax>"));
    }

    /**
     * CDCRM-117
     * Create Order with Invoice, validate XML, update lines and validate XML
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderWithInvoiceAndUpdateLinesTest() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTotalTax = "72.00";
        String expectedUpdatedTotalTax = "60.00";
        List<String> expectedTaxRates = Arrays.asList("0.06000", "0.06000", "0.06000");

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.fillShipToAddressInfo("1000 Airport Blvd", "Pittsburgh", "15231", "USA");
        orderPage.enterCustomer(customerAccount);
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.addProduct(productName, "10", null);
        orderPage.addProduct(productName, quantity, null);

        orderPage.clickCalculateTax();
        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTotalTax, "Total Tax Validation Failed");
        List<String> orderTaxRates = orderPage.getTaxSummariesDetails("Tax Rate");
        assertEquals(orderTaxRates, expectedTaxRates, "Tax Rate Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTotalTax, "Total Tax Validation Failed");
        List<String> invoiceTaxRates = invoicePage.getTaxSummariesDetails("Tax Rate");
        assertEquals(invoiceTaxRates, expectedTaxRates, "Tax Rate Validation Failed");

        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);
        assertTrue(xml.contains("<TotalTax>72.0</TotalTax>"));

        // Update order with different lines
        salesHomePage = vertexAdminPage.navigateToSalesHomePage();
        orderPage = salesHomePage.navigateToSalesOrdersPage();
        orderPage.openDocument(orderID);

        orderPage.updateQuantity("5", 2);
        orderPage.deleteProduct(1);
        orderPage.addProduct(productName, "4", null);

        orderPage.clickCalculateTax();
        String updatedTotalTax = orderPage.getTotalTax();
        assertEquals(updatedTotalTax, expectedUpdatedTotalTax, "Total Tax Validation Failed");
        List<String> updatedOrderTaxRates = orderPage.getTaxSummariesDetails("Tax Rate");
        assertEquals(updatedOrderTaxRates, expectedTaxRates, "Tax Rate Validation Failed");

        // Invoice updated order and verify tax and tax rates
        invoicePage = orderPage.clickCreateInvoice();
        String updatedInvoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String updatedInvoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(updatedInvoiceTotalTax, expectedUpdatedTotalTax, "Total Tax Validation Failed");
        List<String> updatedInvoiceTaxRates = invoicePage.getTaxSummariesDetails("Tax Rate");
        assertEquals(updatedInvoiceTaxRates, expectedTaxRates, "Tax Rate Validation Failed");

        vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String updatedXml = vertexAdminPage.getFirstXMLLog(updatedInvoiceID);
        assertTrue(updatedXml.contains("<TotalTax>60.0</TotalTax>"));
    }

    /**
     * CDCRM-118
     * Create Order with Invoice and Change Shipping Address
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderWithInvoiceUpdateShippingTest() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String expectedPATax = "6.00";
        String expectedWATax = "10.10";
        String quantity = "1";
        List<String> expectedPATaxRates = Arrays.asList("0.06000");
        List<String> expectedWATaxRates = Arrays.asList("0.06500", "0.00000", "0.02200", "0.01400");

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("1000 Airport Blvd ", "Pittsburgh", "15231", "USA");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedPATax, "Total Tax Validation Failed");
        List<String> orderTaxRates = orderPage.getTaxSummariesDetails("Tax Rate");
        assertEquals(orderTaxRates, expectedPATaxRates, "Tax Rate Validation Failed");

        // update ship to address
        orderPage.fillShipToAddressInfo("22833 NE 8th St ", "Sammamish", "98074", "USA");
        orderPage.clickSave();
        orderPage.clickCalculateTax();

        totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedWATax, "Total Tax Validation Failed");
        orderTaxRates = orderPage.getTaxSummariesDetails("Tax Rate");
        assertEquals(orderTaxRates, expectedWATaxRates, "Tax Rate Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedWATax, "Total Tax Validation Failed");
        List<String> invoiceTaxRates = invoicePage.getTaxSummariesDetails("Tax Rate");
        assertEquals(invoiceTaxRates, expectedWATaxRates, "Tax Rate Validation Failed");


        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<TotalTax>10.1</TotalTax>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"40256\">WASHINGTON</Jurisdiction>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"COUNTY\" jurisdictionId=\"40453\">KING</Jurisdiction>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"CITY\" jurisdictionId=\"60576\">SAMMAMISH</Jurisdiction>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"102655\">REGIONAL TRANSIT AUTHORITY</Jurisdiction>"));
    }

    /**
     * CDCRM-134
     * Create Order with different billing and shipping
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderWithDifferentBillAndShipAddressesTest() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "6.00";
        List<String> expectedTaxRates = Arrays.asList("0.06000");

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.enterCustomer(customerAccount);
        orderPage.fillBillToAddressInfo("100 N Orange St", "Wilmington", "19801", "USA");
        orderPage.fillShipToAddressInfo("1000 Airport Blvd ", "Pittsburgh", "15231", "USA");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");
        List<String> orderTaxRates = orderPage.getTaxSummariesDetails("Tax Rate");
        assertEquals(orderTaxRates, expectedTaxRates, "Tax Rate Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");
        List<String> invoiceTaxRates = invoicePage.getTaxSummariesDetails("Tax Rate");
        assertEquals(invoiceTaxRates, expectedTaxRates, "Tax Rate Validation Failed");


        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<TotalTax>6.0</TotalTax>"));
        assertTrue(xml.contains("<Destination taxAreaId=\"390030000\">\n" +
                "                    <StreetAddress1>1000 Airport Blvd</StreetAddress1>\n" +
                "                    <City>Pittsburgh</City>\n" +
                "                    <MainDivision>PA</MainDivision>\n" +
                "                    <PostalCode>15231-1001</PostalCode>\n" +
                "                    <Country>USA</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"US Dollar\" isoCurrencyCodeAlpha=\"USD\" isoCurrencyCodeNum=\"840\">1.0</CurrencyConversion>\n" +
                "                </Destination>"));
        assertTrue(xml.contains("<AdministrativeDestination taxAreaId=\"80030100\">\n" +
                "                    <StreetAddress1>100 N Orange St</StreetAddress1>\n" +
                "                    <City>Wilmington</City>\n" +
                "                    <PostalCode>19801</PostalCode>\n" +
                "                    <Country>USA</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"US Dollar\" isoCurrencyCodeAlpha=\"USD\" isoCurrencyCodeNum=\"840\">1.0</CurrencyConversion>\n" +
                "                </AdministrativeDestination>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"31152\">PENNSYLVANIA</Jurisdiction>"));
    }

    /**
     * CDCRM-138
     * Create Order with no state tax
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderWithZeroStateTaxTest() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "0.00";
        List<String> expectedTaxRates = Arrays.asList("0.00000");

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("100 N Orange St", "Wilmington", "19801", "USA");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");
        List<String> orderTaxRates = orderPage.getTaxSummariesDetails("Tax Rate");
        assertEquals(orderTaxRates, expectedTaxRates, "Tax Rate Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");
        List<String> invoiceTaxRates = invoicePage.getTaxSummariesDetails("Tax Rate");
        assertEquals(invoiceTaxRates, expectedTaxRates, "Tax Rate Validation Failed");


        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<TotalTax>0.0</TotalTax>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"5182\">DELAWARE</Jurisdiction>"));
        assertTrue(xml.contains("<CalculatedTax>0.0</CalculatedTax>"));
        assertTrue(xml.contains("<EffectiveRate>0.0</EffectiveRate>"));
        assertTrue(xml.contains("<NonTaxable>100.0</NonTaxable>"));
        assertTrue(xml.contains("<Taxable>0.0</Taxable>"));
    }

    /**
     * CDCRM-139
     * Create Order with only city tax
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderWithOnlyCityTaxTest() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "5.00";
        List<String> expectedTaxRates = Arrays.asList("0.00000", "0.00000", "0.05000");

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("6525 Glacier Hwy", "Juneau", "99801", "USA");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");
        List<String> orderTaxRates = orderPage.getTaxSummariesDetails("Tax Rate");
        assertEquals(orderTaxRates, expectedTaxRates, "Tax Rate Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");
        List<String> invoiceTaxRates = invoicePage.getTaxSummariesDetails("Tax Rate");
        assertEquals(invoiceTaxRates, expectedTaxRates, "Tax Rate Validation Failed");


        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<TotalTax>5.0</TotalTax>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"COUNTY\" jurisdictionId=\"976\">JUNEAU BOROUGH</Jurisdiction>\n" +
                "                <CalculatedTax>0.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.0</EffectiveRate>\n" +
                "                <NonTaxable>100.0</NonTaxable>\n" +
                "                <Taxable>0.0</Taxable>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"912\">ALASKA</Jurisdiction>\n" +
                "                <CalculatedTax>0.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.0</EffectiveRate>\n" +
                "                <NonTaxable>100.0</NonTaxable>\n" +
                "                <Taxable>0.0</Taxable>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"CITY\" jurisdictionId=\"977\">JUNEAU</Jurisdiction>\n" +
                "                <CalculatedTax>5.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.05</EffectiveRate>\n" +
                "                <Taxable>100.0</Taxable>"));
    }

    /**
     * CDCRM-137
     * Create Order with taxpayer that is not registered
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderWithTaxpayerNotRegisteredTest() {
        String customerAccount = "Test Account";
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
        orderPage.fillShipToAddressInfo("1440 Monroe Street", "Madison", "53711", "USA");
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
    }

    /**
     * CDCRM-103
     * Create Order with customer code exemption
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderWithCustomerCodeExemptionTest() {
        String customerAccount = "VTXCCode";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "0.00";
        List<String> expectedTaxRates = Arrays.asList("0.00000", "0.00000");

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("1000 Airport Blvd", "Pittsburgh", "15231", "USA");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.addProduct(productName, "5", null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");
        List<String> orderTaxRates = orderPage.getTaxSummariesDetails("Tax Rate");
        assertEquals(orderTaxRates, expectedTaxRates, "Tax Rate Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");
        List<String> invoiceTaxRates = invoicePage.getTaxSummariesDetails("Tax Rate");
        assertEquals(invoiceTaxRates, expectedTaxRates, "Tax Rate Validation Failed");


        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<TotalTax>0.0</TotalTax>"));
        assertTrue(xml.contains("<CalculatedTax>0.0</CalculatedTax>"));
        assertTrue(xml.contains("<EffectiveRate>0.0</EffectiveRate>"));
        assertTrue(xml.contains("<Exempt>500.0</Exempt>"));
        assertTrue(xml.contains("<Exempt>100.0</Exempt>"));
        assertTrue(xml.contains("<Taxable>0.0</Taxable>"));
    }

    /**
     * CDCRM-104
     * Create Order with product class exemption
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderWithProductClassExemptionTest() {
        String customerAccount = "Test Account";
        String productName = "Class Exempt Product";
        String quantity = "5";
        String expectedTax = "6.00";
        List<String> expectedTaxRates = Arrays.asList("0.00000", "0.06000");

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("1000 Airport Blvd", "Pittsburgh", "15231", "USA");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct("Furniture", "1", null);
        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");
        List<String> orderTaxRates = orderPage.getTaxSummariesDetails("Tax Rate");
        assertEquals(orderTaxRates, expectedTaxRates, "Tax Rate Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");
        List<String> invoiceTaxRates = invoicePage.getTaxSummariesDetails("Tax Rate");
        assertEquals(invoiceTaxRates, expectedTaxRates, "Tax Rate Validation Failed");


        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<TotalTax>6.0</TotalTax>"));
        assertTrue(xml.contains("<CalculatedTax>0.0</CalculatedTax>"));
        assertTrue(xml.contains("<EffectiveRate>0.0</EffectiveRate>"));
        assertTrue(xml.contains("<Exempt>500.0</Exempt>"));
        assertTrue(xml.contains("<Taxable>0.0</Taxable>"));
    }

    /**
     * CDCRM-105
     * Create Order with product code exemption
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderWithProductCodeExemptionTest() {
        String customerAccount = "Test Account";
        String productName = "Code Exempt Product";
        String quantity = "5";
        String expectedTax = "6.00";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("1000 Airport Blvd", "Pittsburgh", "15231", "USA");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct("Furniture", "1", null);
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

        assertTrue(xml.contains("<TotalTax>6.0</TotalTax>"));
        assertTrue(xml.contains("<CalculatedTax>0.0</CalculatedTax>"));
        assertTrue(xml.contains("<EffectiveRate>0.0</EffectiveRate>"));
        assertTrue(xml.contains("<Exempt>500.0</Exempt>"));
        assertTrue(xml.contains("<Taxable>0.0</Taxable>"));
    }

    /**
     * CDCRM-114
     * Create Order with customer exemption certificate
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderWithCustomerExemptionCertificateTest() {
        String customerAccount = "VTXCExemptCert";
        String productName = "Furniture";
        String quantity = "5";
        String expectedTax = "0.00";
        List<String> expectedTaxRates = Arrays.asList("0.00000", "0.00000", "0.00000", "0.00000", "0.00000", "0.00000");

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("4505 Ridgeside Dr", " Dallas", "75244", "USA");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct("Furniture", "1", null);
        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");
        List<String> orderTaxRates = orderPage.getTaxSummariesDetails("Tax Rate");
        assertEquals(orderTaxRates, expectedTaxRates, "Tax Rate Validation Failed");

        // Invoice order and verify tax and tax rates
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");
        List<String> invoiceTaxRates = invoicePage.getTaxSummariesDetails("Tax Rate");
        assertEquals(invoiceTaxRates, expectedTaxRates, "Tax Rate Validation Failed");


        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<TotalTax>0.0</TotalTax>"));
    }

    /**
     * CDCRM-141
     * Create Order with clothing for NJ customer
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderClothingTaxExemptionTest() {
        String customerAccount = "NJ Account";
        String productName = "Clothing";
        String quantity = "1";
        String expectedTax = "0.00";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("883 Route 1", " Edison", "08817", "USA");
        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.enterFreightAmount("5.00");
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

        assertTrue(xml.contains("<NonTaxable>50.0</NonTaxable>"));
        assertTrue(xml.contains("<TotalTax>0.0</TotalTax>"));
    }

    /**
     * CDCRM-142
     * Create Order with US origin and CAN destination
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderUStoCANTest() {
        String customerAccount = "CAN Account";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "7.00";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("1820 Store St", "Victoria", "V8T 4R4", "CAN");
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
        assertTrue(xml.contains("<Destination taxAreaId=\"700030450\">\n" +
                "                    <StreetAddress1>1820 Store St</StreetAddress1>\n" +
                "                    <City>Victoria</City>\n" +
                "                    <PostalCode>V8T 4R4</PostalCode>\n" +
                "                    <Country>CAN</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"US Dollar\" isoCurrencyCodeAlpha=\"USD\" isoCurrencyCodeNum=\"840\">1.0</CurrencyConversion>\n" +
                "                </Destination>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"PROVINCE\" jurisdictionId=\"43604\">BRITISH COLUMBIA</Jurisdiction>"));
        assertTrue(xml.contains("<TotalTax>7.0</TotalTax>"));
    }

    /**
     * CDCRM-143
     * Create Order with CAN origin and US destination
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderCANtoUSTest() {
        String customerAccount = "Test Account";
        String shipFromLocation = "CAN QC";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "6.00";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.enterShipFromAddress(shipFromLocation);
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("1000 Airport Blvd", "Pittsburgh", "15231", "USA");
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

        assertTrue(xml.contains("<PhysicalOrigin taxAreaId=\"700190930\">\n" +
                "                    <StreetAddress1>2450 Boulevard Laurier</StreetAddress1>\n" +
                "                    <StreetAddress2>G12a</StreetAddress2>\n" +
                "                    <City>Quebec City</City>\n" +
                "                    <PostalCode>G1V 2L1</PostalCode>\n" +
                "                    <Country>CAN</Country>\n" +
                "                </PhysicalOrigin>"));
        assertTrue(xml.contains("<Destination taxAreaId=\"390030000\">\n" +
                "                    <StreetAddress1>1000 Airport Blvd</StreetAddress1>\n" +
                "                    <City>Pittsburgh</City>\n" +
                "                    <MainDivision>PA</MainDivision>\n" +
                "                    <PostalCode>15231-1001</PostalCode>\n" +
                "                    <Country>USA</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"US Dollar\" isoCurrencyCodeAlpha=\"USD\" isoCurrencyCodeNum=\"840\">1.0</CurrencyConversion>\n" +
                "                </Destination>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"8\">CANADA</Jurisdiction>\n" +
                "                <CalculatedTax>0.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.0</EffectiveRate>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"PROVINCE\" jurisdictionId=\"44906\">QUEBEC</Jurisdiction>\n" +
                "                <CalculatedTax>0.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.0</EffectiveRate>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"31152\">PENNSYLVANIA</Jurisdiction>\n" +
                "                <CalculatedTax>6.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.06</EffectiveRate>"));
        assertTrue(xml.contains("<TotalTax>6.0</TotalTax>"));
    }

    /**
     * CDCRM-144
     * Create Order with CAN NB origin and CAN NB destination
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderCANtoCANSameProvinceTest() {
        String customerAccount = "CAN Account";
        String shipFromLocation = "CAN NB";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "15.00";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.enterShipFromAddress(shipFromLocation);
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("21 Pettingill Rd", "Quispamsis", "E2E 6B1", "CAN");
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

        assertTrue(xml.contains("<PhysicalOrigin taxAreaId=\"700070737\">\n" +
                "                    <StreetAddress1>11 Bancroft Point Rd</StreetAddress1>\n" +
                "                    <City>Grand Manan</City>\n" +
                "                    <PostalCode>E5G 4C1</PostalCode>\n" +
                "                    <Country>CAN</Country>\n" +
                "                </PhysicalOrigin>"));
        assertTrue(xml.contains("<Destination taxAreaId=\"700072882\">\n" +
                "                    <StreetAddress1>21 Pettingill Rd</StreetAddress1>\n" +
                "                    <City>Quispamsis</City>\n" +
                "                    <PostalCode>E2E 6B1</PostalCode>\n" +
                "                    <Country>CAN</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"US Dollar\" isoCurrencyCodeAlpha=\"USD\" isoCurrencyCodeNum=\"840\">1.0</CurrencyConversion>\n" +
                "                </Destination>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"8\">CANADA</Jurisdiction>\n" +
                "                <CalculatedTax>15.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.15</EffectiveRate>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"PROVINCE\" jurisdictionId=\"43911\">NEW BRUNSWICK</Jurisdiction>\n" +
                "                <CalculatedTax>0.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.0</EffectiveRate>"));
        assertTrue(xml.contains("<TotalTax>15.0</TotalTax>"));
    }

    /**
     * CDCRM-145
     * Create Order with CAN QC origin and CAN BC destination
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createOrderCANtoCANDifferentProvinceTest() {
        String customerAccount = "CAN Account";
        String shipFromLocation = "CAN QC";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTax = "12.00";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.enterShipFromAddress(shipFromLocation);
        orderPage.enterCustomer(customerAccount);
        orderPage.fillShipToAddressInfo("1820 Store St", "Victoria", "V8T 4R4", "CAN");
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

        assertTrue(xml.contains("<PhysicalOrigin taxAreaId=\"700190930\">\n" +
                "                    <StreetAddress1>2450 Boulevard Laurier</StreetAddress1>\n" +
                "                    <StreetAddress2>G12a</StreetAddress2>\n" +
                "                    <City>Quebec City</City>\n" +
                "                    <PostalCode>G1V 2L1</PostalCode>\n" +
                "                    <Country>CAN</Country>\n" +
                "                </PhysicalOrigin>"));
        assertTrue(xml.contains("<Destination taxAreaId=\"700030450\">\n" +
                "                    <StreetAddress1>1820 Store St</StreetAddress1>\n" +
                "                    <City>Victoria</City>\n" +
                "                    <PostalCode>V8T 4R4</PostalCode>\n" +
                "                    <Country>CAN</Country>\n" +
                "                    <CurrencyConversion isoCurrencyName=\"US Dollar\" isoCurrencyCodeAlpha=\"USD\" isoCurrencyCodeNum=\"840\">1.0</CurrencyConversion>\n" +
                "                </Destination>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"8\">CANADA</Jurisdiction>\n" +
                "                <CalculatedTax>5.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.05</EffectiveRate>\n" +
                "                <Taxable>100.0</Taxable>\n" +
                "                <Imposition impositionId=\"3\">GST/HST</Imposition>\n" +
                "                <ImpositionType impositionTypeId=\"43\">Goods and Services Tax</ImpositionType>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"PROVINCE\" jurisdictionId=\"43604\">BRITISH COLUMBIA</Jurisdiction>\n" +
                "                <CalculatedTax>7.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.07</EffectiveRate>\n" +
                "                <Taxable>100.0</Taxable>\n" +
                "                <Imposition impositionId=\"1\">Provincial Sales Tax (PST)</Imposition>\n" +
                "                <ImpositionType impositionTypeId=\"1\">General Sales and Use Tax</ImpositionType>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"PROVINCE\" jurisdictionId=\"44906\">QUEBEC</Jurisdiction>\n" +
                "                <CalculatedTax>0.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.0</EffectiveRate>\n" +
                "                <Taxable>0.0</Taxable>"));
        assertTrue(xml.contains("<TotalTax>12.0</TotalTax>"));
    }

    /**
     * CDCRM-140
     * Create Order with customer with modified origin state
     * @author Vivek Olumbe
     */
    public void createOrderModifiedOriginStateTest() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String expectedTax = "9.50";
        String quantity = "1";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.fillShipToAddressInfo("100 Universal City Plaza", "Universal City", "91608", "USA");
        orderPage.enterCustomer(customerAccount);

        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        orderPage.addProduct(productName, quantity, null);
        orderPage.clickCalculateTax();

        String totalTax = orderPage.getTotalTax();
        assertEquals(totalTax, expectedTax, "Total Tax Validation Failed");

        // Invoice order and verify tax
        SalesInvoicesPage invoicePage = orderPage.clickCreateInvoice();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.clickCalculateTax();
        String invoiceTotalTax = invoicePage.getTotalTax();
        assertEquals(invoiceTotalTax, expectedTax, "Total Tax Validation Failed");

        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(orderID);

        assertTrue(xml.contains("<TotalTax>9.5</TotalTax>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"2398\">CALIFORNIA</Jurisdiction>\n" +
                "                <CalculatedTax>6.0</CalculatedTax>\n" +
                "                <EffectiveRate>0.06</EffectiveRate>\n" +
                "                <Taxable>100.0</Taxable>\n"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"COUNTY\" jurisdictionId=\"2872\">LOS ANGELES</Jurisdiction>\n" +
                "                <CalculatedTax>1.25</CalculatedTax>\n" +
                "                <EffectiveRate>0.0125</EffectiveRate>\n" +
                "                <Taxable>100.0</Taxable>\n"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"2873\">TRANSPORTATION COMMISSION (LATC)</Jurisdiction>\n" +
                "                <CalculatedTax>0.5</CalculatedTax>\n" +
                "                <EffectiveRate>0.005</EffectiveRate>\n" +
                "                <Taxable>100.0</Taxable>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"66710\">TRANSIT DISTRICT (LACT)</Jurisdiction>\n" +
                "                <CalculatedTax>0.5</CalculatedTax>\n" +
                "                <EffectiveRate>0.005</EffectiveRate>\n" +
                "                <Taxable>100.0</Taxable>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"88645\">METROPOLITAN TRANSIT DISTRICT (LAMT)</Jurisdiction>\n" +
                "                <CalculatedTax>0.5</CalculatedTax>\n" +
                "                <EffectiveRate>0.005</EffectiveRate>\n" +
                "                <Taxable>100.0</Taxable>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"102864\">TRANSACTIONS AND USE TAX (LAMA)</Jurisdiction>\n" +
                "                <CalculatedTax>0.5</CalculatedTax>\n" +
                "                <EffectiveRate>0.005</EffectiveRate>\n" +
                "                <Taxable>100.0</Taxable>"));
        assertTrue(xml.contains("<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"103278\">MEASURE H TRANSACTIONS AND USE TAX (LACH)</Jurisdiction>\n" +
                "                <CalculatedTax>0.25</CalculatedTax>\n" +
                "                <EffectiveRate>0.0025</EffectiveRate>\n" +
                "                <Taxable>100.0</Taxable>"));
    }
}

