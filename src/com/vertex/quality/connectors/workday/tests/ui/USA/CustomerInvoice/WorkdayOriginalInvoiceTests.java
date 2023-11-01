package com.vertex.quality.connectors.workday.tests.ui.USA.CustomerInvoice;

import com.vertex.quality.connectors.workday.pages.WorkdayInvoiceReviewPage;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * this test class is to verify "Tax Amount" after customer Invoice Quote request with different data
 * such as Exempted Product Class, Exempted Company and default tax calculation data
 *
 * @author dpatel
 */

public class WorkdayOriginalInvoiceTests extends WorkdayBaseTest {

    /**
     * this is the data provider that Provides Test Data
     */
    @DataProvider(name = "TestData")
    public Object[][] getDataFromDataProvider() {
        return new Object[][]
                {
                        {"Global Modern Services, Inc. (USA)", "LexCorp", "Product 10", 2, "1,697.86"},
                        {"Global Modern Services, Inc. (USA)", "LexCorp", "Product 3", 1, "0.00"},
                        {"Global Modern Services, Inc. (USA)", "Bluestar Corporation", "Services 13", 2, "0.00"},
                        {"Global Modern Services, Inc. (USA)", "LexCorp", "Product 4 has", 2, "0.00"},
                        {"Green Planet Solutions, Inc. (USA)", "Conners & Myers", "Product 10", 10, "5,978.38"}
                };
    }

