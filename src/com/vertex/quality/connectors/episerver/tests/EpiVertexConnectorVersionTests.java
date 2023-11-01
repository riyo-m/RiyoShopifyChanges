package com.vertex.quality.connectors.episerver.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.VersionType;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiOseriesPage;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class EpiVertexConnectorVersionTests extends EpiBaseTest
{
	@Test(groups = { "smoke", "version" })
	public void episerverVertexConnectorVersionTest( )
	{
		EpiAdminHomePage admindashboardpage;
		EpiOseriesPage oSeriespage;

		// login as Admin user into EpiserverAdmin Page
		admindashboardpage = logInAsAdminUser();
		admindashboardpage.validateDashBoardDefaultPage();

		// navigate to Vertex O Series Page
		admindashboardpage.clickOnMainMenu("CMS");
		admindashboardpage.clickOnSubMenu("Admin");
		admindashboardpage.selectTabInCmsAdminPage("Admin");
		oSeriespage = admindashboardpage.navigateToOseriespage();

		// Retrieve Core Version
		String coreVersion = oSeriespage.getVersion(VersionType.Core_Version.text);
		assertTrue(coreVersion != null, "Core Version 'not available'/'Blank'");
		VertexLogger.log(String.format("Vertex Connector Core Version is: %s", coreVersion));

		// Retrieve Adapter Version
		String adapterVersion = oSeriespage.getVersion(VersionType.Adapter_Version.text);
		assertTrue(adapterVersion != null, "Adapter Version 'not available'/'Blank'");
		VertexLogger.log(String.format("Vertex Connector Adapter Version is: %s", adapterVersion));

		// Logout from Admin Portal
		admindashboardpage.adminlogout();
	}
}
