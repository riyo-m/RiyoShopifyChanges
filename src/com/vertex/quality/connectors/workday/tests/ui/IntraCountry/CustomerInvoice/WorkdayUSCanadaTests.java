package com.vertex.quality.connectors.workday.tests.ui.IntraCountry.CustomerInvoice;

import com.vertex.quality.connectors.workday.pages.WorkdayBusinessProcessPage;
import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * this test class is to verify "Tax Amount", third party tax calculation status after Customer Invoice is submitted
 * It also verifies that while Changing the customer Invoice reversal request is only being called once
 * @author dpatel
 */

public class WorkdayUSCanadaTests extends WorkdayBaseTest {

    /**
     * This test case is to validate Tax Amount When Item is being purchased by US Customer From Canadian Supplier.
     * Since Customer Invoice is dealing with Sales Tax , this should not return any tax i.e Zero Tax
     * CWD-237
     */
    @Test(groups = { "workday_regression" } )
    public void workdayCustomerInvoiceValidateQuotePostCanadaToUSTest() {

        //Test Data
        final String expectedTaxCalc = "0.00";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Signing in the home page
        signInToHomePage(testStartPage);

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGMSCanadaCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("GMS Canada", "lexcorp", "Product 1", 1)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
    }

    /**
     * This test case is to validate Tax Amount When Item is being purchased by Canadian Customer From US Supplier.
     * Since Customer Invoice is dealing with Sales Tax , this should not return any tax i.e Zero Tax
     * CWD-721
     */
    @Test(groups = { "workday_regression" } )
    public void workdayCustomerInvoiceValidateQuotePostUSToCanadaTest() {

        //Test Data
        final String expectedTaxCalc = "0.00";
        final String postOption = "post";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;

        // Signing in the home page
        signInToHomePage(testStartPage);

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBPForGMSCanadaCustomerInvoice(postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("Spectre", "Maple", "Services 13", 10)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxCalc, failedTaxCalcVal);
    }
}
