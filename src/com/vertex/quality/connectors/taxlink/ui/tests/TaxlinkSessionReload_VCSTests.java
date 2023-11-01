package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxlinkSessionReload_VCSPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

/**
 * This test class contains test for update of Customer Name in VCS after clicking on Reload icon
 *
 * @author Shilpi.Verma
 */
public class TaxlinkSessionReload_VCSTests extends TaxLinkBaseTest {

    @BeforeMethod(alwaysRun = true)
    public void loginTestCase() {
        signOnPage = new TaxLinkSignOnPage(driver);
        sessionReloadVSCPage = new TaxlinkSessionReload_VCSPage(driver);
        initialization_QA_VCS();
    }

    /**
     * JIRA Issue: COERPC-10966
     */
    @Test(groups = {"taxlink_ui_e2e_regression"})
    public void sessionReload_VCS_Test() {
        assertTrue(sessionReloadVSCPage.reloadSession());
    }

}
