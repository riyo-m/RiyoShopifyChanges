package com.vertex.quality.connectors.netsuite.NetsuiteCertification.SingleCompanyCertification.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteHomepage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.tests.base.NetsuiteBaseTest;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.NetsuiteSCGeneralPreferencesPage;
import org.testng.annotations.Test;

/**
 * Contains common navigation methods for One World
 *
 * @author mwilliams
 */
@Test(groups = { "netsuite_cert" })
public class SCCertBaseTest extends NetsuiteBaseTest {

    protected String defaultTaxCode = "Vertex";

    @Override
    protected DBConnectorNames getConnectorName( )
    {
        return DBConnectorNames.NETSUITE_SINGLE_COMPANY;
    }

    /**
     * Configures the settings for Netsuite API
     */
    protected NetsuiteSetupManagerPage configureSettings( )
    {
        NetsuiteHomepage homepage = signIntoHomepageAsSingleCompany();
        NetsuiteNavigationMenus generalPreferencesMenu = getGeneralPreferencesMenu();
        NetsuiteSCGeneralPreferencesPage generalPreferencesPage = homepage.navigationPane.navigateThrough(
                generalPreferencesMenu);
        generalPreferencesPage.openCustomPreferencesTab();

        generalPreferencesPage.selectInstallFlag();

        return generalPreferencesPage.savePreferences();
    }
}
