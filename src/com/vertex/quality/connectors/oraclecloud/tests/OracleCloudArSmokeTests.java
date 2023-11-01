package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudCreateTransactionPage;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudReviewTransactionPage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests the successful creation of a
 * single and double line AR manual invoice
 * with differing item amounts
 *
 * @author cgajes
 */
public class OracleCloudArSmokeTests extends OracleCloudBaseTest
{
	public final String transClassSelect = "Invoice";
	public final String busUnitInput = "VTX_US_BU";
	public final String transSourceInput = "Manual";
	public final String transTypeInput = "Invoice";
	public final String billToNameSearch = "Search: Bill-to Name";
	public final String searchByName = "Name";
	public final String billToNameSearchInput = "MCC%";
	public final String mccAllStates = "MCC All States";
	public final String mccCustomerOne = "MCC Customer 1";
	public final String uomEachInput = "Ea";
	public final String uomDayInput = "Day";

	public final String createHeader = "Create Transaction: Invoice";
	public final String detailEditHeader = "EditInvoice Line: ";
	public final String createTransEditHeader = "Edit Transaction";
	public final String buttonText = "Complete and Review";

	/**
	 * Tests whether the single line manual invoice
	 * has been successfully created, and that the expected tax
	 * has been correctly calculated
	 */
	@Test(groups = { "oerpc_smoke" })
	public void createSingleLineArManualInvoiceTest( )
	{
		final String EXPECTED_TOTAL_TAX = "6.00";

		final String descriptionInput = "Single Line Manual Inv Test";
		final String quantityInput = "1";
		final String unitPriceInput = "100";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateTransactionPage createTransactionPage = navigateToCreateTransactionPage();

		changeARInvoiceTransactionClass(createTransactionPage, transClassSelect, createHeader);

		invoiceGeneralInformationRequiredInfo(createTransactionPage, busUnitInput, transSourceInput, transTypeInput,
			null);

		useSearchForBillToAndShipTo(createTransactionPage, billToNameSearch, searchByName, mccAllStates,
			mccAllStates);

		addInvoiceLineItem(createTransactionPage, 1, null, descriptionInput, uomEachInput, quantityInput,
			unitPriceInput, null);

		OracleCloudReviewTransactionPage reviewTransactionPage = createTransactionPage.clickCompleteButtonOption(
			buttonText);

		boolean taxValidation = validateTax(reviewTransactionPage, EXPECTED_TOTAL_TAX);

		assertTrue(taxValidation);
	}

	/**
	 * Tests whether the single line manual invoice with multiple items
	 * has been successfully created, and that the expected total tax
	 * for the items has been correctly calculated
	 */
	@Test(groups = { "" }) //future UI smoke
	public void createSingleLineArInvoiceWithItems( )
	{
		final String EXPECTED_TOTAL_TAX = "17.75";

		final String lineOneItemInput = "Item001";
		final String lineTwoItemInput = "Item002";
		final String lineThreeItemInput = "Item_tax_basis";
		final String quantityInput = "1";
		final String unitPriceInput = "100";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateTransactionPage createTransactionPage = navigateToCreateTransactionPage();

		changeARInvoiceTransactionClass(createTransactionPage, transClassSelect, createHeader);

		invoiceGeneralInformationRequiredInfo(createTransactionPage, busUnitInput, transSourceInput, transTypeInput,
			null);

		useSearchForBillToAndShipTo(createTransactionPage, billToNameSearch, searchByName, billToNameSearchInput,
			mccCustomerOne);

		addInvoiceLineItem(createTransactionPage, 1, lineOneItemInput, null, null, quantityInput, unitPriceInput, null);
		addInvoiceLineItem(createTransactionPage, 2, lineTwoItemInput, null, null, quantityInput, unitPriceInput, null);
		addInvoiceLineItem(createTransactionPage, 3, lineThreeItemInput, null, null, quantityInput, unitPriceInput,
			null);

		modifyLineDetails(createTransactionPage, 3, lineThreeItemInput, null, null, null, unitPriceInput,
			detailEditHeader, createTransEditHeader);

		OracleCloudReviewTransactionPage reviewTransactionPage = createTransactionPage.clickCompleteButtonOption(
			buttonText);

		boolean taxValidation = validateTax(reviewTransactionPage, EXPECTED_TOTAL_TAX);

		assertTrue(taxValidation);
	}

