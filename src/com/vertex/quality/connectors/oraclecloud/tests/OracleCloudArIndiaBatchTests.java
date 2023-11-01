package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.keywords.api.*;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;

/**
 * Encompasses test scenarios for India (O2C aka AR) related taxes.
 *
 * @author msalomone
 */
public class OracleCloudArIndiaBatchTests {

    private ProcessesKeywords processes = new ProcessesKeywords();
    private TemplateKeywords templates = new TemplateKeywords();
    private DataKeywords data = new DataKeywords();

    /**
     * Tests India invoice transaction data through AR processing.
     *
     * COERPC-9256
     */
    @Test(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ar", "oerpc_us_can" })
    public void arIndiaInvoiceTest() throws InterruptedException {
        initializeApiTest();
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("15-AutoInvoiceImportTemplate_India_INV.xlsm",
                "RaInterfaceLinesAll_IndiaINV.csv","","","",
                "ArAutoinvoiceImport_India_Invoice.zip","AR", "VTX_IN_BU");
        processes.importAutoInvoiceAR( "ArAutoinvoiceImport_India_Invoice.zip",
                "ArAutoinvoiceImport_India_Invoice.zip", "VTX_IN_BU","India-FBDI-Source");
        Thread.sleep(380000);
        data.getArDataFromTables("ArIndiaINV_Results");
        templates.compareArResults("ArIndiaINV_Baseline.csv", "ArIndiaINV_Results.csv");
    }

    /**
     * Tests India Credit Memos attached to invoice transaction data through AR processing.
     *
     * COERPC-9257
     */
    @Test(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ar", "oerpc_us_can" })
    public void arIndiaCreditMemoTest() throws InterruptedException {
        initializeApiTest();
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("15-AutoInvoiceImportTemplate_India_INV.xlsm",
                "RaInterfaceLinesAll_IndiaINV.csv","","","",
                "ArAutoinvoiceImport_India_Invoice.zip","AR", "VTX_IN_BU");
        templates.generateCsvFromTemplate("16-AutoInvoiceImportTemplate_India_CM.xlsm",
                "RaInterfaceLinesAll_IndiaCM.csv","","","",
                "ArAutoinvoiceImport_India_CM.zip","AR", "VTX_IN_BU");
        processes.importAutoInvoiceAR( "ArAutoinvoiceImport_India_Invoice.zip",
                "ArAutoinvoiceImport_India_Invoice.zip", "VTX_IN_BU","India-FBDI-Source");
        Thread.sleep(380000);
        processes.importAutoInvoiceAR( "ArAutoinvoiceImport_India_CM.zip",
                "ArAutoinvoiceImport_India_CM.zip", "VTX_IN_BU","India-FBDI-Source");
        Thread.sleep(990000);
        data.getArDataFromTables("ArIndiaCM_Results");
        templates.compareArResults("ArIndiaCM_Baseline.csv", "ArIndiaCM_Results.csv");
    }

    /**
     * India GST TCS tax scenarios.
     *
     * COERPC-11000
     */
    @Test(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ar", "oerpc_us_can" })
    public void arIndiaTCSInvoiceTest() throws InterruptedException {
        initializeApiTest();
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("19-AutoInvoiceImportTemplate_India_INV-TCS.xlsm",
                "RaInterfaceLinesAll_IndiaINV.csv","","","",
                "ArAutoinvoiceImport_India_TCS_Invoice.zip","AR", "VTX_IN_BU");
        processes.importAutoInvoiceAR( "ArAutoinvoiceImport_India_TCS_Invoice.zip",
                "ArAutoinvoiceImport_India_TCS_Invoice.zip", "VTX_IN_BU","India-FBDI-Source");
        Thread.sleep(380000);
        data.getArDataFromTables("ArIndiaTcsINV_Results");
        templates.compareArResults("ArIndiaTcsINV_Baseline.csv", "ArIndiaTcsINV_Results.csv");
    }
}
