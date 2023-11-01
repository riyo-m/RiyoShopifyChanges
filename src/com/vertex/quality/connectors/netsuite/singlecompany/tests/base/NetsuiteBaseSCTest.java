package com.vertex.quality.connectors.netsuite.singlecompany.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteHomepage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.tests.base.NetsuiteBaseTest;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.NetsuiteSCGeneralPreferencesPage;
import org.testng.annotations.Test;

/**
 * Contains common navigation methods for Single Company
 *
 * @author hho
 */
@Test(groups = { "single_company" })
public class NetsuiteBaseSCTest extends NetsuiteBaseTest
{
	protected String defaultTaxCode = "Vertex Zero Tax Code";

	@Override
	protected DBConnectorNames getConnectorName( )
	{
		return DBConnectorNames.NETSUITE_SINGLE_COMPANY;
	}

	/**
	 * Configures the settings for Single Company
	 */
	protected NetsuiteSetupManagerPage configureSettings( )
	{
		NetsuiteHomepage homepage = signIntoHomepageAsSingleCompany();
		NetsuiteNavigationMenus generalPreferencesMenu = getGeneralPreferencesMenu();
		NetsuiteSCGeneralPreferencesPage generalPreferencesPage = homepage.navigationPane.navigateThrough(
			generalPreferencesMenu);
		generalPreferencesPage.openCustomPreferencesTab();

		generalPreferencesPage.selectInstallFlag();
		generalPreferencesPage.unselectOneWorldFlag();
		generalPreferencesPage.unselectCanadianLicensing();

		generalPreferencesPage.setDefaultTaxCode(defaultTaxCode);

		return generalPreferencesPage.savePreferences();
	}
}
