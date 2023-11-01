package com.vertex.quality.connectors.hybris.tests.base;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.hybris.pages.admin.HybrisAdminHomePage;
import com.vertex.quality.connectors.hybris.pages.admin.HybrisAdminLoginPage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOHomePage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOLoginPage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOVertexConfigurationPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStoreGuestLoginPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStorePage;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Hybris Base Test includes all required credentials and configurations
 *
 * @author Nagaraju Gampa
 */
@Test(groups = { "hybris" })
public abstract class HybrisBaseTest extends VertexUIBaseTest
{
	private final boolean isDriverServiceProvisioned = Boolean.getBoolean("services.webdriver.provisioned");
	private final boolean isDriverHeadlessMode = Boolean.getBoolean("services.webdriver.headless");
	private final boolean isSeleniumHost = Boolean.getBoolean("services.webdriver.host.selenium");

	private static ReadProperties readEnvUrls;
	private static ReadProperties readCredentials;
	private static ReadProperties readConfigDetails;

	private static String TEST_CREDENTIALS_FILE_PATH = CommonDataProperties.TEST_CREDENTIALS_FILE_PATH;
	private static String ENV_PROP_FILE_PATH = CommonDataProperties.ENV_PROP_FILE_PATH;
	private static String CONFIG_PROP_FILE_PATH = CommonDataProperties.CONFIG_DETAILS_FILE_PATH;

	private String HYBRIS_ADMIN_USERNAME = null;
	private String HYBRIS_ADMIN_PASSWORD = null;

	private String HYBRIS_BO_USERNAME = null;
	private String HYBRIS_BO_PASSWORD = null;

	private String HYBRIS_ADMIN_URL = null;
	private String HYBRIS_BO_URL = null;

	protected String HYBRIS_VERTEXCONFIG_ADMIN_CONFIGCODE = null;
	protected String HYBRIS_VERTEXCONFIG_ADMIN_EMAIL_TO_SEND_CONFIG = null;
	protected String HYBRIS_VERTEXCONFIG_ADMIN_EMAIL_TO_RECEIVE_CONFIG = null;
	protected String HYBRIS_VERTEXCONFIG_ADMIN_ENDPOINT_ADDRESS_URL = null;
	protected String HYBRIS_VERTEXCONFIG_ADMIN_ENDPOINT_TAX_URL = null;
	protected String HYBRIS_VERTEXCONFIG_ADMIN_TRUSTEDID = null;
	protected String HYBRIS_VERTEXCONFIG_ADMIN_USERNAME = null;
	protected String HYBRIS_VERTEXCONFIG_ADMIN_PASSWORD = null;
	protected String HYBRIS_STORE_FRONT_URL = null;
	protected String HYBRIS_B2C_EMAIL_TO_SEND_CONFIGURATION = null;
	protected String HYBRIS_B2C_EMAIL_TO_RECEIVE_CONFIGURATION = null;
	protected String HYBRIS_FALSE_TRUSTED_ID = "false@#$";
	protected String HYBRIS_O_SERIES_9_TRUSTED_ID = "$hybris01234$";
	protected String HYBRIS_O_SERIES_9_USERNAME = "hybrisUser";
	protected String HYBRIS_O_SERIES_9_PASSWORD = "vertex";

