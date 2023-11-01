package com.vertex.quality.connectors.taxlink.ui_e2e.tests;

import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkAPInvSrcPage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * This class contains TaxLink part of test method to verify AP Invoice Source import in Taxlink
 *
 * @author Shilpi.Verma
 */
public class TaxLinkAPInvSrcTests extends TaxLinkBaseTest
{

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		apInvSrcPage = new TaxLinkAPInvSrcPage(driver);
		initialization_Taxlink();
		homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
	}

	/**
	 * JIRA Issue: COERPC-8957
	 */
	@Test(groups = { "taxlink_ui_e2e_regression" })
	public void importAPInvSrc_e2e_Test( )
	{
		apInvSrcPage.importAPInvSrc();
	}
}
