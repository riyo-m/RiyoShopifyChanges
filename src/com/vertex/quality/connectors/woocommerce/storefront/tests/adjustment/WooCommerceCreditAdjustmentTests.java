package com.vertex.quality.connectors.woocommerce.storefront.tests.adjustment;

import com.vertex.quality.connectors.woocommerce.admin.pages.WooCommerceAdminHomePage;
import com.vertex.quality.connectors.woocommerce.enums.WooData;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceCartPage;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceCheckoutPage;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceProductsPage;
import com.vertex.quality.connectors.woocommerce.storefront.tests.base.WooCommerceBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test Case CWOO-351
 * WOO - Sales - Credit/Adjustment.
 * same billing and shipping Addresses
 * CCMMER-464 WOO - tech debt reduction
 *
 * @author Shivam.Soni
 */
public class WooCommerceCreditAdjustmentTests extends WooCommerceBaseTest {

    WooCommerceAdminHomePage homePage;
    WooCommerceProductsPage productsPage;
    WooCommerceCartPage cartPage;
    WooCommerceCheckoutPage checkoutPage;
    String orderNumber;
    String shippingAmount;

    /**
     * CWOO-355
     * WOO - Test Case - Return Sales order - full order with shipping.
     */
    @Test(groups = "woo_regression")
    public void wooCommerceFullOrderWithShippingRefundTest() {
        // Setting up physical origin or Ship From address
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        // Processing order
        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCTS_TWO_HOODIES_BELT.data);
        productsPage.updateLineItems(WooData.QUANTITIES_FIVE_ONE_TWO.data, WooData.PRODUCTS_TWO_HOODIES_BELT.data);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_CA_LA.data);
        checkoutPage.clickPlaceOrder();
        orderNumber = checkoutPage.getOrderNoFromUI();
        shippingAmount = checkoutPage.getShippingAmountUI();
        assertEquals(checkoutPage.calculatePercentageBasedTax(9.5025), checkoutPage.getTaxFromUIAfterOrderPlace());

        // doing refund for placed order
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.refundItems(orderNumber, WooData.PRODUCTS_TWO_HOODIES_BELT.data, WooData.QUANTITIES_FIVE_ONE_TWO.data);
        assertEquals(WooData.QUANTITIES_FIVE_ONE_TWO.data[0], homePage.getRefundedItemQuantities(WooData.PRODUCTS_TWO_HOODIES_BELT.data[0]));
        assertEquals(WooData.QUANTITIES_FIVE_ONE_TWO.data[1], homePage.getRefundedItemQuantities(WooData.PRODUCTS_TWO_HOODIES_BELT.data[1]));
        assertEquals(WooData.QUANTITIES_FIVE_ONE_TWO.data[2], homePage.getRefundedItemQuantities(WooData.PRODUCTS_TWO_HOODIES_BELT.data[2]));
        assertTrue(homePage.verifyRefundedItemAmount(WooData.PRODUCTS_TWO_HOODIES_BELT.data[0]));
        assertTrue(homePage.verifyRefundedItemAmount(WooData.PRODUCTS_TWO_HOODIES_BELT.data[1]));
        assertTrue(homePage.verifyRefundedItemAmount(WooData.PRODUCTS_TWO_HOODIES_BELT.data[2]));
        assertTrue(homePage.refundedShippingAmount(shippingAmount));
    }

    /**
     * CWOO-352
     * WOO - Test Case - Return Sales order - partial amount returned.
     */
    @Test(groups = "woo_regression")
    public void wooCommercePartialAmountRefundTest() {
        // Setting up physical origin or Ship From address
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        // Processing order
        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCTS_TWO_HOODIES.data);
        productsPage.updateLineItems(WooData.QUANTITIES_SIX_TWO.data, WooData.PRODUCTS_TWO_HOODIES.data);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_CA_LA.data);
        checkoutPage.clickPlaceOrder();
        orderNumber = checkoutPage.getOrderNoFromUI();
        shippingAmount = checkoutPage.getShippingAmountUI();
        assertEquals(checkoutPage.calculatePercentageBasedTax(9.5), checkoutPage.getTaxFromUIAfterOrderPlace());

        // doing refund for placed order
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.refundItems(orderNumber, WooData.PRODUCTS_TWO_HOODIES.data, WooData.QUANTITIES_THREE_ONE.data);
        assertEquals(WooData.QUANTITIES_THREE_ONE.data[0], homePage.getRefundedItemQuantities(WooData.PRODUCTS_TWO_HOODIES.data[0]));
        assertEquals(WooData.QUANTITIES_THREE_ONE.data[1], homePage.getRefundedItemQuantities(WooData.PRODUCTS_TWO_HOODIES.data[1]));
        assertTrue(homePage.verifyRefundedItemAmount(WooData.PRODUCTS_TWO_HOODIES.data[0]));
        assertTrue(homePage.verifyRefundedItemAmount(WooData.PRODUCTS_TWO_HOODIES.data[1]));
        assertTrue(homePage.refundedShippingAmount(shippingAmount));
    }

    /**
     * CWOO-354
     * WOO - Test Case - Return Sales order - partial order with shipping.
     */
    @Test(groups = "woo_regression")
    public void wooCommercePartialOrderWithShippingTest() {
        // Setting up physical origin or Ship From address
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        // Processing order
        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCTS_TWO_HOODIES_BELT.data);
        productsPage.updateLineItems(WooData.QUANTITIES_FIVE_ONE_TWO.data, WooData.PRODUCTS_TWO_HOODIES_BELT.data);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_CA_LA.data);
        checkoutPage.clickPlaceOrder();
        orderNumber = checkoutPage.getOrderNoFromUI();
        shippingAmount = checkoutPage.getShippingAmountUI();
        assertEquals(checkoutPage.calculatePercentBasedTax(9.5025), checkoutPage.getTaxFromUIAfterOrder());

        // doing refund for placed order
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.refundItems(orderNumber, WooData.PRODUCTS_TWO_HOODIES.data, WooData.QUANTITIES_FIVE_ONE.data);
        assertEquals(WooData.QUANTITIES_FIVE_ONE.data[0], homePage.getRefundedItemQuantities(WooData.PRODUCTS_TWO_HOODIES.data[0]));
        assertEquals(WooData.QUANTITIES_FIVE_ONE.data[1], homePage.getRefundedItemQuantities(WooData.PRODUCTS_TWO_HOODIES.data[1]));
        assertTrue(homePage.verifyRefundedItemAmount(WooData.PRODUCTS_TWO_HOODIES.data[0]));
        assertTrue(homePage.verifyRefundedItemAmount(WooData.PRODUCTS_TWO_HOODIES.data[1]));
        assertTrue(homePage.refundedShippingAmount(shippingAmount));
    }

    /**
     * CWOO-353
     * WOO - Test Case - Return Sales order - partial quantity returned.
     */
    @Test(groups = {"woo_regression", "woo_smoke"})
    public void wooCommercePartialQuantityRefundTest() {
        // Setting up physical origin or Ship From address
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_NJ_ED.data[0] + " — " + WooData.US_NJ_ED.data[3], WooData.US_NJ_ED.data[1], WooData.US_NJ_ED.data[2], WooData.US_NJ_ED.data[4]);

        quitDriver();
        createChromeDriver();

        // Processing order
        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCT_HOODIE.data);
        productsPage.updateLineItems(WooData.QUANTITY_FIVE.data, WooData.PRODUCT_HOODIE.data);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_TX_DL.data);
        checkoutPage.clickPlaceOrder();
        orderNumber = checkoutPage.getOrderNoFromUI();
        shippingAmount = checkoutPage.getShippingAmountUI();
        assertEquals(checkoutPage.calculatePercentageBasedTax(8.25), checkoutPage.getTaxFromUIAfterOrderPlace());

        // doing refund for placed order
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.refundItems(orderNumber, WooData.PRODUCT_HOODIE.data, WooData.QUANTITY_FIVE.data);
        assertEquals(WooData.QUANTITY_FIVE.data[0], homePage.getRefundedItemQuantities(WooData.PRODUCT_HOODIE.data[0]));
        assertTrue(homePage.verifyRefundedItemAmount(WooData.PRODUCT_HOODIE.data[0]));
        assertTrue(homePage.refundedShippingAmount(shippingAmount));
    }

    /**
     * CWOO-356
     * WOO - Test Case - Return Sale Order with Shipping for VAT (Intra EU DE-DE) and Invoice.
     */
    @Test(groups = "woo_regression")
    public void wooCommercePartialSalesOrderRefundTest() {
        // Setting up physical origin or Ship From address
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.DE_BERLIN_10696.data[0] + " — " + WooData.DE_BERLIN_10696.data[2], WooData.DE_BERLIN_10696.data[1], WooData.DE_BERLIN_10696.data[2], WooData.DE_BERLIN_10696.data[4]);

        quitDriver();
        createChromeDriver();

        // Processing order
        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCTS_TWO_HOODIES_BELT.data);
        productsPage.updateLineItems(WooData.QUANTITIES_FIVE_ONE_TWO.data, WooData.PRODUCTS_TWO_HOODIES_BELT.data);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.DE_BERLIN_13405.data);
        checkoutPage.clickPlaceOrder();
        orderNumber = checkoutPage.getOrderNoFromUI();
        assertEquals(checkoutPage.calculatePercentageBasedTax(19), checkoutPage.getTaxFromUIAfterOrderPlace());

        // doing refund for placed order
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.refundItems(orderNumber, WooData.PRODUCTS_TWO_HOODIES.data, WooData.QUANTITIES_THREE_ONE.data);
        assertEquals(WooData.QUANTITIES_THREE_ONE.data[0], homePage.getRefundedItemQuantities(WooData.PRODUCTS_TWO_HOODIES.data[0]));
        assertEquals(WooData.QUANTITIES_THREE_ONE.data[1], homePage.getRefundedItemQuantities(WooData.PRODUCTS_TWO_HOODIES.data[1]));
        assertTrue(homePage.verifyRefundedItemAmount(WooData.PRODUCTS_TWO_HOODIES.data[0]));
        assertTrue(homePage.verifyRefundedItemAmount(WooData.PRODUCTS_TWO_HOODIES.data[1]));
    }
}
