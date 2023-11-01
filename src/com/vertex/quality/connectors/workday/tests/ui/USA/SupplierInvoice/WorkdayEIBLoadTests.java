package com.vertex.quality.connectors.workday.tests.ui.USA.SupplierInvoice;

import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.pages.WorkdaySupplierInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * this test class covers tests for EIB load of supplier Invoice
 *
 * @author dpatel
 */

public class WorkdayEIBLoadTests extends WorkdayBaseTest {

    String calcTaxDue = "Calculate Tax Due to Supplier";
    String post = "post";
    String headerError = "Sum of all Vertex-Tax Paid Line Taxes is not equal to Header tax";
    String eibLoadFailedError = "Supplier Invoice Load Integration Failed";
    WorkdayInvoiceReviewPage invoiceReviewPage;
    WorkdaySupplierInvoiceReviewPage reviewPage;

    /**
     * this test covers end to end EIB load to verify supplier Invoice Fields value
     * It uploads same XML file over and over from upload folder
     * CWD-548
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceEIBLoadTest() {

        String postOption = post;

        //signing in the home page
        signInToHomePage(testStartPage);

        // Loading XML file with 1 Supplier Invoice and 5 Line Items
        assertTrue(launchSupplierInvoiceLoad("currency"), eibLoadFailedError);

        //Getting recently loaded Supplier Invoice
        getEIBLoadedSupplierInvoice("dpCurrencyRateEIB");

        //Verify Successful Integration,Currency Fields,locked in workday,worktagsplit template,Payment Practices
        verifyEIBLoadedSupplierInvoiceFieldsValue(selfAssessedTax);
    }

    /**
     * This test Covers Supplier Invoice line level custom object Happy path scenario with Post
     * When Tax option is "Enter Tax Due" with no header Tax then no tax should populate in Custom Object Vertex Calculated Tax
     * BP: Post
     * CWD-559
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceLineLevelCustomObjectHappyPathScenarioQuotePostTest() {
        String postOption = post;
        String updatedTaxOption = calcTaxDue;
        String invoiceName = "dpLineLevelEIB";
        String eibName = "linelevel";
        String[] actualLineTaxes;
        String[] expectedLineTaxes = {"57", "0.15", "0.21", "0", "4.64"};

        //Initialize Page Classes
        invoiceReviewPage = new WorkdayInvoiceReviewPage(driver);
        reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //signing in the home page
        signInToHomePage(testStartPage);

        // Loading XML file with 1 Supplier Invoice and 5 Line Items
        assertTrue(launchSupplierInvoiceLoad(eibName), eibLoadFailedError);

        //Getting recently loaded Supplier Invoice
        getEIBLoadedSupplierInvoice(invoiceName);

        //Verify Successful Integration and Line level taxes
        actualLineTaxes = reviewPage.getArrayOfLineLevelTaxFromCustomObject();
        assertTrue(invoiceReviewPage.compareLineLevelTaxes(actualLineTaxes, expectedLineTaxes));
        assertTrue(reviewPage.verifyUpdatedTaxOption(updatedTaxOption));
    }

    /**
     * This test Covers Supplier Invoice Prorate Tax import when Vendor charged is less that what Vertex is calculating.
     * Some Items have a ship to address of California so we can verify Roll over Tax as well
     * Tax Option: Enter Tax Due with Header Tax
     * BP: Post
     * CWD-595
     */
    @Test(groups = {"workday_regression"})
    public void workdaySupplierInvoiceProrateTaxImportWithCaliforniaAddressTest() {
        String postOption = post;
        String updatedTaxOption = calcTaxDue;
        String headerTax = "101.27";
        String expectedTax = "315.46";
        String[] actuallineLevelTaxes;
        String invoiceName = "dpProrateDiffAddEIB";
        String eibName = "prorateDiffAddImport";
        String[] expectedLineLevelTaxes = {"39.75", "32.33", "82.72", "67.28", "31.25", "12.50", "12.50", "31.25", "0.00", "0.00"
                , "0.00", "0.00", "0.00", "2.06", "1.66", "0.77", "0.31", "0.31", "0.77", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00"};


        // Initialize Page classes
        invoiceReviewPage = new WorkdayInvoiceReviewPage(driver);
        reviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        //signing in the home page and modify BP
        signInToHomePage(testStartPage);

        // Loading XML file with 1 Supplier Invoice and 5 Line Items
        assertTrue(launchSupplierInvoiceLoad(eibName), eibLoadFailedError);

        //Getting recently loaded Supplier Invoice
        getEIBLoadedSupplierInvoice(invoiceName);

        //Verify Successful Integration and Line level taxes
        assertTrue(reviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(reviewPage.verifyTaxDetails(expectedTax));

        //Verifying sum of all VertexTaxPaid Line Taxes is equal to header tax
        reviewPage.clickOnTaxTab();
        assertEquals(reviewPage.getSumOfAllVertexTaxPaidLineItems(), headerTax, headerError);

        //Getting Line Level Tax from Tax Tab and storing in Array
        actuallineLevelTaxes = reviewPage.getAllLineLevelTaxesFromTaxTab();

        //Verify LineLevelTaxDetails
        assertTrue(invoiceReviewPage.compareLineLevelTaxes(actuallineLevelTaxes, expectedLineLevelTaxes));
    }
}
