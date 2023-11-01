package com.vertex.quality.connectors.workday.tests.ui.IntraCountry.SupplierInvoice;

import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.pages.WorkdaySupplierInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class WorkdayFranceToUKTests extends WorkdayBaseTest {

    WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage;

    @BeforeMethod
    public void beforeTest()
    {
        signInToHomePage(testStartPage);
    }
    /**
     * This Test validates Accrual (Reverse Charged) scenario for France to UK transaction
     * Tax option: Enter Tax Due To Supplier
     * Input Tax: 10
     * CWD-714
     */
    @Test(groups = { "workday_regression"} )
    public void workdaySupplierInvoiceFranceToUKReverseChargedAccrualEnterTaxDueWithInputTaxTest()
    {
        //Test Data
        String tax = "10.00";
        String expectedTax = "10.00";
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String [] item = {"Black Ballpoint Pens"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", "Adecco",
                originalTaxOption, item,100,tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTax));
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTax, failedTaxCalcVal);
        assertTrue(supplierInvoiceReviewPage.verifyLineLevelTaxPaidTax(tax));
    }

    /**
     * This Test validates Accrual (Reverse Charged) scenario for France to UK transaction
     * Tax option: Self Assessed Tax
     * Input Tax: 0
     * CWD-713
     */
    @Test(groups = { "workday_regression"} )
    public void workdaySupplierInvoiceFranceToUKReverseChargedCalcSelfAssessedTest()
    {
        //Test Data
        String expectedTaxCalc = "4,461.39";
        String [] expectedTax = {"20.40","0.00","5.94","4,434.78","0.27"};
        String tax = "0";
        String originalTaxoption = selfAssessedTax;
        String [] item = {"Binder Clips","Chairs","Ergonomic Keyboard","Publicity","Black Ballpoint Pens"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", "Adecco",
                originalTaxoption, item,100,tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("0");
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");
    }

    /**
     * This Test validates Accrual (Reverse Charged) scenario for France to UK transaction
     * Tax option: enter Tax Due
     * Input Tax: "4,434.78"
     * CWD-765
     */
    @Test(groups = { "workday_regression"} )
    public void reverseChargedFranceToUKVendorChargedPartialRecovTest()
    {
        //Test Data
        String tax = "4,434.78";
        String [] expectedTax = {tax};
        String originalTaxoption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String [] item = {"Publicity"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", "Adecco",
                originalTaxoption, item,100,tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(tax));
        assertTrue(supplierInvoiceReviewPage.verifyNonRecoverableTax("2,217.39"));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject(tax);
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");
    }

    /**
     * This Test validates Accrual (Reverse Charged) scenario for France to UK transaction
     * Tax option: Enter Tx Due
     * Input Tax: 0
     * CWD-766
     */
    @Test(groups = { "workday_regression"} )
    public void reverseChargedFranceToUKVendorChargedNoRecovTest()
    {
        //Test Data
        String tax = "12.25";
        String [] expectedTax = {tax};
        String originalTaxoption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String [] item = {"Binder Clips"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", "Adecco",
                originalTaxoption, item,100,tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(tax));
        assertTrue(supplierInvoiceReviewPage.verifyNonRecoverableTax("12.25"));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject(tax);
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");
    }
}
