package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkDefaultMappingPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the smoke test cases for Default Mapping Tab contained
 * Rules Mapping Module in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkDefaultMappingTests extends TaxLinkBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		defaultMapping = new TaxLinkDefaultMappingPage(driver);
		utils = new TaxLinkUIUtilities();
		initialization();
		homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
	}

	/**
	 * View Default Mapping in Tax link application
	 * COERPC-7276
	 */

	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void viewDefaultMappingTest( )
	{
		assertTrue(defaultMapping.viewDefaultMapping());
	}
}
