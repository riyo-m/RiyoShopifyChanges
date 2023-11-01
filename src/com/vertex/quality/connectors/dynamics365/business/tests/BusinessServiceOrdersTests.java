package com.vertex.quality.connectors.dynamics365.business.tests;

import com.vertex.quality.connectors.dynamics365.business.enums.BusinessMainMenuNavTabs;
import com.vertex.quality.connectors.dynamics365.business.pages.*;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessPurchaseInvoicePage;
import com.vertex.quality.connectors.dynamics365.business.tests.base.BusinessBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
@Listeners(TestRerunListener.class)
public class BusinessServiceOrdersTests extends BusinessBaseTest {
    BusinessAdminHomePage homePage;
    BusinessServiceHomePage servicePage;
    /**
     * CDBC-1171
     * Create Service Order and verify taxes
     * @author Shruti
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central", "D365_Business_Central_Service_Regression","D365_Business_Central_Smoke"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxForServiceOrderTest(){
        String customerNo="TestingService_PA";
        String serviceItemNo="1009";
        String itemNumber = "1896-S";
        String quantity = "1";
        String expectedTax="69.33";
        String expectedAmountTax="0.59";
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();

        //enable service management toggle
        adminPage.updateServiceManagementOn();
        adminPage.clickBackAndSaveArrow();

        //select Service Order in service management menu
        BusinessServiceOrderListPage serviceOrders=servicePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SERVICE_MANAGEMENT.value,"Service Orders" );
        BusinessServiceOrdersPage serviceOrder = serviceOrders.pageNavMenu.clickNew();
        fillInServiceOrdersGeneralInfo(serviceOrder,customerNo);
        serviceOrder.expandTable();
        serviceOrder.fillInServiceTableInfo("1008",1);
        serviceOrder.fillInServiceTableInfo(serviceItemNo,2);
        serviceOrder.exitExpandTable();
        String orderNumber = serviceOrder.getCurrentSalesNumber();
        BusinessServiceItemPage serviceItemSheet=serviceOrder.navigateToServiceItemWorksheet();
        serviceItemSheet.expandServiceItemWorksheet();
        serviceOrder.fillInItemsTableInfoForService("Resource","KATHERINE", quantity,null,1);
        serviceOrder.activateRowForServiceOrder(2, 2);
        serviceOrder.fillInItemsTableInfoForService("Item",itemNumber, quantity,null,2);
        serviceOrder.activateRowForServiceOrder(3, 2);
        serviceOrder.fillInItemsTableInfoForService("G/L Account","10300", quantity,null,3);
        serviceOrder.activateRowForServiceOrder(4, 1);
        serviceOrder.fillInItemsTableInfoForService("Cost","MILEAGE", quantity,null,4);

        //Verify Total tax on Service Item Worksheet
        String totalTaxOnServiceItemSheet=serviceItemSheet.getTotalTaxAmount();
        assertEquals(totalTaxOnServiceItemSheet,expectedTax);
        serviceItemSheet.activateRow(1);
        String amountIncludingTax=serviceItemSheet.amountTax();
        assertEquals(amountIncludingTax,expectedAmountTax);

        //open vertex tax details
        serviceMgmtVertexTaxDetails(serviceItemSheet);
        List<String> taxAmountValues = new ArrayList<String>();
        taxAmountValues.add("9.24");
        taxAmountValues.add("60.05");
        taxAmountValues.add("0.00");
        taxAmountValues.add("0.04");
        List<String> taxAmounts = serviceOrder.getTaxAmountForEachServiceLine("9.24", "60.05", "0.00", "0.04");
        assertEquals(taxAmounts, taxAmountValues);
        serviceItemSheet.serviceMgmtItemSheetCloseButton();
        serviceItemSheet.closeItemSheet();

        //Click item 1008 and go to service item worksheet
        serviceItemSheet = serviceOrder.navigateToServiceItemWorksheet();
        serviceItemSheet.fillInItemsTableInfoForService("Resource","KATHERINE", quantity,null,1);
        serviceOrder.activateRowForServiceOrder(2, 2);
        serviceItemSheet.fillInItemsTableInfoForService("Item","1900-S", quantity,null,2);
        serviceOrder.activateRowForServiceOrder(3, 2);
        serviceItemSheet.fillInItemsTableInfoForService("G/L Account","10600", "2.5",null,3);
        serviceOrder.activateRowForServiceOrder(4, 1);
        serviceItemSheet.fillInItemsTableInfoForService("Cost","MILEAGE", quantity,null,4);

        //Verify Total tax on Service Item Worksheet
        totalTaxOnServiceItemSheet=serviceItemSheet.getTotalTaxAmount();
        assertEquals(totalTaxOnServiceItemSheet,"20.85");

        //open vertex tax details
        serviceMgmtVertexTaxDetails(serviceItemSheet);
        List<String> taxAmountValues2 = new ArrayList<String>();
        taxAmountValues2.add("9.24");
        taxAmountValues2.add("11.57");
        taxAmountValues2.add("0.00");
        taxAmountValues2.add("0.04");
        List<String> taxAmounts2 = serviceOrder.getTaxAmountForEachServiceLine("9.24", "11.57", "0.00", "0.04");
        assertEquals(taxAmounts2, taxAmountValues2);
        serviceItemSheet.serviceMgmtItemSheetCloseButton();
        serviceItemSheet.closeItemSheet();

        //Navigate to Order then Service Lines and select filter as All and verify Sales Tax
        serviceOrder.clickOrderAndServiceLines();
        serviceOrder.selectAndFilterLinesFilter("Per Selected Service Item Line", "All");
        totalTaxOnServiceItemSheet=serviceItemSheet.getTotalTaxAmount();
        assertEquals(totalTaxOnServiceItemSheet,"90.17");

        //Verify Vertex Tax Details
        serviceMgmtVertexTaxDetails(serviceItemSheet);
        taxAmounts2 = serviceOrder.getTaxAmountForEachServiceLine("9.24", "11.57", "0.00", "0.04");
        assertEquals(taxAmounts2, taxAmountValues2);
        serviceItemSheet.clickBackArrow();

        //Then select Per Selected Service Item Line and verify tax
        serviceOrder.selectAndFilterLinesFilter("All", "Per Selected Service Item Line");
        totalTaxOnServiceItemSheet=serviceItemSheet.getTotalTaxAmount();
        assertEquals(totalTaxOnServiceItemSheet,"20.85");
        serviceItemSheet.closeItemSheet();

        //Select 2nd item and navigate back to Service Lines, select Per Selected Service Item Line and verify tax
        serviceOrder.selectLineWithServiceItemNumber("1009");
        serviceOrder.clickOrderAndServiceLines();
        serviceOrder.selectAndFilterLinesFilter("Per Selected Service Item Line", "Per Selected Service Item Line");
        totalTaxOnServiceItemSheet=serviceItemSheet.getTotalTaxAmount();
        assertEquals(totalTaxOnServiceItemSheet,"69.32");
        serviceItemSheet.closeItemSheet();

        // Navigate to XML and validate total tax
        adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(orderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>9.24</TotalTax>"), "Total tax validation failed");

    }

    /**
     * @TestCase CDBC-1305
     * @Description - Create a Service Order, Recalculate Tax and Verify The Request/Response
     * @Author Mario Saint-Fleur
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_ServiceMgmt", "D365_Business_Central_Service_Regression","D365_Business_Central_Smoke"}, retryAnalyzer = TestRerun.class)
    public void recalculateTaxForServiceOrderAndVerifyResponseAndRequestTest(){
        String customerNo="TestingService_PA";
        String serviceItemNo="1009";
        String itemNumber = "1896-S";
        String quantity = "1";
        String expectedTax="60.05";
        int expectedCount = 6;
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();

        //enable service management toggle
        adminPage.updateServiceManagementOn();
        adminPage.clickBackAndSaveArrow();

        //select Service Quote in service management menu
        BusinessServiceOrderListPage serviceOrders=servicePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SERVICE_MANAGEMENT.value,"Service Orders" );
        BusinessServiceOrdersPage serviceOrder = serviceOrders.pageNavMenu.clickNew();
        fillInServiceOrdersGeneralInfo(serviceOrder,customerNo);
        serviceOrder.expandTable();
        serviceOrder.fillInServiceTableInfo(serviceItemNo,1);
        serviceOrder.exitExpandTable();
        String orderNumber = serviceOrder.getCurrentSalesNumber();
        BusinessServiceItemPage serviceItemSheet=serviceOrder.navigateToServiceItemWorksheet();
        serviceItemSheet.expandServiceItemWorksheet();
        serviceItemSheet.fillInItemsTableInfoForService("Item",itemNumber, quantity,null,1);

        //Recalculate Tax
        recalculateTax(serviceOrder);

        //Verify Total tax on Service Item Worksheet
        String totalTaxOnServiceItemSheet=serviceItemSheet.getTotalTaxAmount();
        assertEquals(totalTaxOnServiceItemSheet,expectedTax);

        //Navigate to XML page and validate Response/Request amount
        adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(orderNumber);
        int count = adminPage.getResponseCount(orderNumber);
        assertEquals(count,expectedCount,"Row validation failed");
    }

    /**
     * @TestCase CDBC-1338
     * @Description - Create a Service Order, Verify Service Line & Header have same tax
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_ServiceMgmt", "D365_Business_Central_Service_Regression","D365_Business_Central_Smoke"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxInServiceItemLinesAndHeaderTest() {
        String customerNo="TestingService_PA";
        String serviceItemNo = "1009";
        String itemNumber ="1896-S";
        String quantity = "1";
        String price = "10000";
        String expectedTax="600.00";
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();

        //enable service management toggle
        adminPage.updateServiceManagementOn();
        adminPage.clickBackAndSaveArrow();

        //select Service Order in service management menu
        BusinessServiceOrderListPage serviceOrders=servicePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SERVICE_MANAGEMENT.value,"Service Orders" );
        BusinessServiceOrdersPage serviceOrder = serviceOrders.pageNavMenu.clickNew();

        //create Service Order
        fillInServiceOrdersGeneralInfo(serviceOrder,customerNo);
        serviceOrder.expandTable();
        serviceOrder.fillInServiceTableInfo(serviceItemNo,1);
        serviceOrder.exitExpandTable();

        // Navigate to Service Lines
        BusinessServiceItemPage serviceItem = serviceOrder.navigateToServiceItemLines();
        serviceItem.expandServiceLines();
        serviceItem.fillInTableInfoForServiceLine(serviceItemNo,"Item",itemNumber, quantity,price,1);
        String totalTaxOnServiceLines=serviceOrder.getTotalTaxAmount();
        assertEquals(totalTaxOnServiceLines, expectedTax);

        //Verify Total tax on Tax Details
        serviceMgmtVertexTaxDetails(serviceItem);
        List<String> taxAmountValues = new ArrayList<String>();
        taxAmountValues.add("600.00");
        List<String> taxAmounts = serviceOrder.getTaxAmountForEachServiceLine("600.00");
        assertEquals(taxAmounts, taxAmountValues);
        serviceItem.clickBackArrow();
        serviceItem.closeItemSheet();

        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.ORDER.value, "Statistics");
        String actualTax=serviceOrder.getAPStatTaxAmount();
        assertEquals(actualTax, expectedTax);
    }

    /**
     * @TestCase CDBC-1337
     * @Description - Create a Service Order, Verify Service Line & Header have same tax, Post Service Order and Verify Tax in Posted Invoice
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_ServiceMgmt", "D365_Business_Central_Service_Regression","D365_Business_Central_Smoke"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxInServiceItemAndPostedInvoiceTest() {
        String customerNo="TestingService_PA";
        String serviceItemNo = "1009";
        String itemNumber ="1896-S";
        String quantity = "1";
        String price = "10000";
        String expectedTax="600.00";
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();

        //enable service management toggle
        adminPage.updateServiceManagementOn();
        adminPage.clickBackAndSaveArrow();

        //select Service Order in service management menu
        BusinessServiceOrderListPage serviceOrders=servicePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SERVICE_MANAGEMENT.value,"Service Orders" );
        BusinessServiceOrdersPage serviceOrder = serviceOrders.pageNavMenu.clickNew();

        //create Service Order
        fillInServiceOrdersGeneralInfo(serviceOrder,customerNo);
        serviceOrder.expandTable();
        serviceOrder.fillInServiceTableInfo(serviceItemNo,1);
        serviceOrder.exitExpandTable();

        // Navigate to Service Lines
        BusinessServiceItemPage serviceItem = serviceOrder.navigateToServiceItemLines();
        serviceItem.expandServiceLines();
        serviceItem.fillInTableInfoForServiceLine(serviceItemNo,"Item",itemNumber, quantity,price,1);
        String totalTaxOnServiceLines=serviceOrder.getTotalTaxAmount();
        assertEquals(totalTaxOnServiceLines, expectedTax);

        //Verify Total tax on Tax Details
        serviceMgmtVertexTaxDetails(serviceItem);
        List<String> taxAmountValues = new ArrayList<String>();
        taxAmountValues.add("600.00");
        List<String> taxAmounts = serviceOrder.getTaxAmountForEachServiceLine("600.00");
        assertEquals(taxAmounts, taxAmountValues);
        serviceItem.clickBackArrow();
        serviceItem.closeItemSheet();

        homePage.mainNavMenu.goToSubNavTab1(
                BusinessMainMenuNavTabs.ORDER.value, "Statistics");
        String actualTax=serviceOrder.getAPStatTaxAmount();
        assertEquals(actualTax, expectedTax);
        serviceOrder.closeTaxDetailsDialog();

        BusinessSalesCreditMemoPage postedInvoice = postSalesReturnOrder();

        //Navigate to Statistics and get Sales Credit Memo TaxAmount
        homePage.mainNavMenu.goToChildSubNavTab1(
                BusinessMainMenuNavTabs.POSTED_SERVICE_INVOICE.value, BusinessMainMenuNavTabs.INVOICE.value,"Statistics");
        String postedTaxAmount = postedInvoice.getStatisticsTaxAmount();
        assertEquals(postedTaxAmount, expectedTax);
        postedInvoice.closeTaxDetailsDialog();

    }

    /**
     * @TestCase CDBC-1402
     * @Description - Verifying Shipment Method Code For US To Canada For Service Order
     * @Author Mario Saint-Fleur
     * */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_ServiceMgmt", "D365_Business_Central_Service_Regression","D365_Business_Central_Smoke"}, retryAnalyzer = TestRerun.class)
    public void verifyShipmentMethodCodeForUSToCanadaForServiceOrder() {
        String customerNo = "TestingService_PA";
        String serviceItemNo = "1009";
        String itemNumber = "1896-S";
        String quantity = "1";
        String unitPrice = "10000";
        String expectedTax = "600.00";
        String expectedTaxAfterUpdate = "1,300.00";
        String shipmentMethodCode = "SUP";

        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();

        //Enable service management toggle
        adminPage.updateServiceManagementOn();
        adminPage.clickBackAndSaveArrow();

        //Select Service Order in service management menu
        BusinessServiceOrderListPage serviceOrders = servicePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SERVICE_MANAGEMENT.value,"Service Orders" );
        BusinessServiceOrdersPage serviceOrder = serviceOrders.pageNavMenu.clickNew();

