package com.vertex.quality.connectors.dynamics365.retail.tests;

import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailDiscountPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class DRetailDiscountTests extends DRetailBaseTest
{
	/**
	 * @TestCase CD365R-22
	 * @Description - Create mult-line POS order with line and total discount percentage
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "Retail_regression" })
	public void createMultLinePOSOrderWithLineDiscountPercentageAndTotalDiscountPercentageTest( )
	{
		//================script implementation========================
		DRetailHomePage homePage = new DRetailHomePage(driver);
		DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
		DRetailDiscountPage discountPage = new DRetailDiscountPage(driver);

		//Navigate to product page
		allRetailSalesOrderPage.productPage();
		allRetailSalesOrderPage.addWatch();
		allRetailSalesOrderPage.addItemButton();
		allRetailSalesOrderPage.productPage();
		allRetailSalesOrderPage.addMensShoes();
		allRetailSalesOrderPage.addItemButton();

		//Navigate to transaction page and enter line discount percentage and total discount percentage
		allRetailSalesOrderPage.transactionButton();
		discountPage.clickDiscount();
		discountPage.selectLineDiscountPercent();
		discountPage.enterDiscountPercent();
		discountPage.selectCustomerSatisfaction();
		discountPage.selectTotalDiscountPercent();
		discountPage.enterDiscountPercent();
		discountPage.selectCustomerSatisfaction();

		//Validate the tax amount after the discount is applied
		String returnTaxAmount = allRetailSalesOrderPage.taxValidation();
		assertTrue(returnTaxAmount.equalsIgnoreCase("29.40"),
				"'Total sales tax amount' value is not expected");

		//Pay Cash
		allRetailSalesOrderPage.selectPayCash();
		allRetailSalesOrderPage.cashAccepted();
		allRetailSalesOrderPage.closeButton();
	}

	/**
	 * @TestCase CD365R-23
	 * @Description - Create mult-line POS order with line and total discount amount
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "Retail_regression" })
	public void createMultLinePOSOrderWithLineDiscountAmountAndTotalDiscountAmountTest( )
	{
		//================script implementation========================
		DRetailHomePage homePage = new DRetailHomePage(driver);
		DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
		DRetailDiscountPage discountPage = new DRetailDiscountPage(driver);

		//Navigate to product page
		allRetailSalesOrderPage.productPage();
		allRetailSalesOrderPage.addWatch();
		allRetailSalesOrderPage.addItemButton();
		allRetailSalesOrderPage.productPage();
		allRetailSalesOrderPage.addMensShoes();
		allRetailSalesOrderPage.addItemButton();

		//Navigate to transaction page and enter line discount percentage and total discount percentage
		allRetailSalesOrderPage.transactionButton();
		discountPage.clickDiscount();
		discountPage.selectLineDiscountAmount();
		discountPage.enterDiscountAmount("2");
		discountPage.selectCustomerSatisfaction();
		discountPage.selectTotalDiscountAmount();
		discountPage.enterDiscountAmount("2");
		discountPage.selectCustomerSatisfaction();

		//Validate the tax amount after the discount is applied
		String returnTaxAmount = allRetailSalesOrderPage.taxValidation();
		assertTrue(returnTaxAmount.equalsIgnoreCase("33.08"),
				"'Total sales tax amount' value is not expected");

		//Pay Cash
		allRetailSalesOrderPage.selectPayCash();
		allRetailSalesOrderPage.cashAccepted();
		allRetailSalesOrderPage.closeButton();
	}
}