package com.vertex.quality.connectors.taxlink.ui.base;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.pages.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * This class contains the Base Page for extending into each Page Test Class
 *
 * @author mgaikwad, Shilpi.Verma
 */
public class TaxLinkBaseTest extends VertexUIBaseTest
{
	public Path rootPath = Paths
		.get("")
		.toAbsolutePath();

	private boolean isDriverServiceProvisioned;
	private boolean isDriverHeadlessMode;
	private boolean isSeleniumHost;

	private static ReadProperties readEnvUrls;
	private static ReadProperties readCredentials;
	private static String TEST_CREDENTIALS_FILE_PATH = CommonDataProperties.TEST_CREDENTIALS_FILE_PATH;
	private static String ENV_PROP_FILE_PATH = CommonDataProperties.ENV_PROP_FILE_PATH;
	protected static String TAXLINK_VOD_USERNAME;
	protected static String TAXLINK_VOD_PASSWORD;
	protected static String TAXLINK_CLOUD_USERNAME;
	protected static String TAXLINK_CLOUD_PASSWORD;
	protected static String TAXLINK_URL;
	protected static String TAXLINK_DB_URL;
	protected static String  TAXLINK_DB_94_URL;
	protected static String TAXLINK_DB_USERNAME;
	protected static String TAXLINK_DB_PASSWORD;
	protected static String TAXLINK_DB94_USERNAME;
	protected static String TAXLINK_DB94_PASSWORD;
	protected static String TAXLINK_DB_POSTGRES_URL;
	protected static String TAXLINK_DB_POSTGRES_USERNAME;
	protected static String TAXLINK_DB_POSTGRES_PASSWORD;
	protected static String VCS_URL;
	protected static String VCS_USERNAME;
	protected static String VCS_PASSWORD;
	protected static String VCS_QA_URL;
	protected static String VCS_QA_USERNAME;
	protected static String VCS_QA_PASSWORD;
	protected static String VOD_ORA901_URL;
	protected static String VOD_ORA901_USERNAME;
	protected static String VOD_ORA901_PASSWORD;
	protected static String VOD_ORA901_EMAIL;

	protected static String ERP_USERNAME;
	protected static String ERP_PASSWORD;
	protected static String ERP_TRUSTEDID;

	public TaxLinkOnboardingDashboardPage instancePage;
	public TaxLinkSignOnPage signOnPage;
	public TaxLinkHomePage homePage;
	public TaxLinkBusinessUnitPage buPage;
	public TaxLinkLegalEntityPage lePage;
	public TaxLinkArTransactionTypePage arTransactionTypePage;
	public TaxLinkArTransactionSourcePage arTransactionSourcePage;
	public TaxLinkPreCalcRulesMappingPage preRulesMapping;
	public TaxLinkPostCalcRulesMappingPage postRulesMapping;
	public TaxLinkConditionSetsPage condSetRulesMapping;
	public TaxLinkInvPreCalcRulesMappingPage invPreCalcRules;

	public TaxLinkJournalOutputFilePage journalOutputFilePage;

	public TaxLinkProjectOutputFilePage projectOutputFilePage;
	public TaxLinkInvConditionSetsPage invCondSetRulesMapping;
	public TaxLinkDefaultMappingPage defaultMapping;
	public TaxLinkOfflineBIPExtractJobsPage offBIPPage;
	public TaxLinkAPInvoiceSourcePage apInvPage;
	public TaxLinkUserProfileOptionsPage userPrOptPage;
	public TaxLinkSuppliesPage suppliesPage;
	public TaxLinkAPScanINVSrcPage apScanInvSrcPage;
	public TaxLinkLookupsPage lookupsPage;
	public TaxLinkMonitoringPage retryJobs;
	public TaxLinkMonitoringPage pollingJobs;
	public TaxLinkMonitoringPage transMonitoring;
	public TaxLinkMonitoringPage summaryReports;
	public TaxLinkAPAssumeTaxSrcPage apAssumeTaxSrcPage;
	public TaxLinkAPTaxCalcExclPage apTaxCalcExclPage;
	public TaxLinkAPTaxActionRangesPage apTaxActRangesPage;
	public TaxLinkUsersPage usersPage;
	public TaxLinkCloneInstancePage cloneInstancePage;
	public TaxLinkPoTaxCalcExclPage poTaxCalcExclPage;
	public TaxLinkRealTimeBIPPage realTimeBIPPage;
	public TaxLinkSupplierTypePage suppTypePage;
	public TaxLinkAPTaxActionOverridePage apTaxActOverridePage;
	public TaxLinkTouchlessPage touchlessPage;
	public TaxLinkRolesPage roles;
	public TaxLinkGenerateNewPasswordPage newPwdPage;
	public TaxlinkSessionReload_VCSPage sessionReloadVSCPage;

