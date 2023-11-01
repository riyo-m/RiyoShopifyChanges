package com.vertex.quality.connectors.workday.tests.ui.USA.CustomerInvoice;

import com.vertex.quality.connectors.workday.pages.WorkdayBusinessProcessPage;
import com.vertex.quality.connectors.workday.pages.WorkdayCustomerInvoiceReviewPage;
import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * this test class covers end to end functionality for Customer Invoice Adjustment
 *
 * @author Vishwa
 */

public class WorkdayAdjustedInvoiceTests extends WorkdayBaseTest {

    final String taxAmountFailedVerification = "Tax Amount verification for changed invoice failed";
    final String fullCreditedInvoiceTaxVerificationFailed = "Full Credited Invoice doesn't have negative values";
    String [] firstQuoteExpectedStrings = {"AdjustmentFlag: 1", "PostOption: QUOTE", "Changed POST option: QUOTE","QuotationRequest"};
    String [] secondDistExpectedStrings = {"AdjustmentFlag: 1", "PostOption: DISTRIBUTE", "Changed POST option: POST","InvoiceRequest"};
    String [] expectedQuoteStrings = {"AdjustmentFlag: 1", "PostOption: QUOTE", "Changed POST option: QUOTE","QuotationRequest"};
    String [] expectedPostStrings = {"AdjustmentFlag: 1", "PostOption: POST", "Changed POST option: POST","InvoiceRequest"};
    String [] secondPostExpectedStrings = {"AdjustmentFlag: 1", "PostOption: POST", "Changed POST option: POST","InvoiceRequest"};

    @BeforeMethod
    public void beforeTest()
    {
        signInToHomePage(testStartPage);
    }

