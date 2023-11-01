package com.vertex.quality.connectors.ariba.connector.tests.misc;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavOption;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnHomePage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnVertexServicesPage;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaConnBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Verifies the functionality of the Services page
 *
 * @author ssalisbury
 */
@SuppressWarnings("Duplicates")

public class AribaConnVersionTests extends AribaConnBaseTest
{
	/**
	 * tests whether the Vertex Services page on this configuration site
	 * displays the correct version number for the connector
	 *
	 * TODO JIRA lacks a story for the UI of the Services page
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void connectorVersionTest( )
	{
		AribaConnNavOption navOption = AribaConnNavOption.VERTEX_SERVICES;

		//checks that the version has 3 1 or 2 digit numbers separated by 2 periods
		final String connectorVersionFormat = "\\d\\d?\\.\\d\\d?\\.\\d\\d??";

		boolean isVertexServicesPagePresent = testStartPage.navPanel.isNavOptionEnabled(navOption);
		assertTrue(isVertexServicesPagePresent);

		AribaConnVertexServicesPage servicesPage = testStartPage.navPanel.clickNavOption(navOption);
		boolean isPageLoaded = servicesPage.isCurrentPage();
		assertTrue(isPageLoaded);

		boolean isVersionPresent = servicesPage.isConnectorVersionDisplayed();
		assertTrue(isVersionPresent);

		String version = servicesPage.getConnectorVersion();
		boolean isVersionValid = (version != null && !version.isEmpty());
		assertTrue(isVersionValid);

		String connectorVersionMessage = String.format("Current Connector Version: %s", version);
		VertexLogger.log(connectorVersionMessage);

		assertTrue(version.matches(connectorVersionFormat));
	}

	/**
	 * tests whether the Vertex Services page on this configuration site
	 * displays the correct connector WSDL link
	 *
	 * TODO JIRA lacks a story for the UI of the Services page
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void isConnectorWSDLPresentTest( )
	{
		AribaConnNavOption navOption = AribaConnNavOption.VERTEX_SERVICES;

		boolean isVertexServicesPagePresent = testStartPage.navPanel.isNavOptionEnabled(navOption);
		assertTrue(isVertexServicesPagePresent);

		AribaConnVertexServicesPage servicesPage = testStartPage.navPanel.clickNavOption(navOption);
		boolean isPageLoaded = servicesPage.isCurrentPage();
		assertTrue(isPageLoaded);

		boolean isWSDLPresent = servicesPage.isConnectorWSDLDisplayed();
		assertTrue(isWSDLPresent);

		String wsdlLink = servicesPage.getConnectorWSDLLink();
		boolean isVersionValid = (wsdlLink != null && !wsdlLink.isEmpty());
		assertTrue(isVersionValid);

		String message = String.format("Current Connector WSDL Link: %s", wsdlLink);
		VertexLogger.log(message);
	}

	/**
	 * tests whether the Vertex Services page on this configuration site
	 * displays the correct version number for the connector for an authenticated user
	 *
	 * TODO JIRA lacks a story for the UI of the Services page
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void connectorVersionAuthenticatedUserTest( )
	{
		AribaConnNavOption navOption = AribaConnNavOption.VERTEX_SERVICES;

		AribaConnHomePage homePage = connUtil.signInToConfig(testStartPage);

		boolean isVertexServicesPagePresent = homePage.navPanel.isNavOptionEnabled(navOption);
		assertTrue(isVertexServicesPagePresent);

		AribaConnVertexServicesPage servicesPage = homePage.navPanel.clickNavOption(navOption);
		boolean isPageLoaded = servicesPage.isCurrentPage();
		assertTrue(isPageLoaded);

		boolean isVersionPresent = servicesPage.isConnectorVersionDisplayed();
		assertTrue(isVersionPresent);

		String version = servicesPage.getConnectorVersion();
		boolean isVersionValid = (version != null && !version.isEmpty());
		assertTrue(isVersionValid);

		String connectorVersionMessage = String.format("Current Connector Version: %s", version);
		VertexLogger.log(connectorVersionMessage);
	}

	/**
	 * tests whether the Vertex Services page on this configuration site
	 * displays the connectors WSDL link for an authenticated user
	 *
	 * TODO JIRA lacks a story for the UI of the Services page
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void isConnectorWSDLPresentAuthenticatedUserTest( )
	{
		AribaConnNavOption navOption = AribaConnNavOption.VERTEX_SERVICES;

		AribaConnHomePage homePage = connUtil.signInToConfig(testStartPage);

		boolean isVertexServicesPagePresent = homePage.navPanel.isNavOptionEnabled(navOption);
		assertTrue(isVertexServicesPagePresent);

		AribaConnVertexServicesPage servicesPage = homePage.navPanel.clickNavOption(navOption);
		boolean isPageLoaded = servicesPage.isCurrentPage();
		assertTrue(isPageLoaded);

		boolean isWSDLPresent = servicesPage.isConnectorWSDLDisplayed();
		assertTrue(isWSDLPresent);

		String wsdlLink = servicesPage.getConnectorWSDLLink();
		boolean isVersionValid = (wsdlLink != null && !wsdlLink.isEmpty());
		assertTrue(isVersionValid);

		String message = String.format("Current Connector WSDL Link: %s", wsdlLink);
		VertexLogger.log(message);
	}
}