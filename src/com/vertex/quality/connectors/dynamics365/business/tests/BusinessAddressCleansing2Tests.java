package com.vertex.quality.connectors.dynamics365.business.tests;

import com.vertex.quality.connectors.dynamics365.business.enums.BusinessMainMenuNavTabs;
import com.vertex.quality.connectors.dynamics365.business.enums.BusinessPopupMessage;
import com.vertex.quality.connectors.dynamics365.business.pages.*;
import com.vertex.quality.connectors.dynamics365.business.pojo.BusinessAddressPojo;
import com.vertex.quality.connectors.dynamics365.business.tests.base.BusinessBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

/**
 * This is extension of BusinessAddressCleansingTests class
 */
@Listeners(TestRerunListener.class)
public class BusinessAddressCleansing2Tests extends BusinessBaseTest {
    BusinessAdminHomePage homePage;

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

    /**
     * This Test Validates Address cleansing with custom ship to address
     * Document Type: Sales Quote
     * Needed for 2.2.x.0 Release
     * C365BC-500
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression"}, retryAnalyzer = TestRerun.class)
    public void validateAddressCleansingWithCustomShipToSalesQuoteTest()
    {
        //Test Data
        String customerCode = "test1234";
        String itemNumber = "2000-S";
        String quantity = "1";
        String shipToOption = "Custom Address";
        String expectedZipCode = "19720-8919";
        String expectedUpdatedTaxAmount = "0.00";
        BusinessAddressPojo Address = BusinessAddressPojo
                .builder()
                .line1("605 Muscovy CT")
                .city("New Castle")
                .state("DE")
                .zip_code("19720")
                .country("US")
                .build();

        //Class Initialization
        BusinessCustomerCardPage customerCardPage = new BusinessCustomerCardPage(driver);
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

        //Creating New sales Quotes
        BusinessSalesQuotesListPage salesQuotes = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Quotes");
        BusinessSalesQuotesPage newSalesQuote = salesQuotes.pageNavMenu.clickNew();

        fillInSalesQuoteGeneralInfo(newSalesQuote, customerCode);
        newSalesQuote.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesQuote.exitExpandTable();

        String actualTotalTaxAmount = newSalesQuote.getTotalTaxAmount();
        String documentNo = newSalesQuote.getCurrentSalesNumber();

        //Modified Ship to address
        newSalesQuote.shippingAndBillingComponent.selectShipToCustomAddress(shipToOption);
        newSalesQuote.shippingAndBillingComponent.fillInShippingAddress(Address);
        newSalesQuote.clickBackArrow();
        assertEquals(newSalesQuote.getAddressValidPopupMessage(), BusinessPopupMessage.AddressCleansingCustomAddressPopupMessage.value);
        salesQuotes.filterDocument(documentNo);
        String actualZipCode = customerCardPage.getZipCode();

        //Validate address cleansing
        assertEquals(actualZipCode,expectedZipCode);
        String actualUpdatedTaxAmount = newSalesQuote.getTotalTaxAmount();

        //validate the tax after address has been modified
        assertEquals(actualUpdatedTaxAmount,expectedUpdatedTaxAmount);

        //Delete the document
        customerCardPage.deleteDocument();
    }

    /**
     * This Test Validates Address cleansing with custom ship to address
     * Document Type: Sales Order
     * Needed for 2.2.x.0
     * C365BC-499
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression"}, retryAnalyzer = TestRerun.class)
    public void validateAddressCleansingWithCustomShipToSalesOrderTest()
    {
        //Test Data
        String itemNumber1 = "2000-S";
        String quantity = "1";
        String customerCode = "test1234";
        String shipToOption = "Custom Address";
        String expectedZipCode = "34747-1583";
        String expectedUpdatedTaxAmount = "14.26";
        BusinessAddressPojo Address = BusinessAddressPojo
                .builder()
                .line1("8970 Coco Palm Rd")
                .city("Kissimmee")
                .state("FL")
                .zip_code("34747")
                .country("US")
                .build();

        //Class Initialization
        BusinessCustomerCardPage customerCardPage = new BusinessCustomerCardPage(driver);
        BusinessSalesQuotesListPage salesQuotes = new BusinessSalesQuotesListPage(driver);

        //Creating New Sales Order
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
        BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber1, null, null, quantity, null, 1);
        newSalesOrder.exitExpandTable();
        String actualTotalTaxAmount = newSalesOrder.getTotalTaxAmount();
        String documentNo = newSalesOrder.getCurrentSalesNumber();

        //Modified Ship to address
        newSalesOrder.shippingAndBillingComponent.selectShipToCustomAddress(shipToOption);
        newSalesOrder.shippingAndBillingComponent.fillInShippingAddress(Address);
        newSalesOrder.clickBackArrow();
        assertEquals(newSalesOrder.getAddressValidPopupMessage(), BusinessPopupMessage.AddressCleansingCustomAddressPopupMessage.value);
        salesQuotes.filterDocument(documentNo);
        String actualZipCode = customerCardPage.getZipCode();

        //Validate address cleansing
        assertEquals(actualZipCode,expectedZipCode);
        String actualUpdatedTaxAmount = newSalesOrder.getTotalTaxAmount();

        //validate the tax after address has been modified
        assertEquals(actualUpdatedTaxAmount,expectedUpdatedTaxAmount);

        //Delete the document
        customerCardPage.deleteDocument();
    }

    /**
     * This Test Validates Address cleansing with custom ship to address
     * Document Type: Sales Invoice
     * Needed for 2.2.x.0 Release
     * C365BC-498
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression"}, retryAnalyzer = TestRerun.class)
    public void validateAddressCleansingWithCustomShipToSalesInvoiceTest()
    {
        //Test Data
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity = "1";
        String expectedZipCode = "19720-8919";
        String shipToOption = "Custom Address";
        String expectedUpdatedTaxAmount = "0.00";
        BusinessAddressPojo Address = BusinessAddressPojo
                .builder()
                .line1("605 Muscovy CT")
                .city("New Castle")
                .state("DE")
                .zip_code("19720")
                .country("US")
                .build();

        //Initialization of the classes
        BusinessCustomerCardPage customerCardPage = new BusinessCustomerCardPage(driver);
        BusinessSalesQuotesListPage salesQuotes = new BusinessSalesQuotesListPage(driver);

        //Creating Sales Invoice
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
        BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
        newInvoice.expandTable();

        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newInvoice.exitExpandTable();

        String actualTotalTaxAmount = newInvoice.getTotalTaxAmount();
        String documentNo = newInvoice.getCurrentSalesNumber();

        //Modified Ship to address
        newInvoice.shippingAndBillingComponent.selectShipToCustomAddress(shipToOption);
        newInvoice.shippingAndBillingComponent.fillInShippingAddress(Address);
        newInvoice.clickBackArrow();
        newInvoice.acceptSavedButNotPostedPopup();

        //Validate the popup
        assertEquals(newInvoice.getAddressValidPopupMessage(), BusinessPopupMessage.AddressCleansingCustomAddressPopupMessage.value);
        salesQuotes.filterDocument(documentNo);
        String actualZipCode = customerCardPage.getZipCode();

        //Validate address cleansing
        assertEquals(actualZipCode,expectedZipCode);
        String actualUpdatedTaxAmount = newInvoice.getTotalTaxAmount();

        //validate the tax after address has been modified
        assertEquals(actualUpdatedTaxAmount,expectedUpdatedTaxAmount);

        //Delete the document
        customerCardPage.deleteDocument();
    }


}
