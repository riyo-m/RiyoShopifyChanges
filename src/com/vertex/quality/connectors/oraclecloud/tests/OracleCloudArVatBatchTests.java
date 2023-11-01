package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.keywords.api.DataKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.ProcessesKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.TemplateKeywords;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;

public class OracleCloudArVatBatchTests
{
	private ProcessesKeywords processes = new ProcessesKeywords();
	private TemplateKeywords templates = new TemplateKeywords();
	private DataKeywords data = new DataKeywords();

	/**
	 * Generate transaction number prefix used for AR tx processing.
	 */
	@BeforeClass(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ar", "oerpc_vat" })
	public void arVatBatchSetup() throws InterruptedException {
		initializeApiTest();
		data.generateTransactionNumberPrefix();
		templates.generateCsvFromTemplate("3-AutoInvoiceImportTemplate_VAT_RegTestINV.xlsm",
			"RaInterfaceLinesAll_VatINV.csv", "","","","ArAutoinvoiceImport_VAT_Invoice.zip",
				"AR", "VTX_US_BU");
		templates.generateCsvFromTemplate("4-AutoInvoiceImportTemplate_VAT_RegTestCM.xlsm",
			"RaInterfaceLinesAll_VatCM.csv", "","","","ArAutoinvoiceImport_VAT_CM.zip",
				"AR", "VTX_US_BU");
		processes.importAutoInvoiceAR( "ArAutoinvoiceImport_VAT_Invoice.zip",
			"ArAutoinvoiceImport_VAT_Invoice.zip", "VTX_US_BU","MCC_LEGACY123456789123456");
		Thread.sleep(1200000);
	}

	/**
	 * Tests VAT Invoice transaction data through AR processing.
	 *
	 * COERPC-2790
	 */
	@Test(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ar", "oerpc_vat" })
	public void arVatInvoiceTest() {
		data.getArDataFromTables("VatINV_Results");
		templates.compareArResults("VatINV_Baseline.csv", "VatINV_Results.csv");
	}

	/**
	 * Tests VAT credit memo transaction data through AR processing.
	 *
	 * COERPC-2789
	 */
	@Test(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ar", "oerpc_vat" })
	public void arVatCreditMemoTest() throws InterruptedException
	{
		Thread.sleep(990000);
		processes.importAutoInvoiceAR( "ArAutoinvoiceImport_VAT_CM.zip",
			"ArAutoinvoiceImport_VAT_CM.zip", "VTX_US_BU","MCC_LEGACY123456789123456");
		data.getArDataFromTables("VatCM_Results");
		templates.compareArResults("VatCM_Baseline.csv", "VatCM_Results.csv");
	}
}
