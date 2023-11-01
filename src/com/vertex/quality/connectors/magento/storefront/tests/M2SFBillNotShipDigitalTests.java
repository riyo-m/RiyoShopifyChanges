package com.vertex.quality.connectors.magento.storefront.tests;

import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import com.vertex.quality.connectors.magento.storefront.tests.base.M2StorefrontBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * tests of the correct tax is displayed at each stage of inputting an order in
 * Magento Storefront as a customer different billing and shipping for a digital product
 *
 * @author alewis
 */
public class M2SFBillNotShipDigitalTests extends M2StorefrontBaseTest
{
	protected String virtualURL = MagentoData.STOREFRONT_VIRTUAL_PRODUCT_URL.data;
	protected String downloadableURL = MagentoData.STOREFRONT_DOWNLOADABLE_PRODUCT_URL.data;
	protected String jwUsername = MagentoData.JOHN_WINSTON_USERNAME.data;
	protected String jwPassword = MagentoData.STOREFRONT_PASSWORD.data;

	/**
	 * tests whether the correct tax is displayed in the Shopping Cart page Item section
	 * before address is inputted
	 */
	@Test()
	public void taxOnMinicartItemTest( )
	{
		signInToStorefront();
		M2StorefrontProductDetailsPage productDetailsPage = putInVirtualAndDownloadableProducts();

		String inclTaxSubtotal = productDetailsPage.getIncludeTaxCartSubtotal();
		String exclTaxSubtotal = productDetailsPage.getExcludeTaxCartSubtotal();
		String inclTaxFirstItem = productDetailsPage.getIncludeTaxInMinicartFirstItem();
		String exclTaxFirstItem = productDetailsPage.getExcludeTaxInMinicartFirstItem();
		String inclTaxSecondItem = productDetailsPage.getIncludeTaxInMinicartSecondItem();
		String exclTaxSecondItem = productDetailsPage.getExcludeTaxInMinicartSecondItem();

		clickLogoButton();
		clearShoppingCart();

		assertTrue(exclTaxSubtotal.equals(MagentoData.twoHundredDollars.data));
		assertTrue(inclTaxSubtotal.equals(MagentoData.twoHundredTwelveDollars.data));
		assertTrue(inclTaxFirstItem.equals(MagentoData.oneHundredSixDollars.data));
		assertTrue(exclTaxFirstItem.equals(MagentoData.oneHundredDollars.data));
		assertTrue(inclTaxSecondItem.equals(MagentoData.oneHundredSixDollars.data));
		assertTrue(exclTaxSecondItem.equals(MagentoData.oneHundredDollars.data));
	}

	/**
	 * test whether the correct tax is displayed in the Shopping Cart page Summary section
	 * before address is inputted
	 */
	@Test()
	public void taxOnShoppingCartAfterAddressTest( )
	{
		signInToStorefront();
		M2StorefrontShoppingCartPage shoppingCartPage = navigateToShoppingCartPage();

		String inclTaxSubtotalFirst = shoppingCartPage.getIncludingTaxSubtotalFirstItem();
		String exclTaxSubtotalFirst = shoppingCartPage.getExcludingTaxSubtotalFirstItem();
		String inclTaxSubtotalSecond = shoppingCartPage.getIncludingTaxSubtotalSecondItem();
		String exclTaxSubtotalSecond = shoppingCartPage.getExcludingTaxSubtotalSecondItem();

		String inclSubtotalSummary = shoppingCartPage.getSubtotalIncludingTaxInSummary();
		String exclSubtotalSummary = shoppingCartPage.getSubtotalExcludingTaxInSummary();
		String inclTotalSummary = shoppingCartPage.getTotalIncludingTaxInSummary();
		String exclTotalSummary = shoppingCartPage.getTotalExcludingTaxInSummary();

		clickLogoButton();
		clearShoppingCart();

		assertTrue(inclTaxSubtotalFirst.equals(MagentoData.oneHundredSixDollars.data));
		assertTrue(exclTaxSubtotalFirst.equals(MagentoData.oneHundredDollars.data));
		assertTrue(inclTaxSubtotalSecond.equals(MagentoData.oneHundredSixDollars.data));
		assertTrue(exclTaxSubtotalSecond.equals(MagentoData.oneHundredDollars.data));
		assertTrue(exclSubtotalSummary.equals(MagentoData.twoHundredDollars.data));
		assertTrue(inclSubtotalSummary.equals(MagentoData.twoHundredTwelveDollars.data));
		assertTrue(inclTotalSummary.equals(MagentoData.twoHundredTwelveDollars.data));
		assertTrue(exclTotalSummary.equals(MagentoData.twoHundredDollars.data));
	}