	public TaxLinkUIUtilities utils;
	public TaxLinkCleanUpInstancePage cleanUpInstancePage;

	public TaxLinkBaseTest( )
	{
		initializeVariables();
	}

	/**
	 * this method initializes credentials for TaxLink login page
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
				VertexLogger.log("Test Credentials properties file is not found", VertexLogLevel.ERROR);
			}

			File testPropFilePath = new File(ENV_PROP_FILE_PATH);
			if ( testPropFilePath != null && testPropFilePath.exists() )
			{
				readEnvUrls = new ReadProperties(ENV_PROP_FILE_PATH);
			}
			else
			{
				VertexLogger.log("Environment details properties file is not found", VertexLogLevel.ERROR);
			}
			TAXLINK_VOD_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.DEV.VOD.USERNAME");
			TAXLINK_VOD_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.DEV.VOD.PASSWORD");
			TAXLINK_CLOUD_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.DEV.VERTEXCLOUD.USERNAME");
			TAXLINK_CLOUD_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.DEV.VERTEXCLOUD.PASSWORD");
			TAXLINK_URL = readEnvUrls.getProperty("DEV.ENV.TAXLINK.URL");
			TAXLINK_DB_URL = readEnvUrls.getProperty("DB.ENV.TAXLINK.URL");
			TAXLINK_DB_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.DB.USERNAME");
			TAXLINK_DB_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.DB.PASSWORD");
			TAXLINK_DB_94_URL = readEnvUrls.getProperty("DB.ENV.TAXLINK_94.URL");
			TAXLINK_DB94_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.DB94.USERNAME");
			TAXLINK_DB94_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.DB94.PASSWORD");
			TAXLINK_DB_POSTGRES_URL = readEnvUrls.getProperty("DB.ENV.TAXLINK_POSTGRES.URL");
			TAXLINK_DB_POSTGRES_USERNAME = readEnvUrls.getProperty("TEST.CREDENTIALS.DB_POSTGRES.USERNAME");
			TAXLINK_DB_POSTGRES_PASSWORD = readEnvUrls.getProperty("TEST.CREDENTIALS.DB_POSTGRES.PASSWORD");
			VCS_URL = readEnvUrls.getProperty("DEV.ENV.VCS.URL");
			VCS_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.DEV.VCS.USERNAME");
			VCS_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.DEV.VCS.PASSWORD");
			VCS_QA_URL = readEnvUrls.getProperty("TEST.ENV.VCS.URL");
			VCS_QA_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.QA.VCS.USERNAME");
			VCS_QA_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.QA.VCS.PASSWORD");
			VOD_ORA901_URL = readEnvUrls.getProperty("TEST.ENV.VOD.ORA901.URL");
			VOD_ORA901_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.DEV.VOD.ORA901.USERNAME");
			VOD_ORA901_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.DEV.VOD.ORA901.PASSWORD");
			VOD_ORA901_EMAIL = readCredentials.getProperty("TEST.CREDENTIALS.DEV.VOD.ORA901.EMAIL");
			ERP_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.QA.ERP.USERNAME");
			ERP_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.QA.ERP.PASSWORD");
			ERP_TRUSTEDID = readCredentials.getProperty("TEST.CREDENTIALS.QA.ERP.TRUSTEDID");
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * Method to launch browser and navigate to TaxLink URL
	 * to be called in @BeforeMethod in each Test class
	 */
	public void initialization( )
	{
		driver.get(TAXLINK_URL);
		driver
			.manage()
			.window()
			.maximize();

		if ( driver
			.getTitle()
			.contains("Vertex Cloud") )
		{
			signOnPage.userLogin(TAXLINK_CLOUD_USERNAME, TAXLINK_CLOUD_PASSWORD);
		}
		else
		{
			signOnPage.userLogin(TAXLINK_VOD_USERNAME, TAXLINK_VOD_PASSWORD);
		}
	}