    /**
     * This test Covers Adjustment up (Debit) of customer Invoice scenario
     * BP: Quote-Distribute
     * CWD-251
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoiceAdjustmentUPQuoteDistribute() {

        //Test Data
        final String expectedTaxCalcInvoice = "5,996.43";
        final String expectedTaxCalcAdjustedInvoice = "3,777.68";
        final String postOption = "distribute";
        String company = "Vertex";
        String[] product = {"Product 1", "Product 10", "Product 4 has", "Services 13", "Services 2"};
        String[] expectedTax = new String[]{"4,437.50", "848.93", "710.00", "0.00", "0.00"};
        String[] updatedExpectedTax = new String[]{"2,218.75", "848.93", "710.00", "0.00", "0.00"};
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBusinessProcessForCustomerInvoice(company, postOption);

        //Filling out and Submitting the invoice
        //afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("GPS", "LexCorp", "Product 4 has", 10)
        afterSubmit = fillCustomerInvoiceFormMultipleProducts("GPS", "LexCorp", product, 10, null)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcInvoice,taxAmountFailedVerification);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceDebitAdjForm("CustomerAdjustment", 5).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcAdjustedInvoice,taxAmountFailedVerification);
        assertTrue(afterSubmit.verifyProductLineTax(product, updatedExpectedTax));
        afterSubmit.clickOnClose();
        afterSubmit.clickOnCustomerInvoiceNewAdjustmentLink();

        //Verify server logs for Adjusted Invoice Quote
        String fileName= downloadServerLogs(true);
        for (String expectedString : firstQuoteExpectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Adjusted Invoice Distribute
        afterSubmit.deleteFilesFromDirectory();
        fileName= downloadServerLogs(false);
        for (String expectedString : secondDistExpectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers Adjustment of customer Invoice, Bug was found that even during the quote tax was being distributed
     * Integration is successful and correct Tax amount is populating in the field
     * Verifying from the server logs that tax is not being distributed upon quote
     * BP: Quote
     * CWD-543
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoiceAdjustmentBugFixUSQuoteTest()
    {
        //Test Data
        final String expectedTaxCalc = "122.90";
        final String expectedTaxCalcUpdatedInvoice = "(61.45)";
        final String postOption = "quote";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
       bpPage.changeBusinessProcessForCustomerInvoice("Vertex", postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes(spectre, "LexCorp", "Services 13", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceAdjForm("CustomerAdjustment",5, false).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,taxAmountFailedVerification);

        afterSubmit.clickOnCustomerInvoiceNewAdjustmentLink();

        //download server logs and verify values
        String fileName= downloadServerLogs(true);
        for (String expectedString : expectedQuoteStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers Adjsustment of customer Invoice, Bug was found that even during the quote tax was being distributed
     * Integration is successful and correct Tax amount is populating in the field
     * Verifying from the server logs that tax is not being distributed upon quote
     * BP: Post
     * CWD-544
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoiceAdjustmentBugFixUSPostTest() {

        //Test Data
        final String expectedTaxCalc = "122.90";
        final String expectedTaxCalcUpdatedInvoice = "(61.45)";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;


        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForSpectreCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes(spectre, "LexCorp", "Services 13", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceAdjForm("CustomerAdjustment",5, false).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,taxAmountFailedVerification);

        afterSubmit.clickOnCustomerInvoiceNewAdjustmentLink();

        //download server logs and verify values
        String fileName= downloadServerLogs(true);
        for (String expectedString : expectedPostStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers Adjustment of customer Invoice, Bug was found that even during the quote tax was being distributed
     * Integration is successful and correct Tax amount is populating in the field
     * Verifying from the server logs that tax is not being distributed upon quote
     * BP: Quote-Post
     * CWD-545
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoiceAdjustmentBugFixUSQuotePostTest() {

        //Test Data
        final String expectedTaxCalc = "7,600.00";
        final String expectedTaxCalcUpdatedInvoice = "(3,800.00)";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;


        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGPSCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("GPS", "LexCorp", "Product 4 has", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceAdjForm("CustomerAdjustment",5, false).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,taxAmountFailedVerification);

        afterSubmit.clickOnCustomerInvoiceNewAdjustmentLink();
        //Verify server logs for Adjusted Invoice Quote
        String fileName= downloadServerLogs(true);
        for (String expectedString : firstQuoteExpectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Adjusted Invoice Distribute
        afterSubmit.deleteFilesFromDirectory();
        fileName= downloadServerLogs(false);
        for (String expectedString : secondPostExpectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers Adjustment of customer Invoice, Bug was found that even during the quote tax was being distributed
     * Integration is successful and correct Tax amount is populating in the field
     * Verifying from the server logs that tax is not being distributed upon quote
     * BP: Quote-Distribute
     * CWD-546
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoiceAdjustmentBugFixUSQuoteDistributeTest() {

        //Test Data
        final String expectedTaxCalc = "7,600.00";
        final String expectedTaxCalcUpdatedInvoice = "(3,800.00)";
        final String postOption = "distribute";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;


        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGPSCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("GPS", "LexCorp", "Product 4 has", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceAdjForm("CustomerAdjustment",5, false).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,taxAmountFailedVerification);

        afterSubmit.clickOnCustomerInvoiceNewAdjustmentLink();
        //Verify server logs for Adjusted Invoice Quote
        String fileName= downloadServerLogs(true);
        for (String expectedString : firstQuoteExpectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Adjusted Invoice Distribute
        afterSubmit.deleteFilesFromDirectory();
        fileName= downloadServerLogs(false);
        for (String expectedString : secondDistExpectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers Adjustment up (Debit) of customer Invoice scenario
     * BP: Quote-Distribute
     * CWD-250
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoiceAdjustmentUPQuotePost() {

        //Test Data
        final String expectedTaxCalc = "7,600.00";
        final String expectedTaxCalcUpdatedInvoice = "3,800.00";
        final String postOption = "distribute";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;


        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGPSCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("GPS", "LexCorp", "Product 4 has", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceDebitAdjForm("CustomerAdjustment", 5).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,taxAmountFailedVerification);

        afterSubmit.clickOnCustomerInvoiceNewAdjustmentLink();
        //Verify server logs for Adjusted Invoice Quote
        String fileName= downloadServerLogs(true);
        for (String expectedString : firstQuoteExpectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Adjusted Invoice Distribute
        afterSubmit.deleteFilesFromDirectory();
        fileName= downloadServerLogs(false);
        for (String expectedString : secondDistExpectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers bug found in negative amount full credited Invoice, It was sending positive amount
     * BP: Quote-Post
     * CWD-655
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoiceNegativeFullCreditedInvoiceBugFixTest()
    {
        //Test Data
        final String expectedTaxCalc = "4,750.00";
        final String expectedTaxCalcUpdatedInvoice = "(4,750.00)";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        final String expectedStrings = "<vtx:ExtendedPrice>-50000</vtx:ExtendedPrice>";
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGPSCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("GPS", "LexCorp", "Product 1", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceAdjForm("CreditRebill",5,false).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        afterSubmit.clickOnFullCreditedInvoiceLink();
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,taxAmountFailedVerification);

        //Verify server logs for Adjusted Invoice Distribute
        downloadServerLogs(true);

        //Verify server logs for Adjusted Invoice Distribute
        afterSubmit.deleteFilesFromDirectory();
        String fileName= downloadServerLogs(false);
        assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName,expectedStrings));
    }

    /**
     * This test Covers bug found in negative amount full credited Invoice, It was sending positive amount
     * BP: Quote-Post
     * CWD-672
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoiceNegativeFullAdjustedInvoiceBugFixTest()
    {
        //Test Data
        final String expectedTaxCalc = "4,750.00";
        final String expectedTaxCalcUpdatedInvoice = "(4,750.00)";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        final String expectedStrings = "<TotalTax>-4750.0</TotalTax>";
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGPSCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("GPS", "LexCorp", "Product 1", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceAdjForm("CustomerAdjustment",10,false).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,taxAmountFailedVerification);

        afterSubmit.clickOnCustomerInvoiceNewAdjustmentLink();

        //download server logs and verify values
        downloadServerLogs(true);

        //Verify server logs for Adjusted Invoice Distribute
        afterSubmit.deleteFilesFromDirectory();
        String fileName= downloadServerLogs(false);
        assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName,expectedStrings));
    }

    /**
     * This test Covers change the customer Invoice Quantity from the original and verifies that
     * Integration is successful and correct Tax amount is populating in the field
     * BP: Quote
     * CWD-526
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoiceChangeUSQuoteTest()
    {
        //Test Data
        final String expectedTaxCalc = "122.90";
        final String expectedTaxCalcUpdatedInvoice = "61.45";
        final String postOption = "quote";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForSpectreCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("Spectre", "LexCorp", "Services 13", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);
        afterSubmit.clickOnNewCustomerInvoiceApprovedLink();

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceChangeForm(5).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,taxAmountFailedVerification);
    }

    /**
     * This test Covers change the customer Invoice Quantity from the original and verifies that
     * Integration is successful and correct Tax amount is populating in the field
     * BP: Post
     * CWD-527
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoiceChangeUSPostTest()
    {
        //Test Data
        final String expectedTaxCalc = "122.90";
        final String expectedTaxCalcUpdatedInvoice = "61.45";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;


        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForSpectreCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("Spectre", "LexCorp", "Services 13", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);
        afterSubmit.clickOnNewCustomerInvoiceApprovedLink();

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceChangeForm(5).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,taxAmountFailedVerification);

    }

    /**
     * This test Covers change the customer Invoice Quantity from the original and verifies that
     * Integration is successful and correct Tax amount is populating in the field
     * BP: Quote-Post
     * CWD-522
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoiceChangeUSQuotePostTest()
    {
        //Test Data
        final String expectedTaxCalc = "7,600.00";
        final String expectedTaxCalcUpdatedInvoice = "3,800.00";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;


        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGPSCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("GPS", "LexCorp", "Product 4 has", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);
        afterSubmit.clickOnNewCustomerInvoiceApprovedLink();

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceChangeForm(5).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,taxAmountFailedVerification);
    }

    /**
     * This test Covers change the customer Invoice Quantity from the original and verifies that
     * Integration is successful and correct Tax amount is populating in the field
     * BP: Quote-Distribute
     * CWD-529
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoiceChangeUSQuoteDistributeTest()
    {
        //Test Data
        final String expectedTaxCalc = "7,600.00";
        final String expectedTaxCalcUpdatedInvoice = "3,800.00";
        final String postOption = "distribute";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGPSCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("GPS", "LexCorp", "Product 4 has", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);
        afterSubmit.clickOnNewCustomerInvoiceApprovedLink();

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceChangeForm(5).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,taxAmountFailedVerification);
    }

    /**
     * This test Covers Credit Rebill of customer Invoice, Bug was found that even during the quote tax was being distributed
     * Integration is successful and correct Tax amount is populating in the field
     * Verifying from the server logs that tax is not being distributed upon quote
     * BP: Quote
     * CWD-539
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoiceCreditRebillBugFixUSQuoteTest()
    {
        //Test Data
        final String expectedTaxCalc = "122.90";
        final String expectedTaxCalcUpdatedInvoice = "61.45";
        final String postOption = "quote";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForSpectreCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes(spectre, "LexCorp", "Services 13", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceAdjForm("CreditRebill",5, false).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,taxAmountFailedVerification);

        //verifying the successful integration and tax amount for adjusted Invoice
        afterSubmit.clickOnFullCreditedInvoiceLink();
        assertEquals(afterSubmit.getValuesFromTaxAmount(),afterSubmit.convertToNegativeValues(expectedTaxCalc),fullCreditedInvoiceTaxVerificationFailed);
        String fileName= downloadServerLogs(true);
        for (String expectedString : expectedQuoteStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers Credit Rebill of customer Invoice, Bug was found that even during the quote tax was being distributed
     * Integration is successful and correct Tax amount is populating in the field
     * Verifying from the server logs that tax is not being distributed upon quote
     * BP: Post
     * CWD-540
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoiceCreditRebillBugFixUSPostTest() {

        //Test Data
        final String expectedTaxCalc = "122.90";
        final String expectedTaxCalcUpdatedInvoice = "61.45";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForSpectreCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes(spectre, "LexCorp", "Services 13", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceAdjForm("CreditRebill",5, false).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,taxAmountFailedVerification);

        //verifying the successful integration and tax amount for adjusted Invoice
        afterSubmit.clickOnFullCreditedInvoiceLink();
        assertEquals(afterSubmit.getValuesFromTaxAmount(),afterSubmit.convertToNegativeValues(expectedTaxCalc),fullCreditedInvoiceTaxVerificationFailed);
        String fileName= downloadServerLogs(true);
        for (String expectedString : expectedPostStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers Credit Rebill of customer Invoice, Bug was found that even during the quote tax was being distributed
     * Integration is successful and correct Tax amount is populating in the field
     * Verifying from the server logs that tax is not being distributed upon quote
     * BP: Quote-Post
     * CWD-541
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoiceCreditRebillBugFixUSQuotePostTest() {

        //Test Data
        final String expectedTaxCalc = "7,600.00";
        final String expectedTaxCalcUpdatedInvoice = "3,800.00";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGPSCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("GPS", "LexCorp", "Product 4 has", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceAdjForm("CreditRebill",5, false).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,taxAmountFailedVerification);

        //Verifying Adjusted Invoice Tax amount
        afterSubmit.clickOnFullCreditedInvoiceLink();
        assertEquals(afterSubmit.getValuesFromTaxAmount(),afterSubmit.convertToNegativeValues(expectedTaxCalc),fullCreditedInvoiceTaxVerificationFailed);

        //Verify server logs for Adjusted Invoice Quote
        String fileName= downloadServerLogs(true);
        for (String expectedString : firstQuoteExpectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Adjusted Invoice Post
        afterSubmit.deleteFilesFromDirectory();
        fileName= downloadServerLogs(false);
        for (String expectedString : secondPostExpectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }
    /**
     * This test Covers Credit Rebill of customer Invoice, Bug was found that even during the quote tax was being distributed
     * Integration is successful and correct Tax amount is populating in the field
     * Verifying from the server logs that tax is not being distributed upon quote
     * BP: Quote-Distribute
     * CWD-542
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoiceCreditRebillBugFixUSQuoteDistributeTest() {

        //Test Data
        final String expectedTaxCalc = "7,600.00";
        final String expectedTaxCalcUpdatedInvoice = "3,800.00";
        final String postOption = "distribute";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGPSCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("GPS", "LexCorp", "Product 4 has", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceAdjForm("CreditRebill",5, false).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,taxAmountFailedVerification);

        //Verify server logs for Adjusted Invoice Quote
        afterSubmit.clickOnFullCreditedInvoiceLink();
        assertEquals(afterSubmit.getValuesFromTaxAmount(),afterSubmit.convertToNegativeValues(expectedTaxCalc),fullCreditedInvoiceTaxVerificationFailed);

        //Verify server logs for Adjusted Invoice Quote
        String fileName= downloadServerLogs(true);
        for (String expectedString : firstQuoteExpectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Adjusted Invoice Distribute
        afterSubmit.deleteFilesFromDirectory();
        fileName= downloadServerLogs(false);
        for (String expectedString : secondDistExpectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers Bug fix test for Line Items Swapped in Credit Re-bill
     * BP: Quote-Distribute
     * @author dpatel
     * CWD-698
     */
    @Test(groups = { "workday_regression" })
    public void lineItemsTaxSwappedCreditRebillBugFixTest() {

        //Test Data
        final String expectedTaxCalcUpdatedInvoice = "1,591.81";
        final String expectedTaxCalcOriginalInv = "1,629.81";
        WorkdayInvoiceReviewPage afterSubmit;
        String[] expectedTax = new String[]{"(9.50)","(95.00)","776.30","958.01","0.00"};
        String[] product = {"Product 1","Product 2","Product 5","Product 10", "Services 13"};
        String [] expectedQuoteLog = {"AdjustmentFlag: 1", "PostOption: QUOTE", "pPostOption transformed: QUOTE", "QuotationRequest","taxDate"};
        String [] expectedDistributeLog = {"AdjustmentFlag: 1", "PostOption: DISTRIBUTE", "pPostOption transformed: POST", "InvoiceRequest", "taxDate"};

        //Filling out and Submitting the invoice
        fillOutCustomerInvoiceFormExceptSalesItems("GMS USA", "Lexcorp");
        fillOutCustomerInvoiceSalesItemFields(product[0],"1","-100",null);
        fillOutCustomerInvoiceSalesItemFields(product[1],"1","-1000",null);
        fillOutCustomerInvoiceSalesItemFields(product[2],"1",null,null);
        fillOutCustomerInvoiceSalesItemFields(product[3],"1",null,null);
        afterSubmit= fillOutCustomerInvoiceSalesItemFields(product[4],"1",null,null).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcOriginalInv, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Creating Credit/Re-bill
        afterSubmit = fillCustomerInvoiceAdjForm("CreditRebill", 5, false).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcUpdatedInvoice, taxAmountFailedVerification);

