package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.keywords.api.*;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;

/**
 * All batch tests targeted at the AR portion of Oracle's ERP system.
 *
 * @author msalomone
 */
public class OracleCloudArUsCanBatchTests
{
	private ProcessesKeywords processes = new ProcessesKeywords();
	private TemplateKeywords templates = new TemplateKeywords();
	private DataKeywords data = new DataKeywords();

	/**
	 * 1. Generate transaction number prefix used for AR tx processing.
	 * 2. Update Invoice and CM templates, and upload data to ERP.
	 */
	@BeforeClass(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ar", "oerpc_us_can" })
	public void arBatchSetup() {
		initializeApiTest();
		data.generateTransactionNumberPrefix();
		templates.generateCsvFromTemplate("1-AutoInvoiceImportTemplate_US-CAN_RegTestINV.xlsm",
			"RaInterfaceLinesAll_UsCanINV.csv","","","",
				"ArAutoinvoiceImport_UsCan_Invoice.zip","AR", "VTX_US_BU");
		templates.generateCsvFromTemplate("2-AutoInvoiceImportTemplate_US-CAN_RegTestCM.xlsm",
			"RaInterfaceLinesAll_UsCanCM.csv","","","",
				"ArAutoinvoiceImport_UsCan_CM.zip","AR", "VTX_US_BU");
		processes.importAutoInvoiceAR( "ArAutoinvoiceImport_UsCan_Invoice.zip",
			"ArAutoinvoiceImport_UsCan_Invoice.zip", "VTX_US_BU","MCC_LEGACY123456789123456");
	}
	/**
	 * Tests US and Canada invoice transaction data through AR processing.
	 *
	 * COERPC-2996
	 */
	@Test(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ar", "oerpc_us_can" })
	public void arUsCanInvoiceTest() throws InterruptedException {
		Thread.sleep(360000);
		data.getArDataFromTables("ArUsCanINV_Results");
		templates.compareArResults("ArUsCanINV_Baseline.csv", "ArUsCanINV_Results.csv");
	}

	/**
	 * Tests US and Canada credit memo transaction data through AR processing.
	 *
	 * COERPC-2995
	 */
	@Test(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ar", "oerpc_us_can" })
	public void arUsCanCreditMemoTest() throws InterruptedException {
		Thread.sleep(990000);
		processes.importAutoInvoiceAR( "ArAutoinvoiceImport_UsCan_CM.zip",
			"ArAutoinvoiceImport_UsCan_CM.zip", "VTX_US_BU","MCC_LEGACY123456789123456");
		data.getArDataFromTables("ArUsCanCM_Results");
		templates.compareArResults("ArUsCanCM_Baseline.csv", "ArUsCanCM_Results.csv");
	}
}
