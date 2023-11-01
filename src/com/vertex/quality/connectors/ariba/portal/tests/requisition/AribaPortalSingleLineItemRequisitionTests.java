package com.vertex.quality.connectors.ariba.portal.tests.requisition;

import com.vertex.quality.connectors.ariba.portal.enums.AribaPlants;
import com.vertex.quality.connectors.ariba.portal.pages.catalog.AribaPortalCatalogHomePage;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import com.vertex.quality.connectors.ariba.portal.pages.requisition.AribaPortalRequisitionCheckoutPage;
import com.vertex.quality.connectors.ariba.portal.pojos.AribaLineItem;
import com.vertex.quality.connectors.ariba.portal.pojos.AribaTaxResult;
import com.vertex.quality.connectors.ariba.portal.tests.base.AribaTwoXPortalBaseTest;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * TODO JIRA lacks a story for the corresponding feature
 * maybe CARIBA-195?
 *
 * These tests cover a number of jurisdictions for order with a single line item, as a quick
 * smoke test that the system is working correctly.
 *
 * @author dgorecki, ssalisbury, osabha
 */

public class AribaPortalSingleLineItemRequisitionTests extends AribaTwoXPortalBaseTest
{

	final String singleLineItemRequisition = "New Requisition- single Line Item";

	/**
	 * CARIBA-464
	 * this is asserting for taxes that the buyer will pay the vendor,
	 * all consumer use taxes are not shown here
	 * Same basic idea - submit a requisition and verify we get taxes back
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void catalogItemTaxALTest( )
	{
		String alCountyTax = "COUNTY JEFFERSON: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String alStateTax = "STATE ALABAMA: Sales and Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult countyTax = new AribaTaxResult(100.00, 6.00, alCountyTax, 1, "6.0%", false, testCurrency);
		AribaTaxResult stateTax = new AribaTaxResult(100.00, 4.00, alStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults = new ArrayList<>();

		taxResults.add(countyTax);
		taxResults.add(stateTax);

		final AribaLineItem fork = AribaLineItem
			.builder()
			.shipFromAddress("TestAutomationS_USPA_KingOfPrussia")
			.plant_shipToAddress(AribaPlants.US_AL_BIRMINGHAM)
			.expectedTaxAmount(10.00)
			.shippingCost("10")
			.build();

		fork.setTaxResults(taxResults);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier, singleLineItemRequisition, fork);


		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, fork);

		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-465
	 * Same basic idea - submit a requisition and verify we get taxes back.  This one is tricky
	 * though because the address is very obscure and does not easily map back to a single
	 * tax area id
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })

	public void catalogItemTaxAZTest( )
	{
		String arCityTax = "CITY APACHE JUNCTION: Local Transaction Privilege and Use Tax - Invoice Text: SELLER_USE";
		String arStateTax = "STATE ARIZONA: Transaction Privilege and Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax = new AribaTaxResult(100.00, 2.40, arCityTax, 1, "2.4%", false, testCurrency);
		AribaTaxResult stateTax = new AribaTaxResult(100.00, 5.60, arStateTax, 1, "5.6%", false, testCurrency);

		List<AribaTaxResult> taxResults = new ArrayList<>();

		taxResults.add(cityTax);
		taxResults.add(stateTax);

		final AribaLineItem fork = AribaLineItem
			.builder()
			.shipFromAddress("TestAutomationS_USPA_KingOfPrussia")
			.plant_shipToAddress(AribaPlants.US_AZ_APACHE_JUNCTION)
			.expectedTaxAmount(8.00)
			.build();

		fork.setTaxResults(taxResults);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier, singleLineItemRequisition, fork);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, fork);

		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-466
	 * Basic single line item requisition test; i like this one because of the number of
	 * jurisdictions that come back
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })

	public void catalogItemTaxCATest( )
	{
		String caCountyTax = "COUNTY LOS ANGELES: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE CALIFORNIA: Sales and Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult countyTax = new AribaTaxResult(100.00, 0.00, caCountyTax, 1, "0.0%", false, testCurrency);
		AribaTaxResult stateTax = new AribaTaxResult(100.00, 0.00, caStateTax, 1, "0.0%", false, testCurrency);

		List<AribaTaxResult> taxResults = new ArrayList<>();

		taxResults.add(countyTax);
		taxResults.add(stateTax);

		final AribaLineItem fork = AribaLineItem
			.builder()
			.shipFromAddress("TestAutomationS_USPA_KingOfPrussia")
			.plant_shipToAddress(AribaPlants.US_CA_LOS_ANGELES)
			.expectedTaxAmount(0.00)
			.build();

		fork.setTaxResults(taxResults);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier, singleLineItemRequisition, fork);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, fork);

		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-467
	 * another single line line requisition test, this time for colorado
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })

	public void catalogItemTaxCOTest( )
	{
		String coCountyTax = "COUNTY DOUGLAS: Local Sales and Use Tax Combined - Invoice Text: SALES";
		String coStateTax = "STATE COLORADO: Sales and Use Tax - Invoice Text: SALES";

		AribaTaxResult countyTax = new AribaTaxResult(100.00, 2.10, coCountyTax, 1, "2.1%", false, testCurrency);
		AribaTaxResult stateTax = new AribaTaxResult(100.00, 2.90, coStateTax, 1, "2.9%", false, testCurrency);

		List<AribaTaxResult> taxResults = new ArrayList<>();

		taxResults.add(countyTax);
		taxResults.add(stateTax);

		final AribaLineItem fork = AribaLineItem
			.builder()
			.shipFromAddress("TestAutomationS_USPA_KingOfPrussia")
			.plant_shipToAddress(AribaPlants.US_CO_HIGHLANDS_RANCH)
			.expectedTaxAmount(5.00)
			.build();

		fork.setTaxResults(taxResults);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier, singleLineItemRequisition, fork);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, fork);

		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-468
	 * Another single line requisition test, this one is for the best state ever (no sales taxes!)
	 * Consequently the tax rate for this one should be $0.
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void catalogItemTaxDETest( )
	{
		String deStateTax = "STATE DELAWARE: Occupation Use Tax / No Sales or Use Tax Imposed - Invoice Text: SELLER_USE";
		AribaTaxResult stateTax = new AribaTaxResult(100.00, 0.00, deStateTax, 1, "0.0%", false, testCurrency);

		final AribaLineItem fork = AribaLineItem
			.builder()
			.shipFromAddress("TestAutomationS_USPA_KingOfPrussia")
			.plant_shipToAddress(AribaPlants.US_DE_NEWARK)
			.expectedTaxAmount(0.00)
			.taxResult(stateTax)
			.build();
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier, singleLineItemRequisition, fork);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, fork);

		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * Here another one, this one using Florida as the jurisdiction.Important note, we used this
	 * one to test tax-exempt items, it is expected that we get 0 taxes back, if we ever do get taxes
	 * back there's something wrong with our setup.
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void catalogItemTaxFLTest( )
	{
		String flCountyTax = "COUNTY ORANGE: Local Sales and Use Tax (Discretionary Sales Surtax) - Invoice Text: SELLER_USE";
		String flStateTax = "STATE FLORIDA: Sales and Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult countyTax = new AribaTaxResult(100.00, 0.50, flCountyTax, 1, "0.5%", false, testCurrency);
		AribaTaxResult stateTax = new AribaTaxResult(100.00, 6.00, flStateTax, 1, "6.0%", false, testCurrency);

		List<AribaTaxResult> taxResults = new ArrayList<>();

		taxResults.add(countyTax);
		taxResults.add(stateTax);

		final AribaLineItem fork = AribaLineItem
			.builder()
			.shipFromAddress("TestAutomationS_USPA_KingOfPrussia")
			.plant_shipToAddress(AribaPlants.US_FL_ORLANDO)
			.expectedTaxAmount(6.50)
			.build();

		fork.setTaxResults(taxResults);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier, singleLineItemRequisition, fork);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, fork);

		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-470
	 * Another one, this one is for Louisiana
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void catalogItemTaxLATest( )
	{
		String laParishTax = "PARISH ORLEANS: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String laStateTax = "STATE LOUISIANA: Sales and Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult parishTax = new AribaTaxResult(100.00, 5.00, laParishTax, 1, "5.0%", false, testCurrency);
		AribaTaxResult stateTax = new AribaTaxResult(100.00, 4.45, laStateTax, 1, "4.45%", false, testCurrency);

		List<AribaTaxResult> taxResults = new ArrayList<>();

		taxResults.add(parishTax);
		taxResults.add(stateTax);

		final AribaLineItem fork = AribaLineItem
			.builder()
			.shipFromAddress("TestAutomationS_USPA_KingOfPrussia")
			.plant_shipToAddress(AribaPlants.US_LA_NEW_ORLEANS)
			.expectedTaxAmount(9.45)
			.build();

		fork.setTaxResults(taxResults);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier, singleLineItemRequisition, fork);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, fork);

		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-472
	 * NY, where the taxes are too high
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void catalogItemTaxNYTest( )
	{
		String nyCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String nyStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax = new AribaTaxResult(100.00, 4.88, nyCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult stateTax = new AribaTaxResult(100.00, 4.00, nyStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults = new ArrayList<>();

		taxResults.add(cityTax);
		taxResults.add(stateTax);

		final AribaLineItem fork = AribaLineItem
			.builder()
			.shipFromAddress("TestAutomationS_USPA_KingOfPrussia")
			.plant_shipToAddress(AribaPlants.US_NY_NEW_YORK)
			.expectedTaxAmount(8.88)
			.build();

		fork.setTaxResults(taxResults);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier, singleLineItemRequisition, fork);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, fork);

		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-473
	 * simple PA test, tax rate is a straight 6%
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void catalogItemTaxPATest( )
	{
		String paStateTax = "STATE PENNSYLVANIA: Sales and Use Tax - Invoice Text: SALES";
		AribaTaxResult stateTax = new AribaTaxResult(100.00, 6.00, paStateTax, 1, "6.0%", false, testCurrency);

		final AribaLineItem fork = AribaLineItem
			.builder()
			.shipFromAddress("TestAutomationS_USPA_KingOfPrussia")
			.plant_shipToAddress(AribaPlants.US_PA_LANCASTER)
			.expectedTaxAmount(6.00)
			.taxResult(stateTax)
			.build();
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier, singleLineItemRequisition, fork);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, fork);

		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-475
	 * Puerto Rico test to ensure we can handle us territories
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void catalogItemTaxPRTest( )
	{
		String prCountyTax = "COUNTY PONCE: Contribution on Retail Sales - Invoice Text: SELLER_USE";
		String prTerrTax = "TERRITORY PUERTO RICO: Sales and Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult countyTax = new AribaTaxResult(100.00, 1.00, prCountyTax, 1, "1.0%", false, testCurrency);
		AribaTaxResult territoryTax = new AribaTaxResult(100.00, 10.50, prTerrTax, 1, "10.5%", false, testCurrency);

		List<AribaTaxResult> taxResults = new ArrayList<>();

		taxResults.add(countyTax);
		taxResults.add(territoryTax);

		final AribaLineItem fork = AribaLineItem
			.builder()
			.shipFromAddress("TestAutomationS_USPA_KingOfPrussia")
			.plant_shipToAddress(AribaPlants.US_PR_PONCE)
			.expectedTaxAmount(11.50)
			.build();

		fork.setTaxResults(taxResults);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier, singleLineItemRequisition, fork);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, fork);

		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-476
	 * This one is for Texas
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void catalogItemTaxTXTest( )
	{
		String txCityTax = "CITY DALLAS: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String txStateTax = "STATE TEXAS: Sales and Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult cityTax = new AribaTaxResult(100.00, 2.00, txCityTax, 1, "2.0%", false, testCurrency);
		AribaTaxResult stateTax = new AribaTaxResult(100.00, 6.25, txStateTax, 1, "6.25%", false, testCurrency);

		List<AribaTaxResult> taxResults = new ArrayList<>();

		taxResults.add(cityTax);
		taxResults.add(stateTax);

		final AribaLineItem fork = AribaLineItem
			.builder()
			.shipFromAddress("TestAutomationS_USPA_KingOfPrussia")
			.plant_shipToAddress(AribaPlants.US_TX_DALLAS)
			.expectedTaxAmount(8.25)
			.build();

		fork.setTaxResults(taxResults);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier, singleLineItemRequisition, fork);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, fork);

		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-477
	 * And this one is for Washington
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void catalogItemTaxWATest( )
	{
		String waCountyTax = "COUNTY KING: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String waStateTax = "STATE WASHINGTON: Retail Sales and Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult countyTax = new AribaTaxResult(100.00, 3.60, waCountyTax, 1, "3.6%", false, testCurrency);
		AribaTaxResult stateTax = new AribaTaxResult(100.00, 6.50, waStateTax, 1, "6.5%", false, testCurrency);

		List<AribaTaxResult> taxResults = new ArrayList<>();

		taxResults.add(countyTax);
		taxResults.add(stateTax);

		final AribaLineItem fork = AribaLineItem
			.builder()
			.shipFromAddress("TestAutomationS_USPA_KingOfPrussia")
			.plant_shipToAddress(AribaPlants.US_WA_SAMMAMISH)
			.expectedTaxAmount(10.10)
			.build();

		fork.setTaxResults(taxResults);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier, singleLineItemRequisition, fork);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, fork);

		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-333
	 * this test will create a basic requisition with shipping
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void basicRequisitionAndInvoiceTest( )
	{
		String caCountyTax = "COUNTY LOS ANGELES: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE CALIFORNIA: Sales and Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult countyTax = new AribaTaxResult(200.00, 0.00, caCountyTax, 1, "0.0%", false, testCurrency);
		AribaTaxResult stateTax = new AribaTaxResult(200.00, 0.00, caStateTax, 1, "0.0%", false, testCurrency);

		List<AribaTaxResult> taxResults = new ArrayList<>();

		taxResults.add(countyTax);
		taxResults.add(stateTax);

		final AribaLineItem animal = AribaLineItem
			.builder()
			.productName(chicken)
			.quantity("2")
			.shipFromAddress("TestAutomation_Montana")
			.plant_shipToAddress(AribaPlants.US_CA_LOS_ANGELES)
			.billToAddress("New York")
			.expectedTaxAmount(0.00)
			.build();

		animal.setTaxResults(taxResults);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier, singleLineItemRequisition, animal);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, animal);

		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-402
	 * create basic requisition single line item, tax exempt.
	 * Ship to Louisiana
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void basicRequisitionTaxExemptProductTest( )
	{
		String laParishTax = "PARISH ORLEANS: Local Sales and Use Tax Combined - Invoice Text: SALES";
		String laStateTax = "STATE LOUISIANA: Sales and Use Tax - Invoice Text: SALES";

		AribaTaxResult parishTax = new AribaTaxResult(100.00, 0.00, laParishTax, 1, "0.0%", false, testCurrency);
		AribaTaxResult stateTax = new AribaTaxResult(100.00, 0.00, laStateTax, 1, "0.0%", false, testCurrency);

		List<AribaTaxResult> taxResults = new ArrayList<>();

		taxResults.add(parishTax);
		taxResults.add(stateTax);

		final AribaLineItem service = AribaLineItem
			.builder()
			.productName(waterService)
			.shipFromAddress("TestAutomation_Montana")
			.plant_shipToAddress(AribaPlants.US_LA_NEW_ORLEANS)
			.billToAddress("New York")
			.expectedTaxAmount(0.00)
			.build();

		service.setTaxResults(taxResults);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier, singleLineItemRequisition, service);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, service);
		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-317
	 * Create a Purchase order with invoice and validate the xml, change the lines  and validate xml
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void createPurchaseOrderAndChangeThePOTest( )
	{
		String laParishTax = "PARISH ORLEANS: Local Sales and Use Tax Combined - Invoice Text: SALES";
		String laStateTax = "STATE LOUISIANA: Sales and Use Tax - Invoice Text: SALES";

		AribaTaxResult parishTax = new AribaTaxResult(100.00, 0.00, laParishTax, 1, "0.0%", false, testCurrency);
		AribaTaxResult stateTax = new AribaTaxResult(100.00, 0.00, laStateTax, 1, "0.0%", false, testCurrency);

		List<AribaTaxResult> taxResults = new ArrayList<>();

		taxResults.add(parishTax);
		taxResults.add(stateTax);

		final AribaLineItem service = AribaLineItem
			.builder()
			.productName(waterService)
			.shipFromAddress("TestAutomation_Montana")
			.plant_shipToAddress(AribaPlants.US_LA_NEW_ORLEANS)
			.billToAddress("New York")
			.expectedTaxAmount(0.00)
			.build();

		service.setTaxResults(taxResults);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier, singleLineItemRequisition, service);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, service);
		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-318
	 * Create a Purchase order with invoice and validate the xml, change the lines  and validate xml
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void returnPurchaseOrderChangeOrderPartialOrderAndQuantityReturnedTest( )
	{
		String laParishTax = "PARISH ORLEANS: Local Sales and Use Tax Combined - Invoice Text: SALES";
		String laStateTax = "STATE LOUISIANA: Sales and Use Tax - Invoice Text: SALES";

		AribaTaxResult parishTax = new AribaTaxResult(100.00, 0.00, laParishTax, 1, "0.0%", false, testCurrency);
		AribaTaxResult stateTax = new AribaTaxResult(100.00, 0.00, laStateTax, 1, "0.0%", false, testCurrency);

		List<AribaTaxResult> taxResults = new ArrayList<>();

		taxResults.add(parishTax);
		taxResults.add(stateTax);

		final AribaLineItem service = AribaLineItem
			.builder()
			.productName(waterService)
			.shipFromAddress("TestAutomation_Montana")
			.plant_shipToAddress(AribaPlants.US_LA_NEW_ORLEANS)
			.billToAddress("New York")
			.expectedTaxAmount(0.00)
			.build();

		service.setTaxResults(taxResults);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier, singleLineItemRequisition, service);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, service);
		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-464
	 * this is asserting for taxes that the buyer will pay the vendor,
	 * all consumer use taxes are not shown here
	 * Same basic idea - submit a requisition and verify we get taxes back
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void checkInvoiceCitationTextPresentTest( )
	{
		String correctName = "COUNTRY FRANCE: VAT - Invoice Text: IntraEU - subject to reverse charge";
		String franceTax = "COUNTRY FRANCE: VAT - Invoice Text: IntraEU - subject to reverse charge";

		AribaTaxResult stateTax = new AribaTaxResult(100.00, 8.00, franceTax, 1, "8.0%", false, testCurrency);
		final AribaLineItem fork = AribaLineItem
				.builder()
				.shipFromAddress("TestAutomationS_FranceMarseille")
				.plant_shipToAddress(AribaPlants.EU_DE_BERLIN)
				.expectedTaxAmount(8.00)
				.taxResult(stateTax)
				.shippingCost("10")
				.build();
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
				ariba_twoX_Supplier, singleLineItemRequisition, fork);

		checkoutPage.clickTaxesButton();
		String name = checkoutPage.getNameFieldString();

		assertEquals(name,correctName);
	}

	/**
	 * CARIBA-
	 * this is asserting for taxes that the buyer will pay the vendor,
	 * then some of the line item details are updated and verified that taxes are getting updated
	 * then submit a requisition
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void catalogItemUpdateTaxALToNYTest( )
	{
		String alCountyTax = "COUNTY JEFFERSON: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String alStateTax = "STATE NEW YORK: Sales and Compensating Use Tax Combined - Invoice Text: SELLER_USE";

		AribaTaxResult countyTax = new AribaTaxResult(100.00, 6.00, alCountyTax, 1, "6.0%", false, testCurrency);
		AribaTaxResult stateTax = new AribaTaxResult(100.00, 4.00, alStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> taxResults = new ArrayList<>();

		taxResults.add(countyTax);
		taxResults.add(stateTax);

		final AribaLineItem fork = AribaLineItem
				.builder()
				.shipFromAddress("TestAutomationS_USPA_KingOfPrussia")
				.plant_shipToAddress(AribaPlants.US_AL_BIRMINGHAM)
				.expectedTaxAmount(10.00)
				.shippingCost("10")
				.build();

		fork.setTaxResults(taxResults);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
				ariba_twoX_Supplier, singleLineItemRequisition, fork);

		String updatedNYCityTax = "CITY NEW YORK: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String updatedNYStateTax = "STATE NEW YORK: Sales and Compensating Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult updatedCityTax = new AribaTaxResult(1000.00, 48.75, updatedNYCityTax, 1, "4.88%", false, testCurrency);
		AribaTaxResult updatedStateTax = new AribaTaxResult(1000.00, 40.00, updatedNYStateTax, 1, "4.0%", false, testCurrency);

		List<AribaTaxResult> updatedTaxResults = new ArrayList<>();

		updatedTaxResults.add(updatedCityTax);
		updatedTaxResults.add(updatedStateTax);

		final AribaLineItem updatedFork = AribaLineItem
				.builder()
				.shipFromAddress("TestAutomationS_USPA_Pitt")
				.plant_shipToAddress(AribaPlants.US_NY_NEW_YORK)
				.expectedTaxAmount(88.75)
				.shippingCost("10")
				.updatedPrice("1000")
				.build();

		updatedFork.setTaxResults(updatedTaxResults);

		AribaPortalRequisitionCheckoutPage updatedTaxCheckoutPage = editCatalogLineItemsRequisition(checkoutPage,
				ariba_twoX_Supplier, singleLineItemRequisition, updatedFork);

		validateAllRequisitionTaxes(updatedTaxCheckoutPage, defaultTaxCode, updatedFork);

		AribaPortalCatalogHomePage catalogPage = updatedTaxCheckoutPage.toolbar.submitRequisition();
		catalogPage
				.getSubmittedNotification()
				.openRequisition();
	}
}