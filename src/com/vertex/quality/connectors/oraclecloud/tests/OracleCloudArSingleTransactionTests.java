package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.common.configuration.OracleSettings;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudCreateTransactionPageFieldData;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudPageNavigationData;
import com.vertex.quality.connectors.oraclecloud.keywords.api.DatabaseKeywords;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudCreateTransactionPage;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudManageTransactionsPage;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudReceivablesBillingPage;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudReviewTransactionPage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;
import static org.testng.Assert.assertTrue;

import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeDbSettings;

/**
 * Runs the standard one-off AR tests that should be
 * performed for each patch
 *
 * @author cgajes
 */
public class OracleCloudArSingleTransactionTests extends OracleCloudBaseTest
{
	public final String transClassSelect = "Invoice";
	public final String busUnitInput = "VTX_US_BU";
	public final String transSourceInput = "Manual";
	public final String transTypeInput = "Invoice";
	public final String mccCustomer1 = "MCC Customer 1";
	public final String mccCanadaCustomer = "Canada Customer";
	public final String prCountryCustomer = "PR Country Customer";
	public final String paymentTermsInput = "30 Net";
	public final String transNumOperatorSelect = "Equals";

	public final String itemOne = "Item001";

	public final String changeHeader = "Transaction Number: Equals";
	public final String createHeader = "Create Transaction: Invoice";
	public final String buttonText = "Complete and Review";

	private DatabaseKeywords database = new DatabaseKeywords();
	private OracleSettings settings = OracleSettings.getOracleSettingsInstance();

