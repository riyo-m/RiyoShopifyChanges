package com.vertex.quality.connectors.workday.tests.ui.USA.SupplierInvoice;

import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReportPage;
import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.pages.WorkdaySupplierInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class WorkdayAdjustedInvoiceSecondTests extends WorkdayBaseTest {

    String calcSelfAssessed = "Calculate Self-Assessed Tax";
    String enterTaxDue = "Enter Tax Due to Supplier";
    String calcTaxDue = "Calculate Tax Due to Supplier";
    final int updatedQty = 5;
    String [] expectedStringsForAdjustedInv = {"taxDate","AccrualRequest","AdjustmentFlag: 1","PostOption: POST"};

    /**
     * this test Create and Submit a Supplier Invoice and then create adjustment on the same invoice
     * and verify the negative tax amount and the integration is completed
     * Tax option: Enter Tax Due
     * Input Tax: 22.51
     * Adjustment Down (Decrease Liability)
     * CWD-690
     */
    @Test(groups = {"workday_regression"})
    public void adjustmentDownEnterTaxDueTest() {
        //Test Data
        String expectedTaxCalc = "70.48";
        String[] expectedTax = {"22.19", "0.00", "17.67", "0.19", "30.43"};
        String[] expectedTaxAdjustedInvoice = {"1.11"};
        String enteredTaxDue = "22.51";
        String expectedAdjustedTaxCalc = "(1.11)";
        String totalAdjustedAmount = "(12.50)";
        String[] expectedAdjustedItems = {alcoholSwab};
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String[] item = {alcoholSwab, "Chairs", "iPhone", "Alcohol Wipe Pads", "External Hard Drive"};

        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage;

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Sign-in
        signInToHomePage(testStartPage);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GPS", "American",
                originalTaxOption, item, 100, enteredTaxDue, null).clickOnSubmit();

        //Validate form completed successfully after submit
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
     * this test Create and Submit a Supplier Invoice and then create adjustment on the same invoice
     * and verify the negative tax amount and the integration is completed
     * Tax option: Enter Tax Due
     * Input Tax: 22.51
     * Adjustment Up (Increase Liability)
     * CWD-782
     */
    @Test(groups = {"workday_regression"})
    public void adjustmentUpEnterTaxDueTest() {
        //Test Data
        String expectedTaxCalc = "47.66";
        String[] expectedTax = {"15.00", "0.00", "11.94", "0.14", "20.58"};
        String[] expectedTaxAdjustedInvoice = {"0.75"};
        String tax = "22.51";
        String expectedAdjustedTaxCalc = "0.75";
        String totalAdjustedAmount = "12.50";
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
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GPS", "American",
                originalTaxOption, item, 100, tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        assertTrue(supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject(tax));
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
     * this test Create and Submit a Supplier Invoice and then create adjustment on the same invoice
     * and verify the negative tax amount and the integration is completed
     * Tax Option: Calc Self Assessed Tax
     * CWD-689
     */
    @Test(groups = {"workday_regression"})
    public void adjustmentDownCalcSelfAssessedTest() {
        //Test Data
        String expectedTaxCalc = "47.66";
        String[] expectedTax = {"15.00", "0.00", "11.94", "0.14", "20.58"};
        String[] expectedTaxAdjustedInvoice = {"0.75"};
        String tax = "0";
        String expectedAdjustedTaxCalc = "(0.75)";
        String totalAdjustedAmount = "(12.50)";
        String[] expectedAdjustedItems = {alcoholSwab};
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String[] item = {alcoholSwab, "Chairs", "iPhone", "Alcohol Wipe Pads", "External Hard Drive"};
        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage;

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Sign-in
        signInToHomePage(testStartPage);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GPS", "American",
                originalTaxOption, item, 100, tax, null).clickOnSubmit();
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
     * this test Create and Submit a Supplier Invoice and then create adjustment on the same invoice
     * and verify the negative tax amount and the integration is completed
     * Tax Option: Calc Tax Due to supplier
     * CWD-691
     */
    @Test(groups = {"workday_regression"})
    public void adjustmentDownCalcTaxDueTest() {
        //Test Data
        String expectedTaxCalc = "47.66";
        String[] expectedTax = {"15.00", "0.00", "11.94", "0.14", "20.58"};
        String[] expectedTaxAdjustedInvoice = {"0.75"};
        String tax = "0";
        String expectedAdjustedTaxCalc = "(0.75)";
        String totalAdjustedAmount = "(12.50)";
        String[] expectedAdjustedItems = {alcoholSwab};
        String originalTaxOption = calcTaxDue;
        String updatedTaxOption = calcTaxDue;
        String[] item = {alcoholSwab, "Chairs", "iPhone", "Alcohol Wipe Pads", "External Hard Drive"};
        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage;

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Sign-in
        signInToHomePage(testStartPage);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GPS", "American",
                originalTaxOption, item, 100, tax, null).clickOnSubmit();
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
     * this test Create and Submit a Supplier Invoice and then create adjustment on the same invoice
     * by back dating adj on previous dated
     * and verify the negative tax amount and the integration is completed
     * Tax option: Calc Self Assessed
     * Adjustment Down (Decrease Liability)
     * CWD-787
     */
    @Test(groups = {"workday_regression"})
    public void adjustmentDownBackDateSelfAssessedTest() {
        //Test Data
        String expectedTaxCalc = "47.66";
        String[] expectedTax = {"15.00", "0.00", "11.94", "0.14", "20.58"};
        String[] expectedTaxAdjustedInvoice = {"0.75"};
        String tax = "0";
        String expectedAdjustedTaxCalc = "(0.75)";
        String totalAdjustedAmount = "(12.50)";
        String[] expectedAdjustedItems = {alcoholSwab};
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String[] item = {alcoholSwab, "Chairs", "iPhone", "Alcohol Wipe Pads", "External Hard Drive"};
        WorkdayInvoiceReportPage invoiceReportPage = new WorkdayInvoiceReportPage(driver);
        String[] expectedStringsForBackDateAdjustedInv = {"taxDate", "AccrualRequest", "AdjustmentFlag: 1", "PostOption: POST",
                invoiceReportPage.getYesterdaysDateyyyymmdd()};
        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage;

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Sign-in
        signInToHomePage(testStartPage);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GPS", "American",
                originalTaxOption, item, 100, tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("0");
        verifyHeaderLevelCustomObjectTransactionData("POST", "SUCCESS", "Yes");
        supplierInvoiceReviewPage.navigateToSupplierInvoiceDetails();

        afterSubmit = fillSupplierInvoiceAdjustmentForm(updatedQty, true, false).clickOnSubmit();

        //Verify Successful Integration and Tax Amount
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedAdjustedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyTotalAdjustedAmount(totalAdjustedAmount));
        assertTrue(afterSubmit.verifyProductLineTax(expectedAdjustedItems, expectedTaxAdjustedInvoice));
        validateServerLogStringsSupInv(afterSubmit, expectedStringsForBackDateAdjustedInv);
    }
}
