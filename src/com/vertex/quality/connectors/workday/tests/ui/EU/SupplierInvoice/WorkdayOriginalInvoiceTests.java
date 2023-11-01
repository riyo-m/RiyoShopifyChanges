package com.vertex.quality.connectors.workday.tests.ui.EU.SupplierInvoice;

import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.pages.WorkdaySupplierInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class WorkdayOriginalInvoiceTests extends WorkdayBaseTest {

    WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage;

    @BeforeMethod
    public void beforeTest()
    {
        signInToHomePage(testStartPage);
    }
    /**
     * This Test validates UK-UK Full recoverability scenario
     * Tax option: Enter Tax Due To Supplier
     * Input Tax: 10
     * CWD-638
     */
    @Test(groups = { "workday_regression"} )
    public void workdaySupplierInvoiceUKToUKEnterTaxDueInputTaxFullRecovTest()
    {
        //Test Data
        String [] expectedTax = {"12.25","0.00"};
        String tax = "12.25";
        String originalTaxoption = enterTaxDue;
        String [] item = {"Black Ballpoint Pens","Chairs"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", "British Telecom",
                originalTaxoption, item,30,tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(tax));
        assertEquals(afterSubmit.getValuesFromTaxAmount(),tax, failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        assertTrue(supplierInvoiceReviewPage.verifyLineLevelSelfAssessedTax("0.00"));
        assertTrue(supplierInvoiceReviewPage.verifyLineLevelTaxPaidTax(tax));
        afterSubmit.clickOnClose();
        supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject(tax);
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");
    }

    /**
     * This test Covers Supplier Invoice CAN-CAN with multiple exempt sales Items
     * It verifies that last line is being imported with Vendor Charged Tax
     * Tax Option: Enter Tax Due with Header Tax
     * BP: Post
     * CWD-684
     */
    @Test(groups = { "workday_regression"} )
    public void workdaySupplierInvoiceUKToUKAllMultipleExemptItemsTest()
    {
        String originalTaxOption = enterTaxDue;
        String updatedTaxOption = calcTaxDue;
        String tax = "10.00";
        String company = "GMS UK";
        String [] item = {chairs,"Desks","Projector"};


        //Instantiating Class
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling out supplier Invoice form for exempted item
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(company, americanElectric,originalTaxOption, item, 1,tax,null)
                .clickOnSubmit();

        //Verify Integration is successful
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);

        //Verify Invoice Details
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(tax));
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(supplierInvoiceReviewPage.verifyLineLevelTaxPaidTax(tax));
        assertTrue(supplierInvoiceReviewPage.verifyLineLevelSelfAssessedTax("0.00"));

        //Navigating to Newly Created Invoice
        afterSubmit.clickonNewlyCreatedSupplierInvoice();

        //Verify Vendor charged Tax
        assertTrue(supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("10"));
    }

    /**
     * This Test validates UK-UK Self Assessed Reverse Charged Tax
     * Tax option: Self Assessed Tax
     * CWD-764
     */
    @Test(groups = { "workday_regression"} )
    public void reverseChargedUKtoUKCalcSelfAssessedTest()
    {
        //Test Data
        String [] expectedTax = {"0.00","0.00"};
        String originalTaxoption = selfAssessedTax;
        String [] item = {"Black Ballpoint Pens","Chairs"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS UK", "British Telecom",
                originalTaxoption, item,30,"0", null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails("0"));
        assertEquals(afterSubmit.getValuesFromTaxAmount(),"0", failedTaxCalcVal);
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("0");
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");

    }
}
