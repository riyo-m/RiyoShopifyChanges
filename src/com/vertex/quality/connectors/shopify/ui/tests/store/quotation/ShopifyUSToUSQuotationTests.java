package com.vertex.quality.connectors.shopify.ui.tests.store.quotation;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.shopify.base.ShopifyUIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.*;
import com.vertex.quality.connectors.shopify.ui.pages.StoreFront.PaymentStoreFront;
import com.vertex.quality.connectors.shopify.ui.pages.StoreFront.StoreFrontContactDetailsPage;
import com.vertex.quality.connectors.shopify.ui.pages.StoreFront.StoreFrontPage;
import com.vertex.quality.connectors.shopify.ui.pages.StoreFront.StoreFrontTaxAndPaymentPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

/**
 * CCMMER-3066 Shopify - Test automation - Part 2, Contains all US to US tests
 *
 * @author Shivam.Soni
 */
public class ShopifyUSToUSQuotationTests extends ShopifyUIBaseTest
{

	ShopifyStoreAuthenticationPage storeAuthenticationPage;
	ShopifyStoreLoginPage storeLoginPage;
	ShopifyStoreHeaderPage storeHeaderPage;
	ShopifyStoreCartPage storeCartPage;
	ShopifyStoreSearchResultPage storeSearchResultPage;
	ShopifyStoreItemPage storeItemPage;
	ShopifyStoreShippingInfoPage storeShippingInfoPage;
	ShopifyStorePaymentPage storePaymentPage;
	PaymentStoreFront quotationPaymentPage;
	StoreFrontContactDetailsPage quotationStoreFrontContactDetailsPage;
	StoreFrontTaxAndPaymentPage quoationStoreFrontTaxAndPaymentPage;
	StoreFrontPage quotationStoreFrontPage;
	double tax;

