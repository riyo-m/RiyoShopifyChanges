package com.vertex.quality.connectors.salesforce.tests.cpqbilling.base;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.salesforce.enums.SuccessMessages;
import com.vertex.quality.connectors.salesforce.pages.SalesForceLogInPage;
import com.vertex.quality.connectors.salesforce.pages.cpq.SalesForceCPQPostLogInPage;
import com.vertex.quality.connectors.salesforce.pages.crm.SalesForceCRMConfigPage;
import com.vertex.quality.connectors.salesforce.tests.cpq.base.SalesForceCPQBaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.text.DecimalFormat;

import static org.testng.Assert.assertEquals;

/**
 * this class represents all the common methods used in most of the test cases
 * such as logging in to the admin home page and other helper methods
 *
 * @author bjohnson
 */
@Test(groups = "salesforce_crm")
public abstract class SalesForceCPQBillingBaseTest extends VertexUIBaseTest
{
	private ReadProperties readEnvUrls;
	private ReadProperties readCredentials;
	private ReadProperties readConfigDetails;

	private final String TEST_CREDENTIALS_FILE_PATH = CommonDataProperties.TEST_CREDENTIALS_FILE_PATH;
	private final String ENV_PROP_FILE_PATH = CommonDataProperties.ENV_PROP_FILE_PATH;
	private final String CONFIG_DETAILS_FILE_PATH = CommonDataProperties.CONFIG_DETAILS_FILE_PATH;

	public String SALESFORCE_BILLING_USERNAME = null;
	public String SALESFORCE_BILLING_PASSWORD = null;

	public String SALESFORCE_BILLING_URL = null;

	public String SALESFORCE_TAX_LOOKUP_URL = null;
	public String SALESFORCE_TAX_CALCULATION_URL = null;
	public String CONFIG_USERNAME = null;
	public String CONFIG_PASSWORD = null;
	public String CONFIG_TRUSTEDID = null;
	public String CONFIG_TAX_PAYER_CODE = null;
	public String CONFIG_SEVERITY = "DEBUG";
	public String CONFIG_MAX_ROWS = null;
	public String LOGNAME = null;
	public String RESPONSE_STATUS = null;
	public String RESPONSE_STATUS_CODE = null;

	@BeforeClass(alwaysRun = true)
	public void initSetup( )
	{
		if ( new File(TEST_CREDENTIALS_FILE_PATH).exists() )
		{
			readCredentials = new ReadProperties(TEST_CREDENTIALS_FILE_PATH);
		}
		else
		{
			VertexLogger.log("Test Credentials properties file is not found", VertexLogLevel.ERROR,
				SalesForceCPQBaseTest.class);
		}

		if ( new File(ENV_PROP_FILE_PATH).exists() )
		{
			readEnvUrls = new ReadProperties(ENV_PROP_FILE_PATH);
		}
		else
		{
			VertexLogger.log("Environment details properties file is not found", VertexLogLevel.ERROR,
				SalesForceCPQBaseTest.class);
		}

		if ( new File(CONFIG_DETAILS_FILE_PATH).exists() )
		{
			readConfigDetails = new ReadProperties(CONFIG_DETAILS_FILE_PATH);
		}
		else
		{
			VertexLogger.log("Configuration details properties file is not found", VertexLogLevel.ERROR,
				SalesForceCPQBaseTest.class);
		}

		SALESFORCE_BILLING_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.SFCPQBILLING.ADMIN.USERNAME");
		SALESFORCE_BILLING_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.SFCPQBILLING.ADMIN.PASSWORD");
		SALESFORCE_BILLING_URL = readEnvUrls.getProperty("TEST.ENV.SF.STOREFRONT.URL");

		SALESFORCE_TAX_LOOKUP_URL = readConfigDetails.getProperty("TEST.VERTEX.SF.TAX_AREA_LOOKUP_ENDPOINT_URL");
		SALESFORCE_TAX_CALCULATION_URL = readConfigDetails.getProperty("TEST.VERTEX.SF.TAX_CALCULATION_ENDPOINT_URL");
		CONFIG_USERNAME = readConfigDetails.getProperty("TEST.VERTEX.SF.USER_NAME");
		CONFIG_PASSWORD = readConfigDetails.getProperty("TEST.VERTEX.SF.PASSWORD");
		CONFIG_TRUSTEDID = readConfigDetails.getProperty("TEST.VERTEX.SF.TRUSTED_ID");
		CONFIG_TAX_PAYER_CODE = readConfigDetails.getProperty("TEST.VERTEX.SF.TAX_PAYER_CODE");
		CONFIG_SEVERITY = readConfigDetails.getProperty("TEST.VERTEX.SF.SEVERITY");
		CONFIG_MAX_ROWS = readConfigDetails.getProperty("TEST.VERTEX.SF.MAX_ROWS");

		LOGNAME = readConfigDetails.getProperty("TEST.VERTEX.SF.LOGNAME");
		RESPONSE_STATUS = readConfigDetails.getProperty("TEST.VERTEX.SF.RESPONSE_STATUS");
		RESPONSE_STATUS_CODE = readConfigDetails.getProperty("TEST.VERTEX.SF.RESPONSE_STATUS_CODE");
	}

	/**
	 * Navigate to the login page for CPQ
	 *
	 * author: bjohnson
	 */
	protected void NavigateToLoginPage( )
	{
		VertexLogger.log(String.format("Launching SalesForce CPQ URL - %s", SALESFORCE_BILLING_URL),
			VertexLogLevel.DEBUG, SalesForceCPQBaseTest.class);
		driver.get(SALESFORCE_BILLING_URL);
	}

	/**
	 * Login as CPQ User for Salesforce
	 *
	 * author: bjohnson
	 */
	protected SalesForceCPQPostLogInPage logInAsSalesForceBillingUser( )
	{
		SalesForceLogInPage logInPage = new SalesForceLogInPage(driver);
		// open Sales force CPQ login page
		NavigateToLoginPage();
		// login as user
		SalesForceCPQPostLogInPage postLogInPage = logInPage.logInAsCPQUser(SALESFORCE_BILLING_USERNAME,
			SALESFORCE_BILLING_PASSWORD);
		postLogInPage.checkAndSwitchClassicView();
		postLogInPage.closeLightningExperienceDialog();
		return postLogInPage;
	}

	/**
	 * Function to validate Log Message
	 */
	protected void validateAndCloseSuccessMessage( SalesForceCRMConfigPage configPage )
	{
		String actuallookupMessage = configPage.getAreaLookUpMessage();
		assertEquals(SuccessMessages.AREA_LOOKUP_SUCCESS.text, actuallookupMessage,
			"Fail. Actual Value: " + actuallookupMessage);

		String actualTaxCalcMessage = configPage.getTaxCalcMessage();
		assertEquals(SuccessMessages.TAX_CALC_SUCCESS.text, actualTaxCalcMessage,
			"Fail. Actual Value: " + actualTaxCalcMessage);

		String actualConfigMessage = configPage.getConfigurationMessage();
		assertEquals(SuccessMessages.CONFIG_SUCCESS.text, actualConfigMessage,
			"Fail. Actual Value: " + actualConfigMessage);
	}

	/**
	 * Function to format the double values to two Decimal value
	 */
	protected double formatDoubleValues( double valueToFormat )
	{
		DecimalFormat dFormat = new DecimalFormat("###.##");
		String expectedValue = dFormat.format(valueToFormat);
		double expectedDoubleValue = Double.parseDouble(expectedValue);
		return expectedDoubleValue;
	}
}
