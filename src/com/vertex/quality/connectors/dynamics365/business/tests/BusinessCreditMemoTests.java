package com.vertex.quality.connectors.dynamics365.business.tests;

import com.vertex.quality.connectors.dynamics365.business.enums.BusinessMainMenuNavTabs;
import com.vertex.quality.connectors.dynamics365.business.pages.*;
import com.vertex.quality.connectors.dynamics365.business.tests.base.BusinessBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * contains tests for different credit memo scenarios
 *
 * @author cgajes, bhikshapathi
 */
@Listeners(TestRerunListener.class)
public class
BusinessCreditMemoTests extends BusinessBaseTest
{
	BusinessAdminHomePage homePage;
	/**
	 * CDBC-1020
	 * Creates a new sales order and copies it onto a sales memo
	 * Posts the memo and verifies tax
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Smoke" }, retryAnalyzer = TestRerun.class)
	public void createSalesOrderAndCreditMemoTest( )
	{
		String expectedTaxAmount = "60.05";
		String expectedTaxRate = "6.00";
		String expectedSalesOrderNumber;

		String customerCode = "C00020";
		String customerName = "Upgrade16 cust";
		String itemNumber = "1896-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
		BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

		fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.exitExpandTable();
		expectedSalesOrderNumber = newSalesOrder.getCurrentSalesNumber();

		openVertexTaxDetailsWindow(newSalesOrder);
		assertTrue(newSalesOrder.verifyVertexTaxDetailsField("Imposition"));
		String actualTaxRate = newSalesOrder.getTaxRate();
		String actualTaxAmount = newSalesOrder.getTaxAmount();

		assertEquals(expectedTaxAmount, actualTaxAmount);
		assertEquals(expectedTaxRate, actualTaxRate);

		newSalesOrder.closeTaxDetailsDialog();
		newSalesOrder.clickBackAndSaveArrow();

		BusinessSalesCreditMemoListPage sales_memos = sales_orders.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Credit Memos");
		BusinessSalesCreditMemoPage newCreditMemo = sales_memos.pageNavMenu.clickNew();

		fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
		newCreditMemo.activateRow(1);
		newCreditMemo.salesEditNavMenu.clickPrepareButton();
		newCreditMemo.salesEditNavMenu.selectCopyDocument();
		newCreditMemo.fillOutCopyDocumentInformation("Order", expectedSalesOrderNumber);
		String sellToNumber = newCreditMemo.getSellToCustomerNumber();
		String sellToName = newCreditMemo.getSellToCustomerName();
		assertEquals(customerCode, sellToNumber);
		assertEquals(customerName, sellToName);
		newCreditMemo.dialogBoxClickOk();

		openVertexTaxDetailsWindow(newCreditMemo);
		assertTrue(newCreditMemo.verifyVertexTaxDetailsField("Imposition"));
		actualTaxRate = newCreditMemo.getTaxRate();
		actualTaxAmount = newCreditMemo.getTaxAmount();

		assertEquals(expectedTaxAmount, actualTaxAmount);
		assertEquals(expectedTaxRate, actualTaxRate);

		newCreditMemo.closeTaxDetailsDialog();

		BusinessSalesCreditMemoPage postedMemo = postCreditMemo(newCreditMemo);

		actualTaxAmount = postedMemo.getTotalTaxAmount();

		assertEquals(expectedTaxAmount, actualTaxAmount);
	}

	/**
	 * CDBC-56
	 * Tests whether the print preview for a credit memo shows the correct tax amount
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void creditMemoPrintPreviewTest( )
	{
		String expectedTaxAmount = "60.05";
		String expectedTaxRate = "6.00";

		String customerCode = "Upgrade16 cust";
		String customerName = "Cust_Test_2";
		String itemNumber = "1896-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesCreditMemoListPage creditMemos = homePage.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Credit Memos");
		BusinessSalesCreditMemoPage newCreditMemo = creditMemos.pageNavMenu.clickNew();

		fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
		newCreditMemo.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newCreditMemo.exitExpandTable();

		newCreditMemo.salesEditNavMenu.clickMoreOptions();
		newCreditMemo.salesEditNavMenu.clickMoreOptionsActionsButton();
		newCreditMemo.salesEditNavMenu.selectActionsPostingButton();
		newCreditMemo.salesEditNavMenu.selectTestReportButton();

		BusinessPrintPreviewPage printPreview = newCreditMemo.clickPrintPreview();

		String allText = printPreview.getPdfText();
		String subString = allText.substring(allText.indexOf("Total USD Excl. Tax"),
			allText.indexOf("Total USD Incl. Tax"));
		assertTrue(subString.contains(expectedTaxAmount));
	}

	/**
	 * CDBC-57
	 * Tests whether the print preview for a posted credit memo shows the correct tax amount
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void postedMemoPrintPreviewTest( )
	{
		String expectedTaxAmount = "60.05";
		String customerCode = "Upgrade16 cust";
		String itemNumber = "1896-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesCreditMemoListPage creditMemos = homePage.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Credit Memos");
		BusinessSalesCreditMemoPage newCreditMemo = creditMemos.pageNavMenu.clickNew();

		fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
		newCreditMemo.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newCreditMemo.exitExpandTable();
		BusinessSalesCreditMemoPage postedMemo = postCreditMemo(newCreditMemo);

		postedMemo.salesEditNavMenu.clickPrintSendButton();
		postedMemo.salesEditNavMenu.selectPrint();
		BusinessPrintPreviewPage printPreview = postedMemo.clickPrintPreview();

		String allText = printPreview.getPdfText();
		assertTrue(allText.contains("Total Tax\n" + expectedTaxAmount));
	}

	/**
	 * CDBC-1039
	 * Tests creating a sales quote, converting it to an order, posting the invoice,
	 * copying that to a credit memo, posting the memo
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void conversionWorkflowTest( )
	{
		String expectedTaxAmount = "60.05";
		String customerCode = "Upgrade16 cust";
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

		String expectedQuoteNumber = newSalesQuote.getCurrentSalesNumber();
		String actualTaxAmount = newSalesQuote.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedTaxAmount);

		BusinessSalesOrderPage convertedSalesOrder = newSalesQuote.convertQuoteToOrder();

		String actualQuoteNumber = convertedSalesOrder.getQuoteNumber();
		actualTaxAmount = convertedSalesOrder.getTotalTaxAmount();
		assertEquals(actualQuoteNumber, expectedQuoteNumber);
		assertEquals(actualTaxAmount, expectedTaxAmount);

		String expectedOrderNumber = convertedSalesOrder.getCurrentSalesNumber();

		BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(convertedSalesOrder);

		String actualOrderNumber = postedInvoice.getOrderNumber();
		actualQuoteNumber = postedInvoice.getQuoteNumber();
		actualTaxAmount = postedInvoice.getTotalTaxAmount();
		assertEquals(actualOrderNumber, expectedOrderNumber);
		assertEquals(actualQuoteNumber, expectedQuoteNumber);
		assertEquals(actualTaxAmount, expectedTaxAmount);

		String invoiceNumber = postedInvoice.getCurrentSalesNumber();

		postedInvoice.clickBackAndSaveArrow();

		BusinessSalesCreditMemoListPage creditMemos = salesQuotes.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Credit Memos");
		BusinessSalesCreditMemoPage newCreditMemo = creditMemos.pageNavMenu.clickNew();

		fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
		copySalesDocumentToMemo(newCreditMemo, "Posted Invoice", invoiceNumber);

		openVertexTaxDetailsWindow(newCreditMemo);
		actualTaxAmount = newCreditMemo.getTaxAmount();
		assertEquals(actualTaxAmount, expectedTaxAmount);
		newCreditMemo.closeTaxDetailsDialog();

		BusinessSalesCreditMemoPage postedMemo = postCreditMemo(newCreditMemo);
		actualTaxAmount = postedMemo.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedTaxAmount);
	}

	/**
	 * CDBC-1040
	 * Tests creating a sales quote, converting it to an order, posting the invoice,
	 * copying that to a credit memo, posting the memo,
	 * ensuring for all types the tax details document shows the correct document type and number
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void conversionWorkflowTaxDetailHeaderTest( )
	{
		String expectedTaxAmount = "60.05";
		String customerCode = "Upgrade16 cust";
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

		String expectedQuoteNumber = newSalesQuote.getCurrentSalesNumber();

		openVertexTaxDetailsWindow(newSalesQuote);
		String actualTaxDetailHeader = newSalesQuote.taxDetailsDocumentReadHeader();
		String expectedTaxDetailHeader = String.format("Edit - Sales Line Tax Details - Quote ∙ %s",
			expectedQuoteNumber);
		String actualTaxAmount = newSalesQuote.getTaxAmount();
		assertEquals(actualTaxAmount, expectedTaxAmount);
		assertEquals(actualTaxDetailHeader, expectedTaxDetailHeader);
		newSalesQuote.closeTaxDetailsDialog();

		BusinessSalesOrderPage convertedSalesOrder = newSalesQuote.convertQuoteToOrder();

		String actualQuoteNumber = convertedSalesOrder.getQuoteNumber();
		assertEquals(actualQuoteNumber, expectedQuoteNumber);

		String expectedOrderNumber = convertedSalesOrder.getCurrentSalesNumber();
		openVertexTaxDetailsWindow(convertedSalesOrder);
		actualTaxDetailHeader = convertedSalesOrder.taxDetailsDocumentReadHeader();
		expectedTaxDetailHeader = String.format("Edit - Sales Line Tax Details - Order ∙ %s", expectedOrderNumber);
		actualTaxAmount = convertedSalesOrder.getTaxAmount();
		assertEquals(actualTaxAmount, expectedTaxAmount);
		assertEquals(actualTaxDetailHeader, expectedTaxDetailHeader);
		convertedSalesOrder.closeTaxDetailsDialog();

		BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(convertedSalesOrder);

		actualTaxAmount = postedInvoice.getTotalTaxAmount();
		String actualOrderNumber = postedInvoice.getOrderNumber();
		actualQuoteNumber = postedInvoice.getQuoteNumber();
		assertEquals(actualOrderNumber, expectedOrderNumber);
		assertEquals(actualQuoteNumber, expectedQuoteNumber);
		assertEquals(actualTaxAmount, expectedTaxAmount);

		String invoiceNumber = postedInvoice.getCurrentSalesNumber();
		openVertexTaxDetailsWindow(postedInvoice);
		actualTaxDetailHeader = postedInvoice.taxDetailsDocumentReadHeader();
		expectedTaxDetailHeader = String.format("Edit - Sales Line Tax Details - Invoice ∙ %s", invoiceNumber);
		actualTaxAmount = postedInvoice.getTaxAmount();
		assertEquals(actualTaxAmount, expectedTaxAmount);
		assertEquals(actualTaxDetailHeader, expectedTaxDetailHeader);
		postedInvoice.closeTaxDetailsDialog();

		postedInvoice.clickBackAndSaveArrow();

		BusinessSalesCreditMemoListPage creditMemos = salesQuotes.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Credit Memos");
		BusinessSalesCreditMemoPage newCreditMemo = creditMemos.pageNavMenu.clickNew();

		fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
		copySalesDocumentToMemo(newCreditMemo, "Posted Invoice", invoiceNumber);

		String memoNumber = newCreditMemo.getCurrentSalesNumber();
		openVertexTaxDetailsWindow(newCreditMemo);
		actualTaxDetailHeader = newCreditMemo.taxDetailsDocumentReadHeader();
		expectedTaxDetailHeader = String.format("Edit - Sales Line Tax Details - Credit Memo ∙ %s", memoNumber);
		actualTaxAmount = newCreditMemo.getTaxAmount();
		assertEquals(actualTaxAmount, expectedTaxAmount);
		assertEquals(actualTaxDetailHeader, expectedTaxDetailHeader);
		newCreditMemo.closeTaxDetailsDialog();

		BusinessSalesCreditMemoPage postedMemo = postCreditMemo(newCreditMemo);
		memoNumber = postedMemo.getCurrentSalesNumber();
		openVertexTaxDetailsWindow(postedMemo);
		assertTrue(postedMemo.verifyVertexTaxDetailsField("Imposition"));
		actualTaxDetailHeader = postedMemo.taxDetailsDocumentReadHeader();
		expectedTaxDetailHeader = String.format("Edit - Sales Line Tax Details - Credit Memo ∙ %s", memoNumber);
		actualTaxAmount = postedMemo.getTaxAmount();
		assertEquals(actualTaxAmount, expectedTaxAmount);
		assertEquals(actualTaxDetailHeader, expectedTaxDetailHeader);
	}

	/**
	 * CDBC-1021
	 * Tests creating a sales quote, converting it to an order, posting the invoice,
	 * copying that to a credit memo, posting the memo,
	 * then verifying the memo's print preview
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void convertSalesQuoteToOrderAndCopyToMemoCheckPrintPreviewTest( )
	{
		String expectedTaxRate = "6.00";
		String expectedTaxAmount = "60.05";

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
		newSalesQuote.activateRow(2);
		newSalesQuote.exitExpandTable();

		String expectedQuoteNumber = newSalesQuote.getCurrentSalesNumber();
		String actualTaxAmount = newSalesQuote.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedTaxAmount);

		BusinessSalesOrderPage convertedSalesOrder = newSalesQuote.convertQuoteToOrder();

		String actualQuoteNumber = convertedSalesOrder.getQuoteNumber();
		actualTaxAmount = convertedSalesOrder.getTotalTaxAmount();
		assertEquals(actualQuoteNumber, expectedQuoteNumber);
		assertEquals(actualTaxAmount, expectedTaxAmount);

		String expectedOrderNumber = convertedSalesOrder.getCurrentSalesNumber();

		BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(convertedSalesOrder);

		String actualOrderNumber = postedInvoice.getOrderNumber();
		actualQuoteNumber = postedInvoice.getQuoteNumber();
		actualTaxAmount = postedInvoice.getTotalTaxAmount();
		assertEquals(actualOrderNumber, expectedOrderNumber);
		assertEquals(actualQuoteNumber, expectedQuoteNumber);
		assertEquals(actualTaxAmount, expectedTaxAmount);

		String invoiceNumber = postedInvoice.getCurrentSalesNumber();

		postedInvoice.clickBackAndSaveArrow();

		BusinessSalesCreditMemoListPage creditMemos = salesQuotes.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Credit Memos");
		BusinessSalesCreditMemoPage newCreditMemo = creditMemos.pageNavMenu.clickNew();

		fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
		copySalesDocumentToMemo(newCreditMemo, "Posted Invoice", invoiceNumber);

		openVertexTaxDetailsWindow(newCreditMemo);
		actualTaxAmount = newCreditMemo.getTaxAmount();
		assertEquals(actualTaxAmount, expectedTaxAmount);
		newCreditMemo.closeTaxDetailsDialog();

		BusinessSalesCreditMemoPage postedMemo = postCreditMemo(newCreditMemo);
		actualTaxAmount = postedMemo.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedTaxAmount);

		postedMemo.salesEditNavMenu.clickPrintSendButton();
		postedMemo.salesEditNavMenu.selectPrint();
		BusinessPrintPreviewPage printPreview = postedMemo.clickPrintPreview();

		String allText = printPreview.getPdfText();

		assertTrue(allText.contains("Total Tax\n" + expectedTaxAmount));
	}

	/**
	 * CDBC-1041
	 * Tests creating a sales quote using an alternate customer address, converting it to an order, posting the invoice,
	 * copying that to a credit memo, posting the memo,
	 * and ensuring the alternate address is correctly reflected
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void conversionWorkflowAlternateAddressTest( )
	{
		String expectedDefaultAddressTaxAmount = "81.32";
		String expectedAltAddressTaxAmount = "62.55";
		String expectedDefaultJurisdiction = "NEW YORK";
		String expectedAltAddressJurisdiction = "ILLINOIS";

		String customerCode = "VERTEX TAX AREA ISSUE";
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

		String actualTaxAmount = newSalesQuote.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedDefaultAddressTaxAmount);
		openVertexTaxDetailsWindow(newSalesQuote);
		String actualJurisdiction = newSalesQuote.getJurisdiction();
		assertEquals(actualJurisdiction, expectedDefaultJurisdiction);
		newSalesQuote.closeTaxDetailsDialog();

		newSalesQuote.openShippingAndBillingCategory();
		newSalesQuote.selectAlternateShipToAddress("IL_ADDR");

		recalculateTax(newSalesQuote);
		openVertexTaxDetailsWindow(newSalesQuote);
		actualJurisdiction = newSalesQuote.getJurisdiction();
		assertEquals(actualJurisdiction, expectedAltAddressJurisdiction);
		newSalesQuote.closeTaxDetailsDialog();
		actualTaxAmount = newSalesQuote.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedAltAddressTaxAmount);

		BusinessSalesOrderPage convertedSalesOrder = newSalesQuote.convertQuoteToOrder();

		actualTaxAmount = convertedSalesOrder.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedAltAddressTaxAmount);
		openVertexTaxDetailsWindow(convertedSalesOrder);
		actualJurisdiction = convertedSalesOrder.getJurisdiction();
		assertEquals(actualJurisdiction, expectedAltAddressJurisdiction);
		convertedSalesOrder.closeTaxDetailsDialog();

		BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(convertedSalesOrder);

		actualTaxAmount = postedInvoice.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedAltAddressTaxAmount);
		openVertexTaxDetailsWindow(postedInvoice);
		actualJurisdiction = postedInvoice.getJurisdiction();
		assertEquals(actualJurisdiction, expectedAltAddressJurisdiction);
		postedInvoice.closeTaxDetailsDialog();

		String invoiceNumber = postedInvoice.getCurrentSalesNumber();

		postedInvoice.clickBackAndSaveArrow();

		BusinessSalesCreditMemoListPage creditMemos = salesQuotes.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Credit Memos");
		BusinessSalesCreditMemoPage newCreditMemo = creditMemos.pageNavMenu.clickNew();

		fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
		copySalesDocumentToMemo(newCreditMemo, "Posted Invoice", invoiceNumber);

		newCreditMemo.openCategory("Original Address");
		newCreditMemo.enterSalesInvoiceNo(invoiceNumber);

		recalculateTax(newCreditMemo);

		openVertexTaxDetailsWindow(newCreditMemo);
		actualJurisdiction = newCreditMemo.getJurisdiction();
		assertEquals(actualJurisdiction, expectedAltAddressJurisdiction);
		newCreditMemo.closeTaxDetailsDialog();
		actualTaxAmount = newCreditMemo.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedAltAddressTaxAmount);

		BusinessSalesCreditMemoPage postedMemo = postCreditMemo(newCreditMemo);
		actualTaxAmount = postedMemo.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedAltAddressTaxAmount);
		openVertexTaxDetailsWindow(postedMemo);
		actualJurisdiction = postedMemo.getJurisdiction();
		assertEquals(actualJurisdiction, expectedAltAddressJurisdiction);
		postedMemo.closeTaxDetailsDialog();
	}

	/**
	 * CDBC-1042
	 * Tests creating a sales quote and setting the price to zero, recalculating the tax,
	 * converting it to an order, posting the invoice,
	 * copying that to a credit memo, posting the memo,
	 * and ensuring the recalculated zero tax is correctly reflected
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void conversionWorkflowRecalculateZeroTaxTest( )
	{
		String expectedTaxRate = "6.00";
		String expectedTaxAmount = "60.05";
		String expectedZeroTax = "0.00";

		String customerCode = "C00020";
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
		String actualTaxAmount = newSalesQuote.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedTaxAmount);

		//newSalesQuote.returnToFirstCell(1);
		newSalesQuote.expandTable();
		updatePrice("0.00",1);
		newSalesQuote.exitExpandTable();
		recalculateTax(newSalesQuote);

		actualTaxAmount = newSalesQuote.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedZeroTax);

		BusinessSalesOrderPage convertedSalesOrder = newSalesQuote.convertQuoteToOrder();

		actualTaxAmount = convertedSalesOrder.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedZeroTax);

		BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(convertedSalesOrder);

		actualTaxAmount = postedInvoice.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedZeroTax);

		String invoiceNumber = postedInvoice.getCurrentSalesNumber();

		postedInvoice.clickBackAndSaveArrow();

		BusinessSalesCreditMemoListPage creditMemos = salesQuotes.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Credit Memos");
		BusinessSalesCreditMemoPage newCreditMemo = creditMemos.pageNavMenu.clickNew();

		fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
		copySalesDocumentToMemo(newCreditMemo, "Posted Invoice", invoiceNumber);

		openVertexTaxDetailsWindow(newCreditMemo);
		actualTaxAmount = newCreditMemo.getTaxAmount();
		assertEquals(actualTaxAmount, expectedZeroTax);
		newCreditMemo.closeTaxDetailsDialog();

		BusinessSalesCreditMemoPage postedMemo = postCreditMemo(newCreditMemo);
		actualTaxAmount = postedMemo.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedZeroTax);
	}

	/**
	 * CDBC-1043/C365BC-509
	 * Tests creating a sales quote with all item types and all
	 * items being untaxable, converting it to an order, posting the invoice,
	 * copying that to a credit memo, posting the memo,
	 * ensuring the tax is correctly calculated as zero
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void conversionWorkflowNontaxableProductClassAllTypesTest( )
	{

		String expectedAmount = "0.00";
		String customerCode = "Ticket42TEST";
		String itemNumber = "1972-S";
		String quantity = "1";

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
		fillInItemsTableInfo("Resource", "MARTY", null, null, quantity, null, 3);
		newSalesQuote.activateRow(4);
		fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, null, quantity, "50", 4);
		newSalesQuote.assignToQuantity("1", 4);
		newSalesQuote.activateRow(5);
		fillInItemsTableInfo("Fixed Asset", "FA000120", null, null, quantity, "15", 5);
		newSalesQuote.activateRow(5);
		newSalesQuote.exitExpandTable();

		String expectedQuoteNumber = newSalesQuote.getCurrentSalesNumber();
		String actualTaxAmount = newSalesQuote.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedAmount);

		BusinessSalesOrderPage convertedSalesOrder = newSalesQuote.convertQuoteToOrder();

		String actualQuoteNumber = convertedSalesOrder.getQuoteNumber();
		actualTaxAmount = convertedSalesOrder.getTotalTaxAmount();
		assertEquals(actualQuoteNumber, expectedQuoteNumber);
		assertEquals(actualTaxAmount, expectedAmount);

		String expectedOrderNumber = convertedSalesOrder.getCurrentSalesNumber();

		BusinessSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(convertedSalesOrder);

		String actualOrderNumber = postedInvoice.getOrderNumber();
		actualQuoteNumber = postedInvoice.getQuoteNumber();
		actualTaxAmount = postedInvoice.getTotalTaxAmount();
		assertEquals(actualOrderNumber, expectedOrderNumber);
		assertEquals(actualQuoteNumber, expectedQuoteNumber);
		assertEquals(actualTaxAmount, expectedAmount);

		String invoiceNumber = postedInvoice.getCurrentSalesNumber();

		postedInvoice.clickBackAndSaveArrow();

		BusinessSalesCreditMemoListPage creditMemos = salesQuotes.mainNavMenu.goToSubNavTab(
			BusinessMainMenuNavTabs.SALES.value, "Sales Credit Memos");
		BusinessSalesCreditMemoPage newCreditMemo = creditMemos.pageNavMenu.clickNew();

		fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
		copySalesDocumentToMemo(newCreditMemo, "Posted Invoice", invoiceNumber);

		openVertexTaxDetailsWindow(newCreditMemo);
		actualTaxAmount = newCreditMemo.getTaxAmount();
		assertEquals(actualTaxAmount, expectedAmount);
		newCreditMemo.closeTaxDetailsDialog();

		BusinessSalesCreditMemoPage postedMemo = postCreditMemo(newCreditMemo);
		actualTaxAmount = postedMemo.getTotalTaxAmount();
		assertEquals(actualTaxAmount, expectedAmount);
	}

	/**
	 * CDBC-671
	 * Creates a new sales order and copies it onto a sales memo
	 * Posts the memo and verifies tax
	 * @author bhikshapathi
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void createSalesCreditMemoTest( )  {
		String expectedTaxAmount = "60.05";
		String expectedTaxRate = "6.00";
		String customerCode = "Test Phila";
		String itemNumber = "1896-S";
		String quantity = "1";
		String locationCode = "EAST";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesCreditMemoListPage sales_memos = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Credit Memos");
		BusinessSalesCreditMemoPage newCreditMemo = sales_memos.pageNavMenu.clickNew();

		fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
		newCreditMemo.expandTable();
		fillInItemsTableInfo("Item", itemNumber,null,locationCode,quantity,null, 1);

		newCreditMemo.exitExpandTable();

		//Navigate to Vertex Tax Details and Validate Tax information
		openVertexTaxDetailsWindow(newCreditMemo);
		String actualTaxRate = newCreditMemo.getTaxRate();
		String actualTaxAmount = newCreditMemo.getTaxAmount();

		assertEquals(expectedTaxAmount, actualTaxAmount);
		assertEquals(expectedTaxRate, actualTaxRate);

		newCreditMemo.closeTaxDetailsDialog();

		BusinessSalesCreditMemoPage postedMemo = postCreditMemo(newCreditMemo);
		actualTaxAmount = postedMemo.getTotalTaxAmount();
		assertEquals(expectedTaxAmount, actualTaxAmount);

		String postedCreditMemoNumber = postedMemo.getCurrentSalesNumber();
		System.out.println(postedCreditMemoNumber);

		//Select light bulb and type vertex (this sends you to Vertex Admin)
		BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();

		// Check XML
		adminPage.openXmlLogCategory();
		//Tax Calc Request and Response
		adminPage.filterDocuments(postedCreditMemoNumber);

		//Tax Calc Response XML Verification
		adminPage.filterxml("Invoice Response");
		String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
		assertTrue(xmlStr.contains("<CalculatedTax>-60.05</CalculatedTax>"), "Calculated Tax Validation Failed");

		assertTrue(xmlStr.contains("<TotalTax>-60.05</TotalTax>"), "Total Tax  Validation Failed");
		assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");

	}
	/**
	 * CDBC-240
	 * Tests Statistics Functionality for Sales Credit Memo TaxAmount using all product types when TAC= VERTEX
	 * @author P.Potdar
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void compareSalesCreditMemoTaxAmountAllProductInStatisticsTest(){

		String customerCode = "test1234";
		String itemNumber = "1896-S";
		String quantity = "1";
		String nontaxableCode = "SUPPLIES";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesCreditMemoListPage creditMemos = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Credit Memos");
		BusinessSalesCreditMemoPage newCreditMemo = creditMemos.pageNavMenu.clickNew();

		fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
		newCreditMemo.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newCreditMemo.activateRow(2);
		fillInItemsTableInfo("G/L Account", "10100", null, null, quantity, "50", 2);
		newCreditMemo.activateRow(3);
		fillInItemsTableInfo("Resource", "MARY", null, null, quantity, null, 3);
		newCreditMemo.activateRow(4);
		fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, null, quantity, "50", 4);
		newCreditMemo.activateRow(5);
		fillInItemsTableInfo("Fixed Asset", "FA000110", null, null, quantity, "15", 5);
		newCreditMemo.activateRow(6);
		newCreditMemo.exitExpandTable();

		//Navigate to Statistics and get Sales Credit Memo TaxAmount
		homePage.mainNavMenu.goToSubNavTab1(
				BusinessMainMenuNavTabs.CREDIT_MEMO.value, "Statistics");

		String actualTaxAmount1 = newCreditMemo.getStatisticsTaxAmount();
		newCreditMemo.closeTaxDetailsDialog();
		String actualTotalTaxAmount = newCreditMemo.getTotalTaxAmount();
		assertEquals(actualTotalTaxAmount,actualTaxAmount1);

	}
	/**
	 * CDBC-241
	 * Tests Statistics Functionality for POSTED Sales Credit Memo TaxAmount using all product types
	 * @author P.Potdar
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void comparePostedSalesCreditMemoTaxAmountAllProductInStatisticsTest() {

		String customerCode = "test1234";
		String itemNumber = "1896-S";
		String quantity = "1";
		String nontaxableCode = "SUPPLIES";
		String expectedTaxAmount = "63.95";
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesCreditMemoListPage creditMemos = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Credit Memos");
		BusinessSalesCreditMemoPage newCreditMemo = creditMemos.pageNavMenu.clickNew();
		fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
		newCreditMemo.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newCreditMemo.activateRow(2);
		fillInItemsTableInfo("G/L Account", "10100", null, null, quantity, "50", 2);
		newCreditMemo.activateRow(3);
		fillInItemsTableInfo("Resource", "MARY", null, null, quantity, null, 3);
		newCreditMemo.activateRow(4);
		fillInItemsTableInfo("Fixed Asset", "FA000120", null, null, quantity, "15", 4);
		newCreditMemo.activateRow(5);
		newCreditMemo.exitExpandTable();
		BusinessSalesCreditMemoPage postedMemo = postCreditMemo(newCreditMemo);

		String actualTaxAmount = postedMemo.getTotalTaxAmount();
		assertEquals(expectedTaxAmount, actualTaxAmount);
        //Navigate to Statistics and get Sales Credit Memo TaxAmount
		homePage.mainNavMenu.goToChildSubNavTab1(
				BusinessMainMenuNavTabs.POSTED_SALES_CREDIT_MEMO.value, BusinessMainMenuNavTabs.CREDIT_MEMO.value,"Statistics");
		String postedTaxAmount = newCreditMemo.getStatisticsTaxAmount();
		postedMemo.closeTaxDetailsDialog();
		String statTotalTaxAmount = newCreditMemo.getTotalTaxAmount();
		assertEquals(statTotalTaxAmount, postedTaxAmount);
	}
	/**
	 * CDBC-242
	 * Tests Statistics Functionality for POSTED Sales Credit Memo TaxAmount using all product types
	 * @author P.Potdar
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void comparePostedSalesCreditMemoTaxAmountAllProductInStatisticsNoVertexTest() {
		String customerCode = "cust_NOVERTEX";
		String itemNumber = "1896-S";
		String quantity = "1";
		String expectedTaxAmount = "60.05";
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesCreditMemoListPage creditMemos = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Credit Memos");
		BusinessSalesCreditMemoPage newCreditMemo = creditMemos.pageNavMenu.clickNew();
		fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
		newCreditMemo.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newCreditMemo.exitExpandTable();
		BusinessSalesCreditMemoPage postedMemo = postCreditMemo(newCreditMemo);

		String actualTaxAmount = postedMemo.getTotalTaxAmount();
		assertEquals(expectedTaxAmount, actualTaxAmount);
        //Navigate to Statistics and get Sales Credit Memo TaxAmount
		homePage.mainNavMenu.goToChildSubNavTab1(
				BusinessMainMenuNavTabs.POSTED_SALES_CREDIT_MEMO.value, BusinessMainMenuNavTabs.CREDIT_MEMO.value,"Statistics");
		String postedTaxAmount = newCreditMemo.getStatisticsTaxAmount();
		newCreditMemo.closeTaxDetailsDialog();
		String statTotalTaxAmount = newCreditMemo.getTotalTaxAmount();
		assertEquals(statTotalTaxAmount, postedTaxAmount);
	}
	/**
	 * CDBC-280
	 * Tests Statistics Functionality for POSTED Sales Credit Memo TaxAmount using all product types
	 * @author P.Potdar
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void compareSalesCreditMemoTaxAmountAllProductInStatisticsNoVertexTest() {

		String customerCode = "cust_NOVERTEX";
		String itemNumber = "1896-S";
		String quantity = "1";
		String nontaxableCode = "LABOR";
		String expectedTaxAmount = "30.02";
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesCreditMemoListPage creditMemos = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Credit Memos");
		BusinessSalesCreditMemoPage newCreditMemo = creditMemos.pageNavMenu.clickNew();
		fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
		newCreditMemo.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newCreditMemo.exitExpandTable();

		//Navigate to Statistics and get Sales Credit Memo TaxAmount

		homePage.mainNavMenu.goToSubNavTab1(
				BusinessMainMenuNavTabs.CREDIT_MEMO.value, "Statistics");
		String actualTaxAmount1 = newCreditMemo.getStatisticsTaxAmount();
		newCreditMemo.closeTaxDetailsDialog();
		String actualTotalTaxAmount = newCreditMemo.getTotalTaxAmount();
		assertEquals(actualTotalTaxAmount,actualTaxAmount1);
		}
	/**
	 * CDBC-281
	 * This Test validate Alert message when tax group code on CREDIT MEMO is missing or blank while accessing the statitics page TAC= VERTEX
	 * @author P.Potdar
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void salesCreditMemoStatisticsPageMissingTaxGroupCodeAlertValidationTest(){

		String customerCode = "test1234";
		String itemNumber = "2000-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesCreditMemoListPage creditMemos = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Credit Memos");
		BusinessSalesCreditMemoPage newCreditMemo = creditMemos.pageNavMenu.clickNew();

		fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
		newCreditMemo.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newCreditMemo.exitExpandTable();

		String actualTotalTaxAmount = newCreditMemo.getTotalTaxAmount();
		String documentNo = newCreditMemo.getCurrentSalesNumber();

		//Navigate to Statistics and get Sales Quote TaxAmount
		homePage.mainNavMenu.goToSubNavTab1(
				BusinessMainMenuNavTabs.CREDIT_MEMO.value, "Statistics");

		String actualAlertMessage = newCreditMemo.getAlertMessageForTaxGroupCode();
		String lineNo = "10000";

		String expectedAlertMessage = newCreditMemo.createExpectedAlertMessageStrings("Credit Memo",documentNo,lineNo);
		assertEquals(actualAlertMessage,expectedAlertMessage,"Tax Group Statistics Alert Message Validation Failed");

	}
	/**
	 * CDBC-282
	 * This Test validate Alert message when tax group code on CREDIT MEMO is missing or blank while accessing the statitics page when TAC NOT= VERTEX
	 * @author P.Potdar
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void salesCreditMemoStatisticsPageMissingTaxGroupCodeAlertValidationNoVertexTest(){

		String customerCode = "cust_NOVERTEX";
		String itemNumber = "2000-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesCreditMemoListPage creditMemos = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Credit Memos");
		BusinessSalesCreditMemoPage newCreditMemo = creditMemos.pageNavMenu.clickNew();

		fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
		newCreditMemo.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
		newCreditMemo.exitExpandTable();

		String actualTotalTaxAmount = newCreditMemo.getTotalTaxAmount();
		String documentNo = newCreditMemo.getCurrentSalesNumber();
		String lineNo = "10000";

		//Navigate to Statistics and get Sales Quote TaxAmount
		homePage.mainNavMenu.goToSubNavTab1(
				BusinessMainMenuNavTabs.CREDIT_MEMO.value, "Statistics");

		String actualAlertMessage = newCreditMemo.getAlertMessageForTaxGroupCode();

		String expectedAlertMessage = newCreditMemo.createExpectedAlertMessageStrings("Credit Memo",documentNo,lineNo);
		assertEquals(actualAlertMessage,expectedAlertMessage,"Tax Group Statistics Alert Message Validation Failed");
	}
	/**
	 * CDBC-786
	 * This Test validates if extra XML calls are being made on copying the document that contains only comments
	 * @author Shruti
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void copyDocWithOnlyCommentsTest(){
		String customerCode = "test1234";
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
		BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

		fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
		newInvoice.expandTable();
		fillInItemsTableComment(null, "Test Comment", 1);
		newInvoice.exitExpandTable();
		String invoiceNo=newInvoice.getCurrentSalesNumber();
		newInvoice.clickBackArrow();
		newInvoice.dialogBoxClickYes();
		BusinessSalesCreditMemoListPage creditMemos = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Credit Memos");
		BusinessSalesCreditMemoPage newCreditMemo = creditMemos.pageNavMenu.clickNew();

		fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
		copySalesDocumentToMemo(newCreditMemo, "Invoice", invoiceNo);
		String creditNumber = newCreditMemo.getCurrentSalesNumber();
		BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
		adminPage.openXmlLogCategory();
		adminPage.filterDocuments(creditNumber);
		String xmlData=adminPage.validatingNoXMLLog();
		assertTrue(xmlData.contains("(There is nothing to show in this view)"), "XML Validation Failed and XMl exists");
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
