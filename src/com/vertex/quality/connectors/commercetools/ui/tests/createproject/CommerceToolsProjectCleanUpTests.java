package com.vertex.quality.connectors.commercetools.ui.tests.createproject;

import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import com.vertex.quality.connectors.commercetools.ui.pages.CommerceToolsCreateProjectPage;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * CSAPCT-463:CCT - Automate Project Cleanup process.
 *
 * @author Vivek.Kumar
 */
public class CommerceToolsProjectCleanUpTests extends CommerceToolAPITestUtilities {
    String projectKey;

    /**
     * Test case to cleanUp unnecessary created Projects from commerce tools merchant center
     */
    @Test(groups = "commercetools_DeleteProject")
    public void CommerceToolsProjectCleanUpTest() {
        projectKey = System.getProperty("projectKey");
        String[] projectKeys = projectKey.split(",");

        createDriver();
        openManageProject();
        CommerceToolsCreateProjectPage page = new CommerceToolsCreateProjectPage(driver);
        page.cleanProjectsList(Arrays.asList(projectKeys));

    }
}