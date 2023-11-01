package com.vertex.quality.connectors.mirakl.ui.tests;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.pojos.OSeriesConfiguration;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.connectors.mirakl.ui.pages.MiraklConnectorStatusPage;
import com.vertex.quality.connectors.mirakl.ui.pages.MiraklLoginPage;

import static com.vertex.quality.common.utils.SQLConnection.getEnvironmentCredentials;

/**
 * Class for common methods for Mirakl UI
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

		MiraklLoginPage signInPage = loadSignOnPage();
		return signInPage;
	}

	protected MiraklLoginPage loadSignOnPage( )
	{
		MiraklLoginPage signOnPage;

		String url = this.miraklUrl;

		driver.get(url);

		signOnPage = new MiraklLoginPage(driver);

		return signOnPage;
	}

	protected MiraklConnectorStatusPage signInToAdmin( final MiraklLoginPage signOnPage )
	{
		return signOnPage.loginAsUser(signInUsername, signInPassword);
	}
}
