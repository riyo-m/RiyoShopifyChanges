package com.vertex.quality.connectors.ariba.integration.twoX.vat;

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
 * this class contains tests that do invoice reconciliation  from the Buyers site
 * these tests are incorporated with others tests in another test classes to perform a complete Ariba transaction,
 * from buyer to supplier, and back to buyer to reconcile.
 * VAT scenarios only
 *
 * @author osabha
 */

public class AribaReconciliationTests extends AribaTwoXPortalBaseTest
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
	 * CARIBA-277
	 * This test will create a basic requisition for VAT (DE-DE) and invoice
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void basicRequisitionDEToDETest( final Method method )
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
	 * CARIBA-583
	 * This test will create a basic requisition for VAT (DE- US CA)
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void EuDEToUSCAHLOverTaxAcceptVendorTest( final Method method )
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
	 * CARIBA-606
	 * This test will create a basic requisition for VAT (EUFR- EUBE )
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void VATEUFREUBENotRegisteredLL_Summarized_MatchTest( final Method method )
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
	 * CARIBA-648
	 * This test will create a basic requisition for VAT (EUFR- EUBE )
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void CAN_NB_NB_HST_HL_Summarized_OverTaxAcceptVendorTest( final Method method )
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
	 * CARIBA-305
	 * Create a Purchase order for VAT (EU-US), invoice and validate the xml
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void CreatePurchaseOrderforVATUS_EU_andInvoiceTest( final Method method )
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
	 * CARIBA-582
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * * based on Ariba Test: VATUSEU_HL_Summarized_OverTaxAcceptVertex
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void USPA_EUDE_HL_Summarized_OverTaxAcceptVertexTest( final Method method )
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
	 * CARIBA-584
	 * create invoice and reconcile through payment attach OK to pay to
	 * paymentbased on Ariba Test: VATSG_FR_HL_Summarized_UnderTaxAcceptVendor
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void NonEUSG_EUFR_HL_Summarized_UnderTaxAcceptVendorTest( final Method method )
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
	 * CARIBA-585
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: LATAM_CRCO_HLSummarized_OverTaxAcceptVertex
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LATAM_CR_CO_HL_Summarized_OverTaxAcceptVertexTest( final Method method )
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
	 * CARIBA-587
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: VATEUEU_HL_Summarized_0VendorTaxAcceptVertex
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void EUDE_EUFR_HL_Summarized_0TaxAcceptVertexTest( final Method method )
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
	 * CARIBA-588
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: VATEUEU_HL_Summarized_0VendorTaxAcceptVendor
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void EUDE_EUFR_HL_Summarized_0TaxAcceptVendorTest( final Method method )
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
	 * CARIBA-589
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: VATEUEU_HL_Summarized_Match
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void EUDE_EUFR_HL_Summarized_MatchTest( final Method method )
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
	 * CARIBA-591
	 * create  invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: VATEUEU_HL_Granular_OverTaxAcceptVertex
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void EUDE_EUFR_HL_Granular_OverAcceptVertexTest( final Method method )
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
	 * CARIBA-659
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: CAN_BCON_HST_LL_Summarized_OverTaxAcceptVertex
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void CAN_BC_ON_GST_HST_LL_Summarized_OverTaxAcceptVertexTest( final Method method )
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
	 * CARIBA-586
	 * create PO invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: APACSGJP_LLSummarized_OverandUnderTaxAcceptVendor
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void APAC_SG_JP_LL_Summarized_OverAndUnderAcceptVendorTest( final Method method )
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
	 * CARIBA-662
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: CAN_BCQC_HL_Summarized_OverUnderTaxAcceptVertex
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void CAN_BC_QC_GSTQST_HL_Summarized_OverUnderTaxAcceptVertexTest( final Method method )
	{
		String testCaseName = method.getName();
		String invoiceId = tryGetInvoiceId(testCaseName);
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(AribaPortalManageListPage.MY_TODO);

		toDoPage.searchForInvoice(invoiceId);
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice(invoiceId);
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
		boolean taxExceptionsPresent = reconciliationPage.exceptionsTab.isThereAnyTaxVariance();
		assertTrue(!taxExceptionsPresent);
		tryDeleteOrderRecordFromDatabase(testCaseName);
	}

	/**
	 * CARIBA-646
	 *
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: CAN_CNQCUSPA_LHL_Summarized_UnderTaxAcceptVendor
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void CAN_CNQC_USPA_LL_Summarized_UnderTaxAcceptVendorTest( final Method method )
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
}
