package com.vertex.quality.connectors.woocommerce.storefront.tests.CustomerCodeClass;

import com.vertex.quality.connectors.woocommerce.admin.pages.WooCommerceAdminHomePage;
import com.vertex.quality.connectors.woocommerce.admin.pages.WooCommerceAdminLoginPage;
import com.vertex.quality.connectors.woocommerce.enums.WooCommerceData;
import com.vertex.quality.connectors.woocommerce.enums.WooData;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceCartPage;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceCheckoutPage;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceProductsPage;
import com.vertex.quality.connectors.woocommerce.storefront.tests.base.WooCommerceBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

/**
 * Test Case CWOO-369.
 * WOO - Sales - Support Customer Code and Customer Class fields.
 *
 * @author vivek.kumar
 */
public class WooCommerceCustomerCodeExemptionTests extends WooCommerceBaseTest {

    WooCommerceAdminLoginPage adminLoginPage;
    WooCommerceAdminHomePage homePage;
    WooCommerceProductsPage productsPage;
    WooCommerceCartPage cartPage;
    WooCommerceCheckoutPage checkoutPage;

    /**
     * CWOO-371
     * WOO - Test Case - Create Sales order with Customer Code Exemption.
     */
    @Test(groups = "woo_regression")
    public void wooCommerceCustomerCodeExemptionTest() {
        // Setting up physical origin or Ship From address
        homePage = new WooCommerceAdminHomePage(driver);
        homePage.setWooStoreAddress(WooData.US_PA_GTY.data[0] + " — " + WooData.US_PA_GTY.data[3], WooData.US_PA_GTY.data[1], WooData.US_PA_GTY.data[2], WooData.US_PA_GTY.data[4]);

        quitDriver();
        createChromeDriver();

        driver.get(WooCommerceData.ADMIN_URL.data);
        adminLoginPage = new WooCommerceAdminLoginPage(driver);
        adminLoginPage.loginAsUser(WooCommerceData.EXEMPT_CUSTOMER_CODE_USERNAME.data, WooCommerceData.EXEMPT_CUSTOMER_CODE_PASSWORD.data);

        // Processing order
        loadStoreFrontPage();
        productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart(WooData.PRODUCT_BEANIE.value);
        cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingsAddress(WooData.US_CA_LA.data);
        checkoutPage.handleAddressCleansing(false);
        checkoutPage.clickPlaceOrder();
        assertFalse(checkoutPage.verifyIsTaxPresent());
    }
}