package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkAPScanINVSrcPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * This class contains all the test methods for AP Scan Invoice Source tab
 *
 * @author Shilpi.Verma
 */

public class TaxLinkAPScanInvSrcTests extends TaxLinkBaseTest
{

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		apScanInvSrcPage = new TaxLinkAPScanINVSrcPage(driver);
		homePage = new TaxLinkHomePage(driver);
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * JIRA Issue: COERPC-7822
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAPScanInvSrcTest( )
	{
		assertTrue(apScanInvSrcPage.addAPScanInvSrc());
	}

	/**
	 * JIRA Issue: COERPC-7823
	 */
	@Test(groups = "taxlink_ui_regression")
	public void editAPScanInvSrcTest( )
	{
		assertTrue(apScanInvSrcPage.editAPScanInvSrc());
	}

	/**
	 * JIRA Issue: COERPC-7824
	 */
	@Test(groups = "taxlink_ui_regression")
	public void viewAPScanInvSrcTest( )
	{
		assertTrue(apScanInvSrcPage.viewAPScanInvSrc());
	}

	/**
	 * JIRA Issue: COERPC-8349
	 */
	@Test(groups = "taxlink_ui_regression")
	public void negative_start_end_dateAPScanInvSrcTest( )
	{
		assertTrue(apScanInvSrcPage.negative_start_end_dateAPScanInvSrc());
	}

	/**
	 * JIRA Issue: COERPC-7825
	 *
	 * @throws IOException
	 */
	@Test(groups = "taxlink_ui_regression")
	public void exportToCSVAPScanInvSrcTest( ) throws Exception
	{
		assertTrue(apScanInvSrcPage.exportToCSVAPScanInvSrc());
	}
}
