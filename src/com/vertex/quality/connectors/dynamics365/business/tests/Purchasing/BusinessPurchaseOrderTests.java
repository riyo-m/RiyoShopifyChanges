package com.vertex.quality.connectors.dynamics365.business.tests.Purchasing;

import com.vertex.quality.connectors.dynamics365.business.enums.BusinessMainMenuNavTabs;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessAdminHomePage;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessVertexAdminConfigurationPage;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessVertexAdminPage;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessPurchaseOrderPage;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessPurchaseOrdersListPage;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessPurchaseQuotePage;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessPurchaseQuotesListPage;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import com.vertex.quality.connectors.dynamics365.business.tests.base.BusinessBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Creates Purchase Order Document, Verifies taxes on total tax UI field, Statistics page XML, on updating line level details and Ship-to addresses
 * @author Shruti
 */
@Listeners(TestRerunListener.class)
public class BusinessPurchaseOrderTests extends BusinessBaseTest {
    BusinessAdminHomePage homePage;
    BusinessVertexAdminPage adminPage;

    @AfterMethod(alwaysRun = true)
    public void teardownTest( )
    {
        adminPage = new BusinessVertexAdminPage(driver);

        //Navigate to admin page and enable the Account Payable toggle button
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.updateAccountPayableToOn();
        adminPage.clickBackAndSaveArrow();

    }

