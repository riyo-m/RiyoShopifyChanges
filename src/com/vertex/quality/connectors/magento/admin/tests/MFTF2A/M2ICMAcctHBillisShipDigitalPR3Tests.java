package com.vertex.quality.connectors.magento.admin.tests.MFTF2A;

import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test cases for an order where an invoice and credit memo is made for only part of order
 * Writing to Vertex tax journal when Magento Order Status = Processing,
 *
 * @author alewis
 */
public class M2ICMAcctHBillisShipDigitalPR3Tests extends MagentoAdminBaseTest
{
	protected String homepageURL = MagentoData.STOREFRONT_SIGN_ON_URL.data;
	protected String url = MagentoData.ADMIN_SIGN_ON_URL.data;
	protected String username = MagentoData.ADMIN_USERNAME.data;
	protected String password = MagentoData.ADMIN_PASSWORD.data;
	protected String virtualURL = MagentoData.STOREFRONT_VIRTUAL_PRODUCT_URL.data;
	protected String downloadableURL = MagentoData.STOREFRONT_DOWNLOADABLE_PRODUCT_URL.data;

	String wcUsername = MagentoData.ARDERN_STOREFRONT_USERNAME.data;
	String passwordString = MagentoData.STOREFRONT_PASSWORD.data;

	protected String configTitleText = "Configuration / Settings / Stores / Magento Admin";
	protected String salesNavPanelHeaderText = "Sales";
	protected String orderTitleTest = "Orders / Operations / Sales / Magento Admin";
	protected String vertexSettingsHead = "Vertex Settings";
	protected String customerOrdersTitleText = "New Order / Orders / Operations / Sales / Magento Admin";
	protected String SKU1 = "AL-01";
	protected String SKU2 = "240-LV07";
	protected String customerID = "Steve Buscemi";
	protected String refundProductString = "Aaron's Virtual";
	protected String qty = "0";

	String totalRefundedString = "$106.00";

	protected String vertexInvoiceRefunded = "The Vertex invoice has been refunded.";
	protected String magentoCredMemoCreated = "You created the credit memo.";

	/**
	 * Test to see that the right refund amount is credited for a partial refund
	 */
	@Test()
	public void vertexInvoiceSentAfterCreditMemoTest( )
	{
		M2AdminOrderViewInfoPage infoPage = navigateToCreditMemoPageForExistOrder();

		boolean correctString = infoPage.checkMessage(vertexInvoiceRefunded);
		boolean correctMagentoString = infoPage.checkMessage(magentoCredMemoCreated);

		infoPage.clickTaxBlind();

		String totalRefunded = infoPage.getTotalRefundAmount();

		M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();
		configVertexSetting.resetConfigSettingToDefault();
		configVertexSetting.clickIntegrationSettingsTab();

		assertTrue(correctString == true);
		assertTrue(correctMagentoString == correctMagentoString);
		assertEquals(totalRefundedString, totalRefunded);
		assertFalse(configVertexSetting.verifyWhenOrderStatusField());
	}

	protected M2StorefrontHomePage openStorefrontHomepage( )
	{
		driver.get(homepageURL);

		M2StorefrontHomePage homePage = new M2StorefrontHomePage(driver);

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

		detailsPage.selectSizeAndColorSmall();

		detailsPage.clickAddToCartButton();

		clickLogoButton();
		clearShoppingCart();

		driver.get(virtualURL);

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
			shoppingCartPage.popup.enterUserInfo(wcUsername, passwordString);
			shoppingCartPage.clickProceedToCheckout();
			if (shoppingCartPage.verifyPopUpLogin()){
				shoppingCartPage.closePopUpLogin();
				shoppingCartPage.clickProceedToCheckout();
			}
		}

		M2StorefrontPaymentMethodPage paymentMethodPage = new M2StorefrontPaymentMethodPage(driver);

