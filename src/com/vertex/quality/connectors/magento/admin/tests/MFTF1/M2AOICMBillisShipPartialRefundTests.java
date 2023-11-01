package com.vertex.quality.connectors.magento.admin.tests.MFTF1;

import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import java.text.DecimalFormat;

import static org.testng.Assert.assertTrue;

/**
 * tests inputting a bundled product order, of which billing and shipping address are different
 * and a partial refund is done on the order
 *
 * @author alewis
 */
public class M2AOICMBillisShipPartialRefundTests extends MagentoAdminBaseTest
{
	protected String url = MagentoData.ADMIN_SIGN_ON_URL.data;
	protected String username = MagentoData.ADMIN_USERNAME.data;
	protected String password = MagentoData.ADMIN_PASSWORD.data;
	protected String salesNavPanelHeaderText = "Sales";
	protected String orderTitleTest = "Orders / Operations / Sales / Magento Admin";
	protected String vertexSettingsHead = "Vertex Settings";
	protected String customerOrdersTitleText = "New Order / Orders / Operations / Sales / Magento Admin";
	protected String SKU = "24-WG080";
	protected String customerID = "Jacinda Ardern";

	String correctTaxRate = MagentoData.PA_TAX_RATE.data;
	String correctTaxNumber = MagentoData.PA_TAX_NUMBER.data;

	/**
	 * Checks Subtotal of tax in Items to Refund section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkSubtotalTaxInMemoPageRefundItemsSectionTest( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();

		String exclPrice = creditMemoPage.getSubtotalExclTaxFirstInGroup();
		String inclPrice = creditMemoPage.getSubtotalInclTaxFirstInGroup();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(correctTaxRate.equals(differenceString));
	}

	/**
	 * checks for correct tax amount in Items to Refund section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkTaxAmountInMemoPageRefundItemsSectionTest( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();

		String taxAmount = creditMemoPage.getTaxAmountFirstInGroup();
		String subtotalPrice = creditMemoPage.getSubtotalExclTaxFirstInGroup();

		String parseTaxAmount = taxAmount.substring(1);
		double doubleTaxAmount = Double.parseDouble(parseTaxAmount);

		String parseSubtotalPrice = subtotalPrice.substring(1);
		double doubleSubtotalPrice = Double.parseDouble(parseSubtotalPrice);

		double difference = doubleTaxAmount / doubleSubtotalPrice;
		String differenceString = Double.toString(difference);

		assertTrue(correctTaxNumber.equals(differenceString));
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

		assertTrue(correctTaxRate.equals(differenceString));

	}

	/**
	 * test tax in Blind in Refund Totals section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkBlindTaxInMemoPageRefundTotalsSectionTest( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();

		String salesUsePrice = creditMemoPage.getSalesUseTax();
		String subtotalPrice = creditMemoPage.getRefundSubtotalExclTax();

		String parseSalesUsePrice = salesUsePrice.substring(1);
		double doubleSalesUsePrice = Double.parseDouble(parseSalesUsePrice);
		String parseSubtotalPrice = subtotalPrice.substring(1);
		double doubleSubtotalPrice = Double.parseDouble(parseSubtotalPrice);

		double difference = doubleSalesUsePrice / doubleSubtotalPrice;

		String differenceString = String.format("%.2f", difference);

		assertTrue(correctTaxNumber.equals(differenceString));
	}

	/**
	 * tests tax in Refund Totals section of New Memo page for PA state sales tax
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

		assertTrue(correctTaxRate.equals(differenceString));
	}

	/**
	 * test tax in Refund Totals section of Order Info page for PA state sales tax
	 * after the refund is put through
	 */
	@Test()
	public void checkTaxOnRefundPageTest( )
	{
		M2AdminOrderViewInfoPage infoPage = submitCreditMemo();

		String exclPrice = infoPage.getPriceTotalExclTax();
		String inclPrice = infoPage.getPriceTotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = String.format("%.2f", difference);

		assertTrue(correctTaxRate.equals(differenceString));
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
	 * tests whether navigation can reach the M2AdminOrderCustomerPage
	 *
	 * @return the Order Customers Page
	 */
	protected M2AdminCreateNewOrderPage navigateToOrderCustomerPage( )
	{
		M2AdminOrdersPage ordersPage = navigateToOrders();
		M2AdminOrderCustomerPage orderCustomerPage = ordersPage.clickNewOrderButton();
		String pageTitle = orderCustomerPage.getPageTitle();
		assertTrue(pageTitle.equals(customerOrdersTitleText));
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
		newOrderPage.configurePartialRefundOrder();
		newOrderPage.clickAddProductsToOrderButton();

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
		newOrderPage.addShippingMethod(2);
		M2AdminOrderViewInfoPage infoPage = newOrderPage.clickSubmitOrderButton();

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
		String one = "1";

		M2AdminNewInvoicePage newInvoicePage = navigateToNewInvoicePage();
		newInvoicePage.clickSubmitInvoiceButton();
		M2AdminCreditMemoPage creditMemoPage = newInvoicePage.clickCreditMemoButton();
		creditMemoPage.clicksTaxBlind();
		creditMemoPage.changeRefundQTY(one);
		creditMemoPage.clicksTaxBlind();

		return creditMemoPage;
	}

	protected M2AdminOrderViewInfoPage submitCreditMemo( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();
		M2AdminOrderViewInfoPage orderViewInfoPage = creditMemoPage.clickRefundOfflineButton();

		return orderViewInfoPage;
	}
}