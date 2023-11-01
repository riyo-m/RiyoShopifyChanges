package com.vertex.quality.connectors.dynamics365.sales.tests;

import com.vertex.quality.connectors.dynamics365.sales.pages.*;
import com.vertex.quality.connectors.dynamics365.sales.tests.base.SalesBaseTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * this class represents all the test cases for the Sales Invoices
 *
 * @author Shruti
 */
public class SalesInvoicesTests extends SalesBaseTest {

    /**
     * CDCRM-131
     * Create Invoice with Quantity 10
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createInvoiceQuantity10Test() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String expectedTotalTax = "111.10";
        String quantity = "10";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesInvoicesPage invoicePage = salesHomePage.navigateToSalesInvoicesPage();

        // Create new order
        invoicePage.clickNew();
        invoicePage.fillGeneralInfo("Test", "TestPriceList");

        invoicePage.fillShipToAddressInfo("22833 NE 8th St ", "Sammamish", "98074", "USA");
        invoicePage.enterCustomer(customerAccount);

        invoicePage.clickSave();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.addProduct(productName, quantity, null);
        invoicePage.addProduct(productName, "1", null);
        invoicePage.clickCalculateTax();

        String totalTax = invoicePage.getTotalTax();
        assertEquals(totalTax, expectedTotalTax, "Total Tax Validation Failed");

        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);

        assertTrue(xml.contains("<TotalTax>111.1</TotalTax>"));
    }

    /**
     * CDCRM-295
     * Verify no tax calculation after posting invoice
     * @author Vivek Olumbe
     */
    public void createInvoiceAndVerifyNoTaxCalcAfterPostTest() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTotalTax = "6.00";
        int expectedCount = 2;

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesInvoicesPage invoicePage = salesHomePage.navigateToSalesInvoicesPage();

        // Create invoice
        invoicePage.clickNew();
        invoicePage.fillGeneralInfo("Test", "TestPriceList");
        invoicePage.fillShipToAddressInfo("201 Granite Run Dr", "Lancaster", "17601", "USA");
        invoicePage.enterCustomer(customerAccount);

        invoicePage.clickSave();
        String invoiceID = invoicePage.getIDAndUpdateName();

        invoicePage.addProduct(productName, quantity, null);
        invoicePage.clickCalculateTax();

        String totalTax = invoicePage.getTotalTax();
        assertEquals(totalTax, expectedTotalTax, "Total Tax Validation Failed");

        invoicePage.clickPostInvoice();
        invoicePage.clickYes();
        invoicePage.clickCalculateTax();
        invoicePage.clickCalculateTax();

        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Request");
        int actualCount = vertexAdminPage.getDocumentCount(invoiceID, expectedCount);
        assertEquals(actualCount, expectedCount, String.format("Expected document count: %d, Actual document count: %d", expectedCount, actualCount));
    }

}
