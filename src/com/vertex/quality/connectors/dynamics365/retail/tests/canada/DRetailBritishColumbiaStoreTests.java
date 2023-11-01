package com.vertex.quality.connectors.dynamics365.retail.tests.canada;

import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceHomePage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceSettingsPage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceXMLInquiryPage;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.dynamics365.retail.enums.DRetailAddresses;
import com.vertex.quality.connectors.dynamics365.retail.enums.DRetailStores;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailShippingPage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DRetailBritishColumbiaStoreTests extends DRetailBaseTest {

    @BeforeClass(alwaysRun = true)
    public void setupTest() {
        DRetailBaseTest.STORE = DRetailStores.BRITISH_COLUMBIA;
    }

    @AfterClass(alwaysRun = true)
    public void teardownTest() {
        DRetailBaseTest.STORE = DRetailStores.HOUSTON;
    }

    /**
     * @TestCase - CD365-4468
     * @Description - Create order from British Columbia store to British Columbia address and verify tax
     * @Author - Vivek Olumbe
     */
    @Test(groups = {"FO_Integration", "britishColumbiaToBritishColumbia"}, priority = 48)
    public void createBritishColumbiaToBritishColumbiaOrderTest(){
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
        DRetailShippingPage shippingPage = new DRetailShippingPage(driver);

        String expectedTax = "31.20";
        String expectedSubtotal = "260.00";

        //Select customer, and add items to cart
        allRetailSalesOrderPage.transactionButton();
        allRetailSalesOrderPage.addCustomer("Mathew Tolley");
        allRetailSalesOrderPage.clickProducts();
        allRetailSalesOrderPage.clickBrownAviatorButton();
        allRetailSalesOrderPage.clickBlackThickRimmedButton();

        //Create and ship an order
        allRetailSalesOrderPage.clickOrders();
        allRetailSalesOrderPage.selectShipAll();

        //Select ship to address
        shippingPage.selectShipToAddress(DRetailAddresses.VANCOUVER.value);
        shippingPage.shipStandard();

        //Verify subtotal and tax
        String actualTax = allRetailSalesOrderPage.taxValidation();
        String actualSubtotal = allRetailSalesOrderPage.getSubtotal();
        assertEquals(actualTax, expectedTax, "Actual tax is not equal to the expected tax");
        assertEquals(actualSubtotal, expectedSubtotal, "Actual subtotal  is not equal to the expected subtotal");

        //Pay cash and complete order
        allRetailSalesOrderPage.selectPayCash();
        allRetailSalesOrderPage.cashAccepted();
        allRetailSalesOrderPage.closeButton();
    }

    public class DRetailValidateBritishColumbiaOrderTest extends DFinanceBaseTest {

        /**
         * @TestCase - CD365-4469
         * @Description - Validate British Columbia order and PST tax
         * @Author - Vivek Olumbe
         */
        @Test(groups = { "FO_Integration" }, dependsOnGroups = { "britishColumbiaToBritishColumbia" }, priority = 49)
        public void validateBritishColumbiaOrderTest() {
            DFinanceHomePage homePage = new DFinanceHomePage(driver);
            DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);

            settingPage.selectCompany("USRT");

            DFinanceXMLInquiryPage xmlInquiryPage = homePage.navigateToPage(
                    DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                    DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

            //Validate that tax amount and addresses are present in the log
            xmlInquiryPage.clickOnFirstResponse();
            String response = xmlInquiryPage.getLogResponseValue();
            assertTrue(response.contains("<TotalTax>31.2</TotalTax>"), "Actual tax is not equal to the expected tax");

            xmlInquiryPage.selectDocType("Retail transactions");
            xmlInquiryPage.clickOnFirstRequest();
            String request = xmlInquiryPage.getLogRequestValue();
            assertTrue(request.contains(DRetailStores.BRITISH_COLUMBIA.addressXML), "Expected origin XML not found in the request");
            assertTrue(request.contains(DRetailAddresses.VANCOUVER.addressXML), "Expected destination XML not found in the request");
        }
    }
}
