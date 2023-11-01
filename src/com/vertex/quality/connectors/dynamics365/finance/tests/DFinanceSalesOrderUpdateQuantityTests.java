package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceAllSalesOrdersPage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceAllSalesOrdersSecondPage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceHomePage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceSettingsPage;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;
import java.util.*;
@Listeners(TestRerunListener.class)
public class DFinanceSalesOrderUpdateQuantityTests extends DFinanceBaseTest
{
	/**
	 * @TestCase CD365-1406
	 * @Description - creates a new sales order, change the quantity and validates the tax is correct
	 * @author Sgupta
	 */

	@Test(groups = { "FO_AR_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void salesOrderUpdateQuantityTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//================Data Declaration ===========================
		final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();

		final String itemNumber = "1000";
		final String itemQuantity = "4";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";
		final String quantity = "2";

		//================script implementation=======================
		//Navigate to  All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
			DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
			DFinanceModulePanelLink.ALL_SALES_ORDERS);

		createSalesOrder(allSalesOrdersPage, defaultCustomerAccount);

		//Click on "Header" option
		allSalesOrdersPage.openHeaderTab();

		//lets the new sales order be identified & eliminated at the end of the test
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderId();

		//Set "Sales tax group" -- 'VertexAR'
		allSalesOrdersPage.setSalesOrderTaxGroup(vertexAR);

		//Click on "Lines" option
		allSalesOrdersPage.openLinesTab();

		//Add line item
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, itemQuantity, unitPrice, 0);

		//Click on "Sales Tax" option
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();

		//Verify the "Total calculated sales tax amount" value
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("320.00"),
			"'Total calculated sales tax amount' value is not expected");

		//Click on "OK" button
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Click on update line
		allSalesOrdersPage.clickOnUpdateLine();

		//Select delivery remainder
		allSalesOrdersPage.selectDeliveryRemainder();

		//Update quantity
		allSalesOrdersPage.changeQuantity(quantity);
		allSalesOrdersPage.clickOnOKBtn();

		//Click on "Sales Tax" option
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();

		//Verify the "Updated calculated sales tax amount" value
		String updatedCalculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		//Verify the "Total calculated sales tax amount" value
		assertTrue(updatedCalculatedSalesTaxAmount.equalsIgnoreCase("160.00"),
			"'Total calculated sales tax amount' value is not expected");

		//Click on "OK" button
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Click on "Delete" option from the menu
		allSalesOrdersPage.clickDeleteButton();

		//Click on "Yes" option from the pop-up
		allSalesOrdersPage.agreeToConfirmationPopup();




	}
}
