package com.vertex.quality.connectors.dynamics365.business.tests;

import com.vertex.quality.connectors.dynamics365.business.enums.BusinessMainMenuNavTabs;
import com.vertex.quality.connectors.dynamics365.business.pages.*;
import com.vertex.quality.connectors.dynamics365.business.tests.base.BusinessBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * contains test cases for all the different sales return orders scenarios.
 *
 * @author bhikshapathi
 */
@Listeners(TestRerunListener.class)
public class BusinessSalesReturnOrdersTests extends BusinessBaseTest
{
    BusinessAdminHomePage homePage;
    /**
     * CDBC-267
     * Business Central Statistics pages -Sales Return ORDER_All Products_TAC= VERTEX
     * @author bhikshapathi
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Smoke"}, retryAnalyzer = TestRerun.class)
    public void compareSalesReturnOrderTaxAmountInStatisticsTest() {
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity = "10";
        String expectedTaxAmount = "600.48";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesReturnOrderListPage returnOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Return Orders");
        BusinessSalesReturnOrderPage newSalesReturnOrder = returnOrders.pageNavMenu.clickNew();

        fillInSalesReturnOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, "MAIN", quantity, null, 1);
        newSalesReturnOrder.exitExpandTable();

        String actualTotalTaxAmount = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(actualTotalTaxAmount, expectedTaxAmount);

        //Navigate to Statistics and get Sales order TaxAmount
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.RETURN_ORDER.value, "Statistics");

        String actualTaxAmount1 = newSalesReturnOrder.getStatisticsTaxAmount();
        newSalesReturnOrder.closeTaxDetailsDialog();
        assertEquals(actualTotalTaxAmount, actualTaxAmount1);

    }

    /**
     * CDBC-278/C365BC-525
     * This Test validate Alert message when tax group code is missing or blank while accessing the statitics page
     * @author P.Potdar
     */
    @Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
    public void salesReturnOrderStatisticsPageMissingTaxGroupCodeAlertValidationTest(){

        String customerCode = "test1234";
        String itemNumber = "2000-S";
        String quantity = "1";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesReturnOrderListPage returnOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Return Orders");
        BusinessSalesReturnOrderPage newSalesReturnOrder = returnOrders.pageNavMenu.clickNew();

        fillInSalesReturnOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, "MAIN", quantity, null, 1);
        newSalesReturnOrder.exitExpandTable();

