package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;
@Listeners(TestRerunListener.class)
public class DFinanceTaxRateChangeTests extends DFinanceBaseTest {


    /**
     * This test Verify new date parameter and also verifies correct tax date (Current Date) is populating in "Tax Date"
     * for sales order and Invoice
     * CD365-798
     */
    @Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void taxRateChangeWithCurrentDateTest( )
    {
        // Test Data
        final String tax = DFinanceLeftMenuNames.TAX.getData();
        final String setup = DFinanceLeftMenuNames.SETUP.getData();
        final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
        final String taxParametersData = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
        final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();
        final String itemNumber = "1000";
        final String site = "2";
        final String warehouse = "22";
        final String unitPrice = "899";
        final String quantity = "1";
        final String quoteDate = "Current";

        // Object assignment
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage;
        DFinanceXMLInquiryPage xMLInqueryPage = new DFinanceXMLInquiryPage(driver);

        //Changing Quote Date
        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(tax);
        homePage.collapseAll();
        homePage.expandModuleCategory(setup);
        homePage.expandModuleCategory(vertex);
        settingsPage = homePage.selectModuleTabLink(taxParametersData);
        assertTrue(settingsPage.verifySalesOrderQuoteCalcParameter(),"Quote Date parameter isn't present on UI");
        settingsPage.changeSalesOrderQuoteDate(quoteDate);
        settingsPage.clickOnSaveButton();


        //Navigate to  All Sales Orders page
        DFinanceAllSalesOrdersPage salesOrderPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        //Click on "New" option
        settingsPage.clickOnNewButton();

        //Enter "Customer account" -- 'US-004'
        salesOrderPage.expandCustomerSection();
        salesOrderPage.setCustomerAccount(defaultCustomerAccount);

        //Click on "OK"  button on "Create Sales Order" screen
        salesOrderPage.finishCreatingSalesOrder();

        //Click on "Header" option
        salesOrderPage.openHeaderTab();

        String salesOrderNumber = salesOrderPage.getSalesOrderNumber();
        VertexLogger.log(String.format("Sales Order# %s", salesOrderNumber));

        //Set "Sales tax group" -- 'VertexAR'
        salesOrderPage.setSalesOrderTaxGroup(vertexAR);

        // add line item
        salesOrderPage.addItemLine(itemNumber, quantity, site, warehouse, unitPrice);

        //Click on "Sales Tax" option
        salesOrderPage.clickOnTab(salesOrderPage.sellTabName);
        salesOrderPage.openSalesTaxCalculation();

        //Verify the "Total calculated sales tax amount" value
        String calculatedSalesTaxAmount = salesOrderPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

        assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("71.92"),
                "'Total calculated sales tax amount' value is not expected");

        //Click on "OK" button
        salesOrderPage.clickOk();

        //Navigate to  XML inquiry
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Select the Correct Response from the list
        xMLInqueryPage.getDocumentID(salesOrderNumber);
        xMLInqueryPage.clickOnFirstRequest();

