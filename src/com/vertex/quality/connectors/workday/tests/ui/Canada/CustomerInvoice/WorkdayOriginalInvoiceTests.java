package com.vertex.quality.connectors.workday.tests.ui.Canada.CustomerInvoice;


import com.vertex.quality.connectors.workday.pages.WorkdayBusinessProcessPage;
import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * This test class is to verify "Tax Amount", third party tax calculation status after Customer Invoice is submitted for Canadian Providences
 * It also verifies that while Changing the customer Invoice reversal request is only being called once
 *
 * Providences to be tested are British Columbia (GST@5% + PST@7% = 12%), Nunavut (GST@5%), Ontario (HST@13%) , Quebec (GST@5%+QST@9.975% = 14%)
 * Test Addresses include:
 * British Columbia: 1090 Pender Street W Suite 30 Vancouver, BC V6C 3H1 Canada
 * Nunavut: 1056 Mivvik Street Iqaluit, NU X0A 0H0 Canada
 * Ontario: 393 RayLawson Blvd Brampton, ON L6Y 4C5 Canada
 * Quebec: 340 Boulevard Charest E Quebec, QC G1K 3H5 Canada
 * @author dpatel, vmurthy
 */

public class WorkdayOriginalInvoiceTests extends WorkdayBaseTest {

    String [] expectedQuoteLogStrings = {"AdjustmentFlag: 0", "PostOption: QUOTE", "Changed POST option: QUOTE", "QuotationRequest"};
    String [] expectedInvoiceLogStrings = {"AdjustmentFlag: 0", "PostOption: POST", "Changed POST option: POST", "InvoiceRequest"};
    String [] expectedDistributeLogStrings = {"AdjustmentFlag: 0", "PostOption: DISTRIBUTE", "Changed POST option: DISTRIBUTE", "DistributeTaxRequest"};

    @BeforeMethod
    public void beforeTest()
    {
        signInToHomePage(testStartPage);
    }

    /**
     * This test case is to validate Tax Amount When Item is being purchased by Canadian Customer From Canadian Supplier.
     * Since Destination address is from Quebec Canada the tax would be GST@5% + QST@9.975% = 14.98%
     * CWD-239
     */
    @Test(groups = { "workday_regression" } )
    public void workdayCustomerInvoiceValidateQuotePostQuebecProvidenceTest() {

        //Test Data
        final String expectedTaxCalc = "992.73";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGMSCanadaCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAddressAttributes("GMS Canada", "Maple", "Product 1", 1, "340 Boulevard")
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
    }

    /**
     * This test case is to validate Tax Amount When Item is being purchased by Canadian Customer From Canadian Supplier.
     * Since Destination address is from Ontario Territory address Canada the tax would be 13% to HST
     * @author Vishwa
     * CWD-610
     */
    @Test(groups = { "workday_regression" } )
    public void workdayCustomerInvoiceQuotePostOntarioProvidenceShipAddressTest() {

        //Test Data
        final String expectedTaxCalc = "861.80";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGMSCanadaCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAddressAttributes("GMS Canada", "Maple", "Product 1", 1, "393 RayLawson")
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
    }

