package com.vertex.quality.connectors.netsuite.singlecompany.tests;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteHomepage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuitePrices;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.NetsuiteSCGeneralPreferencesPage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCSalesOrderPage;
import com.vertex.quality.connectors.netsuite.singlecompany.tests.base.NetsuiteBaseSCTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests Vertex Integration and other basic config in Netsuite Single Company
 *
 * @author hho, jyareeda
 */

@Test(groups = { "config" })
public class NetsuiteSCBasicConfigTests extends NetsuiteBaseSCTest
{
	/**
	 * Tests to ensure that tax calculation doesn't occur while Vertex Integration is off
	 * CNSL-327
	 */
	@Test(groups = { "netsuite_smoke" })
	protected void basicConfigWithoutVertexTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
		NetsuiteItem itemOne = NetsuiteItem
			.builder(NetsuiteItemName.CHLOROFORM_ITEM)
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

		NetsuiteHomepage homepage = signIntoHomepageAsSingleCompany();
		NetsuiteSCGeneralPreferencesPage generalPreferencesPage = homepage.navigationPane.navigateThrough(
			generalPreferencesMenu);
		generalPreferencesPage.openCustomPreferencesTab();
		generalPreferencesPage.unselectInstallFlag();
		generalPreferencesPage.enterTrustedId(trustedId);
		generalPreferencesPage.enterCompanyCode(companyCode);
		generalPreferencesPage.enterTaxServiceUrl(taxServiceURL);
		generalPreferencesPage.enterAddressServiceUrl(addressServiceURL);
		generalPreferencesPage.setDefaultTaxCode(defaultTaxCode);
		NetsuiteSetupManagerPage setupManagerPage = generalPreferencesPage.savePreferences();

		NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);

		setupBasicOrder(salesOrderPage, customer, itemOne);

		checkBasicOrderAmounts(salesOrderPage, expectedPricesBeforeSaving);

		NetsuiteSCSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

		checkBasicOrderAmounts(salesOrderPage, expectedPricesAfterSaving);
		checkOrderWithTax(salesOrderPage, expectedPricesAfterSaving, itemOne);

		NetsuiteSCSalesOrderPage editedSalesOrderPage = savedSalesOrderPage.editOrder();
		NetsuiteTransactionsPage transactionsPage = editedSalesOrderPage.deleteOrder();
		assertTrue(transactionsPage.isOrderDeleted());
	}

	/**
	 * Tests to ensure that tax calculation occurs while Vertex Integration is on
	 * CNSL-326
	 */
	@Test(groups = { "netsuite_smoke" })
	protected void basicConfigWithVertexTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
		NetsuiteItem itemOne = NetsuiteItem
			.builder(NetsuiteItemName.CHLOROFORM_ITEM)
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

		NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);

		setupBasicOrder(salesOrderPage, customer, itemOne);

		checkBasicOrderAmounts(salesOrderPage, expectedPricesBeforeSaving);
		checkOrderWithTax(salesOrderPage, expectedPricesBeforeSaving, itemOne);

		NetsuiteSCSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

		checkBasicOrderAmounts(salesOrderPage, expectedPricesAfterSaving);
		checkOrderWithTax(salesOrderPage, expectedPricesAfterSaving, itemOne);

		NetsuiteSCSalesOrderPage editedSalesOrderPage = savedSalesOrderPage.editOrder();
		NetsuiteTransactionsPage transactionsPage = editedSalesOrderPage.deleteOrder();
		assertTrue(transactionsPage.isOrderDeleted());
	}
}
