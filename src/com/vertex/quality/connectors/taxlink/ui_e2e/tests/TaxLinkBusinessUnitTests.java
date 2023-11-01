package com.vertex.quality.connectors.taxlink.ui_e2e.tests;

import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkBusinessUnitPage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * this class represents E2E test cases for Business Unit page
 * from Oracle ERP to Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkBusinessUnitTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		buPage = new TaxLinkBusinessUnitPage(driver);
		initialization_Taxlink();
		homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
	}

	/**
	 * Import Business Unit in Tax link application
	 * JIRA Issue: COERPC-8877
	 */
	@Test(groups = { "taxlink_ui_e2e_regression" })
	public void importBusinessUnitTest( )
	{
		assertTrue(buPage.importBusinessUnitTaxlink(String.valueOf(getFileReadPath().get(0))));
	}
}
