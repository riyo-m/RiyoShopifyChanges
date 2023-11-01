package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuNames;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * To check the D365 F&O Tax Flex Field look n feel and verify there is no error message
 * when different flex fields are selected and saved
 *
 * @author Sgupta
 */
@Listeners(TestRerunListener.class)
public class DFinanceFlexFieldsTests extends DFinanceBaseTest

{
	/**
	 * Select some other flex field and source table values and revert back to original
	 * CD365-259
	 */

	@Test(groups = { "FO_General_regression", "FO_ALL", "FO_smoke" }, retryAnalyzer = TestRerun.class)
	public void flexFieldTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceFlexFieldsPage flexFieldsPage = new DFinanceFlexFieldsPage(driver);
		String tax = DFinanceLeftMenuNames.TAX.getData();
		String setup = DFinanceLeftMenuNames.SETUP.getData();
		String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		String flexField = DFinanceLeftMenuNames.VERTEX_FLEX_FIELDS.getData();

		//Navigate to Vertex Settings page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		homePage.selectModuleTabLink(flexField);

		//Navigate to Vertex flex field page and select and save source table and source field value
		flexFieldsPage.selectSalesOrderSourceTable();
		flexFieldsPage.selectSourceField();
		flexFieldsPage.clickSaveButton();
	}

	/**
	 * Validate the created date and time flex field in the date field source field table
	 * CD365-543
	 */

	@Test(groups = { "FO_General_regression", "FO_ALL", "FO_smoke" }, retryAnalyzer = TestRerun.class)
	public void CreateDateTimeFlexFieldTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;
		DFinanceFlexFieldsPage flexFieldsPage = new DFinanceFlexFieldsPage(driver);
		String tax = DFinanceLeftMenuNames.TAX.getData();
		String setup = DFinanceLeftMenuNames.SETUP.getData();
		String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		String flexField = DFinanceLeftMenuNames.VERTEX_FLEX_FIELDS.getData();

		//Navigate to Vertex Settings page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(flexField);

		settingsPage.selectCompany("USRT");

		//Navigate to Vertex flex field page and Go to Date field & select create date & time flex field
		flexFieldsPage.expandDateFieldSection();
		flexFieldsPage.clickDateFields();
	}

	/**
	 * Validate all the flex field in the retail parameter page are displayed
	 * CD365-258 CD365-1478
	 */
	@Test(groups = { "FO_General_regression", "FO_ALL", "FO_smoke" }, retryAnalyzer = TestRerun.class)
	public void ValidateAllTheFlexFieldTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;
		DFinanceFlexFieldsPage flexFieldsPage = new DFinanceFlexFieldsPage(driver);
		String tax = DFinanceLeftMenuNames.TAX.getData();
		String setup = DFinanceLeftMenuNames.SETUP.getData();
		String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		String flexField = DFinanceLeftMenuNames.VERTEX_FLEX_FIELDS.getData();

		//Navigate to Vertex Settings page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(flexField);

		settingsPage.selectCompany("USRT");

		//Validate all the Retails Transactions Flex Fields are Loaded correctly
		flexFieldsPage.selectRetail();
		flexFieldsPage.validateStringFlexFields();
		flexFieldsPage.validateDateFlexFields();
		flexFieldsPage.validateNumericFlexFields();
	}

	/**
	 * @TestCase CD365-255/CD365-256
	 * @Description - Create Sales order, invoice and validate Flex Field Code, Date, and Numeric fields, update order and verify fields
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "RSAT_Coverage" }, retryAnalyzer = TestRerun.class)
	public void createSalesOrderInvoiceAndValidateFlexFieldsTest() {
		String itemNumber = "1000";
		String quantity = "1";
		String site = "2";
		String wareHouse = "22";
		String unitPrice = "1000";
		final double expectedTaxAmount = 320.00;

		String tax = DFinanceLeftMenuNames.TAX.getData();
		String setup = DFinanceLeftMenuNames.SETUP.getData();
		String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		String flexField = DFinanceLeftMenuNames.VERTEX_FLEX_FIELDS.getData();

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
		DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		DFinanceFlexFieldsPage flexFieldsPage = new DFinanceFlexFieldsPage(driver);

		settingsPage.selectCompany(usmfCompany);

		homePage.navigateToPage(DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Create new sales order
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();

		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, wareHouse, quantity, unitPrice, 0);
		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, wareHouse, quantity, unitPrice, 1);
		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, wareHouse, quantity, unitPrice, 2);
		allSalesOrdersSecondPage.fillItemsInfo("D0006", site, wareHouse, quantity, unitPrice, 3);

		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);

		//Validate tax amount
		allSalesOrdersPage.openSalesTaxCalculation();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Navigate to Vertex XML Inquiry and validate Flex Fields
		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<FlexibleFields>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"2\">Philadelphia, PA 19104\n" +
				"USA</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"3\">USA</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"5\">0</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"7\">004021</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"8\">2473 Hackworth Rd Apt 2\n" +
				"Birmingham, AL 3</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"9\">Birmingham</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"10\">AL</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"11\">35214-1909</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"13\">true</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"15\">false</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"16\">004021</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleNumericField fieldId=\"1\">4000</FlexibleNumericField>\n" +
				"\t\t\t\t<FlexibleNumericField fieldId=\"2\">0</FlexibleNumericField>\n" +
				"\t\t\t\t<FlexibleNumericField fieldId=\"3\">0</FlexibleNumericField>\n" +
				"\t\t\t\t<FlexibleDateField fieldId=\"1\">2021-09-10</FlexibleDateField>\n" +
				"\t\t\t\t<FlexibleDateField fieldId=\"2\">2021-05-27</FlexibleDateField>\n" +
				"\t\t\t</FlexibleFields>"));

		//Navigate to Vertex Flex Fields
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.collapseAll();
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(flexField);

		flexFieldsPage.editSourceFieldValue("SODate", 1);
		settingsPage.clickOnSaveButton();

		homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Update Sales Order
		allSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);
		allSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

		allSalesOrdersPage.removeLineItem("D0006");
		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, wareHouse, "5", unitPrice, 3);
		allSalesOrdersPage.clickOnSaveButton();
		allSalesOrdersPage.clickRefreshButton("2");

		//Navigate to "Pick and pack" Tab
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.pickandpackTabName);

		//Click "Generate picking list"
		allSalesOrdersPage.openPickingList();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();
		boolean isOperationCompletedPickingList = allSalesOrdersPage.messageBarConfirmation(operationCompleted);

		//Click "Post packing slip"
		allSalesOrdersPage.postPackingSlip("1");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();
		boolean isOperationCompletedPackingSlip = allSalesOrdersPage.messageBarConfirmation(operationCompleted);

		//Generate invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();
		boolean isOperationCompleted = allSalesOrdersPage.messageBarConfirmation(operationCompleted);
		assertTrue(isOperationCompleted, "Generating Invoice Failed");

		//Journal Invoice and verify Posted Sales Tax
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		String postedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		assertTrue(postedSalesTaxAmount.equalsIgnoreCase("640.00"),
				assertionTotalCalculatedSalesTaxAmount);

		//Navigate to Vertex XML Inquiry and validate Flex Fields
		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		String currentDate = getCurrentDateSlashFormat();
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstResponse();
		String response1 = xmlInquiryPage.getLogResponseValue();
		assertTrue(response1.contains("<FlexibleFields>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"2\">Philadelphia, PA 19104\n" +
				"USA</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"3\">USA</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"5\">0</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"7\">004021</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"8\">2473 Hackworth Rd Apt 2\n" +
				"Birmingham, AL 3</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"9\">Birmingham</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"10\">AL</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"11\">35214-1909</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"13\">true</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"15\">false</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"16\">004021</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleNumericField fieldId=\"1\">8000</FlexibleNumericField>\n" +
				"\t\t\t\t<FlexibleNumericField fieldId=\"2\">0</FlexibleNumericField>\n" +
				"\t\t\t\t<FlexibleNumericField fieldId=\"3\">0</FlexibleNumericField>\n" +
				"\t\t\t\t<FlexibleDateField fieldId=\"1\">2021-09-10</FlexibleDateField>\n" +
				"\t\t\t\t<FlexibleDateField fieldId=\"2\">2021-05-27</FlexibleDateField>\n" +
				"\t\t\t</FlexibleFields>"));
	}
}
