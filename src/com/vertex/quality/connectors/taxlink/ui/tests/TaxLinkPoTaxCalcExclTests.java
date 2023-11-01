package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkPoTaxCalcExclPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the test cases for PO Tax calculation Exclusion page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkPoTaxCalcExclTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		poTaxCalcExclPage = new TaxLinkPoTaxCalcExclPage(driver);
		utils = new TaxLinkUIUtilities();
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Add PO Tax calc excl record in Tax link application
	 * COERPC-10616
	 */

	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAndVerifyPoTaxCalcExclTest( )
	{
		assertTrue(poTaxCalcExclPage.addAndVerifyPOTaxCalcExcl());
	}

	/**
	 * View PO Tax calc excl record in Tax link application
	 * COERPC-10617
	 */

	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void viewPoTaxCalcExclTest( )
	{
		assertTrue(poTaxCalcExclPage.viewPOTaxCalcExcl());
	}

	/**
	 * Edit PO Tax calc excl record in Tax link application
	 * COERPC-10618
	 */

	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void editPoTaxCalcExclTest( )
	{
		assertTrue(poTaxCalcExclPage.editPOTaxCalcExcl());
	}

	/**
	 * Export To CSV - PO Tax calc excl record in Tax link application
	 * COERPC-10619
	 */

	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void exportToCSVPoTaxCalcExclTest( ) throws IOException
	{
		assertTrue(poTaxCalcExclPage.exportToCSVPoTaxCalcExcl(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * JIRA Issue:COERPC-10910
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void emptyFields_reflectAllTest( )
	{
		assertTrue(poTaxCalcExclPage.emptyFields_reflectAll());
	}
}