    /**
     * CDBC-934
     * Verify the tax for Purchase Order, quantity >1000 and with decimal values, all product types
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyCreationOfPurchaseOrderTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1003";
        String quantity1 = "1003.12345";
        String expectedTax = "93,571.64";
        String location = "WEST";
        String expectedStatTax="93,571.64";
        List<String> expectedTaxAmounts = Arrays.asList("35,105", "0", "13,791.25", "20,062.47", "10,031.23", "9,529.67", "5,015.62", "1.6", "0.8", "0.76", "0.4", "2.76", "1.38", "1.31", "0.69", "4", "2", "1.9", "1", "8", "4", "3.8", "2");


        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        //Order
        BusinessPurchaseOrdersListPage purchaseOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Orders");
        BusinessPurchaseOrderPage newPurchaseOrder = purchaseOrders.pageNavMenu.clickNew();
        fillInPurchaseOrderGeneralInfo(newPurchaseOrder, vendorCode);
        newPurchaseOrder.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, location, quantity, null, 1);
        newPurchaseOrder.activateRow(2);
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity1, null, 2);
        newPurchaseOrder.activateRow(3);
        fillInItemsTableInfo("G/L Account", "10200", null, null, "1", "40", 3);
        newPurchaseOrder.activateRow(4);
        fillInItemsTableInfo("Resource", "MARTY", null, null, "1", null, 4);
        newPurchaseOrder.activateRow(5);
        fillInItemsTableInfo("Fixed Asset", "FA000110", null, null, "1", "100", 5);
        newPurchaseOrder.activateRow(6);
        fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, null, "1", "200", 6);
        newPurchaseOrder.exitExpandTable();

        //Verify taxes in Vertex tax details popup
        openVertexTaxDetailsWindow(newPurchaseOrder);
        List<String> actualTaxAmounts = newPurchaseOrder.getMultipleTaxAmount("Purchase");
        assertEquals(actualTaxAmounts, expectedTaxAmounts);
        newPurchaseOrder.closeTaxDetailsDialog();

        //Verify taxes in Statistics Page
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.ORDER.value, "Statistics");
        String actualStatTax=newPurchaseOrder.getAPStatTaxAmount();
        assertEquals(actualStatTax, expectedStatTax);
        newPurchaseOrder.closeTaxDetailsDialog();

        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseOrder.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTax);
        String purchaseOrderNumber = newPurchaseOrder.getCurrentSalesNumber();
        //Verify Tax in XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTax.replace(",","")+"</TotalTax>"), "Total Tax  Validation Failed");
        adminPage.clickBackAndSaveArrow();
    }

    /**
     * CDBC-984
     * Verify if vertex calls are not made after disabling the AP button on admin page
     *
     * @author Shruti
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Special"}, retryAnalyzer = TestRerun.class)
    public void verifyVertexCallsOnPurchaseOrderTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1908-S";
        String quantity = "1";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();

        //Update 'Account Payable Enabled' button to off
        adminPage.updateAccountPayableToOff();
        adminPage.clickBackAndSaveArrow();

        //Create purchase Order
        BusinessPurchaseOrdersListPage purchaseOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Orders");
        BusinessPurchaseOrderPage newPurchaseOrder = purchaseOrders.pageNavMenu.clickNew();
        fillInPurchaseOrderGeneralInfo(newPurchaseOrder, vendorCode);
        newPurchaseOrder.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newPurchaseOrder.activateRow(2);
        newPurchaseOrder.exitExpandTable();
        openVertexTaxDetailsWindow(newPurchaseOrder);
        String popUp = newPurchaseOrder.noTaxDetailsDialog();
        assertTrue(popUp.contains("No tax details on current document"), "Tax details Validation Failed");
    }


    /**
     * CDBC-998
     * Verify the tax for Purchase Order in Statistics tab for non-vertex tax area code
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyCreationOfPurchaseOrderForNonVertexTest() {
        String vendorCode = "First Up Consultants";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";
        String expectedTax = "25.00";
        String taxAreaCode = "CHICAGO, IL";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        //Create Order
        BusinessPurchaseOrdersListPage purchaseOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Orders");
        BusinessPurchaseOrderPage newPurchaseOrder = purchaseOrders.pageNavMenu.clickNew();
        fillInPurchaseOrderGeneralInfo(newPurchaseOrder, vendorCode);
        newPurchaseOrder.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        updateTaxAreaCode(taxAreaCode, 1);
        newPurchaseOrder.activateRow(2);
        newPurchaseOrder.exitExpandTable();

       //Update Tax Area Code in Invoice Details
        newPurchaseOrder.openInvoiceDetailsCategory();
        newPurchaseOrder.clickShowMore();
        newPurchaseOrder.enterTaxAreaCodeInvoiceDetails(taxAreaCode);
        newPurchaseOrder.dialogBoxClickYes();

        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.ORDER.value, "Statistics");
        newPurchaseOrder.dialogBoxClickClose();

        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseOrder.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTax);

        //Navigate to statistics and verify if non-vertex TAC field and its tax
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.ORDER.value, "Statistics");
        assertTrue(newPurchaseOrder.isNonVertexTaxAreaCodePresent(taxAreaCode, expectedTax), "NonVertex field validation failed");

    }

    /**
     * CDBC-1038
     * Verify the tax in total tax UI field, vertex tax details for Purchase Order on updating the Quantity, price and location and deleting line item
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxOnUpdatingLinePurchaseOrderTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String itemNo2 = "1900-S";
        String quantity = "1";
        String expectedTax = "63.40";
        String location = "WEST";
        String expectedTaxAfterDeletion = "128.00";
        List<String> expectedTaxAmounts = Arrays.asList("35", "0", "13.75", "10.52", "0", "4.13");

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        //Order
        BusinessPurchaseOrdersListPage purchaseOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Orders");
        BusinessPurchaseOrderPage newPurchaseOrder = purchaseOrders.pageNavMenu.clickNew();
        fillInPurchaseOrderGeneralInfo(newPurchaseOrder, vendorCode);
        newPurchaseOrder.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, location, quantity, null, 1);
        newPurchaseOrder.activateRow(2);
        fillInItemsTableInfo(itemType, itemNo2, null, location, quantity, null, 2);
        newPurchaseOrder.exitExpandTable();

        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseOrder.getTotalTaxAmount();
        assertEquals(expectedTax, actualTotalTax);
        openVertexTaxDetailsWindow(newPurchaseOrder);
        List<String> actualTaxAmounts = newPurchaseOrder.getMultipleTaxAmount("Purchase");
        assertEquals(expectedTaxAmounts, actualTaxAmounts);
        newPurchaseOrder.closeTaxDetailsDialog();
        newPurchaseOrder.expandTable();
        //Update location code, quantity, price, tax group code
        newPurchaseOrder.activateRow(1);
        addLineLevelLocationCode("EAST", 1);
        updateQuantity("2", 1);
        updatePrice("800", 1);
        updateTaxGroupCode("SUPPLIES", 1);

        String purchaseOrderNumber = newPurchaseOrder.getCurrentSalesNumber();
        //Verify Tax in XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>142.65</TotalTax>"), " Tax Validation Failed");
        assertTrue(xmlStr.contains("<Purchase purchaseClass=\"SUPPLIES\">1896-S</Purchase>"), "Tax Group Validation failed");
        assertTrue(xmlStr.contains("<Quantity>2.0</Quantity>"), "Quantity validation failed");
        assertTrue(xmlStr.contains("<UnitPrice>800.0</UnitPrice>"), "Unit Price validation failed");
        adminPage.clickBackAndSaveArrow();
        newPurchaseOrder.activateRow(2);
        deletingLine(2);
        //Verify tax in total tax UI field
        String actualTotalTaxAfterDeletion = newPurchaseOrder.getTotalTaxAmount();
        assertEquals(expectedTaxAfterDeletion, actualTotalTaxAfterDeletion);
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.filterDocuments(purchaseOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStrOnDeletion = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStrOnDeletion.contains(" <TotalTax>128.0</TotalTax>"), " Tax Validation Failed");
        assertTrue(!xmlStrOnDeletion.contains("<Purchase purchaseClass=\"FURNITURE\">1900-S</Purchase>"), "Item not deleted, Validation Failed");
        adminPage.clickBackAndSaveArrow();
    }

    /**
     * CDBC-1055
     * Creates a Purchase Order, adds in line item, changes Ship-to field to 'Location', verifies taxes and changes Ship-To field to Custom and verifies tax in XML
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxForDifferentShipToAddressPurchaseOrderTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";
        String expectedTax = "65.70";
        String location1 = "WEST";
        String location2 = "MAIN";
        String location3 = "EAST";
        String expectedUpdatedTax = "67.05";
        List<String> expectedDefaultTaxesList=Arrays.asList("35", "0", "13.75", "6.25", "1", "0.4", "0.4", "4", "2", "1.9", "1");
        List<String> expectedTaxAmounts = Arrays.asList("35", "0", "13.75", "6.25", "1", "0.4", "0.4", "6", "1.25", "0.5", "0.5", "0.5", "0.5", "0.25", "0.75");
        List<String> expectedCustomAddressTaxesList = Arrays.asList("35", "0", "13.75", "6.25", "1", "0.4", "0.4", "7", "0", "2.75");
        List<String> expectedCustomerAddressTaxesList= Arrays.asList("35", "0", "13.75", "6.25", "1", "0.4", "0.4","0") ;
        String expectedTaxForCustomAddress = "66.55";
        String expectedTaxForCustomerAddress="56.80";
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseOrdersListPage purchaseOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Orders");
        BusinessPurchaseOrderPage newPurchaseOrder = purchaseOrders.pageNavMenu.clickNew();
        fillInPurchaseOrderGeneralInfo(newPurchaseOrder, vendorCode);
        newPurchaseOrder.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, location1, quantity, null, 1);
        newPurchaseOrder.activateRow(2);
        fillInItemsTableInfo("Resource", "MARTY", null, location2, "1", "100", 2);
        newPurchaseOrder.activateRow(3);
        fillInItemsTableInfo("Fixed Asset", "FA000120", null, location3, "1", "10", 3);
        newPurchaseOrder.activateRow(4);
        fillInItemsTableInfo("Charge (Item)", "P-ALLOWANCE", null, null, "1", "100", 4);
        newPurchaseOrder.activateRow(5);
        newPurchaseOrder.exitExpandTable();
        openVertexTaxDetailsWindow(newPurchaseOrder);
        List<String> actualDefaultTaxesList = newPurchaseOrder.getMultipleTaxAmount("Purchase");
        assertEquals(expectedDefaultTaxesList, actualDefaultTaxesList);
        newPurchaseOrder.closeTaxDetailsDialog();
        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseOrder.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTax);

        //Update Ship-to to Location
        newPurchaseOrder.openShippingAndPaymentCategory();
        newPurchaseOrder.selectLocationInShipToAddress();
        newPurchaseOrder.updateLocationCodeFromShippingandBilling("CA");
        newPurchaseOrder.dialogBoxClickYes();
        newPurchaseOrder.clickRefreshButton();

        //Verify tax in total tax UI field
        String actualUpdatedTotalTax = newPurchaseOrder.getTotalTaxAmount();
        assertEquals(expectedUpdatedTax, actualUpdatedTotalTax);
        openVertexTaxDetailsWindow(newPurchaseOrder);
        List<String> actualTotalTaxes = newPurchaseOrder.getMultipleTaxAmount("Purchase");
        assertEquals(actualTotalTaxes, expectedTaxAmounts);
        newPurchaseOrder.closeTaxDetailsDialog();

        //Change address to Custom and verify tax
        newPurchaseOrder.selectCustomInShipToAddressForPurchase();
        newPurchaseOrder.dialogBogClickOkOnLocationChange();
        newPurchaseOrder.clickShowMore();
        fillInCustomAddressInShippingAndBilling("1790 Galleria Blvd", "Franklin", "TN", "37067");
        recalculateTax(newPurchaseOrder);
        newPurchaseOrder.dialogBoxClickOk();
        openVertexTaxDetailsWindow(newPurchaseOrder);
        List<String> actualCustomAddressTaxesList = newPurchaseOrder.getMultipleTaxAmount("Purchase");
        assertEquals(expectedCustomAddressTaxesList, actualCustomAddressTaxesList);
        newPurchaseOrder.closeTaxDetailsDialog();
        String actualTaxForCustomAddress = newPurchaseOrder.getTotalTaxAmount();
        assertEquals(actualTaxForCustomAddress, expectedTaxForCustomAddress);

        //Change Ship-to to Customer Address and verify tax
        newPurchaseOrder.selectCustomerInShipToAddress();
        newPurchaseOrder.selectCustomerNoInShippingAndBilling("C00520");
        newPurchaseOrder.clickRefreshButton();
        openVertexTaxDetailsWindow(newPurchaseOrder);
        List<String> actualCustomerAddressTaxesList = newPurchaseOrder.getMultipleTaxAmount("Purchase");
        assertEquals(actualCustomerAddressTaxesList, expectedCustomerAddressTaxesList);
        newPurchaseOrder.closeTaxDetailsDialog();
        String actualTaxForCustomerAddress = newPurchaseOrder.getTotalTaxAmount();
        assertEquals(actualTaxForCustomerAddress, expectedTaxForCustomerAddress);
        String pOrderNo=newPurchaseOrder.getCurrentSalesNumber();
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(pOrderNo);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>56.8</TotalTax>"), " Tax Validation Failed");
        adminPage.clickBackAndSaveArrow();

    }
    /**
     * CDBC-1061
     * Verify if Recalculation Pop up is displayed on changing ship-to to Custom Address and trying to exit without recalculation
     */
    @Test(alwaysRun = true, groups={"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyPurchaseOrderRecalculateTaxPopupTest() {
        String vendorCode="TestVendorCA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "2";
        String expectedTax = "89.00";
        String expectedShipToChangedTax = "97.50";
        String expectedMsg = "Ship-to address was changed but taxes have not been recalculated. Would you like to recalculate taxes now?";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseOrdersListPage purchaseOrders=homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Orders");
        BusinessPurchaseOrderPage newPurchaseOrder=purchaseOrders.pageNavMenu.clickNew();
        fillInPurchaseOrderGeneralInfo(newPurchaseOrder,vendorCode);
        newPurchaseOrder.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newPurchaseOrder.activateRow(2);
        newPurchaseOrder.exitExpandTable();

        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseOrder.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTax);

        //Change address to Custom
        newPurchaseOrder.openShippingAndPaymentCategory();
        newPurchaseOrder.selectCustomInShipToAddressForPurchase();
        newPurchaseOrder.clickShowMore();
        fillInCustomAddressInShippingAndBilling("1790 Galleria Blvd", "Franklin", "TN", "37067");
        newPurchaseOrder.clickBackArrow();

        //Verify the Tax Recalculation Popup
        String actualMsg = newPurchaseOrder.getAlertMessagesInAP();
        assertEquals(actualMsg, expectedMsg);
        newPurchaseOrder.dialogBoxClickYes();
        newPurchaseOrder.dialogBoxClickOk();
        String actualShipToChangedTotalTax = newPurchaseOrder.getTotalTaxAmount();
        assertEquals(actualShipToChangedTotalTax, expectedShipToChangedTax);
        String purchaseQuoteNumber = newPurchaseOrder.getCurrentSalesNumber();

        //Verify Tax in XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseQuoteNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>97.5</TotalTax>"), " Tax Validation Failed");
        adminPage.clickBackAndSaveArrow();

    }

