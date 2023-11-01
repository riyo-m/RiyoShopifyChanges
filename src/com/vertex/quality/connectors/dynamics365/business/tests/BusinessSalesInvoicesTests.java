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
@Listeners(TestRerunListener.class)
public class BusinessSalesInvoicesTests extends BusinessBaseTest
{
	BusinessAdminHomePage homePage;

	/**
	 * CDBC-63
	 * Tests that when using the same document number twice across document types,
	 * total tax remains consistent when posting the invoice of that number
	 */
	@Ignore
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Deprecated" }, retryAnalyzer = TestRerun.class)
	public void sameNumberTwoDocumentTypesTest( )
	{
		String customerCode = "Test Vertex Customer";
		String customerName = "Cust_Test";
		String itemNumber = "1896-S";
		String quantity = "2";
		String expectedTotalTax = "100.08";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

		BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
		BusinessSalesQuotesPage newQuote = salesQuotes.pageNavMenu.clickNew();

		fillInSalesQuoteGeneralInfo(newQuote, customerCode);
		newQuote.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newQuote.exitExpandTable();

		String documentNumber = newQuote.getCurrentSalesNumber();
		String actualTotalTax = newQuote.getTotalTaxAmount();
		assertEquals(actualTotalTax, expectedTotalTax);

		newQuote.clickBackAndSaveArrow();

		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

		newInvoice.enterDocumentNumber(documentNumber);
		fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		openVertexTaxDetailsWindow(newInvoice);
		newInvoice.closeTaxDetailsDialog();
		actualTotalTax = newInvoice.getTotalTaxAmount();
		assertEquals(actualTotalTax, expectedTotalTax);

		BusinessSalesInvoicePage postedInvoice = salesDocumentPostInvoice(newInvoice);
		actualTotalTax = postedInvoice.getTotalTaxAmount();
		assertEquals(actualTotalTax, expectedTotalTax);
	}

	/**
	 * CDBC-64
	 * Tests that when using the same document number three times across document types,
	 * total tax remains consistent when posting the invoice of that number
	 */
	@Ignore
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Deprecated" }, retryAnalyzer = TestRerun.class)
	public void sameNumberThreeDocumentTypesTest( )
	{
		String customerCode = "Test Vertex Customer";
		String customerName = "Cust_Test";
		String itemNumber = "1896-S";
		String quantity = "2";
		String expectedTotalTax = "100.08";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

		BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
		BusinessSalesQuotesPage newQuote = salesQuotes.pageNavMenu.clickNew();

		fillInSalesQuoteGeneralInfo(newQuote, customerCode);
		newQuote.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newQuote.exitExpandTable();
		openVertexTaxDetailsWindow(newQuote);
		newQuote.closeTaxDetailsDialog();
		String documentNumber = newQuote.getCurrentSalesNumber();
		String actualTotalTax = newQuote.getTotalTaxAmount();
		assertEquals(actualTotalTax, expectedTotalTax);

		newQuote.clickBackAndSaveArrow();

		BusinessSalesOrderListPage salesOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
			"Sales Orders");
		BusinessSalesOrderPage newOrder = salesOrders.pageNavMenu.clickNew();

		fillInSalesOrderGeneralInfo(newOrder, customerCode);
		newOrder.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newOrder.exitExpandTable();
		openVertexTaxDetailsWindow(newOrder);
		newOrder.closeTaxDetailsDialog();
		actualTotalTax = newOrder.getTotalTaxAmount();
		assertEquals(actualTotalTax, expectedTotalTax);

		newOrder.clickBackArrow();

		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

		fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
		newOrder.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newInvoice.exitExpandTable();
		openVertexTaxDetailsWindow(newInvoice);
		newInvoice.closeTaxDetailsDialog();
		actualTotalTax = newInvoice.getTotalTaxAmount();
		assertEquals(actualTotalTax, expectedTotalTax);

