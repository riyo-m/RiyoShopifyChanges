package com.vertex.quality.connectors.netsuite.oneworld.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pages.customers.NetsuiteCustomerListPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.oneworld.pages.customers.NetsuiteOWCustomerPage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWSalesOrderPage;
import com.vertex.quality.connectors.netsuite.oneworld.tests.base.NetsuiteBaseOWTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests if changing any addresses for any transaction would accurately change the price
 *
 * @author hho, ravunuri
 */
public class NetsuiteOWDestinationAddressTests extends NetsuiteBaseOWTest
{
	/**
	 * Tests address cleansing on a new customer record (corresponds to the Administrative Destination)
	 * Address input is a valid address
	 * CNSL-217
	 */
	@Test(groups = { "address", "netsuite_ow_regression"})
	public void validCustomerAddressTest( )
	{
		NetsuiteAddress validAddress = NetsuiteAddress
			.builder("19406")
			.addressLine1("2301 Renaissance Blvd")
			.city("King Of Prussia")
			.state(State.PA)
			.country(Country.USA)
			.build();
		NetsuiteAddress verifiedAddress = NetsuiteAddress
			.builder("19406")
			.addressLine1("2301 Renaissance Blvd")
			.city("King Of Prussia")
			.state(State.PA)
			.country(Country.USA)
			.zip9("19406-2772")
			.build();
		NetsuiteNavigationMenus customerMenu = getCustomerMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteOWCustomerPage customerPage = setupManagerPage.navigationPane.navigateThrough(customerMenu);
		customerPage.selectAddressTab();
		customerPage.createNewAddress(validAddress);
		assertTrue(customerPage.isAddressVerified());
		assertTrue(customerPage.isAddressDisplayed(verifiedAddress));
	}

	/**
	 * Tests address cleansing on a new customer record (corresponds to the Administrative Destination)
	 * Address input is an address with an invalid zip - should still be able to be cleansed
	 * CNSL-210
	 */
	@Test(groups = { "address", "netsuite_ow_regression" })
	public void invalidZipCustomerAddressTest( )
	{
		NetsuiteAddress invalidAddress = NetsuiteAddress
			.builder("99999")
			.addressLine1("2301 Renaissance Blvd")
			.city("King Of Prussia")
			.state(State.PA)
			.country(Country.USA)
			.build();
		NetsuiteAddress verifiedAddress = NetsuiteAddress
			.builder("19406")
			.addressLine1("2301 Renaissance Blvd")
			.city("King Of Prussia")
			.state(State.PA)
			.country(Country.USA)
			.zip9("19406-2772")
			.build();
		NetsuiteNavigationMenus customerMenu = getCustomerMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteOWCustomerPage customerPage = setupManagerPage.navigationPane.navigateThrough(customerMenu);
		customerPage.selectAddressTab();
		customerPage.createNewAddress(invalidAddress);
		assertTrue(customerPage.isAddressVerified());
		assertTrue(customerPage.isAddressDisplayed(verifiedAddress));
	}

	/**
	 * Tests address cleansing on a new customer record (corresponds to the Administrative Destination)
	 * Address input is an invalid address (invalid city and zip) that cannot be cleansed
	 * CNSL-214
	 */
	@Test(groups = { "address", "netsuite_ow_regression" })
	public void InvalidZipAndCityCustomerAddressTest( )
	{
		NetsuiteAddress invalidAddress = NetsuiteAddress
			.builder("99999")
			.addressLine1("2301 Renaissance Blvd")
			.city("Philadelphia")
			.state(State.PA)
			.country(Country.USA)
			.build();
		NetsuiteNavigationMenus customerMenu = getCustomerMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteOWCustomerPage customerPage = setupManagerPage.navigationPane.navigateThrough(customerMenu);
		customerPage.selectAddressTab();
		customerPage.createNewAddress(invalidAddress);
		assertTrue(!customerPage.isAddressVerified());
	}

	/**
	 * Tests address cleansing on a new customer record (corresponds to the Administrative Destination)
	 * Address input is an invalid address (invalid city and zip), followed by a valid address on the same open form
	 * CNSL-36
	 */
	@Test(groups = { "address", "netsuite_ow_regression" })
	public void recleanseInvalidCustomerAddressTest( )
	{
		NetsuiteAddress invalidAddress = NetsuiteAddress
			.builder("99999")
			.addressLine1("2301 Renaissance Blvd")
			.city("Philadelphia")
			.state(State.PA)
			.country(Country.USA)
			.build();
		NetsuiteAddress validAddress = NetsuiteAddress
			.builder("19406")
			.addressLine1("2301 Renaissance Blvd")
			.city("King Of Prussia")
			.state(State.PA)
			.country(Country.USA)
			.build();
		NetsuiteAddress verifiedAddress = NetsuiteAddress
			.builder("19406")
			.addressLine1("2301 Renaissance Blvd")
			.city("King Of Prussia")
			.state(State.PA)
			.country(Country.USA)
			.zip9("19406-2772")
			.build();
		NetsuiteNavigationMenus customerMenu = getCustomerMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteOWCustomerPage customerPage = setupManagerPage.navigationPane.navigateThrough(customerMenu);
		customerPage.selectAddressTab();
		customerPage.createNewAddress(invalidAddress);
		assertTrue(!customerPage.isAddressVerified());
		customerPage.editOpenAddressForm(validAddress);
		assertTrue(customerPage.isAddressVerified());
		assertTrue(customerPage.isAddressDisplayed(verifiedAddress));
	}

