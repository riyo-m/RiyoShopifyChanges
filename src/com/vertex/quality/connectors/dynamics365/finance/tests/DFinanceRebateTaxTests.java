package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * All tests related to Rebate
 * @author Mario Saint-Fleur
 */
@Listeners(TestRerunListener.class)
public class DFinanceRebateTaxTests extends DFinanceBaseTest
{
	/**
	 * @TestCase CD365-1296 CD365-1405
	 * @Description - To check the "rebate tax calculation" settings
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void rebateTaxSettingsTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;
		DFinanceAllSalesOrdersPage salesOrderPage;

		//================Data Declaration ===========================
		final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
		final String tax = DFinanceLeftMenuNames.TAX.getData();
		final String setup = DFinanceLeftMenuNames.SETUP.getData();
		final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		final String vertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
		final String retail = DFinanceLeftMenuNames.RETAIL.getData();
		final String discount = DFinanceLeftMenuNames.DISCOUNT.getData();
		final String all_discount = DFinanceLeftMenuNames.ALL_DISCOUNT.getData();
		final String taxParametersData = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();

		//================script implementation========================
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);

		//Navigate to vertex ta parameter page and enabled rebate tax

		settingsPage = homePage.selectModuleTabLink(vertexTaxParameters);
		settingsPage.selectSettingsPage(taxGroupSettings);
		settingsPage.selectCompany("USRT");
		settingsPage.toggleRebateButton(true);
		settingsPage.navigateToDashboardPage();

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(retail);
		homePage.expandModuleCategory(discount);
		homePage.selectModuleTabLink(all_discount);

		//Validate the rebate section displayed in all discount page
		settingsPage.validateRebate();
		settingsPage.navigateToDashboardPage();

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.collapseAll();
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(taxParametersData);

		settingsPage.selectSettingsPage(taxGroupSettings);
		settingsPage.toggleRebateButton(false);
		settingsPage.navigateToDashboardPage();

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(retail);
		homePage.expandModuleCategory(discount);
		homePage.selectModuleTabLink(all_discount);

		//Validate the rebate section is not displayed in all discount page
		settingsPage.validateRebate();

		//Change the company back to USMF
		settingsPage.selectCompany("USMF");

	}

	/**
	 * @TestCase CD365-709
	 * @Description - To Validate Rebate is working with Partial Invoice
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_Special" }, retryAnalyzer = TestRerun.class)
	public void partialInvoiceRebateTest( ){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;
		final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();
		final String itemNumber = "0001";
		final String discountAmount = "18.55";
		final String secondTimeQuantity = "10";
		final String fulFillmentProfile = "USRT-000002";
		final String[] expectedStrings ={"ST100016","ST100000"};

		//================Data Declaration ===========================
		final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
		final String tax = DFinanceLeftMenuNames.TAX.getData();
		final String setup = DFinanceLeftMenuNames.SETUP.getData();
		final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		final String vertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
		final DFinanceAllSalesOrdersSecondPage secondSalesOrderPageObj = new DFinanceAllSalesOrdersSecondPage(driver);
		final DFinanceBatchProcessingPage batchProcessingPage ;

		//================script implementation========================
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);

		//Navigate to vertex ta parameter page and enabled rebate tax

		settingsPage = homePage.selectModuleTabLink(vertexTaxParameters);
		settingsPage.selectSettingsPage(taxGroupSettings);
		settingsPage.selectCompany("USRT");
		settingsPage.toggleRebateButton(true);
		settingsPage.navigateToDashboardPage();

		//================script implementation=======================
		//Navigate to  All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		createSalesOrder(allSalesOrdersPage, defaultCustomerAccount);

		//Click on "Header" option
		allSalesOrdersPage.openHeaderTab();

		//lets the new sales order be identified & eliminated at the end of the test
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

		//Set "Sales tax group" -- 'VertexAR'
		allSalesOrdersPage.setSalesOrderTaxGroup(vertexAR);

		//Click on "Lines" option
		allSalesOrdersPage.openLinesTab();

		//Go to "Sales order lines" section and Enter "Item Number"
		allSalesOrdersPage.setItemNumber(defaultTableRow, itemNumber);

		//verify Discount amount
		assertEquals(secondSalesOrderPageObj.getDiscount(),discountAmount);

		//Change Delivery Mode
		secondSalesOrderPageObj.changeDeliveryMode("11");

		// Clicking on salesOrder and price details
		secondSalesOrderPageObj.clickOnSalesOrderLineTab();
		secondSalesOrderPageObj.clickOnPriceDetails();

		//Asserting Discount Codes
		String [] actualDiscountCodes = secondSalesOrderPageObj.getDiscountCodes();
		assertEquals(actualDiscountCodes,expectedStrings);
		secondSalesOrderPageObj.closeDetailsWindow();
		allSalesOrdersPage.enterLineQuantity(secondTimeQuantity);
		allSalesOrdersPage.clickNOOnLeadTimePopup();
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();

		//Verify the "Total calculated sales tax amount" value
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("30.87"),
				"'Total calculated sales tax amount' value is not expected");
		allSalesOrdersPage.salesTaxCalculator.clickOkButton();
		secondSalesOrderPageObj.clickOnComplete();
		String balance = secondSalesOrderPageObj.getBalance();
		secondSalesOrderPageObj.addPayment(balance, "1");

		//Navigate to DOM_PROCESSOR_JOB_SETUP
		batchProcessingPage = homePage.navigateToPage(DFinanceLeftMenuModule.RETAIL_AND_COMMERCE,
				DFinanceModulePanelCategory.DISTRIBUTED_ORDER_MANAGEMENT,
				DFinanceModulePanelCategory.BATCH_PROCESSING,
				DFinanceModulePanelLink.DOM_PROCESSOR_JOB_SETUP);
		batchProcessingPage.selectFulfillmentProfile(fulFillmentProfile);

		//Navigate to  All Sales Orders page
		homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Search created sales order
		allSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);
		allSalesOrdersPage.clickOnDisplayedSalesOrderNumber();
		secondSalesOrderPageObj.expandSalesOrderHeader();

		//verify DOM Status
		assertTrue(secondSalesOrderPageObj.verifyDOMStatus());

		//Verify Quantity is divided according to warehouse availability
		assertTrue(secondSalesOrderPageObj.verifyMoreThanOneQtyLines());

	}

}