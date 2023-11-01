package com.vertex.quality.connectors.magento.admin.tests.MFTF3;

import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Test case for changing the default tax class for customers
 * Bundle-1289
 *
 * @author alewis
 */
public class CheckDefaultTaxClassForCustomerTests extends MagentoAdminBaseTest
{
	final String taxClassName = "Tax Class Test";

	/**
	 * Tests creating a new tax class and setting it as the
	 * default tax class for new customer groups
	 * Then, deletes the new tax class created for the test
	 */
	@Test()
	public void checkDefaultTaxClassForCustomerOptionTest( )
	{
		M2AdminCacheMgmt cacheMgmt = flushCache();

		assertTrue(cacheMgmt.navPanel.isCustomersButtonVisible());

		cacheMgmt.navPanel.clickCustomersButton();

		M2AdminCustomerGroupsPage groupsPage = cacheMgmt.navPanel.clickCustomerGroupsButton();

		M2AdminAddNewCustomerGroupPage addNewCustomerGroupPage = groupsPage.clickAddNewCustomerGroupButton();

		String defaultSelectedClass = addNewCustomerGroupPage.checkSelectedTaxClass();

		assertTrue(taxClassName.equals(defaultSelectedClass));

		addNewCustomerGroupPage.navPanel.clickStoresButton();

		M2AdminTaxRulesPage taxRulesPage = groupsPage.navPanel.clickTaxRulesButton();

		M2AdminNewTaxRulePage newTaxRulePage = taxRulesPage.clickAddNewTaxRule();

		newTaxRulePage.clickAdditionalSettings();

		newTaxRulePage.deleteCustomerTaxClass(taxClassName);
	}

	/**
	 * loads and signs into this configuration site
	 *
	 * @return a representation of the page that loads immediately after
	 * successfully signing into this configuration site
	 */
	protected M2AdminHomepage signInToAdminHomepage( )
	{
		driver.get(url);

		M2AdminSignOnPage signOnPage = new M2AdminSignOnPage(driver);

		signOnPage.enterUsername(username);

		signOnPage.enterPassword(password);

		M2AdminHomepage homepage = signOnPage.login();

		return homepage;
	}

	/**
	 * navigates to the customer groups page
	 *
	 * @return a representation of the customer groups page
	 */
	protected M2AdminCustomerGroupsPage navigateToCustomerGroupsPage( )
	{
		M2AdminHomepage homepage = signInToAdminHomepage();

		assertTrue(homepage.navPanel.isCustomersButtonVisible());

		homepage.navPanel.clickCustomersButton();

		M2AdminCustomerGroupsPage groupsPage = homepage.navPanel.clickCustomerGroupsButton();

		return groupsPage;
	}

	/**
	 * checks the default tax class for new customer groups,
	 * which should be "Retail Customer"
	 *
	 * @return a representation of the customer groups page
	 */
	protected M2AdminCustomerGroupsPage checkInitialDefaultTaxClass( )
	{
		String expectedDefaultName = "Retail Customer";

		M2AdminCustomerGroupsPage groupsPage = navigateToCustomerGroupsPage();
		M2AdminAddNewCustomerGroupPage addNewCustomerGroupPage = groupsPage.clickAddNewCustomerGroupButton();

		String defaultName = addNewCustomerGroupPage.checkSelectedTaxClass();

		assertTrue(expectedDefaultName.equals(defaultName));

		return groupsPage;
	}

	/**
	 * navigates to the tax rules page
	 *
	 * @return a representation of the tax rules page
	 */
	protected M2AdminTaxRulesPage navigateToTaxRules( )
	{
		M2AdminCustomerGroupsPage groupsPage = checkInitialDefaultTaxClass();

		assertTrue(groupsPage.navPanel.isStoresButtonVisible());

		groupsPage.navPanel.clickStoresButton();

		M2AdminTaxRulesPage taxRulesPage = groupsPage.navPanel.clickTaxRulesButton();

		return taxRulesPage;
	}

	/**
	 * navigate to the add new tax rules page
	 * and add a new tax rule
	 *
	 * @return a representation of the new tax rules page
	 */
	protected M2AdminNewTaxRulePage addNewTaxRule( )
	{
		M2AdminTaxRulesPage taxRulesPage = navigateToTaxRules();
		M2AdminNewTaxRulePage newTaxRulePage = taxRulesPage.clickAddNewTaxRule();

		newTaxRulePage.clickAdditionalSettings();
		assertTrue(newTaxRulePage.isAdditionalSettingsExpanded());

		newTaxRulePage.clickAddNewCustomerTaxClass();

		newTaxRulePage.inputTaxClassName(taxClassName);

		newTaxRulePage.confirmNewTaxClass();

		return newTaxRulePage;
	}

	/**
	 * navigate to the configuration page
	 *
	 * @return a representation the configuration page
	 */
	protected M2AdminConfigPage navigateToConfig( )
	{
		M2AdminNewTaxRulePage newTaxRulePage = addNewTaxRule();

		assertTrue(newTaxRulePage.navPanel.isStoresButtonVisible());

		newTaxRulePage.navPanel.clickStoresButton();

		M2AdminConfigPage configPage = newTaxRulePage.navPanel.clickConfigButton();

		return configPage;
	}

	/**
	 * Tests navigation to the Vertex settings
	 *
	 * @return the Vertex settings page
	 */
	protected M2AdminSalesTaxConfigPage navigateToVertexSettings( )
	{
		M2AdminConfigPage configPage = navigateToConfig();
		assertTrue(configPage.isSalesTabVisible());
		configPage.clickSalesTab();
		assertTrue(configPage.isTaxTabVisible());

		M2AdminSalesTaxConfigPage vertexSettings = configPage.clickTaxTab();
		return vertexSettings;
	}

	/**
	 * Configure the sales tax settings to set the newly created tax class
	 * as the default
	 *
	 * @return the Vertex settings page
	 */
	protected M2AdminSalesTaxConfigPage configureSettings( )
	{
		M2AdminSalesTaxConfigPage vertexSettings = navigateToVertexSettings();

		vertexSettings.changeDefaultCustomerTaxClass(taxClassName);

		return vertexSettings;
	}

	/**
	 * Navigate to the cache management page and
	 * flush the cache
	 *
	 * @return the cache management page
	 */
	protected M2AdminCacheMgmt flushCache( )
	{
		String success = "The Magento cache storage has been flushed.";

		M2AdminSalesTaxConfigPage vertexSettings = configureSettings();

		vertexSettings.navPanel.clickSystemButton();

		M2AdminCacheMgmt cacheMgmtPage = vertexSettings.navPanel.clickCacheManagementButton();

		cacheMgmtPage.checkInvalidatedCacheTypes();

		String message = cacheMgmtPage.clickFlushMagentoCacheButton();

		assertTrue(success.equals(message));

		return cacheMgmtPage;
	}
}
