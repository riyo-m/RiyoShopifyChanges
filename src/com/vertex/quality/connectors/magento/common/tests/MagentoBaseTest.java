package com.vertex.quality.connectors.magento.common.tests;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import java.io.File;

@Test(groups = { "magento" })
public class MagentoBaseTest extends VertexUIBaseTest
{

	private static ReadProperties readEnvUrls;
	private static ReadProperties readCredentials;
	private static final String TEST_CREDENTIALS_FILE_PATH = CommonDataProperties.TEST_CREDENTIALS_FILE_PATH;
	private static final String ENV_PROP_FILE_PATH = CommonDataProperties.ENV_PROP_FILE_PATH;
	protected String MAGENTO_ADMIN_URL;
	protected String MAGENTO_ADMIN_USERNAME;
	protected String MAGENTO_ADMIN_PASSWORD;
	protected String MAGENTO_STOREFRONT_URL;

	public MagentoBaseTest( )
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

			MAGENTO_ADMIN_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.M2.ADMIN.USERNAME");
			MAGENTO_ADMIN_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.M2.ADMIN.PASSWORD");
			MAGENTO_ADMIN_URL = readEnvUrls.getProperty("TEST.ENV.M2.ADMIN.URL");
			MAGENTO_STOREFRONT_URL = readEnvUrls.getProperty("TEST.ENV.M2.STOREFRONT.URL");
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
}