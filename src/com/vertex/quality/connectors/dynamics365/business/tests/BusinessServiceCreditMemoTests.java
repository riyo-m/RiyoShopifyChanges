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

/**
 * contains test cases for all the service credit memo scenarios
 *
 * @author Vivek Olumbe
 */
@Listeners(TestRerunListener.class)
public class BusinessServiceCreditMemoTests extends BusinessBaseTest {
    BusinessAdminHomePage homePage;
    BusinessServiceHomePage servicePage;

    /**
     * @TestCase CDBC-1377
     * @Description - Create service order with alternative address, post as invoice, and create service credit memo
     *                with invoice lines and verify address and tax
     * @Author Vivek Olumbe
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_ServiceMgmt", "D365_Business_Central_Service_Regression","D365_Business_Central_Smoke"}, retryAnalyzer = TestRerun.class)
    public void verifyAlternateAddressFromServiceInvoiceTest() {
        String customerNo = "TestingService_PA";
        String serviceItemNo = "1008";
        String resourceNo = "MARK";
        String quantity = "20";

        String expectedJurisdiction = "MICHIGAN";
        String expectedTax = "166.80";
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();

        //enable service management toggle
        adminPage.updateServiceManagementOn();
        adminPage.clickBackAndSaveArrow();

        //select Service Order in service management menu
        BusinessServiceOrderListPage serviceOrders = servicePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SERVICE_MANAGEMENT.value,"Service Orders");
        BusinessServiceOrdersPage serviceOrder = serviceOrders.pageNavMenu.clickNew();


        //create Service Order
        fillInServiceOrdersGeneralInfo(serviceOrder,customerNo);

        //select alternative address
        serviceOrder.openCategory("Shipping");
        serviceOrder.selectShipToCodeForCustomerAddress("MI");

        // Add service item
        serviceOrder.expandTable();
        serviceOrder.enterItemNumber("1008", 1);
        serviceOrder.exitExpandTable();

        // Navigate to Service Item Worksheet
        BusinessServiceItemPage serviceItemSheet = serviceOrder.navigateToServiceItemWorksheet();
        serviceItemSheet.expandServiceItemWorksheet();
        serviceItemSheet.fillInItemsTableInfoForService("Resource",resourceNo, quantity,null,1);

        //Verify Total tax on Service Item Worksheet
        String totalTaxOnServiceLines = serviceOrder.getTotalTaxAmount();
        assertEquals(totalTaxOnServiceLines, expectedTax);

        //Verify Jurisdiction in Tax Details
        serviceMgmtVertexTaxDetails(serviceItemSheet);
        String actualJurisdiction = serviceOrder.getJurisdiction();
        assertEquals(actualJurisdiction, expectedJurisdiction);
        serviceItemSheet.serviceMgmtItemSheetCloseButton();
        serviceItemSheet.closeItemSheet();

        // Post invoice
        BusinessServiceInvoicePage postedInvoice = postServiceOrder();
        String postedInvoiceNo = postedInvoice.getCurrentSalesNumber();

        // Verify tax amount
        homePage.mainNavMenu.goToChildSubNavTab1(
                BusinessMainMenuNavTabs.POSTED_SERVICE_INVOICE.value, BusinessMainMenuNavTabs.INVOICE.value,"Statistics");
        String postedTaxAmount = postedInvoice.getStatisticsTaxAmount();
        assertEquals(postedTaxAmount, expectedTax);
        postedInvoice.closeTaxDetailsDialog();

        // Verify jurisdiction
        openVertexTaxDetailsWindow(postedInvoice);
        actualJurisdiction = serviceOrder.getJurisdiction();
        assertEquals(actualJurisdiction, expectedJurisdiction);
        postedInvoice.closeTaxDetailsDialog();
        postedInvoice.clickBackArrow();

        // Create service credit memo
        BusinessServiceCreditMemoListPage creditMemos = servicePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SERVICE_MANAGEMENT.value, "Service Credit Memos");
        BusinessServiceCreditMemoPage serviceCreditMemo = creditMemos.pageNavMenu.clickNew();

        fillInServiceCreditMemosGeneralInfo(serviceCreditMemo, customerNo);
        fillInItemsTableInfo("Resource", resourceNo, null, null, quantity, null, 1);

        // Set original service invoice No. in Invoicing section
        serviceCreditMemo.openCategory("Invoicing");
        serviceCreditMemo.enterServiceInvoiceNo(postedInvoiceNo);

        recalculateTax(serviceCreditMemo);

        // Verify total tax
        String totalTax = serviceCreditMemo.getTotalTaxAmount();
        assertEquals(totalTax, expectedTax);

        // Verify jurisdiction
        openVertexTaxDetailsWindow(serviceCreditMemo);
        actualJurisdiction = serviceOrder.getJurisdiction();
        assertEquals(actualJurisdiction, expectedJurisdiction);
        serviceCreditMemo.closeTaxDetailsDialog();

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
