package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSupplierTypePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * This test class contains all the tests for Supplier Type tab
 *
 * @author Shilpi.Verma
 */

public class TaxLinkSupplierTypeTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		suppTypePage = new TaxLinkSupplierTypePage(driver);
		homePage = new TaxLinkHomePage(driver);
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * JIRA Issue: COERPC-10795
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void verifyAddSupplierTypeTest( )
	{
		assertTrue(suppTypePage.addSupplierType());
	}

	/**
	 * JIRA Issue: COERPC-10796
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void verifyEditSupplierTypeTest( )
	{
		assertTrue(suppTypePage.editSupplierType());
	}

	/**
	 * JIRA Issue: COERPC-10797
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void verifyViewSupplierTypeTest( )
	{
		assertTrue(suppTypePage.viewSupplierType());
	}

	/**
	 * JIRA Issue: COERPC-10798
	 *
	 * @throws Exception
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void verifyExportToCSVSupplierTypeTest( ) throws Exception
	{
		assertTrue(suppTypePage.exportToCSV());
	}

	/**
	 * JIRA Issue: COERPC-10800
	 *
	 * @throws Exception
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void verifySupplierDropdownTest( )
	{
		assertTrue(suppTypePage.supplierDropdown());
	}
}
