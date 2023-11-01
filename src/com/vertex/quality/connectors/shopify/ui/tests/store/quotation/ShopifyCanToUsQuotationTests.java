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

public class ShopifyCanToUsQuotationTests extends ShopifyUIBaseTest
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
	 * XRAYSHOP-14 Shopify - Quotation - Test Case -Create Sales Order with Invoice CAN to US
	 */
	@Test(groups = { "shopify_smoke", "shopify_regression", "shopify_quotation" })
	public void taxEstimationCanToUSTest( ) throws InterruptedException
	{
		// Load storefront
		loadShopifyStore();

		// Handle store authentication
		driver.get(ShopifyDataUI.StoreData.VTX_PROD_STORE.text);

		// Handle store authentication
		storeAuthenticationPage = new ShopifyStoreAuthenticationPage(driver);
		storeAuthenticationPage.enterIntoStoreByStorePassword();

		storeHeaderPage = new ShopifyStoreHeaderPage(driver);
		storeHeaderPage.clickOnSearch();
		quotationStoreFrontPage = new StoreFrontPage(driver);
		quotationStoreFrontPage.goToStore(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);

		quotationStoreFrontContactDetailsPage = new StoreFrontContactDetailsPage(driver);
		quotationStoreFrontContactDetailsPage.enterContactDetails();

		quotationPaymentPage = new PaymentStoreFront(driver);
		quotationPaymentPage.enterCreditCardDetails();

		quoationStoreFrontTaxAndPaymentPage = new StoreFrontTaxAndPaymentPage(driver);
		//		quoationStoreFrontTaxAndPaymentPage.calculatePercentTaxBeforeOrderPlace(13);
		tax = quoationStoreFrontTaxAndPaymentPage.getTaxFrmUIBeforeOrderPlace();
		// Delete all the cart items if any available in the cart
		//        if (storeHeaderPage.getCartItemCount() > 0) {
		//            storeCartPage = storeHeaderPage.clickOnCartIcon();
		//            storeCartPage.deleteAllCartItems();
		//        }

		// Login to store
		//		storeHeaderPage = new ShopifyStoreHeaderPage(driver);
		//		storeHeaderPage.clickOnLoginAccount();
		//		storeLoginPage = new ShopifyStoreLoginPage(driver);
		//		storeLoginPage.loginToStore();
		//		storeHeaderPage.gotoStoreHomePage();

		// Search the product, open product & add to the cart

		//        storeSearchResultPage = storeHeaderPage.searchInEntireStore(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		//        storeItemPage = storeSearchResultPage.clickOnSearchedResult(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		//        storeItemPage.clickAddToCart();
		//        storeCartPage = storeHeaderPage.clickOnCartIcon();

		// Checkout, enter destination address, select shipping method
		//		storeCartPage = new ShopifyStoreCartPage(driver);
		//       storeShippingInfoPage = storeCartPage.clickOnCheckout();
		//        storeShippingInfoPage.setShippingAddress(true, Address.Gettysburg.country.fullName, Address.Gettysburg.addressLine1,
		//                Address.Gettysburg.city, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5);
		//        storeShippingInfoPage.clickOnContinueShipping();
		//        storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.STANDARD.text);

		//		System.out.println("Quotation Tax Before Fulfillment Tax :" + tax);
		//        // Payment, Place order & note down Order No.
		//        storePaymentPage = new ShopifyStorePaymentPage(driver);
		//        assertEquals(storePaymentPage.getTaxFromUIBeforeOrderPlace(), storePaymentPage.calculatePercentBasedTaxBeforeOrderPlaceUpRounding(6));
	}
}
