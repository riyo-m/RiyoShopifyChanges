package com.vertex.quality.connectors.workday.tests.ui.Canada.SupplierInvoice;

import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.pages.WorkdaySupplierInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * This test class is to verify "Tax Amount", third party tax calculation status after Supplier Invoice is submitted
 * It also verifies that while changing the Customer Invoice reversal request is only being called once
 *
 * @author jdillman
 */

public class WorkdayAdjustedInvoiceTests extends WorkdayBaseTest {

    String calcSelfAssessed = "Calculate Self-Assessed Tax";
    String enterTaxDue = "Enter Tax Due to Supplier";
    String calcTaxDue = "Calculate Tax Due to Supplier";
    final int updatedQty = 5;
    String[] expectedStringsForAdjustedInv = {"taxDate", "AccrualRequest", "AdjustmentFlag: 1", "PostOption: POST"};


    /**
     * This test is to validate the ability to perform an invoice adjustment with decreased liability in the
     * enter tax due scenario
     * CWD-871
     */
    @Test(groups = {"workday_regression"})
    public void adjustmentDownEnterTaxDueTest() {
        //Test Data
        String expectedTaxCalc = "96.22";
        String[] expectedTax = {"30.29", "0.00", "24.11", "0.27", "41.55"};
        String[] expectedTaxAdjustedInvoice = {"1.16"};
        String headerTax = "22.51";
        String totalAdjustedAmount = "(16.57)";
        String[] expectedAdjustedItems = {alcoholSwab};
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String[] item = {alcoholSwab, chairs, iPhone, alcoholWipePads, hardDrive};

        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage;

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Sign-in
        signInToHomePage(testStartPage);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Bell Canada",
                originalTaxOption, item, 100, headerTax, null).clickOnSubmit();

        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("0");
        verifyHeaderLevelCustomObjectTransactionData("POST", "SUCCESS", "Yes");
        supplierInvoiceReviewPage.navigateToSupplierInvoiceDetails();

