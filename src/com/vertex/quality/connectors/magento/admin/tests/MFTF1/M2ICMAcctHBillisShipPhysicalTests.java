package com.vertex.quality.connectors.magento.admin.tests.MFTF1;

import com.vertex.quality.connectors.magento.admin.pages.M2AdminCreditMemoPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminNewInvoicePage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrderViewInfoPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrdersPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * tests that tax is correct in each stage of putting in a invoice and credit navigateToOrdersTest();
 * for an existing account physical order
 * where billing and shipping addresses are the same
 *
 * @author alewis
 */
public class M2ICMAcctHBillisShipPhysicalTests extends MagentoAdminBaseTest
{
	protected String homepageURL = MagentoData.STOREFRONT_SIGN_ON_URL.data;
	protected String hoodieURL = MagentoData.STOREFRONT_HERO_HOODIE_URL.data;
	protected String tankURL = MagentoData.STOREFRONT_FITNESS_TANK_URL.data;
	protected String spcmURL = MagentoData.STOREFRONT_SPCM_URL.data;
	String wcUsername = MagentoData.ARDERN_STOREFRONT_USERNAME.data;
	String passwordString = MagentoData.STOREFRONT_PASSWORD.data;

	/**
	 * test subtotal tax in Refund Totals section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkSubtotalTaxInMemoPageRefundTotalsSectionTest( )
	{
		String correctTax = "1.03";

		navigateToCreditMemoPageForExistOrder();

		M2AdminCreditMemoPage creditMemoPage = new M2AdminCreditMemoPage(driver);

		String exclPrice = creditMemoPage.getRefundSubtotalExclTax();
		String inclPrice = creditMemoPage.getRefundSubtotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String slicedDifferenceString = String.format("%.2f", difference);

		assertTrue(correctTax.equals(slicedDifferenceString));
	}

	/**
	 * test tax in Refund Totals section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkTaxInMemoPageRefundTotalsSectionTest( )
	{
		String correctTax = "1.03";

		navigateToCreditMemoPageForExistOrder();

		M2AdminCreditMemoPage creditMemoPage = new M2AdminCreditMemoPage(driver);

		String exclPrice = creditMemoPage.getRefundTotalExclTax();
		String inclPrice = creditMemoPage.getRefundTotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String slicedDifferenceString = String.format("%.2f", difference);

		assertTrue(correctTax.equals(slicedDifferenceString));
	}

	protected M2StorefrontHomePage openStorefrontHomepage( )
	{
		driver.get(homepageURL);

		M2StorefrontHomePage homePage = new M2StorefrontHomePage(driver);

		//String pageTitle = homePage.getPageTitle();
		//assertTrue(pageTitle.equals(homePageTitleText));

		return homePage;
	}

	/**
	 * Signs into Magento Storefront
	 *
	 * @return Magento Storefront Homepage
	 */
	protected M2StorefrontHomePage signInToStorefront( )
	{
		M2StorefrontHomePage homePage = openStorefrontHomepage();

		M2StorefrontLoginPage loginPage = homePage.clickSignInButton();

		loginPage.enterUsername(wcUsername);
		loginPage.enterPassword(passwordString);
		M2StorefrontHomePage signedInHomepage = loginPage.clickSignInButton();

		return signedInHomepage;
	}

	/**
	 * adds a virtual and downloadable product to the shopping cart
	 *
	 * @return the product details page
	 */
	protected M2StorefrontProductDetailsPage putInVirtualAndDownloadableProducts( )
	{
		signInToStorefront();

		M2StorefrontProductDetailsPage detailsPage = new M2StorefrontProductDetailsPage(driver);

		driver.get(hoodieURL);

		detailsPage.selectSizeAndColor();

		detailsPage.clickAddToCartButton();

		clickLogoButton();
		clearShoppingCart();

		driver.get(hoodieURL);

		detailsPage.selectSizeAndColor();

		detailsPage.clickAddToCartButton();

		driver.get(tankURL);

		detailsPage.selectSizeAndColor();

		detailsPage.clickAddToCartButton();

		driver.get(spcmURL);

		detailsPage.clickAddToCartButton();

		detailsPage.clickCartButton();

		return detailsPage;
	}

