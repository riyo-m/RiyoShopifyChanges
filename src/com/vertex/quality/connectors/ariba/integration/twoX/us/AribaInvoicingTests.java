package com.vertex.quality.connectors.ariba.integration.twoX.us;

import com.vertex.quality.connectors.ariba.common.utils.AribaAPITestUtilities;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaUtilities;
import com.vertex.quality.connectors.ariba.portal.enums.AribaNonPoInvoiceTextFields;
import com.vertex.quality.connectors.ariba.portal.enums.AribaPlants;
import com.vertex.quality.connectors.ariba.portal.enums.AribaPortalInvoiceTaxTypes;
import com.vertex.quality.connectors.ariba.portal.enums.AribaPortalManageListPage;
import com.vertex.quality.connectors.ariba.portal.pages.catalog.AribaPortalCatalogProductsListPage;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import com.vertex.quality.connectors.ariba.portal.pages.invoice.AribaPortalInvoiceAddItemPage;
import com.vertex.quality.connectors.ariba.portal.pages.invoice.AribaPortalInvoiceCreationPage;
import com.vertex.quality.connectors.ariba.portal.pages.invoice.AribaPortalPostInvoiceSubmissionPage;
import com.vertex.quality.connectors.ariba.portal.pages.procurement.AribaPortalBeforeReconciliationPage;
import com.vertex.quality.connectors.ariba.portal.pages.procurement.AribaPortalReconciliationPage;
import com.vertex.quality.connectors.ariba.portal.pages.procurement.AribaPortalToDoPage;
import com.vertex.quality.connectors.ariba.portal.pojos.AribaLineItem;
import com.vertex.quality.connectors.ariba.portal.tests.base.AribaTwoXPortalBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * contains test cases that createInvoice and reconcile it all from the buyer site
 *
 * @author osabha
 */
public class AribaInvoicingTests extends AribaTwoXPortalBaseTest
{
	/**
	 * CARIBA-325
	 * create invoice and reconcile through payment, attach OK to pay to payment.
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void lineGranularTaxAcceptedLaTest( ) throws InterruptedException {
		AribaUtilities utils = new AribaUtilities(driver);
		final String supplierName = "Automation New Ariba Test Supplier";
		final String invoiceName = "UnderTaxAccepted_LA" + utils.generateInvoiceId();
		final String lancasterShipFromAddress = "TestAutomationS_USPA";
		final String deliverToPerson = "Someone Important";
		final String invoiceType = "Non-PO";
		//******************Test Input Data*********************//
		final String chickenStateTax = "$4.00";
		final String chickenCountyTax = "$0.50";
		final String chickenCityTax = "$1.00";
		final String duckStateTax = "$2.00";
		final String duckCountyTax = "$0.10";
		final String duckCityTax = "$0.25";
		//*************************Test AssertionData*************//
		final String expectedInvoiceTaxAmount = "$4.70 USD";
		final String expectedInvoiceTotal = "$155.70 USD";

		AribaLineItem chickenItem = AribaLineItem
			.builder()
			.itemIndex(1)
			.shipFromAddress(lancasterShipFromAddress)
			.deliverToPerson(deliverToPerson)
			.plant_shipToAddress(AribaPlants.US_LA_NEW_ORLEANS)
			.quantity("1")
			.productName(chicken)
			.build();
		AribaLineItem duckItem = AribaLineItem
			.builder()
			.itemIndex(2)
			.shipFromAddress(lancasterShipFromAddress)
			.deliverToPerson(deliverToPerson)
			.plant_shipToAddress(AribaPlants.US_LA_NEW_ORLEANS)
			.quantity("2")
			.productName(duck)
			.build();
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalInvoiceCreationPage newInvoicePage = loggedInDashboardPage.startNewInvoice();
		newInvoicePage.selectInvoiceType(invoiceType);
		newInvoicePage.writeTextField(supplierName, AribaNonPoInvoiceTextFields.SUPPLIER);
		newInvoicePage.clickSupplierContactSelectButton();
		newInvoicePage.supplierContact.selectContactValue(lancasterShipFromAddress);
		newInvoicePage.writeTextField(invoiceName, AribaNonPoInvoiceTextFields.SUPPLIER_INVOICE);
		AribaPortalInvoiceAddItemPage addItemToInvoicePage = newInvoicePage.addCatalogItem();
		AribaPortalCatalogProductsListPage productsListPage = addItemToInvoicePage.topMenuBar.searchCatalog(
			chickenItem.productName);
		AribaPortalInvoiceCreationPage newInvoicePage1 = productsListPage.addItemToInvoice(ariba_twoX_Supplier,
			chickenItem.productName,
			chickenItem.quantity);
		AribaPortalInvoiceAddItemPage addItemToInvoicePage1 = newInvoicePage.addCatalogItem();
		AribaPortalCatalogProductsListPage productsListPage1 = addItemToInvoicePage1.topMenuBar.searchCatalog(
			duckItem.productName);
		AribaPortalInvoiceCreationPage newInvoicePage2 = productsListPage1.addItemToInvoice(ariba_twoX_Supplier,
			duckItem.productName,
			duckItem.quantity);

		newInvoicePage2.writeTextField(chickenItem.shipFromAddress, AribaNonPoInvoiceTextFields.SHIP_FROM);
		newInvoicePage2.writeTextField(chickenItem.plant_shipToAddress.plantDisplayName,
			AribaNonPoInvoiceTextFields.PLANT);
		newInvoicePage2.writeTextField(chickenItem.deliverToPerson, AribaNonPoInvoiceTextFields.DELIVER_TO);
		newInvoicePage2.selectOnlyOneItemByIndex(chickenItem.itemIndex);
		newInvoicePage2.addItemTaxesDetail();
		newInvoicePage2.addTaxDialog.addTaxesToItem(AribaPortalInvoiceTaxTypes.STATE_SALES_TAX.getType(),
			chickenStateTax);
		newInvoicePage2.addItemTaxesDetail();
		newInvoicePage2.addTaxDialog.addTaxesToItem(AribaPortalInvoiceTaxTypes.COUNTY_SALES_TAX.getType(),
			chickenCountyTax);
		newInvoicePage2.addItemTaxesDetail();
		newInvoicePage2.addTaxDialog.addTaxesToItem(AribaPortalInvoiceTaxTypes.CITY_SALES_TAX.getType(),
			chickenCityTax);
		newInvoicePage2.selectOnlyOneItemByIndex(duckItem.itemIndex);
		newInvoicePage2.addItemTaxesDetail();
		newInvoicePage2.addTaxDialog.addTaxesToItem(AribaPortalInvoiceTaxTypes.STATE_SALES_TAX.getType(), duckStateTax);
		newInvoicePage2.addItemTaxesDetail();
		newInvoicePage2.addTaxDialog.addTaxesToItem(AribaPortalInvoiceTaxTypes.COUNTY_SALES_TAX.getType(),
			duckCountyTax);
		newInvoicePage2.addItemTaxesDetail();
		newInvoicePage2.addTaxDialog.addTaxesToItem(AribaPortalInvoiceTaxTypes.CITY_SALES_TAX.getType(), duckCityTax);
		AribaPortalPostInvoiceSubmissionPage summaryPage = newInvoicePage2.clickSubmitButton();
		summaryPage.clickViewInvoiceStatus();
		summaryPage.clickInvoiceSummaryTab();
		Assert.assertEquals(expectedInvoiceTaxAmount, summaryPage.getInvoiceSummaryDetailsTax());
		Assert.assertEquals(expectedInvoiceTotal, summaryPage.getInvoiceSummaryDetailsTotal());
	}
}
