package com.vertex.quality.connectors.taxlink.ui.tests;

/**
 * This class contains all the test methods for AP Assume Tax Sources tab
 *
 * @author Shilpi.Verma
 */

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkAPAssumeTaxSrcPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class TaxLinkAPAssumeTaxSrcTests extends TaxLinkBaseTest
{

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		apAssumeTaxSrcPage = new TaxLinkAPAssumeTaxSrcPage(driver);
		homePage = new TaxLinkHomePage(driver);
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * JIRA Issue: COERPC-7831
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAPAssumeTaxSrcTest( )
	{
		assertTrue(apAssumeTaxSrcPage.addAPAssumeTaxSrc());
	}

	/**
	 * JIRA Issue: COERPC-7833
	 */
	@Test(groups = "taxlink_ui_regression")
	public void editAPAssumeTaxSrcTest( )
	{
		assertTrue(apAssumeTaxSrcPage.editAPAssumeTaxSrc());
	}

	/**
	 * JIRA Issue: COERPC-7832
	 */
	@Test(groups = "taxlink_ui_regression")
	public void viewAPAssumeTaxSrcTest( )
	{
		assertTrue(apAssumeTaxSrcPage.viewAPAssumeTaxSrc());
	}

	/**
	 * JIRA Issue: COERPC-8351
	 */
	@Test(groups = "taxlink_ui_regression")
	public void verifyNegative_end_dateDuplicateAPAssumeTaxSrc( )
	{
		assertTrue(apAssumeTaxSrcPage.negative_end_dateAssumeTaxSrc());
	}

	/**
	 * JIRA Issue: COERPC-7834
	 *
	 * @throws IOException
	 */
	@Test(groups = "taxlink_ui_regression")
	public void exportToCSVAPAssumeTaxSrcTest( ) throws Exception
	{
		assertTrue(apAssumeTaxSrcPage.exportToCSVAPAssumeTaxSrc());
	}
}