	/**
	 * Tests whether a customer invoice from Puerto Rico
	 * is validated
	 *
	 * COERPC-4992
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ar_single" })
	public void arInvoiceValidatePRCustomerTest( )
	{
		final String EXPECTED_TOTAL_TAX = "1.15";

		final String quantityInput = "1";
		final String unitPriceInput = "10";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateTransactionPage createTransactionPage = navigateToCreateTransactionPage();

		changeARInvoiceTransactionClass(createTransactionPage, transClassSelect, createHeader);

		invoiceGeneralInformationRequiredInfo(createTransactionPage, busUnitInput, transSourceInput, transTypeInput,
				null);

		setBillToAndShipTo(createTransactionPage, prCountryCustomer);

		addInvoiceLineItem(createTransactionPage, 1, itemOne, null, null, quantityInput, unitPriceInput, null);

		createTransactionPage.clickSaveButton();

		try{
			Thread.sleep(8000);
		}
		catch ( InterruptedException e)
		{
			e.printStackTrace();
		}
		OracleCloudReviewTransactionPage reviewTransactionPage = createTransactionPage.clickCompleteButtonOption(
				buttonText);

		boolean taxValidated = validateTax(reviewTransactionPage, EXPECTED_TOTAL_TAX);

		assertTrue(taxValidated);
	}

	/**
	 * Tests whether the correct tax is calculated
	 * when unit price and quantity have a large number of decimals
	 *
	 * COERPC-4933
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ar_single" })
	public void arInvoiceLargeDecimalNumberTest( )
	{
		final String EXPECTED_TOTAL_TAX = "2.09";

		final String quantityInput = "11.96583";
		final String unitPriceInput = "1.96587364";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateTransactionPage createTransactionPage = navigateToCreateTransactionPage();

		changeARInvoiceTransactionClass(createTransactionPage, transClassSelect, createHeader);

		invoiceGeneralInformationRequiredInfo(createTransactionPage, busUnitInput, transSourceInput, transTypeInput,
				null);

		setBillToAndShipTo(createTransactionPage, mccCustomer1);

		setPaymentTerms(createTransactionPage, paymentTermsInput);

		addInvoiceLineItem(createTransactionPage, 1, itemOne, null, null, quantityInput, unitPriceInput, null);

		createTransactionPage.clickSaveButton();
		try{
			Thread.sleep(8000);
		}
		catch ( InterruptedException e)
		{
			e.printStackTrace();
		}
		OracleCloudReviewTransactionPage reviewTransactionPage = createTransactionPage.clickCompleteButtonOption(
				buttonText);

		boolean taxValidated = validateTax(reviewTransactionPage, EXPECTED_TOTAL_TAX);

		assertTrue(taxValidated);
	}

	/**
	 * Tests whether duplicated invoice for abbreviated
	 * province properly calculates tax
	 *
	 * COERPC-4990
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ar_single" })
	public void arInvoiceAbbreviatedProvinceTest( )
	{
		initializeApiTest();

		String EXPECTED_TOTAL_TAX = "11.00";
		String transactionNumber = "";

		if(settings.environment.equals("https://ecog-test.fa.us2.oraclecloud.com"))
			transactionNumber = "438101"; //"438101" - tx number for ecog-test (DO NOT DELETE)
		else {
			transactionNumber = "429098"; // "429098" - tx number for ecog-dev1 (DO NOT DELETE)
			EXPECTED_TOTAL_TAX = "6.00";
		}
		loadInitialTestPage();

		signInToHomePage();

		OracleCloudManageTransactionsPage manageTransactionsPage = navigateToManageTransactionsPage();
		manageTransactionsPage.writeTransNumOperator(transactionNumber);
		manageTransactionsPage.clickSearchButton();
		manageTransactionsPage.findSearchResult(transactionNumber);
		manageTransactionsPage.clickDuplicateIcon();
		manageTransactionsPage.waitForLoadedPage("Create Transaction: Invoice");
		manageTransactionsPage.clickSaveButton();
		OracleCloudReviewTransactionPage reviewTransactionPage = manageTransactionsPage.clickCompleteButtonOption(
				buttonText);
		boolean taxValidated = validateTax(reviewTransactionPage, EXPECTED_TOTAL_TAX);

		assertTrue(taxValidated);
	}

	/**
	 * Tests whether the correct tax is calculated
	 * with a recycling fee
	 * COERPC-4961
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ar_single" })
	public void arInvoiceRecyclingFeeTest( )
	{
		final String EXPECTED_TOTAL_TAX = "102.50";
		String transactionNumber = "338099";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudManageTransactionsPage manageTransactionsPage = navigateToManageTransactionsPage();
		manageTransactionsPage.writeTransNumOperator(transactionNumber);
		manageTransactionsPage.clickSearchButton();
		manageTransactionsPage.findSearchResult(transactionNumber);
		manageTransactionsPage.clickDuplicateIcon();
		manageTransactionsPage.waitForLoadedPage("Create Transaction: Invoice");
		manageTransactionsPage.clickSaveButton();
		OracleCloudReviewTransactionPage reviewTransactionPage = manageTransactionsPage.clickCompleteButtonOption(
				buttonText);
		boolean taxValidated = validateTax(reviewTransactionPage, EXPECTED_TOTAL_TAX);

		assertTrue(taxValidated);
	}

	/**
	 * Tests the successful creation of a tax only invoice
	 *
	 * Jira test: COERPC-3245
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ar_single" })
	public void arInvoiceTaxOnlyTest( )
	{
		final String taxOnlyInput = "Tax-Only";
		final String rateNameInput = "***VENDOR CHRG TAX RATECDP2P***";
		final String taxAmountInput = "60.00";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateTransactionPage createTransactionPage = navigateToCreateTransactionPage();

		changeARInvoiceTransactionClass(createTransactionPage, transClassSelect, createHeader);

		invoiceGeneralInformationRequiredInfo(createTransactionPage, busUnitInput, transSourceInput, transTypeInput,
				null);

		setBillToAndShipTo(createTransactionPage, mccCustomer1);

		addInvoiceLineTaxOnly(createTransactionPage, 1, taxOnlyInput, taxOnlyInput);

		createTransactionPage.clickSaveButton();

		WebElement detailTaxPopup = createTransactionPage.clickTaxEditButton();
		addTaxLineOnEditPopup(createTransactionPage, detailTaxPopup, rateNameInput, taxAmountInput);
		createTransactionPage.clickTaxEditPopupButton(detailTaxPopup, "Save and Close");

		OracleCloudReviewTransactionPage reviewTransactionPage = createTransactionPage.clickCompleteButtonOption(
				buttonText);

		boolean taxValidated = validateTax(reviewTransactionPage, taxAmountInput);

		assertTrue(taxValidated);
	}

	/**
	 * Tests correct tax recalculation when an invoice is completed
	 * and then edited, leading to a different total tax
	 *
	 * Jira test case: COERPC-3263
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ar_single" })
	public void arInvoiceRecalcTest( ) {
		final String EXPECTED_INITIAL_TAX = "355.00";
		final String EXPECTED_RECALCULATED_TAX = "2,218.75";
		String quantityFive = "5";
		String newUnitPrice = "5,000";
		String transactionsNumber = "297102";
		loadInitialTestPage();

		signInToHomePage();

		OracleCloudManageTransactionsPage manageTransPage = navigateToManageTransactionPage();

		manageTransPage.writeTransNumOperator(transactionsNumber);

		manageTransPage.clickSearchButton();

		manageTransPage.findSearchResult(transactionsNumber);

		OracleCloudCreateTransactionPage createTransactionPage = manageTransPage.clickDuplicateIcon();

		createTransactionPage.clickSaveButton();

		OracleCloudReviewTransactionPage reviewTransactionPage = manageTransPage.clickCompleteButtonOption(
				buttonText);

		boolean taxValidated = validateTax(reviewTransactionPage, EXPECTED_INITIAL_TAX);

		assertTrue(taxValidated, "Initial tax calculated not equal to "+EXPECTED_INITIAL_TAX);

		createTransactionPage = reviewTransactionPage.clickIncompleteButton();

		editInvoiceLineItem(createTransactionPage, 1, null, null, null, quantityFive,
				newUnitPrice, null);

		reviewTransactionPage = manageTransPage.clickCompleteButtonOption(buttonText);

		boolean taxRevalidated = validateTax(reviewTransactionPage, EXPECTED_RECALCULATED_TAX);

		assertTrue(taxRevalidated, "Recalculated tax not equal to "+EXPECTED_RECALCULATED_TAX);
	}

	/**
	 * Tests the successful deletion of a completed and saved invoice
	 *
	 * Test case: COERPC-3281
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ar_single" })
	public void arInvoiceDeleteTest( ) throws InterruptedException
	{
		final String EXPECTED_RESULT_TEXT = "No results found.";

		final String quantityInput = "4";
		final String unitPriceInput = "1,000";

		initializeDbSettings();

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateTransactionPage createTransactionPage = navigateToCreateTransactionPage();

		changeARInvoiceTransactionClass(createTransactionPage, transClassSelect, createHeader);

		invoiceGeneralInformationRequiredInfo(createTransactionPage, busUnitInput, transSourceInput, transTypeInput,
				null);

		setBillToAndShipTo(createTransactionPage, mccCustomer1);

		setPaymentTerms(createTransactionPage, paymentTermsInput);

		addInvoiceLineItem(createTransactionPage, 1, itemOne, null, null, quantityInput, unitPriceInput, null);

		OracleCloudReviewTransactionPage reviewTransactionPage = createTransactionPage.clickCompleteButtonOption(
				buttonText);

		saveAndWaitForTaxUpdate(false);
		String transactionNumber = reviewTransactionPage.getTransactionNumber();

		OracleCloudReceivablesBillingPage billingPage = reviewTransactionPage.clickCancelButton();

		Thread.sleep(15000);

		database.checkInvoiceIsPresent(transactionNumber);

		OracleCloudManageTransactionsPage manageTransPage = navigateToManageTransactionPageAndSearch(billingPage,
				transactionNumber);

		deleteTransaction(manageTransPage, transactionNumber);

		manageTransPage.writeTransNumOperator(transactionNumber);
		manageTransPage.clickSearchButton();
		String displayedResultText = manageTransPage.getSearchResultText();

		assertTrue(EXPECTED_RESULT_TEXT.equals(displayedResultText));

		//Verify invoice deletion occured in the Taxlink database.

		database.checkInvoiceDeleted(transactionNumber);
	}

	/**
	 * Navigate to the manage transaction page
	 *
	 * @param page
	 * @param transactionNumber
	 *
	 * @return the manage transaction page
	 */
	protected OracleCloudManageTransactionsPage navigateToManageTransactionPageAndSearch( OracleCloudReceivablesBillingPage page,
																						  String transactionNumber )
	{

		WebElement menu = page.openSearchTab();
		page.searchTransactionNumber(menu, transactionNumber);

		OracleCloudManageTransactionsPage manageTransPage = page.clickSearch(menu);

		return manageTransPage;
	}

