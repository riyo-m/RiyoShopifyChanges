package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.keywords.api.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;
import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeDbSettings;

/**
 * All batch tests targeted at the AP portion of Oracle's ERP system.
 *
 */

public class OracleCloudApBatchTests
{
	private ProcessesKeywords processes = new ProcessesKeywords();
	private TemplateKeywords templates = new TemplateKeywords();
	private DataKeywords data = new DataKeywords();
	private DatabaseKeywords database = new DatabaseKeywords();

	/**
	 * Configures test settings.
	 */
	@BeforeClass(alwaysRun = true, groups = { "oerpc_regression", "oerpc_batch", "oerpc_ap" })
	private void setup() {
		initializeApiTest();
	}

	/**
	 * Tests Canada transaction data through AP processing.
	 *
	 * COERPC-2792
	 */
	@Test(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ap" })
	public void apCanadaBuTest() throws InterruptedException {
		data.generateTransactionNumberPrefix();
		templates.generateCsvFromTemplate("7-PayablesStandardInvoiceImportTemplate_CA_BU.xlsm",
			"ApInvoicesInterfaceCA_BU.csv","ApInvoiceLinesInterfaceCA_BU.csv","","",
				"apinvoiceimportCA_BU.zip","AP", "");
		processes.importDataPayablesInvoicesAP( "apinvoiceimportCA_BU.zip",
				"apinvoiceimportCA_BU.zip","VTX_CA_BU", "External");
		Thread.sleep(240000);
		processes.validatePayablesInvoicesAP("VTX_CA_BU");
		Thread.sleep(300000);
		processes.runPartnerTransactionDataExtract("VTX_CA_BU");
		Thread.sleep(120000);
		processes.validatePayablesInvoicesAP("VTX_CA_BU");
		Thread.sleep(1080000);
		data.getApDataFromTables("CanadaBu_Results");
		templates.compareApResults("CanadaBu_Baseline.csv", "CanadaBu_Results.csv");
	}

	/**
	 * Tests transaction data using the India BU through AP processing.
	 *
	 * COERPC-9363
	 */
	@Test(groups = { "oerpc_regression", "oerpc_batch", "oerpc_ap" })
	public void apIndiaTest() throws InterruptedException {
		data.generateTransactionNumberPrefix();
		templates.generateCsvFromTemplate("17-PayablesStandardInvoiceImportTemplate_India.xlsm",
				"ApInvoicesInterface_India.csv","ApInvoiceLinesInterface_India.csv","","",
				"apinvoiceimport_India.zip","AP", "");
		processes.importDataPayablesInvoicesAP( "apinvoiceimport_India.zip",
				"apinvoiceimport_India.zip","VTX_IN_BU", "External");
		Thread.sleep(240000);
		processes.validatePayablesInvoicesAP("VTX_IN_BU");
		Thread.sleep(120000);
		processes.runPartnerTransactionDataExtract("VTX_IN_BU");
		Thread.sleep(120000);
		processes.validatePayablesInvoicesAP("VTX_IN_BU");
		Thread.sleep(1080000);
		data.getApDataFromTables("ApIndia_Results");
		templates.compareApResults("ApIndia_Baseline.csv", "ApIndia_Results.csv");
	}

	/**
	 * Tests US Doc Level transaction data through AP processing.
	 *
	 * COERPC-2992
	 */
	@Test( groups = { "oerpc_regression", "oerpc_batch", "oerpc_ap" })
	public void apUsDocLevelTest() throws InterruptedException {
		data.generateTransactionNumberPrefix();
		templates.generateCsvFromTemplate("6-PayablesStandardInvoiceImportTemplate_US_DocLevel.xlsm",
			"ApInvoicesInterface_UsDocLevel.csv","ApInvoiceLinesInterface_UsDocLevel.csv","","",
				"apinvoiceimport_UsDocLevel.zip", "AP", "");
		processes.importDataPayablesInvoicesAP( "apinvoiceimport_UsDocLevel.zip",
			"apinvoiceimport_UsDocLevel.zip", "VTX_US_BU", "External");
		Thread.sleep(280000);
		processes.importPayablesInvoicesAP("VTX_US_BU", "ERS");
		Thread.sleep(120000);
		processes.importPayablesInvoicesAP("VTX_US_BU", "Ignore");
		Thread.sleep(180000);
		processes.validatePayablesInvoicesAP("VTX_US_BU");
		Thread.sleep(120000);
		processes.runPartnerTransactionDataExtract("VTX_US_BU");
		Thread.sleep(1100000);
		processes.validatePayablesInvoicesAP("VTX_US_BU");
		Thread.sleep(1200000);
		ArrayList<String> holdIds = processes.getHolds();
		processes.removeHold(holdIds);
		Thread.sleep(60000);
		data.getApErrorDataFromTables("ApUsDocLevel_ErrorData_Results");
		data.getApDataFromTables("ApUsDocLevel_Results");
		templates.compareApErrorResults("ApUsDocLevel_ErrorData_Baseline.csv","ApUsDocLevel_ErrorData_Results.csv");
		templates.compareApResults("ApUsDocLevel_Baseline.csv", "ApUsDocLevel_Results.csv");
	}

	/**
	 * Tests US Line Level transaction data through AP processing.
	 *
	 * COERPC-2993
	 */
	@Test( groups = { "oerpc_regression", "oerpc_batch", "oerpc_ap" })
	public void apUsLineLevelTest() throws InterruptedException {
		try{
			initializeDbSettings();
			database.flipUSLineLevelSetting("Y");
			data.generateTransactionNumberPrefix();
			templates.generateCsvFromTemplate("10-PayablesStandardInvoiceImportTemplate_US_LineLevel.xlsm",
					"ApInvoicesInterface_UsLineLevel.csv", "ApInvoiceLinesInterface_UsLineLevel.csv", "", "",
					"apinvoiceimport_UsLineLevel.zip", "AP", "");
			processes.importDataPayablesInvoicesAP("apinvoiceimport_UsLineLevel.zip",
					"apinvoiceimport_UsLineLevel.zip", "VTX_US_BU", "");
			Thread.sleep(240000);
			processes.validatePayablesInvoicesAP("VTX_US_BU");
			Thread.sleep(120000);
			processes.runPartnerTransactionDataExtract("VTX_US_BU");
			Thread.sleep(120000);
			processes.validatePayablesInvoicesAP("VTX_US_BU");
			Thread.sleep(240000);
			data.getApDataFromTables("ApUsLineLevel_Results");
			templates.compareApResults("ApUsLineLevel_Baseline.csv", "ApUsLineLevel_Results.csv");
		}
		finally {
			database.flipUSLineLevelSetting("N");
		}
	}

	/**
	 * Tests VAT invoice data through AP processing.
	 *
	 * COERPC-2994
	 */
	@Test( groups = { "oerpc_regression", "oerpc_batch", "oerpc_ap" })
	public void apVatTest() throws InterruptedException {
		data.generateTransactionNumberPrefix();
		templates.generateCsvFromTemplate("8-PayablesStandardInvoiceImportTemplate_VAT.xlsm",
			"ApInvoicesInterface_VAT.csv","ApInvoiceLinesInterface_VAT.csv", "","",
				"apinvoiceimport_Vat.zip","AP", "");
		processes.importDataPayablesInvoicesAP( "apinvoiceimport_Vat.zip",
			"apinvoiceimport_Vat.zip","VTX_US_BU", "");
		Thread.sleep(280000);
		processes.validatePayablesInvoicesAP("VTX_US_BU");
		Thread.sleep(120000);
		processes.runPartnerTransactionDataExtract("VTX_US_BU");
		Thread.sleep(280000);
		processes.validatePayablesInvoicesAP("VTX_US_BU");
		Thread.sleep(1200000);
		ArrayList<String> holdIds = processes.getHolds();
		processes.removeHold(holdIds);
		data.getApDataFromTables("ApVat_Results");
		templates.compareApResults("ApVat_Baseline.csv", "ApVat_Results.csv");
	}
}
