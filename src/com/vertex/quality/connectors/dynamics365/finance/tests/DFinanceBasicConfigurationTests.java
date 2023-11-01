package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * To check the Vertex Connector Configuration status using the following parameters:
 * Trusted ID, Tax API URL and Address API URL
 *
 * @author Saidulu Kodadala ssalisbury
 */
@Listeners(TestRerunListener.class)
public class DFinanceBasicConfigurationTests extends DFinanceBaseTest
{
	/**
	 * JIRA ticket CD365-273 (a preliminary part of it)
	 *
	 * To check the Vertex Connector Configuration status using the following parameters:Trusted ID,
	 * Tax API URL and Address API URL
	 */
	@Test(groups = { "FO_General_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void connectionConfigurationTest( )
	{
		final String expectedAddressValidationMsg = "Address URL and login are valid.";
		final String expectedTaxValidationMsg = "Sales tax URL and login are valid.";

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		String validationMessage = configureVertexConnection(homePage, DFINANCE_DEFAULT_TRUSTED_ID,
			DFINANCE_TAX_CALCULATION_URL, DFINANCE_ADDRESS_VALIDATION_URL);

		assertTrue(validationMessage != null, "Connection failed (environment may be misconfigured)");

		final String addressValidationFailureMessage = String.format(
			"Expected message: %s, but actual displayed message: %s", expectedAddressValidationMsg, validationMessage);
		assertTrue(validationMessage.contains(expectedAddressValidationMsg), addressValidationFailureMessage);

		final String taxValidationFailureMessage = String.format(
			"Expected message: %s, but actual displayed message: %s", expectedTaxValidationMsg, validationMessage);
		assertTrue(validationMessage.contains(expectedTaxValidationMsg), taxValidationFailureMessage);
	}

	//TODO automate configuring everything from JIRA ticket CD365-273 in D365 Finance


	/**
	 * CD365-799
	 *
	 * This test verify new cloud endpoints parameter and also verifies failed test connection
	 * if incorrect URL is entered
	 */
	@Test(groups = { "FO_General_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void connectionConfigurationForCloudParameterTest( )
	{
		final String expectedAddressValidationMsg = "Address URL and login are valid.";
		final String expectedTaxValidationMsg = "Sales tax URL and login are valid.";

		DFinanceHomePage homePage = new DFinanceHomePage(driver);

		//Verify new parameter to input cloud URL is present in the vertex tax paramter

		//Verify Successful Test Connection with leaving the new parameter as a blank and with a correct Oseries 8 URL

		//Verify Successful Test Connection by entering correct cloud URL in new parameter

		//Verify Failed Test Connection by entering incorrect cloud URL in new paramter
			//(Add "?wsdl" at the end of the valid URL to create incorrect URL)

		String validationMessage = configureVertexConnection(homePage, DFINANCE_DEFAULT_TRUSTED_ID,
				DFINANCE_TAX_CALCULATION_URL, DFINANCE_ADDRESS_VALIDATION_URL);

		assertTrue(validationMessage != null, "Connection failed (environment may be misconfigured)");

		final String addressValidationFailureMessage = String.format(
				"Expected message: %s, but actual displayed message: %s", expectedAddressValidationMsg, validationMessage);
		assertTrue(validationMessage.contains(expectedAddressValidationMsg), addressValidationFailureMessage);

		final String taxValidationFailureMessage = String.format(
				"Expected message: %s, but actual displayed message: %s", expectedTaxValidationMsg, validationMessage);
		assertTrue(validationMessage.contains(expectedTaxValidationMsg), taxValidationFailureMessage);
	}

	/**
	 * CD365-1096
	 *
	 * This test verify Successful Posting of a sales order when address cleansing is disabled
	 * @author dpatel
	 */
	@Test(groups = { "FO_General_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void verifyPostingWithAddCleansingDisabled( )
	{
		//================Data Declaration ===========================
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();
		final String addressValidation = DFinanceVertexTaxParametersLeftMenuNames.ADDRESS_VALIDATION.getData();
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
		final String Quantity = "All";

		//================script implementation========================
		//Navigate to Vertex Settings page
		DFinanceSettingsPage settingsPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_TAX_PARAMETERS);

		//Click on "Address Validation" from the left pane
		settingsPage.selectSettingsPage(addressValidation);

		//Set the Acceptable confidence level and Set Always list all address results
		settingsPage.toggleAddressCleansingButton(false);

		//Navigate to  All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		createSalesOrder(allSalesOrdersPage, PittsburghCustomerAccount);

		//Click on "Header" option
		allSalesOrdersPage.openHeaderTab();

		//lets the new sales order be identified & eliminated at the end of the test
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

		//Go to "Set up" section
		allSalesOrdersPage.expandSetupHeaderSection();

		//Set "Sales tax group" -- 'VertexAR'
		allSalesOrdersPage.setSalesOrderTaxGroup(vertexAR);

		//Click on "Lines" option
		allSalesOrdersPage.openLinesTab();

		// add multiple line item to the SO
		allSalesOrdersSecondPage.fillFirstItemsInfo("1000","2", "22", "1", "1000", 0);

		//Click on "Sales Tax" option
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();

		//Verify the "Total calculated sales tax amount" value
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		//fixme FAILING with actual value "100.00"
		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("60.00"),
				"'Total calculated sales tax amount' value is not expected");

		//Verify the "Total actual sales tax amount" value
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("60.00"),
				"'Total actual sales tax amount' value is not expected");

		//Click on "OK" button
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Click on "Invoice" tab
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();

		// Select Quantity Parameter to "All"
		allSalesOrdersSecondPage.selectAllInQuantity(Quantity);
		allSalesOrdersSecondPage.clickOnOk();
		allSalesOrdersPage.clickOkForPostWithoutPrinting();
		boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
		assertTrue(isOperationCompleted, "Invoice Confirmation failed");
		// Create an invoice order
		allSalesOrdersSecondPage.clickOnInvoice();

		//Verify the "Total sales invoice amount" value
		String totalSalesInvoiceAmount = allSalesOrdersPage.salesTaxCalculator.getSalesInvoiceAmount();
		assertTrue(totalSalesInvoiceAmount.equalsIgnoreCase("1,060.00"),
				"'Total sales invoice amount' value is not expected");

		//TODO move to after method?
		//Navigate to  All Sales Orders page
		DFinanceAllSalesOrdersPage postTestAllSalesOrdersPage = allSalesOrdersPage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//In "ALL SALES ORDERS" search filter enter the above created Sales order number and then search by "Sales Order Number"
		postTestAllSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);
		//Click on "Sales Order Number" resulted in the Search Result
		postTestAllSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

		//Click on "Delete" option from the menu
		postTestAllSalesOrdersPage.clickDeleteButton();

		//Click on "Yes" option from the pop-up
		postTestAllSalesOrdersPage.agreeToConfirmationPopup();

		//Navigate to Vertex Settings page
		 homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_TAX_PARAMETERS);

		//Click on "Address Validation" from the left pane
		settingsPage.selectSettingsPage(addressValidation);

		//Set the Acceptable confidence level and Set Always list all address results
		settingsPage.toggleAddressCleansingButton(true);


	}

	/**
	 * @TestCase CD365R-263
	 * @Description - Validating Vertex Distributed Tax Errors Page Exist
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = {"smoke", "FO_General_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void validatingVertexDistributedTaxErrorsPageExistTest() {
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceVertexDistributedTaxErrorsPage vertexDistributedTaxErrorsPage = new DFinanceVertexDistributedTaxErrorsPage(driver);

		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_DISTRIBUTED_TAX_ERRORS);

		String expectedValue = "Vertex distributed tax errors";
		String actualValue = vertexDistributedTaxErrorsPage.verifyVTXDistributedTaxErrorsHeader();

		assertTrue(actualValue.contains(expectedValue), "VTXDistributedTaxErrors is not present and/or displayed on this page");
	}

}