	/**
	 * Navigate to the manage transaction page
	 *
	 *
	 * @return the manage transaction page
	 */
	protected OracleCloudManageTransactionsPage navigateToManageTransactionPage()
	{
		openNavPanel();
		OracleCloudReceivablesBillingPage billingPage = navigateToPage(
				OracleCloudPageNavigationData.RECEIVABLES_BILLING_PAGE);

		WebElement menu = billingPage.openTasksTab();
		OracleCloudManageTransactionsPage manageTransPage = billingPage.clickMenuLink(
				OracleCloudPageNavigationData.MANAGE_TRANSACTION_PAGE, menu);

		return manageTransPage;
	}

	/**
	 * After searching for an invoice on the manage transaction page,
	 * select the intended result and delete it
	 * Confirm the delete on a popup
	 *
	 * @param managePage
	 * @param transactionNumber
	 */
	protected void deleteTransaction( OracleCloudManageTransactionsPage managePage, String transactionNumber )
	{
		WebElement result = managePage.findSearchResult(transactionNumber);
		OracleCloudReviewTransactionPage reviewPage = managePage.selectSearchResult(result);
		OracleCloudCreateTransactionPage createPage = reviewPage.clickIncompleteButton();
		WebElement popup = createPage.clickDeleteButton();

		createPage.clickDeletePopupYesButton(popup);
	}

	/**
	 * Verifies an error message on a popup
	 *
	 * @param page
	 * @param expectedMessage
	 *
	 * @return whether the message on the popup matches the expected message (true) or not (false)
	 */
	protected boolean verifyErrorMessage( OracleCloudCreateTransactionPage page, String expectedMessage )
	{
		WebElement errorPopup = page.errorPopupWaitFor();

		String errorReceived = page.errorPopupGetMessage(errorPopup);

		boolean verified = expectedMessage.equals(errorReceived);

		return verified;
	}

	/**
	 * On the tax edit popup, add a tax line and input required information
	 *
	 * @param page
	 * @param taxPopup
	 * @param rateInput
	 * @param taxAmtInput
	 */
	protected void addTaxLineOnEditPopup( OracleCloudCreateTransactionPage page, WebElement taxPopup, String rateInput,
										  String taxAmtInput )
	{
		page.clickTaxEditAddRow(taxPopup);
		page.detailTaxInputRateName(taxPopup, rateInput);
		page.detailTaxWaitForName(taxPopup, "VENDOR CHARGED TAX");
		page.detailTaxInputTaxAmount(taxPopup, taxAmtInput);
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