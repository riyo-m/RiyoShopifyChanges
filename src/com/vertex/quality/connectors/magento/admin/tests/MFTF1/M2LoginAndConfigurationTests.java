package com.vertex.quality.connectors.magento.admin.tests.MFTF1;

import com.vertex.quality.connectors.magento.admin.pages.M2AdminHomepage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminPartnersPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminSalesTaxConfigPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * tests that login and configurations are correct on Magento Admin
 *
 * @author alewis
 */
public class M2LoginAndConfigurationTests extends MagentoAdminBaseTest
{
	protected String loggedInHeaderText = "Dashboard";
	protected String vertexAPIStatus = "VALID";
	protected String vertexString = "Vertex";

	/**
	 * tests whether tests can so much as properly sign in to this
	 * configuration site
	 */
	@Test(groups = { "magentoSmoke" })
	public void adminLoginTest( )
	{
		M2AdminHomepage homePage = signInToAdminHomepage();

		String homePageBannerText = homePage.checkJustLoggedIn();

		assertTrue(loggedInHeaderText.equals(homePageBannerText));
	}

	/**
	 * tests whether Vertex API Status is marked as Valid
	 */
	@Test(groups = { "magentoSmoke"})
	public void isVertexAPIStatusValidTest( )
	{
		M2AdminSalesTaxConfigPage taxSettingsPage = navigateToSalesTaxConfig();
		taxSettingsPage.clickConnectionSettingsTab();
		String taxStatusString = taxSettingsPage.getVertexAPIStatus();
		assertTrue(vertexAPIStatus.equals(taxStatusString));
	}

	/**
	 * Checks to see if Vertex is listed as Partner in Platinum Partners Page
	 */
	@Test(groups = { "magentoSmoke"})
	public void isVertexListedAsPartnerTest( )
	{
		M2AdminPartnersPage partnersPage = navigateToPartnersPage();
		String vertexPartner = partnersPage.findVertex();

		assertTrue(vertexString.equals(vertexPartner));
	}
}
