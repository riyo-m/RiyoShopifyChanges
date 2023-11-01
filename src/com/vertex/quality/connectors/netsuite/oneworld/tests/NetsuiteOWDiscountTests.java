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
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWSalesOrderPage;
import com.vertex.quality.connectors.netsuite.oneworld.tests.base.NetsuiteBaseOWTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests discounts on orders
 *
 * @author hho
 */
@SuppressWarnings("Duplicates")
public class NetsuiteOWDiscountTests extends NetsuiteBaseOWTest
{

	/**
	 * Validates
	 * 1) That tax is calculated using the GROSS price when Tax Assist Rule is applied
	 * 2) That tax is calculated using the DISCOUNTED price when the rule is disabled
	 * CNSL-1544
	 */
	@Test(groups = { "netsuite_ow_smoke" })
	protected void grossPriceTaxCalculationTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_AUTOMATION_CUSTOMER_1;
		NetsuiteItem grossPriceItem = NetsuiteItem
				.builder(NetsuiteItemName.DISCOUNT_TAX_ITEM)
				.quantity("1")
				.amount("100.00")
				.build();

		NetsuiteItem discountedPriceItem = NetsuiteItem
				.builder(NetsuiteItemName.GROSS_TAX_ITEM)
				.quantity("1")
				.amount("100.00")
				.build();

		String discountCode = NetsuiteItemName.STANDARD_25_FLAT_OFF.toString();

		NetsuiteAddress addressOne = NetsuiteAddress
				.builder("91608")
				.fullAddressLine1("100 Universal City Plz")
				.addressLine1("100 Universal City Plaza")
				.city("Universal City")
				.state(State.CA)
				.country(Country.USA)
				.zip9("91608-1002")
				.build();

		NetsuitePrices expectedPricesBeforeSaving = NetsuitePrices
				.builder()
				.itemTaxRate("0.0%")
				.itemTaxCode(defaultTaxCode)
				.subtotal("75.00")
				.taxAmount("0.00")
				.total("75.00")
				.build();
		NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
				.builder()
				.itemTaxRate("9.5067%")
				.itemTaxCode(defaultTaxCode)
				.subtotal("75.00")
				.taxAmount("7.13")
				.total("82.13")
				.build();

		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();

		//Part 1: Control item test
		NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		setupBasicOrder(salesOrderPage, customer, addressOne, discountedPriceItem);
		checkBasicOrderAmounts(salesOrderPage, expectedPricesBeforeSaving);

		//Should calculate normally
		NetsuiteOWSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();
		checkBasicOrderAmounts(savedSalesOrderPage, expectedPricesAfterSaving);
		checkOrderWithTax(savedSalesOrderPage, expectedPricesAfterSaving, discountedPriceItem);

		savedSalesOrderPage.editOrder();
		NetsuiteTransactionsPage transactionsPage = savedSalesOrderPage.deleteOrder();
		assertTrue(transactionsPage.isOrderDeleted());

	}


	/**
	 * Checks for Vertex tax integration on sales order transactions.
	 * CNSL-641
	 */
	@Test(groups = { "netsuite_ow_smoke" })
	protected void validateSalesOrderTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_AUTOMATION_CUSTOMER_1;
		NetsuiteItem itemOne = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.quantity("1")
			.amount("100.00")
			.build();

		NetsuiteAddress addressOne = NetsuiteAddress
			.builder("91608")
			.fullAddressLine1("100 Universal City Plz")
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

		NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);

		transactionNumber = salesOrderPage.getTransactionNumber();
		setupBasicOrder(salesOrderPage, customer, addressOne, itemOne);

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
}
