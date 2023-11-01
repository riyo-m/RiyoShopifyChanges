package com.vertex.quality.connectors.workday.tests.ui.USA.SupplierInvoice;

import com.vertex.quality.connectors.workday.pages.WorkdayBusinessProcessPage;
import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.pages.WorkdaySupplierInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * this test class is to verify "Tax Amount", third party tax calculation status after Supplier Invoice is submitted
 * It also verifies that while Changing the customer Invoice reversal request is only being called once
 * @author dpatel
 */

public class WorkdayAdjustedInvoiceTests extends WorkdayBaseTest {

    String calcSelfAssessed = "Calculate Self-Assessed Tax";
    String enterTaxDue = "Enter Tax Due to Supplier";
    String calcTaxDue = "Calculate Tax Due to Supplier";
    String post = "post";
    String distribute = "distribute";
    String gps = "GPS";
    String quote = "quote";
    String noTax = "0";
    String headerTax = "50";
    final int qty = 10;
    final int updatedQty = 5;
    WorkdayBusinessProcessPage bpPage;
    WorkdaySupplierInvoiceReviewPage reviewPage;
    String[] moreVendorCharged = {alcoholSwab, laptopPowerAdaptor, chairs};

    /**
     * This test Covers supplier invoice Change scenario with OnDemand/Premise Credential for "Calc Tax Due to Supplier" Tax Option
     * BP: Quote-Post
     * CWD-532
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceChangeUSCalcTaxDueQuotePostTest() {
        final String expectedTaxCalc = noTax;
        final String expectedTaxCalcUpdatedInvoice = noTax;
        String originalTaxOption = calcTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = spectre;
        reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        // Sign in the home page
        signInToHomePage(testStartPage);

        //Fill out Full Supplier Invoice Form and submit it
        fillSupplierInvoiceFormForUpdatedAttributes(company, americanElectric, originalTaxOption, alcoholSwab, qty, noTax).clickOnSubmit();

        //Verify Successful integration and Tax details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalc));

        //Changing the invoice and submitting it
        fillSupplierInvoiceChangeForm(updatedQty).clickOnSubmit();

        //Clicks on newly created invoice and verifies all the invoice details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalcUpdatedInvoice));
    }

    /**
     * This test Covers supplier invoice Change scenario with OnDemand/Premise Credential for "Enter Tax Due to Supplier" Tax Option
     * BP: Quote-Post, No header Tax
     * CWD-531
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceChangeUSEnterTaxDueQuotePostTest() {

        final String expectedTaxCalc = "2.22";
        final String expectedTaxCalcUpdatedInvoice = "1.11";
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcSelfAssessed;
        String company = spectre;

        // Sign in the home page
        signInToHomePage(testStartPage);

        //Fill out Full Supplier Invoice Form and submit it
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(company, americanElectric, originalTaxOption, alcoholSwab, qty, noTax)
                .clickOnSubmit();

        //Verify Successful integration and Tax details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalc));

        //Changing the invoice and submitting it
        afterSubmit = fillSupplierInvoiceChangeForm(updatedQty).clickOnSubmit();

        //Clicks on newly created invoice and verifies all the invoice details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalcUpdatedInvoice));
    }

    /**
     * This test Covers supplier invoice Change scenario with OnDemand/Premise Credential for "Calculate Self Assessed tax" Tax Option
     * BP: Quote-Post, No header Tax
     * CWD-530
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceChangeUSCalcSelfAssessedQuotePostTest() {

        final String expectedTaxCalc = "2.22";
        final String expectedTaxCalcUpdatedInvoice = "1.11";
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String company = spectre;

        // Sign in the home page
        signInToHomePage(testStartPage);

        //Fill out Full Supplier Invoice Form and submit it
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(company, americanElectric, originalTaxOption, alcoholSwab, qty, noTax)
                .clickOnSubmit();

        //Verify Successful integration and Tax details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalc));

        //Changing the invoice and submitting it
        afterSubmit = fillSupplierInvoiceChangeForm(updatedQty).clickOnSubmit();

        //Clicks on newly created invoice and verifies all the invoice details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalcUpdatedInvoice));

    }

    /**
     * This test Covers supplier invoice Change scenario with OnDemand/Premise Credential for "Calculate Self Assessed tax" Tax Option
     * BP: Quote-Distribute, No header Tax
     * CWD-534
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceChangeUSCalcSelfAssessedQuoteDistributeTest() {

        final String expectedTaxCalc = "2.22";
        final String expectedTaxCalcUpdatedInvoice = "1.11";
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String company = spectre;

        // Sign in the home page
        signInToHomePage(testStartPage);

        //Fill out Full Supplier Invoice Form and submit it
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(company, americanElectric, originalTaxOption, alcoholSwab, qty, noTax)
                .clickOnSubmit();

        //Verify Successful integration and Tax details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalc));

        //Changing the invoice and submitting it
        afterSubmit = fillSupplierInvoiceChangeForm(updatedQty).clickOnSubmit();

        //Clicks on newly created invoice and verifies all the invoice details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalcUpdatedInvoice));


    }

    /**
     * This test Covers supplier invoice Change scenario with OnDemand/Premise Credential for "Calculate Self Assessed tax" Tax Option
     * BP: Quote, No header Tax
     * CWD-535
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceChangeUSCalcSelfAssessedQuoteTest() {

        final String expectedTaxCalc = "1.50";
        final String expectedTaxCalcUpdatedInvoice = "0.75";
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String company = gps;

        // Sign in the home page
        signInToHomePage(testStartPage);

        //Fill out Full Supplier Invoice Form and submit it
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(company, americanElectric, originalTaxOption, alcoholSwab, qty, noTax)
                .clickOnSubmit();

        //Verify Successful integration and Tax details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalc));

        //Changing the invoice and submitting it
        afterSubmit = fillSupplierInvoiceChangeForm(updatedQty).clickOnSubmit();

        //Clicks on newly created invoice and verifies all the invoice details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalcUpdatedInvoice));
    }

    /**
     * This test Covers supplier invoice Change scenario with OnDemand/Premise Credential for "Calculate Self Assessed tax" Tax Option
     * BP: Post, No header Tax
     * CWD-536
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceChangeUSEnterTaxDuePostTest() {

        final String expectedTaxCalc = "1.50";
        final String expectedTaxCalcUpdatedInvoice = "0.75";
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String company = gps;
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        // Sign in the home page
        signInToHomePage(testStartPage);

        //Fill out Full Supplier Invoice Form and submit it
        fillSupplierInvoiceFormForUpdatedAttributes(company, americanElectric, originalTaxOption, alcoholSwab, qty, noTax).clickOnSubmit();

        //Verify Successful integration and Tax details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalc));

        //Changing the invoice and submitting it
        fillSupplierInvoiceChangeForm(updatedQty).clickOnSubmit();

        //Clicks on newly created invoice and verifies all the invoice details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalcUpdatedInvoice));

    }


    /**
     * This test Covers Supplier Invoice line level custom object Happy path scenario after changing the original invoice
     * When Tax option is "Enter Tax Due" with no header Tax then no tax should populate in Custom Object Vertex Calculated Tax
     * BP: Quote-Post
     * CWD-561
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceLineLevelCustomObjectHappyPathScenarioChangeQuotePostTest() {
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = spectre;

        // Sign in the home page
        signInToHomePage(testStartPage);

        //Fill out Full Supplier Invoice Form and submit it
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(company, americanElectric, originalTaxOption, alcoholSwab, qty, headerTax)
                .clickOnSubmit();

        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Verify Successful Integration and Tax Amount
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        assertTrue(reviewPage.verifyUpdatedTaxOption(updatedTaxOption));

        //Click on Newly Created Supplier Invoice and verify Line level Vertex Calculated Tax
        assertTrue(reviewPage.verifyLineLevelCustomObjectVertexCalculatedTax("1", "2.22"));

        //Navigating to Supplier Invoice Form
        reviewPage.navigateToSupplierInvoiceFromCustomObject();

        //Changing the invoice and submitting it
        afterSubmit = fillSupplierInvoiceChangeForm(updatedQty).clickOnSubmit();

        //Verify Successful Integration and Tax Amount
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        assertTrue(reviewPage.verifyUpdatedTaxOption(updatedTaxOption));

        //Click on Newly Created Supplier Invoice and verify Line level Vertex Calculated Tax
        assertTrue(reviewPage.verifyLineLevelCustomObjectVertexCalculatedTax("1", "1.11"));
    }

    /**
     * This test Covers a bug which was failing Supplier invoice Adjustment in Pre-process, it also covers updating Line level
     * Custom Object during adjustment
     * BP: Quote-Post
     * CWD-564
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceAdjustmentPreProcessAndCustomObjectBugTest() {

        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = spectre;
        String totalAdjustedAmount = "(12.50)";
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        // Sign in the home page
        signInToHomePage(testStartPage);

        //Fill out Full Supplier Invoice Form and submit it
        fillSupplierInvoiceFormForUpdatedAttributes(company, americanElectric, originalTaxOption, alcoholSwab, qty, headerTax).clickOnSubmit();

        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Verify Successful Integration and Tax Amount
        assertTrue(reviewPage.verifyUpdatedTaxOption(updatedTaxOption));

        //Click on Newly Created Supplier Invoice and verify Line level Vertex Calculated Tax
        assertTrue(reviewPage.verifyLineLevelCustomObjectVertexCalculatedTax("1", "50"));

        //Navigating to Supplier Invoice Form
        reviewPage.navigateToSupplierInvoiceFromCustomObject();

        //Changing the invoice and submitting it
        fillSupplierInvoiceAdjustmentForm(updatedQty, true,true).clickOnSubmit();

        //Verify Successful Integration and Tax Amount
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(reviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(reviewPage.verifyTotalAdjustedAmount(totalAdjustedAmount));

        //Click on Newly Created Supplier Invoice and verify Line level Vertex Calculated Tax
        assertTrue(reviewPage.verifyLineLevelCustomObjectVertexCalculatedTax("1", "0"));
    }

    /**
     * This test Covers supplier invoice Change scenario with OnDemand/Premise Credential for "Enter Tax Due to Supplier" Tax Option
     * BP: Quote-Post, No header Tax
     * CWD-577
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceChangeIntSysCallTwoEnterTaxDueQuotePostTest() {

        final String expectedTaxCalc = "2.22";
        final String expectedTaxCalcUpdatedInvoice = "1.11";
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = spectre;
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        // Sign in the home page
        signInToHomePage(testStartPage);

        //Fill out Full Supplier Invoice Form and submit it
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(company, americanElectric, originalTaxOption, alcoholSwab, qty, headerTax)
                .clickOnSubmit();

        //Verify Successful integration and Tax details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(reviewPage.verifyVendorChrgedTaxFromCustomObject(headerTax));
        verifyHeaderLevelCustomObjectTransactionData("POST", "SUCCESS", "Yes");
        reviewPage.navigateToSupplierInvoiceDetails();

        //Changing the invoice and submitting it
        afterSubmit = fillSupplierInvoiceChangeForm(updatedQty).clickOnSubmit();

        //Clicks on newly created invoice and verifies all the invoice details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalcUpdatedInvoice));
        assertTrue(reviewPage.verifyVendorChrgedTaxFromCustomObject(headerTax));
        verifyHeaderLevelCustomObjectTransactionData("POST", "SUCCESS", "Yes");
    }

    /**
     * This test covers Prorating taxes functionality for supplier Invoice Change
     * This test validate that vendorcharged tax is prorated correctly by using serverlogs
     * BP: Post
     * CWD-583
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceChangeAccrualProratingTaxTest() {
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = gps;
        String headerTaxValue = "3.51";
        final String expectedTaxCalcUpdatedInvoice = "4.11";
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);


        // Signing in Workday
        signInToHomePage(testStartPage);

        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, moreVendorCharged, headerTaxValue)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, moreVendorCharged, updatedTaxOption, headerTaxValue, headerTaxValue, originalTaxOption);
        reviewPage.navigateToSupplierInvoiceDetails();

        //Changing the invoice and submitting it
        afterSubmit = fillSupplierInvoiceChangeForm(updatedQty).clickOnSubmit();

        //Clicks on newly created invoice and verifies all the invoice details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalcUpdatedInvoice));
        assertTrue(reviewPage.verifyVendorChrgedTaxFromCustomObject(headerTaxValue));

    }

    /**
     * this test Create and Submit a Supplier Invoice and then create adjustment on the same invoice
     * and verify the negative tax amount and the integration is completed
     * CWD-205
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceCUTTaxablePostAdjustmentPostTest() {
        String expectedTaxCalc = "97,625.00";
        String expectedTaxCalcForAdjustedInvoice = "(48,812.50)";
        String updatedTaxOption = calcSelfAssessed;
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        // Signing in Workday
        signInToHomePage(testStartPage);

        //creating supplier Invoice
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(spectre, americanElectric, selfAssessedTax, banners, qty, noTax)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
        //Changing the invoice and submitting it
        afterSubmit = fillSupplierInvoiceAdjustmentForm(updatedQty, true,true).clickOnSubmit();

        //Verify Successful Integration and Tax Amount
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(reviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalcForAdjustedInvoice, failedTaxCalcAdjVal);

    }

    /**
     * This test covers change the Supplier Invoice Quantity from the original and verifies that
     * integration is successful and correct Tax amount is populating in the field
     * BP: Post
     * CWD-911
     */
    @Test(groups = {"workday_regression"})
    public void verifyTransactionIDSupplierInvoiceChangeUSPostTest() {
        final String expectedTaxCalc = noTax;
        final String expectedTaxCalcUpdatedInvoice = noTax;
        final String postOption = post;
        String originalTaxOption = calcTaxDue;
        String company = spectre;
        String[] originalProcessLog = {""};
        String[] changedProcessLog = {""};
        WorkdayInvoiceReviewPage afterSubmit;
        reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);
        final WorkdayBusinessProcessPage bpPage;

