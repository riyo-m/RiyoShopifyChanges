package com.vertex.quality.connectors.mirakl.ui.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.pojos.OSeriesConfiguration;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.connectors.mirakl.ui.enums.MiraklUIData;
import com.vertex.quality.connectors.mirakl.ui.pages.MiraklConnectorStatusPage;
import com.vertex.quality.connectors.mirakl.ui.pages.MiraklLoginPage;

import static com.vertex.quality.common.utils.SQLConnection.getEnvironmentCredentials;

/**
 * Class for common methods for Mirakl connector UI
 *
 * @author alewis
 */
public class MiraklUIBaseTest extends VertexUIBaseTest<MiraklLoginPage>
{

	protected String signInUsername;
	protected String signInPassword;
	protected String miraklUrl;
	protected String environmentURL;
	protected EnvironmentInformation MiraklEnvironment;
	protected EnvironmentCredentials MiraklCredentials;
	protected OSeriesConfiguration oSeriesConfiguration;
	protected MiraklConnectorStatusPage objMiraklConnectorStatusPage;
	protected MiraklLoginPage objLoginPage;
	public String MIRAKL_TAX_LOOKUP_URL = null;
	public String oseriesURL = null;

	private DBEnvironmentDescriptors getEnvironmentDescriptor( )
	{
		return DBEnvironmentDescriptors.MIRAKL;
	}

	/**
	 * gets sign on information such as username, password, and url from SQL server
	 */
	@Override
	public MiraklLoginPage loadInitialTestPage( )
	{
		DBConnectorNames OSERIES_VAR = SQLConnection.getOSeriesDefaults();
		try
		{
			oSeriesConfiguration = SQLConnection.getOSeriesConfiguration(OSERIES_VAR);

			MiraklEnvironment = SQLConnection.getEnvironmentInformation(DBConnectorNames.MIRAKL, DBEnvironmentNames.QA,
				getEnvironmentDescriptor());
			MiraklCredentials = getEnvironmentCredentials(MiraklEnvironment);
			miraklUrl = MiraklEnvironment.getEnvironmentUrl();
			signInUsername = MiraklCredentials.getUsername();
			signInPassword = MiraklCredentials.getPassword();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		MIRAKL_TAX_LOOKUP_URL = oSeriesConfiguration.getAddressServiceUrl();
		oseriesURL = MIRAKL_TAX_LOOKUP_URL.split("services")[0];

		MiraklLoginPage signInPage = null;
		return signInPage;
	}

	/**
	 * To load Mirakl login page
	 *
	 * @return signOnPage for login to user
	 */
	protected MiraklLoginPage loadSignOnPage( )
	{
		MiraklLoginPage signOnPage;
		driver.get(MiraklUIData.MIRAKL_CONNECTOR_URL.data);
		signOnPage = new MiraklLoginPage(driver);
		return signOnPage;
	}

	/**
	 * Sign in to admin panel
	 *
	 * @param signOnPage mirakl login page to sign in
	 *
	 * @return login page with username and password
	 */
	protected MiraklConnectorStatusPage signInToAdmin( final MiraklLoginPage signOnPage )
	{
		return signOnPage.loginAsUser(signInUsername, signInPassword);
	}

	/**
	 * To open authorize user page for Mirakl connector
	 */
	protected void loadAuthorizePage( )
	{
		driver.get(MiraklUIData.MIRAKL_CONNECTOR_URL.data);
	}

	/**
	 * To get all connections status of connector
	 *
	 * @return text for all connections status of connector
	 */
	protected String checkAllConnections( )
	{
		objMiraklConnectorStatusPage = new MiraklConnectorStatusPage(driver);
		return objMiraklConnectorStatusPage.getAllConnectionsText();
	}

	/**
	 * To check the status of database
	 *
	 * @return text for the status of database
	 */
	protected String checkDatabaseStatus( )
	{
		objMiraklConnectorStatusPage = new MiraklConnectorStatusPage(driver);
		return objMiraklConnectorStatusPage.getDatabaseStatusText();
	}

	/**
	 * To check the status of mirakl
	 *
	 * @return text for the status of mirakl
	 */
	protected String checkMiraklConnectionStatus( )
	{
		objMiraklConnectorStatusPage = new MiraklConnectorStatusPage(driver);
		return objMiraklConnectorStatusPage.getMiraklConnectionStatus();
	}

	/**
	 * To check version of database
	 *
	 * @return text for version of database
	 */
	protected String checkConnectorVersion( )
	{
		objMiraklConnectorStatusPage = new MiraklConnectorStatusPage(driver);
		return objMiraklConnectorStatusPage.getConnectorVersionText();
	}

	/**
	 * Function to authorize user with OAuth token and open home page
	 *
	 * @return text for home page title
	 */
	protected String authorizeUser( )
	{
		objLoginPage = new MiraklLoginPage(driver);
		objLoginPage.enterConnectorUsername(MiraklUIData.MIRAKL_CONNECTOR_USERNAME.data);
		objLoginPage.clickNext();
		objLoginPage.enterConnectorPassword(MiraklUIData.MIRAKL_CONNECTOR_PASSWORD.data);
		objLoginPage.clickSignIn();
		objLoginPage.clickMiraklDevUrl();
		objLoginPage.clickConfirmUrl();
		objLoginPage.clickConfirmPermissions();
		return objLoginPage.getHomePageTitle();
	}

	/**
	 * Click on Connector status tab
	 *
	 * @return text for Connector status page title
	 */
	protected String getConnectorStatusPageTitle( )
	{
		objMiraklConnectorStatusPage = new MiraklConnectorStatusPage(driver);
		objMiraklConnectorStatusPage.clickConnectorStatusTab();
		return objMiraklConnectorStatusPage.getPageTitle();
	}

	/**
	 * Click on Operator tab
	 *
	 * @return text for Operator page title
	 */
	protected String getOperatorPageTitle( )
	{
		objMiraklConnectorStatusPage = new MiraklConnectorStatusPage(driver);
		objMiraklConnectorStatusPage.clickOperatorsTab();
		return objMiraklConnectorStatusPage.getPageTitle();
	}

	/**
	 * Click on Marketplace tab
	 *
	 * @return text for Marketplace page title
	 */
	protected String getMarketplacePageTitle( )
	{
		objMiraklConnectorStatusPage = new MiraklConnectorStatusPage(driver);
		objMiraklConnectorStatusPage.clickMarketplaceTab();
		return objMiraklConnectorStatusPage.getPageTitle();
	}

	/**
	 * Click on Stores tab
	 *
	 * @return text for Stores page title
	 */
	protected String getStoresPageTitle( )
	{
		objMiraklConnectorStatusPage = new MiraklConnectorStatusPage(driver);
		objMiraklConnectorStatusPage.clickStoresTab();
		return objMiraklConnectorStatusPage.getPageTitle();
	}

	/**
	 * Click on Orders tab
	 *
	 * @return text for Orders page title
	 */
	protected String getOrdersPageTitle( )
	{
		objMiraklConnectorStatusPage = new MiraklConnectorStatusPage(driver);
		objMiraklConnectorStatusPage.clickOrdersTab();
		return objMiraklConnectorStatusPage.getPageTitle();
	}
}
