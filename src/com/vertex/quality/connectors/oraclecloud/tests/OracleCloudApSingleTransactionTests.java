package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.common.utils.OracleUtilities;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudProfileOptions;
import com.vertex.quality.connectors.oraclecloud.keywords.api.DataKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.DatabaseKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.ProcessesKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.TemplateKeywords;
import com.vertex.quality.connectors.oraclecloud.pages.*;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.vertex.quality.connectors.oraclecloud.common.utils.Template.transactionNumber;
import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;
import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeDbSettings;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * Runs the standard one-off AP tests that should be
 * performed for each patch
 *
 * @author cgajes
 */
public class OracleCloudApSingleTransactionTests extends OracleCloudBaseTest
{
	private ProcessesKeywords processes = new ProcessesKeywords();
	private TemplateKeywords templates = new TemplateKeywords();
	private DataKeywords data = new DataKeywords();
	private DatabaseKeywords database = new DatabaseKeywords();
	private OracleUtilities utilities = new OracleUtilities();

	final String busUnitInput = "VTX_US_BU";
	final String busUnitInputCanada = "VTX_CA_BU";
	final String supplierInputCurryTax = "Curry With Tax";
	final String supplierInputCurryNoTax = "Curry No Tax";
	final String supplierInputMccCali = "MCC Calif";
	final String supplierInputCanada = "Canada Supplier";
	final String supplierInputVtxCanada = "Canada_VTX_Supplier";
	final String supplierInputPRCountry = "PR Country";
	final String paymentTermsInputImmediate = "Immediate";
	final String rateNameInputStd = "STD";
	final String getRateNameInputCaGstImp = "VTX_CA_GST_IMPORT_STD";
	final String getRateNameInputCaHstImp = "VTX_CA_HST_IMPORT_STD";
	final String getRateNameInputCaQstImp = "VTX_CA_QST_IMPORT_STD";
	final String getRateNameInputCaPstImp = "VTX_CA_PST_IMPORT_STD";
	final String distributionSetTestExpenseInput = "VTX Test Expense";

	final String editTaxesButton = "Edit Taxes";
	final String saveAndCloseButton = "Save and Close";
	final String validateTextSelect = "Validate Ctrl+Alt+V";
	final String calculateTextSelect = "Calculate Tax Ctrl+Alt+X";
	final String cancelTextSelect = "Cancel Invoice";
	final String payInFullTextSelect = "Pay in Full";
	final String invoiceValidated = "Validated";
	final String invoiceNeedsRevalidation = "Needs revalidation";
	final String invoiceCanceled = "Canceled";
	final String invoiceUnpaid = "Unpaid";
	final String invoiceAvailable = "Available";
	final String manageHolds = "Manage Holds";

	final String testDate = "12/31/21";
	final String todaysDate = utilities.getTodaysDate("M/d/yy");
	String paymentNumber = "";
	public String invoiceNumber = "";

	/**
	 * Tests the successful creation of an invoice
	 *
	 */
	@Test(groups = { "oerpc_smoke" })
	public void apSmokeTest( )
	{
		final String testId = "SMOKE";
		final String amountInput = "1,000.00";
		final String shipToInput = "MCC_NY";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputMccCali, null, amountInput, null,
				todaysDate, null);

		generateAndWriteInvoiceNumber(createInvoicePage, testId);

		createInvoicePage.clickExpandLinesButton();

		addInvoiceLineItem(createInvoicePage, 1, null, amountInput, distributionSetTestExpenseInput,
				null, null, shipToInput, false);

