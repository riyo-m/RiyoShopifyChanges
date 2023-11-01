package com.vertex.quality.connectors.dynamics365.business.tests.Purchasing;

import com.vertex.quality.connectors.dynamics365.business.enums.BusinessMainMenuNavTabs;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessAdminHomePage;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessVertexAdminPage;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessPurchaseQuotePage;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessPurchaseQuotesListPage;
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
 * Creates Purchase Quote Document, Verifies taxes on total tax UI field, XML, on updating line level details and Ship-to addresses
 * @author Shruti
 */
@Listeners(TestRerunListener.class)
public class BusinessPurchaseQuotesTests extends BusinessBaseTest {
    BusinessAdminHomePage homePage;
    /**
     * CDBC-936
     * Verify the tax for quantity greater than 1000 with decimal values and doc creation for all items for Purchase Quote
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyVendorForQuotesTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1002";
        String quantity1 = "1001.12345";
        String expectedTax = "97,665.44";
        String location = "WEST";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseQuotesListPage purchaseQuotes = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Quotes");
        BusinessPurchaseQuotePage newPurchaseQuote = purchaseQuotes.pageNavMenu.clickNew();
        fillInPurchaseQuoteGeneralInfo(newPurchaseQuote, vendorCode);
        newPurchaseQuote.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, location, quantity, null, 1);
        newPurchaseQuote.activateRow(2);
        fillInItemsTableInfo(itemType, itemNo, null, location, quantity1, null, 2);
        newPurchaseQuote.activateRow(3);
        fillInItemsTableInfo("G/L Account", "10200", null, null, "1", "20", 3);
        newPurchaseQuote.activateRow(4);
        fillInItemsTableInfo("Resource", "MARY", null, null, "1", "100", 4);
        newPurchaseQuote.activateRow(5);
        fillInItemsTableInfo("Fixed Asset", "FA000120", null, null, "1", "10", 5);
        newPurchaseQuote.activateRow(6);
        fillInItemsTableInfo("Charge (Item)", "P-ALLOWANCE", null, null, "1", "18", 6);
        newPurchaseQuote.activateRow(7);
        newPurchaseQuote.exitExpandTable();

        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseQuote.getTotalTaxAmount();
        assertEquals(expectedTax, actualTotalTax);
        String purchaseQuoteNumber = newPurchaseQuote.getCurrentSalesNumber();

        //Verify taxes in Statistics Page
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.QUOTE.value, "Statistics");
        String actualStatTax=newPurchaseQuote.getAPStatTaxAmount();
        assertEquals(expectedTax,actualStatTax);
        newPurchaseQuote.closeTaxDetailsDialog();

        //Verify Tax in XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseQuoteNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>97665.44</TotalTax>"), " Tax Validation Failed");
    }

    /**
     * CDBC-1050
     * Verify the tax for Purchase Quote, vertex tax details and XML on updating the Quantity, price and location and deleting line
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxOnUpdatingLinePurchaseQuoteTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String itemNo2 = "1906-S";
        String quantity = "1";
        String expectedTax = "97.50";
        String location = "WEST";
        String expectedTaxAfterDeletion = "128.00";
        List<String> expectedTaxAmounts = Arrays.asList("35", "0", "13.75", "35", "0", "13.75");
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        //Create a Quote
        BusinessPurchaseQuotesListPage purchaseQuotes = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Quotes");
        BusinessPurchaseQuotePage newPurchaseQuotes = purchaseQuotes.pageNavMenu.clickNew();
        fillInPurchaseQuoteGeneralInfo(newPurchaseQuotes, vendorCode);
        newPurchaseQuotes.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, location, quantity, null, 1);
        newPurchaseQuotes.activateRow(2);
        fillInItemsTableInfo(itemType, itemNo2, null, location, quantity, null, 2);
        newPurchaseQuotes.exitExpandTable();

        openVertexTaxDetailsWindow(newPurchaseQuotes);
        List<String> actualTaxAmounts = newPurchaseQuotes.getMultipleTaxAmount("Purchase");
        assertEquals(expectedTaxAmounts, actualTaxAmounts);
        newPurchaseQuotes.closeTaxDetailsDialog();
        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseQuotes.getTotalTaxAmount();
        assertEquals(expectedTax, actualTotalTax);
        newPurchaseQuotes.expandTable();
        //Update location code, quantity, price, tax group code
        newPurchaseQuotes.activateRow(1);
        addLineLevelLocationCode("EAST", 1);
        updateQuantity("2", 1);
        updatePrice("800", 1);
        updateTaxGroupCode("SUPPLIES", 1);
        String purchaseOrderNumber = newPurchaseQuotes.getCurrentSalesNumber();
        //Verify Tax in XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>176.75</TotalTax>"), " Tax Validation Failed");
        assertTrue(xmlStr.contains("<Purchase purchaseClass=\"SUPPLIES\">1896-S</Purchase>"), "Tax Group Validation failed");
        assertTrue(xmlStr.contains("<Quantity>2.0</Quantity>"), "Quantity validation failed");
        assertTrue(xmlStr.contains("<UnitPrice>800.0</UnitPrice>"), "Unit Price validation failed");
        adminPage.clickBackAndSaveArrow();
        newPurchaseQuotes.activateRow(2);
        //Delete line item
        deletingLine(2);
        //Verify tax in total tax UI field
        String actualTotalTaxAfterDeletion = newPurchaseQuotes.getTotalTaxAmount();
        assertEquals(expectedTaxAfterDeletion, actualTotalTaxAfterDeletion);
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.filterDocuments(purchaseOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStrOnDeletion = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStrOnDeletion.contains(" <TotalTax>128.0</TotalTax>"), " Tax Validation Failed");
        assertTrue(!xmlStrOnDeletion.contains("<Purchase purchaseClass=\"FURNITURE\">1900-S</Purchase>"), "Item not deleted, Validation Failed");
    }

    /**
     * CDBC-1054
     * Creates a Purchase Quotes, adds in line item, changes Ship-to field to 'Location', verifies taxes and changes Ship-To field to Custom and verifies tax in XML
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxForDifferentShipToAddressTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";
        String expectedTax = "59.38";
        String location1="EAST";
        String location2="WEST";
        String location3="MAIN";
        String expectedUpdatedTax = "60.23";
        List<String> expectedTaxAmounts = Arrays.asList("20", "20", "7", "0", "2.75","0.63","0.1","7","0","2.75");
        List<String> expectedCustomAddressTaxesList = Arrays.asList("20", "20", "7", "0", "2.75","0.63","0.1","2.9","3.07","1");
        String expectedTaxForCustomAddress = "57.45";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseQuotesListPage purchaseQuotes = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Quotes");
        BusinessPurchaseQuotePage newPurchaseQuote = purchaseQuotes.pageNavMenu.clickNew();
        fillInPurchaseQuoteGeneralInfo(newPurchaseQuote, vendorCode);
        newPurchaseQuote.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, location1, quantity, null, 1);
        newPurchaseQuote.activateRow(2);
        fillInItemsTableInfo("Resource", "MARTY", null, location2, "1", "100", 2);
        newPurchaseQuote.activateRow(3);
        fillInItemsTableInfo("Fixed Asset", "FA000120", null, location3, "1", "10", 3);
        newPurchaseQuote.activateRow(4);
        fillInItemsTableInfo("Charge (Item)", "P-ALLOWANCE", null, null, "1", "100", 4);
        newPurchaseQuote.activateRow(5);
        newPurchaseQuote.exitExpandTable();

        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseQuote.getTotalTaxAmount();
        assertEquals(expectedTax, actualTotalTax);

        //Update Ship-to to Location
        newPurchaseQuote.openShippingAndPaymentCategory();
        newPurchaseQuote.selectLocationInShipToAddress();
        newPurchaseQuote.updateLocationCodeFromShippingandBilling("WEST");
        newPurchaseQuote.dialogBoxClickYes();

        //Verify tax in total tax UI field
        newPurchaseQuote.clickRefreshButton();
        String actualUpdatedTotalTax = newPurchaseQuote.getTotalTaxAmount();
        assertEquals(expectedUpdatedTax, actualUpdatedTotalTax);
        openVertexTaxDetailsWindow(newPurchaseQuote);
        List<String> actualTotalTaxes = newPurchaseQuote.getMultipleTaxAmount("Purchase");
        assertEquals(expectedTaxAmounts, actualTotalTaxes);
        newPurchaseQuote.closeTaxDetailsDialog();

        //Change address to Custom and verify tax
        newPurchaseQuote.selectCustomInShipToAddressForPurchase();
        newPurchaseQuote.dialogBoxClickOk();
        newPurchaseQuote.clickShowMore();
        fillInCustomAddressInShippingAndBilling("1575 Space Center Drive", "Colorado Springs", "CO", "80915");
        recalculateTax(newPurchaseQuote);
        newPurchaseQuote.dialogBoxClickOk();
        openVertexTaxDetailsWindow(newPurchaseQuote);
        List<String> actualCustomAddressTaxesList = newPurchaseQuote.getMultipleTaxAmount("Purchase");
        assertEquals(expectedCustomAddressTaxesList, actualCustomAddressTaxesList);
        newPurchaseQuote.closeTaxDetailsDialog();
        String actualTaxForCustomAddress = newPurchaseQuote.getTotalTaxAmount();
        assertEquals(expectedTaxForCustomAddress, actualTaxForCustomAddress);
    }


    /**
     * CDBC-1053
     * Verify if Recalculation Pop up is displayed on changing ship-to to Custom Address and trying to exit without recalculation
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyRecalculateTaxPopupTest() {
        String vendorCode = "TestVendorCA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";
        String expectedTax = "44.50";
        String expectedMsg = "Ship-to address was changed but taxes have not been recalculated. Would you like to recalculate taxes now?";
        String expectedShipToChangedTax = "34.85";
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

        //Change address to Custom
        newPurchaseQuote.openShippingAndPaymentCategory();
        newPurchaseQuote.selectCustomInShipToAddressForPurchase();
        newPurchaseQuote.clickShowMore();
        fillInCustomAddressInShippingAndBilling("1575 Space Center Drive", "Colorado Springs", "CO", "80915");
        newPurchaseQuote.clickBackArrow();

        //Verify the Tax Recalculation Popup
        String actualMsg = newPurchaseQuote.getAlertMessagesInAP();
        assertEquals(actualMsg, expectedMsg);
        newPurchaseQuote.dialogBoxClickYes();
        newPurchaseQuote.dialogBoxClickOk();
        String actualShipToChangedTotalTax = newPurchaseQuote.getTotalTaxAmount();
        assertEquals(actualShipToChangedTotalTax, expectedShipToChangedTax);
        String purchaseQuoteNumber = newPurchaseQuote.getCurrentSalesNumber();

        //Verify Tax in XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseQuoteNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>34.85</TotalTax>"), " Tax Validation Failed");
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
