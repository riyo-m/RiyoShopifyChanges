package com.vertex.quality.connectors.episerver.tests;

import com.vertex.quality.connectors.episerver.common.enums.Status;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiOseriesPage;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class EpiRunHealthCheckTests extends EpiBaseTest
{
	@Test(groups = { "smoke" })
	public void episerverVertexConnectorHealthCheckTest( )
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

		// retrieve Vertex O Series Connector Status and Validate
		String expectedStatus = Status.GOOD.text;
		oSeriespage.validateConnectorTooltip("Vertex O Series status");
		oSeriespage.validateVertexOSeriesConnectorStatus();
		oSeriespage.clickRefreshStatusButton();
		oSeriespage.validateConnectorTooltip("Vertex O Series status");
		String actualstatus = oSeriespage.getVertexOseriesConnectorStatus();
		boolean result = actualstatus
			.toUpperCase()
			.contains(expectedStatus.toUpperCase());
		assertTrue(result, "Vertex O Series Connector status is not valid/good");
	}
}
