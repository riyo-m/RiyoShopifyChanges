package com.vertex.quality.connectors.dynamics365.business.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.business.enums.BusinessMainMenuNavTabs;
import com.vertex.quality.connectors.dynamics365.business.pages.*;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessPurchaseInvoicePage;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessPurchaseInvoicesListPage;
import com.vertex.quality.connectors.dynamics365.business.tests.base.BusinessBaseTest;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
@Listeners(TestRerunListener.class)
public class BusinessVertexAdminConfigurationTests extends BusinessBaseTest {
    BusinessAdminHomePage homePage;
    BusinessVertexAdminPage adminPage;
    BusinessVertexAdminConfigurationPage configPage;
    final String companyCode = "USMF";
    final String trustedID = "7326208514988659";
    final String taxCalculationURL = "https://gpcsconnect.vertexsmb.com/vertex-ws/services/CalculateTax70";
    final String addressValidationURL = "https://gpcsconnect.vertexsmb.com/vertex-ws/services/LookupTaxAreas70";

    @BeforeMethod(alwaysRun = true)
    public void setupTest( )
    {
        homePage = new BusinessAdminHomePage(driver);
        adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        configPage = new BusinessVertexAdminConfigurationPage(driver);

            adminPage.openXmlLogAdmin();
            adminPage.updateLogARTaxRequestToOff();
            adminPage.updateLogARTaxResponseOff();
            adminPage.updateLogARInvoiceRequestOff();
            adminPage.updateLogARInvoiceResponseOff();
            adminPage.updateLogAPTaxRequestToOff();
            adminPage.updateLogAPTaxResponseOff();
            adminPage.updateLogAPInvoiceRequestOff();
            adminPage.updateLogAPInvoiceResponseOff();
            configPage.toggleReadOnlyMode();
            configPage.clickBackAndSaveArrow();

    }

    @AfterMethod(alwaysRun = true)
    public void teardownTest( )
    {
        adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        configPage = new BusinessVertexAdminConfigurationPage(driver);

           adminPage.openXmlLogAdmin();
           adminPage.updateLogARTaxRequestToOn();
           adminPage.updateLogARTaxResponseOn();
           adminPage.updateLogARInvoiceRequestOn();
           adminPage.updateLogARInvoiceResponseOn();
           adminPage.updateLogAPTaxRequestToOn();
           adminPage.updateLogAPTaxResponseOn();
           adminPage.updateLogAPInvoiceRequestOn();
           adminPage.updateLogAPInvoiceResponseOn();
           configPage.clickBackAndSaveArrow();
    }

    /**
     * CDBC-1047
     * disable the toggle button for AR Tax Request,Response, Log AR Invoice Request, Response and verify if XML logs are generated*/
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Sales_Regression"}, retryAnalyzer = TestRerun.class)
    public void verifyXMLLogsForARTest() {
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity = "1";

        //Navigate to Sales Invoice and create a new Sales Invoice
        BusinessSalesInvoiceListPage salesInvoices = homePage.mainNavMenu.goToSubNavTab(
                BusinessMainMenuNavTabs.SALES.value, "Sales Invoices");
        BusinessSalesInvoicePage newInvoice = salesInvoices.pageNavMenu.clickNew();

        fillInSalesInvoiceGeneralInfo(newInvoice, customerCode);
        newInvoice.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newInvoice.activateRow(2);
        newInvoice.exitExpandTable();
        String invoiceNo= newInvoice.getCurrentSalesNumber();
        BusinessSalesInvoicePage postedInvoice = salesDocumentPostInvoice(newInvoice);
        String postedInvoiceNo= postedInvoice.getCurrentSalesNumber();
        homePage.searchForAndNavigateToVertexAdminPage();

        // Check XML
        adminPage.openXmlLogCategory();

        //Verify if Tax Calc Request and Response exist
        adminPage.filterDocuments(invoiceNo);
        String xmlData=adminPage.validatingNoXMLLog();
        assertTrue(xmlData.contains("(There is nothing to show in this view)"), "XML Validation Failed and XML exists");
        adminPage.filterDocuments(postedInvoiceNo);
        String xml=adminPage.validatingNoXMLLog();
        assertTrue(xml.contains("(There is nothing to show in this view)"), "XML Validation Failed and XML exists");
        adminPage.clickBackAndSaveArrow();
    }

