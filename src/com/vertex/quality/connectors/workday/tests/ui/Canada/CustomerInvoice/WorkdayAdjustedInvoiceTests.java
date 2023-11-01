package com.vertex.quality.connectors.workday.tests.ui.Canada.CustomerInvoice;

import com.vertex.quality.connectors.workday.pages.WorkdayBusinessProcessPage;
import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * This Class Represent UI based Canada country Customer Invoice Tests
 * This Class Represent Adjusted Invoice and rebill Tests
 *
 * @author vmurthy
 */

public class WorkdayAdjustedInvoiceTests extends WorkdayBaseTest {

    final String taxAmountFailedVerification = "Tax Amount verification for changed invoice failed";
    final String fullCreditedInvoiceTaxVerificationFailed = "Full Credited Invoice doesn't have negative values";
    String[] expectedQuoteLog = {"AdjustmentFlag: 1", "PostOption: QUOTE", "Changed POST option: QUOTE", "QuotationRequest"};
    String[] expectedPostLog = {"AdjustmentFlag: 1", "PostOption: POST", "Changed POST option: POST", "InvoiceRequest"};
    String[] expectedKeywordsInPostLog = {"PostOption: POST", "Mode: SINGLE","<vtx:CustomerCode>", "</vtx:Product>"};
    String[] expectedKeywordsInQuoteLog = {"PostOption: QUOTE", "Mode: SINGLE","<vtx:CustomerCode>", "</vtx:Product>"};

    @BeforeMethod
    public void beforeTest()
    {
        signInToHomePage(testStartPage);
    }

