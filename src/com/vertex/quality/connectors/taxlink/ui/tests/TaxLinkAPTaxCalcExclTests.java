package com.vertex.quality.connectors.taxlink.ui.tests;

/**
 * This class contains all the test methods for AP Tax Calculation Exclusions tab
 *
 * @author Shilpi.Verma
 */

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkAPTaxCalcExclPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class TaxLinkAPTaxCalcExclTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		apTaxCalcExclPage = new TaxLinkAPTaxCalcExclPage(driver);
		homePage = new TaxLinkHomePage(driver);
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * JIRA issue: COERPC-7593
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke", "taxlink_ui_e2e_regression" })
	public void addAPTaxCalcExclTest( )
	{
		assertTrue(apTaxCalcExclPage.addAPTaxCalcExcl());
	}

	/**
	 * JIRA issue: COERPC-7951
	 */
	@Test(groups = "taxlink_ui_regression")
	public void addAPTaxCalcExcl_SingleRec_Test( )
	{
		assertTrue(apTaxCalcExclPage.addAPTaxCalcExcl_SingleRec());
	}

	/**
	 * JIRA issue: COERPC-7594
	 */
	@Test(groups = "taxlink_ui_regression")
	public void editAPTaxCalcExclTest( )
	{
		assertTrue(apTaxCalcExclPage.editAPTaxCalcExcl());
	}

	/**
	 * JIRA issue: COERPC-7595
	 */
	@Test(groups = "taxlink_ui_regression")
	public void viewAPTaxCalcExclTest( )
	{
		assertTrue(apTaxCalcExclPage.viewAPTaxCalcExcl());
	}

	/*
	 * Export to CSV Ar Exclusions in Tax link application
	 * JIRA issue: COERPC-7596
	 */
	@Test(groups = "taxlink_ui_regression")
	public void exportToCSVAPTaxCalcExclTest( ) throws Exception
	{
		assertTrue(apTaxCalcExclPage.exportToCSVAPTaxCalcExcl());
	}

	/**
	 * JIRA Issue:COERPC-10909
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void emptyFields_reflectAllTest( )
	{
		assertTrue(apTaxCalcExclPage.emptyFields_reflectAll());
	}
}
