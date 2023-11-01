package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudCreateInvoicePage;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudOverviewPage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * This class contains Oracle ERP part of test method to verify PTDE job status in Taxlink
 *
 * @author Shilpi.Verma
 */

public class OracleCloudPTDEJobStatusTests extends OracleCloudBaseTest
{
	/**
	 * JIRA Issue: COERPC- 9659
	 * @throws IOException
	 */
	@Test(groups = { "taxlink_ui_e2e_regression" })
	public void job_PTDEstatusTest( ) throws IOException
	{
		String testId = "E2E";
		String invoiceHeader_amount = "1,000.00";
		String invoiceLine_amount = "1,000.00";
		String shipToLocation = "VTX_PA";

		loadInitialTestPage_ecogdev3();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();
		OracleCloudApSingleTransactionTests test = new OracleCloudApSingleTransactionTests();

		invoiceHeaderRequiredInfo(createInvoicePage, test.busUnitInput, test.supplierInputMccCali, null,
			invoiceHeader_amount, null, test.todaysDate, null);

		generateAndWriteInvoiceNumber(createInvoicePage, testId);

		createInvoicePage.clickExpandLinesButton();

		addInvoiceLineItem(createInvoicePage, 1, null, invoiceLine_amount, test.distributionSetTestExpenseInput, null,
			null, shipToLocation, false);

		driver.navigate().refresh();

		createInvoicePage.clickSaveButton();
		createInvoicePage.saveAndClose_Main();

		OracleCloudOverviewPage overviewPage = new OracleCloudOverviewPage(driver);
		overviewPage.scheduleProcess();

		String fileName = overviewPage.getFileName();

		writeToFile(fileName);
	}
}
