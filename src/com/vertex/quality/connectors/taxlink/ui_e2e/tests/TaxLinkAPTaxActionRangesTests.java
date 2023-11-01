package com.vertex.quality.connectors.taxlink.ui_e2e.tests;

import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkAPTaxActRangesPage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkApTaxCalcExclPage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * This class contains TaxLink part of test method to verify Overcharged tax action in Taxlink
 *
 * @author Shilpi.Verma, mgaikwad
 */
public class TaxLinkAPTaxActionRangesTests extends TaxLinkBaseTest
{

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		taRangePage = new TaxLinkAPTaxActRangesPage(driver);
		apTaxCalcExclPage = new TaxLinkApTaxCalcExclPage(driver);
		initialization_Taxlink();
		homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
	}

	/**
	 * JIRA Issue: COERPC-9174
	 */
	@Test(groups = { "taxlink_ui_e2e_regression", "taxlink_e2e_smoke" })
	public void addTaxAction_e2e_OverchargedTest( )
	{
		assertTrue(taRangePage.addAPTaxActRanges_Overcharged());
	}

	/**
	 * JIRA Issue: COERPC- 9466
	 */
	@Test(groups = { "taxlink_ui_e2e_regression", "taxlink_e2e_smoke" })
	public void addTaxAction_e2e_UnderchargedTest( )
	{
		assertTrue(taRangePage.addAPTaxActRanges_Undercharged());
	}

	/**
	 * JIRA Issue: COERPC- 9836
	 */
	@Test(groups = { "taxlink_ui_e2e_regression", "taxlink_e2e_smoke" })
	public void addTaxAction_e2e_ZerochargedTest( )
	{
		assertTrue(taRangePage.addAPTaxActRanges_Zerocharged());
	}

	/**
	 * Method to disable all existing Apply hold records
	 * from Tax action ranges tab in TaxLink UI
	 * reason being Invoice won't get validated in ERP until and unless
	 * these records are disabled, it goes to 'Needs re-validation' stage
	 */

	@Test(groups = { "taxlink_rulesmapping_regression", "taxlink_ui_e2e_regression", "taxlink_e2e_smoke" })
	public void disableApplyHoldRecordsTest( )
	{
		assertTrue(taRangePage.disableApplyHoldRecords());
	}

}