	/**
	 * Method to launch VCS in browser and navigate to TaxLink URL
	 * to be called in @BeforeMethod in each Test class for smoke testing on VCS
	 */
	public void initialization_VCS( )
	{
		driver.get(VCS_URL);
		driver
			.manage()
			.window()
			.maximize();

		signOnPage.userLogin_VCS(VCS_USERNAME, VCS_PASSWORD);
		signOnPage.launchStageAccelerator();
	}

	/**
	 * Method to launch VCS in browser and navigate to TaxLink URL
	 * to be called in @BeforeMethod in each Test class for smoke testing on VCS
	 */
	public void initialization_QA_VCS( )
	{
		driver.get(VCS_QA_URL);
		driver
			.manage()
			.window()
			.maximize();

		signOnPage.userLogin_VCS(VCS_QA_USERNAME, VCS_QA_PASSWORD);
	}

	/**
	 * Method to launch VOD in browser and navigate to TaxLink URL on ORA901 platform
	 * to be called in @BeforeMethod in each Test class
	 */
	public void initialization_VOD_ORA901( )
	{
		driver.get(VOD_ORA901_URL);
		driver
			.manage()
			.window()
			.maximize();
		signOnPage.clickOnEntLoginButton();
		signOnPage.entLoginWithEmailORA901(VOD_ORA901_EMAIL);
		signOnPage.userLogin_VOD_ORA901(VOD_ORA901_USERNAME, VOD_ORA901_PASSWORD);
	}

	/**
	 * Overridden Method to create chrome browser and
	 * assign default download path for Export to CSV
	 * in TaxLink URL
	 */
	protected void createChromeDriver( )
	{
		isDriverServiceProvisioned = Boolean.getBoolean("services.webdriver.provisioned");
		isDriverHeadlessMode = Boolean.getBoolean("services.webdriver.headless");
		isSeleniumHost = Boolean.getBoolean("services.webdriver.host.selenium");

		String fileDownloadPath = String.valueOf(getFileDownloadPath());
		HashMap<String, Object> chromePrefs = new HashMap<>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", fileDownloadPath);

		// Add ChromeDriver-specific capabilities through ChromeOptions.
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("window-size=1920,1080");
		options.addArguments("--disable-infobars");

		if ( !isDriverServiceProvisioned )
		{
			options.addArguments("--incognito");
			options.addArguments("--remote-debugging-port=9022");
		}
		if ( isDriverHeadlessMode )
		{
			options.addArguments("--headless");
		}
		driver = isDriverServiceProvisioned ? new RemoteWebDriver(getDriverServiceUrl(), options) : new ChromeDriver(
			options);
	}

	/**
	 * Method to check service provisioning
	 * if URL belongs to remote web driver
	 *
	 * @return URL
	 */
	private URL getDriverServiceUrl( )
	{
		try
		{
			String hostname = "localhost"; 
            if(isSeleniumHost) {
                hostname = "selenium"; //instead of "localhost" for GitHub Actions
            }
            return new URL("http://" + hostname + ":4444/wd/hub");
		}
		catch ( MalformedURLException e )
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method to get the file download path
	 */
	public Path getFileDownloadPath( )
	{
		Path resourcePath = this.rootPath;
		String csvDir = File.separator + "resources" + File.separator + "csvfiles" + File.separator + "taxlink";
		resourcePath = Paths.get(resourcePath.toString() + csvDir);
		VertexLogger.log(String.valueOf(resourcePath));

		return resourcePath;
	}

	/**
	 * Method to check if user has been logged out
	 * from Taxlink Application and login page is visible
	 */
	public void tearDown( )
	{
		if ( signOnPage.userLogout() )
		{
			VertexLogger.log("User has been logged out successfully!!");
		}
		else
		{
			VertexLogger.log("Failed to Logout!!");
		}
	}

	/**
	 * Method to run clean instance after whole test suite is run
	 */
	/*@AfterGroups(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void cleanInstance( ) throws Exception
	{
		dbPage = new TaxLinkDatabase();
		dbPage.db_deleteInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
	}*/
}

