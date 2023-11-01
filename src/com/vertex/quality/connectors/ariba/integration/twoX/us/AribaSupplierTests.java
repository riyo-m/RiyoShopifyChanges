package com.vertex.quality.connectors.ariba.integration.twoX.us;

import com.vertex.quality.connectors.ariba.supplier.pages.AribaSupplierCreateInvoicePage;
import com.vertex.quality.connectors.ariba.supplier.pages.AribaSupplierHomePage;
import com.vertex.quality.connectors.ariba.supplier.pages.AribaSupplierInboxPage;
import com.vertex.quality.connectors.ariba.supplier.pages.AribaSupplierInvoiceReviewAndSubmitPage;
import com.vertex.quality.connectors.ariba.supplier.tests.AribaSupplierBaseTest;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import static org.hamcrest.Matchers.equalTo;

/**
 * this class contains tests that do invoicing from the supplier site
 * these tests are incorporated with others tests in another test classes to perform a complete Ariba transaction,
 * from buyer to supplier, and back to buyer to reconcile.
 *
 * @author osabha
 */

public class AribaSupplierTests extends AribaSupplierBaseTest
{
	/**
	 * CARIBA-272
	 * Multiple Items, Header Level Taxes Summarized NoException California - within Thresh matches
	 *
	 * @param method instance of the Method class to get test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void headerNoExceptionWithinThreshTest( final Method method )
	{
		final String headerLevelTax = "Header level tax";
		final String taxType = "Sales";
		final String taxAmount = "$14.35 USD";
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
		ValidatableResponse messageLog = apiUtil.getXMLMessageLog("IR201910020952", "3", 4, "vrealm_2174");
		messageLog
			.assertThat()
			.body("**.find { it.name() == 'ChargedTax' }", equalTo(0.0))
			.body("**. find {it.name()=='OutsideThreshold'}", equalTo(false))
			.body("**. find {it.name()=='TotalTax'}", equalTo(14.35));
		tryStoreInvoiceId(invoiceId, testCaseName);
	}

	/**
	 * CARIBA-273
	 * Multiple Items, Header Level Taxes Summarized NoException California - within Thresh matches
	 *
	 * @param method instance of the Method class to get test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void lineNoExceptionWithinThreshTest( final Method method )
	{
		final String lineLevelTax = "Line level tax";
		final String taxType = "Sales";
		final String chickenTaxes = "$9.50 USD";
		final String duckTaxes = "$4.85 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";
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
	 * CARIBA-320
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_UnderTaxDisputed
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void headUnderTaxDisputedCaTest( final Method method )
	{
		final String headerLevelTax = "Header level tax";
		final String taxType = "Sales";
		final String taxAmount = "$11.00 USD";
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
	 * CARIBA-440
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_OverTaxDisputed
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void headOverTaxDisputedDeTest( final Method method )
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
	 * CARIBA-441
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_OverTaxDisputed_NoConfigMAP
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void headOverTaxDisputedNoConfigTest( final Method method )
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
	 * CARIBA-385
	 * create PO invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: LL_Summarized_TaxAccepted_NONELA
	 *
	 * @param method instance of the Method class to get test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LL_Summarized_TaxAccepted_LA_NONETest( final Method method )
	{
		final String lineLevelTax = "Line level tax";
		final String taxType = "Sales";
		final String chickenTaxes = "$9.50 USD";
		final String duckTaxes = "$4.85 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";
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
	 * CARIBA-350
	 * create invoice and reconcile through payment attach OK to pay
	 * to paymentbased on Ariba Test: LL_Summarized_0TaxAccepted
	 *
	 * @param method instance of the Method class to get test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LL_Summarized_0TaxAccepted_LA_0_Vendor_Tax_Reject_VertexTest( final Method method )
	{
		final String lineLevelTax = "Line level tax";
		final String taxType = "Sales";
		final String chickenTaxes = "$9.50 USD";
		final String duckTaxes = "$4.85 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";
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
	 * CARIBA-353
	 * create PO invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Granular_NoExceptionLA
	 *
	 * @param method instance of the Method class to get test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Granular_NoExceptionLA_withinThreshMatchesTest( final Method method )
	{
		final String lineLevelTax = "Line level tax";
		final String taxType = "Sales";
		final String chickenTaxes = "$9.50 USD";
		final String duckTaxes = "$4.85 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";
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
	 * CARIBA-327
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: LL_Granular_OverTaxDisputed_LA
	 *
	 * @param method instance of the Method class to get test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LL_Granular_OverTaxDisputedCA_OverThresh_pickVertexTest( final Method method )
	{
		final String lineLevelTax = "Line level tax";
		final String taxType = "Sales";
		final String chickenTaxes = "$9.50 USD";
		final String duckTaxes = "$4.85 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";
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
	 * CARIBA-389
	 * create PO invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: LL_Summarized_UnderTaxAccepted_DiscountandShipping
	 *
	 * @param method instance of the Method class to get test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LL_Granular_UnderTaxAcceptedLA_DiscountandShippingTest( final Method method )
	{
		final String lineLevelTax = "Line level tax";
		final String taxType = "Sales";
		final String chickenTaxes = "$9.50 USD";
		final String duckTaxes = "$4.85 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";
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
	 * CARIBA-556
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_OverTaxDisputed_NoConfigComponentMAP
	 *
	 * @param method instance of the Method class to get test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Summarized_OverTaxDisputed_noConfigComponentMappingTest( final Method method )
	{
		final String lineLevelTax = "Line level tax";
		final String taxType = "Sales";
		final String chickenTaxes = "$9.50 USD";
		final String duckTaxes = "$4.85 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";
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
	 * CARIBA-370
	 * create  invoice and reconcile through payment attach OK to pay to paymentbased on Ariba Test:
	 * BPHL_Granular_0TaxDisputedDPP
	 *
	 * @param method instance of the Method class to get test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Granular_0TaxDisputed_0VendorTaxAcceptVertexDPPBuyerPAYTest( final Method method )
	{
		final String lineLevelTax = "Line level tax";
		final String taxType = "Sales";
		final String chickenTaxes = "$9.50 USD";
		final String duckTaxes = "$4.85 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";
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
	 * CARIBA-386
	 * create PO invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_OverTaxAccepted_VendorNotRegistered
	 *
	 * @param method instance of the Method class to get test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Summarized_OverTaxAcceptedFL_VendorNotRegisteredTest( final Method method )
	{
		final String lineLevelTax = "Line level tax";
		final String taxType = "Sales";
		final String chickenTaxes = "$9.50 USD";
		final String duckTaxes = "$4.85 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";
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
	 * CARIBA-352
	 * create invoice and reconcile through payment attach OK to pay to paymentbased on Ariba Test:
	 * LL_Granular_NoException
	 *
	 * @param method instance of the Method class to get test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LL_Granular_NoExceptionLAwithinThreshMatchesTest( final Method method )
	{
		final String lineLevelTax = "Line level tax";
		final String taxType = "Sales";
		final String chickenTaxes = "$9.50 USD";
		final String duckTaxes = "$4.85 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";
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
	 * CARIBA-390
	 * create PO invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: LL_Summarized_UnderTaxAccepted_NoMappingVertexTaxLA
	 *
	 * @param method instance of the Method class to get test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LL_Granular_UnderTaxAcceptedLA_NoMappingVertexTaxTest( final Method method )
	{
		final String lineLevelTax = "Line level tax";
		final String taxType = "Sales";
		final String chickenTaxes = "$9.50 USD";
		final String duckTaxes = "$4.85 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";
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
	 * CARIBA-391
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Granular_UnderTaxDisputed_NoVendorTaxType
	 *
	 * @param method instance of the Method class to get test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Granular_UnderTaxDisputedCA_NoVendorTaxTypeTest( final Method method )
	{
		final String lineLevelTax = "Line level tax";
		final String taxType = "Sales";
		final String chickenTaxes = "$9.50 USD";
		final String duckTaxes = "$4.85 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";
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
	 * CARIBA-269
	 * This test will create a basic requisition with shipping and invoice
	 *
	 * @param method instance of the Method class to get test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void basic_Requisition_Invoiced_with_shippingTest( final Method method )
	{
		final String lineLevelTax = "Line level tax";
		final String taxType = "Sales";
		final String chickenTaxes = "$9.50 USD";
		final String duckTaxes = "$4.85 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";
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
	 * CARIBA-324
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_0TaxDisputed
	 *
	 * @param method instance of the Method class to get test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Summarized_0TaxAcceptVertexTest( final Method method )
	{
		final String lineLevelTax = "Line level tax";
		final String taxType = "Sales";
		final String chickenTaxes = "$9.50 USD";
		final String duckTaxes = "$4.85 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";
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
	 * CARIBA-319
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_OverTaxDisputed
	 *
	 * @param method instance of the Method class to get test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Summarized_OverTaxDisputed_CA_OverThreshPickVertexTest( final Method method )
	{
		final String lineLevelTax = "Line level tax";
		final String taxType = "Sales";
		final String chickenTaxes = "$9.50 USD";
		final String duckTaxes = "$4.85 USD";
		final String testCaseName = method.getName();
		final String inboxButton = "INBOX";
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
}


