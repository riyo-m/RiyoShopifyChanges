package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.CleanUpInstance;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkCloneInstancePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkUsersPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkOnboardingDashboardPage;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * This page contains all the test assertions of Clone Instance page in TaxLink
 *
 * @author Shilpi.Verma, mgaikwad
 */

public class TaxLinkCloneInstanceTests extends TaxLinkBaseTest {
    @BeforeMethod(alwaysRun = true)
    public void loginTestCase() {
        signOnPage = new TaxLinkSignOnPage(driver);
        cloneInstancePage = new TaxLinkCloneInstancePage(driver);
        homePage = new TaxLinkHomePage(driver);
        initialization();
        homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
    }

    /**
     * JIRA Issue: COERPC-8715
     */
    @Test(groups = "taxlink_ui_regression")
    public void cloneInstanceTest() throws Exception {
        assertTrue(cloneInstancePage.cloneInstance());
    }

    /**
     * JIRA Issue: COERPC-11146
     *
     * @throws IOException
     */
    @Test
    public void cloneInstanceWithoutDefaultRulesTest() throws IOException {
        assertTrue(cleanUpInstancePage.verifyCleanUpInstance("test2"));
        assertTrue(new TaxLinkOnboardingDashboardPage(driver).addAndVerifyInstanceForCC());
        assertTrue(cloneInstancePage.cloneWithoutDefaultRules());
    }

    /**
     * JIRA Issue: COERPC-10923
     */
    @Test(groups = {"taxlink_ui_regression"})
    public void verifyExportToCSVCloneInstanceTest() throws Exception {
        assertTrue(cloneInstancePage.exportToCSVCloneInstance());
    }

    /**
     * Verify that Non FI_ADMIN instances are not present in the instance dropdown i.e. LOV
     * are not listed in the Clone instance source and target dropdowns for cloning
     * in Taxlink UI
     * JIRA Issue: COERPC-11051
     */
    @Test(groups = "taxlink_ui_regression")
    public void verifyCloneInstanceAsPerChangeInRoleForInstanceTest() throws IOException {
        cloneInstancePage.navigateToUsersTab();
        assertTrue(cloneInstancePage.selectUser(CleanUpInstance.Details.user));
        assertTrue(cloneInstancePage.removeRoleFromAnInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
        assertTrue(cloneInstancePage.verifyCloneInstanceAsPerChangeInRole(CleanUpInstance.Details.user,
                OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
        cloneInstancePage.clickOnUsersTab();
        new TaxLinkUsersPage(driver).editInstanceRole("FI_ADMIN", OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
    }
}
