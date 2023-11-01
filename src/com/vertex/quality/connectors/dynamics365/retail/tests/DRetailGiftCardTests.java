package com.vertex.quality.connectors.dynamics365.retail.tests;

import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class DRetailGiftCardTests extends DRetailBaseTest
{
	/**
	 * Validate that if gift card is added first and than other items, it's not causing any tax issue
	 */
	@Test(groups = { "gift card", "Retail_regression" })
	public void GiftCardCausingNoTaxTest( )
	{
		//================script implementation========================
		//Navigate to add product page
		DRetailHomePage homePage = new DRetailHomePage(driver);
		DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);

		//Add Gift Card to the cart
		allRetailSalesOrderPage.transactionButton();
		allRetailSalesOrderPage.addGiftCard();

		//Validate the gift card tax amount
		String calculatedSalesTaxAmount = allRetailSalesOrderPage.taxValidation();
		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("0.00"),
			"'Total sales tax amount' value is not expected");

		//Navigate to all product page
		allRetailSalesOrderPage.productPage();

		//Navigate to accessories page
		allRetailSalesOrderPage.addSunglass();

		//Select pink sun glass and add to the cart
		allRetailSalesOrderPage.selectPinkSunglass();
		allRetailSalesOrderPage.addItemButton();

		//Click on the transaction button
		allRetailSalesOrderPage.transactionButton();

		//Validate the tax amount after adding the other item to the cart
		String salesTaxAmount = allRetailSalesOrderPage.taxValidation();
		assertTrue(salesTaxAmount.equalsIgnoreCase("10.73"),
			"'Total sales tax amount' value is not expected");

		//Void the current transaction and release the gift card
		allRetailSalesOrderPage.voidTransaction();
		allRetailSalesOrderPage.clickYesButton();

	}

	/**
	 * Validate that if a item is return and buy more item in the same transaction is not causing any tax issue
	 */
	@Test(groups = { "return item", "Retail_regression" })
	public void ReturnAndAddMoreItemTaxTest( )
	{
		//================script implementation========================
		DRetailHomePage homePage = new DRetailHomePage(driver);
		DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);

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

		//Return item
		allRetailSalesOrderPage.returnProduct();

		//Validate the tax amount after adding the returning item to the cart
		String returnTaxAmount = allRetailSalesOrderPage.taxValidation();
		assertTrue(returnTaxAmount.equalsIgnoreCase("8.58"),
			"'Total sales tax amount' value is not expected");

		//Navigate to all product page
		allRetailSalesOrderPage.productPage();

		//Add watch to the cart
		allRetailSalesOrderPage.addWatch();

		//Click add item button
		allRetailSalesOrderPage.addItemButton();

		//Navigate to transaction page
		allRetailSalesOrderPage.transactionButton();

		//Navigate to all product page
		allRetailSalesOrderPage.productPage();

		//Add men's shoes to the cart
		allRetailSalesOrderPage.addMensShoes();

		//Click add item button
		allRetailSalesOrderPage.addItemButton();

		//Navigate to transaction page
		allRetailSalesOrderPage.transactionButton();

		//Validate the tax amount after adding more item to the cart
		String allTaxAmount = allRetailSalesOrderPage.taxValidation();
		assertTrue(allTaxAmount.equalsIgnoreCase("24.83"),
			"'Total sales tax amount' value is not expected");

		//Void the current transaction
		allRetailSalesOrderPage.deleteTransaction();
		allRetailSalesOrderPage.clickYesButton();
	}
}