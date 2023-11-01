package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceConstantDataResource;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuNames;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceVertexTaxParametersLeftMenuNames;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceAllSalesOrdersPage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceAllSalesOrdersSecondPage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceHomePage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceSettingsPage;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * To check the "Sales tax group" settings
 *
 * @author Saidulu Kodadala
 */
@Listeners(TestRerunListener.class)
public class DFinanceTaxGroupTests extends DFinanceBaseTest
{

	Boolean advTaxGroupChanged = false;

	@BeforeMethod(alwaysRun = true)
	public void setupTest( )
	{
		advTaxGroupChanged = false;
	}

	@AfterMethod(alwaysRun = true)
	public void teardownTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		String VertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
		if (advTaxGroupChanged) {

			homePage.clickOnNavigationPanel();
			homePage.selectLeftMenuModule(tax);
			homePage.collapseAll();
			homePage.expandModuleCategory(setup);
			homePage.expandModuleCategory(vertex);
			settingsPage = homePage.selectModuleTabLink(VertexTaxParameters);
			settingsPage.setVertexSalesTaxGroup(defaultSalesTaxGroup);
			settingsPage.clickOnSaveButton();
		}
	}

	/**
	 * @TestCase CD365-1407
	 * @Description - To check the "Sales tax group" settings
	 * @Author - Saidulu Kodadala
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void TaxGroupSettingsTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;
		DFinanceAllSalesOrdersPage salesOrdersPage;
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//================Data Declaration ===========================
		final String tax = DFinanceLeftMenuNames.TAX.getData();
		final String setup = DFinanceLeftMenuNames.SETUP.getData();
		final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		final String VertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
		final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
		final String california = DFinanceConstantDataResource.CA.getData();
		final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();
		final String accountReceivable = DFinanceLeftMenuNames.ACCOUNTS_RECEIVABLE.getData();
		final String orders = DFinanceLeftMenuNames.ORDERS.getData();
		final String allSalesOrders = DFinanceLeftMenuNames.ALL_SALES_ORDERS.getData();

		final String itemNumber = "1000";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";
		final String quantity = "1";

		//================script implementation========================
		this.navigateToCategory(tax, setup, vertex);
		settingsPage = homePage.selectModuleTabLink(VertexTaxParameters);

		//Click on "Tax Group Settings"
		settingsPage.selectSettingsPage(taxGroupSettings);

		//Verify "Vertex sales tax group" dropdown present
		boolean vertexSalesTaxGroup = settingsPage.verifyVertexSalesTaxGroup();
		this.elementVerification(vertexSalesTaxGroup);

		//Verify "Default item tax group" dropdown present
		boolean defaultItemTaxGroup = settingsPage.verifyDefaultItemTaxGroup();
		this.elementVerification(defaultItemTaxGroup);

		//Verify "Vertex purchase sales tax group" dropdown present
		boolean vertexPurchaseSalesTaxGroup = settingsPage.verifyVertexPurchaseSalesTaxGroup();
		this.elementVerification(vertexPurchaseSalesTaxGroup);

		//Verify "Vertex consumer use tax group" dropdown present
		boolean vertexConsumerUseTaxGroup = settingsPage.verifyVertexConsumerUseTaxGroup();
		this.elementVerification(vertexConsumerUseTaxGroup);

		//Set "Vertex sales tax group" 
		settingsPage.setVertexSalesTaxGroup(california);
		advTaxGroupChanged = true;

		//Click on "Save" option
		settingsPage.clickOnSaveButton();

		//Navigate to  All Sales Orders page
		homePage.navigateToAllSalesOrdersPage();

		//Create sales order 
		salesOrdersPage = homePage.newAddress(defaultCustomerAccount);

		//Click on "OK"  button on "Create Sales Order" screen
		salesOrdersPage.finishCreatingSalesOrder();

		//Click on "Header" option
		salesOrdersPage.openHeaderTab();
		String salesOrderNumber = salesOrdersPage.getSalesOrderNumber();
		VertexLogger.log(String.format("Sales Order# %s", salesOrderNumber));

		//Go to "Set up" section and Set "Sales tax group"
		salesOrdersPage.setSalesOrderTaxGroup(defaultSalesTaxGroup);

		//Click on "Lines" option
		salesOrdersPage.openLinesTab();

		/// add line item
		salesOrdersPage.addItemLine(itemNumber, quantity, site, warehouse, unitPrice);

		//Click on "Sales Tax" option
		salesOrdersPage.clickOnTab(salesOrdersPage.sellTabName);

		// navigate to Tax --> Sales Tax page
		salesOrdersPage.openSalesTaxCalculation();

		//Verify the "Total actual sales tax amount" value
		String calculatedSalesTaxAmount = salesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("0.00"), "Temporary sales tax is not displayed");

		//Verify the "Total calculated sales tax amount" value 
		String actualSalesTaxAmount = salesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"), "Temporary sales tax is not displayed");

		//Verify the FIRST tax line item  
		salesOrdersPage.verifyTempSalesTaxTrxnLinesCount(1);
		salesOrdersPage.verifyLineData("1", "VertexAR", "0.00000", "0.00", "Sales tax payble", "BLANK", "1000");

		//Click on "OK" button
		salesOrdersPage.finishCreatingSalesOrder();

		//Click on "X" to close the Sales Order Screen
		salesOrdersPage.clickOnCloseSalesOrderPage("2");

		//Search created sales order
		salesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);
		salesOrdersPage.clickOnDisplayedSalesOrderNumber();


		//Click on "Header" option
		salesOrdersPage.openHeaderTab();

		//Go to "Set up" section and Set "Sales tax group"
		salesOrdersPage.clickAndSetSalesTaxGroup(california);

		//Click on "Save" option
		salesOrdersPage.clickOnSaveButton();

		//Click on "Sales Tax" option
		allSalesOrdersSecondPage.updateSalesTaxGroupToggle();
		allSalesOrdersSecondPage.clickOnOK();
		salesOrdersPage.clickOnTab(salesOrdersPage.sellTabName);
		salesOrdersPage.openSalesTaxCalculation();

		//Verify the "Total actual sales tax amount" value
		calculatedSalesTaxAmount = salesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("80.00"), "Temporary sales tax is not displayed");

		//Verify the "Total calculated sales tax amount" value 
		actualSalesTaxAmount = salesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("80.00"), "Temporary sales tax is not displayed");

		//Click on "OK" button
		homePage = salesOrdersPage.finishCreatingSalesOrder();

		salesOrdersPage.clickDeleteButton();
		salesOrdersPage.clickOnYes();
	}

	/**
	 * ========================This Class Common Methods ==================================================================	
	 */
	/**
	 * Navigate to category
	 *
	 * @param menu
	 * @param category
	 * @param subCategory
	 */
	private void navigateToCategory( String menu, String category, String subCategory )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(menu);
		homePage.expandModuleCategory(category);
		homePage.expandModuleCategory(subCategory);
	}

	/**
	 * Element verification
	 *
	 * @param textName
	 */
	private void elementVerification( boolean textName )
	{
		assertTrue(textName == true, "Element is not displayed ");
		String adapterVersionMessage = String.format(" : %s", textName);
		VertexLogger.log(adapterVersionMessage);
	}
}

