package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceDistributionSchedulePage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceHomePage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceSettingsPage;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailCustomerPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;


/**
 * This Class contains Tests for Incorrect Second Cloud URL Parameters in Vertex Tax parameters page
 */
@Listeners(TestRerunListener.class)
public class DFinanceInvalidSecondCloudURLTests extends DFinanceBaseTest {

    final String trustedID = "7326208514988659";
    final String taxCalculationURL = "https://axcsconnect.vertexsmb.com/vertex-ws/services/CalculateTax70";
    final String addressValidationURL = "https://axcsconnect.vertexsmb.com/vertex-ws/services/LookupTaxAreas70";
    final String invalidSecondCloudURL = "https://axconnect.vertexsmb.com";
    final String validSecondCloudURL = "https://axconnect.vertexsmb.com/vertex-ws/services/EchoDoc";
    final String jobName = "1080";
    final int batchJobWaitInMS = 180000;

    /**
     * This Test Runs the Distribution Job with invalid second cloud URL
     * CD365-799
     */
    @Test(groups = { "FO_Integration_Deprecated" }, retryAnalyzer = TestRerun.class,priority = 0)
    public void invalidCloudSecondURLTest()
    {
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
        settingsPage.setSecondCloudURL(invalidSecondCloudURL);
        settingsPage.clickOnSaveButton();

        DFinanceDistributionSchedulePage distributionSchedulePage = homePage.navigateToPage(DFinanceLeftMenuModule.RETAIL_AND_COMMERCE, DFinanceModulePanelCategory.IT,
                DFinanceModulePanelLink.DISTRIBUTION_SCHEDULE);
        distributionSchedulePage.initiateTheJob(jobName);
        distributionSchedulePage.clickOK();
        distributionSchedulePage.waitStaticallyForBatchJobToRun(batchJobWaitInMS);
    }

    public class DRetailValidSecondCloudURLTests extends DRetailBaseTest
    {
        /**
         * Return the above created order in POS
         * CD365-803
         */
        @Test(groups = { "FO_Integration_Deprecated" }, retryAnalyzer = TestRerun.class,priority = 1)
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
            //customerPage.clickHomeButton();
            customerPage.clickFindCustomerButton();
            customerPage.searchCustomer();
            customerPage.addCustomer();

            //Add standard shipping
            allRetailSalesOrderPage.clickOrders();
            allRetailSalesOrderPage.shipAllStandard();

            //Validate the tax amount after adding the returning item to the cart
            String returnTaxAmount = allRetailSalesOrderPage.taxValidation();
            assertTrue(returnTaxAmount.equalsIgnoreCase("32.18"),
                    "'Total sales tax amount' value is not expected");
        }
    }

    /**
     * This Test class contains after Method to put correct Second cloud URl
     */
    public class CorrectURLTests extends DFinanceBaseTest
    {
        /**
         * Putting Back Correct URL and run the distribution job
         * CD365-802
         */
        @Test(groups = { "FO_Integration_Deprecated" }, retryAnalyzer = TestRerun.class,priority = 2)
        public void puttingBackCorrectURLTest()
        {
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
            settingsPage.setSecondCloudURL(validSecondCloudURL);
            settingsPage.clickOnSaveButton();

            DFinanceDistributionSchedulePage distributionSchedulePage = homePage.navigateToPage(DFinanceLeftMenuModule.RETAIL_AND_COMMERCE, DFinanceModulePanelCategory.IT,
                    DFinanceModulePanelLink.DISTRIBUTION_SCHEDULE);
            distributionSchedulePage.initiateTheJob(jobName);
            distributionSchedulePage.clickOK();
            distributionSchedulePage.waitStaticallyForBatchJobToRun(batchJobWaitInMS);
        }
    }
}
