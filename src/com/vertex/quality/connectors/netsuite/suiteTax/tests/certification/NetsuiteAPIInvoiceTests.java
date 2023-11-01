package com.vertex.quality.connectors.netsuite.suiteTax.tests.certification;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteEmployee;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteExpenseCategory;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteExpense;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPIExpenseReportsPage;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPIInvoicePage;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseAPITest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Tests SuiteTax Invoice features
 *
 * @author ravunuri
 */

public class NetsuiteAPIInvoiceTests extends NetsuiteBaseAPITest {
    /**
     * Certifies Nexus, tax types & tax codes are correctly reflecting the ship address works properly
     * Invoice: Select a PA customer and add a New Jersey ship address. Verify the invoice has NJ Nexus & New Jersey tax details.
     * CNSAPI-418
     */
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void NexusTests (){
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLIES_PA;
        String expectedTaxResult = "Success";
        String taxRate = "0.0663%";
        String taxDetails = "NEW JERSEY Sales and Use Tax :$6.63";
        String taxStructure = "Tax Structure: SINGLE_RATE";
        String taxCode = "NJ State";
        String taxType = "US Sales";
        String expectedTaxNexus = "NJ";

        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .amount("100.00")
                .location("01: San Francisco")
                .build();
        NetsuiteAddress shipAddress = NetsuiteAddress
                .builder("08505")
                .fullAddressLine1("14 Walnut St")
                .addressLine1("14 Walnut St")
                .city("Bordentown")
                .state(State.NJ)
                .country(Country.USA)
                .zip9("08505")
                .build();

        //Get menus
        NetsuiteNavigationMenus invoiceMenu = getCreateInvoiceMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteAPIInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(invoiceMenu);
        //Enter few items and select shipping address on each item and save the invoice
        setupBasicOrder(invoicePage, customer, item);
        invoicePage.selectExistingShipToAddress(shipAddress);
        //Preview Tax and retrieve the values
        //Save invoice and validate
        invoicePage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = invoicePage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Verify the “Tax Nexus” field is showing 'NJ'
        String actualNexus = invoicePage.getNexusText();
        assertEquals(actualNexus, expectedTaxNexus);

        //Validate the RateClass, Invoice Code and Tax Structure in tax details
        String actualTaxDetails =  invoicePage.gettaxDetailstext();
        assertTrue(actualTaxDetails.contains(taxRate));
        assertTrue(actualTaxDetails.contains(taxDetails));
        assertTrue(actualTaxDetails.contains(taxStructure));
        assertTrue(actualTaxDetails.contains(taxCode));
        assertTrue(actualTaxDetails.contains(taxType));

        deleteDocument(invoicePage);
    }

