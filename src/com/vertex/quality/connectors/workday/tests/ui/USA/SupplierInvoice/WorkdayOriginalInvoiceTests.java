package com.vertex.quality.connectors.workday.tests.ui.USA.SupplierInvoice;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.misc.VertexFileUtils;
import com.vertex.quality.connectors.workday.pages.WorkdayBusinessProcessPage;
import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.pages.WorkdaySupplierInvoicePage;
import com.vertex.quality.connectors.workday.pages.WorkdaySupplierInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * this test class is to verify "Tax Amount", updated tax option,third party tax calculation status after Supplier Invoice is submitted
 * It also verifies that all the text fields are updated correctly after update of supplier invoice
 *
 * @author dpatel
 */

public class WorkdayOriginalInvoiceTests extends WorkdayBaseTest {

    String headerTax = "4.01";
    String zeroHeaderTax = "0";
    String proratedVendorChargedTax = "1007.21";
    String calcSelfAssessed = "Calculate Self-Assessed Tax";
    String enterTaxDue = "Enter Tax Due to Supplier";
    String calcTaxDue = "Calculate Tax Due to Supplier";
    String trustedID = "trustedID";
    String taxCalcURL = "taxCalcURL";
    String post = "post";
    String distribute = "distribute";
    String greenPlanetSolutions = "GPS";
    String quote = "quote";
    String spectreTax = "76.70";
    String gpsTax = "5.79";
    String noTax = "0";
    String usAddress = "king of prussia";
    String caAddress = "vancour";
    String euAddress = "123 high street";
    String[] item = {chairs, alcoholSwab, alcoholWipePads, binderClips, laptopPowerAdaptor};
    String[] proRateTaxItems = {"Advil Packets", alcoholSwab, chairs, binderClips, "water bottle", "Book", "Intercompany fees", "Ergonomic Keyboard", "External hard drive",
            "Audio Visual Services", "Catering services"};
    String[] moreVendorCharged = {alcoholSwab, laptopPowerAdaptor, chairs};
    String[] expectedMoreVendorCharged = {"<vtx:ChargedTax>964.09</vtx:ChargedTax>", "<vtx:ChargedTax>43.12</vtx:ChargedTax>"};
    String[] expectedProRateTaxItems = {"12.74", "26.51", "4.88", "0.66", "0.36", "1.06", "3.64", "13.26", "38.16"};
    WorkdaySupplierInvoiceReviewPage reviewPage;
    WorkdayBusinessProcessPage bpPage;
    WorkdayInvoiceReviewPage invoiceReviewPage;

    /**
     * This test Covers update supplier scenario with OnDemand/Premise Credential for "Calculate Self Assessed Tax" Tax Option
     * BP: Post
     * CWD-490
     */
    @Test(groups = {"workday_smoke"})
    public void workdaySupplierInvoiceCalcSelfAssessedTaxPostTest() {
        String expectedTaxCalc = gpsTax;
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String company = greenPlanetSolutions;
        String headerTaxValue = zeroHeaderTax;
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);


        fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
    }

    /**
     * This test Covers update supplier scenario with OnDemand/Premise Credential for "Calculate Self Assessed Tax" Tax Option
     * BP: Quote-Post
     * CWD-499
     * Removed
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceCalcSelfAssessedTaxQuotePostTest() {
        String expectedTaxCalc = spectreTax;
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String company = spectre;
        String headerTaxValue = zeroHeaderTax;

        signInToHomePage(testStartPage);
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
    }

    /**
     * This test Covers update supplier scenario with OnDemand/Premise Credential for "Calculate Tax Due to Supplier" Tax Option
     * BP: Post
     * CWD-492
     */
    @Test(groups = {"workday_smoke"})
    public void workdaySupplierInvoiceCalcTaxDuePostTest() {
        String expectedTaxCalc = noTax;
        String originalTaxOption = calcTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = greenPlanetSolutions;
        String headerTaxValue = zeroHeaderTax;
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
    }

    /**
     * This test Covers update supplier scenario with OnDemand/Premise Credential for "Calculate Tax Due to Supplier" Tax Option
     * BP: Quote-Post
     * CWD-502
     * Removed
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceCalcTaxDueQuotePostTest() {
        String expectedTaxCalc = noTax;
        String originalTaxOption = calcTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = spectre;
        String headerTaxValue = zeroHeaderTax;
        WorkdaySupplierInvoiceReviewPage reviewPage;
        WorkdayBusinessProcessPage bpPage;


        signInToHomePage(testStartPage);
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
    }

    /**
     * This test Covers update supplier scenario with OnDemand/Premise Credential for "Enter Tax Due to Supplier" Tax Option
     * BP: Post , with Header Tax
     * CWD-491
     */
    @Test(groups = {"workday_smoke"})
    public void workdaySupplierInvoiceEnterTaxDueWithNonZeroHeaderTaxPostTest() {
        String expectedTaxCalc = noTax;
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = greenPlanetSolutions;
        String headerTaxValue = headerTax;
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
    }

    /**
     * This test Covers update supplier scenario with OnDemand/Premise Credential for "Enter Tax Due to Supplier" Tax Option
     * BP: Quote , without Header Tax
     * CWD-496
     * Removed
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceEnterTaxDueWithZeroHeaderTaxQuoteTest() {
        String expectedTaxCalc = gpsTax;
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcSelfAssessed;
        String company = greenPlanetSolutions;
        String headerTaxValue = zeroHeaderTax;


        // Sign in the home page
        signInToHomePage(testStartPage);

        //Fill out Full Supplier Invoice Form and submit it
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();

        //Verifies that integration is completed
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Clicks on newly created invoice and verifies all the invoice details
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
    }

    /**
     * This test Covers update supplier scenario with OnDemand/Premise Credential for "Enter Tax Due to Supplier" Tax Option
     * BP: Post , without Header Tax
     * CWD-482
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceEnterTaxDueWithZeroHeaderTaxPostTest() {
        String expectedTaxCalc = gpsTax;
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcSelfAssessed;
        String company = greenPlanetSolutions;
        String headerTaxValue = zeroHeaderTax;
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);
        WorkdayBusinessProcessPage bpPage;

        signInToHomePage(testStartPage);
        fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
    }

    /**
     * This test Covers update supplier scenario with OnDemand/Premise Credential for "Enter Tax Due to Supplier" Tax Option
     * BP: Quote-Post , without Header Tax
     * CWD-497
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceEnterTaxDueWithZeroHeaderTaxQuotePostTest() {
        String expectedTaxCalc = spectreTax;
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcSelfAssessed;
        String company = spectre;
        String headerTaxValue = zeroHeaderTax;


        signInToHomePage(testStartPage);
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
    }

    /**
     * This test Covers update supplier scenario with OnDemand/Premise Credential for "Enter Tax Due to Supplier" Tax Option
     * BP: Quote-Distribute , without Header Tax
     * CWD-505
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceEnterTaxDueWithZeroHeaderTaxQuoteDistributeTest() {
        String expectedTaxCalc = spectreTax;
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcSelfAssessed;
        String company = spectre;
        String headerTaxValue = zeroHeaderTax;

        signInToHomePage(testStartPage);
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
    }

    /**
     * This test Covers update supplier scenario with OnDemand/Premise Credential for "Enter Tax Due to Supplier" Tax Option
     * BP: Quote , with Header Tax
     * CWD-503
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceEnterTaxDueWithNonZeroHeaderTaxQuoteTest() {
        String expectedTaxCalc = noTax;
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = greenPlanetSolutions;
        String headerTaxValue = headerTax;

        signInToHomePage(testStartPage);
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
    }

    /**
     * This test Covers update supplier scenario with OnDemand/Premise Credential for "Enter Tax Due to Supplier" Tax Option
     * BP: Quote-Post, with Header Tax
     * CWD-511
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceEnterTaxDueWithNonZeroHeaderTaxQuotePostTest() {
        String expectedTaxCalc = noTax;
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = spectre;
        String headerTaxValue = headerTax;

        signInToHomePage(testStartPage);
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
    }

    /**
     * This test Covers update supplier scenario with OnDemand/Premise Credential for "Enter Tax Due to Supplier" Tax Option
     * BP: Quote-Distribute, with Header Tax
     * CWD-507
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceEnterTaxDueWithNonZeroHeaderTaxQuoteDistributeTest() {
        String expectedTaxCalc = noTax;
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = spectre;
        String headerTaxValue = headerTax;


        signInToHomePage(testStartPage);
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
    }

    /**
     * This test Covers update supplier scenario with OnDemand/Premise Credential for "Calculate Tax Due to Supplier" Tax Option
     * BP: Quote
     * CWD-504
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceCalcTaxDueQuoteTest() {
        String expectedTaxCalc = noTax;
        String originalTaxOption = calcTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = greenPlanetSolutions;
        String headerTaxValue = zeroHeaderTax;
        WorkdaySupplierInvoiceReviewPage reviewPage;


        signInToHomePage(testStartPage);
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
    }

    /**
     * This test Covers update supplier scenario with OnDemand/Premise Credential for "Calculate Tax Due to Supplier" Tax Option
     * BP: Quote-Distribute
     * CWD-501
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceCalcTaxDueQuoteDistributeTest() {
        String expectedTaxCalc = noTax;
        String originalTaxOption = calcTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = spectre;
        String headerTaxValue = zeroHeaderTax;

        signInToHomePage(testStartPage);
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
    }

    /**
     * This test Covers update supplier scenario with OnDemand/Premise Credential for "Calculate Self Assessed Tax" Tax Option
     * BP: Quote
     * CWD-498
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceCalcSelfAssessedTaxQuoteTest() {
        String expectedTaxCalc = gpsTax;
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String company = greenPlanetSolutions;
        String postOption = quote;
        String headerTaxValue = zeroHeaderTax;

        signInToHomePage(testStartPage);
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
    }

    /**
     * This test Covers update supplier scenario with OnDemand/Premise Credential for "Calculate Self Assessed Tax" Tax Option
     * BP: Quote- Distribute
     * CWD-500
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceCalcSelfAssessedTaxQuoteDistributeTest() {
        String expectedTaxCalc = spectreTax;
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String company = spectre;
        String postOption = distribute;
        String headerTaxValue = zeroHeaderTax;

        signInToHomePage(testStartPage);
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
    }

    /**
     * This test Covers update supplier scenario with Could Credential for "Calcualte Tax Due to Supplier" Tax Option
     * BP: Quote-Post
     * CWD-510
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdayCloudSupplierInvoiceCalcTaxDueQuotePostTest() {
        String expectedTaxCalc = noTax;
        String originalTaxOption = calcTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = spectre;
        String postOption = post;
        String headerTaxValue = zeroHeaderTax;

        signInToHomePage(testStartPage);

        // Entering cloud credential
        enterValidAttribute(trustedID, supInvInt);
        enterValidAttribute(taxCalcURL, supInvInt);

        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);

        // Entering OnDemand/Premise credential
        enterValidAttributeForOnDemandPremise(trustedID, supInvInt);
        enterValidAttributeForOnDemandPremise(taxCalcURL, supInvInt);
    }

    /**
     * This test Covers update supplier scenario with Could Credential
     * BP: Quote-Post
     * CWD-506
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdayCloudSupplierInvoiceEnterTaxDueNonZeroHeaderQuotePostTest() {
        String expectedTaxCalc = noTax;
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = spectre;
        String postOption = post;
        String headerTaxValue = headerTax;

        signInToHomePage(testStartPage);

        // Entering cloud credential
        enterValidAttribute(trustedID, supInvInt);
        enterValidAttribute(taxCalcURL, supInvInt);

        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);

        // Entering OnDemand/Premise credential
        enterValidAttributeForOnDemandPremise(trustedID, supInvInt);
        enterValidAttributeForOnDemandPremise(taxCalcURL, supInvInt);
    }

    /**
     * This test Covers update supplier Invoice scenario for Billable Tag
     * BP: Quote-Post
     * CWD-558
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceEnterTaxDueNonZeroHeaderQuotePostBillableTest() {
        String expectedTaxCalc = "47.94";
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = selfAssessedTax;
        String company = greenPlanetSolutions;
        String postOption = post;

        //signing in the home page
        signInToHomePage(testStartPage);

        //Fill out Supplier Invoice Form
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValuesBillable(company, americanElectric, originalTaxOption, chairs)
                .clickOnSubmit();

        //Verify that Integration is completed
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();

        //Verify Tax Details
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalc), "Verification Failed For Tax Amount");

        //VerifyBillable Tag
        assertTrue(reviewPage.verifyBillableCheckbox());
    }

    /**
     * This test Covers Supplier Invoice line level custom object Negative Scenario
     * When Tax option is "Enter Tax Due" with no header Tax then no tax should populate in Custom Object Vertex Calculated Tax
     * BP: Quote-Post
     * CWD-558 & 752
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceLineLevelCustomObjectNegativeScenarioTest() {
        String expectedTaxCalc = "9,762.50";
        String postOption = post;
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcSelfAssessed;
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //signing in the home page
        signInToHomePage(testStartPage);

        //Fill out Supplier Invoice Form
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(spectre, americanElectric, originalTaxOption, banners, 1, noTax)
                .clickOnSubmit();

        //Verify Successful Integration and Tax Amount
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);

        //Click on Newly Created Supplier Invoice and verify Line level Vertex Calculated Tax
        assertTrue(reviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        reviewPage.clickOnTaxTab();
        assertTrue(reviewPage.verifyLineLevelCustomObjectVertexCalculatedTax("1", "0"));
    }

    /**
     * This test Covers Supplier Invoice line level custom object Happy path scenario with Post
     * When Tax option is "Enter Tax Due" with no header Tax then no tax should populate in Custom Object Vertex Calculated Tax
     * BP: Post
     * CWD-560
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceLineLevelCustomObjectHappyPathScenarioPostTest() {
        String postOption = post;
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //signing in the home page
        signInToHomePage(testStartPage);

        //Fill out Supplier Invoice Form
        fillSupplierInvoiceFormForUpdatedAttributes(greenPlanetSolutions, americanElectric, originalTaxOption, alcoholSwab, 10, "4").clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Verify Successful Integration and Tax Amount
        assertTrue(reviewPage.verifyUpdatedTaxOption(updatedTaxOption));

        //Click on Newly Created Supplier Invoice and verify Line level Vertex Calculated Tax
        assertTrue(reviewPage.verifyLineLevelCustomObjectVertexCalculatedTax("1", "1.5"));
    }

    /**
     * This test Covers Custom Object update with transaction information as well as import supplier invoice for Calc Tax Due tax option
     * BP: Quote
     * CWD-570
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceIntSysCallTwoCalcTaxDueQuoteTest() {
        String expectedTaxCalc = gpsTax;
        String originalTaxOption = calcTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = greenPlanetSolutions;
        String postOption = quote;
        String headerTaxValue = zeroHeaderTax;

        //signing in the home page
        signInToHomePage(testStartPage);

        //Filling Supplier Invoice Form
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();

        //Verifying Details after Submitting
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
        verifyHeaderLevelCustomObjectTransactionData("QUOTE", "SUCCESS", "");
    }

    /**
     * This test Covers Custom Object update with transaction information as well as import supplier invoice for Calc Tax Due tax option
     * BP: Quote-Post
     * CWD-572
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceIntSysCallTwoCalcTaxDueQuotePostTest() {
        String expectedTaxCalc = noTax;
        String originalTaxOption = calcTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = spectre;
        String postOption = post;
        String headerTaxValue = zeroHeaderTax;

        //signing in the home page
        signInToHomePage(testStartPage);

        //Filling Supplier Invoice Form
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();

        //Verifying Information after Submit
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
        verifyHeaderLevelCustomObjectTransactionData("POST", "SUCCESS", "Yes");
    }

    /**
     * This test Covers Custom Object update with transaction information
     * BP: Quote-Post
     * CWD-573
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceIntSysCallTwoCalcSelfAssessedPostTest() {
        String expectedTaxCalc = noTax;
        String originalTaxOption = calcTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = greenPlanetSolutions;
        String postOption = post;
        String headerTaxValue = zeroHeaderTax;
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //signing in the home page
        signInToHomePage(testStartPage);

        //Filling Supplier invoice Form
        fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue).clickOnSubmit();

        //Verifying information after Update
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
        verifyHeaderLevelCustomObjectTransactionData("POST", "SUCCESS", "Yes");
    }

    /**
     * This test Covers Custom Object update with transaction information
     * BP: Quote-Distribute
     * CWD-574
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceIntSysCallTwoCalcSelfAssessedQuoteDistributeTest() {
        String expectedTaxCalc = "5.79";
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String company = spectre;
        String postOption = distribute;
        String headerTaxValue = zeroHeaderTax;

        //signing in the home page
        signInToHomePage(testStartPage);

        //Filling Supplier invoice form
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();

        //Verifying Information after Submitting
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
        verifyHeaderLevelCustomObjectTransactionData("DISTRIBUTE", "SUCCESS", "Yes");
    }

    /**
     * This test covers Prorating taxes functionality for supplier Invoice with post in BP
     * and VendorChragedTax is more to what vertex calculate
     * BP: Post
     * CWD-578
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceAccrualProratingMoreVendorChargedTaxPostTest() {
        String expectedTaxCalc = noTax;
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = greenPlanetSolutions;
        String postOption = post;
        String headerTaxValue = proratedVendorChargedTax;
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        signInToHomePage(testStartPage);

        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, moreVendorCharged, headerTaxValue)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, moreVendorCharged, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);
        reviewPage.navigateToSupplierInvoiceDetails();
        afterSubmit.switchToTab("Process History");
        afterSubmit.clickOnBusinessProcessLink();
        String fileName = downloadServerLogs(false);
        for (String expectedString : expectedMoreVendorCharged) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test covers Prorating taxes functionality for supplier Invoice with Quote in BP
     * Prorating will not happen when supplier Invoice has Quote in BP
     * BP: Quote
     * CWD-582
     */
    @Test(groups = {"workday_deprecate"}, enabled = false)
    public void workdaySupplierInvoiceAccrualProratingTaxNegativeQuoteTest() {
        String originalTaxOption = enterTaxDue;
        String company = spectre;
        String postOption = post;
        String headerTaxValue = proratedVendorChargedTax;

        signInToHomePage(testStartPage);

        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, moreVendorCharged, headerTaxValue)
                .clickOnSubmit();
        assertTrue(afterSubmit.integrationNotCompletedVerification());
    }

    /**
     * This test covers Prorating taxes functionality for supplier Invoice with post in BP
     * and VendorChargedTax is less than what vertex calculate
     * BP: Post
     * CWD-581
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceAccrualProratingLessVendorChargedTaxPostTest() {
        String postOption = post;
        String updatedTaxOption = calcTaxDue;
        String expectedTaxCalc = "907.21";


        signInToHomePage(testStartPage);

        // Loading XML file with 1 Supplier Invoice and 5 Line Items
        assertTrue(launchSupplierInvoiceLoad("prorate"), "Supplier Invoice Load Integration Failed");

        //Getting recently loaded Supplier Invoice
        getEIBLoadedSupplierInvoice("dpProrateSingleEIB");

        verifyEIBLoadedSupplierInvoiceProratedFieldsValue(updatedTaxOption, expectedTaxCalc, expectedProRateTaxItems);
    }

    /**
     * This test covers Prorating taxes functionality for supplier Invoice with post in BP
     * and VendorChragedTax is equal to what vertex calculate
     * BP: Post
     * CWD-580
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceAccrualProratingEqualVendorChargedTaxPostTest() {
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String company = greenPlanetSolutions;
        String postOption = post;
        String headerTaxValue = "3.51";
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);


        // Signing in Workday
        signInToHomePage(testStartPage);

        fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, moreVendorCharged, headerTaxValue).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, moreVendorCharged, updatedTaxOption, headerTaxValue, headerTaxValue, originalTaxOption);
    }

    /**
     * This test Covers Supplier Invoice Prorate Tax import with only exempted sales Item
     * Tax Option: Enter Tax Due with Header Tax
     * BP: Post
     * CWD-592
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceAccrualProratingImportOnlyExemptSalesItemTest() {
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String tax = "10.11";
        String company = greenPlanetSolutions;
        String postOption = post;
        String[] item = {chairs};


        //Instantiating Class
        reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        // Signing in Workday
        signInToHomePage(testStartPage);

        //Filling out supplier Invoice form for exempted item
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(company, americanElectric, originalTaxOption, item, 1, tax, null)
                .clickOnSubmit();

        //Verify Integration is successful
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Verify Invoice Details
        assertTrue(reviewPage.verifyTaxDetails(tax));
        assertTrue(reviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        reviewPage.clickOnTaxTab();
        assertTrue(reviewPage.verifyLineLevelTaxPaidTax(tax));
        assertTrue(reviewPage.verifyLineLevelSelfAssessedTax("0.00"));
    }

    /**
     * This test Covers Supplier Invoice Prorate Tax import when Vendor charged is less that what Vertex is calculating.
     * It includes some taxable and some non-taxable sales item
     * Tax Option: Enter Tax Due with Header Tax
     * BP: Post
     * CWD-587
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceAccrualProratingImportLessVendorChargedTaxTest() {
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String expectedTaxCalc = "24.04";
        String company = greenPlanetSolutions;
        String postOption = post;
        String headerTaxValue = "3.51";
        String[] item = {alcoholSwab, "Black Ballpoint Pens", "External Hard Drive", "Advil Packets", binderClips, "Desk Lamp", chairs, "Book"};
        String[] expectedLineLevelTaxes = {"0.11", "0.61", "0.13", "0.02", "0.04", "0.24", "0.31", "0.05", "0.00", "0.00", "1.67", "0.28", "17.57", "3.01"};


        // Signing in Workday
        signInToHomePage(testStartPage);
        reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);
        invoiceReviewPage = new WorkdayInvoiceReviewPage(driver);

        //Filling out supplier Invoice form for exempted item
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric, originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();

        //Verify Intergation is successful
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Verify Invoice Details
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue, originalTaxOption);

        //Getting Line Level Tax from Tax Tab and storing in Array
        reviewPage.navigateToSupplierInvoiceDetails();
        reviewPage.clickOnTaxTab();

        //Verify LineLevelTaxDetails
        String[] actualLineLevelTaxes = reviewPage.getAllLineLevelTaxesFromTaxTab();
        assertTrue(invoiceReviewPage.compareLineLevelTaxes(actualLineLevelTaxes, expectedLineLevelTaxes));
    }

    /**
     * This test Covers Supplier Invoice US with multiple exempt sales Items
     * It verifies that last line is being imported with Vendor Charged Tax
     * Tax Option: Enter Tax Due with Header Tax
     * BP: Post
     * CWD-682
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceAccrualMultipleExemptItemsTest() {
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String tax = "10.00";
        String company = greenPlanetSolutions;
        String postOption = post;
        String[] item = {chairs, "Desks", "Projector"};


        //Instantiating Class
        reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        // Signing in Workday
        signInToHomePage(testStartPage);

        //Filling out supplier Invoice form for exempted item
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(company, americanElectric, originalTaxOption, item, 1, tax, null)
                .clickOnSubmit();

        //Verify Integration is successful
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Verify Invoice Details
        assertTrue(reviewPage.verifyTaxDetails(tax));
        assertTrue(reviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        reviewPage.clickOnTaxTab();
        assertTrue(reviewPage.verifyLineLevelTaxPaidTax(tax));
        assertTrue(reviewPage.verifyLineLevelSelfAssessedTax("0.00"));
    }

    /**
     * CWD-410, CWD-477
     * This test Create and Submit a Supplier Invoice QUOTE with Taxable product and validate the tax amount
     * with the test data that get trimmed to 40 Characters in vertex request
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceCUTaxableQuoteCharacterLimitTest() {
        String expectedTaxCalc = "0.22";
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceForm(spectre, americanElectric, selfAssessedTax, alcoholSwab, noTax)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
    }

    /**
     * This test Create and Submit a Supplier Invoice QUOTE with Taxable product and validate the tax amount
     * It also Validates types of post option and Vertex Request XML
     * CWD-203
     */
    @Test(groups = {"workday_regression"})
    public void workdayValidateSupplierInvoiceQuoteSelfAssessedRequestXMLTest() {
        String expectedTaxCalc = "0.22";
        String postOptionToVerify = "PostOption: POST";
        String postOption = "post";

        signInToHomePage(testStartPage);

        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(spectre, americanElectric, selfAssessedTax, alcoholSwab, 1, noTax)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
        String invNo = afterSubmit.getInvoiceNumber();
        afterSubmit.switchToTab("Process");
        afterSubmit.clickOnBusinessProcessLink();
        afterSubmit.navigateToServerLogFile();
        String fileName = afterSubmit.clickOnFirstServerLog();
        VertexFileUtils.waitForFileToExist(VertexPage.DOWNLOAD_DIRECTORY_PATH + "//" + fileName, 10);
        assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, postOptionToVerify));
    }


    /**
     * this test Create and Submit a Supplier Invoice POST with Non-taxable Purchase class
     * CWD-200
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoicePostCutNonTaxablePurchaseCodeTest() {
        String expectedTaxCalc = "0";
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceForm(spectre, americanElectric, selfAssessedTax, chairs, noTax)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
    }

    /**
     * this test Create and Submit a Supplier Invoice POST with Ship to address of Non-taxable State
     * CWD-201
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoicePOSTCUTNonTaxableUSStateTest() {
        String expectedTaxCalc = "0";
        String address = "Wilmington";
        String postOption = "post";
        String[] items = {alcoholSwab};

        signInToHomePage(testStartPage);
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(spectre, americanElectric, selfAssessedTax, items, 1, noTax, address)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
    }

    /**
     * this test Create and Submit a Supplier Invoice QUOTE with Taxable product and validate the tax amount
     * CWD-199
     */
    @Test(groups = {"workday_smoke"})
    public void workdaySupplierInvoiceCUTTaxablePostTest() {
        String expectedTaxCalc = "9,762.50";
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceForm(spectre, americanElectric, selfAssessedTax, banners, noTax)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
    }

    /**
     * This test case verifies that our integration works as expected when we do not enter the Ship-to Address
     * in the header level and instead only include that information at the line level
     * CWD-954
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceNoHeaderShipToTest() {
        // Test Data
        String updatedTaxOption = calcTaxDue;
        String tax = "1.50";
        String company = greenPlanetSolutions;
        String originalTaxOption = calcTaxDue;
        String item = alcoholSwab;
        String address = usAddress;

        //Instantiating Class
        reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling out supplier Invoice form for exempted item
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormNoHeaderShipTo(company, americanElectric, originalTaxOption, item, tax, 10, address)
                .clickOnSubmit();

        //Verify Integration is successful
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Verify Invoice Details
        assertTrue(reviewPage.verifyTaxDetails(tax));
        assertTrue(reviewPage.verifyUpdatedTaxOption(updatedTaxOption));

    }

    /**
     * This test verifies that we correctly set the control total to 0.00 during our pre-process
     * CWD-971
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceValidateControlTotalZero() {
        // Test Data
        String expectedTaxCalc = "0.60";
        String originalTaxOption = enterTaxDue;
        String company = greenPlanetSolutions;
        String headerTaxValue = noTax;
        String controlTotal = "10.00";
        String[] preProcessLog = {"<wd:Control_Amount_Total>10</wd:Control_Amount_Total>",
                "<wd:Control_Amount_Total>0</wd:Control_Amount_Total>"};

        // Filling out Supplier Invoice form with Control Total Value
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormWithControlTotal(company, americanElectric, originalTaxOption, alcoholSwab, headerTaxValue, 4, controlTotal)
                .clickOnSubmit();

        // Verify Integration is successful
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);

        // Verify server logs for original process
        afterSubmit.switchToTab("Process");
        String fileName = downloadPreprocessServerLogs();
        for (String expectedString : preProcessLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test verifies that we correctly stored the control total in the custom object during our pre-process
     * CWD-966
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceValidateControlTotalStored() {
        // Test Data
        String expectedTaxCalc = "1.00";
        String originalTaxOption = enterTaxDue;
        String company = greenPlanetSolutions;
        String headerTaxValue = "1.00";
        String controlTotal = "11.00";
        String vertexControlTotalAmount = "11";
        String[] preProcessLog = {"<wd:Control_Amount_Total>11</wd:Control_Amount_Total>",
                "<wd:Control_Amount_Total>0</wd:Control_Amount_Total>"};
        reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        // Filling out Supplier Invoice form with Control Total Value
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormWithControlTotal(company, americanElectric, originalTaxOption, alcoholSwab, headerTaxValue, 4, controlTotal)
                .clickOnSubmit();

        // Verify Integration is successful
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);

        // Check Custom Object for VertexControlTotalAmount
        reviewPage.navigateToCustomObject();
        reviewPage.verifyVertexControlTotalAmount(vertexControlTotalAmount);
        reviewPage.navigateToSupplierInvoiceDetails();

        // Verify server logs for original process
        afterSubmit.switchToTab("Process");
        String fileName = downloadPreprocessServerLogs();
        for (String expectedString : preProcessLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test verifies that we are correctly distributing the tax across multiple products and doing so in a way
     * that is proportional to the cost of the items and takes into account the taxability of the items on supplier
     * invoices where we enter the tax due to the supplier
     * CWD-1082
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceValidateTaxAllocationFix() {
        // Test Data
        String expectedTaxCalc = "10.00";
        String originalTaxOption = enterTaxDue;
        String company = greenPlanetSolutions;
        String headerTaxValue = "10.00";
        String[] taxBreakdown = {"0.38", "0.34", "0.71", "0.00", "8.57"};
        WorkdaySupplierInvoicePage supplierInvoice = new WorkdaySupplierInvoicePage(driver);
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        // Filling out Supplier Invoice form with Control Total Value
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(company, americanElectric, originalTaxOption, item, 5, headerTaxValue, null)
                .clickOnSubmit();

        // Verify Integration is successful
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);

        // Verify Correct Tax Amounts and Allocation
        reviewPage.clickOnTaxTab();
        assertTrue(reviewPage.verifyTaxBreakdown(taxBreakdown));
    }


    /**
     * This test verifies that when additional field are entered on a supplier invoice that they are not wiped out when
     * the integration calls the pre-process
     * CWD-1109
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceValidateAdditionalFields() {
        // Test Data
        String originalTaxOption = enterTaxDue;
        String company = greenPlanetSolutions;
        String headerTaxValue = "1.00";
        String[] additionalFields = {"field 1", "field 2", "field 3", "field 4"};
        String[] preProcessLog = {"<wd:Attribute_Value>field 1</wd:Attribute_Value>",
                "<wd:Attribute_Value>field 2</wd:Attribute_Value>", "<wd:Attribute_Value>field 3</wd:Attribute_Value>",
                "<wd:Attribute_Value>field 4</wd:Attribute_Value>"};
        WorkdaySupplierInvoicePage supplierInvoice = new WorkdaySupplierInvoicePage(driver);
        reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        // Filling out Supplier Invoice form with Control Total Value
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormWithAdditionalFields(company, americanElectric, originalTaxOption, alcoholSwab, headerTaxValue, 4, additionalFields)
                .clickOnSubmit();

        // Verify Integration is successful
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        // Validate field entries
        reviewPage.clickOnAdditionalFieldsTab();
        assertTrue(reviewPage.verifyAdditionalFields(additionalFields));

        // Verify server logs for original process
        afterSubmit.switchToTab("Process");
        String fileName = downloadPreprocessServerLogs();
        for (String expectedString : preProcessLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test validates that we are sending vendor information at the line level and not the header level
     * when calling Vertex
     * CWD-1222
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceValidateLineLevelVendorInformationTest() {
        // Test Data
        String expectedTaxCalc = "5.22";
        String originalTaxOption = calcSelfAssessed;
        String company = greenPlanetSolutions;
        String headerTaxValue = "0.00";
        String[] taxBreakdown = {"0.19", "0.18", "0.38", "0.00", "4.47"};
        String[] lineLevelVendorInfo = {"<vtx:Vendor>"}; // check the level of nesting
        WorkdaySupplierInvoicePage supplierInvoice = new WorkdaySupplierInvoicePage(driver);
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        // Filling out Supplier Invoice form with Control Total Value
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(company, americanElectric, originalTaxOption, item, 5, headerTaxValue, null)
                .clickOnSubmit();

        // Verify Integration is successful
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);

        // Verify Correct Tax Amounts and Allocation
        reviewPage.clickOnTaxTab();
        assertTrue(reviewPage.verifyTaxBreakdown(taxBreakdown));

        // Verify server logs for original process
        afterSubmit.switchToTab("Process");
        String fileName = downloadServerLogs(true);
        for (String expectedString : lineLevelVendorInfo) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }
}
