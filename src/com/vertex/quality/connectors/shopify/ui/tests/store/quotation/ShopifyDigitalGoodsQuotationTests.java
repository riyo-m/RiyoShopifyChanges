package com.vertex.quality.connectors.shopify.ui.tests.store.quotation;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.shopify.base.ShopifyUIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

/**
 * CCMMER-3067 Shopify - Test automation - Part 3
 * Shopify Only Digital & Mixed basket (physical + digital goods) test
 *
 * @author Shivam.Soni
 */
public class ShopifyDigitalGoodsQuotationTests extends ShopifyUIBaseTest {

    ShopifyStoreAuthenticationPage storeAuthenticationPage;
    ShopifyStoreLoginPage storeLoginPage;
    ShopifyStoreHeaderPage storeHeaderPage;
    ShopifyStoreCartPage storeCartPage;
    ShopifyStoreSearchResultPage storeSearchResultPage;
    ShopifyStoreItemPage storeItemPage;
    ShopifyStoreShippingInfoPage storeShippingInfoPage;
    ShopifyStorePaymentPage storePaymentPage;

    /**
     * XRAYSHOP-30 Shopify - Quotation - Test Case -Create Sales Order with Digital Product
     */
    @Test(groups = {"shopify_regression", "shopify_quotation"})
    public void taxEstimationWithDigitalGoodsOnlyTest() {
        // Load storefront
        loadShopifyStore();

        // Handle store authentication
        storeAuthenticationPage = new ShopifyStoreAuthenticationPage(driver);
        storeAuthenticationPage.enterIntoStoreByStorePassword();

        // Login to store
        storeHeaderPage = new ShopifyStoreHeaderPage(driver);
        storeHeaderPage.clickOnLoginAccount();
        storeLoginPage = new ShopifyStoreLoginPage(driver);
        storeLoginPage.loginToStore();
        storeHeaderPage.gotoStoreHomePage();

        // Delete all the cart items if any available in the cart
        if (storeHeaderPage.getCartItemCount() > 0) {
            storeCartPage = storeHeaderPage.clickOnCartIcon();
            storeCartPage.deleteAllCartItems();
        }

        // Search the product, open product & add to the cart
        storeHeaderPage.clickOnSearch();
        storeSearchResultPage = storeHeaderPage.searchInEntireStore(ShopifyDataUI.Products.SUPER_MARIO_GAME.text);
        storeItemPage = storeSearchResultPage.clickOnSearchedResult(ShopifyDataUI.Products.SUPER_MARIO_GAME.text);
        storeItemPage.clickAddToCart();
        storeCartPage = storeHeaderPage.clickOnCartIcon();

        // Checkout, enter destination address, select shipping method
        storeShippingInfoPage = storeCartPage.clickOnCheckout();
        storeShippingInfoPage.setShippingAddress(true, Address.LosAngeles.country.fullName, Address.LosAngeles.addressLine1,
                Address.LosAngeles.city, Address.LosAngeles.state.fullName, Address.LosAngeles.zip5);

        // Payment, Place order & note down Order No.
        storePaymentPage = new ShopifyStorePaymentPage(driver);
        assertFalse(storePaymentPage.isTaxPresentOnUIBeforeOrderPlaceDigitalProducts());
    }

    /**
     * XRAYSHOP-29 Shopify - Quotation - Test Case -Create Sales Order with Gift Card
     */
    @Test(groups = {"shopify_regression", "shopify_quotation"})
    public void taxEstimationWithGiftCardOnlyTest() {
        // Load storefront
        loadShopifyStore();

        // Handle store authentication
        storeAuthenticationPage = new ShopifyStoreAuthenticationPage(driver);
        storeAuthenticationPage.enterIntoStoreByStorePassword();

        // Login to store
        storeHeaderPage = new ShopifyStoreHeaderPage(driver);
        storeHeaderPage.clickOnLoginAccount();
        storeLoginPage = new ShopifyStoreLoginPage(driver);
        storeLoginPage.loginToStore();
        storeHeaderPage.gotoStoreHomePage();

        // Delete all the cart items if any available in the cart
        if (storeHeaderPage.getCartItemCount() > 0) {
            storeCartPage = storeHeaderPage.clickOnCartIcon();
            storeCartPage.deleteAllCartItems();
        }

        // Search the product, open product & add to the cart
        storeHeaderPage.clickOnSearch();
        storeSearchResultPage = storeHeaderPage.searchInEntireStore(ShopifyDataUI.Products.SHOPIFY_GIFT_CARD.text);
        storeItemPage = storeSearchResultPage.clickOnSearchedResult(ShopifyDataUI.Products.SHOPIFY_GIFT_CARD.text);
        storeItemPage.clickAddToCart();
        storeCartPage = storeHeaderPage.clickOnCartIcon();

        // Checkout, enter destination address, select shipping method
        storeShippingInfoPage = storeCartPage.clickOnCheckout();
        storeShippingInfoPage.setShippingAddress(true, Address.Dallas.country.fullName, Address.Dallas.addressLine1,
                Address.Dallas.city, Address.Dallas.state.fullName, Address.Dallas.zip5);

        // Payment, Place order & note down Order No.
        storePaymentPage = new ShopifyStorePaymentPage(driver);
        assertFalse(storePaymentPage.isTaxPresentOnUIBeforeOrderPlaceDigitalProducts());
    }

