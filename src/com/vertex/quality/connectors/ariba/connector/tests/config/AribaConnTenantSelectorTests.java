package com.vertex.quality.connectors.ariba.connector.tests.config;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.connector.components.AribaConnTenantSelector;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavConfigurationMenuOption;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavOption;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnBasePage;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnMenuBasePage;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnAccountingFieldMappingPage;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnManageTenantPage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnHomePage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnSignOnPage;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaConnMenuBaseTest;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * tests of the tool for changing the tenant whose settings are being configured
 *
 * @author ssalisbury
 */

public class AribaConnTenantSelectorTests extends AribaConnMenuBaseTest
{
	/**
	 * JIRA ticket CARIBA-540
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void tenantSelectorInitialTenantTest( )
	{
		AribaConnManageTenantPage tenantsPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);

		String initialTenantName = tenantsPage.tenantSelector.getSelectedTenant();
		assertEquals(initialTenantName, defaultTenantDisplayName);

		AribaConnMenuBasePage previousMenuPage = tenantsPage;

		final int maxNumberOfAttempts = 50;

		for ( int attemptIndex = 1 ; attemptIndex <= maxNumberOfAttempts ; attemptIndex++ )
		{
			AribaConnAccountingFieldMappingPage fieldMappingPage = connUtil.openConfigMenu(previousMenuPage,
				AribaConnNavConfigurationMenuOption.MODIFY_ACCOUNT_FIELD_MAPPING);
			AribaConnManageTenantPage returnedTenantsPage = connUtil.openConfigMenu(fieldMappingPage,
				AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);
			String refreshedInitialTenantName = tenantsPage.tenantSelector.getSelectedTenant();
			final boolean isDefaultTenant = defaultTenantDisplayName.equals(refreshedInitialTenantName);
			if ( !isDefaultTenant )
			{
				final String badInitialTenantMessage = String.format("On the %dth check of the Manage Tenants page, " +
																	 "the initially loaded tenant was %s instead of " +
																	 "'default' as it should have been", attemptIndex,
					refreshedInitialTenantName);
				VertexLogger.log(badInitialTenantMessage, VertexLogLevel.ERROR);
			}
			assertEquals(refreshedInitialTenantName, defaultTenantDisplayName);
			previousMenuPage = returnedTenantsPage;
		}
	}

	/**
	 * checks that the 'Tenant name' tenant selector dropdown loads on each
	 * configuration menu
	 *
	 * JIRA ticket CARIBA-243
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void tenantSelectorLoadTest( )
	{
		AribaConnSignOnPage signOnPage = testStartPage;
		AribaConnHomePage homePage = connUtil.signInToConfig(signOnPage);

		signOnPage.navPanel.clickNavOption(AribaConnNavOption.CONFIGURATION_MENU);

		AribaConnNavConfigurationMenuOption[] menus = AribaConnNavConfigurationMenuOption.values();
		AribaConnMenuBasePage menuPage = connUtil.openConfigMenu(homePage, menus[0]);

		for ( AribaConnNavConfigurationMenuOption menu : menus )
		{
			menuPage = connUtil.openConfigMenu(menuPage, menu);

			boolean selectorAccessible = validateTenantSelectorAccessible(menuPage, menu);
			assertTrue(selectorAccessible);
		}
	}

	/**
	 * checks that the selected tenant element changes its value when the tenant selector
	 * switches between the default tenant and the temporary tenant
	 *
	 * JIRA ticket CARIBA-245
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void tenantSelectorSelectedTenantUpdateTest( )
	{
		AribaConnSignOnPage signOnPage = testStartPage;
		AribaConnHomePage homePage = connUtil.signInToConfig(signOnPage);

		signOnPage.navPanel.clickNavOption(AribaConnNavOption.CONFIGURATION_MENU);
		AribaConnNavConfigurationMenuOption[] menus = AribaConnNavConfigurationMenuOption.values();
		AribaConnMenuBasePage menuPage = connUtil.openConfigMenu(homePage, menus[0]);

		/*
		 * the for loop through the menus ensures that all menus but the first are
		 * tested on switching from the temporary tenant (which had been set by the previous loop
		 * iteration) to the default tenant and from the default tenant back to the temporary tenant.
		 * This sets up the first menu as having temporary tenant before the first menu's for loop iteration
		 * so that the first menu will be tested on switching between the tenants in
		 * both directions in the first iteration of the for loop
		 */
		boolean firstMenuSwitchSuccess = validateTenantSwitch(defaultTenantVariantId, tempTenantVariantId,
			menuPage.tenantSelector, menus[0]);
		assertTrue(firstMenuSwitchSuccess);

