package com.vertex.quality.connectors.ariba.connector.tests.misc;

import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavConfigurationMenuOption;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavOption;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnMenuBasePage;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnManageTenantPage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnHomePage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnSignOnPage;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaConnBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * tests of vital functionality of navigating to various parts of the site, plus
 * less important tests of prominent external links
 *
 * @author ssalisbury
 */

public class AribaConnLoginTests extends AribaConnBaseTest
{
	/**
	 * tests whether tests can so much as load the configuration site's initial page
	 *
	 * TODO JIRA lacks a story for the corresponding feature
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void configSiteAvailableTest( )
	{
		assertTrue(testStartPage.isCurrentPage());
	}

	/**
	 * tests whether tests can so much as properly sign in to this configuration site
	 *
	 * TODO JIRA lacks a story for the corresponding feature
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void loginTest( )
	{

		AribaConnHomePage homePage = connUtil.signInToConfig(testStartPage);
		assertTrue(homePage.isCurrentPage());
	}

	/**
	 * tests whether the logout button loads the sign on page
	 *
	 * TODO JIRA lacks a story for the corresponding feature
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void headerLogoutLinkTest( )
	{
		connUtil.signInToConfig(testStartPage);
		testStartPage.header.clickAccountIcon();
		assertTrue(testStartPage.header.isLogoutButtonDisplayed());

		AribaConnSignOnPage signOnPage = testStartPage.header.clickLogout();
		assertTrue(signOnPage.isLoginAvailable());
	}

	/**
	 * tests whether the logout button loads the sign on page
	 * after logging in
	 *
	 * TODO JIRA lacks a story for the corresponding feature
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui", "ariba_smoke", "ariba_regression" })
	public void headerLoginAndLogoutTest( )
	{
		AribaConnHomePage homePage = connUtil.signInToConfig(testStartPage);

		homePage.header.clickAccountIcon();
		assertTrue(homePage.header.isLogoutButtonDisplayed());

		AribaConnSignOnPage signOnPage = homePage.header.clickLogout();
		assertTrue(signOnPage.isLoginAvailable());
	}

	/**
	 * tests whether the logout button's visibility changes
	 * based on whether its flyout panel is displayed
	 *
	 * TODO JIRA lacks a story for the corresponding feature
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void headerLogoutButtonVisibilityToggleTest( )
	{
		AribaConnHomePage homePage = connUtil.signInToConfig(testStartPage);

		boolean logoutDisplayedOnHomepage = homePage.header.isLogoutButtonDisplayed();
		assertTrue(!logoutDisplayedOnHomepage);

		homePage.header.clickAccountIcon();

		boolean logoutDisplayedAfterClickingIcon = homePage.header.isLogoutButtonDisplayed();
		assertTrue(logoutDisplayedAfterClickingIcon);
	}

	/**
	 * for the following configuration menu access security test cases:
	 * TODO JIRA lacks a story for the corresponding feature
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void configurationMenuCreateOrModifyTenantsLoginSecurityTest( )
	{
		checkConfigurationMenuLoginSecurity(testStartPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);
	}

	@Test(groups = { "ariba_ui","ariba_regression" })
	public void configurationMenuConfigureConnectorLoginSecurityTest( )
	{
		checkConfigurationMenuLoginSecurity(testStartPage,
			AribaConnNavConfigurationMenuOption.CONFIGURE_CONNECTION_PROPERTIES);
	}

	@Test(groups = { "ariba_ui","ariba_regression" })
	public void configurationMenuCustomFieldLoginSecurityTest( )
	{
		checkConfigurationMenuLoginSecurity(testStartPage,
			AribaConnNavConfigurationMenuOption.MODIFY_CUSTOM_FIELD_MAPPING);
	}

	@Test(groups = { "ariba_ui","ariba_regression" })
	public void configurationMenuAccountFieldLoginSecurityTest( )
	{
		checkConfigurationMenuLoginSecurity(testStartPage,
			AribaConnNavConfigurationMenuOption.MODIFY_ACCOUNT_FIELD_MAPPING);
	}

	@Test(groups = { "ariba_ui","ariba_regression" })
	public void configurationMenuTaxRulesLoginSecurityTest( )
	{
		checkConfigurationMenuLoginSecurity(testStartPage, AribaConnNavConfigurationMenuOption.MODIFY_TAX_RULES);
	}

	@Test(groups = { "ariba_ui","ariba_regression" })
	public void configurationViewXMLLoginSecurityTest( )
	{
		checkConfigurationMenuLoginSecurity(testStartPage,
			AribaConnNavConfigurationMenuOption.VIEW_LOGGED_XML_MESSAGES);
	}

	/**
	 * for the following configuration menu access test cases:
	 * TODO JIRA lacks a story for the corresponding feature
	 * maybe CARIBA-14?
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void configurationMenuCreateOrModifyTenantsAccessTest( )
	{
		checkConfigurationMenuAccess(testStartPage, AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);
	}

	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void configurationMenuConfigureConnectorAccessTest( )
	{
		checkConfigurationMenuAccess(testStartPage,
			AribaConnNavConfigurationMenuOption.CONFIGURE_CONNECTION_PROPERTIES);
	}

	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void configurationMenuCustomFieldAccessTest( )
	{
		checkConfigurationMenuAccess(testStartPage, AribaConnNavConfigurationMenuOption.MODIFY_CUSTOM_FIELD_MAPPING);
	}

	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void configurationMenuAccountFieldAccessTest( )
	{
		checkConfigurationMenuAccess(testStartPage, AribaConnNavConfigurationMenuOption.MODIFY_ACCOUNT_FIELD_MAPPING);
	}

	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void configurationMenuTaxRulesAccessTest( )
	{
		checkConfigurationMenuAccess(testStartPage, AribaConnNavConfigurationMenuOption.MODIFY_TAX_RULES);
	}

	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void configurationViewXMLAccessTest( )
	{
		checkConfigurationMenuAccess(testStartPage, AribaConnNavConfigurationMenuOption.VIEW_LOGGED_XML_MESSAGES);
	}

	/**
	 * JIRA ticket CARIBA-427
	 * checks that the default tenant is selected when the user first logs in
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void initiallySelectedTenantTest( )
	{
		AribaConnManageTenantPage tenantPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);
		String initialTenantName = tenantPage.tenantSelector.getSelectedTenant();
		assertEquals(initialTenantName, defaultTenantDisplayName);
	}

	/**
	 * checks that, before logging in, trying to access the given configuration menu option's page redirects to the
	 * login page
	 *
	 * @param startPage  the sign in page on the Ariba connector site, which this procedure starts on
	 * @param menuOption which configuration menu to try to access without having logged in first
	 *
	 * @author ssalisbury
	 */
	protected void checkConfigurationMenuLoginSecurity( final AribaConnSignOnPage startPage,
		final AribaConnNavConfigurationMenuOption menuOption )
	{
	 	connUtil.signInToConfig(startPage);
		// loads the website and verifies that the user is not yet logged into the website

		testStartPage.navPanel.clickNavOption(AribaConnNavOption.CONFIGURATION_MENU);
		AribaConnMenuBasePage maybeConfigMenuPage = testStartPage.navPanel.clickNavConfigurationMenuOption(menuOption);

		assertTrue(didConfigMenuRedirectSignOn(maybeConfigMenuPage, testStartPage, menuOption));
	}

	/**
	 * checks that the given configuration method can be opened after logging in,
	 * then makes sure that the user can log out from that configuration menu
	 *
	 * @param startPage  the sign in page on the Ariba connector site, which this procedure starts on
	 * @param menuOption which configuration menu to try to open
	 */
	protected void checkConfigurationMenuAccess( final AribaConnSignOnPage startPage,
		final AribaConnNavConfigurationMenuOption menuOption )
	{
		AribaConnHomePage homePage = connUtil.signInToConfig(startPage);

		homePage.navPanel.clickNavOption(AribaConnNavOption.CONFIGURATION_MENU);
		AribaConnMenuBasePage configMenuPage = homePage.navPanel.clickNavConfigurationMenuOption(menuOption);
		assertTrue(configMenuPage.isCurrentPage());

		configMenuPage.header.clickAccountIcon();
		configMenuPage.header.clickLogout();
	}
}
