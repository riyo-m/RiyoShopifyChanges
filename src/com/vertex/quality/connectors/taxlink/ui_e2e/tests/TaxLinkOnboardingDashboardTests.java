package com.vertex.quality.connectors.taxlink.ui_e2e.tests;

import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.OnboardingDashboardCloudCustomer;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkOnboardingDashboardPage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * this class represents onboarding of a new instance
 * along with Cloud@customer scenario.
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
		initialization_Taxlink();
	}

	/**
	 * Create a new instance with Cloud@customer
	 * on Add new instance Page
	 *
	 * JIRA ISSUE:COERPC-8878
	 */
	@Test(groups = { "taxlink_ui_e2e_regression","taxlink_ui_regression" })
	public void addAndVerifyCloudAtCustInstanceTest( )
	{
		assertFalse(homePage.selectInstance(OnboardingDashboardCloudCustomer.INSTANCE_DETAILS.instanceNameWithDC));
		assertTrue(instancePage.addAndVerifyInstanceForCC());
	}

	/**
	 * Create a new instance on Add new instance Page
	 * that will be used for all the tests (i.e. ecog-dev3)
	 */
	@Test(groups = { "taxlink_ui_e2e_regression" })
	public void addAndVerifyInstanceTest( )
	{
		assertFalse(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		assertTrue(instancePage.addAndVerifyInstance(true));
	}
}


