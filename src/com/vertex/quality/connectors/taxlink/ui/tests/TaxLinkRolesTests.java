package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkRolesPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the smoke test cases for Roles Tab contained
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkRolesTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		roles = new TaxLinkRolesPage(driver);
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/*
	 * View Roles in Tax link application
	 * COERPC-10857
	 */

	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void viewRolesTest( )
	{
		assertTrue(roles.viewRoles());
	}
}
