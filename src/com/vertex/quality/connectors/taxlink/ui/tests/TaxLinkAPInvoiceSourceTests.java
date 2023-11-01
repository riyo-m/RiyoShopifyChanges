package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkAPInvoiceSourcePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * This class contains all the test methods for AP Invoice Source tab
 *
 * @author Shilpi.Verma
 */

public class TaxLinkAPInvoiceSourceTests extends TaxLinkBaseTest
{

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		apInvPage = new TaxLinkAPInvoiceSourcePage(driver);
		homePage = new TaxLinkHomePage(driver);
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * JIRA Issue: COERPC-7417
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void verifyAddAPInvoiceSourceTest( )
	{
		assertTrue(apInvPage.addAPInvoiceSource());
	}

	/**
	 * JIRA Issue: COERPC-7418
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyEditAPInvoiceSource_userTest( )
	{
		assertTrue(apInvPage.editAPInvoiceSource_userCreated());
	}

	/**
	 * JIRA Issue: COERPC-7419
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyViewAPInvoiceSourceTest( )
	{
		assertTrue(apInvPage.viewAPInvoiceSource());
	}

	/**
	 * JIRA Issue: COERPC-7420
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyImportAPInvoiceSourceTest( ) throws Exception
	{
		assertTrue(apInvPage.importAPInvoiceSource());
	}

	/**
	 * JIRA Issue: COERPC-10965
	 *
	 * @throws Exception
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyEditAPInvoiceSource_importTest( ) throws Exception
	{
		assertTrue(apInvPage.editAPInvoiceSource_importedData());
	}

	/**
	 * JIRA Issue: COERPC-8348
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyNegative_addApInvSrcTest( )
	{
		assertTrue(apInvPage.negative_addDuplicateAPInvSrc());
	}

	/**
	 * JIRA Issue: COERPC-7421
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyExportToCSVAPInvoiceSrcTest( ) throws Exception
	{
		assertTrue(apInvPage.exportToCSVAPInvoiceSrc());
	}
}
