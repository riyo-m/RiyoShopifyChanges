package com.vertex.quality.connectors.dynamics365.business.tests;

import com.vertex.quality.connectors.dynamics365.business.enums.BusinessMainMenuNavTabs;
import com.vertex.quality.connectors.dynamics365.business.pages.*;
import com.vertex.quality.connectors.dynamics365.business.tests.base.BusinessBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * contains test cases for all the different sales quotes scenarios.
 *
 * @author cgajes, osabha, K Bhikshapathi
 */
@Listeners(TestRerunListener.class)
public class BusinessSalesQuotesTests extends BusinessBaseTest {
	BusinessAdminHomePage homePage;

	/**
	 * C365BC-524
	 * Tests creating a sales quote, converting it to an invoice, and then changing the
	 * item quantity on the invoice to test tax recalculation
	 * Posts invoice
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
	public void convertQuoteToInvoiceAndRecalculateTaxTest() {
		String expectedTaxRate = "6.00";
		String initialExpectedTaxAmount = "60.05";
		String adjustedExpectedTaxAmount = "120.10";
		String expectedQuoteNumber;
		String itemNumber = "1896-S";
		String quantity = "1";
		String customerCode = "Upgrade16 cust";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
		BusinessSalesQuotesPage newSalesQuotes = salesQuotes.pageNavMenu.clickNew();

		fillInSalesQuoteGeneralInfo(newSalesQuotes, customerCode);
		newSalesQuotes.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newSalesQuotes.exitExpandTable();
		expectedQuoteNumber = newSalesQuotes.getCurrentSalesNumber();

		BusinessSalesInvoicePage invoicePage = salesDocumentMakeInvoice(newSalesQuotes);

		openVertexTaxDetailsWindow(invoicePage);
		assertTrue(invoicePage.verifyVertexTaxDetailsField("Imposition"));

		String actualTaxRate = invoicePage.getTaxRate();
		String actualTaxAmount = invoicePage.getTaxAmount();

		assertEquals(initialExpectedTaxAmount, actualTaxAmount);
		assertEquals(expectedTaxRate, actualTaxRate);

		invoicePage.closeTaxDetailsDialog();

		invoicePage.expandTable();
		invoicePage.activateRow(1);
		invoicePage.enterQuantity("2", 0);
		invoicePage.exitExpandTable();

		openVertexTaxDetailsWindow(invoicePage);
		actualTaxRate = invoicePage.getTaxRate();
		actualTaxAmount = invoicePage.getTaxAmount();

		assertEquals(adjustedExpectedTaxAmount, actualTaxAmount);
		assertEquals(expectedTaxRate, actualTaxRate);

		invoicePage.closeTaxDetailsDialog();

		BusinessSalesInvoicePage postedInvoicePage = salesDocumentPostInvoice(invoicePage);
		String actualQuoteNumber = postedInvoicePage.getQuoteNumber();
		assertEquals(expectedQuoteNumber, actualQuoteNumber);

		openVertexTaxDetailsWindow(invoicePage);
		assertTrue(postedInvoicePage.verifyVertexTaxDetailsField("Imposition"));
		actualTaxRate = invoicePage.getTaxRate();
		actualTaxAmount = invoicePage.getTaxAmount();

		assertEquals(adjustedExpectedTaxAmount, actualTaxAmount);
		assertEquals(expectedTaxRate, actualTaxRate);
	}

	/**
	 * C365BC-523
	 * Creates a sales quote with only a comment (no items), recalculates the tax, ensures
	 * a "Nothing to recalculate" notice appears, converts quote to order and does the same thing,
	 * and checks the xml logs to make sure the recalculation
	 * does not appear for either document
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
	public void recalculateQuoteAndOrderCommentOnlyTest() {
		String expectedDialogMessage = "No tax to recalculate on current document";
		String customerName = "Cust_Test_2";
		String customerCode = "Upgrade16 cust";
		String expectedTotalTax = "0.00";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

		BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
		BusinessSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();

		fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);
		newSalesQuote.expandTable();
		fillInItemsTableComment(null, "Test Comment", 1);
		newSalesQuote.exitExpandTable();

		String salesQuoteNumber = newSalesQuote.getCurrentSalesNumber();
		System.out.println(salesQuoteNumber);

		recalculateTax(newSalesQuote);
		String actualDialogMessage = newSalesQuote.dialogBoxReadMessage();
		assertEquals(actualDialogMessage, expectedDialogMessage);
		newSalesQuote.dialogBoxClickOk();

		String actualTotalTax = newSalesQuote.getTotalTaxAmount();
		assertEquals(actualTotalTax, expectedTotalTax);

		BusinessSalesOrderPage convertedSalesOrder = newSalesQuote.convertQuoteToOrder();
		String salesOrderNumber = convertedSalesOrder.getCurrentSalesNumber();
		System.out.println(salesOrderNumber);

		recalculateTax(convertedSalesOrder);
		actualDialogMessage = convertedSalesOrder.dialogBoxReadMessage();
		assertEquals(actualDialogMessage, expectedDialogMessage);
		convertedSalesOrder.dialogBoxClickOk();

		actualTotalTax = convertedSalesOrder.getTotalTaxAmount();
		assertEquals(actualTotalTax, expectedTotalTax);

		convertedSalesOrder.deleteDocument();
		salesQuotes.refreshPage();
		salesQuotes.waitForLoadingScreen();

		BusinessVertexAdminPage adminPage = salesQuotes.searchForAndNavigateToVertexAdminPage();
		adminPage.openXmlLogCategory();

		boolean xmlPresent = adminPage.checkXmlTableForDocumentNumber(salesQuoteNumber);
		assertTrue(!xmlPresent);

		xmlPresent = adminPage.checkXmlTableForDocumentNumber(salesOrderNumber);
		//assertTrue(!xmlPresent);
	}

	/**
	 * CDBC-60
	 * Tests whether the print preview shows the correct tax amount
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
	public void salesQuotePrintPreviewTest() {
		String expectedTaxAmount = "60.05";
		String expectedTaxRate = "6.00";

		String customerCode = "Upgrade16 cust";
		String customerName = "Cust_Test_2";
		String itemNumber = "1896-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
		BusinessSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();

		fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);
		newSalesQuote.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newSalesQuote.exitExpandTable();

		newSalesQuote.salesEditNavMenu.clickPrintSendButton();
		newSalesQuote.salesEditNavMenu.selectPrint();
		BusinessPrintPreviewPage printPreview = newSalesQuote.clickPrintPreview();

		String allText = printPreview.getPdfText();
		assertTrue(allText.contains("Tax\n" + expectedTaxAmount));
	}

	/**
	 * CDBC-181
	 * Tests Statistics Functionality for Sales Quote TaxAmount
	 *
	 * @author P.Potdar
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
	public void compareSalesQuoteTaxAmountInStatisticsTest() {

		String customerCode = "test1234";
		String itemNumber = "1896-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
		BusinessSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();

		fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);
		newSalesQuote.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newSalesQuote.activateRow(2);
		newSalesQuote.exitExpandTable();

		//Navigate to Statistics and get Sales Quote TaxAmount
		homePage.mainNavMenu.goToSubNavTab1(
				BusinessMainMenuNavTabs.QUOTE.value, "Statistics");
		String actualTaxAmount1 = newSalesQuote.getStatisticsTaxAmount();
		newSalesQuote.closeTaxDetailsDialog();
		String actualTotalTaxAmount = newSalesQuote.getTotalTaxAmount();
		assertEquals(actualTotalTaxAmount, actualTaxAmount1);
	}

	/**
	 * CDBC-211
	 * Tests Statistics Functionality for Sales Quote TaxAmount for all PRODUCTS
	 *
	 * @author P.Potdar
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
	public void compareSalesQuoteTaxAmountAllProductsInStatisticsTest() {

		String customerCode = "test1234";
		String itemNumber = "1896-S";
		String quantity = "1";
		String nontaxableCode = "SUPPLIES";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
		BusinessSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();

		fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);
		newSalesQuote.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newSalesQuote.activateRow(2);
		fillInItemsTableInfo("G/L Account", "10100", null, null, quantity, "50", 2);
		newSalesQuote.activateRow(3);
		fillInItemsTableInfo("Resource", "MARY", null, null, quantity, null, 3);
		newSalesQuote.activateRow(4);
		fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, null, quantity, "50", 4);
		newSalesQuote.activateRow(5);
		fillInItemsTableInfo("Fixed Asset", "FA000110", null, null, quantity, "15", 5);
		newSalesQuote.activateRow(6);
		newSalesQuote.exitExpandTable();

		//Navigate to Statistics and get Sales Quote TaxAmount
		homePage.mainNavMenu.goToSubNavTab1(
				BusinessMainMenuNavTabs.QUOTE.value, "Statistics");
		String actualTaxAmount1 = newSalesQuote.getStatisticsTaxAmount();
		newSalesQuote.closeTaxDetailsDialog();
		String actualTotalTaxAmount = newSalesQuote.getTotalTaxAmount();
		assertEquals(actualTotalTaxAmount, actualTaxAmount1);
	}


	/**
	 * CDBC-182
	 * Tests Statistics Functionality for Sales Quote TaxAmount TAC is not VERTEX
	 *
	 * @author P.Potdar
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
	public void SalesQuoteTaxAmountInStatisticsNoVertexTest() {

		String customerCode = "cust_NOVERTEX";
		String itemNumber = "1896-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
		BusinessSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();

		fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);
		newSalesQuote.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newSalesQuote.exitExpandTable();

		//Navigate to Statistics and get Sales Quote TaxAmount
		homePage.mainNavMenu.goToSubNavTab1(
				BusinessMainMenuNavTabs.QUOTE.value, "Statistics");
		String actualTaxAmount1 = newSalesQuote.getStatisticsTaxAmount();
		newSalesQuote.closeTaxDetailsDialog();
		String actualTotalTaxAmount = newSalesQuote.getTotalTaxAmount();
		assertEquals(actualTotalTaxAmount, actualTaxAmount1);
	}

	/**
	 * CDBC-234
	 * Tests Statistics Functionality for Sales Quote TaxAmount TAC is not VERTEX for all line products
	 *
	 * @author P.Potdar
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
	public void SalesQuoteTaxAmountAllProductsInStatisticsNoVertexTest() {

		String customerCode = "cust_NOVERTEX";
		String itemNumber = "1896-S";
		String quantity = "1";
		String nontaxableCode = "FURNITURE";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
		BusinessSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();

		fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);
		newSalesQuote.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, "10", 1);
		newSalesQuote.activateRow(2);
		//fillInItemsTableInfo("G/L Account", "10100", null, quantity, null, nontaxableCode,1,true );
		fillInItemsTableInfo("G/L Account", "10100", null, null, quantity, "150", 2);
		newSalesQuote.activateRow(3);
		fillInItemsTableInfo("Resource", "MARY", null, null, quantity, "20", 3);
		newSalesQuote.activateRow(4);
		fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, null, quantity, "150", 4);
		newSalesQuote.activateRow(5);
		fillInItemsTableInfo("Fixed Asset", "FA000110", null, null, quantity, "25", 5);
		newSalesQuote.exitExpandTable();

		//Navigate to Statistics and get Sales Quote TaxAmount
		homePage.mainNavMenu.goToSubNavTab1(
				BusinessMainMenuNavTabs.QUOTE.value, "Statistics");
		String actualTaxAmount1 = newSalesQuote.getStatisticsTaxAmount();
		newSalesQuote.closeTaxDetailsDialog();
		String actualTotalTaxAmount = newSalesQuote.getTotalTaxAmount();
		assertEquals(actualTotalTaxAmount, actualTaxAmount1);
	}

	/**
	 * CDBC-272
	 * This Test validate Alert message when tax group code is missing or blank while accessing the statitics page
	 *
	 * @author D.Patel
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
	public void salesQuoteStatisticsPageMissingTaxGroupCodeAlertValidationTest() {

		String customerCode = "test1234";
		String itemNumber = "2000-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
		BusinessSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();

		fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);
		newSalesQuote.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newSalesQuote.exitExpandTable();

		String actualTotalTaxAmount = newSalesQuote.getTotalTaxAmount();
		String documentNo = newSalesQuote.getCurrentSalesNumber();

		//Navigate to Statistics and get Sales Quote TaxAmount
		homePage.mainNavMenu.goToSubNavTab1(
				BusinessMainMenuNavTabs.QUOTE.value, "Statistics");

		String actualAlertMessage = newSalesQuote.getAlertMessageForTaxGroupCode();
		String lineNo = "10000";

		String expectedAlertMessage = newSalesQuote.createExpectedAlertMessageStrings("Quote", documentNo, lineNo);
		assertEquals(actualAlertMessage, expectedAlertMessage, "Tax Group Statistics Alert Message Validation Failed");

	}

	/**
	 * CDBC-273
	 * This Test validate Alert message when tax group code is missing or blank while accessing the statitics page when TAC NOT= VERTEX
	 *
	 * @author P.Potdar
	 */
	@Ignore
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Deprecated"}, retryAnalyzer = TestRerun.class)
	public void salesQuoteStatisticsPageMissingTaxGroupCodeAlertValidationNoVertexTest() {

		String customerCode = "cust_NOVERTEX";
		String itemNumber = "1896-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
		BusinessSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();

		fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);
		newSalesQuote.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newSalesQuote.exitExpandTable();

