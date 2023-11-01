package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.connectors.dynamics365.finance.dialogs.DFinanceTransactionSalesTaxDialog;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author Mario Saint-Fleur
 * All tests related to POS Transactions and Statement
 */
@Listeners(TestRerunListener.class)
public class DFinancePOSCustomerOrderAndStatementTests extends DFinanceBaseTest{

    public class DRetailTransactionClass extends DRetailBaseTest {
        /**
         * @TestCase CD365-1601
         * @Description - Create a POS order using Customer Order
         * @Author Mario Saint-Fleur
         */
        @Test(groups = {"FO_Integration", "customerOrder"}, retryAnalyzer = TestRerun.class, priority = 12)
        public void createCustomerOrderPOSTest() {
            DRetailHomePage homePage = new DRetailHomePage(driver);
            DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);

            String expectedAmount = "8.22";

            //Navigate to the Transaction Screen
            allRetailSalesOrderPage.transactionButton();
            allRetailSalesOrderPage.addCustomer("Test Auto");
            allRetailSalesOrderPage.addProduct();
            allRetailSalesOrderPage.addSunglass();
            allRetailSalesOrderPage.selectPinkSunglass();
            allRetailSalesOrderPage.addItemButton();

            //Create Customer Order, Select Standard Shipping, and Pay Cash
            allRetailSalesOrderPage.transactionButton();
            allRetailSalesOrderPage.clickOrders();
            allRetailSalesOrderPage.shipSelectedStandardShipping();

            //Verify total tax
            String actualTaxAmount = allRetailSalesOrderPage.taxValidation();
            assertTrue(actualTaxAmount.contains(expectedAmount), "Actual tax amount is not equal to the expected tax amount: " + expectedAmount);

            //Pay Cash
            allRetailSalesOrderPage.selectPayCash();
            allRetailSalesOrderPage.cashAccepted();
            allRetailSalesOrderPage.closeButton();

        }
    }

    /**
     * @TestCase CD365-1562
     * @Description - Verify tax for POS order when creating a statement when invoiced
     * @Author Mario Saint-Fleur
     */
    @Test(groups = {"FO_Integration"}, retryAnalyzer = TestRerun.class, dependsOnGroups = {"customerOrder"}, priority = 13)
    public void verifyTaxForPOSOrderWhenCreatingAStatementTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        final String jobName = "P-0001";
        final int batchJobWaitInMS = 120000;

        final Double expectedTaxAmount = 8.22;

        settingsPage.selectCompany("USRT");

        //Navigate to Distribution schedule and start job
        DFinanceDistributionSchedulePage distributionSchedulePage = homePage.navigateToPage(DFinanceLeftMenuModule.RETAIL_AND_COMMERCE, DFinanceModulePanelCategory.IT,
                DFinanceModulePanelLink.DISTRIBUTION_SCHEDULE);
        distributionSchedulePage.initiateTheJob(jobName);
        distributionSchedulePage.clickOK();
        distributionSchedulePage.waitStaticallyForBatchJobToRun(batchJobWaitInMS);

        //Navigate to Validate Store Transaction
        homePage.navigateToPage(DFinanceLeftMenuModule.RETAIL_AND_COMMERCE, DFinanceModulePanelCategory.RETAIL_AND_COMMERCE_IT,
                DFinanceModulePanelCategory.POS_POSTING, DFinanceModulePanelLink.VALIDATE_STORE_TRANSACTIONS);

        //Validate the Retail store
        allSalesOrdersPage.validateRetailStore("Contoso Retail");
        allSalesOrdersPage.clickOnOKBtn();

        homePage.navigateToPage(DFinanceLeftMenuModule.RETAIL_AND_COMMERCE, DFinanceModulePanelCategory.CHANNELS,
                DFinanceModulePanelCategory.STORES, DFinanceModulePanelLink.STATEMENTS);

        //Create a New Transaction and Post Statement
        allSalesOrdersPage.clickNewOrderAndSelectNewStatementType("NewTransactionalStatement");
        allSalesOrdersPage.clickOnOKBtn();
        allSalesOrdersPage.clickPostStatement();

        //Navigate to All Sales Order
         allSalesOrdersPage= homePage.navigateToPage(DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        allSalesOrdersPage.searchSalesOrderByCustomerName("Test Auto");
        allSalesOrdersSecondPage.applyFilterOnStatus("Open order");
        allSalesOrdersSecondPage.clickOnLatestOrderCreated();

        ///Validate sales tax amount
        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();
        String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        //Navigate to posted sales tax and verify tax
        allSalesOrdersSecondPage.clickOnInvoiceTab();
        allSalesOrdersSecondPage.generateInvoice();
        allSalesOrdersPage.clickOnOKBtn();
        allSalesOrdersPage.clickOnOKPopUp();
        allSalesOrdersSecondPage.clickOnInvoiceTab();
        allSalesOrdersSecondPage.journalInvoice();
        String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");
        allSalesOrdersSecondPage.clickOnPostedSalesTax();
        DFinanceTransactionSalesTaxDialog transactionSalesTaxDialog = new DFinanceTransactionSalesTaxDialog(driver, allSalesOrdersPage);
        String postedSalesTaxAmount = transactionSalesTaxDialog.getCalculatedSalesTaxAmount();
        assertTrue(postedSalesTaxAmount.equalsIgnoreCase("8.22"),
                "'Total calculated sales tax amount' value is not expected");

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify Total Tax
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>8.22</TotalTax>"));
    }
}
