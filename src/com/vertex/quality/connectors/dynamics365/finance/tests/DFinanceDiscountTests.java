package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.openqa.selenium.Keys;
import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;

/**
 * To verify the "default discount code" and also the "discount amounts" at the line item level
 *
 * @author Saidulu Kodadala
 */
@Listeners(TestRerunListener.class)
public class DFinanceDiscountTests extends DFinanceBaseTest
{
	Boolean discountCodeSet = false;
	Boolean rebateOn = true;

	@BeforeMethod(alwaysRun = true)
	public void setupTest( )
	{
		discountCodeSet = false;
		rebateOn = true;

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		//Enable Accounts Receivable
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.collapseAll();
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(taxParametersData);
		settingsPage.toggleAccountsReceivable(true);

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
		if(!rebateOn)
		{
			//Navigate and turn Vertex Parameter on
			homePage.clickOnNavigationPanel();
			homePage.selectLeftMenuModule(tax);
			homePage.expandModuleCategory(setup);
			homePage.expandModuleCategory(vertex);
			settingsPage = homePage.selectModuleTabLink(vertexTaxParameters);
			settingsPage.selectSettingsPage(taxGroupSettings);
			settingsPage.toggleRebateButton(true);
			settingsPage.navigateToDashboardPage();
		}
	}

	/**
	 * To verify the "default discount code" and also the "discount amounts" at the line item level
	 * CD365-585 CD365-248
	 */
	@Test(groups = { "FO_smoke", "FO_AR_regression", "FO_ALL", "FO_Trial" }, retryAnalyzer = TestRerun.class)
	public void DefaultDiscountCodeTest( )
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
		final String accountsReceivable = DFinanceLeftMenuNames.ACCOUNTS_RECEIVABLE.getData();
		final String orders = DFinanceLeftMenuNames.ORDERS.getData();
		final String allSalesOrders = DFinanceLeftMenuNames.ALL_SALES_ORDERS.getData();

		final String itemNumber = "1000";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";
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

		//Go to "Set up" section
		//salesOrderPage.expandSetupHeaderSection();

		//Select "VertexAR" drop down option
		salesOrderPage.setSalesOrderTaxGroup(defaultSalesTaxGroup);

		//Clear the value from Price & Discount > Cash Discounts
		salesOrderPage.expandHeader(salesOrderPage.PRICE_AND_DISCOUNT);
		salesOrderPage.clearCashDiscount();

		//Click on "Lines" option
		salesOrderPage.openLinesTab();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		allSalesOrdersSecondPage.fillItemsInfo("1000","2", "21", "1", "1000", "150",0);

		//Click on "Sales Tax" option 
		salesOrderPage.clickOnTab(salesOrderPage.sellTabName);
		salesOrderPage.openSalesTaxCalculation();

