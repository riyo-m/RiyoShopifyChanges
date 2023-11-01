package com.vertex.quality.connectors.netsuite.singlecompany.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsSearchPage;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsSearchResultPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuitePrices;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCQuotePage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCSalesOrderPage;
import com.vertex.quality.connectors.netsuite.singlecompany.tests.base.NetsuiteBaseSCTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Includes any tests related to testing Single Company's Vertex Integration on
 * any order
 *
 * @author hho
 */
@SuppressWarnings("Duplicates")
public class NetsuiteSCTransactionsTests extends NetsuiteBaseSCTest
{
	/**
	 * Checks for Vertex tax integration on sales order transactions.
	 * CNSL-645
	 */
	@Test(groups = { "netsuite_smoke" })
	protected void validateSalesOrderTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
		NetsuiteItem itemOne = NetsuiteItem
			.builder(NetsuiteItemName.CHLOROFORM_ITEM)
			.quantity("1")
			.amount("100.00")
			.build();

		NetsuiteAddress addressOne = NetsuiteAddress
			.builder("91608")
			.fullAddressLine1("100 Universal City Plaza")
			.addressLine1("100 Universal City Plaza")
			.city("Universal City")
			.state(State.CA)
			.country(Country.USA)
			.zip9("91608-1002")
			.build();

		String transactionNumber;
		NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
			.builder()
			.itemTaxRate("9.5%")
			.itemTaxCode(defaultTaxCode)
			.subtotal("100.00")
			.taxAmount("9.50")
			.total("109.50")
			.build();

		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
		NetsuiteNavigationMenus searchSalesOrderMenu = getTransactionSearchMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();

		NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);

		transactionNumber = salesOrderPage.getTransactionNumber();
		setupBasicOrder(salesOrderPage, customer, addressOne, itemOne);

		NetsuiteSCSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

		checkBasicOrderAmounts(savedSalesOrderPage, expectedPricesAfterSaving);
		checkOrderWithTax(savedSalesOrderPage, expectedPricesAfterSaving, itemOne);

		NetsuiteTransactionsSearchPage searchPage = savedSalesOrderPage.navigationPane.navigateThrough(
			searchSalesOrderMenu);
		searchPage.enterTransactionNumberAndId(transactionNumber);
		NetsuiteTransactionsSearchResultPage searchResultsPage = searchPage.submitSearch();
		NetsuiteSCSalesOrderPage editedSalesOrderPage = searchResultsPage.edit(transactionNumber);
		NetsuiteTransactionsPage transactionsPage = editedSalesOrderPage.deleteOrder();
		assertTrue(transactionsPage.isOrderDeleted());
	}

	/**
	 * Checks for Vertex tax integration on Quotes.
	 * CNSL-643
	 */
	@Test(groups = { "netsuite_smoke" })
	protected void validateQuoteTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
		NetsuiteItem itemOne = NetsuiteItem
			.builder(NetsuiteItemName.CHLOROFORM_ITEM)
			.quantity("1")
			.amount("100.00")
			.build();

		NetsuiteAddress addressOne = NetsuiteAddress
			.builder("19312")
			.addressLine1("1041 Old Cassatt Rd")
			.city("Berwyn")
			.state(State.PA)
			.country(Country.USA)
			.zip9("19312-1152")
			.build();

		String transactionNumber;
		NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
			.builder()
			.itemTaxRate("6.0%")
			.itemTaxCode(defaultTaxCode)
			.subtotal("100.00")
			.taxAmount("6.00")
			.total("106.00")
			.build();

		NetsuiteNavigationMenus quoteMenu = getQuoteMenu();
		NetsuiteNavigationMenus searchQuoteMenu = getTransactionSearchMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();

		NetsuiteSCQuotePage quotePage = setupManagerPage.navigationPane.navigateThrough(quoteMenu);

		transactionNumber = quotePage.getTransactionNumber();
		setupBasicOrder(quotePage, customer, addressOne, itemOne);

		NetsuiteSCQuotePage savedQuotePage = quotePage.saveOrder();

		checkBasicOrderAmounts(savedQuotePage, expectedPricesAfterSaving);
		checkOrderWithTax(savedQuotePage, expectedPricesAfterSaving, itemOne);

		savedQuotePage.editOrder();
		savedQuotePage.deleteOrder();
	}

	/**
	 * Checks for Vertex tax integration on cart editing
	 * CNSL-644
	 */
	@Test(groups = { "netsuite_smoke" })
	protected void editShoppingCartTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
		NetsuiteItem itemOne = NetsuiteItem
			.builder(NetsuiteItemName.CHLOROFORM_ITEM)
			.quantity("1")
			.amount("100.00")
			.build();
		NetsuiteItem itemTwo = NetsuiteItem
			.builder(NetsuiteItemName.CHLOROFORM_ITEM)
			.quantity("2")
			.amount("200.00")
			.build();

		NetsuiteAddress addressOne = NetsuiteAddress
			.builder("91608")
			.fullAddressLine1("100 Universal City Plaza")
			.addressLine1("100 Universal City Plaza")
			.city("Universal City")
			.state(State.CA)
			.country(Country.USA)
			.zip9("91608-1002")
			.build();

		String transactionNumber;
		NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
			.builder()
			.itemTaxRate("9.5%")
			.itemTaxCode(defaultTaxCode)
			.subtotal("100.00")
			.taxAmount("9.50")
			.total("109.50")
			.build();
		NetsuitePrices editedCartPrice = NetsuitePrices
			.builder()
			.itemTaxRate("9.5%")
			.itemTaxCode(defaultTaxCode)
			.subtotal("200.00")
			.taxAmount("19.00")
			.total("219.00")
			.build();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();

		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
		NetsuiteNavigationMenus searchSalesOrderMenu = getTransactionSearchMenu();

		NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);

		transactionNumber = salesOrderPage.getTransactionNumber();
		setupBasicOrder(salesOrderPage, customer, addressOne, itemOne);

		NetsuiteSCSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

		checkBasicOrderAmounts(savedSalesOrderPage, expectedPricesAfterSaving);
		checkOrderWithTax(savedSalesOrderPage, expectedPricesAfterSaving, itemOne);

		NetsuiteSCSalesOrderPage editedSalesOrderPage = savedSalesOrderPage.editOrder();
		editedSalesOrderPage.editItem(itemOne, itemTwo);
		savedSalesOrderPage = editedSalesOrderPage.saveOrder();

		checkBasicOrderAmounts(savedSalesOrderPage, editedCartPrice);
		checkOrderWithTax(savedSalesOrderPage, editedCartPrice, itemTwo);

		NetsuiteTransactionsSearchPage searchPage = savedSalesOrderPage.navigationPane.navigateThrough(
			searchSalesOrderMenu);
		searchPage.enterTransactionNumberAndId(transactionNumber);
		NetsuiteTransactionsSearchResultPage searchResultsPage = searchPage.submitSearch();
		editedSalesOrderPage = searchResultsPage.edit(transactionNumber);
		NetsuiteTransactionsPage transactionsPage = editedSalesOrderPage.deleteOrder();
		assertTrue(transactionsPage.isOrderDeleted());
	}
}
