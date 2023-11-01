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
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
/**
 * Contains test scenarios for e-commerce orders and recalling in retail
 */
@Listeners(TestRerunListener.class)
public class DCommerceOrderRecallTests extends DCommerceBaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        DCommerceCartPage cartPage = homePage.navigateToCartPage();
        cartPage.removeAllProducts();
        cartPage.navigateToHomePage();

        String accountName = homePage.getAccountName();
        if (!accountName.equals("Test")) {
            loadInitialTestPage();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void teardownTest() {
        System.out.println("Teardown");
    }

    /**
     * @TestCase CD365-2831
     * @Description Add two products to cart, place order with standard shipping,
     *              recall order and change quantity of first product and add comment to second order,
     *              and verify total and tax amounts in E-Commerce, F&O, and Retail
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderRecallChangeQuantityAddCommentTest() {
        //================ Data Declaration ===========================
        String productName1 = "Amber Sports Sunglasses";
        String productName2 = "Gold Round Sunglasses";
        String quantity = "2";
        String quantityToUpdate = "3";

        String expectedSubtotal = "760.00";
        String expectedShippingCost = "Free";
        String expectedTax = "50.35";
        String expectedTotal = "810.35";
        String expectedTotalXML = "<SubTotal>760.0</SubTotal>\n" +
                "\t\t<Total>810.35</Total>\n" +
                "\t\t<TotalTax>50.35</TotalTax>";

        String expectedSubtotalFromRecall = "1,010.00";
        String expectedTaxFromRecall = "66.91";
        String expectedTotalXMLFromRecall =  "<SubTotal>1010.0</SubTotal>\n" +
                "\t\t<Total>1076.91</Total>\n" +
                "\t\t<TotalTax>66.91</TotalTax>";


        //================ Place Order in e-commerce ===========================

        // Add products
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName1);
        homePage.setQuantity(quantity);
        homePage.clickAddToBagButton();

        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName2);
        homePage.setQuantity(quantity);
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
        navigateToDRetailHomePage();
        DRetailAllSalesOrderPage orderPage = new DRetailAllSalesOrderPage(driver);

        // Search for order
        orderPage.transactionButton();
        orderPage.clickOrders();
        orderPage.clickRecallOrder();
        orderPage.clickSearchOrdersButton();
        orderPage.applyFilter("Order/receipt/confirmation number", salesOrderNumber);
        orderPage.clickEditOrder();

        // Change quantity of product 1
        orderPage.clickOnAction();
        orderPage.clickProductLine(productName1);
        orderPage.clickOnSetQuantity(quantityToUpdate);

        orderPage.clickProductLine(productName2);
        orderPage.clickOnLineComment("This is a test comment.");

        // Verify subtotal and tax
        String actualSubtotalAmount = orderPage.getSubtotal();
        assertEquals(actualSubtotalAmount, expectedSubtotalFromRecall, "Subtotal amount validation failed");

        String actualTaxAmount = orderPage.taxValidation();
        assertEquals(actualTaxAmount, expectedTaxFromRecall, "Tax amount validation failed");

        // Pay cash
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
        assertTrue(response.contains(expectedTotalXMLFromRecall), "Subtotal, total tax, total XML validation failed");
    }

    /**
     * //TODO Don't see a change of unitOfMeasure in XML
     * @TestCase CD365-2832
     * @Description Add two products to cart, place order with standard shipping,
     *              recall order and change unit of measure of first product,
     *              and verify total and tax amounts in E-Commerce, F&O, and Retail
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderRecallChangeUnitOfMeasureTest() {
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

        //================ Place Order in e-commerce ===========================

        // Add products
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName1);
        homePage.clickAddToBagButton();

        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName2);
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
        navigateToDRetailHomePage();
        DRetailAllSalesOrderPage orderPage = new DRetailAllSalesOrderPage(driver);

        // Search for order
        orderPage.transactionButton();
        orderPage.clickOrders();
        orderPage.clickRecallOrder();
        orderPage.clickSearchOrdersButton();
        orderPage.applyFilter("Order/receipt/confirmation number", salesOrderNumber);
        orderPage.clickEditOrder();

        // Change unit of measure of product 1
        orderPage.clickOnAction();
        orderPage.clickProductLine(productName1);
        orderPage.clickOnChangeUnitOfMeasure();
        orderPage.selectPieces();
        orderPage.clickYesButton();

        // Verify subtotal and tax
        String actualSubtotalAmount = orderPage.getSubtotal();
        assertEquals(actualSubtotalAmount, expectedSubtotal, "Subtotal amount validation failed");

        String actualTaxAmount = orderPage.taxValidation();
        assertEquals(actualTaxAmount, expectedTax, "Tax amount validation failed");

        // Pay cash
        orderPage.selectPayCash();
        orderPage.payBalance();
        orderPage.closeButton();


        //================ Navigate to D365 Finance ===========================
        navigateToDFinanceHomePage();
        financeHomePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY
        );

        // TODO: Check if unitOfMeasure was updated
        // Verify XML from D365 Retail
        xmlInquiryPage.getDocumentID(salesOrderNumber);
        xmlInquiryPage.selectDocType("Retail transactions");
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains(expectedTotalXML), "Subtotal, total tax, total XML validation failed");
    }

    /**
     * @TestCase CD365-2833
     * @Description Add two products to cart, place order with standard shipping, recall order and void first product,
     *              and verify total and tax amounts in E-Commerce, F&O, and Retail
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderRecallVoidOneLineTest() {
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

        String expectedSubtotalFromRecall = "250.00";
        String expectedTaxFromRecall = "16.56";
        String expectedTotalXMLFromRecall = "<SubTotal>250.0</SubTotal>\n" +
                "\t\t<Total>266.56</Total>\n" +
                "\t\t<TotalTax>16.56</TotalTax>";


        //================ Place Order in e-commerce ===========================

        // Add products
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName1);
        homePage.clickAddToBagButton();

        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName2);
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
        navigateToDRetailHomePage();
        DRetailAllSalesOrderPage orderPage = new DRetailAllSalesOrderPage(driver);

        // Search for order
        orderPage.transactionButton();
        orderPage.clickOrders();
        orderPage.clickRecallOrder();
        orderPage.clickSearchOrdersButton();
        orderPage.applyFilter("Order/receipt/confirmation number", salesOrderNumber);
        orderPage.clickEditOrder();

        // Void second product
        orderPage.clickOnAction();
        orderPage.voidLine(productName2);

        // Verify subtotal and tax
        String actualSubtotalAmount = orderPage.getSubtotal();
        assertEquals(actualSubtotalAmount, expectedSubtotalFromRecall, "Subtotal amount validation failed");

        String actualTaxAmount = orderPage.taxValidation();
        assertEquals(actualTaxAmount, expectedTaxFromRecall, "Tax amount validation failed");

        // Pay cash
        orderPage.selectPayCash();
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
        assertTrue(response.contains(expectedTotalXMLFromRecall), "Subtotal, total tax, total XML validation failed");
    }

    /**
     * @TestCase CD365-2834
     * @Description Add two products to cart, place order with standard shipping,
     *              recall order and add a third product with standard shipping,
     *              and verify total and tax amounts in E-Commerce, F&O, and Retail
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderRecallAddProductTest() {
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

        String expectedSubtotalFromRecall = "530.00";
        String expectedTaxFromRecall = "35.11";
        String expectedTotalXMLFromRecall = "<SubTotal>530.0</SubTotal>\n" +
                "\t\t<Total>565.11</Total>\n" +
                "\t\t<TotalTax>35.11</TotalTax>";


        //================ Place Order in e-commerce ===========================

        // Add products
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName1);
        homePage.clickAddToBagButton();

        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName2);
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
        navigateToDRetailHomePage();
        DRetailAllSalesOrderPage orderPage = new DRetailAllSalesOrderPage(driver);

        // Search for order
        orderPage.transactionButton();
        orderPage.clickOrders();
        orderPage.clickRecallOrder();
        orderPage.clickSearchOrdersButton();
        orderPage.applyFilter("Order/receipt/confirmation number", salesOrderNumber);
        orderPage.clickEditOrder();

        // Add a third product
        orderPage.clickProducts();
        orderPage.clickBrownAviatorButton();

        // Ship third product standard shipping
        orderPage.clickOrders();
        orderPage.selectShipSelected();
        orderPage.standardShipping();

        // Verify subtotal and tax
        String actualSubtotalAmount = orderPage.getSubtotal();
        assertEquals(actualSubtotalAmount, expectedSubtotalFromRecall, "Subtotal amount validation failed");

        String actualTaxAmount = orderPage.taxValidation();
        assertEquals(actualTaxAmount, expectedTaxFromRecall, "Tax amount validation failed");

        // Pay cash
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
        assertTrue(response.contains(expectedTotalXMLFromRecall), "Subtotal, total tax, total XML validation failed");
    }

    /**
     * @TestCase CD365-2835
     * @Description Add two products to cart, place order with standard shipping,
     *              recall order and ship all with standard overnight,
     *              and verify total, shipping charges, and tax amounts in E-Commerce, F&O, and Retail
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderRecallChangeShippingMethodTest() {
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

        String expectedSubtotalFromRecall = "380.00";
        String expectedShippingCostFromRecall = "15.00";
        String expectedTaxFromRecall = "26.17";
        String expectedTotalXMLFromRecall = "<SubTotal>395.0</SubTotal>\n" +
                "\t\t<Total>421.17</Total>\n" +
                "\t\t<TotalTax>26.17</TotalTax>";

        //================ Place Order in e-commerce ===========================

        // Add products
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName1);
        homePage.clickAddToBagButton();

        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName2);
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
        navigateToDRetailHomePage();
        DRetailAllSalesOrderPage orderPage = new DRetailAllSalesOrderPage(driver);

        // Search for order
        orderPage.transactionButton();
        orderPage.clickOrders();
        orderPage.clickRecallOrder();
        orderPage.clickSearchOrdersButton();
        orderPage.applyFilter("Order/receipt/confirmation number", salesOrderNumber);
        orderPage.clickEditOrder();

        // Ship all with standard overnight
        orderPage.clickOrders();
        orderPage.selectShipAll();
        orderPage.standardOvernightShipping();

        // Verify subtotal, shipping charges, and tax
        String actualSubtotalAmount = orderPage.getSubtotal();
        assertEquals(actualSubtotalAmount, expectedSubtotalFromRecall, "Subtotal amount validation failed");

        String actualShippingCharges = orderPage.getCharges();
        assertEquals(actualShippingCharges, expectedShippingCostFromRecall, "Shipping charges amount validation failed");

        String actualTaxAmount = orderPage.taxValidation();
        assertEquals(actualTaxAmount, expectedTaxFromRecall, "Tax amount validation failed");

        // Pay cash
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
        assertTrue(response.contains(expectedTotalXMLFromRecall), "Subtotal, total tax, total XML validation failed");
    }

    /**
     * @TestCase CD365-2836
     * @Description Add two products to cart, place order with standard shipping,
     *              recall order and pick up one product,
     *              and verify total, shipping charges, and tax amounts in E-Commerce, F&O, and Retail
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = { "D365_COMMERCE_REGRESSION" }, retryAnalyzer = TestRerun.class)
    public void verifyOrderRecallPickUpAndShipTest() {
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

        String expectedSubtotalFromRecall = "380.00";
        String expectedShippingCostFromRecall = "6.95";
        String expectedTaxFromRecall = "29.70";
        String expectedTotalXMLFromRecall = "<SubTotal>386.95</SubTotal>\n" +
                "\t\t<Total>416.65</Total>\n" +
                "\t\t<TotalTax>29.7</TotalTax>";

        //================ Place Order in e-commerce ===========================

        // Add products
        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName1);
        homePage.clickAddToBagButton();

        homePage.navigateToCategory("Accessories", "Sunglasses");
        homePage.clickProduct(productName2);
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
        navigateToDRetailHomePage();
        DRetailAllSalesOrderPage orderPage = new DRetailAllSalesOrderPage(driver);

        // Search for order
        orderPage.transactionButton();
        orderPage.clickOrders();
        orderPage.clickRecallOrder();
        orderPage.clickSearchOrdersButton();
        orderPage.applyFilter("Order/receipt/confirmation number", salesOrderNumber);
        orderPage.clickEditOrder();

        // Pick up first product
        orderPage.clickOrders();
        orderPage.clickProductLine(productName1);
        orderPage.clickPickUpSelected();
        orderPage.clickYesButton();
        orderPage.selectHoustonStore();

        // Verify subtotal, shipping charges, and tax
        String actualSubtotalAmount = orderPage.getSubtotal();
        assertEquals(actualSubtotalAmount, expectedSubtotalFromRecall, "Subtotal amount validation failed");

        String actualShippingCharges = orderPage.getCharges();
        assertEquals(actualShippingCharges, expectedShippingCostFromRecall, "Shipping charges amount validation failed");

        String actualTaxAmount = orderPage.taxValidation();
        assertEquals(actualTaxAmount, expectedTaxFromRecall, "Tax amount validation failed");

        // Pay cash
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
        assertTrue(response.contains(expectedTotalXMLFromRecall), "Subtotal, total tax, total XML validation failed");
    }

}
