package com.vertex.quality.connectors.shopify.ui.tests.store.quotation;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.shopify.base.ShopifyUIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * CCMMER-3067 Shopify - Test automation - Part 3
 * Shopify O Series registered customer tests
 *
 * @author Shivam.Soni
 */
public class ShopifyOSeriesRegisteredCustomerTests extends ShopifyUIBaseTest {

    ShopifyStoreAuthenticationPage storeAuthenticationPage;
    ShopifyStoreLoginPage storeLoginPage;
    ShopifyStoreHeaderPage storeHeaderPage;
    ShopifyStoreCartPage storeCartPage;
    ShopifyStoreSearchResultPage storeSearchResultPage;
    ShopifyStoreItemPage storeItemPage;
    ShopifyStoreShippingInfoPage storeShippingInfoPage;
    ShopifyStorePaymentPage storePaymentPage;

    /**
     * XRAYSHOP-22 Shopify - Quotation - Test Case - Create Sale Order where the Customer is registered in O Series and the Financial System
     */
    @Test(groups = {"shopify_regression", "shopify_quotation"})
    public void taxEstimationWithOSRegisteredCustomerTest() {

        // Load storefront
        loadShopifyStore();

        // Handle store authentication
        storeAuthenticationPage = new ShopifyStoreAuthenticationPage(driver);
        storeAuthenticationPage.enterIntoStoreByStorePassword();

        // Login to store
        storeHeaderPage = new ShopifyStoreHeaderPage(driver);
        storeHeaderPage.clickOnLoginAccount();
        storeLoginPage = new ShopifyStoreLoginPage(driver);
        storeLoginPage.loginToStore(ShopifyDataUI.StoreData.VTX_QA_VTX_TAXABLE_EMAIL.text, ShopifyDataUI.StoreData.VTX_QA_VTX_TAXABLE_KEY.text);
        storeHeaderPage.gotoStoreHomePage();

        // Delete all the cart items if any available in the cart
        if (storeHeaderPage.getCartItemCount() > 0) {
            storeCartPage = storeHeaderPage.clickOnCartIcon();
            storeCartPage.deleteAllCartItems();
        }

        // Search the product, open product & add to the cart
        storeHeaderPage.clickOnSearch();
        storeSearchResultPage = storeHeaderPage.searchInEntireStore(ShopifyDataUI.Products.COLLECTION_SNOWBOARD_HYDROGEN.text);
        storeItemPage = storeSearchResultPage.clickOnSearchedResult(ShopifyDataUI.Products.COLLECTION_SNOWBOARD_HYDROGEN.text);
        storeItemPage.clickAddToCart();
        storeCartPage = storeHeaderPage.clickOnCartIcon();

        // Checkout, enter destination address, select shipping method
        storeShippingInfoPage = storeCartPage.clickOnCheckout();
        storeShippingInfoPage.setShippingAddress(true, Address.SaltLakeCity.country.fullName, Address.SaltLakeCity.addressLine1,
                Address.SaltLakeCity.city, Address.SaltLakeCity.state.fullName, Address.SaltLakeCity.zip5);
        storeShippingInfoPage.clickOnContinueShipping();
        storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.FREE.text);

        // Payment, Place order & note down Order No.
        storePaymentPage = new ShopifyStorePaymentPage(driver);
        assertEquals(storePaymentPage.getTaxFromUIBeforeOrderPlace(), storePaymentPage.calculatePercentBasedTaxBeforeOrderPlace(7.45));
    }
}
