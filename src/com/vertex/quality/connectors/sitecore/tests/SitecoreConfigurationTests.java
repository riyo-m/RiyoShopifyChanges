package com.vertex.quality.connectors.sitecore.tests;

import com.vertex.quality.connectors.sitecore.common.enums.SitecoreStatus;
import com.vertex.quality.connectors.sitecore.pages.SitecoreAdminHomePage;
import com.vertex.quality.connectors.sitecore.pages.SitecoreVertexOSeriesConnectorPage;
import com.vertex.quality.connectors.sitecore.tests.base.SitecoreBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * represents sitecore configuration tests
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreConfigurationTests extends SitecoreBaseTest
{

	/**
	 * test connector configuration
	 */
	@Test(groups = "sitecore")
	public void vertexConnectorInitialConfigTest( )
	{

		SitecoreAdminHomePage homePage;
		SitecoreVertexOSeriesConnectorPage configPage;

		// login as admin user into Sitecore admin site
		homePage = logInAsAdminUser();

		// navigate to Vertex O Series Connector Page
		configPage = homePage.navigateToVertexOSeriesConnectorPage();

		String expectedStatus = SitecoreStatus.GOOD.getText();
		// get the current status and validate with expected
		String actualStatus = configPage.getVertexOSeriesStatus();

		boolean result = actualStatus
			.toUpperCase()
			.contains(expectedStatus.toUpperCase());
		assertTrue(result, "Vertex O Series Connector status is not valid/good");
	}
}