	/**
	 * Tests address cleansing on a document for the whole order using the new button (corresponds to the Physical
	 * Destination)
	 * Address input is a valid address
	 * CNSL-37
	 */
	@Test(groups = { "address", "netsuite_ow_regression" })
	public void validSalesOrderShippingAddressTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_AUTOMATION_CUSTOMER_1;
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
		NetsuiteNavigationMenus customerListMenu = getCustomerListMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		salesOrderPage.selectCustomer(customer);
		salesOrderPage.createNewShipToAddress(validAddress);
		assertTrue(salesOrderPage.isAddressVerified());
		assertTrue(salesOrderPage.isCleansedShippingAddressDisplayed(verifiedAddress));
		NetsuiteCustomerListPage customerListPage = salesOrderPage.navigationPane.navigateThrough(customerListMenu);
		NetsuiteOWCustomerPage customerPage = customerListPage.edit(customer.getCustomerName());
		customerPage.selectAddressTab();
		customerPage.removeAddress(verifiedAddress.getAddressLine1());
		customerPage.save();
	}

	/**
	 * Tests address cleansing on a new document for the whole order using the custom option (corresponds to the
	 * Physical Destination)
	 * Address input is an address with an invalid zip - should still be able to be cleansed
	 * CNSL-211
	 */
	@Test(groups = { "address", "netsuite_ow_regression" })
	public void invalidZipSalesOrderShippingAddressTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_AUTOMATION_CUSTOMER_1;
		NetsuiteAddress invalidAddress = NetsuiteAddress
			.builder("99999")
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
		NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		salesOrderPage.selectCustomer(customer);
		salesOrderPage.createCustomShipToAddress(invalidAddress);
		assertTrue(salesOrderPage.isAddressVerified());
		assertTrue(salesOrderPage.isCleansedShippingAddressDisplayed(verifiedAddress));
	}

	/**
	 * Tests address cleansing on a new document for a line item using the new option (corresponds to the Physical
	 * Destination)
	 * Address input is an invalid address (invalid city and zip) that cannot be cleansed
	 * CNSL-215
	 */
	@Test(groups = { "address", "netsuite_ow_regression" })
	public void invalidZipAndCitySalesOrderShippingAddressTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_AUTOMATION_CUSTOMER_1;
		NetsuiteAddress invalidAddress = NetsuiteAddress
			.builder("99999")
			.addressLine1("1041 Old Cassatt Rd")
			.city("Philadelphia")
			.state(State.PA)
			.country(Country.USA)
			.build();
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.amount("100.00")
			.quantity("1")
			.build();
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		salesOrderPage.selectCustomer(customer);
		salesOrderPage.selectItemsTab();
		salesOrderPage.enableLineItemShipping();
		salesOrderPage.addItemToTransaction(item);
		salesOrderPage.createNewItemShipToAddress(item, invalidAddress);
		assertTrue(!salesOrderPage.isAddressVerified());
	}

	/**
	 * Tests address cleansing on a new document for a line item using the custom option (corresponds to the Physical
	 * Destination)
	 * Address input is an invalid address (invalid city and zip), followed by a valid address on the same open form
	 * CNSL-212
	 */
	@Test(groups = { "address", "netsuite_ow_regression" })
	public void recleanseInvalidSalesOrderShippingAddressTest( )
	{
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_AUTOMATION_CUSTOMER_1;
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
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.amount("100.00")
			.quantity("1")
			.build();
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		salesOrderPage.selectCustomer(customer);
		salesOrderPage.selectItemsTab();
		salesOrderPage.enableLineItemShipping();
		salesOrderPage.addItemToTransaction(item);
		salesOrderPage.createCustomItemShipToAddress(item, invalidAddress);
		assertTrue(!salesOrderPage.isAddressVerified());
		salesOrderPage.editOpenAddress(validAddress);
		assertTrue(salesOrderPage.isAddressVerified());
		assertTrue(salesOrderPage.isLineItemShipToAddressCleansed(item, verifiedAddress));
	}
}
