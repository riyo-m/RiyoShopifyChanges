package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudHomePage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Smoke test to verify application is accessible
 *
 * @author cgajes
 */
public class OracleCloudLoginTests extends OracleCloudBaseTest
{

	protected final String loggedInHeaderText = "Welcome";
	protected final String loggedInHeaderTextAlt = "Oracle Applications";

	/**
	 * Tests whether login is successful
	 */
	@Test(groups = { "oerpc_smoke" })
	public void oracleCloudLoginTest( )
	{
		OracleCloudHomePage homePage = signInToHomePage();

		String homePageBannerText = homePage.checkJustLoggedIn();

		assertTrue(loggedInHeaderText.equals(homePageBannerText) || loggedInHeaderTextAlt.equals(homePageBannerText));
	}
}