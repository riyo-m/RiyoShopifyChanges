package com.vertex.quality.connectors.kibo.tests.versionDisplay;

import com.vertex.quality.connectors.kibo.components.KiboMainMenuNavPanel;
import com.vertex.quality.connectors.kibo.enums.OSeriesData;
import com.vertex.quality.connectors.kibo.pages.KiboApplicationsPage;
import com.vertex.quality.connectors.kibo.pages.KiboVertexConnectorPage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test case(s) related to validate configured version of vertex connector with Kibo.
 *
 * @author Shivam.Soni
 */
public class KiboVersionDisplayTests extends KiboTaxCalculationBaseTest {

    KiboVertexConnectorPage connectorPage;
    KiboMainMenuNavPanel navPanel;
    KiboApplicationsPage applicationsPage;

    /**
     * CKIBO-747 Test Case - version displays on the UI
     */
    @Test(groups = {"kibo_smoke"})
    public void kiboVersionDisplayTest() {
        // Go to application page from Kibo.
        connectorPage = new KiboVertexConnectorPage(driver);
        navPanel = connectorPage.clickMainMenu();
        navPanel.gotoSystemTab();
        applicationsPage = navPanel.goToApplicationsPage();
        applicationsPage.selectVertexConnector();
        connectorPage.makeSureApplicationEnabled();

        // Assertion for the configured version of O-Series with Kibo.
        assertEquals(OSeriesData.KIBO_CONNECTOR_VERSION_1_0_0.data, connectorPage.getApplicationVersion());
    }
}
