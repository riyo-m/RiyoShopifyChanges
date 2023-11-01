package com.vertex.quality.connectors.dynamics365.business.tests.Purchasing;

import com.vertex.quality.connectors.dynamics365.business.enums.BusinessMainMenuNavTabs;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessAdminHomePage;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessVertexAdminPage;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.*;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
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
 * Creates Purchase Invoice Document, Verifies taxes on total tax UI field, XML, on updating line level details and Ship-to addresses
 * @author Shruti
 */
@Listeners(TestRerunListener.class)
public class BusinessPurchaseInvoiceTests extends BusinessBaseTest {
    BusinessAdminHomePage homePage;
    /**
     * CDBC-932
     * Verify the tax for Invoice quantity >1000 and with decimal values, Statistics page and for all product types
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyVendorForInvoiceTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1908-S";
        String quantity = "1004";
        String quantity1 = "1004.12345";
        String expectedTax = "35,404.47";
        List<String> expectedTaxAmounts = Arrays.asList("5,947.69", "2,973.85", "2,825.16", "1,486.92", "5,948.43", "2,974.21", "2,825.5", "1,487.11", "803.2", "401.6", "381.52", "200.8", "803.2", "401.6", "381.52", "200.8", "1,204.8", "602.4", "572.28", "301.2", "1,204.8", "602.4", "572.28", "301.2");

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseInvoicesListPage purchaseInvoices = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Invoices");
        BusinessPurchaseInvoicePage newPurchaseInvoice = purchaseInvoices.pageNavMenu.clickNew();
        fillInPurchaseInvoiceGeneralInfo(newPurchaseInvoice, vendorCode);
        newPurchaseInvoice.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, "148.10", 1);
        newPurchaseInvoice.activateRow(2);
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity1, "148.10", 2);
        newPurchaseInvoice.activateRow(3);
        fillInItemsTableInfo("G/L Account", "10200", null, null, quantity, "20", 3);
        newPurchaseInvoice.activateRow(4);
        fillInItemsTableInfo("Resource", "MARY", null, null, quantity, "20", 4);
        newPurchaseInvoice.activateRow(5);
        fillInItemsTableInfo("Fixed Asset", "FA000110", null, null, quantity, "30", 5);
        newPurchaseInvoice.activateRow(6);
        fillInItemsTableInfo("Charge (Item)", "P-ALLOWANCE", null, null, quantity, "30", 6);
        newPurchaseInvoice.exitExpandTable();

        //Verify taxes in Vertex tax details window
        openVertexTaxDetailsWindow(newPurchaseInvoice);
        List<String> actualTaxAmounts = newPurchaseInvoice.getMultipleTaxAmount("Purchase");
        assertEquals(expectedTaxAmounts, actualTaxAmounts);
        newPurchaseInvoice.closeTaxDetailsDialog();

        //Verify taxes in Statistics Page
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.INVOICE.value, "Statistics");
        String actualStatTax = newPurchaseInvoice.getAPStatTaxAmount();
        assertEquals(expectedTax, actualStatTax);
        newPurchaseInvoice.closeTaxDetailsDialog();

        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseInvoice.getTotalTaxAmount();
        assertEquals(expectedTax, actualTotalTax);
        String invoiceNumber = newPurchaseInvoice.getCurrentSalesNumber();
        //Verify Tax in XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(invoiceNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>"+expectedTax.replace(",","")+"</TotalTax>"), " Tax Validation Failed");
    }

    /**
     * CDBC-1051
     * Verify the tax for Purchase Invoice, vertex tax details and XML on updating the Quantity, price and location and deleting line
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxOnUpdatingLinePurchaseInvoiceTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String itemNo2 = "1908-S";
        String quantity = "1";
        String expectedTax = "61.93";
        String location = "WEST";
        String expectedTaxAfterDeletion = "160.00";
        List<String> expectedTaxAmountsOnDeletion = Arrays.asList("80", "80");
        List<String> expectedTaxAmounts = Arrays.asList("35", "0", "13.75","5.93", "2.96", "2.81", "1.48");

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        //Create Invoice
        BusinessPurchaseInvoicesListPage purchaseInvoice = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Invoices");
        BusinessPurchaseInvoicePage newPurchaseInvoice = purchaseInvoice.pageNavMenu.clickNew();
        fillInPurchaseInvoiceGeneralInfo(newPurchaseInvoice, vendorCode);
        newPurchaseInvoice.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, location, quantity, null, 1);
        newPurchaseInvoice.activateRow(2);
        fillInItemsTableInfo(itemType, itemNo2, null, null, quantity, "148.10", 2);
        newPurchaseInvoice.exitExpandTable();

        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseInvoice.getTotalTaxAmount();
        assertEquals(expectedTax, actualTotalTax);
        openVertexTaxDetailsWindow(newPurchaseInvoice);
        List<String> actualTaxAmounts = newPurchaseInvoice.getMultipleTaxAmount("Purchase");
        assertEquals(expectedTaxAmounts, actualTaxAmounts);
        newPurchaseInvoice.closeTaxDetailsDialog();
        newPurchaseInvoice.expandTable();
        //Update location code, quantity, price, tax group code
        newPurchaseInvoice.activateRow(1);
        addLineLevelLocationCode("EAST", 1);
        updateQuantity("2", 1);
        updatePrice("1000", 1);
        updateTaxGroupCode("SUPPLIES", 1);

        String purchaseOrderNumber = newPurchaseInvoice.getCurrentSalesNumber();
        //Verify Tax in XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>173.18</TotalTax>"), " Tax Validation Failed");
        assertTrue(xmlStr.contains("<Purchase purchaseClass=\"SUPPLIES\">1896-S</Purchase>"), "Tax Group Validation failed");
        assertTrue(xmlStr.contains("<Quantity>2.0</Quantity>"), "Quantity validation failed");
        assertTrue(xmlStr.contains("<UnitPrice>1000.0</UnitPrice>"), "Unit Price validation failed");
        adminPage.clickBackAndSaveArrow();
        newPurchaseInvoice.activateRow(2);
        deletingLine(2);
        newPurchaseInvoice.exitExpandTable();
        openVertexTaxDetailsWindow(newPurchaseInvoice);
        List<String> actualTaxAmountsOnDeletion = newPurchaseInvoice.getMultipleTaxAmount("Purchase");
        assertEquals(expectedTaxAmountsOnDeletion, actualTaxAmountsOnDeletion);
        newPurchaseInvoice.closeTaxDetailsDialog();
        //Verify tax in total tax UI field
        String actualTotalTaxAfterDeletion = newPurchaseInvoice.getTotalTaxAmount();
        assertEquals(expectedTaxAfterDeletion, actualTotalTaxAfterDeletion);
        //Verify taxes in Statistics Page
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.INVOICE.value, "Statistics");
        String actualStatTax = newPurchaseInvoice.getAPStatTaxAmount();
        assertEquals(expectedTaxAfterDeletion, actualStatTax);
        newPurchaseInvoice.closeTaxDetailsDialog();
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.filterDocuments(purchaseOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStrOnDeletion = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStrOnDeletion.contains(" <TotalTax>160.0</TotalTax>"), " Tax Validation Failed");
        assertTrue(!xmlStrOnDeletion.contains("<Purchase purchaseClass=\"FURNITURE\">1896-S</Purchase>"), "Item not deleted, Validation Failed");
    }

    /**
     * CDBC-1056
     * Creates a Purchase Invoice, adds in line item, changes Ship-to field to 'Location', verifies taxes and changes Ship-To field to Custom and verifies tax in XML
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxForDifferentShipToAddressPurchaseInvoiceTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";
        String expectedTax = "65.14";
        String location = "MAIN";
        String location1 = "WEST";
        String location2 = "EAST";

        String expectedUpdatedTax = "67.17";
        List<String> expectedTaxAmounts = Arrays.asList("31.25", "5", "5.39", "0", "2.12", "4", "4", "9.02", "1.88", "0.75", "0.75", "0.75", "0.75", "0.38", "1.13");
        List<String> expectedCustomAddressTaxesList = Arrays.asList("31.25", "5", "5.39", "0", "2.12", "4", "4", "9.39");
        String expectedTaxForCustomAddress = "61.15";
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseInvoicesListPage purchaseInvoice = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Invoices");
        BusinessPurchaseInvoicePage newPurchaseInvoice = purchaseInvoice.pageNavMenu.clickNew();
        fillInPurchaseInvoiceGeneralInfo(newPurchaseInvoice, vendorCode);
        newPurchaseInvoice.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, location, quantity, null, 1);
        newPurchaseInvoice.activateRow(2);
        fillInItemsTableInfo("Resource", "KATHERINE", null, location1, "1", null, 2);
        newPurchaseInvoice.activateRow(3);
        fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, location2, "1", "100", 3);
        newPurchaseInvoice.activateRow(4);
        fillInItemsTableInfo("Item", "1900-S", null, null, "1", null, 4);
        newPurchaseInvoice.activateRow(5);
        newPurchaseInvoice.exitExpandTable();

        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseInvoice.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTax);

        //Update Ship-to to Location
        newPurchaseInvoice.openShippingAndPaymentCategory();
        newPurchaseInvoice.selectLocationInShipToAddress();
        newPurchaseInvoice.updateLocationCodeFromShippingandBilling("CA");
        newPurchaseInvoice.dialogBoxClickYes();
        newPurchaseInvoice.clickRefreshButton();

        //Verify tax in total tax UI field
        openVertexTaxDetailsWindow(newPurchaseInvoice);
        List<String> actualTotalTaxes = newPurchaseInvoice.getMultipleTaxAmount("Purchase");
        assertEquals(actualTotalTaxes, expectedTaxAmounts);
        newPurchaseInvoice.closeTaxDetailsDialog();
        String actualUpdatedTotalTax = newPurchaseInvoice.getTotalTaxAmount();
        assertEquals(actualUpdatedTotalTax, expectedUpdatedTax);

        //Change address to Custom and verify tax
        newPurchaseInvoice.selectCustomInShipToAddressForPurchase();
        newPurchaseInvoice.dialogBoxClickOk();
        newPurchaseInvoice.clickShowMore();
        fillInCustomAddressInShippingAndBilling("207 South Washington", "Naperville", "IL", "60540");
        recalculateTax(newPurchaseInvoice);
        newPurchaseInvoice.dialogBogClickOkOnLocationChange();
        openVertexTaxDetailsWindow(newPurchaseInvoice);
        List<String> actualCustomAddressTaxesList = newPurchaseInvoice.getMultipleTaxAmount("Purchase");
        assertEquals(actualCustomAddressTaxesList, expectedCustomAddressTaxesList);
        newPurchaseInvoice.closeTaxDetailsDialog();
        String actualTaxForCustomAddress = newPurchaseInvoice.getTotalTaxAmount();
        assertEquals(actualTaxForCustomAddress, expectedTaxForCustomAddress);
        ;
    }

    /**
     * CDBC-1064
     * Verify if tax is recalculated on changing Ship-to field to Custom Address and updating line items
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxOnUpdatingLineInvoiceTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String itemNo2 = "1906-S";
        String quantity = "1";
        String expectedTax = "89.00";
        List<String> expectedTaxAmounts = Arrays.asList("20","10", "9.5","5","20","10","9.5","5");
        String expectedTaxForCustomAddress = "188.50";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        //Create Purchase Invoice
        BusinessPurchaseInvoicesListPage purchaseInvoice = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Invoices");
        BusinessPurchaseInvoicePage newPurchaseInvoice = purchaseInvoice.pageNavMenu.clickNew();
        fillInPurchaseInvoiceGeneralInfo(newPurchaseInvoice, vendorCode);
        newPurchaseInvoice.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newPurchaseInvoice.activateRow(2);
        fillInItemsTableInfo(itemType, itemNo2, null, null, quantity, null, 2);
        newPurchaseInvoice.exitExpandTable();

        openVertexTaxDetailsWindow(newPurchaseInvoice);
        List<String> actualTaxAmounts = newPurchaseInvoice.getMultipleTaxAmount("Purchase");
        assertEquals(actualTaxAmounts, expectedTaxAmounts);
        newPurchaseInvoice.closeTaxDetailsDialog();
        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseInvoice.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTax);

        //Change address to Custom Address
        newPurchaseInvoice.openShippingAndPaymentCategory();
        newPurchaseInvoice.selectCustomInShipToAddressForPurchase();
        newPurchaseInvoice.clickShowMore();
        fillInCustomAddressInShippingAndBilling("2855 South Orange Avenue", "Orlando", "FL", "32806");
        recalculateTax(newPurchaseInvoice);
        //Update Item, quantity, price
        newPurchaseInvoice.expandTable();
        newPurchaseInvoice.activateRow(1);
        updateItemNumber("1900-S", 1);
        newPurchaseInvoice.dialogBoxClickOk();
        updateQuantity("3", 1);
        updatePrice("800", 1);
        newPurchaseInvoice.exitExpandTable();
        String actualTaxForCustomerAddress = newPurchaseInvoice.getTotalTaxAmount();
        assertEquals(actualTaxForCustomerAddress, expectedTaxForCustomAddress);
        String purchaseInvoiceNumber = newPurchaseInvoice.getCurrentSalesNumber();
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseInvoiceNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>188.5</TotalTax>"), " Tax Validation Failed");
    }


    /**
     * CDBC-1066
     * Verify if tax is recalculated regardless of address is changed and on changing Ship-to field to Custom Address
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void recalculateTaxInvoiceTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";
        String expectedTax = "44.50";
        List<String> expectedTaxAmounts = Arrays.asList("20", "10","9.5","5");
        int expectedCalls = 4;
        int expectedFinalCalls = 8;

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        //Create Purchase Invoice
        BusinessPurchaseInvoicesListPage purchaseInvoice = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Invoices");
        BusinessPurchaseInvoicePage newPurchaseInvoice = purchaseInvoice.pageNavMenu.clickNew();
        fillInPurchaseInvoiceGeneralInfo(newPurchaseInvoice, vendorCode);
        newPurchaseInvoice.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newPurchaseInvoice.activateRow(2);
        newPurchaseInvoice.exitExpandTable();
        String purchaseInvoiceNumber = newPurchaseInvoice.getCurrentSalesNumber();
        openVertexTaxDetailsWindow(newPurchaseInvoice);
        List<String> actualTaxAmounts = newPurchaseInvoice.getMultipleTaxAmount("Purchase");
        assertEquals(actualTaxAmounts, expectedTaxAmounts);
        newPurchaseInvoice.closeTaxDetailsDialog();
        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseInvoice.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTax);
        recalculateTax(newPurchaseInvoice);
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseInvoiceNumber);
        int calls = adminPage.getResponseCount(purchaseInvoiceNumber);
        assertEquals(calls, expectedCalls);
        newPurchaseInvoice.clickBackAndSaveArrow();
        //Change address to Custom Address & recalculate
        newPurchaseInvoice.openShippingAndPaymentCategory();
        newPurchaseInvoice.selectCustomInShipToAddressForPurchase();
        newPurchaseInvoice.clickShowMore();
        fillInCustomAddressInShippingAndBilling("3000 Glencoe Street", "Denver", "CO", "80207");
        recalculateTax(newPurchaseInvoice);
        newPurchaseInvoice.dialogBogClickOkOnLocationChange();
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.filterDocuments(purchaseInvoiceNumber);
        int finalCalls = adminPage.getResponseCount(purchaseInvoiceNumber);
        assertEquals(finalCalls, expectedFinalCalls);
    }

    /**
     * CDBC-1067
     * Verify if tax is recalculated on changing Ship-to field to Location and updating line items
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxRecalculatedOnUpdatingLineInvoiceTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo2 = "1906-S";
        String quantity = "1";
        String expectedTax = "48.06";
        String expectedTaxForLocationAddress = "292.90";
        List<String> expectedQuantities = Arrays.asList("4","4","1","1");
        List<String> expectedTaxAmounts = Arrays.asList("250", "40","2.5","0.4");

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        //Create Purchase Invoice
        BusinessPurchaseInvoicesListPage purchaseInvoice = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Invoices");
        BusinessPurchaseInvoicePage newPurchaseInvoice = purchaseInvoice.pageNavMenu.clickNew();
        fillInPurchaseInvoiceGeneralInfo(newPurchaseInvoice, vendorCode);
        newPurchaseInvoice.expandTable();
        fillInItemsTableInfo(itemType, itemNo2, null, null, quantity, null, 1);
        newPurchaseInvoice.activateRow(2);
        fillInItemsTableInfo("Resource", "KATHERINE", null, null, "1", "40", 2);
        newPurchaseInvoice.activateRow(3);
        newPurchaseInvoice.exitExpandTable();

        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseInvoice.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTax);

        //Change address to Location Address
        newPurchaseInvoice.openShippingAndPaymentCategory();
        newPurchaseInvoice.selectLocationInShipToAddress();
        newPurchaseInvoice.updateLocationCodeFromShippingandBilling("MAIN");
        newPurchaseInvoice.dialogBoxClickYes();
        newPurchaseInvoice.clickRefreshButton();
        //Update Item, quantity, price
        newPurchaseInvoice.activateRow(1);
        fillInItemsTableInfo(itemType, "1896-S", null, null, "4", "1000", 1);

        //open vertex tax details and verify details
        openVertexTaxDetailsWindow(newPurchaseInvoice);
        List<String> actualQuantities = newPurchaseInvoice.getMultipleQuantities();
        assertEquals(actualQuantities, expectedQuantities);
        List<String> actualTaxAmounts = newPurchaseInvoice.getMultipleTaxAmount("Purchase");
        assertEquals(actualTaxAmounts, expectedTaxAmounts);
        newPurchaseInvoice.closeTaxDetailsDialog();

        String actualTaxForLocationAddress = newPurchaseInvoice.getTotalTaxAmount();
        assertEquals(expectedTaxForLocationAddress, actualTaxForLocationAddress);
        String purchaseInvoiceNumber = newPurchaseInvoice.getCurrentSalesNumber();

        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseInvoiceNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>292.9</TotalTax>"), " Tax Validation Failed");
    }

    /**
     * CDBC-1091
     * Verify if Recalculation Pop up is displayed on changing ship-to to Custom Address and trying to exit without recalculation
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyRecalculateTaxPopupInvoiceTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";
        String expectedTax = "44.50";
        String expectedMsg = "Ship-to address was changed but taxes have not been recalculated. Would you like to recalculate taxes now?";
        String expectedShipToChangedTax = "47.50";

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
        String actualTotalTax = newPurchaseInvoice.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTax);

        //Change address to Custom
        newPurchaseInvoice.openShippingAndPaymentCategory();
        newPurchaseInvoice.selectCustomInShipToAddressForPurchase();
        newPurchaseInvoice.clickShowMore();
        fillInCustomAddressInShippingAndBilling("9590 Broadway", "Los Angeles", "CA", "90030");
        newPurchaseInvoice.clickBackArrow();
        newPurchaseInvoice.dialogBoxClickYes();
        //Verify the Tax Recalculation Popup
        String actualMsg = newPurchaseInvoice.getAlertMessagesInAP();
        assertEquals(actualMsg, expectedMsg);
        newPurchaseInvoice.dialogBoxClickYes();
        newPurchaseInvoice.dialogBogClickOkOnLocationChange();

        String actualShipToChangedTotalTax = newPurchaseInvoice.getTotalTaxAmount();
        assertEquals(actualShipToChangedTotalTax, expectedShipToChangedTax);
        String purchaseInvoiceNumber = newPurchaseInvoice.getCurrentSalesNumber();

        //Verify Tax in XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseInvoiceNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>47.5</TotalTax>"), " Tax Validation Failed");
        newPurchaseInvoice.clickBackAndSaveArrow();
        fillInCustomAddressInShippingAndBilling("1575 Space Center Drive", "Colorado Springs", "CO", "80915");
        newPurchaseInvoice.clickBackArrow();
        newPurchaseInvoice.dialogBoxClickYes();
        //Verify the Tax on clicking 'No' for Recalculation Popup
        String actualMessage = newPurchaseInvoice.getAlertMessagesInAP();
        assertEquals(actualMessage, expectedMsg);
        newPurchaseInvoice.dialogBoxClickNo();
        newPurchaseInvoice.searchForPurchaseDocuments(purchaseInvoiceNumber);
        String actualCustomTotalTax = newPurchaseInvoice.getTotalTaxAmount();
        assertEquals(actualCustomTotalTax, expectedShipToChangedTax);
    }

    /**
     * CDBC-1101
     * Verify vertex tax details in posted document
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyVertexTaxDetailsForPostedInvoiceTest() {
        String vendorCode = "TestVendorPA";
        String itemNumber = "1896-S";
        String quantity = "1";
        List<String> expectedQuantity = Arrays.asList("1", "1", "1", "1");
        List<String> expectedTaxAmount = Arrays.asList("20", "10", "9.5", "5");
        String expectedTax = "44.50";
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseInvoicesListPage purchaseInvoices = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Invoices");
        BusinessPurchaseInvoicePage newPurchaseInvoice = purchaseInvoices.pageNavMenu.clickNew();
        fillInPurchaseInvoiceGeneralInfo(newPurchaseInvoice, vendorCode);

        newPurchaseInvoice.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newPurchaseInvoice.activateRow(2);
        newPurchaseInvoice.exitExpandTable();

        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseInvoice.getTotalTaxAmount();
        assertEquals(expectedTax, actualTotalTax);

        //Verify taxes in Vertex tax details window
        openVertexTaxDetailsWindow(newPurchaseInvoice);
        List<String> quantities = newPurchaseInvoice.getMultipleQuantities();
        assertEquals(quantities, expectedQuantity);
        List<String> actualTaxAmounts = newPurchaseInvoice.getMultipleTaxAmount("Purchase");
        assertEquals(expectedTaxAmount, actualTaxAmounts);
        newPurchaseInvoice.closeTaxDetailsDialog();
        String invoiceNo = newPurchaseInvoice.getCurrentSalesNumber();
        newPurchaseInvoice.enterVendorInvoiceNo(invoiceNo);

        BusinessPurchaseInvoicePage postedInvoice = salesDocumentPostPurchaseInvoice(newPurchaseInvoice);
        //Verify taxes in Vertex tax details window for Posted document
        openVertexTaxDetailsWindow(postedInvoice);
        List<String> quantitiesPostedDoc = postedInvoice.getMultipleQuantities();
        assertEquals(quantitiesPostedDoc, expectedQuantity);
        List<String> actualTaxAmountsPostedDoc = postedInvoice.getMultipleTaxAmount("Purchase");
        assertEquals(expectedTaxAmount, actualTaxAmountsPostedDoc);
        postedInvoice.closeTaxDetailsDialog();
        String postedInvoiceNumber = postedInvoice.getCurrentSalesNumber();
        //Verify tax in total tax UI field
        String actualTotalTaxPostedDoc = postedInvoice.getTotalTaxAmount();
        assertEquals(expectedTax, actualTotalTaxPostedDoc);
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(postedInvoiceNumber);
        adminPage.filterxml("Invoice Request");
        String xmlStrRequest = adminPage.clickOnFirstLinkAndGetTheXml();
    }

    /**
     * CDBC-1110
     * Verify if freight record is available in XML
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyFreightChargesTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseInvoicesListPage purchaseInvoices = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Invoices");
        BusinessPurchaseInvoicePage newPurchaseInvoice = purchaseInvoices.pageNavMenu.clickNew();
        fillInPurchaseInvoiceGeneralInfo(newPurchaseInvoice, vendorCode);
        newPurchaseInvoice.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newPurchaseInvoice.activateRow(2);
        fillInItemsTableInfo("Charge (Item)", "P-Freight", null, null, quantity, "100", 2);
        newPurchaseInvoice.activateRow(3);
        newPurchaseInvoice.exitExpandTable();
        String purchaseInvoiceNumber = newPurchaseInvoice.getCurrentSalesNumber();
        //Validate tax and freight record in XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseInvoiceNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>53.4</TotalTax>"), " Tax Validation Failed");
        assertTrue(xmlStr.contains("<Purchase purchaseClass=\"LABOR\">P-FREIGHT</Purchase>"),"Freight validation Failed");
    }

    /**
     * @TestCase CDBC-1323
     * @Description - Create a Purchase Invoice, undercharge and recalculate Sales Tax, Post and verify accrual call
     * @Author Mario Saint-Fleur
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyAccrualCallWhenCreatingPurchaseInvoiceAndUnderChargingTaxTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

        //Create Purchase Invoice
        BusinessPurchaseInvoicesListPage purchaseInvoices = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Invoices");
        BusinessPurchaseInvoicePage newPurchaseInvoice = purchaseInvoices.pageNavMenu.clickNew();
        fillInPurchaseInvoiceGeneralInfo(newPurchaseInvoice, vendorCode);
        newPurchaseInvoice.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newPurchaseInvoice.activateRow(2);
        fillInItemsTableInfo("Resource", "MARY", null, null, quantity, null, 2);
        newPurchaseInvoice.activateRow(3);
        newPurchaseInvoice.exitExpandTable();

        String purchaseInvoiceNumber = newPurchaseInvoice.getCurrentSalesNumber();

        //Verify taxes in Vertex tax details
        openVertexTaxDetailsWindow(newPurchaseInvoice);
        newPurchaseInvoice.clickAndEnterActualSalesTaxAmount("50.00");
        newPurchaseInvoice.clickRecalculateTax();
        String calculatedSalesTax = newPurchaseInvoice.getCalculatedSalesTaxAmount();
        String actualSalesTax = newPurchaseInvoice.getActualSalesTaxAmount();
        String accrualSalesTax = newPurchaseInvoice.getAccrualSalesTaxAmount();
        assertEquals(calculatedSalesTax, "52.69");
        assertEquals(actualSalesTax, "50.00");
        assertEquals(accrualSalesTax, "2.69");
        newPurchaseInvoice.closeTaxDetailsDialog();
        String invoiceNo = newPurchaseInvoice.getCurrentSalesNumber();
        newPurchaseInvoice.enterVendorInvoiceNo(invoiceNo);

        //Post Purchase Invoice
        BusinessPurchaseInvoicePage postedInvoice = salesDocumentPostPurchaseInvoice(newPurchaseInvoice);
        openVertexTaxDetailsWindow(postedInvoice);
        postedInvoice.closeTaxDetailsDialog();
        String postedInvoiceNumber = postedInvoice.getCurrentSalesNumber();

        //Verify Accrual Request is not present
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.clickBackAndSaveArrow();
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.clickBackAndSaveArrow();
        homePage.searchForAndNavigateToVertexAdminPage();

        //Verify Accrual Request and Tax for Purchase Invoice
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseInvoiceNumber);
        adminPage.filterxml("Accrual Request");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<AccrualRequest"), "Accrual Request expected NOT to be present");
        adminPage.filterxml("Accrual Response");
        xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>2.69</TotalTax>"), "Total tax amount is not correct");

        //Verify Accrual Request is not present and Tax for Posted Purchase Invoice
        adminPage.filterDocuments(postedInvoiceNumber);
        adminPage.filterxml("Invoice Request");
        xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(!xmlStr.contains("<AccrualRequest postToJournal=false"), "Accrual Request expected NOT to be present");
        adminPage.filterxml("Invoice Response");
        xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>52.69</TotalTax>"), "Total tax amount is not correct");
    }

    /**
     * @TestCase CDBC-1324
     * @Description - Create a Purchase Invoice, overcharge and recalculate Sales Tax, Post and verify accrual call
     * @Author Mario Saint-Fleur
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyAccrualCallWhenCreatingPurchaseInvoiceAndOverChargingTaxTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1900-S";
        String quantity = "1";
        String location = "WEST";
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

        //Create Purchase Invoice
        BusinessPurchaseInvoicesListPage purchaseInvoices = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Invoices");
        BusinessPurchaseInvoicePage newPurchaseInvoice = purchaseInvoices.pageNavMenu.clickNew();
        fillInPurchaseInvoiceGeneralInfo(newPurchaseInvoice, vendorCode);
        newPurchaseInvoice.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, location, quantity, null, 1);
        newPurchaseInvoice.activateRow(2);
        fillInItemsTableInfo("G/L Account", "10100", null, null, "1", "100", 2);
        newPurchaseInvoice.exitExpandTable();

        String purchaseInvoiceNumber = newPurchaseInvoice.getCurrentSalesNumber();

        //Verify taxes in Vertex tax details
        openVertexTaxDetailsWindow(newPurchaseInvoice);
        newPurchaseInvoice.clickAndEnterActualSalesTaxAmount("167.45");
        newPurchaseInvoice.clickRecalculateTax();
        String calculatedSalesTax = newPurchaseInvoice.getCalculatedSalesTaxAmount();
        String actualSalesTax = newPurchaseInvoice.getActualSalesTaxAmount();
        String accrualSalesTax = newPurchaseInvoice.getAccrualSalesTaxAmount();
        assertEquals("23.55", calculatedSalesTax);
        assertEquals("167.45", actualSalesTax);
        assertEquals("0.00", accrualSalesTax);
        newPurchaseInvoice.closeTaxDetailsDialog();
        String invoiceNo = newPurchaseInvoice.getCurrentSalesNumber();
        newPurchaseInvoice.enterVendorInvoiceNo(invoiceNo);

        //Post Purchase Invoice
        BusinessPurchaseInvoicePage postedInvoice = salesDocumentPostPurchaseInvoice(newPurchaseInvoice);
        openVertexTaxDetailsWindow(postedInvoice);
        postedInvoice.closeTaxDetailsDialog();
        String postedInvoiceNumber = postedInvoice.getCurrentSalesNumber();

        //Verify Accrual Request is not present
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.clickBackAndSaveArrow();
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.clickBackAndSaveArrow();
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(postedInvoiceNumber);
        adminPage.filterxml("Invoice Request");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(!xmlStr.contains("<AccrualRequest postToJournal=false"), "Accrual Request expected NOT to be present");
        adminPage.filterxml("Invoice Response");
        xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>23.55</TotalTax>"), "Total tax amount is not correct");
    }

    /**
     * @TestCase CDBC-1325
     * @Description - Create a Purchase Invoice, similar charge and recalculate Sales Tax, Post and verify accrual call
     * @Author Mario Saint-Fleur
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"},retryAnalyzer = TestRerun.class)
    public void verifyAccrualCallWhenCreatingPurchaseInvoiceAndSimilarChargingTaxTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String itemNo2 = "1900-S";
        String quantity = "1";
        String location = "WEST";
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

        //Create Purchase Invoice
        BusinessPurchaseInvoicesListPage purchaseInvoices = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Invoices");
        BusinessPurchaseInvoicePage newPurchaseInvoice = purchaseInvoices.pageNavMenu.clickNew();
        fillInPurchaseInvoiceGeneralInfo(newPurchaseInvoice, vendorCode);
        newPurchaseInvoice.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, location, quantity, null, 1);
        newPurchaseInvoice.activateRow(2);
        fillInItemsTableInfo("Resource", "Mary", null, null, quantity, null, 2);
        newPurchaseInvoice.exitExpandTable();

        //Verify taxes in Vertex tax details
        openVertexTaxDetailsWindow(newPurchaseInvoice);
        String calculatedSalesTax = newPurchaseInvoice.getCalculatedSalesTaxAmount();
        String actualSalesTax = newPurchaseInvoice.getActualSalesTaxAmount();
        String accrualSalesTax = newPurchaseInvoice.getAccrualSalesTaxAmount();
        assertEquals("56.94", calculatedSalesTax);
        assertEquals("56.94", actualSalesTax);
        assertEquals("0.00", accrualSalesTax);
        newPurchaseInvoice.closeTaxDetailsDialog();
        String invoiceNo = newPurchaseInvoice.getCurrentSalesNumber();
        newPurchaseInvoice.enterVendorInvoiceNo(invoiceNo);

        //Post Purchase Invoice
        BusinessPurchaseInvoicePage postedInvoice = salesDocumentPostPurchaseInvoice(newPurchaseInvoice);
        openVertexTaxDetailsWindow(postedInvoice);
        postedInvoice.closeTaxDetailsDialog();
        String postedInvoiceNumber = postedInvoice.getCurrentSalesNumber();

        //Verify Accrual Request is not present
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.clickBackAndSaveArrow();
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.clickBackAndSaveArrow();
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(postedInvoiceNumber);
        adminPage.filterxml("Invoice Request");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(!xmlStr.contains("<AccrualRequest postToJournal=false"), "Accrual Request expected NOT to be present");
        adminPage.filterxml("Invoice Response");
        xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>56.94</TotalTax>"), "Total tax amount is not correct");
    }

    /**
     * @TestCase CDBC-1326
     * @Description - Create a Purchase Invoice, zero charge and recalculate Sales Tax, Post and verify accrual call
     * @Author Mario Saint-Fleur
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyAccrualCallWhenCreatingPurchaseInvoiceAndZeroChargingTaxTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

        //Create Purchase Invoice
        BusinessPurchaseInvoicesListPage purchaseInvoices = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Invoices");
        BusinessPurchaseInvoicePage newPurchaseInvoice = purchaseInvoices.pageNavMenu.clickNew();
        fillInPurchaseInvoiceGeneralInfo(newPurchaseInvoice, vendorCode);
        newPurchaseInvoice.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newPurchaseInvoice.activateRow(2);
        fillInItemsTableInfo("Resource", "Mary", null, null, quantity, null, 2);
        newPurchaseInvoice.exitExpandTable();

        String purchaseInvoiceNumber = newPurchaseInvoice.getCurrentSalesNumber();

        //Verify taxes in Vertex tax details
        openVertexTaxDetailsWindow(newPurchaseInvoice);
        newPurchaseInvoice.clickAndEnterActualSalesTaxAmount("0.00");
        newPurchaseInvoice.clickRecalculateTax();
        String calculatedSalesTax = newPurchaseInvoice.getCalculatedSalesTaxAmount();
        String actualSalesTax = newPurchaseInvoice.getActualSalesTaxAmount();
        String accrualSalesTax = newPurchaseInvoice.getAccrualSalesTaxAmount();
        assertEquals(calculatedSalesTax, "52.69");
        assertEquals(actualSalesTax, "0.00");
        assertEquals(accrualSalesTax, "52.69");
        newPurchaseInvoice.closeTaxDetailsDialog();
        String invoiceNo = newPurchaseInvoice.getCurrentSalesNumber();
        newPurchaseInvoice.enterVendorInvoiceNo(invoiceNo);

        //Post Purchase Invoice
        BusinessPurchaseInvoicePage postedInvoice = salesDocumentPostPurchaseInvoice(newPurchaseInvoice);
        openVertexTaxDetailsWindow(postedInvoice);
        postedInvoice.closeTaxDetailsDialog();
        String postedInvoiceNumber = postedInvoice.getCurrentSalesNumber();

        //Verify Accrual Request is not present
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.clickBackAndSaveArrow();
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.clickBackAndSaveArrow();
        homePage.searchForAndNavigateToVertexAdminPage();

        //Verify Accrual Request and Tax for Purchase Invoice
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseInvoiceNumber);
        adminPage.filterxml("Accrual Request");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<AccrualRequest"), "Accrual Request expected NOT to be present");
        adminPage.filterxml("Accrual Response");
        xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>52.69</TotalTax>"), "Total tax amount is not correct");

        //Verify Accrual Request is not present and Tax for Posted Purchase Invoice
        adminPage.filterDocuments(postedInvoiceNumber);
        adminPage.filterxml("Invoice Request");
        xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(!xmlStr.contains("<AccrualRequest postToJournal=false"), "Accrual Request expected NOT to be present");
        adminPage.filterxml("Invoice Response");
        xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>52.69</TotalTax>"), "Total tax amount is not correct");
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
