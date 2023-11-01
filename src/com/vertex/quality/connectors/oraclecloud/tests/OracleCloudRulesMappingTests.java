package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.common.utils.OracleUtilities;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudCreateInvoicePage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * This class contains Oracle ERP part of test method to create the AP invoice
 * with rule name passed through the notepad file "Taxlink_OracleERP"
 * to validate the rules present in Taxlink package in db
 * (i.e. com\\vertex\\quality\\connectors\\taxlink\\ui_e2e\\tests\\TaxLinkPreCalcRulesMappingTests.java)
 *
 * @author mgaikwad
 */
public class OracleCloudRulesMappingTests extends OracleCloudBaseTest
{
	private OracleUtilities utilities = new OracleUtilities();
	String validateTextSelect = "Validate Ctrl+Alt+V";

	/**
	 * Create AP invoice with Rule name passed from taxlink text file
	 */
	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void createInvoiceRulesMappingTest( ) throws Exception
	{
		String busUnitInput = "VTX_US_BU";
		String supplierInputMccCali = "MCC Calif";
		String distributionSetTestExpenseInput = "VTX Test Expense";
		String todaysDate = utilities.getTodaysDate("M/d/yy");

		loadInitialTestPage_ecogdev3();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputMccCali, null, "1,000.00", null,
			todaysDate, null);

		passInvoiceNumberFromTextFile(createInvoicePage);

		createInvoicePage.clickExpandLinesButton();

		addInvoiceLineItem(createInvoicePage, 1, null, "1,000.00", distributionSetTestExpenseInput, null, null,
			"VTX_PA", false);

		String status = validateInvoice(createInvoicePage);

		if ( status.equals("Validated") )
		{
			VertexLogger.log("The invoice is validated!!");
			assertTrue(true, "The invoice is validated.. Success");
		}
		else
		{
			Assert.fail("The invoice is not validated.. Failed");
		}
	}

	/**
	 * Helper method
	 * Validate an unvalidated invoice
	 *
	 * @param page the create invoice page
	 *
	 * @return the validation status
	 */
	protected String validateInvoice( OracleCloudCreateInvoicePage page )
	{
		WebElement menu = page.clickInvoiceActionsButton();
		try
		{
			page.clickInvoiceActionFromMenu(menu, validateTextSelect);
		}
		catch ( StaleElementReferenceException e )
		{
			menu = page.clickInvoiceActionsButton();
			page.clickInvoiceActionFromMenu(menu, validateTextSelect);
		}

		try
		{
			page.waitForValidation();
		}
		catch ( Exception ex )
		{
			page.waitForValidationByText();
		}

		String validStatus = page.checkValidationStatus();

		return validStatus;
	}
}
