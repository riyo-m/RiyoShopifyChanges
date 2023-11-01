package com.vertex.quality.connectors.ariba.connector.tests.misc;

import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavOption;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnHomePage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnSignOnPage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnSystemStatusPage;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaConnBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Verifies the functionality of the System Status page
 *
 * @author dgorecki
 */

public class AribaConnHealthCheckTests extends AribaConnBaseTest
{
	protected final String healthyStatusText = "OK";
	//specifies that the default O-series instance's list of serviced tenants starts with the 'default' tenant
	protected final String defaultOseriesInstanceLabelStart = "https://oseries9-final.vertexconnectors.com/vertex-ws/";

	/**
	 * JIRA ticket CARIBA-481
	 * checks that the Clear Caches button can be seen and clicked on by the user
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void clearCachesButtonAccessibleTest( )
	{
		AribaConnSystemStatusPage statusPage = validateStatusPagePresentAndLoad(testStartPage);

		boolean isClearCachesButtonPresent = statusPage.isClearCachesButtonEnabled();
		assertTrue(isClearCachesButtonPresent);

		statusPage.clickClearCachesButton();
	}

	/**
	 * JIRA ticket CARIBA-241
	 * checks that the connector has a functioning connection to its database
	 *
	 * @author dgorecki ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void dbConfigTest( )
	{
		AribaConnSystemStatusPage statusPage = validateStatusPagePresentAndLoad(testStartPage);

		boolean isDBStatusPresent = statusPage.isConfigDatabaseStatusDisplayed();
		assertTrue(isDBStatusPresent);


		assertTrue(statusPage.getConfigDatabaseStatus());
	}

	/**
	 * JIRA ticket CARIBA-241
	 * checks that the connector has a functioning connection to its O-Series instance for some set of tenants
	 *
	 * @author dgorecki ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void oseriesConnectionTest( )
	{
		AribaConnSystemStatusPage statusPage = validateStatusPagePresentAndLoad(testStartPage);

		assertTrue(statusPage.getOSeriesConnection(defaultOseriesInstanceLabelStart));
	}

	/**
	 * checks that the System Status page can be navigated to and loaded,
	 * and in the process loads that page
	 *
	 * @param startPage the sign on page for the site
	 *
	 * @return a representation of the System Status page
	 */
	protected AribaConnSystemStatusPage validateStatusPagePresentAndLoad( final AribaConnSignOnPage startPage )
	{
		AribaConnSystemStatusPage statusPage = null;

		AribaConnHomePage homePage = connUtil.signInToConfig(startPage);

		boolean isVertexServicesPagePresent = homePage.navPanel.isNavOptionEnabled(AribaConnNavOption.SYSTEM_STATUS);
		assertTrue(isVertexServicesPagePresent);

		statusPage = homePage.navPanel.clickNavOption(AribaConnNavOption.SYSTEM_STATUS);
		boolean isPageLoaded = statusPage.isCurrentPage();
		assertTrue(isPageLoaded);

		return statusPage;
	}
}
