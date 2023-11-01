package com.vertex.quality.connectors.shopify.ui.tests.store.quotation;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.shopify.base.ShopifyUIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * CCMMER-3066 Shopify - Test automation - Part 2, Contains all Canada to Canada tests
 *
 * @author Shivam.Soni
 */
public class ShopifyCanToCanQuotationTests extends ShopifyUIBaseTest {

    ShopifyStoreAuthenticationPage storeAuthenticationPage;
    ShopifyStoreLoginPage storeLoginPage;
    ShopifyStoreHeaderPage storeHeaderPage;
    ShopifyStoreCartPage storeCartPage;
    ShopifyStoreSearchResultPage storeSearchResultPage;
    ShopifyStoreItemPage storeItemPage;
    ShopifyStoreShippingInfoPage storeShippingInfoPage;
    ShopifyStorePaymentPage storePaymentPage;

    /**
     * XRAYSHOP-12 Shopify - Quotation - Test Case -Create Sales Order with Quotation CANQC to CANBC (GST/PST)
     */
    @Test(groups = {"shopify_regression", "shopify_quotation"})
    public void taxEstimationWithCANQCToCANBCTest() throws InterruptedException
	{
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
        storeShippingInfoPage.setShippingAddress(true, Address.Victoria.country.fullName, Address.Victoria.addressLine1,
                Address.Victoria.city, Address.Victoria.province.fullName, Address.Victoria.zip5);
        storeShippingInfoPage.clickOnContinueShipping();
        storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.INTERNATIONAL.text);

        // Payment, Place order & note down Order No.
        storePaymentPage = new ShopifyStorePaymentPage(driver);
        assertEquals(storePaymentPage.getTaxFromUIBeforeOrderPlace(), storePaymentPage.calculatePercentBasedTaxBeforeOrderPlace(12));
    }

    /**
     * XRAYSHOP-10 Shopify - Quotation - Test Case -Create Sales Order with Invoice CANBC to CANQC different Province (GST/QST)
     */
    @Test(groups = {"shopify_regression", "shopify_quotation"})
    public void taxEstimationWithCANBCToCANQCTest() throws InterruptedException
	{
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
        storeShippingInfoPage.setShippingAddress(true, Address.QuebecCity.country.fullName, Address.QuebecCity.addressLine1,
                Address.QuebecCity.city, Address.QuebecCity.province.fullName, Address.QuebecCity.zip5);
        storeShippingInfoPage.clickOnContinueShipping();
        storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.INTERNATIONAL.text);

        // Payment, Place order & note down Order No.
        storePaymentPage = new ShopifyStorePaymentPage(driver);
        assertEquals(storePaymentPage.getTaxFromUIBeforeOrderPlace(), storePaymentPage.calculatePercentBasedTaxBeforeOrderPlaceUpRounding(14.975));
    }

    /**
     * XRAYSHOP-13 Shopify - Quotation - Test Case -Create Sales Order with Invoice CANNB to CANNB same Province (HST)
     */
    @Test(groups = {"shopify_regression", "shopify_quotation"})
    public void taxEstimationWithCANNBToCANNBTest() throws InterruptedException
	{
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
        storeShippingInfoPage.setShippingAddress(true, Address.Quispamsis.country.fullName, Address.Quispamsis.addressLine1,
                Address.Quispamsis.city, Address.Quispamsis.province.fullName, Address.Quispamsis.zip5);
        storeShippingInfoPage.clickOnContinueShipping();
        storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.INTERNATIONAL.text);

        // Payment, Place order & note down Order No.
        storePaymentPage = new ShopifyStorePaymentPage(driver);
        assertEquals(storePaymentPage.getTaxFromUIBeforeOrderPlace(), storePaymentPage.calculatePercentBasedTaxBeforeOrderPlace(15));
    }

    /**
     * XRAYSHOP-11 Shopify - Quotation - Test Case -Create Sales Order with Invoice CANBC to CANON different Province (GST/HST)
     */
    @Test(groups = {"shopify_regression", "shopify_quotation"})
    public void taxEstimationWithCANBCToCANONTest() throws InterruptedException
	{
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
        storeShippingInfoPage.setShippingAddress(true, Address.Ottawa.country.fullName, Address.Ottawa.addressLine1,
                Address.Ottawa.city, Address.Ottawa.province.fullName, Address.Ottawa.zip5);
        storeShippingInfoPage.clickOnContinueShipping();
        storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.INTERNATIONAL.text);

        // Payment, Place order & note down Order No.
        storePaymentPage = new ShopifyStorePaymentPage(driver);
        assertEquals(storePaymentPage.getTaxFromUIBeforeOrderPlace(), storePaymentPage.calculatePercentBasedTaxBeforeOrderPlace(13));
    }
}