		//Verify the "Total calculated sales tax amount" value
		String calculatedSalesTaxAmount = salesOrderPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("68.00"),
			"'Total calculated sales tax amount' value is not expected");

		//Click on "OK" button
		allSalesOrdersPage.clickOk();

		//Click on "Delete" option from the menu
		allSalesOrdersPage.clickDeleteButton();

		//Click on "Yes" option from the pop-up
		allSalesOrdersPage.agreeToConfirmationPopup();
	}

	/**
	 *  @TestCase CD365-1398
	 * 	@Description - To verify the "discount code" and also the "discount percentage" at the line item level
	 * 	@Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_smoke", "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void multilineDiscountTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		DFinanceSettingsPage settingsPage;
		DFinanceAllSalesOrdersPage salesOrderPage;

		//================Data Declaration ===========================
		final String accountReceivable = DFinanceLeftMenuNames.ACCOUNTS_RECEIVABLE.getData();
		final String orders = DFinanceLeftMenuNames.ORDERS.getData();
		final String allSalesOrders = DFinanceLeftMenuNames.ALL_SALES_ORDERS.getData();

		final String product1Number = "1000";
		final String product2Number = "A0001";
		System.out.println("product - 1 Number: " + product1Number);
		System.out.println("product - 2 Number: " + product2Number);

		final String customerAccount = "US-004";
		final String salesTaxGroup = DFinanceConstantDataResource.VERTEXAR.getData();

		final String quantity = "1";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";
		final double expectedTaxAmount = 80.00;

		int expectedLinesCount = 6;

		//================script implementation=======================

		//Navigate to  All Sales Orders page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(accountReceivable);
		homePage.expandModuleCategory(orders);
		settingsPage = homePage.selectModuleTabLink(allSalesOrders);

		//Click on "New" option
		salesOrderPage = settingsPage.clickOnNewButton();

		//Enter "Customer account" (above created)
		salesOrderPage.expandCustomerSection();
		salesOrderPage.setCustomerAccount(customerAccount);

		//Click on "OK"  button on "Create Sales Order" screen
		salesOrderPage.finishCreatingSalesOrder();

		//Click on "Header" option
		salesOrderPage.openHeaderTab();

		String salesOrderNumber = salesOrderPage.getSalesOrderNumber();
		VertexLogger.log(String.format("Sales Order# %s", salesOrderNumber));

		//Set "Sales tax group" -- 'VertexAR'
		salesOrderPage.setSalesOrderTaxGroup(salesTaxGroup);

		//Click on "Lines" option
		salesOrderPage.openLinesTab();

		// add line item (Product - 1)
		salesOrderPage.addItemLine(product1Number, quantity, site, warehouse, unitPrice);

		// add line item (Product - 1)
		allSalesOrdersSecondPage.fillItemsInfo(product2Number,site, warehouse, quantity, unitPrice, 1);

		//Click on "Sales Tax" option
		salesOrderPage.clickOnTab(salesOrderPage.sellTabName);

		// navigate to Tax --> Sales Tax page
		salesOrderPage.openSalesTaxCalculation();
	}


	/**
	 * Multi line order with Discount Line Percentage
	 * CD365-249
	 * @author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_smoke", "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void multiLineItemSalesOrderWithDiscountLinePercentageTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;

		//================Data Declaration ===========================
		final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
		final String tax = DFinanceLeftMenuNames.TAX.getData();
		final String setup = DFinanceLeftMenuNames.SETUP.getData();
		final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		final String vertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();

		final String itemNumber = "D0007";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";
		final String discount = "15";

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
		allSalesOrdersPage = settingsPage.clickOnNewButton();

		//Enter "Customer account"
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		//Click on "OK"  button on "Create Sales Order" screen
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

		// add multiple line item to the SO
		allSalesOrdersSecondPage.fillItemsInfoWithDiscountPercentage(itemNumber, site, warehouse, "1", unitPrice, discount, 0);
		allSalesOrdersSecondPage.fillItemsInfoWithDiscountPercentage("D0001","1", "13", "1", "500", "50",1);

		//Clicks on the invoice tab
		allSalesOrdersSecondPage.clickInvoice();
		allSalesOrdersSecondPage.clickOk();

		//Navigate to  XML inquiry
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		DFinanceXMLInquiryPage xMLInqueryPage = new DFinanceXMLInquiryPage(driver);

		//Select the Correct Response from the list
		xMLInqueryPage.getDocumentID(salesOrderNumber);
		xMLInqueryPage.clickResponse();

		//Validate that tax amount is present in the log
		String response = xMLInqueryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>88.0</TotalTax>"));
	}

	/**
	 * Multi Line Order with Discount Order Amount
	 * CD365-247
	 * @author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_smoke", "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void multiLineItemSalesOrderWithDiscountLineAmountTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;

		final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
		final String tax = DFinanceLeftMenuNames.TAX.getData();
		final String setup = DFinanceLeftMenuNames.SETUP.getData();
		final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		final String vertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();

		final String itemNumber = "D0007";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";
		final String discount = "15";

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
		allSalesOrdersPage = settingsPage.clickOnNewButton();

		//Enter "Customer account"
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		//Click on "OK"  button on "Create Sales Order" screen
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

		// add multiple line item to the SO
		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, warehouse, "1", unitPrice, discount, 0);
		allSalesOrdersSecondPage.fillItemsInfo("L0001","1", "13", "1", "500", "5",1);

		//Clicks on the invoice tab
		allSalesOrdersSecondPage.clickInvoice();
		allSalesOrdersSecondPage.clickOk();

		//Navigate to  XML inquiry
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		DFinanceXMLInquiryPage xMLInqueryPage = new DFinanceXMLInquiryPage(driver);

		//Select the Correct Response from the list
		xMLInqueryPage.getDocumentID(salesOrderNumber);
		xMLInqueryPage.clickResponse();

		//Validate that tax percentage is present in the log
		String response = xMLInqueryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>118.4</TotalTax>"));
	}

	/**
	 * @TestCase CD365-250
	 * @Description - Create Sales Order with Discount - Multi line with Discount order Percent, Change Discount Order Percent
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void createSalesOrderWithMultLineDiscountAndChangeDiscountPercentageTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;

		final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
		final String tax = DFinanceLeftMenuNames.TAX.getData();
		final String setup = DFinanceLeftMenuNames.SETUP.getData();
		final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		final String vertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();

		final String itemNumber = "D0007";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";
		final String discount = "15";

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);

		settingsPage = homePage.selectModuleTabLink(vertexTaxParameters);
		settingsPage.selectSettingsPage(taxGroupSettings);
		settingsPage.setDefaultDiscountCode(defaultDiscountCode);
		discountCodeSet = true;
		homePage = settingsPage.clickOnSaveButton();

		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Create a new Sales Order
		allSalesOrdersPage = settingsPage.clickOnNewButton();
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillItemsInfoWithDiscountPercentage(itemNumber, site, warehouse, "3", unitPrice, discount, 0);
		allSalesOrdersSecondPage.fillItemsInfoWithDiscountPercentage("L0001","1", "13", "1", "500", "5",1);

		//Verify the "Total calculated sales tax amount" value
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("242.00"),
				"'Total calculated sales tax amount' value is not expected");
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Update Line Items
		allSalesOrdersPage.updateItemQuantity("D0007", "5");
		//Verify the "Total calculated sales tax amount" value
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("378.00"),
				"'Total calculated sales tax amount' value is not expected");
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		allSalesOrdersSecondPage.clickInvoice();
		allSalesOrdersSecondPage.clickOk();

		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		DFinanceXMLInquiryPage xMLInquiryPage = new DFinanceXMLInquiryPage(driver);

		//Validate that tax percentage is present in the log
		xMLInquiryPage.getDocumentID(salesOrderNumber);
		xMLInquiryPage.clickResponse();
		String response = xMLInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>378.0</TotalTax>"));
	}

	/**
	 * @TestCase CD365-245
	 * @Description - Create Sales Order with Discount - Multi line with Discount Percentage and Amount
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void createSalesOrderWithMultLineDiscountPercentageAndAmountTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		final String itemNumber = "D0007";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";
		final String discount = "15";

		Double expectedTaxAmount = 107.60;

		settingsPage.selectCompany(usmfCompany);

		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);


		//Enter "Customer account" and add multiple line items with discount percentage and amount
		allSalesOrdersPage = settingsPage.clickOnNewButton();
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillItemsInfoWithDiscountPercentage(itemNumber, site, warehouse, "1", unitPrice, discount, 0);
		allSalesOrdersSecondPage.fillItemsInfo("L0001","1", "13", "1", "500", "5",1);

		///Validate sales tax amount
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Post the invoice
		allSalesOrdersSecondPage.clickInvoice();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Navigate to  XML inquiry and verify tax amount
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		DFinanceXMLInquiryPage xMLInquiryPage = new DFinanceXMLInquiryPage(driver);
		xMLInquiryPage.getDocumentID(salesOrderNumber);
		xMLInquiryPage.clickResponse();
		String response = xMLInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>107.6</TotalTax>"));
	}

	/**
	 * @TestCase CD365-586
	 * @Description - Validate sales tax for when Vertex Parameter is on and discount parameter is off for item
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validateSalesTaxForSalesOrderWhenVertexParameterIsOnAndDiscountParameterIsOffTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		final String itemNumber = "92074";
		final String quantity = "2";
		final Double expectedTaxAmount = 33.60;

		settingsPage.selectCompany(usrtCompany);

		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Create sales order
		allSalesOrdersPage = settingsPage.clickOnNewButton();
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillItemsInfoForUSRT(itemNumber, quantity, 0);
		allSalesOrdersSecondPage.changeDeliveryMode("11");
		allSalesOrdersSecondPage.changeUnderDelivery("100");
		allSalesOrdersSecondPage.clickOnProductTab();
		allSalesOrdersSecondPage.clickAndEnterProductValue("Size", "7");
		allSalesOrdersSecondPage.clickAndEnterProductValue("Color", "Light Blue");
		allSalesOrdersSecondPage.clickAndEnterProductValue("Style", "Regular"+ Keys.ENTER);

		///Validate sales tax amount
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		allSalesOrdersPage.clickOnCancel();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				calculatedSalesTaxAmount+" : "+assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				actualSalesTaxAmount+" : "+assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Navigate to XML inquiry and verify tax amount
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		DFinanceXMLInquiryPage xMLInquiryPage = new DFinanceXMLInquiryPage(driver);
		xMLInquiryPage.getDocumentID(salesOrderNumber);
		xMLInquiryPage.clickResponse();
		String response = xMLInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>33.6</TotalTax>"));
	}

	/**
	 * @TestCase CD365-587
	 * @Description - Validate sales tax for when Vertex Parameter is on and discount parameter is on for item
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validateSalesTaxForSalesOrderWhenVertexParameterIsOnAndDiscountParameterIsOnTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		final String itemNumber = "0005";
		final String quantity = "2";
		final Double expectedTaxAmount = 0.52;

		settingsPage.selectCompany(usrtCompany);

		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Create sales order
		allSalesOrdersPage = settingsPage.clickOnNewButton();
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillItemsInfoForUSRT(itemNumber, quantity, 0);
		allSalesOrdersSecondPage.changeDeliveryMode("11");
		allSalesOrdersSecondPage.changeUnderDelivery("100");

		///Validate sales tax amount
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		allSalesOrdersPage.clickOnCancel();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Navigate to XML inquiry and verify tax amount
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		DFinanceXMLInquiryPage xMLInquiryPage = new DFinanceXMLInquiryPage(driver);
		xMLInquiryPage.getDocumentID(salesOrderNumber);
		xMLInquiryPage.clickResponse();
		String response = xMLInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>0.52</TotalTax>"));
	}

	/**
	 * @TestCase CD365-588
	 * @Description - Validate sales tax for when Vertex Parameter is off and discount parameter is off for item
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validateSalesTaxForSalesOrderWhenVertexParameterIsOffAndDiscountParameterIsOffTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		final String itemNumber = "92074";
		final String quantity = "2";
		final Double expectedTaxAmount = 33.60;

		final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
		final String tax = DFinanceLeftMenuNames.TAX.getData();
		final String setup = DFinanceLeftMenuNames.SETUP.getData();
		final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		final String vertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();

		settingsPage.selectCompany(usrtCompany);

		//Navigate and turn Vertex Parameter off
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(vertexTaxParameters);
		settingsPage.selectSettingsPage(taxGroupSettings);
		settingsPage.toggleRebateButton(false);
		rebateOn = false;
		settingsPage.navigateToDashboardPage();

		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Create sales order
		allSalesOrdersPage = settingsPage.clickOnNewButton();
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillItemsInfoForUSRT(itemNumber, quantity, 0);
		allSalesOrdersSecondPage.changeDeliveryMode("11");
		allSalesOrdersSecondPage.changeUnderDelivery("100");
		allSalesOrdersSecondPage.clickOnProductTab();
		allSalesOrdersSecondPage.clickAndEnterProductValue("Size", "7");
		allSalesOrdersSecondPage.clickAndEnterProductValue("Color", "Light Blue");
		allSalesOrdersSecondPage.clickAndEnterProductValue("Style", "Regular");

		///Validate sales tax amount
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		allSalesOrdersPage.clickOnCancel();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				calculatedSalesTaxAmount+" : "+assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				actualSalesTaxAmount+" : "+assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Navigate to XML inquiry and verify tax amount
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		DFinanceXMLInquiryPage xMLInquiryPage = new DFinanceXMLInquiryPage(driver);
		xMLInquiryPage.getDocumentID(salesOrderNumber);
		xMLInquiryPage.clickResponse();
		String response = xMLInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>33.6</TotalTax>"));
	}
}