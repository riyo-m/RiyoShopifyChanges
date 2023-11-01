package com.vertex.quality.connectors.dynamics365.commerce.tests;

import com.vertex.quality.connectors.dynamics365.commerce.enums.CommerceDeliveryOptions;
import com.vertex.quality.connectors.dynamics365.commerce.pages.DCommerceCartPage;
import com.vertex.quality.connectors.dynamics365.commerce.pages.DCommerceCheckoutPage;
import com.vertex.quality.connectors.dynamics365.commerce.pages.DCommerceHomePage;
import com.vertex.quality.connectors.dynamics365.commerce.tests.base.DCommerceBaseTest;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceAllSalesOrdersPage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceAllSalesOrdersSecondPage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceHomePage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceXMLInquiryPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailReturnTransactionPage;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Contains test scenarios for e-commerce orders and returning in retail
 */
@Listeners(TestRerunListener.class)
public class DCommerceOrderReturnTests extends DCommerceBaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        DCommerceCartPage cartPage = homePage.navigateToCartPage();
        cartPage.removeAllProducts();
        cartPage.navigateToHomePage();
    }

    @AfterMethod(alwaysRun = true)
    public void teardownTest() {
        System.out.println("Teardown");
    }

    /**
     * @TestCase CD365-2872
     * @Description Add two products to cart, place order with standard shipping, return both products,
     *              and verify total and tax amounts in E-Commerce, F&O, and Retail
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderReturnAllTest() {
        //================ Data Declaration ===========================
        String productName1 = "Amber Sports Sunglasses";
        String productName2 = "Gold Round Sunglasses";

        String expectedSubtotal = "380.00";
        String expectedShippingCost = "Free";
        String expectedTax = "25.18";
        String expectedTotal = "405.18";
        String expectedTotalXML = "<SubTotal>380.0</SubTotal>\n" +
                "\t\t<Total>405.18</Total>\n" +
                "\t\t<TotalTax>25.18</TotalTax>";

        String returnQuantity = "1";
        String expectedTotalXMLFromReturn = "<SubTotal>-380.0</SubTotal>\n" +
                "\t\t<Total>-405.18</Total>\n" +
                "\t\t<TotalTax>-25.18</TotalTax>";
        //================ Place Order in e-commerce ===========================

        // Add products
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName1);
        homePage.clickAddToBagButton();

        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName2);
        homePage.clickAddToBagButton();

        DCommerceCartPage cartPage = homePage.navigateToCartPage();
        DCommerceCheckoutPage checkoutPage = cartPage.navigateToCheckoutPage();
        checkoutPage.selectShippingAddress("NJ Address");
        checkoutPage.selectDeliveryOption(CommerceDeliveryOptions.STANDARD);
        checkoutPage.enterDummyCardDetails();
        checkoutPage.clickSaveAndContinue();

        // Verify amounts
        String subtotalAmount = checkoutPage.getSubtotalAmount();
        String shippingCost = checkoutPage.getShippingCost();
        String taxAmount = checkoutPage.getTaxAmount();
        String totalAmount = checkoutPage.getTotalAmount();

        assertEquals(subtotalAmount, expectedSubtotal, "E-Commerce subtotal validation failed");
        assertEquals(shippingCost, expectedShippingCost, "E-Commerce shipping cost validation failed");
        assertEquals(taxAmount, expectedTax, "E-commerce tax validation failed");
        assertEquals(totalAmount, expectedTotal, "E-commerce total validation failed");

        checkoutPage.clickPlaceOrder();

        //================ Navigate to D365 Finance ===========================
        DFinanceHomePage financeHomePage = navigateToDFinanceHomePage();
        DFinanceXMLInquiryPage xmlInquiryPage = financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        // Verify XML inquiry
        xmlInquiryPage.sortByNewest();
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");

        // Run P-0001 Job and synchronize orders for store
        runBatchJob();
        synchronizeOrders();

        //Validate tax amount in sales order
        DFinanceAllSalesOrdersPage allSalesOrdersPage = navigateToLatestOpenSalesOrder("Test Automation");
        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();
        String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        assertEquals(calculatedSalesTaxAmount, expectedTax);
        String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
        assertEquals(actualSalesTaxAmount, expectedTax);
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        //Post the invoice
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        allSalesOrdersSecondPage.clickOnInvoiceTab();
        allSalesOrdersSecondPage.generateInvoice();
        allSalesOrdersPage.clickOnOKBtn();
        allSalesOrdersPage.clickOnOKPopUp();

        // Validate XML inquiry for sales order
        xmlInquiryPage = financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");

        //================ Navigate to D365 Retail ===========================
        DRetailHomePage retailHomePage = navigateToDRetailHomePage();
        DRetailReturnTransactionPage returnTransactionPage = retailHomePage.goToReturnTransactionPage();
        returnTransactionPage.searchOrder(salesOrderNumber);
        returnTransactionPage.clickOnReturnBarButton();

        //Return both products
        returnTransactionPage.clickOnLine(productName1);
        returnTransactionPage.enterReturnQuantity(returnQuantity);
        returnTransactionPage.selectReturnReason();

        returnTransactionPage.clickOnLine(productName2);
        returnTransactionPage.enterReturnQuantity(returnQuantity);
        returnTransactionPage.selectReturnReason();

        returnTransactionPage.clickOnReturnBarButton();

        // Pay cash
        DRetailAllSalesOrderPage orderPage = new DRetailAllSalesOrderPage(driver);
        orderPage.selectPayCash();
        orderPage.cashAccepted();
        orderPage.closeButton();

        //================ Navigate to D365 Finance ===========================
        navigateToDFinanceHomePage();
        financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        // Verify generated invoice
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXMLFromReturn), "Subtotal, total tax, total XML validation for invoice failed");

        // Verify retail transaction
        xmlInquiryPage.selectDocType("Retail transactions");
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXMLFromReturn), "Subtotal, total tax, total XML validation for retail transaction failed");
    }

    /**
     * @TestCase CD365-2871
     * @Description Add two products to cart, place order with standard shipping, return second product,
     *              and verify total and tax amounts in E-Commerce, F&O, and Retail
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderReturnPartiallyTest() {
        //================ Data Declaration ===========================
        String productName1 = "Amber Sports Sunglasses";
        String productName2 = "Gold Round Sunglasses";

        String expectedSubtotal = "380.00";
        String expectedShippingCost = "Free";
        String expectedTax = "25.18";
        String expectedTotal = "405.18";
        String expectedTotalXML = "<SubTotal>380.0</SubTotal>\n" +
                "\t\t<Total>405.18</Total>\n" +
                "\t\t<TotalTax>25.18</TotalTax>";

        String returnQuantity = "1";
        String expectedTotalXMLFromReturn = "<SubTotal>-130.0</SubTotal>\n" +
                "\t\t<Total>-138.61</Total>\n" +
                "\t\t<TotalTax>-8.61</TotalTax>";
        //================ Place Order in e-commerce ===========================

        // Add products
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName1);
        homePage.clickAddToBagButton();

        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName2);
        homePage.clickAddToBagButton();

        DCommerceCartPage cartPage = homePage.navigateToCartPage();
        DCommerceCheckoutPage checkoutPage = cartPage.navigateToCheckoutPage();
        checkoutPage.selectShippingAddress("NJ Address");
        checkoutPage.selectDeliveryOption(CommerceDeliveryOptions.STANDARD);
        checkoutPage.enterDummyCardDetails();
        checkoutPage.clickSaveAndContinue();

        // Verify amounts
        String subtotalAmount = checkoutPage.getSubtotalAmount();
        String shippingCost = checkoutPage.getShippingCost();
        String taxAmount = checkoutPage.getTaxAmount();
        String totalAmount = checkoutPage.getTotalAmount();

        assertEquals(subtotalAmount, expectedSubtotal, "E-Commerce subtotal validation failed");
        assertEquals(shippingCost, expectedShippingCost, "E-Commerce shipping cost validation failed");
        assertEquals(taxAmount, expectedTax, "E-commerce tax validation failed");
        assertEquals(totalAmount, expectedTotal, "E-commerce total validation failed");

        checkoutPage.clickPlaceOrder();

        //================ Navigate to D365 Finance ===========================
        DFinanceHomePage financeHomePage = navigateToDFinanceHomePage();
        DFinanceXMLInquiryPage xmlInquiryPage = financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        // Verify XML inquiry
        xmlInquiryPage.sortByNewest();
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");

        // Run P-0001 Job and synchronize orders for store
        runBatchJob();
        synchronizeOrders();

        //Validate tax amount in sales order
        DFinanceAllSalesOrdersPage allSalesOrdersPage = navigateToLatestOpenSalesOrder("Test Automation");
        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();
        String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        assertEquals(calculatedSalesTaxAmount, expectedTax);
        String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
        assertEquals(actualSalesTaxAmount, expectedTax);
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        //Post the invoice
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        allSalesOrdersSecondPage.clickOnInvoiceTab();
        allSalesOrdersSecondPage.generateInvoice();
        allSalesOrdersPage.clickOnOKBtn();
        allSalesOrdersPage.clickOnOKPopUp();

        // Validate XML inquiry for sales order
        xmlInquiryPage = financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");

        //================ Navigate to D365 Retail ===========================
        DRetailHomePage retailHomePage = navigateToDRetailHomePage();
        DRetailReturnTransactionPage returnTransactionPage = retailHomePage.goToReturnTransactionPage();
        returnTransactionPage.searchOrder(salesOrderNumber);
        returnTransactionPage.clickOnReturnBarButton();

        //Return second product
        returnTransactionPage.clickOnLine(productName2);
        returnTransactionPage.enterReturnQuantity(returnQuantity);
        returnTransactionPage.selectReturnReason();

        returnTransactionPage.clickOnReturnBarButton();

        // Pay cash
        DRetailAllSalesOrderPage orderPage = new DRetailAllSalesOrderPage(driver);
        orderPage.selectPayCash();
        orderPage.cashAccepted();
        orderPage.closeButton();

        //================ Navigate to D365 Finance ===========================
        navigateToDFinanceHomePage();
        financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        // Verify generated invoice
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXMLFromReturn), "Subtotal, total tax, total XML validation for invoice failed");

        // Verify retail transaction
        xmlInquiryPage.selectDocType("Retail transactions");
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXMLFromReturn), "Subtotal, total tax, total XML validation for retail transaction failed");
    }

    /**
     * @TestCase CD365-2873
     * @Description Add two products with multiple quantities to cart, place order with standard shipping,
     *              return partial quantities, and verify total and tax amounts in E-Commerce, F&O, and Retail
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderReturnLessQuantityTest() {
        //================ Data Declaration ===========================
        String productName1 = "Amber Sports Sunglasses";
        String productName2 = "Gold Round Sunglasses";

        String expectedSubtotal = "1,140.00";
        String expectedShippingCost = "Free";
        String expectedTax = "75.53";
        String expectedTotal = "1,215.53";
        String expectedTotalXML = "<SubTotal>1140.0</SubTotal>\n" +
                "\t\t<Total>1215.53</Total>\n" +
                "\t\t<TotalTax>75.53</TotalTax>";

        String returnQuantityProduct1 = "1";
        String returnQuantityProduct2 = "2";
        String expectedTotalXMLFromReturn = "<SubTotal>-510.0</SubTotal>\n" +
                "\t\t<Total>-543.79</Total>\n" +
                "\t\t<TotalTax>-33.79</TotalTax>";
        //================ Place Order in e-commerce ===========================

        // Add products
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName1);
        homePage.setQuantity("3");
        homePage.clickAddToBagButton();

        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName2);
        homePage.setQuantity("3");
        homePage.clickAddToBagButton();

        DCommerceCartPage cartPage = homePage.navigateToCartPage();
        DCommerceCheckoutPage checkoutPage = cartPage.navigateToCheckoutPage();
        checkoutPage.selectShippingAddress("NJ Address");
        checkoutPage.selectDeliveryOption(CommerceDeliveryOptions.STANDARD);
        checkoutPage.enterDummyCardDetails();
        checkoutPage.clickSaveAndContinue();

        // Verify amounts
        String subtotalAmount = checkoutPage.getSubtotalAmount();
        String shippingCost = checkoutPage.getShippingCost();
        String taxAmount = checkoutPage.getTaxAmount();
        String totalAmount = checkoutPage.getTotalAmount();

        assertEquals(subtotalAmount, expectedSubtotal, "E-Commerce subtotal validation failed");
        assertEquals(shippingCost, expectedShippingCost, "E-Commerce shipping cost validation failed");
        assertEquals(taxAmount, expectedTax, "E-commerce tax validation failed");
        assertEquals(totalAmount, expectedTotal, "E-commerce total validation failed");

        checkoutPage.clickPlaceOrder();

        //================ Navigate to D365 Finance ===========================
        DFinanceHomePage financeHomePage = navigateToDFinanceHomePage();
        DFinanceXMLInquiryPage xmlInquiryPage = financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        // Verify XML inquiry
        xmlInquiryPage.sortByNewest();
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");

        // Run P-0001 Job and synchronize orders for store
        runBatchJob();
        synchronizeOrders();

        //Validate tax amount in sales order
        DFinanceAllSalesOrdersPage allSalesOrdersPage = navigateToLatestOpenSalesOrder("Test Automation");
        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();
        String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        assertEquals(calculatedSalesTaxAmount, expectedTax);
        String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
        assertEquals(actualSalesTaxAmount, expectedTax);
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        //Post the invoice
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        allSalesOrdersSecondPage.clickOnInvoiceTab();
        allSalesOrdersSecondPage.generateInvoice();
        allSalesOrdersPage.clickOnOKBtn();
        allSalesOrdersPage.clickOnOKPopUp();

        // Validate XML inquiry for sales order
        xmlInquiryPage = financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");

        //================ Navigate to D365 Retail ===========================
        DRetailHomePage retailHomePage = navigateToDRetailHomePage();
        DRetailReturnTransactionPage returnTransactionPage = retailHomePage.goToReturnTransactionPage();
        returnTransactionPage.searchOrder(salesOrderNumber);
        returnTransactionPage.clickOnReturnBarButton();

        //Return partial quantities
        returnTransactionPage.clickOnLine(productName1);
        returnTransactionPage.enterReturnQuantity(returnQuantityProduct1);
        returnTransactionPage.selectReturnReason();

        returnTransactionPage.clickOnLine(productName2);
        returnTransactionPage.enterReturnQuantity(returnQuantityProduct2);
        returnTransactionPage.selectReturnReason();

        returnTransactionPage.clickOnReturnBarButton();

        // Pay cash
        DRetailAllSalesOrderPage orderPage = new DRetailAllSalesOrderPage(driver);
        orderPage.selectPayCash();
        orderPage.cashAccepted();
        orderPage.closeButton();

        //================ Navigate to D365 Finance ===========================
        navigateToDFinanceHomePage();
        financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        // Verify generated invoice
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXMLFromReturn), "Subtotal, total tax, total XML validation for invoice failed");

        // Verify retail transaction
        xmlInquiryPage.selectDocType("Retail transactions");
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXMLFromReturn), "Subtotal, total tax, total XML validation for retail transaction failed");
    }
}
