package com.vertex.quality.connectors.netsuite.oneworld.tests;

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
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWInvoicePage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWQuotePage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWSalesOrderPage;
import com.vertex.quality.connectors.netsuite.oneworld.tests.base.NetsuiteBaseOWTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Includes any tests related to testing One World's Vertex Integration on
 * any order
 *
 * @author hho, ravunuri
 */
@SuppressWarnings("Duplicates")
public class NetsuiteOWTransactionsTests extends NetsuiteBaseOWTest
{
	/**
	 * Checks for Vertex tax integration on sales order transactions.
	 * CNSL-641
	 */
	@Test(groups = { "netsuite_ow_smoke" })
	protected void validateSalesOrderTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
		NetsuiteItem itemOne = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.quantity("1")
			.amount("100.00")
			.build();

		NetsuiteAddress addressOne = NetsuiteAddress
			.builder("91608")
			.fullAddressLine1("100 Universal City Plz")
			.addressLine1("100 Universal City Plz")
			.city("Universal City")
			.state(State.CA)
			.country(Country.USA)
			.zip9("91608-1002")
			.build();

		String transactionNumber;
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
			.itemTaxRate("9.5%")
			.itemTaxCode(defaultTaxCode)
			.subtotal("100.00")
			.taxAmount("9.50")
			.total("109.50")
			.build();

		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
		NetsuiteNavigationMenus searchSalesOrderMenu = getTransactionSearchMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();

		NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		transactionNumber = salesOrderPage.getTransactionNumber();
		setupBasicOrder(salesOrderPage, customer, addressOne, itemOne);

		checkBasicOrderAmounts(salesOrderPage, expectedPricesBeforeSaving);
		checkOrderWithTax(salesOrderPage, expectedPricesBeforeSaving, itemOne);

		NetsuiteOWSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

		checkBasicOrderAmounts(savedSalesOrderPage, expectedPricesAfterSaving);
		checkOrderWithTax(savedSalesOrderPage, expectedPricesAfterSaving, itemOne);

