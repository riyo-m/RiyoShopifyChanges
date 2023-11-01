package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSuppliesPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Container for all test cases targeted at the Supplies tab.
 *
 * @author msalomone
 */
public class TaxLinkSuppliesTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		suppliesPage = new TaxLinkSuppliesPage(driver);
		homePage = new TaxLinkHomePage(driver);
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/*
	 * Add and verify AR Tax calc exclusion record in Tax link application
	 * COERPC-7279
	 */

	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAndVerifyArTaxCalculationExclusionsTest( )
	{
		assertTrue(suppliesPage.addAndVerifyArTaxExclusion());
	}

	/*
	 * View AR Tax calc exclusion record in Tax link application
	 * COERPC-7281
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void viewArTaxCalculationExclusionsTest( )
	{
		assertTrue(suppliesPage.viewAndVerifyArExclusion());
	}

	/*
	 * Edit AR Tax calc exclusion record in Tax link application
	 * COERPC-7282
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void editArTaxCalculationExclusionsTest( )
	{
		assertTrue(suppliesPage.editAndVerifyArExclusion());
	}

	/**
	 * JIRA Issue:COERPC-10905
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void emptyFields_reflectAllTest( )
	{
		assertTrue(suppliesPage.emptyFields_reflectAll());
	}

	/*
	 * Export to CSV for AR Tax calc exclusion record in Tax link application
	 * COERPC-7283
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void exportToCSVArTaxCalculationExclusionsTest( )
	{
		assertTrue(suppliesPage.exportAndVerifyArExclusions("Fusion Instance Name: ecog-dev3-us2"));
	}
}
