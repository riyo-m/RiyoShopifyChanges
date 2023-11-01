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

public class DRetailOntarioStoreTests extends DRetailBaseTest {

    @BeforeClass(alwaysRun = true)
    public void setupTest() {
        DRetailBaseTest.STORE = DRetailStores.ONTARIO;
    }

    @AfterClass(alwaysRun = true)
    public void teardownTest() {
        DRetailBaseTest.STORE = DRetailStores.HOUSTON;
    }

    /**
     * @TestCase - CD365-4470
     * @Description - Create order from Ontario store to Ontario address and verify tax
     * @Author - Vivek Olumbe
     */
    @Test(groups = {"FO_Integration", "ontarioToOntario"}, priority = 42)
    public void createOntarioToOntarioOrderTest(){
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
        DRetailShippingPage shippingPage = new DRetailShippingPage(driver);

        String expectedTax = "33.80";
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
        shippingPage.selectShipToAddress(DRetailAddresses.TORONTO.value);
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

    public class DRetailValidateOntarioOrderTest extends DFinanceBaseTest {

        /**
         * @TestCase - CD365-4471
         * @Description - validate Ontario order and HST tax
         * @Author - Vivek Olumbe
         */
        @Test(groups = { "FO_Integration" }, dependsOnGroups = { "ontarioToOntario" }, priority = 43)
        public void validateOntarioOrderTest() {
            DFinanceHomePage homePage = new DFinanceHomePage(driver);
            DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);

            settingPage.selectCompany("USRT");

            DFinanceXMLInquiryPage xmlInquiryPage = homePage.navigateToPage(
                    DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                    DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

            //Validate that tax amount and addresses are present in the log
            xmlInquiryPage.clickOnFirstResponse();
            String response = xmlInquiryPage.getLogResponseValue();
            assertTrue(response.contains("<TotalTax>33.8</TotalTax>"));

            xmlInquiryPage.selectDocType("Retail transactions");
            xmlInquiryPage.clickOnFirstRequest();
            String request = xmlInquiryPage.getLogRequestValue();
            assertTrue(request.contains(DRetailStores.ONTARIO.addressXML));
            assertTrue(request.contains(DRetailAddresses.TORONTO.addressXML));
        }
    }

    /**
     * @TestCase - CD365-4513
     * @Description - Create order from Ontario store to US address and verify tax
     * @Author - Vivek Olumbe
     */
    @Test(groups = {"FO_Integration", "ontarioToUS"}, priority = 44)
    public void createOntarioToUSOrderTest() {
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
        DRetailShippingPage shippingPage = new DRetailShippingPage(driver);

        String expectedTax = "23.10";
        String expectedSubtotal = "280.00";

        //Select customer, and add items to cart
        allRetailSalesOrderPage.transactionButton();
        allRetailSalesOrderPage.addCustomer("Mathew Tolley");
        allRetailSalesOrderPage.clickProducts();
        allRetailSalesOrderPage.clickBrownAviatorButton();
        allRetailSalesOrderPage.clickPinkThickRimmedButton();

        //Create and ship an order
        allRetailSalesOrderPage.clickOrders();
        allRetailSalesOrderPage.selectShipAll();

        //Select ship to address
        shippingPage.selectShipToAddress(DRetailAddresses.HOUSTON.value);
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

    public class DRetailValidateOntarioToUSOrderTest extends DFinanceBaseTest {

        /**
         * @TestCase - CD365-4514
         * @Description - Validate Ontario to US Order
         * @Author - Vivek Olumbe
         */
        @Test(groups = { "FO_Integration" }, dependsOnGroups = { "ontarioToUS" }, priority = 45)
        public void validateOntarioToUSOrderTest() {
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
            String expectedAmount = "23.10";
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
            assertTrue(response.contains("<TotalTax>23.1</TotalTax>"));

            //Validate retail transactions request
            xmlInquiryPage.getDocumentID("");
            xmlInquiryPage.selectDocType("Retail transactions");
            xmlInquiryPage.clickOnFirstRequest();
            String request = xmlInquiryPage.getLogRequestValue();
            assertTrue(request.contains(DRetailStores.ONTARIO.addressXML));
            assertTrue(request.contains(DRetailAddresses.HOUSTON.addressXML));
        }
    }

    /**
     * @TestCase - CD365-4538
     * @Description - Create order from Ontario store to Quebec address with discounts and verify tax
     * @Author - Vivek Olumbe
     */
    @Test(groups = {"FO_Integration", "ontarioToQuebec"}, priority = 46)
    public void createOntarioToQuebecOrderTest() {
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
        DRetailShippingPage shippingPage = new DRetailShippingPage(driver);
        DRetailDiscountPage discountPage = new DRetailDiscountPage(driver);

        String expectedTax = "38.19";
        String expectedSubtotal = "255.00";

        //Select customer, and add items to cart
        allRetailSalesOrderPage.transactionButton();
        allRetailSalesOrderPage.addCustomer("Mathew Tolley");
        allRetailSalesOrderPage.clickProducts();
        allRetailSalesOrderPage.clickBlackBoldFramedButton();
        allRetailSalesOrderPage.clickBrownAviatorButton();

        //Add discounts
        allRetailSalesOrderPage.clickDiscounts();
        discountPage.enterLineDiscountPercent("10");
        discountPage.enterTotalDiscountAmount("20");

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
        assertEquals(actualSubtotal, expectedSubtotal, "Actual subtotal is not equal to the expected subtotal");

        //Pay cash and complete order
        allRetailSalesOrderPage.selectPayCash();
        allRetailSalesOrderPage.cashAccepted();
        allRetailSalesOrderPage.closeButton();

    }

    public class DRetailValidateOntarioToQuebecOrderTest extends DFinanceBaseTest {

        /**
         * @TestCase - CD365-4539
         * @Description - Validate Ontario to Quebec Order
         * @Author - Vivek Olumbe
         */
        @Test(groups = { "FO_Integration" }, dependsOnGroups = { "ontarioToQuebec" }, priority = 47)
        public void validateOntarioToQuebecOrderTest() {
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
            String expectedAmount = "38.19";
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
            assertTrue(response.contains("<TotalTax>38.19</TotalTax>"));

            //Validate retail transactions request
            xmlInquiryPage.getDocumentID("");
            xmlInquiryPage.selectDocType("Retail transactions");
            xmlInquiryPage.clickOnFirstRequest();
            String request = xmlInquiryPage.getLogRequestValue();
            assertTrue(request.contains(DRetailStores.ONTARIO.addressXML));
            assertTrue(request.contains(DRetailAddresses.MONTREAL.addressXML));
        }
    }
}
