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
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCSalesOrderPage;
import com.vertex.quality.connectors.netsuite.singlecompany.tests.base.NetsuiteBaseSCTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests discounts on orders
 *
 * @author hho
 */
@SuppressWarnings("Duplicates")
public class NetsuiteSCDiscountTests extends NetsuiteBaseSCTest
{
	/**
	 * Checks for Vertex tax integration on sales order transactions.
	 * CNSL-717
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
		String discountCode = "MM805";

		NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
			.builder()
			.itemTaxRate("9.5%")
			.itemTaxCode(defaultTaxCode)
			.subtotal("100.00")
			.taxAmount("9.50")
			.total("99.50")
			.build();

		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
		NetsuiteNavigationMenus searchSalesOrderMenu = getTransactionSearchMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();

		NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);

		transactionNumber = salesOrderPage.getTransactionNumber();
		setupBasicOrder(salesOrderPage, customer, addressOne, itemOne);
		salesOrderPage.selectPromotion(discountCode);

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
}
