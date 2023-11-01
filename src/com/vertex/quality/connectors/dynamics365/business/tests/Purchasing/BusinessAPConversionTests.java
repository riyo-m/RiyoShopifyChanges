package com.vertex.quality.connectors.dynamics365.business.tests.Purchasing;

import com.vertex.quality.connectors.dynamics365.business.enums.BusinessMainMenuNavTabs;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessAdminHomePage;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessVertexAdminPage;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.*;
import com.vertex.quality.connectors.dynamics365.business.tests.base.BusinessBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * contains tests for different conversion workflow
 * @author Shruti
 * */

@Listeners(TestRerunListener.class)
public class BusinessAPConversionTests extends BusinessBaseTest {
    BusinessAdminHomePage homePage;
    /**
     * CDBC-1060
     * Converts Quote to order, post the invoice copy to credit memo and post it and verify taxes
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Smoke"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxOnConversionWorkflowTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";
        String expectedTax = "44.50";
        List<String> expectedTaxAmounts=Arrays.asList("20","10","9.5","5");
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseQuotesListPage purchaseQuotes = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Quotes");
        BusinessPurchaseQuotePage newPurchaseQuote = purchaseQuotes.pageNavMenu.clickNew();
        fillInPurchaseQuoteGeneralInfo(newPurchaseQuote, vendorCode);
        newPurchaseQuote.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newPurchaseQuote.activateRow(2);
        newPurchaseQuote.exitExpandTable();

        //Verify tax in total tax UI field and vertex tax details
        String actualTotalTax = newPurchaseQuote.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTax);
        openVertexTaxDetailsWindowFromReturnOrder(newPurchaseQuote);
        List<String> actualTaxAmounts=newPurchaseQuote.getMultipleTaxAmount("Purchase");
        assertEquals(actualTaxAmounts, expectedTaxAmounts);
        newPurchaseQuote.closeTaxDetailsDialog();

        //Make Order and verify taxes
        BusinessPurchaseOrderPage convertedPurchaseOrder=newPurchaseQuote.convertQuoteToOrder();
        String actualConvertedTax= convertedPurchaseOrder.getTotalTaxAmount();
        assertEquals(actualConvertedTax, expectedTax);
        openVertexTaxDetailsWindowFromReturnOrder(convertedPurchaseOrder);
        List<String> actualTaxAmountsOrder=convertedPurchaseOrder.getMultipleTaxAmount("Purchase");
        assertEquals(actualTaxAmountsOrder, expectedTaxAmounts);
        convertedPurchaseOrder.closeTaxDetailsDialog();
        String expectedOrderNumber = convertedPurchaseOrder.getCurrentSalesNumber();
        convertedPurchaseOrder.enterVendorInvoiceNo(expectedOrderNumber);

        //post the document and verify taxes
        BusinessPurchaseInvoicePage postedInvoice = purchaseOrderPostTheInvoice(convertedPurchaseOrder);
        String actualPostedInvoiceTax= convertedPurchaseOrder.getTotalTaxAmount();
        assertEquals(actualPostedInvoiceTax, expectedTax);
        openVertexTaxDetailsWindowFromReturnOrder(postedInvoice);
        List<String> actualTaxAmountsPostedInvoice=postedInvoice.getMultipleTaxAmount("Purchase");
        assertEquals(actualTaxAmountsPostedInvoice, expectedTaxAmounts);
        postedInvoice.closeTaxDetailsDialog();
        String postedInvoiceNumber = postedInvoice.getCurrentSalesNumber();
        postedInvoice.clickBackAndSaveArrow();

        //Open credit memo and Copy document
        BusinessPurchaseCreditMemoListPage purchaseCreditMemo = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Credit Memos");
        BusinessPurchaseCreditMemoPage newCreditMemo = purchaseCreditMemo.pageNavMenu.clickNew();
        fillInPurchaseCreditMemoGeneralInfo(newCreditMemo, vendorCode);
        copySalesDocumentInPurchaseCreditMemo(newCreditMemo,"Posted Invoice", postedInvoiceNumber);
        String actualCreditMemoTax= newCreditMemo.getTotalTaxAmount();
        assertEquals(actualCreditMemoTax, expectedTax);
        openVertexTaxDetailsWindowFromReturnOrder(newCreditMemo);
        List<String> actualTaxAmountsCreditMemo=newCreditMemo.getMultipleTaxAmount("Purchase");
        assertEquals(actualTaxAmountsCreditMemo, expectedTaxAmounts);
        newCreditMemo.closeTaxDetailsDialog();
        String creditMemoNo =  newCreditMemo.getCurrentSalesNumber();
        newCreditMemo.enterCreditMemoNo(creditMemoNo);
        //Post credit memo
        BusinessPurchaseCreditMemoPage postedCreditMemo=postPurchaseCreditMemo(newCreditMemo);
        String actualPostedTax=postedCreditMemo.getTotalTaxAmount();
        assertEquals(actualPostedTax, expectedTax);
        openVertexTaxDetailsWindowFromReturnOrder(postedCreditMemo);
        List<String> actualTaxAmountsPostedMemo=postedCreditMemo.getMultipleTaxAmount("Purchase");
        assertEquals(actualTaxAmountsPostedMemo, expectedTaxAmounts);
        postedCreditMemo.closeTaxDetailsDialog();
        String postedCreditMemoNo =  newCreditMemo.getCurrentSalesNumber();
        System.out.println(postedCreditMemoNo);
        //Verify XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(postedCreditMemoNo);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>-44.5</TotalTax>"), "Total Tax  Validation Failed");
    }

    /**
     * CDBC-1108
     * Converts Quote to order, post the document, Post Document lines to reverse in Return order and verify taxes
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxOnReturnOrderConversionWorkflowTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";
        String expectedTax = "44.50";
        List<String> expectedTaxAmounts = Arrays.asList("20", "10","9.5","5");
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseQuotesListPage purchaseQuotes = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Quotes");
        BusinessPurchaseQuotePage newPurchaseQuote = purchaseQuotes.pageNavMenu.clickNew();
        fillInPurchaseQuoteGeneralInfo(newPurchaseQuote, vendorCode);
        newPurchaseQuote.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newPurchaseQuote.activateRow(2);
        newPurchaseQuote.exitExpandTable();

        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseQuote.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTax);
        //Make Order
        BusinessPurchaseOrderPage convertedPurchaseOrder = newPurchaseQuote.convertQuoteToOrder();
        openVertexTaxDetailsWindow(convertedPurchaseOrder);
        List<String> actualTotalTaxes = convertedPurchaseOrder.getMultipleTaxAmount("Purchase");
        assertEquals(expectedTaxAmounts, actualTotalTaxes);
        convertedPurchaseOrder.closeTaxDetailsDialog();
        String actualConvertedTax = convertedPurchaseOrder.getTotalTaxAmount();
        assertEquals(actualConvertedTax, expectedTax);
        String expectedOrderNumber = convertedPurchaseOrder.getCurrentSalesNumber();
        convertedPurchaseOrder.enterVendorInvoiceNo(expectedOrderNumber);
        //post the document
        BusinessPurchaseInvoicePage postedInvoice = purchaseOrderPostTheInvoice(convertedPurchaseOrder);
        String actualPostedInvoiceTax = convertedPurchaseOrder.getTotalTaxAmount();
        assertEquals(actualPostedInvoiceTax, expectedTax);
        String postedInvoiceNumber = postedInvoice.getCurrentSalesNumber();
        postedInvoice.clickBackAndSaveArrow();
        //Return Order
        BusinessPurchaseReturnOrdersListPage purchaseReturnOrder = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Return Orders");
        BusinessPurchaseReturnOrderPage newReturnOrder = purchaseReturnOrder.pageNavMenu.clickNew();
        fillInPurchaseReturnOrderInfo(newReturnOrder, vendorCode);
        //Select Process tab and filter document
        newReturnOrder.salesEditNavMenu.clickPrepareButton();
        newReturnOrder.selectPostedDocumentLinesToReverse();
        newReturnOrder.enableReturnOriginalQuantity();
        newReturnOrder.filterDocuments(postedInvoiceNumber);
        newReturnOrder.dialogBoxClickOkInAP();
        newReturnOrder.expandTable();
        String documentNo =  newReturnOrder.getCurrentSalesNumber();
        String actualTotalTaxAmount =  newReturnOrder.getTotalTaxAmount();
        assertEquals(expectedTax,actualTotalTaxAmount);
        //Verify XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(documentNo);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>44.5</TotalTax>"), "Total Tax  Validation Failed");
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
