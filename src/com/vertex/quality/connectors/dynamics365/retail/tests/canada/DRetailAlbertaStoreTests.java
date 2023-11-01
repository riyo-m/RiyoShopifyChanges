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

public class DRetailAlbertaStoreTests extends DRetailBaseTest {

    @BeforeClass(alwaysRun = true)
    public void setupTest() {
        DRetailBaseTest.STORE = DRetailStores.ALBERTA;
    }

    @AfterClass(alwaysRun = true)
    public void teardownTest() {
        DRetailBaseTest.STORE = DRetailStores.HOUSTON;
    }

    /**
     * @TestCase - CD365-4472
     * @Description - Create order from Alberta store to Alberta address and verify tax
     * @Author - Vivek Olumbe
     */
    @Test(groups = {"FO_Integration", "albertaToAlberta"}, priority = 28)
    public void createAlbertaToAlbertaOrderTest(){
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
        DRetailShippingPage shippingPage = new DRetailShippingPage(driver);

        String expectedTax = "13.00";
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

    public class DRetailValidateAlbertaOrderTest extends DFinanceBaseTest {

        /**
         * @TestCase - CD365-4473
         * @Description - Validate Alberta order
         * @Author - Vivek Olumbe
         */
        @Test(groups = { "FO_Integration" }, dependsOnGroups = { "albertaToAlberta" }, priority = 29)
        public void validateAlbertaOrderTest() {
            DFinanceHomePage DFinanceHomePage = new DFinanceHomePage(driver);
            DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);

            settingPage.selectCompany("USRT");

            DFinanceXMLInquiryPage xmlInquiryPage = DFinanceHomePage.navigateToPage(
                    DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                    DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

            //Validate that tax amount and addresses are present in the log
            xmlInquiryPage.clickOnFirstResponse();
            String response = xmlInquiryPage.getLogResponseValue();
            assertTrue(response.contains("<TotalTax>13.0</TotalTax>"));

            xmlInquiryPage.selectDocType("Retail transactions");
            xmlInquiryPage.clickOnFirstRequest();
            String request = xmlInquiryPage.getLogRequestValue();
            assertTrue(request.contains(DRetailStores.ALBERTA.addressXML));
            assertTrue(request.contains(DRetailAddresses.CALGARY.addressXML));
        }
    }

    /**
     * @TestCase - CD365-4511
     * @Description - Create order from Alberta store to US address and verify tax
     * @Author - Vivek Olumbe
     */
    @Test(groups = {"FO_Integration", "albertaToUS"}, priority = 30)
    public void createAlbertaToUSOrderTest(){
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
        DRetailShippingPage shippingPage = new DRetailShippingPage(driver);

        String addressName = "Mathew Tolley";
        String expectedTax = "22.43";
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
        shippingPage.selectShipToAddress(addressName);
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

    public class DRetailValidateAlbertaToUSOrderTest extends DFinanceBaseTest {

        /**
         * @TestCase - CD365-4512
         * @Description - Validate Alberta to US order
         * @Author - Vivek Olumbe
         */
        @Test(groups = { "FO_Integration" }, dependsOnGroups = { "albertaToUS" }, priority = 31)
        public void validateAlbertaToUSOrderTest() {
            DFinanceHomePage homePage = new DFinanceHomePage(driver);
            DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);
            DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
            String expectedDestinationXML = "<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                    "\t\t\t\t<StreetAddress1>456 First Avenue</StreetAddress1>\n" +
                    "\t\t\t\t<City>Alameda</City>\n" +
                    "\t\t\t\t<MainDivision>CA</MainDivision>\n" +
                    "\t\t\t\t<PostalCode>94115</PostalCode>\n" +
                    "\t\t\t\t<Country>USA</Country>\n" +
                    "\t\t\t</Destination>";

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
            String expectedAmount = "22.43";
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
            assertTrue(response.contains("<TotalTax>22.43</TotalTax>"));

            //Validate retail transactions request
            xmlInquiryPage.getDocumentID("");
            xmlInquiryPage.selectDocType("Retail transactions");
            xmlInquiryPage.clickOnFirstRequest();
            String request = xmlInquiryPage.getLogRequestValue();
            assertTrue(request.contains(DRetailStores.ALBERTA.addressXML));
            assertTrue(request.contains(expectedDestinationXML));
        }
    }

    /**
     * @TestCase - CD365-4530
     * @Description - Create order from Alberta store to Quebec address and verify tax
     * @Author - Vivek Olumbe
     */
    @Test(groups = {"FO_Integration", "albertaToQuebec"}, priority = 32)
    public void createAlbertaToQuebecOrderTest(){
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
        DRetailShippingPage shippingPage = new DRetailShippingPage(driver);

        String expectedTax = "41.93";
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

    public class DRetailValidateAlbertaToQuebecOrderTest extends DFinanceBaseTest {

        /**
         * @TestCase - CD365-4531
         * @Description - Validate Alberta to Quebec order
         * @Author - Vivek Olumbe
         */
        @Test(groups = { "FO_Integration" }, dependsOnGroups = { "albertaToQuebec" }, priority = 33)
        public void validateAlbertaToQuebecOrderTest() {
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
            String expectedAmount = "41.93";
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
            assertTrue(response.contains("<TotalTax>41.93</TotalTax>"));

            //Validate retail transactions request
            xmlInquiryPage.getDocumentID("");
            xmlInquiryPage.selectDocType("Retail transactions");
            xmlInquiryPage.clickOnFirstRequest();
            String request = xmlInquiryPage.getLogRequestValue();
            assertTrue(request.contains(DRetailStores.ALBERTA.addressXML));
            assertTrue(request.contains(DRetailAddresses.MONTREAL.addressXML));
        }
    }

    /**
     * @TestCase - CD365-4536
     * @Description - Create order from Alberta store to Ontario address with discounts and verify tax
     * @Author - Vivek Olumbe
     */
    @Test(groups = {"FO_Integration", "albertaToOntario"}, priority = 34)
    public void createAlbertaToOntarioOrderTest() {
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
        DRetailShippingPage shippingPage = new DRetailShippingPage(driver);
        DRetailDiscountPage discountPage = new DRetailDiscountPage(driver);

        String expectedTax = "32.10";
        String expectedSubtotal = "240.00";

        //Select customer, and add items to cart
        allRetailSalesOrderPage.transactionButton();
        allRetailSalesOrderPage.addCustomer("Mathew Tolley");
        allRetailSalesOrderPage.clickProducts();
        allRetailSalesOrderPage.clickBrownAviatorButton();
        allRetailSalesOrderPage.clickBlackThickRimmedButton();

        //Add discounts
        allRetailSalesOrderPage.clickDiscounts();
        discountPage.enterLineDiscountAmount("5.00");
        discountPage.enterTotalDiscountAmount("15.00");

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
        assertEquals(actualSubtotal, expectedSubtotal, "Actual subtotal is not equal to the expected subtotal");

        //Pay cash and complete order
        allRetailSalesOrderPage.selectPayCash();
        allRetailSalesOrderPage.cashAccepted();
        allRetailSalesOrderPage.closeButton();

    }

    public class DRetailValidateAlbertaToOntarioOrderTest extends DFinanceBaseTest {

        /**
         * @TestCase - CD365-4537
         * @Description - Validate Alberta to Ontario order
         * @Author - Vivek Olumbe
         */
        @Test(groups = { "FO_Integration" }, dependsOnGroups = { "albertaToOntario" }, priority = 35)
        public void validateAlbertaToOntarioOrderTest() {
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
            String expectedAmount = "32.10";
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
            assertTrue(response.contains("<TotalTax>32.1</TotalTax>"));

            //Validate retail transactions request
            xmlInquiryPage.getDocumentID("");
            xmlInquiryPage.selectDocType("Retail transactions");
            xmlInquiryPage.clickOnFirstRequest();
            String request = xmlInquiryPage.getLogRequestValue();
            assertTrue(request.contains(DRetailStores.ALBERTA.addressXML));
            assertTrue(request.contains(DRetailAddresses.TORONTO.addressXML));
        }
    }
}
