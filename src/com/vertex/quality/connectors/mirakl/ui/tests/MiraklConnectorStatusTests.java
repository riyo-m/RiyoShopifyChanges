package com.vertex.quality.connectors.mirakl.ui.tests;

import com.vertex.quality.connectors.mirakl.ui.base.MiraklUIBaseTest;
import com.vertex.quality.connectors.mirakl.ui.enums.MiraklUIData;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Mirakl connector UI connector status tests
 *
 * @author Rohit.Mogane
 */
public class MiraklConnectorStatusTests extends MiraklUIBaseTest
{

	/**
	 * to open connector status tab
	 */
	private void openConnectorStatusTab( )
	{
		loadAuthorizePage();
		authorizeUser();
		getConnectorStatusPageTitle();
	}

	/**
	 * to test connector status page title
	 */
	@Test
	public void ConnectorStatusPageTitleTest( )
	{
		openConnectorStatusTab();
		assertEquals(getConnectorStatusPageTitle(), MiraklUIData.MIRAKL_CONNECTOR_STATUS_PAGE_TITLE.data);
	}

	/**
	 * to test all the connections for Mirakl connector are working fine
	 */
	@Test
	public void allConnectionsTest( )
	{
		openConnectorStatusTab();
		assertEquals(checkAllConnections(), MiraklUIData.MIRAKL_ALL_CONNECTIONS.data);
	}

	/**
	 * to test database connection is working fine
	 */
	@Test
	public void databaseConnectionTest( )
	{
		openConnectorStatusTab();
		assertEquals(checkDatabaseStatus(), MiraklUIData.MIRAKL_STATUS_UP.data);
	}

	/**
	 * to test Connector version
	 */
	@Test
	public void connectorVersionTest( )
	{
		openConnectorStatusTab();
		assertEquals(checkConnectorVersion(), MiraklUIData.MIRAKL_CONNECTOR_VERSION.data);
	}

	/**
	 * to test mirakl connection status
	 */
	@Test
	public void miraklConnectionTest( )
	{
		openConnectorStatusTab();
		assertEquals(checkMiraklConnectionStatus(), MiraklUIData.MIRAKL_STATUS_UP.data);
	}
}
