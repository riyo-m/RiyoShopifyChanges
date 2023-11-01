package com.vertex.quality.connectors.workday.tests.ui.IntraCountry.SupplierInvoice;

import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.pages.WorkdaySupplierInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * This test class contains all the US-UK transaction tests
 */
public class WorkdayUSToUKTests extends WorkdayBaseTest {
    WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage;

    @BeforeMethod
    public void beforeTest()
    {
        signInToHomePage(testStartPage);
    }

    /**
     * This Test validates Accrual (Reverse Charged) scenario for CAN to CAN transaction
     * Tax option: Enter Tax Due
     * Input Tax: 0
     * CWD-632
     */
    @Test(groups = { "workday_regression"} )
    public void workdaySupplierInvoiceUSToUKReverseChargedCalcSelfAssessedTest()
    {
        //Test Data
        String expectedTaxCalc = "23.30";
        String [] expectedTax = {"23.00","0.00","0.00","0.00","0.30"};
        String tax = "0";
        String originalTaxoption = selfAssessedTax;
        String [] item = {"Binder Clips","Chairs","Ergonomic Keyboard","Publicity","Black Ballpoint Pens"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", "American",
                originalTaxoption, item,100,tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        assertTrue(supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject(tax));
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");
    }

    /**
     * This Test validates Accrual (Reverse Charged) scenario for CAN to CAN transaction
     * Tax option: Calculate Tax Due
     * Input Tax: 0
     * CWD-635
     */
    @Test(groups = { "workday_regression"} )
    public void workdaySupplierInvoiceUSToUKReverseChargedCalcTaxDueTest()
    {
        //Test Data
        String expectedTaxCalc = "23.30";
        String [] expectedTax = {"23.00","0.00","0.00","0.00","0.30"};
        String tax = "0";
        String originalTaxoption = calcTaxDue;
        String [] item = {"Binder Clips","Chairs","Ergonomic Keyboard","Publicity","Black Ballpoint Pens"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", "American",
                originalTaxoption, item,100,tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        assertTrue(supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject(tax));
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");
    }

    /**
     * This Test validates Accrual (Reverse Charged) scenario for CAN to CAN transaction
     * Tax option: Enter Tax Due To Supplier
     * Input Tax: 0
     * CWD-636
     */
    @Test(groups = { "workday_regression"} )
    public void workdaySupplierInvoiceUSToUKReverseChargedEnterTaxDueNoInputTaxTest()
    {
        //Test Data
        String expectedTaxCalc = "23.30";
        String [] expectedTax = {"23.00","0.00","0.00","0.00","0.30"};
        String tax = "0";
        String originalTaxoption = enterTaxDue;
        String updatedTaxOption = selfAssessedTax;
        String [] item = {"Binder Clips","Chairs","Ergonomic Keyboard","Publicity","Black Ballpoint Pens"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", "American",
                originalTaxoption, item,100,tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        assertTrue(supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject(tax));
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");
    }

    /**
     * This Test validates Accrual (Reverse Charged) scenario for US to UK transaction
     * Tax option: Enter Tax Due To Supplier
     * Input Tax: 10
     * CWD-678
     */
    @Test(groups = { "workday_regression"} )
    public void workdaySupplierInvoiceUSToUKReverseChargedAccrualEnterTaxDueWithInputTaxTest()
    {
        //Test Data
        String tax = "10.00";
        String originalTaxoption = enterTaxDue;
        String [] item = {"Black Ballpoint Pens"};
        String [] expectedTax = {"10.00"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", "Apple",
                originalTaxoption, item,100,tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(tax));
        assertEquals(afterSubmit.getValuesFromTaxAmount(),tax, failedTaxCalcVal);
        afterSubmit.switchToTab("Tax");
        assertTrue(supplierInvoiceReviewPage.verifyLineLevelTaxPaidTax(tax));
        assertTrue(supplierInvoiceReviewPage.verifyNonRecoverableTax("10.00"));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        assertTrue(supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("10"));
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");
    }

    /**
     * This Test validates VAT Charged Tax Scenario with Full reocverability when Supplier
     * is registered in UK
     * Tax option: Enter Tax Due To Supplier
     * Input Tax: 0
     * CWD-677
     */
    @Test(groups = { "workday_regression"} )
    public void workdaySupplierInvoiceUSToUKRegisteredSuplEnterTaxDueInputTaxFullRecovTest()
    {
        //Test Data
        String tax = "10.00";
        String originalTaxoption = enterTaxDue;
        String [] item = {"Black Ballpoint Pens"};
        String [] expectedTax = {"10.00"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", "Dell",
                originalTaxoption, item,100,tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(tax));
        assertEquals(afterSubmit.getValuesFromTaxAmount(),tax, failedTaxCalcVal);
        assertTrue(supplierInvoiceReviewPage.verifyLineLevelTaxPaidTax(tax));
        assertTrue(supplierInvoiceReviewPage.verifyNonRecoverableTax("10.00"));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        assertTrue(supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("10"));
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");

    }

    /**
     * This Test validates Accrual (Reverse Charged) scenario for US to UK transaction
     * Tax option: Enter Tax Due To Supplier
     * Input Tax: 10
     * CWD-678
     */
    @Test(groups = { "workday_regression"} )
    public void reverseChargedUSToUKVendorChargedExemptItemsTest()
    {
        //Test Data
        String expectedTaxCalc = "23.30";
        String [] expectedTax = {"0.00","0.00","23.30"};
        String tax = "23.30";
        String originalTaxoption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String [] item = {"Chairs","Ergonomic Keyboard","Publicity"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", "American",
                originalTaxoption, item,100,tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        assertTrue(supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("23.3"));
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");
    }
}
