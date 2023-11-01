package com.vertex.quality.connectors.shopify.ui.tests.admin.onboarding;

import com.vertex.quality.connectors.shopify.base.ShopifyUIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Shopify - Vertex Inc. App OnBoarding test cases
 *
 * @author Shivam.Soni
 */
public class ShopifyOnBoardingTests extends ShopifyUIBaseTest {

    ShopifyAdminLoginPage adminLoginPage;
    ShopifyAdminHomePage adminHomePage;
    ShopifyAdminSettingsPage adminSettingsPage;
    ShopifyAdminSettingsAppPage settingsAppPage;
    ShopifyAdminSettingsTaxesPage settingsTaxesPage;
    ShopifyAppInstallConfirmationPage installConfirmationPage;
    ShopifyVertexOnBoardingPage onBoardingPage;
    String storeName;

    /**
     * XRAYSHOP-34 Shopify - On-boarding - Test Case - Install the App & verify
     */
    @Test(groups = {"shopify_regression", "shopify_onBoarding"})
    public void onBoardVertexAppTest() {
        storeName = ShopifyDataUI.AdminPanelData.VTX_PROD_ADMIN_PANEL.text;
        // If the app's against the store found in DB, then first Uninstalling it, verifying & then doing on-boarding & then again verifying entry on UI & in DB.
        if (checkIfAppInstalledOrNot(storeName)) {
            // Load admin panel
            loadShopifyAdmin(storeName);

            // Login to admin
            adminLoginPage = new ShopifyAdminLoginPage(driver);
            adminLoginPage.loginToAdminPanel();

            // Navigating to settings page
            adminHomePage = new ShopifyAdminHomePage(driver);
            adminHomePage.navigateToLeftPanelOption(ShopifyDataUI.AdminPanelLeftNavigationOptions.SETTINGS.text);

            // Navigating to Apps and sales page
            adminSettingsPage = new ShopifyAdminSettingsPage(driver);
            adminSettingsPage.navigateToSettingsOption(ShopifyDataUI.SettingsLeftNavigationOptions.APPS_AND_SALES_CHANNELS.text);

            // Uninstalling the app
            settingsAppPage = new ShopifyAdminSettingsAppPage(driver);
            settingsAppPage.selectInstalled();
            settingsAppPage.unInstallTheApp(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text);
            assertFalse(settingsAppPage.verifyAppIsInstalled(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text));
            assertFalse(checkIfAppInstalledOrNot(storeName));

            // Navigating to Taxes and duties page
            adminSettingsPage = new ShopifyAdminSettingsPage(driver);
            adminSettingsPage.navigateToSettingsOption(ShopifyDataUI.SettingsLeftNavigationOptions.TAXES_AND_DUTIES.text);

            // Installing the App
            settingsTaxesPage = new ShopifyAdminSettingsTaxesPage(driver);
            installConfirmationPage = settingsTaxesPage.clickAddAppButton(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text);
            onBoardingPage = installConfirmationPage.clickOnInstallApp();

            // Do vertex on-boarding, activate app & validate the connection
            settingsTaxesPage = onBoardingPage.doVertexOnBoarding(ShopifyDataUI.OSeriesInstanceNames.VOD.text);
            settingsTaxesPage.activateTheApp(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text);
            assertTrue(settingsTaxesPage.verifyAppConnectionEstablished(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text));
            assertTrue(checkIfAppInstalledOrNot(storeName));
        }
        // If app's entry against store not found in the DB, then doing on-boarding, verifying on UI & in DB & then Uninstalling on UI & in DB.
        else if (!checkIfAppInstalledOrNot(storeName)) {
            // Load admin panel
            loadShopifyAdmin(storeName);

            // Login to admin
            adminLoginPage = new ShopifyAdminLoginPage(driver);
            adminLoginPage.loginToAdminPanel();

            // Navigating to settings page
            adminHomePage = new ShopifyAdminHomePage(driver);
            adminHomePage.navigateToLeftPanelOption(ShopifyDataUI.AdminPanelLeftNavigationOptions.SETTINGS.text);

            // Navigating to Taxes and duties page
            adminSettingsPage = new ShopifyAdminSettingsPage(driver);
            adminSettingsPage.navigateToSettingsOption(ShopifyDataUI.SettingsLeftNavigationOptions.TAXES_AND_DUTIES.text);

            // Installing the App
            settingsTaxesPage = new ShopifyAdminSettingsTaxesPage(driver);
            installConfirmationPage = settingsTaxesPage.clickAddAppButton(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text);
            onBoardingPage = installConfirmationPage.clickOnInstallApp();

            // Do vertex on-boarding, activate app & validate the connection
            settingsTaxesPage = onBoardingPage.doVertexOnBoarding(ShopifyDataUI.OSeriesInstanceNames.OSC_STAGE.text);
            settingsTaxesPage.activateTheApp(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text);
            assertTrue(settingsTaxesPage.verifyAppConnectionEstablished(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text));
            assertTrue(checkIfAppInstalledOrNot(storeName));

            // Navigating to Apps and sales page
            adminSettingsPage = new ShopifyAdminSettingsPage(driver);
            adminSettingsPage.navigateToSettingsOption(ShopifyDataUI.SettingsLeftNavigationOptions.APPS_AND_SALES_CHANNELS.text);

            // Uninstalling the app
            settingsAppPage = new ShopifyAdminSettingsAppPage(driver);
            settingsAppPage.selectInstalled();
            settingsAppPage.unInstallTheApp(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text);
            assertFalse(settingsAppPage.verifyAppIsInstalled(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text));
            assertFalse(checkIfAppInstalledOrNot(storeName));

            // Navigating to Taxes and duties page
            adminSettingsPage.navigateToSettingsOption(ShopifyDataUI.SettingsLeftNavigationOptions.TAXES_AND_DUTIES.text);

            // Installing the App
            settingsTaxesPage = new ShopifyAdminSettingsTaxesPage(driver);
            installConfirmationPage = settingsTaxesPage.clickAddAppButton(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text);
            onBoardingPage = installConfirmationPage.clickOnInstallApp();

            // Do vertex on-boarding, activate app & validate the connection
            settingsTaxesPage = onBoardingPage.doVertexOnBoarding(ShopifyDataUI.OSeriesInstanceNames.OSC_STAGE.text);
            settingsTaxesPage.activateTheApp(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text);
            assertTrue(settingsTaxesPage.verifyAppConnectionEstablished(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text));
            assertTrue(checkIfAppInstalledOrNot(storeName));
        }
    }

