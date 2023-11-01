package com.vertex.quality.connectors.magento.admin.tests.MFTF2A;

import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test cases for an order where an invoice and Credit Memo is made for the entire order
 * Writing to Vertex tax journal when Magento Order Status = Complete,
 *
 * @author alewis
 */
public class M2AOICMBillisShipPhysicalCANGroupedCO1Tests extends MagentoAdminBaseTest
{
	protected String url = MagentoData.ADMIN_SIGN_ON_URL.data;
	protected String username = MagentoData.ADMIN_USERNAME.data;
	protected String password = MagentoData.ADMIN_PASSWORD.data;

	protected String configTitleText = "Configuration / Settings / Stores / Magento Admin";
	protected String salesNavPanelHeaderText = "Sales";
	protected String orderTitleTest = "Orders / Operations / Sales / Magento Admin";
	protected String vertexSettingsHead = "Vertex Settings";
	protected String customerOrdersTitleText = "New Order / Orders / Operations / Sales / Magento Admin";
	protected String SKU1 = "24-MB02";

	protected String customerID = "John-Luc Carre";

	String totalRefundedString = "$73.58";

	protected String vertexInvoiceRefunded = "The Vertex invoice has been refunded.";
	protected String magentoCredMemoCreated = "You created the credit memo.";
	protected String vertexInvoiceSent = "The Vertex invoice has been sent.";

	/**
	 * Test to see that the "Vertex Invoice Sent" message is displayed on the 'order view info' page,
	 * after an order is submitted on a pending order
	 */
	@Test()
	public void vertexInvoiceSentAfterCreditMemoTest( )
	{
		M2AdminOrderViewInfoPage infoPage = submitCreditMemo();
		boolean correctString = infoPage.checkMessage(vertexInvoiceRefunded);
		boolean correctMagentoString = infoPage.checkMessage(magentoCredMemoCreated);

		infoPage.clickTaxBlind();

		String totalRefunded = infoPage.getTotalRefundAmount();

		M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();
		configVertexSetting.resetConfigSettingToDefault();
		configVertexSetting.clickIntegrationSettingsTab();

		assertTrue(correctString == true);
		assertTrue(correctMagentoString == true);
		assertEquals(totalRefunded, totalRefundedString);
		assertFalse(configVertexSetting.verifyWhenOrderStatusField());
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
	 * tests whether navigation can reach the M2AdminSalesTaxConfigPage
	 *
	 * @return Tax Settings Page
	 */
	protected M2AdminSalesTaxConfigPage navigateToSalesTaxConfigChange( )
	{
		M2AdminConfigPage configPage = navigateToConfig();

		configPage.clickSalesTab();
		M2AdminSalesTaxConfigPage taxSettingsPage = configPage.clickTaxTab();

		taxSettingsPage.clickIntegrationSettingsTab();
		taxSettingsPage.changeSendInvoiceToVertexField("Order Status Is Changed");
		taxSettingsPage.changeInvoiceWhenOrderStatusField("Complete");
		taxSettingsPage.saveConfig();

		String taxSettingsPageTitle = taxSettingsPage.getPageHeader();
		assertTrue(vertexSettingsHead.equals(taxSettingsPageTitle));

		return taxSettingsPage;
	}

	/**
	 * tests whether navigation can reach the OrdersPage
	 *
	 * @return the Orders Page
	 */
	protected M2AdminOrdersPage navigateToOrders( )
	{
		M2AdminSalesTaxConfigPage salesTaxConfigPage = navigateToSalesTaxConfigChange();

		salesTaxConfigPage.navPanel.clickSalesButton();

		String NavPanelBannerText = salesTaxConfigPage.navPanel.getPanelTitle();

		assertTrue(salesNavPanelHeaderText.equals(NavPanelBannerText));

		M2AdminOrdersPage m2AdminOrdersPage = salesTaxConfigPage.navPanel.clickOrdersButton();

		String ordersPageTitle = m2AdminOrdersPage.getPageTitle();

		assertTrue(orderTitleTest.equals(ordersPageTitle));

		return m2AdminOrdersPage;
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
		newOrderPage.enterSKUNumber(SKU1);
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
		newOrderPage.addShippingMethod(1);
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
		M2AdminNewInvoicePage newInvoicePage = navigateToNewInvoicePage();
		newInvoicePage.clickSubmitInvoiceButton();

		M2AdminShipPage shipPage = newInvoicePage.navigateToShipPage();
		shipPage.submitShipment();

		M2AdminCreditMemoPage creditMemoPage = newInvoicePage.clickCreditMemoButton();

		creditMemoPage.clicksTaxBlind();

		return creditMemoPage;
	}

	/**
	 * submits the order
	 *
	 * @return the admin order view info page
	 */
	protected M2AdminOrderViewInfoPage submitCreditMemo( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();
		M2AdminOrderViewInfoPage orderViewInfoPage = creditMemoPage.clickRefundOfflineButton();

		return orderViewInfoPage;
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