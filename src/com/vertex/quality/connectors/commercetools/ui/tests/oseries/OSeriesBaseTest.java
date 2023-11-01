package com.vertex.quality.connectors.commercetools.ui.tests.oseries;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.bigcommerce.ui.pages.refund.oseries.OSeriesSignOnPage;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import java.io.File;

/**
 * this class represents OSeries base test, contains all the helper methods used in all the test cases.
 *
 * @author rohit-mogane
 */
@Test
public abstract class OSeriesBaseTest extends VertexUIBaseTest<OSeriesSignOnPage>
{
	private static ReadProperties readEnvUrls;
	private static ReadProperties readCredentials;
	private static String TEST_CREDENTIALS_FILE_PATH = CommonDataProperties.TEST_CREDENTIALS_FILE_PATH;
	private static String ENV_PROP_FILE_PATH = CommonDataProperties.ENV_PROP_FILE_PATH;
	protected String OSERIES_ADMIN_USERNAME_CT;
	protected String OSERIES_ADMIN_PASSWORD_CT;
	protected String OSERIES_ADMIN_URL;

	public OSeriesBaseTest( )
	{
		initializeVariables();
	}

	/**
	 * this method initializes credentials and url O-Series for login page
	 */
	private void initializeVariables( )
	{
		try
		{
			File testcredential = new File(TEST_CREDENTIALS_FILE_PATH);
			if ( testcredential != null && testcredential.exists() )
			{
				readCredentials = new ReadProperties(TEST_CREDENTIALS_FILE_PATH);
			}
			else
			{
				VertexLogger.log("Test Credentials properties file is not found", VertexLogLevel.ERROR,
					EpiBaseTest.class);
			}

			File testPropFilePath = new File(ENV_PROP_FILE_PATH);
			if ( testPropFilePath != null && testPropFilePath.exists() )
			{
				readEnvUrls = new ReadProperties(ENV_PROP_FILE_PATH);
			}
			else
			{
				VertexLogger.log("Environment details properties file is not found", VertexLogLevel.ERROR,
					EpiBaseTest.class);
			}

			OSERIES_ADMIN_USERNAME_CT = readCredentials.getProperty("TEST.CREDENTIALS.OSERIES.ADMIN.USERNAME.CT");
			OSERIES_ADMIN_PASSWORD_CT = readCredentials.getProperty("TEST.CREDENTIALS.OSERIES.ADMIN.PASSWORD.CT");
			OSERIES_ADMIN_URL = readEnvUrls.getProperty("OSERIES9.URL");
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
}
