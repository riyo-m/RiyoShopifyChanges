package com.vertex.quality.connectors.episerver.tests.v323x.admin.versionDisplay;

import com.vertex.quality.connectors.episerver.common.enums.NavigationAndMenuOptions;
import com.vertex.quality.connectors.episerver.common.enums.OSeriesData;
import com.vertex.quality.connectors.episerver.common.enums.VersionType;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiOseriesPage;
import com.vertex.quality.connectors.episerver.pages.epiCommon.EpiStoreLoginPage;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test class that contains test(s) that validates version of connector.
 *
 * @author Shivam.Soni
 */
public class EpiVersionDisplayTests extends EpiBaseTest {

    EpiStoreLoginPage storeLoginPage;
    EpiAdminHomePage adminHomePage;
    EpiOseriesPage epiOSeries;

    /**
     * CEPI-235 Test Case - version displays on the UI
     */
    @Test(groups = {"epi_smoke"})
    public void hybrisVersionDisplayTest() {
        // Launch Epi-Server's store
        launchEpiStorePage();

        // Login with Admin User's credentials
        storeLoginPage = new EpiStoreLoginPage(driver);
        storeLoginPage.loginToEpiStore(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

        // navigate to Vertex O Series Page
        adminHomePage = new EpiAdminHomePage(driver);
        adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.CMS_EDIT.text);
        adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CmsMenuOptions.ADMIN.text);
        adminHomePage.selectTabInCmsAdminPage(NavigationAndMenuOptions.CmsSubmenuOptions.ADMIN.text);
        epiOSeries = adminHomePage.navigateToOseriespage();

        // Assertion on Connector's version.
        assertEquals(OSeriesData.EPI_CORE_VERSION_2_0_2_0.data, epiOSeries.getVersion(VersionType.Core_Version.text));
        assertEquals(OSeriesData.EPI_ADAPTER_VERSION_3_2_3_1.data, epiOSeries.getVersion(VersionType.Adapter_Version.text));
    }
}
