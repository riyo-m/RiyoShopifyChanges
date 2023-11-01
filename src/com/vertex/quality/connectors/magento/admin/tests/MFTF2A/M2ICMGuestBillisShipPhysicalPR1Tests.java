package com.vertex.quality.connectors.magento.admin.tests.MFTF2A;

import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test cases for an order where an invoice and Credit Memo is made for the entire order
 * Writing to Vertex tax journal when Magento Order Status = Processing,
 *
 * @author alewis
 */
public class M2ICMGuestBillisShipPhysicalPR1Tests extends MagentoAdminBaseTest
{

	String wcZipCode = "19380";
	String totalRefundedString = "$53.00";
	protected String homepageURL = MagentoData.STOREFRONT_SIGN_ON_URL.data;

	protected String invoiceCreatedString = "The invoice has been created.";
	protected String vertexInvoiceSentString = "The Vertex invoice has been sent.";
	protected String vertexInvoiceRefunded = "The Vertex invoice has been refunded.";
	protected String magentoCredMemoCreated = "You created the credit memo.";

	/**
	 * Test to see that the "Vertex Invoice Sent" message is displayed on the 'invoice' page,
	 * after an invoice is submitted
	 */
	@Test()
	public void correctMessageAfterInvoiceTest( )
	{
		M2AdminNewInvoicePage invoicePage = submitInvoice();
		boolean correctMagentoString = invoicePage.checkMessage(invoiceCreatedString);

		M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();
		configVertexSetting.resetConfigSettingToDefault();
		configVertexSetting.clickIntegrationSettingsTab();

		assertTrue(correctMagentoString == true);
		assertFalse(configVertexSetting.verifyWhenOrderStatusField());
	}

	/**
	 * Checks Subtotal of tax in Items to Refund section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkSubtotalTaxInMemoPageRefundItemsSectionTest( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToCreditMemoPageForExistOrder();

		String exclPrice = creditMemoPage.getSubtotalExclTax();
		String inclPrice = creditMemoPage.getSubtotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();
		configVertexSetting.resetConfigSettingToDefault();
		configVertexSetting.clickIntegrationSettingsTab();

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
		assertFalse(configVertexSetting.verifyWhenOrderStatusField());
	}

	/**
	 * checks for correct tax amount in Items to Refund section of New Memo page for PA state sales
	 * tax
	 */
	@Test()
	public void checkTaxAmountInMemoPageRefundItemsSectionTest( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToCreditMemoPageForExistOrder();

		String taxAmount = creditMemoPage.getTaxAmount();
		String subtotalPrice = creditMemoPage.getSubtotalExclTax();

		String parseTaxAmount = taxAmount.substring(1);
		double doubleTaxAmount = Double.parseDouble(parseTaxAmount);

		String parseSubtotalPrice = subtotalPrice.substring(1);
		double doubleSubtotalPrice = Double.parseDouble(parseSubtotalPrice);

		double difference = doubleTaxAmount / doubleSubtotalPrice;
		String differenceString = String.format("%.2f", difference);;

		M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();
		configVertexSetting.resetConfigSettingToDefault();
		configVertexSetting.clickIntegrationSettingsTab();

		assertTrue(differenceString.equals(MagentoData.PA_TAX_NUMBER.data));
		assertFalse(configVertexSetting.verifyWhenOrderStatusField());
	}

	/**
	 * test subtotal tax in Refund Totals section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkSubtotalTaxInMemoPageRefundTotalsSectionTest( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToCreditMemoPageForExistOrder();

		String exclPrice = creditMemoPage.getRefundSubtotalExclTax();
		String inclPrice = creditMemoPage.getRefundSubtotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();
		configVertexSetting.resetConfigSettingToDefault();
		configVertexSetting.clickIntegrationSettingsTab();

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
		assertFalse(configVertexSetting.verifyWhenOrderStatusField());
	}

	/**
	 * test tax in Blind in Refund Totals section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkBlindTaxInMemoPageRefundTotalsSectionTest( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToCreditMemoPageForExistOrder();

		String salesUsePrice = creditMemoPage.getSalesUseTax();
		String subtotalPrice = creditMemoPage.getSubtotalExclTax();

		String parseSalesUsePrice = salesUsePrice.substring(1);
		double doubleSalesUsePrice = Double.parseDouble(parseSalesUsePrice);
		String parseSubtotalPrice = subtotalPrice.substring(1);
		double doubleSubtotalPrice = Double.parseDouble(parseSubtotalPrice);

		double difference = doubleSalesUsePrice / doubleSubtotalPrice;
		String slicedDifferenceString = String.format("%.2f", difference);

		M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();
		configVertexSetting.resetConfigSettingToDefault();
		configVertexSetting.clickIntegrationSettingsTab();

		assertTrue(slicedDifferenceString.equals(MagentoData.PA_TAX_NUMBER.data));
		assertFalse(configVertexSetting.verifyWhenOrderStatusField());
	}

	/**
	 * test tax in Refund Totals section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkTaxInMemoPageRefundTotalsSectionTest( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToCreditMemoPageForExistOrder();

		String exclPrice = creditMemoPage.getRefundTotalExclTax();
		String inclPrice = creditMemoPage.getRefundTotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String slicedDifferenceString = String.format("%.2f", difference);

		M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();
		configVertexSetting.resetConfigSettingToDefault();
		configVertexSetting.clickIntegrationSettingsTab();

		assertTrue(slicedDifferenceString.equals(MagentoData.PA_TAX_RATE.data));
		assertFalse(configVertexSetting.verifyWhenOrderStatusField());
	}

	/**
	 * test that checks that the right refund amount is issued and the correct message is displayed
	 */
	@Test()
	public void vertexInvoiceSentAfterCreditMemoTest( )
	{
		M2AdminOrderViewInfoPage infoPage = submitCreditMemo();

		boolean correctString = infoPage.checkMessage(vertexInvoiceRefunded);
		boolean magentoCorrectString = infoPage.checkMessage(magentoCredMemoCreated);

		infoPage.clickTaxBlind();

		String totalRefunded = infoPage.getTotalRefundAmount();

		M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();
		configVertexSetting.resetConfigSettingToDefault();
		configVertexSetting.clickIntegrationSettingsTab();

		assertTrue(correctString == true);
		assertTrue(magentoCorrectString == true);
		assertEquals(totalRefundedString, totalRefunded);
		assertFalse(configVertexSetting.verifyWhenOrderStatusField());
	}

