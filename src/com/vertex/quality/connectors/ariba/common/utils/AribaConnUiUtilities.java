package com.vertex.quality.connectors.ariba.common.utils;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.pojos.VertexEnvironmentAccessInfo;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavConfigurationMenuOption;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavOption;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnBasePage;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnMenuBasePage;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnComponentTaxTypesPage;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnCustomFieldMappingPage;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnExternalTaxTypesPage;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnTaxRulesPage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnHomePage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnSignOnPage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.vertex.quality.connectors.ariba.connector.tests.base.AribaConnBaseTest.SOLE_CONFIG_USER_INDEX;

/**
 * utility class contains all the necessary methods to interact with the Ariba connector UI
 *
 * @author osabha
 */
public class AribaConnUiUtilities
{
	protected WebDriver driver;
	protected final By customFieldMappingID = By.id("custom-field-map-link");

	public AribaConnUiUtilities( WebDriver driver )
	{
		this.driver = driver;

		environmentChoice = getEnvironmentTarget();
	}

	protected static final String ENVIRONMENT_CHOICE_ENV_VARIABLE = "ariba.ui.environment";
	protected static final DBEnvironmentDescriptors DEFAULT_ENVIRONMENT_CHOICE
		= DBEnvironmentDescriptors.ARIBA2_0_CONFIG;
	protected DBEnvironmentDescriptors environmentChoice;

	protected String currConfigSignOnUrl = "https://ariba-stage-primary.cst-stage.vtxdev.net/vertex-ariba-2.0/";

	protected String configUsername = "ariba_connector_user";
	protected String configPassword = "ConnectorAriba01";

	/**
	 * retrieves the target environment descriptor
	 *
	 * @return descriptor of the target environment
	 */
	public DBEnvironmentDescriptors getEnvironmentTarget( )
	{
		DBEnvironmentDescriptors environmentChoice = DEFAULT_ENVIRONMENT_CHOICE;
		String environmentChoiceString = System.getenv(ENVIRONMENT_CHOICE_ENV_VARIABLE);
		if ( environmentChoiceString != null )
		{
			try
			{
				environmentChoice = DBEnvironmentDescriptors.valueOf(environmentChoiceString);
			}
			catch ( IllegalArgumentException e )
			{
				final String badEnvironmentDescriptorEnvironmentVariableMessage = String.format(
					"Invalid environment descriptor %s was specified for the ariba connector UI testing; " +
					"this had been stored in the test process's environment variable", environmentChoiceString);
				VertexLogger.log(badEnvironmentDescriptorEnvironmentVariableMessage);
			}
		}
		return environmentChoice;
	}

	/**
	 * retrieves the environment access info going by the environment descriptor
	 *
	 * @param environmentDescriptor variable describing the target environment
	 *
	 * @return instance of the vertex environment access info class
	 */
	public VertexEnvironmentAccessInfo getEnvironmentCredentials( DBEnvironmentDescriptors environmentDescriptor )
	{
		VertexEnvironmentAccessInfo accessInfo = null;
		try
		{
			EnvironmentInformation environmentInfo = SQLConnection.getEnvironmentInformation(DBConnectorNames.ARIBA,
				DBEnvironmentNames.QA, environmentChoice);
			EnvironmentCredentials credentials = SQLConnection.getEnvironmentCredentials(environmentInfo);
			String environmentUrl = environmentInfo.getEnvironmentUrl();

			accessInfo = new VertexEnvironmentAccessInfo(environmentUrl, List.of(credentials));
		}
		catch ( Exception e )
		{
			VertexLogger.log("failed to set up a test of the Ariba connector UI", VertexLogLevel.ERROR);
			e.printStackTrace();
		}

		return accessInfo;
	}

	/**
	 * logs into this configuration site
	 *
	 * @param signOnPage the configuration site's sign-on webpage
	 *
	 * @return the page that loads immediately after successfully signing into this configuration site
	 *
	 * @author ssalisbury
	 */
	public AribaConnHomePage signInToConfig( final AribaConnSignOnPage signOnPage )
	{
		AribaConnHomePage homePage = null;
		signOnPage.clickHereToLogin();
		try{
			Thread.sleep(2000);
			signOnPage.enterUsername(configUsername);
			signOnPage.enterPassword(configPassword);

		}catch(TimeoutException | InterruptedException e){

		}
		homePage = signOnPage.login();
		return homePage;
	}

	/**
	 * loads the configuration site
	 *
	 * @return this site's login screen
	 *
	 * @author ssalisbury
	 */
	public AribaConnSignOnPage loadConfig( )
	{
		AribaConnSignOnPage signOnPage = null;
		driver.get(currConfigSignOnUrl);
		signOnPage = new AribaConnSignOnPage(driver);
		return signOnPage;
	}

