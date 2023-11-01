package com.vertex.quality.connectors.episerver.tests.v323x.admin.healthCheck;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.NavigationAndMenuOptions;
import com.vertex.quality.connectors.episerver.common.enums.OSeriesData;
import com.vertex.quality.connectors.episerver.common.enums.Status;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiOseriesPage;
import com.vertex.quality.connectors.episerver.pages.epiCommon.EpiStoreLoginPage;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import java.util.Locale;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * Test class that validates Health-check of connector by applying valid & invalid vertex services' URLs.
 *
 * @author Shivam.Soni
 */
public class EpiHealthCheckTests extends EpiBaseTest {

    EpiStoreLoginPage storeLoginPage;
    EpiAdminHomePage adminHomePage;
    EpiOseriesPage epiOSeries;

    /**
     * CEPI-236 Test Case - Perform a Health check - Invalid address URL
     */
    @Test(groups = {"epi_smoke"})
    public void healthCheckWithInvalidURLTest() {
        try {
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

            // Go to Settings Tab
            epiOSeries.clickOnSettingsTab();

            // combine health-check on both vertex services
            // enter bad URLs for Vertex Taxation & Address Cleansing services
            epiOSeries.enterVertexTaxationURL(OSeriesData.BAD_URL.data);
            epiOSeries.enterVertexAddressCleansingURL(OSeriesData.BAD_URL.data);
            epiOSeries.saveSettings();
            epiOSeries.clickRefreshStatusButton();
            assertTrue(epiOSeries.getVertexOseriesConnectorStatus().toLowerCase(Locale.ROOT).contains(Status.BAD.text));

            // Enter valid URLs for Vertex Taxation & Address Cleansing services
            epiOSeries.enterVertexTaxationURL(OSeriesData.O_SERIES_TAX_CALCULATION_URL.data);
            epiOSeries.enterVertexAddressCleansingURL(OSeriesData.O_SERIES_ADDRESS_CLEANSING_URL.data);
            epiOSeries.saveSettings();
            epiOSeries.clickRefreshStatusButton();

            // Individual health-check on Vertex Taxation service
            // enter bad URL for Vertex Taxation service
            epiOSeries.enterVertexTaxationURL(OSeriesData.BAD_URL.data);
            epiOSeries.saveSettings();
            epiOSeries.clickRefreshStatusButton();
            assertTrue(epiOSeries.getVertexOseriesConnectorStatus().toLowerCase(Locale.ROOT).contains(Status.BAD.text));

            // enter valid URL for Vertex Taxation service
            epiOSeries.enterVertexTaxationURL(OSeriesData.O_SERIES_TAX_CALCULATION_URL.data);
            epiOSeries.saveSettings();
            epiOSeries.clickRefreshStatusButton();

            // Individual health-check on Vertex's Address Cleansing service.
            // enter bad URLs for Vertex Address Cleansing service
            epiOSeries.enterVertexAddressCleansingURL(OSeriesData.BAD_URL.data);
            epiOSeries.saveSettings();
            epiOSeries.clickRefreshStatusButton();
            assertTrue(epiOSeries.getVertexOseriesConnectorStatus().toLowerCase(Locale.ROOT).contains(Status.BAD.text));
        } catch (Exception e) {
            e.printStackTrace();
            VertexLogger.log("Test failed due to error/exception, Kindly check logs for detail");
            fail();
        } finally {
            quitDriver();
            createChromeDriver();

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

            // Go to Settings Tab
            epiOSeries.clickOnSettingsTab();

            // enter Valid URLs for Vertex Taxation & Address Cleansing service
            epiOSeries.enterVertexTaxationURL(OSeriesData.O_SERIES_TAX_CALCULATION_URL.data);
            epiOSeries.enterVertexAddressCleansingURL(OSeriesData.O_SERIES_ADDRESS_CLEANSING_URL.data);
            epiOSeries.saveSettings();
            epiOSeries.clickRefreshStatusButton();
        }
    }

    /**
     * CEPI-237 Test Case - Perform a Health check - Valid address URL
     */
    @Test(groups = {"epi_smoke"})
    public void healthCheckWithValidURLTest() {
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

        // Go to Settings Tab
        epiOSeries.clickOnSettingsTab();

        // enter Valid URLs for Vertex Taxation & Address Cleansing service
        epiOSeries.enterVertexTaxationURL(OSeriesData.O_SERIES_TAX_CALCULATION_URL.data);
        epiOSeries.enterVertexAddressCleansingURL(OSeriesData.O_SERIES_ADDRESS_CLEANSING_URL.data);
        epiOSeries.saveSettings();
        epiOSeries.clickRefreshStatusButton();
        epiOSeries.refreshPage();
        adminHomePage.selectTabInCmsAdminPage(NavigationAndMenuOptions.CmsSubmenuOptions.ADMIN.text);
        adminHomePage.navigateToOseriespage();
        epiOSeries.clickOnSettingsTab();
        assertTrue(epiOSeries.getVertexOseriesConnectorStatus().toLowerCase(Locale.ROOT).contains(Status.GOOD.text));
    }
}
