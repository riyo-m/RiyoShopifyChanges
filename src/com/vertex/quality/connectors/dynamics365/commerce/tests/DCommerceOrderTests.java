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
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceHomePage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceXMLInquiryPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailOrderFulfillmentPage;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Contains test scenarios for orders in e-commerce
 */
@Listeners(TestRerunListener.class)
public class DCommerceOrderTests extends DCommerceBaseTest {

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
     * @TestCase CD365-2821
     * @Description Add a product to cart, place order with standard shipping,
     *              and verify total and tax amounts in E-Commerce and F&O
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderOneProductStandardShippingTest() {
        //================ Data Declaration ===========================
        String expectedSubtotal = "130.00";
        String expectedShippingCost = "6.95";
        String expectedTax = "9.07";
        String expectedTotal = "146.02";
        String expectedTotalXML = "<SubTotal>136.95</SubTotal>\n" +
                "\t\t<Total>146.02</Total>\n" +
                "\t\t<TotalTax>9.07</TotalTax>";

        //================ Place Order in e-commerce ===========================
        // Add product to bag
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Menswear", "All");
        homePage.clickProduct("High Cut Leather Sneaker");
        homePage.selectSize("10");
        homePage.clickAddToBagButton();

        // Checkout
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

        // Validate XML inquiry for sales order
        xmlInquiryPage = financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");

    }

