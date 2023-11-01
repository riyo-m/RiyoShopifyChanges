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
 * All tests related to Statement and Transactions
 */
@Listeners(TestRerunListener.class)
public class DFinanceStatementAndTransactionTests extends DFinanceBaseTest {


    public class DRetailTransactionClass extends DRetailBaseTest {

        /**
         * @TestCase CD365-1152
         * @Description - Create a new Transaction in Retail
         * @Author Mario Saint-Fleur
         */
        @Test(groups = {"FO_Integration", "createTransaction"}, retryAnalyzer = TestRerun.class, priority = 8)
        public void createTransactionTest() {
            DRetailHomePage homePage = new DRetailHomePage(driver);
            DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);

            //Navigate to the Transaction Screen
            allRetailSalesOrderPage.transactionButton();

            //Click Add Product button
            allRetailSalesOrderPage.addProduct();

            //Select Sunglasses
            allRetailSalesOrderPage.addSunglass();

            //Add Pink Sunglasses
            allRetailSalesOrderPage.selectPinkSunglass();

            //Click add item button
            allRetailSalesOrderPage.addItemButton();

            //Navigate to transaction page
            allRetailSalesOrderPage.transactionButton();

            //Select Pay in Cash
            allRetailSalesOrderPage.selectPayCash();

            //Accept the cash payment amount
            allRetailSalesOrderPage.cashAccepted();

            //Click the close button to complete the transaction
            allRetailSalesOrderPage.closeButton();
        }
    }

    /**
     * @TestCase CD365-1151
     * @Description - Validates the retail store transaction, creates a statement, distributes the tax and validates the distribution tax
     * @Author Mario Saint-Fleur
     */
    @Test(groups = {"FO_Integration"}, retryAnalyzer = TestRerun.class, dependsOnGroups = {"createTransaction"}, priority = 9)
    public void verifyDistributeTaxRequestTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        final String jobName = "P-0001";
        final int batchJobWaitInMS = 120000;

        settingsPage.selectCompany("USRT");

        //Navigate to Distribution schedule and start job
        DFinanceDistributionSchedulePage distributionSchedulePage = homePage.navigateToPage(DFinanceLeftMenuModule.RETAIL_AND_COMMERCE, DFinanceModulePanelCategory.IT,
                DFinanceModulePanelLink.DISTRIBUTION_SCHEDULE);
        distributionSchedulePage.initiateTheJob(jobName);
        distributionSchedulePage.clickOK();
        distributionSchedulePage.waitStaticallyForBatchJobToRun(batchJobWaitInMS);

        //Navigate to Validate
        homePage.navigateToPage(DFinanceLeftMenuModule.RETAIL_AND_COMMERCE, DFinanceModulePanelCategory.RETAIL_AND_COMMERCE_IT,
                DFinanceModulePanelCategory.POS_POSTING, DFinanceModulePanelLink.VALIDATE_STORE_TRANSACTIONS);

        //Validate the Retail store
        allSalesOrdersPage.validateRetailStore("Contoso Retail");
        allSalesOrdersPage.clickOnOKBtn();

        //Navigate to Statements
        homePage.navigateToPage(DFinanceLeftMenuModule.RETAIL_AND_COMMERCE, DFinanceModulePanelCategory.CHANNELS,
                DFinanceModulePanelCategory.STORES, DFinanceModulePanelLink.STATEMENTS);

        //Click New and select Transaction Posting
        allSalesOrdersPage.clickNewOrderAndSelectNewStatementType("NewTransactionalStatement");
        allSalesOrdersPage.clickOnOKBtn();

        //Post the statement
        allSalesOrdersPage.clickPostStatement();

        //Navigate to All Sales Order
        allSalesOrdersPage= homePage.navigateToPage(DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        //Click the most recent order
        allSalesOrdersPage.searchSalesOrderByCustomerName("Default Retail Customer");
        allSalesOrdersSecondPage.selectReturnedOrder("true");

        //Navigate to posted sales tax and verify tax
        allSalesOrdersSecondPage.clickOnInvoiceTab();
        allSalesOrdersSecondPage.journalInvoice();
        String invoiceOrderNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");
        allSalesOrdersSecondPage.clickOnPostedSalesTax();

        //Verify "Posted Sales Tax"
        DFinanceTransactionSalesTaxDialog transactionSalesTaxDialog = new DFinanceTransactionSalesTaxDialog(driver, allSalesOrdersPage);
        String postedSalesTaxAmount = transactionSalesTaxDialog.getCalculatedSalesTaxAmount();

        assertTrue(postedSalesTaxAmount.equalsIgnoreCase("10.73"),
                "'Total calculated sales tax amount' value is not expected");

        //Navigate to Distribute Tax Request and click Ok
        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.DISTRIBUTE_TAX_REQUEST);

        allSalesOrdersPage.clickOnOKBtn();

        //Navigate to XML Inquiry and filter out
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Find the document with the correct Sales order number and click the first Request
        xmlInquiryPage.getDocumentID(invoiceOrderNumber);
        xmlInquiryPage.clickOnFirstRequest();

        //Verify presence of Distribute Tax Request
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("DistributeTaxRequest"));
        assertTrue(request.contains("<AdministrativeOrigin taxAreaId=\"442011440\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>4807 San Jacinto St</StreetAddress1>\n" +
                "\t\t\t\t<City>Houston</City>\n" +
                "\t\t\t\t<MainDivision>TX</MainDivision>\n" +
                "\t\t\t\t<PostalCode>77004-5620</PostalCode>\n" +
                "\t\t\t\t<Country>USA</Country>\n" +
                "\t\t\t</AdministrativeOrigin>"));

        //Verify total tax
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>10.73</TotalTax>"));
    }
}
