package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.apache.commons.collections.list.AbstractLinkedList;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Create Sales Order
 *
 * @author Vishwa ssalisbury
 */
@Listeners(TestRerunListener.class)
public class DFinanceCreateNewSalesOrderSecondTests extends DFinanceBaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setupTest( )
    {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage;

        //Enable Accounts Receivable
        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(tax);
        homePage.collapseAll();
        homePage.expandModuleCategory(setup);
        homePage.expandModuleCategory(vertex);
        settingsPage = homePage.selectModuleTabLink(taxParametersData);
        settingsPage.toggleAccountsReceivable(true);
    }

    /**
     * creates a new sales order With Different Ship And Bill Address, validates tax calculation, and then deletes that new
     * @author Vishwa
     * sales order CD365-182 CD365-1379
     */
    @Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void SalesOrderWithDifferentShipAndBillTest( )
    {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        //================Data Declaration ===========================
        final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();

        final String itemNumber = "A0001";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "1000";

        //================script implementation=======================
        //Navigate to  All Sales Orders page
        DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        createSalesOrder(allSalesOrdersPage, DelawareBillingCustomerAccount);

        //Click on "Header" option
        allSalesOrdersPage.openHeaderTab();

        //lets the new sales order be identified & eliminated at the end of the test
        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

        //Go to "Set up" section
        allSalesOrdersPage.expandSetupHeaderSection();

        //Set "Sales tax group" -- 'VertexAR'
        allSalesOrdersPage.setSalesOrderTaxGroup(vertexAR);

        //Click on "Lines" option
        allSalesOrdersPage.openLinesTab();

        //Add line item
        allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "1", unitPrice, 0);

        //Click on "Sales Tax" option
        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();

        //Verify the "Total calculated sales tax amount" value
        String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        //fixme FAILING with actual value "100.00"
        assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("60.00"),
                "'Total calculated sales tax amount' value is not expected");

        //Verify the "Total actual sales tax amount" value
        String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("60.00"),
                "'Total actual sales tax amount' value is not expected");

        //Click on "OK" button
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        //TODO move to after method?
        //Navigate to  All Sales Orders page
        DFinanceAllSalesOrdersPage postTestAllSalesOrdersPage = allSalesOrdersPage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        //In "ALL SALES ORDERS" search filter enter the above created Sales order number and then search by "Sales Order Number"
        postTestAllSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);

        //Click on "Sales Order Number" resulted in the Search Result
        postTestAllSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

        //Click on "Delete" option from the menu
        postTestAllSalesOrdersPage.clickDeleteButton();

        //Click on "Yes" option from the pop-up
        postTestAllSalesOrdersPage.agreeToConfirmationPopup();
    }

    /**
     * creates a new sales order With shipping on Multiple Line Item, validates tax calculation, and then deletes that new
     * @author Vishwa
     * sales order CD365-183
     */

    @Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void SalesOrderSecondLineItemShippingChargeTest( )
    {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        //================Data Declaration ===========================
        final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();

        //================script implementation=======================
        //Navigate to  All Sales Orders page
        DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        createSalesOrder(allSalesOrdersPage, PittsburghCustomerAccount);

        //Click on "Header" option
        allSalesOrdersPage.openHeaderTab();

        //lets the new sales order be identified & eliminated at the end of the test
        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

        //Go to "Set up" section
        allSalesOrdersPage.expandSetupHeaderSection();

        //Set "Sales tax group" -- 'VertexAR'
        allSalesOrdersPage.setSalesOrderTaxGroup(vertexAR);

        //Click on "Lines" option
        allSalesOrdersPage.openLinesTab();

        // add multiple line item to the SO
        allSalesOrdersSecondPage.fillFirstItemsInfo("A0001","1", "11", "1", "500", 0);
        allSalesOrdersSecondPage.fillItemsInfo("A0002","1", "12", "3", "1000", 1);

        //Click on Financials and select Maintain charges
        allSalesOrdersPage.clickOnFinancials();
        allSalesOrdersPage.selectMaintainCharges();
        allSalesOrdersPage.addCharges("FREIGHT", false,"Fixed","10","VertexAR", "All");

        // close Charges page
        allSalesOrdersPage.closeCharges();

        //Click on "Sales Tax" option
        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();

        //Verify the "Total actual sales tax amount" value
        String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("210.60"),
                "'Total actual sales tax amount' value is not expected");

        //Click on "OK" button
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        //TODO move to after method?
        //Navigate to  All Sales Orders page
        DFinanceAllSalesOrdersPage postTestAllSalesOrdersPage = allSalesOrdersPage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        //In "ALL SALES ORDERS" search filter enter the above created Sales order number and then search by "Sales Order Number"
        postTestAllSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);

        //Click on "Sales Order Number" resulted in the Search Result
        postTestAllSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

        //Click on "Delete" option from the menu
        postTestAllSalesOrdersPage.clickDeleteButton();

        //Click on "Yes" option from the pop-up
        postTestAllSalesOrdersPage.agreeToConfirmationPopup();
    }

    /**
     * creates a new sales order With no state tax only city tax, validates tax calculation, and then deletes that new
     * @author Vishwa
     * sales order CD365-187
     */
    @Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void SalesOrderWithNoStateTaxOnlyCityTaxTest( )
    {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        //================Data Declaration ===========================
        final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();

        //================script implementation=======================
        //Navigate to  All Sales Orders page
        DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        createSalesOrder(allSalesOrdersPage, AlaskaDeliveryCustomerAccount);

        //Click on "Header" option
        allSalesOrdersPage.openHeaderTab();

        //lets the new sales order be identified & eliminated at the end of the test
        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

        //Go to "Set up" section
        allSalesOrdersPage.expandSetupHeaderSection();

        //Set "Sales tax group" -- 'VertexAR'
        allSalesOrdersPage.setSalesOrderTaxGroup(vertexAR);

        //Click on "Lines" option
        allSalesOrdersPage.openLinesTab();

        // add multiple line item to the SO
        allSalesOrdersSecondPage.fillFirstItemsInfo("1000","2", "21", "1", "500", 0);

        //Click on "Sales Tax" option
        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();

        //Verify the "Total calculated sales tax amount" value
        String calculatedStateSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedStateSalesTaxAmount();
        //fixme FAILING with actual value "100.00"
        assertTrue(calculatedStateSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total calculated sales tax amount' value is not expected");

        //Verify the "Total calculated sales tax amount" value
        String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        //fixme FAILING with actual value "100.00"
        assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("25.00"),
                "'Total calculated sales tax amount' value is not expected");

        //Verify the "Total actual sales tax amount" value
        String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("25.00"),
                "'Total actual sales tax amount' value is not expected");

        //Click on "OK" button
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        //TODO move to after method?
        //Navigate to  All Sales Orders page
        DFinanceAllSalesOrdersPage postTestAllSalesOrdersPage = allSalesOrdersPage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        //In "ALL SALES ORDERS" search filter enter the above created Sales order number and then search by "Sales Order Number"
        postTestAllSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);

        //Click on "Sales Order Number" resulted in the Search Result
        postTestAllSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

        //Click on "Delete" option from the menu
        postTestAllSalesOrdersPage.clickDeleteButton();

        //Click on "Yes" option from the pop-up
        postTestAllSalesOrdersPage.agreeToConfirmationPopup();
    }

    /**
     * creates a new sales order With no state tax, validates tax calculation, and then deletes that new
     * @author Vishwa
     * sales order CD365-186
     */
    @Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void SalesOrderWithNoStateTaxTest( )
    {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        //================Data Declaration ===========================
        final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();

        //================script implementation=======================
        //Navigate to  All Sales Orders page
        DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        createSalesOrder(allSalesOrdersPage, DelawareDeliveryCustomerAccount);

        //Click on "Header" option
        allSalesOrdersPage.openHeaderTab();

        //lets the new sales order be identified & eliminated at the end of the test
        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

        //Go to "Set up" section
        allSalesOrdersPage.expandSetupHeaderSection();

        //Set "Sales tax group" -- 'VertexAR'
        allSalesOrdersPage.setSalesOrderTaxGroup(vertexAR);

        //Click on "Lines" option
        allSalesOrdersPage.openLinesTab();

        // add multiple line item to the SO
        allSalesOrdersSecondPage.fillFirstItemsInfo("1000","1", "12", "1", "500", 0);
        allSalesOrdersSecondPage.fillItemsInfo("A0001", "1", "12", "1", "1000", 1);

        //Click on "Sales Tax" option
        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();

        //Verify the "Total calculated sales tax amount" value
        String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        //fixme FAILING with actual value "100.00"
        assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total calculated sales tax amount' value is not expected");

        //Verify the "Total actual sales tax amount" value
        String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total actual sales tax amount' value is not expected");

        //Click on "OK" button
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        //TODO move to after method?
        //Navigate to  All Sales Orders page
        DFinanceAllSalesOrdersPage postTestAllSalesOrdersPage = allSalesOrdersPage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        //In "ALL SALES ORDERS" search filter enter the above created Sales order number and then search by "Sales Order Number"
        postTestAllSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);

        //Click on "Sales Order Number" resulted in the Search Result
        postTestAllSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

        //Click on "Delete" option from the menu
        postTestAllSalesOrdersPage.clickDeleteButton();

        //Click on "Yes" option from the pop-up
        postTestAllSalesOrdersPage.agreeToConfirmationPopup();
    }

    /**
     * creates a new sales order With Multiple Line Item and two different delivery address validates tax calculation, and then deletes that new
     * @author Vishwa
     * sales order CD365-184
     */
    @Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void SalesOrderWithMultiLineTwoDifferentLocationTest( )
    {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        //================Data Declaration ===========================
        final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();

        //================script implementation=======================
        //Navigate to  All Sales Orders page
        DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        createSalesOrder(allSalesOrdersPage, PittsburghCustomerAccount);

        //Click on "Header" option
        allSalesOrdersPage.openHeaderTab();

        //lets the new sales order be identified & eliminated at the end of the test
        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

        //Go to "Set up" section
        allSalesOrdersPage.expandSetupHeaderSection();

        //Set "Sales tax group" -- 'VertexAR'
        allSalesOrdersPage.setSalesOrderTaxGroup(vertexAR);

        //Click on "Lines" option
        allSalesOrdersPage.openLinesTab();

        // add multiple line item to the SO
        allSalesOrdersSecondPage.fillFirstItemsInfo("1000","1", "12", "1", "500", 0);
        allSalesOrdersSecondPage.fillItemsInfo("A0001", "1", "12", "1", "1000", 1);

        // Update delivery address for second line item
        allSalesOrdersSecondPage.updateDeliveryAddress("TX");

        //Click on "Sales Tax" option
        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();

        //Verify the "Total calculated sales tax amount" value

        String actualAmount = allSalesOrdersPage.salesTaxCalculator.getActualLineItemSalesTaxAmount(1);
        assertEquals(actualAmount, "30.00");
        actualAmount = allSalesOrdersPage.salesTaxCalculator.getActualLineItemSalesTaxAmount(2);
        assertEquals(actualAmount, "62.50");
        actualAmount = allSalesOrdersPage.salesTaxCalculator.getActualLineItemSalesTaxAmount(3);
        assertEquals(actualAmount, "10.00");
        actualAmount = allSalesOrdersPage.salesTaxCalculator.getActualLineItemSalesTaxAmount(4);
        assertEquals(actualAmount, "10.00");

        //Click on "OK" button
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        //TODO move to after method?
        //Navigate to  All Sales Orders page
        DFinanceAllSalesOrdersPage postTestAllSalesOrdersPage = allSalesOrdersPage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        //In "ALL SALES ORDERS" search filter enter the above created Sales order number and then search by "Sales Order Number"
        postTestAllSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);

        //Click on "Sales Order Number" resulted in the Search Result
        postTestAllSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

        //Click on "Delete" option from the menu
        postTestAllSalesOrdersPage.clickDeleteButton();

        //Click on "Yes" option from the pop-up
        postTestAllSalesOrdersPage.agreeToConfirmationPopup();
    }

    /**
     * @TestCase CD365-181
     * @Description creates a new sales order With Invoice, validates tax calculation, then changes posting date for invoicing,
     *              then deletes that sales order, then validates tax date for both invoice and sales order
     * @author Mario Saint-Fleur
     */

    @Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void SalesOrderInvoiceTest( )
    {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

        //================Data Declaration ===========================
        final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();
        final String Quantity = "All";

        //================script implementation=======================
        //Navigate to  All Sales Orders page
        DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        createSalesOrder(allSalesOrdersPage, PittsburghCustomerAccount);

        //Click on "Header" option
        allSalesOrdersPage.openHeaderTab();

        //lets the new sales order be identified & eliminated at the end of the test
        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

        //Go to "Set up" section
        allSalesOrdersPage.expandSetupHeaderSection();

        //Set "Sales tax group" -- 'VertexAR'
        allSalesOrdersPage.setSalesOrderTaxGroup(vertexAR);

        //Click on "Lines" option
        allSalesOrdersPage.openLinesTab();

        // add multiple line item to the SO
        allSalesOrdersSecondPage.fillFirstItemsInfo("1000","2", "22", "1", "1000", 0);

        //Click on Financials and select Maintain charges
        allSalesOrdersPage.clickOnFinancials();
        allSalesOrdersPage.selectMaintainCharges();
        allSalesOrdersPage.addCharges("FREIGHT", false,"Fixed","50","VertexAR", "All");
        allSalesOrdersPage.closeCharges();

        //Click on "Sales Tax" option
        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();

        //Verify the "Total calculated sales tax amount" value
        String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        //fixme FAILING with actual value "100.00"
        assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("63.00"),
                "'Total calculated sales tax amount' value is not expected");

        //Verify the "Total actual sales tax amount" value
        String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("63.00"),
                "'Total actual sales tax amount' value is not expected");

        //Click on "OK" button
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        //Click on "Invoice" tab
        allSalesOrdersSecondPage.clickOnInvoiceTab();
        allSalesOrdersSecondPage.generateInvoice();

        //Change Invoice Date and Select Quantity Parameter to "All"
        allSalesOrdersPage.selectQuantityValueFromDialog("All");
        allSalesOrdersSecondPage.enterInvoiceDate();
        allSalesOrdersSecondPage.clickOnOk();
        allSalesOrdersPage.clickOkForPostWithoutPrinting();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, "Invoice Confirmation failed");

        //Verify the "Total sales invoice amount" value
        allSalesOrdersSecondPage.clickOnInvoice();
        String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");
        allSalesOrdersSecondPage.clickOnPostedSalesTax();
        String totalSalesInvoiceAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        assertTrue(totalSalesInvoiceAmount.equalsIgnoreCase("63.00"),
                "'Total sales invoice amount' value is not expected");

        //TODO move to after method?
        //Navigate to  All Sales Orders page
        settingsPage.navigateToDashboardPage();
        DFinanceAllSalesOrdersPage postTestAllSalesOrdersPage = allSalesOrdersPage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        //In "ALL SALES ORDERS" search filter enter the above created Sales order number and then search by "Sales Order Number"
        postTestAllSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);
        //Click on "Sales Order Number" resulted in the Search Result
        postTestAllSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

        //Click on "Delete" option from the menu
        postTestAllSalesOrdersPage.clickDeleteButton();

        //Click on "Yes" option from the pop-up
        postTestAllSalesOrdersPage.agreeToConfirmationPopup();

        //Navigate to XML inquiry
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Select Invoice Request
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("locationCode=\"22\" lineItemNumber=\"1\" taxDate=\""+getDesiredDate(2)+"\""));

        //Select Sales Order Request
        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.clickOnFirstRequest();
        request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("locationCode=\"22\" lineItemNumber=\"1\" taxDate=\""+getDesiredDate(2)+"\""));
    }

    /**
     * creates a new sales order With Invoice and exempted Product Tax, validates tax calculation, and then deletes that new
     * @author Vishwa
     * sales order CD365-163
     */

    @Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void SalesOrderExemptedProductInvoiceTest( )
    {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        //================Data Declaration ===========================
        final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();
        final String Quantity = "All";

        //================script implementation=======================
        //Navigate to  All Sales Orders page
        DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        createSalesOrder(allSalesOrdersPage, PittsburghCustomerAccount);

        //Click on "Header" option
        allSalesOrdersPage.openHeaderTab();

        //lets the new sales order be identified & eliminated at the end of the test
        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

        //Go to "Set up" section
        allSalesOrdersPage.expandSetupHeaderSection();

        //Set "Sales tax group" -- 'VertexAR'
        allSalesOrdersPage.setSalesOrderTaxGroup(vertexAR);

        //Click on "Lines" option
        allSalesOrdersPage.openLinesTab();

        // add line item to the SO
        allSalesOrdersSecondPage.fillFirstItemsInfo("T0003","2", "22", "5", "1000", 0);

        //Click on "Sales Tax" option
        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();


        //Verify the "Total calculated sales tax amount" value
        String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        //fixme FAILING with actual value "100.00"
        assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total calculated sales tax amount' value is not expected");

        //Verify the "Total actual sales tax amount" value
        String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total actual sales tax amount' value is not expected");

        //Click on "OK" button
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        //TODO move to after method?
        //Navigate to  All Sales Orders page
        DFinanceAllSalesOrdersPage postTestAllSalesOrdersPage = allSalesOrdersPage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        //In "ALL SALES ORDERS" search filter enter the above created Sales order number and then search by "Sales Order Number"
        postTestAllSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);

        //Click on "Sales Order Number" resulted in the Search Result
        postTestAllSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

        //Click on "Delete" option from the menu
        postTestAllSalesOrdersPage.clickDeleteButton();

        //Click on "Yes" option from the pop-up
        postTestAllSalesOrdersPage.agreeToConfirmationPopup();
    }

    /**
     * @TestCase CD365-1689
     * @Description creates a new sales order With Invoice, using created date
     * @author Mario Saint-Fleur
     */

    @Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void createSalesOrderWithInvoicingUsingCreatedDateTest( )
    {
        final String createdDate = "Created date";
        final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();
        final String Quantity = "All";
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

        //Changing to created date
        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(tax);
        homePage.collapseAll();
        homePage.expandModuleCategory(setup);
        homePage.expandModuleCategory(vertex);
        settingsPage = homePage.selectModuleTabLink(taxParametersData);
        assertTrue(settingsPage.verifySalesOrderQuoteCalcParameter(),"Created date parameter isn't present on UI");
        settingsPage.changeSalesOrderQuoteDate(createdDate);
        settingsPage.clickOnSaveButton();

        //Navigate to  All Sales Orders page
        DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        createSalesOrder(allSalesOrdersPage, PittsburghCustomerAccount);
        allSalesOrdersPage.openHeaderTab();
        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
        allSalesOrdersPage.expandSetupHeaderSection();
        allSalesOrdersPage.setSalesOrderTaxGroup(vertexAR);
        allSalesOrdersPage.openLinesTab();

        //Add line item
        allSalesOrdersSecondPage.fillFirstItemsInfo("1000","2", "22", "1", "1000", 0);

        //Click on Financials and select Maintain charges
        allSalesOrdersPage.clickOnFinancials();
        allSalesOrdersPage.selectMaintainCharges();
        allSalesOrdersPage.addCharges("FREIGHT", false,"Fixed","50","VertexAR", "All");
        allSalesOrdersPage.closeCharges();

        //Click on "Sales Tax" option
        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();
        String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("63.00"),
                "'Total calculated sales tax amount' value is not expected");
        String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("63.00"),
                "'Total actual sales tax amount' value is not expected");
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        //Navigate to Invoice Tab and Select Quantity Parameter to "All"
        allSalesOrdersSecondPage.clickOnInvoiceTab();
        allSalesOrdersSecondPage.generateInvoice();
        allSalesOrdersPage.selectQuantityValueFromDialog("All");
        allSalesOrdersSecondPage.clickOnOk();
        allSalesOrdersPage.clickOkForPostWithoutPrinting();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, "Invoice Confirmation failed");

        //Verify the "Total sales invoice amount" value
        allSalesOrdersSecondPage.clickOnInvoice();
        String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");
        allSalesOrdersSecondPage.clickOnPostedSalesTax();
        String totalSalesInvoiceAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        assertTrue(totalSalesInvoiceAmount.equalsIgnoreCase("63.00"),
                "'Total sales invoice amount' value is not expected");

        //Navigate to XML inquiry
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Select Invoice Request and verify taxDate
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("locationCode=\"22\" lineItemNumber=\"1\" taxDate=\""+getDesiredDate(0)+"\""));

        //Select Sales Order Request and verify taxDate
        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.clickOnFirstRequest();
        request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("locationCode=\"22\" lineItemNumber=\"1\" taxDate=\""+getDesiredDate(0)+"\""));
    }
}