    /**
     * This test Covers a Credit Rebill of a Canadian customer Invoice in the providence Nunavut X0A 0H0 with a Taxable item
     * Integration is successful and correct Tax amount is populating in the field
     * BP: Quote-Post
     * @author Dimitra
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceCreditRebillCanadaQuotePostTaxableTest() {

        //Test Data
        String[] expectedTax = new String[]{"795.51"};
        String[] AdjustedTax = new String[]{"3,977.55"};
        final String expectedTaxCalcUpdatedInvoice = "3,977.55";
        final String expectedTaxCalcOriginalInv = "795.51";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;
        String[] product = {"Product 1"};

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGMSCanadaCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormMultipleProducts("GMS Canada", "Maple", product, 1, "1056 Mivvik")
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcOriginalInv, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceAdjForm("CreditRebill", 5, false).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcUpdatedInvoice, taxAmountFailedVerification);
        assertTrue(afterSubmit.verifyProductLineTax(product, AdjustedTax));
        afterSubmit.clickOnClose();

        //Verifying Adjusted Invoice Tax amount
        afterSubmit.clickOnFullCreditedInvoiceLink();
        assertEquals(afterSubmit.getValuesFromTaxAmount(), afterSubmit.convertToNegativeValues(expectedTaxCalcOriginalInv), fullCreditedInvoiceTaxVerificationFailed);
    }

    /**
     * This test Covers Credit Rebill of Canadian customer Invoice in the providence Nunavut X0A 0H0 with a Nontaxable item
     * Integration is successful and correct Tax amount is populating in the field
     * BP: Quote-Post
     * @author Dimitra
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceCreditRebillNunavutQuotePostNonTaxableTest() {

        //Test Data
        String[] expectedTax = new String[]{"0.00"};
        String[] AdjustedTax = new String[]{"0.00"};
        final String expectedTaxCalcUpdatedInvoice = "0.00";
        final String expectedTaxCalcOriginalInv = "0.00";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;
        String[] products = {"Services 13"};

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGMSCanadaCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormMultipleProducts("GMS Canada", "Maple", products, 1, "1056 Mivvik")
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcOriginalInv, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(products, expectedTax));
        afterSubmit.clickOnClose();

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceAdjForm("CreditRebill", 5, false).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcUpdatedInvoice, taxAmountFailedVerification);
        assertTrue(afterSubmit.verifyProductLineTax(products, AdjustedTax));
        afterSubmit.clickOnClose();

        //Verifying Adjusted Invoice Tax amount
        afterSubmit.clickOnFullCreditedInvoiceLink();
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcOriginalInv, fullCreditedInvoiceTaxVerificationFailed);
    }

    /**
     * This test Covers a Credit Rebill of a Canadian customer Invoice in the providence Vancouver, BC V6C 3H1 with a Taxable item
     * Integration is successful and correct Tax amount is populating in the field
     * BP: Quote-Post
     *
     * @author Dimitra
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceCreditRebillVancouverQuotePostTaxableTest() {

        //Test Data
        String[] expectedTax = new String[]{"795.51"};
        String[] AdjustedTax = new String[]{"3,977.55"};
        final String expectedTaxCalcUpdatedInvoice = "3,977.55";
        final String expectedTaxCalcOriginalInv = "795.51";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;
        String[] product = {"Product 1"};

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGMSCanadaCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormMultipleProducts("GMS Canada", "Maple", product, 1, "1090 Pender Street")
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcOriginalInv, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceAdjForm("CreditRebill", 5, false).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcUpdatedInvoice, taxAmountFailedVerification);
        assertTrue(afterSubmit.verifyProductLineTax(product, AdjustedTax));
        afterSubmit.clickOnClose();

        //Verifying Adjusted Invoice Tax amount
        afterSubmit.clickOnFullCreditedInvoiceLink();
        assertEquals(afterSubmit.getValuesFromTaxAmount(), afterSubmit.convertToNegativeValues(expectedTaxCalcOriginalInv), fullCreditedInvoiceTaxVerificationFailed);
    }


    /**
     * This test Covers Adjustment up (Debit) of customer Invoice scenario
     * BP: Quote-Post
     *
     * @author Vishwa
     * CWD-629
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceCanadaDebitAdjustmentQuotePostTest() {

        //Test Data
        String[] expectedTax = new String[]{"12.00", "795.51", "0.00"};
        final String expectedTaxCalcUpdatedInvoice = "855.51";
        final String expectedTaxCalcOriginalInv = "807.51";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;
        String[] product = {"Certification Exam - 100 (CAD)", "Product 1", "Services 1"};

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBusinessProcessForCustomerInvoice("Vertex", postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormMultipleProducts("GMS Canada", "Maple", product, 1, "1090 Pender")
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcOriginalInv, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceDebitAdjForm("CustomerAdjustment", 5).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcUpdatedInvoice, taxAmountFailedVerification);

        //Verifying Adjusted Invoice Tax amount
        afterSubmit.clickOnCustomerInvoiceNewAdjustmentLink();

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
     * This test Covers Credit Full Adjustment of customer Invoice scenario
     * BP: Quote-Post
     *
     * @author Vishwa
     * CWD-628
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceCanadaCreditFullAdjustmentQuotePostTest() {

        //Test Data
        String[] expectedTax = new String[]{"120.00", "795.51", "0.00"};
        String[] AdjustedTax = new String[]{"120.00", "795.51", "0.00"};
        final String expectedTaxCalcUpdatedInvoice = "915.51";
        final String expectedTaxCalc = "915.51";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;
        String[] product = {"Certification Exam - 100 (CAD)", "Product 1", "Services 1"};

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBusinessProcessForCustomerInvoice("Vertex", postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormMultipleProducts("GMS Canada", "Maple", product, 10, "1090 Pender")
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceAdjForm("CustomerAdjustment", 10, true).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), afterSubmit.convertToNegativeValues(expectedTaxCalcUpdatedInvoice), taxAmountFailedVerification);
        assertTrue(afterSubmit.verifyProductLineTax(product, AdjustedTax));
        afterSubmit.clickOnClose();

        //Verifying Adjusted Invoice Tax amount
        afterSubmit.clickOnCustomerInvoiceNewAdjustmentLink();
        assertEquals(afterSubmit.getValuesFromTaxAmount(), afterSubmit.convertToNegativeValues(expectedTaxCalc), fullCreditedInvoiceTaxVerificationFailed);

        //Verify server logs for Adjusted Invoice Quote
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedKeywordsInQuoteLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Adjusted Invoice Post
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadServerLogs(false);
        for (String expectedString : expectedKeywordsInPostLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test Covers Adjustment down (Credit) of customer Invoice scenario
     * BP: Quote-Post
     * @author Vishwa
     * CWD-676
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceCanadaCreditPartialAdjustmentQuotePostTest() {

        //Test Data
        String[] expectedTax = new String[]{"120.00", "795.51", "0.00"};
        String[] AdjustedTax = new String[]{"60.00", "795.51", "0.00"};
        final String expectedTaxCalcUpdatedInvoice = "855.51";
        final String expectedTaxCalcOnUI = "855.51";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;
        String[] product = {"Certification Exam - 100 (CAD)", "Product 1", "Services 1"};

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGMSCanadaCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormMultipleProducts("GMS Canada", "Maple", product, 10, "1090 Pender")
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();

        //Changing the invoice and submitting it
        afterSubmit = fillCustomerInvoiceAdjForm("CustomerAdjustment", 5, false).clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage); // partial adjustment doesn't exist (full adjustment)
        assertEquals(afterSubmit.getValuesFromTaxAmount(), afterSubmit.convertToNegativeValues(expectedTaxCalcUpdatedInvoice), taxAmountFailedVerification);
        assertTrue(afterSubmit.verifyProductLineTax(product, AdjustedTax));
        afterSubmit.clickOnClose();

        //Verifying Adjusted Invoice Tax amount
        afterSubmit.clickOnCustomerInvoiceNewAdjustmentLink();
        assertEquals(afterSubmit.getValuesFromTaxAmount(), afterSubmit.convertToNegativeValues(expectedTaxCalcOnUI), fullCreditedInvoiceTaxVerificationFailed);

        //Verify server logs for Adjusted Invoice Quote
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedKeywordsInQuoteLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
           assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedTaxCalcOnUI));
        }

        //Verify server logs for Adjusted Invoice Post
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadServerLogs(false);
        for (String expectedString : expectedKeywordsInPostLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedTaxCalcOnUI));
        }
    }
}