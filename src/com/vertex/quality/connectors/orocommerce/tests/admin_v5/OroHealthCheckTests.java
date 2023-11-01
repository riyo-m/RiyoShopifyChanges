package com.vertex.quality.connectors.orocommerce.tests.admin_v5;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.orocommerce.enums.OSeriesData;
import com.vertex.quality.connectors.orocommerce.enums.OroTestData;
import com.vertex.quality.connectors.orocommerce.pages.admin.*;
import com.vertex.quality.connectors.orocommerce.tests.base.OroStoreFrontBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test class that contains all tests related to Oro Commerce Health-check
 *
 * @author Shivam.Soni
 */
public class OroHealthCheckTests extends OroStoreFrontBaseTest {

    OroAdminCommercePage commercePage;
    OroAdminLoginPage loginPage;
    OroAdminHomePage adminHomePage;
    OroSystemConfigurationsPage configPage;
    OroVertexSettingsPage vertexSettingsPage;

    /**
     * COROCOM-1061 Oro 5 - Test Case - Perform a Healthcheck - Invalid address URL
     */
    @Test(groups = {"oroSmoke_v5"})
    public void healthCheckWithInvalidURLTest() {
        try {
            commercePage = new OroAdminCommercePage(driver);
            driver.get(OroTestData.ADMIN_URL_VERSION_5.data);
            loginPage = new OroAdminLoginPage(driver);
            adminHomePage = loginPage.loginAsUser(OroTestData.ADMIN_USERNAME.data, OroTestData.ADMIN_PASSWORD.data);
            assertTrue(adminHomePage.checkErrorMessageInAdminHomePage());
            adminHomePage.mainMenu.goToSystemTab();
            configPage = adminHomePage.mainMenu.system.goToConfigurationsPage();
            vertexSettingsPage = configPage.configurations.goToVertexSettingsPage();
            vertexSettingsPage.performHealthCheckWithSettings(OSeriesData.HEALTH_CHECK_INVALID_URL.data, OSeriesData.O_SERIES_TRUSTED_ID.data, false);
            assertEquals(vertexSettingsPage.getHealthCheckMessageFromUI(), OSeriesData.HEALTH_CHECK_UN_SUCCESS_MSG.data);
        } catch (Exception e) {
            e.printStackTrace();
            VertexLogger.log("Due to error/exception execution failed. Kindly check logs for more details!");
            Assert.fail();
        } finally {
            quitDriver();
            createChromeDriver();
            commercePage = new OroAdminCommercePage(driver);
            driver.get(OroTestData.ADMIN_URL_VERSION_5.data);
            loginPage = new OroAdminLoginPage(driver);
            adminHomePage = loginPage.loginAsUser(OroTestData.ADMIN_USERNAME.data, OroTestData.ADMIN_PASSWORD.data);
            assertTrue(adminHomePage.checkErrorMessageInAdminHomePage());
            adminHomePage.mainMenu.goToSystemTab();
            configPage = adminHomePage.mainMenu.system.goToConfigurationsPage();
            vertexSettingsPage = configPage.configurations.goToVertexSettingsPage();
            vertexSettingsPage.enterHealthCheckURL(OSeriesData.HEALTH_CHECK_VALID_URL.data);
            vertexSettingsPage.enterTrustedID(OSeriesData.O_SERIES_TRUSTED_ID.data);
            vertexSettingsPage.saveSettings();
        }
    }

    /**
     * COROCOM-1062 Oro 5 - Test Case - Perform a Healthcheck - valid address and tax calc
     */
    @Test(groups = {"oroSmoke_v5"})
    public void healthCheckWithValidURLTest() {
        commercePage = new OroAdminCommercePage(driver);
        driver.get(OroTestData.ADMIN_URL_VERSION_5.data);
        loginPage = new OroAdminLoginPage(driver);
        adminHomePage = loginPage.loginAsUser(OroTestData.ADMIN_USERNAME.data, OroTestData.ADMIN_PASSWORD.data);
        assertTrue(adminHomePage.checkErrorMessageInAdminHomePage());
        adminHomePage.mainMenu.goToSystemTab();
        configPage = adminHomePage.mainMenu.system.goToConfigurationsPage();
        vertexSettingsPage = configPage.configurations.goToVertexSettingsPage();
        vertexSettingsPage.performHealthCheckWithSettings(OSeriesData.HEALTH_CHECK_VALID_URL.data, OSeriesData.O_SERIES_TRUSTED_ID.data, false);
        assertEquals(vertexSettingsPage.getHealthCheckMessageFromUI(), OSeriesData.HEALTH_CHECK_SUCCESS_MSG.data);
    }
}
