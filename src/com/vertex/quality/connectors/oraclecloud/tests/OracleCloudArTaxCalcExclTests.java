package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudCreateTransactionPageFieldData;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudCreateTransactionPage;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudReviewTransactionPage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Runs the standard one-off AR tests that should be
 * performed to check no tax has calculated for the
 * AR tax calc set from Taxlink -AR tax calc excl page
 *
 * @author mgaikwad
 */

public class OracleCloudArTaxCalcExclTests extends OracleCloudBaseTest
{

	public final String transClassSelect = "Invoice";
	public final String busUnitInput = "VTX_US_BU";
	public final String transSourceInput = "Manual";
	public final String transTypeInput = "Invoice";
	public final String mccCustomer1 = "MCC Customer 1";
	public final String paymentTermsInput = "30 Net";

	public final String itemOne = "Item001";

	public final String createHeader = "Create Transaction: Invoice";
	public final String buttonText = "Complete and Review";

	TaxLinkUIUtilities utils = new TaxLinkUIUtilities();

	/**
	 * Tests whether a customer invoice
	 * is validated with no tax
	 *
	 * COERPC-9693
	 */
	@Test(groups = { "taxlink_ui_e2e_regression" })
	public void arInvoiceValidateTaxCalcExclusionTest( )
	{
		final String quantityInput = "1";
		final String unitPriceInput = "1000";

		loadInitialTestPage_ecogdev3();

		signInToHomePage();

		OracleCloudCreateTransactionPage createTransactionPage = navigateToCreateTransactionPage();

		changeARInvoiceTransactionClass(createTransactionPage, transClassSelect, createHeader);

		invoiceGeneralInformationRequiredInfo(createTransactionPage, busUnitInput, transSourceInput, transTypeInput,
			null);

		setBillToAndShipTo(createTransactionPage, mccCustomer1);
		setPaymentTerms(createTransactionPage, paymentTermsInput);

		addInvoiceLineItem(createTransactionPage, 1, itemOne, utils.randomAlphaNumericText(), null, quantityInput,
			unitPriceInput, null);

		createTransactionPage.clickSaveButton();

		try
		{
			Thread.sleep(8000);
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
		OracleCloudReviewTransactionPage reviewTransactionPage = createTransactionPage.clickCompleteButtonOption(
			buttonText);

		boolean taxValidated = reviewTransactionPage.getDisplayedTax();
		assertTrue(taxValidated);
	}

	/**
	 * Helper method
	 *
	 * Selects the payment term
	 * Only wraps around one line, but for readability/clarity
	 *
	 * @param page
	 * @param paymentTerms
	 */
	protected void setPaymentTerms( OracleCloudCreateTransactionPage page, String paymentTerms )
	{
		page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.PAYMENT_TERMS, paymentTerms);
	}
}