        //Verifying Adjusted Invoice Tax amount
        afterSubmit.clickOnFullCreditedInvoiceLink();
        assertEquals(afterSubmit.getValuesFromTaxAmount(), afterSubmit.convertToNegativeValues(expectedTaxCalcOriginalInv), fullCreditedInvoiceTaxVerificationFailed);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Verify server logs for Adjusted Invoice Quote
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedQuoteLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Adjusted Invoice Distribute
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadServerLogs(false);
        for (String expectedString : expectedDistributeLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers Bug fix test for Line Items Swapped in Full Adjustment Debit
     * BP: Quote-Distribute
     * @author dpatel
     * CWD-711
     */
    @Test(groups = { "workday_regression" })
    public void lineItemsTaxSwappedFullAdjustmentDebitBugFixTest() {

        //Test Data
        final String expectedTaxCalcUpdatedInvoice = "1,629.81";
        final String expectedTaxCalcOriginalInv = "1,629.81";
        WorkdayInvoiceReviewPage afterSubmit;
        String[] expectedTax = new String[]{"(9.50)","(95.00)","776.30","958.01","0.00"};
        String[] product = {"Product 1","Product 2","Product 5","Product 10", "Services 13"};
        String [] expectedQuoteLog = {"AdjustmentFlag: 1", "PostOption: QUOTE", "pPostOption transformed: QUOTE", "QuotationRequest","taxDate"};
        String [] expectedDistributeLog = {"AdjustmentFlag: 1", "PostOption: DISTRIBUTE", "pPostOption transformed: POST", "InvoiceRequest", "taxDate"};

        //Filling out and Submitting the invoice
        fillOutCustomerInvoiceFormExceptSalesItems("GMS USA", "Lexcorp");
        fillOutCustomerInvoiceSalesItemFields(product[0],"1","-100",null);
        fillOutCustomerInvoiceSalesItemFields(product[1],"1","-1000",null);
        fillOutCustomerInvoiceSalesItemFields(product[2],"1",null,null);
        fillOutCustomerInvoiceSalesItemFields(product[3],"1",null,null);
        afterSubmit= fillOutCustomerInvoiceSalesItemFields(product[4],"1",null,null).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcOriginalInv, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Creating Credit/Re-bill
        afterSubmit = fillCustomerInvoiceDebitAdjForm("CustomerAdjustment", 1).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcUpdatedInvoice, taxAmountFailedVerification);

        //Verifying Adjusted Invoice Tax amount
        afterSubmit.clickOnCustomerInvoiceNewAdjustmentLink();
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Verify server logs for Adjusted Invoice Quote
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedQuoteLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Adjusted Invoice Distribute
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadServerLogs(false);
        for (String expectedString : expectedDistributeLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers Bug fix test for Line Items Swapped in Full Adjustment Credit
     * BP: Quote-Distribute
     * @author dpatel
     * CWD-708
     */
    @Test(groups = { "workday_regression" })
    public void lineItemsTaxSwappedFullAdjustmentCreditBugFixTest() {

        //Test Data
        final String expectedTaxCalcUpdatedInvoice = "(1,629.81)";
        final String expectedTaxCalcOriginalInv = "1,629.81";
        WorkdayInvoiceReviewPage afterSubmit;
        String[] expectedTax = new String[]{"(9.50)","(95.00)","776.30","958.01","0.00"};
        String[] product = {"Product 1","Product 2","Product 5","Product 10", "Services 13"};
        String [] expectedQuoteLog = {"AdjustmentFlag: 1", "PostOption: QUOTE", "pPostOption transformed: QUOTE", "QuotationRequest","taxDate"};
        String [] expectedDistributeLog = {"AdjustmentFlag: 1", "PostOption: DISTRIBUTE", "pPostOption transformed: POST", "InvoiceRequest", "taxDate"};

        //Filling out and Submitting the invoice
        fillOutCustomerInvoiceFormExceptSalesItems("GMS USA", "Lexcorp");
        fillOutCustomerInvoiceSalesItemFields(product[0],"1","-100",null);
        fillOutCustomerInvoiceSalesItemFields(product[1],"1","-1000",null);
        fillOutCustomerInvoiceSalesItemFields(product[2],"1",null,null);
        fillOutCustomerInvoiceSalesItemFields(product[3],"1",null,null);
        afterSubmit= fillOutCustomerInvoiceSalesItemFields(product[4],"1",null,null).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcOriginalInv, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Creating Credit/Re-bill
        afterSubmit = fillCustomerInvoiceAdjForm("CustomerAdjustment", 1, true).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcUpdatedInvoice, taxAmountFailedVerification);

        //Verifying Adjusted Invoice Tax amount
        afterSubmit.clickOnCustomerInvoiceNewAdjustmentLink();
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Verify server logs for Adjusted Invoice Quote
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedQuoteLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Adjusted Invoice Distribute
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadServerLogs(false);
        for (String expectedString : expectedDistributeLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers Bug fix test for Line Items Swapped in Partial Adjustment Debit
     * BP: Quote-Distribute
     * @author dpatel
     * CWD-710
     */
    @Test(groups = { "workday_regression" })
    public void lineItemsTaxSwappedPartialAdjustmentCreditBugFixTest() {

        //Test Data
        final String expectedTaxCalcUpdatedInvoice = "(1,876.81)";
        final String expectedTaxCalcOriginalInv = "2,114.31";
        WorkdayInvoiceReviewPage afterSubmit;
        String[] expectedTax = new String[]{"475.00","(95.00)","776.30","958.01","0.00"};
        String[] expectedTaxForAdjInv = new String[]{"237.50","(95.00)","776.30","958.01","0.00"};
        String[] product = {"Product 1","Product 2","Product 5","Product 10","Services 13"};
        String [] expectedQuoteLog = {"AdjustmentFlag: 1", "PostOption: QUOTE", "pPostOption transformed: QUOTE", "QuotationRequest","taxDate"};
        String [] expectedDistributeLog = {"AdjustmentFlag: 1", "PostOption: DISTRIBUTE", "pPostOption transformed: POST", "InvoiceRequest", "taxDate"};

        //Filling out and Submitting the invoice
        fillOutCustomerInvoiceFormExceptSalesItems("GMS USA", "Lexcorp");
        fillOutCustomerInvoiceSalesItemFields(product[0],"1","5000",null);
        fillOutCustomerInvoiceSalesItemFields(product[1],"1","-1000",null);
        fillOutCustomerInvoiceSalesItemFields(product[2],"1",null,null);
        fillOutCustomerInvoiceSalesItemFields(product[3],"1",null,null);
        afterSubmit= fillOutCustomerInvoiceSalesItemFields(product[4],"1","5000",null).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcOriginalInv, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Creating Credit/Re-bill
        afterSubmit = fillCustomerInvoiceAdjFormForUpdatedPrice("CustomerAdjustment","2500" ).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcUpdatedInvoice, taxAmountFailedVerification);

        //Verifying Adjusted Invoice Tax amount
        afterSubmit.clickOnCustomerInvoiceNewAdjustmentLink();
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTaxForAdjInv));
        afterSubmit.clickOnClose();

        //Verify server logs for Adjusted Invoice Quote
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedQuoteLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Adjusted Invoice Distribute
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadServerLogs(false);
        for (String expectedString : expectedDistributeLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * this test is to verify that "Credit/Re-bill" functionality invoke vertex integration
     * also verify that fully credited invoice get created as a result of this with negative tax amount
     * for the company that has a "Distribute" option in the business Process
     * CWD-480
     */
    @Test(groups = { "workday_smoke" })
    public void workdayCustomerInvoiceCreditRebillUSDistributeTest()
    {
        //Test Data
        final String expectedTaxCalc = "24.58";
        final String expectedTaxCalcUpdatedInvoice = "12.29";
        final String postOption = "distribute";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGPSCustomerInvoice(postOption);

        //Creating Invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("Spectre", "LexCorp", "Services 13", 2)
                .clickOnSubmit();

        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);

        //Doing Credit/Re-bill on the invoice
        afterSubmit = fillCustomerInvoiceAdjForm("CreditRebill",1, false).clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,intFailedAdjMessage);

        //verifying tax calc on fully credited Invoice
        afterSubmit.clickOnFullCreditedInvoiceLink();
        assertEquals(afterSubmit.getValuesFromTaxAmount(),afterSubmit.convertToNegativeValues(expectedTaxCalc),fullCreditedInvoiceTaxVerificationFailed);
    }

