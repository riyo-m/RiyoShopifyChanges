package com.vertex.quality.connectors.magento.admin.tests.MFTF1;

import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * tests tax on each stage of the order for a digital product
 * with the same billing and shipping addresses
 *
 * @author alewis
 */
public class M2AOICMBillisShipDigitalTests extends MagentoAdminBaseTest
{
	protected String url = MagentoData.ADMIN_SIGN_ON_URL.data;
	protected String username = MagentoData.ADMIN_USERNAME.data;
	protected String password = MagentoData.ADMIN_PASSWORD.data;
	protected String configTitleText = "Configuration / Settings / Stores / Magento Admin";
	protected String customerTitleText = "Customers / Customers / Magento Admin";
	protected String salesNavPanelHeaderText = "Sales";
	protected String orderTitleTest = "Orders / Operations / Sales / Magento Admin";
	protected String vertexSettingsHead = "Vertex Settings";
	protected String cacheManagementTitleText = "Cache Management / Tools / System / Magento Admin";
	protected String productsPageTitleText = "Products / Inventory / Catalog / Magento Admin";
	protected String customerOrdersTitleText = "New Order / Orders / Operations / Sales / Magento Admin";
	protected String SKU = "AL-01";
	protected String customerID = "John Winston";

	/**
	 * tests tax on top of page in Create New Order Page for PA State tax rate
	 */
	@Test()
	public void checkTaxInCreateNewOrderItemsOrderTest( )
	{
		M2AdminCreateNewOrderPage newOrderPage = addProductToSalesOrders();

		String excludingPrice = newOrderPage.getPriceSubtotalExclTax();
		String includingPrice = newOrderPage.getPriceSubtotalInclTax();

		String parseTaxValueExcl = excludingPrice.substring(1);
		double priceDoubleExcl = Double.parseDouble(parseTaxValueExcl);

		String parseTaxValueIncl = includingPrice.substring(1);
		double priceDoubleIncl = Double.parseDouble(parseTaxValueIncl);

		double total = priceDoubleIncl / priceDoubleExcl;
		String totalString = Double.toString(total);

		assertTrue(totalString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests tax on bottom right of page in Create New Order Page for PA State tax rate
	 */
	@Test()
	public void checkTaxAmountInCreateNewOrderOrderTotalsTest( )
	{
		M2AdminCreateNewOrderPage newOrderPage = addProductToSalesOrders();

		String excludingPrice = newOrderPage.getPriceTotalExclTax();
		String includingPrice = newOrderPage.getPriceTotalInclTax();

		String parseTaxValueExcl = excludingPrice.substring(1);
		double priceDoubleExcl = Double.parseDouble(parseTaxValueExcl);

		String parseTaxValueIncl = includingPrice.substring(1);
		double priceDoubleIncl = Double.parseDouble(parseTaxValueIncl);

		double total = priceDoubleIncl / priceDoubleExcl;
		String totalString = Double.toString(total);

		assertTrue(totalString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests Tax Amount is correct percent of Original Price in Items Ordered section of Order View Info page
	 * for PA state sales tax
	 */
	@Test()
	public void checkSubtotalTaxInOrderInfoPageItemsOrderedTest( )
	{
		M2AdminOrderViewInfoPage infoPage = navigateOrderViewInfoPage();

		String exclTaxPrice = infoPage.getSubtotalExclTax();
		String inclTaxPrice = infoPage.getSubtotalInclTax();

		String parseExclPrice = exclTaxPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclAmount = inclTaxPrice.substring(1);
		double doubleInclAmount = Double.parseDouble(parseInclAmount);

		double difference = doubleInclAmount / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests tax in Items Ordered section of Order View Info page for PA state sales tax
	 */
	@Test()
	public void checkTaxInOrderInfoPageItemsOrderedTest( )
	{
		M2AdminOrderViewInfoPage infoPage = navigateOrderViewInfoPage();

		String originalPrice = infoPage.getOriginalPrice();
		String taxAmount = infoPage.getTaxAmount();

		String parseOriginalPrice = originalPrice.substring(1);
		double doubleOriginalPrice = Double.parseDouble(parseOriginalPrice);

		String parseTaxAmount = taxAmount.substring(1);
		double doubleTaxAmount = Double.parseDouble(parseTaxAmount);

		double percentTaxPaid = doubleTaxAmount / doubleOriginalPrice;
		String percentTaxPaidString = Double.toString(percentTaxPaid);

		assertTrue(percentTaxPaidString.equals(MagentoData.PA_TAX_NUMBER.data));
	}

	/**
	 * tests the tax percent in Items Ordered section of Order View Info page for PA state sales tax is correct
	 */
	@Test()
	public void checkTaxPercentInOrderInfoPageItemsOrderedTest( )
	{
		M2AdminOrderViewInfoPage infoPage = navigateOrderViewInfoPage();

		String taxPercent = infoPage.getTaxPercent();

		assertTrue(taxPercent.equals(MagentoData.PA_TAX_PERCENT.data));
	}

	/**
	 * tests tax in Order Totals section of Order View Info page for PA state sales tax
	 */
	@Test()
	public void checkSubtotalTaxInOrderInfoPageOrderTotalsTest( )
	{
		M2AdminOrderViewInfoPage infoPage = navigateOrderViewInfoPage();

		String exclPrice = infoPage.getSubtotalExclTaxOrderTotals();
		String inclPrice = infoPage.getSubtotalInclTaxOrderTotals();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests taxes in tax blind in Order Totals section of Order View Info page for PA state sales tax
	 */
	@Test()
	public void checkBlindTaxesInOrderInfoPageOrderTotalsTest( )
	{
		M2AdminOrderViewInfoPage infoPage = navigateOrderViewInfoPage();

		String salesUsePrice = infoPage.getSalesUseTax();

		String exclSubtotal = infoPage.getSubtotalExclTaxOrderTotals();

		String parseSalesUsePrice = salesUsePrice.substring(1);
		double doubleSalesUsePrice = Double.parseDouble(parseSalesUsePrice);

		String parseSubtotalPrice = exclSubtotal.substring(1);
		double doubleSubtotalPrice = Double.parseDouble(parseSubtotalPrice);

		double correctSalesUsePercent = doubleSalesUsePrice / doubleSubtotalPrice;
		String differenceString = Double.toString(correctSalesUsePercent);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_NUMBER.data));
	}

	/**
	 * tests tax in Order Totals section of Order View Info page for PA state sales tax
	 */
	@Test()
	public void checkTaxInOrderInfoPageOrderTotalsTest( )
	{
		M2AdminOrderViewInfoPage infoPage = navigateOrderViewInfoPage();

		String exclPrice = infoPage.getPriceTotalExclTaxNoShopping();
		String inclPrice = infoPage.getPriceTotalInclTaxNoShopping();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests tax in Items to Invoice section of New Invoice page for PA state sales tax
	 */
	@Test()
	public void checkTaxInInvoicePageItemsToInvoiceTest( )
	{
		M2AdminNewInvoicePage invoicePage = navigateToNewInvoicePage();

		String exclPrice = invoicePage.getExclTaxPrice();
		String inclPrice = invoicePage.getInclTaxPrice();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests tax amount is correct percent of excluding tax price
	 * in Items to Invoice section of New Invoice page for PA state sales tax
	 */
	@Test()
	public void checkTaxAmountInItemsToInvoiceTest( )
	{
		M2AdminNewInvoicePage invoicePage = navigateToNewInvoicePage();

		String taxAmount = invoicePage.getTaxAmount();
		String exclPrice = invoicePage.getExclTaxPrice();

		String parseTaxAmount = taxAmount.substring(1);
		String exclPriceString = exclPrice.substring(1);

		double doubleTaxAmount = Double.parseDouble(parseTaxAmount);
		double doubleExclPrice = Double.parseDouble(exclPriceString);

		double difference = doubleTaxAmount / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_NUMBER.data));
	}

	/**
	 * tests Subtotal tax in Invoice Totals section of New Invoice page for PA state sales tax
	 */
	@Test()
	public void checkSubtotalTaxInInvoicePageInvoiceTotalsTest( )
	{
		M2AdminNewInvoicePage invoicePage = navigateToNewInvoicePage();

		String exclPrice = invoicePage.getInvoiceSubtotalExclTax();
		String inclPrice = invoicePage.getInvoiceSubtotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests tax in tax blind Invoice Totals section of New Invoice page for PA state sales tax
	 */
	@Test()
	public void checkTaxInInvoicePageInvoiceTotalsTest( )
	{
		M2AdminNewInvoicePage invoicePage = navigateToNewInvoicePage();

		String exclPrice = invoicePage.getInvoiceTotalExclTax();
		String inclPrice = invoicePage.getInvoiceTotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests tax in Invoice Totals section of New Invoice page for PA state sales tax
	 */
	@Test()
	public void checkTaxInBlindInvoicePageInvoiceTotalsTest( )
	{
		M2AdminNewInvoicePage invoicePage = navigateToNewInvoicePage();

		String salesUsePrice = invoicePage.getSalesUseTax();

		String subtotalExcl = invoicePage.getInvoiceSubtotalExclTax();

		String parseSalesUsePrice = salesUsePrice.substring(1);
		double doubleSalesUsePrice = Double.parseDouble(parseSalesUsePrice);
		String parseSubtotalPrice = subtotalExcl.substring(1);
		double doubleSubtotalPrice = Double.parseDouble(parseSubtotalPrice);

		double difference = doubleSalesUsePrice / doubleSubtotalPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_NUMBER.data));
	}

	/**
	 * Checks Subtotal of tax in Items to Refund section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkSubtotalTaxInMemoPageRefundItemsSectionTest( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();

		String exclPrice = creditMemoPage.getSubtotalExclTax();
		String inclPrice = creditMemoPage.getSubtotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * checks for correct tax amount in Items to Refund section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkTaxAmountInMemoPageRefundItemsSectionTest( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();

		String taxAmount = creditMemoPage.getTaxAmount();
		String subtotalPrice = creditMemoPage.getSubtotalExclTax();

		String parseTaxAmount = taxAmount.substring(1);
		double doubleTaxAmount = Double.parseDouble(parseTaxAmount);

		String parseSubtotalPrice = subtotalPrice.substring(1);
		double doubleSubtotalPrice = Double.parseDouble(parseSubtotalPrice);

		double difference = doubleTaxAmount / doubleSubtotalPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_NUMBER.data));
	}

	/**
	 * test subtotal tax in Refund Totals section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkSubtotalTaxInMemoPageRefundTotalsSectionTest( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();

		String exclPrice = creditMemoPage.getRefundSubtotalExclTax();
		String inclPrice = creditMemoPage.getRefundSubtotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * test tax in Blind in Refund Totals section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkBlindTaxInMemoPageRefundTotalsSectionTest( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();

		String salesUsePrice = creditMemoPage.getSalesUseTax();
		String subtotalPrice = creditMemoPage.getSubtotalExclTax();

		String parseSalesUsePrice = salesUsePrice.substring(1);
		double doubleSalesUsePrice = Double.parseDouble(parseSalesUsePrice);
		String parseSubtotalPrice = subtotalPrice.substring(1);
		double doubleSubtotalPrice = Double.parseDouble(parseSubtotalPrice);

		double difference = doubleSalesUsePrice / doubleSubtotalPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_NUMBER.data));
	}

	/**
	 * test tax in Refund Totals section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkTaxInMemoPageRefundTotalsSectionTest( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();

		String exclPrice = creditMemoPage.getRefundTotalExclTax();
		String inclPrice = creditMemoPage.getRefundTotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * loads and signs into this configuration site
	 *
	 * @return a representation of the page that loads immediately after
	 * successfully signing into this configuration site
	 */
	protected M2AdminHomepage signInToAdminHomepage( )
	{
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

		return homepage;
	}

	/**
	 * tests whether navigation can reach the configPage
	 *
	 * @return the configuration page
	 */
	protected M2AdminConfigPage navigateToConfig( )
	{
		M2AdminHomepage homePage = signInToAdminHomepage();

		assertTrue(homePage.navPanel.isStoresButtonVisible());

		homePage.navPanel.clickStoresButton();

		M2AdminConfigPage configPage = homePage.navPanel.clickConfigButton();

		String configPageTitle = configPage.getPageTitle();

		assertTrue(configTitleText.equals(configPageTitle));

		return configPage;
	}

	/**
	 * tests whether navigation can reach the OrdersPage
	 *
	 * @return the Orders Page
	 */
	protected M2AdminOrdersPage navigateToOrders( )
	{
		M2AdminHomepage homePage = signInToAdminHomepage();

		assertTrue(homePage.navPanel.isSalesButtonVisible());

		homePage.navPanel.clickSalesButton();

		String NavPanelBannerText = homePage.navPanel.getPanelTitle();

		assertTrue(salesNavPanelHeaderText.equals(NavPanelBannerText));

		M2AdminOrdersPage m2AdminOrdersPage = homePage.navPanel.clickOrdersButton();

		String ordersPageTitle = m2AdminOrdersPage.getPageTitle();

		assertTrue(orderTitleTest.equals(ordersPageTitle));

		return m2AdminOrdersPage;
	}

	/**
	 * tests whether navigation can reach the M2AdminSalesTaxConfigPage
	 *
	 * @return Tax Settings Page
	 */
	protected M2AdminSalesTaxConfigPage navigateToSalesTaxConfig( )
	{
		M2AdminConfigPage configPage = navigateToConfig();

		configPage.clickSalesTab();
		M2AdminSalesTaxConfigPage taxSettingsPage = configPage.clickTaxTab();

		String taxSettingsPageTitle = taxSettingsPage.getPageHeader();
		assertTrue(vertexSettingsHead.equals(taxSettingsPageTitle));

		return taxSettingsPage;
	}

	/**
	 * tests whether navigation can reach the M2AdminCustomersPage
	 *
	 * @return the Customers Page
	 */
	protected M2AdminCustomersPage navigateToAllCustomers( )
	{
		M2AdminHomepage homePage = signInToAdminHomepage();

		assertTrue(homePage.navPanel.isCustomersButtonVisible());

		homePage.navPanel.clickCustomersButton();

		M2AdminCustomersPage customersPage = homePage.navPanel.clickAllCustomersButton();

		return customersPage;
	}

	/**
	 * tests whether navigation can reach the AdminCacheManagementPage
	 *
	 * @return the Cache Management Page
	 */
	protected M2AdminCacheMgmt navigateToCacheManagement( )
	{
		M2AdminHomepage homepage = signInToAdminHomepage();

		assertTrue(homepage.navPanel.isCustomersButtonVisible());

		homepage.navPanel.clickSystemButton();

		M2AdminCacheMgmt cacheManagementPage = homepage.navPanel.clickCacheManagementButton();

		return cacheManagementPage;
	}

	/**
	 * tests whether navigation can reach the M2AdminProductsPage
	 *
	 * @return the Products Page
	 */
	protected M2AdminProductsPage navigateToProducts( )
	{
		M2AdminHomepage homepage = signInToAdminHomepage();

		assertTrue(homepage.navPanel.isCustomersButtonVisible());

		homepage.navPanel.clickCatalogButton();

		M2AdminProductsPage productsPage = homepage.navPanel.clickProductsButton();

		return productsPage;
	}

	/**
	 * tests whether navigation can reach the M2AdminOrderCustomerPage
	 *
	 * @return the Order Customers Page
	 */
	protected M2AdminCreateNewOrderPage navigateToOrderCustomerPage( )
	{
		M2AdminOrdersPage ordersPage = navigateToOrders();
		M2AdminOrderCustomerPage orderCustomerPage = ordersPage.clickNewOrderButton();
		M2AdminCreateNewOrderPage newOrderPage = orderCustomerPage.clickCustomer(customerID);
		return newOrderPage;
	}

	/**
	 * tests whether navigation can reach the M2AdminOrderCustomerPage
	 *
	 * @return the Order Customers Page
	 */
	protected M2AdminCreateNewOrderPage addProductToSalesOrders( )
	{
		M2AdminCreateNewOrderPage newOrderPage = navigateToOrderCustomerPage();
		newOrderPage.clickAddSKUButton();
		newOrderPage.enterSKUNumber(SKU);
		newOrderPage.enterQty();
		newOrderPage.clickAddToOrderButton();

		return newOrderPage;
	}

	/**
	 * Navigation to Order Info after order is placed
	 *
	 * @return Order View Info Page
	 */
	protected M2AdminOrderViewInfoPage navigateOrderViewInfoPage( )
	{
		M2AdminCreateNewOrderPage newOrderPage = addProductToSalesOrders();

		newOrderPage.selectCheckAsPaymentMethod();

		M2AdminOrderViewInfoPage infoPage = newOrderPage.clickSubmitOrderButtonNoMask();

		infoPage.clickTaxBlind();

		return infoPage;
	}

	/**
	 * Navigation to New Invoice page after placing an order
	 *
	 * @return Order View Info page
	 */
	protected M2AdminNewInvoicePage navigateToNewInvoicePage( )
	{
		M2AdminOrderViewInfoPage newOrderPage = navigateOrderViewInfoPage();
		M2AdminNewInvoicePage invoicePage = newOrderPage.clickInvoiceButton();

		invoicePage.openTaxBlind();

		return invoicePage;
	}

	/**
	 * Navigation to Credit Memo page after inputting an invoice
	 *
	 * @return Credit Order page
	 */
	protected M2AdminCreditMemoPage navigateToNewMemoPage( )
	{
		M2AdminNewInvoicePage newInvoicePage = navigateToNewInvoicePage();
		newInvoicePage.clickSubmitInvoiceButton();
		M2AdminCreditMemoPage creditMemoPage = newInvoicePage.clickCreditMemoButton();
		creditMemoPage.clicksTaxBlind();

		return creditMemoPage;
	}

	/**
	 * Submits a credit memo
	 *
	 * @return the Order View Info Page
	 */
	protected M2AdminOrderViewInfoPage submitCreditMemo( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();
		M2AdminOrderViewInfoPage orderViewInfoPage = creditMemoPage.clickRefundOfflineButton();

		return orderViewInfoPage;
	}

	/**
	 * Navigates to Marketing page
	 */
	protected void navigateToMarketingPage( )
	{
		M2AdminHomepage homepage = signInToAdminHomepage();
		homepage.navPanel.clickMarketingButton();
	}

	/**
	 * Navigates to Partners page
	 *
	 * @return the Partners Page
	 */
	protected M2AdminPartnersPage navigateToPartnersPage( )
	{
		M2AdminHomepage homepage = signInToAdminHomepage();
		M2AdminPartnersPage partnersPage = homepage.navPanel.clickPartnersButton();

		return partnersPage;
	}
}