        //Validate that address is present in the log
        String response = xMLInqueryPage.getLogRequestValue();
        assertTrue(response.contains("taxDate=\""+getDesiredDate(0)+"\""));
    }

    /**
     * This test Verify new date parameter and also verifies correct tax date (Receipt Date) is populating in "Tax Date"
     * for sales order
     * CD365-804
     */
    @Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void taxRateChangeWithConfirmedReceiptDateTest( )
    {
        // Test Data
        final String tax = DFinanceLeftMenuNames.TAX.getData();
        final String setup = DFinanceLeftMenuNames.SETUP.getData();
        final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
        final String taxParametersData = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
        final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();
        final String itemNumber = "1000";
        final String site = "2";
        final String warehouse = "22";
        final String unitPrice = "899";
        final String quantity = "1";
        final String quoteDate = "receipt";
        final int daysToAdd = 2;

        // Object assignment
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage;
        DFinanceXMLInquiryPage xMLInqueryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        //Changing Quote Date
        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(tax);
        homePage.collapseAll();
        homePage.expandModuleCategory(setup);
        homePage.expandModuleCategory(vertex);
        settingsPage = homePage.selectModuleTabLink(taxParametersData);
        assertTrue(settingsPage.verifySalesOrderQuoteCalcParameter(),"Quote Date parameter isn't present on UI");
        settingsPage.changeSalesOrderQuoteDate(quoteDate);
        settingsPage.clickOnSaveButton();


        //Navigate to  All Sales Orders page
        DFinanceAllSalesOrdersPage salesOrderPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        //Click on "New" option
        settingsPage.clickOnNewButton();

        //Enter "Customer account" -- 'US-004'
        salesOrderPage.expandCustomerSection();
        salesOrderPage.setCustomerAccount(defaultCustomerAccount);

        //Click on "OK"  button on "Create Sales Order" screen
        salesOrderPage.finishCreatingSalesOrder();

        //Click on "Header" option
        salesOrderPage.openHeaderTab();

        String salesOrderNumber = salesOrderPage.getSalesOrderNumber();
        VertexLogger.log(String.format("Sales Order# %s", salesOrderNumber));

        //Set "Sales tax group" -- 'VertexAR'
        salesOrderPage.setSalesOrderTaxGroup(vertexAR);
        salesOrderPage.setConfirmedReceiptDate(daysToAdd);

        // add line item
        salesOrderPage.addItemLine(itemNumber, quantity, site, warehouse, unitPrice);

        //Click on "Sales Tax" option
        salesOrderPage.clickOnTab(salesOrderPage.sellTabName);
        salesOrderPage.openSalesTaxCalculation();

        //Verify the "Total calculated sales tax amount" value
        String calculatedSalesTaxAmount = salesOrderPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

        assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("71.92"),
                "'Total calculated sales tax amount' value is not expected");

        //Click on "OK" button
        salesOrderPage.clickOk();

        allSalesOrdersSecondPage.clickOnDelivery();
        String receiptDate = salesOrderPage.getConfirmedShipOrReceiptDate("Receipt");

        //Navigate to  XML inquiry
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Select the Correct Response from the list
        xMLInqueryPage.getDocumentID(salesOrderNumber);
        xMLInqueryPage.clickOnFirstRequest();

        //Validate that address is present in the log
        String request = xMLInqueryPage.getLogRequestValue();
        assertTrue(request.contains("taxDate=\""+receiptDate+"\""));
    }

    /**
     * This test Verify new date parameter and also verifies correct tax date (Ship Date) is populating in "Tax Date"
     * for sales order
     * CD365-805
     */
    @Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void taxRateChangeWithConfirmedShipDateTest( ) {
        // Test Data
        final String tax = DFinanceLeftMenuNames.TAX.getData();
        final String setup = DFinanceLeftMenuNames.SETUP.getData();
        final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
        final String taxParametersData = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
        final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();
        final String itemNumber = "1000";
        final String site = "2";
        final String warehouse = "22";
        final String unitPrice = "899";
        final String quantity = "1";
        final String quoteDate = "ship";
        final int daysToAdd = 5;

        // Object assignment
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage;
        DFinanceXMLInquiryPage xMLInqueryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        //Changing Quote Date
        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(tax);
        homePage.collapseAll();
        homePage.expandModuleCategory(setup);
        homePage.expandModuleCategory(vertex);
        settingsPage = homePage.selectModuleTabLink(taxParametersData);
        assertTrue(settingsPage.verifySalesOrderQuoteCalcParameter(),"Quote Date parameter isn't present on UI");
        settingsPage.changeSalesOrderQuoteDate(quoteDate);
        settingsPage.clickOnSaveButton();


        //Navigate to  All Sales Orders page
        DFinanceAllSalesOrdersPage salesOrderPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        //Click on "New" option
        settingsPage.clickOnNewButton();

        //Enter "Customer account" -- 'US-004'
        salesOrderPage.expandCustomerSection();
        salesOrderPage.setCustomerAccount(defaultCustomerAccount);

        //Click on "OK"  button on "Create Sales Order" screen
        salesOrderPage.finishCreatingSalesOrder();

        //Click on "Header" option
        salesOrderPage.openHeaderTab();

        String salesOrderNumber = salesOrderPage.getSalesOrderNumber();
        VertexLogger.log(String.format("Sales Order# %s", salesOrderNumber));

        //Set "Sales tax group" -- 'VertexAR'
        salesOrderPage.setSalesOrderTaxGroup(vertexAR);
        salesOrderPage.setConfirmedReceiptDate(daysToAdd);

        // add line item
        salesOrderPage.addItemLine(itemNumber, quantity, site, warehouse, unitPrice);

        //Click on "Sales Tax" option
        salesOrderPage.clickOnTab(salesOrderPage.sellTabName);
        salesOrderPage.openSalesTaxCalculation();
        //salesOrderPage.clickOverwriteOkButton();

        //Verify the "Total calculated sales tax amount" value
        String calculatedSalesTaxAmount = salesOrderPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

        assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("71.92"),
                "'Total calculated sales tax amount' value is not expected");

        //Click on "OK" button
        salesOrderPage.clickOk();

        allSalesOrdersSecondPage.clickOnDelivery();
        String shippingDate = salesOrderPage.getConfirmedShipOrReceiptDate("Shipping");

        //Navigate to  XML inquiry
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Select the Correct Response from the list
        xMLInqueryPage.getDocumentID(salesOrderNumber);
        xMLInqueryPage.clickOnFirstRequest();

        //Validate that address is present in the log
        String request = xMLInqueryPage.getLogRequestValue();
        assertTrue(request.contains("taxDate=\""+shippingDate+"\""));
    }
}
