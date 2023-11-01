package com.vertex.quality.connectors.woocommerce.storefront.tests.basicSalesOrder;

import com.vertex.quality.connectors.woocommerce.admin.pages.WooCommerceAdminHomePage;
import com.vertex.quality.connectors.woocommerce.enums.WooData;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceCartPage;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceCheckoutPage;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceProductsPage;
import com.vertex.quality.connectors.woocommerce.storefront.tests.base.WooCommerceBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * CWOO-347 WOO - Calc - Create Sales Order basic (required fields only) - Invoice Request
 * CCMMER-464 WOO - tech debt reduction
 * All the test cases related to sales orders
 *
 * @author Shivam.Soni
 */
public class WooBasicSalesOrder extends WooCommerceBaseTest {

    WooCommerceAdminHomePage homePage;
    WooCommerceProductsPage productsPage;
    WooCommerceCartPage cartPage;
    WooCommerceCheckoutPage checkoutPage;

    /**
     * smoke test to test a basic order flow
     */
    @Test(groups = "woo_smoke")
    public void wooCreateSalesOrderTest() {
        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.FR_PARIS_75007.data);
        checkoutPage.clickPlaceOrder();
        assertTrue(checkoutPage.verifyOrderReceived());
    }

    /**
     * CWOO-350
     * WOO - Test Case -Create Sales Order with Modified Origin State
     */
    @Test(groups = "woo_regression")
    public void wooCommerceOrderModifiedOriginStateTest() {
        try {
            homePage = new WooCommerceAdminHomePage(driver);
            homePage.setWooStoreAddress(WooData.US_CA_LA.data[0] + " — " + WooData.US_CA_LA.data[3], WooData.US_CA_LA.data[1], WooData.US_CA_LA.data[2], WooData.US_CA_LA.data[4]);

            quitDriver();
            createChromeDriver();

            loadStoreFrontPage();
            productsPage = new WooCommerceProductsPage(driver);
            productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
            cartPage = new WooCommerceCartPage(driver);
            cartPage.goToCheckout();
            checkoutPage = new WooCommerceCheckoutPage(driver);
            checkoutPage.addBillingsAddress(WooData.US_CA_UC.data);
            checkoutPage.clickPlaceOrder();
            assertEquals(checkoutPage.calculatePercentageBasedTax(9.5), checkoutPage.getTaxFromUIAfterOrderPlace());
            assertEquals(WooData.TAX_TC_WOO_US_US_COMMON.value, checkoutPage.getTaxFromUIAfterOrderPlace());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            homePage = new WooCommerceAdminHomePage(driver);
            homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);
        }
    }

    /**
     * CWOO-349
     * WOO - Test Case -Create Sales Order with no State Tax, locally administered
     */
    @Test(groups = "woo_regression")
    public void wooCommerceNoStateTaxLocallyAdministeredTest() {
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_AL_JN.data);
        checkoutPage.clickPlaceOrder();
        assertEquals(checkoutPage.calculatePercentageBasedTax(5), checkoutPage.getTaxFromUIAfterOrderPlace());
        assertEquals(WooData.TAX_TC_WOO_348.value, checkoutPage.getTaxFromUIAfterOrderPlace());
    }

    /**
     * CWOO-348
     * WOO - Test Case -Create Sales Order with no State Tax
     */
    @Test(groups = "woo_regression")
    public void wooCommerceNoStateTaxSalesOrderTest() {
        try {
            homePage = new WooCommerceAdminHomePage(driver);
            homePage.setWooStoreAddress(WooData.US_CO_HR.data[0] + " — " + WooData.US_CO_HR.data[3], WooData.US_CO_HR.data[1], WooData.US_CO_HR.data[2], WooData.US_CO_HR.data[4]);

            quitDriver();
            createChromeDriver();

            loadStoreFrontPage();
            productsPage = new WooCommerceProductsPage(driver);
            productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
            cartPage = new WooCommerceCartPage(driver);
            cartPage.goToCheckout();
            checkoutPage = new WooCommerceCheckoutPage(driver);
            checkoutPage.addBillingsAddress(WooData.US_DE_WI.data);
            checkoutPage.clickPlaceOrder();
            assertTrue(checkoutPage.verifyOrderReceived() && checkoutPage.verifyTaxNotApplied());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            homePage = new WooCommerceAdminHomePage(driver);
            homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);
        }
    }
}
