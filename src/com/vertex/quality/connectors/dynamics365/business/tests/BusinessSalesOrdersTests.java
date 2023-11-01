package com.vertex.quality.connectors.dynamics365.business.tests;

import com.vertex.quality.connectors.dynamics365.business.enums.BusinessMainMenuNavTabs;
import com.vertex.quality.connectors.dynamics365.business.pages.*;
import com.vertex.quality.connectors.dynamics365.business.pojo.BusinessAddressPojo;
import com.vertex.quality.connectors.dynamics365.business.tests.base.BusinessBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * contains test cases for all the different sales orders scenarios.
 *
 * @author osabha, cgajes, K.Bhikshapathi
 */
@Listeners(TestRerunListener.class)
public class BusinessSalesOrdersTests extends BusinessBaseTest {

    BusinessAdminHomePage homePage;

    /**
     * CDBC-674/C365BC-521
     * creates a new sales quote and verifies the calculated tax at the quote level
     * then converts the quote to a sales order and verifies the calculated tax at the order level
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void createSalesQuoteAndChangeToOrderTest() {
        String expectedTaxRate = "6.00";
        String expectedTaxAmount = "60.05";
        String itemNumber = "1896-S";
        String quantity = "1";
        String customerCode = "Upgrade16 cust";
        String taxGroupCode = "TEST_VERTEX";

        //Sign In
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

        //Navigate to Sales Quote and create new
        BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
        BusinessSalesQuotesPage newSalesQuotes = salesQuotes.pageNavMenu.clickNew();

        //Fills Quote Info
        fillInSalesQuoteGeneralInfo(newSalesQuotes, customerCode);
        newSalesQuotes.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesQuotes.exitExpandTable();

        //Open Vertex Tax Detail Window
        openVertexTaxDetailsWindow(newSalesQuotes);

        //Get Tax Rate and amount and verify
        String actualTaxRate = newSalesQuotes.getTaxRate();
        String actualTaxAmount = newSalesQuotes.getTaxAmount();
        assertEquals(actualTaxAmount,expectedTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);
        newSalesQuotes.closeTaxDetailsDialog();

        //Convert Quote to Order
        BusinessSalesOrderPage thisSalesOrder = newSalesQuotes.convertQuoteToOrder();

        //Open Vertex Tax Detail Window
        openVertexTaxDetailsWindow(thisSalesOrder);

        //Get Tax Rate and amount and verify
        actualTaxRate = thisSalesOrder.getTaxRate();
        actualTaxAmount = thisSalesOrder.getTaxAmount();
        assertEquals(actualTaxAmount, expectedTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);
    }

    /**
     * Creates a new sales order and verifies the tax,
     * then posts that sales order and verifies the order number and tax
     * C365BC-518
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Smoke"}, retryAnalyzer = TestRerun.class)
    public void createAndPostSalesOrderTest() {
        String expectedTaxAmount = "60.05";
        String expectedTaxRate = "6.00";
        String expectedSalesOrderNumber;
        String customerCode = "Upgrade16 cust";
        String itemNumber = "1896-S";
        String quantity = "1";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.exitExpandTable();
        expectedSalesOrderNumber = newSalesOrder.getCurrentSalesNumber();

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(expectedTaxRate, actualTaxRate);

        newSalesOrder.closeTaxDetailsDialog();

        BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        String postedOrderNum = postedInvoice.getOrderNumber();

        assertEquals(expectedSalesOrderNumber, postedOrderNum);

        openVertexTaxDetailsWindow(postedInvoice);
        actualTaxRate = postedInvoice.getTaxRate();
        actualTaxAmount = postedInvoice.getTaxAmount();

        assertEquals(actualTaxRate, expectedTaxRate);
        assertEquals(actualTaxAmount, expectedTaxAmount);
    }

    /**
     * CDBC-656
     * creates a new sales order US TO CAN same billing and shipping address and verifies the calculated tax at the
     * quote level
     * !!! Fails with new environment, no tax !!!
     */
    @Test(alwaysRun = true, groups = {"Deprecated"}, retryAnalyzer = TestRerun.class)
    public void SalesOrderWithInvoiceUsToCanTest() {
        String expectedTaxRate = "6.00";
        String expectedTaxAmount = "60.05";
        String itemNumber = "1896-S";
        String quantity = "1";
        String customerName = "Canada Customer";
        String customerCode = "C00450";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = salesQuotes.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.exitExpandTable();
        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(expectedTaxRate, actualTaxRate);
    }

