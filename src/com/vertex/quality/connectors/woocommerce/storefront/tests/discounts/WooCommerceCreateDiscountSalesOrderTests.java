package com.vertex.quality.connectors.woocommerce.storefront.tests.discounts;

import com.vertex.quality.connectors.woocommerce.admin.pages.WooCommerceAdminHomePage;
import com.vertex.quality.connectors.woocommerce.enums.WooCommerceData;
import com.vertex.quality.connectors.woocommerce.enums.WooData;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceCartPage;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceCheckoutPage;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceProductsPage;
import com.vertex.quality.connectors.woocommerce.storefront.tests.base.WooCommerceBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test Case CWOO-357
 * WOO - Create Sales Order with Discounts (Quote, Invoice, Distribute Request)
 * CCMMER-464 WOO - tech debt reduction
 *
 * @author Shivam.Soni
 */
public class WooCommerceCreateDiscountSalesOrderTests extends WooCommerceBaseTest {

    WooCommerceAdminHomePage homePage;
    WooCommerceProductsPage productsPage;
    WooCommerceCartPage cartPage;
    WooCommerceCheckoutPage checkoutPage;

    /**
     * CWOO-360
     * WOO - Test Case -Create Sales Order with Discount - Multi line order with Discount line Amount.
     */
    @Test(groups = {"woo_regression", "woo_smoke"})
    public void wooCommerceDiscountLineAmountTest() {
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCTS_TWO_HOODIES.data);
        productsPage.updateLineItems(WooData.QUANTITIES_FIVE_ONE.data, WooData.PRODUCTS_TWO_HOODIES.data);
        productsPage.applyCouponCode(WooData.TEN_DOLLAR_ITEM.value);

        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_CA_LA.data);
        checkoutPage.clickOnCheckBox();
        checkoutPage.addShippingAddress(WooData.US_CA_LA.data);
        checkoutPage.clickPlaceOrder();
        assertEquals(checkoutPage.calculatePercentBasedTax(9.5025), checkoutPage.getTaxFromUIAfterOrder());
    }

    /**
     * CWOO-365
     * WOO - Test Case -Create Sales Order with Discounts and Invoice.
     */
    @Test(groups = "woo_regression")
    public void wooCommerceSalesOrderDiscountTest() {
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCTS_BELT_TWO_HOODIES.data);
        productsPage.updateLineItems(WooData.QUANTITIES_FIVE_ONE_TWO.data, WooData.PRODUCTS_BELT_TWO_HOODIES.data);
        productsPage.applyCouponCode(WooData.TEN_PERCENT_ORDER.value);
        productsPage.applyCouponCode(WooData.TEN_DOLLAR_ITEM.value);
        productsPage.applyCouponCode(WooData.ONE_OFF_SHIP.value);

        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_WA_SM.data);
        checkoutPage.clickOnCheckBox();
        checkoutPage.addShippingAddress(WooData.US_WA_SM.data);
        checkoutPage.clickPlaceOrder();

        assertEquals(checkoutPage.calculatePercentBasedTax(10.101), checkoutPage.getTaxFromUIAfterOrder());
    }

    /**
     * CWOO-363
     * WOO - Test Case -Create Sales Order with multiple Discounts.
     * Discount Shipping Amount, Discount Order Percent, Discount Line Amount.
     */
    @Test(groups = "woo_regression")
    public void wooCommerceMultipleDiscountTest() {
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCTS_TWO_HOODIES_BELT.data);
        productsPage.updateLineItems(WooData.QUANTITIES_FIVE_ONE_TWO.data, WooData.PRODUCTS_TWO_HOODIES_BELT.data);
        productsPage.applyCouponCode(WooData.TEN_PERCENT_ORDER.value);
        productsPage.applyCouponCode(WooData.TEN_DOLLAR_ITEM.value);
        productsPage.applyCouponCode(WooData.ONE_OFF_SHIP.value);

        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_WA_SM.data);
        checkoutPage.clickOnCheckBox();
        checkoutPage.addShippingAddress(WooData.US_WA_SM.data);
        checkoutPage.clickPlaceOrder();

        assertEquals(checkoutPage.calculatePercentBasedTax(10.101), checkoutPage.getTaxFromUIAfterOrder());
    }

    /**
     * CWOO-362
     * WOO - Test Case -Create Sales Order with Discount.
     * Multi Line Order with Discount Shipping Amount.
     */
    @Test(groups = "woo_regression")
    public void WooCommerceShippingAmountDiscountTest() {
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCTS_TWO_HOODIES.data);
        productsPage.updateLineItems(WooData.QUANTITIES_FIVE_ONE.data, WooData.PRODUCTS_TWO_HOODIES.data);
        productsPage.applyCouponCode(WooData.ONE_OFF_SHIP.value);

        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_CA_LA.data);
        checkoutPage.clickPlaceOrder();

        assertEquals(checkoutPage.calculatePercentBasedTax(9.5), checkoutPage.getTaxFromUIAfterOrder());
    }

    /**
     * CWOO-358
     * WOO - Test Case -Create Sales Order with Discount.
     * Multi line with Discount order Percent, Change Discount Order Percent.
     */
    @Test(groups = "woo_regression")
    public void wooCommerceChangeDiscountTest() {
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_CO_HR.data[0] + " — " + WooData.US_CO_HR.data[3], WooData.US_CO_HR.data[1], WooData.US_CO_HR.data[2], WooData.US_CO_HR.data[3]);

        quitDriver();
        createChromeDriver();

        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCTS_TWO_HOODIES.data);
        productsPage.updateLineItems(WooData.QUANTITIES_FIVE_ONE.data, WooData.PRODUCTS_TWO_HOODIES.data);
        productsPage.applyCouponCode(WooCommerceData.TEN_PERCENT_ORDER.data);

        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_LA_NO.data);
        checkoutPage.clickPlaceOrder();

        assertEquals(checkoutPage.calculatePercentBasedTax(9.455), checkoutPage.getTaxFromUIAfterOrder());
    }

    /**
     * CWOO-364
     * WOO - Test Case - Create Sale Order with a Discount Shipping for VAT (Intra EU DE-DE) and Invoice.
     */
    @Test(groups = "woo_regression")
    public void wooCommerceVatDiscountTest() {
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.DE_BERLIN_10696.data[0] + " — " + WooData.DE_BERLIN_10696.data[2], WooData.DE_BERLIN_10696.data[1], WooData.DE_BERLIN_10696.data[2], WooData.DE_BERLIN_10696.data[4]);

        quitDriver();
        createChromeDriver();

        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCTS_TWO_HOODIES_BELT.data);
        productsPage.updateLineItems(WooData.QUANTITIES_FIVE_ONE_TWO.data, WooData.PRODUCTS_TWO_HOODIES_BELT.data);
        productsPage.applyCouponCode(WooCommerceData.TEN_PERCENT_ORDER.data);
        productsPage.applyCouponCode(WooCommerceData.TEN_DOLLAR_ITEM.data);

        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.DE_BERLIN_13405.data);
        checkoutPage.clickOnCheckBox();
        checkoutPage.addShippingAddress(WooData.DE_BERLIN_13405.data);
        checkoutPage.clickPlaceOrder();

        assertEquals(checkoutPage.calculatePercentBasedTax(19), checkoutPage.getTaxFromUIAfterOrder());
    }

    /**
     * CWOO-361
     * WOO - Test Case -Create Sales Order with Discount - Multi Line Order with Discount Order Amount.
     */
    @Test(groups = "woo_regression")
    public void wooCommerceDiscountOrderAmountTest() {
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCTS_TWO_HOODIES.data);
        productsPage.updateLineItems(WooData.QUANTITIES_FIVE_ONE.data, WooData.PRODUCTS_TWO_HOODIES.data);
        productsPage.applyCouponCode(WooData.FIVE_DOLLAR_ORDER.value);

        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_CA_LA.data);
        checkoutPage.clickOnCheckBox();
        checkoutPage.addShippingAddress(WooData.US_CA_LA.data);
        checkoutPage.clickPlaceOrder();

        assertEquals(checkoutPage.calculatePercentBasedTax(9.5), checkoutPage.getTaxFromUIAfterOrder());
    }

    /**
     * CWOO-593
     * WOO - Create Sales Order with Discount - Multi Line Order with Discount Shipping Amount
     */
    @Test(groups = "woo_regression")
    public void wooCommerceDiscountShippingAmountTest() {
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCTS_TWO_HOODIES.data);
        productsPage.updateLineItems(WooData.QUANTITIES_FIVE_ONE.data, WooData.PRODUCTS_TWO_HOODIES.data);
        productsPage.applyCouponCode(WooData.ONE_OFF_SHIP.value);

        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_CA_LA.data);
        checkoutPage.clickOnCheckBox();
        checkoutPage.addShippingAddress(WooData.US_CA_LA.data);
        checkoutPage.clickPlaceOrder();

        assertEquals(checkoutPage.calculatePercentBasedTax(9.5), checkoutPage.getTaxFromUIAfterOrder());
    }

    /**
     * CWOO-594
     * WOO - Create Sales Order with Discount - Multi line with Discount order Percent, Change Discount Order Percent
     */
    @Test(groups = "woo_regression")
    public void wooCommerceDiscountOrderAmountChangeDiscountPercentTest() {
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_CO_HR.data[0] + " — " + WooData.US_CO_HR.data[3], WooData.US_CO_HR.data[1], WooData.US_CO_HR.data[2], WooData.US_CO_HR.data[4]);

        quitDriver();
        createChromeDriver();

        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCTS_TWO_HOODIES.data);
        productsPage.updateLineItems(WooData.QUANTITIES_FIVE_ONE.data, WooData.PRODUCTS_TWO_HOODIES.data);
        productsPage.applyCouponCode(WooData.TEN_PERCENT_ORDER.value);

        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_LA_NO.data);

        checkoutPage.addCouponCode(WooData.FIVE_PERCENT_ORDER.value);
        checkoutPage.clickPlaceOrder();

        assertEquals(checkoutPage.calculatePercentBasedTax(9.45), checkoutPage.getTaxFromUIAfterOrder());
    }

    /**
     * CWOO-359
     * WOO - Test Case -Create Sales Order with Discount - Multi line order with Discount Line Percentage.
     */
    @Test(groups = "woo_regression")
    public void wooCommerceDiscountLinePercentageTest() {
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCTS_TWO_HOODIES.data);
        productsPage.updateLineItems(WooData.QUANTITIES_FIVE_ONE.data, WooData.PRODUCTS_TWO_HOODIES.data);
        productsPage.applyCouponCode(WooData.FIVE_PERCENT_ITEM.value);

        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_WA_SM.data);
        checkoutPage.clickOnCheckBox();
        checkoutPage.addShippingAddress(WooData.US_WA_SM.data);
        checkoutPage.clickPlaceOrder();

        assertEquals(checkoutPage.calculatePercentBasedTax(10.101), checkoutPage.getTaxFromUIAfterOrder());
    }
}
