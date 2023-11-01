package com.vertex.quality.connectors.shopify.ui.tests.store.quotation;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.shopify.base.ShopifyUIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

/**
 * CCMMER-4066 Shopify - Test automation - Part 5
 * Shopify Product level exemption tests
 *
 * @author Shivam.Soni
 */
public class ShopifyProductExemptionQuotationTests extends ShopifyUIBaseTest {

    ShopifyStoreAuthenticationPage storeAuthenticationPage;
    ShopifyStoreLoginPage storeLoginPage;
    ShopifyStoreHeaderPage storeHeaderPage;
    ShopifyStoreCartPage storeCartPage;
    ShopifyStoreSearchResultPage storeSearchResultPage;
    ShopifyStoreItemPage storeItemPage;
    ShopifyStoreShippingInfoPage storeShippingInfoPage;
    ShopifyStorePaymentPage storePaymentPage;

    /**
     * XRAYSHOP-84 Shopify - Quotation - Test Case - Create Sales order with Vertex's exempted product (Product Code)
     */
    @Test(groups = {"shopify_regression", "shopify_quotation"})
    public void vertexProductCodeTest() throws InterruptedException
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
        storeSearchResultPage = storeHeaderPage.searchInEntireStore(ShopifyDataUI.Products.VERTEX_EXEMPTED_PRODUCT.text);
        storeItemPage = storeSearchResultPage.clickOnSearchedResult(ShopifyDataUI.Products.VERTEX_EXEMPTED_PRODUCT.text);
        storeItemPage.clickAddToCart();
        storeCartPage = storeHeaderPage.clickOnCartIcon();

        // Checkout, enter destination address, select shipping method
        storeShippingInfoPage = storeCartPage.clickOnCheckout();
        storeShippingInfoPage.setShippingAddress(true, Address.LosAngeles.country.fullName, Address.LosAngeles.addressLine1,
                Address.LosAngeles.city, Address.LosAngeles.state.fullName, Address.LosAngeles.zip5);
        storeShippingInfoPage.clickOnContinueShipping();
        storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.STANDARD.text);

        // Payment, Place order & note down Order No.
        storePaymentPage = new ShopifyStorePaymentPage(driver);
        assertFalse(storePaymentPage.isTaxPresentOnUIBeforeOrderPlace());
    }

    /**
     * XRAYSHOP-35 Shopify - Quotation - Test Case - Create Sales order with Shopify exempted product
     */
    @Test(groups = {"shopify_regression", "shopify_quotation"})
    public void shopifyExemptedProductTest() throws InterruptedException
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
        storeSearchResultPage = storeHeaderPage.searchInEntireStore(ShopifyDataUI.Products.SHOPIFY_EXEMPTED_PRODUCT.text);
        storeItemPage = storeSearchResultPage.clickOnSearchedResult(ShopifyDataUI.Products.SHOPIFY_EXEMPTED_PRODUCT.text);
        storeItemPage.clickAddToCart();
        storeCartPage = storeHeaderPage.clickOnCartIcon();

        // Checkout, enter destination address, select shipping method
        storeShippingInfoPage = storeCartPage.clickOnCheckout();
        storeShippingInfoPage.setShippingAddress(true, Address.Dallas.country.fullName, Address.Dallas.addressLine1,
                Address.Dallas.city, Address.Dallas.state.fullName, Address.Dallas.zip5);
        storeShippingInfoPage.clickOnContinueShipping();
        storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.STANDARD.text);

        // Payment, Place order & note down Order No.
        storePaymentPage = new ShopifyStorePaymentPage(driver);
        assertFalse(storePaymentPage.isTaxPresentOnUIBeforeOrderPlace());
    }
}
