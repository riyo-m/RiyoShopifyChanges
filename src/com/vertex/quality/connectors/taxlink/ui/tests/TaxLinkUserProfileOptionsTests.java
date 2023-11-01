package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkUserProfileOptionsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * This class contains all the test methods for Profile Options tab
 *
 * @author Shilpi.Verma
 */

public class TaxLinkUserProfileOptionsTests extends TaxLinkBaseTest
{

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		userPrOptPage = new TaxLinkUserProfileOptionsPage(driver);
		homePage = new TaxLinkHomePage(driver);
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * JIRA issue: COERPC-7544
	 *
	 * @throws IOException
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyViewProfileOptionsTest( )
	{
		assertTrue(userPrOptPage.viewProfileOptions(userPrOptPage.mapValues()));
	}

	/**
	 * JIRA issue: COERPC-7545
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void verifyAddEditProfileOptionsTest( )
	{
		assertTrue(userPrOptPage.addAndEditProfileOptions(userPrOptPage.mapValues(), OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * JIRA Issue: COERPC-7546
	 *
	 * @throws IOException
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyExportToCSVUserProfileOptionsTest( ) throws IOException
	{
		assertTrue(userPrOptPage.exportToCSV_UserProfileOptions(userPrOptPage.mapValues()));
	}

	/**
	 * JIRA Issue: COERPC-10932
	 *
	 * @throws IOException
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyExportToCSVSystemProfileOptionsTest( ) throws Exception
	{
		assertTrue(userPrOptPage.exportToCSV_SystemProfileOptions());
	}
}
