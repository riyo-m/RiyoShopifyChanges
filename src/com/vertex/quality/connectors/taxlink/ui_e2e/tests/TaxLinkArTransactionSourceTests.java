package com.vertex.quality.connectors.taxlink.ui_e2e.tests;

import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkArTransactionSourcePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * this class represents test case for Import AR Transaction source
 * from Oracle ERP to Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkArTransactionSourceTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		arTransSrc = new TaxLinkArTransactionSourcePage(driver);
		initialization_Taxlink();
		homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
	}

	/**
	 * Import Transaction Source in Tax link application
	 * JIRA ISSUE: COERPC-8936
	 */
	@Test(groups = { "taxlink_ui_e2e_regression" })
	public void importArTransactionSourceFromERPTest( )
	{
		assertTrue(arTransSrc.importArTransSourceToTaxlink(String.valueOf(getFileReadPath().get(0))));
	}
}
