package com.vertex.quality.connectors.netsuite.singlecompany.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pages.customers.NetsuiteCustomerListPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.customers.NetsuiteSCCustomerPage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCSalesOrderPage;
import com.vertex.quality.connectors.netsuite.singlecompany.tests.base.NetsuiteBaseSCTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests if changing any addresses for any transaction would accurately change the price
 *
 * @author hho, jyareeda
 */
public class NetsuiteSCDestinationAddressTests extends NetsuiteBaseSCTest
{
	/**
	 * Tests address cleansing on a new customer record (corresponds to the Administrative Destination)
	 * Address input is a valid address
	 * CNSL-32
	 */
	@Test(groups = { "address", "netsuite_regression" })
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
		NetsuiteSCCustomerPage customerPage = setupManagerPage.navigationPane.navigateThrough(customerMenu);
		customerPage.selectAddressTab();
		customerPage.createNewAddress(validAddress);
		assertTrue(customerPage.isAddressVerified());
		assertTrue(customerPage.isAddressDisplayed(verifiedAddress));
	}

	/**
	 * Tests address cleansing on a new customer record (corresponds to the Administrative Destination)
	 * Address input is an address with an invalid zip - should still be able to be cleansed
	 * CNSL-34
	 */
	@Test(groups = { "address", "netsuite_regression" })
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
		NetsuiteSCCustomerPage customerPage = setupManagerPage.navigationPane.navigateThrough(customerMenu);
		customerPage.selectAddressTab();
		customerPage.createNewAddress(invalidAddress);
		assertTrue(customerPage.isAddressVerified());
		assertTrue(customerPage.isAddressDisplayed(verifiedAddress));
	}

	/**
	 * Tests address cleansing on a new customer record (corresponds to the Administrative Destination)
	 * Address input is an invalid address (invalid city and zip) that cannot be cleansed
	 * CNSL-35
	 */
	@Test(groups = { "address", "netsuite_regression" })
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
		NetsuiteSCCustomerPage customerPage = setupManagerPage.navigationPane.navigateThrough(customerMenu);
		customerPage.selectAddressTab();
		customerPage.createNewAddress(invalidAddress);
		assertTrue(!customerPage.isAddressVerified());
	}

	/**
	 * Tests address cleansing on a new customer record (corresponds to the Administrative Destination)
	 * Address input is an invalid address (invalid city and zip), followed by a valid address on the same open form
	 * CNSL-216
	 */
	@Test(groups = { "address", "netsuite_regression" })
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
		NetsuiteSCCustomerPage customerPage = setupManagerPage.navigationPane.navigateThrough(customerMenu);
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
	 * CNSL-213
	 */
	@Test(groups = { "address", "netsuite_regression" })
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
		NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		salesOrderPage.selectCustomer(customer);
		salesOrderPage.selectAddressTab();
		salesOrderPage.createNewShipToAddress(validAddress);
		assertTrue(salesOrderPage.isAddressVerified());
		assertTrue(salesOrderPage.isCleansedShippingAddressDisplayed(verifiedAddress));
		NetsuiteCustomerListPage customerListPage = salesOrderPage.navigationPane.navigateThrough(customerListMenu);
		NetsuiteSCCustomerPage customerPage = customerListPage.edit(customer.getCustomerName());
		customerPage.selectAddressTab();
		customerPage.removeAddress(verifiedAddress.getAddressLine1());
		customerPage.save();
	}

	/**
	 * Tests address cleansing on a new document for the whole order using the custom option (corresponds to the
	 * Physical Destination)
	 * Address input is an address with an invalid zip - should still be able to be cleansed
	 * CNSL-38
	 */
	@Test(groups = { "address", "netsuite_regression" })
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
		NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		salesOrderPage.selectCustomer(customer);
		salesOrderPage.selectAddressTab();
		salesOrderPage.createCustomShipToAddress(invalidAddress);
		assertTrue(salesOrderPage.isAddressVerified());
		assertTrue(salesOrderPage.isCleansedShippingAddressDisplayed(verifiedAddress));
	}

	/**
	 * Tests address cleansing on a new document for a line item using the new option (corresponds to the Physical
	 * Destination)
	 * Address input is an invalid address (invalid city and zip) that cannot be cleansed
	 * CNSL-39
	 */
	@Test(groups = { "address", "netsuite_regression" })
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
			.builder(NetsuiteItemName.STANDARD_TEST_ITEM_1)
			.amount("10.00")
			.quantity("1")
			.build();
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
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
	 * CNSL-40
	 */
	@Test(groups = { "address", "netsuite_regression" })
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
			.builder(NetsuiteItemName.STANDARD_TEST_ITEM_1)
			.amount("10.00")
			.quantity("1")
			.build();
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
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
