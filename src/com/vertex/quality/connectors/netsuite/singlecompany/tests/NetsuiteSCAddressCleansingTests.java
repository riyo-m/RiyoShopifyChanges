package com.vertex.quality.connectors.netsuite.singlecompany.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.customers.NetsuiteSCCustomerPage;
import com.vertex.quality.connectors.netsuite.singlecompany.tests.base.NetsuiteBaseSCTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Contains all tests to verify that address cleansing is working correctly
 * for Single Company
 *
 * @author hho, jyareeda
 */
public class NetsuiteSCAddressCleansingTests extends NetsuiteBaseSCTest
{
	/**
	 * Checks if address cleansing is functional by first configuring Vertex
	 * integration to be on, and then checking
	 *
	 * @Test(groups = { "address_cleansing", "netsuite_smoke" })
	 */
	public void invalidZipandCityAddressCleansingTest( )
	{
		NetsuiteAddress invalidAddress1 = NetsuiteAddress
			.builder("99999")
			.addressLine1("45 Rockefeller Plaza")
			.city("Hyderabad")
			.state(State.CA)
			.country(Country.USA)
			.build();
		// To navigate to Customer menu
		NetsuiteNavigationMenus customerMenu = getCustomerMenu();
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteSCCustomerPage customerPage = setupManagerPage.navigationPane.navigateThrough(customerMenu);

		// Checking to see address is verified and displayed
		customerPage.selectAddressTab();
		customerPage.createNewAddress(invalidAddress1);
		assertTrue(!customerPage.isAddressVerified());
	}

	/**
	 * Validate address with invalid zip
	 * CNSL-325
	 */
	@Test(groups = { "address_cleansing", "netsuite_smoke" })
	public void invalidZipAddressCleansingTest( )
	{
		NetsuiteAddress invalidAddress2 = NetsuiteAddress
			.builder("99999")
			.addressLine1("45 Rockefeller Plaza")
			.city("Anaheim")
			.state(State.CA)
			.country(Country.USA)
			.build();
		// To navigate to Customer menu
		NetsuiteNavigationMenus customerMenu = getCustomerMenu();
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteSCCustomerPage customerPage = setupManagerPage.navigationPane.navigateThrough(customerMenu);

		// Checking to see address is verified and displayed
		customerPage.selectAddressTab();
		customerPage.createNewAddress(invalidAddress2);
		assertTrue(!customerPage.isAddressVerified());
	}

	/**
	 * Validate address
	 * CNSL-324
	 */
	@Test(groups = { "address_cleansing", "netsuite_smoke" })
	public void validAddressCleansingTest( )
	{
		NetsuiteAddress validAddress1 = NetsuiteAddress
			.builder("92805")
			.addressLine1("1816 E Morava Ave")
			.city("Anaheim")
			.state(State.CA)
			.country(Country.USA)
			.zip9("92805-5433")
			.build();

		// To navigate to Customer menu
		NetsuiteNavigationMenus customerMenu = getCustomerMenu();
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteSCCustomerPage customerPage = setupManagerPage.navigationPane.navigateThrough(customerMenu);

		// Checking to see address is verified and displayed
		customerPage.selectAddressTab();

		customerPage.createNewAddress(validAddress1);
		assertTrue(customerPage.isAddressVerified());
		customerPage.addAddress();
		assertTrue(customerPage.isAddressDisplayed(validAddress1));
	}
}
