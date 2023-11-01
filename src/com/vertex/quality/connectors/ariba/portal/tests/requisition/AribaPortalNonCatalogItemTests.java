package com.vertex.quality.connectors.ariba.portal.tests.requisition;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.portal.pages.catalog.AribaPortalCatalogHomePage;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import com.vertex.quality.connectors.ariba.portal.pages.requisition.AribaPortalRequisitionCheckoutPage;
import com.vertex.quality.connectors.ariba.portal.pages.requisition.AribaPortalRequisitionLineItemDetailsPage;
import com.vertex.quality.connectors.ariba.portal.pages.requisition.AribaPortalRequisitionNonCatalogItemCreationPage;
import com.vertex.quality.connectors.ariba.portal.pages.requisition.AribaPortalRequisitionNonCatalogItemCreationPage.NonCatalogTextField;
import com.vertex.quality.connectors.ariba.portal.tests.base.AribaTwoXPortalBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Test cases for submitting requisitions with non-catalog items
 *
 * @author ssalisbury osabha
 * @author dgorecki
 */

public class AribaPortalNonCatalogItemTests extends AribaTwoXPortalBaseTest
{
	/**
	 * TODO JIRA lacks a story for the corresponding feature
	 *
	 * Test to ensure a taxes can be properly calculated on a requisition request
	 * containing a non-catalog item
	 * fixme Note - this test is running way too slow right now
	 *
	 * @author ssalisbury osabha
	 * @author dgorecki
	 */

	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void nonCatalogItemRequisitionTest( )
	{
		// test data
		final String newProductCommodityCode = "Filter papers";
		final String newProductPrice = "1000.00";
		final String requisitionName = "VertexAutomatedTest - Non-Catalog Item";
		final String newProductMaterialGroup = "00305 (Components)";
		final double expectedTaxes = 60.00;

		// sign in and start requisition
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalCatalogHomePage catalogHomePage1 = loggedInDashboardPage.openCatalog();
		AribaPortalCatalogHomePage catalogHomePage2 = catalogHomePage1.startNewRequisition();

		// add a non-catalog item
		AribaPortalRequisitionNonCatalogItemCreationPage nonCatalogItemCreationPage
			= catalogHomePage2.addNonCatalogItem();
		nonCatalogItemCreationPage.writeTextField(nonCatalogItemSupplier, NonCatalogTextField.VENDOR);

		nonCatalogItemCreationPage.writeTextField(newProductCommodityCode, NonCatalogTextField.COMMODITY_CODE);
		//nonCatalogItemCreationPage.writeTextField(newProductMaterialGroup, NonCatalogTextField.MATERIAL_GROUP);
		nonCatalogItemCreationPage.writeTextField(newProductPrice, NonCatalogTextField.PRICE);
		AribaPortalCatalogHomePage catalogHomePage3 = nonCatalogItemCreationPage.clickAddToCart();

		// checkout and set requisition title
		AribaPortalRequisitionCheckoutPage checkoutPage = catalogHomePage3.topMenuBar.proceedToCheckout();

		checkoutPage.setRequisitionTitle(requisitionName);

		// update required fields on line item
		AribaPortalRequisitionLineItemDetailsPage detailsPage = checkoutPage.itemsTable.editLineItemDetails(1);
		//detailsPage.writeTextField(newProductMaterialGroup, NonCatalogTextField.MATERIAL_GROUP);
		checkoutPage = detailsPage.clickOkButton();

		// perform tax calculation
		checkoutPage.updateTaxes();

		// verify tax calculation
		String itemTaxResult = checkoutPage.itemsTable.getRequisitionLineItemTaxes(1);
		double itemTaxValue = extractCurrencyNum(itemTaxResult);

		// need to log before assert in case assert fails
		String taxMessage = String.format("Non Catalog item tax  = %s", itemTaxValue);
		VertexLogger.log(taxMessage, getClass());

		assertTrue(itemTaxValue == expectedTaxes);

		// submit requisition
		checkoutPage.toolbar.submitRequisition();

		// TODO add step to open requisition and again verify taxes (it makes another call when the submitted)
	}
}
