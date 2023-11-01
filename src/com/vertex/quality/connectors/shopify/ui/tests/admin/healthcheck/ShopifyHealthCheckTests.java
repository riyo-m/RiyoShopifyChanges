package com.vertex.quality.connectors.shopify.ui.tests.admin.healthcheck;

import com.vertex.quality.connectors.shopify.base.ShopifyUIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyAdminHomePage;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyAdminLoginPage;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyAdminSettingsPage;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyAdminSettingsTaxesPage;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

/**
 * Shopify - Health check tests
 *
 * @author Shivam.Soni
 */
public class ShopifyHealthCheckTests extends ShopifyUIBaseTest {

    ShopifyAdminLoginPage adminLoginPage;
    ShopifyAdminHomePage adminHomePage;
    ShopifyAdminSettingsPage adminSettingsPage;
    ShopifyAdminSettingsTaxesPage settingsTaxesPage;

    /**
     * XRAYSHOP-27 Shopify - Configuration - Test Case - Perform a Health check - Valid Client credentials
     */
    @Test(groups = {"shopify_regression", "shopify_healthCheck"})
    public void healthCheckForEstablishedConnectionTest() {
        // Load admin panel
        loadShopifyAdmin();

        // Login to admin
        adminLoginPage = new ShopifyAdminLoginPage(driver);
        adminLoginPage.loginToAdminPanel();

        // Navigating to settings page
        adminHomePage = new ShopifyAdminHomePage(driver);
        adminHomePage.navigateToLeftPanelOption(ShopifyDataUI.AdminPanelLeftNavigationOptions.SETTINGS.text);

        // Navigating to Taxes and duties page
        adminSettingsPage = new ShopifyAdminSettingsPage(driver);
        adminSettingsPage.navigateToSettingsOption(ShopifyDataUI.SettingsLeftNavigationOptions.TAXES_AND_DUTIES.text);

        // Validate app connection
        settingsTaxesPage = new ShopifyAdminSettingsTaxesPage(driver);
        assertTrue(settingsTaxesPage.verifyAppConnectionEstablished(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text));
    }
}
