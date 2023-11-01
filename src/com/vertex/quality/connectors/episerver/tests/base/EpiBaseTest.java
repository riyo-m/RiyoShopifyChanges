package com.vertex.quality.connectors.episerver.tests.base;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import com.vertex.quality.connectors.episerver.common.enums.NavigationAndMenuOptions;
import com.vertex.quality.connectors.episerver.pages.epiCommon.EpiStoreFrontHomePage;
import com.vertex.quality.connectors.episerver.pages.epiCommon.EpiStoreLoginPage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiAdministrationHomePage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiWarehousePage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

@Test(groups = { "episerver" })
public abstract class EpiBaseTest extends VertexUIBaseTest
{
	protected boolean skipConfig = false;
	private static ReadProperties readEnvUrls;
	private static ReadProperties readCredentials;
	private static ReadProperties readConfigDetails;
	private static String TEST_CREDENTIALS_FILE_PATH = CommonDataProperties.TEST_CREDENTIALS_FILE_PATH;
	private static String ENV_PROP_FILE_PATH = CommonDataProperties.ENV_PROP_FILE_PATH;
	private static String CONFIG_PROP_FILE_PATH = CommonDataProperties.CONFIG_DETAILS_FILE_PATH;

	private final boolean isDriverServiceProvisioned = Boolean.getBoolean("services.webdriver.provisioned");
	private final boolean isDriverHeadlessMode = Boolean.getBoolean("services.webdriver.headless");
	private final boolean isSeleniumHost = Boolean.getBoolean("services.webdriver.host.selenium");

	public String EPI_SERVER_ADMIN_USERNAME = null;
	public String EPI_SERVER_ADMIN_PASSWORD = null;
	public String EPI_SERVER_ADMIN_URL = null;
	public String TAX_CALCULATION_URL = null;
	public String ADDRESS_VALIDATION_URL = null;
	public String BASIC_CONFIG_USERNAME = null;
	public String BASIC_CONFIG_TRUSTED_ID = null;
	public String EPI_SERVER_STOREFRONT_URL = null;
	private String EPI_SERVER_STOREFRONT_USERNAME = null;
	private String EPI_SERVER_STOREFRONT_PASSWORD = null;

