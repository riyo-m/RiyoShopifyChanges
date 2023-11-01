package com.vertex.quality.connectors.taxlink.ui_e2e.tests;

/**
 * This class contains the test method for Add AP Tax Calculation Exclusions
 *
 * @author mgaikwad
 */

import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkApTaxCalcExclPage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkBusinessUnitPage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class TaxLinkApTaxCalcExclTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		apTaxCalcExclPage = new TaxLinkApTaxCalcExclPage(driver);
		homePage = new TaxLinkHomePage(driver);
		buPage = new TaxLinkBusinessUnitPage(driver);
		initialization_Taxlink();
		homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
	}

	/**
	 * JIRA issue: COERPC-9539
	 */
	@Test(groups = { "taxlink_ui_e2e_regression", "taxlink_e2e_smoke" })
	public void addAPTaxCalcExcl_e2e_Test( )
	{
		assertTrue(buPage.importBusinessUnitTaxlink("VTX_US_BU"));
		assertTrue(apTaxCalcExclPage.addAPTaxCalcExcl());
	}

	/**
	 * Method to disable all Manual Invoices for "VTX_US_BU"
	 * for AP Tax Calculation Exclusions tab in TaxLink UI
	 * reason being rules won't get applied to a transaction until and unless
	 * these records are disabled
	 */
	@Test(groups = { "taxlink_ui_e2e_regression", "taxlink_rulesmapping_regression" })
	public void disableApTaxCalcExclTest( )
	{
		assertTrue(apTaxCalcExclPage.disableApTaxCalcExcl());
	}
}
