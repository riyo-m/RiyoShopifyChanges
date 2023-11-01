package com.vertex.quality.connectors.netsuite.NetsuiteCertification.OneWorldCertification.tests.base;

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
 * @author mwilliams
 */
@Test(groups = { "netsuite_cert" })
public class OWCertBaseTest extends NetsuiteBaseTest {

    protected String defaultTaxCode = "Vertex";

    @Override
    protected DBConnectorNames getConnectorName( )
    {
        return DBConnectorNames.NETSUITE_ONE_WORLD;
    }

    /**
     * Configures the settings for Netsuite API
     */
    protected NetsuiteSetupManagerPage configureSettings( )
    {
        NetsuiteHomepage homepage = signintoHomepageAsOneWorldRP();
        NetsuiteNavigationMenus generalPreferencesMenu = getGeneralPreferencesMenu();
        NetsuiteOWGeneralPreferencesPage generalPreferencesPage = homepage.navigationPane.navigateThrough(
                generalPreferencesMenu);
        generalPreferencesPage.openCustomPreferencesTab();

        generalPreferencesPage.selectInstallFlag();
        generalPreferencesPage.selectOneWorldFlag();

        return generalPreferencesPage.savePreferences();
    }
}
