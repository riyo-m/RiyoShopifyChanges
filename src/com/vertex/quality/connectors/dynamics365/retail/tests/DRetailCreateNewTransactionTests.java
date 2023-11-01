package com.vertex.quality.connectors.dynamics365.retail.tests;

import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuNames;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author Mario Saint-Fleur
 * All tests related to Transaction creation and validation
 */

public class DRetailCreateNewTransactionTests extends DRetailBaseTest {
    final String tax = DFinanceLeftMenuNames.TAX.getData();
    final String setup = DFinanceLeftMenuNames.SETUP.getData();
    final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
    final String taxParametersData = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
    final String jobName = "1080";
    final int batchJobWaitInMS = 600000;

    /**
     * @TestCase CD365R-209
     * @Description - Create a new Retail transaction and verify Usage Class in XML Inquiry
     * @Author Mario Saint-Fleur
     */
    @Test(groups = {"FO_Integration", "overrideLineProductTaxList"}, priority = 14)
    public void createTransactionAndOverrideLineProductTaxListTest() {
        DRetailHomePage homePage = new DRetailHomePage(driver);
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);

        //Navigate to the Transaction Screen
        allRetailSalesOrderPage.transactionButton();

        //Add customer "Test Auto"
        allRetailSalesOrderPage.addCustomer("Test Auto");

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

        //Click Override Line Product Tax List
        allRetailSalesOrderPage.clickOverrideLineProductTaxList();

        //Select Pay in Cash
        allRetailSalesOrderPage.selectPayCash();

        //Accept the cash payment amount
        allRetailSalesOrderPage.cashAccepted();

