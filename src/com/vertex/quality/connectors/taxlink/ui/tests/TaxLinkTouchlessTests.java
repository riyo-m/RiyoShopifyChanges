package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkMonitoringPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkTouchlessPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * Container for all test cases targeted for the Touchless Resubmission functionality
 *
 * @author Aman.Jain
 */

public class TaxLinkTouchlessTests extends TaxLinkBaseTest
{
	/**
	 * Initializing method
	 */
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		touchlessPage = new TaxLinkTouchlessPage(driver);
		retryJobs = new TaxLinkMonitoringPage(driver);
		homePage = new TaxLinkHomePage(driver);
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Test to verify the Touchless Resubmission enabled or disabled
	 * COERPC-10809
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void verifyEnabledTouchlessTest( ) throws Exception
	{
		assertTrue(touchlessPage.verifyRetryJobsButton(retryJobs));
	}
}
