package com.vertex.quality.connectors.dynamics365.retail.tests.canada;

import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.dynamics365.retail.enums.DRetailStores;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailShippingPage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DRetailDeliveryTermTests extends DFinanceBaseTest {

    @AfterClass(alwaysRun = true)
    public void teardownTest() { DRetailBaseTest.STORE = DRetailStores.HOUSTON; }

    /**
     * @TestCase - CD365-4544
     * @Description - Set incorrect Canada delivery terms
     * @Author - Vivek Olumbe
     */
    @Test(groups = {"FO_Integration", "incorrectDeliveryTerms"}, priority = 54)
    public void setIncorrectDeliveryTermsTest() {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        settingPage.selectCompany("USRT");
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX,
                DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_TAX_PARAMETERS);

        settingPage.enterCanadaToUSDeliveryTerm("123");
        settingPage.enterUSToCanadaDeliveryTerm("123");
        settingPage.clickOnSaveButton();

        //Run 9999 job
        DFinanceDistributionSchedulePage distributionSchedulePage = homePage.navigateToPage(DFinanceLeftMenuModule.RETAIL_AND_COMMERCE, DFinanceModulePanelCategory.IT,
                DFinanceModulePanelLink.DISTRIBUTION_SCHEDULE);
        distributionSchedulePage.initiateTheJob("9999");
        distributionSchedulePage.clickOK();
        distributionSchedulePage.waitStaticallyForBatchJobToRun(180000);
    }

    public class DRetailCanadaToUSIncorrectDeliveryTermTest extends DRetailBaseTest {
        @BeforeClass(alwaysRun = true)
        public void setupTest() { DRetailBaseTest.STORE = DRetailStores.BRITISH_COLUMBIA; }

        /**
         * @TestCase - CD365-4546
         * @Description - Create British Columbia to US order after setting incorrect delivery terms
         * @Author - Vivek Olumbe
         */
        @Test(groups = {"FO_Integration", "incorrectDeliveryTermsOrder"}, dependsOnGroups = {"incorrectDeliveryTerms"}, priority = 55)
        public void createBritishColumbiaToUSOrderTest() {
            DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
            DRetailShippingPage shippingPage = new DRetailShippingPage(driver);

            String expectedTax = "10.27";
            String expectedSubtotal = "130.00";

            //Select customer, and add items to cart
            allRetailSalesOrderPage.transactionButton();
            allRetailSalesOrderPage.addCustomer("Owen Tolley");
            allRetailSalesOrderPage.clickProducts();
            allRetailSalesOrderPage.clickBrownLeopardprintButton();

            //Create and ship an order
            allRetailSalesOrderPage.clickOrders();
            allRetailSalesOrderPage.selectShipAll();
            shippingPage.shipStandard();

            //Verify subtotal and tax
            String actualTax = allRetailSalesOrderPage.taxValidation();
            String actualSubtotal = allRetailSalesOrderPage.getSubtotal();
            assertEquals(actualTax, expectedTax, "Actual tax is not equal to the expected tax");
            assertEquals(actualSubtotal, expectedSubtotal, "Actual subtotal is not equal to the expected subtotal");

            //Pay cash and complete order
            allRetailSalesOrderPage.selectPayCash();
            allRetailSalesOrderPage.cashAccepted();
            allRetailSalesOrderPage.closeButton();
        }
    }

    /**
     * @TestCase - CD365-4548
     * @Description - Validate delivery term is EXW after setting incorrect delivery term
     * @Author - Vivek Olumbe
     */
    @Test(groups = {"FO_Integration"}, dependsOnGroups = {"incorrectDeliveryTermsOrder"}, priority = 56)
    public void validateIncorrectDeliveryTermTest() {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        settingPage.selectCompany("USRT");

        //Navigate to All Sales Orders page
        DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        allSalesOrdersPage.searchSalesOrderByCustomerName("Owen Tolley");
        allSalesOrdersSecondPage.applyFilterOnStatus("Open order");
        allSalesOrdersSecondPage.clickOnLatestOrderCreated();

        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

        //Validate Sales Tax amount
        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();
        String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        String expectedAmount = "10.27";
        assertTrue(calculatedSalesTaxAmount.contains(expectedAmount), "Actual tax amount is not equal to the expected tax amount");
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        //Generate invoice
        allSalesOrdersSecondPage.clickOnInvoiceTab();
        allSalesOrdersSecondPage.generateInvoice();
        allSalesOrdersPage.clickOnOKBtn();
        allSalesOrdersPage.clickOnOKPopUp();

        //Validate that tax amount and delivery terms are present in the log
        DFinanceXMLInquiryPage xmlInquiryPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Validate sales order response
        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>10.27</TotalTax>"));
        assertTrue(response.contains("deliveryTerm=\"EXW\""), "Delivery term is not correct");

        //Validate retail transactions request
        xmlInquiryPage.getDocumentID("");
        xmlInquiryPage.selectDocType("Retail transactions");
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("deliveryTerm=\"EXW\""), "Delivery term is not correct");
    }

    /**
     * @TestCase - CD365-4545
     * @Description - Set correct Canada delivery terms
     * @Author - Vivek Olumbe
     */
    @Test(groups = {"FO_Integration", "correctDeliveryTerms"}, priority = 57)
    public void setCorrectDeliveryTermsTest() {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        settingPage.selectCompany("USRT");
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX,
                DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_TAX_PARAMETERS);

        settingPage.enterCanadaToUSDeliveryTerm("SUP");
        settingPage.enterUSToCanadaDeliveryTerm("SUP");
        settingPage.clickOnSaveButton();

        //Run 9999 job
        DFinanceDistributionSchedulePage distributionSchedulePage = homePage.navigateToPage(DFinanceLeftMenuModule.RETAIL_AND_COMMERCE, DFinanceModulePanelCategory.IT,
                DFinanceModulePanelLink.DISTRIBUTION_SCHEDULE);
        distributionSchedulePage.initiateTheJob("9999");
        distributionSchedulePage.clickOK();
        distributionSchedulePage.waitStaticallyForBatchJobToRun(180000);
    }

    public class DRetailCanadaToUSCorrectDeliveryTermTest extends DRetailBaseTest {
        @BeforeClass(alwaysRun = true)
        public void setupTest() { DRetailBaseTest.STORE = DRetailStores.BRITISH_COLUMBIA; }

        /**
         * @TestCase - CD365-4547
         * @Description - Create an order from British Columbia to US after setting correct delivery terms
         * @Author - Vivek Olumbe
         */
        @Test(groups = {"FO_Integration", "correctDeliveryTermsOrder"}, dependsOnGroups = {"correctDeliveryTerms"}, priority = 58)
        public void createBritishColumbiaToUSOrderTest() {
            DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
            DRetailShippingPage shippingPage = new DRetailShippingPage(driver);

            String expectedTax = "10.27";
            String expectedSubtotal = "130.00";

            //Select customer, and add items to cart
            allRetailSalesOrderPage.transactionButton();
            allRetailSalesOrderPage.addCustomer("Owen Tolley");
            allRetailSalesOrderPage.clickProducts();
            allRetailSalesOrderPage.clickBrownLeopardprintButton();

            //Create and ship an order
            allRetailSalesOrderPage.clickOrders();
            allRetailSalesOrderPage.selectShipAll();
            shippingPage.shipStandard();

            //Verify subtotal and tax
            String actualTax = allRetailSalesOrderPage.taxValidation();
            String actualSubtotal = allRetailSalesOrderPage.getSubtotal();
            assertEquals(actualTax, expectedTax, "Actual tax is not equal to the expected tax");
            assertEquals(actualSubtotal, expectedSubtotal, "Actual subtotal is not equal to the expected subtotal");

            //Pay cash and complete order
            allRetailSalesOrderPage.selectPayCash();
            allRetailSalesOrderPage.cashAccepted();
            allRetailSalesOrderPage.closeButton();
        }
    }

    /**
     * @TestCase - CD365-4549
     * @Description - Validate delivery term is SUP
     * @Author - Vivek Olumbe
     */
    @Test(groups = {"FO_Integration"}, dependsOnGroups = {"correctDeliveryTermsOrder"}, priority = 59)
    public void validateCorrectDeliveryTermTest() {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        settingPage.selectCompany("USRT");

        //Navigate to All Sales Orders page
        DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        allSalesOrdersPage.searchSalesOrderByCustomerName("Owen Tolley");
        allSalesOrdersSecondPage.applyFilterOnStatus("Open order");
        allSalesOrdersSecondPage.clickOnLatestOrderCreated();

        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

        //Validate Sales Tax amount
        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();
        String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        String expectedAmount = "10.27";
        assertTrue(calculatedSalesTaxAmount.contains(expectedAmount), "Actual tax amount is not equal to the expected tax amount");
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        //Generate invoice
        allSalesOrdersSecondPage.clickOnInvoiceTab();
        allSalesOrdersSecondPage.generateInvoice();
        allSalesOrdersPage.clickOnOKBtn();
        allSalesOrdersPage.clickOnOKPopUp();

        //Validate that tax amount and delivery terms are present in the log
        DFinanceXMLInquiryPage xmlInquiryPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Validate sales order response
        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>10.27</TotalTax>"));
        assertTrue(response.contains("deliveryTerm=\"SUP\""), "Delivery term is not correct");

        //Validate retail transactions request
        xmlInquiryPage.getDocumentID("");
        xmlInquiryPage.selectDocType("Retail transactions");
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("deliveryTerm=\"SUP\""), "Delivery term is not correct");
    }
}