		for ( AribaConnNavConfigurationMenuOption menu : menus )
		{
			menuPage = connUtil.openConfigMenu(menuPage, menu);

			boolean isCurrTenantUpdated = validateTenantSwitch(tempTenantVariantId, defaultTenantVariantId,
				menuPage.tenantSelector, menu);
			assertTrue(isCurrTenantUpdated);

			isCurrTenantUpdated = validateTenantSwitch(defaultTenantVariantId, tempTenantVariantId,
				menuPage.tenantSelector, menu);
			assertTrue(isCurrTenantUpdated);
		}
	}

	/**
	 * checks that the selected tenant element changes its value when the tenant
	 * switches between the default tenant and the temporary tenant and that this change persists
	 * through navigating to another configuration menu and back
	 * tries a variety of combinations of menus to swap back and forth with,
	 * following two rules: each menu is used at least once as either the first or
	 * second menu, both when switching from default tenant to temporary tenant and when
	 * switching from temporary tenant to default tenant no pair of menus is swapped between in
	 * multiple cases- that is, this doesn't happen: one case where menu A switches
	 * to menu B and back, and then also another case where menu B switches to menu
	 * A and back
	 *
	 * JIRA ticket CARIBA-246
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void tenantSelectorSelectedTenantUpdatePersistsBetweenMenusTest( )
	{
		AribaConnSignOnPage signOnPage = testStartPage;
		AribaConnHomePage homePage = connUtil.signInToConfig(signOnPage);

		signOnPage.navPanel.clickNavOption(AribaConnNavOption.CONFIGURATION_MENU);

		AribaConnNavConfigurationMenuOption[] menus = AribaConnNavConfigurationMenuOption.values();
		AribaConnMenuBasePage menuPage = connUtil.openConfigMenu(homePage, menus[0]);

		assertTrue(validateTenantSwitchPersists(defaultTenantVariantId, tempTenantVariantId, menuPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS,
			AribaConnNavConfigurationMenuOption.ARIBA_EXTERNAL_TAX_TYPES_MAINTENANCE));

		assertTrue(validateTenantSwitchPersists(defaultTenantVariantId, tempTenantVariantId, menuPage,
			AribaConnNavConfigurationMenuOption.CONFIGURE_CONNECTION_PROPERTIES,
			AribaConnNavConfigurationMenuOption.MODIFY_TAX_RULES));

		assertTrue(validateTenantSwitchPersists(defaultTenantVariantId, tempTenantVariantId, menuPage,
			AribaConnNavConfigurationMenuOption.ARIBA_COMPONENT_TAX_TYPES_MAINTENANCE,
			AribaConnNavConfigurationMenuOption.CONFIGURE_CONNECTION_PROPERTIES));

		assertTrue(validateTenantSwitchPersists(defaultTenantVariantId, tempTenantVariantId, menuPage,
			AribaConnNavConfigurationMenuOption.MODIFY_ACCOUNT_FIELD_MAPPING,
			AribaConnNavConfigurationMenuOption.VIEW_LOGGED_XML_MESSAGES));

		assertTrue(validateTenantSwitchPersists(defaultTenantVariantId, tempTenantVariantId, menuPage,
			AribaConnNavConfigurationMenuOption.VIEW_LOGGED_XML_MESSAGES,
			AribaConnNavConfigurationMenuOption.MODIFY_CUSTOM_FIELD_MAPPING));

		assertTrue(validateTenantSwitchPersists(defaultTenantVariantId, tempTenantVariantId, menuPage,
			AribaConnNavConfigurationMenuOption.ARIBA_EXTERNAL_TAX_TYPES_MAINTENANCE,
			AribaConnNavConfigurationMenuOption.ARIBA_COMPONENT_TAX_TYPES_MAINTENANCE));

		assertTrue(validateTenantSwitchPersists(defaultTenantVariantId, tempTenantVariantId, menuPage,
			AribaConnNavConfigurationMenuOption.MODIFY_CUSTOM_FIELD_MAPPING,
			AribaConnNavConfigurationMenuOption.MODIFY_ACCOUNT_FIELD_MAPPING));

		assertTrue(validateTenantSwitchPersists(defaultTenantVariantId, tempTenantVariantId, menuPage,
			AribaConnNavConfigurationMenuOption.MODIFY_TAX_RULES,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS));

		assertTrue(validateTenantSwitchPersists(tempTenantVariantId, defaultTenantVariantId, menuPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS,
			AribaConnNavConfigurationMenuOption.VIEW_LOGGED_XML_MESSAGES));

		assertTrue(validateTenantSwitchPersists(tempTenantVariantId, defaultTenantVariantId, menuPage,
			AribaConnNavConfigurationMenuOption.ARIBA_EXTERNAL_TAX_TYPES_MAINTENANCE,
			AribaConnNavConfigurationMenuOption.MODIFY_ACCOUNT_FIELD_MAPPING));

		assertTrue(validateTenantSwitchPersists(tempTenantVariantId, defaultTenantVariantId, menuPage,
			AribaConnNavConfigurationMenuOption.CONFIGURE_CONNECTION_PROPERTIES,
			AribaConnNavConfigurationMenuOption.MODIFY_CUSTOM_FIELD_MAPPING));

		assertTrue(validateTenantSwitchPersists(tempTenantVariantId, defaultTenantVariantId, menuPage,
			AribaConnNavConfigurationMenuOption.MODIFY_ACCOUNT_FIELD_MAPPING,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS));

		assertTrue(validateTenantSwitchPersists(tempTenantVariantId, defaultTenantVariantId, menuPage,
			AribaConnNavConfigurationMenuOption.VIEW_LOGGED_XML_MESSAGES,
			AribaConnNavConfigurationMenuOption.ARIBA_COMPONENT_TAX_TYPES_MAINTENANCE));

		assertTrue(validateTenantSwitchPersists(tempTenantVariantId, defaultTenantVariantId, menuPage,
			AribaConnNavConfigurationMenuOption.MODIFY_CUSTOM_FIELD_MAPPING,
			AribaConnNavConfigurationMenuOption.ARIBA_EXTERNAL_TAX_TYPES_MAINTENANCE));

		assertTrue(validateTenantSwitchPersists(tempTenantVariantId, defaultTenantVariantId, menuPage,
			AribaConnNavConfigurationMenuOption.MODIFY_TAX_RULES,
			AribaConnNavConfigurationMenuOption.CONFIGURE_CONNECTION_PROPERTIES));

		assertTrue(validateTenantSwitchPersists(tempTenantVariantId, defaultTenantVariantId, menuPage,
			AribaConnNavConfigurationMenuOption.ARIBA_COMPONENT_TAX_TYPES_MAINTENANCE,
			AribaConnNavConfigurationMenuOption.MODIFY_TAX_RULES));
	}

	/**
	 * This checks that the right tenant is stored by the webpage as the selected
	 * tenant by comparing their name 'value's
	 *
	 * @param expectedTenantId the variant id of the tenant which the webpage should
	 *                         have stored as the selected tenant
	 * @param tenantField      the component that accesses the tenant-changing dropdown menu
	 * @param menu             the configuration menu that is being accessed as the webpage's
	 *                         stored value for selected tenant is validated
	 *
	 * @return whether the webpage has stored the right tenant as the selected
	 * tenant
	 *
	 * @author ssalisbury
	 */
	protected boolean validateSelectedTenant( final String expectedTenantId, final AribaConnTenantSelector tenantField,
		final AribaConnNavConfigurationMenuOption menu )
	{
		boolean isCurrTenantCorrect = false;

		String currTenantId = tenantField.getSelectedTenantId();

		isCurrTenantCorrect = expectedTenantId.equals(currTenantId);

		if ( !isCurrTenantCorrect )
		{
			String wrongSelectedTenant = String.format("On %s menu, Tenant is %s but should be %s", menu.toString(),
				currTenantId, expectedTenantId);
			VertexLogger.log(wrongSelectedTenant, VertexLogLevel.WARN);
		}
		return isCurrTenantCorrect;
	}

	/**
	 * this selects a new tenant and then validates that that new tenant is stored
	 * by the webpage as the selected tenant
	 *
	 * @param oldTenantId the tenant which is initially selected
	 * @param newTenantId the tenant that should be switched to as the selected tenant
	 * @param tenantField the component that accesses the tenant-changing dropdown menu
	 * @param menu        the configuration menu that is being accessed as the webpage's
	 *                    stored value for selected tenant is validated
	 *
	 * @return whether the webpage has stored the right tenant as the selected
	 * tenant
	 *
	 * @author ssalisbury
	 */
	protected boolean validateTenantSwitch( final String oldTenantId, final String newTenantId,
		final AribaConnTenantSelector tenantField, final AribaConnNavConfigurationMenuOption menu )
	{
		boolean tenantSwitchesOldToNew = false;

		boolean oldCurrTenantCorrect = validateSelectedTenant(oldTenantId, tenantField, menu);
		tenantField.selectTenant(newTenantId);

		boolean newCurrTenantCorrect = validateSelectedTenant(newTenantId, tenantField, menu);

		tenantSwitchesOldToNew = oldCurrTenantCorrect && newCurrTenantCorrect;
		if ( !tenantSwitchesOldToNew )
		{
			String failTenantSwitchMessage = String.format(
				"Failure when attempting to switch from %s tenant to %s tenant on configuration menu %s, " +
				"the %s tenant was selected before the switch and the %s tenant was selected after the switch",
				oldTenantId, newTenantId, menu.toString(), oldCurrTenantCorrect ? "correct" : "incorrect",
				newCurrTenantCorrect ? "correct" : "incorrect");
			VertexLogger.log(failTenantSwitchMessage);
		}

		return tenantSwitchesOldToNew;
	}

	/**
	 * verifies that updating the tenant works and persists between menus
	 * expected behavior that's being checked: when the tenant updates to the new
	 * value on one menu, the selected tenant stored on that first menu goes from
	 * the old tenant to the new tenant. Furthermore, when the test navigates to a
	 * second menu, the selected tenant stored in the second menu is also the new
	 * tenant, and when the test navigates back from the second menu to the first
	 * menu, the selected tenant on the first menu is still the new tenant
	 *
	 * @param oldTenantId    the tenant that is selected at the start of the test Note- this
	 *                       should not be the same as newTenant or the test may return false
	 *                       positives
	 * @param newTenantId    the tenant that is switched to in order to check that the new
	 *                       tenant selection persists through the other configuration menus
	 * @param configSitePage just some webpage on the configuration site, only used to load the
	 *                       first configuration menu
	 * @param firstMenu      the first configuration menu being used, where the old tenant is
	 *                       validated and the new tenant is selected. Also, at the end this
	 *                       menu tests whether the tenant selection on a menu remains the same
	 *                       when you navigate to another menu and then back to the first menu
	 *                       again
	 * @param secondMenu     the configuration menu that is switched to in order to check that
	 *                       the tenant selection persists between menus
	 *
	 * @return whether updating the tenant works and persists between menus
	 *
	 * @author ssalisbury
	 */
	protected boolean validateTenantSwitchPersists( final String oldTenantId, final String newTenantId,
		final AribaConnBasePage configSitePage, final AribaConnNavConfigurationMenuOption firstMenu,
		final AribaConnNavConfigurationMenuOption secondMenu )
	{
		boolean tenantUpdatePersists = false;

		AribaConnMenuBasePage firstMenuPage = connUtil.openConfigMenu(configSitePage, firstMenu);

		firstMenuPage.tenantSelector.selectTenant(oldTenantId);

		boolean firstMenuTenantSwitchesCorrect = validateTenantSwitch(oldTenantId, newTenantId,
			firstMenuPage.tenantSelector, firstMenu);

		AribaConnMenuBasePage secondMenuPage = connUtil.openConfigMenu(firstMenuPage, secondMenu);

		boolean secondMenuTenantCorrect = validateSelectedTenant(newTenantId, secondMenuPage.tenantSelector,
			secondMenu);

		firstMenuPage = connUtil.openConfigMenu(secondMenuPage, firstMenu);

		boolean firstMenuTenantPersistsCorrect = validateSelectedTenant(newTenantId, firstMenuPage.tenantSelector,
			firstMenu);

		tenantUpdatePersists = firstMenuTenantSwitchesCorrect && secondMenuTenantCorrect &&
							   firstMenuTenantPersistsCorrect;
		if ( !tenantUpdatePersists )
		{
			String failTenantSwitchPersistMessage = String.format(
				"Failure attempting to switch from %s tenant to %s tenant on configuration menu %s, verify that the second tenant is selected on configuration menu %s as well, " +
				"and then verify the selected tenant on that first menu: The selected tenant %s update correctly to a " +
				"new value on that first menu. Then the selected tenant on the second menu %s properly hold that " +
				"new value. Finally, the selected tenant on the first menu %s still hold the new value when " +
				"the test navigated back to the first menu from the second menu", oldTenantId, newTenantId,
				firstMenu.toString(), secondMenu.toString(), firstMenuTenantSwitchesCorrect ? "did" : "didn't",
				secondMenuTenantCorrect ? "did" : "didn't", firstMenuTenantPersistsCorrect ? "did" : "didn't");
			VertexLogger.log(failTenantSwitchPersistMessage);
		}

		return tenantUpdatePersists;
	}

	/**
	 * checks whether the tenant selector is displayed&enabled on the current
	 * configuration menu
	 *
	 * @param menuPage the currently loaded configuration menu
	 * @param menu     description of the configuration menu which should be loaded
	 *
	 * @return whether the tenant selector is displayed&enabled on the current
	 * configuration menu
	 *
	 * @author ssalisbury
	 */
	protected boolean validateTenantSelectorAccessible( final AribaConnMenuBasePage menuPage,
		final AribaConnNavConfigurationMenuOption menu )
	{
		boolean selectorAccessible = menuPage.tenantSelector.isTenantSelectorEnabled();
		if ( !selectorAccessible )
		{
			String missingTenantSelectorMessage = String.format("Tenant selector not loaded on %s menu",
				menu.toString());
			VertexLogger.log(missingTenantSelectorMessage, VertexLogLevel.ERROR);
		}
		return selectorAccessible;
	}
}