        //Create Service Order
        fillInServiceOrdersGeneralInfo(serviceOrder,customerNo);
        serviceOrder.expandTable();
        serviceOrder.fillInServiceTableInfo(serviceItemNo,1);
        serviceOrder.exitExpandTable();
        String orderNumber = serviceOrder.getCurrentSalesNumber();

        //Navigate to Service Lines
        BusinessServiceItemPage serviceItem = serviceOrder.navigateToServiceItemLines();
        serviceItem.expandServiceLines();
        serviceItem.fillInTableInfoForServiceLine(serviceItemNo,"Item", itemNumber, quantity, unitPrice,1);
        String totalTaxOnServiceLines = serviceOrder.getTotalTaxAmount();
        assertEquals(totalTaxOnServiceLines, expectedTax);
        serviceItem.closeItemSheet();

        //Change Shipment address to Canada and Shipment Code to SUP
        serviceOrder.openCategory("Shipping");
        fillInCustomAddressInShippingAndBilling("3631 James Street","Smooth Rock Falls","Ontario","P0L 2B0", "CA");
        serviceOrder.selectShipmentMethodCode(shipmentMethodCode);

        //Navigate to Service Item Worksheet, recalculate taxes, verify taxes, open Vertex tax details, and verify imposition
        serviceItem = serviceOrder.navigateToServiceItemWorksheet();
        serviceItem.expandServiceLines();
        serviceMgmtRecalculateTax(serviceItem);
        String updatedTotalTaxOnServiceLines = serviceOrder.getTotalTaxAmount();
        assertEquals(expectedTaxAfterUpdate, updatedTotalTaxOnServiceLines);
        serviceMgmtVertexTaxDetails(serviceItem);
        String actualImpositionValue = serviceOrder.getImpositionValue();
        String expectedImpositionValue = "GST/HST";
        assertEquals(expectedImpositionValue, actualImpositionValue);
        serviceItem.serviceMgmtItemSheetCloseButton();
        serviceItem.closeItemSheet();

