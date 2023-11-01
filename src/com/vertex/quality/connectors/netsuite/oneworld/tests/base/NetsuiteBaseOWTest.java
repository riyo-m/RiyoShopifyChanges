package com.vertex.quality.connectors.netsuite.oneworld.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteHomepage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.tests.base.NetsuiteBaseTest;
import com.vertex.quality.connectors.netsuite.oneworld.pages.NetsuiteOWGeneralPreferencesPage;
import org.testng.annotations.Test;

/**
 * Contains common navigation methods for One World
 *
 * @author hho
 */
@Test(groups = { "one_world" })
public class NetsuiteBaseOWTest extends NetsuiteBaseTest
{

	@Override
	protected DBConnectorNames getConnectorName( )
	{
		return DBConnectorNames.NETSUITE_ONE_WORLD;
	}

	/**
	 * Configures the settings for One World
	 */
	protected NetsuiteSetupManagerPage configureSettings( )
	{
		NetsuiteHomepage homepage = signintoHomepageAsOneWorld();
		NetsuiteNavigationMenus generalPreferencesMenu = getGeneralPreferencesMenu();
		NetsuiteOWGeneralPreferencesPage generalPreferencesPage = homepage.navigationPane.navigateThrough(
			generalPreferencesMenu);
		generalPreferencesPage.openCustomPreferencesTab();
		generalPreferencesPage.selectInstallFlag();
		generalPreferencesPage.selectOneWorldFlag();
		generalPreferencesPage.unselectCanadianLicensing();
		generalPreferencesPage.setDefaultTaxCode(defaultTaxCode);
		generalPreferencesPage.setDefaultNontaxableTaxCode("");

		generalPreferencesPage.selectCustomerExceptionsCheckbox();

		return generalPreferencesPage.savePreferences();
	}
}
