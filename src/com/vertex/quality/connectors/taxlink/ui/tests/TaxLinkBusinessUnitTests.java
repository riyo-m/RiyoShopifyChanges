package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkBusinessUnitPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the smoke test cases for Business Unit page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkBusinessUnitTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		buPage = new TaxLinkBusinessUnitPage(driver);
		utils = new TaxLinkUIUtilities();
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Add Business Unit in Tax link application
	 *
	 * COERPC-6962 & 7355
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAndVerifyBusinessUnitTest( )
	{
		assertTrue(buPage.addAndVerifyBusinessUnit());
	}

	/**
	 * View Business Unit in Tax link application
	 * COERPC-7255
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void viewBusinessUnitTest( )
	{
		assertTrue(buPage.viewBusinessUnit());
	}

	/**
	 * Edit Business Unit in Tax link application
	 * COERPC-7256
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void editBusinessUnitTest( )
	{
		assertTrue(buPage.editBusinessUnit());
	}

	/**
	 * Import Business Unit in Tax link application
	 * COERPC-7258
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void importBusinessUnitTest( )
	{
		assertTrue(buPage.importBusinessUnit());
	}

	/**
	 * Export to CSV Business Unit in Tax link application
	 * COERPC-7257
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void exportToCSVBusinessUnitTest( ) throws IOException
	{
		assertTrue(buPage.exportToCSVBusinessUnit(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}
}