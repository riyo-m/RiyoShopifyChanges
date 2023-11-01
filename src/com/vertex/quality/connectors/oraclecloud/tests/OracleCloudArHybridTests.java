package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.keywords.api.DataKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.DatabaseKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.ProcessesKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.TemplateKeywords;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;
import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeDbSettings;

/**
 * Container for Oracle Cloud hybrid scenarios. These scenarios
 * entail a batch data upload followed by SQL database verification
 * of expected database value.
 *
 * @author msalomone
 */
public class OracleCloudArHybridTests {

    private ProcessesKeywords processes = new ProcessesKeywords();
    private TemplateKeywords templates = new TemplateKeywords();
    private DataKeywords data = new DataKeywords();
    private DatabaseKeywords database = new DatabaseKeywords();

    /**
     * Tests combination of data into one template
     *
     * Jira Story: COERPC-5891
     */
    @BeforeTest(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ar" })
    public void arHybridSetup() throws InterruptedException
    {
        initializeApiTest();
        initializeDbSettings();
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("5-AutoInvoiceImportTemplate_US_RegTestINV_One-Offs.xlsm",
                "RaInterfaceLinesAll_SingleBatchHybrid.csv","",
                "","",
                "ArAutoinvoiceImport.zip", "AR", "");
        processes.importAutoInvoiceAR( "ArAutoinvoiceImport.zip",
                "ArAutoinvoiceImport.zip", "VTX_US_BU","MCC_LEGACY123456789123456");
        processes.importAutoInvoiceAR( "ArAutoinvoiceImport.zip",
                "ArAutoinvoiceImport.zip", "VTX_US_BU","LEGACY");
        Thread.sleep(360000);
    }

    /**
     * Test updates a trx_number in the database then verifies that
     * the AR billing sync process fires after the tax partner reporting
     * sync extract process is ran. A returns code field populated
     * indicates a successful outcome.
     *
     * Jira Test: COERPC-3265
     */
    @Test(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ar" })
    public void arReturnsCodeTest() {
        database.confirmInvoicesInOsrJournal();
        processes.taxPartnerReportingSyncExtract("VTX_US_BU", "BOTH");
        database.getInvoiceInfo();
        database.updateInvoiceInfo();
        database.verifyBillingSyncNotCalled();
    }

    /**
     * Test verifies that the billing sync process never
     * fires upon completion of running PTDE after importing
     * a batch of AR transactions.
     *
     * Jira Test: COERPC-3264
     */
    @Test(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ar" })
    public void arBillingSyncTest() {
        processes.runPartnerTransactionDataExtract("VTX_US_BU");
        processes.taxPartnerReportingSyncExtract("VTX_US_BU", "BOTH");
        database.verifyBillingSyncNotCalled();
    }

    /**
     * Test verifies that the transprocdate in the VTX9_JRNL
     * table is the current date. This behavior demonstrates
     * that transactions ran through the ERP are properly
     * mapped in the O Series LineItem table.
     *
     * Jira Test: COERPC-3294
     */
    public void arRulesMappingTest() {
        database.verifyRulesMapping();
    }

}
