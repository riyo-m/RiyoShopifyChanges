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
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWCreditMemoPage;
import com.vertex.quality.connectors.netsuite.oneworld.tests.base.NetsuiteBaseOWTest;
import org.testng.annotations.Test;

/**
 * Tests credit memos
 *
 * @author hho, ravunuri
 */
public class NetsuiteOWCreditMemoTests extends NetsuiteBaseOWTest
{
	/**
	 * Checks for Vertex tax integration on credit memos.
	 * CNSL-227
	 */
	@Test(groups = { "netsuite_ow_smoke" })
	protected void validateCreditMemoTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
		NetsuiteItem itemOne = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.quantity("1")
			.amount("100.00")
			.build();

		NetsuiteAddress addressOne = NetsuiteAddress
			.builder("08817")
			.fullAddressLine1("883 US Highway 1")
			.addressLine1("883 US Highway 1")
			.city("Edison")
			.state(State.NJ)
			.country(Country.USA)
			.zip9("08817-4677")
			.build();

		String location = "San Francisco";

		NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
			.builder()
			.itemTaxRate("6.63%")
			.itemTaxCode(defaultTaxCode)
			.subtotal("100.00")
			.taxAmount("6.63")
			.total("106.63")
			.build();

		NetsuiteNavigationMenus creditMemoMenu = getCreditMemoMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();

		NetsuiteOWCreditMemoPage creditMemoPage = setupManagerPage.navigationPane.navigateThrough(creditMemoMenu);
		setupBasicOrder(creditMemoPage, customer, addressOne, itemOne);
		creditMemoPage.selectLocation(location);

		NetsuiteOWCreditMemoPage savedCreditMemoPage = creditMemoPage.saveOrder();

		checkBasicOrderAmounts(savedCreditMemoPage, expectedPricesAfterSaving);
		checkOrderWithTax(savedCreditMemoPage, expectedPricesAfterSaving, itemOne);

		savedCreditMemoPage.editOrder();
		savedCreditMemoPage.deleteMemo();
	}
}
