package com.vertex.quality.connectors.dynamics365.nav.tests;

import com.vertex.quality.connectors.dynamics365.nav.pages.*;
import com.vertex.quality.connectors.dynamics365.nav.tests.base.NavBaseTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class NavSalesReturnOrdersTests extends NavBaseTest
{
    /**
     * CDNAV-476
     * Return Sales order - full order without shipping
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void returnSalesFullOrderWithoutShippingTest() {
        String customerCode = "test1234";
        String expectedTaxAmount = "23.14";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesReturnOrderListPage sales_ReturnOrder = homePage.mainNavMenu.goToSubNavTab("Sales Return Orders");
        NavSalesReturnOrderPage newSalesReturnOrder = sales_ReturnOrder.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesReturnOrder, customerCode);
        String postedInvoiceNo="PSI-103152";
        newSalesReturnOrder.selectProcessGetPostedDocumentLinestoReverse();
        newSalesReturnOrder.filterDocuments(postedInvoiceNo);
        newSalesReturnOrder.dialogBoxClickOk();

        //Enter the Return Reason Code
        newSalesReturnOrder.expandTable();
        newSalesReturnOrder.enterReasonCode("WRONG",3);
        clickRecalculateTax(newSalesReturnOrder);
        //verify tax on UI
        String actualTotalTaxUI = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(expectedTaxAmount,actualTotalTaxUI);
        openVertexTaxDetailsWindow(newSalesReturnOrder);
        String actualTaxAmount = newSalesReturnOrder.getTaxAmount();
        assertEquals(expectedTaxAmount,actualTaxAmount);
        newSalesReturnOrder.closeTaxDetailsDialog();
        navigateToStatWindow();
        String actualStatTaxAmount = newSalesReturnOrder.getVertexStatTaxAmount();
        newSalesReturnOrder.closeTaxDetailsDialog();
        assertEquals(expectedTaxAmount,actualStatTaxAmount);

        //Verify XML
        String returnOrder=newSalesReturnOrder.getCurrentSalesNumber();
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(returnOrder);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
    }

    /**
     * CDNAV-517
     * Creates a new sales return order for a nonVertex customer and verifies that the
     * Total Tax and the Statistics tax are the same
     * @author mariosaint-fleur,dpatel
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void compareStatisticsTaxAmountForSalesReturnOrderNonVertexTest(){
        String customerCode = "Cust_NoVertex";
        String quantity = "1";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesReturnOrderListPage salesReturnOrders = homePage.mainNavMenu.goToSubNavTab("Sales Return Orders");
        NavSalesReturnOrderPage newSalesReturnOrders = salesReturnOrders.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesReturnOrders, customerCode);
        newSalesReturnOrders.expandTable();

        fillInItemsTableInfo("Item", "1896-S", null, null, quantity, null, 1);
        newSalesReturnOrders.activateRow(2);

        newSalesReturnOrders.exitExpandTable();

        //Navigate to Statistics and get Sales Quote TaxAmount
        openStatisticsWindowFromReturnOrder(newSalesReturnOrders);
        String actualTaxAmount1 = newSalesReturnOrders.getNoVertexTaxAmount();
        newSalesReturnOrders.closeTaxDetailsDialog();
        String actualTotalTaxAmount = newSalesReturnOrders.getTotalTaxAmount();
        assertEquals(actualTotalTaxAmount,actualTaxAmount1);
    }

    /**
     * CDNAV-516
     * Tests Statistics Functionality for Sales Return Order TaxAmount
     * @author Shruti Jituri
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void checkSalesReturnOrderTaxAmountInStatisticsTest() {
        String vertexCustomerCode="test1234";
        String quantity = "1";
        String itemNumber = "1896-S";

        //Navigate to Sales Return Order
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesReturnOrderListPage sales_Return_Order = homePage.mainNavMenu.goToSubNavTab("Sales Return Orders");
        NavSalesReturnOrderPage newSalesReturnOrder = sales_Return_Order.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesReturnOrder, vertexCustomerCode);
        newSalesReturnOrder.expandTable();

        //Add in line items
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesReturnOrder.activateRow(2);
        String actualTotalTaxAmount = newSalesReturnOrder.getTotalTaxAmount();

        //Navigate to statistics page and check if tax amount is same for vertex customer
        openStatisticsWindowFromReturnOrder(newSalesReturnOrder);
        String actualVertexTax= newSalesReturnOrder.getVertexStatTaxAmount();
        newSalesReturnOrder.closeTaxDetailsDialog();
        assertEquals(actualTotalTaxAmount, actualVertexTax);
    }
    /**
     * CDNAV-535
     * Validates if alert message appears on keeping Tax group code blank and user clicks Statistics Page
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_Deprecated"})
    public void missingTaxGroupCodeValidationErrorForStatisticsReturnOrderTest() {
        String customerCode = "test1234";
        String itemNumber = "1900-S";
        String quantity="1";

        //Navigate to Sales Return Order
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesReturnOrderListPage sales_ReturnOrder = homePage.mainNavMenu.goToSubNavTab("Sales Return Orders");
        NavSalesReturnOrderPage newSalesReturnOrder = sales_ReturnOrder.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.expandTable();

        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesReturnOrder.activateRow(2);
        fillInItemsTableInfo("G/L Account", "14100", null, null, quantity, "10", 2);
        newSalesReturnOrder.activateRow(3);
        fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, null, quantity, "20", 3);
        newSalesReturnOrder.activateRow(4);
        fillInItemsTableInfo("Resource", "MARY", null, null, quantity, null, 4);

        newSalesReturnOrder.exitExpandTable();
        String documentNum = newSalesReturnOrder.getCurrentSalesNumber();
        openStatisticsWindowFromReturnOrder(newSalesReturnOrder);

        String actualAlertMessage = newSalesReturnOrder.getAlertMessageForTaxGroupCode();
        String lineNum = "10000";
        String expectedAlertMessage = newSalesReturnOrder.createExpectedAlertMessageStrings("Return Order",documentNum,lineNum);
        assertEquals(actualAlertMessage,expectedAlertMessage,"Tax Group Code Statistics error validation Failed");

    }

    /**
     * CDNAV-543
     * Create Sales Return Order, check if we can recalculate tax even if tax group code is blank
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_Deprecated"})
    public void missingTaxGroupCodeValidationRecalculateTest() {
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity="1";

        //Navigate to Sales Return Order
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesReturnOrderListPage sales_ReturnOrder = homePage.mainNavMenu.goToSubNavTab("Sales Return Orders");
        NavSalesReturnOrderPage newSalesReturnOrder = sales_ReturnOrder.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.expandTable();

        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesReturnOrder.activateRow(2);
        fillInItemsTableInfo("G/L Account", "14100", null, null, quantity, "10", 2);
        newSalesReturnOrder.activateRow(3);
        fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, null, quantity, "20", 3);
        newSalesReturnOrder.activateRow(4);
        fillInItemsTableInfo("Resource", "MARY", null, null, quantity, null, 4);


        String actualTaxAmountBefore = newSalesReturnOrder.getTotalTaxAmount();
        clickRecalculateTax(newSalesReturnOrder);

        //Tax amount before and after recalculate
        String actualTaxAmount = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(actualTaxAmountBefore, actualTaxAmount);
    }

    /**
     * CDNAV-648
     * Create Sales Return Order, copy the posted invoice and verify the total tax UI field
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void validateTaxOnCopyPostedInvoiceToReturnOrderTest() {
        String customerCode = "test1234";
        String expectedTax = "60.05";

        //Navigate to Sales Return Order
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesReturnOrderListPage sales_ReturnOrder = homePage.mainNavMenu.goToSubNavTab("Sales Return Orders");
        NavSalesReturnOrderPage newSalesReturnOrder = sales_ReturnOrder.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.expandTable();

        //Copy Document
        String documentNumber = "PSI-103007";
        copySalesDocumentToReturnOrder(newSalesReturnOrder, "Posted Invoice", documentNumber);
        newSalesReturnOrder.dialogBoxClickOk();

        //Validate Tax
        String actualTaxAmountReturnOrder = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(expectedTax, actualTaxAmountReturnOrder);
        openStatisticsWindowFromReturnOrder(newSalesReturnOrder);
        String actualVertexTax= newSalesReturnOrder.getVertexStatTaxAmount();
        newSalesReturnOrder.closeTaxDetailsDialog();
        assertEquals(actualTaxAmountReturnOrder, actualVertexTax);
    }

    /**
     * CDNAV-234
     * Create Sales Return Order, recalculate the tax after modifying the price
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void recalculateTaxOnReturnOrderTest() {
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity="1";
        String expectedTax="100.01";

        //Navigate to Sales Return Order
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesReturnOrderListPage sales_ReturnOrder = homePage.mainNavMenu.goToSubNavTab("Sales Return Orders");
        NavSalesReturnOrderPage newSalesReturnOrder = sales_ReturnOrder.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.expandTable();

        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesReturnOrder.activateRow(2);
        fillInItemsTableInfo("G/L Account", "13300", null, null, quantity, "100", 2);
        newSalesReturnOrder.activateRow(3);
        fillInItemsTableInfo("Resource", "MARY", null, null, quantity, null, 3);
        newSalesReturnOrder.activateRow(4);
        fillInItemsTableInfo("Fixed Asset", "FA000040", null, null, quantity, "200", 4);
        newSalesReturnOrder.activateRow(5);
        fillInItemsTableInfo("Charge (Item)", "S-ALLOWANCE", null, null, quantity, "500", 5);

        //Validate Tax
        String actualTaxAmount = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(expectedTax, actualTaxAmount);
        openStatisticsWindowFromReturnOrder(newSalesReturnOrder);
        String actualVertexStatTax= newSalesReturnOrder.getVertexStatTaxAmount();
        newSalesReturnOrder.closeTaxDetailsDialog();
        assertEquals(actualVertexStatTax, expectedTax);

        //Update Price
        changedLineAmount("500", 3);
        clickRecalculateTax(newSalesReturnOrder);
        //Verify Tax
        String actualTaxAmountReturnOrder = newSalesReturnOrder.getTotalTaxAmount();
        openStatisticsWindowFromReturnOrder(newSalesReturnOrder);
        String actualVertexStatUpdatedTax= newSalesReturnOrder.getVertexStatTaxAmount();
        assertEquals(actualTaxAmountReturnOrder, actualVertexStatUpdatedTax);
        newSalesReturnOrder.closeTaxDetailsDialog();

        //Verify tax in XML
        String  returnOrderNumber = newSalesReturnOrder.getCurrentSalesNumber();
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(returnOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTax+"</TotalTax>"), "Total Tax  Validation Failed");
    }
    /**
     * CDNAV-665
     * Create Sales Return Order, modify quantity and verify tax amount in Vertex tax details
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void recalculateTaxOnChangeInQuantityTest() {
        String customerCode = "test1234";
        String itemNumber = "1110";
        String quantity = "1";
        String expectedTaxBeforeRecalculate="94.98";
        String expectedTotalTaxUI="34.98";
        List<String> expectedTaxAmount = Arrays.asList("30.00", "0.00", "4.98", "0.00", "60.00");
        List<String> updatedExpectedTaxAmount=Arrays.asList("30.00", "0.00", "4.98", "0.00","0.00");

        //Navigate to Sales Return Order
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesReturnOrderListPage sales_ReturnOrder = homePage.mainNavMenu.goToSubNavTab("Sales Return Orders");
        NavSalesReturnOrderPage newSalesReturnOrder = sales_ReturnOrder.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.expandTable();

        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, "500", 1);
        newSalesReturnOrder.activateRow(2);
        fillInItemsTableInfo("G/L Account", "12200", null, null, quantity, "1000", 2);
        newSalesReturnOrder.activateRow(3);
        fillInItemsTableInfo("Resource", "MARK", null, null, quantity, "83", 3);
        newSalesReturnOrder.activateRow(4);
        fillInItemsTableInfo("Fixed Asset", "FA000040", null, null, quantity, "600", 4);
        newSalesReturnOrder.activateRow(5);
        fillInItemsTableInfo("Charge (Item)", "P-ALLOWANCE", null, null, quantity, "1000", 5);

        //Verify tax
        String totalTaxUI = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(expectedTaxBeforeRecalculate,totalTaxUI);
        openVertexTaxDetailsWindow(newSalesReturnOrder);
        List<String> actualTaxAmount = newSalesReturnOrder.getMultipleTaxAmount();
        assertEquals(expectedTaxAmount, actualTaxAmount);
        newSalesReturnOrder.closeTaxDetailsDialog();

        //Update Quantity
        updateQuantity("0",5);

        //Recalculate after updating quantity and verify tax
        clickRecalculateTax(newSalesReturnOrder);
        String actualTotalTaxUI = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(expectedTotalTaxUI,actualTotalTaxUI);
        navigateToStatWindow();
        String actualStatTaxAmount = newSalesReturnOrder.getVertexStatTaxAmount();
        newSalesReturnOrder.closeTaxDetailsDialog();
        assertEquals(expectedTotalTaxUI,actualStatTaxAmount);
        openVertexTaxDetailsWindow(newSalesReturnOrder);
        List<String> actualTaxAmountAfterUpdate = newSalesReturnOrder.getMultipleTaxAmount();
        assertEquals(updatedExpectedTaxAmount, actualTaxAmountAfterUpdate);
        newSalesReturnOrder.closeTaxDetailsDialog();
    }
    /**
     * CDNAV-669
     * Create a Sales Return order and access the Statistics Page
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void validateStatisticsTest() {
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity="1";
        String expectedTotalTaxUI="65.03";

        //Navigate to Sales Return Order
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesReturnOrderListPage sales_ReturnOrder = homePage.mainNavMenu.goToSubNavTab("Sales Return Orders");
        NavSalesReturnOrderPage newSalesReturnOrder = sales_ReturnOrder.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesReturnOrder, customerCode);
        newSalesReturnOrder.expandTable();

        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesReturnOrder.activateRow(2);
        fillInItemsTableInfo("G/L Account", "14100", null, null, quantity, "10", 2);
        newSalesReturnOrder.activateRow(3);
        fillInItemsTableInfo("Resource", "MARK", null, null, quantity, null, 3);
        newSalesReturnOrder.activateRow(4);
        fillInItemsTableInfo("Fixed Asset", "FA000010", null, null, quantity, null, 4);

        newSalesReturnOrder.exitExpandTable();
        String actualTotalTaxUI = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(expectedTotalTaxUI,actualTotalTaxUI);
        openStatisticsWindowFromReturnOrder(newSalesReturnOrder);
        String actualVertexStatUpdatedTax= newSalesReturnOrder.getVertexStatTaxAmount();
        assertEquals(expectedTotalTaxUI,actualVertexStatUpdatedTax);
        newSalesReturnOrder.closeTaxDetailsDialog();

        //Post the return order
        NavSalesCreditMemoPage postedCreditMemo = postReturnOrder(newSalesReturnOrder);
        //Validate tax on UI field and Stat page
        String actualTaxAmountPostedCredit = postedCreditMemo.getTotalTaxAmount();
        assertEquals(actualTaxAmountPostedCredit, expectedTotalTaxUI);
        navigateToStatWindow();
        String actualVertexStatTaxPostedCredit = newSalesReturnOrder.getVertexStatTaxAmount();
        newSalesReturnOrder.closeTaxDetailsDialog();
        assertEquals(actualTaxAmountPostedCredit, actualVertexStatTaxPostedCredit);

    }

    /**
     * CDNAV-474
     * Return Sales order - partial quantity
     * @author Shruti Jituri
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void returnSalesOrderPartialQuantityTest() {
        String customerCode = "test1234";
        String expectedTotalTaxUI = "60.05";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesReturnOrderListPage sales_ReturnOrder = homePage.mainNavMenu.goToSubNavTab("Sales Return Orders");
        NavSalesReturnOrderPage newSalesReturnOrder = sales_ReturnOrder.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesReturnOrder, customerCode);
        String postedInvoiceNo="PSI-103146";
        newSalesReturnOrder.selectProcessGetPostedDocumentLinestoReverse();
        newSalesReturnOrder.filterDocuments(postedInvoiceNo);
        newSalesReturnOrder.dialogBoxClickOk();
        //Update Return reason code and quantity
        newSalesReturnOrder.enterReasonCode("DEFECTIVE",3);
        updateQuantity("1",3);

        //verify tax on UI, vertex tax details
        String actualTotalTaxUI = newSalesReturnOrder.getTotalTaxAmount();
        assertEquals(expectedTotalTaxUI,actualTotalTaxUI);
        openVertexTaxDetailsWindow(newSalesReturnOrder);
        String actualTaxAmount = newSalesReturnOrder.getTaxAmount();
        assertEquals(expectedTotalTaxUI,actualTaxAmount);
        newSalesReturnOrder.closeTaxDetailsDialog();
        navigateToStatWindow();
        String actualStatTaxAmount = newSalesReturnOrder.getVertexStatTaxAmount();
        newSalesReturnOrder.closeTaxDetailsDialog();
        assertEquals(expectedTotalTaxUI,actualStatTaxAmount);

        //Verify XML
        String returnOrder=newSalesReturnOrder.getCurrentSalesNumber();
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(returnOrder);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTotalTaxUI+"</TotalTax>"), "Total Tax  Validation Failed");
    }
    /**
     * CDNAV-473
     * Return Sales order - partial amount
     * @author Shruti Jituri
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void returnSalesOrderPartialAmountTest() {
        String customerCode = "test1234";
        String expectedTotalTaxUI = "72.00";
        String itemNumber="1896-S";
        String quantity="2";

        //Navigate to Sales Invoice page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToChildSubNavTab("Sales Invoices");
        NavSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();
        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
        newInvoice.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        NavSalesInvoicePage postedInvoice=postTheInvoice(newInvoice);
        String postedInvoiceNumber = postedInvoice.getCurrentSalesNumber();
        postedInvoice.clickBackAndSaveArrow();

        NavAdminHomePage homePageForReturn = initializeTestPageAndSignOn();
        NavSalesReturnOrderListPage sales_ReturnOrder = homePageForReturn.mainNavMenu.goToSubNavTab("Sales Return Orders");
        NavSalesReturnOrderPage newSalesReturnOrder = sales_ReturnOrder.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesReturnOrder, customerCode);

        newSalesReturnOrder.selectProcessGetPostedDocumentLinestoReverse();
        newSalesReturnOrder.filterDocuments(postedInvoiceNumber);
        newSalesReturnOrder.dialogBoxClickOk();

        //Update Return reason code and unit price
        newSalesReturnOrder.expandTable();
        newSalesReturnOrder.enterReasonCode("NONEED",3);
        changedLineAmount("600", 3);

        //Post the return order
        NavSalesCreditMemoPage postedCreditMemo = postReturnOrder(newSalesReturnOrder);
        //Validate tax on UI field, vertex tax details and Stat page
        String actualTaxAmountPostedCredit = postedCreditMemo.getTotalTaxAmount();
        assertEquals(actualTaxAmountPostedCredit, expectedTotalTaxUI);
        openVertexTaxDetailsWindow(postedCreditMemo);
        String actualTaxAmount = postedCreditMemo.getTaxAmount();
        assertEquals(expectedTotalTaxUI,actualTaxAmount);
        postedCreditMemo.closeTaxDetailsDialog();
        navigateToStatWindow();
        String actualVertexStatTaxPostedCredit = postedCreditMemo.getVertexStatTaxAmount();
        postedCreditMemo.closeTaxDetailsDialog();
        assertEquals(actualTaxAmountPostedCredit, actualVertexStatTaxPostedCredit);

        //Verify XML
        String postedNo=postedCreditMemo.getCurrentSalesNumber();
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(postedNo);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>-72.0</TotalTax>"), "Total Tax Validation Failed");
    }
}

