package com.vertex.quality.connectors.concur.tests.healthcheck;

import com.vertex.quality.connectors.concur.enums.ConcurHeaderTab;
import com.vertex.quality.connectors.concur.pages.misc.ConcurHomePage;
import com.vertex.quality.connectors.concur.pages.panelPages.ConcurAppCenterPage;
import com.vertex.quality.connectors.concur.tests.base.ConcurUIBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * tests functionality of home page
 *
 * @author alewis
 */
@Test(groups = { "concur_smoke" })
public class ConcurHealthcheckTests extends ConcurUIBaseTest
{
	/**
	 * tests whether vertex card exist in App Center page
	 */
	@Test(groups = { "config" })
	public void vertexConnectorAppPresentTest( )
	{
		final String expectedText = "Vertex Indirect Tax";
		ConcurAppCenterPage appCenterPage = navigateToHeaderTabPage(ConcurHeaderTab.APP_CENTER, testStartPage);
		String cardText = appCenterPage.findCard(expectedText);

		assertEquals(expectedText, cardText);
	}

	/**
	 * tests whether tests can sign into concur site
	 */
	@Test()
	public void loginTest( )
	{
		ConcurHomePage homePage = signIntoConcur(testStartPage);
		boolean isLoggedIn = homePage.checkIfLoggedIn();

		assertTrue(isLoggedIn);
	}
}
