package com.vertex.quality.connectors.woocommerce.storefront.tests.ProductCodeClass;

import com.vertex.quality.connectors.woocommerce.admin.pages.WooCommerceAdminHomePage;
import com.vertex.quality.connectors.woocommerce.enums.WooCommerceData;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceCartPage;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceCheckoutPage;
import com.vertex.quality.connectors.woocommerce.storefront.pages.WooCommerceProductsPage;
import com.vertex.quality.connectors.woocommerce.storefront.tests.base.WooCommerceBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test Case CWOO-366
 * WOO - Sales - Support Product Code / Class
 *
 * @author vivek.kumar
 */
public class WooCommerceProductCodeExemptionTests extends WooCommerceBaseTest {
    protected String totalString;

    /**
     * CWOO-368
     * WOO - Test Case - Create Sales order with Product Code Exemption.
     */
    @Test(groups = "woo_regression")
    public void wooCommerceProductCodeExemptionTest() {
        WooCommerceAdminHomePage homePage = new WooCommerceAdminHomePage(driver);
        homePage.setProductCode(WooCommerceData.PRODUCT_CODE.data);
        loadStoreFrontPage();
        WooCommerceProductsPage productsPage = new WooCommerceProductsPage(driver);
        productsPage.addItemToCartViewCart();
        WooCommerceCartPage cartPage = new WooCommerceCartPage(driver);
        cartPage.goToCheckout();
        WooCommerceCheckoutPage checkoutPage = new WooCommerceCheckoutPage(driver);
        checkoutPage.addBillingAddress(WooCommerceData.US_BILLING1_FIRST_NAME.data,
                WooCommerceData.US_BILLING1_LAST_NAME.data, WooCommerceData.US_BILLING1_COUNTRY.data,
                WooCommerceData.US_BILLING1_STREET0.data, WooCommerceData.US_BILLING1_ZIP.data,
                WooCommerceData.US_BILLING1_CITY.data, WooCommerceData.US_BILLING1_PHONE.data,
                WooCommerceData.BLANK_VAT.data);

        checkoutPage.clickPlaceOrder();
        double total = checkoutPage.calculateTax();

        totalString = Double.toString(total);
        Assert.assertEquals(WooCommerceData.US_TAX_RATE.data, totalString);
    }
}