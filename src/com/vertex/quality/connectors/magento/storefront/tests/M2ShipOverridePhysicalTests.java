package com.vertex.quality.connectors.magento.storefront.tests;

import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import com.vertex.quality.connectors.magento.storefront.tests.base.M2StorefrontBaseTest;
import org.testng.annotations.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static org.testng.Assert.assertTrue;

/**
 * tests of the correct tax is displayed at each stage of inputting an order in
 * Magento Storefront as an existing customer with different billing and shipping address
 * for a physical product, overrides to the correct shipping address
 *
 * @author alewis
 */
public class M2ShipOverridePhysicalTests extends M2StorefrontBaseTest
{
	protected String homepageURL = MagentoData.STOREFRONT_SIGN_ON_URL.data;
	protected String hoodieURL = MagentoData.STOREFRONT_HERO_HOODIE_URL.data;
	protected String tankURL = MagentoData.STOREFRONT_FITNESS_TANK_URL.data;
	protected String spcmURL = MagentoData.STOREFRONT_SPCM_URL.data;
	String paUsername = MagentoData.JOHN_WINSTON_USERNAME.data;
	String passwordString = MagentoData.STOREFRONT_PASSWORD.data;

	/**
	 * test whether the correct tax is displayed in the mini cart signed in
	 */
	@Test()
	public void checkTaxOnProductsPageSignedInTest( )
	{
		M2StorefrontProductDetailsPage productPage = putInVirtualAndDownloadableProducts();

		String includeTaxPrice = productPage.getIncludeTaxCartSubtotal();
		String currencySignIncl = includeTaxPrice.substring(1);
		double inclTaxDouble = Double.parseDouble(currencySignIncl);

		String excludeTaxPrice = productPage.getExcludeTaxCartSubtotal();
		String currencySignExcl = excludeTaxPrice.substring(1);
		double exclTaxDouble = Double.parseDouble(currencySignExcl);

		String inclTaxFirstItem = productPage.getIncludeTaxInMinicartFirstItem();
		String currencySigInclFirst = inclTaxFirstItem.substring(1);
		double inclTaxDoubleFirst = Double.parseDouble(currencySigInclFirst);

		String exclTaxFirstItem = productPage.getExcludeTaxInMinicartFirstItem();
		String currencySigExclFirst = exclTaxFirstItem.substring(1);
		double exclTaxDoubleFirst = Double.parseDouble(currencySigExclFirst);

		String inclTaxSecondItem = productPage.getIncludeTaxInMinicartSecondItem();
		String currencySigInclSecond = inclTaxSecondItem.substring(1);
		double inclTaxDoubleSecond = Double.parseDouble(currencySigInclSecond);

		String exclTaxSecondItem = productPage.getExcludeTaxInMinicartSecondItem();
		String currencySigExclSecond = exclTaxSecondItem.substring(1);
		double exclTaxDoubleSecond = Double.parseDouble(currencySigExclSecond);

		String exclTaxThirdItem = productPage.getExcludeTaxInMinicartThirdItem();
		String currencySigExclThird = exclTaxThirdItem.substring(1);
		double exclTaxDoubleThird = Double.parseDouble(currencySigExclThird);

		String inclTaxThirdItem = productPage.getIncludeTaxInMinicartThirdItem();
		String currencySigInclThird = inclTaxThirdItem.substring(1);
		double inclTaxDoubleThird = Double.parseDouble(currencySigInclThird);

		double difference = inclTaxDouble / exclTaxDouble;
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.CEILING);
		String differenceString = df.format(difference);

		double differenceFirstItem = inclTaxDoubleFirst / exclTaxDoubleFirst;
		String differenceStringFirst = String.format("%.1f", differenceFirstItem);

		double differenceSecondItem = inclTaxDoubleSecond / exclTaxDoubleSecond;
		String differenceStringSecond = String.format("%.2f", differenceSecondItem);

