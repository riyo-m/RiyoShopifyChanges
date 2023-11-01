package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudApTaxCalcExclusionPage;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudCreateInvoicePage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * This class contains Oracle ERP part to verify AP Tax calc exclusion
 *
 * @author mgaikwad
 */
public class OracleCloudApTaxCalcExclusionTests extends OracleCloudBaseTest
{
	/**
	 * JIRA Issue: COERPC-9539
	 */
	@Test(groups = { "taxlink_ui_e2e_regression", "taxlink_e2e_smoke" })
	public void verifyApTaxCalcExclERPTest( )
	{
		loadInitialTestPage_ecogdev3();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		OracleCloudApSingleTransactionTests test = new OracleCloudApSingleTransactionTests();
		invoiceHeaderRequiredInfo(createInvoicePage, test.busUnitInput, test.supplierInputMccCali, null, "1,000.00",
			null, test.todaysDate, "Net 30");

		generateAndWriteInvoiceNumber(createInvoicePage, "E2E");

		createInvoicePage.clickExpandLinesButton();

		addInvoiceLineItem(createInvoicePage, 1, null, "1,000.00", test.distributionSetTestExpenseInput, null, null,
			"VTX_PA", false);

		String status = test.validateInvoice(createInvoicePage);

		if ( status.equals("Validated") )
		{
			OracleCloudApTaxCalcExclusionPage taxExCalc = new OracleCloudApTaxCalcExclusionPage(driver);
			Assert.assertTrue(taxExCalc.verifyTaxRecord());
		}
		else
		{
			Assert.fail("The invoice is not validated.. Failed");
		}
	}
}