    /**
     * CDBC-1065
     * Verify if Recalculation Pop up is displayed on changing ship-to to Custom Address and trying to exit without recalculation, Click NO on popup
     */
    @Test(alwaysRun = true, groups={"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyExitingWithoutRecalculateTaxPopupTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "2";
        String expectedTax = "89.00";
        String expectedMsg = "Ship-to address was changed but taxes have not been recalculated. Would you like to recalculate taxes now?";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseOrdersListPage purchaseOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Orders");
        BusinessPurchaseOrderPage newPurchaseOrder = purchaseOrders.pageNavMenu.clickNew();
        fillInPurchaseOrderGeneralInfo(newPurchaseOrder, vendorCode);
        newPurchaseOrder.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newPurchaseOrder.activateRow(2);
        newPurchaseOrder.exitExpandTable();

        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseOrder.getTotalTaxAmount();
        assertEquals(expectedTax, actualTotalTax);

        //Change address to Custom
        newPurchaseOrder.openShippingAndPaymentCategory();
        newPurchaseOrder.selectCustomInShipToAddressForPurchase();
        newPurchaseOrder.clickShowMore();
        fillInCustomAddressInShippingAndBilling("1186 Roseville Pkwy", "Roseville", "CA", "95678");
        String purchaseOrder=newPurchaseOrder.getCurrentSalesNumber();
        newPurchaseOrder.clickBackArrow();

        //Verify the Tax Recalculation Popup
        String actualMsg = newPurchaseOrder.getAlertMessagesInAP();
        assertEquals(actualMsg, expectedMsg);
        newPurchaseOrder.dialogBoxClickNo();
        purchaseOrders.searchForPurchaseDocument(purchaseOrder);
        //Verify tax in total tax UI field
        String actualTaxTotal = newPurchaseOrder.getTotalTaxAmount();
        assertEquals(actualTaxTotal, expectedTax);
    }
    /**
     * CDBC-1063
     * Verify if tax is recalculated on changing Ship-to field to Customer Address and updating line items
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxOnUpdatingLineForShipToAddressTest() {
        String vendorCode = "TestVendorCA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String itemNo2 = "1900-S";
        String quantity = "1";
        String expectedTax = "57.88";
        String expectedTaxForCustomerAddress = "0.00";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesBasePage businessPage = new BusinessSalesBasePage(driver);
        //Create Purchase Order
        BusinessPurchaseOrdersListPage purchaseOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Orders");
        BusinessPurchaseOrderPage newPurchaseOrder = purchaseOrders.pageNavMenu.clickNew();
        fillInPurchaseOrderGeneralInfo(newPurchaseOrder, vendorCode);
        newPurchaseOrder.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newPurchaseOrder.activateRow(2);
        fillInItemsTableInfo(itemType, itemNo2, null, null, quantity, null, 2);
        newPurchaseOrder.exitExpandTable();

        //Verify tax in total tax UI field
        String actualTotalTax = newPurchaseOrder.getTotalTaxAmount();
        assertEquals(expectedTax, actualTotalTax);

        //Change address to Customer Address
        newPurchaseOrder.openShippingAndPaymentCategory();
        newPurchaseOrder.selectCustomerInShipToAddress();
        newPurchaseOrder.selectCustomerNoInShippingAndBilling("C00520");
        newPurchaseOrder.clickRefreshButton();

        //Update Item, location code, quantity, price, tax group code
        newPurchaseOrder.expandTable();
        newPurchaseOrder.activateRow(1);
        updateItemNumber("1900-S",1);
        updateQuantity("2", 1);
        updatePrice("1000", 1);
        newPurchaseOrder.exitExpandTable();
        String actualTaxForCustomerAddress = newPurchaseOrder.getTotalTaxAmount();
        assertEquals(expectedTaxForCustomerAddress, actualTaxForCustomerAddress);
        String purchaseOrderNumber = newPurchaseOrder.getCurrentSalesNumber();
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>0.0</TotalTax>"), " Tax Validation Failed");
        adminPage.clickBackAndSaveArrow();

    }

    /**
     * @TestCase CDBC-1309
     * @Description - Create a Purchase Quote, convert to PO, undercharge and recalculate Sales Tax, Post and verify accrual call
     * @Author Mario Saint-Fleur
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyAccrualCallWhenCreatingPurchaseQuoteConvertToPOAndUnderChargingTaxTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String itemNo2 = "1900-S";
        String quantity = "1";
        String location = "WEST";
        BusinessSalesBasePage businessPage = new BusinessSalesBasePage(driver);

        //Create Purchase Quote
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseQuotesListPage purchaseQuotes = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Quotes");
        BusinessPurchaseQuotePage newPurchaseQuote = purchaseQuotes.pageNavMenu.clickNew();
        fillInPurchaseQuoteGeneralInfo(newPurchaseQuote, vendorCode);
        newPurchaseQuote.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, location, quantity, null, 1);
        newPurchaseQuote.activateRow(2);
        fillInItemsTableInfo(itemType, itemNo2, null, "MAIN", quantity, null, 2);
        newPurchaseQuote.exitExpandTable();

        //Convert to Purchase Order
        BusinessPurchaseOrderPage convertedPurchaseOrder = newPurchaseQuote.convertQuoteToOrder();

        //Verify taxes in Vertex tax details
        openVertexTaxDetailsWindow(convertedPurchaseOrder);
        convertedPurchaseOrder.clickAndEnterActualSalesTaxAmount("50.00");
        convertedPurchaseOrder.clickRecalculateTax();
        String calculatedSalesTax = convertedPurchaseOrder.getCalculatedSalesTaxAmount();
        String actualSalesTax = convertedPurchaseOrder.getActualSalesTaxAmount();
        String accrualSalesTax = convertedPurchaseOrder.getAccrualSalesTaxAmount();
        assertEquals( calculatedSalesTax, "59.65");
        assertEquals(actualSalesTax, "50.00");
        assertEquals(accrualSalesTax,"9.64");
        convertedPurchaseOrder.closeTaxDetailsDialog();

        String purchaseOrderNumber = convertedPurchaseOrder.getCurrentSalesNumber();
        convertedPurchaseOrder.enterVendorInvoiceNo(purchaseOrderNumber);

        //Verify tax in verify tax details
        openVertexTaxDetailsWindow(convertedPurchaseOrder);
        calculatedSalesTax = convertedPurchaseOrder.getCalculatedSalesTaxAmount();
        actualSalesTax = convertedPurchaseOrder.getActualSalesTaxAmount();
        accrualSalesTax = convertedPurchaseOrder.getAccrualSalesTaxAmount();
        assertEquals(calculatedSalesTax,"59.65");
        assertEquals(actualSalesTax,"50.01");
        assertEquals(accrualSalesTax, "9.64");
        convertedPurchaseOrder.closeTaxDetailsDialog();

        //Post the Purchase Order
        postDocumentAndSelectPostingOption(convertedPurchaseOrder, "Receive and Invoice");
        convertedPurchaseOrder.dialogBoxClickOk();
        convertedPurchaseOrder.dialogBoxClickYes();

        openVertexTaxDetailsWindow(convertedPurchaseOrder);
        convertedPurchaseOrder.closeTaxDetailsDialog();

        //Verify Accrual Request is not present
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseOrderNumber);
        adminPage.filterxml("Tax Calc Request");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(!xmlStr.contains("<AccrualRequest postToJournal=false"), "Accrual Request expected NOT to be present");
        adminPage.clickBackAndSaveArrow();

    }

    /**
     * @TestCase CDBC-1320
     * @Description - Create a Purchase Order, with negative quantity, and verify total tax, total calculated tax, and actual tax
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_InProgress"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxAmountIsNegativeWhenAddingNegativeQuantityPurchaseOrderTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "-2";
        String expectedTax = "-113.56";

        //Create Purchase Order
        BusinessPurchaseOrdersListPage purchaseOrders = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Orders");
        BusinessPurchaseOrderPage newPurchaseOrder = purchaseOrders.pageNavMenu.clickNew();
        fillInPurchaseOrderGeneralInfo(newPurchaseOrder, vendorCode);

        newPurchaseOrder.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newPurchaseOrder.activateRow(2);
        fillInItemsTableInfo("Resource", "MARY", null, null, "-3", null, 2);
        newPurchaseOrder.activateRow(2);
        newPurchaseOrder.exitExpandTable();

        //Verify no tax details popup
        openVertexTaxDetailsWindow(newPurchaseOrder);
        String popUp = newPurchaseOrder.noTaxDetailsDialog();
        assertTrue(popUp.contains("No tax details on current document"), "Tax details Validation Failed");

        //Clicks the recalculate tax button
        newPurchaseOrder.salesEditNavMenu.clickMoreOptionsActionsButton();
        newPurchaseOrder.salesEditNavMenu.selectRecalculateTaxButton();

        //Verify total tax in the UI and tax in verify tax details
        String actualTotalTax = newPurchaseOrder.getTotalTaxAmount();
        assertEquals(expectedTax, actualTotalTax);
        openVertexTaxDetailsWindow(newPurchaseOrder);
        String calculatedSalesTax = newPurchaseOrder.getCalculatedSalesTaxAmount();
        String actualSalesTax = newPurchaseOrder.getActualSalesTaxAmount();
        assertEquals("-121.49", calculatedSalesTax);
        assertEquals("-121.49", actualSalesTax);
        newPurchaseOrder.closeTaxDetailsDialog();
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
