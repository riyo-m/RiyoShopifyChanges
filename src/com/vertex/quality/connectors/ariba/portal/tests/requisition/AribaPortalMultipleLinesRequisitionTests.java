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

/**
 * represents all the requisition test cases with multiple line items
 *
 * @author osabha
 */

public class AribaPortalMultipleLinesRequisitionTests extends AribaTwoXPortalBaseTest

{
	/**
	 * CARIBA-265
	 * creates a basic requisition multiple line items and multiple destinations
	 */

	@Test(groups = { "ariba_ui","ariba_smoke", "ariba_regression"})
	public void basicRequisitionMultipleLinesDifferentLocationsTest( )
	{
		String caCountyTax = "COUNTY LOS ANGELES: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax = "STATE CALIFORNIA: Sales and Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult countyTax1 = new AribaTaxResult(51.00, 0.00, caCountyTax, 1, "0.0%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(51.00, 0.00, caStateTax, 1, "0.0%", false, testCurrency);

		List<AribaTaxResult> taxResults = new ArrayList<>();

		taxResults.add(countyTax1);
		taxResults.add(stateTax1);

		String paStateTax = "STATE PENNSYLVANIA: Sales and Use Tax - Invoice Text: SELLER_USE";
		AribaTaxResult stateTax2 = new AribaTaxResult(1000000.00, 60000.00, paStateTax, 1, "6.0%", false, testCurrency);

		AribaLineItem item1 = AribaLineItem
			.builder()
			.productName(duck)
			.quantity("2")
			.itemIndex(1)
			.expectedTaxAmount(0.00)
			.shipFromAddress("TestAutomation_Montana")
			.billToAddress("1010 - TX - Dallas")
			.plant_shipToAddress(AribaPlants.US_CA_LOS_ANGELES)
			.build();

		item1.setTaxResults(taxResults);

		AribaLineItem item2 = AribaLineItem
			.builder()
			.productName(elephant)
			.quantity("1")
			.itemIndex(2)
			.shipFromAddress("TestAutomation_Montana")
			.billToAddress("New York")
			.taxResult(stateTax2)
			.expectedTaxAmount(60000.00)
			.plant_shipToAddress(AribaPlants.US_PA_GETTYSBURG)
			.build();

		final String requisitionTitle = "Basic Requisition - multi line ship to different locations";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier,
			requisitionTitle, item1,
			item2);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, item1, item2);

		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-263
	 * create basic requisition multiple line items, one item taxed and another exempt
	 * Ship to California
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void CreateBasicRequisitionRegularAndExemptItemsCATest( )
	{
		String caCountyTax1 = "COUNTY LOS ANGELES: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax1 = "STATE CALIFORNIA: Sales and Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult countyTax1 = new AribaTaxResult(200.00, 0.00, caCountyTax1, 1, "0.0%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(200.00, 0.00, caStateTax1, 1, "0.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(countyTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = AribaLineItem
			.builder()
			.productName(chicken)
			.quantity("2")
			.itemIndex(1)
			.expectedTaxAmount(0.00)
			.shipFromAddress("TestAutomation_Montana")
			.billToAddress("New York")
			.plant_shipToAddress(AribaPlants.US_CA_LOS_ANGELES)
			.build();

		item1.setTaxResults(taxResults1);

		String caCountyTax2 = "COUNTY LOS ANGELES: Local Sales and Use Tax Combined - Invoice Text: SALES";
		String caStateTax2 = "STATE CALIFORNIA: Sales and Use Tax - Invoice Text: SALES";

		AribaTaxResult countyTax2 = new AribaTaxResult(100.00, 0.00, caCountyTax2, 1, "0.0%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(100.00, 0.00, caStateTax2, 1, "0.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(countyTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = AribaLineItem
			.builder()
			.productName(waterService)
			.quantity("1")
			.itemIndex(2)
			.shipFromAddress("TestAutomation_Montana")
			.billToAddress("New York")
			.expectedTaxAmount(0.00)
			.plant_shipToAddress(AribaPlants.US_CA_LOS_ANGELES)
			.build();

		item2.setTaxResults(taxResults2);

		final String requisitionTitle = "Basic Requisition - 1 line tax, 1 line exempt";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier,
			requisitionTitle, item1,
			item2);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, item1, item2);

		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-346
	 * create basic requisition multiple line items, one item taxed and another exempt
	 * Ship to Louisiana
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void CreateBasicRequisitionRegularAndExemptItemsLATest( )
	{
		String laParishTax1 = "PARISH ORLEANS: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String laStateTax1 = "STATE LOUISIANA: Sales and Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult item1ParishTax = new AribaTaxResult(200.00, 10.00, laParishTax1, 1, "5.0%", false, testCurrency);
		AribaTaxResult item1StateTax = new AribaTaxResult(200.00, 8.90, laStateTax1, 1, "4.45%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(item1ParishTax);
		taxResults1.add(item1StateTax);

		AribaLineItem item1 = AribaLineItem
			.builder()
			.productName(chicken)
			.quantity("2")
			.itemIndex(1)
			.expectedTaxAmount(18.90)
			.shipFromAddress("TestAutomation_Montana")
			.billToAddress("New York")
			.plant_shipToAddress(AribaPlants.US_LA_NEW_ORLEANS)
			.build();

		item1.setTaxResults(taxResults1);

		String laParishTax2 = "PARISH ORLEANS: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String laStateTax2 = "STATE LOUISIANA: Sales and Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult item2ParishTax = new AribaTaxResult(100.00, 0.00, laParishTax2, 1, "0.0%", false, testCurrency);
		AribaTaxResult item2StateTax = new AribaTaxResult(100.00, 0.00, laStateTax2, 1, "0.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults1.add(item2ParishTax);
		taxResults1.add(item2StateTax);

		AribaLineItem item2 = AribaLineItem
			.builder()
			.productName(waterService)
			.quantity("1")
			.itemIndex(2)
			.shipFromAddress("TestAutomation_Montana")
			.billToAddress("New York")
			.expectedTaxAmount(0.00)
			.plant_shipToAddress(AribaPlants.US_LA_NEW_ORLEANS)
			.build();

		item2.setTaxResults(taxResults2);

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		final String requisitionTitle = "Basic Requisition - 1 line tax, 1 line exempt";
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier,
			requisitionTitle, item1,
			item2);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, item1, item2);

		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}

	/**
	 * CARIBA-436
	 * creates a basic requisition multiple line items and same destination
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void basicRequisitionMultipleLinesSameLocationsTest( )
	{
		String caCountyTax1 = "COUNTY LOS ANGELES: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax1 = "STATE CALIFORNIA: Sales and Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult countyTax1 = new AribaTaxResult(51.00, 0.00, caCountyTax1, 1, "0.0%", false, testCurrency);
		AribaTaxResult stateTax1 = new AribaTaxResult(51.00, 0.00, caStateTax1, 1, "0.0%", false, testCurrency);

		List<AribaTaxResult> taxResults1 = new ArrayList<>();

		taxResults1.add(countyTax1);
		taxResults1.add(stateTax1);

		AribaLineItem item1 = AribaLineItem
			.builder()
			.productName(duck)
			.quantity("2")
			.itemIndex(1)
			.expectedTaxAmount(0.00)
			.shipFromAddress("TestAutomation_Montana")
			.billToAddress("1010 - TX - Dallas")
			.plant_shipToAddress(AribaPlants.US_CA_LOS_ANGELES)
			.build();

		item1.setTaxResults(taxResults1);

		String caCountyTax2 = "COUNTY LOS ANGELES: Local Sales and Use Tax Combined - Invoice Text: SELLER_USE";
		String caStateTax2 = "STATE CALIFORNIA: Sales and Use Tax - Invoice Text: SELLER_USE";

		AribaTaxResult countyTax2 = new AribaTaxResult(1000000.00, 0.00, caCountyTax2, 1, "0.0%", false, testCurrency);
		AribaTaxResult stateTax2 = new AribaTaxResult(1000000.00, 0.00, caStateTax2, 1, "0.0%", false, testCurrency);

		List<AribaTaxResult> taxResults2 = new ArrayList<>();

		taxResults2.add(countyTax2);
		taxResults2.add(stateTax2);

		AribaLineItem item2 = AribaLineItem
			.builder()
			.productName(elephant)
			.quantity("1")
			.itemIndex(2)
			.shipFromAddress("TestAutomation_Montana")
			.billToAddress("1010 - TX - Dallas")
			.expectedTaxAmount(0.00)
			.plant_shipToAddress(AribaPlants.US_CA_LOS_ANGELES)
			.build();

		item2.setTaxResults(taxResults2);

		final String requisitionTitle = "Basic Requisition - multi line ship to different locations";
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			ariba_twoX_Supplier,
			requisitionTitle, item1,
			item2);
		validateAllRequisitionTaxes(checkoutPage, defaultTaxCode, item1, item2);

		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();
		catalogPage
			.getSubmittedNotification()
			.openRequisition();
	}
}