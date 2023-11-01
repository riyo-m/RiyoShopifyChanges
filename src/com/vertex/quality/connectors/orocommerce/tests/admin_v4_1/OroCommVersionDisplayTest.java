package com.vertex.quality.connectors.orocommerce.tests.admin_v4_1;

import com.vertex.quality.connectors.orocommerce.enums.OSeriesData;
import com.vertex.quality.connectors.orocommerce.enums.OroTestData;
import com.vertex.quality.connectors.orocommerce.pages.admin.*;
import com.vertex.quality.connectors.orocommerce.tests.base.OroStoreFrontBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test class that contains version display test(s) for Oro Commerce
 *
 * @author Shivam.Soni
 */
public class OroCommVersionDisplayTest extends OroStoreFrontBaseTest {
    OroAdminCommercePage commercePage;
    OroAdminLoginPage loginPage;
    OroAdminHomePage adminHomePage;
    OroSystemConfigurationsPage configPage;
    OroVertexSettingsPage vertexSettingsPage;

    /**
     * COROCOM-1029 Oro 4.1 - Test Case - version displays on the UI
     */
    @Test(groups = {"oroSmoke_v4_1"})
    public void versionDisplayTest() {
        commercePage = new OroAdminCommercePage(driver);
        driver.get(OroTestData.ADMIN_URL.data);
        loginPage = new OroAdminLoginPage(driver);
        adminHomePage = loginPage.loginAsUser(OroTestData.ADMIN_USERNAME.data, OroTestData.ADMIN_PASSWORD.data);
        assertTrue(adminHomePage.checkErrorMessageInAdminHomePage());
        adminHomePage.mainMenu.goToSystemTab();
        configPage = adminHomePage.mainMenu.system.goToConfigurationsPage();
        vertexSettingsPage = configPage.configurations.goToVertexSettingsPage();
        assertEquals(OSeriesData.ORO_4_1_VERSION.data, vertexSettingsPage.getVersionFromUI());
    }
}
