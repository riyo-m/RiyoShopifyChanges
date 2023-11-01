package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudCreateTransactionPage;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudReviewTransactionPage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests the successful creation of a
 * single and double line AR credit memo
 *
 * @author cgajes
 */
public class OracleCloudArCreditMemoTests extends OracleCloudBaseTest
{

	public final String transClassSelect = "Credit memo";
	public final String busUnitInput = "VTX_US_BU";
	public final String transSourceInput = "Manual";
	public final String transTypeInput = "Credit Memo";
	public final String billToNameSearch = "Search: Bill-to Name";
	public final String searchByName = "Name";
	public final String billToNameSearchInput = "MCC%";
	public final String mccAllStates = "MCC All States";
	public final String uomEachInput = "Ea";
	public final String uomDayInput = "Day";

	public final String createHeader = "Create Transaction: Credit Memo";
	public final String buttonText = "Complete and Review";

	/**
	 * Tests whether the single line credit memo has been successfully created,
	 * and that the expected total tax has been correctly calculated
	 */
	@Test(groups = { "" }) //future UI smoke
	public void createSingleLineArCreditMemoTest( )
	{
		final String EXPECTED_TOTAL_TAX = "-6.00";

		final String descriptionInput = "Single Line Credit Memo Test";
		final String quantityInput = "1";
		final String unitPriceInput = "-100";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateTransactionPage createTransactionPage = navigateToCreateTransactionPage();

		changeARInvoiceTransactionClass(createTransactionPage, transClassSelect, createHeader);

		invoiceGeneralInformationRequiredInfo(createTransactionPage, busUnitInput, transSourceInput, transTypeInput,
			null);

		useSearchForBillToAndShipTo(createTransactionPage, billToNameSearch, searchByName, billToNameSearchInput,
			mccAllStates);

		addInvoiceLineItem(createTransactionPage, 1, null, descriptionInput, uomEachInput, quantityInput,
			unitPriceInput, null);

		OracleCloudReviewTransactionPage reviewTransactionPage = createTransactionPage.clickCompleteButtonOption(
			buttonText);

		boolean taxValidation = validateTax(reviewTransactionPage, EXPECTED_TOTAL_TAX);

		assertTrue(taxValidation);
	}

	/**
	 * Tests whether the double line credit memo has been successfully created,
	 * and that the expected total tax for the items has been correctly calculated
	 */
	@Test(groups = { "oracle_erp_cloud_regression" })
	public void createDoubleLineArCreditMemoTest( )
	{
		final String EXPECTED_TOTAL_TAX = "-126.00";
		final String lineOneDescInput = "Line 1";
		final String lineTwoDescInput = "Line 2";
		final String lineOneQuantityInput = "1";
		final String lineTwoQuantityInput = "2";
		final String lineOneUnitPriceInput = "-100";
		final String lineTwoUnitPriceInput = "-1,000";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateTransactionPage createTransactionPage = navigateToCreateTransactionPage();

		changeARInvoiceTransactionClass(createTransactionPage, transClassSelect, createHeader);

		invoiceGeneralInformationRequiredInfo(createTransactionPage, busUnitInput, transSourceInput, transTypeInput,
			null);

		useSearchForBillToAndShipTo(createTransactionPage, billToNameSearch, searchByName, billToNameSearchInput,
			mccAllStates);

		addInvoiceLineItem(createTransactionPage, 1, null, lineOneDescInput, uomEachInput, lineOneQuantityInput,
			lineOneUnitPriceInput, null);

		addInvoiceLineItem(createTransactionPage, 2, null, lineTwoDescInput, uomDayInput, lineTwoQuantityInput,
			lineTwoUnitPriceInput, null);

		OracleCloudReviewTransactionPage reviewTransactionPage = createTransactionPage.clickCompleteButtonOption(
			buttonText);

		boolean taxValidation = validateTax(reviewTransactionPage, EXPECTED_TOTAL_TAX);

		assertTrue(taxValidation);
	}
}
