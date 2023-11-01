package com.vertex.quality.connectors.workday.tests.ui.IntraCountry.SupplierInvoice;

import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.pages.WorkdaySupplierInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * This test class is to verify "Tax Amount", third party tax calculation status after Supplier Invoice is submitted
 * It also verifies that while changing the Customer Invoice reversal request is only being called once
 * @author jdillman
 */
public class WorkdayAdjustedInvoiceTests extends WorkdayBaseTest {
    String calcSelfAssessed = "Calculate Self-Assessed Tax";
    String enterTaxDue = "Enter Tax Due to Supplier";
    String calcTaxDue = "Calculate Tax Due to Supplier";
    final int updatedQty = 5;
    String [] expectedStringsForAdjustedInv = {"taxDate","AccrualRequest","AdjustmentFlag: 1","PostOption: POST"};


    /**
     * This test is to validate the ability to perform an invoice adjustment with decreased liability in the
     * enter tax due scenario
     * CWD-872
     */
    @Test(groups = { "workday_regression"} )
    public void adjustmentDownEnterTaxDueTest()
    {
        //Test Data
        String expectedTaxCalc = "12.25";
        String [] expectedTax = {"12.25","0.00"};
        String [] expectedTaxAdjustedInvoice = {"1.50"};
        String tax = "12.25";
        String expectedAdjustedTaxCalc = "(1.50)";
        String totalAdjustedAmount = "(30.00)";
        String [] expectedAdjustedItems = {"Black Ballpoint Pens"};
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String [] item = {"Black Ballpoint Pens","Chairs"};

        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage;

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Sign-in
        signInToHomePage(testStartPage);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", americanElectric,
                originalTaxOption, item,100,tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("0");
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");
        supplierInvoiceReviewPage.navigateToSupplierInvoiceDetails();

        afterSubmit = fillSupplierInvoiceAdjustmentForm(updatedQty,true,true).clickOnSubmit();

        //Verify Successful Integration and Tax Amount
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedAdjustedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyTotalAdjustedAmount(totalAdjustedAmount));
        assertTrue(afterSubmit.verifyProductLineTax(expectedAdjustedItems, expectedTaxAdjustedInvoice));
        validateServerLogStringsSupInv(afterSubmit,expectedStringsForAdjustedInv);
    }

    /**
     * This test is to validate the ability to perform an invoice adjustment with increased liability in the
     * enter tax due scenario
     * CWD-873
     */
    @Test(groups = { "workday_regression"} )
    public void adjustmentUpEnterTaxDueTest()
    {
        //Test Data
        String expectedTaxCalc = "12.25";
        String [] expectedTax = {"12.25","0.00"};
        String [] expectedTaxAdjustedInvoice = {"1.50"};
        String tax = "12.25";
        String expectedAdjustedTaxCalc = "1.50";
        String totalAdjustedAmount = "30.00";
        String [] expectedAdjustedItems = {"Black Ballpoint Pens"};
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String [] item = {"Black Ballpoint Pens","Chairs"};

        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage;

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Sign-in
        signInToHomePage(testStartPage);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", americanElectric,
                originalTaxOption, item,100,tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        assertTrue(supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject(tax));
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");
        supplierInvoiceReviewPage.navigateToSupplierInvoiceDetails();

        afterSubmit = fillSupplierInvoiceAdjustmentForm(updatedQty,false,true).clickOnSubmit();

        //Verify Successful Integration and Tax Amount
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedAdjustedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyTotalAdjustedAmount(totalAdjustedAmount));
        assertTrue(afterSubmit.verifyProductLineTax(expectedAdjustedItems, expectedTaxAdjustedInvoice));
        validateServerLogStringsSupInv(afterSubmit,expectedStringsForAdjustedInv);

    }

    /**
     * This test is to validate the ability to perform an invoice change with decreased liability in the
     * enter tax due scenario
     * CWD-874
     */
    @Test(groups = { "workday_regression"} )
    public void adjustmentDownCalcSelfAssessedTest()
    {
        //Test Data
        String expectedTaxCalc = "30.00";
        String [] expectedTax = {"30.00","0.00"};
        String [] expectedTaxAdjustedInvoice = {"1.50"};
        String tax = "0.00";
        String expectedAdjustedTaxCalc = "(1.50)";
        String totalAdjustedAmount = "(30.00)";
        String [] expectedAdjustedItems = {"Black Ballpoint Pens"};
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String [] item = {"Black Ballpoint Pens","Chairs"};
        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage;

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Sign-in
        signInToHomePage(testStartPage);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", americanElectric,
                originalTaxOption, item,100,tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("0");
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");
        supplierInvoiceReviewPage.navigateToSupplierInvoiceDetails();

        afterSubmit = fillSupplierInvoiceAdjustmentForm(updatedQty,true,true).clickOnSubmit();

        //Verify Successful Integration and Tax Amount
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedAdjustedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyTotalAdjustedAmount(totalAdjustedAmount));
        assertTrue(afterSubmit.verifyProductLineTax(expectedAdjustedItems, expectedTaxAdjustedInvoice));
        validateServerLogStringsSupInv(afterSubmit,expectedStringsForAdjustedInv);
    }

    /**
     * This test is to validate the ability to perform an invoice change with increased liability in the
     * enter tax due scenario
     * CWD-875
     */
    @Test(groups = { "workday_regression"} )
    public void adjustmentUpCalcSelfAssessedTest()
    {
        //Test Data
        String expectedTaxCalc = "30.00";
        String [] expectedTax = {"30.00","0.00"};
        String [] expectedTaxAdjustedInvoice = {"1.50"};
        String tax = "0.00";
        String expectedAdjustedTaxCalc = "1.50";
        String totalAdjustedAmount = "30.00";
        String [] expectedAdjustedItems = {"Black Ballpoint Pens"};
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String [] item = {"Black Ballpoint Pens","Chairs"};
        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage;

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Sign-in
        signInToHomePage(testStartPage);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", americanElectric,
                originalTaxOption, item,100,tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("0");
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");
        supplierInvoiceReviewPage.navigateToSupplierInvoiceDetails();

        afterSubmit = fillSupplierInvoiceAdjustmentForm(updatedQty,false,true).clickOnSubmit();

        //Verify Successful Integration and Tax Amount
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedAdjustedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyTotalAdjustedAmount(totalAdjustedAmount));
        assertTrue(afterSubmit.verifyProductLineTax(expectedAdjustedItems, expectedTaxAdjustedInvoice));
        validateServerLogStringsSupInv(afterSubmit,expectedStringsForAdjustedInv);
    }

    /**
     * This test covers Supplier Invoice Change scenario for Enter Tax Due option
     * BP: Post, No header Tax
     * CWD-886
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceChangeUSEnterTaxDuePostTest() {
        // test data
        final String expectedTaxCalc = "12.25";
        final String [] expectedTax = {"12.25","0.00"};
        final String expectedTaxCalcUpdatedInvoice = "12.25";
        String tax = "12.25";
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String [] item = {"Black Ballpoint Pens","Chairs"};
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);
        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        // Sign in the home page
        signInToHomePage(testStartPage);

        //Fill out Full Supplier Invoice Form and submit it
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", americanElectric,
                originalTaxOption, item, 100, tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("0");
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");
        supplierInvoiceReviewPage.navigateToSupplierInvoiceDetails();

        //Changing the invoice and submitting it
        fillSupplierInvoiceChangeForm(updatedQty).clickOnSubmit();

        //Clicks on newly created invoice and verifies all the invoice details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalcUpdatedInvoice));

    }

    /**
     * This test covers Supplier Invoice Change scenario for Calculate Self-Assessed Tax option
     * BP: Post, No header Tax
     * CWD-887
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceChangeUSCalcSelfAssessedPostTest() {

        String expectedTaxCalc = "30.00";
        String [] expectedTax = {"30.00","0.00"};
        final String expectedTaxCalcUpdatedInvoice = "1.50";
        String tax = "0.00";
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String [] item = {"Black Ballpoint Pens","Chairs"};
        WorkdaySupplierInvoiceReviewPage reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);
        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        // Sign in the home page
        signInToHomePage(testStartPage);

        //Fill out Full Supplier Invoice Form and submit it
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", americanElectric,
                originalTaxOption, item, 100, tax, null).clickOnSubmit();

        //Verify Successful integration and Tax details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("0");
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");
        supplierInvoiceReviewPage.navigateToSupplierInvoiceDetails();

        //Changing the invoice and submitting it
        fillSupplierInvoiceChangeForm(updatedQty).clickOnSubmit();

        //Clicks on newly created invoice and verifies all the invoice details
        assertTrue(verifySupplierInvoiceIntegrationCompleted(), intfailedMessage);
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalcUpdatedInvoice));

    }

}
