package com.vertex.quality.connectors.dynamics365.nav.tests;

import com.vertex.quality.connectors.dynamics365.nav.pages.*;
import com.vertex.quality.connectors.dynamics365.nav.tests.base.NavBaseTest;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertTrue;

public class NavConversionWorkflowTests extends NavBaseTest {

    /**
     * CDNAV-603
     * Tests creates a Sales Quote, converts it to an Invoice, posts the Invoice,
     * copy posted invoice to a Credit Memo, and post the Credit Memo
     * @author  Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Smoke", "D365_NAV_Regression"})
    public void creatingDocumentQuoteToPostedCreditMemoTest() {
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity="1";
        String expectedTaxAmount="60.05";

        //Navigate to Sales Quote page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesQuotesListPage salesQuotes =homePage.mainNavMenu.goToSubNavTab("Sales Quotes");
        NavSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();
        fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);

        newSalesQuote.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null,null, quantity, null, 1);
        newSalesQuote.activateRow(2);

        //Validate total tax and tax in Statistics tab
        String actualQuoteTotalTaxAmount = newSalesQuote.getTotalTaxAmount();
        assertEquals(expectedTaxAmount,actualQuoteTotalTaxAmount);
        navigateToStatWindow();
        String actualStatTaxAmountQuote = newSalesQuote.getVertexStatTaxAmount();
        newSalesQuote.closeTaxDetailsDialog();
        assertEquals(actualQuoteTotalTaxAmount,actualStatTaxAmountQuote);

        //convert Quote to Invoice
        NavSalesInvoicePage convertedSalesInvoice = salesDocumentMakeInvoice(newSalesQuote);

        //Validate tax amount in UI field on Sales Invoice
        String actualInvoiceTotalTaxAmount = convertedSalesInvoice.getTotalTaxAmount();
        assertEquals(expectedTaxAmount, actualInvoiceTotalTaxAmount);

        //Validate tax amount for customer in Statistics functionality on Sales Invoice
        navigateToStatWindow();
        String actualInvoiceStatTax= convertedSalesInvoice.getVertexStatTaxAmount();
        convertedSalesInvoice.closeTaxDetailsDialog();
        assertEquals(actualInvoiceStatTax, actualInvoiceTotalTaxAmount);

        //Post Invoice and Validate tax amount in UI field and Stat Page
        NavSalesInvoicePage postedInvoice=postTheInvoice(convertedSalesInvoice);
        String actualPostedInvoiceTotalTaxAmount=postedInvoice.getTotalTaxAmount();
        assertEquals(expectedTaxAmount, actualPostedInvoiceTotalTaxAmount);
        //Please ensure you have enabled 'Invoice Request enabled' checkbox on admin page to verify tax on Stat page
        navigateToStatWindow();
        String actualStatTax= postedInvoice.getVertexStatTaxAmount();
        postedInvoice.closeTaxDetailsDialog();
        assertEquals(actualStatTax, actualPostedInvoiceTotalTaxAmount);

        //get Posted Invoice number
        String postedInvoiceNum = postedInvoice.getCurrentSalesNumber();
        postedInvoice.clickBackAndSaveArrow();

        //Open credit Memo and copy document
        NavAdminHomePage creditMemoHomePage = initializeTestPageAndSignOn();
        NavSalesCreditMemoListPage salesCreditMemo = creditMemoHomePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newSalesCreditMemo = salesCreditMemo.pageNavMenu.clickNew();
        fillInSalesMemoGeneralInfo(newSalesCreditMemo, customerCode);

        copySalesDocumentToMemo(newSalesCreditMemo,"Posted Invoice",postedInvoiceNum);
        newSalesCreditMemo.dialogBoxClickOk();
        String actualTaxAmountCreditMemo = newSalesCreditMemo.getTotalTaxAmount();
        assertEquals(expectedTaxAmount,actualTaxAmountCreditMemo);
        navigateToStatWindow();
        String actualCreditMemoStatVertexTax= newSalesCreditMemo.getVertexStatTaxAmount();
        newSalesCreditMemo.closeTaxDetailsDialog();
        assertEquals(actualCreditMemoStatVertexTax,actualTaxAmountCreditMemo);

        //Post Credit Memo and verify tax
        NavSalesCreditMemoPage postedMemo = postCreditMemo(newSalesCreditMemo);
        String actualTaxAmountPostedCreditMemo = postedMemo.getTotalTaxAmount();
        assertEquals(expectedTaxAmount, actualTaxAmountPostedCreditMemo);
        navigateToStatWindow();
        String actualPostedCreditStatVertexTax= newSalesCreditMemo.getVertexStatTaxAmount();
        newSalesCreditMemo.closeTaxDetailsDialog();
        assertEquals(actualPostedCreditStatVertexTax,actualTaxAmountPostedCreditMemo);
    }

    /**
     * CDNAV-604
     * Conversion Workflow - Quote -> Order -> Posted Invoice -> Credit Memo -> Posted Credit memo
     * Tests creates a Sales Quote, converts it to an Order, posts the order,
     * copy posted invoice to a Credit Memo, and post the Credit Memo
     * @author  Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Smoke","D365_NAV_Regression"})
    public void creatingDocumentQuoteToOrderToPostedCreditMemoTest() {
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity="1";
        String expectedTaxAmount="60.05";

        //Navigate to Sales Quote page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesQuotesListPage salesQuotes =homePage.mainNavMenu.goToSubNavTab("Sales Quotes");
        NavSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();
        fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);

        newSalesQuote.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null,null, quantity, null, 1);
        newSalesQuote.activateRow(2);
        String expectedQuoteNumber = newSalesQuote.getCurrentSalesNumber();
        String actualTaxAmountQuote = newSalesQuote.getTotalTaxAmount();
        assertEquals(actualTaxAmountQuote, expectedTaxAmount);

        //Convert Quote to Order
        NavSalesOrderPage convertedSalesOrder=newSalesQuote.convertQuoteToOrder();
        String actualQuoteNumber = convertedSalesOrder.getQuoteNumber();
        assertEquals(actualQuoteNumber, expectedQuoteNumber);

        //Validate tax on UI and stat page
        String actualTotalTaxAmountOrder = convertedSalesOrder.getTotalTaxAmount();
        assertEquals(actualTotalTaxAmountOrder,expectedTaxAmount);
        navigateToStatWindow();
        String actualOrderStatTax= convertedSalesOrder.getVertexStatTaxAmount();
        convertedSalesOrder.closeTaxDetailsDialog();
        assertEquals(actualOrderStatTax, actualTotalTaxAmountOrder);

        //Post the order
        NavSalesInvoicePage postedInvoice=salesOrderSelectShipAndInvoiceThenPost(convertedSalesOrder);
        String actualQuoteNumberOnPostedInvoice = postedInvoice.getQuoteNumber();
        assertEquals(actualQuoteNumberOnPostedInvoice, expectedQuoteNumber);

        //Validate tax on UI field and Stat page
        String actualTotalTaxAmountPostedInvoice = convertedSalesOrder.getTotalTaxAmount();
        assertEquals(actualTotalTaxAmountPostedInvoice,expectedTaxAmount);
        navigateToStatWindow();
        String actualOrderStatTaxPostedInvoice= convertedSalesOrder.getVertexStatTaxAmount();
        convertedSalesOrder.closeTaxDetailsDialog();
        assertEquals(actualOrderStatTaxPostedInvoice, actualTotalTaxAmountPostedInvoice);

        String postedInvoiceNumber = postedInvoice.getCurrentSalesNumber();
        postedInvoice.clickBackAndSaveArrow();

        //Open credit Memo page
        NavAdminHomePage creditMemoHomePage = initializeTestPageAndSignOn();
        NavSalesCreditMemoListPage salesCreditMemo = creditMemoHomePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newSalesCreditMemo = salesCreditMemo.pageNavMenu.clickNew();
        fillInSalesMemoGeneralInfo(newSalesCreditMemo, customerCode);

        //Copy sales document
        copySalesDocumentToMemo(newSalesCreditMemo,"Posted Invoice",postedInvoiceNumber);
        newSalesCreditMemo.dialogBoxClickOk();
        String actualTotalTaxAmountCreditMemo = newSalesCreditMemo.getTotalTaxAmount();
        assertEquals(actualTotalTaxAmountCreditMemo,expectedTaxAmount);

        navigateToStatWindow();
        String actualSalesCreditStatVertexTax= newSalesCreditMemo.getVertexStatTaxAmount();
        newSalesCreditMemo.closeTaxDetailsDialog();
        assertEquals(actualSalesCreditStatVertexTax,actualTotalTaxAmountCreditMemo);

        //Post Credit Memo and validate tax on UI field and Stat page
        NavSalesCreditMemoPage postedMemo = postCreditMemo(newSalesCreditMemo);
        String actualTotalTaxAmountPostedCreditMemo = postedMemo.getTotalTaxAmount();
        assertEquals(actualTotalTaxAmountPostedCreditMemo, expectedTaxAmount);
        navigateToStatWindow();
        String actualPostedMemoStatVertexTax= newSalesCreditMemo.getVertexStatTaxAmount();
        newSalesCreditMemo.closeTaxDetailsDialog();
        assertEquals(actualPostedMemoStatVertexTax,actualTotalTaxAmountPostedCreditMemo);
    }
    /**
     * CDNAV-605
     * Conversion Workflow 3- Return Order->Posted credit Memo,
     * views the receipts on posted credit memo with Print and Preview
     * @author  Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Smoke","D365_NAV_Regression"})
    public void creatingDocumentReturnOrderToPostedCreditMemoTest() {
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity = "1";
        String expectedTaxAmount = "60.05";

        //Navigate to Sales Return Order
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesReturnOrderListPage sales_ReturnOrder = homePage.mainNavMenu.goToSubNavTab("Sales Return Orders");
        NavSalesReturnOrderPage newSalesReturnOrder = sales_ReturnOrder.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.expandTable();

        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesReturnOrder.activateRow(2);

        //Validate tax
        String actualTaxAmountReturnOrder = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(actualTaxAmountReturnOrder, expectedTaxAmount);
        openStatisticsWindowFromReturnOrder(newSalesReturnOrder);
        String actualVertexStatTax = newSalesReturnOrder.getVertexStatTaxAmount();
        newSalesReturnOrder.closeTaxDetailsDialog();
        assertEquals(actualTaxAmountReturnOrder, actualVertexStatTax);

        //Post the return order
        NavSalesCreditMemoPage postedCreditMemo = postReturnOrder(newSalesReturnOrder);

        //Validate tax on UI field and Stat page
        String actualTaxAmountPostedCredit = postedCreditMemo.getTotalTaxAmount();
        assertEquals(actualTaxAmountPostedCredit, expectedTaxAmount);
        navigateToStatWindow();
        String actualVertexStatTaxPostedCredit = newSalesReturnOrder.getVertexStatTaxAmount();
        newSalesReturnOrder.closeTaxDetailsDialog();
        assertEquals(actualTaxAmountPostedCredit, actualVertexStatTaxPostedCredit);

        //View Receipt
        postedCreditMemo.salesEditNavMenu.selectActionsTab();
        postedCreditMemo.salesEditNavMenu.selectPrint();
        //Click Preview and validate the tax
        NavPrintPreviewPage printPreview = postedCreditMemo.clickPrintPreview();
        String textOfPdf = printPreview.getPdfText();
        assertTrue(textOfPdf.contains("Total Tax\n" + expectedTaxAmount));
    }

    /**
     * CDNAV-670
     * Tests creates a Sales Quote, converts it to an Invoice, selects Alternate address, recalculates tax
     * posts the Invoice, copy to sales credit memo and post it
     * Quote → Invoice → Alternate address/ Custom Address → Recalc →Posted Invoice → Copy to Sales credit memo → Posted Credit memo
     * @author  Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Smoke", "D365_NAV_Regression"})
    public void creatingDocQuoteToPostedCreditMemoWithAlternateAddressTest(){
        String customerCode = "test1234";
        String itemNumber = "1900-S";
        String quantity="1";
        String expectedTaxAmount="16.55";
        String expectedAlternateAddTaxAmount="26.20";

        //Navigate to Sales Quote page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesQuotesListPage salesQuotes =homePage.mainNavMenu.goToSubNavTab("Sales Quotes");
        NavSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();
        fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);

        newSalesQuote.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null,null, quantity, null, 1);
        newSalesQuote.activateRow(2);
        fillInItemsTableInfo("G/L Account", "11200", null,null, quantity, "500", 2);
        newSalesQuote.activateRow(3);
        fillInItemsTableInfo("Resource", "MARK", null,null, quantity, null, 3);
        newSalesQuote.activateRow(4);
        fillInItemsTableInfo("Fixed Asset", "FA000050", null,null, quantity, "50", 4);

        //Validate total tax and tax in Statistics tab in Quote
        String actualQuoteTotalTaxAmount = newSalesQuote.getTotalTaxAmount();
        assertEquals(expectedTaxAmount,actualQuoteTotalTaxAmount);

        //convert Quote to Invoice and Validate tax
        NavSalesInvoicePage convertedSalesInvoice = salesDocumentMakeInvoice(newSalesQuote);
        String actualInvoiceTotalTaxAmount = convertedSalesInvoice.getTotalTaxAmount();
        assertEquals(expectedTaxAmount, actualInvoiceTotalTaxAmount);
        navigateToStatWindow();
        String actualInvoiceStatTax= convertedSalesInvoice.getVertexStatTaxAmount();
        convertedSalesInvoice.closeTaxDetailsDialog();
        assertEquals(actualInvoiceStatTax, actualInvoiceTotalTaxAmount);

        //Change to alternate shipping
        convertedSalesInvoice.openShippingAndBillingCategory();
        convertedSalesInvoice.selectAlternateShipToAddress("CA");
        openRecalculateTaxFromHomePage(convertedSalesInvoice);
        String actualTaxAmount = convertedSalesInvoice.getTotalTaxAmount();
        assertEquals(expectedAlternateAddTaxAmount, actualTaxAmount);
        navigateToStatWindow();
        String invoiceAlternateAddStatTax= convertedSalesInvoice.getVertexStatTaxAmount();
        convertedSalesInvoice.closeTaxDetailsDialog();
        assertEquals(expectedAlternateAddTaxAmount, invoiceAlternateAddStatTax);

        //Post Invoice and Validate tax amount in UI field and Stat Page
        NavSalesInvoicePage postedInvoice=postTheInvoice(convertedSalesInvoice);
        String actualPostedInvoiceTotalTaxAmount=postedInvoice.getTotalTaxAmount();
        assertEquals(expectedAlternateAddTaxAmount, actualPostedInvoiceTotalTaxAmount);
        navigateToStatWindow();
        String postedInvoiceAlternateAddStatTax= postedInvoice.getVertexStatTaxAmount();
        postedInvoice.closeTaxDetailsDialog();
        assertEquals(expectedAlternateAddTaxAmount, postedInvoiceAlternateAddStatTax);

        //get Posted Invoice number in Credit Memo and copy document
        String postedInvoiceNum = postedInvoice.getCurrentSalesNumber();
        postedInvoice.clickBackAndSaveArrow();
        NavAdminHomePage creditMemoHomePage = initializeTestPageAndSignOn();
        NavSalesCreditMemoListPage salesCreditMemo = creditMemoHomePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newSalesCreditMemo = salesCreditMemo.pageNavMenu.clickNew();
        fillInSalesMemoGeneralInfo(newSalesCreditMemo, customerCode);
        copySalesDocumentToMemo(newSalesCreditMemo,"Posted Invoice",postedInvoiceNum);
        newSalesCreditMemo.dialogBoxClickOk();
        String actualTaxAmountCreditMemo = newSalesCreditMemo.getTotalTaxAmount();
        assertEquals(expectedAlternateAddTaxAmount,actualTaxAmountCreditMemo);
        navigateToStatWindow();
        String actualCreditMemoStatVertexTax= newSalesCreditMemo.getVertexStatTaxAmount();
        newSalesCreditMemo.closeTaxDetailsDialog();
        assertEquals(actualCreditMemoStatVertexTax,actualTaxAmountCreditMemo);
        //Post credit memo and verify tax
        NavSalesCreditMemoPage postedMemo = postCreditMemo(newSalesCreditMemo);
        String actualTaxAmountPostedCreditMemo = postedMemo.getTotalTaxAmount();
        assertEquals(expectedAlternateAddTaxAmount, actualTaxAmountPostedCreditMemo);
        navigateToStatWindow();
        String actualPostedCreditStatVertexTax= postedMemo.getVertexStatTaxAmount();
        postedMemo.closeTaxDetailsDialog();
        assertEquals(actualPostedCreditStatVertexTax,actualTaxAmountPostedCreditMemo);
        String postedCreditMemoNumber = postedMemo.getCurrentSalesNumber();

        //Verify in XML
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(postedCreditMemoNumber);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
    }

}
