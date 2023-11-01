package com.vertex.quality.connectors.netsuite.oneworld.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteNavigationMenuTitles;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteGlobalSearchResultsPage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteLocationListPage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.oneworld.pages.NetsuiteOWLocationPage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.NetsuiteOWSubsidiariesListPage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.NetsuiteOWSubsidiaryPage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWSalesOrderPage;
import com.vertex.quality.connectors.netsuite.oneworld.tests.base.NetsuiteBaseOWTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Tests origin address cleansing
 * Only physical origin is currently supported
 *
 * @author hho
 */
@SuppressWarnings("Duplicates")
public class NetsuiteOWOriginAddressTests extends NetsuiteBaseOWTest
{
	/**
	 * Tests address cleansing on the subsidiary's shipping address(corresponds to the Physical Origin)
	 * Address input is a valid address
	 * CNSL-220
	 */
	@Test(groups = { "address", "netsuite_ow_regression" })
	public void validSubsidiaryShippingAddressTest( )
	{
		String subsidiary = "Honeycomb Mfg.";
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_AUTOMATION_CUSTOMER_1;
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.quantity("1")
			.amount("100.00")
			.build();
		NetsuiteAddress validAddress = getAddress()
			.zip9("")
			.build();
		NetsuiteAddress verifiedAddress = getAddress().build();
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteGlobalSearchResultsPage globalSearchResultsPage = setupManagerPage.navigationPane.search(
			NetsuiteNavigationMenuTitles.SUBSIDIARIES);
		NetsuiteOWSubsidiariesListPage subsidiariesListPage = globalSearchResultsPage.view(
			NetsuiteNavigationMenuTitles.SUBSIDIARIES.getTitle());
		NetsuiteOWSubsidiaryPage subsidiaryPage = subsidiariesListPage.edit(subsidiary);
		subsidiaryPage.editShippingAddress(validAddress);
		assertTrue(subsidiaryPage.isAddressVerified());
		assertTrue(subsidiaryPage.isCleansedShippingAddressDisplayed(verifiedAddress));
		NetsuiteOWSubsidiariesListPage configuredSubsidiariesListPage = subsidiaryPage.save();
		NetsuiteOWSalesOrderPage salesOrderPage = configuredSubsidiariesListPage.navigationPane.navigateThrough(
			salesOrderMenu);
		setupSalesOrderAndCheckLogs(salesOrderPage, customer, item, "", verifiedAddress);
	}

	/**
	 * Tests address cleansing on the subsidiary's shipping address(corresponds to the Physical Origin)
	 * Address input is an invalid address with invalid zip
	 * CNSL-221
	 */
	@Test(groups = { "address", "netsuite_ow_regression" })
	public void invalidZipSubsidiaryShippingAddressTest( )
	{
		String subsidiary = "Honeycomb Mfg.";

		NetsuiteAddress invalidAddress = getAddress()
			.zip9("")
			.zip5("")
			.build();
		NetsuiteAddress verifiedAddress = getAddress().build();
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteGlobalSearchResultsPage globalSearchResultsPage = setupManagerPage.navigationPane.search(
			NetsuiteNavigationMenuTitles.SUBSIDIARIES);
		NetsuiteOWSubsidiariesListPage subsidiariesListPage = globalSearchResultsPage.view(
			NetsuiteNavigationMenuTitles.SUBSIDIARIES.getTitle());
		NetsuiteOWSubsidiaryPage subsidiaryPage = subsidiariesListPage.edit(subsidiary);
		subsidiaryPage.editShippingAddress(invalidAddress);
		assertTrue(subsidiaryPage.isAddressVerified());
		assertTrue(subsidiaryPage.isCleansedShippingAddressDisplayed(verifiedAddress));
		NetsuiteOWSubsidiariesListPage configuredSubsidiariesListPage = subsidiaryPage.save();
		configuredSubsidiariesListPage.navigationPane.navigateThrough(
			salesOrderMenu);

	}

	/**
	 * Tests address cleansing on the subsidiary's shipping address(corresponds to the Physical Origin)
	 * Address input is an invalid address with invalid zip and invalid city
	 * CNSL-48
	 */
	@Test(groups = { "address", "netsuite_ow_regression" })
	public void invalidZipAndCitySubsidiaryShippingAddressTest( )
	{
		String subsidiary = "Honeycomb Mfg.";
		NetsuiteAddress invalidAddress = getAddress()
			.zip9("")
			.zip5("")
			.city("Berwyn")
			.build();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteGlobalSearchResultsPage globalSearchResultsPage = setupManagerPage.navigationPane.search(
			NetsuiteNavigationMenuTitles.SUBSIDIARIES);
		NetsuiteOWSubsidiariesListPage subsidiariesListPage = globalSearchResultsPage.view(
			NetsuiteNavigationMenuTitles.SUBSIDIARIES.getTitle());
		NetsuiteOWSubsidiaryPage subsidiaryPage = subsidiariesListPage.edit(subsidiary);
		subsidiaryPage.editShippingAddress(invalidAddress);
		assertFalse(subsidiaryPage.isAddressVerified());
	}

