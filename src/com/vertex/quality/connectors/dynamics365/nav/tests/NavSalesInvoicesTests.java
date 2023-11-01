package com.vertex.quality.connectors.dynamics365.nav.tests;

import com.vertex.quality.connectors.dynamics365.nav.pages.*;
import com.vertex.quality.connectors.dynamics365.nav.tests.base.NavBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class NavSalesInvoicesTests extends NavBaseTest
{
    /**
     * CDNAV-445
     * Create Invoice only
     * @author bhikshapathi
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void createInvoiceOnlyTest() {
        String expectedTaxAmount = "60.05";
        String expectedTaxRate = "6.00";
        String customerCode = "Test PA";
        String itemNumber = "1896-S";
        String quantity = "1";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToChildSubNavTab("Sales Invoices");
        NavSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, null, quantity, null, 1);

        openVertexTaxDetailsWindow(newInvoice);
        String actualTaxRate = newInvoice.getTaxRate();
        String actualTaxAmount = newInvoice.getTaxAmount();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(expectedTaxRate, actualTaxRate);

        newInvoice.closeTaxDetailsDialog();
        String postedOrderNum=newInvoice.getCurrentSalesNumber();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Responce
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
    }

    /**
     * CDNAV-515
     * Creates a new sales invoice for a nonVertex customer and verifies that the
     * Total Tax and the Statistics tax are the same
     * @author mariosaint-fleur
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void compareStatisticsTaxAmountForSalesInvoiceNonVertexTest(){
        String customerCode = "Cust_NoVertex";
        String quantity = "1";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToChildSubNavTab("Sales Invoices");
        NavSalesInvoicePage newSalesInvoices = salesInvoices.pageNavMenu.clickNew();
        fillInSalesInvoiceGeneralInfo(newSalesInvoices, customerCode);

        newSalesInvoices.expandTable();

        fillInItemsTableInfo("Item", "1896-S", null, null, quantity, null, 1);

        newSalesInvoices.exitExpandTable();

        //Navigate to Statistics and get Sales Quote TaxAmount
        navigateToStatWindow();
        String actualTaxAmount1 = newSalesInvoices.getNoVertexTaxAmount();
        newSalesInvoices.closeTaxDetailsDialog();
        String actualTotalTaxAmount = newSalesInvoices.getTotalTaxAmount();
        assertEquals(actualTotalTaxAmount,actualTaxAmount1);
    }

    /**
     * CDNAV-514
     * Tests Statistics Functionality for Sales Invoice TaxAmount
     * @author Shruti Jituri
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void checkSalesInvoiceTaxAmountInStatisticsTest() {
        String vertexCustomerCode="test1234";
        String quantity = "1";
        String itemNumber = "1896-S";

        //Navigate to Sales Invoice page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToChildSubNavTab("Sales Invoices");
        NavSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

        fillInSalesInvoiceGeneralInfo(newInvoice, vertexCustomerCode);
        newInvoice.expandTable();
        fillInItemsTableInfo("Item",itemNumber, null, null, quantity, null, 1);
        newInvoice.activateRow(2);

        //Validate tax amount for vertex customer in Statistics functionality
        String actualTotalTaxAmount = newInvoice.getTotalTaxAmount();
        navigateToStatWindow();
        String actualVertexTax= newInvoice.getVertexStatTaxAmount();
        newInvoice.closeTaxDetailsDialog();
        assertEquals(actualTotalTaxAmount, actualVertexTax);

    }

    /**
     * CDNAV-536
     * Validates if pop or alert appears on keeping Tax group code blank and user clicks Statistics Page
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_Deprecated"})
    public void missingTaxGroupCodeValidationErrorForStatisticsInvoiceTest() {
        String customerCode = "Test Phila";
        String itemNumber = "1900-S";
        String quantity="1";

        //Navigate to Sales Invoice page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToChildSubNavTab("Sales Invoices");
        NavSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();
        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);

        newInvoice.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newInvoice.exitExpandTable();

        String documentNum = newInvoice.getCurrentSalesNumber();
        navigateToStatWindow();

        String actualAlertMessage = newInvoice.getAlertMessageForTaxGroupCode();
        String lineNum = "10000";
        String expectedAlertMessage = newInvoice.createExpectedAlertMessageStrings("Invoice",documentNum,lineNum);
        assertEquals(actualAlertMessage,expectedAlertMessage,"Tax Group Code Statistics error validation Failed");

    }
    /**
     * CDNAV-541
     * Verifies that if tax group code is missing we can still recalculate tax
     * @author Shruti Jituri, dpatel
     */
    @Test(groups = {"D365_Deprecated"})
    public void missingTaxGroupCodeAndRecalculateTest() {
        String customerCode = "Test Phila";
        String itemNumber = "1896-S";
        String quantity="1";
        String expectedTaxAmount="113.70";

        //Navigate to Sales Invoice page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToChildSubNavTab("Sales Invoices");
        NavSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();
        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);

        newInvoice.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newInvoice.activateRow(2);
        fillInItemsTableInfo("G/L Account", "14100", null, null, quantity, "10", 2);
        newInvoice.activateRow(3);
        fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, null, quantity, "20", 3);
        newInvoice.activateRow(4);
        fillInItemsTableInfo("Resource", "MARY", null, null, quantity, null, 4);
        newInvoice.exitExpandTable();

        //Change Shipping Address
        newInvoice.openShippingAndBillingCategory();
        newInvoice.selectCustomShipToAddress();
        fillInCustomAddressInShippingAndBilling("CAL","5950 S Broadway","Los Angeles","CA","90030");

        clickRecalculateTax(newInvoice);

        String actualTaxAmount = newInvoice.getTotalTaxAmount();
        assertEquals(actualTaxAmount, expectedTaxAmount);
    }
    /**
     * CDNAV-455
     * Create invoice with default shipping on line item only
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void createInvoiceWithDefaultShipping() {
        String customerCode = "test1234";
        String itemNumber = "S-FREIGHT";
        String quantity="1";
        String expectedTaxRate="6.00";

        //Navigate to Sales Invoice page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToChildSubNavTab("Sales Invoices");
        NavSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();
        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);

        newInvoice.expandTable();
        fillInItemsTableInfo("Charge (Item)", itemNumber, null, null, quantity, "5", 1);

        //Validate tax amount & tax rate
        String actualTotalTaxAmount = newInvoice.getTotalTaxAmount();
        openVertexTaxDetailsWindow(newInvoice);
        String actualTaxRate = newInvoice.getTaxRate();
        assertEquals(expectedTaxRate, actualTaxRate);
        String actualTaxAmount = newInvoice.getTaxAmount();
        assertEquals(actualTotalTaxAmount, actualTaxAmount);
        newInvoice.closeTaxDetailsDialog();
    }
    /**
     * CDNAV-451
     * Create invoice with different billing and shipping address
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void createInvoiceWithDifferentBillingShippingTest(){
        String customerCode = "Test Phila";
        String itemNumber = "1896-S";
        String quantity="1";
        String expectedTaxRate="6.00";
        String expectedTotalTax="101.08";

        //Navigate to Sales Invoice page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToChildSubNavTab("Sales Invoices");
        NavSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();
        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);

        newInvoice.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null,quantity, null, 1);
        newInvoice.exitExpandTable();

        //check tax rate
        openVertexTaxDetailsWindow(newInvoice);
        String actualTaxRate = newInvoice.getTaxRate();
        assertEquals(expectedTaxRate, actualTaxRate);
        newInvoice.closeTaxDetailsDialog();

        //Change to alternate shipping
        newInvoice.openShippingAndBillingCategory();
        newInvoice.selectAlternateShipToAddress("WA");
        clickRecalculateTax(newInvoice);

        //check total tax in UI field
        String actualTotalTax=newInvoice.getTotalTaxAmount();
        assertEquals(expectedTotalTax, actualTotalTax);

        String invoiceNumber=newInvoice.getCurrentSalesNumber();

        //Navigate to admin page and check tax in XML
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(invoiceNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTotalTax+"</TotalTax>"), "Total Tax  Validation Failed");
    }
    /**
     * CDNAV-630
     * Create Invoice, post it and validate the tax on Statistics Page
     * Test to ensure the statistics is accessible for a posted invoice
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void createInvoiceAndPostTest(){
        String customerCode = "Test Phila";
        String itemNumber = "1896-S";
        String quantity="2";
        String expectedTaxAmount="120.10";

        //Navigate to Sales Invoice page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesInvoiceListPage salesInvoice = homePage.mainNavMenu.goToChildSubNavTab("Sales Invoices");
        NavSalesInvoicePage newInvoice = salesInvoice.pageNavMenu.clickNew();
        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);

        newInvoice.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, "MAIN",quantity, null, 1);
        newInvoice.exitExpandTable();

        //Validate tax amount for vertex customer in Statistics functionality
        String actualTotalTaxAmount = newInvoice.getTotalTaxAmount();
        navigateToStatWindow();
        String actualStatTax= newInvoice.getVertexStatTaxAmount();
        newInvoice.closeTaxDetailsDialog();
        assertEquals(actualTotalTaxAmount, expectedTaxAmount);
        assertEquals(actualTotalTaxAmount, actualStatTax);

        //Post Invoice and Validate tax amount in UI field and Stat Page
        NavSalesInvoicePage postedInvoice=postTheInvoice(newInvoice);
        String actualPostedInvoiceTotalTaxAmount=postedInvoice.getTotalTaxAmount();
        assertEquals(actualPostedInvoiceTotalTaxAmount, expectedTaxAmount);

        //Please ensure you have enabled 'Invoice Request enabled' checkbox on admin page to verify tax on Statistics page
        navigateToStatWindow();
        String actualStatTaxPostedInvoice= postedInvoice.getVertexStatTaxAmount();
        postedInvoice.closeTaxDetailsDialog();
        assertEquals(actualStatTaxPostedInvoice, actualPostedInvoiceTotalTaxAmount);
    }

    /**
     * CDNAV-650
     * Tests if Total tax UI field is refreshed on Creating Invoice and
     * Changing  to Custom address
     * Validate the tax on UI field
     *
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void verifyTotalTaxOnSelectingCustomAddressTest() {
        String customerCode = "Test Phila";
        String itemNumber = "1896-S";
        String quantity = "1";
        String expectedTax = "60.05";
        String expectedTaxAmount = "95.08";

        //Navigate to Sales Invoice page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesInvoiceListPage salesInvoice = homePage.mainNavMenu.goToChildSubNavTab("Sales Invoices");
        NavSalesInvoicePage newInvoice = salesInvoice.pageNavMenu.clickNew();
        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);

        newInvoice.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newInvoice.activateRow(2);
        fillInItemsTableInfo("G/L Account", "11200", null, null, quantity, "50", 2);

        String actualTotalTaxAmount = newInvoice.getTotalTaxAmount();
        assertEquals(actualTotalTaxAmount, expectedTax);

        //Change Shipping Address
        newInvoice.openShippingAndBillingCategory();
        newInvoice.selectCustomShipToAddress();
        fillInCustomAddressInShippingAndBilling("CAL", "5950 S Broadway", "Los Angeles", "CA", "90030");

        //Recalculate and verify tax
        clickRecalculateTax(newInvoice);
        String actualTaxAmount = newInvoice.getTotalTaxAmount();
        assertEquals(actualTaxAmount, expectedTaxAmount);
        navigateToStatWindow();
        String actualVertexTax= newInvoice.getVertexStatTaxAmount();
        newInvoice.closeTaxDetailsDialog();
        assertEquals(expectedTaxAmount, actualVertexTax);

        //Post Invoice, validate tax amount in UI field
        newInvoice.salesEditNavMenu.selectHomeTab();
        NavSalesInvoicePage postedInvoice = postTheInvoice(newInvoice);
        String actualPostedInvoiceTotalTaxAmount = postedInvoice.getTotalTaxAmount();
        assertEquals(actualPostedInvoiceTotalTaxAmount, expectedTaxAmount);
        navigateToStatWindow();
        String actualStatTaxPostedInvoice= postedInvoice.getVertexStatTaxAmount();
        postedInvoice.closeTaxDetailsDialog();
        assertEquals(expectedTaxAmount, actualStatTaxPostedInvoice);
    }

    /**
     * CDNAV-691
     * Verify exceptions notification in making call to service for Sales Invoice doc type
     *
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void invalidQuantityErrorMessageTest() {
        String expectedTax="11.57";
        String taxCalculationAddress = "https://oseries9-final.vertexconnectors.com/vertex-ws/services/LookupTaxAreas80";
        String addressValidation = "https://oseries9-final.vertexconnectors.com/vertex-ws/services/CalculateTax80";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavVertexAdminPage adminPage = homePage.navigatingToVertexAdminPage();
        NavSalesInvoicePage newSalesInvoices = new NavSalesInvoicePage(driver);

        //swap the URL of tax calculation and address validation on admin page
        adminPage.fillInURLAddress(taxCalculationAddress, addressValidation);
        adminPage.SavecloseAdminSection();
        //Navigate to invoice page and update quantity
        NavSalesInvoiceListPage salesInvoice = homePage.mainNavMenu.goToChildSubNavTab("Sales Invoices");
        String invoiceNo="S-INV1185";
        salesInvoice.filterDocuments(invoiceNo);
        newSalesInvoices.expandTable();
        //Revert changes and Verify tax
        String actualTotalTaxAmount = newSalesInvoices.getTotalTaxAmount();
        assertEquals(expectedTax, actualTotalTaxAmount);
        updateQuantity("2",1);
        newSalesInvoices.revertError();
        String actualTaxAmount = newSalesInvoices.getTotalTaxAmount();
        assertEquals(expectedTax, actualTaxAmount);

        //Update back the correct URL
        homePage.navigatingToVertexAdminPage();
        adminPage.fillInURLAddress(addressValidation, taxCalculationAddress);
        adminPage.SavecloseAdminSection();
    }

    /**
     * CDNAV-182
     * Verify no service call on adding only comments
     * and post invoice
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void verifyInvoiceCommentOnlyAndPostTest(){
        String customerCode = "Test Phila";
        String expectedPopupMessage="There is nothing to post.";

        //Navigate to Sales Invoice page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesInvoiceListPage salesInvoice = homePage.mainNavMenu.goToChildSubNavTab("Sales Invoices");
        NavSalesInvoicePage newInvoice = salesInvoice.pageNavMenu.clickNew();
        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
        fillInItemsTableInfo("Comment", "MD", null, null, null, null, 1);
        newInvoice.activateRow(2);
        String invoiceNumber = newInvoice.getCurrentSalesNumber();

        //Navigate to vertex admin page and verify if no XMl is generated
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(invoiceNumber);
        String noXmlStr = adminPage.checkNoXmlLog();
        assertTrue(noXmlStr.contains("(There is nothing to show in this view)"), "XML Validation Failed and XMl exists");
        adminPage.closeAdminSection();

        // Post invoice & Verify popup message
        newInvoice.salesEditNavMenu.selectPostButtonOnInvoicePage();
        newInvoice.dialogBoxClickYes();
        String actualPopupMessage=newInvoice.dialogBoxReadMessage();
        assertEquals(expectedPopupMessage,actualPopupMessage);
        newInvoice.dialogBoxClickOk();
    }
    }
