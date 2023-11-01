package com.vertex.quality.connectors.dynamics365.sales.tests;

import com.vertex.quality.connectors.dynamics365.sales.pages.*;
import com.vertex.quality.connectors.dynamics365.sales.tests.base.SalesBaseTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * this class represents all the test cases for the Sales Quotes
 *
 * @author Shruti
 */
public class SalesQuotesTests extends SalesBaseTest {

    /**
     * CDCRM-45
     * Create Quote with Required Fields Only
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createQuoteRequiredFieldsOnlyTest() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String expectedTotalTax = "6.00";
        String quantity = "1";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesQuotesPage quotePage = salesHomePage.navigateToSalesQuotesPage();

        // Create new quote
        quotePage.clickNew();
        quotePage.fillGeneralInfo("Test", "TestPriceList");
        quotePage.fillShipToAddressInfo("201 Granite Run Dr", "Lancaster", "17601", "USA");
        quotePage.enterCustomer(customerAccount);

        quotePage.clickSave();
        String quoteID = quotePage.getIDAndUpdateName();

        quotePage.addProduct(productName, quantity, null);
        quotePage.clickCalculateTax();

        String totalTax = quotePage.getTotalTax();
        assertEquals(totalTax, expectedTotalTax, "Total Tax Validation Failed");

        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(quoteID);

        assertTrue(xml.contains("<TotalTax>6.0</TotalTax>"));
    }

    /**
     * CDCRM-47
     * Create Quote with Line Discount
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createQuoteWithLineDiscountTest() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String expectedTotalTax = "6.00";
        String expectedUpdatedTotalTax = "5.40";
        String quantity = "1";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesQuotesPage quotePage = salesHomePage.navigateToSalesQuotesPage();

        // Create new quote
        quotePage.clickNew();
        quotePage.fillGeneralInfo("Test", "TestPriceList");
        quotePage.fillShipToAddressInfo("201 Granite Run Dr", "Lancaster", "17601", "USA");
        quotePage.enterCustomer(customerAccount);

        quotePage.clickSave();
        String quoteID = quotePage.getIDAndUpdateName();

        quotePage.addProduct(productName, quantity, null);
        quotePage.clickCalculateTax();

        String totalTax = quotePage.getTotalTax();
        assertEquals(totalTax, expectedTotalTax, "Total Tax Validation Failed");

        quotePage.updateDiscount("10", 1);
        quotePage.clickSave();
        quotePage.clickCalculateTax();

        String updatedTotalTax = quotePage.getTotalTax();
        assertEquals(updatedTotalTax, expectedUpdatedTotalTax, "Total Tax Validation Failed");

        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(quoteID);

        assertTrue(xml.contains("<TotalTax>5.4</TotalTax>"));
    }

    /**
     * CDCRM-130
     * Create Quote and validate XML
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Regression" })
    public void createQuoteTest() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String quantity = "1";
        String expectedTotalTax = "6.00";
        List<String> expectedTaxRates = Arrays.asList("0.06000");

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesQuotesPage quotePage = salesHomePage.navigateToSalesQuotesPage();

        // Create new quote
        quotePage.clickNew();
        quotePage.fillGeneralInfo("Test", "TestPriceList");
        quotePage.fillShipToAddressInfo("1000 Airport Blvd", "Pittsburgh", "15231", "USA");
        quotePage.enterCustomer(customerAccount);
        quotePage.clickSave();

        String quoteID = quotePage.getIDAndUpdateName();
        quotePage.addProduct(productName, quantity, null);

        quotePage.clickCalculateTax();
        String totalTax = quotePage.getTotalTax();
        assertEquals(totalTax, expectedTotalTax, "Total Tax Validation Failed");
        List<String> orderTaxRates = quotePage.getTaxSummariesDetails("Tax Rate");
        assertEquals(orderTaxRates, expectedTaxRates, "Tax Rate Validation Failed");

        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Response");
        String xml = vertexAdminPage.getFirstXMLLog(quoteID);
        assertTrue(xml.contains("<TotalTax>6.0</TotalTax>"));
    }
}
