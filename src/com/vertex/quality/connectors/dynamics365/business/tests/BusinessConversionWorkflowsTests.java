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

@Listeners(TestRerunListener.class)
public class BusinessConversionWorkflowsTests extends BusinessBaseTest {

    BusinessAdminHomePage homePage;

    /**
     * Tests creating a sales quote, converting it to an order, posting the invoice,
     *  copying that to a credit memo, posting the memo
     *  C365BC-507
     */
    @Test(alwaysRun = true, groups = { "D365_Business_Central_Regression"}, retryAnalyzer = TestRerun.class)
    public void documentsConversionWorkflowTest( )
    {
        String expectedTaxAmount = "24,000.00";
        String customerCode = "Upgrade16 cust";
        String itemNumber = "1896-S";
        String quantity = "1000";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
        BusinessSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();

        fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);
        newSalesQuote.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, "100", 1);
        newSalesQuote.activateRow(2);
        fillInItemsTableInfo("G/L Account", "10100", null, null, quantity, "100", 2);
        newSalesQuote.activateRow(3);
        fillInItemsTableInfo("Resource", "MARY", null, null, quantity, "100", 3);
        newSalesQuote.activateRow(4);
        fillInItemsTableInfo("Fixed Asset", "FA000120", null, null, quantity, "100", 4);
        newSalesQuote.activateRow(5);
        newSalesQuote.exitExpandTable();

        String expectedQuoteNumber = newSalesQuote.getCurrentSalesNumber();
        openVertexTaxDetailsWindow(newSalesQuote);
        newSalesQuote.closeTaxDetailsDialog();
        String actualTaxAmount = newSalesQuote.getTotalTaxAmount();
        assertEquals(actualTaxAmount, expectedTaxAmount);

        BusinessSalesOrderPage convertedSalesOrder = newSalesQuote.convertQuoteToOrder();

        String actualQuoteNumber = convertedSalesOrder.getQuoteNumber();
        openVertexTaxDetailsWindow(convertedSalesOrder);
        convertedSalesOrder.closeTaxDetailsDialog();
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

        BusinessSalesCreditMemoPage postedMemo = postCreditMemo(newCreditMemo);
        actualTaxAmount = postedMemo.getTotalTaxAmount();
        assertEquals(actualTaxAmount, expectedTaxAmount);
    }

    /**
     * C365BC-506
     * Conversion workflow for Sales Return Order (With Qty 1000)
     */
    @Test(alwaysRun = true, groups = { "D365_Business_Central_Regression"}, retryAnalyzer = TestRerun.class)
    public void documentsConversionSalesReturnOrderTest()
    {
        String customerCode = "Test Phila";
        String itemNumber = "1896-S";
        String quantity = "1000";
        String nontaxableCode = "FURNITURE";
        String expectedTaxAmount ="126,048.00";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesReturnOrderListPage returnOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SALES.value,
                "Sales Return Orders");
        BusinessSalesReturnOrderPage newSalesReturnOrder = returnOrders.pageNavMenu.clickNew();

        fillInSalesReturnOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, "MAIN", quantity, null, 1);
        newSalesReturnOrder.activateRow(2);
        fillInItemsTableInfo("G/L Account","10100", null, "MAIN", quantity, "100", 2);
        newSalesReturnOrder.activateRow(3);
        fillInItemsTableInfo("Resource","MARY", null, "MAIN", quantity, "1000", 3);
        newSalesReturnOrder.activateRow(4);
        newSalesReturnOrder.exitExpandTable();
        String actualTaxableAmount = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(actualTaxableAmount, expectedTaxAmount);
        String returnOrderNum = newSalesReturnOrder.getCurrentSalesNumber();
        BusinessSalesCreditMemoPage newCreditMemo = new BusinessSalesCreditMemoPage(driver);
        BusinessSalesCreditMemoPage postedMemo =postSalesReturnOrder();
        openVertexTaxDetailsWindow(postedMemo);
        postedMemo.closeTaxDetailsDialog();
        String actualTaxAmount = postedMemo.getTotalTaxAmount();
        assertEquals(actualTaxAmount, expectedTaxAmount);
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


