package com.vertex.quality.connectors.dynamics365.finance.tests;


import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailCustomerPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

/**
 * @author Mario Saint-Fleur
 * All tests related to the Redesign Backup Tax Group Functionality
 */
@Listeners(TestRerunListener.class)
public class DFinanceRedesignBackupTaxGroupFunctionalityTests extends DFinanceBaseTest{

    final String trustedID = "iSw0dhj2RDYh";
    final String taxCalculationURL = "https://connectortest.dev.ondemand.vertexinc.com/vertex-ws/services/CalculateTax70";
    final String addressValidationURL = "https://connectortest.dev.ondemand.vertexinc.com/vertex-ws/services/LookupTaxAreas70";
    final String invalidTaxCalculationURL = "https://axcsconnect.vertexsmb.com/vertex-ws-dp/services/CalculateTax70";
    final String jobName = "9999";
    final int batchJobWaitInMS = 600000;

    Boolean invalidURL = false;

    @AfterSuite(alwaysRun = true)
    public void teardownTest( )
    {
        if (invalidURL) {
            setupTestCase();
            final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
            final String tax = DFinanceLeftMenuNames.TAX.getData();
            final String setup = DFinanceLeftMenuNames.SETUP.getData();
            final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
            final String vertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();

            DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
            DFinanceHomePage homePage = new DFinanceHomePage(driver);
            settingsPage.selectCompany("USRT");
            homePage.navigateToPage(DFinanceLeftMenuModule.TAX,
                    DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                    DFinanceModulePanelLink.VERTEX_TAX_PARAMETERS);

            // set Vertex Tax Parameter Details
            settingsPage.setTrustedId(trustedID);
            settingsPage.setTrustedTaxURL(taxCalculationURL);
            settingsPage.setTrustedAddressURL(addressValidationURL);
            invalidURL = false;

            homePage.clickOnNavigationPanel();
            homePage.selectLeftMenuModule(tax);
            homePage.expandModuleCategory(setup);
            homePage.expandModuleCategory(vertex);

            //Navigate to Vertex Tax Parameter page and enable Backup Tax Group and set Retail Retry Number
            settingsPage = homePage.selectModuleTabLink(vertexTaxParameters);
            settingsPage.selectSettingsPage(taxGroupSettings);
            settingsPage.clickOnSaveButton();

            //Initiate the Job as 9999 and run the job
            DFinanceDistributionSchedulePage distributionSchedulePage = homePage.navigateToPage(DFinanceLeftMenuModule.RETAIL_AND_COMMERCE, DFinanceModulePanelCategory.IT,
                    DFinanceModulePanelLink.DISTRIBUTION_SCHEDULE);
            distributionSchedulePage.initiateTheJob(jobName);
            distributionSchedulePage.clickOK();
            distributionSchedulePage.waitStaticallyForBatchJobToRun(batchJobWaitInMS);
            cleanupTestRun();
        }
    }

    /**
     * @TestCase CD365-1052
     * @Description - Redesign Of Backup TaxGroup With Invalid Url
     * @Author Mario Saint-Fleur
     */
    @Test(groups = {"FO_Integration", "backUpTaxGroupInvalidUrl"}, retryAnalyzer = TestRerun.class, priority = 18)
    public void redesignedBackupTaxGroupWithInvalidUrlTest(){

        final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
        final String tax = DFinanceLeftMenuNames.TAX.getData();
        final String setup = DFinanceLeftMenuNames.SETUP.getData();
        final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
        final String vertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();

        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        settingsPage.selectCompany("USRT");
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX,
                DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_TAX_PARAMETERS);

        // set Vertex Tax Parameter Details
        settingsPage.setTrustedId(trustedID);
        settingsPage.setTrustedTaxURL(invalidTaxCalculationURL);
        settingsPage.setTrustedAddressURL(addressValidationURL);
        invalidURL = true;

        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(tax);
        homePage.expandModuleCategory(setup);
        homePage.expandModuleCategory(vertex);

        //Navigate to Vertex Tax Parameter page and enable Backup Tax Group and set Retail Retry Number
        settingsPage = homePage.selectModuleTabLink(vertexTaxParameters);
        settingsPage.selectSettingsPage(taxGroupSettings);
        settingsPage.toggleTxBkUpGrp(true);
        settingsPage.enterInterval("4");
        settingsPage.clickOnSaveButton();

