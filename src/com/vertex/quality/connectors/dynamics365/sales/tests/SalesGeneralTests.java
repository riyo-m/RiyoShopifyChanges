package com.vertex.quality.connectors.dynamics365.sales.tests;

import com.vertex.quality.connectors.dynamics365.sales.pages.*;
import com.vertex.quality.connectors.dynamics365.sales.tests.base.SalesBaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * For basic/general tests that don't necessarily fit into a specific or single category
 *
 * @author v-olumbe (Vivek Olumbe)
 */
public class SalesGeneralTests extends SalesBaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        System.out.println("Setup");
    }

    @AfterMethod(alwaysRun = true)
    public void teardownTest() {
        System.out.println("Teardown");
    }

    /**
     *
     * Test logging in to the application
     */
    @Test(groups = { "D365_SALES_Smoke" })
    public void loginTest()
    {
        SalesAdminHomePage homePage = new SalesAdminHomePage(driver);
        homePage.waitForLoadingScreen();
    }

    /**
     * CDCRM-106
     * Create invoice and verify assisted parameters in XML request
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Smoke" })
    public void createInvoiceAndVerifyXMLAssistedParametersTest() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String expectedTotalTax = "6.00";
        String quantity = "1";

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

        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Request");

        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);
        assertTrue(xml.contains("returnAssistedParametersIndicator=\"true\""));
    }

    /**
     * CDCRM-286
     * Create invoice and verify trusted ID is not in XML request
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Smoke" })
    public void createInvoiceAndVerifyXMLTrustedIDTest() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String expectedTotalTax = "6.00";
        String quantity = "1";

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

        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Request");

        String xml = vertexAdminPage.getFirstXMLLog(invoiceID);
        assertTrue(xml.contains("<TrustedId>HIDDEN</TrustedId>"));
    }

    /**
     * CDCRM-290
     * Create Quote with Freight amount and verify XML request
     * @author Vivek Olumbe
     */
    @Test(groups = { "D365_SALES_Smoke" })
    public void createQuoteAndVerifyXMLFreightTest() {
        String customerAccount = "Test Account";
        String productName = "Furniture";
        String expectedTotalTax = "6.60";
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
        quotePage.enterFreightAmount("10");
        quotePage.clickCalculateTax();

        String totalTax = quotePage.getTotalTax();
        assertEquals(totalTax, expectedTotalTax, "Total Tax Validation Failed");

        // navigate to Vertex Admin
        SalesVertexAdminPage vertexAdminPage = salesHomePage.navigateToVertexAdminPage();
        vertexAdminPage.navigateToXMLLogs();
        vertexAdminPage.filterLogs("Calculating Tax - Request");
        String xml = vertexAdminPage.getFirstXMLLog(quoteID);

        assertTrue(xml.contains("<Product productClass=\"null\">FREIGHT</Product>\n" +
                "            <Quantity>1</Quantity>\n" +
                "            <UnitPrice>10.0000000000</UnitPrice>\n" +
                "            <ExtendedPrice>10.0000000000</ExtendedPrice>"));
    }

    /**
     * CDCRM-294
     * Create Order for account with Do Not Process and verify calculation method
     * @author Vivek Olumbe
     */
    public void verifyAccountCalculationMethodInOrderTest() {
        String customerAccount = "Do Not Process Account";
        String expectedCalcMethod = "Do Not Process";

        SalesAdminHomePage adminHomePage = new SalesAdminHomePage(driver);
        SalesHomePage salesHomePage = adminHomePage.navigateToSalesHomePage();
        SalesOrdersPage orderPage = salesHomePage.navigateToSalesOrdersPage();

        // Create new order
        orderPage.clickNew();
        orderPage.fillGeneralInfo("Test", "TestPriceList");
        orderPage.fillShipToAddressInfo("1575 Space Center Dr", "Colorado Springs", "80915", "USA");
        orderPage.enterCustomer(customerAccount);

        // Verify default calculation method
        String calcMethod = orderPage.getCalculationMethod();
        assertEquals(calcMethod, "Process", "Calculation Method is not 'Process' by default");

        orderPage.clickSave();
        String orderID = orderPage.getIDAndUpdateName();

        // Verify calculation method from account
        calcMethod = orderPage.getCalculationMethod();
        assertEquals(calcMethod, expectedCalcMethod, "Calculation Method is not updated to Account's Settings");
    }




}
