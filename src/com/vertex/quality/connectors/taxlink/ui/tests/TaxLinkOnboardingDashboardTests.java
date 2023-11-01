package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkOnboardingDashboardPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * this class represents all the smoke test cases for Onboarding Dashboard.
 *
 * @author mgaikwad
 */

public class TaxLinkOnboardingDashboardTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		instancePage = new TaxLinkOnboardingDashboardPage(driver);
		utils = new TaxLinkUIUtilities();
		initialization();
	}

	/**
	 * Create a new instance on Add new instance Page
	 *
	 * COERPC-7249
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAndVerifyInstanceTest( )
	{
		assertFalse(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		assertTrue(
			instancePage.addAndVerifyInstance("ecog-dev3", "DEV", "us2", ERP_USERNAME, ERP_PASSWORD, ERP_TRUSTEDID,
				"ecog-dev3-us2", true));
	}

	/**
	 * Verify View functionality on View page for added instance
	 *
	 * COERPC-7251
	 **/
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyViewInstanceTest( )
	{
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		assertTrue(instancePage.verifyViewInstancePage());
	}

	/*
	 * Verify Edit functionality on Edit page for added instance
	 *
	 * COERPC-7252
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyEditInstanceTest( )
	{
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		assertTrue(instancePage.verifyEditInstancePage());
	}

	/*
	 * Verify Export to CSV functionality on instance page
	 *
	 * COERPC-7253
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void exportToCSVOnboardingDashboardTest( ) throws IOException, InterruptedException
	{
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		assertTrue(
			instancePage.exportToCSVOnboardingDashboard(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	@AfterMethod(alwaysRun = true)
	public void logOutTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		tearDown();
	}
}


