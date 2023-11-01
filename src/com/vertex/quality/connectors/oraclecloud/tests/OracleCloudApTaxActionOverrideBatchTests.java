package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.keywords.api.*;

import org.testng.annotations.Test;

import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;
import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeDbSettings;

/**
 * AP batch tests to verify 'Tax Action Override' change
 * @author Nithin.Mathew
 */
public class OracleCloudApTaxActionOverrideBatchTests {

    private ProcessesKeywords processes = new ProcessesKeywords();
    private TemplateKeywords templates = new TemplateKeywords();
    private DatabaseKeywords database = new DatabaseKeywords();
    private DataKeywords data = new DataKeywords();

    /**
     * Tests Doc level 'Tax Action Override' data through AP processing
     * Parent Dev Story ID: COR-46
     * @author Nithin.Mathew
     */
    @Test
    public void apTaxActionOverrideTest() throws InterruptedException{
        try{
            initializeDbSettings();
            database.taxActionOverrideFlagSetting("Y");
            data.generateTransactionNumberPrefix();
            templates.generateCsvFromTemplate("6a-PayablesStandardInvoiceImportTemplate_US-TaxActionOverride.xlsm",
                    "ApInvoicesInterface_US-TaxActionOverride.csv", "ApInvoiceLinesInterface_US-TaxActionOverride.csv", "", "",
                    "apinvoiceimport_US-TaxActionOverride.zip", "AP", "");
            processes.importDataPayablesInvoicesAP("apinvoiceimport_US-TaxActionOverride.zip",
                    "apinvoiceimport_US-TaxActionOverride.zip", "VTX_US_BU", "");
            Thread.sleep(240000);
            processes.validatePayablesInvoicesAP("VTX_US_BU");
            Thread.sleep(240000);
            processes.runPartnerTransactionDataExtract("VTX_US_BU");
            Thread.sleep(120000);
            processes.validatePayablesInvoicesAP("VTX_US_BU");
            Thread.sleep(440000);
            data.getApDataFromTables("ApUsTaxActionOverride_Results");
            templates.compareApResults("ApUsTaxActionOverride_Baseline.csv", "ApUsTaxActionOverride_Results.csv");
        }
        finally {
            database.taxActionOverrideFlagSetting("N");
        }
    }

    /**
     * Tests Line level 'Tax Action Override' data through AP processing
     * Parent Dev Story ID: COR-46
     * @author Nithin.Mathew
     */
    @Test
    public void apTaxActionOverrideLineLevelTest() throws InterruptedException {
        try {
            initializeDbSettings();
            database.taxActionOverrideFlagSetting("Y");
            database.flipUSLineLevelSetting("Y");
            data.generateTransactionNumberPrefix();
            templates.generateCsvFromTemplate("6b-PayablesStandardInvoiceImportTemplate_US-Line-Level-TaxActionOverride.xlsm",
                    "ApInvoicesInterface_US-Line-Level-TaxActionOverride.csv", "ApInvoiceLinesInterface_US-Line-Level-TaxActionOverride.csv", "", "",
                    "apinvoiceimport_US-Line-Level-TaxActionOverride.zip", "AP", "");
            processes.importDataPayablesInvoicesAP("apinvoiceimport_US-Line-Level-TaxActionOverride.zip",
                    "apinvoiceimport_US-Line-Level-TaxActionOverride.zip", "VTX_US_BU", "");
            Thread.sleep(240000);
            processes.validatePayablesInvoicesAP("VTX_US_BU");
            Thread.sleep(240000);
            processes.runPartnerTransactionDataExtract("VTX_US_BU");
            Thread.sleep(120000);
            processes.validatePayablesInvoicesAP("VTX_US_BU");
            Thread.sleep(440000);
            data.getApDataFromTables("ApUsLineLevelTaxActionOverride_Results");
            templates.compareApResults("ApUsLineLevelTaxActionOverride_Baseline.csv", "ApUsLineLevelTaxActionOverride_Results.csv");
        } finally {
            database.taxActionOverrideFlagSetting("N");
            database.flipUSLineLevelSetting("N");
        }
    }

}
