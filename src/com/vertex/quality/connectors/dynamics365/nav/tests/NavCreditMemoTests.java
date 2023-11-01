package com.vertex.quality.connectors.dynamics365.nav.tests;

import com.vertex.quality.connectors.dynamics365.nav.pages.*;
import com.vertex.quality.connectors.dynamics365.nav.tests.base.NavBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test class for Doc type: Sales Credit Memo
 * @author dpatel
 */
public class NavCreditMemoTests extends NavBaseTest
{
    /**
     * CDNAV-425
     * Creates a new sales order and copies it onto a sales memo
     * Posts the memo and verifies tax
     * @author bhikshapathi
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void createSalesCreditMemoTest( )  {
        String expectedTaxAmount = "60.05";
        String expectedTaxRate = "6.00";
        String customerCode = "Test PA";
        String itemNumber = "1896-S";
        String quantity = "1";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesCreditMemoListPage sales_memos = homePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newCreditMemo = sales_memos.pageNavMenu.clickNew();

        fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, "EAST", quantity, null, 1);

        String credtMemoNumber = newCreditMemo.getCurrentSalesNumber();

        //Navigate to Vertex Tax Details and Validate Tax information
        openVertexTaxDetailsWindow(newCreditMemo);
        String actualTaxRate = newCreditMemo.getTaxRate();
        String actualTaxAmount = newCreditMemo.getTaxAmount();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(expectedTaxRate, actualTaxRate);

        newCreditMemo.closeTaxDetailsDialog();

        NavSalesCreditMemoPage postedMemo = postCreditMemo(newCreditMemo);
        actualTaxAmount = postedMemo.getTotalTaxAmount();
        assertEquals(expectedTaxAmount, actualTaxAmount);

        String postedCreditMemoNumber = postedMemo.getCurrentSalesNumber();
        System.out.println(postedCreditMemoNumber);

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();

        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Responce
        adminPage.filterDocuments(postedCreditMemoNumber);

        //Tax Calc Reponse XML Verification
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<CalculatedTax>-60.05</CalculatedTax>"), "Calculated Tax Validation Failed");
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");

    }

    /**
     * CDNAV-240
     * Creates a new sales order through the copy document workflow
     * Verifies that the total tax and Vertex tax details are the same
     * @author mariosaint-fleur
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void createCopyDocumentAndCheckForCorrectTaxAmountTest(){
        String customerCode = "Test Pa";
        String expectedTaxAmount = "60.05";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesCreditMemoListPage salesCreditMemo = homePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newSalesCreditMemo = salesCreditMemo.pageNavMenu.clickNew();

        fillInSalesMemoGeneralInfo(newSalesCreditMemo, customerCode);

        String documentNumber = "PSI-103327";

        copySalesDocumentToMemo(newSalesCreditMemo, "Posted Invoice", documentNumber);
        newSalesCreditMemo.dialogBoxClickOk();
        //Navigate to Vertex Tax Details and Validate Tax information
        openVertexTaxDetailsWindow(newSalesCreditMemo);
        String actualTaxAmount = newSalesCreditMemo.getTaxAmount();

        assertEquals(expectedTaxAmount, actualTaxAmount);

        newSalesCreditMemo.closeTaxDetailsDialog();
    }
    /**
     * CDNAV-537
     * Validates if pop or alert appears on keeping Tax group code blank and user clicks Statistics Page
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_Deprecated"})
    public void missingTaxGroupCodePopupValidationForStatisticsTest() {
        String customerCode = "test1234";
        String itemNumber = "1906-S";
        String quantity="1";

        //Navigate to Sales Credit Memo page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesCreditMemoListPage salesCreditMemo = homePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newCreditMemo = salesCreditMemo.pageNavMenu.clickNew();
        fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);

        newCreditMemo.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newCreditMemo.exitExpandTable();

        String documentNum = newCreditMemo.getCurrentSalesNumber();
        navigateToStatWindow();

        String actualAlertMessage = newCreditMemo.getAlertMessageForTaxGroupCode();
        String lineNum = "10000";
        String expectedAlertMessage = newCreditMemo.createExpectedAlertMessageStrings("Credit Memo",documentNum,lineNum);
        assertEquals(actualAlertMessage,expectedAlertMessage,"Tax Group Code Statistics error validation Failed");

    }

    /**
     * CDNAV-540
     * Verifies that we can recalculate tax even if tax Group Code is missing
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void missingTaxGroupCodeValidationRecalculateTest() {
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity="1";

        //Navigate to Sales Credit Memo page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesCreditMemoListPage salesCreditMemo = homePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newCreditMemo = salesCreditMemo.pageNavMenu.clickNew();
        fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);

        newCreditMemo.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newCreditMemo.activateRow(2);

        String actualTaxAmountBefore = newCreditMemo.getTotalTaxAmount();
        clickRecalculateTax(newCreditMemo);

        //Tax amount before and after recalculate
        String actualTaxAmount = newCreditMemo.getTotalTaxAmount();
        assertEquals(actualTaxAmountBefore, actualTaxAmount);
    }

    /**
     * CDNAV-612
     * Statistics Functionality for Sales credit Memo
     * @author Shruti Jituri
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void checkStatisticsFuntionalityTest() {
        String customerCode="test1234";
        String quantity = "1";
        String itemNumber = "1896-S";

        //Navigate to Sales Credit Memo page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesCreditMemoListPage salesCreditMemo = homePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newCreditMemo = salesCreditMemo.pageNavMenu.clickNew();
        fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);

        newCreditMemo.expandTable();
        fillInItemsTableInfo("Item",itemNumber, null, null, quantity, null, 1);

        //Validate tax amount for vertex customer with Statistics functionality
        String actualTotalTaxAmount = newCreditMemo.getTotalTaxAmount();
        navigateToStatWindow();
        String actualVertexTax= newCreditMemo.getVertexStatTaxAmount();
        newCreditMemo.closeTaxDetailsDialog();
        assertEquals(actualTotalTaxAmount, actualVertexTax);
    }
    /**
     * CDNAV-621
     * Copy document functionality for Sales credit Memo, after Posting Sales Invoice
     * Enables the Include header and Recalculate line checkboxes
     * @author Shruti Jituri
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void createCopyDocumentAndEnableAllTest(){
        String customerCode = "test1234";
        String itemNumber="MARK";
        String quantity="1";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToChildSubNavTab("Sales Invoices");
        NavSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();
        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);

        newInvoice.expandTable();
        fillInItemsTableInfo("Resource", itemNumber, null, null, quantity, "50", 1);

        //Validate Tax
        String actualTotalTaxInvoice=newInvoice.getTotalTaxAmount();
        navigateToStatWindow();
        String actualVertexStatTaxInvoice= newInvoice.getVertexStatTaxAmount();
        newInvoice.closeTaxDetailsDialog();
        assertEquals(actualTotalTaxInvoice, actualVertexStatTaxInvoice);

        //Post Invoice and get the posted invoice number
        NavSalesInvoicePage postedInvoice=postTheInvoice(newInvoice);
        String postedInvoiceNumber = postedInvoice.getCurrentSalesNumber();
        //Validate Tax
        String actualTotalTaxPostedInv=newInvoice.getTotalTaxAmount();
        navigateToStatWindow();
        String actualVertexStatTaxPostedInv= newInvoice.getVertexStatTaxAmount();
        newInvoice.closeTaxDetailsDialog();
        assertEquals(actualTotalTaxPostedInv, actualVertexStatTaxPostedInv);
        postedInvoice.clickBackAndSaveArrow();

        NavSalesCreditMemoListPage salesCreditMemo = homePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newSalesCreditMemo = salesCreditMemo.pageNavMenu.clickNew();
        fillInSalesMemoGeneralInfo(newSalesCreditMemo, customerCode);

        //Copy Document on Credit Memo and enable all checkboxes
        copySalesDocumentToMemo(newSalesCreditMemo, "Posted Invoice", postedInvoiceNumber);
        enableIncludeHeaderAndRecalculateLines(newSalesCreditMemo);

        String actualTotalTaxCreditMemo=newSalesCreditMemo.getTotalTaxAmount();
        navigateToStatWindow();
        String actualVertexStatTaxCreditMemo= newSalesCreditMemo.getVertexStatTaxAmount();
        newInvoice.closeTaxDetailsDialog();
        assertEquals(actualTotalTaxCreditMemo, actualVertexStatTaxCreditMemo);

        //Post the Credit Memo
        NavSalesCreditMemoPage postedMemo = postCreditMemo(newSalesCreditMemo);
        String actualTaxAmountPostedMemo = postedMemo.getTotalTaxAmount();

        //Validate tax on Stat Page
        navigateToStatWindow();
        String actualStatVertexTaxPostedMemo = postedMemo.getVertexStatTaxAmount();
        newSalesCreditMemo.closeTaxDetailsDialog();
        assertEquals(actualTaxAmountPostedMemo, actualStatVertexTaxPostedMemo);
    }

    /**
     * CDNAV-620
     * Copy document functionality for Sales credit Memo,
     * disables the Include header and Recalculate line checkboxes
     *
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void createCopyDocumentAndDisableAllTest() {
        String customerCode = "test1234";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesCreditMemoListPage salesCreditMemo = homePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newSalesCreditMemo = salesCreditMemo.pageNavMenu.clickNew();
        fillInSalesMemoGeneralInfo(newSalesCreditMemo, customerCode);

        //Copy Document
        String documentNumber = "PSI-103011";
        copySalesDocumentToMemo(newSalesCreditMemo, "Posted Invoice", documentNumber);
        disableBothIncludeHeaderAndRecalculateLines(newSalesCreditMemo);

        //Validate Tax information
        String actualTaxAmountCreditMemo = newSalesCreditMemo.getTotalTaxAmount();
        //Navigate to statistics window
        navigateToStatWindow();
        String actualStatVertexTax= newSalesCreditMemo.getVertexStatTaxAmount();
        newSalesCreditMemo.closeTaxDetailsDialog();
        assertEquals(actualTaxAmountCreditMemo, actualStatVertexTax);

        //Post the Credit Memo
        NavSalesCreditMemoPage postedMemo = postCreditMemo(newSalesCreditMemo);
        String actualTaxAmountPostedMemo = postedMemo.getTotalTaxAmount();

        //Validate tax on Stat Page
        navigateToStatWindow();
        String actualStatVertexTaxPostedMemo = newSalesCreditMemo.getVertexStatTaxAmount();
        newSalesCreditMemo.closeTaxDetailsDialog();
        assertEquals(actualTaxAmountPostedMemo, actualStatVertexTaxPostedMemo);
    }
    /**
     * CDNAV-631
     * Create Sales credit Memo, post it
     * Validate the Statistics on posted credit memo
     *
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void createCreditMemoAndPostTest() {
        String customerCode = "test1234";
        String quantity = "5";
        String itemNumber = "1896-S";
        String expectedTax="300.24";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesCreditMemoListPage salesCreditMemo = homePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newSalesCreditMemo = salesCreditMemo.pageNavMenu.clickNew();
        fillInSalesMemoGeneralInfo(newSalesCreditMemo, customerCode);

        newSalesCreditMemo.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);

        //Validate Tax
        String actualTotalTaxCredit=newSalesCreditMemo.getTotalTaxAmount();
        navigateToStatWindow();
        String actualVertexStatTax= newSalesCreditMemo.getVertexStatTaxAmount();
        newSalesCreditMemo.closeTaxDetailsDialog();
        assertEquals(actualTotalTaxCredit, actualVertexStatTax);

        //Post Credit Memo and verify tax
        NavSalesCreditMemoPage postedCreditMemo = postCreditMemo(newSalesCreditMemo);
        String actualTaxAmountPostedCreditMemo = postedCreditMemo.getTotalTaxAmount();
        assertEquals(expectedTax, actualTaxAmountPostedCreditMemo);
        navigateToStatWindow();
        String actualPostedCreditStatVertexTax= newSalesCreditMemo.getVertexStatTaxAmount();
        newSalesCreditMemo.closeTaxDetailsDialog();
        assertEquals(actualPostedCreditStatVertexTax,actualTaxAmountPostedCreditMemo);

        //View Receipts
        postedCreditMemo.salesEditNavMenu.selectActionsTab();
        postedCreditMemo.salesEditNavMenu.selectPrint();
        //Click Preview and validate the tax
        NavPrintPreviewPage printPreview = postedCreditMemo.clickPrintPreview();
        String textOfPdf = printPreview.getPdfText();
        assertTrue(textOfPdf.contains("Total Tax\n" + expectedTax));
    }

    /**
     * CDNAV-636
     * Create a Sales credit memo, utilize copy document functionality which includes 'Comment' in line items
     * Validates if we are able to access Stat page
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void validateStatisticsPageForCommentTest() {
        String customerCode = "test1234";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesCreditMemoListPage salesCreditMemo = homePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newSalesCreditMemo = salesCreditMemo.pageNavMenu.clickNew();
        fillInSalesMemoGeneralInfo(newSalesCreditMemo, customerCode);

        //Copy Document
        String documentNumber = "PSI-103213";
        copySalesDocumentToMemo(newSalesCreditMemo, "Posted Invoice", documentNumber);
        newSalesCreditMemo.dialogBoxClickOk();
        String actualTaxAmountCreditMemo = newSalesCreditMemo.getTotalTaxAmount();
        navigateToStatWindow();
        String actualCreditMemoStatVertexTax= newSalesCreditMemo.getVertexStatTaxAmount();
        newSalesCreditMemo.closeTaxDetailsDialog();
        assertEquals(actualCreditMemoStatVertexTax,actualTaxAmountCreditMemo);

    }

    /**
     * CDNAV-232
     * Create a Sales credit memo, Recalculates and verifies tax
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void recalculateTaxAllProductTest() {
        String customerCode = "test1234";
        String quantity = "1";
        String itemNumber = "1896-S";
        String expectedTax = "100.01";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesCreditMemoListPage salesCreditMemo = homePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newSalesCreditMemo = salesCreditMemo.pageNavMenu.clickNew();
        fillInSalesMemoGeneralInfo(newSalesCreditMemo, customerCode);

        newSalesCreditMemo.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesCreditMemo.activateRow(2);
        fillInItemsTableInfo("G/L Account", "13300", null, null, quantity, "100", 2);
        newSalesCreditMemo.activateRow(3);
        fillInItemsTableInfo("Resource", "MARY", null, null, quantity, null, 3);
        newSalesCreditMemo.activateRow(4);
        fillInItemsTableInfo("Fixed Asset", "FA000040", null, null, quantity, "200", 4);
        newSalesCreditMemo.activateRow(5);
        fillInItemsTableInfo("Charge (Item)", "S-ALLOWANCE", null, null, quantity, "500", 5);

        //Update Price and Recalculate tax
        changedLineAmount("200", 2);
        clickRecalculateTax(newSalesCreditMemo);
        //Validate Tax
        String actualTotalTaxCredit = newSalesCreditMemo.getTotalTaxAmount();
        assertEquals(expectedTax, actualTotalTaxCredit);
        navigateToStatWindow();
        String actualVertexStatTax = newSalesCreditMemo.getVertexStatTaxAmount();
        newSalesCreditMemo.closeTaxDetailsDialog();
        assertEquals(actualTotalTaxCredit, actualVertexStatTax);
    }

    /**
     * CDNAV-181
     * Create a Sales credit memo, validates if negative values are generated for credit Memo in XML Response
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void verifyNegativeValuesForCreditMemoTest(){
        String customerCode="test1234";
        String itemNumber="1896-S";
        String quantity="1";
        String expectedTax="70.01";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesCreditMemoListPage sales_memos = homePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newCreditMemo = sales_memos.pageNavMenu.clickNew();

        fillInSalesMemoGeneralInfo(newCreditMemo, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, null, quantity, null,  1);
        newCreditMemo.activateRow(2);
        fillInItemsTableInfo("Resource","MARK", null, null, "2", null,  2);
        newCreditMemo.activateRow(3);
        String actualTotalTax=newCreditMemo.getTotalTaxAmount();
        assertEquals(expectedTax,actualTotalTax);

        //Post it
        NavSalesCreditMemoPage postedMemo = postCreditMemo(newCreditMemo);
        String postedCreditMemoNumber = postedMemo.getCurrentSalesNumber();
        String actualTotalTaxPostedMemo=newCreditMemo.getTotalTaxAmount();
        assertEquals(expectedTax,actualTotalTaxPostedMemo);

        //Open XML and verify the quantity, unit price & extended Price
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(postedCreditMemoNumber);

        //XML Response Verification
        adminPage.filterxml("Invoice Response");
        String xmlStrRes = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStrRes.contains("<SubTotal>-1166.8</SubTotal>\n" +
                "        <Total>-1236.81</Total>\n" +
                "        <TotalTax>-70.01</TotalTax>"), "Validation Failed");
        adminPage.closeAdminSection();

    }
    /**
     * CDNAV-671
     * Validates if total tax is refreshed with Alternate Address,
     * copying posted invoice with Sales Return Order and verify tax and XML
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void validateTotalTaxUIForCreditMemoTest(){
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity="1";
        String expectedTotalTax="95.08";

        //Navigate to Sales Invoice page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToChildSubNavTab("Sales Invoices");
        NavSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();
        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
        newInvoice.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);

        //Validate tax amount on UI and stat page
        String actualInvoiceTotalTaxAmount = newInvoice.getTotalTaxAmount();
        navigateToStatWindow();
        String actualInvoiceStatTax= newInvoice.getVertexStatTaxAmount();
        newInvoice.closeTaxDetailsDialog();
        assertEquals(actualInvoiceStatTax, actualInvoiceTotalTaxAmount);

        //Change to alternate shipping and Recalculate tax
        newInvoice.openShippingAndBillingCategory();
        newInvoice.selectAlternateShipToAddress("CA");
        openRecalculateTaxFromHomePage(newInvoice);

        //check total tax in UI field
        String actualTotalTaxAlternateShipping=newInvoice.getTotalTaxAmount();
        assertEquals(expectedTotalTax, actualTotalTaxAlternateShipping);

        //Post the invoice
        NavSalesInvoicePage postedInvoice = postTheInvoice(newInvoice);
        String actualPostedInvoiceTotalTaxAmount = postedInvoice.getTotalTaxAmount();
        assertEquals(expectedTotalTax, actualPostedInvoiceTotalTaxAmount);
        //Get Posted Invoice number
        String postedInvoiceNumber = postedInvoice.getCurrentSalesNumber();
        postedInvoice.clickBackAndSaveArrow();

        //Copy document to return order
        NavSalesReturnOrderListPage sales_ReturnOrder = homePage.mainNavMenu.goToSubNavTab("Sales Return Orders");
        NavSalesReturnOrderPage newSalesReturnOrder = sales_ReturnOrder.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.expandTable();
        copySalesDocumentToReturnOrder(newSalesReturnOrder, "Posted Invoice", postedInvoiceNumber);
        newSalesReturnOrder.dialogBoxClickOk();

        //Validate Tax on Return Order
        String actualTaxAmountReturnOrder = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(expectedTotalTax, actualTaxAmountReturnOrder);
        openStatisticsWindowFromReturnOrder(newSalesReturnOrder);
        String actualVertexTax= newSalesReturnOrder.getVertexStatTaxAmount();
        newSalesReturnOrder.closeTaxDetailsDialog();
        assertEquals(actualTaxAmountReturnOrder, actualVertexTax);

        //Post the return order
        NavSalesCreditMemoPage postedCreditMemo = postReturnOrder(newSalesReturnOrder);
        //Validate tax on UI field and Stat page
        String actualTaxAmountPostedCredit = postedCreditMemo.getTotalTaxAmount();
        assertEquals(actualTaxAmountPostedCredit, expectedTotalTax);
        navigateToStatWindow();
        String actualVertexStatTaxPostedCredit = newSalesReturnOrder.getVertexStatTaxAmount();
        newSalesReturnOrder.closeTaxDetailsDialog();
        assertEquals(actualTaxAmountPostedCredit, actualVertexStatTaxPostedCredit);
        String postedCreditMemoNum = postedCreditMemo.getCurrentSalesNumber();

        //Verify XML
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(postedCreditMemoNum);
        adminPage.filterxml("Invoice Response");
        String xmlStrRes = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStrRes.contains("<SubTotal>-1000.8</SubTotal>\n" +
                "        <Total>-1095.88</Total>\n" +
                "        <TotalTax>-95.08</TotalTax>"), "Validation Failed");
        adminPage.closeAdminSection();

    }

    /**
     * CDNAV-672
     * Validates if total tax UI field is refreshed with Custom Address,
     * copying posted invoice using sales credit memo & verify XML
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void validateTotalTaxUITest(){
        String customerCode = "Test PA";
        String itemNumber = "1900-S";
        String quantity="1";
        String expectedTaxAmount="18.32";

        //Navigate to Sales Invoice page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToChildSubNavTab("Sales Invoices");
        NavSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();
        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
        newInvoice.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);

        //Validate tax amount on UI and statistics page
        String actualInvoiceTotalTaxAmount = newInvoice.getTotalTaxAmount();
        navigateToStatWindow();
        String actualInvoiceStatTax= newInvoice.getVertexStatTaxAmount();
        newInvoice.closeTaxDetailsDialog();
        assertEquals(actualInvoiceStatTax, actualInvoiceTotalTaxAmount);

        //Change Shipping Address to Custom and validate tax after recalculate
        newInvoice.openShippingAndBillingCategory();
        newInvoice.selectCustomShipToAddress();
        fillInCustomAddressInShippingAndBilling("CAL","5950 S Broadway","Los Angeles","CA","90030");
        openRecalculateTaxFromHomePage(newInvoice);
        String actualTaxAmount = newInvoice.getTotalTaxAmount();
        assertEquals(actualTaxAmount, expectedTaxAmount);

        //Post the invoice
        NavSalesInvoicePage postedInvoice = postTheInvoice(newInvoice);
        String actualPostedInvoiceTotalTaxAmount = postedInvoice.getTotalTaxAmount();
        assertEquals(expectedTaxAmount, actualPostedInvoiceTotalTaxAmount);
        navigateToStatWindow();
        String actualStatTaxPostedInvoice= postedInvoice.getVertexStatTaxAmount();
        postedInvoice.closeTaxDetailsDialog();
        assertEquals(actualStatTaxPostedInvoice, actualPostedInvoiceTotalTaxAmount);

        //Get Posted Invoice number
        String postedInvoiceNumber = postedInvoice.getCurrentSalesNumber();
        postedInvoice.clickBackAndSaveArrow();

        NavSalesCreditMemoListPage salesCreditMemo = homePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newSalesCreditMemo = salesCreditMemo.pageNavMenu.clickNew();
        fillInSalesMemoGeneralInfo(newSalesCreditMemo, customerCode);

        //Copy document using Sales credit memo and verify tax
        copySalesDocumentToMemo(newSalesCreditMemo, "Posted Invoice", postedInvoiceNumber);
        newSalesCreditMemo.dialogBoxClickOk();
        String actualTotalTaxCreditMemo=newSalesCreditMemo.getTotalTaxAmount();
        navigateToStatWindow();
        String actualVertexStatTaxCreditMemo= newSalesCreditMemo.getVertexStatTaxAmount();
        newInvoice.closeTaxDetailsDialog();
        assertEquals(actualTotalTaxCreditMemo, actualVertexStatTaxCreditMemo);

        //Post Memo and validate tax
        NavSalesCreditMemoPage postedMemo = postCreditMemo(newSalesCreditMemo);
        String actualTaxAmountPostedMemo = postedMemo.getTotalTaxAmount();
        navigateToStatWindow();
        String actualStatVertexTaxPostedMemo = postedMemo.getVertexStatTaxAmount();
        newSalesCreditMemo.closeTaxDetailsDialog();
        assertEquals(actualTaxAmountPostedMemo, actualStatVertexTaxPostedMemo);
        String postedCreditMemoNum = postedMemo.getCurrentSalesNumber();

        //Validate XML
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(postedCreditMemoNum);
        adminPage.filterxml("Invoice Response");
        String xmlStrRes = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStrRes.contains("<SubTotal>-192.8</SubTotal>\n" +
                "        <Total>-211.12</Total>\n" +
                "        <TotalTax>-18.32</TotalTax>"), "Validation Failed");
        adminPage.closeAdminSection();
    }


    /**
     * CDNAV-698
     * Validates if we are able to access stat page for previously created document without making
     * multiple service calls
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void validateServiceCallCreditMemoTest(){
        String documentNo="SC-R1041";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesCreditMemoListPage salesCreditMemo = homePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newSalesCreditMemo =new NavSalesCreditMemoPage(driver);

        //Filter already created document and access stat page
        salesCreditMemo.filterDocuments(documentNo);
        navigateToStatWindow();
        newSalesCreditMemo.closeTaxDetailsDialog();
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(documentNo);

        //Verify if current date service call exists in XML Log section
        String dateVal= adminPage.checkingDate();
        String currentTime=adminPage.getTime();
        assertTrue(!dateVal.contains(currentTime),"validation failed since service call made");

    }

    /**
     * CDNAV-205
     * Verify the posted credit memo Physical origin, Administrative origin
     * and Destination address in XML
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void verifyTheAddressTest() {
        String customerCode = "test1234";
        String expectedTax="60.05";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesCreditMemoListPage salesCreditMemo = homePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newSalesCreditMemo = salesCreditMemo.pageNavMenu.clickNew();
        fillInSalesMemoGeneralInfo(newSalesCreditMemo, customerCode);
        String documentNumber = "PSI-103242";
        copySalesDocumentToMemo(newSalesCreditMemo, "Posted Invoice", documentNumber);
        newSalesCreditMemo.dialogBoxClickOk();
        String actualTotalTax=newSalesCreditMemo.getTotalTaxAmount();
        assertEquals(expectedTax,actualTotalTax);
        NavSalesCreditMemoPage postedMemo = postCreditMemo(newSalesCreditMemo);
        String actualTaxAmount = postedMemo.getTotalTaxAmount();
        assertEquals(expectedTax, actualTaxAmount);

        //Navigate to admin page and verify address in XML
        String postedCreditMemoNum = postedMemo.getCurrentSalesNumber();
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(postedCreditMemoNum);
        adminPage.filterxml("Invoice Response");
        String xmlStrRes = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStrRes.contains("<PhysicalOrigin taxAreaId=\"140318411\">\n" +
                "              <StreetAddress1>520 N Michigan Ave</StreetAddress1>\n" +
                "              <City>Chicago</City>\n" +
                "              <MainDivision>IL</MainDivision>\n" +
                "              <PostalCode>60611-6982</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </PhysicalOrigin>"), "Physical Origin validation Failed");
        assertTrue(xmlStrRes.contains("<AdministrativeOrigin taxAreaId=\"113210000\">\n" +
                "              <StreetAddress1>7122 South Ashford Street</StreetAddress1>\n" +
                "              <City>Atlanta</City>\n" +
                "              <MainDivision>GA</MainDivision>\n" +
                "              <PostalCode>31772</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </AdministrativeOrigin>"), "Administrative origin validation Failed");
        assertTrue(xmlStrRes.contains("<Destination taxAreaId=\"390290000\">\n" +
                "              <StreetAddress1>400 Simpson Dr</StreetAddress1>\n" +
                "              <City>Chester Springs</City>\n" +
                "              <MainDivision>PA</MainDivision>\n" +
                "              <PostalCode>19425-9546</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>"), "Destination Address validation Failed");
            }
    /**
     * CDNAV-204
     * Verify that document type with only comment does not interact with O-series
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void verifyCommentOnlyTest(){
        String customerCode = "test1234";
        String expectedPopMsg="No tax to recalculate on current document";
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesCreditMemoListPage salesCreditMemo = homePage.mainNavMenu.goToSubNavTab("Sales Credit Memos");
        NavSalesCreditMemoPage newSalesCreditMemo = salesCreditMemo.pageNavMenu.clickNew();
        fillInSalesMemoGeneralInfo(newSalesCreditMemo, customerCode);
        fillInItemsTableInfo("Comment", "SC", null, null, null, null, 1);
        newSalesCreditMemo.activateRow(2);
        clickRecalculateTax(newSalesCreditMemo);
        String actualPopupMsg=newSalesCreditMemo.dialogBoxReadMessage();
        assertEquals(expectedPopMsg,actualPopupMsg);
        newSalesCreditMemo.dialogBoxClickOk();
        String creditMemoNumber = newSalesCreditMemo.getCurrentSalesNumber();

        //Navigate to vertex admin page and verify if no XMl is generated
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(creditMemoNumber);
        String noXmlStr = adminPage.checkNoXmlLog();
        assertTrue(noXmlStr.contains("(There is nothing to show in this view)"), "XML Validation Failed and XMl exists");

    }
}
