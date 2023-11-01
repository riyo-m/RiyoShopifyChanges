package com.vertex.quality.connectors.ariba.connector.tests.base;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavConfigurationMenuOption;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavOption;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnBasePage;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnMenuBasePage;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnManageTenantPage;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnViewLoggedXMLMessagesPage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnHomePage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnSignOnPage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnSystemStatusPage;
import org.testng.ITestResult;

/**
 * stores variables & functionality used by tests of most or all of the configuration menus,
 * particularly for testing multi-tenancy
 *
 * @author ssalisbury
 */
public abstract class AribaConnMenuBaseTest extends AribaConnBaseTest
{
	protected final String tempTenantDisplayName = "NewTenant";
	protected final String tempTenantVariantId = tempTenantDisplayName;

	/**
	 * creates a new/temporary tenant whose configurations can be manipulated without jeopardizing system stability
	 *
	 * @return the login page for the Ariba connector ui
	 *
	 * @author ssalisbury
	 */
	@Override
	public AribaConnSignOnPage loadInitialTestPage( )
	{
		AribaConnSignOnPage newPage = null;

		AribaConnSignOnPage startPage = super.loadInitialTestPage();
		AribaConnManageTenantPage tenantPage = connUtil.loadConfigMenu(startPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);

		if ( !tenantPage.tenantSelector.doesTenantExist(tempTenantVariantId) )
		{
			tenantPage.createNewTenant();
			tenantPage.setDisplayName(tempTenantDisplayName);
			tenantPage.setVariantId(tempTenantVariantId);
			tenantPage.submitChanges();
		}
		else
		{
			VertexLogger.log("Temporary tenant was not successfully removed after a previous test run",
				VertexLogLevel.WARN);
		}

		newPage = tenantPage.header.clickLogout();

		return newPage;
	}

	/**
	 * deletes the temporary/test tenant which was used for the test
	 *
	 * Note- assumes that the test did not log out of the connector UI website
	 */
	@Override
	protected void handleLastTestPage( final ITestResult testResult )
	{
		AribaConnHomePage homePage = connUtil.loadConfigHome();

		AribaConnMenuBasePage postWipePage = eliminateTenant(homePage, tempTenantVariantId);
		postWipePage.header.clickLogout();

		super.handleLastTestPage(testResult);
	}

	/**
	 * completely erases a tenant from the connector
	 *
	 * @param prevPage the page in the connector's site that was already loaded
	 *                 the user must have already logged in
	 * @param tenantId the variant id of the tenant which must be wiped from the system
	 *
	 * @return the configuration menu page which is open at the end of the process
	 */
	protected AribaConnMenuBasePage eliminateTenant( final AribaConnBasePage prevPage, final String tenantId )
	{
		AribaConnSystemStatusPage statusPage = prevPage.navPanel.clickNavOption(AribaConnNavOption.SYSTEM_STATUS);
		if ( statusPage.isClearCachesButtonEnabled() )
		{
			statusPage.clickClearCachesButton();
		}

		AribaConnViewLoggedXMLMessagesPage logsPage = connUtil.openConfigMenu(statusPage,
			AribaConnNavConfigurationMenuOption.VIEW_LOGGED_XML_MESSAGES);

		AribaConnMenuBasePage finalPage = logsPage;
		if ( logsPage.tenantSelector.doesTenantExist(tenantId) )
		{
			AribaConnMenuBasePage lastWipedMenuPage = connUtil.wipeTenantDataRows(tenantId, logsPage);

			AribaConnManageTenantPage cleanupTenantPage = connUtil.openConfigMenu(lastWipedMenuPage,
				AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);
			cleanupTenantPage.deleteSelectedTenant();
			if ( cleanupTenantPage.isFailureMessageDisplayed() )
			{
				final String failedTenantDeleteMessage = String.format("Couldn't delete tenant %s", tenantId);
				VertexLogger.log(failedTenantDeleteMessage, VertexLogLevel.ERROR);
			}
			cleanupTenantPage.tenantSelector.selectTenant(defaultTenantVariantId);

			AribaConnSystemStatusPage postEliminationStatusPage = cleanupTenantPage.navPanel.clickNavOption(
				AribaConnNavOption.SYSTEM_STATUS);
			postEliminationStatusPage.clickClearCachesButton();
			finalPage = connUtil.openConfigMenu(postEliminationStatusPage,
				AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);
		}
		else
		{
			final String deleteMissingTenantMessage = String.format(
				"can't delete the tenant with variant id %s because it doesn't exist", tenantId);
			VertexLogger.log(deleteMissingTenantMessage);
		}

		return finalPage;
	}
}