		BusinessSalesInvoicePage postedInvoice = salesDocumentPostInvoice(newInvoice);
		actualTotalTax = postedInvoice.getTotalTaxAmount();
		assertEquals(actualTotalTax, expectedTotalTax);
	}

	/**
	 * CDBC-68
	 * Creates a sales invoice with only a comment (no items), recalculates the tax, ensures
	 * a "Nothing to recalculate" notice appears,
	 * and checks the xml logs to make sure the recalculation does not appear
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
	public void recalculateInvoiceCommentOnlyTest( )
	{
		String expectedDialogMessage = "No tax to recalculate on current document";
		String customerName = "Cust_Test_2";
		String customerCode = "Upgrade16 cust";
		String expectedTotalTax = "0.00";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

		fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
		newInvoice.expandTable();
		fillInItemsTableComment(null, "Test Comment", 1);
		newInvoice.exitExpandTable();
		String invoiceNumber = newInvoice.getCurrentSalesNumber();

		recalculateTax(newInvoice);
		String actualDialogMessage = newInvoice.dialogBoxReadMessage();
		assertEquals(actualDialogMessage, expectedDialogMessage);
		newInvoice.dialogBoxClickOk();

		String actualTotalTax = newInvoice.getTotalTaxAmount();
		assertEquals(actualTotalTax, expectedTotalTax);

		newInvoice.deleteDocument();
		salesInvoices.refreshPage();
		salesInvoices.waitForLoadingScreen();

		BusinessVertexAdminPage adminPage = salesInvoices.searchForAndNavigateToVertexAdminPage();
		adminPage.openXmlLogCategory();

		boolean xmlPresent = adminPage.checkXmlTableForDocumentNumber(invoiceNumber);
		assertTrue(!xmlPresent);
	}

	/**
	 * CDBC-58
	 * Tests whether the print preview for an invoice shows the correct tax amount
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
	public void invoicePrintPreviewTest( )
	{
		String expectedTaxAmount = "60.05";
		String customerCode = "Upgrade16 cust";
		String itemNumber = "1896-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

		newInvoice.enterCustomerCode(customerCode);
		newInvoice.setPostingDate();
		newInvoice.setDueDate();
		newInvoice.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newInvoice.exitExpandTable();
		newInvoice.salesEditNavMenu.clickMoreOptions();
		newInvoice.salesEditNavMenu.clickMoreOptionsActionsButton();
		newInvoice.salesEditNavMenu.selectActionsPostingButton();
		newInvoice.salesEditNavMenu.selectOtherPostingButton();
		newInvoice.salesEditNavMenu.selectDraftInvoiceButton();

		BusinessPrintPreviewPage printPreview = newInvoice.clickPrintPreview();

		String allText = printPreview.getPdfText();

		assertTrue(allText.contains("Total Tax\n" + expectedTaxAmount));
	}

	/**
	 * CDBC-59
	 * Tests whether the print preview for a posted invoice shows the correct tax amount
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
	public void postedInvoicePrintPreviewTest( )
	{
		String expectedTaxAmount = "60.05";
		String expectedTaxRate = "6.00";

		String customerCode = "Upgrade16 cust";
		String customerName = "Cust_Test_2";
		String itemNumber = "1896-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

		newInvoice.enterCustomerCode(customerCode);
		newInvoice.setPostingDate();
		newInvoice.setDueDate();
		newInvoice.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newInvoice.exitExpandTable();
		BusinessSalesInvoicePage postedInvoice = salesDocumentPostInvoice(newInvoice);

		postedInvoice.salesEditNavMenu.clickPrintSendButton();
		postedInvoice.salesEditNavMenu.selectPrint();
		BusinessPrintPreviewPage printPreview = postedInvoice.clickPrintPreview();

		String allText = printPreview.getPdfText();
		assertTrue(allText.contains("Total Tax\n" + expectedTaxAmount));
	}

	/**
	 * Tests creating a sales order with a customer with an exempt class and posting it to an invoice,
	 * ensuring the tax is properly shown as 0 (exempt)
	 *
	 * C365BC-104/C365BC-527
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
	public void exemptCustomerClassTest( )
	{
		String expectedTaxAmount = "0.00";
		String customerCode = "NoTaxpayer";
		String itemLineOneNumber = "1896-S";
		String itemLineTwoNumber = "1906-S";
		String quantityOne = "1";
		String quantityFive = "5";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesOrderListPage salesOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
			"Sales Orders");
		BusinessSalesOrderPage newSalesOrder = salesOrders.pageNavMenu.clickNew();

		fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
		newSalesOrder.expandTable();
		fillInItemsTableInfo("Item", itemLineOneNumber, null, null, quantityOne, null, 1);
		newSalesOrder.activateRow(2);
		fillInItemsTableInfo("Item", itemLineTwoNumber, null, null, quantityFive, null, 2);
		newSalesOrder.exitExpandTable();
		String actualTaxAmount = newSalesOrder.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedTaxAmount);

		BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);

		actualTaxAmount = postedInvoice.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedTaxAmount);
	}

	/**
	 * CDBC-212
	 * Tests Statistics Functionality for Sales Invoice TaxAmount
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
	public void compareSalesInvoiceTaxAmountInStatisticsTest(){
		String customerCode = "test1234";
		String itemNumber = "1896-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

		fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
		newInvoice.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newInvoice.activateRow(2);
		newInvoice.exitExpandTable();

		String actualTotalTaxAmount = newInvoice.getTotalTaxAmount();

		//Navigate to Statistics and get Sales Invoice TaxAmount
		homePage.mainNavMenu.goToSubNavTab1(
				BusinessMainMenuNavTabs.INVOICE.value, "Statistics");

		String actualTaxAmount1 = newInvoice.getStatisticsTaxAmount();
		assertEquals(actualTotalTaxAmount,actualTaxAmount1);
	}
	/**
	 * CDBC-213
	 * Tests Statistics Functionality for Sales Invoice TaxAmount for all applicable product types and the different tax group codes
	 * @author P.Potdar
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
	public void compareSalesInvoiceTaxAmountAllProductsInStatisticsTest(){

		String customerCode = "test1234";
		String itemNumber = "1896-S";
		String quantity = "1";
		String nontaxableCode = "SUPPLIES";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

		fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
		newInvoice.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newInvoice.activateRow(2);
		fillInItemsTableInfo("Resource", "MARY", null, null, quantity, null, 2);
		newInvoice.activateRow(3);
		//fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, quantity, "100",null, 4);
		//newInvoice.activateRow(5); // Will need it in future
		fillInItemsTableInfo("Fixed Asset", "FA000110", null, null, quantity, "150", 3);
		newInvoice.activateRow(4);
		fillInItemsTableInfo("G/L Account", "10100", null, null, quantity, "50", 4);
		newInvoice.exitExpandTable();

		//Navigate to Statistics and get Sales Invoice TaxAmount
		homePage.mainNavMenu.goToSubNavTab1(
				BusinessMainMenuNavTabs.INVOICE.value, "Statistics");

		String actualTaxAmount1 = newInvoice.getStatisticsTaxAmount();
		newInvoice.closeTaxDetailsDialog();
		String actualTotalTaxAmount = newInvoice.getTotalTaxAmount();
		assertEquals(actualTotalTaxAmount,actualTaxAmount1);
	}
	/**
	 * CDBC-235
	 * Tests Statistics Functionality for Sales Invoice TaxAmount TAC is not VERTEX for all line products
	 * @author P.Potdar
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
	public void SalesInvoiceTaxAmountAllProductsInStatisticsNoVertexTest(){

		String customerCode = "cust_NOVERTEX";
		String itemNumber = "1896-S";
		String quantity = "1";
		String nontaxableCode = "SUPPLIES";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

		fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
		newInvoice.expandTable();
		fillInItemsTableInfo("Item", itemNumber,null, null, quantity,null, 1);
		newInvoice.activateRow(2);
		fillInItemsTableInfo("Resource", "MARY", null, null, quantity, "10", 2);
		newInvoice.activateRow(3);
		fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, null, quantity, "150", 3);
		newInvoice.activateRow(4);
		fillInItemsTableInfo("Fixed Asset", "FA000110", null, null, quantity, "25", 4);
		newInvoice.activateRow(5);
		fillInItemsTableInfo("G/L Account", "10100", null, null, quantity, "150", 5);

		newInvoice.exitExpandTable();

		//Navigate to Statistics and get Sales Quote TaxAmount
		homePage.mainNavMenu.goToSubNavTab1(
				BusinessMainMenuNavTabs.INVOICE.value, "Statistics");
		String postedTaxAmount = newInvoice.getStatisticsTaxAmount();
		newInvoice.closeTaxDetailsDialog();
		String statTotalTaxAmount = newInvoice.getTotalTaxAmount();
		assertEquals(statTotalTaxAmount, postedTaxAmount);
	}
	/**
	 * CDBC-1111
	 * Tests Statistics Functionality for Sales Invoice TaxAmount for all applicable product types and the different tax group codes
	 * and POST invoice for same
	 * @author P.Potdar
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
	public void comparePostedSalesInvoiceTaxAmountAllProductsInStatisticsTest(){

		String customerCode = "test1234";
		String itemNumber = "1896-S";
		String quantity = "1";
		String nontaxableCode = "SUPPLIES";
		String expectedTaxAmount = "72.05";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

		fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
		newInvoice.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newInvoice.activateRow(2);
		fillInItemsTableInfo("Resource", "MARY", null, null, quantity, null, 2);
		newInvoice.activateRow(3);
		//fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, quantity, "100",null, 4);
		//newInvoice.activateRow(5);
		fillInItemsTableInfo("Fixed Asset", "FA000120", null, null, quantity, "150", 3);
		newInvoice.activateRow(4);
		fillInItemsTableInfo("G/L Account", "10100", null, null, quantity, "50", 4);
		newInvoice.activateRow(5);
		newInvoice.exitExpandTable();

		// Post invoice
		BusinessSalesInvoicePage postedInvoice = salesDocumentPostInvoice(newInvoice);
		//Navigate to Statistics and get Posted Sales Invoice TaxAmount
		homePage.mainNavMenu.goToChildSubNavTab1(
				BusinessMainMenuNavTabs.POSTED_SALES_INVOICE.value, BusinessMainMenuNavTabs.INVOICE.value,"Statistics");
		String postedTaxAmount = postedInvoice.getStatisticsTaxAmount();
		postedInvoice.closeTaxDetailsDialog();
		String statTotalTaxAmount = postedInvoice.getTotalTaxAmount();
		assertEquals(statTotalTaxAmount, postedTaxAmount);
		assertEquals(expectedTaxAmount, statTotalTaxAmount);
	}
	/**
	 * CDBC-683
	 * Create Invoice only
	 *@author bhikshapathi
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Smoke","D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
	public void createInvoiceOnlyTest() {
		String expectedTaxAmount = "60.05";
		String expectedTaxRate = "6.00";
		String customerCode = "Test Phila";
		String itemNumber = "1896-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

		fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
		newInvoice.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newInvoice.activateRow(2);
		newInvoice.exitExpandTable();

		openVertexTaxDetailsWindow(newInvoice);
		assertTrue(newInvoice.verifyVertexTaxDetailsField("Imposition"));
		String actualTaxRate = newInvoice.getTaxRate();
		String actualTaxAmount = newInvoice.getTaxAmount();

		assertEquals(expectedTaxAmount, actualTaxAmount);
		assertEquals(expectedTaxRate, actualTaxRate);

		newInvoice.closeTaxDetailsDialog();
		String postedOrderNum=newInvoice.getCurrentSalesNumber();

		//Select light bulb and type vertex (this sends you to Vertex Admin)
		BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
		// Check XML
		adminPage.openXmlLogCategory();
		//Tax Calc Request and Response
		adminPage.filterDocuments(postedOrderNum);
		adminPage.filterxml("Tax Calc Response");
		String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
		assertTrue(xmlStr.contains("<Customer>\n" +
				"            <CustomerCode>C00470</CustomerCode>\n" +
				"            <Destination taxAreaId=\"391013000\">\n" +
				"              <StreetAddress1>2955 Market St Ste 2</StreetAddress1>\n" +
				"              <City>Philadelphia</City>\n" +
				"              <MainDivision>PA</MainDivision>\n" +
				"              <PostalCode>19104-2817</PostalCode>\n" +
				"              <Country>US</Country>\n" +
				"            </Destination>\n" +
				"          </Customer>"), "Address Validation Failed");
		assertTrue(xmlStr.contains("<LineItem lineItemNumber=\"10000\" locationCode=\"WEST\" deliveryTerm=\"EXW\">"), "LineItem Validation Failed");
		assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
	}

	/**
	 * CDBC-276
	 * This Test validate Alert message for Sales invoice  when tax group code is missing or blank while accessing the statitics page TAC= VERTEX
	 * @author P.Potdar
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
	public void salesInvoiceStatisticsPageMissingTaxGroupCodeAlertValidationTest(){
		String customerCode = "test1234";
		String itemNumber = "2000-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

		fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
		newInvoice.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newInvoice.activateRow(2);
		newInvoice.exitExpandTable();

		String actualTotalTaxAmount = newInvoice.getTotalTaxAmount();
		String documentNo = newInvoice.getCurrentSalesNumber();

		//Navigate to Statistics and get Sales Quote TaxAmount
		homePage.mainNavMenu.goToSubNavTab1(
				BusinessMainMenuNavTabs.INVOICE.value, "Statistics");

		String actualAlertMessage = newInvoice.getAlertMessageForTaxGroupCode();
		String lineNo = "10000";

		String expectedAlertMessage = newInvoice.createExpectedAlertMessageStrings("Invoice",documentNo,lineNo);
		assertEquals(actualAlertMessage,expectedAlertMessage,"Tax Group Statistics Alert Message Validation Failed");

	}
	/**
	 * CDBC-277
	 * This Test validate Alert message when tax group code is missing on SALES INVOICE or blank while accessing the statitics page when TAC NOT= VERTEX
	 * @author P.Potdar
	 */
	@Ignore
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Deprecated" }, retryAnalyzer = TestRerun.class)
	public void salesInvoiceStatisticsPageMissingTaxGroupCodeAlertValidationNoVertexTest(){

		String customerCode = "cust_NOVERTEX";
		String itemNumber = "2000-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

		fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
		newInvoice.expandTable();

		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newInvoice.exitExpandTable();

		String actualTotalTaxAmount = newInvoice.getTotalTaxAmount();
		String documentNo = newInvoice.getCurrentSalesNumber();

		//Navigate to Statistics and get Sales Quote TaxAmount
		homePage.mainNavMenu.goToSubNavTab1(
				BusinessMainMenuNavTabs.INVOICE.value, "Statistics");

		String actualAlertMessage = newInvoice.getAlertMessageForTaxGroupCode();
		String lineNo = "10000";

		String expectedAlertMessage = newInvoice.createExpectedAlertMessageStrings("Invoice",documentNo,lineNo);
		assertEquals(actualAlertMessage,expectedAlertMessage,"Tax Group Statistics Alert Message Validation Failed");

	}
	/**
	 * CDBC-782
	 * This Test validates Alert message when tax group code is missing on posting documents
	 * @author Shruti Jituri
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
	public void validateMissingTaxGroupCodeAlertValidationTest(){
		String customerCode = "test1234";
		String itemNumber = "2000-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

		fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
		newInvoice.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, "20", 1);
		newInvoice.activateRow(2);
		fillInItemsTableInfo("Resource", "MARY", null, null, quantity, "10", 2);
		newInvoice.activateRow(3);
		fillInItemsTableInfo("G/L Account", "10100", null, null, quantity, null, 3);
		newInvoice.activateRow(4);
		newInvoice.exitExpandTable();
		String docNo=newInvoice.getCurrentSalesNumber();
		newInvoice.salesEditNavMenu.clickPostingTab();
		newInvoice.salesEditNavMenu.selectPostButton();
		newInvoice.dialogBoxClickYes();
		String actualMessage=newInvoice.getAlertMessage();
		String lineNo="10000";
		String expectedError=newInvoice.expectedMessages("Invoice",docNo,lineNo);
		assertTrue(actualMessage.equals(expectedError));
	}

	/**
	 * CDBC-784
	 * Validates if deleted document creates extra calls to Vertex
	 * @author Shruti-Jituri
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
	public void validateDeletedDocumentTest() {
		String customerCode = "test1234";
		String itemNumber = "1896-S";
		String quantity = "1";
		int expectedCount=4;

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

		fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
		newInvoice.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, "20", 1);
		newInvoice.activateRow(2);
		newInvoice.exitExpandTable();
		String documentNo = newInvoice.getCurrentSalesNumber();
		newInvoice.deleteDocument();
		BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
		adminPage.openXmlLogCategory();
		adminPage.filterDocuments(documentNo);
		int count = adminPage.getResponseCount(documentNo);
		assertEquals(count,expectedCount,"Row validation failed");
	}

	/**
	 * CDBC-788
	 * This Test validates if correct tax is displayed in print preview functionality for document that contains comments
	 * @author Shruti
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
	public void validateTaxWithPrintPreviewTest(){
		String customerCode = "test1234";
		String itemNumber = "1896-S";
		String quantity = "1";
		String expectedTaxAmount="17.34";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

		fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
		newInvoice.expandTable();

		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, "100", 1);
		newInvoice.activateRow(2);
		fillInItemsTableInfo("G/L Account", "10100", null, null, quantity, "50", 2);
		newInvoice.activateRow(3);
		fillInItemsTableInfo("Resource", "MARY", null, null, quantity, "139", 3);
		newInvoice.activateRow(4);
		fillInItemsTableComment(null, "Test Comment", 4);
		newInvoice.setTaxAreaCode("VERTEX",4);
		newInvoice.activateRow(5);
		newInvoice.exitExpandTable();
		String totalTaxAmount= newInvoice.getTotalTaxAmount();
		assertEquals(totalTaxAmount,expectedTaxAmount);
		newInvoice.salesEditNavMenu.clickMoreOptions();
		newInvoice.salesEditNavMenu.clickMoreOptionsActionsButton();
		newInvoice.salesEditNavMenu.selectActionsPostingButton();
		newInvoice.salesEditNavMenu.selectOtherPostingButton();
		newInvoice.salesEditNavMenu.selectDraftInvoiceButton();

		BusinessPrintPreviewPage printPreview = newInvoice.clickPrintPreview();
		String text = printPreview.getPdfText();
		assertTrue(text.contains("Total Tax\n" + expectedTaxAmount));
	}

	/**
	 * CDBC-1341
	 * Tests for flat fee on delivery charges in sales invoices with shipping to Colorado
	 * @author Vivek Olumbe
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
	public void verifyColoradoDeliveryFeeSalesInvoiceTest(){
		String customerCode = "test1234";
		String itemNumber = "1896-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

		fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
		newInvoice.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newInvoice.activateRow(2);
		fillInItemsTableInfo("Charge (Item)", "S-FREIGHT", null, null, quantity, "100", 2);
		newInvoice.activateRow(2);
		newInvoice.exitExpandTable();

		//Change address to Custom Address & recalculate
		newInvoice.openShippingAndBillingCategory();
		newInvoice.selectCustomShipToAddress();
		newInvoice.clickShowMore();
		fillInCustomAddressInShippingAndBilling("1575 Space Center Drive", "Colorado Springs", "CO", "80915");
		recalculateTax(newInvoice);

		//Open Vertex tax details window
		openVertexTaxDetailsWindow(newInvoice);
		List<String> actualTaxAmounts = newInvoice.getMultipleTaxAmount("Sales");
		assertTrue(actualTaxAmounts.contains("0.28"));
		newInvoice.closeTaxDetailsDialog();

		String documentNo = newInvoice.getCurrentSalesNumber();
		BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
		adminPage.openXmlLogCategory();
		adminPage.filterDocuments(documentNo);
		adminPage.filterxml("Tax Calc Response");
		String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
		assertTrue(xmlStr.contains("<Customer>\n" +
				"            <CustomerCode classCode=\"CUSTCLASS\">C00210</CustomerCode>\n" +
				"            <Destination taxAreaId=\"60410090\">\n" +
				"              <StreetAddress1>1575 Space Center Dr</StreetAddress1>\n" +
				"              <City>Colorado Springs</City>\n" +
				"              <MainDivision>CO</MainDivision>\n" +
				"              <PostalCode>80915-2441</PostalCode>\n" +
				"              <Country>US</Country>\n" +
				"            </Destination>\n" +
				"          </Customer>"), "Address Validation Failed");
		assertTrue(xmlStr.contains("<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"4220\">COLORADO</Jurisdiction>\n" +
				"            <CalculatedTax>0.28</CalculatedTax>"));
		assertTrue(xmlStr.contains("<Imposition impositionId=\"13\">Retail Delivery Fee</Imposition>\n"));
	}

	/**
	 * CDBC-1347
	 * Verifies tax with different destination address on invoice created from posted invoice
	 * @author Vivek Olumbe
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Sales_Regression" }, retryAnalyzer = TestRerun.class)
	public void verifyTaxOnAlternateAddressPostedInvoiceTest(){
		String customerCode = "test1234";
		String itemNumber = "1896-S";
		String quantity = "1";
		String expectedTax = "60.05";
		String expectedTaxAlternateAddress = "97.58";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage postedInvoice = salesInvoices.pageNavMenu.clickNew();

		// create invoice to post
		fillInSalesInvoiceGeneralInfo(postedInvoice, customerCode);
		postedInvoice.expandTable();
		postedInvoice.activateRow(1);
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		postedInvoice.activateRow(2);
		postedInvoice.exitExpandTable();

		String totalTax = postedInvoice.getTotalTaxAmount();
		assertEquals(totalTax,expectedTax);
		salesDocumentPostInvoice(postedInvoice);
		String docNo = postedInvoice.getCurrentSalesNumber();
		postedInvoice.clickBackArrow();

		// copy posted invoice to new invoice
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();
		fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
		newInvoice.salesEditNavMenu.clickPrepareButton();
		newInvoice.salesEditNavMenu.selectCopyDocument();
		newInvoice.fillOutCopyDocumentInformation("Posted Invoice", docNo);
		newInvoice.toggleIncludeHeader(true);
		newInvoice.dialogBoxClickOk();

		//Change destination address to TN address & recalculate
		newInvoice.openShippingAndBillingCategory();
		newInvoice.selectAlternateShipToAddress("TN ADDRESS");
		recalculateTax(newInvoice);

		String totalTaxAlternateAddress = newInvoice.getTotalTaxAmount();
		assertEquals(totalTaxAlternateAddress, expectedTaxAlternateAddress);

		String documentNo = newInvoice.getCurrentSalesNumber();

		BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
		adminPage.openXmlLogCategory();
		adminPage.filterDocuments(documentNo);
		adminPage.filterxml("Tax Calc Response");
		String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
		assertTrue(xmlStr.contains("<Destination taxAreaId=\"431870280\">\n" +
				"              <StreetAddress1>1790 Galleria Blvd</StreetAddress1>\n" +
				"              <City>Franklin</City>\n" +
				"              <MainDivision>TN</MainDivision>\n" +
				"              <PostalCode>37067-1601</PostalCode>\n" +
				"              <Country>US</Country>\n" +
				"            </Destination>"), "Destination Address Validation Failed");
		assertTrue(xmlStr.contains("<TotalTax>97.58</TotalTax>"), "Tax Validation Failed");
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