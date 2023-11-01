package com.vertex.quality.connectors.ariba.integration.twoX.us;

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
 * this class contains tests that do requisition from the portal site
 * these tests are incorporated with others tests in another test classes to perform a complete Ariba transaction,
 * from buyer to supplier, and back to buyer to reconcile.
 *
 * @author osabha
 */

public class AribaRequisitionTests extends AribaTwoXPortalBaseTest
{

	/**
	 * CARIBA-272
	 * Multiple Items, Header Level Taxes Summarized NoException California - within Thresh matches.
	 *
	 * @param method instance of the Method class to help get the test case name during the test run
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void headerNoExceptionWithinThreshTest( final Method method )
	{
		String caCityTax1 = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax1 = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax1, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax1, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
			    californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		String caCityTax2 = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax2 = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax2, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax2, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
			    californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);

		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case - HL_Summarized_NoException CA - within Thresh matches";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-273
	 * Multiple Items, Line Level Taxes Summarized NoException California - within Thresh matches
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void lineNoExceptionWithinThreshTest( final Method method )
	{
		String caCityTax1 = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax1 = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax1, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax1, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		String caCityTax2 = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax2 = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax2, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax2, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -LL_Summarized_NoException CA - within Thresh matches";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-320
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_UnderTaxDisputed
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void headUnderTaxDisputedCaTest( final Method method )
	{
		String caCityTax1 = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax1 = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax1, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax1, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		String caCityTax2 = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax2 = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax2, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax2, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);// one chicken and two ducks
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -LL_Summarized_NoException CA - within Thresh matches";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-440
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_OverTaxDisputed
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void headOverTaxDisputedDeTest( final Method method )
	{
		//test data of one chicken and two ducks shipped from lancaster to Delaware.

		String caCityTax1 = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax1 = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax1, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax1, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax1, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax1, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputedDE";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-441
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_OverTaxDisputed_NoConfigMAP
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void headOverTaxDisputedNoConfigTest( final Method method )
	{
		String caCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputed_NoConfigMAP";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-771
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_OverTaxDisputed_NoConfigMAP
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_InvoiceOnly_Summarized_0TaxAccepted( final Method method )
	{
		String caCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputed_NoConfigMAP";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-281
	 * Create a Basic Procurement order with shipping and virtual product invoice and validate the xml
	 **/
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void createPurchaseOrderWithShippingAndVirtualOrderAndInvoiceTest( final Method method )
	{
		String caCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputed_NoConfigMAP";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-385
	 * Create a Basic Procurement order with shipping and virtual product invoice and validate the xml
	 **/
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LL_Summarized_TaxAccepted_LA_NONETest( final Method method )
	{
		String caCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputed_NoConfigMAP";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-350
	 * create invoice and reconcile through payment attach OK to pay to
	 * payment based on Ariba Test: LL_Summarized_0TaxAccepted
	 **/
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LL_Summarized_0TaxAccepted_LA_0_Vendor_Tax_Reject_VertexTest( final Method method )
	{
		String caCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputed_NoConfigMAP";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-353
	 * create PO invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Granular_NoExceptionLA
	 **/
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Granular_NoExceptionLA_withinThreshMatchesTest( final Method method )
	{
		String caCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputed_NoConfigMAP";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-327
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: LL_Granular_OverTaxDisputed_LA
	 **/
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LL_Granular_OverTaxDisputedCA_OverThresh_pickVertexTest( final Method method )
	{
		String caCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputed_NoConfigMAP";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-389
	 * create PO invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: LL_Summarized_UnderTaxAccepted_DiscountandShipping
	 **/
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LL_Granular_UnderTaxAcceptedLA_DiscountandShippingTest( final Method method )
	{
		String caCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputed_NoConfigMAP";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-556
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_OverTaxDisputed_NoConfigComponentMAP
	 **/
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Summarized_OverTaxDisputed_noConfigComponentMappingTest( final Method method )
	{
		String caCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputed_NoConfigMAP";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-370
	 * create  invoice and reconcile through payment attach OK to pay to paymentbased on Ariba Test:
	 * BPHL_Granular_0TaxDisputedDPP
	 **/
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Granular_0TaxDisputed_0VendorTaxAcceptVertexDPPBuyerPAYTest( final Method method )
	{
		String caCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputed_NoConfigMAP";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-386
	 * create PO invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_OverTaxAccepted_VendorNotRegistered
	 **/
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Summarized_OverTaxAcceptedFL_VendorNotRegisteredTest( final Method method )
	{
		String caCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputed_NoConfigMAP";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-352
	 * create invoice and reconcile through payment attach OK to pay to paymentbased on Ariba Test:
	 * LL_Granular_NoException
	 **/
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LL_Granular_NoExceptionLAwithinThreshMatchesTest( final Method method )
	{
		String caCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputed_NoConfigMAP";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-390
	 * create PO invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: LL_Summarized_UnderTaxAccepted_NoMappingVertexTaxLA
	 **/
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void LL_Granular_UnderTaxAcceptedLA_NoMappingVertexTaxTest( final Method method )
	{
		String caCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputed_NoConfigMAP";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-391
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Granular_UnderTaxDisputed_NoVendorTaxType
	 **/
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Granular_UnderTaxDisputedCA_NoVendorTaxTypeTest( final Method method )
	{
		String caCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputed_NoConfigMAP";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-269
	 * This test will create a basic requisition with shipping and invoice
	 **/
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void basic_Requisition_Invoiced_with_shippingTest( final Method method )
	{
		String caCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputed_NoConfigMAP";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-324
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_0TaxDisputed
	 **/
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void HL_Summarized_0TaxAcceptVertexTest( final Method method )
	{
		String caCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputed_NoConfigMAP";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}

	/**
	 * CARIBA-319
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: HL_Summarized_OverTaxDisputed
	 **/
	@Test(groups = { "ariba_ui", "ariba_regression" })
	public void HL_Summarized_OverTaxDisputed_CA_OverThreshPickVertexTest( final Method method )
	{
		String caCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax1 = new AribaTaxResult(100, 4.88, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(100, 4.00, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(cityTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = buildTestDataItem(
				chicken, lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"1",
				1,
				8.88);

		item1.setTaxResults(taxResults1);

		AribaTaxResult cityTax2 = new AribaTaxResult(51.00, 2.48, caCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(51.00, 2.04, caStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(cityTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = buildTestDataItem(
				duck,
				lancasterShipFromAddress,
				AribaPlants.US_NY_NEW_YORK,
				californiaBillToAddress,
				"2",
				2,
				4.52);

		item2.setTaxResults(taxResults2);

		List<AribaLineItem> testData = List.of(item1, item2);
		String testCaseName = method.getName();

		String requisitionTitle = "Ariba - Test Case -HL_Summarized_OverTaxDisputed_NoConfigMAP";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);

		executeTestCase(loggedInDashboardPage, ariba_twoX_Supplier, requisitionTitle, testCaseName, defaultTaxCode,
			testData);
	}
}