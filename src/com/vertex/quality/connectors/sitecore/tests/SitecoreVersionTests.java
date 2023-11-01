package com.vertex.quality.connectors.sitecore.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.sitecore.pages.SitecoreAdminHomePage;
import com.vertex.quality.connectors.sitecore.pages.SitecoreVertexOSeriesConnectorPage;
import com.vertex.quality.connectors.sitecore.tests.base.SitecoreBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests sitecore version
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreVersionTests extends SitecoreBaseTest
{
	/**
	 * Tests sitecore version
	 */
	@Test(groups = "sitecore")
	public void vertexConnectorVersionTest( )
	{

		SitecoreAdminHomePage homePage;
		SitecoreVertexOSeriesConnectorPage oSeriesPage;

		// login as admin user into Sitecore admin site
		homePage = logInAsAdminUser();

		// navigate to Vertex O Series Connector Page
		oSeriesPage = homePage.navigateToVertexOSeriesConnectorPage();

		// get Vertex Connector version details
		String coreVersion = oSeriesPage.getCoreVersion();
		assertTrue(coreVersion != null, "Core Version not available");
		String coreVersionMessage = String.format("Core Version: %s", coreVersion);
		VertexLogger.log(coreVersionMessage, getClass());

		String adapterVersion = oSeriesPage.getAdapterVersion();
		assertTrue(adapterVersion != null, "Adapter Version not available");
		String adapterVersionMessage = String.format("Adapter Version: %s", adapterVersion);
		VertexLogger.log(adapterVersionMessage, getClass());
	}
}
