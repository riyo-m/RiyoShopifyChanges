package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudCreateInvoicePage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * This class contains Oracle ERP part of test method to create Undercharged rule in Taxlink
 *
 * @author Shilpi.Verma
 */

public class OracleCloudApInvoice_UnderchargedTests extends OracleCloudBaseTest
{
	/**
	 * JIRA Issue-9466
	 */
	@Test(groups = { "taxlink_ui_e2e_regression", "taxlink_e2e_smoke" })
	public void underchargedRuleTest( )
	{
		String overrideTaxName = "***VENDOR CHRG TAX RATECDP2P***";
		String testId = "E2E";
		String invoiceHeader_amount = "1,060.00";
		String invoiceLine_amount = "1,000.00";
		String shipToLocation = "VTX_PA";
		String rateId = "5";

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

		WebElement taxPopup = test.openTransactionTaxPopup(createInvoicePage);

		createInvoicePage.taxPopupClickAddRow(taxPopup);
		createInvoicePage.taxPopupWriteName(taxPopup, overrideTaxName);
		createInvoicePage.taxPopupWriteRateId(taxPopup, rateId);
		test.closeEditTransactionTaxPopup(createInvoicePage, taxPopup);

		WebElement menu = createInvoicePage.clickInvoiceActionsButton();
		createInvoicePage.clickInvoiceActionFromMenu(menu, test.validateTextSelect);
		createInvoicePage.waitForValidationByText();

		String status = createInvoicePage.checkValidationStatus();
		assertEquals(status, test.invoiceValidated);

		createInvoicePage.taxCalcSummaryTable();

		String vendorChargedScenario = createInvoicePage
			.taxCalcSummaryTable_Column(9)
			.getText();
		assertEquals(vendorChargedScenario, "Under Charged");

		String taxAction = createInvoicePage
			.taxCalcSummaryTable_Column(10)
			.getText();
		assertEquals(taxAction, "Pay Calculated");

		String taxActionType = createInvoicePage
			.taxCalcSummaryTable_Column(11)
			.getText();
		assertEquals(taxActionType, "Range");

		VertexLogger.log("Column values verified in Vertex tax calculation summary table!!");
	}
}
