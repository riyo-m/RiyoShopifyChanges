package com.vertex.quality.connectors.dynamics365.business.tests.Purchasing;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.dynamics365.business.enums.BusinessMainMenuNavTabs;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessAdminHomePage;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessVertexAdminConfigurationPage;
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
 * Creates Purchase Credit Memo Document, Verifies taxes on total tax UI field, XML, on updating line level details and Ship-to addresses
 * @author Shruti
 */
@Listeners(TestRerunListener.class)
public class BusinessPurchaseCreditMemoTests extends BusinessBaseTest {
    BusinessAdminHomePage homePage;
    BusinessVertexAdminPage adminPage;
    BusinessVertexAdminConfigurationPage configPage;

    @BeforeMethod(alwaysRun = true)
    public void setupTest( )
    {
        homePage = new BusinessAdminHomePage(driver);
        adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        configPage = new BusinessVertexAdminConfigurationPage(driver);

        String role="Business Manager";
        String verifyPage=homePage.verifyHomepageHeader();
        if(!verifyPage.contains(role)){

            //navigate to select role as Business Manager
            homePage.selectSettings();
            homePage.navigateToManagerInSettings(role);
        }

        adminPage.openXmlLogAdmin();
        adminPage.updateLogAddressRequestOn();
        adminPage.updateLogAddressResponseOn();
        configPage.clickBackAndSaveArrow();

    }
    /**
     *  CDBC-930
     *  Verify the credit memo tax, for Quantity >1000 and with decimal values, all Product types
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyVendorForCreditMemoTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1900-S";
        String quantity = "1001";
        String quantity1 = "1002.12345";
        String expectedTax = "32,063.12";
        List<String> expectedQuantities=Arrays.asList("1,001","1,001","1,002.12345","1,002.12345","1","1","1","1");
        List<String> expectedTaxAmounts=Arrays.asList("8,008","8,008","8,016.99","8,016.99","1.2","4.14","1.8","6");
        String location="EAST";
        BusinessPurchaseCreditMemoListPage purchaseCreditMemo = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Credit Memos");
        BusinessPurchaseCreditMemoPage newCreditMemo = purchaseCreditMemo.pageNavMenu.clickNew();
        fillInPurchaseCreditMemoGeneralInfo(newCreditMemo, vendorCode);
        newCreditMemo.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, location, quantity, "200", 1);
        newCreditMemo.activateRow(2);
        fillInItemsTableInfo(itemType, itemNo, null, location, quantity1, "200", 2);
        newCreditMemo.activateRow(3);
        fillInItemsTableInfo("G/L Account", "10400", null, null, "1", "20", 3);
        newCreditMemo.activateRow(4);
        fillInItemsTableInfo("Resource", "MARTY", null, null, "1", null, 4);
        newCreditMemo.activateRow(5);
        fillInItemsTableInfo("Fixed Asset", "FA000110", null, null, "1", "30", 5);
        newCreditMemo.activateRow(6);
        fillInItemsTableInfo("Charge (Item)", "P-ALLOWANCE", null, null, "1", "100", 6);
        newCreditMemo.exitExpandTable();

        //Verify tax in total tax UI field
        String actualTotalTax = newCreditMemo.getTotalTaxAmount();
        assertEquals(expectedTax,actualTotalTax);
        String creditMemoNumber = newCreditMemo.getCurrentSalesNumber();
        openVertexTaxDetailsWindow(newCreditMemo);
        List<String> actualQuantities=newCreditMemo.getMultipleQuantities();
        assertEquals(actualQuantities,expectedQuantities);
        List<String> actualTaxAmounts=newCreditMemo.getMultipleTaxAmount("Purchase");
        assertEquals(actualTaxAmounts,expectedTaxAmounts);
        newCreditMemo.closeTaxDetailsDialog();

        //Verify taxes in Statistics Page
        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.CREDIT_MEMO.value, "Statistics");
        String actualStatTax=newCreditMemo.getAPStatTaxAmount();
        assertEquals(expectedTax,actualStatTax);
        newCreditMemo.closeTaxDetailsDialog();

        //Verify Tax in XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(creditMemoNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>-32063.12</TotalTax>"), " Tax Validation Failed");


    }

    /**
     * CDBC-1052
     * Verify the tax for Purchase Credit Memo, vertex tax details and XML on updating the Quantity, price and location and deleting line     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxOnUpdatingLinePurchaseCreditMemoTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String itemNo2="1900-S";
        String quantity = "1";
        String expectedTax = "57.77";
        String location = "WEST";
        String expectedTaxAfterDeletion="120.00";
        List<String> expectedTaxAmounts = Arrays.asList("35", "0", "13.75","9.02");

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        //Create Credit Memo Doc
        BusinessPurchaseCreditMemoListPage purchaseCreditMemo = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Credit Memos");
        BusinessPurchaseCreditMemoPage newCreditMemo = purchaseCreditMemo.pageNavMenu.clickNew();
        fillInPurchaseCreditMemoGeneralInfo(newCreditMemo, vendorCode);
        newCreditMemo.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, location, quantity, null, 1);
        newCreditMemo.activateRow(2);
        fillInItemsTableInfo(itemType, itemNo2, null, null, quantity, null, 2);
        newCreditMemo.exitExpandTable();

        //Verify tax in total tax UI field
        String actualTotalTax = newCreditMemo.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTax);
        openVertexTaxDetailsWindow(newCreditMemo);
        List<String> actualTaxAmounts = newCreditMemo.getMultipleTaxAmount("Purchase");
        assertEquals(actualTaxAmounts, expectedTaxAmounts);
        newCreditMemo.closeTaxDetailsDialog();
        newCreditMemo.expandTable();
        //Update location code, quantity, price, tax group code
        newCreditMemo.activateRow(1);
        updateItemNumber("1906",1);
        updateQuantity("2",1);
        updatePrice("1000",1);
        updateTaxGroupCode("FREIGHT",1);
        newCreditMemo.exitExpandTable();
        String purchaseOrderNumber = newCreditMemo.getCurrentSalesNumber();
        //Verify Tax in XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>-129.02</TotalTax>"), " Tax Validation Failed");
        assertTrue(xmlStr.contains("<Purchase purchaseClass=\"FREIGHT\">1906-S</Purchase>"),"Tax Group Validation failed");
        assertTrue(xmlStr.contains("<Quantity>2.0</Quantity>"),"Quantity validation failed");
        assertTrue(xmlStr.contains("<UnitPrice>-1000.0</UnitPrice>"),"Unit Price validation failed");
        adminPage.clickBackAndSaveArrow();
        newCreditMemo.activateRow(2);
        deletingLine(2);
        openVertexTaxDetailsWindow(newCreditMemo);
        String tax= newCreditMemo.getTaxAmount();
        newCreditMemo.closeTaxDetailsDialog();
        //Verify tax in total tax UI field
        String actualTotalTaxAfterDeletion = newCreditMemo.getTotalTaxAmount();
        assertEquals(expectedTaxAfterDeletion, actualTotalTaxAfterDeletion);
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.filterDocuments(purchaseOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStrOnDeletion = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStrOnDeletion.contains(" <TotalTax>-120.0</TotalTax>"), " Tax Validation Failed");
        assertTrue(!xmlStrOnDeletion.contains("<Purchase purchaseClass=\"FURNITURE\">1900-S</Purchase>"),"Item not deleted, Validation Failed");
    }
    /**
     * CDBC-1121
     * Verify if tax is recalculated on changing Ship-to field to Custom Address and updating line items
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxOnUpdatingLineCreditMemoTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String itemNo2 = "1906-S";
        String quantity = "1";
        String expectedTax = "60.00";
        String expectedTaxForCustomAddress = "181.25";
        List<String> expectedTaxAmounts=Arrays.asList("150","31.25");
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        //Create Credit Memo Doc
        BusinessPurchaseCreditMemoListPage purchaseCreditMemo = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Credit Memos");
        BusinessPurchaseCreditMemoPage newCreditMemo = purchaseCreditMemo.pageNavMenu.clickNew();
        fillInPurchaseCreditMemoGeneralInfo(newCreditMemo, vendorCode);
        newCreditMemo.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newCreditMemo.activateRow(2);
        fillInItemsTableInfo(itemType, itemNo2, null, null, quantity, null, 2);
        newCreditMemo.exitExpandTable();

        //Verify tax in total tax UI field
        String actualTotalTax = newCreditMemo.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTax);

        //Change address to Custom Address
        newCreditMemo.openShippingAndPaymentCategory();
        newCreditMemo.selectCustomInShipToAddressForPurchase();
        newCreditMemo.clickShowMore();
        fillInCustomAddressInShippingAndBilling(Address.Illinois.addressLine1, Address.Illinois.city, Address.Illinois.state.abbreviation, Address.Illinois.zip5);

        //Update Item, quantity, price
        newCreditMemo.expandTable();
        newCreditMemo.activateRow(1);
        updateItemNumber("1900-S", 1);
        updateQuantity("3", 1);
        updatePrice("800", 1);
        newCreditMemo.exitExpandTable();
        recalculateTax(newCreditMemo);

        //Verify tax
        openVertexTaxDetailsWindow(newCreditMemo);
        List<String> actualTaxAmounts = newCreditMemo.getMultipleTaxAmount("Purchase");
        assertEquals(actualTaxAmounts, expectedTaxAmounts);
        newCreditMemo.closeTaxDetailsDialog();
        String actualTaxForCustomerAddress = newCreditMemo.getTotalTaxAmount();
        assertEquals(actualTaxForCustomerAddress, expectedTaxForCustomAddress);
        String purchaseInvoiceNumber = newCreditMemo.getCurrentSalesNumber();
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseInvoiceNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>-181.25</TotalTax>"), " Tax Validation Failed");
    }

    /**
     *  CDBC-1086
     *  Verify the tax for Purchase CreditMemo, Recalculate the taxes on default and changing addresses to Custom and Alternate
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyRecalculatedTaxesForCreditMemoTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";
        String expectedTax="30";
        int expectedCalls=4;
        String expectedTaxForCustom="34.85";
        int expectedCallsForCustom=8;
        String expectedTaxForAlternate="48.75";
        int expectedCallsForAlternate=10;
        List<String> expectedTaxAmounts=Arrays.asList("14.5","15.35","5");

        //Create Credit Memo Doc
        BusinessPurchaseCreditMemoListPage purchaseCreditMemo = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Credit Memos");
        BusinessPurchaseCreditMemoPage newCreditMemo = purchaseCreditMemo.pageNavMenu.clickNew();
        fillInPurchaseCreditMemoGeneralInfo(newCreditMemo, vendorCode);
        newCreditMemo.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newCreditMemo.activateRow(2);
        newCreditMemo.exitExpandTable();

        //Verify tax and get Doc number
        openVertexTaxDetailsWindow(newCreditMemo);
        String tax=newCreditMemo.getTaxAmount();
        assertEquals(tax, expectedTax);
        newCreditMemo.closeTaxDetailsDialog();
        String actualTotalTax = newCreditMemo.getTotalTaxAmount();
        assertEquals("30.00",actualTotalTax);
        String creditMemoNumber = newCreditMemo.getCurrentSalesNumber();
        recalculateTax(newCreditMemo);

        //Check XML calls on admin page
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(creditMemoNumber);
        int calls=adminPage.getResponseCount(creditMemoNumber);
        assertEquals(calls,expectedCalls);
        newCreditMemo.clickBackAndSaveArrow();

        //Change address to Custom Address & recalculate
        newCreditMemo.openShippingAndPaymentCategory();
        newCreditMemo.selectCustomInShipToAddressForPurchase();
        newCreditMemo.clickShowMore();
        fillInCustomAddressInShippingAndBilling(Address.ColoradoSprings.addressLine1, Address.ColoradoSprings.city, Address.ColoradoSprings.state.abbreviation, Address.ColoradoSprings.zip5);
        recalculateTax(newCreditMemo);
        newCreditMemo.dialogBoxClickOk();

        //Verify XML for Address Cleanse calls
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.filterDocuments(creditMemoNumber);
        adminPage.filterxml("Address Cleanse Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("  <StreetAddress1>1575 Space Center Dr</StreetAddress1>\n" +
                "            <City>Colorado Springs</City>\n" +
                "            <MainDivision>CO</MainDivision>\n" +
                "            <SubDivision>El Paso</SubDivision>\n" +
                "            <PostalCode>80915-2441</PostalCode>\n" +
                "            <Country>USA</Country>"));
        newCreditMemo.clickBackAndSaveArrow();

        //Verify tax
        openVertexTaxDetailsWindow(newCreditMemo);
        List<String> actualTaxAmounts = newCreditMemo.getMultipleTaxAmount("Purchase");
        assertEquals(expectedTaxAmounts, actualTaxAmounts);
        newCreditMemo.closeTaxDetailsDialog();
        String actualTotalTaxForCustom = newCreditMemo.getTotalTaxAmount();
        assertEquals(expectedTaxForCustom,actualTotalTaxForCustom);

        //Verify calls and tax in XML
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.filterDocuments(creditMemoNumber);
        int callsForCustom=adminPage.getResponseCount(creditMemoNumber);
        assertEquals(callsForCustom,expectedCallsForCustom);
        adminPage.filterxml("Tax Calc Response");
        String xmlString = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlString.contains(" <TotalTax>-34.85</TotalTax>"), " Tax Validation Failed");
        newCreditMemo.clickBackAndSaveArrow();

        //Change address to Alternate Address & verify tax
        newCreditMemo.selectAlternateVendorAddInShipToAddress();
        newCreditMemo.selectVendorCode("ALTADD");
        String actualTotalTaxForAlternate = newCreditMemo.getTotalTaxAmount();
        assertEquals(expectedTaxForAlternate,actualTotalTaxForAlternate);
        newCreditMemo.clickRefreshButton();

        //Verify calls and tax in XML for Alternate address
        homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.filterDocuments(creditMemoNumber);
        int callsForAlternate=adminPage.getResponseCount(creditMemoNumber);
        assertEquals(callsForAlternate,expectedCallsForAlternate);
        adminPage.filterxml("Tax Calc Response");
        String xmlStrAlt = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStrAlt.contains(" <TotalTax>-48.75</TotalTax>"), " Tax Validation Failed");

    }

    /**
     * CDBC-1088
     * Verify if Recalculation Pop up is displayed on changing ship-to to Custom Address and trying to exit without recalculation, clicking 'Yes' in popup and verifying tax, followed by clicking 'No' and verifying tax
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyRecalculateTaxPopupCreditMemoTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";
        String expectedTax = "30.00";
        String expectedMsg = "Ship-to address was changed but taxes have not been recalculated. Would you like to recalculate taxes now?";
        String expectedShipToChangedTax = "50.50";

        //Create Credit Memo Doc
        BusinessPurchaseCreditMemoListPage purchaseCreditMemo = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Credit Memos");
        BusinessPurchaseCreditMemoPage newCreditMemo = purchaseCreditMemo.pageNavMenu.clickNew();
        fillInPurchaseCreditMemoGeneralInfo(newCreditMemo, vendorCode);
        newCreditMemo.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newCreditMemo.activateRow(2);
        newCreditMemo.exitExpandTable();

        //Verify tax in total tax UI field
        String actualTotalTax = newCreditMemo.getTotalTaxAmount();
        assertEquals(actualTotalTax, expectedTax);

        //Change address to Custom
        newCreditMemo.openShippingAndPaymentCategory();
        newCreditMemo.selectCustomInShipToAddressForPurchase();
        newCreditMemo.clickShowMore();
        fillInCustomAddressInShippingAndBilling("317 Commercial Ave", "Anacortes", "WA", "98052");
        newCreditMemo.clickBackArrow();

        //Verify the Tax on clicking 'Yes' for Recalculation Popup
        String actualMsg = newCreditMemo.getAlertMessagesInAP();
        assertEquals(actualMsg, expectedMsg);
        newCreditMemo.dialogBoxClickYes();
        newCreditMemo.dialogBoxClickOk();
        String actualShipToChangedTotalTax = newCreditMemo.getTotalTaxAmount();
        assertEquals(actualShipToChangedTotalTax, expectedShipToChangedTax);
        String purchaseCreditNumber = newCreditMemo.getCurrentSalesNumber();

        //Verify Tax in XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(purchaseCreditNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>-50.5</TotalTax>"), " Tax Validation Failed");
        newCreditMemo.clickBackAndSaveArrow();
        fillInCustomAddressInShippingAndBilling("1 Spruce", "Eliot", "ME", "03903");
        newCreditMemo.clickBackArrow();

        //Verify the Tax on clicking 'No' for Recalculation Popup
        String actualMessage= newCreditMemo.getAlertMessagesInAP();
        assertEquals(actualMessage, expectedMsg);
        newCreditMemo.dialogBoxClickNo();
        newCreditMemo.searchForPurchaseDocuments(purchaseCreditNumber);
        String actualCustomTotalTax = newCreditMemo.getTotalTaxAmount();
        assertEquals(actualCustomTotalTax, expectedShipToChangedTax);

    }


    /**
     * CDBC-1090
     * Verify the tax details for posted document lines to reverse
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxOnPostedDocumentsLineToReversePurchaseCreditMemoTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";
        String expectedTax = "44.50";

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
        //Create Credit Memo
        BusinessPurchaseCreditMemoListPage purchaseCreditMemo = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Credit Memos");
        BusinessPurchaseCreditMemoPage newCreditMemo = purchaseCreditMemo.pageNavMenu.clickNew();
        fillInPurchaseCreditMemoGeneralInfo(newCreditMemo, vendorCode);

        //Select Process tab and filter document
        newCreditMemo.salesEditNavMenu.clickPrepareButton();
        newCreditMemo.selectPostedDocumentLinesToReverse();
        newCreditMemo.enableReturnOriginalQuantity();
        newCreditMemo.filterDocuments(postedInvoiceNo);
        newCreditMemo.dialogBoxClickOk();
        newCreditMemo.expandTable();
        String documentNo =  newCreditMemo.getCurrentSalesNumber();
        String actualTotalTaxAmount =  newCreditMemo.getTotalTaxAmount();
        assertEquals(expectedTax, actualTotalTaxAmount);
        //Verify XML
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(documentNo);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>-44.5</TotalTax>"), "Total Tax Validation Failed");
    }

    /**
     * CDBC-1092
     * Post the purchase credit memo
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void postPurchaseCreditMemoTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1";
        String expectedTax = "48.04";
        List<String> expectedQuantities=Arrays.asList("1","2");
        List<String> expectedTaxAmounts=Arrays.asList("30","18.04");

        //Create Credit Memo Doc
        BusinessPurchaseCreditMemoListPage purchaseCreditMemo = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Credit Memos");
        BusinessPurchaseCreditMemoPage newCreditMemo = purchaseCreditMemo.pageNavMenu.clickNew();
        fillInPurchaseCreditMemoGeneralInfo(newCreditMemo, vendorCode);
        newCreditMemo.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newCreditMemo.activateRow(2);
        fillInItemsTableInfo(itemType, "1900-S", null, null, "2", null, 2);
        newCreditMemo.activateRow(2);
        newCreditMemo.exitExpandTable();

        String creditMemoNo =  newCreditMemo.getCurrentSalesNumber();
        newCreditMemo.enterCreditMemoNo(creditMemoNo);
        //Verify tax in total tax UI field
        String actualTotalTax = newCreditMemo.getTotalTaxAmount();
        assertEquals(expectedTax,actualTotalTax);
        openVertexTaxDetailsWindow(newCreditMemo);
        List<String> quantities = newCreditMemo.getMultipleQuantities();
        assertEquals(quantities, expectedQuantities);
        List<String> actualTaxAmounts = newCreditMemo.getMultipleTaxAmount("Purchase");
        assertEquals(expectedTaxAmounts, actualTaxAmounts);
        newCreditMemo.closeTaxDetailsDialog();
        BusinessPurchaseCreditMemoPage postedCreditMemo=postPurchaseCreditMemo(newCreditMemo);
        String actualPostedTax=postedCreditMemo.getTotalTaxAmount();
        assertEquals(expectedTax,actualPostedTax);
        openVertexTaxDetailsWindow(newCreditMemo);
        List<String> postedQuantities = postedCreditMemo.getMultipleQuantities();
        assertEquals(postedQuantities, expectedQuantities);
        List<String> postedActualTaxAmounts = postedCreditMemo.getMultipleTaxAmount("Purchase");
        assertEquals(postedActualTaxAmounts, expectedTaxAmounts);
        postedCreditMemo.closeTaxDetailsDialog();
    }

    /**
     * @TestCase CDBC-1310
     * @Description - Create a Purchase Credit Memo, convert to Posted Purchase Credit Memo, while similar charging, Post and verify accrual call is not present
     * @Author Mario Saint-Fleur
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyAccrualCallWhenCreatingPurchaseCreditMemoConvertToPostedPurchaseCreditMemoAndSimilarChargingTaxTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "1.50";

        //Create Credit Memo Doc
        BusinessPurchaseCreditMemoListPage purchaseCreditMemo = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Credit Memos");
        BusinessPurchaseCreditMemoPage newCreditMemo = purchaseCreditMemo.pageNavMenu.clickNew();
        fillInPurchaseCreditMemoGeneralInfo(newCreditMemo, vendorCode);
        newCreditMemo.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newCreditMemo.activateRow(2);
        fillInItemsTableInfo(itemType, "1900-S", null, null, "2.75", null, 2);
        newCreditMemo.activateRow(2);
        newCreditMemo.exitExpandTable();

        //Change address to Custom Address
        newCreditMemo.openShippingAndPaymentCategory();
        newCreditMemo.selectCustomInShipToAddressForPurchase();
        newCreditMemo.clickShowMore();
        fillInCustomAddressInShippingAndBilling(Address.ColoradoSprings.addressLine1, Address.ColoradoSprings.city, Address.ColoradoSprings.state.abbreviation, Address.ColoradoSprings.zip5);

        String creditMemoNo =  newCreditMemo.getCurrentSalesNumber();
        newCreditMemo.enterCreditMemoNo(creditMemoNo);

        //Verify tax in verify tax details
        openVertexTaxDetailsWindow(newCreditMemo);
        String calculatedSalesTax = newCreditMemo.getCalculatedSalesTaxAmount();
        String actualSalesTax = newCreditMemo.getActualSalesTaxAmountWithoutInput();
        String accrualSalesTax = newCreditMemo.getAccrualSalesTaxAmount();
        assertEquals("-69.80", calculatedSalesTax);
        assertEquals("69.80", actualSalesTax);
        assertEquals("0.00", accrualSalesTax);
        newCreditMemo.closeTaxDetailsDialog();

        openRecalculateTaxDetailsWindow(newCreditMemo);

        //Post the Purchase Credit Memo
        newCreditMemo.dialogBoxClickOk();
        BusinessPurchaseCreditMemoPage postedCreditMemo = postPurchaseCreditMemo(newCreditMemo);
        openVertexTaxDetailsWindow(postedCreditMemo);
        calculatedSalesTax = newCreditMemo.getCalculatedSalesTaxAmount();
        actualSalesTax = newCreditMemo.getActualSalesTaxAmountWithoutInput();
        accrualSalesTax = newCreditMemo.getAccrualSalesTaxAmount();
        assertEquals("81.08", calculatedSalesTax);
        assertEquals("81.08", actualSalesTax);
        assertEquals("0.00", accrualSalesTax);
        newCreditMemo.closeTaxDetailsDialog();

        //Verify Accrual Request is not present
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(creditMemoNo);
        adminPage.filterxml("Tax Calc Request");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(!xmlStr.contains("<AccrualRequest"), "Accrual Request expected NOT to be present");
    }

    /**
     * @TestCase CDBC-1319
     * @Description - Create a Purchase Credit Memo, with negative quantity, and verify total tax, total calculated tax, actual tax, and has read only set as true
     * @Author Mario Saint-Fleur
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxAmountIsNegativeWhenAddingNegativeQuantityPurchaseCreditMemoTest() {
        String vendorCode = "TestVendorPA";
        String itemType = "Item";
        String itemNo = "1896-S";
        String quantity = "-2";
        String expectedTax = "-76.56";

        //Create Credit Memo Doc
        BusinessPurchaseCreditMemoListPage purchaseCreditMemo = homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Credit Memos");
        BusinessPurchaseCreditMemoPage newCreditMemo = purchaseCreditMemo.pageNavMenu.clickNew();
        fillInPurchaseCreditMemoGeneralInfo(newCreditMemo, vendorCode);
        newCreditMemo.expandTable();
        fillInItemsTableInfo(itemType, itemNo, null, null, quantity, null, 1);
        newCreditMemo.activateRow(2);
        fillInItemsTableInfo("Resource", "Mary", null, null, "-3", null, 2);
        newCreditMemo.activateRow(2);
        newCreditMemo.exitExpandTable();

        //Enter Credit Memo Number
        String creditMemoNo =  newCreditMemo.getCurrentSalesNumber();
        newCreditMemo.enterCreditMemoNo(creditMemoNo);

        //Verify total tax in the UI and tax in verify tax details
        String actualTotalTax = newCreditMemo.getTotalTaxAmount();
        assertEquals(expectedTax,actualTotalTax);
        openVertexTaxDetailsWindow(newCreditMemo);
        String calculatedSalesTax = newCreditMemo.getCalculatedSalesTaxAmount();
        String actualSalesTax = newCreditMemo.getActualSalesTaxAmountWithoutInput();
        String actualSalesTaxReadOnlyVal = newCreditMemo.verifyActualTaxIsReadOnly();
        assertEquals("76.56", calculatedSalesTax);
        assertEquals("-76.56", actualSalesTax);
        assertEquals(actualSalesTaxReadOnlyVal, "true");
        newCreditMemo.closeTaxDetailsDialog();
    }

}
