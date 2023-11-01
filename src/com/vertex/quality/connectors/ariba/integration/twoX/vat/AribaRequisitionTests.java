package com.vertex.quality.connectors.ariba.integration.twoX.vat;

import com.vertex.quality.connectors.ariba.portal.enums.AribaPlants;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import com.vertex.quality.connectors.ariba.portal.pojos.AribaLineItem;
import com.vertex.quality.connectors.ariba.portal.pojos.AribaTaxResult;
import com.vertex.quality.connectors.ariba.portal.tests.base.AribaTwoXPortalBaseTest;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * this class contains tests that do requisitions from the Buyers site
 * these tests are incorporated with others tests in another test classes to perform a complete Ariba transaction,
 * from buyer to supplier, and back to buyer to reconcile.
 * VAT scenarios only
 *
 * @author osabha
 */

public class AribaRequisitionTests extends AribaTwoXPortalBaseTest
{
	/**
	 * CARIBA-277
	 * This test will create a basic requisition for VAT (DE-DE) and invoice
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void basicRequisitionDEToDETest( final Method method )
	{
		String caCountyTax = "COUNTY LOS ANGELES: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE CALIFORNIA: Sales and Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult countyTax1 = new AribaTaxResult(100, 0.00, caCountyTax, 1, "0.0%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 0.00, caStateTax, 1, "0.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(countyTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(chicken, lancasterShipFromAddress, AribaPlants.US_CA_LOS_ANGELES, californiaBillToAddress, "1", 1, 0.00, stateTax1);

		item1.setTaxResults(taxResults1);

		AribaTaxResult countyTax2 = new AribaTaxResult(51.00, 0.00, caCountyTax, 1, "0.0%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 0.00, caStateTax, 1, "0.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(countyTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(duck, lancasterShipFromAddress, AribaPlants.US_CA_LOS_ANGELES, californiaBillToAddress, "2", 2, 0.00, stateTax2);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();
		String requisitionTitle = "Basic requisition for VAT (DE-DE) and invoice";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
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
		String expectedTaxCode = "Tax Code: I1,";

		String caCountyTax = "COUNTY LOS ANGELES: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE CALIFORNIA: Sales and Use Tax - Invoice Text: SELLER_USE";
		String geCountryTax = "COUNTRY GERMANY: VAT";

		AribaTaxResult caCountyTax1 = new AribaTaxResult(100, 0.00, caCountyTax, 1, "0.0%", false, testCurrency);
		AribaTaxResult caStateTax1 = new AribaTaxResult(100, 0.00, caStateTax, 2, "0.0%", false, testCurrency);
		AribaTaxResult geCountryTax1 = new AribaTaxResult(100, 0.00, geCountryTax, 3, "0.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(caCountyTax1);
		taxResults1.add(caStateTax1);
		taxResults1.add(geCountryTax1);

		AribaLineItem item1 = buildTestDataItem(chicken, "TestAutomationS_DEOranienstrabe", AribaPlants.US_CA_LOS_ANGELES, californiaBillToAddress, "1", 1, 0.00);

		item1.setTaxResults(taxResults1);

		AribaTaxResult caCountyTax2 = new AribaTaxResult(51.00, 0.00, caCountyTax, 1, "0.0%", false, testCurrency);
		AribaTaxResult caStateTax2 = new AribaTaxResult(51.00, 0.00, caStateTax, 2, "0.0%", false, testCurrency);
		AribaTaxResult geCountryTax2 = new AribaTaxResult(51.00, 0.00, geCountryTax, 3, "0.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(caCountyTax2);
		taxResults2.add(caStateTax2);
		taxResults2.add(geCountryTax2);

		AribaLineItem item2 = buildTestDataItem(duck, "TestAutomationS_DEOranienstrabe", AribaPlants.US_CA_LOS_ANGELES, californiaBillToAddress, "2", 2, 0.00);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);

		String testCaseName = method.getName();

		String requisitionTitle = "VATEU_US_HL_Summarized_OverTaxAcceptVendor";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, expectedTaxCode,
			testData);
	}

	/**
	 * CARIBA-590
	 * This test will create a basic requisition for VAT (DE- US CA)
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void VATEUEU_LL_Summarized_UnderTaxAcceptVertexTest( final Method method )
	{
		String geCountryTax = "COUNTRY GERMANY: VAT - 100% Deductible";

		AribaTaxResult geCountyTax1 = new AribaTaxResult(100, 19.00, geCountryTax, 1, "19.0%", true, testCurrency);

		AribaLineItem item1 = buildTestDataItem(chicken, "TestAutomationS_EUDE_Berlin", AribaPlants.EU_DE_BERLIN, "TestAutomationPL_EUDE_Berlin", "1", 1, 19.00, geCountyTax1);

		AribaTaxResult geCountyTax2 = new AribaTaxResult(51.00, 9.69, geCountryTax, 1, "19.0%", true, testCurrency);

		AribaLineItem item2 = buildTestDataItem(duck, "TestAutomationS_EUDE_Berlin", AribaPlants.EU_DE_BERLIN, "TestAutomationPL_EUDE_Berlin", "2", 2, 9.69, geCountyTax2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();
		String requisitionTitle = "VATEUEU_LL_Summarized_UnderTaxAcceptVertex";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, "Tax Code: I1,",
			testData);
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
		String frCountryTax = "COUNTRY FRANCE: VAT - Invoice Text: IntraEU - subject to reverse charge";

		AribaTaxResult stateTax1 = new AribaTaxResult(100, 0.00, frCountryTax, 1, "0.0%", false, testCurrency);

		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 0.00, frCountryTax, 1, "0.0%", false, testCurrency);

		AribaLineItem item1 = buildTestDataItem(chicken, "TestAutomationS_EUFR_Paris", AribaPlants.EU_DE_BERLIN, "TestAutomationPL_BE_Gen", "1", 1, 0.00, stateTax1);

		AribaLineItem item2 = buildTestDataItem(duck, "TestAutomationS_EUFR_Paris", AribaPlants.EU_DE_BERLIN, "TestAutomationPL_BE_Gen", "2", 2, 0.00, stateTax2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();
		String requisitionTitle = "VATEUFREUBENotRegisteredLL_Summarized_MatchTest";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, "Tax Code: I1,",
			testData);
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
		String canCountryTax = "COUNTRY CANADA: GST/HST - 100% Deductible - Invoice Text: VAT";

		AribaTaxResult stateTax1 = new AribaTaxResult(100, 15.00, canCountryTax, 1, "15.0%", true, testCurrency);

		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 7.65, canCountryTax, 1, "15.0%", true, testCurrency);

		AribaLineItem item1 = buildTestDataItem(chicken, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT, "TestAutomationPL_CA_NB", "1", 1, 15.00, stateTax1);

		AribaLineItem item2 = buildTestDataItem(duck, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT, "TestAutomationPL_CA_NB", "2", 2, 7.65, stateTax2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();
		String requisitionTitle = "CAN_NBNB_HL_Summarized_OverTaxAcceptVendor";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName,
			"Tax Code: I1, A/P 7% GST, 8% Ont PST distributed", testData);
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
		String canCountryTax = "COUNTRY CANADA: GST/HST - 100% Deductible - Invoice Text: VAT";

		AribaTaxResult stateTax1 = new AribaTaxResult(100, 15.00, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 7.65, canCountryTax, 1, "15.0%", true, testCurrency);

		AribaLineItem item1 = buildTestDataItem(chicken, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT, "TestAutomationPL_CA_NB", "1", 1, 15.00, stateTax1);

		AribaLineItem item2 = buildTestDataItem(duck, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT, "TestAutomationPL_CA_NB", "2", 2, 7.65, stateTax2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();
		String requisitionTitle = "CAN_NBNB_HL_Summarized_OverTaxAcceptVendor";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName,
			"Tax Code: I1, A/P 7% GST, 8% Ont PST distributed", testData);
	}

	/**
	 * CARIBA-582
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: VATUSEU_HL_Summarized_OverTaxAcceptVertex
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void USPA_EUDE_HL_Summarized_OverTaxAcceptVertexTest( final Method method )
	{
		String canCountryTax = "COUNTRY CANADA: GST/HST - 100% Deductible - Invoice Text: VAT";

		AribaTaxResult stateTax1 = new AribaTaxResult(100, 15.00, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 7.65, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaLineItem item1 = buildTestDataItem(chicken, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "1", 1, 15.00, stateTax1);
		AribaLineItem item2 = buildTestDataItem(duck, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "2", 2, 7.65, stateTax2);
		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();
		String requisitionTitle = "CAN_NBNB_HL_Summarized_OverTaxAcceptVendor";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName,
			"Tax Code: I1, A/P 7% GST, 8% Ont PST distributed", testData);
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
		String canCountryTax = "COUNTRY CANADA: GST/HST - 100% Deductible - Invoice Text: VAT";

		AribaTaxResult stateTax1 = new AribaTaxResult(100, 15.00, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 7.65, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaLineItem item1 = buildTestDataItem(chicken, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "1", 1, 15.00, stateTax1);
		AribaLineItem item2 = buildTestDataItem(duck, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "2", 2, 7.65, stateTax2);
		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();
		String requisitionTitle = "CAN_NBNB_HL_Summarized_OverTaxAcceptVendor";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName,
			"Tax Code: I1, A/P 7% GST, 8% Ont PST distributed", testData);
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
		String canCountryTax = "COUNTRY CANADA: GST/HST - 100% Deductible - Invoice Text: VAT";

		AribaTaxResult stateTax1 = new AribaTaxResult(100, 15.00, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 7.65, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaLineItem item1 = buildTestDataItem(chicken, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "1", 1, 15.00, stateTax1);
		AribaLineItem item2 = buildTestDataItem(duck, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "2", 2, 7.65, stateTax2);
		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();
		String requisitionTitle = "CAN_NBNB_HL_Summarized_OverTaxAcceptVendor";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName,
			"Tax Code: I1, A/P 7% GST, 8% Ont PST distributed", testData);
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
		String canCountryTax = "COUNTRY CANADA: GST/HST - 100% Deductible - Invoice Text: VAT";

		AribaTaxResult stateTax1 = new AribaTaxResult(100, 15.00, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 7.65, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaLineItem item1 = buildTestDataItem(chicken, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "1", 1, 15.00, stateTax1);
		AribaLineItem item2 = buildTestDataItem(duck, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "2", 2, 7.65, stateTax2);
		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();
		String requisitionTitle = "CAN_NBNB_HL_Summarized_OverTaxAcceptVendor";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName,
			"Tax Code: I1, A/P 7% GST, 8% Ont PST distributed", testData);
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
		String canCountryTax = "COUNTRY CANADA: GST/HST - 100% Deductible - Invoice Text: VAT";

		AribaTaxResult stateTax1 = new AribaTaxResult(100, 15.00, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 7.65, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaLineItem item1 = buildTestDataItem(chicken, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "1", 1, 15.00, stateTax1);
		AribaLineItem item2 = buildTestDataItem(duck, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "2", 2, 7.65, stateTax2);
		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();
		String requisitionTitle = "CAN_NBNB_HL_Summarized_OverTaxAcceptVendor";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName,
			"Tax Code: I1, A/P 7% GST, 8% Ont PST distributed", testData);
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
		String canCountryTax = "COUNTRY CANADA: GST/HST - 100% Deductible - Invoice Text: VAT";

		AribaTaxResult stateTax1 = new AribaTaxResult(100, 15.00, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 7.65, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaLineItem item1 = buildTestDataItem(chicken, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "1", 1, 15.00, stateTax1);
		AribaLineItem item2 = buildTestDataItem(duck, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "2", 2, 7.65, stateTax2);
		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();
		String requisitionTitle = "CAN_NBNB_HL_Summarized_OverTaxAcceptVendor";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName,
			"Tax Code: I1, A/P 7% GST, 8% Ont PST distributed", testData);
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
		String canCountryTax = "COUNTRY CANADA: GST/HST - 100% Deductible - Invoice Text: VAT";

		AribaTaxResult stateTax1 = new AribaTaxResult(100, 15.00, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 7.65, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaLineItem item1 = buildTestDataItem(chicken, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "1", 1, 15.00, stateTax1);
		AribaLineItem item2 = buildTestDataItem(duck, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "2", 2, 7.65, stateTax2);
		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();
		String requisitionTitle = "CAN_NBNB_HL_Summarized_OverTaxAcceptVendor";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName,
			"Tax Code: I1, A/P 7% GST, 8% Ont PST distributed", testData);
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
		String canCountryTax = "COUNTRY CANADA: GST/HST - 100% Deductible - Invoice Text: VAT";

		AribaTaxResult stateTax1 = new AribaTaxResult(100, 15.00, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 7.65, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaLineItem item1 = buildTestDataItem(chicken, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "1", 1, 15.00, stateTax1);
		AribaLineItem item2 = buildTestDataItem(duck, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "2", 2, 7.65, stateTax2);
		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();
		String requisitionTitle = "CAN_NBNB_HL_Summarized_OverTaxAcceptVendor";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName,
			"Tax Code: I1, A/P 7% GST, 8% Ont PST distributed", testData);
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
		String canCountryTax = "COUNTRY CANADA: GST/HST - 100% Deductible - Invoice Text: VAT";

		AribaTaxResult stateTax1 = new AribaTaxResult(100, 15.00, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 7.65, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaLineItem item1 = buildTestDataItem(chicken, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "1", 1, 15.00, stateTax1);
		AribaLineItem item2 = buildTestDataItem(duck, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "2", 2, 7.65, stateTax2);
		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();
		String requisitionTitle = "CAN_NBNB_HL_Summarized_OverTaxAcceptVendor";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName,
			"Tax Code: I1, A/P 7% GST, 8% Ont PST distributed", testData);
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
		String canCountryTax = "COUNTRY CANADA: GST/HST - 100% Deductible - Invoice Text: VAT";

		AribaTaxResult stateTax1 = new AribaTaxResult(100, 15.00, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 7.65, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaLineItem item1 = buildTestDataItem(chicken, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "1", 1, 15.00, stateTax1);
		AribaLineItem item2 = buildTestDataItem(duck, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "2", 2, 7.65, stateTax2);
		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();
		String requisitionTitle = "CAN_NBNB_HL_Summarized_OverTaxAcceptVendor";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName,
			"Tax Code: I1, A/P 7% GST, 8% Ont PST distributed", testData);
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
		String canCountryTax = "COUNTRY CANADA: GST/HST - 100% Deductible - Invoice Text: VAT";

		AribaTaxResult stateTax1 = new AribaTaxResult(100, 15.00, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 7.65, canCountryTax, 1, "15.0%", true, testCurrency);
		AribaLineItem item1 = buildTestDataItem(chicken, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "1", 1, 15.00, stateTax1);
		AribaLineItem item2 = buildTestDataItem(duck, "TestAutomationS_CA_NB", AribaPlants.CA_NB_PLANT,
			"TestAutomationPL_CA_NB", "2", 2, 7.65, stateTax2);
		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();
		String requisitionTitle = "CAN_NBNB_HL_Summarized_OverTaxAcceptVendor";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName,
			"Tax Code: I1, A/P 7% GST, 8% Ont PST distributed", testData);
	}
}
