package com.vertex.quality.connectors.magento.admin.tests.MFTF3;

import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;


/**
 * Test case for changing the default tax class for products
 *
 * Bundle-1287
 *
 * @author alewis
 */
public class checkDefaultTaxClassForProdTests extends MagentoAdminBaseTest
{

	@Test()
	public void checkDefaultTaxClassForProductTest( )
	{
		M2AdminNewProductPage newProductPage = navigateToNewProductPage();
		String taxClassDefaultText = newProductPage.checkTestClassIsDefault();

		assertTrue(taxClassDefaultText.contains("Test Class"));
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
	 * tests whether navigation can reach the M2AdminProductsPage
	 *
	 * @return the Products Page
	 */
	protected M2AdminNewTaxRulePage navigateToTaxRules( )
	{
		M2AdminHomepage homepage = signInToAdminHomepage();

		homepage.navPanel.clickCatalogButton();

		M2AdminProductsPage productsPage = homepage.navPanel.clickProductsButton();

		M2AdminNewProductPage newProductPage = productsPage.clickAddProductButton();

		newProductPage.navPanel.clickStoresButton();

		M2AdminTaxRulesPage taxRulesPage = newProductPage.navPanel.clickTaxRulesButton();

		M2AdminNewTaxRulePage addNewTaxRulePage = taxRulesPage.clickAddNewTaxRule();

		addNewTaxRulePage.clickAdditionalSettings();

		addNewTaxRulePage.clickAddNewTaxClass("Test Class");

		return addNewTaxRulePage;
	}

	/**
	 * tests whether navigation can reach the M2AdminSalesTaxConfigPage
	 *
	 * @return Tax Settings Page
	 */
	protected M2AdminNewProductPage navigateToNewProductPage( )
	{
		M2AdminNewTaxRulePage newTaxRulePage = navigateToTaxRules();

		newTaxRulePage.navPanel.clickStoresButton();

		M2AdminConfigPage configPage = newTaxRulePage.navPanel.clickConfigButton();

		configPage.clickSalesTab();
		M2AdminSalesTaxConfigPage taxSettingsPage = configPage.clickTaxTab();

		taxSettingsPage.changeDefaultTaxClassForProduct("Test Class");

		taxSettingsPage.navPanel.clickCatalogButton();

		M2AdminProductsPage productsPage = taxSettingsPage.navPanel.clickProductsButton();

		M2AdminNewProductPage newProductPage = productsPage.clickAddProductButton();

		return newProductPage;
	}
}