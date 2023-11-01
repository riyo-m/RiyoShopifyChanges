package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.keywords.api.DataKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.ProcessesKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.TemplateKeywords;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;
import static com.vertex.quality.connectors.oraclecloud.common.utils.Template.batchIdNumber;

/**
 * Accounts Payable batch scenarios used to measure performance time of end-to-end
 * tax calculation through Oracle ERP.
 *
 * @author msalomone
 */
public class OracleCloudApPerformanceTests {

    private ProcessesKeywords processes = new ProcessesKeywords();
    private TemplateKeywords templates = new TemplateKeywords();
    private DataKeywords data = new DataKeywords();

    @BeforeClass(groups = { "oerpc_batch", "oerpc_ar", "oerpc_performance" })
    private void setup() { initializeApiTest(); }

    /**
     * Tests creation of large AP invoice
     * with 1000 lines batch process
     *
     * Jira test: COERPC-3725
     */
    @Test( groups = { "oerpc_batch", "oerpc_ap", "oerpc_performance" })
    public void apInvoice1000LinesTest() throws InterruptedException {
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("PayablesStandardInvoiceImportTemplate_US_1000LineInv.xlsm",
                "ApInvoicesInterface_1000Lines.csv","ApInvoiceLinesInterface_1000Lines.csv", "","",
                "apinvoiceimport_1000Lines.zip","AP", "");
        processes.importDataPayablesInvoicesAP( "apinvoiceimport_1000Lines.zip",
                "apinvoiceimport_1000Lines.zip","VTX_US_BU", "");
        Thread.sleep(300000);
        processes.validatePayablesInvoicesAP("VTX_US_BU");
        Thread.sleep(1200000);
        processes.runPartnerTransactionDataExtract("VTX_US_BU");
        Thread.sleep(2800000);
        processes.validatePayablesInvoicesAP("VTX_US_BU");
        data.getApDataFromTablesPerformance("ApInvoice1000Lines_Results");
        templates.compareApResults("ApInvoice1000Lines_Baseline.csv", "ApInvoice1000Lines_Results.csv");
    }

    /**
     * Tests calculation of 1000 invoices
     * each with 10 line items.
     *
     * Jira test:COERPC-11168
     */
    @Test( groups = { "oerpc_batch", "oerpc_ap", "oerpc_performance" })
    public void apInvoice1000Invoices10LinesTest() throws InterruptedException {
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("PayablesStandardInvoiceImportTemplate_1000Inv-10-lines.xlsm",
                "ApInvoicesInterface_1000Inv10Lines.csv","ApInvoiceLinesInterface_1000Inv10Lines.csv", "","",
                "apinvoiceimport_1000Inv10Lines.zip","AP", "");
        processes.importDataPayablesInvoicesAP( "apinvoiceimport_1000Inv10Lines.zip",
                "apinvoiceimport_1000Inv10Lines.zip","VTX_US_BU", "");
        Thread.sleep(300000);
        processes.validatePayablesInvoicesAP("VTX_US_BU");
        Thread.sleep(1200000);
        processes.runPartnerTransactionDataExtract("VTX_US_BU");
        Thread.sleep(2800000);
        processes.validatePayablesInvoicesAP("VTX_US_BU");
        data.getApDataFromTablesPerformance("ApInvoice1000Lines_Results");
        templates.compareApResults("ApInvoice1000Inv10Lines_Baseline.csv",
                "ApInvoice1000Inv10Lines_Results.csv");
    }

    /**
     * Tests calculation of 400 invoices
     * each with 10 line items.
     *
     * Jira test: COERPC-11169
     */
    @Test( groups = { "oerpc_batch", "oerpc_ap", "oerpc_performance" })
    public void apInvoice400Invoices10LinesTest() throws InterruptedException {
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("PayablesStandardInvoiceImportTemplate_400Inv-10-lines.xlsm",
                "ApInvoicesInterface_1000Lines.csv","ApInvoiceLinesInterface_1000Lines.csv", "","",
                "apinvoiceimport_1000Lines.zip","AP", "");
        processes.importDataPayablesInvoicesAP( "apinvoiceimport_1000Lines.zip",
                "apinvoiceimport_1000Lines.zip","VTX_US_BU", "");
        Thread.sleep(300000);
        processes.validatePayablesInvoicesAP("VTX_US_BU");
        Thread.sleep(1200000);
        processes.runPartnerTransactionDataExtract("VTX_US_BU");
        Thread.sleep(2800000);
        processes.validatePayablesInvoicesAP("VTX_US_BU");
        data.getApDataFromTablesPerformance("ApInvoice400Lines_Results");
        templates.compareApResults("ApInvoice1000Lines_Baseline.csv", "ApInvoice400Lines_Results.csv");
    }

