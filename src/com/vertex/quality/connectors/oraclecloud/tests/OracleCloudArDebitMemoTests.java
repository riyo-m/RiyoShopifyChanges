package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudCreateTransactionPageFieldData;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudCreateTransactionPage;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudReviewTransactionPage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests the successful creation of a
 * single and double line AR debit memo
 *
 * @author cgajes
 */
public class OracleCloudArDebitMemoTests extends OracleCloudBaseTest
{

	public final String transClassSelect = "Debit memo";
	public final String busUnitInput = "VTX_US_BU";
	public final String transSourceInput = "Manual";
	public final String transTypeInput = "Debit Memo";
	public final String billToNameSearch = "Search: Bill-to Name";
	public final String searchByName = "Name";
	public final String billToNameSearchInput = "MCC%";
	public final String mccAllStates = "MCC All States";
	public final String uomEachInput = "Ea";
	public final String uomDayInput = "Day";

	public final String createHeader = "Create Transaction: Debit Memo";
	public final String buttonText = "Complete and Review";

	/**
	 * Tests whether the single line debit memo has been successfully created,
	 * and that the expected total tax has been correctly calculated
	 */
	@Test(groups = { "oracle_erp_cloud_regression" })
	public void createSingleLineArDebitMemoTest( )
	{
		final String EXPECTED_TOTAL_TAX = "6.00";

		final String descriptionInput = "Single Line Debit Memo Test";
		final String quantityInput = "1";
		final String unitPriceInput = "100";

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
	 * Tests whether the double line debit memo has been successfully created,
	 * and that the expected total tax for the items has been correctly calculated
	 */
	@Test(groups = { "" }) //future UI smoke
	public void createDoubleLineArDebitMemoTest( )
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

	/**
	 * Input all required header information for the accounts receivable invoice
	 * Overridden for debit memo
	 *
	 * @param busUnit
	 * @param transSource
	 * @param transType
	 * @param currency
	 */
	protected void invoiceGeneralInformationRequiredInfo( OracleCloudCreateTransactionPage page, String busUnit,
		String transSource, String transType, String currency )
	{
		page
			.inputAndCheck(OracleCloudCreateTransactionPageFieldData.BUSINESS_UNIT_DEBIT, busUnit)
			.sendKeys(Keys.TAB);

		if ( page.autoSuggestFailure() )
		{
			page
				.inputAndCheck(OracleCloudCreateTransactionPageFieldData.BUSINESS_UNIT_DEBIT, busUnit)
				.sendKeys(Keys.TAB);
		}

		if ( null != currency )
		{
			page.selectFromDropdown(OracleCloudCreateTransactionPageFieldData.CURRENCY, currency);
		}
		else
		{
			page.waitForAutomaticInput(OracleCloudCreateTransactionPageFieldData.CURRENCY, true);
		}

		page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.TRANSACTION_SOURCE, transSource);

		page.writeToGeneralField(OracleCloudCreateTransactionPageFieldData.TRANSACTION_TYPE, transType);
	}

	/**
	 * Adds a line item to the invoice/memo, with all major information
	 * Overridden for debit memo
	 *
	 * @param page
	 * @param lineNum
	 * @param item
	 * @param description
	 * @param uom
	 * @param quantity
	 * @param price
	 */
	protected void addInvoiceLineItem( OracleCloudCreateTransactionPage page, int lineNum, String item,
		String description, String uom, String quantity, String price, String transBusCategory )
	{
		WebElement line = page.selectInvoiceOrMemoLine(lineNum);

		if ( null != item )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.ITEM, item, line);
		}

		if ( null != description )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.DESCRIPTION, description, line);
		}
		else
		{
			page.waitForAutomaticInput(OracleCloudCreateTransactionPageFieldData.DESCRIPTION, line, true);
		}

		if ( null != uom )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.UOM, uom, line);
		}

		page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.QUANTITY_DEBIT, quantity, line);

		page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.UNIT_PRICE_DEBIT, price, line);

		if ( null != transBusCategory )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.TRANSACTION_BUSINESS_CATEGORY,
				transBusCategory, line);
		}
	}
}
