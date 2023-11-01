package com.vertex.quality.connectors.ariba.integration.twoX.us;

import com.vertex.quality.connectors.ariba.portal.enums.AribaPortalManageListPage;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import com.vertex.quality.connectors.ariba.portal.pages.procurement.AribaPortalBeforeReconciliationPage;
import com.vertex.quality.connectors.ariba.portal.pages.procurement.AribaPortalReconciliationPage;
import com.vertex.quality.connectors.ariba.portal.pages.procurement.AribaPortalToDoPage;
import com.vertex.quality.connectors.ariba.portal.tests.base.AribaTwoXPortalBaseTest;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import static org.testng.Assert.assertTrue;

/**
 * this class contains tests that do reconciliation from the portal site
 * these tests are incorporated with others tests in another test classes to perform a complete Ariba transaction,
 * from buyer to supplier, and back to buyer to reconcile.
 *
 * @author osabha
 */

public class AribaReconciliationTests extends AribaTwoXPortalBaseTest
{
	/**
	 * CARIBA-272
	 * Multiple Items, Header Level Taxes Summarized NoException California - within Thresh matches
	 *
	 * @param method instance of the Method class that get the test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void headerNoExceptionWithinThreshTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);
		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		reconciliationPage.exceptionsTab.acceptAllQuantityVariances();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);
		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-273
	 * Multiple Items, Header Level Taxes Summarized NoException California - within Thresh matches
	 *
	 * @param method instance of the method class that
	 *               helps provide information about the test case and test class.
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void lineNoExceptionWithinThreshTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);

		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);

		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-320
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_UnderTaxDisputed
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void headUnderTaxDisputedCaTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);

		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);

		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-440
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_OverTaxDisputed
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void headOverTaxDisputedDeTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);
		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();

		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(taxExceptionsPresent);

		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-441
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_OverTaxDisputed_NoConfigMAP
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void headOverTaxDisputedNoConfigTest( final Method method )
	{

		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);
		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();

		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(taxExceptionsPresent);

		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-385
	 * Multiple Items, Header Level Taxes Summarized NoException California - within Thresh matches
	 *
	 * @param method instance of the Method class that get the test case name
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void LL_Summarized_TaxAccepted_LA_NONETest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);

		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		reconciliationPage.exceptionsTab.acceptAllQuantityVariances();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);
		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-350
	 * create invoice and reconcile through payment attach OK to pay to
	 * paymentbased on Ariba Test: LL_Summarized_0TaxAccepted
	 *
	 * @param method instance of the Method class that get the test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LL_Summarized_0TaxAccepted_LA_0_Vendor_Tax_Reject_VertexTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);

		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		reconciliationPage.exceptionsTab.acceptAllQuantityVariances();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);
		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-353
	 * create PO invoice and reconcile through payment attach OK to pay to
	 * payment based on Ariba Test:HL_Granular_NoExceptionLA
	 *
	 * @param method instance of the Method class that get the test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Granular_NoExceptionLA_withinThreshMatchesTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);

		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		reconciliationPage.exceptionsTab.acceptAllQuantityVariances();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);
		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-327
	 *
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: LL_Granular_OverTaxDisputed_LA
	 *
	 * @param method instance of the Method class that get the test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LL_Granular_OverTaxDisputedCA_OverThresh_pickVertexTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);

		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		reconciliationPage.exceptionsTab.acceptAllQuantityVariances();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);
		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-389
	 *
	 * create PO invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: LL_Summarized_UnderTaxAccepted_DiscountandShipping
	 *
	 * @param method instance of the Method class that get the test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LL_Granular_UnderTaxAcceptedLA_DiscountandShippingTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);

		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		reconciliationPage.exceptionsTab.acceptAllQuantityVariances();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);
		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-556
	 *
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_OverTaxDisputed_NoConfigComponentMAP
	 *
	 * @param method instance of the Method class that get the test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Summarized_OverTaxDisputed_noConfigComponentMappingTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);

		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		reconciliationPage.exceptionsTab.acceptAllQuantityVariances();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);
		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-370
	 * create  invoice and reconcile through payment attach OK to pay to paymentbased on Ariba Test:
	 * BPHL_Granular_0TaxDisputedDPP
	 *
	 * @param method instance of the Method class that get the test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Granular_0TaxDisputed_0VendorTaxAcceptVertexDPPBuyerPAYTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);

		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		reconciliationPage.exceptionsTab.acceptAllQuantityVariances();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);
		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-386
	 * create PO invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_OverTaxAccepted_VendorNotRegistered
	 *
	 * @param method instance of the Method class that get the test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Summarized_OverTaxAcceptedFL_VendorNotRegisteredTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);

		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		reconciliationPage.exceptionsTab.acceptAllQuantityVariances();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);
		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-352
	 * create invoice and reconcile through payment attach OK to pay to paymentbased on Ariba Test:
	 * LL_Granular_NoException
	 *
	 * @param method instance of the Method class that get the test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LL_Granular_NoExceptionLAwithinThreshMatchesTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);

		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		reconciliationPage.exceptionsTab.acceptAllQuantityVariances();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);
		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-390
	 * create PO invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: LL_Summarized_UnderTaxAccepted_NoMappingVertexTaxLA
	 *
	 * @param method instance of the Method class that get the test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LL_Granular_UnderTaxAcceptedLA_NoMappingVertexTaxTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);

		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		reconciliationPage.exceptionsTab.acceptAllQuantityVariances();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);
		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-391
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Granular_UnderTaxDisputed_NoVendorTaxType
	 *
	 * @param method instance of the Method class that get the test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Granular_UnderTaxDisputedCA_NoVendorTaxTypeTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);

		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		reconciliationPage.exceptionsTab.acceptAllQuantityVariances();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);
		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-269
	 * This test will create a basic requisition with shipping and invoice
	 *
	 * @param method instance of the Method class that get the test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void basic_Requisition_Invoiced_with_shippingTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);

		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		reconciliationPage.exceptionsTab.acceptAllQuantityVariances();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);
		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-324
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_0TaxDisputed
	 *
	 * @param method instance of the Method class that get the test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Summarized_0TaxAcceptVertexTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);

		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		reconciliationPage.exceptionsTab.acceptAllQuantityVariances();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);
		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-319
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_OverTaxDisputed
	 *
	 * @param method instance of the Method class that get the test case name
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Summarized_OverTaxDisputed_CA_OverThreshPickVertexTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);
		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		reconciliationPage.exceptionsTab.acceptAllQuantityVariances();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);
		tryDeleteOrderRecordFromDatabase(testCaseName);
	}
}
