package com.vertex.quality.connectors.taxlink.ui_e2e.tests;

import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkArTransactionTypePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * this class represents test case for Import AR Transaction type
 * from Oracle ERP to Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkArTransactionTypeTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		arTransType = new TaxLinkArTransactionTypePage(driver);
		initialization_Taxlink();
		homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
	}

	/**
	 * Import Transaction type in Tax link application
	 * JIRA ISSUE: COERPC-8920
	 */
	@Test(groups = { "taxlink_ui_e2e_regression", "taxlink_e2e_smoke" })
	public void importArTransactionTypeFromERPTest( )
	{
		assertTrue(arTransType.importArTransTypeToTaxlink(String.valueOf(getFileReadPath().get(0))));
	}
}