	/**
	 * loads the configuration site, checks whether it's already logged in, then logs in if it wasn't already
	 * Only supposed to be used when the site might recognize the
	 * user as already being signed in from the previous time the site was accessed
	 *
	 * @return this site's home page
	 *
	 * @author ssalisbury
	 */
	public AribaConnHomePage loadConfigHome( )
	{
		AribaConnHomePage homePage = null;

		AribaConnSignOnPage maybeSignOnPage = loadConfig();

		AribaConnHomePage maybeHomePage = new AribaConnHomePage(driver);
		if ( maybeHomePage.isCurrentPage() )
		{
			homePage = maybeHomePage;
		}
		else
		{
			homePage = signInToConfig(maybeSignOnPage);
		}
		return homePage;
	}

	/**
	 * navigates to the given configuration menu page
	 *
	 * @param priorPage  the webpage on the configuration site from which
	 *                   the test is opening the configuration menu webpage indicated by menuOption
	 * @param menuOption the indicator of which configuration menu page to go to
	 *
	 * @return the given configuration menu
	 *
	 * @author ssalisbury
	 */
	public <T extends AribaConnBasePage> T openConfigMenu( final AribaConnBasePage priorPage,
		final AribaConnNavConfigurationMenuOption menuOption )
	{
		T configMenuPage = null;

		priorPage.navPanel.waitForNavOptionEnabled(AribaConnNavOption.CONFIGURATION_MENU);
		if ( !priorPage.navPanel.isConfigurationMenuDropdownExpanded() )
		{
			priorPage.navPanel.clickNavOption(AribaConnNavOption.CONFIGURATION_MENU);
			priorPage.navPanel.waitForConfigMenuDropdownExpanded();
		}

		priorPage.navPanel.waitForNavConfigurationMenuOptionEnabled(menuOption);
		configMenuPage = priorPage.navPanel.clickNavConfigurationMenuOption(menuOption);

		return configMenuPage;
	}

	/**
	 * loads and signs into the site and then navigates to the given configuration menu page
	 *
	 * @param menuOption the indicator of which configuration menu page to go to
	 *
	 * @return the given configuration menu
	 *
	 * @author ssalisbury
	 */
	public <T extends AribaConnMenuBasePage> T loadConfigMenu( AribaConnSignOnPage signOnPage,
		final AribaConnNavConfigurationMenuOption menuOption )
	{
		T configMenuPage = null;

		AribaConnHomePage homePage = signInToConfig(signOnPage);

		configMenuPage = openConfigMenu(homePage, menuOption);

		return configMenuPage;
	}

	/**
	 * loads and signs into the site and then navigates to the given configuration menu page
	 *
	 * @return the given configuration menu
	 *
	 * @author alewiw
	 */
	public AribaConnCustomFieldMappingPage loadConfigMenuOne(final AribaConnNavConfigurationMenuOption menuOption) {

		AribaConnCustomFieldMappingPage customFieldMappingPage = new AribaConnCustomFieldMappingPage(driver);
		customFieldMappingPage.navPanel.waitForNavOptionEnabled(AribaConnNavOption.CONFIGURATION_MENU);
		customFieldMappingPage.navPanel.clickNavOption(AribaConnNavOption.CONFIGURATION_MENU);
		customFieldMappingPage.navPanel.clickNavConfigurationMenuOption(menuOption);
		WebElement customFieldMapping = driver.findElement(customFieldMappingID);
		customFieldMapping.click();

		return customFieldMappingPage;
	}

	/**
	 * deletes all rows of configuration data in the configuration menus for the given tenant
	 *
	 * @param tenantId           the variant id of the tenant whose data rows should all be deleted
	 * @param prevPostSignOnPage the previous page that was loaded on the site
	 *                           this can't be the sign on page
	 *
	 * @return the page which is open after this deletion process completes
	 */
	public AribaConnMenuBasePage wipeTenantDataRows( final String tenantId, final AribaConnBasePage prevPostSignOnPage )
	{
		AribaConnCustomFieldMappingPage customMappingPage = openConfigMenu(prevPostSignOnPage,
			AribaConnNavConfigurationMenuOption.MODIFY_CUSTOM_FIELD_MAPPING);

		customMappingPage.tenantSelector.selectTenant(tenantId);

		customMappingPage.deleteAllConfigRows();

		AribaConnTaxRulesPage taxRulesPage = openConfigMenu(customMappingPage,
			AribaConnNavConfigurationMenuOption.MODIFY_TAX_RULES);
		taxRulesPage.deleteAllConfigRows();

		AribaConnComponentTaxTypesPage componentTaxTypesPage = openConfigMenu(taxRulesPage,
			AribaConnNavConfigurationMenuOption.ARIBA_COMPONENT_TAX_TYPES_MAINTENANCE);
		componentTaxTypesPage.deleteAllConfigRows();

		AribaConnExternalTaxTypesPage externalTaxTypesPage = openConfigMenu(componentTaxTypesPage,
			AribaConnNavConfigurationMenuOption.ARIBA_EXTERNAL_TAX_TYPES_MAINTENANCE);
		externalTaxTypesPage.deleteAllConfigRows();

		return externalTaxTypesPage;
	}
}