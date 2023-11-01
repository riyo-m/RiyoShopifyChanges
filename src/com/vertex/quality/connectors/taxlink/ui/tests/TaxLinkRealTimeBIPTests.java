package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkRealTimeBIPPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * This class contains all the test methods for Real Time BIP
 *
 * @author Shilpi.Verma
 */
public class TaxLinkRealTimeBIPTests extends TaxLinkBaseTest
{

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		realTimeBIPPage = new TaxLinkRealTimeBIPPage(driver);
		homePage = new TaxLinkHomePage(driver);

		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * JIRA Issue: COERPC-10728
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void verifyEnableRealTimeBIPsTest( )
	{
		assertTrue(realTimeBIPPage.enableBIPs());
	}

	/**
	 * JIRA Issue: COERPC-10729
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void verifyEditRealTimeBIPsTest( ) throws Exception
	{
		assertTrue(realTimeBIPPage.editRealTimeBIP());
	}

	/**
	 * JIRA Issue: COERPC-10922
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyExportToCSVRealTimeBIPTest( ) throws Exception
	{
		assertTrue(realTimeBIPPage.exportToCSVRealTimeBIP());
	}
}