        //Click the close button to complete the transaction
        allRetailSalesOrderPage.closeButton();

    }

    /**
     * @TestCase CD365R-210
     * @Description - Create a new Retail transaction and verify Usage Class in XML Inquiry does not exist
     * @Author - Mario Saint-Fleur
     */
    @Test(groups = {"FO_Special", "overrideLineProductTaxListOff"}, dependsOnGroups = {"overrideButtonOff"})
    public void createTransactionAndOverrideLineProductTaxListOffTest() {
        DRetailHomePage homePage = new DRetailHomePage(driver);
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);

        //Navigate to the Transaction Screen
        allRetailSalesOrderPage.transactionButton();

        //Add customer "Test Auto"
        allRetailSalesOrderPage.addCustomer("Test Auto");

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

        //Click Override Line Product Tax List
        allRetailSalesOrderPage.clickOverrideLineProductTaxList();

        //Select Pay in Cash
        allRetailSalesOrderPage.selectPayCash();

        //Accept the cash payment amount
        allRetailSalesOrderPage.cashAccepted();

        //Click the close button to complete the transaction
        allRetailSalesOrderPage.closeButton();

    }

    public class DFinanceVerifyUsageClass extends DFinanceBaseTest {
        /**
         * @TestCase - CD365R-213
         * @Description - Verifying the usage class of a transaction
         * @Author - Mario Saint-Fleur
         */
        @Test(groups = {"FO_Integration"}, dependsOnGroups = {"overrideLineProductTaxList"}, priority = 15)
        public void verifyUsageClassExistenceTest() {
            DFinanceHomePage homePage = new DFinanceHomePage(driver);
            DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
            DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);

            settingPage.selectCompany("USRT");

            homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                    DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

            //Find the document with the id of Houston-Houston-17 and the first Response
            xmlInquiryPage.getDocumentID("Houston-Houston-17");
            xmlInquiryPage.clickOnFirstRequest();
            String request = xmlInquiryPage.getLogRequestValue();

            //Verify LineItem does usageClass
            assertTrue(request.contains("<LineItem lineItemId=\"1\" usageClass=\"ExemptItem\" lineItemNumber=\"0\" " +
                    "projectNumber=\"\" generalLedgerAccount=\"401100\" departmentCode=\"033\" costCenter=\"016\" locationCode=\"HOUSTON\""));

        }

        /**
         * @Testcase - CD365R-214
         * @Description - Turning the Retain Item Tax Group Override Button off
         * and starting job 1080
         * @Author - Mario Saint-Fleur
         */
        @Test(groups = {"FO_Special", "overrideButtonOff"})
        public void toggleRetainTaxGroupOverrideButtonOffTest(){
            DFinanceHomePage homePage = new DFinanceHomePage(driver);
            DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);

            settingPage.selectCompany("USRT");

            //Navigate to Vertex Settings
            homePage.clickOnNavigationPanel();
            homePage.selectLeftMenuModule(tax);
            homePage.collapseAll();
            homePage.expandModuleCategory(setup);
            homePage.expandModuleCategory(vertex);
            settingPage = homePage.selectModuleTabLink(taxParametersData);
            settingPage.toggleRetainTaxGroupOverrideButton(false);

            DFinanceDistributionSchedulePage distributionSchedulePage = homePage.navigateToPage(DFinanceLeftMenuModule.RETAIL_AND_COMMERCE,
                    DFinanceModulePanelCategory.IT, DFinanceModulePanelLink.DISTRIBUTION_SCHEDULE);
            distributionSchedulePage.initiateTheJob(jobName);
            distributionSchedulePage.clickOK();
            distributionSchedulePage.waitStaticallyForBatchJobToRun(batchJobWaitInMS);
        }

        /**
         * @TestCase - CD365R-215
         * @Description - Verifying the Usage class of a Retail transaction is not present
         * @Author - Mario Saint-Fleur
         */
        @Test(groups = {"FO_Special"}, dependsOnGroups = {"overrideLineProductTaxListOff"})
        public void verifyUsageClassNonExistenceTest() {
            DFinanceHomePage homePage = new DFinanceHomePage(driver);
            DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
            DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);

            settingPage.selectCompany("USRT");

            homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                    DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

            //Find the document with the id of Houston-Houston-17 and the first Response
            xmlInquiryPage.getDocumentID("Houston-Houston-17");
            xmlInquiryPage.clickOnFirstRequest();
            String request = xmlInquiryPage.getLogRequestValue();

            //Verify LineItem does not contain usageClass
            assertTrue(!request.contains("<LineItem lineItemId=\"1\" usageClass=\"ExemptItem\" lineItemNumber=\"0\" locationCode=\"HOUSTON\">"));
        }
    }

    /**
     * @TestCase - CD365R-237
     * @Description - Validate Deposit Due When Using Deposit Override In POS Order
     * @Author - Mario Saint-Fleur
     */
    @Test(groups = {"Retail_regression"})
    public void verifyDepositDueDuringDepositOverrideTest(){
        String expectedAmount = "$0.00";

        DRetailHomePage homePage = new DRetailHomePage(driver);
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);

        //Add customer and product
        allRetailSalesOrderPage.transactionButton();
        allRetailSalesOrderPage.addCustomer("Test Auto");
        allRetailSalesOrderPage.addProduct();
        allRetailSalesOrderPage.addSunglass();
        allRetailSalesOrderPage.selectPinkSunglass();
        allRetailSalesOrderPage.addItemButton();
        allRetailSalesOrderPage.transactionButton();

        //Create customer order
        allRetailSalesOrderPage.clickOrders();
        allRetailSalesOrderPage.clickCreateCustomerOrder();

        //Ship Orders
        allRetailSalesOrderPage.shipAllStandard();

        //Deposit override
        allRetailSalesOrderPage.clickDepositOverride();
        allRetailSalesOrderPage.clickMaximumOverrideAmount();

        //Select Pay in Cash
        allRetailSalesOrderPage.selectPayCash();
        allRetailSalesOrderPage.cashAccepted();
        allRetailSalesOrderPage.closeButton();

        //Return order
        allRetailSalesOrderPage.clickRecallOrder();
        allRetailSalesOrderPage.clickAddFilterBtn();
        allRetailSalesOrderPage.clickOnFilterOption("Order date");
        allRetailSalesOrderPage.enterOrderDate();
        allRetailSalesOrderPage.clickApplyFilterBtn();
        allRetailSalesOrderPage.selectSalesOrder();
        allRetailSalesOrderPage.clickEditOrder();
        boolean isPageLoaded = allRetailSalesOrderPage.isPageLoaded();

        String actualAmount = allRetailSalesOrderPage.getDepositDue();

        //Validate deposit due
        assertTrue(actualAmount.contains(expectedAmount), "Deposit Due is not equal to 0");

        //Select Pay in Cash
        allRetailSalesOrderPage.selectPayCash();
        allRetailSalesOrderPage.closeButton();
    }
}