    /**
     * Certifies Location Codes work properly
     * Invoice: Select a NJ customer & select CA location, verify the Physical Origin on the request reflects the CA address.
     * CNSAPI-417
     */
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void locationCodeTests (){
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_NJ;
        String expectedTaxResult = "Success";
        String physicalOrigin = "<PhysicalOrigin> <StreetAddress1>1500 3rd St</StreetAddress1> <City>San Mateo</City> " +
								"<MainDivision>CA</MainDivision> <PostalCode>94403</PostalCode> " +
								"<Country>USA</Country> </PhysicalOrigin>";
        String administrativeOrigin = "<AdministrativeOrigin> <StreetAddress1>2955 Campus Dr Ste 100</StreetAddress1> " +
									  "<City>San Mateo</City> <MainDivision>CA</MainDivision> <PostalCode>94403-2539" +
									  "</PostalCode> <Country>USA</Country> </AdministrativeOrigin>";
        String expectedTaxNexus = "NJ";
        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .amount("100.00")
                .location("01: San Francisco")
                .build();
        //Get menus
        NetsuiteNavigationMenus invoiceMenu = getCreateInvoiceMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteAPIInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(invoiceMenu);
        //Enter few items and select shipping address on each item and save the invoice
        setupBasicOrder(invoicePage, customer, item);
        invoicePage.saveOrder();
        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = invoicePage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);
        //Verify the “Tax Nexus” field is showing 'NJ'
        String actualNexus = invoicePage.getNexusText();
        assertEquals(actualNexus, expectedTaxNexus);
        //Click on the call details and verify Physical Origin address details
        checkDocumentLogsXmlRequest(invoicePage, physicalOrigin, administrativeOrigin);
    }

    /**
     * Certifies you can Edit Invoices properly
     * CNSAPI-419
     */
    @Ignore
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void editInvoiceTests (){

    }

    /**
     * Certifies Preview Tax feature
     * Invoice: Select a customer and add items. Click on Preview Tax button and verify the tax is populated
     * CNSAPI-981
     */
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void previewTaxFeatureTests (){
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLIES_PA;
        String expectedTaxResult = "Success";
        String taxRate = "0.06";
        String taxDetails = "PENNSYLVANIA Sales and Use Tax :$6";
        String taxStructure = "Tax Structure: SINGLE_RATE";
        String taxCode = "United States State";

        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .amount("100.00")
                .location("01: San Francisco")
                .build();

        //Get menus
        NetsuiteNavigationMenus invoiceMenu = getCreateInvoiceMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteAPIInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(invoiceMenu);
        //Enter few items and select shipping address on each item and save the invoice
        setupBasicOrder(invoicePage, customer, item);

        //Click Preview Tax and retrieve the values for validation
        invoicePage.previewTaxes();
        //Validate the RateClass, Invoice Code and Tax Structure in tax details
        String previewTaxDetails =  invoicePage.gettaxDetailstext();
        assertTrue(previewTaxDetails.contains(taxRate));
        assertTrue(previewTaxDetails.contains(taxDetails));
        assertTrue(previewTaxDetails.contains(taxStructure));
        assertTrue(previewTaxDetails.contains(taxCode));

        //Save invoice and validate
        invoicePage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = invoicePage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);
    }

    /**
     * Certifies Invoice with Expenses as well as Items
     * Invoice: Select a customer and add expense, Item/s. Save the invoice, make sure tax is showing correctly.
     * CNSAPI-981
     */
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void invoiceWithExpensesTest (){
        //Define Employee and Expenses
        NetsuiteEmployee employee = NetsuiteEmployee.EMPLOYEE_TEST;
        NetsuiteExpense expense = NetsuiteExpense
                .builder(NetsuiteExpenseCategory.CATEGORY_1)
                .currency("USA")
                .amount("10.00")
                .build();

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLIES_PA;
        String expectedTaxResult = "Success";
        String itemTaxRate = "0.06%";
        String expenseTaxRate = "0.06%";
        String itemTaxDetails = "PENNSYLVANIA Sales and Use Tax :$6";
        String expenseTaxDetails = "PENNSYLVANIA Sales and Use Tax :$0.6";
        String taxStructure = "Tax Structure: SINGLE_RATE";
        String taxCode = "United States State";
        String itemTaxAmount = "6.00";
        String expenseTaxAmount = "0.60";

        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .amount("100.00")
                .location("01: San Francisco")
                .build();

        //Get menus
        NetsuiteNavigationMenus expenseReportMenu = getExpenseReportsMenu();
        NetsuiteNavigationMenus invoiceMenu = getCreateInvoiceMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();

        //Navigate to menu items for Expense reports
        NetsuiteAPIExpenseReportsPage expenseReportPage = setupManagerPage.navigationPane.navigateThrough(expenseReportMenu);
        //Create Expense Reports
        createExpenseReport(expenseReportPage, employee, customer, expense);
        //Save Expense Report
        expenseReportPage.saveOrder();

        //Navigate to menu items for Invoice creation
        NetsuiteAPIInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(invoiceMenu);
        setupBasicOrder(invoicePage, customer, item);

        //Click expense MarkAll Button to select the expenses
        invoicePage.expenseMarkAll();

        //Save invoice and validate
        invoicePage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = invoicePage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Capture and validate all Tax details of Invoice_Expenses
        String previewTaxDetails =  invoicePage.gettaxDetailsTabletext();
        assertTrue(previewTaxDetails.contains(itemTaxRate));
        assertTrue(previewTaxDetails.contains(expenseTaxRate));
        assertTrue(previewTaxDetails.contains(itemTaxDetails));
        assertTrue(previewTaxDetails.contains(expenseTaxDetails));
        assertTrue(previewTaxDetails.contains(taxStructure));
        assertTrue(previewTaxDetails.contains(taxCode));
        assertTrue(previewTaxDetails.contains(itemTaxAmount));
        assertTrue(previewTaxDetails.contains(expenseTaxAmount));

        //deleteDocument(invoicePage);
    }

    /**
     * Certifies Invoice with Expenses only, no items added
     * Invoice: Select a customer and add expense. Save the invoice, make sure tax is showing correctly.
     * CNSAPI-1088
     */
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void invoiceWithOnlyExpensesNoItemsTest (){
        //Define Employee and Expenses
        NetsuiteEmployee employee = NetsuiteEmployee.EMPLOYEE_TEST;
        NetsuiteExpense expense = NetsuiteExpense
                .builder(NetsuiteExpenseCategory.CATEGORY_1)
                .currency("USA")
                .amount("10.00")
                .build();

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLIES_PA;
        String expectedTaxResult = "Success";
        String expenseTaxRate = "0.06%";
        String expenseTaxDetails = "PENNSYLVANIA Sales and Use Tax :$0.6";
        String taxStructure = "Tax Structure: SINGLE_RATE";
        String expenseTaxAmount = "0.60";

        //Get menus
        NetsuiteNavigationMenus expenseReportMenu = getExpenseReportsMenu();
        NetsuiteNavigationMenus invoiceMenu = getCreateInvoiceMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();

        //Navigate to menu items for Expense reports
        NetsuiteAPIExpenseReportsPage expenseReportPage = setupManagerPage.navigationPane.navigateThrough(expenseReportMenu);
        //Create Expense Reports
        createExpenseReport(expenseReportPage, employee, customer, expense);
        //Save Expense Report
        expenseReportPage.saveOrder();

        //Navigate to menu items for Invoice creation
        NetsuiteAPIInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(invoiceMenu);
        setupBasicOrder(invoicePage, customer);

        //Click expense MarkAll Button to select the expenses
        invoicePage.expenseMarkAll();

        //Save invoice and validate
        invoicePage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = invoicePage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Capture and validate all Tax details of Invoice_Expenses
        String previewTaxDetails =  invoicePage.gettaxDetailsTabletext();
        assertTrue(previewTaxDetails.contains(expenseTaxRate));
        assertTrue(previewTaxDetails.contains(expenseTaxDetails));
        assertTrue(previewTaxDetails.contains(taxStructure));
        assertTrue(previewTaxDetails.contains(expenseTaxAmount));
    }
}
