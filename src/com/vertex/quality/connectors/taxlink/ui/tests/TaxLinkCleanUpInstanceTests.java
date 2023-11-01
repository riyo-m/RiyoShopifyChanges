package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.CleanUpInstance;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * This page contains all the test assertions of Clean Up Instance page in TaxLink
 *
 * @author mgaikwad
 */

public class TaxLinkCleanUpInstanceTests extends TaxLinkBaseTest {
    @BeforeMethod(alwaysRun = true)
    public void loginTestCase() {
        signOnPage = new TaxLinkSignOnPage(driver);
        instancePage = new TaxLinkOnboardingDashboardPage(driver);
        cleanUpInstancePage = new TaxLinkCleanUpInstancePage(driver);
        homePage = new TaxLinkHomePage(driver);
        initialization();
        homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
    }

    /**
     * Verify that all instances present in the instance dropdown i.e. LOV
     * are listed in the summary page of Cleanup instance page in Taxlink UI
     * <p>
     * JIRA Issue: COERPC-10986
     */
    @Test(groups = "taxlink_ui_regression")
    public void verifyAllInstancesInSummaryOfCleanUpTest() {
        if (!homePage.selectInstance(CleanUpInstance.Details.prodInstance)) {
            assertTrue(
                    instancePage.addAndVerifyInstance("ecog-dev1", "PROD", "us2", ERP_USERNAME, ERP_PASSWORD, ERP_TRUSTEDID,
                            "ecog-dev1-us2", true));
        }
        assertTrue(cleanUpInstancePage.verifyAllInstancesInSummaryOfCleanUp());
    }

    /**
     * Verify that Prod instances are not listed in the instance clean up dropdown
     * on the Cleanup instance page
     * JIRA Issue: COERPC-10987
     */
    @Test(groups = "taxlink_ui_regression")
    public void verifyNonProdInstancesInSourceCleanUpTest() {
        assertTrue(cleanUpInstancePage.verifyNonProdInstancesInSourceCleanUp(CleanUpInstance.Details.prodInstance));
    }

    /**
     * Verify that Non FI_ADMIN instances are not present in the instance dropdown i.e. LOV
     * are not listed in the Cleanup instance dropdown for cleanup
     * in Taxlink UI
     * JIRA Issue: COERPC-10988
     */
    @Test(groups = "taxlink_ui_regression")
    public void verifyCleanUpAsPerChangeInRoleForInstanceTest() {
        assertTrue(cleanUpInstancePage.verifyCleanUpInstanceAsPerChangeInRole(CleanUpInstance.Details.user,
                OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
    }

    /**
     * Export To CSV for Cleanup Instance table in Tax link application
     * JIRA Issue: COERPC-10989
     */
    @Test(groups = {"taxlink_ui_regression"})
    public void verifyExportToCSVCleanUpInstanceTest() throws Exception {
        assertTrue(cleanUpInstancePage.exportToCSVCleanUpInstance());
    }

    /**
     * Verify the cleanup functionality for Non-prod, FI_ADMIN instances
     * in Cleanup tab in Taxlink application
     * JIRA Issue: COERPC-10990
     */
    @Test(groups = "taxlink_ui_regression")
    public void verifyCleanUpInstanceTest() throws IOException {
        assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
        new TaxLinkUsersPage(driver).editInstanceRole("FI_ADMIN", OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
        assertTrue(cleanUpInstancePage.verifyCleanUpInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
        assertTrue(homePage.selectInstance(CleanUpInstance.Details.cloudInstance));
        assertTrue(cleanUpInstancePage.verifyCleanUpInstance(CleanUpInstance.Details.cloudInstance));
        assertTrue(cleanUpInstancePage.verifyCleanUpInstance("ecog-dev2-us2"));
    }
}