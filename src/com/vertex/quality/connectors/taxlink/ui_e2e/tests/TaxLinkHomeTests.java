package com.vertex.quality.connectors.taxlink.ui_e2e.tests;

import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkSignOnPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * This class contains all the tests for initial settings after logging into TaxLink UI
 *
 * @author mgaikwad
 */

public class TaxLinkHomeTests extends TaxLinkBaseTest
{

	@BeforeMethod(alwaysRun = true)
	public void loginTestCaseERP( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		utils = new TaxLinkUIUtilities();

		initialization_OracleERP();
	}

	/**
	 * Verify if user is on the Home Page of Oracle ERP
	 */
	@Test
	public void verifyHomePageERPTitleTest( )
	{
		String homePageTitle = homePage.getPageTitle();
		assertEquals(homePageTitle, "Welcome");
	}

	@AfterMethod(alwaysRun = true)
	public void logoutTestCaseERP( )
	{
		tearDown();
	}
}