    /**
     * XRAYSHOP-33 Shopify - On-boarding - Test Case - Directly hit on-boarding URL
     */
    @Test(groups = {"shopify_regression", "shopify_onBoarding"})
    public void onBoardVertexAppDirectURLTest() {
        // Hit on-boarding url directly without following the installation flow
        onBoardingPage = new ShopifyVertexOnBoardingPage(driver);
        onBoardingPage.directLoadOnBoardingPage();

        // Verify Unauthorized Access attempt error message
        assertEquals(onBoardingPage.getUnauthorizedAccessMessage(), ShopifyDataUI.OnBoardingErrorMSG.UN_AUTHORIZED_ACCESS.text);
    }

    /**
     * XRAYSHOP-26 Shopify - On-boarding - Test Case - On-boarding with wrong credentials
     */
    @Test(groups = {"shopify_regression", "shopify_onBoarding"})
    public void onBoardVertexAppWithWrongValuesTest() {
        // Load admin panel
        loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VERTEX_EKS_ADMIN_PANEL.text);

        // Login to admin
        adminLoginPage = new ShopifyAdminLoginPage(driver);
        adminLoginPage.loginToAdminPanel();

        // Navigating to settings page
        adminHomePage = new ShopifyAdminHomePage(driver);
        adminHomePage.navigateToLeftPanelOption(ShopifyDataUI.AdminPanelLeftNavigationOptions.SETTINGS.text);

        // Navigating to Apps and sales page
        adminSettingsPage = new ShopifyAdminSettingsPage(driver);
        adminSettingsPage.navigateToSettingsOption(ShopifyDataUI.SettingsLeftNavigationOptions.APPS_AND_SALES_CHANNELS.text);

        // Uninstalling the app
        settingsAppPage = new ShopifyAdminSettingsAppPage(driver);
        settingsAppPage.selectInstalled();
        settingsAppPage.unInstallTheApp(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text);
        assertFalse(settingsAppPage.verifyAppIsInstalled(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text));

        // Navigating to Taxes and duties page
        adminSettingsPage = new ShopifyAdminSettingsPage(driver);
        adminSettingsPage.navigateToSettingsOption(ShopifyDataUI.SettingsLeftNavigationOptions.TAXES_AND_DUTIES.text);