    /**
     * CDBC-672
     * create sales order with customer exemption certificate
     * then verify tax calculation
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void SalesOrderWithTaxExemptCertificateTest() {
        String expectedTaxRate = "0.00";
        String expectedTaxAmount = "0.00";
        String itemNumber1 = "1972-S";
        String itemNumber2 = "1969-W";
        String quantity1 = "1";
        String quantity10 = "10";
        String customerCode = "Ticket42TEST";
        String taxGroupCode = "NONTAXABLE";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber1, null, null, quantity1, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("Item", itemNumber2, null, null, quantity10, null, 2);
        newSalesOrder.exitExpandTable();
        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();

        assertEquals(actualTaxAmount, expectedTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);
    }

    /**
     * CDBC-675
     * create sales order with customer code exemption
     * then verify tax calculation
     * !!! Fails, tax !=0 !!!
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void SalesOrderWithCustomerCodeExemptionTest() {
        String expectedTaxRate = "0.00";
        String expectedTaxAmount = "0.00";
        String itemNumber1 = "1972-S";
        String itemNumber2 = "1969-W";
        String quantity1 = "1";
        String quantity5 = "5";
        String customerName = "PACS1";
         String customerCode = "Ticket42TEST";
        String taxGroupCode = "NONTAXABLE";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber1, null, null, quantity1, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("Item", itemNumber2, null, null, quantity5, null, 2);
        newSalesOrder.exitExpandTable();

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();

        assertEquals(actualTaxAmount, expectedTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);
    }

    /**
     * CDBC-673
     * create sales order with customer code exemption
     * then verify tax calculation
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void createInvoiceWithNewCustomerDifferentBillingAndShippingTest() {
        String expectedTaxRate = "6.00";
        String expectedTaxAmount = "120.10";
        String itemNumber1 = "1896-S";
        String quantity2 = "2";
        String customerName = "Cust_Test_2";
        String customerCode = "Upgrade16 cust";
        String shipToOption = "Custom Address";
        BusinessAddressPojo Address = BusinessAddressPojo
                .builder()
                .line1("8970 Coco Palm Rd")
                .city("Kissimmee")
                .state("FL")
                .zip_code("34747")
                .country("US")
                .build();

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber1, null, null, quantity2, null, 1);
        newSalesOrder.exitExpandTable();
        newSalesOrder.shippingAndBillingComponent.selectShipToCustomAddress(shipToOption);
        newSalesOrder.shippingAndBillingComponent.fillInShippingAddress(Address);

        recalculateTax(newSalesOrder);

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();

        assertEquals(actualTaxAmount, expectedTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);
    }

    /**
     * C365BC-515
     * Tests creating and posting a sales order while using all item types in the
     * item table, excluding the fixed asset type
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void salesOrderAllTypesTest() {
        String customerCode = "Upgrade16 cust";
        String itemNumber = "1896-S";
        String quantity = "1";

        String expectedTaxAmount = "63.95"; //With Charged item : 78.05

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableComment(null, "This is a test :)", 2);
        newSalesOrder.activateRow(3);
        fillInItemsTableInfo("G/L Account", "10100", null, null, "1", "50", 3);
        newSalesOrder.activateRow(4);
        fillInItemsTableInfo("Resource", "MARY", null, null, "1", null, 4);
        newSalesOrder.activateRow(5);
        //fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, "1", "50", null, 5);
        //newSalesOrder.activateRow(6);
        fillInItemsTableInfo("Fixed Asset", "FA000120", null, null, "1", "15", 5);
        newSalesOrder.exitExpandTable();
        BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        String actualTaxAmount = postedInvoice.getTotalTaxAmount();

        assertEquals(actualTaxAmount, expectedTaxAmount);
    }

    /**
     * CDBC-61
     * Tests whether the print preview shows the correct tax amount
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void salesOrderPrintPreviewTest() {
        String expectedTaxAmount = "60.05";
        String expectedTaxRate = "6.00";

        String customerCode = "Upgrade16 cust";
        String customerName = "Cust_Test_2";
        String itemNumber = "1896-S";
        String quantity = "1";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage salesOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = salesOrders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.exitExpandTable();
        newSalesOrder.salesEditNavMenu.clickPrintSendButton();
        newSalesOrder.salesEditNavMenu.selectPrintConfirmation();
        BusinessPrintPreviewPage printPreview = newSalesOrder.clickPrintPreview();

        String allText = printPreview.getPdfText();
        assertTrue(allText.contains("Total Tax\n" + expectedTaxAmount));
    }


    /**
     * CDBC-653
     * Creates a new sales order and verifies the tax for no State Tax,
     * then posts that sales order and verifies the order number and tax
     *
     * @author bhikshapathi
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void createAndPostSalesOrderWithNoStateTaxTest() {
        String expectedTaxAmount = "0.00";
        String expectedTaxRate = "0.00";

        String customerCode = "Test_DE";
        String itemNumber = "1896-S";
        String quantity = "1";
        String locationCode = "EAST";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.activateRow(2);
        newSalesOrder.exitExpandTable();

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();

        assertEquals(actualTaxAmount, expectedTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);

        newSalesOrder.closeTaxDetailsDialog();

        BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        String postedOrderNum = postedInvoice.getOrderNumber();
        System.out.println(postedOrderNum);
        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        actualTaxRate = postedInvoice.getTaxRate();
        actualTaxAmount = postedInvoice.getTaxAmount();

        assertEquals(actualTaxRate, expectedTaxRate);
        assertEquals(actualTaxAmount, expectedTaxAmount);
        newSalesOrder.closeTaxDetailsDialog();
        postedInvoice.clickBackAndSaveArrow();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<Customer>\n" +
                "            <CustomerCode>C00520</CustomerCode>\n" +
                "            <Destination taxAreaId=\"80030100\">\n" +
                "              <StreetAddress1>100 N Orange St</StreetAddress1>\n" +
                "              <City>Wilmington</City>\n" +
                "              <MainDivision>DE</MainDivision>\n" +
                "              <PostalCode>19801</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>"), "Address Validation Failed");
        assertTrue(xmlStr.contains("<CalculatedTax>0.0</CalculatedTax>"), "Calculated Tax Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.0</EffectiveRate>"), "Effective Rate Validation Failed");
        assertTrue(xmlStr.contains("<TotalTax>0.0</TotalTax>"), "Total Tax  Validation Failed");
        assertTrue(xmlStr.contains("<MainDivision>DE</MainDivision>"), "Main Division Validation Failed");

    }

    /**
     * CDBC-652
     * Creates a new sales order and verifies  no State Tax only City tax,
     * then posts that sales order and verifies the order number and tax
     *
     * @author bhikshapathi
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void createAndPostSalesOrderWithNoStateTaxOnlyCityTaxTest() {

        String expectedTaxAmount = "50.04";
        List<String> expectedTaxRate = Arrays.asList("0.00", "0.00", "5.00");
        String customerCode = "Test C365BC 224";
        String itemNumber = "1896-S";
        String quantity = "1";
        String locationCode = "EAST";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.activateRow(2);
        newSalesOrder.exitExpandTable();

        String actualTaxAmount = newSalesOrder.getTotalTaxAmount();

        openVertexTaxDetailsWindow(newSalesOrder);
        List<String> actualTaxRate = newSalesOrder.getMultipleTaxRates();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(expectedTaxRate, actualTaxRate);

        newSalesOrder.closeTaxDetailsDialog();
        BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        String postedOrderNum = postedInvoice.getOrderNumber();
        System.out.println(postedOrderNum);

        actualTaxAmount = postedInvoice.getTotalTaxAmount();

        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        List<String> actualTaxRateInInvoice = postedInvoice.getMultipleTaxRates();

        assertEquals(expectedTaxRate, actualTaxRateInInvoice);
        assertEquals(expectedTaxAmount, actualTaxAmount);
        newSalesOrder.closeTaxDetailsDialog();
        postedInvoice.clickBackAndSaveArrow();
        //Select light bulb and type vertex (this sends you to Vertex Admin)
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<Customer>\n" +
                "            <CustomerCode>C00510</CustomerCode>\n" +
                "            <Destination taxAreaId=\"21100030\">\n" +
                "              <StreetAddress1>6525 Glacier Hwy</StreetAddress1>\n" +
                "              <City>Juneau</City>\n" +
                "              <MainDivision>AK</MainDivision>\n" +
                "              <PostalCode>99801-7905</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>"));
        assertTrue(xmlStr.contains("<CalculatedTax>50.04</CalculatedTax>"), "Calculated Tax Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.05</EffectiveRate>"), "Effective Rate Validation Failed");
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");

    }

    /**
     * CDBC-236
     * Tests Statistics Functionality for Sales Order TaxAmount using all product types
     *
     * @author P.Potdar
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void compareSalesOrderTaxAmountAllProductsInStatisticsTest() {
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity = "1";
        String nontaxableCode = "FURNITURE";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("G/L Account", "10100", null, null, quantity, "10", 2);
        newSalesOrder.activateRow(3);
        fillInItemsTableInfo("Resource", "MARY", null, null, quantity, null, 3);
        newSalesOrder.activateRow(4);
        fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, null, quantity, "50", 4);
        newSalesOrder.activateRow(5);
        fillInItemsTableInfo("Fixed Asset", "FA000110", null, null, quantity, "150", 5);
        newSalesOrder.exitExpandTable();

        //Navigate to Statistics and get Sales Quote TaxAmount
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.ORDER.value, "Statistics");
        String actualTaxAmount1 = newSalesOrder.getStatisticsTaxAmount();
        newSalesOrder.dialogBoxClickClose();
        String actualTotalTaxAmount = newSalesOrder.getTotalTaxAmount();
        assertEquals(actualTotalTaxAmount, actualTaxAmount1);
    }

    /**
     * CDBC-237
     * Tests Statistics Functionality for Sales Order TaxAmount TAC is not VERTEX for all line products
     *
     * @author P.Potdar
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void SalesOrderTaxAmountAllProductsInStatisticsNoVertexTest() {

        String customerCode = "cust_NOVERTEX";
        String itemNumber = "1896-S";
        String quantity = "1";
        String nontaxableCode = "FURNITURE";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("G/L Account", "10100", null, null, quantity, "0.00", 2);
        newSalesOrder.activateRow(3);
        fillInItemsTableInfo("Resource", "MARY", null, null, quantity, null, 3);
        newSalesOrder.activateRow(4);
        fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, null, quantity, "50", 4);
        newSalesOrder.activateRow(5);
        fillInItemsTableInfo("Fixed Asset", "FA000110", null, null, quantity, "150", 5);
        newSalesOrder.exitExpandTable();

        //Navigate to Statistics and get Sales Quote TaxAmount
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.ORDER.value, "Statistics");
        String actualTaxAmount1 = newSalesOrder.getStatisticsTaxAmount();
        newSalesOrder.dialogBoxClickClose();
        String actualTotalTaxAmount = newSalesOrder.getTotalTaxAmount();
        assertEquals(actualTotalTaxAmount, actualTaxAmount1);
    }

    /**
     * CDBC-660
     * Creates a new sales order and verifies  no State Tax only City tax,
     * then posts that sales order and verifies the order number and tax
     *
     * @author bhikshapathi
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void createAndPostSalesOrderWithModifiedOriginStateTest() {
        String expectedTaxAmount = "95.08";
        List<String> expectedTaxRate = Arrays.asList("6.00", "1.25", "0.50", "0.50", "0.50", "0.50", "0.25");

        String customerCode = "ModifiedOriginState";
        String itemNumber = "1896-S";
        String quantity = "1";
        String locationCode = "EAST";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.exitExpandTable();
        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.clearLocationCodeFromShippingandBilling();

        String actualTaxAmount = newSalesOrder.getTotalTaxAmount();

        openVertexTaxDetailsWindow(newSalesOrder);
        List<String> actualTaxRate = newSalesOrder.getMultipleTaxRates();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);

        newSalesOrder.closeTaxDetailsDialog();
        newSalesOrder.expandTable();
        addLineLevelLocationCode(locationCode, 1);
        newSalesOrder.exitExpandTable();

        BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        String postedOrderNum = postedInvoice.getOrderNumber();
        System.out.println(postedOrderNum);

        actualTaxAmount = postedInvoice.getTotalTaxAmount();

        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        List<String> actualTaxRateInInvoice = postedInvoice.getMultipleTaxRates();

        assertEquals(expectedTaxRate, actualTaxRateInInvoice);
        assertEquals(expectedTaxAmount, actualTaxAmount);
        newSalesOrder.closeTaxDetailsDialog();
        postedInvoice.clickBackAndSaveArrow();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<Customer>\n" +
                "            <CustomerCode>C00180</CustomerCode>\n" +
                "            <Destination taxAreaId=\"50371900\">\n" +
                "              <StreetAddress1>100 Universal City Plz</StreetAddress1>\n" +
                "              <City>Universal City</City>\n" +
                "              <MainDivision>CA</MainDivision>\n" +
                "              <PostalCode>91608-1002</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>"));
        assertTrue(xmlStr.contains("<CalculatedTax>60.07</CalculatedTax>"), "Calculated Tax Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
    }

    /**
     * CDBC-677
     * Create Order with Discount - Multi line order amount Change Discount
     * @author bhikshapathi
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void createSalesOrderWithDiscountTest() {
        String customerCode = "Change Discount";
        String itemNumber = "1896-S";
        String quantity = "1";
        String expectedTotalTax = "57.95";
        String expectedTaxableAmount = "965.72";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        changeLineDiscount("10",1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("G/L Account", "10100", null, null, quantity, "50", 2);
        newSalesOrder.activateRow(3);
        fillInItemsTableInfo("Fixed Asset", "FA000110", null, null, quantity, "15", 3);
        newSalesOrder.activateRow(4);
        newSalesOrder.exitExpandTable();
        String actualTaxableAmount = newSalesOrder.getTaxableAmount();
        assertEquals(expectedTaxableAmount, actualTaxableAmount);
        String actualTotalTax = newSalesOrder.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTotalTax);
        String orderNum = newSalesOrder.getCurrentSalesNumber();
        //Navigate to admin page and checkXML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(orderNum);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<Customer>\n" +
                "            <CustomerCode>C00670</CustomerCode>\n" +
                "            <Destination taxAreaId=\"390910000\">\n" +
                "              <StreetAddress1>36 Railroad St</StreetAddress1>\n" +
                "              <City>Royersford</City>\n" +
                "              <MainDivision>PA</MainDivision>\n" +
                "              <PostalCode>19468-3518</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>"), "Address Validation Failed");
        assertTrue(xmlStr.contains("<Product productClass=\"FURNITURE\">1896-S</Product>\n" +
                "          <Quantity>1.0</Quantity>"), "Product Validation Failed");
        assertTrue(xmlStr.contains("<Product productClass=\"NONTAXABLE\">10100</Product>\n" +
                "          <Quantity>1.0</Quantity>"), "Product Validation Failed");
        assertTrue(xmlStr.contains("<Product productClass=\"NONTAXABLE\">FA000110</Product>\n" +
                "          <Quantity>1.0</Quantity>"), "Product Validation Failed");
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTotalTax+"</TotalTax>"), "Total Tax  Validation Failed");
    }

    /**
     * CDBC-664
     * Create Sales Order - Quotation
     * @author bhikshapathi
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void createSalesOrderQuotationTest() {
        String expectedTaxAmount = "60.05";
        String expectedTaxRate = "6.00";
        String customerCode = "Test Phila";
        String itemNumber = "1896-S";
        String quantity = "1";
        String locationCode = "EAST";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, locationCode, quantity, null, 1);
        newSalesOrder.exitExpandTable();

        //Updated Location code in Shipping and Billing
        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.updateLocationCodeFromShippingandBilling(locationCode);

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();

        assertEquals(actualTaxAmount, expectedTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);

        newSalesOrder.closeTaxDetailsDialog();
        String  SalesOrderNumber = newSalesOrder.getCurrentSalesNumber();

        salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(SalesOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<Customer>\n" +
                "            <CustomerCode>C00470</CustomerCode>\n" +
                "            <Destination taxAreaId=\"391013000\">\n" +
                "              <StreetAddress1>2955 Market St Ste 2</StreetAddress1>\n" +
                "              <City>Philadelphia</City>\n" +
                "              <MainDivision>PA</MainDivision>\n" +
                "              <PostalCode>19104-2817</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>"), "Address Validation Failed");
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
        assertTrue(xmlStr.contains("<SubTotal>1000.8</SubTotal>"), "SubTotal Tax Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");

    }
    /**
     * CDBC-662
     * Create Sales Order with Invoice
     * @author bhikshapathi
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void createSalesOrderWithInvoiceTest() {
        String expectedTaxAmount = "60.05";
        String expectedTaxRate = "6.00";

        String customerCode = "Test Phila";
        String itemNumber = "1896-S";
        String quantity = "1";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.exitExpandTable();

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(expectedTaxRate, actualTaxRate);

        newSalesOrder.closeTaxDetailsDialog();

        newSalesOrder.expandTable();

        addLineLevelLocationCode("EAST", 1);
        newSalesOrder.exitExpandTable();

        BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        String postedOrderNum = postedInvoice.getOrderNumber();
        System.out.println(postedOrderNum);
        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        actualTaxRate = postedInvoice.getTaxRate();
        actualTaxAmount = postedInvoice.getTaxAmount();

        assertEquals(expectedTaxRate, actualTaxRate);
        assertEquals(expectedTaxAmount, actualTaxAmount);
        newSalesOrder.closeTaxDetailsDialog();
        postedInvoice.clickBackAndSaveArrow();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<LineItem lineItemNumber=\"10000\" locationCode=\"EAST\" deliveryTerm=\"EXW\">"), "LineItem Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");

    }
    /**
     * CDBC-663
     * Create Sales Order with Invoice OFF
     * @author bhikshapathi
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Special"}, retryAnalyzer = TestRerun.class)
    public void createSalesOrderWithInvoiceOFFTest() {
        String customerCode = "Test Phila";
        String itemNumber = "1896-S";
        String itemNumber2 = "1908-S";
        String quantity = "1";
        String locationCode = "WEST";
        String expectedTaxableAmount = "65.99";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        // Set Invoice request enabled: OFF
        BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
        adminPage.invoiceRequestEnabledOff();
        adminPage.clickBackAndSaveArrow();

        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("Item", itemNumber2, null, "EAST", "1", "99", 2);
        newSalesOrder.exitExpandTable();
        openVertexTaxDetailsWindow(newSalesOrder);
        newSalesOrder.closeTaxDetailsDialog();
        String actualTaxableAmount = newSalesOrder.getTotalTaxAmount();
        assertEquals(expectedTaxableAmount, actualTaxableAmount);

        BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);

        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        String poptext=postedInvoice.noTaxDetailsDialog();
        assertTrue(poptext.contains("No tax details on current document"), "Tax details Validation Failed");
        String postedOrderNum = postedInvoice.getOrderNumber();
        postedInvoice.clickBackAndSaveArrow();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
         homePage.searchForAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<LineItem lineItemNumber=\"10000\" locationCode=\"WEST\" deliveryTerm=\"EXW\">"), "LineItem Validation Failed");
        assertTrue(xmlStr.contains("<LineItem lineItemNumber=\"20000\" locationCode=\"EAST\" deliveryTerm=\"EXW\">"), "LineItem Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
        adminPage.invoiceRequestEnabledOn();
    }
    /**
     * CDBC-658
     * Create Sales Order with Customer shipping location
     * @author bhikshapathi
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void createSalesOrderWithCustomerShippingLocationTest() {
        String customerCode = "Test PR";
        String itemNumber = "1896-S";
        String quantity = "1";
        List<String> expectedTaxRates = Arrays.asList("10.50", "1.00");

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.exitExpandTable();

        openVertexTaxDetailsWindow(newSalesOrder);
        List<String> actualTaxRates = newSalesOrder.getMultipleTaxRates();

        assertEquals(actualTaxRates, expectedTaxRates);

        newSalesOrder.closeTaxDetailsDialog();
        addLineLevelLocationCode("EAST", 1);

        BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);

        openVertexTaxDetailsWindowFromInvoice(postedInvoice);

        actualTaxRates = postedInvoice.getMultipleTaxRates();

        assertEquals(actualTaxRates, expectedTaxRates);

        newSalesOrder.closeTaxDetailsDialog();

        String postedOrderNum = postedInvoice.getCurrentSalesNumber();
        postedInvoice.clickBackAndSaveArrow();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<LineItem lineItemNumber=\"10000\" locationCode=\"EAST\" deliveryTerm=\"EXW\">"), "LineItem Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.105</EffectiveRate>"), "Effective Rate Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.01</EffectiveRate>"), "Effective Rate Validation Failed");

    }
    /**
     * CDBC-644
     * Create Sales Order with Invoice multi-lines with different locations
     * @author bhikshapathi
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void createSalesOrderWithInvoiceMultiLinesWithDifferentLocationsTest() {
        String customerCode = "Test Phila";
        String itemNumber = "1896-S";
        String itemNumber1 = "1908-S";
        String itemNumber2 = "1960-S";
        String quantity = "1";
        String expectedTaxRate="6.00";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, "WEST", quantity, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("Item", itemNumber1, null, "MAIN", "2", null, 2);
        newSalesOrder.activateRow(3);
        fillInItemsTableInfo("Item", itemNumber2, null, "EAST", quantity, null, 3);
        newSalesOrder.exitExpandTable();

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();

        assertEquals(expectedTaxRate, actualTaxRate);

        newSalesOrder.closeTaxDetailsDialog();

        BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);

        openVertexTaxDetailsWindowFromInvoice(postedInvoice);

        actualTaxRate = postedInvoice.getTaxRate();

        assertEquals(actualTaxRate, expectedTaxRate);

        newSalesOrder.closeTaxDetailsDialog();

        String postedOrderNum = postedInvoice.getCurrentSalesNumber();
        postedInvoice.clickBackAndSaveArrow();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<LineItem lineItemNumber=\"10000\" locationCode=\"WEST\" deliveryTerm=\"EXW\">"), "LineItem Validation Failed");
        assertTrue(xmlStr.contains("<LineItem lineItemNumber=\"20000\" locationCode=\"MAIN\" deliveryTerm=\"EXW\">"), "LineItem Validation Failed");
        assertTrue(xmlStr.contains("<LineItem lineItemNumber=\"30000\" locationCode=\"EAST\" deliveryTerm=\"EXW\">"), "LineItem Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
    }
    /**
     *     CDBC-275
     * 	  This Test validate Alert message for Sales Order  when tax group code is missing or blank while accessing the statistics page
     *      @author P.Potdar
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void salesOrderStatisticsPageMissingTaxGroupCodeAlertValidationTest() {

        String customerCode = "test1234";
        String itemNumber = "2000-S";
        String quantity = "1";
        String nontaxableCode = "FURNITURE";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.exitExpandTable();
        String actualTotalTaxAmount = newSalesOrder.getTotalTaxAmount();

        String documentNo = newSalesOrder.getCurrentSalesNumber();

        //Navigate to Statistics and get Sales Order TaxAmount
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.ORDER.value, "Statistics");

        String actualAlertMessage = newSalesOrder.getAlertMessageForTaxGroupCode();
        String lineNo = "10000";

        String expectedAlertMessage = newSalesOrder.createExpectedAlertMessageStrings("Order",documentNo,lineNo);
        assertEquals(actualAlertMessage,expectedAlertMessage,"Tax Group Statistics Alert Message Validation Failed");
    }
    /**
     * CDBC-274
     * This Test validate Alert message when tax group code is missing or blank while accessing the statitics page when TAC NOT= VERTEX
     * @author P.Potdar
     */
    @Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
    public void salesOrderStatisticsPageMissingTaxGroupCodeAlertValidationNoVertexTest(){

        String customerCode = "cust_NOVERTEX";
        String itemNumber = "2000-S";
        String quantity = "1";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.exitExpandTable();

        String actualTotalTaxAmount = newSalesOrder.getTotalTaxAmount();
        String documentNo = newSalesOrder.getCurrentSalesNumber();

        //Navigate to Statistics and get Sales Order TaxAmount
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.ORDER.value, "Statistics");

