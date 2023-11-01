package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.CleanUpInstance;
import com.vertex.quality.connectors.taxlink.ui.enums.GenerateNewPassword;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkGenerateNewPasswordPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkUsersPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the regression test cases for Generate new password page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkGenerateNewPasswordTests extends TaxLinkBaseTest
{

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		newPwdPage = new TaxLinkGenerateNewPasswordPage(driver);
		initialization();
	}

	/**
	 * Verify generate new password functionality in Generate new password tab
	 * in Tax link application
	 * COERPC-10954
	 */

	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void verifyGenerateNewPasswordTest( )
	{
		homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
		assertTrue(newPwdPage.verifySyncedPassword());
	}

	/**
	 * Method to verify disabled Generate new password tab
	 * when Instance does not have FI_ADMIN roles assigned in Taxlink UI
	 *
	 * COERPC-11014
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void verifyDisabledGenNewPwdTabTest( ) throws IOException
	{
		homePage.selectInstance(GenerateNewPassword.GENERATE_NEW_PASSWORD_DETAILS.instanceName);
		assertTrue(newPwdPage.verifyDisabledGenNewPwdTab(GenerateNewPassword.GENERATE_NEW_PASSWORD_DETAILS.user,
			GenerateNewPassword.GENERATE_NEW_PASSWORD_DETAILS.instanceName));
		new TaxLinkUsersPage(driver).editInstanceRole("FI_ADMIN", GenerateNewPassword.GENERATE_NEW_PASSWORD_DETAILS.instanceName);
	}
}