    /**
     * This test case is to validate Tax Amount When Item is being purchased by Canadian Customer From Canadian Supplier.
     * Since Destination address is from Vancouver, British Columbia address Canada the tax would be GST@5% + PST@7% = 12%
     * @author Vishwa
     * CWD-611
     */
    @Test(groups = { "workday_regression" } )
    public void workdayCustomerInvoiceQuotePostVancouverBritishColumbiaProvidenceShipAddressTest() {

        //Test Data
        String[] expectedJurisdictions = new String[]{"BRITISH COLUMBIA", "CANADA"};
        String[] expectedTax = new String[]{"696.07", "497.20"};
        final String expectedTaxCalc = "1,193.27";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGMSCanadaCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAddressAttributes("GMS Canada", "Maple", "product 2", 1, "1090 Pender")
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyJurisdictionLineLevelTaxes(expectedJurisdictions, expectedTax));
    }

    /**
     * This test case is to validate Tax Amount When Item is being purchased by Canadian Customer From Canadian Supplier.
     * Exempted Sales Items and tax would be 0.00
     * @author Vishwa
     * CWD-618
     */
    @Test(groups = { "workday_regression" } )
    public void workdayCustomerInvoiceQuotePostVancouverBritishColumbiaProvidenceExemptSalesItemTest() {

        //Test Data
        String[] expectedJurisdictions = new String[]{"CANADA", "CANADA", "BRITISH COLUMBIA", "BRITISH COLUMBIA"};
        String[] expectedTax = new String[]{"0.00", "0.00", "0.00", "0.00"};
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGMSCanadaCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAddressAttributes("GMS Canada", "Maple", "Services 1", 1, "1090 Pender")
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertTrue(afterSubmit.verifyJurisdictionLineLevelTaxes(expectedJurisdictions, expectedTax));
    }

    /**
     * This test case is to validate Tax Amount When Item is being purchased by Canadian Customer From Canadian Supplier.
     * Multiple Products
     * @author Vishwa
     * CWD-624
     */
    @Test(groups = { "workday_regression" } )
    public void workdayCustomerInvoiceQuotePostVancouverBritishColumbiaProvidenceMultipleProductsTest() {

        //Test Data
        String[] expectedTax = new String[]{"12.00", "795.51", "0.00", "0.00", "0.00"};
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;
        String[] products = {"Certification Exam - 100 (CAD)", "Product 1", "Services 1", "Services 13", "Services 2"};

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGMSCanadaCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormMultipleProducts("GMS Canada", "Maple", products, 1, "1090 Pender")
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertTrue(afterSubmit.verifyProductLineTax(products, expectedTax));
    }

    /**
     * This test case is to validate Tax Amount When Item is being purchased by Canadian Customer From Canadian Supplier.
     * Since Destination address is from Vancouver, British Columbia address Canada the tax would be GST@5% + PST@7% = 12%
     * Integration is successful and correct Tax amount is populating in the field
     * Validates the server logs BP: Quote-Invoice
     * @author Vishwa
     * CWD-625
     */
    @Test(groups = { "workday_regression" } )
    public void workdayCustomerInvoiceQuotePostVancouverBritishColumbiaProvidenceShipAddressQuoteInvoiceTest() {

        //Test Data
        String[] expectedJurisdictions = new String[]{"BRITISH COLUMBIA", "CANADA"};
        String[] expectedTax = new String[]{"696.07", "497.20"};
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGMSCanadaCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAddressAttributes("GMS Canada", "Maple", "Product 2", 1, "1090 Pender")
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertTrue(afterSubmit.verifyJurisdictionLineLevelTaxes(expectedJurisdictions, expectedTax));
        afterSubmit.clickOnNewCustomerInvoiceApprovedLink();

        //Verify server logs for Invoice Quote
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedQuoteLogStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Invoice Request
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadServerLogs(false);
        for (String expectedString : expectedInvoiceLogStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test case is to validate Tax Amount When Item is being purchased by Canadian Customer From Canadian Supplier.
     * Since Destination address is from Vancouver, British Columbia address Canada the tax would be GST@5% + PST@7% = 12%
     * Integration is successful and correct Tax amount is populating in the field
     * Validates the server logs BP: Post-Invoice
     * CWD-626
     * @author Vishwa
     */
    @Test(groups = { "workday_regression" } )
    public void workdayCustomerInvoiceQuotePostVancouverBritishColumbiaProvidenceShipAddressPostInvoiceTest() {

        //Test Data
        String[] expectedJurisdictions = new String[]{"BRITISH COLUMBIA", "CANADA"};
        String[] expectedTax = new String[]{"928.10", "662.92"};
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGMSCanadaCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAddressAttributes("GMS Canada", "Maple", "Product 3", 1, "1090 Pender")
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertTrue(afterSubmit.verifyJurisdictionLineLevelTaxes(expectedJurisdictions, expectedTax));
        afterSubmit.clickOnNewCustomerInvoiceApprovedLink();

        //Verify server logs for Invoice Quote
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedQuoteLogStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Invoice Request
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadServerLogs(false);
        for (String expectedString : expectedInvoiceLogStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }
    /**
     * This test case is to validate Tax Amount When Item is being purchased by Canadian Customer From Canadian Supplier.
     * Since Destination address is a Quebec providence address, tax should be (Quebec-GST@5% QST@9.975% = 14.98%)
     * @author Vishwa
     * CWD-238
     */
    @Test(groups = { "workday_regression" } )
    public void workdayCustomerInvoiceQuotePostQuebecProvidenceShipAddressTest() {

        //Test Data
        final String expectedTaxCalc = "1,584.84";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGMSCanadaCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAddressAttributes("GMS Canada", "Maple", "Product 5", 1, "340 Boulevard")
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
        afterSubmit.clickOnNewCustomerInvoiceApprovedLink();

        //Verify server logs for Invoice Quote
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedQuoteLogStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Invoice Request
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadServerLogs(false);
        for (String expectedString : expectedInvoiceLogStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }
}