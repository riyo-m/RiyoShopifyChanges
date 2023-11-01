package com.vertex.quality.connectors.workday.tests.ui.Canada.SupplierInvoice;

import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.pages.WorkdaySupplierInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * This Test class contains tests covering CAN to CAN transactions
 */
public class WorkdayOriginalInvoiceTests extends WorkdayBaseTest {

    String enterTaxDue = "Enter Tax Due to Supplier";
    String calcTaxDue = "Calculate Tax Due to Supplier";
    String calcSelfAssessed = "Calculate Self-Assessed Tax";
    WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage;

    @BeforeMethod
    public void beforeTest() {
        signInToHomePage(testStartPage);
    }

    /**
     * this test Create and Submit a Supplier Invoice for Canada to Canada Transaction in which we are charging more
     * Vendor charged tax so Province jurisdiction is returning no tax, only the Canada Jurisdiction is returning tax
     * with recoverability
     * CWD-607
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceCanadaToCanadaQuotePostMoreVendorChargedTaxTest() {
        String expectedTaxCalc = "200.00";
        String tax = "200";
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String[] item = {alcoholSwab};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Bell Canada", originalTaxOption, item, 100, tax, null)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Validate tax option, tax amount
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
    }

    /**
     * This test is for the Quebec Bug fix, It creates supplier Invoice for Can-Can transaction with Quebec Ship-To Add
     * Line Level custom object wasn't getting updated with correct tax rates because response filtering was incorrect
     * CWD-648
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceCanadaToCanadaPostLessVendorChargedQuebecBugFixTest() {
        //Test Data
        String expectedTaxCalc = "5.50";
        String tax = "5.50";
        String address = "Quebec";
        String originalTaxOption = enterTaxDue;
        String[] item = {alcoholSwab, "chairs"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Bell Canada", originalTaxOption, item, 100, tax, address)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
        assertTrue(supplierInvoiceReviewPage.verifyLineLevelCustomObjectVertexCalculatedTax("1", "5.5"));
        supplierInvoiceReviewPage.navigateToSupplierInvoiceFromCustomObject();
        assertTrue(supplierInvoiceReviewPage.verifyLineLevelCustomObjectVertexCalculatedTax("2", "5.5"));
    }

    /**
     * This Test validates Accrual (Reverse Charged) scenario for CAN to CAN transaction
     * Tax option: Enter Tax Due
     * Input Tax: 0
     * CWD-633
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceCanTOCanReverseChargedEnterTaxDueNoInputTaxTest() {
        //Test Data
        String expectedTaxCalc = "23.20";
        String[] expectedTax = {"23.20", "0.00"};
        String headerTax = "0";
        String address = "Vancouver";
        String originalTaxOption = enterTaxDue;
        String[] item = {alcoholSwab, "Chairs"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Bell Canada",
                originalTaxOption, item, 100, headerTax, address).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));

    }

    /**
     * This Test validates Accrual (Reverse Charged) scenario for CAN to CAN transaction
     * Tax option: Calculate Tax Due to Supplier
     * Input Tax: 0
     * CWD-634
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceCanTOCanReverseChargedCalcTaxDueTest() {
        //Test Data
        String expectedTaxCalc = "23.20";
        String[] expectedTax = {"23.20", "0.00"};
        String headerTax = "0";
        String address = "Vancouver";
        String originalTaxOption = calcTaxDue;
        String[] item = {alcoholSwab, chairs};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Bell Canada",
                originalTaxOption, item, 100, headerTax, address).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
    }

    /**
     * This Test validates Accrual (Reverse Charged) scenario for CAN to CAN transaction
     * Tax option: Calculate Self Assessed Tax
     * Input Tax: 0
     * CWD-631
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceCanTOCanReverseChargedCalcSelfAssessedTest() {
        //Test Data
        String expectedTaxCalc = "23.20";
        String[] expectedTax = {"23.20", "0.00"};
        String headerTax = "0";
        String address = "Vancouver";
        String originalTaxOption = calcSelfAssessed;
        String[] item = {alcoholSwab, "Chairs"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Bell Canada",
                originalTaxOption, item, 100, headerTax, address).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
    }

    /**
     * This Test validates Only Exempt for CAN to CAN transaction
     * Tax option: Enter Tax Due
     * Input Tax: 10
     * CWD-646
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceCanToCanExemptItemsEnterTaxDueWithInputTaxTest() {
        //Test Data
        String expectedTaxCalc = "10.00";
        String[] expectedTax = {"9.83", "0.17"};
        String headerTax = "10";
        String address = "Vancouver";
        String originalTaxOption = enterTaxDue;
        String[] items = {"Desks", "Chairs"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Bell Canada",
                originalTaxOption, items, 100, headerTax, address)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(items, expectedTax));
        afterSubmit.clickOnClose();
        assertTrue(supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject(headerTax));
        verifyHeaderLevelCustomObjectTransactionData("POST", "SUCCESS", "Yes");
    }

    /**
     * This Test validates Full recoverability scenario for CAN to CAN transaction
     * when Vendor charged tax is less than what vertex is calculating
     * Tax option: Enter Tax Due
     * Input Tax: 100
     * CWD-639
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceCanToCanFullRecovEnterTaxDueWithInputTaxLessChargedTaxTest() {
        //Test Data
        String expectedTaxCalc = "1,982.28";
        String[] expectedTax = {"1,945.03", "0.00", "33.52", "3.28", "0.45"};
        String headerTax = "100";
        String address = "Vancouver";
        String originalTaxOption = enterTaxDue;
        String[] items = {"iPhone", "Chairs", "External Hard Drive", "Ergonomic Keyboard", "Binder Clips"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Bell Canada",
                originalTaxOption, items, 100, headerTax, address)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(items, expectedTax));
    }

    /**
     * This Test validates Full recoverability scenario for CAN to CAN transaction
     * when Vendor charged tax is More than what vertex is calculating
     * Tax option: Enter Tax Due
     * Input Tax: 2000
     * CWD-644
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceCanToCanFullRecovEnterTaxDueWithInputTaxMoreChargedTaxTest() {
        //Test Data
        String expectedTaxCalc = "3,226.76";
        String[] expectedTax = {"3,166.13", "0.00", "54.57", "5.33", "0.73"};
        String headerTax = "2,100.00";
        String address = "Vancouver";
        String originalTaxOption = enterTaxDue;
        String[] items = {"iPhone", "Chairs", "External Hard Drive", "Ergonomic Keyboard", "Binder Clips"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Bell Canada",
                originalTaxOption, items, 100, headerTax, address)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(items, expectedTax));
    }

    /**
     * This Test is to validate the bug fix of Incorrect tax types
     * Tax option: Enter Tax Due
     * Input Tax: 5
     * CWD-674
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceCanToCanIncorrectTaxTypeBugFixTest() {
        //Test Data
        String expectedTaxCalc = "28.20";
        String headerTax = "5";
        String address = "Vancouver";
        String originalTaxOption = enterTaxDue;
        String[] items = {alcoholSwab};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Bell Canada",
                originalTaxOption, items, 100, headerTax, address)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
        afterSubmit.switchToTab("Tax");
        assertTrue(supplierInvoiceReviewPage.verifyLineLevelSelfAssessedTax("23.20"));

    }

    /**
     * This test Covers Supplier Invoice CAN-CAN with multiple exempt sales Items
     * It verifies that last line is being imported with Vendor Charged Tax
     * Tax Option: Enter Tax Due with Header Tax
     * BP: Post
     * CWD-683
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceCanToCanAllMultipleExemptItemsTest() {
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String headerTax = "10.00";
        String address = "Vancouver";

        String[] expectedTax = {"1.76", "0.99", "7.25"};
        String[] item = {chairs, "Desks", "Projector"};


        //Instantiating Class
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling out supplier Invoice form for exempted item
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Bell Canada", originalTaxOption, item, 1, headerTax, address)
                .clickOnSubmit();

        //Verify Integration is successful
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);

        //Verify Invoice Details
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(headerTax));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        supplierInvoiceReviewPage.clickOnTaxTab();
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();

        //Verify Vendor charged Tax
        assertTrue(supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("10"));
        verifyHeaderLevelCustomObjectTransactionData("POST", "SUCCESS", "Yes");
    }

    /**
     * This test verifies that we are correctly distributing the tax across multiple products and doing so in a way
     * that is proportional to the cost of the items and takes into account the taxability of the items on supplier
     * invoices where we enter the tax due to the supplier and works for Canada addresses
     * CWD-1083
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceValidateTaxAllocationFix() {
        // Test Data
        String expectedTaxCalc = "10.38";
        String originalTaxOption = enterTaxDue;
        String headerTax = "10.00";
        String address = "Vancouver";
        String[] item = {chairs, alcoholSwab, alcoholWipePads, binderClips, laptopPowerAdaptor};
        String[] taxBreakdown = {"0.40", "0.35", "0.73", "0.00", "8.90"};
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        // Filling out Supplier Invoice form with Control Total Value
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Bell Canada", originalTaxOption, item, 5, headerTax, address)
                .clickOnSubmit();

        // Verify Integration is successful
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);

        // Verify Correct Tax Amounts and Allocation
        reviewPage.clickOnTaxTab();
        assertTrue(reviewPage.verifyTaxBreakdown(taxBreakdown));
    }

}
