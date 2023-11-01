package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * This class contains all the tests for initial settings after logging into TaxLink UI
 *
 * @author mgaikwad
 */

public class TaxLinkHomeTests extends TaxLinkBaseTest
{

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		utils = new TaxLinkUIUtilities();
		initialization();
	}

	/**
	 * JIRA Issue: COERPC-6839
	 */
	@Test(groups = { "taxlink_ui_smoke" })
	public void verifyHomePageTitleTest( )
	{
		String homePageTitle = homePage.getPageTitle();
		assertEquals(homePageTitle, "Vertex Accelerator for Oracle ERP Cloud");
	}

	/**
	 * JIRA Issue: COERPC-6840
	 */
	@Test(groups = { "taxlink_ui_smoke" })
	public void verifyUserRoleAssignedTest( )
	{
		assertTrue(homePage.verifyUserRoleAssigned());
	}


}
