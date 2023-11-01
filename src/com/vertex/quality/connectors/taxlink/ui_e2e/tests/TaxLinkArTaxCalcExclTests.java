package com.vertex.quality.connectors.taxlink.ui_e2e.tests;

/**
 * This class contains the test method for Add AR Tax Calculation Exclusions
 *
 * @author mgaikwad
 */

import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class TaxLinkArTaxCalcExclTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		arTaxCalcExclPage = new TaxLinkArTaxCalcExclPage(driver);
		homePage = new TaxLinkHomePage(driver);
		buPage = new TaxLinkBusinessUnitPage(driver);
		arTransSrc = new TaxLinkArTransactionSourcePage(driver);
		arTransType = new TaxLinkArTransactionTypePage(driver);
		initialization_Taxlink();
		homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
	}

	/**
	 * Method to add a Manual Invoice for "VTX_US_BU"
	 * for AR Tax Calculation Exclusions tab in TaxLink UI
	 * JIRA issue: COERPC-9693
	 */
	@Test(groups = "taxlink_ui_e2e_regression")
	public void addARTaxCalcExcl_e2e_Test( )
	{
		assertTrue(buPage.importBusinessUnitTaxlink("VTX_US_BU"));
		assertTrue(arTransSrc.importArTransSourceToTaxlink("Manual"));
		assertTrue(arTransType.importArTransTypeToTaxlink("Invoice"));
		assertTrue(arTaxCalcExclPage.addARTaxCalcExcl("VTX_US_BU", "Manual", "Invoice"));
	}
}