        // Installing the App
        settingsTaxesPage = new ShopifyAdminSettingsTaxesPage(driver);
        installConfirmationPage = settingsTaxesPage.clickAddAppButton(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text);
        onBoardingPage = installConfirmationPage.clickOnInstallApp();

        // Do vertex on-boarding with wrong credentials
        onBoardingPage.clickConnectVertexAccount();
        onBoardingPage.enterCompanyCode("WrongCompanyCode");
        onBoardingPage.enterClientID("WrongClientID");
        onBoardingPage.enterClientSecret("WrongClientSecret");
        onBoardingPage.clickConnect();
        settingsTaxesPage = new ShopifyAdminSettingsTaxesPage(driver);

        // Verify wrong credentials error message
        assertEquals(onBoardingPage.getItemErrorMessage(), ShopifyDataUI.OnBoardingErrorMSG.WRONG_CREDENTIALS.text);
    }

    /**
     * XRAYSHOP-66 Shopify - On-boarding - Test Case - On-boarding with empty credentials' values
     */
    @Test(groups = {"shopify_regression", "shopify_onBoarding"})
    public void onBoardVertexAppWithEmptyValuesTest() {
        // Load admin panel
        loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VERTEX_EKS_ADMIN_PANEL.text);

        // Login to admin
        adminLoginPage = new ShopifyAdminLoginPage(driver);
        adminLoginPage.loginToAdminPanel();

        // Navigating to settings page
        adminHomePage = new ShopifyAdminHomePage(driver);
        adminHomePage.navigateToLeftPanelOption(ShopifyDataUI.AdminPanelLeftNavigationOptions.SETTINGS.text);

        // Navigating to Apps and sales page
        adminSettingsPage = new ShopifyAdminSettingsPage(driver);
        adminSettingsPage.navigateToSettingsOption(ShopifyDataUI.SettingsLeftNavigationOptions.APPS_AND_SALES_CHANNELS.text);

        // Uninstalling the app
        settingsAppPage = new ShopifyAdminSettingsAppPage(driver);
        settingsAppPage.selectInstalled();
        settingsAppPage.unInstallTheApp(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text);
        assertFalse(settingsAppPage.verifyAppIsInstalled(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text));

        // Navigating to Taxes and duties page
        adminSettingsPage = new ShopifyAdminSettingsPage(driver);
        adminSettingsPage.navigateToSettingsOption(ShopifyDataUI.SettingsLeftNavigationOptions.TAXES_AND_DUTIES.text);

        // Installing the App
        settingsTaxesPage = new ShopifyAdminSettingsTaxesPage(driver);
        installConfirmationPage = settingsTaxesPage.clickAddAppButton(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text);
        onBoardingPage = installConfirmationPage.clickOnInstallApp();

        // Do vertex on-boarding, with empty credentials
        onBoardingPage.clickConnectVertexAccount();
        onBoardingPage.enterCompanyCode("");
        onBoardingPage.enterClientID("");
        onBoardingPage.enterClientSecret("");
        onBoardingPage.clickConnect();
        settingsTaxesPage = new ShopifyAdminSettingsTaxesPage(driver);

        // Verify error messages for empty fields
        assertEquals(onBoardingPage.getCompanyCodeErrorMessage(), ShopifyDataUI.OnBoardingErrorMSG.EMPTY_COMPANY_CODE.text);
        assertEquals(onBoardingPage.getClientIdErrorMessage(), ShopifyDataUI.OnBoardingErrorMSG.EMPTY_CLIENT_ID.text);
        assertEquals(onBoardingPage.getClientSecretErrorMessage(), ShopifyDataUI.OnBoardingErrorMSG.EMPTY_CLIENT_SECRET.text);
    }

    /**
     * XRAYSHOP-81 Shopify - On-boarding - Test Case - On-boarding with empty credentials' values & hit cancel button
     */
    @Test(groups = {"shopify_regression", "shopify_onBoarding"})
    public void onBoardVertexAppWithEmptyValuesCancelTest() {
        // Load admin panel
        loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VERTEX_EKS_ADMIN_PANEL.text);

        // Login to admin
        adminLoginPage = new ShopifyAdminLoginPage(driver);
        adminLoginPage.loginToAdminPanel();

        // Navigating to settings page
        adminHomePage = new ShopifyAdminHomePage(driver);
        adminHomePage.navigateToLeftPanelOption(ShopifyDataUI.AdminPanelLeftNavigationOptions.SETTINGS.text);

        // Navigating to Apps and sales page
        adminSettingsPage = new ShopifyAdminSettingsPage(driver);
        adminSettingsPage.navigateToSettingsOption(ShopifyDataUI.SettingsLeftNavigationOptions.APPS_AND_SALES_CHANNELS.text);

        // Uninstalling the app
        settingsAppPage = new ShopifyAdminSettingsAppPage(driver);
        settingsAppPage.selectInstalled();
        settingsAppPage.unInstallTheApp(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text);
        assertFalse(settingsAppPage.verifyAppIsInstalled(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text));

        // Navigating to Taxes and duties page
        adminSettingsPage = new ShopifyAdminSettingsPage(driver);
        adminSettingsPage.navigateToSettingsOption(ShopifyDataUI.SettingsLeftNavigationOptions.TAXES_AND_DUTIES.text);

        // Installing the App
        settingsTaxesPage = new ShopifyAdminSettingsTaxesPage(driver);
        installConfirmationPage = settingsTaxesPage.clickAddAppButton(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text);
        onBoardingPage = installConfirmationPage.clickOnInstallApp();

        // Do vertex on-boarding, with empty credentials
        onBoardingPage.clickConnectVertexAccount();
        onBoardingPage.enterCompanyCode("");
        onBoardingPage.enterClientID("");
        onBoardingPage.enterClientSecret("");
        onBoardingPage.clickCancel();
        settingsTaxesPage = new ShopifyAdminSettingsTaxesPage(driver);

        // Verify Unauthorized Access attempt error message
        assertEquals(onBoardingPage.getUnauthorizedAccessMessage(), ShopifyDataUI.OnBoardingErrorMSG.UN_AUTHORIZED_ACCESS.text);
    }

    /**
     * XRAYSHOP-82 Shopify - On-boarding - Test Case - On-boarding with valid credentials' values & hit cancel button
     */
    @Test(groups = {"shopify_regression", "shopify_onBoarding"})
    public void onBoardVertexAppWithValidValuesCancelTest() {
        // Load admin panel
        loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VERTEX_EKS_ADMIN_PANEL.text);

        // Login to admin
        adminLoginPage = new ShopifyAdminLoginPage(driver);
        adminLoginPage.loginToAdminPanel();

        // Navigating to settings page
        adminHomePage = new ShopifyAdminHomePage(driver);
        adminHomePage.navigateToLeftPanelOption(ShopifyDataUI.AdminPanelLeftNavigationOptions.SETTINGS.text);

        // Navigating to Apps and sales page
        adminSettingsPage = new ShopifyAdminSettingsPage(driver);
        adminSettingsPage.navigateToSettingsOption(ShopifyDataUI.SettingsLeftNavigationOptions.APPS_AND_SALES_CHANNELS.text);

        // Uninstalling the app
        settingsAppPage = new ShopifyAdminSettingsAppPage(driver);
        settingsAppPage.selectInstalled();
        settingsAppPage.unInstallTheApp(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text);
        assertFalse(settingsAppPage.verifyAppIsInstalled(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text));

        // Navigating to Taxes and duties page
        adminSettingsPage = new ShopifyAdminSettingsPage(driver);
        adminSettingsPage.navigateToSettingsOption(ShopifyDataUI.SettingsLeftNavigationOptions.TAXES_AND_DUTIES.text);

        // Installing the App
        settingsTaxesPage = new ShopifyAdminSettingsTaxesPage(driver);
        installConfirmationPage = settingsTaxesPage.clickAddAppButton(ShopifyDataUI.VertexAppNames.VERTEX_TAX_STAGE.text);
        onBoardingPage = installConfirmationPage.clickOnInstallApp();

        // Do vertex on-boarding, with empty credentials
        onBoardingPage.clickConnectVertexAccount();
        onBoardingPage.enterCompanyCode(ShopifyDataUI.OSeriesDetails.STAGE_OSC_COMPANY_CODE_1.text);
        onBoardingPage.enterClientID(ShopifyDataUI.OSeriesDetails.STAGE_OSC_CLIENT_ID.text);
        onBoardingPage.enterClientSecret(ShopifyDataUI.OSeriesDetails.STAGE_OSC_CLIENT_SECRET.text);
        onBoardingPage.clickCancel();
        settingsTaxesPage = new ShopifyAdminSettingsTaxesPage(driver);

        // Verify Unauthorized Access attempt error message
        assertEquals(onBoardingPage.getUnauthorizedAccessMessage(), ShopifyDataUI.OnBoardingErrorMSG.UN_AUTHORIZED_ACCESS.text);
    }
}
