package com.vertex.quality.connectors.woocommerce.storefront.tests.addressCleansing;

import com.vertex.quality.connectors.woocommerce.admin.pages.WooCommerceAdminHomePage;
import com.vertex.quality.connectors.woocommerce.admin.pages.WooCommerceAdminLoginPage;
import com.vertex.quality.connectors.woocommerce.enums.WooCommerceData;
import com.vertex.quality.connectors.woocommerce.enums.WooData;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceCartPage;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceCheckoutPage;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceProductsPage;
import com.vertex.quality.connectors.woocommerce.storefront.tests.base.WooCommerceBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CWOO-603 - WOO - tech debt reduction - address cleansing (SHIP_FROM & SHIP_TO) and health check
 * In this class all SHIP_TO cases are covered for Woo Commerce Store
 *
 * @author Shivam.Soni
 */
public class WooAddressCleansingTests extends WooCommerceBaseTest {

    /**
     * CWOO-539 WOO - Address Cleansing OFF with Invalid Zip
     * this method is to check address cleansing features with Invalid zip.
     */
    @Test(groups = {"woo_regression", "woo_addressCleansing"})
    public void invalidZipTest() {
        driver.get(WooCommerceData.ADMIN_URL.data);
        WooCommerceAdminLoginPage adminLoginPage = new WooCommerceAdminLoginPage(driver);
        WooCommerceAdminHomePage homePage = adminLoginPage.loginAsUser(WooCommerceData.ADMIN_USERNAME.data, WooCommerceData.ADMIN_PASSWORD.data);
        homePage.goToStoreFromAdmin();

        WooCommerceProductsPage productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCT_HOODIE.data);
        productsPage.updateLineItems(WooData.QUANTITY_ONE.data, WooData.PRODUCT_HOODIE.data);

