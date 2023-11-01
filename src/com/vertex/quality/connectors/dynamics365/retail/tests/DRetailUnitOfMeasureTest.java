package com.vertex.quality.connectors.dynamics365.retail.tests;

import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceHomePage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceSettingsPage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceXMLInquiryPage;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailCustomerPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class DRetailUnitOfMeasureTest extends DRetailBaseTest {

    /**
     * Validate Unit Of Measure get truncated to 2 character that is acceptable to Oseries
     * CD365R-180
     */
    @Test(groups = { "Retail_regression"},priority = 0)
    public void validateTruncatedUnitOfMeasureTest( )
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

        customerPage.clickFindCustomerButton();
        customerPage.searchCustomer();
        customerPage.addCustomer();

        //Add standard shipping
        allRetailSalesOrderPage.clickOrders();
        allRetailSalesOrderPage.shipAllStandard();

        //Validate shipping is added
        String shipText = allRetailSalesOrderPage.shipValidation();
        assertTrue(shipText.contains("Ship Standard from Houston"),
                "'Shipping is not displayed");

    }

    /**
     * Validating the XML File for truncated Unit of Measure
     * CD365R-180
     */
    public class ValidationTests extends DFinanceBaseTest
    {

        @Test(groups = { "Retail_regression"},priority = 1)
        public void validateXMLForUnitOfMeasureTest( )
        {
            DFinanceHomePage homePage = new DFinanceHomePage(driver);
            DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);
            DFinanceXMLInquiryPage xMLInqueryPage = new DFinanceXMLInquiryPage(driver);

            //Navigate to  XML inquiry
            homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                    DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

            settingPage.selectCompany("USRT");

            //Select the Correct Response from the list
            xMLInqueryPage.clickOnFirstRequest();

            //Validate that address is present in the log
            String response = xMLInqueryPage.getLogRequestValue();
            assertTrue(response.contains("<Quantity unitOfMeasure=\"Ea\">1</Quantity>"));
            assertTrue(response.contains("<FlexibleCodeField fieldId=\"7\">Retail store</FlexibleCodeField>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"8\">100001</FlexibleCodeField>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"9\">HOUSTON</FlexibleCodeField>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"10\">052</FlexibleCodeField>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"11\">000017</FlexibleCodeField>"));
        }
    }

}
