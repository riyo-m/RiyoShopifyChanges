package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkLookupsPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the smoke test cases for Look ups page
 * of Monitoring section in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkLookupsTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		lookupsPage = new TaxLinkLookupsPage(driver);
		utils = new TaxLinkUIUtilities();
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Verify total number of records in Lookups table in Tax link application
	 * COERPC-7725
	 */

	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void verifyNumberOfRecordsInLookupTableTest( )
	{
		assertTrue(lookupsPage.verifyNumberOfRecords());
	}

	/**
	 * View Lookup - VTX_CALC_EXCL_TAXES in Lookups table in Tax link application
	 * COERPC-7726
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void viewLookup_VTX_CALC_EXCL_TAXES_Test( )
	{
		assertTrue(lookupsPage.viewLookup_VTX_CALC_EXCL_TAXES());
	}

	/**
	 * View Lookup - VTX_IMPOSITION_TYPE in Lookups table in Tax link application
	 * COERPC-7728
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void viewLookup_VTX_IMPOSITION_TYPE_Test( )
	{
		assertTrue(lookupsPage.viewLookup_VTX_IMPOSITION_TYPE());
	}

	/**
	 * Export To CSV - VTX_CALC_EXCL_TAXES in Lookups table in Tax link application
	 * COERPC-7727
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void exportToCSV_VTX_CALC_EXCL_TAXES_Test( ) throws IOException
	{
		assertTrue(lookupsPage.exportToCSV_VTX_CALC_EXCL_TAXES(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Export To CSV - VTX_IMPOSITION_TYPE in Lookups table in Tax link application
	 * COERPC-7729
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void exportToCSV_VTX_IMPOSITION_TYPE_Test( ) throws IOException
	{
		assertTrue(lookupsPage.exportToCSV_VTX_IMPOSITION_TYPE(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Export To CSV for Lookups table in Tax link application
	 * COERPC-7730
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void exportToCSVLookupsTest( ) throws IOException
	{
		assertTrue(lookupsPage.exportToCSVLookups(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}
}
