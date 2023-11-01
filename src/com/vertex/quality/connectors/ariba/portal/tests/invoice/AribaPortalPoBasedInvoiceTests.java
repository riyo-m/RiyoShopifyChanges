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
 * contains all the po based invoice tests
 *
 * @author osabha
 */
public class AribaPortalPoBasedInvoiceTests extends AribaTwoXPortalBaseTest
{
	/**
	 * CARIBA-332
	 * creates a PO and then creates an invoice for it
	 * then verifies tax calculation at both levels.
	 */
	@Test(groups = { "ariba_ui","ariba_regression"})
	public void justCreateAnInvoiceTest( ) throws InterruptedException {
		AribaUtilities utils = new AribaUtilities(driver);
		String expectedInvoiceTotal = "$109.50 USD";
		String expectedTaxAmount = "$9.50 USD";
		final String supplierName = "Automation New Ariba Test Supplier";
		final String invoiceName = "Invoice - Po based" + utils.generateInvoiceId();
		final String invoiceType = "PO-Based";
		AribaLineItem chickenItem = AribaLineItem
			.builder()
			.itemIndex(1)
			.shipFromAddress("USPA")
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
		newInvoicePage.selectFirstPO();

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
		newInvoicePage.addItemTaxesDetail();
		newInvoicePage.addTaxDialog.enableLookUpByTaxType();
		newInvoicePage.addTaxDialog.selectTaxType("SalesTax");
		newInvoicePage.addTaxDialog.enterTaxAmount("$9.50");
		newInvoicePage.addTaxDialog.clickAddButton();
		Assert.assertEquals(expectedTaxAmount, newInvoicePage.getInvoiceTaxAmount());
		Assert.assertEquals(expectedInvoiceTotal, newInvoicePage.getInvoiceTotal());
	}
}