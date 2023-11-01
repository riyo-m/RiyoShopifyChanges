package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuNames;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceVertexTaxParametersLeftMenuNames;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceAllSalesOrdersPage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceHomePage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceSettingsPage;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * To check the "Tax Area Id" field in XML request, when
 * "Use address tax area ID in sales tax calculation" parameter is set to "YES" and "NO"
 *
 * @author Saidulu Kodadala , dpatel
 */
@Listeners(TestRerunListener.class)
public class DFinanceLoggingTests extends DFinanceBaseTest
{
	Boolean discountCodeSet = false;

	@BeforeMethod(alwaysRun = true)
	public void setupTest( )
	{
		discountCodeSet = false;
	}

	@AfterMethod(alwaysRun = true)
	public void teardownTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		final String vertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
		final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
		if (discountCodeSet) {

			homePage.clickOnNavigationPanel();
			homePage.selectLeftMenuModule(tax);
			homePage.collapseAll();
			homePage.expandModuleCategory(setup);
			homePage.expandModuleCategory(vertex);

			settingsPage = homePage.selectModuleTabLink(vertexTaxParameters);
			settingsPage.selectSettingsPage(taxGroupSettings);
			settingsPage.setDefaultDiscountCode(defaultDiscountCode1);
			homePage = settingsPage.clickOnSaveButton();
		}
	}

	/**
	 *
	 * 	@TestCase CD365-1293 CD365-1402
	 * 	@Description - To check the "Sales tax group" settings
	 * 	@Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void TaxAreaIdTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;
		DFinanceAllSalesOrdersPage salesOrdersPage;

		//================Data Declaration ===========================
		final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
		final String tax = DFinanceLeftMenuNames.TAX.getData();
		final String setup = DFinanceLeftMenuNames.SETUP.getData();
		final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		final String vertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();

		final String itemNumber = "1000";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";
		final String quantity = "1";


		//================script implementation=======================
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);

		settingsPage = homePage.selectModuleTabLink(vertexTaxParameters);
		settingsPage.selectSettingsPage(taxGroupSettings);
		settingsPage.setDefaultDiscountCode(defaultDiscountCode);
		discountCodeSet = true;
		homePage = settingsPage.clickOnSaveButton();

		//Navigate to  All Sales Orders page
		salesOrdersPage = homePage.navigateToAllSalesOrdersPage();

		//Create sales order
		homePage.newAddress(defaultCustomerAccount);

		//Click on "OK"  button on "Create Sales Order" screen
		salesOrdersPage.finishCreatingSalesOrder();

		//Click on "Header" option
		salesOrdersPage.openHeaderTab();
		String salesOrderNumber = salesOrdersPage.getSalesOrderNumber();

		//Go to "Set up" section and Set "Sales tax group"
		salesOrdersPage.setSalesOrderTaxGroup(defaultSalesTaxGroup);

		/// add line item
		salesOrdersPage.addItemLine(itemNumber, quantity, site, warehouse, unitPrice);

		//Click on "Sales Tax" option
		salesOrdersPage.clickOnTab(salesOrdersPage.sellTabName);

		// navigate to Tax --> Sales Tax page
		salesOrdersPage.openSalesTaxCalculation();
		//Click on "OK" button
		salesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		homePage.navigateToAllSalesOrdersPage();

		//Search created sales order
		salesOrdersPage = homePage.searchCreatedSalesOrder(salesOrderNumber);
		salesOrdersPage.clickOnSearchButton();
		salesOrdersPage.clickOnDisplayedSalesOrderNumber();

		//Click on "Delete" option from the menu
		salesOrdersPage.clickDeleteButton();
		salesOrdersPage.clickOnYes();
	}
}