        //Navigate to XML and validate presence of delivery term
        adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(orderNumber);
        adminPage.filterxml("Tax Calc Request");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();assertTrue(xmlStr.contains("deliveryTerm=\"SUP\""), "Delivery Term Validation Failed, Expected: " + shipmentMethodCode + ". Found Incorrect Shipment Method Code");

    }

    /**
     * @TestCase CDBC-1404
     * @Description - Verifying Quantity To Ship and Invoice For Service Order Item
     * @Author Mario Saint-Fleur
     * */
    @Test(groups = {"D365_Business_Central_ServiceMgmt", "D365_Business_Central_Service_Regression","D365_Business_Central_Smoke"})
    public void verifyQuantityToShipCalculatesCorrectTaxesForServiceOrder() {
        String customerNo = "TestingService_PA";
        String serviceItemNo = "1009";
        String itemNumber = "1896-S";
        String quantity = "1";
        String unitPrice = "10000";
        String expectedTax = "600.00";
        String expectedQtyToShip = "1";
        String expectedQtyToInvoice = "1";

        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();

        //Enable service management toggle
        adminPage.updateServiceManagementOn();
        adminPage.clickBackAndSaveArrow();

        //Select Service Order in service management menu
        BusinessServiceOrderListPage serviceOrders = servicePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SERVICE_MANAGEMENT.value,"Service Orders" );
        BusinessServiceOrdersPage serviceOrder = serviceOrders.pageNavMenu.clickNew();

        //Create Service Order
        fillInServiceOrdersGeneralInfo(serviceOrder,customerNo);
        serviceOrder.expandTable();
        serviceOrder.fillInServiceTableInfo(serviceItemNo,1);
        serviceOrder.exitExpandTable();
        String orderNumber = serviceOrder.getCurrentSalesNumber();

        //Navigate to Service Lines
        BusinessServiceItemPage serviceItem = serviceOrder.navigateToServiceItemLines();
        serviceItem.expandServiceLines();
        serviceItem.fillInTableInfoForServiceLine(serviceItemNo,"Item", itemNumber, quantity, unitPrice,1);
        String actualQtyToShip = serviceItem.getQtyToShip();
        assertEquals(actualQtyToShip, expectedQtyToShip);
        String actualQtyToInvoice = serviceItem.getQtyToInvoice();
        assertEquals(actualQtyToInvoice, expectedQtyToInvoice);
        String totalTaxOnServiceLines = serviceOrder.getTotalTaxAmount();
        assertEquals(totalTaxOnServiceLines, expectedTax);
        serviceItem.closeItemSheet();

        //Navigate to XML and validate presence of delivery term
        adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(orderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <TotalTax>600.0</TotalTax>"), "Total tax validation failed, Expected: " + expectedTax + ". Found Incorrect Total Tax Amount");
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpServiceMgmt(){
        String role="Service Manager";
        homePage = new BusinessAdminHomePage(driver);
        servicePage=new BusinessServiceHomePage(driver);
        String verifyPage=homePage.verifyHomepageHeader();
        if(!verifyPage.contains(role)){

            //navigate to select role as Service Manager role
            homePage.selectSettings();
            homePage.navigateToManagerInSettings(role);
        }
    }
}