        String actualAlertMessage = newSalesOrder.getAlertMessageForTaxGroupCode();
        String lineNo = "10000";

        String expectedAlertMessage = newSalesOrder.createExpectedAlertMessageStrings("Order",documentNo,lineNo);
        assertEquals(actualAlertMessage,expectedAlertMessage,"Tax Group Statistics Alert Message Validation Failed");

    }
    /**
     * CDBC-681
     * Create Sales Order with Different Ship And Bill
     * @author bhikshapathi
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void createSalesOrderDifferentShipAndBillTest() {
        String customerCode = "Test Phila";
        String itemNumber = "1896-S";
        String quantity = "1";
        String locationCode = "WEST";
        String expectedTaxRate="6.00";
        String expectedTax="101.08";
        List<String> expectedTaxRates=Arrays.asList("6.50","0.00","2.20","1.40");

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, locationCode, quantity, null, 1);
        newSalesOrder.exitExpandTable();

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        assertEquals(actualTaxRate, expectedTaxRate);

        newSalesOrder.closeTaxDetailsDialog();
        // Ship-to: Alternate Shipping and Updated Location code
        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.selectAlternateShipToAddress("WA");

        //Verify Tax in UI field, Vertex Tax details, Stat page
        String actualTotalTax = newSalesOrder.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTax);
        openVertexTaxDetailsWindow(newSalesOrder);
        List<String> taxRates= newSalesOrder.getMultipleTaxRates();
        assertEquals(taxRates, expectedTaxRates);
        newSalesOrder.closeTaxDetailsDialog();
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.ORDER.value, "Statistics");
        String actualStatTaxAmount = newSalesOrder.getStatisticsTaxAmount();
        newSalesOrder.closeTaxDetailsDialog();
        assertEquals(actualStatTaxAmount, expectedTax);

        String  SalesOrderNumber = newSalesOrder.getCurrentSalesNumber();
        newSalesOrder.clickBackAndSaveArrow();
        assertFalse(newSalesOrder.isPopupDisplayed(),"Recalculate Popup Displayed");
        //Select light bulb and type vertex (this sends you to Vertex Admin)
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(SalesOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<Customer>\n" +
                "            <CustomerCode>C00470</CustomerCode>\n" +
                "            <Destination taxAreaId=\"480332364\">\n" +
                "              <StreetAddress1>22833 NE 8th St</StreetAddress1>\n" +
                "              <City>Sammamish</City>\n" +
                "              <MainDivision>WA</MainDivision>\n" +
                "              <PostalCode>98074-7232</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>"));
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTax+"</TotalTax>"), "Total Tax  Validation Failed");

    }
    /**
     * CDBC-667
     * Create Sales Order, Create Invoice, add/delete lines, change quantity and location
     * @author bhikshapathi
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void CreateInvoiceAndUpdateLinesAndChangeQuantityAndLocationTest() {
        String customerCode = "Test Phila";
        String itemNumber1 = "1896-S";
        String itemNumber = "1908-S";
        String itemNumber2 = "1960-S";
        String itemNumber3 = "1972-S";
        String quantity = "1";
        String expectedTaxRate="6.00";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("Item", itemNumber1, null, "EAST", "10", null, 2);
        newSalesOrder.activateRow(3);
        fillInItemsTableInfo("Item", itemNumber2, null, "EAST", quantity, null, 3);
        newSalesOrder.exitExpandTable();

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        assertEquals(expectedTaxRate, actualTaxRate);

        newSalesOrder.closeTaxDetailsDialog();
       String postedOrderNum = newSalesOrder.getCurrentSalesNumber();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<Customer>\n" +
                "            <CustomerCode>C00470</CustomerCode>\n" +
                "            <Destination taxAreaId=\"391013000\">\n" +
                "              <StreetAddress1>2955 Market St Ste 2</StreetAddress1>\n" +
                "              <City>Philadelphia</City>\n" +
                "              <MainDivision>PA</MainDivision>\n" +
                "              <PostalCode>19104-2817</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>\n"), "Address Validation Failed");
        assertTrue(xmlStr.contains("<Product productClass=\"FURNITURE\">1908-S</Product>\n" +
                "          <Quantity>1.0</Quantity>"), "Product Validation Failed");
        assertTrue(xmlStr.contains("<Product productClass=\"FURNITURE\">1896-S</Product>\n" +
                "          <Quantity>10.0</Quantity>"), "Product Validation Failed");
        assertTrue(xmlStr.contains("<Product productClass=\"FURNITURE\">1960-S</Product>\n" +
                "          <Quantity>1.0</Quantity>"), "Product Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
        adminPage.clickBackAndSaveArrow();

        // change Lines
        deletingLine(1);
        newSalesOrder.activateRow(1);
        updateQuantity("5",1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("Item", itemNumber3, null, "EAST", "2", null, 2);
        newSalesOrder.activateRow(1);
        addLineLevelLocationCode("WEST", 1);

        BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);

        openVertexTaxDetailsWindowFromInvoice(postedInvoice);

        actualTaxRate = postedInvoice.getTaxRate();

        assertEquals(actualTaxRate, expectedTaxRate);

        newSalesOrder.closeTaxDetailsDialog();

        String postedInvoiceNum = postedInvoice.getCurrentSalesNumber();
        postedInvoice.clickBackAndSaveArrow();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        homePage.searchForAndNavigateToVertexAdminPage();
        // Check XML
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedInvoiceNum);
        adminPage.filterxml("Invoice Response");
        String invoiceXmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(invoiceXmlStr.contains("<Customer>\n" +
                "            <CustomerCode>C00470</CustomerCode>\n" +
                "            <Destination taxAreaId=\"391013000\">\n" +
                "              <StreetAddress1>2955 Market St Ste 2</StreetAddress1>\n" +
                "              <City>Philadelphia</City>\n" +
                "              <MainDivision>PA</MainDivision>\n" +
                "              <PostalCode>19104-2817</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>\n"), "Address Validation Failed");
        assertTrue(invoiceXmlStr.contains("<Product productClass=\"FURNITURE\">1896-S</Product>\n" +
                "         <Quantity>5.0</Quantity>"), "Product Validation Failed");
        assertTrue(invoiceXmlStr.contains("<Product productClass=\"NONTAXABLE\">1972-S</Product>\n" +
                "          <Quantity>2.0</Quantity>"), "Product Validation Failed");
        assertTrue(invoiceXmlStr.contains("<TotalTax>300.24</TotalTax>"), "TotalTax Validation Failed");
        assertTrue(invoiceXmlStr.contains("<TotalTax>22.82</TotalTax>"), "TotalTax Validation Failed");
          }
    /**
     * CDBC-666
     * Creates a new sales order and Create Invoice with quantity 10
     * @author bhikshapathi
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void createsSalesOrderAndPostInvoiceWithQuantity10Test() {
        String expectedTaxAmount = "1,030.01";
        List<String> expectedTaxRate = Arrays.asList("6.50", "0.00", "2.20", "1.40", "6.50", "0.00", "2.20","1.40");
        String customerCode = "Cust Sam WA";
        String itemNumber = "1896-S";
        String itemNumber1 = "1908-S";
        String quantity = "1";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, "MAIN", "10", null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("Item", itemNumber1, null, "EAST", quantity, null, 2);
        newSalesOrder.exitExpandTable();

        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.updateLocationCodeFromShippingandBilling("EAST");

        openVertexTaxDetailsWindow(newSalesOrder);
        assertTrue(newSalesOrder.verifyVertexTaxDetailsField("Imposition"));
        List<String> actualTaxRate = newSalesOrder.getMultipleTaxRates();
        newSalesOrder.closeTaxDetailsDialog();
        String actualTaxAmount = newSalesOrder.getTotalTaxAmount();
        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);

        BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        actualTaxAmount = postedInvoice.getTotalTaxAmount();

        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        List<String> actualTaxRateInInvoice = postedInvoice.getMultipleTaxRates();

        assertEquals(expectedTaxRate, actualTaxRateInInvoice);
        assertEquals(expectedTaxAmount, actualTaxAmount);
        newSalesOrder.closeTaxDetailsDialog();
        String postedOrderNum = postedInvoice.getCurrentSalesNumber();
        System.out.println(postedOrderNum);
        postedInvoice.clickBackAndSaveArrow();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<Customer>\n" +
                "            <CustomerCode>C00120</CustomerCode>\n" +
                "            <Destination taxAreaId=\"480332364\">\n" +
                "              <StreetAddress1>22833 NE 8th St</StreetAddress1>\n" +
                "              <City>Sammamish</City>\n" +
                "              <MainDivision>WA</MainDivision>\n" +
                "              <PostalCode>98074-7232</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>"), "Address Validation Failed");
        assertTrue(xmlStr.contains("<Quantity>10.0</Quantity>"), "Quantity Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.065</EffectiveRate>"), "Effective Rate Validation Failed");
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount.replace(",","")+"</TotalTax>"), "Total Tax  Validation Failed");
    }
    /**
     * CDBC-680
     * Create Invoice with Shipping - change shipping address
     * @author bhikshapathi
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void createInvoiceWithShippingChangeShippingAddressTest() {
        String customerCode = "Test Phila";
        String itemNumber = "1896-S";
        String quantity = "1";
        String expectedTaxRate="6.00";
        List<String> expectedTaxRates = Arrays.asList("2.90", "1.23", "3.07", "1.00");

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.exitExpandTable();

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();

        assertEquals(actualTaxRate, expectedTaxRate);
        String postedOrderNum=newSalesOrder.getCurrentSalesNumber();
        newSalesOrder.closeTaxDetailsDialog();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<Customer>\n" +
                "            <CustomerCode>C00470</CustomerCode>\n" +
                "            <Destination taxAreaId=\"391013000\">\n" +
                "              <StreetAddress1>2955 Market St Ste 2</StreetAddress1>\n" +
                "              <City>Philadelphia</City>\n" +
                "              <MainDivision>PA</MainDivision>\n" +
                "              <PostalCode>19104-2817</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>"), "Address Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
        adminPage.clickBackAndSaveArrow();

        //Change Shipping Address
        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.clickShowMore();
        newSalesOrder.selectCustomShipToAddress();
        fillInCustomAddressInShippingAndBilling("1575 Space Center Drive","Colorado Springs","CO","80915");
        openVertexTaxDetailsWindow(newSalesOrder);

        actualTaxRate = newSalesOrder.getTaxRate();

        assertEquals(actualTaxRate, expectedTaxRate);
        newSalesOrder.closeTaxDetailsDialog();

        BusinessSalesInvoicePage postedInvoice = postOrderInvoiceWithCustomAddress(newSalesOrder);
        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        List<String> actualTaxRateInInvoice = postedInvoice.getMultipleTaxRates();

        assertEquals(actualTaxRateInInvoice, expectedTaxRates);
        newSalesOrder.closeTaxDetailsDialog();
        String postedInvoiceNum = postedInvoice.getCurrentSalesNumber();
        System.out.println(postedInvoiceNum);
        postedInvoice.clickBackAndSaveArrow();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        homePage.searchForAndNavigateToVertexAdminPage();

        //Check Xml Tax Calc Request and Response
        adminPage.filterDocuments(postedInvoiceNum);
        adminPage.filterxml("Invoice Response");
        String invoiceXmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(invoiceXmlStr.contains("<Customer>\n" +
                "            <CustomerCode>C00470</CustomerCode>\n" +
                "            <Destination taxAreaId=\"60410090\">\n" +
                "              <StreetAddress1>1575 Space Center Dr</StreetAddress1>\n" +
                "              <City>Colorado Springs</City>\n" +
                "              <MainDivision>CO</MainDivision>\n" +
                "              <PostalCode>80915-2441</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>"), "Address Validation Failed");
        assertTrue(invoiceXmlStr.contains("<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"4220\">COLORADO</Jurisdiction>\n" +
                "            <CalculatedTax>29.02</CalculatedTax>\n" +
                "            <EffectiveRate>0.029</EffectiveRate>"), "State Tax Rate Validation Failed");
        assertTrue(invoiceXmlStr.contains(" <Jurisdiction jurisdictionLevel=\"CITY\" jurisdictionId=\"4420\">COLORADO SPRINGS</Jurisdiction>\n" +
                "            <CalculatedTax>30.73</CalculatedTax>\n" +
                "            <EffectiveRate>0.0307</EffectiveRate>"), "City Tax rate Validation Failed");
        assertTrue(invoiceXmlStr.contains("<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"79121\">PIKES PEAK RURAL TRANSPORTATION AUTHORITY</Jurisdiction>\n" +
                "            <CalculatedTax>10.01</CalculatedTax>\n" +
                "            <EffectiveRate>0.01</EffectiveRate>"), "DISTRICT Tax Rate Validation Failed");
    }

    /**
     * CDBC-702
     * Verifies that NJ customer has no tax on clothing
     */
    @Test(alwaysRun = true, groups = {  "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
    public void salesOrderClothingExemptionTest( )
    {
        String customerCode = "Test Phila";
        String itemNumber = "1972-S";
        String quantity = "1";
        String expectedTaxAmount = "0.00";
        String expectedTaxRate = "0.00";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage salesOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = salesOrders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.exitExpandTable();
        //Open Vertex Tax Detail Window
        openVertexTaxDetailsWindow(newSalesOrder);

        //Get Tax Rate and amount and verify
        String actualTaxRate = newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();
        assertEquals(actualTaxAmount,expectedTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);
        newSalesOrder.closeTaxDetailsDialog();
    }

    /**
     * CDBC-712
     * Tests creating a sales order With Product Code Exemption and posting it to an invoice,
     * ensuring the tax is properly shown as 0 (exempt)
     */
    @Test(alwaysRun = true, groups = {  "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
    public void salesOrderWithProductCodeExemptionTest( )
    {
        String customerCode = "Test Phila";
        String itemNumber = "VTXPCODE";
        String quantity = "1";
        String expectedTaxAmount = "0.00";
        String expectedTaxRate = "0.00";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage salesOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = salesOrders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.exitExpandTable();
        recalculateTax(newSalesOrder);
        //Open Vertex Tax Detail Window
        //New Commit
        openVertexTaxDetailsWindow(newSalesOrder);

        //Get Tax Rate and amount and verify
        String actualTaxRate = newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();
        assertEquals(actualTaxAmount,expectedTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);
        newSalesOrder.closeTaxDetailsDialog();
    }
    /**
     * CDBC-708
     * Verifies that tax is 0 for non-registered taxpayer
     */
    @Test(alwaysRun = true, groups = {  "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
    public void verifyZeroTaxTest( ){
        String customerCode = "Test_Wisconsin";
        String itemNumber = "1896-S";
        String quantity = "1";
        String expectedTaxRate="0.00";
        String expectedTaxAmount="0.00";
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage salesOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = salesOrders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.exitExpandTable();
        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();
        assertEquals(actualTaxAmount,expectedTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);
        newSalesOrder.closeTaxDetailsDialog();
        String orderNum=  newSalesOrder.getCurrentSalesNumber();
        String totalTaxAmount=newSalesOrder.getTotalTaxAmount();
        assertEquals(totalTaxAmount,expectedTaxAmount);

    }
    /**
     * CDBC-999
     * Verifies that address is cleansed on providing incorrect address while posting
     */
    @Test(alwaysRun = true, groups = {  "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
    public void verifyAddressCleansingInPostingTest( ) {
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity = "1";
        String expectedTaxAmount = "0.00";

        //Navigate to sales order and create document
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage salesOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = salesOrders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.exitExpandTable();

        //change to Custom address, verify address cleansing and tax recalculation
        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.clickShowMore();
        newSalesOrder.selectCustomShipToAddress();
        fillInCustomAddressInShippingAndBilling(null,"Wilmington","DE","19805");
        newSalesOrder.salesEditNavMenu.clickPostingTab();
        newSalesOrder.salesEditNavMenu.selectPostButton();
        String actualMessage=newSalesOrder.dialogBoxReadMessage();
        assertTrue(actualMessage.contains("The address cannot be verified. Some of the address information may be missing or incorrect, or it does not exist in the USPS database. This may result in incorrect tax being applied to transactions using this address. Do you want to save the address as entered?"), "Error message Validation Failed");
        newSalesOrder.dialogBoxClickYes();
        newSalesOrder.dialogBoxClickYes();
        newSalesOrder.selectShipAndInvoicePosting();
        BusinessSalesInvoicePage invoicePage = newSalesOrder.goToInvoice();
        String actualTaxAmount=invoicePage.getTotalTaxAmount();
        assertEquals(expectedTaxAmount,actualTaxAmount);
    }

    /**
     * @TestCase CDBC-1401
     * @Description - Verifying Shipment Method Code For US To Canada For Sales Order
     * @Author Mario Saint-Fleur
     * */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void verifyShipmentMethodCodeForUSToCanadaForSalesOrder() {
        String customerCode = "TestingService_PA";
        String itemNumber = "1896-S";
        String quantity = "1";
        String expectedTax = "60.05";
        String expectedTaxAfterUpdate = "130.10";
        String shipmentMethodCode = "SUP";

        //Navigate to sales order and create document
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage salesOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = salesOrders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.exitExpandTable();

        String orderNum = newSalesOrder.getCurrentSalesNumber();
        String actualTaxAmount=newSalesOrder.getTotalTaxAmount();
        assertEquals(expectedTax, actualTaxAmount);

        //Change Shipment address to Canada and Shipment Code to SUP
        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.clickShowMore();
        newSalesOrder.selectCustomShipToAddress();
        fillInCustomAddressInShippingAndBilling("3631 James Street","Smooth Rock Falls","Ontario","P0L 2B0", "CA");
        newSalesOrder.selectShipmentMethodCode(shipmentMethodCode);

        //Recalculate taxes, verify taxes, open Vertex tax details, and verify imposition
        recalculateTax(newSalesOrder);
        String updatedTotalTaxOnServiceLines = newSalesOrder.getTotalTaxAmount();
        assertEquals(expectedTaxAfterUpdate, updatedTotalTaxOnServiceLines);
        openVertexTaxDetailsWindow(newSalesOrder);
        String actualImpositionValue = newSalesOrder.getImpositionValue();
        String expectedImpositionValue = "GST/HST";
        assertEquals(expectedImpositionValue, actualImpositionValue);
        newSalesOrder.closeTaxDetailsDialog();

        //Navigate to XML and validate presence of delivery term
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(orderNum);
        adminPage.filterxml("Tax Calc Request");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("deliveryTerm=\"SUP\""), "Delivery term is not correct. Expected: " + shipmentMethodCode + " but found a different code");
    }

    /**
     * @TestCase CDBC-1412
     * @Description - Verifying Shipment Method Code That Is Invalid In Oseries For Sales Order
     * @Author Mario Saint-Fleur
     * */
    @Test(groups = {"D365_Business_Central_Sales_Regression"})
    public void verifyShipmentMethodCodeInvalidForOseriesSalesOrder() {
        String customerCode = "TestingService_PA";
        String itemNumber = "1896-S";
        String quantity = "1";
        String expectedTax = "63.95";
        String shipmentMethodCode = "123";

        //Navigate to sales order and create document
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage salesOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = salesOrders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("G/L Account", "10100", null, null, quantity, "50", 2);
        newSalesOrder.activateRow(3);
        fillInItemsTableInfo("Fixed Asset", "FA000110", null, null, quantity, "15", 3);
        newSalesOrder.exitExpandTable();

        //Verify Sales Tax Amount
        String orderNum = newSalesOrder.getCurrentSalesNumber();
        String actualTaxAmount=newSalesOrder.getTotalTaxAmount();
        assertEquals(expectedTax, actualTaxAmount);

        //Change Shipment Code to 123
        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.clickShowMore();
        newSalesOrder.selectShipmentMethodCode(shipmentMethodCode);

        //Navigate to XML and validate presence of delivery term
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(orderNum);
        adminPage.filterxml("Tax Calc Request");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("deliveryTerm=\"EXW\""), "Delivery term is not correct. Expected: " + shipmentMethodCode + " but found a different code");
        adminPage.filterxml("Tax Calc Response");
        xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>63.95</TotalTax>"), "Total tax validation failed. Expected: " + expectedTax + " but found incorrect tax amount");
    }
    @BeforeMethod(alwaysRun = true)
    public void setUpBusinessMgmt(){
        String role="Business Manager";
        homePage = new BusinessAdminHomePage(driver);
        String verifyPage=homePage.verifyHomepageHeader();
        if(!verifyPage.contains(role)){

            //navigate to select role as Business Manager
            homePage.selectSettings();
            homePage.navigateToManagerInSettings(role);
        }
    }
}