package com.vertex.quality.connectors.taxlink.ui_e2e.base;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.taxlink.common.TaxLinkDatabase;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterGroups;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains the Base Page for extending into each Page Test Class
 *
 * @author mgaikwad
 */
public class TaxLinkBaseTest extends VertexUIBaseTest
{
	private boolean isDriverServiceProvisioned;
	private boolean isDriverHeadlessMode;
	private boolean isSeleniumHost;
	public Path rootPath = Paths
		.get("")
		.toAbsolutePath();
	String textFilePath = File.separator + "resources" + File.separator + "textfiles" + File.separator + "taxlink";
	Path filePath = Paths.get(this.rootPath.toString() + textFilePath);
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
	protected static String TAXLINK_DB_USERNAME;
	protected static String TAXLINK_DB_PASSWORD;
	protected static String ORACLE_ERP_USERNAME;
	protected static String ORACLE_ERP_PASSWORD;
	protected static String ORACLE_ERP_URL;
	protected static String VCS_URL;
	protected static String VCS_USERNAME;
	protected static String VCS_PASSWORD;
	protected static String VOD_ORA901_URL;
	protected static String VOD_ORA901_USERNAME;
	protected static String VOD_ORA901_PASSWORD;
	protected static String VOD_ORA901_EMAIL;

	public TaxLinkSignOnPage signOnPage;
	public TaxLinkHomePage homePage;
	public TaxLinkOnboardingDashboardPage instancePage;
	public TaxLinkBusinessUnitPage buPage;
	public TaxLinkArTransactionTypePage arTransType;
	public TaxLinkArTransactionSourcePage arTransSrc;
	public TaxLinkLegalEntityPage lePage;
	public TaxLinkAPInvSrcPage apInvSrcPage;
	public TaxLinkPreCalcRulesMappingPage preCalcRulesMapping;
	public TaxLinkPostCalcRulesMappingPage postCalcRulesMapping;

	public TaxLinkPreCalcInvRulesMappingPage preCalcInvRulesMapping;
	public TaxLinkJournalInvOutputFilePage journalInvOutputFilePage;
	public TaxLinkProjectInvOutputFilePage projectInvOutputFilePage;
	public TaxLinkAPTaxActRangesPage taRangePage;
	public TaxLinkApTaxCalcExclPage apTaxCalcExclPage;
	public TaxLinkArTaxCalcExclPage arTaxCalcExclPage;
	public TaxLinkPTDEJobPage ptdeJobPage;
	public TaxLinkUIUtilities utils;
	public TaxLinkDatabase dbPage;

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
			ORACLE_ERP_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.DEV.USERNAME");
			ORACLE_ERP_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.DEV.PASSWORD");
			ORACLE_ERP_URL = readEnvUrls.getProperty("DEV.ENV.ORACLEERP.URL");
			VCS_URL = readEnvUrls.getProperty("DEV.ENV.VCS.URL");
			VCS_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.DEV.VCS.USERNAME");
			VCS_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.DEV.VCS.PASSWORD");
			VOD_ORA901_URL = readEnvUrls.getProperty("TEST.ENV.VOD.ORA901.URL");
			VOD_ORA901_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.DEV.VOD.ORA901.USERNAME");
			VOD_ORA901_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.DEV.VOD.ORA901.PASSWORD");
			VOD_ORA901_EMAIL = readCredentials.getProperty("TEST.CREDENTIALS.DEV.VOD.ORA901.EMAIL");
		}
		catch ( Exception e )
		{
			e.printStackTrace();
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
	 * Method to launch browser and navigate to TaxLink URL
	 * to be called in @BeforeMethod in Test class
	 */
	public void initialization_Taxlink( )
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
			signOnPage.userLogin_Taxlink(TAXLINK_CLOUD_USERNAME, TAXLINK_CLOUD_PASSWORD);
		}
		else
		{
			signOnPage.userLogin_Taxlink(TAXLINK_VOD_USERNAME, TAXLINK_VOD_PASSWORD);
		}
	}

	/**
	 * Method to launch browser and navigate to Oracle ERP URL
	 * to be called in @BeforeMethod in Test class
	 */
	public void initialization_OracleERP( )
	{
		driver.get(ORACLE_ERP_URL);
		signOnPage.userLogin_OracleERP(ORACLE_ERP_USERNAME, ORACLE_ERP_PASSWORD);
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
	 * Method to create a text file, if doesn't exists already
	 * and write data into it
	 *
	 * @param text
	 */

	public void writeToFile( String text ) throws IOException
	{
		Path rootPath = Paths
			.get("")
			.toAbsolutePath();

		String textFilePath = File.separator + "resources" + File.separator + "textfiles" + File.separator + "taxlink";

		Path filePath = Paths.get(rootPath.toString() + textFilePath);
		Path fileCreatePath = filePath.resolve("taxlink_OracleERP.txt");
		VertexLogger.log("File created in path: " + fileCreatePath);

		try
		{
			if ( !Files.exists(fileCreatePath, LinkOption.NOFOLLOW_LINKS) )
			{
				Files.createFile(fileCreatePath);
				Files.write(fileCreatePath, text.getBytes());
			}
			else
			{
				Files.write(fileCreatePath, text.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
			}
		}
		catch ( FileAlreadyExistsException ignored )
		{
		}
	}

	/**
	 * Method to Read file on the given path and return
	 * String text from file and the Path of the file
	 *
	 * @return
	 */
	public List<Object> getFileReadPath( )
	{
		Path fileCreatePath = filePath.resolve("taxlink_OracleERP.txt");

		String textRead = null;
		try
		{
			textRead = Files
				.lines(fileCreatePath)
				.collect(Collectors.joining(System.lineSeparator()));
			VertexLogger.log("Reading Text: " + textRead);
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

		List<Object> fileDetails = Arrays.asList(textRead, fileCreatePath);
		return fileDetails;
	}

	/**
	 * Logout method for Oracle ERP
	 * to be called in @AfterMethod in Test class
	 */

	public void tearDown( )
	{
		signOnPage.userLogout_OracleERP();
	}

	/**
	 * Method to run clean instance after whole test suite is run
	 */
	@AfterGroups(alwaysRun = true, groups = { "taxlink_ui_e2e_regression" })
	public void cleanInstance( ) throws Exception
	{
		dbPage = new TaxLinkDatabase();
		//dbPage.db_deleteInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
	}
}

