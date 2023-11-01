package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkLegalEntityPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * This class contains all the test methods for Legal Entity tab
 *
 * @author Shilpi.Verma
 */

public class TaxLinkLegalEntityTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		lePage = new TaxLinkLegalEntityPage(driver);
		homePage = new TaxLinkHomePage(driver);
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * JIRA Issue: COERPC-7356
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void verifyAddLegalEntityTest( )
	{
		assertTrue(lePage.addLegalEntity());
	}

	/**
	 * JIRA Issue: COERPC-7357
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyEditLegalEntityTest( )
	{
		assertTrue(lePage.editLegalEntity());
	}

	/**
	 * JIRA Issue: COERPC-7358
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyViewLegalEntityTest( )
	{
		assertTrue(lePage.viewLegalEntity());
	}

	/**
	 * JIRA Issue: COERPC-7359
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyImportLegalEntityTest( )
	{
		assertTrue(lePage.importLegalEntity());
	}

	/**
	 * JIRA Issue: COERPC-8347
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyNegative_addDuplicateLETest( )
	{
		assertTrue(lePage.negative_addDuplicateLE());
	}

	/**
	 * JIRA Issue: COERPC-7360
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyExportToCSVLegalEntityTest( ) throws Exception
	{
		assertTrue(lePage.exportToCSVLegalEntity());
	}
}