    /**
     * this test is to verify that "Credit/Re-bill" functionality invoke vertex integration
     * also verify that fully credited invoice get created as a result of this with negative tax amount
     * for the company that has a "POST" option in the business Process
     * CWD-738
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoiceCreditRebillUSPostTest()
    {
        //Creating Invoice
        String expectedTaxCalc = "3,151.37";
        String expectedTaxCalcUpdatedInvoice = "630.27";
        WorkdayInvoiceReviewPage afterSubmit = fillCustomerInvoiceForm("Green Planet Solutions, Inc. (USA)", "Conners & Myers", "Product 10", 5)
                .clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);

        //Doing Credit/Re-bill on the invoice
        afterSubmit = fillCustomerInvoiceAdjForm("CreditRebill",1, false).clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,intFailedAdjMessage);

        //verifying tax calc on fully credited Invoice
        afterSubmit.clickOnFullCreditedInvoiceLink();
        assertEquals(afterSubmit.getValuesFromTaxAmount(),afterSubmit.convertToNegativeValues(expectedTaxCalc),fullCreditedInvoiceTaxVerificationFailed);
    }

    /**
     * this test is to verify that "Create adjustment" functionality invoke vertex integration
     * also verify the tax amount for company that has business process set up for "Distribute" option
     * CWD-435
     */
    @Test(groups = { "workday_smoke" })
    public void workdayCustomerInvoicePartialAdjustmentUSDistributeTest()
    {
        //Creating Invoice
        String expectedTaxCalc = "61.45";
        String expectedTaxCalcadj = "(12.29)";
        WorkdayInvoiceReviewPage afterSubmit= fillCustomerInvoiceForm("Spectre","LexCorp", "Services 13", 5)
                .clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);

