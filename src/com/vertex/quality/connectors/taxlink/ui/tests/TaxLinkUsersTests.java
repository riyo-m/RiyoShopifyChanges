package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.common.TaxLinkDatabase;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkUsersPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the regression test cases for Users page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkUsersTests extends TaxLinkBaseTest
{
	public String userName;
	public TaxLinkDatabase dbPage;

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		usersPage = new TaxLinkUsersPage(driver);
		utils = new TaxLinkUIUtilities();
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Verify Sync and Edit User (i.e. assign roles to user) functionality in Users tab in Tax link application
	 * COERPC-7290 & COERPC-7292
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void syncAndEditUsersTest( )
	{
		userName = usersPage.addUserFromSyncUsersPopUp();
		assertTrue(usersPage.editUsers(userName));
	}

	/**
	 * View Users functionality which also checks for enabled/disabled users according to roles assigned
	 * in Users tab in Tax link application
	 * COERPC-7291
	 */

	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void viewUsersTest( )
	{
		assertTrue(usersPage.viewUser());
	}

	/**
	 * Export To CSV for Users table in Tax link application
	 * COERPC-7293
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void exportToCSVUsersTest( )
	{
		assertTrue(usersPage.exportToCSVUsers());
	}
}
