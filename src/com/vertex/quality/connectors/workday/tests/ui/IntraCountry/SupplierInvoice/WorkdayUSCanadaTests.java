package com.vertex.quality.connectors.workday.tests.ui.IntraCountry.SupplierInvoice;

import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.pages.WorkdaySupplierInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class WorkdayUSCanadaTests extends WorkdayBaseTest {

    WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage;

    @BeforeMethod
    public void beforeTest()
    {
        signInToHomePage(testStartPage);
    }

    /**
     * This Test validates Reverse charged scenario for US to Canada transaction
     * Tax option: Self Assessed Tax
     * Input Tax: 0
     * CWD-212
     */
    @Test(groups = { "workday_regression"} )
    public void usToCanadaReverseChargedSelfAssessedTest()
    {
        //Test Data
        String expectedTaxCalc = "1,752.77";
        String [] expectedTax = {"0.00","0.00","2.35","1,750.00","0.42"};
        String tax = "0";
        String originalTaxoption = selfAssessedTax;
        String [] expectedStrings = {"PostOption: POST","AccrualRequest"};
        String [] item = {"Advil Packets","Chairs","Ergonomic Keyboard","Publicity","Black Ballpoint Pens"};

        //Class Initialization
        supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GMS Canada", "Dell",
                originalTaxoption, item,1,tax, null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        supplierInvoiceReviewPage.verifyVendorChrgedTaxFromCustomObject("0");

        //Validate POST to Vertex
        verifyHeaderLevelCustomObjectTransactionData("POST","SUCCESS", "Yes");
        supplierInvoiceReviewPage.navigateToSupplierInvoiceDetails();

        //Validate Line Level Taxes
        assertTrue(afterSubmit.verifyProductLineTax(item, expectedTax));
        afterSubmit.clickOnClose();
        afterSubmit.switchToTab("Process History");

        //Validate XML Server Logs
        String fileName= downloadServerLogsForSupplierInv(true);
        for (String expectedString : expectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }
}
