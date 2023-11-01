package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.keywords.api.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;

/**
 * All batch tests targeted at the Requisition portion of Oracle's ERP system.
 *
 */

public class OracleCloudRequisitionBatchTests
{
	private ProcessesKeywords processes = new ProcessesKeywords();
	private TemplateKeywords templates = new TemplateKeywords();
	private DataKeywords data = new DataKeywords();

	/**
	 * Configures test settings.
	 */
	@BeforeClass(alwaysRun = true, groups = {"oerpc_regression", "oerpc_batch", "oerpc_req"})
	private void setup() {
		initializeApiTest();
	}

	/**
	 * Tests Requisition processing.
	 *
	 * Jira Test Case: COERPC-2998
	 */
	@Test(groups = {"oerpc_regression", "oerpc_batch", "oerpc_req"})
	public void requisitionBatchTest() throws InterruptedException {
		data.generateTransactionNumberPrefix();
		templates.generateCsvFromTemplate("11-RequisitionImportTemplate.xlsm",
			"PorReqHeadersInterfaceAll.csv", "PorReqLinesInterfaceAll.csv",
			"PorReqDistsInterfaceAll.csv","",
			"PorImportRequisitions.zip","REQUISITION", "");
		processes.importRequisitions( "PorImportRequisitions.zip",
			"PorImportRequisitions.zip", "VTX_US_BU","", "418201");
		Thread.sleep(360000);
		data.getRequisitionDataFromTables("RequisitionBatch_Results");
		templates.compareRequisitionResults("RequisitionBatch_Baseline.csv", "RequisitionBatch_Results.csv");
	}
}
