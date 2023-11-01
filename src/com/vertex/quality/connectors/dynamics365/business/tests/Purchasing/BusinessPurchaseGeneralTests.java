package com.vertex.quality.connectors.dynamics365.business.tests.Purchasing;

import com.vertex.quality.connectors.dynamics365.business.enums.BusinessMainMenuNavTabs;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessAdminHomePage;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessVertexDiagnosticsPage;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessPurchaseInvoicesListPage;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessPurchaseOrdersListPage;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessPurchaseQuotesListPage;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessVendorListsPage;
import com.vertex.quality.connectors.dynamics365.business.tests.base.BusinessBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
@Listeners(TestRerunListener.class)
public class BusinessPurchaseGeneralTests extends BusinessBaseTest {
    String emailAddress = "Mario.Saint-Fleur@vertexinc.com";
    String expectedEmailSuccessMessage = String.format("Email sent to %s", emailAddress);
    BusinessAdminHomePage homePage;

    /**
     *  CDBC-940
     *  Verify the vertex diagnostics for Purchase Quote
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyVertexDiagnosticsPurchaseQuoteTest() {
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseQuotesListPage purchaseQuotes=homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Quotes");
        String docNumber=purchaseQuotes.getDocNumberByRowIndex();
        purchaseQuotes.refreshAndWaitForLoad();

        //Navigate to Vertex Diagnostics page and enter the Account type and email address
        BusinessVertexDiagnosticsPage diagnosticsPage = purchaseQuotes.searchForAndNavigateToVertexDiagnosticsPage();
        diagnosticsPage.selectType("Purchase Quote");
        diagnosticsPage.inputAccountOrDocumentNumber(docNumber);
        diagnosticsPage.inputEmailAddress(emailAddress);
        diagnosticsPage.runDiagnostic();
        String actualMessage = diagnosticsPage.getDialogBoxText();
        assertEquals(actualMessage, expectedEmailSuccessMessage);
    }

    /**
     *  CDBC-941
     *  Verify the vertex diagnostics for Purchase Order
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyVertexDiagnosticsPurchaseOrderTest() {
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseOrdersListPage purchaseOrders=homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Orders");
        String docNumber=purchaseOrders.getDocNumberByRowIndex();
        purchaseOrders.refreshAndWaitForLoad();

        //Navigate to Vertex Diagnostics page, enter the Account type and email address
        BusinessVertexDiagnosticsPage diagnosticsPage = purchaseOrders.searchForAndNavigateToVertexDiagnosticsPage();
        diagnosticsPage.selectType("Purchase Order");
        diagnosticsPage.inputAccountOrDocumentNumber(docNumber);
        diagnosticsPage.inputEmailAddress(emailAddress);
        diagnosticsPage.runDiagnostic();
        String actualMessage = diagnosticsPage.getDialogBoxText();
        assertEquals(actualMessage, expectedEmailSuccessMessage);
    }

    /**
     *  CDBC-942
     *  Verify the vertex diagnostics for Vendor
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyVertexDiagnosticsVendorTest() {
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessVendorListsPage vendorsList =homePage.navigateToVendorsListPage();
        String docNumber=vendorsList.getDocNumberByRowIndex();
        vendorsList.refreshAndWaitForLoad();

        //Navigate to Vertex Diagnostics page, enter the Account type and email address
        BusinessVertexDiagnosticsPage diagnosticsPage = vendorsList.searchForAndNavigateToVertexDiagnosticsPage();
        diagnosticsPage.selectType("Account (Vendor)");
        diagnosticsPage.inputAccountOrDocumentNumber(docNumber);
        diagnosticsPage.inputEmailAddress(emailAddress);
        diagnosticsPage.runDiagnostic();
        String actualMessage = diagnosticsPage.getDialogBoxText();
        assertEquals(actualMessage, expectedEmailSuccessMessage);
    }

    /**
     *  CDBC-1046
     *  Verify the vertex diagnostics for Purchase Invoice
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyVertexDiagnosticsPurchaseInvoiceTest() {
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessPurchaseInvoicesListPage purchaseInvoices=homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Invoices");
        String docNumber=purchaseInvoices.getDocNumberByRowIndex();
        purchaseInvoices.refreshAndWaitForLoad();

        //Navigate to Vertex Diagnostics page, enter the Purchase Invoice no. and email address
        BusinessVertexDiagnosticsPage diagnosticsPage = purchaseInvoices.searchForAndNavigateToVertexDiagnosticsPage();
        diagnosticsPage.selectType("Purchase Invoice");
        diagnosticsPage.inputAccountOrDocumentNumber(docNumber);
        diagnosticsPage.inputEmailAddress(emailAddress);
        diagnosticsPage.runDiagnostic();
        String actualMessage = diagnosticsPage.getDialogBoxText();
        assertEquals(actualMessage, expectedEmailSuccessMessage);
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
