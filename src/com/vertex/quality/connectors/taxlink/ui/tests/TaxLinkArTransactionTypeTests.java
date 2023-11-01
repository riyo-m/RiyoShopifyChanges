package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkArTransactionTypePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the smoke test cases for Ar Transaction Type page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkArTransactionTypeTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		arTransactionTypePage = new TaxLinkArTransactionTypePage(driver);
		utils = new TaxLinkUIUtilities();
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Add Ar Transaction Type in Tax link application
	 * COERP-7259
	 */

	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAndVerifyArTransactionTypeTest( )
	{
		assertTrue(arTransactionTypePage.addAndVerifyArTransactionType());
	}

	/**
	 * View Ar Transaction Type in Tax link application
	 * COERP-7261
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void viewArTransactionTypeTest( )
	{
		assertTrue(arTransactionTypePage.viewArTransactionType());
	}

	/*
	 * Edit Ar Transaction Type  in Tax link application
	 * COERP-7262
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void editArTransactionTypeTest( )
	{
		assertTrue(arTransactionTypePage.editArTransactionType());
	}

	/*
	 * Import Ar Transaction Type in Tax link application
	 * COERP-7264
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void importArTransactionTypeTest( )
	{
		assertTrue(arTransactionTypePage.importArTransactionType());
	}

	/*
	 * Export to CSV Ar Transaction Type in Tax link application
	 * COERP-7263
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void exportToCSVArTransactionTypeTest( ) throws IOException
	{
		assertTrue(arTransactionTypePage.exportToCSVArTransactionType(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}
}
