package com.vertex.quality.connectors.oseriesEdge.tests.base;

import com.vertex.quality.connectors.oseriesEdge.pages.OseriesEdgeHomePage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * This test is set to verify users are able to log in and click tabs on the landing page
 *
 * @author Laxmi Lama-Palladino
 */
public class OseriesEdgeImageTests extends OseriesEdgeBaseTest
{
    OseriesEdgeHomePage selectName, action;

    /**
     *This test enables user to generate image and push the image to docker hub
     * XRAYOSE-33
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void generateImageTestDocker() throws InterruptedException {
        selectName = new OseriesEdgeHomePage(driver);
        clickGenerateImage();
        assertTrue(selectName.generateImage(), "User is not able to create container image.");

    }
    /**
     *This test enables user to search template and perform actions like push
     * XRAYOSE-45
     */
    public void actionButtonTest() throws InterruptedException {
        action = new OseriesEdgeHomePage(driver);
        actionButton();
        assertTrue(action.clickAction(), "User is not able to click action button.");
    }
}
