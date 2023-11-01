package com.vertex.quality.connectors.magento.storefront.tests;

import com.vertex.quality.connectors.magento.admin.pages.M2AdminConfigPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminHomepage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminSalesTaxConfigPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminSignOnPage;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import com.vertex.quality.connectors.magento.storefront.tests.base.M2StorefrontBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * This Test Changes the value to ShippingTo Spain in admin config page and test the correct tax is displayed
 * at each stage of inputting an order in Magento Storefront as a customer same billing and shipping for a
 * Straps bundle products to an address in Italy
 *
 * @author alewis
 */
public class M2BillisShipPhysicalITTests extends M2StorefrontBaseTest
{
	protected String url = MagentoData.ADMIN_SIGN_ON_URL.data;
	protected String username = MagentoData.ADMIN_USERNAME.data;
	protected String password = MagentoData.ADMIN_PASSWORD.data;
	protected String loggedInHeaderText = "Dashboard";
	protected String configTitleText = "Configuration / Settings / Stores / Magento Admin";
	protected String homepageURL = MagentoData.STOREFRONT_SIGN_ON_URL.data;
	protected String strapsBundleURL = MagentoData.STOREFRONT_STRAPS_BUNDLE.data;
	String cateUsername = MagentoData.CATE_DEMEDICI.data;
	String passwordString = MagentoData.STOREFRONT_PASSWORD.data;

	/**
	 * test whether the correct tax is displayed in the Order Number page Items Ordered
	 */
	@Test()
	public void TaxOnStorefrontOrderReviewPageTest( )
	{
		String correctExclSubtotalTax = "52.0";
		String correctInclSubtotalTax = "63.44";
		String correctSalesUseTax = "$11.44";
		String correctTotalTaxAmount = "$14.74";
		String correctExclTotalTax = "67.0";
		String correctInclTotalTax = "81.74";

		//Log In to Magento Application and Navigate to the Configuration Page
		M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();

		//Changing the value of Use Vertex For Orders ShippingTo Field To Italy
		configVertexSetting.changeUseVertexForOrdersShippingToField("Italy");

		//Click on save configuration
		configVertexSetting.saveConfig();

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
		assertTrue(taxTotal.equals(correctTotalTaxAmount));
		assertTrue(inclTaxTotal.equals(correctInclTotalTax));
	}

	/**
	 * Opens Storefront Homepage
	 *
	 * @return Magento Storefront Homepage
	 */
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

		loginPage.enterUsername(cateUsername);
		loginPage.enterPassword(passwordString);
		M2StorefrontHomePage signedInHomepage = loginPage.clickSignInButton();

		return signedInHomepage;
	}

	/**
	 * adds a virtual and downloadable product to the shopping cart
	 *
	 * @return the product details page
	 */
	protected M2StorefrontProductDetailsPage putInBundleProducts( )
	{
		driver.get(strapsBundleURL);

		M2StorefrontProductDetailsPage detailsPage = new M2StorefrontProductDetailsPage(driver);

		detailsPage.addStrapsToCart();

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
		M2StorefrontProductDetailsPage productDetailsPage = putInBundleProducts();

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
		//shoppingCartPage.openTaxBlind();

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

	/**
	 * tests whether navigation can reach the M2AdminSalesTaxConfigPage
	 *
	 * @return Tax Settings Page
	 */
	protected M2AdminSalesTaxConfigPage navigateToSalesTaxConfig() {
		M2AdminConfigPage configPage = navigateToConfig();

		configPage.clickSalesTab();

		M2AdminSalesTaxConfigPage taxSettingsPage = configPage.clickTaxTab();

		return taxSettingsPage;
	}

	/**
	 * tests whether navigation can reach the configPage
	 *
	 * @return the configuration page
	 */
	protected M2AdminConfigPage navigateToConfig() {
		M2AdminHomepage homePage = signInToAdminHomepage();

		assertTrue(homePage.navPanel.isStoresButtonVisible());

		homePage.navPanel.clickStoresButton();

		M2AdminConfigPage configPage = homePage.navPanel.clickConfigButton();

		String configPageTitle = configPage.getPageTitle();

		assertEquals(configTitleText, configPageTitle);

		return configPage;
	}

	/**
	 * loads and signs into this configuration site
	 *
	 * @return a representation of the page that loads immediately after
	 * successfully signing into this configuration site
	 */
	protected M2AdminHomepage signInToAdminHomepage() {
		driver.get(url);

		M2AdminSignOnPage signOnPage = new M2AdminSignOnPage(driver);

		boolean isUsernameFieldPresent = signOnPage.isUsernameFieldPresent();
		assertTrue(isUsernameFieldPresent);

		signOnPage.enterUsername(username);

		boolean isPasswordFieldPresent = signOnPage.isPasswordFieldPresent();
		assertTrue(isPasswordFieldPresent);

		signOnPage.enterPassword(password);

		boolean isLoginButtonPresent = signOnPage.isLoginButtonPresent();
		assertTrue(isLoginButtonPresent);

		M2AdminHomepage homepage = signOnPage.login();

		String homePageBannerText = homepage.checkJustLoggedIn();

		assertEquals(loggedInHeaderText, homePageBannerText);

		return homepage;
	}
}
