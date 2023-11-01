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
@Listeners(TestRerunListener.class)
public class BusinessPurchaseReturnOrderTests extends BusinessBaseTest {
    BusinessAdminHomePage homePage;
    /**
     *  CDBC-938
     *  Verify the tax for Purchase Return Order, Recalculate the taxes on default and changing addresses
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyVendorForReturnOrderTest() {
        String vendorCode = "TestVendorCA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String itemNo2="1900-S";
        String quantity = "1";
        String expectedTax="40.00";
        String location="EAST";
        int expectedCalls=6;
        String expectedTaxForCustom="49.02";
        int expectedCallsForCustom=10;
        String expectedTaxForAlternate="51.81";
        int expectedCallsForAlternate=12;
        List<String> expectedTaxAmounts=Arrays.asList("20","20","9.02");

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseReturnOrdersListPage purchaseReturnOrder = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Return Orders");
        BusinessPurchaseReturnOrderPage newReturnOrder = purchaseReturnOrder.pageNavMenu.clickNew();
        fillInPurchaseReturnOrderInfo(newReturnOrder, vendorCode);
        newReturnOrder.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, location, quantity, null, 1);
        newReturnOrder.activateRow(2);
        fillInItemsTableInfo(itemType, itemNo2, null, null, quantity, null, 2);
        newReturnOrder.activateRow(3);
        newReturnOrder.exitExpandTable();

        //Verify tax and get Doc number
        String actualTotalTax = newReturnOrder.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTax);
        String returnOrderNumber = newReturnOrder.getCurrentSalesNumber();
        recalculateTax(newReturnOrder);

        //Check XML calls on admin page
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(returnOrderNumber);
        int calls=adminPage.getResponseCount(returnOrderNumber);
        assertEquals(calls,expectedCalls);
        newReturnOrder.clickBackAndSaveArrow();

        //Change address to Custom Address & recalculate
        newReturnOrder.openShippingAndPaymentCategory();
        newReturnOrder.selectCustomInShipToAddressForPurchase();
        newReturnOrder.clickShowMore();
        fillInCustomAddressInShippingAndBilling("310 Collins Rd", "Cedar Rapids", "IA", "52402");
        recalculateTax(newReturnOrder);
        newReturnOrder.dialogBogClickOkOnLocationChange();

        //Open Vertex tax details window
        openVertexTaxDetailsWindow(newReturnOrder);
        List<String> actualTaxAmounts = newReturnOrder.getMultipleTaxAmount("Purchase");
        assertEquals(actualTaxAmounts, expectedTaxAmounts);
        newReturnOrder.closeTaxDetailsDialog();
        //Verify tax
        String actualTotalTaxForCustom = newReturnOrder.getTotalTaxAmount();
        assertEquals(actualTotalTaxForCustom, expectedTaxForCustom);
        //Verify calls and tax in XML
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.filterDocuments(returnOrderNumber);
        int callsForCustom=adminPage.getResponseCount(returnOrderNumber);
        assertEquals(callsForCustom,expectedCallsForCustom);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>49.02</TotalTax>"), " Tax Validation Failed");
        newReturnOrder.clickBackAndSaveArrow();

        //Change address to Alternate Address & verify tax
        newReturnOrder.selectAlternateVendorAddInShipToAddress();
        newReturnOrder.selectVendorCode("COLADD");
        newReturnOrder.clickRefreshButton();
        String actualTotalTaxForAlternate = newReturnOrder.getTotalTaxAmount();
        assertEquals(actualTotalTaxForAlternate, expectedTaxForAlternate);

        //Verify calls and tax in XML for Alternate address
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.filterDocuments(returnOrderNumber);
        int callsForAlternate=adminPage.getResponseCount(returnOrderNumber);
        assertEquals(callsForAlternate,expectedCallsForAlternate);
        adminPage.filterxml("Tax Calc Response");
        String xmlStrAlt = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStrAlt.contains("<TotalTax>51.81</TotalTax>"), " Tax Validation Failed");

    }

    /**g
     * CDBC-939
     * Verify the documents for Quantity >1000 and with decimal values, verify the tax in Statistics
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyQuantityForReturnOrderTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1001";
        String quantity1 = "2001.12345";
        String expectedTax="90,063.71";
        String expectedStatTax="90,063.71";
        List<String> expectedQuantities=Arrays.asList("1,001", "2,001.12345");
        List<String> expectedTaxAmounts=Arrays.asList("30,030","60,033.71");
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseReturnOrdersListPage purchaseReturnOrder = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Return Orders");
        BusinessPurchaseReturnOrderPage newReturnOrder = purchaseReturnOrder.pageNavMenu.clickNew();
        fillInPurchaseReturnOrderInfo(newReturnOrder, vendorCode);
        newReturnOrder.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newReturnOrder.activateRow(2);
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity1, null, 2);
        newReturnOrder.activateRow(3);
        newReturnOrder.exitExpandTable();

        //Open Vertex tax details window
        openVertexTaxDetailsWindow(newReturnOrder);
        List<String> actualQuantities = newReturnOrder.getMultipleQuantities();
        assertEquals(expectedQuantities, actualQuantities);
        List<String> actualTaxAmounts = newReturnOrder.getMultipleTaxAmount("Purchase");
        assertEquals(expectedTaxAmounts, actualTaxAmounts);
        newReturnOrder.closeTaxDetailsDialog();

        //Verify taxes in Statistics Page
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.RETURN_ORDER.value, "Statistics");
        String actualStatTax=newReturnOrder.getAPStatTaxAmount();
        assertEquals(expectedStatTax,actualStatTax);
        newReturnOrder.closeTaxDetailsDialog();

        //Verify tax
        String actualTotalTax = newReturnOrder.getTotalTaxAmount();
        assertEquals(expectedTax,actualTotalTax);
    }

    /**
     * CDBC-1084
     * Verify if Recalculation Pop up is displayed on changing ship-to to Custom Address and trying to exit without recalculation, clicking 'Yes' in popup and verifying tax, followed by clicking 'No' and verifying tax
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyRecalculateTaxPopupPurchaseReturnOrderTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "2";
        String expectedTax = "60.00";
        String expectedMsg = "Ship-to address was changed but taxes have not been recalculated. Would you like to recalculate taxes now?";
        String expectedShipToChangedTax = "95.00";
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseReturnOrdersListPage purchaseReturnOrder = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Return Orders");
        BusinessPurchaseReturnOrderPage newReturnOrder = purchaseReturnOrder.pageNavMenu.clickNew();
        fillInPurchaseReturnOrderInfo(newReturnOrder, vendorCode);
        newReturnOrder.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newReturnOrder.activateRow(2);
        newReturnOrder.exitExpandTable();

        //Verify tax in total tax UI field
        String actualTotalTax = newReturnOrder.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTax);

        //Change address to Custom
        newReturnOrder.openShippingAndPaymentCategory();
        newReturnOrder.selectCustomInShipToAddressForPurchase();
        newReturnOrder.clickShowMore();
        fillInCustomAddressInShippingAndBilling("9590 Broadway", "Los Angeles", "CA", "90030");
        newReturnOrder.clickBackArrow();

        //Verify the Tax on clicking 'Yes' for Recalculation Popup
        String actualMsg = newReturnOrder.getAlertMessagesInAP();
        assertEquals(actualMsg, expectedMsg);
        newReturnOrder.dialogBoxClickYes();
        newReturnOrder.dialogBogClickOkOnLocationChange();
        String actualShipToChangedTotalTax = newReturnOrder.getTotalTaxAmount();
        assertEquals(actualShipToChangedTotalTax, expectedShipToChangedTax);
        String purchaseReturnOrderNumber = newReturnOrder.getCurrentSalesNumber();

        //Verify Tax in XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseReturnOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>95.0</TotalTax>"), " Tax Validation Failed");
        newReturnOrder.clickBackAndSaveArrow();
        fillInCustomAddressInShippingAndBilling("1575 Space Center Drive", "Colorado Springs", "CO", "80915");
        newReturnOrder.clickBackArrow();

        //Verify the Tax on clicking 'No' for Recalculation Popup
        String actualMessage= newReturnOrder.getAlertMessagesInAP();
        assertEquals(actualMessage, expectedMsg);
        newReturnOrder.dialogBoxClickNo();
        newReturnOrder.searchForPurchaseDocuments(purchaseReturnOrderNumber);
        String actualCustomTotalTax = newReturnOrder.getTotalTaxAmount();
        assertEquals(actualCustomTotalTax, expectedShipToChangedTax);

    }

    /**
     * CDBC-1085
     * Verify the tax details for Purchase Return Order on updating the Quantity, price and Item
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxOnUpdatingLinePurchaseReturnOrderTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";
        String expectedTax = "48.60";
        List<String> expectedTaxAmounts = Arrays.asList("120", "1.2", "12", "3", "2.4");
        List<String> expectedQuantities = Arrays.asList("2","1","1","1","1");
        String expectedTaxOnUpdation="138.60";
        String expectedTaxAfterDeletion="136.20";

        //create Purchase Return Order
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseReturnOrdersListPage purchaseReturnOrder = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Return Orders");
        BusinessPurchaseReturnOrderPage newReturnOrder = purchaseReturnOrder.pageNavMenu.clickNew();
        fillInPurchaseReturnOrderInfo(newReturnOrder, vendorCode);
        newReturnOrder.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newReturnOrder.activateRow(2);
        fillInItemsTableInfo("G/L Account", "10200", null, null, "1", "20", 2);
        newReturnOrder.activateRow(3);
        fillInItemsTableInfo("Resource", "MARY", null, null, "1", "200", 3);
        newReturnOrder.activateRow(4);
        fillInItemsTableInfo("Fixed Asset", "FA000110", null, null, "1", "50", 4);
        newReturnOrder.activateRow(5);
        fillInItemsTableInfo("Charge (Item)", "P-ALLOWANCE", null, null, "1", "40", 5);
        newReturnOrder.activateRow(6);
        newReturnOrder.exitExpandTable();

        //Verify tax in total tax UI field
        String actualTotalTax = newReturnOrder.getTotalTaxAmount();
        assertEquals(expectedTax, actualTotalTax);

        newReturnOrder.expandTable();
        //Update itemNo, quantity, price
        newReturnOrder.activateRow(1);
        updateItemNumber("1900-S",1);
        updateQuantity("2", 1);
        updatePrice("1000", 1);
        newReturnOrder.exitExpandTable();
        openVertexTaxDetailsWindow(newReturnOrder);
        List<String> actualQuantities = newReturnOrder.getMultipleQuantities();
        assertEquals(expectedQuantities, actualQuantities);
        List<String> actualTaxAmounts = newReturnOrder.getMultipleTaxAmount("Purchase");
        assertEquals(expectedTaxAmounts, actualTaxAmounts);
        newReturnOrder.closeTaxDetailsDialog();

        //Verify tax in total tax UI field after updating lines
        String actualTotalTaxOnUpdation = newReturnOrder.getTotalTaxAmount();
        assertEquals(expectedTaxOnUpdation, actualTotalTaxOnUpdation);

        String purchaseRetOrdNumber = newReturnOrder.getCurrentSalesNumber();
        //Verify Tax in XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseRetOrdNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>138.6</TotalTax>"), "Total Tax  Validation Failed");
        assertTrue(xmlStr.contains("<Quantity>2.0</Quantity>"), "Quantity validation failed");
        assertTrue(xmlStr.contains("<UnitPrice>1000.0</UnitPrice>"), "Unit Price validation failed");
        adminPage.clickBackAndSaveArrow();
        //deleteLine
        newReturnOrder.activateRow(5);
        deletingLine(5);
        //Verify tax in total tax UI field
        String actualTotalTaxAfterDeletion = newReturnOrder.getTotalTaxAmount();
        assertEquals(expectedTaxAfterDeletion, actualTotalTaxAfterDeletion);
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.filterDocuments(purchaseRetOrdNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStrOnDeletion = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStrOnDeletion.contains("<TotalTax>136.2</TotalTax>"), " Tax Validation Failed");
        assertTrue(!xmlStrOnDeletion.contains("<Purchase purchaseClass=\"LABOR\">P-ALLOWANCE</Purchase>"),"Item not deleted, Validation Failed");
    }

    /**
     * CDBC-1089
     * Verify the tax details for posted document lines to reverse
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxOnPostedDocumentsLineToReversePurchaseReturnOrderTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";
        String expectedTax = "44.50";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        //Create Invoice
        BusinessPurchaseInvoicesListPage purchaseInvoice = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Invoices");
        BusinessPurchaseInvoicePage newPurchaseInvoice = purchaseInvoice.pageNavMenu.clickNew();
        fillInPurchaseInvoiceGeneralInfo(newPurchaseInvoice, vendorCode);
        newPurchaseInvoice.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newPurchaseInvoice.activateRow(2);
        newPurchaseInvoice.exitExpandTable();

        //Verify tax in total tax UI field
        String invoiceNo =  newPurchaseInvoice.getCurrentSalesNumber();
        newPurchaseInvoice.enterVendorInvoiceNo(invoiceNo);
        String actualTotalTax = newPurchaseInvoice.getTotalTaxAmount();
        assertEquals(expectedTax, actualTotalTax);

        BusinessPurchaseInvoicePage postedInvoice=salesDocumentPostPurchaseInvoice(newPurchaseInvoice);
        String actualTotalTaxPostedDoc = newPurchaseInvoice.getTotalTaxAmount();
        assertEquals(expectedTax, actualTotalTaxPostedDoc);
        String postedInvoiceNo=postedInvoice.getCurrentSalesNumber();
        System.out.println("Posted Invoice No:"+postedInvoiceNo);
         postedInvoice.clickBackAndSaveArrow();
        //Return Order
        BusinessPurchaseReturnOrdersListPage purchaseReturnOrder = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Return Orders");
        BusinessPurchaseReturnOrderPage newReturnOrder = purchaseReturnOrder.pageNavMenu.clickNew();
        fillInPurchaseReturnOrderInfo(newReturnOrder, vendorCode);
        //Select Process tab and filter document
        newReturnOrder.salesEditNavMenu.clickPrepareButton();
        newReturnOrder.selectPostedDocumentLinesToReverse();
        newReturnOrder.enableReturnOriginalQuantity();
        newReturnOrder.filterDocuments(postedInvoiceNo);
        newReturnOrder.dialogBoxClickOk();
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

    /**
     * @TestCase CDBC-1322
     * @Description - Verify Tax For Purchase Return Order And Actual Tax Field Is Set
     * @Author Mario Saint-Fleur
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxForPurchaseReturnOrderAndActualTaxFieldIsSetTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "2";
        String expectedTax = "76.56";

        //Create Purchase Return Order
        BusinessPurchaseReturnOrdersListPage purchaseReturnOrder = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Return Orders");
        BusinessPurchaseReturnOrderPage newReturnOrder = purchaseReturnOrder.pageNavMenu.clickNew();
        fillInPurchaseReturnOrderInfo(newReturnOrder, vendorCode);
        newReturnOrder.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newReturnOrder.activateRow(2);
        fillInItemsTableInfo("Resource", "MARY", null, null, "3", null, 2);
        newReturnOrder.activateRow(3);
        newReturnOrder.exitExpandTable();

        //Enter Vendor Credit Memo
        String creditMemoNo =  newReturnOrder.getCurrentSalesNumber();
        newReturnOrder.enterCreditMemoNo(creditMemoNo);

        //Verify total tax in teh UI and tax in verify tax details
        String actualTotalTax = newReturnOrder.getTotalTaxAmount();
        assertEquals(expectedTax,actualTotalTax);
        openVertexTaxDetailsWindow(newReturnOrder);
        String calculatedSalesTax = newReturnOrder.getCalculatedSalesTaxAmount();
        String actualSalesTax = newReturnOrder.getActualSalesTaxAmountWithoutInput();
        String actualSalesTaxReadOnlyVal = newReturnOrder.verifyActualTaxIsReadOnly();
        assertEquals("76.56", calculatedSalesTax);
        assertEquals("76.56", actualSalesTax);
        assertEquals(actualSalesTaxReadOnlyVal, "true");
        newReturnOrder.closeTaxDetailsDialog();
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
