package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceHomePage;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Version Tests implementation
 *
 * @author Saidulu kodadala ssalisbury
 */
@Listeners(TestRerunListener.class)
public class DFinanceVersionTests extends DFinanceBaseTest
{
	protected final String vertexPackageName = "Vertex";

	/**
	 * JIRA ticket CD365-240
	 *
	 * Version validation f`or Dynamics365 Finance & Operations
	 */
	@Test(groups = { "FO_smoke", "FO_General_regression", "FO_ALL", "FO_trial" }, retryAnalyzer = TestRerun.class)
	public void d365VertexConnectorVersionTest( )
	{
		//regex- the version should contain a sequence of 4 numbers separated by periods
		final String connectorVersionFormat = ".*\\d+\\.\\d+\\.\\d+\\.\\d+.*";

		DFinanceHomePage homePage = new DFinanceHomePage(driver);

		//================script implementation========================		
		homePage.headerBar.clickOnHelpAndSupport();
		homePage.headerBar.clickOnAbout();

		String installedProductVersion = homePage.aboutDialog.getInstalledProductVersion();
		assertTrue(installedProductVersion != null, "D365 Finance product version not available");
		String productVersionMessage = String.format("D365 Finance product version: %s", installedProductVersion);
		VertexLogger.log(productVersionMessage);

		String installedPlatformVersion = homePage.aboutDialog.getInstalledPlatformVersion();
		assertTrue(installedPlatformVersion != null, "D365 Finance platform version not available");
		String platformVersionMessage = String.format("D365 Finance platform version: %s", installedPlatformVersion);
		VertexLogger.log(platformVersionMessage);

		homePage.aboutDialog.moveToVersionTab();

		String vertexConnectorVersion = homePage.aboutDialog.getVertexVersion();
		assertTrue(vertexConnectorVersion != null, "Vertex connector version not available");
		String connectorVersionMessage = String.format("Vertex connector version: %s", vertexConnectorVersion);

		VertexLogger.log(connectorVersionMessage);

		assertTrue(vertexConnectorVersion.matches(connectorVersionFormat));
	}
}
