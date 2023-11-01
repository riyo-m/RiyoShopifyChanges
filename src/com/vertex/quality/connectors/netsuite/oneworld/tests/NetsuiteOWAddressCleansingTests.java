package com.vertex.quality.connectors.netsuite.oneworld.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.oneworld.pages.customers.NetsuiteOWCustomerPage;
import com.vertex.quality.connectors.netsuite.oneworld.tests.base.NetsuiteBaseOWTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Contains all tests to verify that address cleansing is working correctly
 * for One World
 *
 * @author hho, ravunuri
 */
@SuppressWarnings("Duplicates")
public class NetsuiteOWAddressCleansingTests extends NetsuiteBaseOWTest
{
	/**
	 * Tests to check if address cleansing is functional
	 * Checks if address cleansing is functional by first configuring Vertex
	 * integration to be on, and then checking for a variety of valid and
	 * invalid addresses to see if an error is displayed.
	 * CNSL-224
	 */

	@Test(groups = { "address_cleansing", "netsuite_ow_smoke" })
	public void addressCleansingTest( )
	{
		NetsuiteAddress invalidAddress = NetsuiteAddress
			.builder("99999")
			.addressLine1("45 Rockefeller Plaza")
			.city("Hyderabad")
			.state(State.CA)
			.country(Country.USA)
			.build();

		NetsuiteAddress invalidAddress2 = NetsuiteAddress
			.builder("99999")
			.addressLine1("45 Rockefeller Plaza")
			.city("Anaheim")
			.state(State.CA)
			.country(Country.USA)
			.build();

		NetsuiteAddress validAddress = NetsuiteAddress
			.builder("92805")
			.addressLine1("1816 E Morava Ave")
			.city("Anaheim")
			.state(State.CA)
			.country(Country.USA)
			.zip9("92805-5433")
			.build();

		NetsuiteNavigationMenus customerMenus = getCustomerMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();

		NetsuiteOWCustomerPage customerPage = setupManagerPage.navigationPane.navigateThrough(customerMenus);

		customerPage.selectAddressTab();
		customerPage.createNewAddress(invalidAddress);
		assertTrue(!customerPage.isAddressVerified());
		customerPage.cancelAddressEdit();
		customerPage.createNewAddress(invalidAddress2);
		assertTrue(!customerPage.isAddressVerified());
		customerPage.cancelAddressEdit();
		customerPage.createNewAddress(validAddress);
		assertTrue(customerPage.isAddressVerified());
		customerPage.addAddress();
		assertTrue(customerPage.isAddressDisplayed(validAddress));
	}
}