        WooCommerceCartPage cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        WooCommerceCheckoutPage checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_PA_INVALID_ZIP.data);
        Assert.assertEquals(checkoutPage.getAddressCleansingMessage(), WooData.ADDRESS_CLEANSING_MSG_INVALID_ZIP_MSG.value);
    }

    /**
     * CWOO-540 WOO - Address Cleansing OFF with No Zip.
     * this method is to check address cleansing features with No zip.
     */
    @Test(groups = {"woo_regression", "woo_addressCleansing"})
    public void noZipTest() {
        driver.get(WooCommerceData.ADMIN_URL.data);
        WooCommerceAdminLoginPage adminLoginPage = new WooCommerceAdminLoginPage(driver);
        WooCommerceAdminHomePage homePage = adminLoginPage.loginAsUser(WooCommerceData.ADMIN_USERNAME.data, WooCommerceData.ADMIN_PASSWORD.data);
        homePage.goToStoreFromAdmin();

        WooCommerceProductsPage productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCT_HOODIE.data);
        productsPage.updateLineItems(WooData.QUANTITY_ONE.data, WooData.PRODUCT_HOODIE.data);

        WooCommerceCartPage cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        WooCommerceCheckoutPage checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_PA_EMPTY_ZIP.data);
        Assert.assertEquals(checkoutPage.getAddressCleansingMessage(), WooData.ADDRESS_CLEANSING_NO_ZIP_MSG.value);
    }

    /**
     * CWOO-541 WOO - Address Cleansing Continue to Calc OFF with Invalid Zip - Shipping Address.
     * this method is to check address cleansing features with Invalid zip and calc off .
     */
    @Test(groups = {"woo_regression", "woo_addressCleansing"})
    public void calcOffInvalidZipTest() {
        WooCommerceAdminHomePage homePage = new WooCommerceAdminHomePage(driver);
        homePage.enableDisableTaxCalculation(false);
        homePage.goToStoreFromAdmin();
        try {
            WooCommerceProductsPage productsPage = new WooCommerceProductsPage(driver);
            productsPage.addItemToCartViewCarts(WooData.PRODUCT_HOODIE.data);
            productsPage.updateLineItems(WooData.QUANTITY_ONE.data, WooData.PRODUCT_HOODIE.data);
            WooCommerceCartPage cartPage = new WooCommerceCartPage(driver);
            cartPage.goToCheckout();
            WooCommerceCheckoutPage checkoutPage = new WooCommerceCheckoutPage(driver);
            checkoutPage.addBillingsAddress(WooData.US_PA_INVALID_ZIP.data);
            Assert.assertEquals(checkoutPage.getAddressCleansingMessage(), WooData.ADDRESS_CLEANSING_MSG_INVALID_ZIP_MSG.value);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            homePage.enableDisableTaxCalculation(true);
        }
    }

    /**
     * CWOO-544 WOO - Validate Sales Order with Address Cleansing No Zip.
     * this method is to check address cleansing features with NULL zip and Bill To Address .
     */
    @Test(groups = {"woo_regression", "woo_addressCleansing"})
    public void noZipWithBillingAddressTest() {
        driver.get(WooCommerceData.ADMIN_URL.data);
        WooCommerceAdminLoginPage adminLoginPage = new WooCommerceAdminLoginPage(driver);
        WooCommerceAdminHomePage homePage = adminLoginPage.loginAsUser(WooCommerceData.ADMIN_USERNAME.data, WooCommerceData.ADMIN_PASSWORD.data);
        homePage.goToStoreFromAdmin();

        WooCommerceProductsPage productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCT_HOODIE.data);
        productsPage.updateLineItems(WooData.QUANTITY_ONE.data, WooData.PRODUCT_HOODIE.data);

        WooCommerceCartPage cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        WooCommerceCheckoutPage checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_PA_NULL_ZIP.data);
        Assert.assertEquals(checkoutPage.getAddressCleansingMessage(), WooData.ADDRESS_CLEANSING_NULL_ZIP_MSG.value);
    }

    /**
     * CWOO-542 WOO - Validate addresses with address cleansing OFF - Shipping Address.
     * this method is to check address cleansing features with blank zip.
     */
    @Test(groups = {"woo_regression", "woo_addressCleansing"})
    public void shippingAddressTest() {
        driver.get(WooCommerceData.ADMIN_URL.data);
        WooCommerceAdminLoginPage adminLoginPage = new WooCommerceAdminLoginPage(driver);
        WooCommerceAdminHomePage homePage = adminLoginPage.loginAsUser(WooCommerceData.ADMIN_USERNAME.data, WooCommerceData.ADMIN_PASSWORD.data);
        homePage.goToStoreFromAdmin();

        WooCommerceProductsPage productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCT_HOODIE.data);
        productsPage.updateLineItems(WooData.QUANTITY_ONE.data, WooData.PRODUCT_HOODIE.data);

        WooCommerceCartPage cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        WooCommerceCheckoutPage checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_PA_BLANK_ZIP.data);
        Assert.assertEquals(checkoutPage.getAddressCleansingMessage(), WooData.ADDRESS_CLEANSING_BLANK_ZIP_MSG.value);
    }

    /**
     * CWOO-469 - WOO - Address Cleansing OFF with Invalid City and Zip
     * this method is to check address cleansing with invalid city & invalid zip
     */
    @Test(groups = {"woo_regression", "woo_addressCleansing"})
    public void invalidCityZipTest() {
        driver.get(WooCommerceData.ADMIN_URL.data);
        WooCommerceAdminLoginPage adminLoginPage = new WooCommerceAdminLoginPage(driver);
        WooCommerceAdminHomePage homePage = adminLoginPage.loginAsUser(WooCommerceData.ADMIN_USERNAME.data, WooCommerceData.ADMIN_PASSWORD.data);
        homePage.goToStoreFromAdmin();

        WooCommerceProductsPage productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCT_HOODIE.data);
        productsPage.updateLineItems(WooData.QUANTITY_ONE.data, WooData.PRODUCT_HOODIE.data);

        WooCommerceCartPage cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        WooCommerceCheckoutPage checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_PA_INVALID_CITY_ZIP.data);
        Assert.assertEquals(checkoutPage.getAddressCleansingMessage(), WooData.ADDRESS_CLEANSING_INVALID_CITY_ZIP_MSG.value);
    }

    /**
     * CWOO-470 - WOO - Address Cleansing OFF with Invalid State and Zip
     * this method is to check address cleansing with invalid state & invalid zip
     */
    @Test(groups = {"woo_regression", "woo_addressCleansing"})
    public void invalidStateZipTest() {
        driver.get(WooCommerceData.ADMIN_URL.data);
        WooCommerceAdminLoginPage adminLoginPage = new WooCommerceAdminLoginPage(driver);
        WooCommerceAdminHomePage homePage = adminLoginPage.loginAsUser(WooCommerceData.ADMIN_USERNAME.data, WooCommerceData.ADMIN_PASSWORD.data);
        homePage.goToStoreFromAdmin();

        WooCommerceProductsPage productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCarts(WooData.PRODUCT_HOODIE.data);
        productsPage.updateLineItems(WooData.QUANTITY_ONE.data, WooData.PRODUCT_HOODIE.data);

        WooCommerceCartPage cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        WooCommerceCheckoutPage checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_PA_INVALID_STATE_ZIP.data);
        Assert.assertEquals(checkoutPage.getAddressCleansingMessage(), WooData.ADDRESS_CLEANSING_INVALID_STATE_ZIP_MSG.value);
    }
}