		return paymentMethodPage;
	}

	/**
	 * Puts in order for M2ICMGuestBillisShipPhysicalTests
	 *
	 * @return the thank you page
	 */
	protected M2StorefrontThankYouPage putInOrderForAdmin( )
	{
		M2StorefrontPaymentMethodPage paymentPage = navigateToPaymentMethodPage();

		paymentPage.selectPaymentMethod();

		M2StorefrontThankYouPage thankYouPage = paymentPage.clickPlaceOrderButton();

		return thankYouPage;
	}

	/**
	 * gets the order number from the thank you page after putting in order from storefront
	 *
	 * @return a String of the order number
	 */
	protected String getOrderFromStorefront( )
	{
		M2StorefrontThankYouPage thankYouPage = putInOrderForAdmin();
		String orderNumber = thankYouPage.getOrderNumber();
		clickLogoButton();
		clearShoppingCart();

		return orderNumber;
	}

	/**
	 * tests whether navigation can reach the configPage
	 *
	 * @return the configuration page
	 */
	protected M2AdminConfigPage navigateToConfig( )
	{
		M2AdminHomepage homePage = signInToAdminHomepage();

		homePage.navPanel.clickStoresButton();

		M2AdminConfigPage configPage = homePage.navPanel.clickConfigButton();

		return configPage;
	}

	/**
	 * tests whether navigation can reach the M2AdminSalesTaxConfigPage
	 *
	 * @return Tax Settings Page
	 */
	protected M2AdminOrdersPage updateConfigNavigateToOrders()
	{
		M2AdminConfigPage configPage = navigateToConfig();

		configPage.clickSalesTab();
		M2AdminSalesTaxConfigPage taxSettingsPage = configPage.clickTaxTab();

		taxSettingsPage.clickIntegrationSettingsTab();
		taxSettingsPage.changeSendInvoiceToVertexField("Order Status Is Changed");
		taxSettingsPage.changeInvoiceWhenOrderStatusField("Processing");
		taxSettingsPage.saveConfig();

		taxSettingsPage.navPanel.clickSalesButton();

		M2AdminOrdersPage m2AdminOrdersPage = taxSettingsPage.navPanel.clickOrdersButton();

		return m2AdminOrdersPage;
	}

	/**
	 * navigates to the view info page for a customer with same billing and shipping address order
	 *
	 * @return View Info Page
	 */
	protected M2AdminOrderViewInfoPage navigateToCreditMemoPageForExistOrder( )
	{
		String orderNumber = getOrderFromStorefront();

		M2AdminOrdersPage ordersPage = updateConfigNavigateToOrders();
		M2AdminOrderViewInfoPage orderViewInfoPage = ordersPage.clickOrder(orderNumber);

		M2AdminNewInvoicePage invoicePage = orderViewInfoPage.clickInvoiceButton();

		invoicePage.doPartialRefund(refundProductString, "0");
		invoicePage.clickUpdateButton();

		invoicePage.openTaxBlind();

		invoicePage.clickSubmitInvoiceButton();
		M2AdminCreditMemoPage creditMemoPage = invoicePage.clickCreditMemoButton();
		creditMemoPage.clicksTaxBlind();

		M2AdminOrderViewInfoPage orderViewFinalInfoPage = creditMemoPage.clickRefundOfflineButton();

		return orderViewFinalInfoPage;
	}

	/**
	 * tests whether navigation can reach the M2AdminSalesTaxConfigPage
	 *
	 * @return Tax Settings Page
	 */
	protected M2AdminSalesTaxConfigPage navigateToSalesTaxConfig() {
		M2AdminHomepage homePage = new M2AdminHomepage(driver);

		homePage.navPanel.clickStoresButton();

		M2AdminConfigPage configPage = homePage.navPanel.clickConfigButton();

		configPage.clickSalesTab();

		M2AdminSalesTaxConfigPage taxSettingsPage = configPage.clickTaxTab();

		return taxSettingsPage;
	}
}