        afterSubmit = fillSupplierInvoiceAdjustmentForm(updatedQty, true, true).clickOnSubmit();

        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Verify Successful Integration and Tax Amount
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(supplierInvoiceReviewPage.verifyTotalAdjustedAmount(totalAdjustedAmount));
        assertTrue(afterSubmit.verifyProductLineTax(expectedAdjustedItems, expectedTaxAdjustedInvoice));
        validateServerLogStringsSupInv(afterSubmit, expectedStringsForAdjustedInv);
    }

    /**
     * This test is to validate the ability to perform an invoice adjustment with increased liability in the
     * enter tax due scenario
     * CWD-866
     */
    @Test(groups = {"workday_regression"})
    public void adjustmentUpEnterTaxDueTest() {
        //Test Data
        String expectedTaxCalc = "96.22";
        String[] expectedTax = {"30.29", "0.00", "24.11", "0.27", "41.55"};
        String[] expectedTaxAdjustedInvoice = {"1.16"};
        String headerTax = "22.51";
        String expectedAdjustedTaxCalc = "1.16";
        String totalAdjustedAmount = "16.57";
        String[] expectedAdjustedItems = {alcoholSwab};
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String[] item = {alcoholSwab, chairs, iPhone, alcoholWipePads, hardDrive};

        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage;

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Sign-in
        signInToHomePage(testStartPage);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Bell Canada",
                originalTaxOption, item, 100, headerTax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        assertTrue(supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject(headerTax));
        verifyHeaderLevelCustomObjectTransactionData("POST", "SUCCESS", "Yes");
        supplierInvoiceReviewPage.navigateToSupplierInvoiceDetails();

        afterSubmit = fillSupplierInvoiceAdjustmentForm(updatedQty, false, true).clickOnSubmit();

        //Verify Successful Integration and Tax Amount
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedAdjustedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(supplierInvoiceReviewPage.verifyTotalAdjustedAmount(totalAdjustedAmount));
        assertTrue(afterSubmit.verifyProductLineTax(expectedAdjustedItems, expectedTaxAdjustedInvoice));
        validateServerLogStringsSupInv(afterSubmit, expectedStringsForAdjustedInv);

    }

    /**
     * This test is to validate the ability to perform an invoice change with decreased liability in the
     * enter tax due scenario
     * CWD-870
     */
    @Test(groups = {"workday_regression"})
    public void adjustmentDownCalcSelfAssessedTest() {
        //Test Data
        String expectedTaxCalc = "0";
        String[] expectedTax = {"23.20", "0.00", "18.47", "0.21", "31.83"};
        String[] expectedTaxAdjustedInvoice = {"1.16"};
        String headerTax = "0.00";
        String expectedAdjustedTaxCalc = "(1.16)";
        String totalAdjustedAmount = "(16.57)";
        String[] expectedAdjustedItems = {alcoholSwab};
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String[] item = {alcoholSwab, chairs, iPhone, alcoholWipePads, hardDrive};
        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage;

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Sign-in
        signInToHomePage(testStartPage);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Bell Canada",
                originalTaxOption, item, 100, headerTax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("0");
        verifyHeaderLevelCustomObjectTransactionData("POST", "SUCCESS", "Yes");
        supplierInvoiceReviewPage.navigateToSupplierInvoiceDetails();

        afterSubmit = fillSupplierInvoiceAdjustmentForm(updatedQty, true, true).clickOnSubmit();

        //Verify Successful Integration and Tax Amount
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedAdjustedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyTotalAdjustedAmount(totalAdjustedAmount));
        assertTrue(afterSubmit.verifyProductLineTax(expectedAdjustedItems, expectedTaxAdjustedInvoice));
        validateServerLogStringsSupInv(afterSubmit, expectedStringsForAdjustedInv);
    }

    /**
     * This test is to validate the ability to perform an invoice change with increased liability in the
     * enter tax due scenario
     * CWD-865
     */
    @Test(groups = {"workday_regression"})
    public void adjustmentUpCalcSelfAssessedTest() {
        //Test Data
        String expectedTaxCalc = "0";
        String[] expectedTax = {"23.20", "0.00", "18.47", "0.21", "31.83"};
        String[] expectedTaxAdjustedInvoice = {"1.16"};
        String headerTax = "0.00";
        String expectedAdjustedTaxCalc = "1.16";
        String totalAdjustedAmount = "16.57";
        String[] expectedAdjustedItems = {alcoholSwab};
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String[] item = {alcoholSwab, chairs, iPhone, alcoholWipePads, hardDrive};
        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage;

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Sign-in
        signInToHomePage(testStartPage);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Bell Canada",
                originalTaxOption, item, 100, headerTax, null).clickOnSubmit();

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("0");
        verifyHeaderLevelCustomObjectTransactionData("POST", "SUCCESS", "Yes");
        supplierInvoiceReviewPage.navigateToSupplierInvoiceDetails();

        afterSubmit = fillSupplierInvoiceAdjustmentForm(updatedQty, false, true).clickOnSubmit();

        //Verify Successful Integration and Tax Amount
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedAdjustedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyTotalAdjustedAmount(totalAdjustedAmount));
        assertTrue(afterSubmit.verifyProductLineTax(expectedAdjustedItems, expectedTaxAdjustedInvoice));
        validateServerLogStringsSupInv(afterSubmit, expectedStringsForAdjustedInv);
    }

    /**
     * This test covers Supplier Invoice Change scenario for Enter Tax Due option
     * BP: Post, No header Tax
     * CWD-879
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceChangeUSEnterTaxDuePostTest() {

        final String expectedTaxCalc = "73.71";
        final String expectedTaxCalcUpdatedInvoice = "51.67";
        String headerTax = "0";
        String[] expectedTax = {"23.20", "0.00", "18.47", "0.21", "31.83"};
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = selfAssessedTax;
        String[] item = {alcoholSwab, chairs, iPhone, alcoholWipePads, hardDrive};
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);
        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        // Sign in the home page
        signInToHomePage(testStartPage);

        //Fill out Full Supplier Invoice Form and submit it
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Bell Canada",
                originalTaxOption, item, 100, headerTax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("0");
        verifyHeaderLevelCustomObjectTransactionData("POST", "SUCCESS", "Yes");
        supplierInvoiceReviewPage.navigateToSupplierInvoiceDetails();

        //Changing the invoice and submitting it
        fillSupplierInvoiceChangeForm(updatedQty).clickOnSubmit();

        //Clicks on newly created invoice and verifies all the invoice details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalcUpdatedInvoice));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));


    }

    /**
     * This test covers Supplier Invoice Change scenario for Calculate Self-Assessed Tax option
     * BP: Post, No header Tax
     * CWD-880
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceChangeUSCalcSelfAssessedPostTest() {

        final String expectedTaxCalc = "73.71";
        final String expectedTaxCalcUpdatedInvoice = "0";
        String tax = "0.00";
        String[] expectedTax = {"23.20", "0.00", "18.47", "0.21", "31.83"};
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String[] item = {alcoholSwab, chairs, iPhone, alcoholWipePads, hardDrive};
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);
        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        // Sign in the home page
        signInToHomePage(testStartPage);

        //Fill out Full Supplier Invoice Form and submit it
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Bell Canada",
                originalTaxOption, item, 100, tax, null).clickOnSubmit();

        //Verify Successful integration and Tax details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("0");
        verifyHeaderLevelCustomObjectTransactionData("POST", "SUCCESS", "Yes");
        supplierInvoiceReviewPage.navigateToSupplierInvoiceDetails();

        //Changing the invoice and submitting it
        fillSupplierInvoiceChangeForm(updatedQty).clickOnSubmit();

        //Clicks on newly created invoice and verifies all the invoice details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalcUpdatedInvoice));

    }

}