    /**
     * XRAYSHOP-31 Shopify - Quotation - Test Case -Create Sales Order with Mixed Basket (Physical + Virtual products)
     */
    @Test(groups = {"shopify_regression", "shopify_quotation"})
    public void taxEstimationWithMixedBasketTest() {
        double taxWithoutDigitalProduct;

        // Load storefront
        loadShopifyStore();

        // Handle store authentication
        storeAuthenticationPage = new ShopifyStoreAuthenticationPage(driver);
        storeAuthenticationPage.enterIntoStoreByStorePassword();

        // Login to store
        storeHeaderPage = new ShopifyStoreHeaderPage(driver);
        storeHeaderPage.clickOnLoginAccount();
        storeLoginPage = new ShopifyStoreLoginPage(driver);
        storeLoginPage.loginToStore();
        storeHeaderPage.gotoStoreHomePage();

        // Delete all the cart items if any available in the cart
        if (storeHeaderPage.getCartItemCount() > 0) {
            storeCartPage = storeHeaderPage.clickOnCartIcon();
            storeCartPage.deleteAllCartItems();
        }

        // Search the product, open product & add to the cart
        storeHeaderPage.clickOnSearch();
        storeSearchResultPage = storeHeaderPage.searchInEntireStore(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
        storeItemPage = storeSearchResultPage.clickOnSearchedResult(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
        storeItemPage.clickAddToCart();
        storeCartPage = storeHeaderPage.clickOnCartIcon();

        // Checkout, enter destination address, select shipping method
        storeShippingInfoPage = storeCartPage.clickOnCheckout();
        storeShippingInfoPage.setShippingAddress(true, Address.UniversalCity.country.fullName, Address.UniversalCity.addressLine1,
                Address.UniversalCity.city, Address.UniversalCity.state.fullName, Address.UniversalCity.zip5);
        storeShippingInfoPage.clickOnContinueShipping();
        storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.STANDARD.text);

        // Payment, Place order & note down Order No.
        storePaymentPage = storeShippingInfoPage.clickContinuePayment();
        storePaymentPage.clickCODPayment();
        taxWithoutDigitalProduct = storePaymentPage.getTaxFromUIBeforeOrderPlace();
        assertEquals(taxWithoutDigitalProduct, storePaymentPage.calculatePercentBasedTaxBeforeOrderPlace(9.5));

        // Navigate to home page, search digital product & add it to the cart.
        loadShopifyStore();
        storeHeaderPage.clickOnSearch();
        storeSearchResultPage = storeHeaderPage.searchInEntireStore(ShopifyDataUI.Products.SUPER_MARIO_GAME.text);
        storeItemPage = storeSearchResultPage.clickOnSearchedResult(ShopifyDataUI.Products.SUPER_MARIO_GAME.text);
        storeItemPage.clickAddToCart();
        storeCartPage = storeHeaderPage.clickOnCartIcon();

        // Checkout, enter destination address, select shipping method
        storeShippingInfoPage = storeCartPage.clickOnCheckout();
        storeShippingInfoPage.setShippingAddress(true, Address.UniversalCity.country.fullName, Address.UniversalCity.addressLine1,
                Address.UniversalCity.city, Address.UniversalCity.state.fullName, Address.UniversalCity.zip5);
        storeShippingInfoPage.clickOnContinueShipping();
        storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.STANDARD.text);

        // Payment, Place order & note down Order No.
        storePaymentPage = storeShippingInfoPage.clickContinuePayment();
        storePaymentPage.clickCODPayment();

        assertEquals(taxWithoutDigitalProduct, storePaymentPage.getTaxFromUIBeforeOrderPlace());
    }
}