	public EpiBaseTest( )
	{
		super();

		initializeVariables();
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

			File testConfigFilePath = new File(CONFIG_PROP_FILE_PATH);
			if ( testConfigFilePath != null && testConfigFilePath.exists() )
			{
				readConfigDetails = new ReadProperties(CONFIG_PROP_FILE_PATH);
			}
			else
			{
				VertexLogger.log("Configuration details properties file is not found", VertexLogLevel.ERROR,
					EpiBaseTest.class);
			}

			EPI_SERVER_ADMIN_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.EPI.ADMIN.USERNAME");
			EPI_SERVER_ADMIN_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.EPI.ADMIN.PASSWORD");
			EPI_SERVER_ADMIN_URL = readEnvUrls.getProperty("TEST.ENV.EPI.ADMIN.URL");
			EPI_SERVER_STOREFRONT_URL = readEnvUrls.getProperty("TEST.ENV.EPI.STOREFRONT.URL");
			EPI_SERVER_STOREFRONT_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.EPI.CUSTOMER.USERNAME");
			EPI_SERVER_STOREFRONT_PASSWORD = readCredentials
				.getProperty("TEST.CREDENTIALS.EPI.CUSTOMER.PASSWORD")
				.trim();

			TAX_CALCULATION_URL = readConfigDetails.getProperty("TEST.VERTEX.EPI_SERVER.TAX_CALCULATION_URL");
			ADDRESS_VALIDATION_URL = readConfigDetails.getProperty("TEST.VERTEX.EPI_SERVER.ADDRESS_VALIDATION_URL");
			BASIC_CONFIG_USERNAME = readConfigDetails.getProperty("TEST.VERTEX.EPI_SERVER.USERNAME");
			BASIC_CONFIG_TRUSTED_ID = readConfigDetails.getProperty("TEST.VERTEX.EPI_SERVER.TRUSTED_ID");
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	/***
	 * This is a method for launching Episerver Admin URL
	 *
	 */
	protected void launchAdminLoginPage( )
	{
		VertexLogger.log(String.format("Launching Episerver Administration URL - %s", EPI_SERVER_ADMIN_URL),
			VertexLogLevel.DEBUG);
		driver.get(EPI_SERVER_ADMIN_URL);
	}

	/**
	 * Launches Epi Server's commerce manager
	 */
	protected void launchCommerceManagerPage() {
		VertexLogger.log(String.format("Launching Episerver Page - %s", EpiDataCommon.EpiURLs.EPI_COMMERCE_MANAGER_URL.text),
				VertexLogLevel.DEBUG);
		driver.get(EpiDataCommon.EpiURLs.EPI_COMMERCE_MANAGER_URL.text);
	}

	/**
	 * Launches Epi Server's Storefront
	 */
	protected void launch324EpiStorePage() {
		VertexLogger.log(String.format("Launching Episerver Page - %s", EpiDataCommon.EpiURLs.EPI_STORE_URL_325.text),
				VertexLogLevel.DEBUG);
		driver.get(EpiDataCommon.EpiURLs.EPI_STORE_URL_325.text);
	}

	/**
	 * Launches Epi Server's Storefront page
	 */
	protected void launchEpiStorePage() {
		VertexLogger.log(String.format("Launching Episerver Page - %s", EpiDataCommon.EpiURLs.EPI_STORE_URL.text),
				VertexLogLevel.DEBUG);
		driver.get(EpiDataCommon.EpiURLs.EPI_STORE_URL.text);
	}

	protected void launchStoreFrontPage( )
	{
		VertexLogger.log(String.format("Launching Episerver Store Front URL - %s", EPI_SERVER_STOREFRONT_URL),
			VertexLogLevel.DEBUG, EpiBaseTest.class);
		driver.get(EPI_SERVER_STOREFRONT_URL);
	}

	/***
	 * This is a common re-usable method for both Episerver Admin and Storefront
	 * applications login
	 *
	 */
	protected void logInAsUser( String username, String Password )
	{
		EpiStoreLoginPage login = new EpiStoreLoginPage(driver);
		login.enterUsername(username);
		login.enterPassword(Password);
		login.clickLoginButton();
		login.waitForPageLoad();
	}

	/***
	 * This is a method for logging into Episerver Admin Portal and returning an
	 * object to AdminDashboard page
	 *
	 */
	protected EpiAdminHomePage logInAsAdminUser( )
	{
		VertexLogger.log("Signing in to Episerver Administration...");
		EpiAdminHomePage admindashboardpage = new EpiAdminHomePage(driver);
		// open Episever administration login page
		this.launchAdminLoginPage();
		// login as EpiServer administration user
		this.logInAsUser(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);
		return admindashboardpage;
	}

	/**
	 * Used for login to Epi Server's Commerce Manager
	 */
	protected EpiAdminHomePage loginToCommerceManager() {
		VertexLogger.log("Signing in to Episerver Commerce Manager...");
		// open Epi-sever commerce manager login page
		this.launchCommerceManagerPage();
		// login as EpiServer administration user
		this.logInAsUser(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);
		EpiAdminHomePage adminHomePage = new EpiAdminHomePage(driver);
		adminHomePage.waitForPageLoad();
		return adminHomePage;
	}

	/***
	 * This is a method for logging into Episerver Portal and returning an object to
	 * portalhomepage
	 *
	 */
	protected EpiStoreFrontHomePage logInAsCustomer( )
	{
		VertexLogger.log("Signing in to Episerver StoreFront...", EpiBaseTest.class);
		EpiStoreFrontHomePage portalhomepage = new EpiStoreFrontHomePage(driver);
		// open Episever portal login page
		this.launchStoreFrontPage();
		// login as EpiServer Storefront user
		this.logInAsUser(EPI_SERVER_STOREFRONT_USERNAME, EPI_SERVER_STOREFRONT_PASSWORD);
		return portalhomepage;
	}

	protected String getUsername( )
	{
		return BASIC_CONFIG_USERNAME;
	}

	protected String getAddressUrl( )
	{
		return ADDRESS_VALIDATION_URL;
	}

	protected String getTaxCalUrl( )
	{
		return TAX_CALCULATION_URL;
	}

	protected String getTrustedId( )
	{
		System.out.println("Value is" + BASIC_CONFIG_TRUSTED_ID);
		return BASIC_CONFIG_TRUSTED_ID;
	}

	/**
	 * Generates random 8-digit number and appends connector id as a tracking number
	 *
	 * @return tracking number
	 */
	protected String generateTrackingNo() {
		return "EPI" + ThreadLocalRandom.current().nextInt(999999999);
	}

	/**
	 * Sets default address of Warehouse for EPI 3.2.4.x environment
	 *
	 * @param whName Warehouse name which default address should be updated
	 */
	protected void setDefaultWarehouseAddress324(String whName) {
		quitDriver();
		createChromeDriver();

		// Launch Epi-Server's store
		launch324EpiStorePage();

		// Login to storefront
		EpiStoreLoginPage storeLoginPage = new EpiStoreLoginPage(driver);
		storeLoginPage.loginToEpiStore(EpiDataCommon.EpiCredentials.EPI_324_STORE_USER.text, EpiDataCommon.EpiCredentials.EPI_324_STORE_PASS.text);
		EpiStoreFrontHomePage storeFrontHomePage = new EpiStoreFrontHomePage(driver);
		storeFrontHomePage.navigateToHomePage();

		// navigate to administration option from left panel
		EpiAdminHomePage adminHomePage = new EpiAdminHomePage(driver);
		adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.COMMERCE.text);
		adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CommerceMenuOptions.ADMINISTRATION.text);

		// Goto warehouse setting & update warehouse address as default
		EpiAdministrationHomePage administrationHomePage = new EpiAdministrationHomePage(driver);
		administrationHomePage.goToTab(NavigationAndMenuOptions.AdministrationTabs.WAREHOUSES.text);
		EpiWarehousePage warehousePage = new EpiWarehousePage(driver);
		warehousePage.setDefaultAddressOfWarehouse(whName);
	}
}
