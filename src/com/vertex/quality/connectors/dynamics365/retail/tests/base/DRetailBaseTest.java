package com.vertex.quality.connectors.dynamics365.retail.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.dynamics365.retail.enums.DRetailStores;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailLoginPage;
import org.testng.annotations.Test;

/**
 * collects setup actions, constants, utility functions, etc which are shared by the test classes for D365 Finance
 *
 * @author sgupta
 */

@Test(groups = "d365")
public abstract class DRetailBaseTest extends VertexUIBaseTest<DRetailHomePage>
{
	protected final DBConnectorNames connectorName = DBConnectorNames.D365_RETAIL;
	protected final DBEnvironmentNames environmentName = DBEnvironmentNames.QA;

	protected final DBEnvironmentDescriptors dfinancePrimaryEnvironmentDescriptor
		= DBEnvironmentDescriptors.D365_RETAIL;
	private final String CONFIG_DETAILS_FILE_PATH = CommonDataProperties.CONFIG_DETAILS_FILE_PATH;

	public String DRETAIL_USERNAME = null;
	public String DRETAIL_PASSWORD = null;
	public String DRETAIL_URL = null;
	public static DRetailStores STORE = DRetailStores.HOUSTON;

	protected final int defaultTableRow = 1;

	@Override
	public DRetailHomePage loadInitialTestPage( )
	{
		initSetup();
		DRetailHomePage homePage = logInAsAdminUser();

		return homePage;
	}

	/**
	 * Get the data from resources folder -> property file
	 */
	protected void initSetup( )
	{
		try
		{
			String oldEnv = "https://vtxauto10p335fa3f037d2a974b7pos.cloudax.dynamics.com/";
			String cloudEnv = "https://vtxcloud34a96596021f89d6devpos.axcloud.dynamics.com";
			String newEnv = "https://vtxnewauto10p40326431b415193b50pos.cloudax.dynamics.com/";
			String nextEnv = "https://vtx-nextgen5f481fbd71acc5eddevpos.axcloud.dynamics.com";
			String hempEnv = "https://vtx-qa-hempelfccd41715f7b76bddevpos.axcloud.dynamics.com";
			String featureEnv = "https://vtx-feature7c7832bed25699aadevpos.axcloud.dynamics.com/";
			String matFirEnv = "https://vtx-qa-mtrsfd778d118d5f02078devpos.axcloud.dynamics.com";
			//String envToRun = "next";
			String envToRun = System.getProperty("url");
			if(envToRun != null)
			{
				if (envToRun.contains("new"))
					DRETAIL_URL=newEnv;
				else if(envToRun.contains("next"))
					DRETAIL_URL=nextEnv;
				else if(envToRun.contains("feature"))
					DRETAIL_URL=featureEnv;
				else if(envToRun.contains("hemp"))
					DRETAIL_URL=hempEnv;
				else if(envToRun.contains("matFir"))
					DRETAIL_URL=matFirEnv;
				else if(envToRun.contains("cloud"))
					DRETAIL_URL=cloudEnv;
				else if(envToRun.contains("https"))
					DRETAIL_URL=envToRun;
				else
					DRETAIL_URL=oldEnv;
			}
			else
			{
				DRETAIL_URL = oldEnv;
			}
			ReadProperties readConfigDetails = new ReadProperties(CONFIG_DETAILS_FILE_PATH);

			DRETAIL_USERNAME = readConfigDetails.getProperty("TEST.VERTEX.D365.RETAIL.USERNAME");
			DRETAIL_PASSWORD = readConfigDetails.getProperty("TEST.VERTEX.D365.RETAIL.PASSWORD");
		}
		catch ( Exception e )
		{
			VertexLogger.log("Failure initializing test data");
			e.printStackTrace();
		}
	}

	/**
	 * Launch Dynamics365 Retail login page
	 */
	protected DRetailLoginPage launchLoginPage( )
	{
		DRetailLoginPage loginPage = null;
		driver.manage().window().maximize();
		VertexLogger.log(String.format("Launching Dynamics365 Retail URL - %s", DRETAIL_URL),
			VertexLogLevel.TRACE);
		driver.get(DRETAIL_URL);

		loginPage = new DRetailLoginPage(driver);
		return loginPage;
	}

	/**
	 * Common method for enter valid user name and password ,login into Dynamics365 Finance &
	 * Operations application.
	 *
	 * @return the home page of the d365 retail website after signing in
	 */
	protected DRetailHomePage logInAsAdminUser( )
	{
		DRetailHomePage homePage = null;

		DRetailLoginPage loginPage = this.launchLoginPage();
		loginPage.activateStore(STORE.value);
		driver.manage().window().maximize();

		System.out.println(STORE);
		homePage = this.logInAsUser(loginPage, DRETAIL_USERNAME, DRETAIL_PASSWORD);
		VertexLogger.log("Signed in to D365 Retail", VertexLogLevel.TRACE);
		return homePage;
	}

	/**
	 * Enter user name , password and click on 'login' button
	 *
	 * @param loginPage the page where the test signs into the site
	 * @param username  the test account's username
	 * @param password  the test account's password
	 *
	 * @return the home page of the d365 retail site after signing in
	 */
	protected DRetailHomePage logInAsUser( final DRetailLoginPage loginPage, final String username,
		final String password )
	{
		DRetailHomePage homePage = null;

		loginPage.setUsername(username);
		loginPage.setPassword(password);
		loginPage.clickLoginButton();
		loginPage.closeTutorial();

		return homePage;
	}
 }