        //Doing Adjustment on the invoice and verifying Tax calc
        afterSubmit = fillCustomerInvoiceAdjForm("CustomerAdjustment",1, false).clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"),intFailedAdjMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcadj,failedTaxCalcAdjVal);

    }

    /**
     * this test is to verify that "Create adjustment" functionality invoke vertex integration
     * also verify the tax amount for company that has business process set up for "POST" option
     * CWD-236
     */
    @Test(groups = { "workday_regression" })
    public void workdayCustomerInvoicePartialAdjustmentUSPostTest()
    {
        //Creating Invoice
        String expectedTaxCalc = "1,562.50";
        String expectedTaxCalcadj = "(312.50)";
        WorkdayInvoiceReviewPage afterSubmit= fillCustomerInvoiceForm("GPS", "Conners & Myers", "Product 1", 5)
                .clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);

        //Doing Adjustment on the invoice and verifying Tax calc
        afterSubmit = fillCustomerInvoiceAdjForm("CustomerAdjustment",1, false).clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"),intFailedAdjMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcadj,failedTaxCalcAdjVal);

    }

    /**
     * This test Covers Bug fix test for Line Items Swapped in Credit Re-bill
     * For redesigned/latest/v1.1.1 code base
     * BP: Quote-Distribute
     * @author dpatel
     * CWD-771
     */
    @Test(groups = { "workday_regression" })
    public void lineItemsTaxSwappedCreditRebillBugFixRedesignedCodeBaseTest() {

        //Test Data
        final String expectedTaxCalcUpdatedInvoice = "1,591.81";
        final String expectedTaxCalcOriginalInv = "1,629.81";
        final String postOption = "distribute";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;
        String[] expectedTax = new String[]{"(9.50)","(95.00)","776.30","958.01","0.00"};
        String[] product = {"Product 1","Product 2","Product 5","Product 10", "Services 13"};
        String [] expectedQuoteLog = {"AdjustmentFlag: 1", "PostOption: QUOTE", "Changed POST option: QUOTE", "QuotationRequest","taxDate"};
        String [] expectedDistributeLog = {"AdjustmentFlag: 1", "PostOption: DISTRIBUTE", "Changed POST option: POST", "InvoiceRequest", "taxDate"};

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGPSCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        fillOutCustomerInvoiceFormExceptSalesItems("GPS USA", "Lexcorp");
        fillOutCustomerInvoiceSalesItemFields(product[0],"1","-100",null);
        fillOutCustomerInvoiceSalesItemFields(product[1],"1","-1000",null);
        fillOutCustomerInvoiceSalesItemFields(product[2],"1",null,null);
        fillOutCustomerInvoiceSalesItemFields(product[3],"1",null,null);
        afterSubmit= fillOutCustomerInvoiceSalesItemFields(product[4],"1",null,null).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcOriginalInv, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Creating Credit/Re-bill
        afterSubmit = fillCustomerInvoiceAdjForm("CreditRebill", 5, false).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcUpdatedInvoice, taxAmountFailedVerification);

        //Verifying Adjusted Invoice Tax amount
        afterSubmit.clickOnFullCreditedInvoiceLink();
        assertEquals(afterSubmit.getValuesFromTaxAmount(), afterSubmit.convertToNegativeValues(expectedTaxCalcOriginalInv), fullCreditedInvoiceTaxVerificationFailed);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Verify server logs for Adjusted Invoice Quote
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedQuoteLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Adjusted Invoice Distribute
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadServerLogs(false);
        for (String expectedString : expectedDistributeLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers Bug fix test for Line Items Swapped in Credit Re-bill
     * For redesigned/latest/v1.1.1 code base
     * BP: POST
     * @author dpatel
     * CWD-701
     */
    @Test(groups = { "workday_regression" })
    public void lineItemsTaxSwappedCreditRebillPostBugFixRedesignedCodeBaseTest() {

        //Test Data
        final String expectedTaxCalcUpdatedInvoice = "12.29";
        final String expectedTaxCalcOriginalInv = "12.29";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;
        String[] expectedTax = new String[]{"0.00","0.00","0.00","0.00","12.29"};
        String[] product = {"Product 1","Product 2","Product 5","Product 10", "Services 13"};
        String [] expectedPostLog = {"AdjustmentFlag: 1", "PostOption: POST", "Changed POST option: POST", "InvoiceRequest", "taxDate"};

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForSpectreCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        fillOutCustomerInvoiceFormExceptSalesItems("Spectre", "Lexcorp");
        fillOutCustomerInvoiceSalesItemFields(product[0],"1","-100",null);
        fillOutCustomerInvoiceSalesItemFields(product[1],"1","-1000",null);
        fillOutCustomerInvoiceSalesItemFields(product[2],"1",null,null);
        fillOutCustomerInvoiceSalesItemFields(product[3],"1",null,null);
        afterSubmit= fillOutCustomerInvoiceSalesItemFields(product[4],"1",null,null).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcOriginalInv, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Creating Credit/Re-bill
        afterSubmit = fillCustomerInvoiceAdjForm("CreditRebill", 5, false).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcUpdatedInvoice, taxAmountFailedVerification);

        //Verifying Adjusted Invoice Tax amount
        afterSubmit.clickOnFullCreditedInvoiceLink();
        assertEquals(afterSubmit.getValuesFromTaxAmount(), afterSubmit.convertToNegativeValues(expectedTaxCalcOriginalInv), fullCreditedInvoiceTaxVerificationFailed);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Verify server logs for Adjusted Invoice Quote
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedPostLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers Bug fix test for Line Items Swapped in Full Adjustment Credit
     * For redesigned/latest/v1.1.1 code base
     * BP: Quote-Distribute
     * @author dpatel
     * CWD-768
     */
    @Test(groups = { "workday_regression" })
    public void lineItemsTaxSwappedFullAdjustmentCreditBugFixRedesignedCodeBaseTest() {

        //Test Data
        final String expectedTaxCalcUpdatedInvoice = "(1,629.81)";
        final String expectedTaxCalcOriginalInv = "1,629.81";
        WorkdayInvoiceReviewPage afterSubmit;
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        String[] expectedTax = new String[]{"(9.50)","(95.00)","776.30","958.01","0.00"};
        String[] product = {"Product 1","Product 2","Product 5","Product 10", "Services 13"};
        String [] expectedQuoteLog = {"AdjustmentFlag: 1", "PostOption: QUOTE", "Changed POST option: QUOTE", "QuotationRequest","taxDate"};
        String [] expectedPostLog = {"AdjustmentFlag: 1", "PostOption: POST", "Changed POST option: POST", "InvoiceRequest", "taxDate"};

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGPSCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        fillOutCustomerInvoiceFormExceptSalesItems("GPS", "Lexcorp");
        fillOutCustomerInvoiceSalesItemFields(product[0],"1","-100",null);
        fillOutCustomerInvoiceSalesItemFields(product[1],"1","-1000",null);
        fillOutCustomerInvoiceSalesItemFields(product[2],"1",null,null);
        fillOutCustomerInvoiceSalesItemFields(product[3],"1",null,null);
        afterSubmit= fillOutCustomerInvoiceSalesItemFields(product[4],"1",null,null).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcOriginalInv, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Creating Credit/Re-bill
        afterSubmit = fillCustomerInvoiceAdjForm("CustomerAdjustment", 1, true).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcUpdatedInvoice, taxAmountFailedVerification);

        //Verifying Adjusted Invoice Tax amount
        afterSubmit.clickOnCustomerInvoiceNewAdjustmentLink();
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Verify server logs for Adjusted Invoice Quote
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedQuoteLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Adjusted Invoice Distribute
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadServerLogs(false);
        for (String expectedString : expectedPostLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers Bug fix test for Line Items Swapped in Partial Adjustment Debit
     * For redesigned/latest/v1.1.1 code base
     * BP: Quote-Distribute
     * @author dpatel
     * CWD-769
     */
    @Test(groups = { "workday_regression" })
    public void lineItemsTaxSwappedPartialAdjustmentCreditBugFixRedesignedCodeBaseTest() {

        //Test Data
        final String expectedTaxCalcUpdatedInvoice = "(1,876.81)";
        final String expectedTaxCalcOriginalInv = "2,114.31";
        WorkdayInvoiceReviewPage afterSubmit;
        final String postOption = "distribute";
        final WorkdayBusinessProcessPage bpPage;
        String[] expectedTax = new String[]{"475.00","(95.00)","776.30","958.01","0.00"};
        String[] expectedTaxForAdjInv = new String[]{"237.50","(95.00)","776.30","958.01","0.00"};
        String[] product = {"Product 1","Product 2","Product 5","Product 10","Services 13"};
        String [] expectedQuoteLog = {"AdjustmentFlag: 1", "PostOption: QUOTE", "Changed POST option: QUOTE", "QuotationRequest","taxDate"};
        String [] expectedDistributeLog = {"AdjustmentFlag: 1", "PostOption: DISTRIBUTE", "Changed POST option: POST", "InvoiceRequest", "taxDate"};

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGPSCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        fillOutCustomerInvoiceFormExceptSalesItems("GPS", "Lexcorp");
        fillOutCustomerInvoiceSalesItemFields(product[0],"1","5000",null);
        fillOutCustomerInvoiceSalesItemFields(product[1],"1","-1000",null);
        fillOutCustomerInvoiceSalesItemFields(product[2],"1",null,null);
        fillOutCustomerInvoiceSalesItemFields(product[3],"1",null,null);
        afterSubmit= fillOutCustomerInvoiceSalesItemFields(product[4],"1","5000",null).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcOriginalInv, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Creating Credit/Re-bill
        afterSubmit = fillCustomerInvoiceAdjFormForUpdatedPrice("CustomerAdjustment","2500" ).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcUpdatedInvoice, taxAmountFailedVerification);

        //Verifying Adjusted Invoice Tax amount
        afterSubmit.clickOnCustomerInvoiceNewAdjustmentLink();
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTaxForAdjInv));
        afterSubmit.clickOnClose();

        //Verify server logs for Adjusted Invoice Quote
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedQuoteLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Adjusted Invoice Distribute
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadServerLogs(false);
        for (String expectedString : expectedDistributeLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers Bug fix test for Line Items Swapped in Full Adjustment Debit
     * For redesigned/latest/v1.1.1 code base
     * BP: Quote-Post
     * @author dpatel
     * CWD-770
     */
    @Test(groups = { "workday_regression" })
    public void lineItemsTaxSwappedFullAdjustmentDebitBugFixRedesignedCodeBaseTest() {

        //Test Data
        final String expectedTaxCalcUpdatedInvoice = "1,629.81";
        final String expectedTaxCalcOriginalInv = "1,629.81";
        WorkdayInvoiceReviewPage afterSubmit;
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        String[] expectedTax = new String[]{"(9.50)","(95.00)","776.30","958.01","0.00"};
        String[] product = {"Product 1","Product 2","Product 5","Product 10", "Services 13"};
        String [] expectedQuoteLog = {"AdjustmentFlag: 1", "PostOption: QUOTE", "Changed POST option: QUOTE", "QuotationRequest","taxDate"};
        String [] expectedDistributeLog = {"AdjustmentFlag: 1", "PostOption: POST", "Changed POST option: POST", "InvoiceRequest", "taxDate"};

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGPSCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        fillOutCustomerInvoiceFormExceptSalesItems("GPS", "Lexcorp");
        fillOutCustomerInvoiceSalesItemFields(product[0],"1","-100",null);
        fillOutCustomerInvoiceSalesItemFields(product[1],"1","-1000",null);
        fillOutCustomerInvoiceSalesItemFields(product[2],"1",null,null);
        fillOutCustomerInvoiceSalesItemFields(product[3],"1",null,null);
        afterSubmit= fillOutCustomerInvoiceSalesItemFields(product[4],"1",null,null).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcOriginalInv, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Creating Credit/Re-bill
        afterSubmit = fillCustomerInvoiceDebitAdjForm("CustomerAdjustment", 1).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcUpdatedInvoice, taxAmountFailedVerification);

        //Verifying Adjusted Invoice Tax amount
        afterSubmit.clickOnCustomerInvoiceNewAdjustmentLink();
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Verify server logs for Adjusted Invoice Quote
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedQuoteLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Adjusted Invoice Distribute
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadServerLogs(false);
        for (String expectedString : expectedDistributeLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test covers changing the Customer Invoice Quantity from the original and verifies that
     * Integration is successful and correct Tax amount is populating in the field, as well as verifying that we
     * are using the new Vertex Transaction ID in our custom object and logs
     * BP: Post
     * CWD-907
     */
    @Test(groups = { "workday_regression" })
    public void verifyTransactionIDCustomerInvoiceChangeUSPostTest()
    {
        //Test Data
        final String expectedTaxCalc = "122.90";
        final String expectedTaxCalcUpdatedInvoice = "61.45";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        String[] originalProcessLog = {""};
        String[] changedProcessLog = {"", ""};
        WorkdayInvoiceReviewPage afterSubmit;
        WorkdayCustomerInvoiceReviewPage reviewPage = new WorkdayCustomerInvoiceReviewPage(driver);

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForSpectreCustomerInvoice(postOption);

        // Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("Spectre", "LexCorp", "Services 13", 10)
                .clickOnSubmit();

        // verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);
        originalProcessLog[0] += reviewPage.getVertexTransactionId();
        changedProcessLog[0] += originalProcessLog[0];
        reviewPage.navigateToCustomerInvoiceDetails();

        // Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceChangeForm(5).clickOnSubmit();

        // verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,taxAmountFailedVerification);
        changedProcessLog[1] += reviewPage.getVertexTransactionId();
        reviewPage.navigateToCustomerInvoiceDetails();

        //Verify server logs for original process
        String fileName = downloadChangeInvoiceServerLogs(true);
        for (String expectedString : originalProcessLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for changed invoice process
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadChangeInvoiceServerLogs(false);
        for (String expectedString : changedProcessLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers change the customer Invoice Quantity from the original and verifies that
     * Integration is successful and correct Tax amount is populating in the field
     * BP: Quote-Post
     * CWD-908
     */
    @Test(groups = { "workday_regression" })
    public void verifyTransactionIDCustomerInvoiceChangeUSQuotePostTest()
    {
        //Test Data
        final String expectedTaxCalc = "4,750.00";
        final String expectedTaxCalcUpdatedInvoice = "2,375.00";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        String[] originalProcessLog = {""};
        String[] changedProcessLog = {"", ""};
        WorkdayInvoiceReviewPage afterSubmit;
        WorkdayCustomerInvoiceReviewPage reviewPage = new WorkdayCustomerInvoiceReviewPage(driver);

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGPSCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("GPS", "LexCorp", "Product 1", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc,failedTaxCalcVal);
        originalProcessLog[0] += reviewPage.getVertexTransactionId();
        changedProcessLog[0] += originalProcessLog[0];
        reviewPage.navigateToCustomerInvoiceDetails();

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceChangeForm(5).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"),intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalcUpdatedInvoice,taxAmountFailedVerification);
        changedProcessLog[1] += reviewPage.getVertexTransactionId();
        reviewPage.navigateToCustomerInvoiceDetails();

        //Verify server logs for original process
        String fileName = downloadChangeInvoiceServerLogs(true);
        for (String expectedString : originalProcessLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for changed invoice process
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadChangeInvoiceServerLogs(false);
        for (String expectedString : changedProcessLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers change the customer Invoice Quantity from the original and verifies that
     * Integration is successful and correct Tax amount is populating in the field
     * BP: Quote-Distribute
     * CWD-909
     */
    @Test(groups = { "workday_regression" })
    public void verifyTransactionIDCustomerInvoiceChangeUSQuoteDistributeTest() {
        //Test Data
        final String expectedTaxCalc = "4,750.00";
        final String expectedTaxCalcUpdatedInvoice = "2,375.00";
        final String postOption = "distribute";
        final WorkdayBusinessProcessPage bpPage;
        String[] originalProcessLog = {""};
        String[] changedProcessLog = {"", ""};
        WorkdayInvoiceReviewPage afterSubmit;
        WorkdayCustomerInvoiceReviewPage reviewPage = new WorkdayCustomerInvoiceReviewPage(driver);

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGPSCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("GPS", "LexCorp", "Product 1", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
        originalProcessLog[0] += reviewPage.getVertexTransactionId();
        changedProcessLog[0] += originalProcessLog[0];
        reviewPage.navigateToCustomerInvoiceDetails();

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceChangeForm(5).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcUpdatedInvoice, taxAmountFailedVerification);
        changedProcessLog[1] += reviewPage.getVertexTransactionId();
        reviewPage.navigateToCustomerInvoiceDetails();

        //Verify server logs for original process
        String fileName = downloadChangeInvoiceServerLogs(true);
        for (String expectedString : originalProcessLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for changed invoice process
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadChangeInvoiceServerLogs(false);
        for (String expectedString : changedProcessLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }
}