		double differenceThirdItem = inclTaxDoubleThird / exclTaxDoubleThird;
		String differenceStringThird = String.format("%.2f", differenceThirdItem);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(differenceString.equals(MagentoData.Exemption_Rate_SUBTOTAL.data));
		assertTrue(differenceStringFirst.equals(MagentoData.NO_TAX_RATE.data));
		assertTrue(differenceStringSecond.equals(MagentoData.PA_TAX_RATE.data));
		assertTrue(differenceStringThird.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests whether correct tax is applied to subtotal displayed on top of page
	 * when signed in for product bought in PA
	 */
	@Test()
	public void checkTaxOnShoppingCartPageSignedInTest( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = navigateToShoppingCartPageAndAddAddress();

		String includingTaxPrice = shoppingCartPage.getIncludingTaxSubtotalFirstItem();
		String currencySignIncl = includingTaxPrice.substring(1);
		double inclTaxDouble = Double.parseDouble(currencySignIncl);

		String excludingTaxPrice = shoppingCartPage.getExcludingTaxSubtotalFirstItem();
		String currencySignExcl = excludingTaxPrice.substring(1);
		double exclTaxDouble = Double.parseDouble(currencySignExcl);

		String includingTaxPriceSecond = shoppingCartPage.getIncludingTaxSubtotalSecondItem();
		String currencySignInclSecond = includingTaxPriceSecond.substring(1);
		double inclTaxDoubleSecond = Double.parseDouble(currencySignInclSecond);

		String excludingTaxPriceSecond = shoppingCartPage.getExcludingTaxSubtotalSecondItem();
		String currencySignExclSecond = excludingTaxPriceSecond.substring(1);
		double exclTaxDoubleSecond = Double.parseDouble(currencySignExclSecond);

		String includingTaxPriceThird = shoppingCartPage.getIncludingTaxSubtotalThirdItem();
		String currencySignInclThird = includingTaxPriceThird.substring(1);
		double inclTaxDoubleThird = Double.parseDouble(currencySignInclThird);

		String excludingTaxPriceThird = shoppingCartPage.getExcludingTaxSubtotalThirdItem();
		String currencySignExclThird = excludingTaxPriceThird.substring(1);
		double exclTaxDoubleThird = Double.parseDouble(currencySignExclThird);

		double difference = inclTaxDouble / exclTaxDouble;
		String differenceString = Double.toString(difference);

		double differenceSecond = inclTaxDoubleSecond / exclTaxDoubleSecond;
		String differenceStringSecond = String.format("%.2f", differenceSecond);

		double differenceThird = inclTaxDoubleThird / exclTaxDoubleThird;
		String differenceStringThird = Double.toString(differenceThird);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
		assertTrue(differenceStringSecond.equals(MagentoData.PA_TAX_RATE.data));
		assertTrue(differenceStringThird.equals(MagentoData.NO_TAX_RATE.data));
	}

	/**
	 * tests whether correct tax is applied to subtotal displayed in summary for
	 * product bought in PA
	 */
	@Test()
	public void checkSubtotalTaxInSummaryExisCustTest( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = navigateToShoppingCartPageAndAddAddress();

		String includingTax = shoppingCartPage.getSubtotalIncludingTaxInSummary();
		String excludingTax = shoppingCartPage.getSubtotalExcludingTaxInSummary();

		double includingTaxDouble = Double.parseDouble(includingTax.substring(1));
		double excludingTaxDouble = Double.parseDouble(excludingTax.substring(1));

		double difference = includingTaxDouble / excludingTaxDouble;
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.CEILING);
		String differenceString = df.format(difference);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(differenceString.equals(MagentoData.Exemption_Rate_SUBTOTAL.data));

	}