	@BeforeClass(alwaysRun = true)
	public void initSetup( )
	{
		try
		{
			if ( new File(TEST_CREDENTIALS_FILE_PATH).exists() )
			{
				readCredentials = new ReadProperties(TEST_CREDENTIALS_FILE_PATH);
			}
			else
			{
				VertexLogger.log("Test Credentials properties file is not found", VertexLogLevel.ERROR,
					HybrisBaseTest.class);
			}

			if ( new File(ENV_PROP_FILE_PATH).exists() )
			{
				readEnvUrls = new ReadProperties(ENV_PROP_FILE_PATH);
			}
			else
			{
				VertexLogger.log("Environment details properties file is not found", VertexLogLevel.ERROR,
					HybrisBaseTest.class);
			}

			if ( new File(CONFIG_PROP_FILE_PATH).exists() )
			{
				readConfigDetails = new ReadProperties(CONFIG_PROP_FILE_PATH);
			}
			else
			{
				VertexLogger.log("Configuration details properties file is not found", VertexLogLevel.ERROR,
					HybrisBaseTest.class);
			}

			HYBRIS_ADMIN_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.HYBRIS.ADMIN.USERNAME");
			HYBRIS_ADMIN_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.HYBRIS.ADMIN.PASSWORD");

			HYBRIS_BO_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.HYBRIS.BACKOFFICE.USERNAME");
			HYBRIS_BO_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.HYBRIS.BACKOFFICE.PASSWORD");

			HYBRIS_ADMIN_URL = readEnvUrls.getProperty("TEST.ENV.HYBRIS.ADMIN.URL");
			HYBRIS_BO_URL = readEnvUrls.getProperty("TEST.ENV.HYBRIS.BACKOFFICE.URL");
			HYBRIS_STORE_FRONT_URL = readEnvUrls.getProperty("TEST.ENV.HYBRIS.STOREFRONT.URL");

			HYBRIS_VERTEXCONFIG_ADMIN_CONFIGCODE = readConfigDetails.getProperty(
				"TEST.VERTEX.HYBRIS.CONFIGURATION_CODE");
			HYBRIS_VERTEXCONFIG_ADMIN_EMAIL_TO_SEND_CONFIG = readConfigDetails.getProperty(
				"TEST.VERTEX.HYBRIS.EMAIL_TO_SEND_CONFIGURATION");
			HYBRIS_VERTEXCONFIG_ADMIN_EMAIL_TO_RECEIVE_CONFIG = readConfigDetails.getProperty(
				"TEST.VERTEX.HYBRIS.EMAIL_TO_RECEIVE_CONFIGURATION");
			HYBRIS_VERTEXCONFIG_ADMIN_ENDPOINT_ADDRESS_URL = readConfigDetails.getProperty(
				"TEST.VERTEX.HYBRIS.WS_ADDRESS_ENDPOINT_URL");
			HYBRIS_VERTEXCONFIG_ADMIN_ENDPOINT_TAX_URL = readConfigDetails.getProperty(
				"TEST.VERTEX.HYBRIS.WS_TAX_ENDPOINT_URL");
			HYBRIS_VERTEXCONFIG_ADMIN_TRUSTEDID = readConfigDetails.getProperty("TEST.VERTEX.HYBRIS.TRUSTED_ID");
			HYBRIS_VERTEXCONFIG_ADMIN_USERNAME = readConfigDetails.getProperty("TEST.VERTEX.HYBRIS.USER_NAME");
			HYBRIS_VERTEXCONFIG_ADMIN_PASSWORD = readConfigDetails.getProperty("TEST.VERTEX.HYBRIS.PASSWORD");
			HYBRIS_B2C_EMAIL_TO_SEND_CONFIGURATION = readConfigDetails.getProperty(
				"TEST.VERTEX.HYBRIS.EMAIL_TO_SEND_CONFIGURATION");
			HYBRIS_B2C_EMAIL_TO_RECEIVE_CONFIGURATION = readConfigDetails.getProperty(
				"TEST.VERTEX.HYBRIS.EMAIL_TO_RECEIVE_CONFIGURATION");

			// close stream objects
			readCredentials.close();
			readEnvUrls.close();
			readConfigDetails.close();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * initializes the ChromeDriver which interacts with the browser
	 */
	protected void createChromeDriver() {
		HashMap<String, Object> chromePrefs = new HashMap<>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", VertexPage.DOWNLOAD_DIRECTORY_PATH);

		// Add ChromeDriver-specific capabilities through ChromeOptions.
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("--ignore-ssl-errors=yes");
		options.addArguments("--ignore-certificate-errors");
		options.addArguments("window-size=1920,1080");
		options.addArguments("--disable-infobars");
		options.addArguments("--incognito");
		if(isDriverHeadlessMode) {
			options.addArguments("--headless");
		}
		driver = isDriverServiceProvisioned
				? new RemoteWebDriver(getDriverServiceUrl(), options)
				: new ChromeDriver(options);
	}

	/**
	 * used to get service url
	 * @return service url
	 */
	private URL getDriverServiceUrl() {
		try {
			String hostname = "localhost";
			if(isSeleniumHost) {
				hostname = "selenium"; //instead of "localhost" for GitHub Actions
			}
			return new URL("http://" + hostname + ":4444/wd/hub");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/***
	 * This is a method for launching Hybris BackOffice URL
	 */
	protected HybrisBOLoginPage launchBackofficePage( )
	{
		final String launchBOUrlMsg = String.format("Launching Hybris BackOffice URL - %s", HYBRIS_BO_URL);
		VertexLogger.log(launchBOUrlMsg);
		driver.get(HYBRIS_BO_URL);
		final HybrisBOLoginPage bologin = new HybrisBOLoginPage(driver);
		return bologin;
	}

	/***
	 * Method to log-in into BackOffice Page and returning an object to Home page
	 *
	 */
	protected HybrisBOHomePage loginBOUser( )
	{
		VertexLogger.log("Signing in to Hybris BackOffice");
		this.launchBackofficePage();
		HybrisBOHomePage boHomePage = new HybrisBOHomePage(driver);
		boHomePage.hybrisWaitForPageLoad();
		int loginAttempts = 0;
		final int maxLoginAttempts = 15;
		boolean loginSuccessful = false;
		while ( loginAttempts < maxLoginAttempts && !loginSuccessful )
		{
			final HybrisBOLoginPage bologin = new HybrisBOLoginPage(driver);
			bologin.setUsername(HYBRIS_BO_USERNAME);
			bologin.setPassword(HYBRIS_BO_PASSWORD);
			boHomePage = new HybrisBOHomePage(driver);
			bologin.clickLogInButton();
			if ( boHomePage.loginSuccessful() )
			{
				final String successfulLoginMsg = String.format("User is successfully logged in to Backoffice");
				VertexLogger.log(successfulLoginMsg);
				loginSuccessful = true;
			}
			else if ( bologin.isUsernamePresent() && bologin
				.getUsername()
				.trim()
				.equals("") )
			{
				final String failureLoginMsg = String.format(
					"Wierd issue during signing in, retrying and login again...");
				VertexLogger.log(failureLoginMsg);
			}
			loginAttempts = loginAttempts + 1;
		}
		boHomePage.hybrisWaitForPageLoad();
		return boHomePage;
	}

	/***
	 * Method to launch B2c Electronics Store Page
	 *
	 * @return - Launch page of Electronic Store Front
	 */
	protected HybrisEStorePage launchB2CPage( )
	{
		final String launchElectronicsStoreMsg = String.format("Launching Hybris B2C URL - %s", HYBRIS_STORE_FRONT_URL);
		VertexLogger.log(launchElectronicsStoreMsg);
		driver.get(HYBRIS_STORE_FRONT_URL);
		final HybrisEStorePage storefrontlaunch = new HybrisEStorePage(driver);
		return storefrontlaunch;
	}

	/***
	 * Method to launch Hybris Admin URL
	 *
	 * @return returning an object to adminlogin page
	 */
	protected HybrisAdminLoginPage launchAdminPage( )
	{
		final String launchAdminPageMsg = String.format("Launching Hybris Admin URL - %s", HYBRIS_ADMIN_URL);
		VertexLogger.log(launchAdminPageMsg);
		driver.get(HYBRIS_ADMIN_URL);
		final HybrisAdminLoginPage adminlogin = new HybrisAdminLoginPage(driver);
		return adminlogin;
	}

	/**
	 * Handling 404 page not found error as it appears sometime in automation execution while login to Hybris admin console
	 */
	public void handle404Error() {
		HybrisAdminLoginPage adminLogin = new HybrisAdminLoginPage(driver);
		By error404 = By.xpath(".//h2[contains(text(),'404')]");
		adminLogin.hybrisWaitForPageLoad();
		if (adminLogin.element.isElementPresent(error404)) {
			driver.navigate().back();
			adminLogin.hybrisWaitForPageLoad();
		}
	}

	/***
	 * Method to log-in into Hybris-Admin page with Admin Credentials
	 *
	 * @return Admin Home Page
	 *         Admin Home Page Describes and user can perform all administrative operations
	 */
	protected HybrisAdminHomePage loginAsAdminUser( )
	{
		VertexLogger.log("Signing in to Hybris Admin Page");
		this.launchAdminPage();
		HybrisAdminHomePage adminHomePage = null;
		int loginAttempts = 0;
		final int maxLoginAttempts = 15;
		boolean loginSuccessful = false;
		while ( loginAttempts < maxLoginAttempts && !loginSuccessful )
		{
			final HybrisAdminLoginPage launchAdminLoginPage = new HybrisAdminLoginPage(driver);
			launchAdminLoginPage.setUsername(HYBRIS_ADMIN_USERNAME);
			launchAdminLoginPage.setPassword(HYBRIS_ADMIN_PASSWORD);
			adminHomePage = new HybrisAdminHomePage(driver);
			launchAdminLoginPage.clickLoginButton();
			// Sometimes hybris login page is reloading after clicking on login button, so to get
			// around the weird issue, try re-login again if blank username present
			handle404Error();
			if ( adminHomePage.loginSuccessful() )
			{
				final String successfulLoginMsg = String.format("User is successfully logged in to Hybris Admin Page");
				VertexLogger.log(successfulLoginMsg);
				loginSuccessful = true;
			}
			else if ( launchAdminLoginPage.isUsernamePresent() && launchAdminLoginPage
				.getUsername()
				.trim()
				.equals("") )
			{
				final String failureLoginMsg = String.format(
					"Wierd issue during signing in, retrying and login again...");
				VertexLogger.log(failureLoginMsg);
			}
			loginAttempts = loginAttempts + 1;
		}
		return adminHomePage;
	}

	/***
	 * Method to set Default Vertex Administration Configuration
	 */
	protected void setDefaultVertexAdministrationConfiguration( HybrisBOVertexConfigurationPage vertexConfigPage )
	{
		vertexConfigPage.setVertexConfigCode(HYBRIS_VERTEXCONFIG_ADMIN_CONFIGCODE);
		vertexConfigPage.setEmailToSendConfig(HYBRIS_VERTEXCONFIG_ADMIN_EMAIL_TO_SEND_CONFIG);
		vertexConfigPage.setEmailToReceiveConfig(HYBRIS_VERTEXCONFIG_ADMIN_EMAIL_TO_RECEIVE_CONFIG);
		vertexConfigPage.setEndpointAddressVerification(HYBRIS_VERTEXCONFIG_ADMIN_ENDPOINT_ADDRESS_URL);
		vertexConfigPage.setEndpointTaxCalculation(HYBRIS_VERTEXCONFIG_ADMIN_ENDPOINT_TAX_URL);
	}

	/***
	 * Method to set Vertex Configuration with TrustedID
	 */
	protected void setVertexConfigurationTrustedID( HybrisBOVertexConfigurationPage vertexConfigPage )
	{
		vertexConfigPage.selectAuthenticationTab();
		vertexConfigPage.clearVertexConfigurationUsername();
		vertexConfigPage.clearVertexConfigurationPassword();
		vertexConfigPage.setVertexConfigurationTrustedId(HYBRIS_VERTEXCONFIG_ADMIN_TRUSTEDID);
	}

	/***
	 * Method to set Vertex Configuration with UserCredentials
	 */
	protected void setVertexConfigurationUserCredentials( HybrisBOVertexConfigurationPage vertexConfigPage )
	{
		vertexConfigPage.selectAuthenticationTab();
		vertexConfigPage.clearVertexConfigurationTrustedID();
		vertexConfigPage.enterVertexConfigurationUsername(HYBRIS_VERTEXCONFIG_ADMIN_USERNAME);
		vertexConfigPage.enterVertexConfigurationPassword(HYBRIS_VERTEXCONFIG_ADMIN_PASSWORD);
	}

	/***
	 * Method to set Guest Email & Confirm Guest Email
	 */
	protected void setGuestEmailAndConfirmEmail( HybrisEStoreGuestLoginPage electronicsStoreLoginPage )
	{
		electronicsStoreLoginPage.enterGuestEmail(HYBRIS_B2C_EMAIL_TO_SEND_CONFIGURATION);
		electronicsStoreLoginPage.confirmGuestEmail(HYBRIS_B2C_EMAIL_TO_SEND_CONFIGURATION);
	}
}
