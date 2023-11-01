package com.vertex.quality.connectors.shopify.ui.tests.store.quotation;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.shopify.base.ShopifyUIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * CCMMER-3067 Shopify - Test automation - Part 3
 * Shopify bulk qty order tests
 *
 * @author Shivam.Soni
 */
public class ShopifyBulkQtyTests extends ShopifyUIBaseTest
{

	ShopifyStoreAuthenticationPage storeAuthenticationPage;
	ShopifyStoreLoginPage storeLoginPage;
	ShopifyStoreHeaderPage storeHeaderPage;
	ShopifyStoreCartPage storeCartPage;
	ShopifyStoreSearchResultPage storeSearchResultPage;
	ShopifyStoreItemPage storeItemPage;
	ShopifyStoreShippingInfoPage storeShippingInfoPage;
	ShopifyStorePaymentPage storePaymentPage;

	/**
	 * XRAYSHOP-9 Shopify - Test Case - Create Quotation with bulk quantities 10
	 */
	@Test(groups = { "shopify_regression", "shopify_quotation" })
	public void taxEstimationWithBulkQtyTest( ) throws InterruptedException
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
		if ( storeHeaderPage.getCartItemCount() > 0 )
		{
			storeCartPage = storeHeaderPage.clickOnCartIcon();
			storeCartPage.deleteAllCartItems();
		}

		// Search the product, open product & add to the cart
		storeHeaderPage.clickOnSearch();
		storeSearchResultPage = storeHeaderPage.searchInEntireStore(
			ShopifyDataUI.Products.COLLECTION_SNOWBOARD_HYDROGEN.text);
		storeItemPage = storeSearchResultPage.clickOnSearchedResult(
			ShopifyDataUI.Products.COLLECTION_SNOWBOARD_HYDROGEN.text);
		storeItemPage.clickAddToCart();
		storeCartPage = storeHeaderPage.clickOnCartIcon();

		// Update quantity
		storeCartPage.updateQuantity(ShopifyDataUI.Products.COLLECTION_SNOWBOARD_HYDROGEN.text,
			ShopifyDataUI.Quantities.QTY_10.text);

		// Go to store home page, search & add another product
		loadShopifyStore();
		storeHeaderPage.clickOnSearch();
		storeSearchResultPage = storeHeaderPage.searchInEntireStore(
			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		storeItemPage = storeSearchResultPage.clickOnSearchedResult(
			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		storeItemPage.clickAddToCart();
		storeCartPage = storeHeaderPage.clickOnCartIcon();

		// Checkout, enter destination address, select shipping method
		storeShippingInfoPage = storeCartPage.clickOnCheckout();
		storeShippingInfoPage.setShippingAddress(true, Address.Washington.country.fullName,
			Address.Washington.addressLine1, Address.Washington.city, Address.Washington.state.fullName,
			Address.Washington.zip5);
		storeShippingInfoPage.clickOnContinueShipping();
		storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.STANDARD.text);
Thread.sleep(5000);
		// Payment, Place order & note down Order No.
		storePaymentPage = new ShopifyStorePaymentPage(driver);
		assertEquals(storePaymentPage.getTaxFromUIBeforeOrderPlace(),
			storePaymentPage.calculatePercentBasedTaxBeforeOrderPlace(10.1));
	}
}
