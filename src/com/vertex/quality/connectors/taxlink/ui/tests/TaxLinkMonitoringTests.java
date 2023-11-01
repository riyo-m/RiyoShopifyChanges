package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.Monitoring;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkMonitoringPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the smoke test cases for Retry Jobs page of Monitoring section
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkMonitoringTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		retryJobs = new TaxLinkMonitoringPage(driver);
		pollingJobs = new TaxLinkMonitoringPage(driver);
		summaryReports = new TaxLinkMonitoringPage(driver);
		transMonitoring = new TaxLinkMonitoringPage(driver);
		utils = new TaxLinkUIUtilities();
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Verify View Polling Jobs functionality under Monitoring section of Tax link UI,
	 * COERPC-7285
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void viewPollingJobsTest( ) throws InterruptedException
	{
		assertTrue(
			pollingJobs.verifyEditInstanceForPollingJobs()); //checks if AR and AP service subscriptions are checked for the selected instance
		assertTrue(pollingJobs.viewPollingJobs());
	}

	/**
	 * Verify Transaction Monitoring Status functionality under Monitoring section of Tax link UI,
	 * COERPC-10688
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void transactionMonitoringTest( ) throws InterruptedException
	{
		assertTrue(transMonitoring.transactionMonitoringStatus("AP_STANDARD_INVOICES","VTX_US_BU","300000027151336"));
	}

	/**
	 * Verify View Summary Report functionality under Monitoring section of Tax link UI,
	 * COERP-7287
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void viewSummaryReportTest( ) throws InterruptedException, IOException
	{
		assertTrue(summaryReports.viewSummaryReport(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Export To CSV for Polling Jobs table in Tax link application
	 * COERPC-7289
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void exportToCSVPollingJobsTest( ) throws IOException, InterruptedException
	{
		assertTrue(pollingJobs.exportToCSVPollingJobs(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}
}