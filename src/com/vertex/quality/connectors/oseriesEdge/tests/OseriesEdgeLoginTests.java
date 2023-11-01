package com.vertex.quality.connectors.oseriesEdge.tests;


import com.vertex.quality.connectors.oseriesEdge.pages.OseriesEdgeHomePage;
import com.vertex.quality.connectors.oseriesEdge.pages.OseriesEdgeLoginPage;
import com.vertex.quality.connectors.oseriesEdge.pages.OseriesEdgePostLoginPage;
import com.vertex.quality.connectors.oseriesEdge.tests.base.OseriesEdgeBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * This test is set to verify users are able to log in and click tabs on the landing page
 *
 * @author Laxmi Lama-Palladino
 */
public class OseriesEdgeLoginTests extends OseriesEdgeBaseTest
{
    OseriesEdgeLoginPage loginPage;
    OseriesEdgePostLoginPage postLoginPage;
    OseriesEdgeHomePage settingsButton, instanceButton, imageButton;

    /**
     * This test verifies a user is able ot login to Oseries Edge
     * XRAYOSE-1
     * @author Laxmi Lama-Palladino
     */
    @Test(groups = {"oseriesEdge-smoke"})
    public void OseriesLoginValidationTest( )
    {
        loginPage = new OseriesEdgeLoginPage(driver);
        postLoginPage = new OseriesEdgePostLoginPage(driver);

        logInAsOseriesEdgeUser();
        assertTrue(postLoginPage.getOseriesEdgeLinkIsVisible(), "Oseries Edge Link is not Visible");
    }
    /**
     * This test verifies a user is able to click on the Template tab
     * XRAYOSE-4
     */
    @Test(groups = {"oseriesEdge-smoke"})
    public void oseriesEdgeTemplateTest( )
    {
        goToHomepageTemplateTab();
    }

    /**
     * This test verifies user is on the image page
     * XRAYOSE-34
     */
    @Test(groups = {"oseriesEdge-smoke"})
    public void oseriesEdgeImageTest()  {
        imageButton = new OseriesEdgeHomePage(driver);
        clickImageTab();
        assertTrue(imageButton.verifyOseriesImageTab(), "Oseries Edge Image tab is not Visible");
    }

    /**
     * This test verifies a user is able to click on the Settings tab
     * XRAYOSE-6
     */
    @Test(groups = {"oseriesEdge-smoke"})
    public void oseriesEdgeSettingsTest( )
    {
        settingsButton = new OseriesEdgeHomePage(driver);
        goToHomepageSettingsTab();
        assertTrue(settingsButton.getOseriesEdgesettingsButtonIsVisible(), "Oseries Edge Settings Button is not Visible");
    }
    /**
     * This test verifies a user is able to click on the Instances tab
     * XRAYOSE-7
     */
    @Test(groups = {"oseriesEdge-smoke"})
    public void oseriesEdgeInstancesTest( )
    {
        instanceButton = new OseriesEdgeHomePage(driver);
        goToHomepageInstancesTab();
        assertTrue(instanceButton.getOseriesEdgeInstanceButtonIsVisible(), "Oseries Edge Instance Button is not Visible");
    }

}
