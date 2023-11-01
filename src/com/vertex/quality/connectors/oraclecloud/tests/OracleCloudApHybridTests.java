package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.keywords.api.DataKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.DatabaseKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.ProcessesKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.TemplateKeywords;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudManageInvoicesPage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import com.vertex.quality.connectors.oraclecloud.common.utils.OracleUtilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;
import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeDbSettings;
import static com.vertex.quality.connectors.oraclecloud.common.utils.Template.transactionNumber;

/**
 * Container for Oracle Cloud AP hybrid scenarios. These scenarios
 * entail a batch data upload followed by UI or SQL actions.
 *
 * @author msalomone
 */
public class OracleCloudApHybridTests extends OracleCloudBaseTest {

    private ProcessesKeywords processes = new ProcessesKeywords();
    private TemplateKeywords templates = new TemplateKeywords();
    private DataKeywords data = new DataKeywords();
    private DatabaseKeywords database = new DatabaseKeywords();

    private OracleUtilities utilities = new OracleUtilities();

    private String todaysDate = utilities.getTodaysDate("M/dd/yy");

    /**
     * Handles template uploading and setting initialization for
     * AP hybrid test cases.
     *
     * Jira Story: COERPC-6206
     */
    @BeforeTest(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ap" })
    private void apHybridSetup() throws InterruptedException {
        initializeApiTest();
        initializeDbSettings();
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("9-PayablesStandardInvoiceImportTemplate_US-CAN_One-Offs.xlsm",
                "ApInvoicesInterface_Hybrid.csv", //ApInvoicesInterfaceCA_BU.csv
                "ApInvoiceLinesInterface_Hybrid.csv",
                "","",
                "apinvoiceimport_hybrid.zip", "AP", "");
        processes.importDataPayablesInvoicesAP( "apinvoiceimport_hybrid.zip",
                "apinvoiceimport_hybrid.zip", "VTX_US_BU","");
        Thread.sleep(360000);
        VertexLogger.log("Ap single-hybrid test setup complete.", VertexLogLevel.INFO);
    }

    /**
     * Discards line from an invoice after invoice upload via batch.
     * The invoice has a new line added, gets saved, then verified by
     * their line_level column being labeled DISCARDED.
     *
     * Jira Test: COERPC-3214
     */
    @Test(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ap" })
    public void apInvDiscardedLineTest() throws InterruptedException {
        String invoiceNum1 = transactionNumber+"_EC775A";
        String invoiceNum2 = transactionNumber+"_EC775B";
        String amount = "100.00";
        String distCombo = "3211-20-60041-110-0000-0000";
        String shipTo = "VTX_PA";

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudManageInvoicesPage manageInvoicesPage = navigateToManageInvoicesPage();

        // Invoice 1
        manageInvoicesPage.searchInvoiceNumber(invoiceNum1);
        manageInvoicesPage.editInvoice();
        // Cancel a line
        manageInvoicesPage.cancelLineItem();
        // Add new line with same details as cancelled line.
        manageInvoicesPage.clickAddRowToLines();
        addInvoiceLineItem(manageInvoicesPage, 1, null, amount, null,
                distCombo, todaysDate, shipTo, false);
        manageInvoicesPage.clickSaveAndCloseButton();

        manageInvoicesPage.expandSearch();

        // Invoice 2
        manageInvoicesPage.searchInvoiceNumber(invoiceNum2);
        manageInvoicesPage.editInvoice();
        // Cancel a line
        manageInvoicesPage.cancelLineItem();
        // Add new line with same details as cancelled line.
        manageInvoicesPage.clickAddRowToLines();
        addInvoiceLineItem(manageInvoicesPage, 1, null, amount, null,
                null, null, null, false);
        manageInvoicesPage.clickSaveAndCloseButton();

        driver.close();

        processes.validatePayablesInvoicesAP("VTX_US_BU");
        Thread.sleep(360000);
        processes.runPartnerTransactionDataExtract("VTX_US_BU");
        Thread.sleep(360000);

        // Verification by checking Vertex extract file to be added

    }

    /**
     * Process a Canadian AP invoice match scenario using
     * the new upper case SASKATCHEWAN ship-to and the lower
     * case ship-to Saskatchewan.Verify both ship-tos in the
     * OSR Req.
     *
     * Jira Test: COERPC-3215
     */
    @Test(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ap" })
    public void apCanInvoiceCaseSensitiveShipToTest() {
        String invoiceNum = "UQ062321"+"_EC838A";
        invoiceNum = transactionNumber+"_EC838A";
        String expectedTax = "22.00";

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudManageInvoicesPage manageInvoicesPage = navigateToManageInvoicesPage();
        manageInvoicesPage.searchInvoiceNumber(invoiceNum);
        manageInvoicesPage.editInvoice();
        manageInvoicesPage.calculateTax();

        //Capture tax from UI
        String tax = manageInvoicesPage.getTotalTransTax();

        assertTrue(expectedTax.equals(tax));

        manageInvoicesPage.clickSaveAndCloseButton();

        //make sure invoice is in OSR Req? (Oseries journal perhaps?) Might not be needed
    }

    /**
     * Verify the handling of blank tax lines.
     * Check the Do Not Show NoTax Results config in o-series before running the test
     *
     * Jira Test: COERPC-9041
     */
    @Test(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ap" })
    public void apBlankTaxLineSupportTest() {
        String invoiceNum = "NOTAX_BLK_"+"ET090221";
        invoiceNum = "NOTAX_BLK_"+transactionNumber;
        String expectedTax = "1.00";

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudManageInvoicesPage manageInvoicesPage = navigateToManageInvoicesPage();
        manageInvoicesPage.searchInvoiceNumber(invoiceNum);
        manageInvoicesPage.editInvoice();
        manageInvoicesPage.calculateTax();

        //Capture tax from UI
        String tax = manageInvoicesPage.getTotalTransTax();

        assertTrue(expectedTax.equals(tax));

        manageInvoicesPage.clickSaveAndCloseButton();
    }

    /**
     * Verifies an invoice with an abbreviated Canadian province
     * ship-to location in one line item, and an uppercase province
     * ship-to value of SASKATCHEWAN in another line item is
     * processed successfully.
     *
     * Jira Test: COERPC-3216
     */
    @Test(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ap" })
    public void apCanInvAbbreviatedProvinceTest() {
        String invoiceNum = transactionNumber+"_EC1048A";
        String expectedTax = "22.00";

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudManageInvoicesPage manageInvoicesPage = navigateToManageInvoicesPage();
        manageInvoicesPage.searchInvoiceNumber(invoiceNum);
        manageInvoicesPage.editInvoice();
        manageInvoicesPage.calculateTax();
        // capture TAX from UI
        String tax = manageInvoicesPage.getTotalTransTax();

        assertTrue(expectedTax.equals(tax));

        // Add verification step for checking if the two line items
        // for Satsketchwan are in the PTDE ParternerTax_Vertex file.
    }

    /**
     * This negative test scenario verifies accounts payable invoices with amounts
     * exceeding the 13 number character limit are not processed.
     *
     * Jira Test: COERPC-3217
     */
    @Test(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ap" })
    public void apInvLargeNumberErrorsTest() {

        String invoiceNum = transactionNumber+"_EC1029A";
        String errorText = "VERTEX-0006-Generic error during tax calculation:Could not commit JPA transaction";

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudManageInvoicesPage manageInvoicesPage = navigateToManageInvoicesPage();
        manageInvoicesPage.searchInvoiceNumber(invoiceNum);
        manageInvoicesPage.editInvoice();
        boolean errorPresent = manageInvoicesPage.validateInvoiceErrors(manageInvoicesPage, errorText);

        assertTrue(errorPresent);
    }

    /**
     * Verifies invoices uploaded with an inclusive indicator result in showing
     * attributes "matched" and "Pay Calculated" within the invoices Taxes ->
     * Vertex Tax Calculation Summary section.
     *
     * Additionally, none of the items listed under Taxes -> Transaction Taxes
     * should have Self-Assessed checked off. Instead, Inclusive should be checked.
     *
     * Jira Test: COERPC-3218
     */
    @Test(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ap" })
    public void apTaxInclusiveIndicatorTest() throws InterruptedException {

        processes.validatePayablesInvoicesAP("VTX_US_BU");
        Thread.sleep(360000);
        processes.runPartnerTransactionDataExtract("VTX_US_BU");
        Thread.sleep(360000);
        processes.validatePayablesInvoicesAP("VTX_US_BU");
        Thread.sleep(360000);

        String invoiceNum1 = "TAXINCL_C01";
        String invoiceNum2 = "TAXINCL_C02";
        String invoiceNum3 = "TAXINCL_C03";
        String invoiceNum4 = "TAXINCL_C04";

        loadInitialTestPage();
        signInToHomePage();

        OracleCloudManageInvoicesPage manageInvoicesPage = navigateToManageInvoicesPage();
        manageInvoicesPage.searchInvoiceNumber(invoiceNum1);
        manageInvoicesPage.editInvoice();

        // TODO Michael Salomone assertTrue(); // Assert tax amounts and action = pay calculated

        manageInvoicesPage = navigateToManageInvoicesPage();
        manageInvoicesPage.searchInvoiceNumber(invoiceNum2);
        manageInvoicesPage.editInvoice();

//        assertTrue(); // Assert tax amounts and action = pay calculated

        manageInvoicesPage = navigateToManageInvoicesPage();
        manageInvoicesPage.searchInvoiceNumber(invoiceNum3);
        manageInvoicesPage.editInvoice();

//        assertTrue(); // Assert tax amounts and action = pay calculated

        manageInvoicesPage = navigateToManageInvoicesPage();
        manageInvoicesPage.searchInvoiceNumber(invoiceNum4);
        manageInvoicesPage.editInvoice();

//        assertTrue(); // Assert tax amounts and action = pay calculated
    }

    /**
     * TODO Michael Salomone
     *
     * COERPC-
     */
    @Ignore
    @Test
    public void apExcludedSourcesTest() {

    }
}
