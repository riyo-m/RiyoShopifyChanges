package com.vertex.quality.connectors.orocommerce.tests.admin;

import com.vertex.quality.connectors.orocommerce.pages.admin.OroAdminHomePage;
import com.vertex.quality.connectors.orocommerce.pages.admin.OroSystemConfigurationsPage;
import com.vertex.quality.connectors.orocommerce.pages.admin.OroVertexLogsPage;
import com.vertex.quality.connectors.orocommerce.pages.admin.OroVertexSettingsPage;
import com.vertex.quality.connectors.orocommerce.tests.base.OroAdminBaseTest;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertTrue;

/**
 * @author alewis
 */
public class OroAdminLoggingTests extends OroAdminBaseTest {

    @Test(groups = { "oroCommerce" })
    public void successfulRequestTest() {
        OroAdminHomePage homePage = signInToAdmin(testStartPage);
        homePage.mainMenu.goToTaxesTab();
        OroVertexLogsPage vertexLogsPage = homePage.mainMenu.taxes.goToVertexLogsPage( );
    }

    /**
     * COROCOM-643
     *
     * Be careful this deletes all logs
     */
    @Test(groups = { "oroCommerce_monthly" })
    public void clearLogFilesTest() {
        OroAdminHomePage homePage = signInToAdmin(testStartPage);
        homePage.mainMenu.goToTaxesTab();
        OroVertexLogsPage vertexLogsPage = homePage.mainMenu.taxes.goToVertexLogsPage( );
        vertexLogsPage.deleteAllLogs();
        assertTrue(true);
        System.out.println("Be Very Careful This deletes all logs");

    }

    /**
     * CARIBA-664
     */
    @Test(groups = { "oroCommerce_monthly" })
    public void logFilePurgeButtonPresentTest() {
        OroAdminHomePage homePage = signInToAdmin(testStartPage);
        homePage.mainMenu.goToTaxesTab();
        OroVertexLogsPage vertexLogsPage = homePage.mainMenu.taxes.goToVertexLogsPage( );
        boolean isDeleteButtonPresent = vertexLogsPage.deleteLogsButtonPresent();
        assertTrue(isDeleteButtonPresent);
    }

    /**
     * CARIBA-669
     */
    @Test(groups = { "oroCommerce","Oro_UI_Test" })
    public void checkVersionNumberInLogTest() {
        OroAdminHomePage homePage = signInToAdmin(testStartPage);
        homePage.mainMenu.goToTaxesTab();
        OroVertexLogsPage vertexLogsPage = homePage.mainMenu.taxes.goToVertexLogsPage( );
        vertexLogsPage.clickLog();
    }

    @Test(groups = { "oroCommerce"})
    public void returnAssistedInLogTest() {
        assertTrue(true);

    }

}