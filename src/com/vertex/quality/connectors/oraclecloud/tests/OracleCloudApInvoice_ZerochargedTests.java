package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudCreateInvoicePage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * This class contains test for creating and validating invoice after resolving hold for Zerocharged tax actions
 *
 * @author Shilpi.Verma
 */

public class OracleCloudApInvoice_ZerochargedTests extends OracleCloudBaseTest
{

	/**
	 * This test is currently written only till invoice validation
	 * as there is an ongoing issue with resolving hold in OERP
	 */
	@Test(groups = { "taxlink_ui_e2e_regression", "taxlink_e2e_smoke" })
	public void zerochargedRuleTest( )
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

		createInvoicePage.clickSaveButton();

		WebElement menu = createInvoicePage.clickInvoiceActionsButton();
		createInvoicePage.clickInvoiceActionFromMenu(menu, test.validateTextSelect);
		createInvoicePage.waitForNeedsRevalidation();

		String revalidationStatus = createInvoicePage.checkValidationStatus();
		assertEquals(revalidationStatus, test.invoiceNeedsRevalidation);

		WebElement menuHold = createInvoicePage.clickInvoiceActionsButton();
		createInvoicePage.clickInvoiceActionFromMenu(menuHold, test.manageHolds);

		createInvoicePage.resolveHolds();

		String validationStatus = createInvoicePage.checkValidationStatus();
		assertEquals(validationStatus, test.invoiceValidated);

		createInvoicePage.clickExpandTaxesButton();
		createInvoicePage.taxCalcSummaryTable();

		String vendorChargedScenario = createInvoicePage
			.taxCalcSummaryTable_Column(9)
			.getText();
		assertEquals(vendorChargedScenario, "Zero Charged");

		String taxAction = createInvoicePage
			.taxCalcSummaryTable_Column(10)
			.getText();
		assertEquals(taxAction, "Accrue All");

		String taxActionType = createInvoicePage
			.taxCalcSummaryTable_Column(11)
			.getText();
		assertEquals(taxActionType, "Range");

	}
}