		NetsuiteTransactionsSearchPage searchPage = savedSalesOrderPage.navigationPane.navigateThrough(
			searchSalesOrderMenu);
		searchPage.enterTransactionNumberAndId(transactionNumber);
		NetsuiteTransactionsSearchResultPage searchResultsPage = searchPage.submitSearch();
		NetsuiteOWSalesOrderPage editedSalesOrderPage = searchResultsPage.edit(transactionNumber);
		NetsuiteTransactionsPage transactionsPage = editedSalesOrderPage.deleteOrder();
		assertTrue(transactionsPage.isOrderDeleted());
	}

	/**
	 * Checks for Vertex tax integration on Quotes.
	 * CNSL-1343
	 */
	@Test(groups = { "netsuite_ow_smoke" })
	protected void validateQuoteTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
		NetsuiteItem itemOne = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
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
		NetsuitePrices expectedPricesBeforeSaving = NetsuitePrices
			.builder()
			.subtotal("100.00")
			.total("100.00")
			.build();
		NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
			.builder()
			.subtotal("100.00")
			.total("100.00")
			.build();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();

		NetsuiteNavigationMenus quoteMenu = getQuoteMenu();
		NetsuiteNavigationMenus searchQuoteMenu = getQuoteSearchMenu();

		NetsuiteOWQuotePage quotePage = setupManagerPage.navigationPane.navigateThrough(quoteMenu);
		transactionNumber = quotePage.getTransactionNumber();
		setupBasicOrder(quotePage, customer, addressOne, itemOne);

		checkBasicOrderAmounts(quotePage, expectedPricesBeforeSaving);

		NetsuiteOWQuotePage savedQuotePage = quotePage.saveOrder();

		checkBasicOrderAmounts(savedQuotePage, expectedPricesAfterSaving);

		NetsuiteTransactionsSearchPage searchPage = savedQuotePage.navigationPane.navigateThrough(searchQuoteMenu);
		searchPage.enterTransactionNumberAndId(transactionNumber);
		NetsuiteTransactionsSearchResultPage searchResultsPage = searchPage.submitSearch();
		NetsuiteOWQuotePage editedQuotePage = searchResultsPage.edit(transactionNumber);
		NetsuiteTransactionsPage transactionsPage = editedQuotePage.deleteOrder();
		assertTrue(transactionsPage.isOrderDeleted());
	}

	/**
	 * Checks for Vertex tax integration on cart editing
	 * CNSL-1344
	 */
	@Test(groups = { "netsuite_ow_smoke" })
	protected void editShoppingCartTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
		NetsuiteItem itemOne = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.quantity("1")
			.amount("100.00")
			.build();
		NetsuiteItem itemTwo = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.quantity("2")
			.amount("200.00")
			.build();

		NetsuiteAddress addressOne = NetsuiteAddress
			.builder("91608")
			.fullAddressLine1("100 Universal City Plz")
			.addressLine1("100 Universal City Plz")
			.city("Universal City")
			.state(State.CA)
			.country(Country.USA)
			.zip9("91608-1002")
			.build();

		String transactionNumber;

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
			.itemTaxRate("9.5%")
			.itemTaxCode(defaultTaxCode)
			.subtotal("100.00")
			.taxAmount("9.50")
			.total("109.50")
			.build();
		NetsuitePrices editedPrices = NetsuitePrices
			.builder()
			.itemTaxRate("9.5%")
			.itemTaxCode(defaultTaxCode)
			.subtotal("200.00")
			.taxAmount("19.00")
			.total("219.00")
			.build();

		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
		NetsuiteNavigationMenus searchSalesOrderMenu = getTransactionSearchMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();

		NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);

		transactionNumber = salesOrderPage.getTransactionNumber();
		setupBasicOrder(salesOrderPage, customer, addressOne, itemOne);

		checkBasicOrderAmounts(salesOrderPage, expectedPricesBeforeSaving);
		checkOrderWithTax(salesOrderPage, expectedPricesBeforeSaving, itemOne);

		NetsuiteOWSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

		checkBasicOrderAmounts(savedSalesOrderPage, expectedPricesAfterSaving);
		checkOrderWithTax(savedSalesOrderPage, expectedPricesAfterSaving, itemOne);

		NetsuiteOWSalesOrderPage editedSalesOrderPage = savedSalesOrderPage.editOrder();
		editedSalesOrderPage.editItem(itemOne, itemTwo);
		savedSalesOrderPage = editedSalesOrderPage.saveOrder();

		checkBasicOrderAmounts(editedSalesOrderPage, editedPrices);
		checkOrderWithTax(editedSalesOrderPage, editedPrices, itemOne);

		NetsuiteTransactionsSearchPage searchPage = savedSalesOrderPage.navigationPane.navigateThrough(
			searchSalesOrderMenu);
		searchPage.enterTransactionNumberAndId(transactionNumber);
		NetsuiteTransactionsSearchResultPage searchResultsPage = searchPage.submitSearch();
		editedSalesOrderPage = searchResultsPage.edit(transactionNumber);
		NetsuiteTransactionsPage transactionsPage = editedSalesOrderPage.deleteOrder();
		assertTrue(transactionsPage.isOrderDeleted());
	}

	/**
	 * Checks that the batch process ignores documents with a 299 Error code
	 * when processing invoices
	 * CNSL-1331
	 */
	@Test(groups = { "netsuite_ow_regression" })
	protected void ignore299ErrorCodesTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
		// This item WILL be get processed by the Batch Script
		NetsuiteItem processedItem = NetsuiteItem
				.builder(NetsuiteItemName.ACC00002_ITEM)
				.quantity("1")
				.amount("100.00")
				.location("01: San Francisco")
				.build();

		// This Item will NOT get processed by the Batch Script
		NetsuiteItem nonProcessedItem = NetsuiteItem
				.builder(NetsuiteItemName.ACC00002_ITEM)
				.quantity("1")
				.amount("100.00")
				.location("01: San Francisco")
				.build();

		NetsuiteAddress addressOne = NetsuiteAddress
				.builder("91608")
				.fullAddressLine1("100 Universal City Plaza")
				.addressLine1("100 Universal City Plz")
				.city("01: San Francisco")
				.state(State.CA)
				.country(Country.USA)
				.zip9("91608-1002")
				.build();

		NetsuiteNavigationMenus InvoiceMenu = getCreateInvoiceMenu();
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteOWInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(InvoiceMenu);

		setupBasicInvoice(invoicePage, customer, addressOne, processedItem);
		invoicePage.selectTaxCode(processedItem, "-Not Taxable-");
		NetsuiteOWInvoicePage savedProcessedInvoicePage = invoicePage.saveOrder();

		String code = invoicePage.getVertexTaxErrorCode();

		savedProcessedInvoicePage =savedProcessedInvoicePage.editOrder();
		NetsuiteTransactionsPage transactionsPage = savedProcessedInvoicePage.deleteOrder();
		assertTrue(transactionsPage.isOrderDeleted());

	}
}
