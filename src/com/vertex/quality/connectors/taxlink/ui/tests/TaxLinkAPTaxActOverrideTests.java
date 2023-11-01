package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkAPTaxActionOverridePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the smoke test cases for AP Tax action override page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkAPTaxActOverrideTests extends TaxLinkBaseTest
{

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		apTaxActOverridePage = new TaxLinkAPTaxActionOverridePage(driver);
		homePage = new TaxLinkHomePage(driver);
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Verify Add AP Tax Action Override functionality
	 * JIRA Issues:COERPC-10821
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAPTaxActOverrideTest( )
	{
		assertTrue(apTaxActOverridePage.addAPTaxActOverride());
	}

	/**
	 * Verify Edit AP Tax Action Override functionality
	 * JIRA Issues:COERPC-10822
	 */
	@Test(groups = "taxlink_ui_regression")
	public void editAPTaxActOverrideTest( )
	{
		assertTrue(apTaxActOverridePage.editAPTaxActOverride());
	}

	/**
	 * Verify View AP Tax Action Override functionality
	 * JIRA Issues:COERPC-10831
	 */
	@Test(groups = "taxlink_ui_regression")
	public void viewAPTaxActOverrideTest( )
	{
		assertTrue(apTaxActOverridePage.viewAPTaxActOverride());
	}

	/**
	 * Verify export To CSV in AP Tax Action Override for Undercharged functionality
	 * JIRA Issues:COERPC-10832
	 */
	@Test(groups = "taxlink_ui_regression")
	public void exportToCSV_UnderchargedOverrideTest( ) throws Exception
	{
		assertTrue(
			apTaxActOverridePage.exportToCSV_Undercharged(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Verify export To CSV in AP Tax Action Override for Overcharged functionality
	 * JIRA Issues:COERPC-10833
	 */
	@Test(groups = "taxlink_ui_regression")
	public void exportToCSV_OverchargedOverrideTest( ) throws Exception
	{
		assertTrue(
			apTaxActOverridePage.exportToCSV_Overcharged(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Verify export To CSV in AP Tax Action Override for zerocharged functionality
	 * JIRA Issues:COERPC-10834
	 */
	@Test(groups = "taxlink_ui_regression")
	public void exportToCSV_ZerochargedOverrideTest( ) throws Exception
	{
		assertTrue(
			apTaxActOverridePage.exportToCSV_Zerocharged(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * JIRA Issue:COERPC-10907
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void emptyFields_reflectAllTest( )
	{
		assertTrue(apTaxActOverridePage.emptyFields_reflectAll());
	}
}
