package com.vertex.quality.connectors.netsuite.oneworld.tests;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteHomepage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuitePrices;
import com.vertex.quality.connectors.netsuite.oneworld.pages.NetsuiteOWGeneralPreferencesPage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWSalesOrderPage;
import com.vertex.quality.connectors.netsuite.oneworld.tests.base.NetsuiteBaseOWTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests Vertex Integration and other basic config in Netsuite One World
 *
 * @author hho
 */
@SuppressWarnings("Duplicates")
public class NetsuiteOWBasicConfigTests extends NetsuiteBaseOWTest
{
	/**
	 * Tests to ensure that tax calculation doesn't occur while Vertex Integration is off
	 * CNSL-225
	 */
	@Test(groups = { "netsuite_ow_smoke" })
	protected void basicConfigWithoutVertexTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
		NetsuiteItem itemOne = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.quantity("1")
			.amount("100.00")
			.build();

		NetsuitePrices expectedPricesBeforeSaving = NetsuitePrices
			.builder()
			.itemTaxRate("0.0%")
			.itemTaxCode(defaultTaxCode)
			.subtotal("100.00")
			.taxAmount("0.00")
			.total("100.00")
			.build();
		NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
			.builder()
			.itemTaxRate("0.0%")
			.itemTaxCode(defaultTaxCode)
			.subtotal("100.00")
			.taxAmount("0.00")
			.total("100.00")
			.build();

		NetsuiteNavigationMenus generalPreferencesMenu = getGeneralPreferencesMenu();
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteHomepage homepage = signintoHomepageAsOneWorld();

		NetsuiteOWGeneralPreferencesPage generalPreferencesPage = homepage.navigationPane.navigateThrough(
			generalPreferencesMenu);
		generalPreferencesPage.openCustomPreferencesTab();
		generalPreferencesPage.unselectInstallFlag();
		generalPreferencesPage.unselectOneWorldFlag();
		generalPreferencesPage.unselectCanadianLicensing();
		generalPreferencesPage.enterTrustedId(trustedId);
		generalPreferencesPage.enterCompanyCode(companyCode);
		generalPreferencesPage.enterTaxServiceUrl(taxServiceURL);
		generalPreferencesPage.enterAddressServiceUrl(addressServiceURL);
		generalPreferencesPage.setDefaultTaxCode(defaultTaxCode);
		generalPreferencesPage.setDefaultNontaxableTaxCode(" ");
		NetsuiteSetupManagerPage setupManagerPage = generalPreferencesPage.savePreferences();

		NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		setupBasicOrder(salesOrderPage, customer, itemOne);

		checkBasicOrderAmounts(salesOrderPage, expectedPricesBeforeSaving);
		checkOrderWithTax(salesOrderPage, expectedPricesBeforeSaving, itemOne);

		NetsuiteOWSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

		checkBasicOrderAmounts(savedSalesOrderPage, expectedPricesAfterSaving);
		checkOrderWithTax(savedSalesOrderPage, expectedPricesAfterSaving, itemOne);

		NetsuiteOWSalesOrderPage editedSalesOrderPage = savedSalesOrderPage.editOrder();
		NetsuiteTransactionsPage transactionsPage = editedSalesOrderPage.deleteOrder();
		assertTrue(transactionsPage.isOrderDeleted());
	}

	/**
	 * Tests to ensure that tax calculation occurs while Vertex Integration is on
	 * CNSL-226
	 */
	@Test(groups = { "netsuite_ow_smoke" })
	protected void basicConfigWithVertexTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
		NetsuiteItem itemOne = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.quantity("1")
			.amount("100.00")
			.build();

		NetsuitePrices expectedPricesBeforeSaving = NetsuitePrices
			.builder()
			.itemTaxRate("0.0%")
			.itemTaxCode(defaultTaxCode)
			.subtotal("100.00")
			.taxAmount("0.00")
			.total("100.00")
			.build();
		NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
			.builder()
			.itemTaxRate("8.25%")
			.itemTaxCode(defaultTaxCode)
			.subtotal("100.00")
			.taxAmount("8.25")
			.total("108.25")
			.build();

		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();

		NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		setupBasicOrder(salesOrderPage, customer, itemOne);

		checkBasicOrderAmounts(salesOrderPage, expectedPricesBeforeSaving);
		checkOrderWithTax(salesOrderPage, expectedPricesBeforeSaving, itemOne);

		NetsuiteOWSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

		checkBasicOrderAmounts(savedSalesOrderPage, expectedPricesAfterSaving);
		checkOrderWithTax(savedSalesOrderPage, expectedPricesAfterSaving, itemOne);

		NetsuiteOWSalesOrderPage editedSalesOrderPage = salesOrderPage.editOrder();
		NetsuiteTransactionsPage transactionsPage = editedSalesOrderPage.deleteOrder();
		assertTrue(transactionsPage.isOrderDeleted());
	}
}