	/**
	 * test whether the correct tax is displayed in the Payment page Summary section
	 */
	@Test()
	public void TaxOnPaymentPageTest( )
	{
		signInToStorefront();
		M2StorefrontPaymentMethodPage paymentMethodPage = navigateToPaymentMethodPage();

		double inclTaxDouble = paymentMethodPage.getSubtotalInclTax();
		String inclTax = Double.toString(inclTaxDouble);

		double exclTaxDouble = paymentMethodPage.getSubtotalExclTax();
		String exclTax = Double.toString(exclTaxDouble);

		paymentMethodPage.openTaxBlind();

		double salesUseDouble = paymentMethodPage.getSalesUseTax();
		String salesUse = Double.toString(salesUseDouble);

		double inclTaxTotalDouble = paymentMethodPage.getTotalInclTax();
		String inclTaxTotal = Double.toString(inclTaxTotalDouble);

		double exclTaxTotalDouble = paymentMethodPage.getTotalExclTax();
		String exclTaxTotal = Double.toString(exclTaxTotalDouble);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(inclTax.equals(MagentoData.twoHundredTwelve.data));
		assertTrue(exclTax.equals(MagentoData.twoHundred.data));
		assertTrue(salesUse.equals(MagentoData.twelve.data));
		assertTrue(inclTaxTotal.equals(MagentoData.twoHundredTwelve.data));
		assertTrue(exclTaxTotal.equals(MagentoData.twoHundred.data));
	}

	/**
	 * test whether the correct tax is displayed in the Order Number page Items Ordered
	 */
	@Test()
	public void TaxOnStorefrontOrderReviewPageTest( )
	{
		signInToStorefront();
		M2StorefrontOrderNumberPage orderNumberPage = navigateToOrderNumberPage();

		double subtotalExlTax = orderNumberPage.getSubtotalExclTaxWholeOrder();
		String subtotalExclString = Double.toString(subtotalExlTax);

		double subtotalInclTax = orderNumberPage.getSubtotalInclTaxWholeOrder();
		String subtotalInclString = Double.toString(subtotalInclTax);

		double grandTotalExclTax = orderNumberPage.getGrandTotalExclTax();
		String exclTaxTotal = Double.toString(grandTotalExclTax);

		String salesUseTax = orderNumberPage.getSalesUseTax();

		String taxTotal = orderNumberPage.getTaxTotal();

		double grandTotalInclTax = orderNumberPage.getGrandTotalInclTax();
		String inclTaxTotal = Double.toString(grandTotalInclTax);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(subtotalExclString.equals(MagentoData.twoHundred.data));
		assertTrue(subtotalInclString.equals(MagentoData.twoHundredTwelve.data));
		assertTrue(exclTaxTotal.equals(MagentoData.twoHundred.data));
		assertTrue(salesUseTax.equals(MagentoData.twelveDollars.data));
		assertTrue(taxTotal.equals(MagentoData.twelveDollars.data));
		assertTrue(inclTaxTotal.equals(MagentoData.twoHundredTwelve.data));
	}

	protected M2StorefrontHomePage openStorefrontHomepage( )
	{
		driver.get(MagentoData.STOREFRONT_SIGN_ON_URL.data);

		M2StorefrontHomePage homePage = new M2StorefrontHomePage(driver);

		String pageTitle = homePage.getPageTitle();
		assertTrue(pageTitle.equals(homePageTitleText));

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

		loginPage.enterUsername(jwUsername);
		loginPage.enterPassword(jwPassword);
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
		driver.get(virtualURL);

		M2StorefrontProductDetailsPage detailsPage = new M2StorefrontProductDetailsPage(driver);

		detailsPage.clickAddToCartButton();

		driver.get(downloadableURL);

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

		shoppingCartPage.clickEstimateShippingTax();
		shoppingCartPage.selectState(MagentoData.PA_NUMBER.data);
		shoppingCartPage.enterZipCode(MagentoData.WESTCHESTER_ZIPCODE.data);

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

		shoppingCartPage.clickProceedToCheckout();

		if (shoppingCartPage.verifyPopUpLogin()){

			shoppingCartPage.popup.enterUserInfo(jwUsername, jwPassword);

			M2StorefrontShippingInfoPage shippingInfoPage = shoppingCartPage.clickProceedToCheckout();
		}

		M2StorefrontPaymentMethodPage paymentMethodPage = new M2StorefrontPaymentMethodPage(driver);

		paymentMethodPage.selectPaymentMethod();

		paymentMethodPage.clickEditAddress(MagentoData.WC_ADDRESS_OPTION.data);

		return paymentMethodPage;
	}

	/**
	 * navigates to Order Number page
	 *
	 * @return the Order Number page
	 */
	protected M2StorefrontOrderNumberPage navigateToOrderNumberPage( )
	{
		M2StorefrontPaymentMethodPage paymentMethodPage = navigateToPaymentMethodPage();

		M2StorefrontThankYouPage thankYouPage = paymentMethodPage.clickPlaceOrderButton();

		M2StorefrontOrderNumberPage numberPage = thankYouPage.clickOrderNumber();

		return numberPage;
	}
}
