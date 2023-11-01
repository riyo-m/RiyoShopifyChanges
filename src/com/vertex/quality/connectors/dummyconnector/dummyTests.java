package com.vertex.quality.connectors.dummyconnector;

import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminConfigPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminHomepage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminSignOnPage;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class dummyTests extends VertexUIBaseTest
{

	protected String url = MagentoData.ADMIN_SIGN_ON_URL.data;
	protected String username = MagentoData.ADMIN_USERNAME.data;
	protected String password = MagentoData.ADMIN_PASSWORD.data;
	protected String loggedInHeaderText = "Dashboard";
	protected String configTitleText = "Configuration / Settings / Stores / Magento Admin";

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
	 * tests whether navigation can reach the configPage
	 *
	 * @return the configuration page
	 */
	protected M2AdminConfigPage navigateToConfig( )
	{
		M2AdminHomepage homePage = signInToAdminHomepage();

		assertTrue(homePage.navPanel.isStoresButtonVisible());

		homePage.navPanel.clickStoresButton();

		M2AdminConfigPage configPage = homePage.navPanel.clickConfigButton();

		String configPageTitle = configPage.getPageTitle();

		assertTrue(configTitleText.equals(configPageTitle));

		return configPage;
	}

	@Test(groups = { "dummyTestType" })
	public void dummyTestOne( )
	{
		M2AdminHomepage homePage = signInToAdminHomepage();

		String homePageBannerText = homePage.checkJustLoggedIn();

		assertTrue(loggedInHeaderText.equals(homePageBannerText));
	}

	@Test(groups = { "dummyTestType", "learning" })
	public void dummyTestTwo( )
	{
		M2AdminConfigPage configPage = navigateToConfig();

		String configPageBannerText = configPage.getPageTitle();

		assertTrue(configTitleText.equals(configPageBannerText));
	}
}