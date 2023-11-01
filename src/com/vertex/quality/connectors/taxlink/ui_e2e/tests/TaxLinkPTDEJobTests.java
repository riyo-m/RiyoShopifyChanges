package com.vertex.quality.connectors.taxlink.ui_e2e.tests;

import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkPTDEJobPage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * This class contains methods for verifying PTDE job status in TaxLink
 *
 * @author Shilpi.Verma
 */

public class TaxLinkPTDEJobTests extends TaxLinkBaseTest
{

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		ptdeJobPage = new TaxLinkPTDEJobPage(driver);
		initialization_Taxlink();
		homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
	}

	/**
	 * JIRA Issue: COERPC-9659
	 */
	@Test(groups = { "taxlink_ui_e2e_regression" })
	public void ptdeJobStatusTest( )
	{
		ptdeJobPage.ptdeJobStatus();
	}
}
