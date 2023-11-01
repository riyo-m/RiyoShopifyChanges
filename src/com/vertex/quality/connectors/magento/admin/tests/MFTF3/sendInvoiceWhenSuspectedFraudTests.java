package com.vertex.quality.connectors.magento.admin.tests.MFTF3;

import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Test case for creating an order from the admin panel and setting the invoice
 * to send to Vertex when the order status is changed to "Suspected Fraud"
 * Bundle-1601
 *
 * @author alewis
 */
public class sendInvoiceWhenSuspectedFraudTests extends MagentoAdminBaseTest
{

	protected String customerName = "Cersei Lannister";
	protected String orderStatusSelect = "fraud";

	/**
	 * Tests changing when to send the invoice to Vertex to "Suspected Fraud",
	 * creating an order from the admin panel, and ensuring that invoice
	 * is sent after it is marked as Suspected Fraud
	 */
	@Test()
	public void suspectedFraudInvoiceTest( )
	{
		String createdOrderMessage = "You created the order.";
		String vertexInvoiceSentMessage = "The invoice has been created.";

		M2AdminOrderViewInfoPage infoPage = inputOrderInfo();
		boolean messageMatch = infoPage.checkMessage(createdOrderMessage);
		assertTrue(messageMatch);

		M2AdminNewInvoicePage invoicePage = infoPage.clickInvoiceButton();
		invoicePage.clickSubmitInvoiceButton();
		infoPage.selectOrderStatus(orderStatusSelect);
		// this message actually appears with the created order message, so the assertTrue fails
		messageMatch = infoPage.checkMessage(vertexInvoiceSentMessage);
		assertTrue(messageMatch);
		infoPage.clickSubmitCommentButton();

		M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();
		configVertexSetting.resetConfigSettingToDefault();
		configVertexSetting.clickIntegrationSettingsTab();
		assertFalse(configVertexSetting.verifyWhenOrderStatusField());

		// Need to download transaction report from cloud and verify it's correct, but can't find how
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

		signOnPage.enterUsername(username);

		signOnPage.enterPassword(password);

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

		homePage.navPanel.clickStoresButton();

		M2AdminConfigPage configPage = homePage.navPanel.clickConfigButton();

		return configPage;
	}

	/**
	 * Tests navigation to the Vertex settings
	 *
	 * @return the Vertex settings page
	 */
	protected M2AdminSalesTaxConfigPage navigateToVertexSettings( )
	{
		M2AdminConfigPage configPage = navigateToConfig();
		configPage.clickSalesTab();

		M2AdminSalesTaxConfigPage vertexSettings = configPage.clickTaxTab();
		return vertexSettings;
	}

	/**
	 * Tests configuration of the Vertex settings;
	 * specifically changing the "Invoice When Order Status" setting
	 *
	 * @return the Vertex settings page
	 */
	protected M2AdminSalesTaxConfigPage configureSettings( )
	{
		M2AdminSalesTaxConfigPage vertexSettings = navigateToVertexSettings();

		vertexSettings.clickIntegrationSettingsTab();
		vertexSettings.changeSendInvoiceToVertexField("Order Status Is Changed");
		vertexSettings.changeInvoiceWhenOrderStatusField("Suspected Fraud");
		vertexSettings.saveConfig();

		return vertexSettings;
	}

	/**
	 * Tests navigation to the create new order page
	 * by selecting an existing customer
	 *
	 * @return the create new order page
	 */
	protected M2AdminCreateNewOrderPage navigateToCreateNewOrderPage( )
	{
		M2AdminSalesTaxConfigPage vertexSettings = configureSettings();
		vertexSettings.navPanel.clickSalesButton();

		M2AdminOrdersPage ordersPage = vertexSettings.navPanel.clickOrdersButton();
		M2AdminOrderCustomerPage customerPage = ordersPage.clickNewOrderButton();
		M2AdminCreateNewOrderPage createNewOrderPage = customerPage.clickCustomer(customerName);

		return createNewOrderPage;
	}

	/**
	 * Input and submit the customer's order info by
	 * adding an item via its SKU number
	 *
	 * @return the order info page
	 */
	protected M2AdminOrderViewInfoPage inputOrderInfo( )
	{
		String skuInput = "Simple Pants";

		M2AdminCreateNewOrderPage newOrderPage = navigateToCreateNewOrderPage();
		newOrderPage.clickAddSKUButton();
		newOrderPage.enterSKUNumber(skuInput);
		newOrderPage.enterQty();
		newOrderPage.clickAddToOrderButton();
		newOrderPage.addShippingMethod(0);
		newOrderPage.selectCheckAsPaymentMethod();
		// Need to select payment method, but can't find way to do so

		M2AdminOrderViewInfoPage orderViewInfoPage = newOrderPage.clickSubmitOrderButton();
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