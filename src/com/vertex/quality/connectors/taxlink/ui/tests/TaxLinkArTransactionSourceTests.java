package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkArTransactionSourcePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the smoke test cases for Ar Transaction Source page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkArTransactionSourceTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		arTransactionSourcePage = new TaxLinkArTransactionSourcePage(driver);
		utils = new TaxLinkUIUtilities();
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Add Ar Transaction Source in Tax link application
	 * in Tax link application
	 * COERP-7265
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAndVerifyArTransactionSourceTest( )
	{
		assertTrue(arTransactionSourcePage.addAndVerifyArTransactionSource());
	}

	/**
	 * View Ar Transaction Source in Tax link application
	 *
	 * COERP-7267
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void viewArTransactionSourceTest( )
	{
		assertTrue(arTransactionSourcePage.viewArTransactionSource());
	}

	/*
	 * Edit Ar Transaction Source in Tax link application
	 * COERP-7268
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void editArTransactionSourceTest( )
	{
		assertTrue(arTransactionSourcePage.editArTransactionSource());
	}

	/*
	 * Import Ar Transaction Source in Tax link application
	 * COERP-7270
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void importArTransactionSourceTest( )
	{
		assertTrue(arTransactionSourcePage.importArTransactionSource());
	}

	/*
	 * Export to CSV Ar Transaction Source in Tax link application
	 * COERP-7269
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void exportToCSVArTransactionSourceTest( ) throws IOException
	{
		assertTrue(arTransactionSourcePage.exportToCSVArTransactionSource(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}
}
