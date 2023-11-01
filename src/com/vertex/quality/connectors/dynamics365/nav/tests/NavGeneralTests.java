package com.vertex.quality.connectors.dynamics365.nav.tests;

import com.vertex.quality.connectors.dynamics365.nav.pages.*;
import com.vertex.quality.connectors.dynamics365.nav.tests.base.NavBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class NavGeneralTests extends NavBaseTest
{
    /**
     * CDNAV-407
     * Tests running Vertex diagnostics against an existing sales quote
     */
    @Test(groups = { "D365_NAV_Smoke", "D365_NAV_Regression" })
    public void vertexDiagnosticSalesQuoteTest( )
    {
        String emailAddress = "dhruv.patel@vertexinc.com";
        String expectedMessageEmailSuccess = String.format("Email sent to %s", emailAddress);

        NavAdminHomePage homePage = initializeTestPageAndSignOn();

        NavSalesQuotesListPage sales_quotes = homePage.mainNavMenu.goToSubNavTab("Sales Quotes");

        String quoteNumber = sales_quotes.getCustomerNumberByRowIndex();

        sales_quotes.refreshAndWaitForLoad();
        NavVertexDiagnosticsPage diagnosticsPage = sales_quotes.searchForAndNavigateToVertexDiagnosticsPage();

        diagnosticsPage.selectType("Sales Quote");
        diagnosticsPage.inputAccountOrDocumentNumber(quoteNumber);
        diagnosticsPage.inputEmailAddress(emailAddress);

        diagnosticsPage.runDiagnostic();

        String actualMessage = diagnosticsPage.getDialogBoxText();

        assertEquals(actualMessage, expectedMessageEmailSuccess);
    }
    /**
     * CDNAV-406
     * Tests running Vertex diagnostics against an existing sales order
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void vertexDiagnosticSalesOrderTest( )
    {
        String emailAddress = "dhruv.patel@vertexinc.com";
        String expectedMessageEmailSuccess = String.format("Email sent to %s", emailAddress);

        NavAdminHomePage homePage = initializeTestPageAndSignOn();


        NavSalesOrderListPage salesOrders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");

        String orderNumber = salesOrders.getCustomerNumberByRowIndex();

        salesOrders.refreshAndWaitForLoad();
        NavVertexDiagnosticsPage diagnosticsPage = salesOrders.searchForAndNavigateToVertexDiagnosticsPage();

        diagnosticsPage.selectType("Sales Order");
        diagnosticsPage.inputAccountOrDocumentNumber(orderNumber);
        diagnosticsPage.inputEmailAddress(emailAddress);

        diagnosticsPage.runDiagnostic();

        String actualMessage = diagnosticsPage.getDialogBoxText();

        assertEquals(actualMessage, expectedMessageEmailSuccess);
    }
    /**
     * CDNAV-408
     * Tests running Vertex diagnostics against an existing customer
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void vertexDiagnosticAccountCustomerTest( )
    {
        String emailAddress = "connectorsdevelopment@vertexinc.com";
        String expectedMessageEmailSuccess = String.format("Email sent to %s", emailAddress);
        String expectedMessageEmailFail = "Email was unable to send. Would you like to download files instead?";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        homePage.waitForPageLoad();
        NavCustomersListPage customers = homePage.navigateToCustomersListPage();

        customers.refreshAndWaitForLoad();
        NavVertexDiagnosticsPage diagnosticsPage = customers.searchForAndNavigateToVertexDiagnosticsPage();

        diagnosticsPage.selectType("Account (Customer)");
        diagnosticsPage.inputAccountOrDocumentNumber("20000");
        diagnosticsPage.inputEmailAddress(emailAddress);

        diagnosticsPage.runDiagnostic();

        String actualMessage = diagnosticsPage.getDialogBoxText();

        assertEquals(actualMessage, expectedMessageEmailSuccess);
    }

    /**
     * CDNAV-405
     * Tests running Vertex diagnostics against an existing sales order
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void vertexDiagnosticSalesInvoiceTest( )
    {
        String emailAddress = "dhruv.patel@vertexinc.com";
        String expectedMessageEmailSuccess = String.format("Email sent to %s", emailAddress);

        NavAdminHomePage homePage = initializeTestPageAndSignOn();


        NavSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToChildSubNavTab("Sales Invoices");

        String orderNumber = salesInvoices.getCustomerNumberByRowIndex();

        salesInvoices.refreshAndWaitForLoad();
        NavVertexDiagnosticsPage diagnosticsPage = salesInvoices.searchForAndNavigateToVertexDiagnosticsPage();

        diagnosticsPage.selectType("Sales Invoice");
        diagnosticsPage.inputAccountOrDocumentNumber(orderNumber);
        diagnosticsPage.inputEmailAddress(emailAddress);

        diagnosticsPage.runDiagnostic();

        String actualMessage = diagnosticsPage.getDialogBoxText();

        assertEquals(actualMessage, expectedMessageEmailSuccess);
    }
    /**
     * CDNAV-404
     * Tests running a Vertex diagnostic on a sales order with an incorrect number,
     * leading to an error
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void vertexDiagnosticInvalidNumberTest( )
    {
        String incorrectDocumentNumber = "12345";
        String emailAddress = "Prachi.potdar@vertexinc.com";
        String expectedErrorMessage = String.format("No Sales Order was found with ID: %s", incorrectDocumentNumber);

        NavAdminHomePage homePage = initializeTestPageAndSignOn();

        NavCustomersListPage customers = homePage.navigateToCustomersListPage();
        customers.refreshAndWaitForLoad();
        NavVertexDiagnosticsPage diagnosticsPage = homePage.searchForAndNavigateToVertexDiagnosticsPage();

        diagnosticsPage.selectType("Sales Order");
        diagnosticsPage.inputAccountOrDocumentNumber(incorrectDocumentNumber);
        diagnosticsPage.inputEmailAddress(emailAddress);

        diagnosticsPage.runDiagnostic();
        String actualErrorMessage = diagnosticsPage.getDialogBoxText();

        assertEquals(actualErrorMessage, expectedErrorMessage);
    }

}