        String actualTotalTaxAmount =  newSalesReturnOrder.getTotalTaxAmount();
        String documentNo =  newSalesReturnOrder.getCurrentSalesNumber();
        openVertexTaxDetailsWindow(newSalesReturnOrder);
        assertTrue(newSalesReturnOrder.verifyVertexTaxDetailsField("Imposition"));
        newSalesReturnOrder.closeTaxDetailsDialog();
        //Navigate to Statistics and get Sales return order TaxAmount
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.RETURN_ORDER.value, "Statistics");

        String actualAlertMessage =  newSalesReturnOrder.getAlertMessageForTaxGroupCode();
        String lineNo = "10000";

        String expectedAlertMessage =  newSalesReturnOrder.createExpectedAlertMessageStrings("Return Order",documentNo,lineNo);
        assertEquals(actualAlertMessage,expectedAlertMessage,"Tax Group Statistics Alert Message Validation Failed");

    }
    /**
     * CDBC-279
     * This Test validate Alert message when tax group code is missing or blank while accessing the statitics page when TAC NOT= VERTEX
     * @author P.Potdar
     */
    @Ignore
    @Test(alwaysRun = true, groups = { "D365_Business_Central_Deprecated" }, retryAnalyzer = TestRerun.class)
    public void salesReturnOrderStatisticsPageMissingTaxGroupCodeAlertValidationNoVertexTest(){

        String customerCode = "cust_NOVERTEX";
        String itemNumber = "2000-S";
        String quantity = "1";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesReturnOrderListPage returnOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Return Orders");
        BusinessSalesReturnOrderPage newSalesReturnOrder = returnOrders.pageNavMenu.clickNew();

        fillInSalesReturnOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, "MAIN", quantity, null, 1);
        newSalesReturnOrder.exitExpandTable();

        String actualTotalTaxAmount =  newSalesReturnOrder.getTotalTaxAmount();
        String documentNo =  newSalesReturnOrder.getCurrentSalesNumber();

        //Navigate to Statistics and get Sales return order TaxAmount
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.RETURN_ORDER.value, "Statistics");

        String actualAlertMessage =  newSalesReturnOrder.getAlertMessageForTaxGroupCode();
        String lineNo = "10000";

        String expectedAlertMessage =  newSalesReturnOrder.createExpectedAlertMessageStrings("Return Order",documentNo,lineNo);
        assertEquals(actualAlertMessage,expectedAlertMessage,"Tax Group Statistics Alert Message Validation Failed");

    }


    /**
     * CDBC-268
     * Business Central Statistics pages -Sales Return ORDER_All Products_TAC NOT = VERTEX
     * @author P.Potdar
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void compareSalesReturnOrderTaxAmountInStatisticsNoVertexTest() {

        String customerCode = "cust_NOVERTEX";
        String itemNumber = "1896-S";
        String quantity = "20";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesReturnOrderListPage returnOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Return Orders");
        BusinessSalesReturnOrderPage newSalesReturnOrder = returnOrders.pageNavMenu.clickNew();

        fillInSalesReturnOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.expandTable();
        newSalesReturnOrder.activateRow(1);
        fillInItemsTableInfo("Item", itemNumber, null, "MAIN", quantity, null, 1);
        newSalesReturnOrder.exitExpandTable();

        //Navigate to Statistics and get Sales order TaxAmount
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.RETURN_ORDER.value, "Statistics");

        String statTaxAmount = newSalesReturnOrder.getStatisticsTaxAmount();

        newSalesReturnOrder.closeTaxDetailsDialog();
        String returnOrderTaxAmount = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(returnOrderTaxAmount, statTaxAmount);

    }
    /**
     * CDBC-1022
     * Business Central Recalculate Tax _ Sales Return Order Card_All PRODUCT types
     * @author bhikshapathi
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void recalculateTaxSalesReturnOrderCardAllPRODUCTTypesTest()
    {
        String customerCode = "Test Phila";
        String itemNumber = "1896-S";
        String quantity = "1";
        String taxAmount="69.65";
        int initialCount=8;
        int finalCount=10;

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesReturnOrderListPage returnOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Return Orders");
        BusinessSalesReturnOrderPage newSalesReturnOrder = returnOrders.pageNavMenu.clickNew();
        fillInSalesReturnOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, "MAIN", quantity, null, 1);
        newSalesReturnOrder.activateRow(2);
        fillInItemsTableInfo("Resource","MARY", null, "MAIN", quantity, "10", 2);
        newSalesReturnOrder.activateRow(3);
        fillInItemsTableInfo("Charge (Item)","P-FREIGHT", null, "MAIN", quantity, "50", 3);
        newSalesReturnOrder.activateRow(4);
        fillInItemsTableInfo("G/L Account","10100", null, "MAIN", quantity, "100", 4);
        newSalesReturnOrder.activateRow(5);
        newSalesReturnOrder.exitExpandTable();
        String actualTaxAmount = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(taxAmount, actualTaxAmount);
        String returnOrderNum = newSalesReturnOrder.getCurrentSalesNumber();
        System.out.println(returnOrderNum);
        //Select light bulb and type vertex (this sends you to Vertex Admin)
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(returnOrderNum);
        int i=adminPage.getResponseCount(returnOrderNum);
        assertEquals(i,initialCount);
        adminPage.clickBackAndSaveArrow();
        openRecalculateTaxDetailsWindow(newSalesReturnOrder);
        //Select light bulb and type vertex (this sends you to Vertex Admin)
        homePage.searchForAndNavigateToVertexAdminPage();
        // Check XML
        //Tax Calc Request and Response
        adminPage.filterDocuments(returnOrderNum);
        int j=adminPage.getResponseCount(returnOrderNum);
        assertEquals(j,finalCount);
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
        assertTrue(xmlStr.contains("<Product productClass=\"FURNITURE\">1896-S</Product>\n" +
                "          <Quantity>1.0</Quantity>"), "Product Validation Failed");
        assertTrue(xmlStr.contains("<Product productClass=\"LABOR\">MARY</Product>\n" +
                "          <Quantity>1.0</Quantity>"), "Product Validation Failed");
        assertTrue(xmlStr.contains("<Product productClass=\"LABOR\">P-FREIGHT</Product>\n" +
                "          <Quantity>1.0</Quantity>"), "Product Validation Failed");
        assertTrue(xmlStr.contains("<Product productClass=\"NONTAXABLE\">10100</Product>\n" +
                "          <Quantity>1.0</Quantity>"), "Product Validation Failed");
        assertTrue(xmlStr.contains("<SubTotal>1160.8</SubTotal>"), "SubTotal Tax Validation Failed");
        assertTrue(xmlStr.contains("<Total>1230.45</Total>"), "Total including Tax  Validation Failed");
        assertTrue(xmlStr.contains("<TotalTax>"+taxAmount+"</TotalTax>"), "Total Tax Amount Validation Failed");
    }
    /**
     * CDBC-700
     * Return Sales order - partial amount
     * @author Shruti
     */
    @Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
    public void validateReturnOrderForPartialAmountTest() {
        String customerCode = "test1234";
        String itemNumber="1896-S";
        String quantity="1";
        String expectedTaxAmount = "30.00";
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

        //create and post invoice
        BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
        BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();
        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
        newInvoice.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newInvoice.exitExpandTable();
        BusinessSalesInvoicePage postedInvoice = salesDocumentPostInvoice(newInvoice);
        String totalTax=postedInvoice.getTotalTaxAmount();
        String postedInvoiceNo=postedInvoice.getCurrentSalesNumber();
        postedInvoice.clickBackAndSaveArrow();

        //Return Order
        BusinessSalesReturnOrderListPage returnOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Return Orders");
        BusinessSalesReturnOrderPage newSalesReturnOrder = returnOrders.pageNavMenu.clickNew();
        fillInSalesReturnOrderGeneralInfo(newSalesReturnOrder, customerCode);
        //Select Process tab and filter document
        newSalesReturnOrder.salesEditNavMenu.clickPrepareButton();
        newSalesReturnOrder.selectPostedDocumentLinesToReverse();
        newSalesReturnOrder.filterDocuments(postedInvoiceNo);
        newSalesReturnOrder.dialogBoxClickOk();
        newSalesReturnOrder.expandTable();
        newSalesReturnOrder.activateRow(3);
        newSalesReturnOrder.updateAmount("500", 3);
        newSalesReturnOrder.exitExpandTable();
        String documentNo =  newSalesReturnOrder.getCurrentSalesNumber();
        String actualTotalTaxAmount =  newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(expectedTaxAmount,actualTotalTaxAmount);
        //Verify XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(documentNo);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<Customer>\n" +
                "            <CustomerCode classCode=\"CUSTCLASS\">C00210</CustomerCode>\n" +
                "            <Destination taxAreaId=\"390290000\">\n" +
                "              <StreetAddress1>400 Simpson Dr</StreetAddress1>\n" +
                "              <City>Chester Springs</City>\n" +
                "              <MainDivision>PA</MainDivision>\n" +
                "              <PostalCode>19425-9546</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>"), "Address Validation Failed");
        assertTrue(xmlStr.contains("<Product productClass=\"FURNITURE\">1896-S</Product>\n" +
                "          <Quantity>1.0</Quantity>"), "Product Validation Failed");
        String actualTotalTax = xmlStr.substring(xmlStr.indexOf("<TotalTax>") + "<TotalTax>".length(), xmlStr.indexOf("</TotalTax>"));
        assertTrue(xmlStr.contains("<TotalTax>30.0</TotalTax>"), String.format("Total Tax Validation Failed: Actual: %1$s    Expected: %2$s",actualTotalTax, expectedTaxAmount));
    }
    /**
     * CDBC-698
     * Return Sales order - partial quantity returned
     * @author Shruti
     */
    @Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
    public void validateReturnOrderForPartialQuantityTest() {
        String customerCode = "test1234";
        String expectedTaxAmount = "11.57";
        String itemNumber="1900-S";
        String quantity="2";
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

        //create and post invoice
        BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
        BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();
        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
        newInvoice.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newInvoice.exitExpandTable();
        BusinessSalesInvoicePage postedInvoice = salesDocumentPostInvoice(newInvoice);
        String totalTax=postedInvoice.getTotalTaxAmount();
        String postedInvoiceNo=postedInvoice.getCurrentSalesNumber();
        postedInvoice.clickBackAndSaveArrow();
        BusinessSalesReturnOrderListPage returnOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Return Orders");
        BusinessSalesReturnOrderPage newSalesReturnOrder = returnOrders.pageNavMenu.clickNew();
        fillInSalesReturnOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.salesEditNavMenu.clickPrepareButton();
        newSalesReturnOrder.selectPostedDocumentLinesToReverse();
        newSalesReturnOrder.enableShowReversibleLines();
        newSalesReturnOrder.enableReturnOriginalQuantity();
        newSalesReturnOrder.filterDocuments(postedInvoiceNo);
        newSalesReturnOrder.dialogBoxClickOk();
        newSalesReturnOrder.expandTable();
        newSalesReturnOrder.activateRow(3);
        newSalesReturnOrder.updateQuantity("1", 3);
        newSalesReturnOrder.exitExpandTable();
        String documentNo =  newSalesReturnOrder.getCurrentSalesNumber();
        String actualTotalTaxAmount =  newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(actualTotalTaxAmount,expectedTaxAmount,String.format("Total Tax Validation Failed: Actual: %1$s    Expected: %2$s",actualTotalTaxAmount, expectedTaxAmount));
        //Verify XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(documentNo);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<Customer>\n" +
                "            <CustomerCode classCode=\"CUSTCLASS\">C00210</CustomerCode>\n" +
                "            <Destination taxAreaId=\"390290000\">\n" +
                "              <StreetAddress1>400 Simpson Dr</StreetAddress1>\n" +
                "              <City>Chester Springs</City>\n" +
                "              <MainDivision>PA</MainDivision>\n" +
                "              <PostalCode>19425-9546</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>"), "Address Validation Failed");
        assertTrue(xmlStr.contains("<Product productClass=\"FURNITURE\">1900-S</Product>\n" +
                "          <Quantity>1.0</Quantity>"), "Product Validation FAiled");
        String actualTotalTax = xmlStr.substring(xmlStr.indexOf("<TotalTax>") + "<TotalTax>".length(), xmlStr.indexOf("</TotalTax>"));
        assertEquals(actualTotalTax, expectedTaxAmount, String.format("Total Tax Validation Failed: Actual: %1$s    Expected: %2$s",actualTotalTax, expectedTaxAmount));
        assertTrue(xmlStr.contains("<TotalTax>11.57</TotalTax>"), String.format("Total Tax Validation Failed: Actual: %1$s    Expected: %2$s",actualTotalTax, expectedTaxAmount));
    }

    /**
     * CDBC-707 Replacement Sales Order
     * @author Shruti
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void validateReplacementSalesOrderTest() {
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity = "-1";
        String expectedTax="-18.00";
        String expectedOrderTax="18.00";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderPage newSalesOrder=new BusinessSalesOrderPage(driver);
        BusinessSalesReturnOrderListPage returnOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Return Orders");
        BusinessSalesReturnOrderPage newSalesReturnOrder = returnOrders.pageNavMenu.clickNew();

        fillInSalesReturnOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, "200", 1);
        newSalesReturnOrder.activateRow(2);
        fillInItemsTableInfo("G/L Account", "10100", null, null, quantity, "100", 2);
        newSalesReturnOrder.activateRow(3);
        newSalesReturnOrder.exitExpandTable();
        String actualTotalTaxAmount = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(actualTotalTaxAmount, expectedTax);
        //Navigate to Functions in Actions Tab
        openFunctionsFromActions(newSalesReturnOrder);
        newSalesReturnOrder.salesEditNavMenu.selectMoveNegativeLines();

        newSalesReturnOrder.salesEditNavMenu.selectOrderDocType();
        newSalesReturnOrder.dialogBoxClickOk();
        newSalesReturnOrder.dialogBoxClickYes();
        String totalTaxAmount = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(totalTaxAmount, expectedOrderTax);
        String orderNum = newSalesOrder.getOrderNumber();
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.ORDER.value, "Statistics");
        String actualTax=newSalesOrder.getStatisticsTaxAmount();
        assertEquals(actualTax, expectedOrderTax);
        newSalesOrder.closeTaxDetailsDialog();
        //Verify XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(orderNum);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<Customer>\n" +
                "            <CustomerCode classCode=\"CUSTCLASS\">C00210</CustomerCode>\n" +
                "            <Destination taxAreaId=\"390290000\">\n" +
                "              <StreetAddress1>400 Simpson Dr</StreetAddress1>\n" +
                "              <City>Chester Springs</City>\n" +
                "              <MainDivision>PA</MainDivision>\n" +
                "              <PostalCode>19425-9546</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>"), "Address Validation Failed");
        assertTrue(xmlStr.contains("<Product productClass=\"FURNITURE\">1896-S</Product>\n" +
                "          <Quantity>1.0</Quantity>"), "Product Validation Failed");
        assertTrue(xmlStr.contains("<Product productClass=\"NONTAXABLE\">10100</Product>\n" +
                "          <Quantity>1.0</Quantity>"), "Product Validation Failed");
        assertTrue(xmlStr.contains("<TotalTax>18.0</TotalTax>"), "Tax Validation Failed");
    }

    /**
     * CDBC-871
     * Verifies if tax is displayed correctly for posted documents line to reverse
     * @author Shruti
     */
    @Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
    public void verifyTaxForPostedDocumentsLinesToReverseTest() {
        String customerCode = "test1234";
        String itemNumber="1896-S";
        String quantity="1";
        String expectedTaxAmount = "60.05";
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        //create and post Invoice
        BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
        BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();
        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
        newInvoice.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newInvoice.activateRow(2);
        newInvoice.exitExpandTable();
        BusinessSalesInvoicePage postedInvoice = salesDocumentPostInvoice(newInvoice);
        String totalTax=postedInvoice.getTotalTaxAmount();
        assertEquals(totalTax, expectedTaxAmount);
        String postedInvoiceNo=postedInvoice.getCurrentSalesNumber();
        postedInvoice.clickBackAndSaveArrow();
        BusinessSalesReturnOrderListPage returnOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Return Orders");
        BusinessSalesReturnOrderPage newSalesReturnOrder = returnOrders.pageNavMenu.clickNew();
        fillInSalesReturnOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.salesEditNavMenu.clickPrepareButton();
        newSalesReturnOrder.selectPostedDocumentLinesToReverse();
        newSalesReturnOrder.enableShowReversibleLines();
        newSalesReturnOrder.enableReturnOriginalQuantity();
        newSalesReturnOrder.filterDocuments(postedInvoiceNo);
        newSalesReturnOrder.dialogBoxClickOk();
        //Verify tax in total tax field and vertex tax details
        String totalTaxAmount = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(totalTaxAmount, expectedTaxAmount);
        openVertexTaxDetailsWindow(newSalesReturnOrder);
        String actualTaxAmount = newSalesReturnOrder.getTaxAmount();
        newSalesReturnOrder.closeTaxDetailsDialog();
        assertEquals(actualTaxAmount,expectedTaxAmount);
    }

    /**
     * CDBC-1321
     * @Description - Verify XML For Ship To Address When Changing Address For Sales Return Order
     * @author Mario Saint-Fleur
     */
    @Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
    public void verifyXMLForShipToAddressWhenChangingAddressForSalesReturnOrderTest() {
        String customerCode = "TestingService_PA";
        String itemNumber = "1896-S";
        String expectedSalesTax="36.00";
        String updatedExpectedSalesTax = "48.15";
        String quantity = "2";
        List<String> expectedTaxAmounts = Arrays.asList("24.00", "12.00");
        List<String> updatedExpectedTaxAmounts = Arrays.asList("27.50", "0.60", "2.00", "2.00", "13.75", "0.30", "1.00", "1.00");

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderPage newSalesOrder=new BusinessSalesOrderPage(driver);
        BusinessSalesReturnOrderListPage returnOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Return Orders");
        BusinessSalesReturnOrderPage newSalesReturnOrder = returnOrders.pageNavMenu.clickNew();

        fillInSalesReturnOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, "200", 1);
        newSalesReturnOrder.activateRow(2);
        fillInItemsTableInfo("G/L Account", "10100", null, null, quantity, "100", 2);
        newSalesReturnOrder.exitExpandTable();

        //Verify tax in total tax field and vertex tax details
        String totalTaxAmount = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(totalTaxAmount, expectedSalesTax);
        openVertexTaxDetailsWindow(newSalesReturnOrder);
        List<String> actualTaxAmounts = newSalesReturnOrder.getMultipleTaxAmount("Sales");
        String orderNum = newSalesOrder.getCurrentSalesNumber();
        newSalesReturnOrder.closeTaxDetailsDialog();
        assertEquals(actualTaxAmounts,expectedTaxAmounts);

        //Change Ship-to address and verify taxes
        newSalesReturnOrder.selectSellToAddressCode("MN");
        recalculateTax(newSalesReturnOrder);
        openVertexTaxDetailsWindow(newSalesReturnOrder);
        actualTaxAmounts = newSalesReturnOrder.getMultipleTaxAmount("Sales");
        newSalesReturnOrder.closeTaxDetailsDialog();
        assertEquals(actualTaxAmounts,updatedExpectedTaxAmounts);
        totalTaxAmount = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(totalTaxAmount, updatedExpectedSalesTax);

        //Verify Response and confirm ship-to address and taxes
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(orderNum);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<Customer>\n" +
                "            <CustomerCode>C00680</CustomerCode>\n" +
                "            <Destination taxAreaId=\"240530650\">\n" +
                "              <StreetAddress1>2623 Logan Ave N</StreetAddress1>\n" +
                "              <City>Minneapolis</City>\n" +
                "              <MainDivision>MN</MainDivision>\n" +
                "              <PostalCode>55411-1955</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>"), "Address Validation Failed");
        assertTrue(xmlStr.contains("<TotalTax>48.15</TotalTax>"), "Tax Validation Failed Expected: " +  updatedExpectedSalesTax);
    }

    /**
     * CDBC-1350
     * @Description validate destination address in Replacement Sales Order and Sales Order
     * @author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void validateAddressReplacementSalesOrderTest() {
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity = "-1";
        String expectedTax="-18.00";
        String expectedOrderTax="18.00";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderPage newSalesOrder=new BusinessSalesOrderPage(driver);
        BusinessSalesReturnOrderListPage returnOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Return Orders");
        BusinessSalesReturnOrderPage newSalesReturnOrder = returnOrders.pageNavMenu.clickNew();

        fillInSalesReturnOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, "200", 1);
        newSalesReturnOrder.activateRow(2);
        fillInItemsTableInfo("G/L Account", "10100", null, null, quantity, "100", 2);
        newSalesReturnOrder.activateRow(3);
        newSalesReturnOrder.exitExpandTable();
        String actualTotalTaxAmount = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(actualTotalTaxAmount, expectedTax);

        //Verify tax and address in response
        String replacementOrderNo = newSalesOrder.getCurrentSalesNumber();
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(replacementOrderNo);
        adminPage.filterxml("Tax Calc Response");
        String xmlStrReplacementOrder = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStrReplacementOrder.contains("<Customer>\n" +
                "            <CustomerCode classCode=\"CUSTCLASS\">C00210</CustomerCode>\n" +
                "            <Destination taxAreaId=\"390290000\">\n" +
                "              <StreetAddress1>400 Simpson Dr</StreetAddress1>\n" +
                "              <City>Chester Springs</City>\n" +
                "              <MainDivision>PA</MainDivision>\n" +
                "              <PostalCode>19425-9546</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>"), "Address Validation Failed");
        assertTrue(xmlStrReplacementOrder.contains("<Product productClass=\"FURNITURE\">1896-S</Product>\n" +
                "          <Quantity>-1.0</Quantity>"), "Product Validation Failed");
        assertTrue(xmlStrReplacementOrder.contains("<Product productClass=\"NONTAXABLE\">10100</Product>\n" +
                "          <Quantity>-1.0</Quantity>"), "Product Validation Failed");
        assertTrue(xmlStrReplacementOrder.contains("<TotalTax>-18.0</TotalTax>"), "Tax Validation Failed");
        adminPage.clickBackAndSaveArrow();

        //Navigate to Functions in Actions Tab
        openFunctionsFromActions(newSalesReturnOrder);
        newSalesReturnOrder.salesEditNavMenu.selectMoveNegativeLines();

        newSalesReturnOrder.salesEditNavMenu.selectOrderDocType();
        newSalesReturnOrder.dialogBoxClickOk();
        newSalesReturnOrder.dialogBoxClickYes();
        String totalTaxAmount = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(totalTaxAmount, expectedOrderTax);
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.ORDER.value, "Statistics");
        String actualTax=newSalesOrder.getStatisticsTaxAmount();
        assertEquals(actualTax, expectedOrderTax);
        newSalesOrder.closeTaxDetailsDialog();
        String orderNo = newSalesOrder.getOrderNumber();

        //Verify tax and address in XML
        adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.filterDocuments(orderNo);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<Customer>\n" +
                "            <CustomerCode classCode=\"CUSTCLASS\">C00210</CustomerCode>\n" +
                "            <Destination taxAreaId=\"390290000\">\n" +
                "              <StreetAddress1>400 Simpson Dr</StreetAddress1>\n" +
                "              <City>Chester Springs</City>\n" +
                "              <MainDivision>PA</MainDivision>\n" +
                "              <PostalCode>19425-9546</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>"), "Address Validation Failed");
        assertTrue(xmlStr.contains("<Product productClass=\"FURNITURE\">1896-S</Product>\n" +
                "          <Quantity>1.0</Quantity>"), "Product Validation Failed");
        assertTrue(xmlStr.contains("<Product productClass=\"NONTAXABLE\">10100</Product>\n" +
                "          <Quantity>1.0</Quantity>"), "Product Validation Failed");
        assertTrue(xmlStr.contains("<TotalTax>18.0</TotalTax>"), "Tax Validation Failed");
    }

    /**
     * CDBC-1351
     * @Description Verifies if destination address is same for posted invoice and return order document line to reverse
     * @author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
    public void verifyAddressForPostedDocumentsLinesToReverseTest() {
        String customerCode = "test1234";
        String itemNumber="1896-S";
        String quantity="1";
        String expectedTaxAmount = "60.05";
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        //create and post Invoice
        BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
        BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();
        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
        newInvoice.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newInvoice.activateRow(2);
        newInvoice.exitExpandTable();
        BusinessSalesInvoicePage postedInvoice = salesDocumentPostInvoice(newInvoice);
        String totalTax=postedInvoice.getTotalTaxAmount();
        assertEquals(totalTax, expectedTaxAmount);

        String postedInvoiceNo=postedInvoice.getCurrentSalesNumber();

        //Verify tax and address in response
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(postedInvoiceNo);
        adminPage.filterxml("Invoice Response");
        String xmlStrReplacementOrder = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStrReplacementOrder.contains("<Customer>\n" +
                "            <CustomerCode classCode=\"CUSTCLASS\">C00210</CustomerCode>\n" +
                "            <Destination taxAreaId=\"390290000\">\n" +
                "              <StreetAddress1>400 Simpson Dr</StreetAddress1>\n" +
                "              <City>Chester Springs</City>\n" +
                "              <MainDivision>PA</MainDivision>\n" +
                "              <PostalCode>19425-9546</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>"), "Address Validation Failed");
        assertTrue(xmlStrReplacementOrder.contains("<TotalTax>60.05</TotalTax>"), "Tax Validation Failed");
        adminPage.clickBackAndSaveArrow();

        postedInvoice.clickBackAndSaveArrow();

        BusinessSalesReturnOrderListPage returnOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Return Orders");
        BusinessSalesReturnOrderPage newSalesReturnOrder = returnOrders.pageNavMenu.clickNew();
        fillInSalesReturnOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.salesEditNavMenu.clickPrepareButton();
        newSalesReturnOrder.selectPostedDocumentLinesToReverse();
        newSalesReturnOrder.enableShowReversibleLines();
        newSalesReturnOrder.enableReturnOriginalQuantity();
        newSalesReturnOrder.filterDocuments(postedInvoiceNo);
        newSalesReturnOrder.dialogBoxClickOk();
        //Verify tax in total tax field and vertex tax details
        String totalTaxAmount = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(totalTaxAmount, expectedTaxAmount);
        openVertexTaxDetailsWindow(newSalesReturnOrder);
        String actualTaxAmount = newSalesReturnOrder.getTaxAmount();
        newSalesReturnOrder.closeTaxDetailsDialog();
        assertEquals(actualTaxAmount,expectedTaxAmount);

        String orderNo = newSalesReturnOrder.getOrderNumber();

        //Verify tax and address in XML
        adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.filterDocuments(orderNo);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<Customer>\n" +
                "            <CustomerCode classCode=\"CUSTCLASS\">C00210</CustomerCode>\n" +
                "            <Destination taxAreaId=\"390290000\">\n" +
                "              <StreetAddress1>400 Simpson Dr</StreetAddress1>\n" +
                "              <City>Chester Springs</City>\n" +
                "              <MainDivision>PA</MainDivision>\n" +
                "              <PostalCode>19425-9546</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>\n" +
                "          </Customer>"), "Address Validation Failed");
        assertTrue(xmlStr.contains("<TotalTax>60.05</TotalTax>"), "Tax Validation Failed");
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