        // Sign in the home page
        signInToHomePage(testStartPage);

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForSpectreSupplierInvoice(postOption);

        //Fill out Full Supplier Invoice Form and submit it
        afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(company, americanElectric, originalTaxOption, alcoholSwab, qty, noTax)
                .clickOnSubmit();

        //Verify Successful integration and Tax details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalc));
        originalProcessLog[0] += reviewPage.getVertexTransactionId();
        reviewPage.navigateToSupplierInvoiceDetails();

        //Changing the invoice and submitting it
        fillSupplierInvoiceChangeForm(updatedQty).clickOnSubmit();

        //Clicks on newly created invoice and verifies all the invoice details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalcUpdatedInvoice));
        changedProcessLog[0] += reviewPage.getVertexTransactionId();
        reviewPage.navigateToSupplierInvoiceDetails();

        //Verify server logs for original process
        String fileName = downloadServerLogs(true);
        for (String expectedString : originalProcessLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for changed invoice process
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadServerLogs(false);
        for (String expectedString : changedProcessLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test covers change the Supplier Invoice Quantity from the original and verifies that
     * integration is successful and correct Tax amount is populating in the field
     * BP: Quote-Post
     * CWD-912
     */
    @Test(groups = {"workday_regression"})
    public void verifyTransactionIDSupplierInvoiceChangeUSQuotePostTest() {
        final String expectedTaxCalc = noTax;
        final String expectedTaxCalcUpdatedInvoice = noTax;
        final String postOption = post;
        String originalTaxOption = calcTaxDue;
        String company = gps;
        String[] originalProcessLog = {""};
        String[] changedProcessLog = {""};
        WorkdayInvoiceReviewPage afterSubmit;
        reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);
        final WorkdayBusinessProcessPage bpPage;

        // Sign in the home page
        signInToHomePage(testStartPage);

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGPSSupplierInvoice(postOption);

        //Fill out Full Supplier Invoice Form and submit it
        afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(company, americanElectric, originalTaxOption, alcoholSwab, qty, noTax)
                .clickOnSubmit();

        //Verify Successful integration and Tax details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalc));
        originalProcessLog[0] += reviewPage.getVertexTransactionId();
        reviewPage.navigateToSupplierInvoiceDetails();

        //Changing the invoice and submitting it
        fillSupplierInvoiceChangeForm(updatedQty).clickOnSubmit();

        //Clicks on newly created invoice and verifies all the invoice details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalcUpdatedInvoice));
        changedProcessLog[0] += reviewPage.getVertexTransactionId();
        reviewPage.navigateToSupplierInvoiceDetails();

        //Verify server logs for original process
        String fileName = downloadServerLogs(true);
        for (String expectedString : originalProcessLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for changed invoice process
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadServerLogs(false);
        for (String expectedString : changedProcessLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test covers changing the tax option of an invoice from "Enter Tax due to Supplier" to
     * "Calculate Self-Assessed" that the initially entered vendor-charged tax is ignored
     * CWD-1254
     */
    @Test(groups = {"workday_regression"})
    public void verifyVendorTaxIgnoredWhenChangedToSelfAssessScenarioTest() {
        // Test Data
        final String initialExpectedTax = "130.00";
        final String changedExpectedTax = "5.22";
        final String company = "GPS";
        final String originalTaxOption = enterTaxDue;
        final String changedTaxOption = "Calculate Self-Assessed Tax";
        String[] item = new String[] {chairs,alcoholSwab, alcoholWipePads,binderClips,laptopPowerAdaptor};
        String[] taxBreakdown = new String[] {"4.98", "4.39", "9.17", "0.00", "111.46"};
        String[] changedTaxBreakdown = new String[] {"0.19", "0.18", "0.38", "0.00", "4.47"};

        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);


        // Sign into Workday and Fill out Supplier Invoice Form
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(company, americanElectric, originalTaxOption, item, 5, initialExpectedTax, null)
                .clickOnSubmit();
        // Check if Integration is Completed
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);
        // Verify Correct Tax was Calculated
        assertEquals(afterSubmit.getValuesFromTaxAmount(),initialExpectedTax, failedTaxCalcVal);
        reviewPage.clickOnTaxTab();
        assertTrue(reviewPage.verifyTaxBreakdown(taxBreakdown));
        // Fill out Supplier Invoice Change Form and Change Tax Option
        fillSupplierInvoiceChangeFormForUpdatedTaxOption(changedTaxOption).clickOnSubmit();
        // Check if Integration is Completed
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);
        // Verify Correct Tax was Calculated
        assertEquals(afterSubmit.getValuesFromTaxAmount(),changedExpectedTax, failedTaxCalcVal);
        reviewPage.clickOnTaxTab();
        assertTrue(reviewPage.verifyTaxBreakdown(changedTaxBreakdown));

    }
}

