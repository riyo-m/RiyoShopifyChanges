package com.vertex.quality.connectors.orocommerce.tests.admin;

import com.vertex.quality.connectors.orocommerce.enums.OroTestData;
import com.vertex.quality.connectors.orocommerce.pages.admin.OroAdminHomePage;
import com.vertex.quality.connectors.orocommerce.pages.admin.OroSystemConfigurationsPage;
import com.vertex.quality.connectors.orocommerce.pages.admin.OroVertexSettingsPage;
import com.vertex.quality.connectors.orocommerce.tests.base.OroAdminBaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * a group of smoke tests for the oro commerce admin site
 *
 * @author alewis
 */
public class OroAdminHealthCheckTests extends OroAdminBaseTest {

    OroVertexSettingsPage vertexSettingsPage;

    /**
     * COROCOM-521
     * <p>
     * to log in and check for the expected home page title
     */
    @Test(groups = {"oroSmoke", "oroCommerce"})
    public void loginTest() {
        String expectedPageTitle = "Dashboard";
        OroAdminHomePage homePage = signInToAdmin(testStartPage);
        WebDriverWait wait = new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.titleContains("Dashboard"));
        String currentPageTitle = homePage.getPageTitle();
        assertTrue(expectedPageTitle.equals(currentPageTitle));
        homePage.mainMenu.goToSystemTab();
    }

    /**
     * COROCOM-838
     * Oro 4.1 - Perform a Healthcheck - schedule
     */
    @Test(groups = {"oroSmoke"})
    public void healthCheckValidUrl() {
        vertexSettingsPage = new OroVertexSettingsPage(driver);
        OroAdminHomePage homePage = signInToAdmin(testStartPage);
        assertTrue(homePage.checkErrorMessageInAdminHomePage());
        homePage.closeErrorMessageInAdminHomePage();
        homePage.mainMenu.goToSystemTab();
        OroSystemConfigurationsPage configPage = homePage.mainMenu.system.goToConfigurationsPage();
        vertexSettingsPage = new OroVertexSettingsPage(driver);
        vertexSettingsPage = configPage.configurations.goToVertexSettingsPage();
        vertexSettingsPage.changeHealthCheckURL(OroTestData.VALID_URL.data);
        assertTrue(vertexSettingsPage.checkHealthCheckMessage());
    }

    /**
     * COROCOM-839
     * Oro 4.1 - Perform a Healthcheck - Invalid address URL
     */
    @Test(groups = {"oroSmoke"})
    public void healthCheckInvalidUrl() {
        vertexSettingsPage = new OroVertexSettingsPage(driver);
        OroAdminHomePage homePage = signInToAdmin(testStartPage);
        assertTrue(homePage.checkErrorMessageInAdminHomePage());
        homePage.closeErrorMessageInAdminHomePage();
        homePage.mainMenu.goToSystemTab();
        OroSystemConfigurationsPage configPage = homePage.mainMenu.system.goToConfigurationsPage();
        vertexSettingsPage = configPage.configurations.goToVertexSettingsPage();
        vertexSettingsPage.changeHealthCheckURL(OroTestData.WRONG_URL.data);
        assertTrue(vertexSettingsPage.checkHealthCheckMessage());
    }

    /**
     * COROCOM-638
     */
    @Test(groups = {"oroSmoke", "oroCommerce", "oro_handshake"})
    public void handshakeTest() {
        vertexSettingsPage = new OroVertexSettingsPage(driver);
        OroAdminHomePage homePage = signInToAdmin(testStartPage);
        assertTrue(homePage.checkErrorMessageInAdminHomePage());
        homePage.closeErrorMessageInAdminHomePage();
        homePage.mainMenu.goToSystemTab();
        OroSystemConfigurationsPage configPage = homePage.mainMenu.system.goToConfigurationsPage();
        vertexSettingsPage = configPage.configurations.goToVertexSettingsPage();
        vertexSettingsPage.changeURL("");
    }

    /**
     * COROCOM-639
     */
    @Test(groups = {"oroCommerce", "oro_BadHandshake"})
    public void handshakeWrongURLTest() {
        vertexSettingsPage = new OroVertexSettingsPage(driver);
        String wrongURL = "http://badurl.com";
        OroAdminHomePage homePage = signInToAdmin(testStartPage);
        assertTrue(homePage.checkErrorMessageInAdminHomePage());
        homePage.closeErrorMessageInAdminHomePage();
        homePage.mainMenu.goToSystemTab();
        OroSystemConfigurationsPage configPage = homePage.mainMenu.system.goToConfigurationsPage();
        vertexSettingsPage = configPage.configurations.goToVertexSettingsPage();
        vertexSettingsPage.changeURL(wrongURL);
    }

    /**
     * COROCOM-678
     */
    @Test(groups = {"oroCommerce", "VATTY"})
    public void vatRequiredCheckBox() {
        OroAdminHomePage homePage = signInToAdmin(testStartPage);
        homePage.mainMenu.goToSystemTab();
        OroSystemConfigurationsPage configPage = homePage.mainMenu.system.goToConfigurationsPage();
        OroVertexSettingsPage vertexSettingsPage = configPage.configurations.goToVertexSettingsPage();
        Boolean checkPresent = vertexSettingsPage.checkBoxPresent();
        assertTrue(checkPresent);
    }

    /**
     * COROCOM-643
     */
    @Test(groups = {"oroCommerce"})
    public void checkVersionNumberOnUITest() {
        String correctVersion = "1.4.0";
        OroAdminHomePage homePage = signInToAdmin(testStartPage);
        homePage.mainMenu.goToSystemTab();
        OroSystemConfigurationsPage configPage = homePage.mainMenu.system.goToConfigurationsPage();
        OroVertexSettingsPage vertexSettingsPage = configPage.configurations.goToVertexSettingsPage();
        String versionNumber = vertexSettingsPage.getVersionNumber();
        assertEquals(versionNumber, correctVersion);
    }
}