    /**
     * CDBC-1048
     * disable the toggle button for AP Tax Request,Response, Log AP Invoice Request, Response and verify if XML logs are generated
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void verifyXMLLogsForAPTest() {
        String vendorCode = "TestVendorPA";
        String itemNumber = "1896-S";
        String quantity = "1";

        //Navigate to Purchase Invoice and create a new Purchase Invoice
        BusinessPurchaseInvoicesListPage purchaseInvoices=homePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.PURCHASING.value, "Purchase Invoices");
        BusinessPurchaseInvoicePage newPurchaseInvoice=purchaseInvoices.pageNavMenu.clickNew();
        fillInPurchaseInvoiceGeneralInfo(newPurchaseInvoice,vendorCode);

        newPurchaseInvoice.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newPurchaseInvoice.activateRow(2);
        newPurchaseInvoice.exitExpandTable();
        String invoiceNo= newPurchaseInvoice.getCurrentSalesNumber();
        newPurchaseInvoice.enterVendorInvoiceNo(invoiceNo);

        BusinessPurchaseInvoicePage postedInvoice = salesDocumentPostPurchaseInvoice(newPurchaseInvoice);
        String postedInvoiceNo= postedInvoice.getCurrentSalesNumber();
        homePage.searchForAndNavigateToVertexAdminPage();

        // Check XML
        adminPage.openXmlLogCategory();

        //Verify if Tax Calc Request and Response exist
        adminPage.filterDocuments(invoiceNo);
        String xmlData=adminPage.validatingNoXMLLog();
        assertTrue(xmlData.contains("(There is nothing to show in this view)"), "XML Validation Failed and XML exists");
        adminPage.filterDocuments(postedInvoiceNo);
        String xml=adminPage.validatingNoXMLLog();
        assertTrue(xml.contains("(There is nothing to show in this view)"), "XML Validation Failed and XML exists");
    }
    /**
     * CDBC-1049
     * disable the toggle button for Address cleanse Request,Response and verify if XML logs are generated
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression"}, retryAnalyzer = TestRerun.class)
    public void createLogAddressCleansingTest() {
        String customerName = "LogAddress" + getTransactionDate().replace("/","");
        String addressLineOne = "7 Denny St";
        String city = "Pittsburgh";
        String state = "PA";
        String shortZip = "15238";
        String country = "US";
        String taxAreaCode = "VERTEX";
        String expectedZipCode = "15238-1402";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.updateLogAddressRequestOff();
        adminPage.updateLogAddressResponseOff();
        adminPage.clickBackAndSaveArrow();
        BusinessCustomersListPage customersListPage = homePage.navigateToCustomersListPage();
        BusinessCustomerCardPage customerCardPage = new BusinessCustomerCardPage(driver);
        BusinessVertexAdminConfigurationPage configPage=new BusinessVertexAdminConfigurationPage(driver);

        customerCardPage.clickNew();
        customerCardPage.selectBTOBCustomer();

        customerCardPage.enterCustomerName(customerName);
        fillInCustomerAddressInfo(addressLineOne, null, city, state, shortZip, country);

        customerCardPage.expandInvoicingSection();
        customerCardPage.enterTaxAreaCode(taxAreaCode);

        customerCardPage.toggleReadOnlyMode();

        String actualZipCode = customerCardPage.getAddressZip();
        assertEquals(actualZipCode, expectedZipCode);

        homePage.searchForAndNavigateToVertexAdminPage();
        //Address Cleanse XML Verification
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(customerName);
        String xmlData=adminPage.validatingNoXMLLog();
        assertTrue(xmlData.contains("(There is nothing to show in this view)"), "XML Validation Failed and XML exists");
        adminPage.clickBackAndSaveArrow();
        customerCardPage.deleteDocument();
    }

    /**
     * @TestCase CDBC-1333
     * @Description - Ensuring the correct URL is being used for tax and address validation
     * @Author Mario Saint-Fleur
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression"}, retryAnalyzer = TestRerun.class)
    public void validTaxAndAddressUrlTest(){
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();

        String expectedCompanyCode = "USMF";
        String expectedTrustedID = "7326208514988659";
        String expectedTaxCalculationURL = "https://gpcsconnect.vertexsmb.com/vertex-ws/services/CalculateTax70";
        String expectedAddressValidationURL = "https://gpcsconnect.vertexsmb.com/vertex-ws/services/LookupTaxAreas70";

        //Set Vertex Tax Parameter Details
        String actualCompanyCode = adminPage.setCompanyCode(companyCode);
        assertEquals(actualCompanyCode, expectedCompanyCode,"Expected company code was: " + expectedCompanyCode);
        String actualTrustedID =  adminPage.setTrustedId(trustedID);
        assertEquals(actualTrustedID, expectedTrustedID,"Expected trusted id was: " + expectedTrustedID);
        String actualTaxCalculationURL=  adminPage.setTaxCalculationServerAddress(taxCalculationURL);
        assertEquals(actualTaxCalculationURL, expectedTaxCalculationURL,"Expected tax calculation url was: " + expectedTaxCalculationURL);
        String actualAddressValidationURL = adminPage.setAddressValidationServerAddress(addressValidationURL);
        assertEquals(actualAddressValidationURL, expectedAddressValidationURL,"Expected address validation url was: " + expectedAddressValidationURL);
        adminPage.clickBackAndSaveArrow();

    }

}