    /**
     * @TestCase CD365-2822
     * @Description Add a product to cart, checkout as guest, verify total and tax amounts in E-Commerce and F&O
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderGuestCheckoutTest() {
        //================ Data Declaration ===========================
        String expectedSubtotal = "250.00";
        String expectedShippingCost = "Free";
        String expectedTax = "15.00";
        String expectedTotal = "265.00";
        String expectedTotalXML = "<SubTotal>250.0</SubTotal>\n" +
                "\t\t<Total>265.0</Total>\n" +
                "\t\t<TotalTax>15.0</TotalTax>";
        //================ Place Order in e-commerce ===========================
        // Add product to bag
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.signOutOfAccount();
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct("Amber Sports Sunglasses");
        homePage.clickAddToBagButton();

        // Checkout
        DCommerceCartPage cartPage = homePage.navigateToCartPage();
        DCommerceCheckoutPage checkoutPage = cartPage.navigateToCheckoutPageAsGuest();
        checkoutPage.enterGuestAddress("Guest", "3000 Market Street", "Philadelphia", "PA", "19104");
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

        checkoutPage.enterGuestEmailAddress();
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
        DFinanceAllSalesOrdersPage allSalesOrdersPage = navigateToLatestOpenSalesOrder("Default Online Customer");
        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();
        String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        assertEquals(calculatedSalesTaxAmount, expectedTax);
        String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
        assertEquals(actualSalesTaxAmount, expectedTax);
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        // Validate XML inquiry for sales order
        xmlInquiryPage = financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");

    }

    /**
     * @TestCase CD365-2823
     * @Description Add two products to cart, place order with pick up for all products,
     *              and verify total and tax amounts in E-Commerce and F&O
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderPickUpAllProductsTest() {
        //================ Data Declaration ===========================
        String expectedSubtotal = "380.00";
        String expectedTax = "31.35";
        String expectedTotal = "411.35";
        String expectedTotalXML = "<SubTotal>380.0</SubTotal>\n" +
                "\t\t<Total>411.35</Total>\n" +
                "\t\t<TotalTax>31.35</TotalTax>";

        //================ Place Order in e-commerce ===========================
        // Add products to bag
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct("Amber Sports Sunglasses");
        homePage.clickAddToBagButton();

        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct("Gold Round Sunglasses");
        homePage.clickAddToBagButton();

        // Click pick up for both products
        DCommerceCartPage cartPage = homePage.navigateToCartPage();
        cartPage.pickProductUpInStore( "Houston");
        cartPage.pickProductUpInStore( "Houston");

        // Checkout
        DCommerceCheckoutPage checkoutPage = cartPage.navigateToCheckoutPage();
        checkoutPage.enterDummyCardDetails();
        checkoutPage.enterBillingAddress("Phila", "3000 Market Street", "Philadelphia", "PA", "19104", "USA");
        checkoutPage.clickSaveAndContinue();

        // Verify amounts
        String subtotalAmount = checkoutPage.getSubtotalAmount();
        String taxAmount = checkoutPage.getTaxAmount();
        String totalAmount = checkoutPage.getTotalAmount();

        assertEquals(subtotalAmount, expectedSubtotal, "E-Commerce subtotal validation failed");
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

        // Validate XML inquiry for sales order
        xmlInquiryPage = financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");

    }

    /**
     * @TestCase CD365-2837
     * @Description Add two products to cart, place order with standard shipping,
     *              and verify total and tax amounts in E-Commerce and F&O
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderShipAllProductsTest() {
        //================ Data Declaration ===========================
        String expectedSubtotal = "380.00";
        String expectedShippingCost = "Free";
        String expectedTax = "25.18";
        String expectedTotal = "405.18";
        String expectedTotalXML = "<SubTotal>380.0</SubTotal>\n" +
                "\t\t<Total>405.18</Total>\n" +
                "\t\t<TotalTax>25.18</TotalTax>";

        //================ Place Order in e-commerce ===========================
        // Add products to bag
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct("Amber Sports Sunglasses");
        homePage.clickAddToBagButton();

        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct("Gold Round Sunglasses");
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

        // Validate XML inquiry for sales order
        xmlInquiryPage = financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");

    }

    /**
     * @TestCase CD365-2824
     * @Description Add two products to cart, place order with standard overnight shipping,
     *              and verify total and tax amounts in E-Commerce and F&O
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderTwoProductsOvernightShippingTest() {
        //================ Data Declaration ===========================
        String expectedSubtotal = "292.00";
        String expectedShippingCost = "15.00";
        String expectedTax = "20.34";
        String expectedTotal = "327.34";
        String expectedTotalXML = "<SubTotal>307.0</SubTotal>\n" +
                "\t\t<Total>327.34</Total>\n" +
                "\t\t<TotalTax>20.34</TotalTax>";

        //================ Place Order in e-commerce ===========================
        // Add products to bag
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct("Amber Sports Sunglasses");
        homePage.clickAddToBagButton();

        homePage.navigateToCategory("Accessories", "Ties");
        homePage.clickProduct("Black Skinny Tie");
        homePage.clickAddToBagButton();

        // Checkout
        DCommerceCartPage cartPage = homePage.navigateToCartPage();
        DCommerceCheckoutPage checkoutPage = cartPage.navigateToCheckoutPage();
        checkoutPage.selectShippingAddress("NJ Address");
        checkoutPage.selectDeliveryOption(CommerceDeliveryOptions.OVERNIGHT);
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

        // Validate XML inquiry for sales order
        xmlInquiryPage = financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");

    }

    /**
     * @TestCase CD365-2825
     * @Description Add two products to cart, place order with pick up first product and ship overnight second product,
     *              and verify total and tax amounts in E-Commerce and F&O
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderTwoProductsPickUpAndShipTest() {
        //================ Data Declaration ===========================
        String expectedSubtotal = "380.00";
        String expectedShippingCost = "15.00";
        String expectedTax = "30.24";
        String expectedTotal = "425.24";
        String expectedTotalXML = "<SubTotal>395.0</SubTotal>\n" +
                "\t\t<Total>425.24</Total>\n" +
                "\t\t<TotalTax>30.24</TotalTax>";

        //================ Place Order in e-commerce ===========================
        // Add products to bag
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct("Amber Sports Sunglasses");
        homePage.clickAddToBagButton();

        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct("Gold Round Sunglasses");
        homePage.clickAddToBagButton();

        // Pick up first product
        DCommerceCartPage cartPage = homePage.navigateToCartPage();
        cartPage.pickProductUpInStore( "Houston");

        DCommerceCheckoutPage checkoutPage = cartPage.navigateToCheckoutPage();
        checkoutPage.selectShippingAddress("NJ Address");
        checkoutPage.selectDeliveryOption(CommerceDeliveryOptions.OVERNIGHT);
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

        // Validate XML inquiry for sales order
        xmlInquiryPage = financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");

    }

    /**
     * @TestCase CD365-2826
     * @Description Add two products to cart, enter promo code, place order with standard shipping,
     *              and verify total and tax amounts in E-Commerce and F&O
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderPromoCodeTest()  {
        //================ Data Declaration ===========================
        String expectedSubtotal = "342.00";
        String expectedShippingCost = "Free";
        String expectedTax = "22.66";
        String expectedTotal = "364.66";
        String expectedTotalXML = "<SubTotal>342.0</SubTotal>\n" +
                "\t\t<Total>364.66</Total>\n" +
                "\t\t<TotalTax>22.66</TotalTax>";

        //================ Place Order in e-commerce ===========================
        // Add products to bag
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct("Amber Sports Sunglasses");
        homePage.clickAddToBagButton();

        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct("Gold Round Sunglasses");
        homePage.clickAddToBagButton();

        // Add promo code
        DCommerceCartPage cartPage = homePage.navigateToCartPage();
        cartPage.enterPromoCode("WEEKLYAD");

        // Checkout
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

        // Validate XML inquiry for sales order
        xmlInquiryPage = financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");

    }

    /**
     * TODO: Failing as XML Request does not contain lines from guest order (CMSMS-562)
     * @TestCase CD365-2827
     * @Description Add two products to cart, enter promo code, place order with standard overnight shipping,
     *              and verify total and tax amounts in E-Commerce and F&O
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderGiftCardTest() {
        //================ Data Declaration ===========================
        String expectedSubtotal = "380.00";
        String expectedShippingCost = "Free";
        String expectedTax = "25.18";
        String expectedTotal = "405.18";
        String expectedTotalXML = "<SubTotal>380.0</SubTotal>\n" +
                "\t\t<Total>405.18</Total>\n" +
                "\t\t<TotalTax>25.18</TotalTax>";

        //================ Place Order in e-commerce ===========================
        // Add products to bag
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct("Amber Sports Sunglasses");
        homePage.clickAddToBagButton();

        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct("Gold Round Sunglasses");
        homePage.clickAddToBagButton();

        // Checkout
        DCommerceCartPage cartPage = homePage.navigateToCartPage();
        DCommerceCheckoutPage checkoutPage = cartPage.navigateToCheckoutPage();
        checkoutPage.selectShippingAddress("NJ Address");
        checkoutPage.selectDeliveryOption(CommerceDeliveryOptions.STANDARD);

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

        // Validate XML inquiry for sales order
        xmlInquiryPage = financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");

    }

    /**
     * @TestCase CD365-2828
     * @Description Add a product to cart, add a second product with line charges, place order with standard shipping,
     *              and verify total and tax amounts in E-Commerce and F&O
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderWithProductHasLineChargesTest() {
        //================ Data Declaration ===========================
        String expectedSubtotal = "490.00";
        String expectedCharges = "10.00";
        String expectedShippingCost = "Free";
        String expectedTax = "33.13";
        String expectedTotal = "533.13";
        String expectedTotalXML = "<SubTotal>500.0</SubTotal>\n" +
                "\t\t<Total>533.13</Total>\n" +
                "\t\t<TotalTax>33.13</TotalTax>";

        //================ Place Order in e-commerce ===========================

        // Add product
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct("Amber Sports Sunglasses");
        homePage.clickAddToBagButton();

        // Add product with line charges
        homePage.navigateToCategory("Accessories", "Jewelry");
        homePage.clickProduct("24K Diamond \"O\" Pendant");
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
        String otherChargesAmount = checkoutPage.getOtherCharges();
        String taxAmount = checkoutPage.getTaxAmount();
        String totalAmount = checkoutPage.getTotalAmount();

        assertEquals(subtotalAmount, expectedSubtotal, "E-Commerce subtotal validation failed");
        assertEquals(shippingCost, expectedShippingCost, "E-Commerce shipping cost validation failed");
        assertEquals(otherChargesAmount, expectedCharges, "E-Commerce other charges validation failed");
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

        // Validate XML inquiry for sales order
        xmlInquiryPage = financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");
    }


    /**
     * @TestCase CD365-2829
     * @Description Add two products with multiple quantities to cart, place order with standard shipping,
     *              ship partial quantity, and verify total and tax amounts in E-Commerce, F&O, and Retail
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderTwoProductsShipPartiallyTest() {
        //================ Data Declaration ===========================
        String productName1 = "Amber Sports Sunglasses";
        String productName2 = "Gold Round Sunglasses";

        String expectedSubtotal = "1,010.00";
        String expectedShippingCost = "Free";
        String expectedTax = "66.91";
        String expectedTotal = "1,076.91";
        String expectedTotalXML = "<SubTotal>1010.0</SubTotal>\n" +
                "\t\t<Total>1076.91</Total>\n" +
                "\t\t<TotalTax>66.91</TotalTax>";

        String quantityToShipProduct1 = "2";
        String quantityToShipProduct2 = "1";
        //================ Place Order in e-commerce ===========================

        // Add products
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName1);
        homePage.setQuantity("3");
        homePage.clickAddToBagButton();

        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName2);
        homePage.setQuantity("2");
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

        // Validate XML inquiry for sales order
        xmlInquiryPage = financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");

        // Go to sales order
        allSalesOrdersPage = financeHomePage.navigateToAllSalesOrdersPage();
        allSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);
        allSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

        // Change Warehouse to Houston
        allSalesOrdersPage.openHeaderTab();
        allSalesOrdersPage.setWarehouseForHeader("Houston");
        allSalesOrdersPage.clickOnOKBtn();
        allSalesOrdersPage.clickOnSaveButton();

        //================ Navigate to D365 Retail ===========================
        DRetailHomePage retailHomePage = navigateToDRetailHomePage();
        DRetailOrderFulfillmentPage orderFulfillmentPage = retailHomePage.goToOrdersToShipPage();
        orderFulfillmentPage.sortOrders();

        // Ship quantity = 2 for first product in order
        orderFulfillmentPage.clickOnLine(salesOrderNumber, productName1);
        orderFulfillmentPage.clickOnShipBarButton();
        orderFulfillmentPage.enterQuantity(quantityToShipProduct1);
        orderFulfillmentPage.clickOnEnterButton();

        // Ship quantity = 1 for second product in order
        orderFulfillmentPage.clickOnLine(salesOrderNumber, productName2);
        orderFulfillmentPage.clickOnShipBarButton();
        orderFulfillmentPage.enterQuantity(quantityToShipProduct2);
        orderFulfillmentPage.clickOnEnterButton();

        orderFulfillmentPage.clickOnLine(salesOrderNumber, productName1);
        DRetailAllSalesOrderPage orderPage = orderFulfillmentPage.goToSelectedOrderPage();
        String actualSubtotalAmount = orderPage.getSubtotal();
        assertEquals(actualSubtotalAmount, expectedSubtotal, "Subtotal amount validation failed");

        String actualTaxAmount = orderPage.taxValidation();
        assertEquals(actualTaxAmount, expectedTax, "Tax amount validation failed");

        // Complete transaction
        orderPage.selectPayCash();
        orderPage.payBalance();
        orderPage.closeButton();

        //================ Navigate to D365 Finance ===========================
        navigateToDFinanceHomePage();
        financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        // Verify XML from D365 Retail
        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.selectDocType("Retail transactions");
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");
    }

    /**
     * @TestCase CD365-2830
     * @Description Add two products with multiple quantities to cart, place order with pick up for all products,
     *              ship partial quantity, and verify total and tax amounts in E-Commerce, F&O, and Retail
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderTwoProductsPickUpPartiallyTest() {
        //================ Data Declaration ===========================
        String productName1 = "Amber Sports Sunglasses";
        String productName2 = "Gold Round Sunglasses";

        String expectedSubtotal = "1,010.00";
        String expectedTax = "83.33";
        String expectedTotal = "1,093.33";
        String expectedTotalXML = "<SubTotal>1010.0</SubTotal>\n" +
                "\t\t<Total>1093.33</Total>\n" +
                "\t\t<TotalTax>83.33</TotalTax>";

        String expectedSubtotalFromPickup = "630.00";
        String expectedTaxFromPickup = "51.98";
        String expectedTotalXMLFromPickup = "\t\t<SubTotal>630.0</SubTotal>\n" +
                "\t\t<Total>681.98</Total>\n" +
                "\t\t<TotalTax>51.98</TotalTax>";

        String quantityToShipProduct1 = "2";
        String quantityToShipProduct2 = "1";
        //================ Place Order in e-commerce ===========================

        // Add products
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName1);
        homePage.setQuantity("3");
        homePage.clickAddToBagButton();

        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName2);
        homePage.setQuantity("2");
        homePage.clickAddToBagButton();

        // Click pick up for both products
        DCommerceCartPage cartPage = homePage.navigateToCartPage();
        cartPage.pickProductUpInStore( "Houston");
        cartPage.pickProductUpInStore( "Houston");

        // Checkout
        DCommerceCheckoutPage checkoutPage = cartPage.navigateToCheckoutPage();
        checkoutPage.enterDummyCardDetails();
        checkoutPage.enterBillingAddress("Phila", "3000 Market Street", "Philadelphia", "PA", "19104", "USA");
        checkoutPage.clickSaveAndContinue();

        // Verify amounts
        String subtotalAmount = checkoutPage.getSubtotalAmount();
        String taxAmount = checkoutPage.getTaxAmount();
        String totalAmount = checkoutPage.getTotalAmount();

        assertEquals(subtotalAmount, expectedSubtotal, "E-Commerce subtotal validation failed");
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

        // Validate tax amount in sales order
        DFinanceAllSalesOrdersPage allSalesOrdersPage = navigateToLatestOpenSalesOrder("Test Automation");
        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();
        String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        assertEquals(calculatedSalesTaxAmount, expectedTax);
        String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
        assertEquals(actualSalesTaxAmount, expectedTax);
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

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
        DRetailOrderFulfillmentPage orderFulfillmentPage = retailHomePage.goToOrdersToPickUpPage();
        orderFulfillmentPage.sortOrders();

        orderFulfillmentPage.clickOnLine(salesOrderNumber, productName1);
        orderFulfillmentPage.clickOnPickUpBarButton();

        // Pick up quantity = 2 for first product in order
        orderFulfillmentPage.pickUpNowForProduct(productName1);
        orderFulfillmentPage.enterQuantity(quantityToShipProduct1);
        orderFulfillmentPage.clickOnEnterButton();

        // Pick up quantity = 1 for second product in order
        orderFulfillmentPage.pickUpNowForProduct(productName2);
        orderFulfillmentPage.enterQuantity(quantityToShipProduct2);
        orderFulfillmentPage.clickOnEnterButton();

        // Click Pick Up and then OK to be redirected to Transaction
        orderFulfillmentPage.clickPickUpForOrder();
        orderFulfillmentPage.clickOk();

        DRetailAllSalesOrderPage orderPage = new DRetailAllSalesOrderPage(driver);

        // Verify subtotal and tax
        String actualSubtotalAmount = orderPage.getSubtotal();
        assertEquals(actualSubtotalAmount, expectedSubtotalFromPickup, "Subtotal amount validation failed");

        String actualTaxAmount = orderPage.taxValidation();
        assertEquals(actualTaxAmount, expectedTaxFromPickup, "Tax amount validation failed");

        // Pay cash
        orderPage.selectPayCash();
        orderPage.cashAccepted();
        orderPage.closeButton();


        //================ Navigate to D365 Finance ===========================
        navigateToDFinanceHomePage();
        financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        // Verify XML from D365 Retail
        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.selectDocType("Retail transactions");
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXMLFromPickup), "Subtotal, total tax, total XML validation failed");
    }
}