	/**
	 * tests whether correct tax is applied to shipping displayed in summary for
	 * product bought in PA
	 */
	@Test()
	public void checkTaxOnShippingInSummaryExisCustTest( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = navigateToShoppingCartPageAndAddAddress();

		String includingTax = shoppingCartPage.getShippingInclTaxInSummary();
		String excludingTax = shoppingCartPage.getShippingExclTaxInSummary();

		double includingTaxDouble = Double.parseDouble(includingTax.substring(1));
		double excludingTaxDouble = Double.parseDouble(excludingTax.substring(1));

		double difference = includingTaxDouble / excludingTaxDouble;
		String differenceString = Double.toString(difference);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests whether correct tax is applied to shipping displayed in summary for
	 * product bought in PA
	 */
	@Test()
	public void checkTaxInBlindInSummaryExisCustTest( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = navigateToShoppingCartPageAndAddAddress();

		String salesAndUseTax = shoppingCartPage.getSalesAndUseTax();
		String shippingTaxInBlindTax = shoppingCartPage.getShippingTaxInBlind();

		String subtotalExclTax = shoppingCartPage.getSubtotalExcludingTaxInSummary();
		String shippingExclTax = shoppingCartPage.getShippingExclTaxInSummary();

		double salesAndUseTaxDouble = Double.parseDouble(salesAndUseTax.substring(1));
		double shippingTaxInBlindTaxDouble = Double.parseDouble(shippingTaxInBlindTax.substring(1));
		double subtotalExclTaxDouble = Double.parseDouble(subtotalExclTax.substring(1));
		double shippingExclTaxDouble = Double.parseDouble(shippingExclTax.substring(1));

		double taxAppliedPreShipping = salesAndUseTaxDouble / subtotalExclTaxDouble;
		double taxAppliedPostShipping = shippingTaxInBlindTaxDouble / shippingExclTaxDouble;

		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.CEILING);
		String taxAppliedPreShippingString = df.format(taxAppliedPreShipping);

		String taxAppliedPostShippingString = String.format("%.2f", taxAppliedPostShipping);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(taxAppliedPreShippingString.equals(MagentoData.Exemption_SALESUSE_RATE.data));
		assertTrue(taxAppliedPostShippingString.equals(MagentoData.PA_TAX_NUMBER.data));
	}

	/**
	 * tests whether correct tax is applied to total displayed in summary for
	 * product bought in PA
	 */
	@Test()
	public void checkTotalTaxInSummaryOnShopCartPageExisCustTest( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = navigateToShoppingCartPageAndAddAddress();

		String includingTax = shoppingCartPage.getTotalIncludingTaxInSummary();
		String excludingTax = shoppingCartPage.getTotalExcludingTaxInSummary();

		double includingTaxDouble = Double.parseDouble(includingTax.substring(1));
		double excludingTaxDouble = Double.parseDouble(excludingTax.substring(1));

		double difference = includingTaxDouble / excludingTaxDouble;

		String slicedShippingString = String.format("%.3f", difference);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(slicedShippingString.equals(MagentoData.Exemption_RATE.data));
	}

