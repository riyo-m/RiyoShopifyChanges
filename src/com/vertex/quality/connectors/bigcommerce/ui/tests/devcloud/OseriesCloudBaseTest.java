package com.vertex.quality.connectors.bigcommerce.ui.tests.devcloud;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.bigcommerce.ui.pages.devcloud.*;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.Test;

import java.io.File;

/**
 * this class represents Vertex Dev Cloud base test, contains all the helper methods used in all the test cases.
 *
 * @author rohit.mogane
 */
@Test
public abstract class OseriesCloudBaseTest extends VertexUIBaseTest<OseriesCloudLoginPage>
{
	private static ReadProperties readEnvUrls;
	private static ReadProperties readCredentials;
	private static ReadProperties readCompanyData;
	private static final String TEST_CREDENTIALS_FILE_PATH = CommonDataProperties.TEST_CREDENTIALS_FILE_PATH;
	private static final String ENV_PROP_FILE_PATH = CommonDataProperties.ENV_PROP_FILE_PATH;
	private static final String COMPANY_DATA_FILE_PATH = CommonDataProperties.COMPANY_DATA_FILE_PATH;

	protected String COMPANY_DATA_TEST_ROLE;
	protected String COMPANY_DATA_TEST_ACCOUNT_TYPE;
	protected String COMPANY_DATA_TEST_FRANCHISE_DEV;
	protected String COMPANY_DATA_TEST_FRANCHISE_QA;
	protected String COMPANY_DATA_TEST_FRANCHISE_STAGE;
	protected String COMPANY_DATA_TEST_PARTY_NUMBER;
	protected String COMPANY_DATA_TEST_CLIENT_NAME;
	protected String COMPANY_DATA_TEST_FIRST_NAME;
	protected String COMPANY_DATA_TEST_LAST_NAME;
	protected String COMPANY_DATA_TEST_EMAIL_ID;
	protected String randomSubString;
	protected String COMPANY_DATA_TEST_COUNTRY;
	protected String COMPANY_DATA_TEST_STATE;
	protected String COMPANY_DATA_TEST_SUBSCRIPTION;
	protected String COMPANY_DATA_TEST_OPTION;
	protected String COMPANY_DATA_TEST_POD_DEV;
	protected String COMPANY_DATA_TEST_PHONE_NUMBER;
	protected String COMPANY_DATA_TEST_ADDRESS1;
	protected String COMPANY_DATA_TEST_ADDRESS2;
	protected String COMPANY_DATA_TEST_CITY;
	protected String COMPANY_DATA_TEST_ZIPCODE;
	protected String COMPANY_DATA_TEST_COMPANY_USER_NAME;
	protected String COMPANY_DATA_TEST_COMPANY_PASSWORD;
	protected String COMPANY_DATA_TEST_COMPANY_CONFIRM_PASSWORD;
	protected String COMPANY_DATA_TEST_COMPANY_CODE;
	protected String COMPANY_DATA_TEST_COMPANY_FEDERAL_NUMBER;
	protected String COMPANY_DATA_TEST_COMPANY_NAME;
	protected String COMPANY_DATA_TEST_COMPANY_FIRST_NAME;
	protected String COMPANY_DATA_TEST_COMPANY_LAST_NAME;
	protected String OSERIES_CLOUD_DEV_ADMIN_USERNAME;
	protected String OSERIES_CLOUD_DEV_ADMIN_PASSWORD;
	protected String OSERIES_CLOUD_DEV_ADMIN_URL;
	protected String OSERIES_CLOUD_QA_ADMIN_USERNAME;
	protected String OSERIES_CLOUD_QA_ADMIN_PASSWORD;
	protected String OSERIES_CLOUD_QA_ADMIN_URL;
	protected String OSERIES_CLOUD_STAGE_ADMIN_USERNAME;
	protected String OSERIES_CLOUD_STAGE_ADMIN_PASSWORD;
	protected String OSERIES_CLOUD_STAGE_ADMIN_URL;

