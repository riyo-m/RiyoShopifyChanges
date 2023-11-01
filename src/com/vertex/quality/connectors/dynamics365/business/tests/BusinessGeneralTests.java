package com.vertex.quality.connectors.dynamics365.business.tests;

import com.vertex.quality.connectors.dynamics365.business.enums.BusinessMainMenuNavTabs;
import com.vertex.quality.connectors.dynamics365.business.pages.*;
import com.vertex.quality.connectors.dynamics365.business.tests.base.BusinessBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.vertex.quality.common.utils.properties.CommonDataProperties.EMAIL;
import static org.testng.Assert.assertEquals;

/**
 * For basic/general tests that don't necessarily fit into a specific or single category
 *
 * @author cgajes, K.Bhikshapathi
 */
@Listeners(TestRerunListener.class)
public class BusinessGeneralTests extends BusinessBaseTest
{	BusinessAdminHomePage homePage;
	String emailAddress = EMAIL;
	String expectedMessageEmailSuccess = String.format("Email sent to %s", emailAddress);
	boolean classCreated;
	String customerClassCode = "TEST_AUTOM";

	@BeforeMethod(alwaysRun = true)
	public void setupTest( )
	{
		classCreated = false;
	}

	@AfterMethod(alwaysRun = true)
	public void teardownTest( )
	{
		if (classCreated) {
			BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
			homePage.refreshAndWaitForLoad();
			BusinessVertexCustomerClassPage customerClassPage = homePage.searchForAndNavigateToVertexCustomerClassPage();
			customerClassPage.getRowByCode(customerClassCode);
			customerClassPage.clickAndConfirmDeleteButton();
		}
	}

	/**
	 * CDBC-1014
	 * Test logging in to the application
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Smoke" }, retryAnalyzer = TestRerun.class)
	public void loginTest( )
	{
		String expectedHeader = "Business Manager - Dynamics 365 Business Central";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		homePage.waitForLoadingScreen();

		String actualHeader = homePage.verifyHomepageHeader();

		assertEquals(expectedHeader, actualHeader);
	}

	/**
	 * Creates a new customer class and creates a new customer using that class
	 */
	@Test(alwaysRun = true, groups = { "Deprecated" }, retryAnalyzer = TestRerun.class)
	public void createCustomerClassAndCreateCustomerWithNewClassTest( )
	{
		String customerName = "Teresa Duarte";

		String customerClassName = "Create New Customer Class Test";
		String addressLineOne = "2955 Market St";
		String city = "Philadelphia";
		String state = "PA";
		String longZip = "19104-2828";
		String country = "US";
		String taxAreaCode = "VERTEX";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessCustomersListPage customersListPage = homePage.navigateToCustomersListPage();
		homePage.refreshAndWaitForLoad();
		BusinessVertexCustomerClassPage customerClassPage = homePage.searchForAndNavigateToVertexCustomerClassPage();

		customerClassPage.clickNewClassButton();
		customerClassPage.enterClassCode(1, customerClassCode);
		customerClassPage.enterClassName(1, customerClassName);
		customerClassPage.clickBackAndSaveArrow();
		classCreated = true;

		customersListPage.refreshAndWaitForLoad();
		customersListPage.switchFrame();
		homePage.navigateToCustomersListPage();
		BusinessCustomerCardPage customerCardPage = customersListPage.clickNewBusinessToBusinessCustomer();

		customerCardPage.enterCustomerName(customerName);
		customerCardPage.enterVertexCustomerCode(customerClassCode);
		fillInCustomerAddressInfo(addressLineOne, null, city, state, longZip, country);
		customerCardPage.expandInvoicingSection();
		customerCardPage.enterTaxAreaCode(taxAreaCode);

		customerCardPage.toggleReadOnlyMode();

		customerCardPage.clickBackAndSaveArrow();

		customersListPage.pageNavMenu.searchForDocument(customerName);
		customerCardPage = customersListPage.openCustomerByRowIndex(1);
		customerCardPage.deleteDocument();
		customersListPage.refreshAndWaitForLoad();
	}

