package com.vertex.quality.connectors.ariba.portal.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.common.utils.AribaAPITestUtilities;
import com.vertex.quality.connectors.ariba.portal.enums.AribaConnectorIntegrationEndpoints;
import com.vertex.quality.connectors.ariba.portal.enums.AribaPortalManageListPage;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalLoginPage;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import com.vertex.quality.connectors.ariba.portal.pages.config.AribaPortalAdministrationPage;
import com.vertex.quality.connectors.ariba.portal.pages.config.AribaPortalEditConfigTaskPage;
import com.vertex.quality.connectors.ariba.portal.pages.config.AribaPortalIntegrationConfigurationPage;
import com.vertex.quality.connectors.ariba.portal.pages.config.AribaPortalIntegrationManagerPage;

import static com.vertex.quality.common.utils.SQLConnection.getEnvironmentCredentials;

@SuppressWarnings("Duplicates")

/**
 * currently just stores constants & utility functions that are used by many or
 * all tests of ariba's primary portal website
 *
 * @author ssalisbury osabha
 */

public class AribaTwoXPortalBaseTest extends AribaPortalBaseTest
{
	protected AribaAPITestUtilities apiUtil;
	protected final String standardSupplier = "Automation New Ariba Test Supplier";
	protected final String nonCatalogItemSupplier = "Automation Test 2";

	protected EnvironmentInformation portalEnvironmentInformation;
	protected EnvironmentCredentials portalEnvironmentCredentials;

	protected String aribaPortalURL = "https://s1.ariba.com/Buyer/Main/aw?awh=r&awssk=QsAOBrxT&realm=taxintegration&dard=1";
	protected String portalUsername = "cnoll";
	protected String portalPassword = "Vertexariba@123";

	/**
	 * Used to be able to set environment descriptor from the child base test
	 *
	 * @return the environment descriptor based on the base test
	 */
	protected DBEnvironmentDescriptors getPortalEnvironmentDescriptor( )
	{
		return DBEnvironmentDescriptors.ARIBA2_0_PORTAL;
	}

	/**
	 * Gets credentials for the connector from the database
	 */
	@Override
	public AribaPortalLoginPage loadInitialTestPage( )
	{
		try
		{
			portalEnvironmentInformation = SQLConnection.getEnvironmentInformation(DBConnectorNames.ARIBA,
				DBEnvironmentNames.QA, getPortalEnvironmentDescriptor());
			portalEnvironmentCredentials = getEnvironmentCredentials(portalEnvironmentInformation);
			aribaPortalURL = "https://s1.ariba.com/Buyer/Main/aw?awh=r&awssk=QsAOBrxT&realm=taxintegration&dard=1";
			portalUsername = "cnoll";
			portalPassword = "Vertexariba@123";
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		AribaPortalLoginPage signOnPage = loadPortal();
		return signOnPage;
	}

	/**
	 * logs into ariba's portal site
	 *
	 * @return the home page of ariba's portal site
	 */
	protected AribaPortalPostLoginBasePage signInToTwoXPortal( final AribaPortalLoginPage signOnPage )
	{
		VertexLogger.log("Signing in to Ariba Portal Site...", VertexLogLevel.INFO, getClass());

		return signOnPage.loginAsUser(portalUsername, portalPassword);
	}

	/**
	 * loads the login page of ariba's 2.x portal site
	 *
	 * @return a representation of this site's login screen
	 */
	protected AribaPortalLoginPage loadPortal( )
	{
		AribaPortalLoginPage signOnPage = null;
		String url = this.aribaPortalURL;

		driver.get(url);

		VertexLogger.log(String.format("Ariba Portal URL - %s", url), VertexLogLevel.DEBUG, getClass());
		signOnPage = new AribaPortalLoginPage(driver);
		return signOnPage;
	}

	/**
	 * changes the endpoint in the target environment
	 *
	 * @param endpoint endpoint to change to
	 *
	 * @return true if the endpoint change was successful.
	 */
	public boolean changeEndPointForTwoXPortal( AribaConnectorIntegrationEndpoints endpoint )
	{
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalAdministrationPage administrationPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.CORE_ADMINISTRATION);
		AribaPortalIntegrationManagerPage integrationManagerPage = administrationPage.goToIntegrationManager();
		AribaPortalIntegrationConfigurationPage configurationPage = integrationManagerPage.goToIntegrationConfig();
		configurationPage.searchForTaxTask();
		AribaPortalEditConfigTaskPage editTaskPage = configurationPage.editTaxTask();
		editTaskPage.setEndpointTo(endpoint);
		AribaPortalIntegrationConfigurationPage configurationPage1 = editTaskPage.saveChanges();
		testStartPage = configurationPage1.logout();
		AribaPortalPostLoginBasePage loggedInDashboardPage1 = signInToTwoXPortal(testStartPage);
		AribaPortalAdministrationPage administrationPage1 = loggedInDashboardPage1.navigateToManageListPages(
			AribaPortalManageListPage.CORE_ADMINISTRATION);
		AribaPortalIntegrationManagerPage integrationManagerPage1 = administrationPage1.goToIntegrationManager();
		AribaPortalIntegrationConfigurationPage configurationPage2 = integrationManagerPage1.goToIntegrationConfig();
		configurationPage2.searchForTaxTask();
		AribaPortalEditConfigTaskPage editTaskPage1 = configurationPage2.editTaxTask();
		boolean endPointChangeSuccess = endpoint
			.getEndpoint()
			.equals(editTaskPage1.getSelectedEndpoint());
		;
		endPointChangeSuccess = endpoint
			.getUrl()
			.equals(editTaskPage1.getEndpointUrl());
		return endPointChangeSuccess;
	}
}
