package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkOfflineBIPExtractJobsPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * This class contains all the test methods for Offline BIP Extract Jobs
 *
 * @author Shilpi.Verma
 */
public class TaxLinkOfflineBIPExtractJobsTests extends TaxLinkBaseTest
{

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		offBIPPage = new TaxLinkOfflineBIPExtractJobsPage(driver);
		homePage = new TaxLinkHomePage(driver);
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * JIRA Issue: COERPC-7662
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void verifyEnableBIPsTest( )
	{
		assertTrue(offBIPPage.enableBIPs());
	}

	/**
	 * JIRA Issue: COERPC-7663
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyEditBIPs_DailyTest( )
	{
		assertTrue(offBIPPage.editDailyFrequencyBIPs());
	}

	/**
	 * JIRA Issue: COERPC-10747
	 */
	@Test(groups = "taxlink_ui_regression")
	public void verifyEditBIPs_Daily_TwiceADayTest( )
	{
		assertTrue(offBIPPage.editDailyRunTwiceADay());
	}

	/**
	 * JIRA Issue: COERPC-7665
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyEditBIPs_WeeklyTest( )
	{
		assertTrue(offBIPPage.editWeeklyFrequencyBIPs());
	}

	/**
	 * JIRA Issue: COERPC-9171
	 */
	@Test(groups = "taxlink_ui_regression")
	public void verifyStatusBIPsTest( )
	{
		assertTrue(offBIPPage.statusBIPS());
	}

	/**
	 * JIRA Issue: COERPC- 7662
	 */
	@Test(groups = "taxlink_ui_regression")
	public void verifyEnabledStatusBIPsTest( )
	{
		assertTrue(offBIPPage.changeStatus());
	}

	/**
	 * JIRA Issue: COERPC- 10051
	 */
	@Test(groups = "taxlink_ui_regression")
	public void verifySearchBIPsTest( )
	{
		assertTrue(offBIPPage.searchBIPS());
	}

	/**
	 * JIRA Issue: COERPC-7666
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyExportToCSVOfflineBIPSTest( ) throws Exception
	{
		assertTrue(offBIPPage.exportToCSVOfflineBIPS());
	}
}