	/**
	 * CDBC-1015
	 * Tests running Vertex diagnostics against an existing customer
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Smoke" }, retryAnalyzer = TestRerun.class)
	public void vertexDiagnosticAccountCustomerTest( )
	{	String expectedMessageEmailFail = "Email was unable to send. Would you like to download files instead?";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		homePage.waitForPageLoad();
		BusinessCustomersListPage customers = homePage.navigateToCustomersListPage();

		String accountNumber = customers.getCustomerNumberByRowIndex();

		customers.refreshAndWaitForLoad();
		BusinessVertexDiagnosticsPage diagnosticsPage = customers.searchForAndNavigateToVertexDiagnosticsPage();

		diagnosticsPage.selectType("Account (Customer)");
		diagnosticsPage.inputAccountOrDocumentNumber(accountNumber);
		diagnosticsPage.inputEmailAddress(emailAddress);

		diagnosticsPage.runDiagnostic();

		String actualMessage = diagnosticsPage.getDialogBoxText();

		assertEquals(actualMessage, expectedMessageEmailSuccess);
	}

	/**
	 * CDBC-1016
	 * Tests running Vertex diagnostics against an existing sales quote
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void vertexDiagnosticSalesQuoteTest( )
	{	BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

		BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");

		String quoteNumber = salesQuotes.getQuoteNumberByRowIndex(1);

		salesQuotes.refreshAndWaitForLoad();
		BusinessVertexDiagnosticsPage diagnosticsPage = salesQuotes.searchForAndNavigateToVertexDiagnosticsPage();

		diagnosticsPage.selectType("Sales Quote");
		diagnosticsPage.inputAccountOrDocumentNumber(quoteNumber);
		diagnosticsPage.inputEmailAddress(emailAddress);

		diagnosticsPage.runDiagnostic();

		String actualMessage = diagnosticsPage.getDialogBoxText();

		assertEquals(actualMessage, expectedMessageEmailSuccess);
	}

	/**
	 * CDBC-1017
	 * Tests running Vertex diagnostics against an existing sales order
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void vertexDiagnosticSalesOrderTest( )
	{	BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

		BusinessSalesOrderListPage salesOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
			"Sales Orders");

		String orderNumber = salesOrders.getOrderNumberByRowIndex(1);

		salesOrders.refreshAndWaitForLoad();
		BusinessVertexDiagnosticsPage diagnosticsPage = salesOrders.searchForAndNavigateToVertexDiagnosticsPage();

		diagnosticsPage.selectType("Sales Order");
		diagnosticsPage.inputAccountOrDocumentNumber(orderNumber);
		diagnosticsPage.inputEmailAddress(emailAddress);

		diagnosticsPage.runDiagnostic();

		String actualMessage = diagnosticsPage.getDialogBoxText();

		assertEquals(actualMessage, expectedMessageEmailSuccess);
	}

	/**
	 * CDBC-1018
	 * Tests running Vertex diagnostics against an existing sales invoice
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void vertexDiagnosticSalesInvoiceTest( )
	{	BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");

		String invoiceNumber = salesInvoices.getInvoiceNumberByRowIndex(0);

		salesInvoices.refreshAndWaitForLoad();
		BusinessVertexDiagnosticsPage diagnosticsPage = salesInvoices.searchForAndNavigateToVertexDiagnosticsPage();

		diagnosticsPage.selectType("Sales Invoice");
		diagnosticsPage.inputAccountOrDocumentNumber(invoiceNumber);
		diagnosticsPage.inputEmailAddress(emailAddress);

		diagnosticsPage.runDiagnostic();

		String actualMessage = diagnosticsPage.getDialogBoxText();

		assertEquals(actualMessage, expectedMessageEmailSuccess);
	}

	/**
	 * CDBC-1019
	 * Tests running a Vertex diagnostic on a sales order with an incorrect number,
	 * leading to an error
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression"}, retryAnalyzer = TestRerun.class)
	public void vertexDiagnosticInvalidNumberTest( )
	{   String incorrectDocumentNumber = "12345";
		String expectedErrorMessage = String.format("No Sales Order was found with ID: %s", incorrectDocumentNumber);

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

		BusinessCustomersListPage customers = homePage.navigateToCustomersListPage();
		customers.refreshAndWaitForLoad();
		BusinessVertexDiagnosticsPage diagnosticsPage = homePage.searchForAndNavigateToVertexDiagnosticsPage();

		diagnosticsPage.selectType("Sales Order");
		diagnosticsPage.inputAccountOrDocumentNumber(incorrectDocumentNumber);
		diagnosticsPage.inputEmailAddress(emailAddress);

		diagnosticsPage.runDiagnostic();
		String actualErrorMessage = diagnosticsPage.getDialogBoxText();

		assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	@BeforeMethod(alwaysRun = true)
	public void setUpBusinessMgmt(){
		String role="Business Manager";
		homePage = new BusinessAdminHomePage(driver);
		String verifyPage=homePage.verifyHomepageHeader();
		if(!verifyPage.contains(role)){

			//navigate to select role as Business Manager
			homePage.selectSettings();
			homePage.navigateToManagerInSettings(role);
		}
	}
}
