package com.vertex.quality.connectors.workday.tests.ui.IntraCountry.CustomerInvoice;

import com.vertex.quality.connectors.workday.pages.WorkdayBusinessProcessPage;
import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * this test class is to verify "Tax Amount", third party tax calculation status after Customer Invoice is submitted
 * It verifies European Union to USA Countries Tests
 * It also verifies that while Changing the customer Invoice reversal request is only being called
 * @author Vishwa
 */

public class WorkdayEUUSATests extends WorkdayBaseTest {

    /**
     * This test case is to validate Tax Amount When Item is being purchased by EU Customer From USA Supplier.
     * Multiple Products
     * @author Vishwa
     * CWD-243
     */

    @Test(groups = { "workday_regression" } )
    public void workdayCustomerInvoiceQuotePostGermanytoUSAMultipleProductsTest() {

        //Test Data
        String[] expectedTax = {"0.00", "0.00", "0.00", "0.00", "0.00"};
        final String postOption = "post";
        final String company = "GMS Germany";
        final WorkdayBusinessProcessPage bpPage;
        WorkdayInvoiceReviewPage afterSubmit;
        String[] product = {"Product 1", "Product 10", "Services 1", "Services 13", "Services 2"};
        // Signing in the home page
        signInToHomePage(testStartPage);

        // Updating Business Process
        bpPage = new WorkdayBusinessProcessPage(driver);
        bpPage.changeBusinessProcessForCustomerInvoice(company, postOption);

        //Filling out and Submitting the invoice
        afterSubmit = fillCustomerInvoiceFormMultipleProducts("GMS Germany", "Lexcorp", product, 1, null)
                .clickOnSubmit();

        //verifying the successful integration and tax amount
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertTrue(afterSubmit.verifyProductLineTax(product, expectedTax));
    }
}
