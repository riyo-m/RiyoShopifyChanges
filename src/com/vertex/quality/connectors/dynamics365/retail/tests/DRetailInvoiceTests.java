package com.vertex.quality.connectors.dynamics365.retail.tests;

import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuNames;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceAllSalesOrdersSecondPage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceHomePage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceSettingsPage;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailCustomerPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * To validate that sales order is created in F&O without running the P job
 * Transaction is passed from Retail to F&O
 *
 * @author SGupta
 */

public class DRetailInvoiceTests extends DRetailBaseTest
{
	/**
	 * Create the order in retail POS with standard shipping
	 */
	@Test(groups = { "shipping", "Retail_regression" }, priority = 0)
	public void createOrderTest( )
	{
		//================script implementation========================
		//Navigate to add product page
		DRetailHomePage homePage = new DRetailHomePage(driver);
		DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
		DRetailCustomerPage customerPage = new DRetailCustomerPage(driver);

		//Navigate to all product page
		allRetailSalesOrderPage.productPage();

		//Navigate to accessories page
		allRetailSalesOrderPage.addSunglass();

		//Select pink sun glass and add to the cart
		allRetailSalesOrderPage.selectPinkSunglass();

		//Click on add item button
		allRetailSalesOrderPage.addItemButton();

		//Navigate to transaction page
		allRetailSalesOrderPage.transactionButton();

		//Navigate to transaction page
		allRetailSalesOrderPage.transactionButton();

		customerPage.clickFindCustomerButton();
		customerPage.searchCustomer();
		customerPage.addCustomer();

		//Add standard shipping
		allRetailSalesOrderPage.clickOrders();
		allRetailSalesOrderPage.shipAllStandard();

		//Complete the transaction , pay cash
		allRetailSalesOrderPage.selectPayCash();
		allRetailSalesOrderPage.cashAccepted();
		allRetailSalesOrderPage.closeButton();

	}

	/**
	 * Validating the sales Order in F&O is in Open order status
	 */

	public class InvoiceTests extends DFinanceBaseTest {

		@Test(priority = 1)
		public void validateInvoiceTest( )
		{
			DFinanceHomePage homePage = new DFinanceHomePage(driver);
			DFinanceSettingsPage settingPage;
			DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
			String receivable = DFinanceLeftMenuNames.ACCOUNTS_RECEIVABLE.getData();
			String orders = DFinanceLeftMenuNames.ORDERS.getData();
			String allSalesOrder = DFinanceLeftMenuNames.ALL_SALES_ORDERS.getData();

			//Navigate to Vertex Settings page
			homePage.clickOnNavigationPanel();
			homePage.selectLeftMenuModule(receivable);
			homePage.collapseAll();
			homePage.expandModuleCategory(orders);
			settingPage = homePage.selectModuleTabLink(allSalesOrder);
			settingPage.selectCompany("USRT");

			//validate sales order status is open order
			String orderStatusText = allSalesOrdersSecondPage.salesOrderValidation();
			assertTrue(orderStatusText.contains("Open order"),
				"'Order is in different status");

		}

	}
}