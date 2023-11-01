package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.keywords.api.DataKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.ProcessesKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.TemplateKeywords;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;
import static com.vertex.quality.connectors.oraclecloud.common.utils.Template.batchIdNumber;

/**
 * Batch scenarios used to measure performance time of end-to-end
 * tax calculation time through Oracle ERP.
 *
 * @author msalomone
 */
public class OracleCloudPerformanceTests {

    private ProcessesKeywords processes = new ProcessesKeywords();
    private TemplateKeywords templates = new TemplateKeywords();
    private DataKeywords data = new DataKeywords();

    @BeforeClass(groups = { "oerpc_batch", "oerpc_ar", "oerpc_performance" })
    private void setup() { initializeApiTest(); }

    /**
     * Tests creation of large AR invoice
     * with 1000 lines batch process
     *
     * COERPC-3686
     */
    @Test(groups = { "oerpc_batch", "oerpc_ar", "oerpc_performance" })
    public void arInvoice1000LinesTest()
    {
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("AutoInvoiceImportTemplate_1000LineInv.xlsm",
                "RaInterfaceLinesAll_1000LineInv.csv","","","",
                "ArAutoinvoiceImport.zip", "AR", "VTX_US_BU");
        processes.importAutoInvoiceAR( "ArAutoinvoiceImport.zip",
                "ArAutoinvoiceImport.zip", "VTX_US_BU","LEGACY");
        data.getArDataFromTablesPerformance("ArInvoice1000Lines_Results");
        templates.compareArResults("ArInvoice1000Lines_Baseline.csv", "ArInvoice1000Lines_Results.csv");
    }

    /**
     * Tests creation of 1 large AR invoice
     * with 5000 lines batch process.
     *
     * Jira test: COERPC-10770
     */
    @Test( groups = { "oerpc_batch", "oerpc_ap", "oerpc_performance" })
    public void arInvoice5000LinesTest() {
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("AutoInvoiceImportTemplate_5000LineInv.xlsm",
                "RaInterfaceLinesAll_5000LineInv.csv","","","",
                "ArAutoinvoiceImport.zip", "AR", "VTX_US_BU");
        processes.importAutoInvoiceAR( "ArAutoinvoiceImport.zip",
                "ArAutoinvoiceImport.zip", "VTX_US_BU","MCC_LEGACY123456789123456");
        data.getArDataFromTablesPerformance("ArInvoice5000Lines_Results");
        templates.compareArResults("ArInvoice5000Lines_Baseline.csv", "ArInvoice5000Lines_Results.csv");
    }

    /**
     * Tests Purchase Order processing performance in bulk using 1000 orders
     * each with 5 lines.
     *
     * Jira Test Case: COERPC-10958
     */
    @Test( groups = { "oerpc_regression", "oerpc_batch", "oerpc_po" })
    public void po1000OrdersTest() throws InterruptedException {
        data.generateTransactionNumberPrefix();
        data.generateBatchIdNumber();
        templates.generateCsvFromTemplate("POPurchaseOrderImportTemplate_1000POs-5-lines.xlsm",
                "PoHeadersInterfaceOrder.csv", "PoLinesInterfaceOrder.csv",
                "PoLineLocationsInterfaceOrder.csv", "PoDistributionsInterfaceOrder.csv",
                "PoImportOrders.zip", "PO", "");
        processes.importOrders("PoImportOrders.zip", "PoImportOrders.zip",
                "VTX_US_BU", "", batchIdNumber);
        Thread.sleep(2000000);
        data.getPoDataFromTablesPerformance("Po1000Orders_Results");
        templates.comparePoResults("Po1000Orders_Baseline.csv", "Po1000Orders_Results.csv");
    }

    /**
     * Tests Purchase Order processing performance in bulk using 5000 orders
     * each with 5 lines.
     *
     * Jira Test Case: COERPC-111130
     */
    @Test( groups = { "oerpc_regression", "oerpc_batch", "oerpc_po" })
    public void po5000OrdersTest() throws InterruptedException {
        data.generateTransactionNumberPrefix();
        data.generateBatchIdNumber();
        templates.generateCsvFromTemplate("POPurchaseOrderImportTemplate_5000POs-5-lines.xlsm",
                "PoHeadersInterfaceOrder.csv", "PoLinesInterfaceOrder.csv",
                "PoLineLocationsInterfaceOrder.csv", "PoDistributionsInterfaceOrder.csv",
                "PoImportOrders.zip", "PO", "");
        processes.importOrders("PoImportOrders.zip", "PoImportOrders.zip",
                "VTX_US_BU", "", batchIdNumber);
        Thread.sleep(2000000);
        data.getPoDataFromTablesPerformance("Po5000Orders_Results");
        templates.comparePoResults("Po5000Orders_Baseline.csv", "Po5000Orders_Results.csv");
    }

    /**
     * Tests Purchase Order processing performance in bulk using 10,000 orders
     * each with 5 lines.
     *
     * Jira Test Case: COERPC-111130
     */
    @Test( groups = { "oerpc_regression", "oerpc_batch", "oerpc_po" })
    public void po10000OrdersTest() throws InterruptedException {
        data.generateTransactionNumberPrefix();
        data.generateBatchIdNumber();
        templates.generateCsvFromTemplate("POPurchaseOrderImportTemplate_10000POs-5-lines.xlsm",
                "PoHeadersInterfaceOrder.csv", "PoLinesInterfaceOrder.csv",
                "PoLineLocationsInterfaceOrder.csv", "PoDistributionsInterfaceOrder.csv",
                "PoImportOrders.zip", "PO", "");
        processes.importOrders("PoImportOrders.zip", "PoImportOrders.zip",
                "VTX_US_BU", "", batchIdNumber);
        Thread.sleep(2000000);
        data.getPoDataFromTablesPerformance("Po10000Orders_Results");
        templates.comparePoResults("Po10000Orders_Baseline.csv", "Po10000Orders_Results.csv");
    }
}