	/**
	 * Tests address cleansing on the subsidiary's shipping address(corresponds to the Physical Origin)
	 * Address input is an invalid address followed by a valid address
	 * CNSL-218
	 */
	@Test(groups = { "address", "netsuite_ow_regression" })
	public void recleanseInvalidSubsidiaryShippingAddressTest( )
	{
		String subsidiary = "Honeycomb Mfg.";
		NetsuiteAddress invalidAddress = getAddress()
			.zip9("")
			.zip5("")
			.city("Berwyn")
			.build();
		NetsuiteAddress validAddress = getAddress()
			.zip9("")
			.build();
		NetsuiteAddress verifiedAddress = getAddress().build();
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteGlobalSearchResultsPage globalSearchResultsPage = setupManagerPage.navigationPane.search(
			NetsuiteNavigationMenuTitles.SUBSIDIARIES);
		NetsuiteOWSubsidiariesListPage subsidiariesListPage = globalSearchResultsPage.view(
			NetsuiteNavigationMenuTitles.SUBSIDIARIES.getTitle());
		NetsuiteOWSubsidiaryPage subsidiaryPage = subsidiariesListPage.edit(subsidiary);
		subsidiaryPage.editShippingAddress(invalidAddress);
		assertFalse(subsidiaryPage.isAddressVerified());
		subsidiaryPage.editOpenAddressForm(validAddress);
		assertTrue(subsidiaryPage.isAddressVerified());
		assertTrue(subsidiaryPage.isCleansedShippingAddressDisplayed(verifiedAddress));
		NetsuiteOWSubsidiariesListPage configuredSubsidiariesListPage = subsidiaryPage.save();
		configuredSubsidiariesListPage.navigationPane.navigateThrough(
			salesOrderMenu);
	}

	/**
	 * Tests address cleansing on a new location address(corresponds to the Physical Origin)
	 * Address input is an invalid address followed by a valid address
	 * CNSL-222
	 */
	@Test(groups = { "address", "netsuite_ow_regression" })
	public void recleanseInvalidLocationAddressTest( )
	{
		String subsidiary = "Honeycomb Holdings Inc. : Honeycomb Mfg.";
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_AUTOMATION_CUSTOMER_1;
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.quantity("1")
			.amount("100.00")
			.build();
		NetsuiteAddress invalidAddress = NetsuiteAddress
			.builder("99999")
			.addressLine1("1041 Old Cassatt Rd")
			.city("Philadelphia")
			.state(State.PA)
			.country(Country.USA)
			.build();
		NetsuiteAddress validAddress = NetsuiteAddress
			.builder("19312")
			.addressLine1("1041 Old Cassatt Rd")
			.city("Berwyn")
			.state(State.PA)
			.country(Country.USA)
			.build();
		NetsuiteAddress verifiedAddress = NetsuiteAddress
			.builder("19312")
			.addressLine1("1041 Old Cassatt Rd")
			.city("Berwyn")
			.state(State.PA)
			.country(Country.USA)
			.zip9("19312-1152")
			.build();
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteGlobalSearchResultsPage globalSearchResultsPage = setupManagerPage.navigationPane.search(
			NetsuiteNavigationMenuTitles.LOCATIONS);
		NetsuiteLocationListPage locationListPage = globalSearchResultsPage.edit("Locations");
		NetsuiteOWLocationPage locationPage = locationListPage.createNew();
		locationPage.enterLocationName(verifiedAddress.getAddressLine1());
		locationPage.selectSubsidiary(subsidiary);
		locationPage.editAddress(invalidAddress);
		assertFalse(locationPage.isAddressVerified());
		locationPage.editOpenAddressForm(validAddress);
		assertTrue(locationPage.isAddressVerified());
		assertTrue(locationPage.isCleansedAddressDisplayed(verifiedAddress));
		locationListPage = locationPage.save();
		NetsuiteOWSalesOrderPage salesOrderPage = locationListPage.navigationPane.navigateThrough(salesOrderMenu);
		NetsuiteTransactionsPage transactionsPage = setupSalesOrderAndCheckLogs(salesOrderPage, customer, item,
			verifiedAddress.getAddressLine1(), verifiedAddress);
		globalSearchResultsPage = transactionsPage.navigationPane.search(NetsuiteNavigationMenuTitles.LOCATIONS);
		locationListPage = globalSearchResultsPage.edit("Locations");
		NetsuiteOWLocationPage editLocationPage = locationListPage.edit(verifiedAddress.getAddressLine1());
		editLocationPage.delete();
	}

	/**
	 * Sets up the sales order and then checks the log
	 *
	 * @param salesOrderPage  the sales order page
	 * @param customer        the customer
	 * @param item            the item
	 * @param location        the location
	 * @param verifiedAddress the cleansed address
	 *
	 * @return the transactions page
	 */
	private NetsuiteTransactionsPage setupSalesOrderAndCheckLogs( NetsuiteOWSalesOrderPage salesOrderPage,
		NetsuiteCustomer customer, NetsuiteItem item, String location, NetsuiteAddress verifiedAddress )
	{
		setupBasicOrder(salesOrderPage, customer, item);
		salesOrderPage.selectLocation(location);
		NetsuiteOWSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();
		NetsuiteOWSalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage,
			verifiedAddress.getAddressLine1(), verifiedAddress.getZip9());
		return deleteDocument(previousSalesOrderPage);
	}

	/**
	 * Test address used for test cases
	 *
	 * @return the test address
	 */
	private NetsuiteAddress.NetsuiteAddressBuilder getAddress( )
	{
		return NetsuiteAddress
			.builder("19104")
			.attention("Test")
			.addressee("Test1")
			.addressLine1("2955 Market St Ste 2")
			.addressLine2("Apt 2")
			.city("Philadelphia")
			.state(State.PA)
			.country(Country.USA)
			.zip9("19104-2817");
	}
}
