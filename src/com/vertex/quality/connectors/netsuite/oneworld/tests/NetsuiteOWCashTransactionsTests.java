package com.vertex.quality.connectors.netsuite.oneworld.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuitePrices;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWCashRefundPage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWCashSalePage;
import com.vertex.quality.connectors.netsuite.oneworld.tests.base.NetsuiteBaseOWTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * Tests cash transactions for One World
 *
 * @author hho
 */
@SuppressWarnings("Duplicates")
public class NetsuiteOWCashTransactionsTests extends NetsuiteBaseOWTest
{
	/**
	 * Checks for Vertex tax integration on cash transactions.
	 */
	@Ignore
	@Test(groups = { "netsuite_ow_smoke" })
	protected void validateCashTransactionTest( )
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
			.addressLine1("100 Universal City Plaza")
			.city("Universal City")
			.state(State.CA)
			.country(Country.USA)
			.zip9("91608-1002")
			.build();

		String date1 = "12/15/2017";
		String date2 = "12/22/2017";
		String location = "01: San Francisco";
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

		NetsuiteNavigationMenus cashSaleMenu = getCashSaleMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();

		NetsuiteOWCashSalePage cashSalePage = setupManagerPage.navigationPane.navigateThrough(cashSaleMenu);
		setupBasicOrder(cashSalePage, customer, addressOne, itemOne);
		cashSalePage.enterDate(date1);
		cashSalePage.selectLocation(location);

		//cashSalePage.selectItemsTab();
		checkBasicOrderAmounts(cashSalePage, expectedPricesBeforeSaving);
		checkOrderWithTax(cashSalePage, expectedPricesBeforeSaving, itemOne);

		NetsuiteOWCashSalePage savedCashSalePage = cashSalePage.saveOrder();

		checkBasicOrderAmounts(savedCashSalePage, expectedPricesAfterSaving);
		checkOrderWithTax(savedCashSalePage, expectedPricesAfterSaving, itemOne);

		NetsuiteOWCashRefundPage cashRefundPage = savedCashSalePage.createCashRefund();

		cashRefundPage.enterDate(date2);

		NetsuiteOWCashRefundPage savedCashRefundPage = cashRefundPage.saveOrder();

		checkBasicOrderAmounts(savedCashRefundPage, expectedPricesAfterSaving);
		checkOrderWithTax(savedCashRefundPage, expectedPricesAfterSaving, itemOne);

		NetsuiteOWCashRefundPage editedCashRefundPage = savedCashRefundPage.editOrder();
		editedCashRefundPage.deleteOrder();
	}
}
