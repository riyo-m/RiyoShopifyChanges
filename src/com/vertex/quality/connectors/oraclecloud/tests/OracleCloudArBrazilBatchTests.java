package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.keywords.api.DataKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.ProcessesKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.TemplateKeywords;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;

/**
 * Tests using Brazil tax data for O2C/AR calculations.
 *
 * @author msalomone
 */
public class OracleCloudArBrazilBatchTests {
    
    private ProcessesKeywords processes = new ProcessesKeywords();
    private TemplateKeywords templates = new TemplateKeywords();
    private DataKeywords data = new DataKeywords();

    /**
     * Configures test settings.
     */
    @BeforeClass(alwaysRun = true)
    private void setup() { initializeApiTest(); }

    /**
     * Tests Brazil O2C tax scenarios in bulk.
     *
     * COERPC-10953
     */
    @Test(groups = {})
    public void arBrazilInvTest() throws InterruptedException {
        data.generateTransactionNumberPrefix();
        templates.generateCsvFromTemplate("18-AutoInvoiceImportTemplate_Brazil_INV.xlsm",
                "RaInterfaceLinesAll_BrazilINV.csv","","","",
                "ArAutoinvoiceImport_Brazil_Invoice.zip","AR", "VTX_BR_BU");
        processes.importAutoInvoiceAR( "ArAutoinvoiceImport_Brazil_Invoice.zip",
                "ArAutoinvoiceImport_Brazil_Invoice.zip", "VTX_BR_BU",
                "VTX BR Manual Nm Src");
        Thread.sleep(360000);
        data.getArDataFromTables("BrazilINV_Results");
        templates.compareArResults("BrazilINV_Baseline.csv", "BrazilINV_Results.csv");
    }
}