	/**
	 * tests whether correct tax is applied to subtotal displayed in summary for
	 * product bought in PA
	 */
	@Test()
	public void checkSubtotalTaxInPaymentPageExistingCustTest( )
	{
		M2StorefrontPaymentMethodPage paymentMethodPage = navigateToPaymentMethodPage();

		double includingTax = paymentMethodPage.getSubtotalInclTax();
		double excludingTax = paymentMethodPage.getSubtotalExclTax();

		double difference = includingTax / excludingTax;

		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.CEILING);
		String slicedDifferenceString = df.format(difference);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(slicedDifferenceString.equals(MagentoData.Exemption_Rate_SUBTOTAL.data));
	}

	/**
	 * tests whether correct tax is applied to shipping displayed in summary for
	 * product bought in PA
	 */
	@Test()
	public void checkShippingTaxInPaymentPageExistingCustTest( )
	{
		M2StorefrontPaymentMethodPage paymentMethodPage = navigateToPaymentMethodPage();

		double includingTax = paymentMethodPage.getShippingInclTax();
		double excludingTax = paymentMethodPage.getShippingExclTax();

		double difference = includingTax / excludingTax;

		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		String slicedDifferenceString = df.format(difference);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(slicedDifferenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests whether correct tax is applied to sales and use and shipping in the blind in summary
	 * for
	 * product bought in PA
	 */
	@Test()
	public void checkBlindTaxInPaymentPageExistingCustTest( )
	{
		M2StorefrontPaymentMethodPage paymentMethodPage = navigateToPaymentMethodPage();

		double salesUseTax = paymentMethodPage.getSalesUseTax();
		double shippingTax = paymentMethodPage.getShippingTaxInBlind();
		double subtotalExclTax = paymentMethodPage.getSubtotalExclTax();
		double shippingExclTax = paymentMethodPage.getShippingExclTax();

		double taxAppliedToSalesUse = salesUseTax / subtotalExclTax;
		double taxAppliedToShipping = shippingTax / shippingExclTax;

		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.CEILING);
		String slicedSalesUseString = df.format(taxAppliedToSalesUse);

		String slicedShippingString = String.format("%.2f", taxAppliedToShipping);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(slicedSalesUseString.equals(MagentoData.Exemption_SALESUSE_RATE.data));
		assertTrue(slicedShippingString.equals(MagentoData.PA_TAX_NUMBER.data));
	}

	/**
	 * tests whether correct tax is applied to total displayed in summary for
	 * product bought in PA
	 */
	@Test()
	public void checkTotalTaxInPaymentPageExistingCustTest( )
	{
		M2StorefrontPaymentMethodPage paymentMethodPage = navigateToPaymentMethodPage();

		double includingTax = paymentMethodPage.getTotalInclTax();
		double excludingTax = paymentMethodPage.getTotalExclTax();

		double difference = includingTax / excludingTax;

		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.CEILING);
		String slicedDifferenceString = df.format(difference);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(slicedDifferenceString.equals(MagentoData.Exemption_RATE.data));
	}

	/**
	 * tests whether correct tax is applied to total displayed in Order Number Page
	 * for product bought in PA
	 */
	@Test()
	public void checkTaxOnOrderNumberPageTest( )
	{
		M2StorefrontOrderNumberPage orderNumberPage = navigateToOrderNumberPage();

		double grandTotalExcl = orderNumberPage.getGrandTotalExclTax();
		double grandTotalIncl = orderNumberPage.getGrandTotalInclTax();

		double shippingExclTax = orderNumberPage.getShippingExclTax();
		double shippingInclTax = orderNumberPage.getShippingInclTax();

		double exclSubtotal = orderNumberPage.getSubtotalExclTaxWholeOrder();
		double inclSubtotal = orderNumberPage.getSubtotalInclTaxWholeOrder();

		double difference = grandTotalIncl / grandTotalExcl;
		String slicedString = String.format("%.3f", difference);

		double differenceShipping = shippingInclTax / shippingExclTax;
		String slicedShippingString = String.format("%.2f", differenceShipping);

		double differenceSubtotal = inclSubtotal / exclSubtotal;
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.CEILING);
		String slicedDifferenceSubtotal = df.format(differenceSubtotal);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(slicedString.equals(MagentoData.Exemption_RATE.data));
		assertTrue(slicedShippingString.equals(MagentoData.PA_TAX_RATE.data));
		assertTrue(slicedDifferenceSubtotal.equals(MagentoData.Exemption_Rate_SUBTOTAL.data));
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

		loginPage.enterUsername(paUsername);
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
		shoppingCartPage.clickEstimateShippingTax();
		shoppingCartPage.selectState(MagentoData.PA_NUMBER.data);
		shoppingCartPage.enterZipCode(MagentoData.WESTCHESTER_ZIPCODE.data);
		shoppingCartPage.clickShippingRateSelector();
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

		shoppingCartPage.clickShippingRateSelector();

		M2StorefrontShippingInfoPage infoPage = shoppingCartPage.clickProceedToCheckout();

		infoPage.changeShippingAddress();

		infoPage.clickNextButton();

		M2StorefrontPaymentMethodPage paymentMethodPage = new M2StorefrontPaymentMethodPage(driver);

		paymentMethodPage.openTaxBlind();

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