    /**
     * Tests calculation of 300 invoices
     * each with 10 line items.
     *
     * Jira test: COERPC-11170
     */
    @Test( groups = { "oerpc_batch", "oerpc_ap", "oerpc_performance" })
    public void apInvoice300Invoices10LinesTest() throws InterruptedException {
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("PayablesStandardInvoiceImportTemplate_300Inv-10-lines.xlsm",
                "ApInvoicesInterface_1000Lines.csv","ApInvoiceLinesInterface_1000Lines.csv", "","",
                "apinvoiceimport_1000Lines.zip","AP", "");
        processes.importDataPayablesInvoicesAP( "apinvoiceimport_1000Lines.zip",
                "apinvoiceimport_1000Lines.zip","VTX_US_BU", "");
        Thread.sleep(300000);
        processes.validatePayablesInvoicesAP("VTX_US_BU");
        Thread.sleep(1200000);
        processes.runPartnerTransactionDataExtract("VTX_US_BU");
        Thread.sleep(2800000);
        processes.validatePayablesInvoicesAP("VTX_US_BU");
        data.getApDataFromTablesPerformance("ApInvoice300Lines_Results");
        templates.compareApResults("ApInvoice1000Lines_Baseline.csv", "ApInvoice300Lines_Results.csv");
    }

    /**
     * Tests calculation of 200 invoices
     * each with 10 line items.
     *
     * Jira test: COERPC-11171
     */
    @Test( groups = { "oerpc_batch", "oerpc_ap", "oerpc_performance" })
    public void apInvoice200Invoices10LinesTest() throws InterruptedException {
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("PayablesStandardInvoiceImportTemplate_200Inv-10-lines.xlsm",
                "ApInvoicesInterface_1000Lines.csv","ApInvoiceLinesInterface_1000Lines.csv", "","",
                "apinvoiceimport_1000Lines.zip","AP", "");
        processes.importDataPayablesInvoicesAP( "apinvoiceimport_1000Lines.zip",
                "apinvoiceimport_1000Lines.zip","VTX_US_BU", "");
        Thread.sleep(300000);
        processes.validatePayablesInvoicesAP("VTX_US_BU");
        Thread.sleep(1200000);
        processes.runPartnerTransactionDataExtract("VTX_US_BU");
        Thread.sleep(2800000);
        processes.validatePayablesInvoicesAP("VTX_US_BU");
        data.getApDataFromTablesPerformance("ApInvoice200Lines_Results");
        templates.compareApResults("ApInvoice1000Lines_Baseline.csv", "ApInvoice200Lines_Results.csv");
    }

    /**
     * Tests calculation of 100 invoices
     * each with 10 line items.
     *
     * Jira test: COERPC-11172
     */
    @Test( groups = { "oerpc_batch", "oerpc_ap", "oerpc_performance" })
    public void apInvoice100Invoices10LinesTest() throws InterruptedException {
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("PayablesStandardInvoiceImportTemplate_100Inv-10-lines.xlsm",
                "ApInvoicesInterface_1000Lines.csv","ApInvoiceLinesInterface_1000Lines.csv", "","",
                "apinvoiceimport_1000Lines.zip","AP", "");
        processes.importDataPayablesInvoicesAP( "apinvoiceimport_1000Lines.zip",
                "apinvoiceimport_1000Lines.zip","VTX_US_BU", "");
        Thread.sleep(300000);
        processes.validatePayablesInvoicesAP("VTX_US_BU");
        Thread.sleep(1200000);
        processes.runPartnerTransactionDataExtract("VTX_US_BU");
        Thread.sleep(2800000);
        processes.validatePayablesInvoicesAP("VTX_US_BU");
        data.getApDataFromTablesPerformance("ApInvoice1000Lines_Results");
        templates.compareApResults("ApInvoice1000Lines_Baseline.csv", "ApInvoice1000Lines_Results.csv");
    }
}