	protected M2StorefrontHomePage openStorefrontHomepage( )
	{
		driver.get(homepageURL);

		M2StorefrontHomePage homePage = new M2StorefrontHomePage(driver);

		homePage.clickSignInButton();

		return homePage;
	}

	/**
	 * Navigation on Magento Storefront to Product Details Page as Guest
	 *
	 * @return Magento Storefront Product Details Page
	 */
	protected M2StorefrontProductDetailsPage openProductPageGuest( )
	{
		M2StorefrontHomePage homePage = openStorefrontHomepage();

		M2StorefrontGearPage gearPage = homePage.navPanel.clickGearButton();

		M2StorefrontBagsPage bagsPage = gearPage.clickBagsButton();

		M2StorefrontProductDetailsPage productPage = bagsPage.clickProductButton();

		productPage.clickAddToCartButton();

		productPage.clickCartButton();

		return productPage;
	}

	/**
	 * Navigation on Magento Storefront to Shopping Cart Page as guest
	 *
	 * @return Magento Storefront Shopping Cart Page
	 */
	protected M2StorefrontShoppingCartPage openShoppingCartPageGuest( )
	{
		M2StorefrontProductDetailsPage productPage = openProductPageGuest();

		M2StorefrontShoppingCartPage shoppingCartPage = productPage.clickViewAndEditButton();

		shoppingCartPage.clickEstimateShippingTax();

		shoppingCartPage.selectState(PA_Number);

		shoppingCartPage.enterZipCode(wcZipCode);

		shoppingCartPage.clickShippingRateSelector();

		shoppingCartPage.openTaxBlind();

		return shoppingCartPage;
	}

	/**
	 * Navigation to the Payment Method Page
	 *
	 * @return the Storefront Payment Method Page
	 */
	protected M2StorefrontPaymentMethodPage openPaymentMethodPageGuest( )
	{
		String tullyFirstName = "Tully";
		String tullyLastName = "Moore";
		String tullyStreetAddress = "233 West Gay Street";
		String tullyCity = "West Chester";

		M2StorefrontShoppingCartPage shoppingCartPage = openShoppingCartPageGuest();

		M2StorefrontShippingInfoPage shippingInfoPage = shoppingCartPage.clickProceedToCheckout();

		shippingInfoPage.enterEmail(email);

		shippingInfoPage.enterFirstName(tullyFirstName);

		shippingInfoPage.enterLastName(tullyLastName);

		shippingInfoPage.enterStreetAddress(tullyStreetAddress);

		shippingInfoPage.enterCity(tullyCity);

		shippingInfoPage.enterPhoneNumber(phoneNumber);

		M2StorefrontPaymentMethodPage paymentMethod = shippingInfoPage.clickNextButton();

		if (shippingInfoPage.verifyUpdateAddressButton()){
			shippingInfoPage.clickUpdateAddress();
			shippingInfoPage.clickNextButton();
		}

		paymentMethod.openTaxBlind();

		return paymentMethod;
	}

	/**
	 * Puts in order for M2ICMGuestBillisShipPhysicalTests
	 *
	 * @return the thank you page
	 */
	protected M2StorefrontThankYouPage putInOrderForAdmin( )
	{
		M2StorefrontPaymentMethodPage paymentPage = openPaymentMethodPageGuest();

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
		String orderNumber = thankYouPage.getOrderNumberGuest();
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
	 * navigates to the credit memo page for a guest order
	 *
	 * @return View Info Page
	 */
	protected M2AdminCreditMemoPage navigateToCreditMemoPageForExistOrder( )
	{
		String orderNumber = getOrderFromStorefront();
		M2AdminOrdersPage ordersPage = updateConfigNavigateToOrders();
		M2AdminOrderViewInfoPage orderViewInfoPage = ordersPage.clickOrder(orderNumber);

		M2AdminNewInvoicePage invoicePage = orderViewInfoPage.clickInvoiceButton();

		invoicePage.clickSubmitInvoiceButton();
		M2AdminCreditMemoPage creditMemoPage = invoicePage.clickCreditMemoButton();
		creditMemoPage.clicksTaxBlind();

		return creditMemoPage;
	}

	/**
	 * Submits invoice
	 *
	 * @return invoice page
	 */
	protected M2AdminNewInvoicePage submitInvoice( )
	{
		String orderNumber = getOrderFromStorefront();
		M2AdminOrdersPage ordersPage = updateConfigNavigateToOrders();
		M2AdminOrderViewInfoPage orderViewInfoPage = ordersPage.clickOrder(orderNumber);

		M2AdminNewInvoicePage invoicePage = orderViewInfoPage.clickInvoiceButton();
		invoicePage.clickSubmitInvoiceButton();

		return invoicePage;
	}

	/**
	 * navigates to the view info page for a guest order
	 *
	 * @return View Info Page
	 */
	protected M2AdminOrderViewInfoPage submitCreditMemo( )
	{

		M2AdminCreditMemoPage creditMemoPage = navigateToCreditMemoPageForExistOrder();
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