	/**
	 * navigates to Shopping Cart page and enter order address, opens tax blind
	 *
	 * @return the Shopping Cart page
	 */
	protected M2StorefrontShoppingCartPage navigateToShoppingCartPage( )
	{
		M2StorefrontProductDetailsPage productDetailsPage = putInVirtualAndDownloadableProducts();

		M2StorefrontShoppingCartPage shoppingCartPage = productDetailsPage.clickViewAndEditButton();

		return shoppingCartPage;
	}

	/**
	 * navigates to Shopping Cart page and enter order address, opens tax blind
	 *
	 * @return the Shopping Cart page
	 */
	protected M2StorefrontShoppingCartPage navigateToShoppingCartPageAndAddAddress( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = navigateToShoppingCartPage();

		return shoppingCartPage;
	}

	/**
	 * navigates to Payment Method page
	 *
	 * @return the Payment Method page
	 */
	protected M2StorefrontPaymentMethodPage navigateToPaymentMethodPage( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = navigateToShoppingCartPageAndAddAddress();

		shoppingCartPage.clickEstimateShippingTax();
		shoppingCartPage.clickShippingRateSelector();

		M2StorefrontShippingInfoPage infoPage = shoppingCartPage.clickProceedToCheckout();

		if (shoppingCartPage.verifyPopUpLogin()){
			shoppingCartPage.popup.enterUserInfo(wcUsername, passwordString);
			shoppingCartPage.clickProceedToCheckout();
			if (shoppingCartPage.verifyPopUpLogin()){
				shoppingCartPage.closePopUpLogin();
				shoppingCartPage.clickProceedToCheckout();
			}
		}

		infoPage.clickNextButton();

		M2StorefrontPaymentMethodPage paymentMethodPage = new M2StorefrontPaymentMethodPage(driver);

		return paymentMethodPage;
	}

	/**
	 * navigates to Order Number page
	 *
	 * @return the Order Number page
	 */
	protected M2StorefrontThankYouPage navigateToOrderNumberPage( )
	{
		M2StorefrontPaymentMethodPage paymentMethodPage = navigateToPaymentMethodPage();

		paymentMethodPage.selectPaymentMethod();

		M2StorefrontThankYouPage thankYouPage = paymentMethodPage.clickPlaceOrderButton();

		return thankYouPage;
	}

	/**
	 * gets the order number from the thank you page after putting in order from storefront
	 *
	 * @return a String of the order number
	 */
	protected String getOrderFromStorefront( )
	{
		M2StorefrontThankYouPage thankYouPage = navigateToOrderNumberPage();
		String orderNumber = thankYouPage.getOrderNumber();
		clickLogoButton();
		clearShoppingCart();

		return orderNumber;
	}

	/**
	 * navigates to the new invoice page for a customer with same billing and shipping address order
	 */
	protected void navigateToInvoicePageForExistOrder( )
	{
		String orderNumber = getOrderFromStorefront();
		M2AdminOrdersPage ordersPage = navigateToOrders();

		ordersPage.clickOrder(orderNumber);
	}

	/**
	 * navigates to the new credit memo page for a customer with same billing and shipping address order
	 */
	protected void navigateToCreditMemoPageForExistOrder( )
	{
		String orderNumber = getOrderFromStorefront();
		M2AdminOrdersPage ordersPage = navigateToOrders();
		M2AdminOrderViewInfoPage orderViewInfoPage = ordersPage.clickOrder(orderNumber);

		M2AdminNewInvoicePage invoicePage = orderViewInfoPage.clickInvoiceButton();

		invoicePage.clickSubmitInvoiceButton();
		M2AdminCreditMemoPage creditMemoPage = invoicePage.clickCreditMemoButton();
		creditMemoPage.clicksTaxBlind();
	}
}