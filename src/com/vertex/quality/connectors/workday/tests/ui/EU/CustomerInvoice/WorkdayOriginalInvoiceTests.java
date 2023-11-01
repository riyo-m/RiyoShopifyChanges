package com.vertex.quality.connectors.workday.tests.ui.EU.CustomerInvoice;

import com.vertex.quality.connectors.workday.pages.WorkdayBusinessProcessPage;
import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * this test class is to verify "Tax Amount", third party tax calculation status after Customer Invoice is submitted
 * It verifies European Union Countries to European Union Countries Tests
 * It also verifies that while Changing the customer Invoice reversal request is only being called once
 * @author Vishwa
 */

public class WorkdayOriginalInvoiceTests extends WorkdayBaseTest {

    String [] expectedStringLog = {"<vtx:TaxRegistrationNumber>DE999999999</vtx:TaxRegistrationNumber>"};

    /**
     * This test case is to validate Tax Amount When Item is being purchased by EU Customer From EU Supplier.
     * Multiple Products
     * @author Vishwa
     * CWD-241
     */
    @Test(groups = { "workday_inprogress" } )
    public void workdayCustomerInvoiceQuotePostEUtoEUMultipleProductsTest() {

        //Test Data
       String[] expectedTax = {"0.00", "0.00", "0.00", "0.00", "0.00"};
        final String postOption = "post";
        String company = "GMS Germany";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;
        String[] product = {"Product 1", "Product 10", "Services 1", "Services 13", "Services 2"};
        // Signing in the home page
        signInToHomePage(testStartPage);

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBusinessProcessForCustomerInvoice(company, postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormMultipleProducts("GMS Germany", "Deutsche", product, 1, null)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
        afterSubmit.clickOnClose();
        afterSubmit.clickOnNewCustomerInvoiceApprovedLink();

        //Verify server logs for Adjusted Invoice Quote
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedStringLog) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test case is to validate Tax Amount When Item is being purchased by EU Customer From EU Supplier.
     * Multiple Products
     * @author Vishwa
     * CWD-240
     */
    @Test(groups = { "workday_regression" } )
    public void workdayCustomerInvoiceQuotePostUKtoGermaniMultipleProductsTest() {

        //Test Data
        String[] expectedTax = {"0.00", "0.00", "0.00", "0.00", "0.00"};
        final String postOption = "post";
        String company = "GMS UK";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;
        String[] product = {"Product 1", "Product 10", "Services 1", "Services 13", "Services 2"};
        // Signing in the home page
        signInToHomePage(testStartPage);

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBusinessProcessForCustomerInvoice(company, postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormMultipleProducts("GMS UK", "Deutsche", product, 1, null)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
    }

    /**
     * This test case is to validate Tax Amount When Item is being purchased by EU Customer From EU Supplier.
     * Multiple Products
     * @author Vishwa
     * CWD-242
     */
    @Test(groups = { "workday_regression" } )
    public void workdayCustomerInvoiceQuotePostUKtoUKMultipleProductsTest() {

        //Test Data
        String[] expectedTax = {"795.45", "1,604.32", "19.89", "20.58", "23.86"};
        final String postOption = "post";
        String company = "GMS UK";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;
        String[] product = {"Product 1", "Product 10", "Services 1", "Services 13", "Services 2"};
        // Signing in the home page
        signInToHomePage(testStartPage);

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBusinessProcessForCustomerInvoice(company, postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormMultipleProducts("GMS UK", "British Telecom", product, 1, null)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
    }
}
