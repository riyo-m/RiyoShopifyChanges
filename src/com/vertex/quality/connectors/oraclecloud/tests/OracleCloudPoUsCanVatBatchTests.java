package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.keywords.api.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.oraclecloud.common.utils.Template.batchIdNumber;
import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;

/**
 * All batch tests targeted at the Purchase Order portion of Oracle's ERP system.
 *
 */

public class OracleCloudPoUsCanVatBatchTests
{
	private ProcessesKeywords processes = new ProcessesKeywords();
	private TemplateKeywords templates = new TemplateKeywords();
	private DataKeywords data = new DataKeywords();

	/**
	 * Configures test settings.
	 */
	@BeforeClass(alwaysRun = true, groups = {"oerpc_regression", "oerpc_batch", "oerpc_po"})
	private void setup() {
		initializeApiTest();
	}

	/**
	 * Tests Purchase Order processing.
	 *
	 * Jira Test Case: COERPC-2997
	 */
	@Test( groups = { "oerpc_regression", "oerpc_batch", "oerpc_po" })
	public void PoUsCanVatBatchTest() throws InterruptedException {
		data.generateTransactionNumberPrefix();
		data.generateBatchIdNumber();
		templates.generateCsvFromTemplate("12-POPurchaseOrderImportTemplate_20A.xlsm",
			"PoHeadersInterfaceOrder.csv", "PoLinesInterfaceOrder.csv",
			"PoLineLocationsInterfaceOrder.csv","PoDistributionsInterfaceOrder.csv",
			"PoImportOrders.zip","PO", "");
		processes.importOrders( "PoImportOrders.zip","PoImportOrders.zip",
			"VTX_US_BU","",batchIdNumber);
		System.out.println(batchIdNumber);
		Thread.sleep(400000);
		data.getPoDataFromTables("PoUsCanVatBatch_Results");
		templates.comparePoResults("PoUsCanVatBatch_Baseline.csv", "PoUsCanVatBatch_Results.csv");
	}
}
