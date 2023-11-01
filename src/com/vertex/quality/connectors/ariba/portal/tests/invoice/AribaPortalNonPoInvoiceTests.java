package com.vertex.quality.connectors.ariba.portal.tests.invoice;

import com.vertex.quality.connectors.ariba.connector.tests.base.AribaUtilities;
import com.vertex.quality.connectors.ariba.portal.enums.AribaNonPoInvoiceTextFields;
import com.vertex.quality.connectors.ariba.portal.enums.AribaPlants;
import com.vertex.quality.connectors.ariba.portal.pages.catalog.AribaPortalCatalogProductsListPage;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import com.vertex.quality.connectors.ariba.portal.pages.invoice.AribaPortalInvoiceAddItemPage;
import com.vertex.quality.connectors.ariba.portal.pages.invoice.AribaPortalInvoiceCreationPage;
import com.vertex.quality.connectors.ariba.portal.pojos.AribaLineItem;
import com.vertex.quality.connectors.ariba.portal.tests.base.AribaTwoXPortalBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * contains all the non po invoice tests
 *
 * @author osabha
 */
public class AribaPortalNonPoInvoiceTests extends AribaTwoXPortalBaseTest {
	/**
	 * Invoicing test for catalog item
	 * CARIBA-331
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression"})
	public void createInvoiceForCatalogItemTest() throws InterruptedException {
		AribaUtilities utils = new AribaUtilities(driver);
		String expectedInvoiceTotal = "$109.50 USD";
		String expectedTaxAmount = "$9.50 USD";
		final String supplierName = "Automation New Ariba Test Supplier";
		final String invoiceName = "Invoice Only - NonPo" + utils.generateInvoiceId();
		final String invoiceType = "Non-PO";
		AribaLineItem chickenItem = AribaLineItem
				.builder()
				.itemIndex(1)
				.shipFromAddress("TestAutomationS_USPA_KingofPrussia")
				.deliverToPerson("Someone Important")
				.plant_shipToAddress(AribaPlants.US_CA_LOS_ANGELES)
				.quantity("1")
				.productName(chicken)
				.build();
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalInvoiceCreationPage newInvoicePage = loggedInDashboardPage.startNewInvoice();
		newInvoicePage.selectInvoiceType(invoiceType);
		newInvoicePage.writeTextField(supplierName, AribaNonPoInvoiceTextFields.SUPPLIER);
		newInvoicePage.clickSupplierContactSelectButton();
		newInvoicePage.supplierContact.selectContactValue("TestAutomationS_USPA_KingofPrussia");
		newInvoicePage.writeTextField(invoiceName, AribaNonPoInvoiceTextFields.SUPPLIER_INVOICE);
		AribaPortalInvoiceAddItemPage addItemToInvoicePage = newInvoicePage.addCatalogItem();
		AribaPortalCatalogProductsListPage productsListPage = addItemToInvoicePage.topMenuBar.searchCatalog(
				chickenItem.productName);
		AribaPortalInvoiceCreationPage newInvoicePage1 = productsListPage.addItemToInvoice(ariba_twoX_Supplier,
				chickenItem.productName,
				chickenItem.quantity);
		newInvoicePage1.writeTextField(chickenItem.shipFromAddress, AribaNonPoInvoiceTextFields.SHIP_FROM);
		newInvoicePage1.writeTextField(chickenItem.plant_shipToAddress.plantDisplayName,
				AribaNonPoInvoiceTextFields.PLANT);
		newInvoicePage1.writeTextField(chickenItem.deliverToPerson, AribaNonPoInvoiceTextFields.DELIVER_TO);
		newInvoicePage1.selectItemByIndex(chickenItem.itemIndex);
		newInvoicePage1.addItemTaxesDetail();
		newInvoicePage1.addTaxDialog.enableLookUpByTaxType();
		newInvoicePage1.addTaxDialog.selectTaxType("SalesTax");
		newInvoicePage1.addTaxDialog.enterTaxAmount("$9.50");
		newInvoicePage1.addTaxDialog.clickAddButton();
		Assert.assertEquals(expectedTaxAmount, newInvoicePage1.getInvoiceTaxAmount());
		Assert.assertEquals(expectedInvoiceTotal, newInvoicePage1.getInvoiceTotal());
	}

	/**
	 * Invoicing test for non catalog item
	 * CARIBA-331
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression"})
	public void createInvoiceForNonCatalogItemTest() throws InterruptedException {
		AribaUtilities utils = new AribaUtilities(driver);
		String expectedInvoiceTotal = "$54.25 USD";
		String expectedTaxAmount = "$4.25 USD";
		final String supplierName = "Automation New Ariba Test Supplier";
		final String invoiceName = "Invoice Only - NonPo" + utils.generateInvoiceId();
		final String invoiceType = "Non-PO";

		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalInvoiceCreationPage newInvoicePage = loggedInDashboardPage.startNewInvoice();
		newInvoicePage.selectInvoiceType(invoiceType);
		newInvoicePage.writeTextField(supplierName, AribaNonPoInvoiceTextFields.SUPPLIER);
		newInvoicePage.clickSupplierContactSelectButton();
		newInvoicePage.supplierContact.selectContactValue("TestAutomationS_USPA_KingofPrussia");
		newInvoicePage.writeTextField(invoiceName, AribaNonPoInvoiceTextFields.SUPPLIER_INVOICE);

		newInvoicePage.addNonCatalogItem();
		newInvoicePage.selectOnlyOneItemByIndex(1);
		newInvoicePage.enterItemPrice(1, "$50.00");
		newInvoicePage.writeTextField("Apparel and Luggage", AribaNonPoInvoiceTextFields.COMMODITY_CODE);
		newInvoicePage.writeTextField("nice sweater", AribaNonPoInvoiceTextFields.FULL_DESCRIPTION);
		newInvoicePage.addItemTaxesDetail();
		newInvoicePage.addTaxDialog.enableLookUpByTaxType();
		newInvoicePage.addTaxDialog.selectTaxType("SalesTax");
		newInvoicePage.addTaxDialog.enterTaxAmount("$4.25");
		newInvoicePage.addTaxDialog.clickAddButton();
		Assert.assertEquals(expectedTaxAmount, newInvoicePage.getInvoiceTaxAmount());
		Assert.assertEquals(expectedInvoiceTotal, newInvoicePage.getInvoiceTotal());
	}
}