        //Initiate the Job as 9999 and run the job
        DFinanceDistributionSchedulePage distributionSchedulePage = homePage.navigateToPage(DFinanceLeftMenuModule.RETAIL_AND_COMMERCE, DFinanceModulePanelCategory.IT,
                DFinanceModulePanelLink.DISTRIBUTION_SCHEDULE);
        distributionSchedulePage.initiateTheJob(jobName);
        distributionSchedulePage.clickOK();
        distributionSchedulePage.waitStaticallyForBatchJobToRun(batchJobWaitInMS);
    }

    public class DRetailValidSecondCloudURLTests extends DRetailBaseTest
    {
        /**
         * @TestCase CD365-1055
         * @Description - Testing to see if the correct tax is returned using the incorrect URL
         * @Author Mario Saint-Fleur
         */
        @Test(groups = { "FO_Integration", "invalidSecondCloudUrl" }, retryAnalyzer = TestRerun.class, dependsOnGroups = {"backUpTaxGroupInvalidUrl"},  priority = 19)
        public void invalidSecondCloudURLPOSTest( )
        {
            //================script implementation========================
            //Navigate to add product page
            DRetailHomePage homePage = new DRetailHomePage(driver);
            DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
            DRetailCustomerPage customerPage = new DRetailCustomerPage(driver);

            //Navigate to all product page
            allRetailSalesOrderPage.productPage();

            //Navigate to accessories page
            allRetailSalesOrderPage.addSunglass();

            //Select pink sun glass and add to the cart
            allRetailSalesOrderPage.selectPinkSunglass();

            //Click on add item button
            allRetailSalesOrderPage.addItemButton();

            //Navigate to transaction page
            allRetailSalesOrderPage.transactionButton();

            //Add customer to the sale
            customerPage.clickFindCustomerButton();
            customerPage.searchCustomer();
            customerPage.addCustomer();

            //Validate the tax amount
            String returnTaxAmount = allRetailSalesOrderPage.taxValidation();
            assertTrue(returnTaxAmount.equalsIgnoreCase("0.00"),
                    "'Total sales tax amount' value is not expected");

            //Select Pay in Cash
            allRetailSalesOrderPage.selectPayCash();

            //Accept the cash payment amount
            allRetailSalesOrderPage.cashAccepted();

            //Click the close button to complete the transaction
            allRetailSalesOrderPage.closeButton();
        }
    }

    /**
     * This Test class contains after Method to put correct Second cloud URl
     */
    public class CorrectURLTests extends DFinanceBaseTest
    {
        /**
         * @TestCase CD365-1053
         * @Description - Redesign Of Backup TaxGroup With Correct Url
         * @Author Mario Saint-Fleur
         */
        @Test(groups = { "FO_Integration", "puttingBackCorrectUrl" }, retryAnalyzer = TestRerun.class, dependsOnGroups = {"invalidSecondCloudUrl"},  priority = 20)
        public void puttingBackCorrectURLTest()
        {
            final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
            final String tax = DFinanceLeftMenuNames.TAX.getData();
            final String setup = DFinanceLeftMenuNames.SETUP.getData();
            final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
            final String vertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();

            DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
            DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
            DFinanceHomePage homePage = new DFinanceHomePage(driver);
            settingsPage.selectCompany("USRT");

            homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                    DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

            //Find the document with the id of Houston-Houston-17 and the first Response
            xmlInquiryPage.getDocumentID("Houston-Houston-17");
            xmlInquiryPage.clickOnFirstResponse();
            String response = xmlInquiryPage.getLogResponseValue();

            //Verify endpoint error
            assertTrue(response.contains("<ErrorMessage>One or more errors occurred.\n" +
                    "There was no endpoint listening at https://axcsconnect.vertexsmb.com/vertex-ws-dp/services/CalculateTax70 that could accept the message. This is often caused by an incorrect address or SOAP action. See InnerException, if present, for more details.</ErrorMessage>"));

            homePage.navigateToPage(DFinanceLeftMenuModule.TAX,
                    DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                    DFinanceModulePanelLink.VERTEX_TAX_PARAMETERS);

            // set Vertex Tax Parameter Details
            settingsPage.setTrustedId(trustedID);
            settingsPage.setTrustedTaxURL(taxCalculationURL);
            settingsPage.setTrustedAddressURL(addressValidationURL);
            invalidURL = false;

            homePage.clickOnNavigationPanel();
            homePage.selectLeftMenuModule(tax);
            homePage.expandModuleCategory(setup);
            homePage.expandModuleCategory(vertex);

            //Navigate to Vertex Tax Parameter page and enable Backup Tax Group and set Retail Retry Number
            settingsPage = homePage.selectModuleTabLink(vertexTaxParameters);
            settingsPage.selectSettingsPage(taxGroupSettings);
            settingsPage.clickOnSaveButton();

            //Initiate the Job as 9999 and run the job
            DFinanceDistributionSchedulePage distributionSchedulePage = homePage.navigateToPage(DFinanceLeftMenuModule.RETAIL_AND_COMMERCE, DFinanceModulePanelCategory.IT,
                    DFinanceModulePanelLink.DISTRIBUTION_SCHEDULE);
            distributionSchedulePage.initiateTheJob(jobName);
            distributionSchedulePage.clickOK();
            distributionSchedulePage.waitStaticallyForBatchJobToRun(batchJobWaitInMS);
        }
    }


    public class DRetailCorrectSecondCloudURLTests extends DRetailBaseTest
    {
        /**
         * @TestCase CD365-1056
         * @Description - Testing to see if the correct tax is returned using the correct URL
         * @Author Mario Saint-Fleur
         */
        @Test(groups = { "FO_Integration" }, retryAnalyzer = TestRerun.class, dependsOnGroups = {"puttingBackCorrectUrl"},  priority = 21)
        public void validSecondCloudURLPOSTest()
        {
            //================script implementation========================
            //Navigate to add product page
            DRetailHomePage homePage = new DRetailHomePage(driver);
            DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
            DRetailCustomerPage customerPage = new DRetailCustomerPage(driver);

            //Navigate to all product page
            allRetailSalesOrderPage.productPage();

            //Navigate to accessories page
            allRetailSalesOrderPage.addSunglass();

            //Select pink sun glass and add to the cart
            allRetailSalesOrderPage.selectPinkSunglass();

            //Click on add item button
            allRetailSalesOrderPage.addItemButton();

            //Navigate to transaction page
            allRetailSalesOrderPage.transactionButton();

            //Add customer to the sale
            customerPage.clickFindCustomerButton();
            customerPage.searchCustomer();
            customerPage.addCustomer();

            //Validate the tax amount
            String returnTaxAmount = allRetailSalesOrderPage.taxValidation();
            assertTrue(returnTaxAmount.equalsIgnoreCase("10.73"),
                    "'Total sales tax amount' value is not expected");;

            //Select Pay in Cash
            allRetailSalesOrderPage.selectPayCash();

            //Accept the cash payment amount
            allRetailSalesOrderPage.cashAccepted();

            //Click the close button to complete the transaction
            allRetailSalesOrderPage.closeButton();
        }
    }
}