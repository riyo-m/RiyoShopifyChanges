package com.vertex.quality.connectors.dynamics365.retail.tests.canada;

import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.dynamics365.retail.enums.DRetailAddresses;
import com.vertex.quality.connectors.dynamics365.retail.enums.DRetailStores;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailDiscountPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailShippingPage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DRetailQuebecStoreTests extends DRetailBaseTest {

    @BeforeClass(alwaysRun = true)
    public void setupTest() {
        DRetailBaseTest.STORE = DRetailStores.QUEBEC;
    }

    @AfterClass(alwaysRun = true)
    public void teardownTest() {
        DRetailBaseTest.STORE = DRetailStores.HOUSTON;
    }

    /**
     * @TestCase - CD365-4466
     * @Description - Create order from Quebec store to Quebec address and verify tax
     * @Author - Vivek Olumbe
     */
    @Test(groups = {"FO_Integration", "quebecToQuebec"}, priority = 36)
    public void createQuebecToQuebecOrderTest(){
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
        DRetailShippingPage shippingPage = new DRetailShippingPage(driver);

        String expectedTax = "38.94";
        String expectedSubtotal = "260.00";

        //Select customer, and add items to cart
        allRetailSalesOrderPage.transactionButton();
        allRetailSalesOrderPage.addCustomer("Mathew Olley");
        allRetailSalesOrderPage.clickProducts();
        allRetailSalesOrderPage.clickBrownAviatorButton();
        allRetailSalesOrderPage.clickBlackThickRimmedButton();

        //Create and ship an order
        allRetailSalesOrderPage.clickOrders();
        allRetailSalesOrderPage.selectShipAll();

        //Select ship to address
        shippingPage.selectShipToAddress(DRetailAddresses.MONTREAL.value);
        shippingPage.shipStandard();

        //Verify subtotal and tax
        String actualTax = allRetailSalesOrderPage.taxValidation();
        String actualSubtotal = allRetailSalesOrderPage.getSubtotal();
        assertEquals(actualTax, expectedTax, "Actual tax is not equal to the expected tax");
        assertEquals(actualSubtotal,expectedSubtotal, "Actual subtotal  is not equal to the expected subtotal");

        //Pay cash and complete order
        allRetailSalesOrderPage.selectPayCash();
        allRetailSalesOrderPage.cashAccepted();
        allRetailSalesOrderPage.closeButton();

    }

    public class DRetailValidateQuebecOrderTest extends DFinanceBaseTest {

        /**
         * @TestCase - CD365-4467
         * @Description - Validate Ontario order and QST tax
         * @Author - Vivek Olumbe
         */
        @Test(groups = { "FO_Integration" }, dependsOnGroups = { "quebecToQuebec" }, priority = 37)
        public void validateQuebecOrderTest() {
            DFinanceHomePage homePage = new DFinanceHomePage(driver);
            DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);

            settingPage.selectCompany("USRT");

            DFinanceXMLInquiryPage xmlInquiryPage = homePage.navigateToPage(
                    DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                    DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

            //Validate that tax amount and addresses are present in the log
            xmlInquiryPage.clickOnFirstResponse();
            String response = xmlInquiryPage.getLogResponseValue();
            assertTrue(response.contains("<TotalTax>38.94</TotalTax>"));

            xmlInquiryPage.selectDocType("Retail transactions");
            xmlInquiryPage.clickOnFirstRequest();
            String request = xmlInquiryPage.getLogRequestValue();
            assertTrue(request.contains(DRetailStores.QUEBEC.addressXML));
            assertTrue(request.contains(DRetailAddresses.MONTREAL.addressXML));
        }
    }

    /**
     * @TestCase - CD365-4532
     * @Description - Create order from Quebec store to Alberta address and verify tax
     * @Author - Vivek Olumbe
     */
    @Test(groups = {"FO_Integration", "quebecToAlberta"}, priority = 38)
    public void createQuebecToAlbertaOrderTest(){
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
        DRetailShippingPage shippingPage = new DRetailShippingPage(driver);

        String expectedTax = "13.00";
        String expectedSubtotal = "260.00";

        //Select customer, and add items to cart
        allRetailSalesOrderPage.transactionButton();
        allRetailSalesOrderPage.addCustomer("Mathew Tolley");
        allRetailSalesOrderPage.clickProducts();
        allRetailSalesOrderPage.clickBrownLeopardprintButton();
        allRetailSalesOrderPage.clickPinkThickRimmedButton();

        //Create and ship an order
        allRetailSalesOrderPage.clickOrders();
        allRetailSalesOrderPage.selectShipAll();

        //Select ship to address
        shippingPage.selectShipToAddress(DRetailAddresses.CALGARY.value);
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

    public class DRetailValidateQuebecToAlbertaOrderTest extends DFinanceBaseTest {

        /**
         * @TestCase - CD365-4533
         * @Description - Validate Quebec to Alberta order
         * @Author - Vivek Olumbe
         */
        @Test(groups = { "FO_Integration" }, dependsOnGroups = { "quebecToAlberta" }, priority = 39)
        public void validateQuebecToAlbertaOrderTest() {
            DFinanceHomePage homePage = new DFinanceHomePage(driver);
            DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);
            DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

            settingPage.selectCompany("USRT");

            //Navigate to All Sales Orders page
            DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
                    DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                    DFinanceModulePanelLink.ALL_SALES_ORDERS);

            allSalesOrdersPage.searchSalesOrderByCustomerName("Mathew Tolley");
            allSalesOrdersSecondPage.applyFilterOnStatus("Open order");
            allSalesOrdersSecondPage.clickOnLatestOrderCreated();

            String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

            //Validate Sales Tax amount
            allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
            allSalesOrdersPage.openSalesTaxCalculation();
            String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
            String expectedAmount = "13.00";
            assertTrue(calculatedSalesTaxAmount.contains(expectedAmount), "Actual tax amount is not equal to the expected tax amount");
            allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

            //Generate invoice
            allSalesOrdersSecondPage.clickOnInvoiceTab();
            allSalesOrdersSecondPage.generateInvoice();
            allSalesOrdersPage.clickOnOKBtn();
            allSalesOrdersPage.clickOnOKPopUp();

            //Validate that tax amount and addresses are present in the log
            DFinanceXMLInquiryPage xmlInquiryPage = homePage.navigateToPage(
                    DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                    DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

            //Validate sales order response
            xmlInquiryPage.getDocumentID(salesOrderNumber);
            xmlInquiryPage.clickOnFirstResponse();
            String response = xmlInquiryPage.getLogResponseValue();
            assertTrue(response.contains("<TotalTax>13.0</TotalTax>"));

            //Validate retail transactions request
            xmlInquiryPage.getDocumentID("");
            xmlInquiryPage.selectDocType("Retail transactions");
            xmlInquiryPage.clickOnFirstRequest();
            String request = xmlInquiryPage.getLogRequestValue();
            assertTrue(request.contains(DRetailStores.QUEBEC.addressXML));
            assertTrue(request.contains(DRetailAddresses.CALGARY.addressXML));
        }
    }

    /**
     * @TestCase - CD365-4534
     * @Description - Create order from Quebec store to British Columbia address with discount and verify tax
     * @Author - Vivek Olumbe
     */
    @Test(groups = {"FO_Integration", "quebecToBritishColumbia"}, priority = 40)
    public void createQuebecToBritishColumbiaOrderTest(){
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
        DRetailShippingPage shippingPage = new DRetailShippingPage(driver);
        DRetailDiscountPage discountPage = new DRetailDiscountPage(driver);

        String expectedTax = "24.83";
        String expectedSubtotal = "199.99";

        //Select customer, and add items to cart
        allRetailSalesOrderPage.transactionButton();
        allRetailSalesOrderPage.addCustomer("Mathew Tolley");
        allRetailSalesOrderPage.clickProducts();
        allRetailSalesOrderPage.clickBrownLeopardprintButton();
        allRetailSalesOrderPage.clickBlackThickRimmedButton();

        //Add discount
        allRetailSalesOrderPage.clickDiscounts();
        discountPage.enterTotalDiscountPercent("16.67");

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
        assertEquals(actualSubtotal, expectedSubtotal, "Actual subtotal is not equal to the expected subtotal");

        //Pay cash and complete order
        allRetailSalesOrderPage.selectPayCash();
        allRetailSalesOrderPage.cashAccepted();
        allRetailSalesOrderPage.closeButton();

    }

    public class DRetailValidateQuebecToBritishColumbiaOrderTest extends DFinanceBaseTest {

        /**
         * @TestCase - CD365-4535
         * @Description - Validate Quebec to British Columbia order
         * @Author - Vivek Olumbe
         */
        @Test(groups = { "FO_Integration" }, dependsOnGroups = { "quebecToBritishColumbia" }, priority = 41)
        public void validateQuebecToBritishColumbiaOrderTest() {
            DFinanceHomePage homePage = new DFinanceHomePage(driver);
            DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);
            DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

            settingPage.selectCompany("USRT");

            //Navigate to All Sales Orders page
            DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
                    DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                    DFinanceModulePanelLink.ALL_SALES_ORDERS);

            allSalesOrdersPage.searchSalesOrderByCustomerName("Mathew Tolley");
            allSalesOrdersSecondPage.applyFilterOnStatus("Open order");
            allSalesOrdersSecondPage.clickOnLatestOrderCreated();

            String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

            //Validate Sales Tax amount
            allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
            allSalesOrdersPage.openSalesTaxCalculation();
            String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
            String expectedAmount = "24.83";
            assertTrue(calculatedSalesTaxAmount.contains(expectedAmount), "Actual tax amount is not equal to the expected tax amount");
            allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

            //Generate invoice
            allSalesOrdersSecondPage.clickOnInvoiceTab();
            allSalesOrdersSecondPage.generateInvoice();
            allSalesOrdersPage.clickOnOKBtn();
            allSalesOrdersPage.clickOnOKPopUp();

            //Validate that tax amount and addresses are present in the log
            DFinanceXMLInquiryPage xmlInquiryPage = homePage.navigateToPage(
                    DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                    DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

            //Validate sales order response
            xmlInquiryPage.getDocumentID(salesOrderNumber);
            xmlInquiryPage.clickOnFirstResponse();
            String response = xmlInquiryPage.getLogResponseValue();
            assertTrue(response.contains("<TotalTax>24.83</TotalTax>"));

            //Validate retail transactions request
            xmlInquiryPage.getDocumentID("");
            xmlInquiryPage.selectDocType("Retail transactions");
            xmlInquiryPage.clickOnFirstRequest();
            String request = xmlInquiryPage.getLogRequestValue();
            assertTrue(request.contains(DRetailStores.QUEBEC.addressXML));
            assertTrue(request.contains(DRetailAddresses.VANCOUVER.addressXML));
        }
    }
}
