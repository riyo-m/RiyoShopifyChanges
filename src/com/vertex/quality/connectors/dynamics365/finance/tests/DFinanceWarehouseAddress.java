package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceAllSalesOrdersPage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceHomePage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceSettingsPage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceXMLInquiryPage;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
@Listeners(TestRerunListener.class)
public class DFinanceWarehouseAddress extends DFinanceBaseTest
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
	 * To check the "correct physical address of site is displayed in the log when warehouse address is blank"
	 * @author SGupta
	 * CD365-1388
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void warehouseAddressTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;
		DFinanceAllSalesOrdersPage salesOrderPage;
		DFinanceXMLInquiryPage xMLInqueryPage = new DFinanceXMLInquiryPage(driver);

		//================Data Declaration ===========================
		final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
		final String tax = DFinanceLeftMenuNames.TAX.getData();
		final String setup = DFinanceLeftMenuNames.SETUP.getData();
		final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		final String vertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
		final String accountsReceivable = DFinanceLeftMenuNames.ACCOUNTS_RECEIVABLE.getData();
		final String orders = DFinanceLeftMenuNames.ORDERS.getData();
		final String allSalesOrders = DFinanceLeftMenuNames.ALL_SALES_ORDERS.getData();

		final String itemNumber = "1000";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "899";
		final String discount = "150";

		//================script implementation========================
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
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
			DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
			DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "New" option
		salesOrderPage = settingsPage.clickOnNewButton();

		//Enter "Customer account"
		salesOrderPage.setCustomerAccount(defaultCustomerAccount);

		//Click on "OK"  button on "Create Sales Order" screen
		salesOrderPage.clickOkBTN();

		//Click on "Header" option
		salesOrderPage.openHeaderTab();

		//lets the new sales order be identified & eliminated at the end of the test
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

		//Go to "Set up" section
		salesOrderPage.expandSetupHeaderSection();

		//Select "VertexAR" drop down option
		salesOrderPage.setSalesOrderTaxGroup(defaultSalesTaxGroup);

		//Click on "Lines" option
		salesOrderPage.openLinesTab();

		//Go to "Sales order lines" section and Enter "Item Number"
		allSalesOrdersPage.setItemNumber(defaultTableRow, itemNumber);

		//Enter "Site"
		allSalesOrdersPage.newSetSite(defaultTableRow, site);

		//Enter "Unit Price"
		allSalesOrdersPage.newUnitPrice(defaultTableRow, unitPrice);

		//Click on "Sales Tax" option
		salesOrderPage.clickOnTab(salesOrderPage.sellTabName);
		salesOrderPage.openSalesTaxCalculation();

		//Verify the "Total calculated sales tax amount" value
		String calculatedSalesTaxAmount = salesOrderPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("71.92"),
			"'Total calculated sales tax amount' value is not expected");

		//Click on "OK" button
		allSalesOrdersPage.clickOk();

		//Navigate to  XML inquiry
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
			DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Select the Correct Response from the list
		xMLInqueryPage.getDocumentID(salesOrderNumber);
		xMLInqueryPage.clickResponse();

		//Validate that address is present in the log
		String response = xMLInqueryPage.getLogRequestValue();
		assertTrue(response.contains("321 North Street, Gate 2"));

	}
}

