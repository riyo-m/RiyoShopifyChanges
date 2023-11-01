package com.vertex.quality.connectors.dynamics365.retail.tests;

import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailCustomerPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class DRetailUserSelectShippingTests extends DRetailBaseTest
{
	/**
	 * Validate that if user select an item to be shipped than there is no error message displayed
	 */
	@Test(groups = { "Retail_regression" })
	public void userSelectsShippingTest( )
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

		customerPage.clickFindCustomerButton();
		customerPage.searchCustomer();
		customerPage.addCustomer();

		//Add standard shipping
		allRetailSalesOrderPage.clickOrders();
		allRetailSalesOrderPage.shipAllStandard();

		//Validate shipping is added
		String shipText = allRetailSalesOrderPage.shipValidation();
		assertTrue(shipText.contains("Ship Standard from Houston"),
			"'Shipping is not displayed");

	}

	/**
	 * @TestCase CD365-1468
	 * @Description - Create a POS Order with IL origin address, validate tax, and pay cash
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "Retail_regression" })
	public void validateAddressInPOSOrderTest( )
	{
		DRetailHomePage homePage = new DRetailHomePage(driver);
		DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);

		final String expectedTaxAmount = "11.30";

		//Select an item, complete the order, verify the tax amount, and pay in cash
		allRetailSalesOrderPage.transactionButton();
		allRetailSalesOrderPage.addCustomer("1001");
		allRetailSalesOrderPage.addProduct();
		allRetailSalesOrderPage.addSunglass();
		allRetailSalesOrderPage.selectPinkSunglass();
		allRetailSalesOrderPage.addItemButton();
		allRetailSalesOrderPage.transactionButton();
		allRetailSalesOrderPage.clickOrders();
		allRetailSalesOrderPage.clickCreateCustomerOrder();
		allRetailSalesOrderPage.shipSelectedStandardShipping();

		String actualTaxAmount = allRetailSalesOrderPage.taxValidation();
		assertTrue(actualTaxAmount.contains(expectedTaxAmount), "Actual tax amount is not equal to the expected tax amount");

		allRetailSalesOrderPage.selectPayCash();
		allRetailSalesOrderPage.cashAccepted();
		allRetailSalesOrderPage.closeButton();
	}
}