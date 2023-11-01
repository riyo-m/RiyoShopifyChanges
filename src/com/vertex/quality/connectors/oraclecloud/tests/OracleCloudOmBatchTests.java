package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.keywords.api.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;

/**
 * All batch tests targeted at the Order Management portion of Oracle's ERP system.
 *
 */

public class OracleCloudOmBatchTests
{
	private ProcessesKeywords processes = new ProcessesKeywords();
	private TemplateKeywords templates = new TemplateKeywords();
	private DataKeywords data = new DataKeywords();

	/**
	 * Configures test settings.
	 */
	@BeforeClass(alwaysRun = true, groups = { "oerpc_regression", "oerpc_batch", "oerpc_om" })
	private void setup() {
		initializeApiTest();
	}

	/**
	 * Tests Sales Order Management processing in ecog-dev or ecog-test environments.
	 *
	 * Jira Test Case: COERPC-3113
	 */
	@Test( groups = { "oerpc_regression", "oerpc_batch", "oerpc_om" })
	public void omNonEhygBatchTest() throws InterruptedException
	{
		data.generateTransactionNumberPrefix();
		templates.generateCsvFromTemplate("13-SourceSalesOrderImportTemplate_20B_SOAK_file_ecog-dev1.xlsm",
			"DooOrderHeadersAllInt.csv","DooOrderLinesAllInt.csv",
			"DooOrderAddressesInt.csv","",
			"SourceSalesOrderImport.zip", "OM", "");
		processes.importDataSalesOrder( "SourceSalesOrderImport.zip",
			"SourceSalesOrderImport.zip", "VTX_US_BU", "");
		Thread.sleep(360000);
		data.getOmDataFromTables("OmBatch_results");
		templates.compareOmResults("OmBatch_Baseline.csv", "OmBatch_Results.csv");
	}

	/**
	 * Tests Sales Order Management processing in ehyg-test environment.
	 * Please change the environment and credentials in order to run this test
	 *
	 * Jira Test Case: COERPC-3113
	 */
	@Test( groups = { "oerpc_regression", "oerpc_batch", "oerpc_om" })
	public void omEhygBatchTest() throws InterruptedException
	{
		data.generateTransactionNumberPrefix();
		templates.generateCsvFromTemplate("13-SourceSalesOrderImportTemplate_20B.xlsm",
				"DooOrderHeadersAllInt.csv","DooOrderLinesAllInt.csv",
				"DooOrderAddressesInt.csv","",
				"SourceSalesOrderImport.zip", "OM", "");
		processes.importDataSalesOrder( "SourceSalesOrderImport.zip",
				"SourceSalesOrderImport.zip", "VTX_US_BU", "");
		Thread.sleep(180000);
		data.getOmDataFromTables("OmBatch_results");
		templates.compareOmResults("OmBatch-80-ehyg-test-on-prem_Baseline.csv", "OmBatch_Results.csv");
	}

	/**
	 * Tests Sales Order Management processing for Project Related Scenario
	 *
	 * Jira Test Case: COERPC-11152
	 */
	@Test( groups = { "oerpc_regression", "oerpc_batch", "oerpc_om" })
	public void omProjectRelatedBatchTest() throws InterruptedException
	{
		data.generateTransactionNumberPrefix();
		templates.generateCsvFromTemplate("13a-SourceSalesOrderImportTemplate_23B-1So4Line-with-project-ecog-test.xlsm",
			"DooOrderHeadersAllInt_ProjectRelated.csv","DooOrderLinesAllInt_ProjectRelated.csv",
			"DooOrderAddressesInt_ProjectRelated.csv","DooProjectsInt_ProjectRelated.csv",
			"SourceSalesOrderImport_ProjectRelated.zip", "OM", "");
		processes.importDataSalesOrder( "SourceSalesOrderImport_ProjectRelated.zip",
			"SourceSalesOrderImport_ProjectRelated.zip", "VTX_US_BU", "");
		Thread.sleep(180000);
		data.getOmDataFromTables("OmBatch_results");
		templates.compareOmResults("OmBatch_ProjectRelated_Baseline.csv", "OmBatch_Results.csv");
	}

}
