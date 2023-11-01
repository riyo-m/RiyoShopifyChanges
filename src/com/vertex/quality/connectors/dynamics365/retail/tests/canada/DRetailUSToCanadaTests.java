package com.vertex.quality.connectors.dynamics365.retail.tests.canada;

import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.dynamics365.retail.enums.DRetailAddresses;
import com.vertex.quality.connectors.dynamics365.retail.enums.DRetailStores;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailShippingPage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DRetailUSToCanadaTests extends DRetailBaseTest {

    @BeforeClass(alwaysRun = true)
    public void setupTest() {
        DRetailBaseTest.STORE = DRetailStores.HOUSTON;
    }

    /**
     * @TestCase - CD365-4540
     * @Description - Create order from Houston store to Alberta address and verify tax
     * @Author - Vivek Olumbe
     */
    @Test(groups = {"FO_Integration", "houstonToAlberta"}, priority = 50)
    public void createHoustonToAlbertaOrderTest(){
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

    public class DRetailValidateHoustonToAlbertaOrderTest extends DFinanceBaseTest {

        /**
         * @TestCase - CD365-4541
         * @Description - Validate Houston to Alberta order
         * @Author - Vivek Olumbe
         */
        @Test(groups = { "FO_Integration" }, dependsOnGroups = { "houstonToAlberta" }, priority = 51)
        public void validateHoustonToAlbertaOrderTest() {
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
            assertTrue(request.contains(DRetailStores.HOUSTON.addressXML));
            assertTrue(request.contains(DRetailAddresses.CALGARY.addressXML));
        }
    }

    /**
     * @TestCase - CD365-4542
     * @Description - Create order from Houston store to Ontario address and verify tax
     * @Author - Vivek Olumbe
     */
    @Test(groups = {"FO_Integration", "houstonToOntario"}, priority = 52)
    public void createHoustonToOntarioOrderTest() {
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
        DRetailShippingPage shippingPage = new DRetailShippingPage(driver);

        String expectedTax = "36.40";
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

    public class DRetailValidateHoustonToOntarioOrderTest extends DFinanceBaseTest {

        /**
         * @TestCase - CD365-4543
         * @Description - Validate Houston to Ontario order
         * @Author - Vivek Olumbe
         */
        @Test(groups = { "FO_Integration" }, dependsOnGroups = { "houstonToOntario" }, priority = 53)
        public void validateHoustonToOntarioOrderTest() {
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
            String expectedAmount = "36.40";
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
            assertTrue(response.contains("<TotalTax>36.4</TotalTax>"));

            //Validate retail transactions request
            xmlInquiryPage.getDocumentID("");
            xmlInquiryPage.selectDocType("Retail transactions");
            xmlInquiryPage.clickOnFirstRequest();
            String request = xmlInquiryPage.getLogRequestValue();
            assertTrue(request.contains(DRetailStores.HOUSTON.addressXML));
            assertTrue(request.contains(DRetailAddresses.TORONTO.addressXML));
        }
    }
}