	/**
	 * XRAYSHOP-62 Enter ship to address, select shipping method & validate the tax
	 */
	@Test(groups = { "shopify_smoke", "shopify_regression", "shopify_quotation" })
	public void taxEstimationWithShippingDetailsTest( ) throws InterruptedException
	{
		{
			// Load storefront
			loadShopifyStore();

			// Handle store authentication
			driver.get(ShopifyDataUI.StoreData.VTX_PROD_STORE.text);

			// Handle store authentication
			storeAuthenticationPage = new ShopifyStoreAuthenticationPage(driver);
			storeAuthenticationPage.enterIntoStoreByStorePassword();

			// Login to store
			storeHeaderPage = new ShopifyStoreHeaderPage(driver);
			//			storeHeaderPage.clickOnLoginAccount();
			//			storeLoginPage = new ShopifyStoreLoginPage(driver);
			//			storeLoginPage.loginToStore();
			//			storeHeaderPage.gotoStoreHomePage();

			// Delete all the cart items if any available in the cart
			//        if (storeHeaderPage.getCartItemCount() > 0) {
			//            storeCartPage = storeHeaderPage.clickOnCartIcon();
			//            storeCartPage.deleteAllCartItems();
			//        }

			// Search the product, open product & add to the cart
			storeHeaderPage.clickOnSearch();
			//        storeSearchResultPage = storeHeaderPage.searchInEntireStore(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
			//        storeItemPage = storeSearchResultPage.clickOnSearchedResult(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
			//        storeItemPage.clickAddToCart();
			//        storeCartPage = storeHeaderPage.clickOnCartIcon();
			quotationStoreFrontPage = new StoreFrontPage(driver);
			quotationStoreFrontPage.goToStore(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);

			quotationStoreFrontContactDetailsPage = new StoreFrontContactDetailsPage(driver);
			quotationStoreFrontContactDetailsPage.enterContactDetails();

			// Checkout, enter destination address, select shipping method
			//					storeCartPage = new ShopifyStoreCartPage(driver);
			//			       storeShippingInfoPage = storeCartPage.clickOnCheckout();
			//        storeShippingInfoPage.setShippingAddress(true, Address.Gettysburg.country.fullName, Address.Gettysburg.addressLine1,
			//                Address.Gettysburg.city, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5);
			storeShippingInfoPage = new ShopifyStoreShippingInfoPage(driver);
			storeShippingInfoPage.clickOnContinueShipping();
			storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.STANDARD.text);
			quotationPaymentPage = new PaymentStoreFront(driver);
			quotationPaymentPage.enterCreditCardDetails();

			quoationStoreFrontTaxAndPaymentPage = new StoreFrontTaxAndPaymentPage(driver);
			//		quoationStoreFrontTaxAndPaymentPage.calculatePercentTaxBeforeOrderPlace(13);
			tax = quoationStoreFrontTaxAndPaymentPage.getTaxFrmUIBeforeOrderPlace();
			//		System.out.println("Quotation Tax Before Fulfillment Tax :" + tax);
			//        // Payment, Place order & note down Order No.
			//        storePaymentPage = new ShopifyStorePaymentPage(driver);
			//        assertEquals(storePaymentPage.getTaxFromUIBeforeOrderPlace(), storePaymentPage.calculatePercentBasedTaxBeforeOrderPlaceUpRounding(6));
		}
	}
	//        // Load storefront
	//        loadShopifyStore();
	//
	//        // Handle store authentication
	//        storeAuthenticationPage = new ShopifyStoreAuthenticationPage(driver);
	//        storeAuthenticationPage.enterIntoStoreByStorePassword();
	//
	//        // Login to store
	//        storeHeaderPage = new ShopifyStoreHeaderPage(driver);
	//        storeHeaderPage.clickOnLoginAccount();
	//        storeLoginPage = new ShopifyStoreLoginPage(driver);
	//        storeLoginPage.loginToStore();
	//        storeHeaderPage.gotoStoreHomePage();
	//
	//        // Delete all the cart items if any available in the cart
	//        if (storeHeaderPage.getCartItemCount() > 0) {
	//            storeCartPage = storeHeaderPage.clickOnCartIcon();
	//            storeCartPage.deleteAllCartItems();
	//        }
	//
	//        // Search the product, open product & add to the cart
	//        storeHeaderPage.clickOnSearch();
	//        storeSearchResultPage = storeHeaderPage.searchInEntireStore(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
	//        storeItemPage = storeSearchResultPage.clickOnSearchedResult(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
	//        storeItemPage.clickAddToCart();
	//        storeCartPage = storeHeaderPage.clickOnCartIcon();
	//
	//        // Checkout, enter destination address, select shipping method
	//        storeShippingInfoPage = storeCartPage.clickOnCheckout();
	//        storeShippingInfoPage.setShippingAddress(true, Address.Gettysburg.country.fullName, Address.Gettysburg.addressLine1,
	//                Address.Gettysburg.city, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5);
	//        storeShippingInfoPage.clickOnContinueShipping();
	//        storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.STANDARD.text);
	//
	//        // Payment, Place order & note down Order No.
	//        storePaymentPage = new ShopifyStorePaymentPage(driver);
	//        assertEquals(storePaymentPage.getTaxFromUIBeforeOrderPlace(), storePaymentPage.calculatePercentBasedTaxBeforeOrderPlaceUpRounding(6));
	//    }

	/**
	 * XRAYSHOP-16 Shopify - Quotation - Test Case -Create Sales Order with Modified Origin State
	 */
	@Test(groups = { "shopify_regression", "shopify_quotation" })
	public void taxEstimationWithModifiedOriginStateTest( )
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
			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		storeItemPage = storeSearchResultPage.clickOnSearchedResult(
			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		storeItemPage.clickAddToCart();
		storeCartPage = storeHeaderPage.clickOnCartIcon();

		// Checkout, enter destination address, select shipping method
		storeShippingInfoPage = storeCartPage.clickOnCheckout();
		storeShippingInfoPage.setShippingAddress(true, Address.UniversalCity.country.fullName,
			Address.UniversalCity.addressLine1, Address.UniversalCity.city, Address.UniversalCity.state.fullName,
			Address.UniversalCity.zip5);
		storeShippingInfoPage.clickOnContinueShipping();
		storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.STANDARD.text);

		// Payment, Place order & note down Order No.
		storePaymentPage = new ShopifyStorePaymentPage(driver);
		assertEquals(storePaymentPage.getTaxFromUIBeforeOrderPlace(),
			storePaymentPage.calculatePercentBasedTaxBeforeOrderPlace(9.5));
	}

	/**
	 * XRAYSHOP-17 Shopify - Quotation - Test Case -Create Sales Order with no State Tax, locally administered
	 */
	@Test(groups = { "shopify_regression", "shopify_quotation" })
	public void taxEstimationWithLocallyAdministeredStateTaxTest( )
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
			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		storeItemPage = storeSearchResultPage.clickOnSearchedResult(
			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		storeItemPage.clickAddToCart();
		storeCartPage = storeHeaderPage.clickOnCartIcon();

		// Checkout, enter destination address, select shipping method
		storeShippingInfoPage = storeCartPage.clickOnCheckout();
		storeShippingInfoPage.setShippingAddress(true, Address.Juneau.country.fullName, Address.Juneau.addressLine1,
			Address.Juneau.city, Address.Juneau.state.fullName, Address.Juneau.zip5);
		storeShippingInfoPage.clickOnContinueShipping();
		storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.STANDARD.text);

		// Payment, Place order & note down Order No.
		storePaymentPage = new ShopifyStorePaymentPage(driver);
		assertEquals(storePaymentPage.getTaxFromUIBeforeOrderPlace(),
			storePaymentPage.calculatePercentBasedTaxBeforeOrderPlace(5));
	}

	/**
	 * XRAYSHOP-18 Shopify - Quotation - Test Case -Create Sales Order with no State Tax
	 */
	@Test(groups = { "shopify_regression", "shopify_quotation" })
	public void taxEstimationWithNoStateTaxTest( )
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
			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		storeItemPage = storeSearchResultPage.clickOnSearchedResult(
			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		storeItemPage.clickAddToCart();
		storeCartPage = storeHeaderPage.clickOnCartIcon();

		// Checkout, enter destination address, select shipping method
		storeShippingInfoPage = storeCartPage.clickOnCheckout();
		storeShippingInfoPage.setShippingAddress(true, Address.Delaware.country.fullName, Address.Delaware.addressLine1,
			Address.Delaware.city, Address.Delaware.state.fullName, Address.Delaware.zip5);
		storeShippingInfoPage.clickOnContinueShipping();
		storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.STANDARD.text);

		// Payment, Place order & note down Order No.
		storePaymentPage = new ShopifyStorePaymentPage(driver);
		assertFalse(storePaymentPage.isTaxPresentOnUIBeforeOrderPlace());
	}
}
