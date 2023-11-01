package com.vertex.quality.connectors.netsuite.singlecompany.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteNavigationMenuTitles;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteCompanyInformationPage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteGlobalSearchResultsPage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteLocationListPage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.NetsuiteSCLocationPage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCSalesOrderPage;
import com.vertex.quality.connectors.netsuite.singlecompany.tests.base.NetsuiteBaseSCTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests origin address cleansing
 * Only physical origin is currently supported
 *
 * @author hho
 */
public class NetsuiteSCOriginAddressTests extends NetsuiteBaseSCTest
{
	/**
	 * Tests address cleansing on the company's shipping address(corresponds to the Physical Origin)
	 * Address input is a valid address
	 * CNSL-659
	 */
	@Test(groups = { "address", "netsuite_regression" })
	public void validCompanyShippingAddressTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_AUTOMATION_CUSTOMER_1;
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.STANDARD_TEST_ITEM_1)
			.amount("10.00")
			.quantity("1")
			.build();
		NetsuiteAddress validAddress = getAddress()
			.zip9("")
			.build();
		NetsuiteAddress verifiedAddress = getAddress().build();
		NetsuiteNavigationMenus companyInformationMenu = getCompanyInformationMenu();
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteCompanyInformationPage companyInformationPage = setupManagerPage.navigationPane.navigateThrough(
			companyInformationMenu);
		companyInformationPage.editShippingAddress(validAddress);
		assertTrue(companyInformationPage.isAddressVerified());
		assertTrue(companyInformationPage.isCleansedShippingAddressDisplayed(verifiedAddress));
		NetsuiteSetupManagerPage configuredSetupManagerPage = companyInformationPage.save();
		NetsuiteSCSalesOrderPage salesOrderPage = configuredSetupManagerPage.navigationPane.navigateThrough(
			salesOrderMenu);
		setupSalesOrderAndCheckLogs(salesOrderPage, customer, item, "", verifiedAddress);
	}

	/**
	 * Tests address cleansing on the company's shipping address(corresponds to the Physical Origin)
	 * Address input is an invalid address with invalid zip
	 * CNSL-47
	 */
	@Test(groups = { "address", "netsuite_regression" })
	public void invalidZipCompanyShippingAddressTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_AUTOMATION_CUSTOMER_1;
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.STANDARD_TEST_ITEM_1)
			.amount("10.00")
			.quantity("1")
			.build();
		NetsuiteAddress invalidAddress = getAddress()
			.zip9("")
			.zip5("")
			.build();
		NetsuiteAddress verifiedAddress = getAddress().build();
		NetsuiteNavigationMenus companyInformationMenu = getCompanyInformationMenu();
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteCompanyInformationPage companyInformationPage = setupManagerPage.navigationPane.navigateThrough(
			companyInformationMenu);
		companyInformationPage.editShippingAddress(invalidAddress);
		assertTrue(companyInformationPage.isAddressVerified());
		assertTrue(companyInformationPage.isCleansedShippingAddressDisplayed(verifiedAddress));
		NetsuiteSetupManagerPage configuredSetupManagerPage = companyInformationPage.save();
		NetsuiteSCSalesOrderPage salesOrderPage = configuredSetupManagerPage.navigationPane.navigateThrough(
			salesOrderMenu);
		setupSalesOrderAndCheckLogs(salesOrderPage, customer, item, "", verifiedAddress);
	}

	/**
	 * Tests address cleansing on the company's shipping address(corresponds to the Physical Origin)
	 * Address input is an invalid address with invalid zip and invalid city
	 * CNSL-48
	 */
	@Test(groups = { "address", "netsuite_regression" })
	public void invalidZipAndCityCompanyShippingAddressTest( )
	{
		NetsuiteAddress invalidAddress = getAddress()
			.zip9("")
			.zip5("")
			.city("Berwyn")
			.build();
		NetsuiteNavigationMenus companyInformationMenu = getCompanyInformationMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteCompanyInformationPage companyInformationPage = setupManagerPage.navigationPane.navigateThrough(
			companyInformationMenu);
		companyInformationPage.editShippingAddress(invalidAddress);
		assertTrue(!companyInformationPage.isAddressVerified());
	}

	/**
	 * Tests address cleansing on the company's shipping address(corresponds to the Physical Origin)
	 * Address input is an invalid address followed by a valid address
	 * CNSL-49
	 */
	@Test(groups = { "address", "netsuite_regression" })
	public void recleanseInvalidCompanyShippingAddressTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_AUTOMATION_CUSTOMER_1;
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.STANDARD_TEST_ITEM_1)
			.amount("10.00")
			.quantity("1")
			.build();
		NetsuiteAddress invalidAddress = getAddress()
			.zip9("")
			.zip5("")
			.city("Berwyn")
			.build();
		NetsuiteAddress validAddress = getAddress()
			.zip9("")
			.build();
		NetsuiteAddress verifiedAddress = getAddress().build();
		NetsuiteNavigationMenus companyInformationMenu = getCompanyInformationMenu();
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteCompanyInformationPage companyInformationPage = setupManagerPage.navigationPane.navigateThrough(
			companyInformationMenu);
		companyInformationPage.editShippingAddress(invalidAddress);
		assertTrue(!companyInformationPage.isAddressVerified());
		companyInformationPage.editOpenAddressForm(validAddress);
		assertTrue(companyInformationPage.isAddressVerified());
		assertTrue(companyInformationPage.isCleansedShippingAddressDisplayed(verifiedAddress));
		NetsuiteSetupManagerPage configuredSetupManagerPage = companyInformationPage.save();
		NetsuiteSCSalesOrderPage salesOrderPage = configuredSetupManagerPage.navigationPane.navigateThrough(
			salesOrderMenu);
		setupSalesOrderAndCheckLogs(salesOrderPage, customer, item, "", verifiedAddress);
	}

	/**
	 * Tests address cleansing on a new location address(corresponds to the Physical Origin)
	 * Address input is an invalid address followed by a valid address
	 * CNSL-50
	 */
	@Test(groups = { "address", "netsuite_regression" })
	public void recleanseInvalidLocationAddressTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_AUTOMATION_CUSTOMER_1;
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.STANDARD_TEST_ITEM_1)
			.amount("10.00")
			.quantity("1")
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
		NetsuiteSCLocationPage locationPage = locationListPage.createNew();
		locationPage.enterLocationName(verifiedAddress.getAddressLine1());
		locationPage.editAddress(invalidAddress);
		assertTrue(!locationPage.isAddressVerified());
		locationPage.editOpenAddressForm(validAddress);
		assertTrue(locationPage.isAddressVerified());
		assertTrue(locationPage.isCleansedAddressDisplayed(verifiedAddress));
		locationListPage = locationPage.save();
		NetsuiteSCSalesOrderPage salesOrderPage = locationListPage.navigationPane.navigateThrough(salesOrderMenu);
		NetsuiteTransactionsPage transactionsPage = setupSalesOrderAndCheckLogs(salesOrderPage, customer, item,
			verifiedAddress.getAddressLine1(), verifiedAddress);
		globalSearchResultsPage = transactionsPage.navigationPane.search(NetsuiteNavigationMenuTitles.LOCATIONS);
		locationListPage = globalSearchResultsPage.edit("Locations");
		NetsuiteSCLocationPage editLocationPage = locationListPage.edit(verifiedAddress.getAddressLine1());
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
	private NetsuiteTransactionsPage setupSalesOrderAndCheckLogs( NetsuiteSCSalesOrderPage salesOrderPage,
		NetsuiteCustomer customer, NetsuiteItem item, String location, NetsuiteAddress verifiedAddress )
	{
		setupBasicOrder(salesOrderPage, customer, item);
		salesOrderPage.selectLocation(location);
		NetsuiteSCSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();
		NetsuiteSCSalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage,
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
