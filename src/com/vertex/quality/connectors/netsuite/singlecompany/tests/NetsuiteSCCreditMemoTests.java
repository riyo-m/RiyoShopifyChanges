package com.vertex.quality.connectors.netsuite.singlecompany.tests;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsSearchPage;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsSearchResultPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuitePrices;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCCreditMemoPage;
import com.vertex.quality.connectors.netsuite.singlecompany.tests.base.NetsuiteBaseSCTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests for credit memos
 *
 * @author hho
 */
@SuppressWarnings("Duplicates")
public class NetsuiteSCCreditMemoTests extends NetsuiteBaseSCTest
{
	/**
	 * Checks for Vertex tax integration on credit memos.
	 */
	@Ignore
	@Test(groups = { "netsuite_smoke" })
	protected void validateCreditMemoTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
		NetsuiteItem itemOne = NetsuiteItem
			.builder(NetsuiteItemName.DESIGNER_CHAIR_LEGS)
			.quantity("1")
			.amount("100.00")
			.build();

		String transactionNumber;
		String postingPeriod = "Dec 2018";
		String location = "Los Angeles";

		NetsuitePrices expectedPricesBeforeSaving = NetsuitePrices
			.builder()
			.itemTaxRate("0.1%")
			.itemTaxCode(defaultTaxCode)
			.subtotal("100.00")
			.taxAmount("0.10")
			.total("100.10")
			.build();
		NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
			.builder()
			.itemTaxRate("8.25%")
			.itemTaxCode(defaultTaxCode)
			.subtotal("100.00")
			.taxAmount("8.25")
			.total("108.25")
			.build();

		NetsuiteNavigationMenus creditMemoMenu = getCreditMemoMenu();
		NetsuiteNavigationMenus searchCreditMemoMenu = getTransactionSearchMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();

		NetsuiteSCCreditMemoPage creditMemoPage = setupManagerPage.navigationPane.navigateThrough(creditMemoMenu);

		transactionNumber = creditMemoPage.getTransactionNumber();
		setupBasicOrder(creditMemoPage, customer, itemOne);
		creditMemoPage.selectPostingPeriod(postingPeriod);
		creditMemoPage.selectLocation(location);

		checkBasicOrderAmounts(creditMemoPage, expectedPricesBeforeSaving);
		checkOrderWithTax(creditMemoPage, expectedPricesBeforeSaving, itemOne);

		NetsuiteSCCreditMemoPage savedCreditMemoPage = creditMemoPage.saveOrder();

		checkBasicOrderAmounts(savedCreditMemoPage, expectedPricesAfterSaving);
		checkOrderWithTax(savedCreditMemoPage, expectedPricesAfterSaving, itemOne);

		NetsuiteTransactionsSearchPage searchPage = savedCreditMemoPage.navigationPane.navigateThrough(
			searchCreditMemoMenu);
		searchPage.enterTransactionNumberAndId(transactionNumber);
		NetsuiteTransactionsSearchResultPage searchResultsPage = searchPage.submitSearch();
		NetsuiteSCCreditMemoPage editedCreditMemoPage = searchResultsPage.edit(transactionNumber);
		NetsuiteTransactionsPage transactionsPage = editedCreditMemoPage.deleteOrder();
		assertTrue(transactionsPage.isOrderDeleted());
	}
}