		String status = validateInvoice(createInvoicePage);
		assertTrue(invoiceValidated.equals(status));
	}

	/**
	 * Test whether a prepayment invoice can successfully be created,
	 * and whether that prepayment can be used to pay another invoice in full.
	 *
	 * Jira test case: COERPC-3118
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ap_single" })
	public void apPrepaymentsTest( )
	{
		final String testId = "PREPAY";

		final String prepayAmtInput = "1,000.00";
		final String halfAmountInput = "500.00";
		final String prepaySelectOption = "Prepayment";
		final String paShipToInput = "VTX_PA";
		final String nyShipToInput = "VTX_NY";
		final String distributionCombo = "3211-00-21010-000-0000-0000";
		final String applyUnapplyPrepayments = "Apply or Unapply Prepayments";
		final String paymentProcessProfile = "Standard Check";
		final String paymentDocument = "VTX-Check";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputCurryTax, null, prepayAmtInput,
			prepaySelectOption, todaysDate, null);

		String prepaymentNumber = generateAndWriteInvoiceNumber(createInvoicePage, testId);

		createInvoicePage.clickExpandLinesButton();

		addInvoiceLineItem(createInvoicePage,1, null, prepayAmtInput, null,
				distributionCombo, null, null, false);

		String status = validateInvoice(createInvoicePage);

		assertTrue(invoiceUnpaid.equals(status), "Unexpected invoice status. Expected status: "+invoiceUnpaid);

		// "Pay in Full" option does not exist on the ecog-dev environment. Option is available on ecog-test.
		payInvoice(createInvoicePage, paymentProcessProfile, paymentDocument);

		status = createInvoicePage.checkValidationStatus();

		assertTrue(invoiceAvailable.equals(status), "Unexpected invoice status. Expected status: "+invoiceAvailable);

		createInvoicePage = createInvoicePage.clickSaveAndCreateNextButton();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputCurryTax, null, prepayAmtInput,
				null, todaysDate, null);

		generateAndWriteInvoiceNumber(createInvoicePage, testId);

		createInvoicePage.clickExpandLinesButton();

		addInvoiceLineItem(createInvoicePage, 6, null, halfAmountInput, "VTX Test Expense",
				null, null, nyShipToInput, true);

		addInvoiceLineItem(createInvoicePage, 7, null, halfAmountInput, "VTX Test Expense",
				null, null, paShipToInput, true);

		selectActionItem(createInvoicePage, applyUnapplyPrepayments);
		WebElement prepayPopup = createInvoicePage.waitForPrepaymentPopup();

		// have to select prepayment previously made
		createInvoicePage.applyPrepayment(prepayPopup, prepaymentNumber);

		status = validateInvoice(createInvoicePage);

		assertTrue(invoiceValidated.equals(status), "Unexpected invoice status. Expected status: "+invoiceValidated);
	}

	/**
	 * Tests the successful creation of a tax only invoice
	 *
	 * Jira Test Case: COERPC-3119
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ap_single" })
	public void apTaxOnlyInvoiceTest( )
	{
		final String testId = "TAXONLY";
		final String amountInput = "100.00";
		final String taxRegimeInput = "VERTEX US TAX";
		final String taxNameInput = "STATE";
		final String taxJurisdictionInput = "IDAHO";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputMccCali, null, amountInput,
				null, null, null);

		generateAndWriteInvoiceNumber(createInvoicePage, testId);

		WebElement taxPopup = openTransactionTaxPopup(createInvoicePage);
		addTaxRow(createInvoicePage, taxPopup, true);
		editTaxRowInformation(createInvoicePage, taxPopup, rateNameInputStd, null, taxRegimeInput,
				taxNameInput, null, taxJurisdictionInput, amountInput);
		closeTransactionTaxPopup(createInvoicePage, taxPopup);

		String status = validateInvoiceByText(createInvoicePage);

		assertTrue(invoiceValidated.equals(status));
	}

	/**
	 * Tests whether a validated invoice can be successfully cancelled
	 *
	 * Jira test case: COERPC-3125
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ap_single" })
	public void apCancelInvoiceTest( )
	{
		final String testId = "CANCEL";
		final String amountInput = "1,000.00";
		final String shipToInput = "MCC_NY";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputMccCali, null, amountInput,
				null, todaysDate, null);

		generateAndWriteInvoiceNumber(createInvoicePage, testId);

		createInvoicePage.clickExpandLinesButton();

		addInvoiceLineItem(createInvoicePage, 1, null, amountInput, distributionSetTestExpenseInput, null, null,
			shipToInput, false);

		String status = validateInvoice(createInvoicePage);
		assertTrue(invoiceValidated.equals(status));

		boolean canceled = checkCancellation(createInvoicePage, 1);
		assertTrue(!canceled);
		canceled = checkCancellation(createInvoicePage, 2);
		assertTrue(!canceled);
		canceled = checkCancellation(createInvoicePage, 3);
		assertTrue(!canceled);

		status = cancelInvoice(createInvoicePage);
		assertTrue(invoiceCanceled.equals(status));

		canceled = checkCancellation(createInvoicePage, 1);
		assertTrue(canceled);
		canceled = checkCancellation(createInvoicePage, 2);
		assertTrue(canceled);
		canceled = checkCancellation(createInvoicePage, 3);
		assertTrue(canceled);
	}

	/**
	 * Tests whether the taxes of a validated invoice are successfully recalculated
	 * when the shipping location changes.
	 *
	 * Jira Test Case: COERPC-3126
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ap_single" })
	public void apRecalculateInvoiceTest( )
	{
		final String EXPECTED_INIT_TAX = "60.00";
		final String EXPECTED_NEW_TAX = "88.75";

		final String testId = "RECALC";
		final String amountInput = "1,000.00";
		final String shipToPA = "MCC_PA";
		final String shipToNy = "MCC_NY";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputMccCali, null, amountInput, null,
			testDate, null);

		generateAndWriteInvoiceNumber(createInvoicePage, testId);

		createInvoicePage.clickExpandLinesButton();
		addInvoiceLineItem(createInvoicePage, 1, null, amountInput, distributionSetTestExpenseInput, null, null,
			shipToPA, false);

		validateInvoice(createInvoicePage);
		String initTotalTax = createInvoicePage.getTotalTransTax();
		assertTrue(EXPECTED_INIT_TAX.equals(initTotalTax));
		createInvoicePage.editShipToLocation(shipToNy);

		revalidateInvoice(createInvoicePage);
		String newTotalTax = createInvoicePage.getTotalTransTax();
		assertTrue(EXPECTED_NEW_TAX.equals(newTotalTax));
	}

	/**
	 * Tests the successful voiding of a check after a prepayment invoice
	 * is paid in full, cancelling the invoice
	 *
	 * Jira Test Case: COERPC-3129
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ap_single" })
	public void apCancelInvoicePaymentVoidTest( )
	{
		final String EXPECTED_CONFIRMATION_MSG = "Payment has been voided. Number of invoices canceled: 1.";

		final String testId = "PAYVOID";
		final String amountInput = "1,000.00";
		final String shipToNy = "MCC_NY";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputCurryNoTax, null, amountInput, null,
			todaysDate, paymentTermsInputImmediate);

		generateAndWriteInvoiceNumber(createInvoicePage, testId);

		createInvoicePage.clickExpandLinesButton();

		addInvoiceLineItem(createInvoicePage, 1, null, amountInput, distributionSetTestExpenseInput, null, null,
			shipToNy, false);

		String status = validateInvoice(createInvoicePage);

		assertTrue(invoiceValidated.equals(status));

		payInvoice(createInvoicePage, "Standard Check", "VTX-Check");

		status = createInvoicePage.checkValidationStatus();

		signOffPage();

		loadInitialTestPage();
		signInToHomePage();

		OracleCloudPayablesPaymentsPage managePaymentsPage = navigateToManagePaymentsPage();
		managePaymentsPage.writePaymentNumOperator(paymentNumber);
		managePaymentsPage.clickSearchButton();
		managePaymentsPage.findSearchResult(paymentNumber);
		managePaymentsPage.clickActionsButton();
		managePaymentsPage.voidPayment("Cancel invoice");

		String msg = managePaymentsPage.getConfirmationMessage();

		assertTrue(EXPECTED_CONFIRMATION_MSG.equals(msg));
	}

	/**
	 * Tests Canadian invoice match scenario using upper and
	 * lower case Saskatchewan ship-tos
	 *
	 * !! UPPER_CAN_SK invalid input !!
	 * Test Fails
	 */
	@Ignore()
	public void apCanadaInvoiceCaseSensitiveShipTo( )
	{
		final String testId = "CASESENSITIVE";
		final String totalAmountInput = "222.00";
		final String lineAmountInput = "100.00";
		final String upperCaseInput = "UPPER_CAN_SK";    // invalid
		final String lowerCaseInput = "Saskatchewan";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputMccCali, null, totalAmountInput, null,
			todaysDate, paymentTermsInputImmediate);

		generateAndWriteInvoiceNumber(createInvoicePage, testId);

		createInvoicePage.clickExpandLinesButton();

		// invalid ship-tos open a search on first line, and test times out
		// force fail to save time
		assertTrue(false);
		addInvoiceLineItem(createInvoicePage, 1, null, lineAmountInput, null, null,
				null, upperCaseInput, false);
		addInvoiceLineItem(createInvoicePage, 1, null, lineAmountInput, null, null,
				null, lowerCaseInput, false);

		String status = validateInvoice(createInvoicePage);

		assertTrue(invoiceValidated.equals(status));

		// verify detail taxes
	}

	/**
	 * Test Canadian invoice match scenario using uppercase Saskatchewan
	 * and abbreviated Saskatchewan ship-tos
	 *
	 * !! Invalid location inputs (UPPER_CAN_SK and ZZZ_SK) !!
	 * Test Fails
	 */
	@Ignore
	public void apCanadaInvoiceCaseSensitiveAbbreviatedProvinceShipTo( )
	{
		final String testId = "CASESENSITIVE_ABBR";
		final String totalAmountInput = "222.00";
		final String lineAmountInput = "100.00";
		final String abbreviatedShipTo = "ZZZ_SK";    // invalid
		final String upperCaseShipTo = "UPPER_CAN_SK";    // invalid

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputCanada, null, totalAmountInput, null,
			todaysDate, paymentTermsInputImmediate);

		generateAndWriteInvoiceNumber(createInvoicePage, testId);
		//wait
		createInvoicePage.clickExpandLinesButton();

		// invalid ship-tos open a search on first line, and test times out
		// force fail to save time
		assertTrue(false);
		addInvoiceLineItem(createInvoicePage, 1, null, lineAmountInput, null, null,
				null, abbreviatedShipTo, false);
		addInvoiceLineItem(createInvoicePage, 2, null, lineAmountInput, null, null,
				null, upperCaseShipTo, false);

		String status = validateInvoice(createInvoicePage);

		assertTrue(invoiceValidated.equals(status));

		// verify detail taxes
	}

	/**
	 * Tests whether invoice can be successfully validated and revalidated
	 * when using Japanese Yen as currency
	 *
	 * Jira Test Case: COERPC-3131
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ap_single" })
	public void apInvoiceWithYenCurrencyTest( )
	{
		final String testId = "YEN";
		final String amountUnitSelect = "JPY - Yen";
		final String amountInput = "100";
		final String newAmount = "200";
		final String shipToInput = "VTX_PA";
		final String recalcShipToInput = "VTX_DE";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputMccCali, amountUnitSelect, amountInput,
			null, todaysDate, null);

		generateAndWriteInvoiceNumber(createInvoicePage, testId);

		createInvoicePage.clickExpandLinesButton();
		addInvoiceLineItem(createInvoicePage, 1, null, amountInput, distributionSetTestExpenseInput,
				null, null, shipToInput, false);

		String status = validateInvoice(createInvoicePage);
		assertTrue(invoiceValidated.equals(status));

		createInvoicePage.editAmount(newAmount);
		selectActionItem(createInvoicePage, calculateTextSelect);
		createInvoicePage.waitForCalculation();
		status = createInvoicePage.checkValidationStatus();
		assertTrue(invoiceNeedsRevalidation.equals(status));

		createInvoicePage.editAmount(amountInput);
		selectActionItem(createInvoicePage, calculateTextSelect);
		createInvoicePage.waitForCalculation();
		status = createInvoicePage.checkValidationStatus();
		assertTrue(invoiceNeedsRevalidation.equals(status));
		status = revalidateInvoice(createInvoicePage);
		assertTrue(invoiceValidated.equals(status));
	}

	// TODO Michael Salomone - Either test another invalid supplier and ship-to address
	// or remove.
	/**
	 * Tests whether proper error is returned when an invalid country is received.
	 *
	 */
	public void apInvoiceInvalidCountryErrorTest( )
	{
		final String EXPECTED_ERROR_MSG =
			"An error occurred in a third-party tax application which caused tax calculation to fail. (ZX-885763)\n" +
			"Tax calculation failed because of the following error: VERTEX-0018-Error during transformation:Jurisdiction does not exist for country code : PR.";
		final String testId = "INVALIDCOUNTRY";
		final String amountInput = "100.00";
		final String vtxPaShipTo = "VTX_PA";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputPRCountry, null, amountInput, null,
			testDate, paymentTermsInputImmediate);

		generateAndWriteInvoiceNumber(createInvoicePage, testId);

		createInvoicePage.clickExpandLinesButton();
		addInvoiceLineItem(createInvoicePage, 1, null, amountInput, null, null,
				null, vtxPaShipTo, false);

		validateInvoice(createInvoicePage);

		String msg = createInvoicePage.getErrorMessage();

		assertTrue(EXPECTED_ERROR_MSG.equals(msg));
	}

	/**
	 * Tests PR Country jurisdiction returns taxes when provided as
	 * a supplier address with a VTX_PA ship-to address.
	 *
	 * Tests MCC Calif jurisdiction returns taxes when provided as
	 * a supplier address with a ZZZ_PR ship-to address.
	 *
	 * Tests MCC Calif jurisdiction returns taxes when provided as
	 * a supplier address with a VTX_PA ship-to address.
	 *
	 * This case covers previously invalid suppliers from apInvoiceInvalidCountryErrorTest.
	 *
	 * Jira test case: COERPC-3121 (original) COERPC-5341 (current)
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ap_single" })
	public void apInvoiceCalifTest( ) {
		final String testId = "MCC Calif";
		final String amountInput = "100.00";
		final String vtxPaShipTo = "VTX_PA";
		final String testPrShipTo = "ZZZ_PR";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		// First invoice
		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputPRCountry, null, amountInput,
				null, todaysDate, paymentTermsInputImmediate);

		String firstInvoice = generateAndWriteInvoiceNumber(createInvoicePage, testId);

		createInvoicePage.clickExpandLinesButton();
		addInvoiceLineItem(createInvoicePage, 1, null, amountInput, distributionSetTestExpenseInput,
				null,null, vtxPaShipTo, false);

		createInvoicePage = createInvoicePage.clickSaveAndCreateNextButton();

		// Second invoice
		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputMccCali, null, amountInput,
				null, todaysDate, paymentTermsInputImmediate);

		String secondInvoice = generateAndWriteInvoiceNumber(createInvoicePage, testId);

		createInvoicePage.clickExpandLinesButton();
		addInvoiceLineItem(createInvoicePage, 6, null, amountInput, distributionSetTestExpenseInput,
				null,null, testPrShipTo, true);

		createInvoicePage = createInvoicePage.clickSaveAndCreateNextButton();

		// Third invoice
		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputMccCali, null, amountInput,
				null, todaysDate, paymentTermsInputImmediate);

		String thirdInvoice = generateAndWriteInvoiceNumber(createInvoicePage, testId);

		createInvoicePage.clickExpandLinesButton();
		addInvoiceLineItem(createInvoicePage, 6, null, amountInput, distributionSetTestExpenseInput,
				null,null, vtxPaShipTo, true);

		createInvoicePage.clickSaveAndCreateNextButton();

		driver.close();

		// Prepare for API calls.
		initializeApiTest();
		transactionNumber = "AUTOTST_MCC Calif_";

		String invoiceResults = "";
		try {
			processes.validatePayablesInvoicesAP("VTX_US_BU");
			processes.runPartnerTransactionDataExtract("VTX_US_BU");
			Thread.sleep(1500000);
			data.getApDataFromTables("ApSingle_Calif_Results");

			String csvName = "ApSingle_Calif_Results.csv";
			Path resourcePath = utilities.getResourcePath("csv");
			resourcePath = Paths.get(resourcePath.toString()+"/"+csvName);
			String resourceFile = resourcePath.toString();
			byte[] encoded = Files.readAllBytes(Paths.get(resourceFile));
			invoiceResults = new String(encoded, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			fail("An issue occurred reading data from the results file.");
		}

		VertexLogger.log("\n"+firstInvoice+"\n"+secondInvoice+"\n"+thirdInvoice, VertexLogLevel.INFO);

		assertTrue(invoiceResults.contains(firstInvoice),"First invoice scenario failed. "+firstInvoice+" not " +
				"found in BI table data.\nSupplier address: "+supplierInputPRCountry+"\nShip-to: "+vtxPaShipTo);
		assertTrue(invoiceResults.contains(secondInvoice), "Second invoice scenario failed. "+secondInvoice+" not " +
				"found in BI table data.\nSupplier address: "+supplierInputPRCountry+"\nShip-to: "+testPrShipTo);
		assertTrue(invoiceResults.contains(thirdInvoice), "Third invoice scenario failed. "+thirdInvoice+" not " +
				"found in BI table data.\nSupplier address: "+supplierInputMccCali+"\nShip-to: "+vtxPaShipTo);
	}

	/**
	 * Scenario for handling two currencies (USD and CAD) without
	 * a declared exchange rate. Test will check if exchange rate
	 * defaults to $1.00 when no defined rate exists.
	 *
	 * COERPC-8960
	 */
	public void apInvoiceDefaultExchangeRateTest() {
		final String testId = "DefaultExchRate";
		final String usPaShipTo = "VTX_PA";
		final String amountInput = "34.00";
		final String amountUnitSelect = "CAD - Canadian Dollar";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputCanada, amountUnitSelect, amountInput,
				null, todaysDate, paymentTermsInputImmediate);

		createInvoicePage.clickExpandLinesButton();
		addInvoiceLineItem(createInvoicePage, 1, null, amountInput, distributionSetTestExpenseInput,
				null,null, usPaShipTo, true);

		generateAndWriteInvoiceNumber(createInvoicePage, testId);

		String status = validateInvoice(createInvoicePage);

		assertTrue(invoiceValidated.equals(status));

		// TODO: Michael Salomone - write keyword that verifies the default exchange rate xml tag is in the response
		// - this can be accomplished by querying the database since it contains attachment/response xmls.
	}

	/**
	 * Tests overriding tax on an invoice
	 *
	 * Jira Test Case: COERPC-3123
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ap_single" })
	public void apInvoiceOverrideTaxTest( )
	{
		final String testId = "OVERRIDE";
		final String amountInput = "1,000.00";
		final String shipToNy = "VTX_NY";
		final String overrideTaxName = "3USER OVERRIDDEN TAX RATECD";  // invalid
		final String overrideAmount = "0";
		final String zeroTax = "0.00";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputMccCali, null, amountInput, null,
				todaysDate, paymentTermsInputImmediate);

		generateAndWriteInvoiceNumber(createInvoicePage, testId);

		createInvoicePage.clickExpandLinesButton();
		addInvoiceLineItem(createInvoicePage, 1, null, amountInput, distributionSetTestExpenseInput, null, null,
				shipToNy, false);

		String status = validateInvoice(createInvoicePage);

		assertTrue(invoiceValidated.equals(status));

		String initTaxTotal = createInvoicePage.getTotalTransTax();

		assertTrue(!zeroTax.equals(initTaxTotal));

		WebElement editPopup = createInvoicePage.clickTransactionTaxButton("Edit Taxes");
		createInvoicePage.taxPopupClickAddRow(editPopup);

		createInvoicePage.taxPopupWriteName(editPopup, overrideTaxName);
		createInvoicePage.clickSaveAndCloseButton();
		createInvoicePage.clickTransactionTaxButton("Edit Taxes");
		createInvoicePage.taxPopupWriteAmount(editPopup, overrideAmount, 4);
		createInvoicePage.clickSaveAndCloseButton();

		status = revalidateInvoice(createInvoicePage);

		assertTrue(invoiceValidated.equals(status));

		String overriddenTaxTotal = createInvoicePage.getTotalTransTax();

		assertTrue(zeroTax.equals(overriddenTaxTotal));
	}

	/**
	 * iSupplier invoice equal to calc tax
	 *
	 * Jira Test Case: COERPC-3138
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ap_single" })
	public void apISupplierInvoiceChargedTaxEqualCalcTaxTest( ) {
		final String testId = "iSupplierEqual";
		final String supplierSite = "FRESNO";
		final String requestorEmail = "michelle.curry@vertexinc.com";
		final String shipToLoc = "VTX_PA";
		final String amountInput = "1,000.00";
		final String expectedAmount = "1,060.00";

		loadInitialTestPage();

		signInToSupplierPortal();

		OracleCloudiSupplierInvoicePage createInvoiceWithoutPoPage = navigateToCreateInvoiceWithoutPOPage();

		invoiceNumber = generateAndWriteInvoiceNumberPO(createInvoiceWithoutPoPage, testId);

		invoiceWithoutPOHeaderRequiredInfo(createInvoiceWithoutPoPage, supplierSite, null, requestorEmail);
		createInvoiceWithoutPoPage.selectFresnoSite();

		createInvoiceWithoutPoPage.clickAddItem();
		createInvoiceWithoutPoPage.itemRequiredInfo(createInvoiceWithoutPoPage, shipToLoc, amountInput);
		createInvoiceWithoutPoPage.calculateTax();
		createInvoiceWithoutPoPage.submitInvoice();

		signOffPageSupplierPortal();

		try{
			Thread.sleep(4000);
		}
		catch ( InterruptedException e)
		{
			e.printStackTrace();
		}

		loadInitialTestPage();
		signInToHomePage();

		OracleCloudPayablesPaymentsDashboardPage paymentsDashboardPage = navigateToPayablesPaymentsDashboardPage();
		paymentsDashboardPage.approveInvoice(invoiceNumber, expectedAmount);

		signOffPage();

		loadInitialTestPage();
		signInToHomePage();

		OracleCloudManageInvoicesPage manageInvoicesPage = navigateToManageInvoicesPage();
		manageInvoicesPage.searchInvoiceNumber(invoiceNumber);
		manageInvoicesPage.editInvoice();
		manageInvoicesPage.editDistributionSet("VTX Test Expense");

		String status = validateInvoice(manageInvoicesPage);

		assertTrue(invoiceValidated.equals(status));
	}

	/**
	 * iSupplier invoice charged less than calc tax
	 *
	 * Jira Test Case: COERPC-3146
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ap_single" })
	public void apISupplierInvoiceChargedTaxLessThanCalcTaxTest( ) {
		final String testId = "iSupplierLess";
		final String supplierSite = "FRESNO";
		final String requestorEmail = "michelle.curry@vertexinc.com";
		final String shipToLoc = "VTX_PA";
		final String amountInput = "1,000.00";
		final String expectedAmount = "1,050.00";

		loadInitialTestPage();

		signInToSupplierPortal();

		OracleCloudiSupplierInvoicePage createInvoiceWithoutPoPage = navigateToCreateInvoiceWithoutPOPage();

		invoiceNumber = generateAndWriteInvoiceNumberPO(createInvoiceWithoutPoPage, testId);

		invoiceWithoutPOHeaderRequiredInfo(createInvoiceWithoutPoPage, supplierSite, null, requestorEmail);
		createInvoiceWithoutPoPage.selectFresnoSite();

		createInvoiceWithoutPoPage.clickAddItem();
		createInvoiceWithoutPoPage.itemRequiredInfo(createInvoiceWithoutPoPage, shipToLoc, amountInput);
		createInvoiceWithoutPoPage.calculateTax();
		createInvoiceWithoutPoPage.editTax("50");
		createInvoiceWithoutPoPage.calculateTax();
		createInvoiceWithoutPoPage.submitInvoice();

		signOffPageSupplierPortal();

		try{
			Thread.sleep(4000);
		}
		catch ( InterruptedException e)
		{
			e.printStackTrace();
		}

		loadInitialTestPage();
		signInToHomePage();

		OracleCloudPayablesPaymentsDashboardPage paymentsDashboardPage = navigateToPayablesPaymentsDashboardPage();
		paymentsDashboardPage.approveInvoice(invoiceNumber, expectedAmount);

		signOffPage();

		loadInitialTestPage();
		signInToHomePage();

		OracleCloudManageInvoicesPage manageInvoicesPage = navigateToManageInvoicesPage();
		manageInvoicesPage.searchInvoiceNumber(invoiceNumber);
		manageInvoicesPage.editInvoice();
		manageInvoicesPage.editDistributionSet("VTX Test Expense");

		String status = validateInvoice(manageInvoicesPage);

		assertTrue(invoiceValidated.equals(status));
	}

	/**
	 * iSupplier invoice charged zero calc tax
	 *
	 * Jira Test Case: COERPC-3148
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ap_single" })
	public void apISupplierInvoiceChargedTaxZeroCalcTaxTest( ) {
		final String testId = "iSupplierZero";
		final String supplierSite = "FRESNO";
		final String requestorEmail = "michelle.curry@vertexinc.com";
		final String shipToLoc = "VTX_PA";
		final String amountInput = "1,000.00";
		final String expectedAmount = "1,000.00";

		loadInitialTestPage();

		signInToSupplierPortal();

		OracleCloudiSupplierInvoicePage createInvoiceWithoutPoPage = navigateToCreateInvoiceWithoutPOPage();

		invoiceNumber = generateAndWriteInvoiceNumberPO(createInvoiceWithoutPoPage, testId);

		invoiceWithoutPOHeaderRequiredInfo(createInvoiceWithoutPoPage, supplierSite, null, requestorEmail);
		createInvoiceWithoutPoPage.selectFresnoSite();

		createInvoiceWithoutPoPage.clickAddItem();
		createInvoiceWithoutPoPage.itemRequiredInfo(createInvoiceWithoutPoPage, shipToLoc, amountInput);
		createInvoiceWithoutPoPage.calculateTax();
		createInvoiceWithoutPoPage.editTax("0");
		createInvoiceWithoutPoPage.calculateTax();
		createInvoiceWithoutPoPage.submitInvoice();

		signOffPageSupplierPortal();

		try{
			Thread.sleep(4000);
		}
		catch ( InterruptedException e)
		{
			e.printStackTrace();
		}

		loadInitialTestPage();
		signInToHomePage();

		OracleCloudPayablesPaymentsDashboardPage paymentsDashboardPage = navigateToPayablesPaymentsDashboardPage();
		paymentsDashboardPage.approveInvoice(invoiceNumber, expectedAmount);

		signOffPage();

		loadInitialTestPage();
		signInToHomePage();

		OracleCloudManageInvoicesPage manageInvoicesPage = navigateToManageInvoicesPage();
		manageInvoicesPage.searchInvoiceNumber(invoiceNumber);
		manageInvoicesPage.editInvoice();
		manageInvoicesPage.editDistributionSet("VTX Test Expense");

		String status = validateInvoice(manageInvoicesPage);

		assertTrue(invoiceValidated.equals(status));
	}

	/**
	 * Verifies an iSupplier invoice (without PO) will have tax credited
	 * when Line Level tax processing is turned on.
	 *
	 * Jira Test Case: COERPC-8928
	 */
	@Test(groups = { "oerpc_ui", "oerpc_ap_single" })
	public void apISupplierLineLevelTaxTest( ) {
		final String testId = "iSupplierLineLevel";
		final String supplierSite = "FRESNO";
		final String requestorEmail = "michelle.curry@vertexinc.com";
		final String shipToLoc = "MCC_PA";
		final String amountInput = "100.00";
		final String expectedAmount = "100.00";

		try{
			initializeDbSettings();
			database.flipUSLineLevelSetting("Y");
			database.updateProfileOption(OracleCloudProfileOptions.ISUPPLIER_CHARGE_ENABLEMENT, "Y");

			loadInitialTestPage();

			signInToSupplierPortal();

			OracleCloudiSupplierInvoicePage createInvoiceWithoutPoPage = navigateToCreateInvoiceWithoutPOPage();

			invoiceNumber = generateAndWriteInvoiceNumberPO(createInvoiceWithoutPoPage, testId);

			invoiceWithoutPOHeaderRequiredInfo(createInvoiceWithoutPoPage, supplierSite, todaysDate, requestorEmail);
			createInvoiceWithoutPoPage.selectFresnoSite();

			createInvoiceWithoutPoPage.clickAddItem();
			createInvoiceWithoutPoPage.itemRequiredInfo(createInvoiceWithoutPoPage, shipToLoc, amountInput);
			createInvoiceWithoutPoPage.calculateTax();
			createInvoiceWithoutPoPage.editTax("4");
			createInvoiceWithoutPoPage.calculateTax();
			createInvoiceWithoutPoPage.submitInvoice();

			signOffPageSupplierPortal();

			loadInitialTestPage();
			signInToHomePage();

			OracleCloudManageInvoicesPage manageInvoicesPage = new OracleCloudManageInvoicesPage(driver);
			manageInvoicesPage.approveInvoice(invoiceNumber,expectedAmount);

			manageInvoicesPage = navigateToManageInvoicesPage();
			manageInvoicesPage.searchInvoiceNumber(invoiceNumber);
			manageInvoicesPage.editInvoice();
			manageInvoicesPage.editDistributionSet("VTX Test Expense");

			String status = validateInvoice(manageInvoicesPage);

			assertTrue(invoiceValidated.equals(status));
		}
		finally {
			database.flipUSLineLevelSetting("N");
			database.updateProfileOption(OracleCloudProfileOptions.ISUPPLIER_CHARGE_ENABLEMENT, "Y");
		}
	}

	/**
	 * Tests the successful creation of tax only invoice using the newly added tax rate code 'VTX_CA_GST_IMP'
	 * Tax regime: Vertex CA GST Tax Name: COUNTRY CA BC GST IMPORT Jurisdiction: BC
	 * Story: CORJD-989
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ap_single" })
	public void apCaGstImportTaxOnlyInvoiceTest( )
	{
		final String testId = "CAGSTIMPORTTAXONLY";
		final String amountInput = "100.00";
		final String taxRegimeInput = "VERTEX CA GST";
		final String taxNameInput = "COUNTRY CA BC GST IMPORT";
		final String taxJurisdictionInput = "BC";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInputCanada, supplierInputVtxCanada, null, amountInput,
				null, null, null);

		generateAndWriteInvoiceNumber(createInvoicePage, testId);

		WebElement taxPopup = openTransactionTaxPopup(createInvoicePage);
		addTaxRow(createInvoicePage, taxPopup, true);
		editTaxRowInformation(createInvoicePage, taxPopup, getRateNameInputCaGstImp, null, taxRegimeInput,
				taxNameInput, null, taxJurisdictionInput, amountInput);
		closeTransactionTaxPopup(createInvoicePage, taxPopup);

		String status = validateInvoiceByText(createInvoicePage);

		assertTrue(invoiceValidated.equals(status));
	}

	/**
	 * Tests the successful creation of tax only invoice using the newly added tax rate code 'VTX_CA_HST_IMP'
	 * Tax regime: Vertex CA HST, Tax Name: COUNTRY CA ON HST IMPORT, Jurisdiction: ON
	 * Story: CORJD-989
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ap_single" })
	public void apCaHstImportTaxOnlyInvoiceTest( )
	{
		final String testId = "CAHSTIMPORTTAXONLY";
		final String amountInput = "100.00";
		final String taxRegimeInput = "VERTEX CA HST";
		final String taxNameInput = "COUNTRY CA ON HST IMPORT";
		final String taxJurisdictionInput = "ON";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInputCanada, supplierInputVtxCanada, null, amountInput,
				null, null, null);

		generateAndWriteInvoiceNumber(createInvoicePage, testId);

		WebElement taxPopup = openTransactionTaxPopup(createInvoicePage);
		addTaxRow(createInvoicePage, taxPopup, true);
		editTaxRowInformation(createInvoicePage, taxPopup, getRateNameInputCaHstImp, null, taxRegimeInput,
				taxNameInput, null, taxJurisdictionInput, amountInput);
		closeTransactionTaxPopup(createInvoicePage, taxPopup);

		String status = validateInvoiceByText(createInvoicePage);

		assertTrue(invoiceValidated.equals(status));
	}

	/**
	 * Tests the successful creation of tax only invoice using the newly added tax rate code 'VTX_CA_QST_IMP'
	 * Tax regime: Vertex CA QST, Tax Name: PROVINCE CA QC QST IMPORT, Jurisdiction: QC
	 * Story: CORJD-989
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ap_single" })
	public void apCaQstImportTaxOnlyInvoiceTest( )
	{
		final String testId = "CAQSTIMPORTTAXONLY";
		final String amountInput = "100.00";
		final String taxRegimeInput = "VERTEX CA QST";
		final String taxNameInput = "PROVINCE CA QC QST IMPORT";
		final String taxJurisdictionInput = "QC";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInputCanada, supplierInputVtxCanada, null, amountInput,
				null, null, null);

		generateAndWriteInvoiceNumber(createInvoicePage, testId);

		WebElement taxPopup = openTransactionTaxPopup(createInvoicePage);
		addTaxRow(createInvoicePage, taxPopup, true);
		editTaxRowInformation(createInvoicePage, taxPopup, getRateNameInputCaQstImp, null, taxRegimeInput,
				taxNameInput, null, taxJurisdictionInput, amountInput);
		closeTransactionTaxPopup(createInvoicePage, taxPopup);

		String status = validateInvoiceByText(createInvoicePage);

		assertTrue(invoiceValidated.equals(status));
	}

	/**
	 * Tests the successful creation of tax only invoice using the newly added tax rate code 'VTX_CA_PST_IMP'
	 * Tax regime: Vertex CA PST, Tax Name: PROVINCE CA BC PST IMPORT, Jurisdiction: BC
	 * Story: CORJD-989
	 */
	@Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_ap_single" })
	public void apCaPstImportTaxOnlyInvoiceTest( )
	{
		final String testId = "CAPSTIMPORTTAXONLY";
		final String amountInput = "100.00";
		final String taxRegimeInput = "VERTEX CA PST";
		final String taxNameInput = "PROVINCE CA BC PST IMPORT";
		final String taxJurisdictionInput = "BC";

		loadInitialTestPage();

		signInToHomePage();

		OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

		invoiceHeaderRequiredInfo(createInvoicePage, busUnitInputCanada, supplierInputVtxCanada, null, amountInput,
				null, null, null);

		generateAndWriteInvoiceNumber(createInvoicePage, testId);

		WebElement taxPopup = openTransactionTaxPopup(createInvoicePage);
		addTaxRow(createInvoicePage, taxPopup, true);
		editTaxRowInformation(createInvoicePage, taxPopup, getRateNameInputCaPstImp, null, taxRegimeInput,
				taxNameInput, null, taxJurisdictionInput, amountInput);
		closeTransactionTaxPopup(createInvoicePage, taxPopup);

		String status = validateInvoiceByText(createInvoicePage);

		assertTrue(invoiceValidated.equals(status));
	}

	/**
	 * Helper method
	 * Validate an unvalidated invoice
	 *
	 * @param page the manage invoices page
	 *
	 * @return the validation status
	 */
	protected String validateInvoice( OracleCloudManageInvoicesPage page )
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
		page.waitForValidated();

		String validStatus = page.checkValidationStatus();

		return validStatus;
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

		try {
			page.waitForValidation();
		}
		catch (Exception ex) {
			page.waitForValidationByText();
		}

		String validStatus = page.checkValidationStatus();

		return validStatus;
	}

	/**
	 * Helper method
	 * Validate an unvalidated invoice
	 *
	 * @param page the create invoice page
	 *
	 * @return the validation status
	 */
	protected String validateInvoiceByText( OracleCloudCreateInvoicePage page )
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

		try {
			page.waitForValidationByText();
		}
		catch (Exception ex) {
			page.waitForValidationByText();
		}

		String validStatus = page.checkValidationStatus();

		return validStatus;
	}

	/**
	 * Helper method
	 * Validate an invoice that had been previously validated
	 *
	 * @param page
	 *
	 * @return the validation status
	 */
	protected String revalidateInvoice( OracleCloudCreateInvoicePage page )
	{
		page.clickSaveButton();
		page.waitForNeedsRevalidation();
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
		page.waitForRevalidation();

		String validStatus = page.checkValidationStatus();

		return validStatus;
	}

	/**
	 * Helper method
	 * From the invoice actions menu, pay the prepayment in full
	 *
	 * @param page
	 *
	 * @return validation status
	 */
	protected String payInvoice( OracleCloudCreateInvoicePage page, String processProfile, String paymentDoc )
	{
		WebElement menu = page.clickInvoiceActionsButton();
		try
		{
			page.clickInvoiceActionFromMenu(menu, payInFullTextSelect);
		}
		catch ( StaleElementReferenceException e )
		{
			menu = page.clickInvoiceActionsButton();
			page.clickInvoiceActionFromMenu(menu, payInFullTextSelect);
		}

		//Fill out Pay In Full pop-up box
		WebElement payInFullPopup = page.waitForFullPaymentPopup();
		paymentNumber = page.fillFullPaymentPopup(payInFullPopup, processProfile, paymentDoc);

		page.waitForRevalidation();

		String status = page.checkValidationStatus();

		return status;
	}

	/**
	 * From the invoice actions menu, cancel the invoice
	 * Confirm the cancellation on a popup
	 *
	 * @param page
	 *
	 * @return validation status, which should reflect cancellation
	 */
	protected String cancelInvoice( OracleCloudCreateInvoicePage page )
	{
		WebElement menu = page.clickInvoiceActionsButton();
		try
		{
			page.clickInvoiceActionFromMenu(menu, cancelTextSelect);
		}
		catch ( StaleElementReferenceException e )
		{
			menu = page.clickInvoiceActionsButton();
			page.clickInvoiceActionFromMenu(menu, cancelTextSelect);
		}

		WebElement warning = page.waitForWarningPopup();
		page.clickWarningPopupButton(warning, "OK");
		String status = page.checkValidationStatus();

		return status;
	}

	/**
	 * Helper method
	 * Click the invoice actions button and click one of the
	 * options on the resulting dropdown menu
	 * Check validation status
	 *
	 * @param page
	 * @param itemText
	 */
	protected void selectActionItem( OracleCloudCreateInvoicePage page, String itemText )
	{
		WebElement menu = page.clickInvoiceActionsButton();

		try
		{
			page.clickInvoiceActionFromMenu(menu, itemText);
		}
		catch ( StaleElementReferenceException e )
		{
			menu = page.clickInvoiceActionsButton();
			page.clickInvoiceActionFromMenu(menu, itemText);
		}

	}

	/**
	 * Click the Edit Taxes button on the transaction tax table
	 * to open the popup
	 *
	 * @param page
	 *
	 * @return edit transaction tax popup
	 */
	protected WebElement openTransactionTaxPopup( OracleCloudCreateInvoicePage page )
	{
		try {
			page.clickTransactionTaxesTabAndEditTaxes();
		} catch (TimeoutException te) {
			page.clickExpandTaxesButton();
			page.clickTransactionTaxesTabAndEditTaxes();
		}
		WebElement editTransTaxPopup = page.getTransactionTaxElement();

		return editTransTaxPopup;
	}

	/**
	 * Close the edit transaction tax popup
	 *
	 * @param page
	 * @param taxPopup
	 */
	protected void closeTransactionTaxPopup( OracleCloudCreateInvoicePage page, WebElement taxPopup )
	{
		page.taxPopupClickButton(taxPopup, saveAndCloseButton);
	}

	/**
	 * Close the edit transaction tax popup
	 *
	 * @param page
	 * @param taxPopup
	 */
	protected void closeEditTransactionTaxPopup( OracleCloudCreateInvoicePage page, WebElement taxPopup )
	{
		page.taxPopupClickSaveAndCloseButton(taxPopup);
	}

	/**
	 * On the tax lines, checks to see if the status is cancelled
	 *
	 * @param page
	 * @param lineNum
	 *
	 * @return whether the status reads "Cancelled" (true) or not (false)
	 */
	protected boolean checkCancellation( OracleCloudCreateInvoicePage page, int lineNum )
	{
		WebElement line = page.selectTaxLine(lineNum);
		boolean canceled = page.checkIfTaxCanceled(line);

		return canceled;
	}
}