	/**
	 * Tests whether the double line manual invoice with items
	 * has been successfully created, and that the expected total tax
	 * for the items has been correctly calculated
	 */
	@Test(groups = { "oerpc_smoke" })
	public void createDoubleLineArManualInvoiceTest( )
	{
		final String EXPECTED_TOTAL_TAX = "126.00";

		final String lineOneDescInput = "Line 1";
		final String lineTwoDescInput = "Line 2";
		final String lineOneQuantityInput = "1";
		final String lineTwoQuantityInput = "2";
		final String lineOneUnitPriceInput = "100";
		final String lineTwoUnitPriceInput = "1,000";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateTransactionPage createTransactionPage = navigateToCreateTransactionPage();

		changeARInvoiceTransactionClass(createTransactionPage, transClassSelect, createHeader);

		invoiceGeneralInformationRequiredInfo(createTransactionPage, busUnitInput, transSourceInput, transTypeInput,
			null);

		useSearchForBillToAndShipTo(createTransactionPage, billToNameSearch, searchByName, mccAllStates,
			mccAllStates);

		addInvoiceLineItem(createTransactionPage, 1, null, lineOneDescInput, uomEachInput, lineOneQuantityInput,
			lineOneUnitPriceInput, null);
		addInvoiceLineItem(createTransactionPage, 2, null, lineTwoDescInput, uomDayInput, lineTwoQuantityInput,
			lineTwoUnitPriceInput, null);

		OracleCloudReviewTransactionPage reviewTransactionPage = createTransactionPage.clickCompleteButtonOption(
			buttonText);

		boolean taxValidated = validateTax(reviewTransactionPage, EXPECTED_TOTAL_TAX);

		assertTrue(taxValidated);
	}

	/**
	 * Tests whether the double line manual invoice with multiple items
	 * has been successfully created, and that the expected total tax
	 * (including several different tax areas and exemptions)
	 * has been correctly calculated
	 */
	@Test(groups = { "" }) //future UI smoke
	public void createDoubleLineArInvoiceWithItems( )
	{
		final String EXPECTED_TOTAL_TAX = "372.75";

		final String lineOneItemInput = "Item001";
		final String lineTwoItemInput = "Item001";
		final String lineThreeItemInput = "Item002_CS";
		final String lineFourItemInput = "Item002_CS";
		final String lineFiveItemInput = "Item_tax_basis_test";
		final String lineSixItemInput = "Item_tax_basis_test";
		final String lineOneQuantityInput = "1";
		final String lineThreeQuantityInput = "1";
		final String lineFiveQuantityInput = "1";
		final String lineTwoQuantityInput = "2";
		final String lineFourQuantityInput = "2";
		final String lineSixQuantityInput = "2";
		final String lineOneUnitPriceInput = "100";
		final String lineThreeUnitPriceInput = "100";
		final String lineFiveUnitPriceInput = "100";
		final String lineTwoUnitPriceInput = "1,000";
		final String lineFourUnitPriceInput = "1,000";
		final String lineSixUnitPriceInput = "1,000";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateTransactionPage createTransactionPage = navigateToCreateTransactionPage();

		changeARInvoiceTransactionClass(createTransactionPage, transClassSelect, createHeader);

		invoiceGeneralInformationRequiredInfo(createTransactionPage, busUnitInput, transSourceInput, transTypeInput,
			null);

		useSearchForBillToAndShipTo(createTransactionPage, billToNameSearch, searchByName, billToNameSearchInput,
			mccCustomerOne);

		addInvoiceLineItem(createTransactionPage, 1, lineOneItemInput, null, null, lineOneQuantityInput,
			lineOneUnitPriceInput, null);
		addInvoiceLineItem(createTransactionPage, 2, lineTwoItemInput, null, null, lineTwoQuantityInput,
			lineTwoUnitPriceInput, null);
		addInvoiceLineItem(createTransactionPage, 3, lineThreeItemInput, null, null, lineThreeQuantityInput,
			lineThreeUnitPriceInput, null);
		addInvoiceLineItem(createTransactionPage, 4, lineFourItemInput, null, null, lineFourQuantityInput,
			lineFourUnitPriceInput, null);
		addInvoiceLineItem(createTransactionPage, 5, lineFiveItemInput, null, null, lineFiveQuantityInput,
			lineFiveUnitPriceInput, null);
		addInvoiceLineItem(createTransactionPage, 6, lineSixItemInput, null, null, lineSixQuantityInput,
			lineSixUnitPriceInput, null);

		OracleCloudReviewTransactionPage reviewTransactionPage = createTransactionPage.clickCompleteButtonOption(
			buttonText);

		boolean taxValidated = validateTax(reviewTransactionPage, EXPECTED_TOTAL_TAX);

		assertTrue(taxValidated);
	}

	/**
	 * Helper method
	 * Navigates to the item details of a specific invoice line and inputs the information entered
	 * Saves and exits the details page
	 *
	 * @param page
	 * @param lineNum
	 * @param item
	 * @param description
	 * @param uom
	 * @param quantity
	 * @param price
	 * @param detailEditHeader
	 * @param createTransHeader
	 */
	protected void modifyLineDetails( OracleCloudCreateTransactionPage page, int lineNum, String item,
		String description, String uom, String quantity, String price, String detailEditHeader,
		String createTransHeader )
	{
		navigateToInvoiceItemDetails(page, lineNum, detailEditHeader);

		editInvoiceLineDetails(page, item, description, uom, quantity, price);

		saveAndCloseInvoiceItemDetails(page, createTransHeader);
	}
}
