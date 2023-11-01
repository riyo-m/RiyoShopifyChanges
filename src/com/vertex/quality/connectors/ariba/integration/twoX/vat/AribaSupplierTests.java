package com.vertex.quality.connectors.ariba.integration.twoX.vat;

import com.vertex.quality.connectors.ariba.supplier.pages.AribaSupplierCreateInvoicePage;
import com.vertex.quality.connectors.ariba.supplier.pages.AribaSupplierHomePage;
import com.vertex.quality.connectors.ariba.supplier.pages.AribaSupplierInboxPage;
import com.vertex.quality.connectors.ariba.supplier.pages.AribaSupplierInvoiceReviewAndSubmitPage;
import com.vertex.quality.connectors.ariba.supplier.tests.AribaSupplierBaseTest;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * this class contains tests that do invoicing from the supplier site
 * these tests are incorporated with others tests in another test classes to perform a complete Ariba transaction,
 * from buyer to supplier, and back to buyer to reconcile.
 * VAT scenarios only
 *
 * @author osabha
 */

public class AribaSupplierTests extends AribaSupplierBaseTest
{

	/**
	 * CARIBA-590
	 * This test will create a basic requisition for VAT (DE- US CA)
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void VATEUEU_LL_Summarized_UnderTaxAcceptVertexTest( final Method method )
	{
		final String headerLevelTax = "Header level tax";
		final String taxType = "Sales";
		final String taxAmount = "$20.00 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";

		AribaSupplierHomePage supplierHomePage = signInToSupplier(testStartPage);
		AribaSupplierInboxPage inboxPage = supplierHomePage.headerPane.clickInboxButton(inboxButton);
		retrieveAndOpenPurchaseOrder(inboxPage, testCaseName);

		inboxPage.clickCreateInvoiceButton();
		AribaSupplierCreateInvoicePage createInvoicePage = inboxPage.selectStandardInvoice();
		String invoiceId = createInvoicePage.enterInvoiceNumber();
		createInvoicePage.taxDetails.enableCheckBoxByDisplayText(headerLevelTax);
		createInvoicePage.taxDetails.setTaxCategory(taxType);
		createInvoicePage.taxDetails.enterTaxAmount(taxAmount);
		createInvoicePage.clickUpdateButton();
		createInvoicePage.clickSaveButton();
		AribaSupplierInvoiceReviewAndSubmitPage invoiceReview = createInvoicePage.clickNext();
		invoiceReview.clickSubmitButton();
		tryStoreInvoiceId(invoiceId, testCaseName);
	}

	/**
	 * CARIBA-277
	 * This test will create a basic requisition for VAT (DE-DE) and invoice
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void basicRequisitionDEToDETest( final Method method )
	{
		final String headerLevelTax = "Header level tax";
		final String taxType = "Sales";
		final String taxAmount = "$20.00 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";

		AribaSupplierHomePage supplierHomePage = signInToSupplier(testStartPage);
		AribaSupplierInboxPage inboxPage = supplierHomePage.headerPane.clickInboxButton(inboxButton);
		retrieveAndOpenPurchaseOrder(inboxPage, testCaseName);

		inboxPage.clickCreateInvoiceButton();
		AribaSupplierCreateInvoicePage createInvoicePage = inboxPage.selectStandardInvoice();
		String invoiceId = createInvoicePage.enterInvoiceNumber();
		createInvoicePage.taxDetails.enableCheckBoxByDisplayText(headerLevelTax);
		createInvoicePage.taxDetails.setTaxCategory(taxType);
		createInvoicePage.taxDetails.enterTaxAmount(taxAmount);
		createInvoicePage.clickUpdateButton();
		createInvoicePage.clickSaveButton();
		AribaSupplierInvoiceReviewAndSubmitPage invoiceReview = createInvoicePage.clickNext();
		invoiceReview.clickSubmitButton();
		tryStoreInvoiceId(invoiceId, testCaseName);
	}

	/**
	 * CARIBA-583
	 * This test will create a basic requisition for VAT (DE- US CA)
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void EuDEToUSCAHLOverTaxAcceptVendorTest( final Method method )
	{
		final String headerLevelTax = "Header level tax";
		final String taxType = "Sales";
		final String taxAmount = "$20.00 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";

		AribaSupplierHomePage supplierHomePage = signInToSupplier(testStartPage);
		AribaSupplierInboxPage inboxPage = supplierHomePage.headerPane.clickInboxButton(inboxButton);
		retrieveAndOpenPurchaseOrder(inboxPage, testCaseName);

		inboxPage.clickCreateInvoiceButton();
		AribaSupplierCreateInvoicePage createInvoicePage = inboxPage.selectStandardInvoice();
		String invoiceId = createInvoicePage.enterInvoiceNumber();
		createInvoicePage.taxDetails.enableCheckBoxByDisplayText(headerLevelTax);
		createInvoicePage.taxDetails.setTaxCategory(taxType);
		createInvoicePage.taxDetails.enterTaxAmount(taxAmount);
		createInvoicePage.clickUpdateButton();
		createInvoicePage.clickSaveButton();
		AribaSupplierInvoiceReviewAndSubmitPage invoiceReview = createInvoicePage.clickNext();
		invoiceReview.clickSubmitButton();
		tryStoreInvoiceId(invoiceId, testCaseName);
	}

	/**
	 * CARIBA-606
	 * This test will create a basic requisition for VAT (EUFR- EUBE )
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void VATEUFREUBENotRegisteredLL_Summarized_MatchTest( final Method method )
	{

		final String lineLevelTax = "Line level tax";
		final String taxType = "VAT";
		final String chickenTaxes = "$21.0 USD";
		final String duckTaxes = "$10.71 USD";
		final String inboxButton = "INBOX";
		final String testCaseName = method.getName();

		AribaSupplierHomePage supplierHomePage = signInToSupplier(testStartPage);
		AribaSupplierInboxPage inboxPage = supplierHomePage.headerPane.clickInboxButton(inboxButton);
		retrieveAndOpenPurchaseOrder(inboxPage, testCaseName);
		inboxPage.clickCreateInvoiceButton();
		AribaSupplierCreateInvoicePage createInvoicePage = inboxPage.selectStandardInvoice();
		String invoiceId = createInvoicePage.enterInvoiceNumber();
		createInvoicePage.taxDetails.enableCheckBoxByDisplayText(lineLevelTax);
		createInvoicePage.lineItemsDetails.setItemTaxDetails(chicken, chickenTaxes, taxType);
		createInvoicePage.lineItemsDetails.setItemTaxDetails(duck, duckTaxes, taxType);
		createInvoicePage.clickSaveButton();
		AribaSupplierInvoiceReviewAndSubmitPage invoiceReview = createInvoicePage.clickNext();
		invoiceReview.clickSubmitButton();
		tryStoreInvoiceId(invoiceId, testCaseName);
	}

	/**
	 * CARIBA-648
	 * This test will create a basic requisition for VAT (EUFR- EUBE )
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void CAN_NB_NB_HST_HL_Summarized_OverTaxAcceptVendorTest( final Method method )
	{
		final String headerLevelTax = "Header level tax";
		final String taxType = "VAT";
		final String taxAmount = "$50.00 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";

		AribaSupplierHomePage supplierHomePage = signInToSupplier(testStartPage);
		AribaSupplierInboxPage inboxPage = supplierHomePage.headerPane.clickInboxButton(inboxButton);
		retrieveAndOpenPurchaseOrder(inboxPage, testCaseName);

		inboxPage.clickCreateInvoiceButton();
		AribaSupplierCreateInvoicePage createInvoicePage = inboxPage.selectStandardInvoice();
		String invoiceId = createInvoicePage.enterInvoiceNumber();
		createInvoicePage.taxDetails.enableCheckBoxByDisplayText(headerLevelTax);
		createInvoicePage.taxDetails.setTaxCategory(taxType);
		createInvoicePage.taxDetails.enterTaxAmount(taxAmount);
		createInvoicePage.clickUpdateButton();
		createInvoicePage.clickSaveButton();
		AribaSupplierInvoiceReviewAndSubmitPage invoiceReview = createInvoicePage.clickNext();
		invoiceReview.clickSubmitButton();
		tryStoreInvoiceId(invoiceId, testCaseName);
	}

	/**
	 * CARIBA-305
	 * Create a Purchase order for VAT (EU-US), invoice and validate the xml
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void CreatePurchaseOrderforVATUS_EU_andInvoiceTest( final Method method )
	{
		final String headerLevelTax = "Header level tax";
		final String taxType = "VAT";
		final String taxAmount = "$50.00 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";

		AribaSupplierHomePage supplierHomePage = signInToSupplier(testStartPage);
		AribaSupplierInboxPage inboxPage = supplierHomePage.headerPane.clickInboxButton(inboxButton);
		retrieveAndOpenPurchaseOrder(inboxPage, testCaseName);

		inboxPage.clickCreateInvoiceButton();
		AribaSupplierCreateInvoicePage createInvoicePage = inboxPage.selectStandardInvoice();
		String invoiceId = createInvoicePage.enterInvoiceNumber();
		createInvoicePage.taxDetails.enableCheckBoxByDisplayText(headerLevelTax);
		createInvoicePage.taxDetails.setTaxCategory(taxType);
		createInvoicePage.taxDetails.enterTaxAmount(taxAmount);
		createInvoicePage.clickUpdateButton();
		createInvoicePage.clickSaveButton();
		AribaSupplierInvoiceReviewAndSubmitPage invoiceReview = createInvoicePage.clickNext();
		invoiceReview.clickSubmitButton();
		tryStoreInvoiceId(invoiceId, testCaseName);
	}

	/**
	 * CARIBA-582
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * * based on Ariba Test: VATUSEU_HL_Summarized_OverTaxAcceptVertex
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void USPA_EUDE_HL_Summarized_OverTaxAcceptVertexTest( final Method method )
	{
		final String headerLevelTax = "Header level tax";
		final String taxType = "VAT";
		final String taxAmount = "$50.00 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";

		AribaSupplierHomePage supplierHomePage = signInToSupplier(testStartPage);
		AribaSupplierInboxPage inboxPage = supplierHomePage.headerPane.clickInboxButton(inboxButton);
		retrieveAndOpenPurchaseOrder(inboxPage, testCaseName);

		inboxPage.clickCreateInvoiceButton();
		AribaSupplierCreateInvoicePage createInvoicePage = inboxPage.selectStandardInvoice();
		String invoiceId = createInvoicePage.enterInvoiceNumber();
		createInvoicePage.taxDetails.enableCheckBoxByDisplayText(headerLevelTax);
		createInvoicePage.taxDetails.setTaxCategory(taxType);
		createInvoicePage.taxDetails.enterTaxAmount(taxAmount);
		createInvoicePage.clickUpdateButton();
		createInvoicePage.clickSaveButton();
		AribaSupplierInvoiceReviewAndSubmitPage invoiceReview = createInvoicePage.clickNext();
		invoiceReview.clickSubmitButton();
		tryStoreInvoiceId(invoiceId, testCaseName);
	}

	/**
	 * CARIBA-584
	 * create invoice and reconcile through payment attach OK to pay to
	 * paymentbased on Ariba Test: VATSG_FR_HL_Summarized_UnderTaxAcceptVendor
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void NonEUSG_EUFR_HL_Summarized_UnderTaxAcceptVendorTest( final Method method )
	{
		final String headerLevelTax = "Header level tax";
		final String taxType = "VAT";
		final String taxAmount = "$50.00 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";

		AribaSupplierHomePage supplierHomePage = signInToSupplier(testStartPage);
		AribaSupplierInboxPage inboxPage = supplierHomePage.headerPane.clickInboxButton(inboxButton);
		retrieveAndOpenPurchaseOrder(inboxPage, testCaseName);

		inboxPage.clickCreateInvoiceButton();
		AribaSupplierCreateInvoicePage createInvoicePage = inboxPage.selectStandardInvoice();
		String invoiceId = createInvoicePage.enterInvoiceNumber();
		createInvoicePage.taxDetails.enableCheckBoxByDisplayText(headerLevelTax);
		createInvoicePage.taxDetails.setTaxCategory(taxType);
		createInvoicePage.taxDetails.enterTaxAmount(taxAmount);
		createInvoicePage.clickUpdateButton();
		createInvoicePage.clickSaveButton();
		AribaSupplierInvoiceReviewAndSubmitPage invoiceReview = createInvoicePage.clickNext();
		invoiceReview.clickSubmitButton();
		tryStoreInvoiceId(invoiceId, testCaseName);
	}

	/**
	 * CARIBA-585
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: LATAM_CRCO_HLSummarized_OverTaxAcceptVertex
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LATAM_CR_CO_HL_Summarized_OverTaxAcceptVertexTest( final Method method )
	{
		final String headerLevelTax = "Header level tax";
		final String taxType = "VAT";
		final String taxAmount = "$50.00 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";

		AribaSupplierHomePage supplierHomePage = signInToSupplier(testStartPage);
		AribaSupplierInboxPage inboxPage = supplierHomePage.headerPane.clickInboxButton(inboxButton);
		retrieveAndOpenPurchaseOrder(inboxPage, testCaseName);

		inboxPage.clickCreateInvoiceButton();
		AribaSupplierCreateInvoicePage createInvoicePage = inboxPage.selectStandardInvoice();
		String invoiceId = createInvoicePage.enterInvoiceNumber();
		createInvoicePage.taxDetails.enableCheckBoxByDisplayText(headerLevelTax);
		createInvoicePage.taxDetails.setTaxCategory(taxType);
		createInvoicePage.taxDetails.enterTaxAmount(taxAmount);
		createInvoicePage.clickUpdateButton();
		createInvoicePage.clickSaveButton();
		AribaSupplierInvoiceReviewAndSubmitPage invoiceReview = createInvoicePage.clickNext();
		invoiceReview.clickSubmitButton();
		tryStoreInvoiceId(invoiceId, testCaseName);
	}

	/**
	 * CARIBA-588
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: VATEUEU_HL_Summarized_0VendorTaxAcceptVendor
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void EUDE_EUFR_HL_Summarized_0TaxAcceptVendorTest( final Method method )
	{
		final String headerLevelTax = "Header level tax";
		final String taxType = "VAT";
		final String taxAmount = "$50.00 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";

		AribaSupplierHomePage supplierHomePage = signInToSupplier(testStartPage);
		AribaSupplierInboxPage inboxPage = supplierHomePage.headerPane.clickInboxButton(inboxButton);
		retrieveAndOpenPurchaseOrder(inboxPage, testCaseName);

		inboxPage.clickCreateInvoiceButton();
		AribaSupplierCreateInvoicePage createInvoicePage = inboxPage.selectStandardInvoice();
		String invoiceId = createInvoicePage.enterInvoiceNumber();
		createInvoicePage.taxDetails.enableCheckBoxByDisplayText(headerLevelTax);
		createInvoicePage.taxDetails.setTaxCategory(taxType);
		createInvoicePage.taxDetails.enterTaxAmount(taxAmount);
		createInvoicePage.clickUpdateButton();
		createInvoicePage.clickSaveButton();
		AribaSupplierInvoiceReviewAndSubmitPage invoiceReview = createInvoicePage.clickNext();
		invoiceReview.clickSubmitButton();
		tryStoreInvoiceId(invoiceId, testCaseName);
	}

	/**
	 * CARIBA-589
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: VATEUEU_HL_Summarized_Match
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void EUDE_EUFR_HL_Summarized_MatchTest( final Method method )
	{
		final String headerLevelTax = "Header level tax";
		final String taxType = "VAT";
		final String taxAmount = "$50.00 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";

		AribaSupplierHomePage supplierHomePage = signInToSupplier(testStartPage);
		AribaSupplierInboxPage inboxPage = supplierHomePage.headerPane.clickInboxButton(inboxButton);
		retrieveAndOpenPurchaseOrder(inboxPage, testCaseName);

		inboxPage.clickCreateInvoiceButton();
		AribaSupplierCreateInvoicePage createInvoicePage = inboxPage.selectStandardInvoice();
		String invoiceId = createInvoicePage.enterInvoiceNumber();
		createInvoicePage.taxDetails.enableCheckBoxByDisplayText(headerLevelTax);
		createInvoicePage.taxDetails.setTaxCategory(taxType);
		createInvoicePage.taxDetails.enterTaxAmount(taxAmount);
		createInvoicePage.clickUpdateButton();
		createInvoicePage.clickSaveButton();
		AribaSupplierInvoiceReviewAndSubmitPage invoiceReview = createInvoicePage.clickNext();
		invoiceReview.clickSubmitButton();
		tryStoreInvoiceId(invoiceId, testCaseName);
	}

	/**
	 * CARIBA-591
	 * create  invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: VATEUEU_HL_Granular_OverTaxAcceptVertex
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void EUDE_EUFR_HL_Granular_OverAcceptVertexTest( final Method method )
	{
		final String headerLevelTax = "Header level tax";
		final String taxType = "VAT";
		final String taxAmount = "$50.00 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";

		AribaSupplierHomePage supplierHomePage = signInToSupplier(testStartPage);
		AribaSupplierInboxPage inboxPage = supplierHomePage.headerPane.clickInboxButton(inboxButton);
		retrieveAndOpenPurchaseOrder(inboxPage, testCaseName);

		inboxPage.clickCreateInvoiceButton();
		AribaSupplierCreateInvoicePage createInvoicePage = inboxPage.selectStandardInvoice();
		String invoiceId = createInvoicePage.enterInvoiceNumber();
		createInvoicePage.taxDetails.enableCheckBoxByDisplayText(headerLevelTax);
		createInvoicePage.taxDetails.setTaxCategory(taxType);
		createInvoicePage.taxDetails.enterTaxAmount(taxAmount);
		createInvoicePage.clickUpdateButton();
		createInvoicePage.clickSaveButton();
		AribaSupplierInvoiceReviewAndSubmitPage invoiceReview = createInvoicePage.clickNext();
		invoiceReview.clickSubmitButton();
		tryStoreInvoiceId(invoiceId, testCaseName);
	}

	/**
	 * CARIBA-659
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: CAN_BCON_HST_LL_Summarized_OverTaxAcceptVertex
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void CAN_BC_ON_GST_HST_LL_Summarized_OverTaxAcceptVertexTest( final Method method )
	{
		final String headerLevelTax = "Header level tax";
		final String taxType = "VAT";
		final String taxAmount = "$50.00 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";

		AribaSupplierHomePage supplierHomePage = signInToSupplier(testStartPage);
		AribaSupplierInboxPage inboxPage = supplierHomePage.headerPane.clickInboxButton(inboxButton);
		retrieveAndOpenPurchaseOrder(inboxPage, testCaseName);

		inboxPage.clickCreateInvoiceButton();
		AribaSupplierCreateInvoicePage createInvoicePage = inboxPage.selectStandardInvoice();
		String invoiceId = createInvoicePage.enterInvoiceNumber();
		createInvoicePage.taxDetails.enableCheckBoxByDisplayText(headerLevelTax);
		createInvoicePage.taxDetails.setTaxCategory(taxType);
		createInvoicePage.taxDetails.enterTaxAmount(taxAmount);
		createInvoicePage.clickUpdateButton();
		createInvoicePage.clickSaveButton();
		AribaSupplierInvoiceReviewAndSubmitPage invoiceReview = createInvoicePage.clickNext();
		invoiceReview.clickSubmitButton();
		tryStoreInvoiceId(invoiceId, testCaseName);
	}

	/**
	 * CARIBA-586
	 * create PO invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: APACSGJP_LLSummarized_OverandUnderTaxAcceptVendor
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void APAC_SG_JP_LL_Summarized_OverAndUnderAcceptVendorTest( final Method method )
	{
		final String headerLevelTax = "Header level tax";
		final String taxType = "VAT";
		final String taxAmount = "$50.00 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";

		AribaSupplierHomePage supplierHomePage = signInToSupplier(testStartPage);
		AribaSupplierInboxPage inboxPage = supplierHomePage.headerPane.clickInboxButton(inboxButton);
		retrieveAndOpenPurchaseOrder(inboxPage, testCaseName);

		inboxPage.clickCreateInvoiceButton();
		AribaSupplierCreateInvoicePage createInvoicePage = inboxPage.selectStandardInvoice();
		String invoiceId = createInvoicePage.enterInvoiceNumber();
		createInvoicePage.taxDetails.enableCheckBoxByDisplayText(headerLevelTax);
		createInvoicePage.taxDetails.setTaxCategory(taxType);
		createInvoicePage.taxDetails.enterTaxAmount(taxAmount);
		createInvoicePage.clickUpdateButton();
		createInvoicePage.clickSaveButton();
		AribaSupplierInvoiceReviewAndSubmitPage invoiceReview = createInvoicePage.clickNext();
		invoiceReview.clickSubmitButton();
		tryStoreInvoiceId(invoiceId, testCaseName);
	}

	/**
	 * CARIBA-662
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: CAN_BCQC_HL_Summarized_OverUnderTaxAcceptVertex
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void CAN_BC_QC_GSTQST_HL_Summarized_OverUnderTaxAcceptVertexTest( final Method method )
	{
		final String headerLevelTax = "Header level tax";
		final String taxType = "VAT";
		final String taxAmount = "$50.00 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";

		AribaSupplierHomePage supplierHomePage = signInToSupplier(testStartPage);
		AribaSupplierInboxPage inboxPage = supplierHomePage.headerPane.clickInboxButton(inboxButton);
		retrieveAndOpenPurchaseOrder(inboxPage, testCaseName);

		inboxPage.clickCreateInvoiceButton();
		AribaSupplierCreateInvoicePage createInvoicePage = inboxPage.selectStandardInvoice();
		String invoiceId = createInvoicePage.enterInvoiceNumber();
		createInvoicePage.taxDetails.enableCheckBoxByDisplayText(headerLevelTax);
		createInvoicePage.taxDetails.setTaxCategory(taxType);
		createInvoicePage.taxDetails.enterTaxAmount(taxAmount);
		createInvoicePage.clickUpdateButton();
		createInvoicePage.clickSaveButton();
		AribaSupplierInvoiceReviewAndSubmitPage invoiceReview = createInvoicePage.clickNext();
		invoiceReview.clickSubmitButton();
		tryStoreInvoiceId(invoiceId, testCaseName);
	}

	/**
	 * CARIBA-646
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: CAN_CNQCUSPA_LHL_Summarized_UnderTaxAcceptVendor
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void CAN_CNQC_USPA_LL_Summarized_UnderTaxAcceptVendorTest( final Method method )
	{
		final String headerLevelTax = "Header level tax";
		final String taxType = "VAT";
		final String taxAmount = "$50.00 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";

		AribaSupplierHomePage supplierHomePage = signInToSupplier(testStartPage);
		AribaSupplierInboxPage inboxPage = supplierHomePage.headerPane.clickInboxButton(inboxButton);
		retrieveAndOpenPurchaseOrder(inboxPage, testCaseName);

		inboxPage.clickCreateInvoiceButton();
		AribaSupplierCreateInvoicePage createInvoicePage = inboxPage.selectStandardInvoice();
		String invoiceId = createInvoicePage.enterInvoiceNumber();
		createInvoicePage.taxDetails.enableCheckBoxByDisplayText(headerLevelTax);
		createInvoicePage.taxDetails.setTaxCategory(taxType);
		createInvoicePage.taxDetails.enterTaxAmount(taxAmount);
		createInvoicePage.clickUpdateButton();
		createInvoicePage.clickSaveButton();
		AribaSupplierInvoiceReviewAndSubmitPage invoiceReview = createInvoicePage.clickNext();
		invoiceReview.clickSubmitButton();
		tryStoreInvoiceId(invoiceId, testCaseName);
	}
}