    /**
     * JIRA TICKET CWD-231
     * This test case is to verify "Tax Amount" for customer Invoice Quote request within US territories
     */
    @Test(dataProvider = "TestData", groups = {"workday_smoke"})
    public void workdayCustomerInvoiceQuoteUSTest(String company, String billToCust, String product, int qty, String expectedResults) {


        WorkdayInvoiceReviewPage afterSubmit = fillCustomerInvoiceForm(company, billToCust, product, qty)
                .clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedResults, failedTaxCalcVal);
    }

    /**
     * JIRA TICKET CWD-245
     * This test case is to verify "Tax Amount" for customer Invoice Quote/Post request within US territories
     */
    @Test(groups = {"workday_smoke"})
    public void workdayOCustomerInvoiceOnDemandPremiseQuotePostUSTest() {

        //TestData
        String expectedResults = "0.00";
        WorkdayInvoiceReviewPage afterSubmit;

        //Filling out Customer Invoice Form
        afterSubmit = fillCustomerInvoiceForm("Green Planet Solutions, Inc. (USA)", "LexCorp", "Services 8", 1)
                .clickOnSubmit();

        //Verify integration is completed
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);

        //Verify the tax amount
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedResults, failedTaxCalcVal);
    }

    /**
     * This test case is to validate Tax registration ID is present in the server logs
     * CWD-547 & 566
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceRegIDMissingBugTest() {

        String expectedTaxAmount = "0.00";
        String[] expectedStrings = {"<vtx:TaxRegistration isoCountryCode=\"GBR\" hasPhysicalPresenceIndicator=\"true\">", "<vtx:TaxRegistrationNumber>GB123 9999 99</vtx:TaxRegistrationNumber>"};

        //Creating Customer Invoice and verifying it
        WorkdayInvoiceReviewPage afterSubmit = fillCustomerInvoiceForm("Green Modern Solutions, Inc. (USA)", "LexCorp", "Services 8", 10)
                .clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxAmount, failedTaxCalcVal);
        afterSubmit.ClickOnCustomerInvoiceAdjustmentLink();

        //downloading the server logs and verifying the values present or not
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

        //Verify server logs for Adjusted Invoice Distribute
        afterSubmit.deleteFilesFromDirectory();
        fileName = downloadServerLogs(false);
        for (String expectedString : expectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test case is to validate character limit trimming functionality on
     * non taxable Quote Character Limit
     * CWD-476
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceNonTaxableQuoteCharacterLimitTest() {
        String expectedTaxAmount = "0.00";
        WorkdayInvoiceReviewPage afterSubmit = fillCustomerInvoiceForm("Global Modern Services, Inc. (USA)", "LexCorp", "Product 4 has", 2)
                .clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxAmount, failedTaxCalcVal);

    }

    /**
     * This test case is to validate Invoice Quote and distribute non-taxable product class
     * CWD-277
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceValidateQuoteDistributeNonTaxableProductClassTest() {
        String expectedTaxAmount = "0.00";
        WorkdayInvoiceReviewPage afterSubmit = fillCustomerInvoiceForm("Green Planet Solutions, Inc. (USA)", "Conners & Myers", "Services 8", 10)
                .clickOnSubmit();

        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxAmount, failedTaxCalcVal);
    }

    /**
     * This test case is to validate that correct elements shows up in the Vertex request which is available in serverLogs
     * CWD-395
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceValidateCorrectElementsTest() {

        String expectedTaxAmount = "1,697.86";
        String[] expectedStrings = {"<vtx:AdministrativeDestination>", "<vtx:Customer>"};
        WorkdayInvoiceReviewPage afterSubmit = fillCustomerInvoiceForm("Global Modern Services, Inc. (USA)", "LexCorp", "Product 10", 2)
                .clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxAmount, failedTaxCalcVal);
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test case is to validate that any company using ROLLUP tax, has the feature available in Transaction Tax line items window
     * CWD-401
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceValidateRollUpTaxTest() {

        String expectedTaxAmount = "896.76";
        String[] expectedTaxByTaxRate = {"573.91", "95.66", "11.96", "47.83", "47.83", "119.57"};

        WorkdayInvoiceReviewPage afterSubmit = fillCustomerInvoiceForm("Global Modern Services, Inc. (USA)", "Initech", "Product 10", 1)
                .clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"));
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxAmount, failedTaxCalcVal);

        afterSubmit.clickOnNewlyCreatedCustInvAdj();
        afterSubmit.switchToTab("Tax");
        String[] actualTaxByTaxRate = afterSubmit.getLineLevelTaxes();
        assertTrue(afterSubmit.isRollUpTaxAvailable(), "Roll Up Tax is not available");
        assertTrue(afterSubmit.compareLineLevelTaxes(actualTaxByTaxRate, expectedTaxByTaxRate));
    }

    /**
     * This test case is to validate Invoice Quote and post taxable product class
     * CWD-234
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceValidateQuotePostNonTaxableProductClassTest() {

        String expectedTaxAmount = "0.00";
        WorkdayInvoiceReviewPage afterSubmit = fillCustomerInvoiceForm("Global Modern Services, Inc. (USA)", "LexCorp", "Services 13", 1)
                .clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxAmount, failedTaxCalcVal);
    }

    /**
     * This test case is to validate Invoice Quote and post taxable product class
     * CWD-244
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceValidateQuoteDistributeTaxableProductClassTest() {

        String expectedTaxAmount = "5,978.38";
        WorkdayInvoiceReviewPage afterSubmit = fillCustomerInvoiceForm("Green Planet Solutions, Inc. (USA)", "Conners & Myers", "Product 10", 10)
                .clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxAmount, failedTaxCalcVal);
    }

    /**
     * This test case is to validate Invoice Quote and post Non-taxable State
     * CWD-233
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceValidateQuotePostNonTaxableStateDETest() {

        String expectedTaxAmount = "0.00";
        WorkdayInvoiceReviewPage afterSubmit = fillCustomerInvoiceForm("Green Planet Solutions, Inc. (USA)", "Bluestar Corporation", "Services 13", 1)
                .updateShipToAddress("Wilmington") // does not exist
                .clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxAmount, failedTaxCalcVal);
    }

    /**
     * This test case is to validate Invoice Quote and post Non-taxable Product Code
     * CWD-232
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceValidateQuotePostNonTaxableProductCodeTest() {

        String expectedTaxAmount = "0.00";
        WorkdayInvoiceReviewPage afterSubmit = fillCustomerInvoiceForm("Global Modern Services, Inc. (USA)", "LexCorp", "Services 8", 1)
                .clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxAmount, failedTaxCalcVal);
    }

    /**
     * This test case is to validate Invoice Quote and post Non-taxable product class
     * CWD-739
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceValidateQuotePostTaxableProductClassTest() {

        String expectedTaxAmount = "848.93";
        WorkdayInvoiceReviewPage afterSubmit = fillCustomerInvoiceForm("Global Modern Services, Inc. (USA)", "LexCorp", "Product 10", 1)
                .clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxAmount, failedTaxCalcVal);
    }

    /**
     * This test case is to verify that we are sending Customer Tax ID and not Company Tax ID
     * CWD-961
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceValidateCustomerTaxIDQuoteTest() {

        // Test Data
        final String expectedTaxAmount = "848.93";


        String[] expectedStrings = {"<vtx:TaxRegistrationNumber>GB123 9999 99</vtx:TaxRegistrationNumber>"};

        // Generate Customer Invoice
        WorkdayInvoiceReviewPage afterSubmit = fillCustomerInvoiceForm("Global Modern Services, Inc. (USA)", "LexCorp", "Product 10", 1)
                .clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxAmount, failedTaxCalcVal);

        // Verify Logs are Correct
        String fileName = downloadServerLogs(true);
        for (String expectedString : expectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This test verifies that when the company Bill-to address is blank, that we default to using the Ship-to address
     * to calculate tax
     * CWD-962
     */
    @Test(groups = {"workday_regression"})
    public void workdayCustomerInvoiceUseBillToWhenShipToIsBlank() {
        // Test Data
        final String expectedTaxAmount = "848.93";

        // Generate Customer Invoice
        WorkdayInvoiceReviewPage afterSubmit = fillCustomerInvoiceForm("Global Modern Services, Inc. (USA)", "LexCorp", "Product 10", 1)
                .clickOnSubmit();
        assertTrue(verifyIntegrationCompleted("Customer"), intfailedMessage);
        assertEquals(afterSubmit.getValuesFromTaxAmount(), expectedTaxAmount, failedTaxCalcVal);
    }

}