		String actualTotalTaxAmount = newSalesQuote.getTotalTaxAmount();
		String documentNo = newSalesQuote.getCurrentSalesNumber();
		String lineNo = "10000";

		//Navigate to Statistics and get Sales Quote TaxAmount
		homePage.mainNavMenu.goToSubNavTab1(
				BusinessMainMenuNavTabs.QUOTE.value, "Statistics");

		String actualAlertMessage = newSalesQuote.getAlertMessageForTaxGroupCode();

		String expectedAlertMessage = newSalesQuote.createExpectedAlertMessageStrings("Quote", documentNo, lineNo);
		assertEquals(actualAlertMessage, expectedAlertMessage, "Tax Group Statistics Alert Message Validation Failed");
	}

	/**
	 * CDBC-657
	 * Create Sales Quote and change to Invoice
	 *
	 * @author bhikshapathi
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Smoke", "D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
	public void CreateSalesQuoteAndChangeToInvoiceTest() {
		String expectedTaxAmount = "60.05";
		String expectedTaxRate = "6.00";
		String customerCode = "Test Phila";
		String itemNumber = "1896-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
		BusinessSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();

		fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);
		newSalesQuote.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newSalesQuote.exitExpandTable();

		openVertexTaxDetailsWindow(newSalesQuote);
		String actualTaxRate = newSalesQuote.getTaxRate();
		String actualTaxAmount = newSalesQuote.getTaxAmount();

		assertEquals(expectedTaxAmount, actualTaxAmount);
		assertEquals(expectedTaxRate, actualTaxRate);

		newSalesQuote.closeTaxDetailsDialog();
		String postedQuoteNum = newSalesQuote.getCurrentSalesNumber();

		//Select light bulb and type vertex (this sends you to Vertex Admin)
		BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
		// Check XML
		adminPage.openXmlLogCategory();
		//Tax Calc Request and Response
		adminPage.filterDocuments(postedQuoteNum);
		adminPage.filterxml("Tax Calc Response");
		String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
		assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
		assertTrue(xmlStr.contains("<LineItem lineItemNumber=\"10000\" locationCode=\"WEST\" deliveryTerm=\"EXW\">"), "LineItem Validation Failed");
		assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
		adminPage.clickBackAndSaveArrow();

		// Create Invoice
		BusinessSalesInvoicePage postedInvoice = salesDocumentMakeInvoice(newSalesQuote);

		openVertexTaxDetailsWindowFromInvoice(postedInvoice);

		actualTaxRate = postedInvoice.getTaxRate();
		actualTaxAmount = postedInvoice.getTaxAmount();

		assertEquals(expectedTaxAmount, actualTaxAmount);
		assertEquals(actualTaxRate, expectedTaxRate);

		newSalesQuote.closeTaxDetailsDialog();

		String postedInvoiceOrderNum = postedInvoice.getCurrentSalesNumber();

		//Select light bulb and type vertex (this sends you to Vertex Admin)
		homePage.searchForAndNavigateToVertexAdminPage();
		// Check XML
		//Tax Calc Request and Response
		adminPage.filterDocuments(postedInvoiceOrderNum);
		adminPage.filterxml("Tax Calc Response");
		String invoiceXmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
		assertTrue(invoiceXmlStr.contains("<TotalTax>60.05</TotalTax>"), "Total Tax Validation Failed");
		assertTrue(invoiceXmlStr.contains("<LineItem lineItemNumber=\"10000\" locationCode=\"WEST\" deliveryTerm=\"EXW\">"), "LineItem Validation Failed");
		assertTrue(invoiceXmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
	}

	/**
	 * CDBC-763
	 * Validates quantity with decimal values in vertex tax details
	 *
	 * @author Shruti-Jituri
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
	public void validateQuantityTest() {
		String customerCode = "test1234";
		String itemNumber = "1896-S";
		String quantity = "1";
		List<String> expectedQuantities = Arrays.asList("1.232", "1.22","1.2324","1");
		String expectedTotalTax="91.04";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
		BusinessSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();

		fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);
		newSalesQuote.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, "1.232", null, 1);
		newSalesQuote.activateRow(2);
		fillInItemsTableInfo("G/L Account", "10200", null, null, "1.22", "100", 2);
		newSalesQuote.activateRow(3);
		fillInItemsTableInfo("Resource", "MARY", null, null, "1.2324", "10", 3);
		newSalesQuote.activateRow(4);
		fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, null, quantity, "150", 4);
		newSalesQuote.activateRow(5);
		newSalesQuote.exitExpandTable();
		openVertexTaxDetailsWindow(newSalesQuote);
		List<String> actualQuantities=newSalesQuote.getMultipleQuantities();
		assertEquals(expectedQuantities,actualQuantities);
		newSalesQuote.closeTaxDetailsDialog();
		String actualTotalTax = newSalesQuote.getTotalTaxAmount();
		assertEquals(actualTotalTax, expectedTotalTax);
		String quoteNumber = newSalesQuote.getCurrentSalesNumber();
		System.out.print(quoteNumber);
		BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
		// Check XML
		adminPage.openXmlLogCategory();
		adminPage.filterDocuments(quoteNumber);
		adminPage.filterxml("Tax Calc Request");
		String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
		assertTrue(xmlStr.contains("<Quantity>1.232</Quantity>"), "Quantity for Line item 1 Validation Failed");
		assertTrue(xmlStr.contains("<Quantity>1.22</Quantity>"), "Quantity for Line item 2 Validation Failed");
		assertTrue(xmlStr.contains("<Quantity>1.2324</Quantity>"), "Quantity for Line item 3 Validation Failed");
		assertTrue(xmlStr.contains("<Quantity>1</Quantity>"), "Quantity for Line item 4 Validation Failed");
	}


	/**
	 * CDBC-985
	 * Verify if vertex calls are not made after disabling the AR button on admin page
	 * @author Shruti
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Special"}, retryAnalyzer = TestRerun.class)
	public void verifyVertexCallsOnSalesQuoteTest() {
		String customerCode = "Test Phila";
		String itemNumber = "1896-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();

		//Update 'Account Receivable Enabled' button to off
		adminPage.updateAccountReceivableToOff();
		adminPage.clickBackAndSaveArrow();

		//Create Sales Quote and verify if vertex call is not made
		BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
		BusinessSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();
		fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);
		newSalesQuote.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newSalesQuote.exitExpandTable();

		openVertexTaxDetailsWindow(newSalesQuote);
		String popUp = newSalesQuote.noTaxDetailsDialog();
		assertTrue(popUp.contains("No tax details on current document"), "Tax details Validation Failed");

		//Navigate to admin page and enable the account Receivable toggle button
		homePage.searchForAndNavigateToVertexAdminPage();
		adminPage.updateAccountReceivableToOn();
		adminPage.clickBackAndSaveArrow();
	}

	/**
	 * @TestCase CDBC-1403
	 * @Description - Verifying Shipment Method Code For US To Canada For Sales Quote
	 * @Author Mario Saint-Fleur
	 * */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
	public void verifyShipmentMethodCodeForUSToCanadaForSalesQuote() {
		String customerCode = "TestingService_PA";
		String itemNumber = "1896-S";
		String quantity = "1";
		String expectedTax = "60.05";
		String expectedTaxAfterUpdate = "50.04";
		String shipmentMethodCode = "SUP";

		//Navigate to sales order and create document
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
		BusinessSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();

		fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);
		newSalesQuote.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newSalesQuote.exitExpandTable();

		String orderNum = newSalesQuote.getCurrentSalesNumber();
		String actualTaxAmount = newSalesQuote.getTotalTaxAmount();
		assertEquals(expectedTax, actualTaxAmount);

		//Change Shipment address to Canada and Shipment Code to SUP
		newSalesQuote.openShippingAndBillingCategory();
		newSalesQuote.selectCustomShipToAddress();
		newSalesQuote.clickShowMore();
		fillInCustomAddressInShippingAndBilling("17 A Avenue","Edmonton","Alberta","T5J 0K7", "CA");
		newSalesQuote.selectShipmentMethodCode(shipmentMethodCode);

		//Recalculate taxes, verify taxes, open Vertex tax details, and verify imposition
		recalculateTax(newSalesQuote);
		String updatedTotalTaxOnServiceLines = newSalesQuote.getTotalTaxAmount();
		assertEquals(expectedTaxAfterUpdate, updatedTotalTaxOnServiceLines);
		openVertexTaxDetailsWindow(newSalesQuote);
		String actualImpositionValue = newSalesQuote.getImpositionValue();
		String expectedImpositionValue = "GST/HST";
		assertEquals(expectedImpositionValue, actualImpositionValue);
		newSalesQuote.closeTaxDetailsDialog();

		//Navigate to XML and validate presence of delivery term
		BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
		adminPage.openXmlLogCategory();
		adminPage.filterDocuments(orderNum);
		adminPage.filterxml("Tax Calc Request");
		String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
		assertTrue(xmlStr.contains("deliveryTerm=\"SUP\""), "Total tax validation failed");
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
