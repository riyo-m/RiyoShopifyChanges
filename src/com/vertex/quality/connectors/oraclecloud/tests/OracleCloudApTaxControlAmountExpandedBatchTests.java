package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.keywords.api.*;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeDbSettings;

/**
 * AP batch tests to verify 'Tax Control Amount Expanded' change
 * @author Nithin.Mathew
 */
public class OracleCloudApTaxControlAmountExpandedBatchTests {

    private ProcessesKeywords processes = new ProcessesKeywords();
    private TemplateKeywords templates = new TemplateKeywords();
    private DatabaseKeywords database = new DatabaseKeywords();
    private DataKeywords data = new DataKeywords();

    /**
     * Tests Doc level 'Tax Control Amount Expanded' data through AP processing
     * Parent Dev Story ID: COR-151
     * @author Nithin.Mathew
     */
    @Test
    public void apTaxControlAmountTest() throws InterruptedException{
        try{
            initializeDbSettings();
            database.taxControlAmountFlagSetting("N");
            data.generateTransactionNumberPrefix();
            templates.generateCsvFromTemplate("6-PayablesStandardInvoiceImportTemplate_US_CTL-HDR-AMT.xlsm",
                    "ApInvoicesInterface_US-TaxControlAmount.csv", "ApInvoiceLinesInterface_US-TaxControlAmount.csv", "", "",
                    "apinvoiceimport_US-TaxControlAmount.zip", "AP", "");
            processes.importDataPayablesInvoicesAP("apinvoiceimport_US-TaxControlAmount.zip",
                    "apinvoiceimport_US-TaxControlAmount.zip", "VTX_US_BU", "");
            Thread.sleep(240000);
            processes.validatePayablesInvoicesAP("VTX_US_BU");
            Thread.sleep(240000);
            processes.runPartnerTransactionDataExtract("VTX_US_BU");
            Thread.sleep(780000);
            processes.validatePayablesInvoicesAP("VTX_US_BU");
            Thread.sleep(780000);
            data.getApDataFromTables("ApUsTaxControlAmount_Results");
            templates.compareApResults("ApUsTaxControlAmount_Baseline.csv", "ApUsTaxControlAmount_Results.csv");
        }
        finally {
            database.taxControlAmountFlagSetting("N");
        }
    }

    /**
     * Tests Line level 'Tax Control Amount' data through AP processing
     * Parent Dev Story ID: COR-151
     * @author Nithin.Mathew
     */
    @Test
    public void apTaxControlAmountLineLevelTest() throws InterruptedException {
        try {
            initializeDbSettings();
            database.taxControlAmountFlagSetting("Y");
            database.flipUSLineLevelSetting("Y");
            data.generateTransactionNumberPrefix();
            templates.generateCsvFromTemplate("6-PayablesStandardInvoiceImportTemplate_US_CTL-HDR-AMT.xlsm",
                    "ApInvoicesInterface_US-Line-Level-TaxControlAmount.csv", "ApInvoiceLinesInterface_US-Line-Level-TaxControlAmount.csv", "", "",
                    "apinvoiceimport_US-Line-Level-TaxControlAmount.zip", "AP", "");
            processes.importDataPayablesInvoicesAP("apinvoiceimport_US-Line-Level-TaxControlAmount.zip",
                    "apinvoiceimport_US-Line-Level-TaxControlAmount.zip", "VTX_US_BU", "");
            Thread.sleep(240000);
            processes.validatePayablesInvoicesAP("VTX_US_BU");
            Thread.sleep(240000);
            processes.runPartnerTransactionDataExtract("VTX_US_BU");
            Thread.sleep(120000);
            processes.validatePayablesInvoicesAP("VTX_US_BU");
            Thread.sleep(440000);
            data.getApDataFromTables("ApUsLineLevelTaxControlAmount_Results");
            templates.compareApResults("ApUsLineLevelTaxControlAmount_Baseline.csv", "ApUsLineLevelTaxControlAmount_Results.csv");
        } finally {
            database.taxControlAmountFlagSetting("N");
            database.flipUSLineLevelSetting("N");
        }
    }

}
