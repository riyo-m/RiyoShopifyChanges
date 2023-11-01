package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkAPTaxActionRangesPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class TaxLinkAPTaxActRangesTests extends TaxLinkBaseTest
{

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		apTaxActRangesPage = new TaxLinkAPTaxActionRangesPage(driver);
		homePage = new TaxLinkHomePage(driver);
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * JIRA Issues: COERPC-7996,7997,7998
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAPTaxActRangesTest( )
	{
		assertTrue(apTaxActRangesPage.addAPTaxActRanges());
	}

	/**
	 * JIRA Issues: COERPC-7999,8000,8001
	 */
	@Test(groups = "taxlink_ui_regression")
	public void editAPTaxActRangesTest( )
	{
		assertTrue(apTaxActRangesPage.editAPTaxActRanges());
	}

	/**
	 * JIRA Issues: COERPC-8002,8003,8004
	 */
	@Test(groups = "taxlink_ui_regression")
	public void viewAPTaxActRangesTest( )
	{
		assertTrue(apTaxActRangesPage.viewAPTaxActRanges());
	}

	/**
	 * JIRA Issue: COERPC-8350
	 */
	@Test(groups = "taxlink_ui_regression")
	public void negative_amtTo_endDate_TaxActionRangesTest( )
	{
		assertTrue(apTaxActRangesPage.negative_addTaxActionRanges());
	}

	/**
	 * JIRA Issue: COERPC-8005
	 *
	 * @throws IOException
	 */
	@Test(groups = "taxlink_ui_regression")
	public void exportToCSV_UnderchargedTest( ) throws Exception
	{
		assertTrue(apTaxActRangesPage.exportToCSV_Undercharged());
	}

	/**
	 * JIRA Issue: COERPC-8005
	 *
	 * @throws IOException
	 */
	@Test(groups = "taxlink_ui_regression")
	public void exportToCSV_OverchargedTest( ) throws Exception
	{
		assertTrue(apTaxActRangesPage.exportToCSV_Overcharged());
	}

	/**
	 * JIRA Issue: COERPC-8005
	 *
	 * @throws IOException
	 */
	@Test(groups = "taxlink_ui_regression")
	public void exportToCSV_ZerochargedTest( ) throws Exception
	{
		assertTrue(apTaxActRangesPage.exportToCSV_Zerocharged());
	}

	/**
	 * JIRA Issue:COERPC-10906
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void emptyFields_reflectAllTest( )
	{
		assertTrue(apTaxActRangesPage.emptyFields_reflectAll());
	}
}
