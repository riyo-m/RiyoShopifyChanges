package com.vertex.quality.connectors.dynamics365.nav.tests;

import com.vertex.quality.connectors.dynamics365.nav.pages.*;
import com.vertex.quality.connectors.dynamics365.nav.tests.base.NavBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class NavSalesQuotesTests extends NavBaseTest
{
    /**
     * CDNAV-446
     * Create Sales Quote and change to Invoice
     * @author bhikshapathi
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void CreateSalesQuoteAndChangeToInvoiceTest() {
        String expectedTaxAmount = "60.05";
        String expectedTaxRate = "6.00";
        String customerCode = "Test PA";
        String itemNumber = "1896-S";
        String quantity = "1";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesQuotesListPage salesQuotes =homePage.mainNavMenu.goToSubNavTab("Sales Quotes");
        NavSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();

        fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, null, quantity, null, 1);

        openVertexTaxDetailsWindowFromInvoice(newSalesQuote);
        String actualTaxRate = newSalesQuote.getTaxRate();
        String actualTaxAmount = newSalesQuote.getTaxAmount();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(expectedTaxRate, actualTaxRate);

        newSalesQuote.closeTaxDetailsDialog();
        String postedQuoteNum=newSalesQuote.getCurrentSalesNumber();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Responce
        adminPage.filterDocuments(postedQuoteNum);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
        adminPage.closeAdminSection();
        adminPage.closeAdminSection();

        // Create Invoice
        NavSalesInvoicePage postedInvoice = salesDocumentMakeInvoice(newSalesQuote);

        openVertexTaxDetailsWindowFromInvoice(postedInvoice);

        actualTaxRate = postedInvoice.getTaxRate();
        actualTaxAmount = postedInvoice.getTaxAmount();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);

        newSalesQuote.closeTaxDetailsDialog();
        postedInvoice.salesOrderSelectShipAndInvoiceThenPost();

        String postedInvoiceOrderNum = postedInvoice.getCurrentSalesNumber();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        //Tax Calc Request and Responce
        adminPage.filterDocuments(postedInvoiceOrderNum);
        adminPage.filterxml("Invoice Response");
        String invoiceXmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(invoiceXmlStr.contains("<TotalTax>60.05</TotalTax>"), "Total Tax Validation Failed");
        assertTrue(invoiceXmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
    }

    /**
     * CDNAV-501
     * Tests Statistics Functionality for Sales Quote TaxAmount for all PRODUCTS
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void compareSalesQuoteTaxAmountAllProductsInStatisticsTest(){

        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity = "1";
        String nontaxableCode = "SUPPLIES";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesQuotesListPage salesQuotes =homePage.mainNavMenu.goToSubNavTab("Sales Quotes");
        NavSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();
        fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);
        newSalesQuote.expandTable();
        fillInItemsTableInfo("Item",itemNumber, null, null, quantity, null, 1);
        newSalesQuote.activateRow(2);
        fillInItemsTableInfo("G/L Account", "14100", null, null, quantity, "50", 2);
        newSalesQuote.activateRow(3);
        fillInItemsTableInfo("Resource", "MARY", null, null, quantity, null, 3);
        newSalesQuote.activateRow(4);
        fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, null, quantity, "50", 4);
        newSalesQuote.activateRow(5);
        fillInItemsTableInfo("Fixed Asset", "FA000100", null, null, quantity, "15", 5);
        newSalesQuote.exitExpandTable();

        //Navigate to Statistics and get Sales Quote TaxAmount
        navigateToStatWindow();
        String actualTaxAmount1 = newSalesQuote.getVertexStatTaxAmount();
        newSalesQuote.closeTaxDetailsDialog();
        String actualTotalTaxAmount = newSalesQuote.getTotalTaxAmount();
        assertEquals(actualTotalTaxAmount,actualTaxAmount1);
    }

    /**
     * CDNAV-503
     * Tests Statistics Functionality for Sales Quote TaxAmount for all PRODUCTS for NO Vertex (Atlanta,GA)
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void compareSalesQuoteTaxAmountAllProductsInStatisticsNoVertexTest(){

        String customerCode = "cust_NOVERTEX";
        String itemNumber = "1896-S";
        String quantity = "1";
        String nontaxableCode = "SUPPLIES";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesQuotesListPage salesQuotes =homePage.mainNavMenu.goToSubNavTab("Sales Quotes");
        NavSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();
        fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);
        newSalesQuote.expandTable();
        fillInItemsTableInfo("Item",itemNumber, null, null, quantity, null, 1);
        newSalesQuote.activateRow(2);
        fillInItemsTableInfo("G/L Account", "14100", null, null, quantity, "50", 2);
        newSalesQuote.activateRow(3);
        fillInItemsTableInfo("Resource", "MARY", null, null, quantity, null, 3);
        newSalesQuote.activateRow(4);
        fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, null, quantity, "50", 4);
        newSalesQuote.activateRow(5);
        fillInItemsTableInfo("Fixed Asset", "FA000100", null, null, quantity, "15", 5);
        newSalesQuote.exitExpandTable();

        //Navigate to Statistics and get Sales Quote TaxAmount
        navigateToStatWindow();
        String actualTaxAmount1 = newSalesQuote.getNoVertexTaxAmount();
        newSalesQuote.closeTaxDetailsDialog();
        String actualTotalTaxAmount = newSalesQuote.getTotalTaxAmount();
        assertEquals(actualTotalTaxAmount,actualTaxAmount1);
    }

    /**
     * CDNAV-538
     * Validates if pop or alert appears on keeping Tax group code blank and user clicks Statistics Page
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_Deprecated"})
    public void missingTaxGroupCodePopupValidationForStatisticsTest() {
        String customerCode = "Test Phila";
        String itemNumber = "1900-S";
        String quantity="1";

        //Navigate to Sales Quote page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesQuotesListPage salesQuotes =homePage.mainNavMenu.goToSubNavTab("Sales Quotes");
        NavSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();
        fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);

        newSalesQuote.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesQuote.exitExpandTable();

        String documentNum = newSalesQuote.getCurrentSalesNumber();
        navigateToStatWindow();

        String actualAlertMessage = newSalesQuote.getAlertMessageForTaxGroupCode();
        String lineNum = "10000";
        String expectedAlertMessage = newSalesQuote.createExpectedAlertMessageStrings("Quote",documentNum,lineNum);
        assertEquals(actualAlertMessage,expectedAlertMessage,"Tax Group Code Statistics error validation Failed");

    }
    /**
     * CDNAV-542
     * Create a Sales Quote,keep tax group code as blank and verify that we can recalculate tax
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void missingTaxGroupCodeValidateOnRecalculateTest() {
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity="1";
        String expectedTaxAmount="82.07";

        //Navigate to Sales Quote page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesQuotesListPage salesQuotes =homePage.mainNavMenu.goToSubNavTab("Sales Quotes");
        NavSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();
        fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);

        newSalesQuote.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesQuote.activateRow(2);

        //Change Shipping Address
        newSalesQuote.openShippingAndBillingCategory();
        newSalesQuote.selectCustomShipToAddress();
        fillInCustomAddressInShippingAndBilling("COL","1575 Space Center Drive","Colorado Springs","CO","80915");

        openRecalculateTaxFromHomePage(newSalesQuote);

        String actualTaxAmount = newSalesQuote.getTotalTaxAmount();
        System.out.print(actualTaxAmount);
        assertEquals(expectedTaxAmount, actualTaxAmount);
    }
    /**
     * CDNAV-403
     * Create a Sales Quote, verifies the customer is exempt from tax for taxable item
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void createSalesQuoteForCustomerClassExemptionTest() {
        String customerCode = "Geo Mason";
        String itemNumber = "1896-S";
        String quantity="1";
        String expectedTaxAmount="0.00";

        //Navigate to Sales Quote page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesQuotesListPage salesQuotes =homePage.mainNavMenu.goToSubNavTab("Sales Quotes");
        NavSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();
        fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);

        newSalesQuote.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null,null, quantity, null, 1);
        newSalesQuote.activateRow(2);

        String actualTaxAmount = newSalesQuote.getTotalTaxAmount();
        assertEquals(expectedTaxAmount, actualTaxAmount);

        String quoteNum=newSalesQuote.getCurrentSalesNumber();

        //Navigate to admin page and verify XML
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(quoteNum);
        adminPage.filterxml("Tax Calc Request");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<CustomerCode classCode=\"CUST_TEST\">"), "Customer Class Validation Failed");
        adminPage.closeAdminSection();
      }
    /**
     * CDNAV-635
     * Create a Sales Quote, verifies that we are able to access the Statistics page even if we add in comment item type
     *
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void validateStatisticsForCommentTest() {
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity = "2";
        String expectedTaxAmount = "120.10";

        //Navigate to Sales Quote page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab("Sales Quotes");
        NavSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();
        fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);

        newSalesQuote.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null,null, quantity, null, 1);
        newSalesQuote.activateRow(2);
        fillInItemsTableInfo("Comment", "SC", null, null, null, null, 2);
        newSalesQuote.activateRow(3);
        fillInItemsTableInfo("Comment", "MD", null, null, null, null, 3);
        newSalesQuote.activateRow(3);
        navigateToStatWindow();
        String actualStatVertexTax = newSalesQuote.getVertexStatTaxAmount();
        newSalesQuote.closeTaxDetailsDialog();
        assertEquals(expectedTaxAmount, actualStatVertexTax);
    }

    }
