package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.keywords.api.DataKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.ProcessesKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.TemplateKeywords;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;

/**
 * Container for small scenarios aimed at smoke testing
 * an ERP machine pointed to a VOD instance.
 *
 * @author msalomone
 */
public class OracleCloudVodBatchTests {
    private ProcessesKeywords processes = new ProcessesKeywords();
    private TemplateKeywords templates = new TemplateKeywords();
    private DataKeywords data = new DataKeywords();

    /**
     * Configures test settings.
     */
    @BeforeClass(alwaysRun = true, groups = { "oerpc_vod", "oerpc_batch"})
    private void setup() {
        initializeApiTest();
    }

    /**
     * Test designed for smoke testing AR invoice processing
     * on a VOD instance.
     *
     * Jira test: COERPC-3251
     */
    @Test( groups = { "oerpc_vod", "oerpc_batch", "oerpc_ar" })
    public void arInvVodTest() throws InterruptedException {
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("VOD/1-AutoInvoiceImportTemplate_US_RegTestINV_VOD.xlsm",
                "RaInterfaceLinesAll_INV_VOD.csv","","","",
                "ArAutoinvoiceImport_INV_VOD.zip", "AR", "VTX_US_BU");
        processes.importAutoInvoiceAR( "ArAutoinvoiceImport_INV_VOD.zip",
                "ArAutoinvoiceImport_INV_VOD.zip", "VTX_US_BU","MCC_LEGACY123456789123456");
        Thread.sleep(480000);
        data.getArDataFromTables("ArInvoiceVOD_Results");
        templates.compareArResults("ArInvoiceVOD_Baseline.csv", "ArInvoiceVOD_Results.csv");
    }

    /**
     * Test designed for smoke testing AP processing on a VOD instance.
     *
     * Jira test: COERPC-3250
     */
    @Test( groups = { "oerpc_vod", "oerpc_batch", "oerpc_ap" })
    public void apInvVodTest() throws InterruptedException {
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("VOD/3-PayablesStandardInvoiceImportTemplate_US_VOD.xlsm",
                "ApInvoicesInterface_VOD.csv","ApInvoiceLinesInterface_VOD.csv", "","",
                "apinvoiceimport_Vod.zip","AP", "");
        processes.importDataPayablesInvoicesAP( "apinvoiceimport_Vod.zip",
                "apinvoiceimport_Vod.zip","VTX_US_BU", "");
        Thread.sleep(280000);
        processes.validatePayablesInvoicesAP("VTX_US_BU");
        Thread.sleep(480000);
        processes.runPartnerTransactionDataExtract("VTX_US_BU");
        Thread.sleep(3000000);
        processes.validatePayablesInvoicesAP("VTX_US_BU");
        Thread.sleep(280000);
        data.getApDataFromTables("ApVOD_Results");
        templates.compareApResults("ApVOD_Baseline.csv", "ApVOD_Results.csv");
    }

    /**
     * Test designed for smoke testing AR credit memo processing
     * on a VOD instance.
     *
     * Jira test: COERPC-3266
     */
    @Test( groups = { "oerpc_vod", "oerpc_batch", "oerpc_ar" })
    public void arCreditMemoVodTest() throws InterruptedException {
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("VOD/1-AutoInvoiceImportTemplate_US_RegTestINV_VOD.xlsm",
                "RaInterfaceLinesAll_INV_VOD.csv","","","",
                "ArAutoinvoiceImport_INV_VOD.zip", "AR", "VTX_US_BU");
        templates.generateCsvFromTemplate("VOD/2-AutoInvoiceImportTemplate_US_RegTestCM_VOD.xlsm",
                "RaInterfaceLinesAll_CM_VOD.csv","","","",
                "ArAutoinvoiceImport_CM_VOD.zip", "AR", "VTX_US_BU");
        processes.importAutoInvoiceAR( "ArAutoinvoiceImport_INV_VOD.zip",
                "ArAutoinvoiceImport_INV_VOD.zip", "VTX_US_BU","MCC_LEGACY123456789123456");
        Thread.sleep(1850000); // Wait 32 minutes before kicking off CM.
        processes.importAutoInvoiceAR( "ArAutoinvoiceImport_CM_VOD.zip",
                "ArAutoinvoiceImport_CM_VOD.zip", "VTX_US_BU","MCC_LEGACY123456789123456");
        Thread.sleep(480000);
        data.getArDataFromTables("ArCreditMemoVOD_Results");
        templates.compareArResults("ArCreditMemoVOD_Baseline.csv", "ArCreditMemoVOD_Results.csv");
    }

    /**
     * Test designed for smoke testing Sales Order Management processing
     * on a VOD instance.
     *
     * Jira test: COERPC-3267
     */
    @Test( groups = { "oerpc_vod", "oerpc_batch", "oerpc_om" })
    public void omVodTest() throws InterruptedException {
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("VOD/6-SourceSalesOrderImportTemplate_VOD.xlsm",
                "DooOrderHeadersAllInt_VOD.csv","DooOrderLinesAllInt_VOD.csv",
                "DooOrderAddressesInt_VOD.csv","",
                "SourceSalesOrderImport_VOD.zip", "OM", "");
        processes.importDataSalesOrder( "SourceSalesOrderImport_VOD.zip",
                "SourceSalesOrderImport_VOD.zip", "VTX_US_BU", "");
        Thread.sleep(360000);
        data.getOmDataFromTables("OmBatchVOD_Results");
        templates.compareOmResults("OmBatchVOD_Baseline.csv", "OmBatchVOD_Results.csv");
    }

    /**
     * Test designed for smoke testing Purchase Order processing
     * on a VOD instance.
     *
     * Jira test: COERPC-3274
     */
    @Test(  groups = { "oerpc_vod", "oerpc_batch", "oerpc_po" })
    public void poVodTest() throws InterruptedException {
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("VOD/5-PurchaseOrderImportTemplate_VOD.xlsm",
                "PoHeadersInterfaceOrder_VOD.csv", "PoLinesInterfaceOrder_VOD.csv",
                "PoLineLocationsInterfaceOrder_VOD.csv","PoDistributionsInterfaceOrder_VOD.csv",
                "PoImportOrders_VOD.zip","PO", "");
        processes.importOrders( "PoImportOrders_VOD.zip","PoImportOrders_VOD.zip",
                "VTX_US_BU","","20200602006");
        Thread.sleep(360000);
        data.getPoDataFromTables("PoUsCanVatBatchVOD_Results");
        templates.comparePoResults("PoUsCanVatBatchVOD_Baseline.csv", "PoUsCanVatBatchVOD_Results.csv");
    }

    /**
     * Test designed for smoke testing Requisition Order processing
     * on a VOD instance.
     *
     * Jira test: COERPC-3276
     */
    @Test( groups = { "oerpc_vod", "oerpc_batch", "oerpc_req" })
    public void reqVodTest() throws InterruptedException {
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("VOD/4-RequisitionImportTemplate_VOD.xlsm",
                "PorReqHeadersInterfaceAll_VOD.csv", "PorReqLinesInterfaceAll_VOD.csv",
                "PorReqDistsInterfaceAll_VOD.csv","",
                "PorImportRequisitions_VOD.zip","REQUISITION", "");
        processes.importRequisitions( "PorImportRequisitions_VOD.zip",
                "PorImportRequisitions_VOD.zip", "VTX_US_BU","","20202901001");
        Thread.sleep(430000);
        data.getRequisitionDataFromTables("RequisitionBatchVOD_Results");
        templates.compareRequisitionResults("RequisitionBatchVOD_Baseline.csv", "RequisitionBatchVOD_Results.csv");
    }
}
