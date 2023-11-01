package com.vertex.quality.connectors.magento.storefront.tests;

import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import com.vertex.quality.connectors.magento.storefront.tests.base.M2StorefrontBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * tests of the correct tax is displayed at each stage of inputting an order in
 * Magento Storefront as an existing customer with same billing and shipping for a physical product
 *
 * @author alewis
 */
public class M2SFBillisShipPhysicalTests extends M2StorefrontBaseTest
{
	protected String homepageURL = MagentoData.STOREFRONT_SIGN_ON_URL.data;
	protected String hoodieURL = MagentoData.STOREFRONT_HERO_HOODIE_URL.data;
	protected String tankURL = MagentoData.STOREFRONT_FITNESS_TANK_URL.data;
	protected String spcmURL = MagentoData.STOREFRONT_SPCM_URL.data;
	String wcUsername = MagentoData.ARDERN_STOREFRONT_USERNAME.data;
	String passwordString = MagentoData.STOREFRONT_PASSWORD.data;

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

		assertTrue(exclTaxSubtotal.equals(MagentoData.oneHundredSeventyTwoDollars.data));
		assertTrue(inclTaxSubtotal.equals(MagentoData.oneHundredSeventySix_ThirtyTwo_Dollars.data));
		assertTrue(inclTaxFirstItem.equals(MagentoData.oneHundredDollars.data));
		assertTrue(exclTaxFirstItem.equals(MagentoData.oneHundredDollars.data));
		assertTrue(inclTaxSecondItem.equals(MagentoData.nineteen_EightDollars.data));
		assertTrue(exclTaxSecondItem.equals(MagentoData.eighteenDollars.data));
	}

	/**
	 * test whether the correct tax is displayed in the Shopping Cart page Summary section
	 * before address is inputted
	 */
	@Test()
	public void taxShoppingCartBeforeAddressTest( )
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

		assertTrue(inclTaxSubtotalFirst.equals(MagentoData.FiftySevenTwentyFourDollars.data));
		assertTrue(exclTaxSubtotalFirst.equals(MagentoData.FiftyFourDollars.data));
		assertTrue(inclTaxSubtotalSecond.equals(MagentoData.nineteen_EightDollars.data));
		assertTrue(exclTaxSubtotalSecond.equals(MagentoData.eighteenDollars.data));
		assertTrue(exclSubtotalSummary.equals(MagentoData.oneHundredSeventyTwoDollars.data));
		assertTrue(inclSubtotalSummary.equals(MagentoData.oneHundredSeventySix_ThirtyTwo_Dollars.data));
		assertTrue(inclTotalSummary.equals(MagentoData.oneHundredSeventySix_ThirtyTwo_Dollars.data));
		assertTrue(exclTotalSummary.equals(MagentoData.oneHundredSeventyTwoDollars.data));
	}

	/**
	 * test whether the correct tax is displayed in the Shopping Cart page Summary section
	 * after address is inputted
	 */
	@Test()
	public void taxShoppingCartAfterAddressTest( )
	{
		String correctAmountSecondItemInc = "$19.08";
		String correctAmountSecondItemExc = "$18.00";
		signInToStorefront();
		M2StorefrontShoppingCartPage shoppingCartPage = navigateToShoppingCartPageAndAddAddress();

		String inclTaxSubtotalSecond = shoppingCartPage.getIncludingTaxSubtotalSecondItem();
		String exclTaxSubtotalSecond = shoppingCartPage.getExcludingTaxSubtotalSecondItem();
		String inclTaxSubtotalThird = shoppingCartPage.getIncludingTaxSubtotalThirdItem();
		String exclTaxSubtotalThird = shoppingCartPage.getExcludingTaxSubtotalThirdItem();

		String inclSubtotalSummary = shoppingCartPage.getSubtotalIncludingTaxInSummary();
		String exclSubtotalSummary = shoppingCartPage.getSubtotalExcludingTaxInSummary();
		String taxAmount = shoppingCartPage.getTaxBlindTotal();
		String salesUseTax = shoppingCartPage.getSalesAndUseTax();
		String inclTotalSummary = shoppingCartPage.getTotalIncludingTaxInSummary();
		String exclTotalSummary = shoppingCartPage.getTotalExcludingTaxInSummary();

		clickLogoButton();
		clearShoppingCart();

		assertTrue(inclTaxSubtotalThird.equals(MagentoData.oneHundredDollars.data));
		assertTrue(exclTaxSubtotalThird.equals(MagentoData.oneHundredDollars.data));
		assertTrue(inclTaxSubtotalSecond.equals(correctAmountSecondItemInc));
		assertTrue(exclTaxSubtotalSecond.equals(correctAmountSecondItemExc));
		assertTrue(exclSubtotalSummary.equals(MagentoData.oneHundredSeventyTwoDollars.data));
		assertTrue(inclSubtotalSummary.equals(MagentoData.oneHundredSeventySix_ThirtyTwo_Dollars.data));
		assertTrue(taxAmount.equals(MagentoData.four_thirtyTwoDollars.data));
		assertTrue(salesUseTax.equals(MagentoData.four_thirtyTwoDollars.data));
		assertTrue(inclTotalSummary.equals(MagentoData.oneHundredSeventySix_ThirtyTwo_Dollars.data));
		assertTrue(exclTotalSummary.equals(MagentoData.oneHundredSeventyTwoDollars.data));
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

		assertTrue(inclTax.equals(MagentoData.oneHundredSeventySix_ThirtyTwo.data));
		assertTrue(exclTax.equals(MagentoData.oneHundredSeventyTwo.data));
		assertTrue(salesUse.equals(MagentoData.four_thirtyTwo.data));
		assertTrue(inclTaxTotal.equals(MagentoData.oneHundredNinetyTwo_TwentyTwo.data));
		assertTrue(exclTaxTotal.equals(MagentoData.oneHundredEightySeven.data));
	}

	/**
	 * test whether the correct tax is displayed in the Order Number page Items Ordered
	 */
	@Test()
	public void TaxOnStorefrontOrderReviewPageTest( )
	{
		String correctExclSubtotalTax = "172.0";
		String correctInclSubtotalTax = "176.32";
		String correctExclTotalTax = "187.0";
		String correctSalesUseTax = "$4.32";
		String correctTotalTax = "$5.22";
		String correctInclTotalTax = "192.22";

		signInToStorefront();
		M2StorefrontOrderNumberPage orderNumberPage = navigateToOrderNumberPage();

		double subtotalExclTax = orderNumberPage.getSubtotalExclTaxWholeOrder();
		String subtotalExclString = Double.toString(subtotalExclTax);

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

		assertTrue(subtotalExclString.equals(correctExclSubtotalTax));
		assertTrue(subtotalInclString.equals(correctInclSubtotalTax));
		assertTrue(exclTaxTotal.equals(correctExclTotalTax));
		assertTrue(salesUseTax.equals(correctSalesUseTax));
		assertTrue(taxTotal.equals(correctTotalTax));
		assertTrue(inclTaxTotal.equals(correctInclTotalTax));
	}

	protected M2StorefrontHomePage openStorefrontHomepage( )
	{
		driver.get(homepageURL);

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
		driver.get(hoodieURL);

		M2StorefrontProductDetailsPage detailsPage = new M2StorefrontProductDetailsPage(driver);

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
		shoppingCartPage.openTaxBlind();

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

			M2StorefrontShippingInfoPage shippingInfoPage = shoppingCartPage.clickProceedToCheckout();
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
	protected M2StorefrontOrderNumberPage navigateToOrderNumberPage( )
	{
		M2StorefrontPaymentMethodPage paymentMethodPage = navigateToPaymentMethodPage();

		paymentMethodPage.selectPaymentMethod();

		M2StorefrontThankYouPage thankYouPage = paymentMethodPage.clickPlaceOrderButton();

		M2StorefrontOrderNumberPage numberPage = thankYouPage.clickOrderNumber();

		return numberPage;
	}
}
