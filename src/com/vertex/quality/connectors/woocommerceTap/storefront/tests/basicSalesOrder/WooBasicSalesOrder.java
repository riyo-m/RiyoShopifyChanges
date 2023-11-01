package com.vertex.quality.connectors.woocommerceTap.storefront.tests.basicSalesOrder;

import com.vertex.quality.connectors.woocommerceTap.admin.pages.WooCommerceAdminHomePage;
import com.vertex.quality.connectors.woocommerceTap.enums.WooData;
import com.vertex.quality.connectors.woocommerceTap.storefront.pages.WooCommerceCartPage;
import com.vertex.quality.connectors.woocommerceTap.storefront.pages.WooCommerceCheckoutPage;
import com.vertex.quality.connectors.woocommerceTap.storefront.pages.WooCommerceProductsPage;
import com.vertex.quality.connectors.woocommerceTap.storefront.tests.base.WooCommerceBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * CCMMER2-347 WOO TAP - Test Automation - Part I
 *
 * @author Shivam.Soni
 */
public class WooBasicSalesOrder extends WooCommerceBaseTest {

    WooCommerceAdminHomePage homePage;
    WooCommerceProductsPage productsPage;
    WooCommerceCartPage cartPage;
    WooCommerceCheckoutPage checkoutPage;

    /**
     * CWOO-655 WooTAP - Test Case -Consignment Sales Order Invoice for VAT (DE FR)
     */
    @Test(groups = {"wooTap_regression", "wooTap_smoke"})
    public void checkTaxAmountInCreateNewOrderDEFRTest() {
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.DE_BERLIN_13405.data[0] + " — " + WooData.DE_BERLIN_13405.data[3], WooData.DE_BERLIN_13405.data[1], WooData.DE_BERLIN_13405.data[2], WooData.DE_BERLIN_13405.data[4]);
        homePage.signOutOfAdminPanel();

        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(false, WooData.FR_PARIS_75007.data);
        checkoutPage.clickPlaceOrder();
        assertEquals(checkoutPage.getTaxFromUIAfterPlacingOrder(), checkoutPage.calculatePercentBasedTax(20));
    }

    /**
     * CWOO-656 WooTAP - Test Case - Create Sale Order for VAT (Intra EU FR-DE) and Invoice
     */
    @Test(groups = "wooTap_regression")
    public void checkTaxAmountInCreateNewOrderIntraFRDETest() {
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.FR_PARIS_75007.data[0], WooData.FR_PARIS_75007.data[1], WooData.FR_PARIS_75007.data[2], WooData.FR_PARIS_75007.data[3]);
        homePage.signOutOfAdminPanel();

        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(true, WooData.DE_BERLIN_13405.data);
        checkoutPage.clickPlaceOrder();
        assertEquals(checkoutPage.getTaxFromUIAfterPlacingOrder(), checkoutPage.calculatePercentBasedTax(19));
    }

    /**
     * CWOO-657 WooTAP - Test Case - Create Sale Order for VAT (US-EU) and Invoice
     */
    @Test(groups = "wooTap_regression")
    public void checkTaxAmountInCreateNewOrderUSEUTest() {
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_BER.data[0] + " — " + WooData.US_PA_BER.data[3], WooData.US_PA_BER.data[1], WooData.US_PA_BER.data[2], WooData.US_PA_BER.data[4]);
        homePage.signOutOfAdminPanel();

        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(false, WooData.FR_PARIS_75007.data);
        checkoutPage.clickPlaceOrder();
        assertEquals(checkoutPage.getTaxFromUIAfterPlacingOrder(), checkoutPage.calculatePercentBasedTax(20));
    }

    /**
     * CWOO-658 WooTAP - Test Case - Create Sale Order for VAT (Greek Territory) and Invoice
     */
    @Test(groups = {"wooTap_regression"})
    public void checkTaxAmountInCreateNewOrderGreekTest() {
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.FR_PARIS_75007.data[0], WooData.FR_PARIS_75007.data[1], WooData.FR_PARIS_75007.data[2], WooData.FR_PARIS_75007.data[3]);
        homePage.signOutOfAdminPanel();

        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(true, WooData.GR_ANALIPSI_85900.data);
        checkoutPage.clickPlaceOrder();
        assertEquals(checkoutPage.getTaxFromUIAfterPlacingOrder(), checkoutPage.calculatePercentBasedTax(24));
    }

    /**
     * CWOO-659 WooTAP - Test Case - Create Sale Order for VAT (Austrian Sub-Division) and Invoice
     */
    @Test(groups = {"wooTap_regression"})
    public void checkTaxAmountInCreateNewOrderAustrianTest() {
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.DE_BERLIN_13405.data[0] + " — " + WooData.DE_BERLIN_13405.data[3], WooData.DE_BERLIN_13405.data[1], WooData.DE_BERLIN_13405.data[2], WooData.DE_BERLIN_13405.data[4]);
        homePage.signOutOfAdminPanel();

        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(false, WooData.AT_MITTELBERG_6993.data);
        checkoutPage.clickPlaceOrder();
        assertEquals(checkoutPage.getTaxFromUIAfterPlacingOrder(), checkoutPage.calculatePercentBasedTax(20));
    }
}