	protected OseriesCloudInviteUserPage invitePage;
	protected OseriesCloudConfigureCompanyPage companyPage;
	protected OseriesCloudInviteNewUserPage newUserPage;
	protected OseriesCloudCompanyRegisterPage registerPage;
	protected OseriesCloudCompanyRegistrationsPage statePage;

	public OseriesCloudBaseTest( )
	{
		initializeVariables();
	}

	/**
	 * this method initializes credentials and url of Vertex Dev Cloud for login page
	 */
	private void initializeVariables( )
	{
		try
		{
			File testCredential = new File(TEST_CREDENTIALS_FILE_PATH);
			if ( testCredential.exists() )
			{
				readCredentials = new ReadProperties(TEST_CREDENTIALS_FILE_PATH);
			}
			else
			{
				VertexLogger.log("Test Credentials properties file is not found", VertexLogLevel.ERROR,
					EpiBaseTest.class);
			}

			File testPropFilePath = new File(ENV_PROP_FILE_PATH);
			if ( testPropFilePath.exists() )
			{
				readEnvUrls = new ReadProperties(ENV_PROP_FILE_PATH);
			}
			else
			{
				VertexLogger.log("Environment details properties file is not found", VertexLogLevel.ERROR,
					EpiBaseTest.class);
			}
			File companyDataFilePath = new File(COMPANY_DATA_FILE_PATH);
			if ( companyDataFilePath.exists() )
			{
				readCompanyData = new ReadProperties(COMPANY_DATA_FILE_PATH);
			}
			else
			{
				VertexLogger.log("Environment details properties file is not found", VertexLogLevel.ERROR,
					EpiBaseTest.class);
			}

			randomSubString = RandomStringUtils.randomAlphabetic(3);
			OSERIES_CLOUD_DEV_ADMIN_USERNAME = readCredentials.getProperty("TEST.CREDENTIAL.DEV.CLOUD.USERNAME");
			OSERIES_CLOUD_DEV_ADMIN_PASSWORD = readCredentials.getProperty("TEST.CREDENTIAL.DEV.CLOUD.PASSWORD");
			OSERIES_CLOUD_DEV_ADMIN_URL = readEnvUrls.getProperty("OSERIES.CLOUD.DEV.URL");
			OSERIES_CLOUD_QA_ADMIN_USERNAME = readCredentials.getProperty("TEST.CREDENTIAL.QA.CLOUD.USERNAME");
			OSERIES_CLOUD_QA_ADMIN_PASSWORD = readCredentials.getProperty("TEST.CREDENTIAL.QA.CLOUD.PASSWORD");
			OSERIES_CLOUD_QA_ADMIN_URL = readEnvUrls.getProperty("OSERIES.CLOUD.QA.URL");
			OSERIES_CLOUD_STAGE_ADMIN_USERNAME = readCredentials.getProperty("TEST.CREDENTIAL.STAGE.CLOUD.USERNAME");
			OSERIES_CLOUD_STAGE_ADMIN_PASSWORD = readCredentials.getProperty("TEST.CREDENTIAL.STAGE.CLOUD.PASSWORD");
			OSERIES_CLOUD_STAGE_ADMIN_URL = readEnvUrls.getProperty("OSERIES.CLOUD.STAGE.URL");
			COMPANY_DATA_TEST_ROLE = readCompanyData.getProperty("OSERIES.COMPANY.DATA.TEST.ROLE");
			COMPANY_DATA_TEST_ACCOUNT_TYPE = readCompanyData.getProperty("OSERIES.COMPANY.DATA.TEST.ACCOUNT.TYPE");
			COMPANY_DATA_TEST_FRANCHISE_DEV = readCompanyData.getProperty("OSERIES.COMPANY.DATA.TEST.FRANCHISE.DEV");
			COMPANY_DATA_TEST_FRANCHISE_QA = readCompanyData.getProperty("OSERIES.COMPANY.DATA.TEST.FRANCHISE.QA");
			COMPANY_DATA_TEST_FRANCHISE_STAGE = readCompanyData.getProperty(
				"OSERIES.COMPANY.DATA.TEST.FRANCHISE.STAGE");
			COMPANY_DATA_TEST_PARTY_NUMBER = RandomStringUtils.randomNumeric(6);
			COMPANY_DATA_TEST_CLIENT_NAME = randomSubString + readCompanyData.getProperty(
				"OSERIES.COMPANY.DATA.TEST.CLIENT.NAME");
			COMPANY_DATA_TEST_FIRST_NAME = readCompanyData.getProperty("OSERIES.COMPANY.DATA.TEST.USER.FIRST.NAME");
			COMPANY_DATA_TEST_LAST_NAME = readCompanyData.getProperty("OSERIES.COMPANY.DATA.TEST.USER.LAST.NAME");
			COMPANY_DATA_TEST_EMAIL_ID = randomSubString + readCompanyData.getProperty(
				"OSERIES.COMPANY.DATA.TEST.USER.EMAIL.ID");
			COMPANY_DATA_TEST_COUNTRY = readCompanyData.getProperty("OSERIES.COMPANY.DATA.TEST.USER.COUNTRY");
			COMPANY_DATA_TEST_STATE = readCompanyData.getProperty("OSERIES.COMPANY.DATA.TEST.USER.STATE");
			COMPANY_DATA_TEST_SUBSCRIPTION = readCompanyData.getProperty("OSERIES.COMPANY.DATA.TEST.USER.SUBSCRIPTION");
			COMPANY_DATA_TEST_OPTION = readCompanyData.getProperty("OSERIES.COMPANY.DATA.TEST.USER.OPTION");
			COMPANY_DATA_TEST_POD_DEV = readCompanyData.getProperty("OSERIES.COMPANY.DATA.TEST.USER.POD.DEV");
			COMPANY_DATA_TEST_PHONE_NUMBER = readCompanyData.getProperty("OSERIES.COMPANY.DATA.TEST.USER.PHONE.NUMBER");
			COMPANY_DATA_TEST_ADDRESS1 = readCompanyData.getProperty("OSERIES.COMPANY.DATA.TEST.ADDRESS1");
			COMPANY_DATA_TEST_ADDRESS2 = readCompanyData.getProperty("OSERIES.COMPANY.DATA.TEST.ADDRESS2");
			COMPANY_DATA_TEST_CITY = readCompanyData.getProperty("OSERIES.COMPANY.DATA.TEST.CITY");
			COMPANY_DATA_TEST_ZIPCODE = readCompanyData.getProperty("OSERIES.COMPANY.DATA.TEST.ZIPCODE");
			COMPANY_DATA_TEST_COMPANY_USER_NAME = readCompanyData.getProperty(
				"OSERIES.COMPANY.DATA.TEST.USER.NAME");
			COMPANY_DATA_TEST_COMPANY_PASSWORD = readCompanyData.getProperty(
				"OSERIES.COMPANY.DATA.TEST.PASSWORD");
			COMPANY_DATA_TEST_COMPANY_CONFIRM_PASSWORD = readCompanyData.getProperty(
				"OSERIES.COMPANY.DATA.TEST.CONFIRM.PASSWORD");
			COMPANY_DATA_TEST_COMPANY_CODE = RandomStringUtils.randomNumeric(4);
			COMPANY_DATA_TEST_COMPANY_FEDERAL_NUMBER = readCompanyData.getProperty(
				"OSERIES.COMPANY.DATA.TEST.FEDERAL.NUMBER");
			COMPANY_DATA_TEST_COMPANY_NAME = randomSubString + readCompanyData.getProperty(
				"OSERIES.COMPANY.DATA.TEST.NAME");
			COMPANY_DATA_TEST_COMPANY_FIRST_NAME = readCompanyData.getProperty(
				"OSERIES.COMPANY.DATA.TEST.FIRST.NAME");
			COMPANY_DATA_TEST_COMPANY_LAST_NAME = readCompanyData.getProperty(
				"OSERIES.COMPANY.DATA.TEST.LAST.NAME");
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
}
