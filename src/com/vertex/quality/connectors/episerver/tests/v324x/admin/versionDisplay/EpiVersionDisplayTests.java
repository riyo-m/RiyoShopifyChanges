package com.vertex.quality.connectors.episerver.tests.v324x.admin.versionDisplay;

import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import com.vertex.quality.connectors.episerver.common.enums.NavigationAndMenuOptions;
import com.vertex.quality.connectors.episerver.common.enums.OSeriesData;
import com.vertex.quality.connectors.episerver.common.enums.VersionType;
import com.vertex.quality.connectors.episerver.pages.epiCommon.EpiStoreFrontHomePage;
import com.vertex.quality.connectors.episerver.pages.epiCommon.EpiStoreLoginPage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiOSeriesPage;
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
    EpiStoreFrontHomePage storeFrontHomePage;
    EpiAdminHomePage adminHomePage;
    EpiOSeriesPage epiOSeries;

    /**
     * CEPI-334 Test Case - version displays on the UI
     */
    @Test(groups = {"epi_smoke_v324"})
    public void epiVersionDisplayTest() {
        // Launch Epi-Server's store
        launch324EpiStorePage();

        // Login to storefront
        storeLoginPage = new EpiStoreLoginPage(driver);
        storeLoginPage.loginToEpiStore(EpiDataCommon.EpiCredentials.EPI_324_STORE_USER.text, EpiDataCommon.EpiCredentials.EPI_324_STORE_PASS.text);
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
        storeFrontHomePage.navigateToHomePage();

        // navigate to Vertex O Series Page & disable invoice
        adminHomePage = new EpiAdminHomePage(driver);
        adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.CMS_EDIT.text);
		adminHomePage.clickOnSubMenuSVG(NavigationAndMenuOptions.CmsLeftMenuOptionsSVG.Add_Ons.text);
        adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CmsMenuOptions.VERTEX_O_SERIES.text);

        // Assertion on Connector's version.
        epiOSeries = new EpiOSeriesPage(driver);
        assertEquals(OSeriesData.EPI_CORE_VERSION_2_0_4_0.data, epiOSeries.getVersion(VersionType.Core_Version.text));
        assertEquals(OSeriesData.EPI_ADAPTER_VERSION_3_2_5_0.data, epiOSeries.getVersion(VersionType.Adapter_Version.text));
    }
}