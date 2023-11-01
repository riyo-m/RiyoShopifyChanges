package com.vertex.quality.connectors.dynamics365.business.tests;

import com.vertex.quality.connectors.dynamics365.business.enums.BusinessMainMenuNavTabs;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessAdminHomePage;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessExtensionsPage;
import com.vertex.quality.connectors.dynamics365.business.tests.base.BusinessBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * this class represents all the test cases for the connector extension
 *
 * @author osabha, cgajes
 */
@Listeners(TestRerunListener.class)
public class BusinessConnectorConfigsTests extends BusinessBaseTest
{
	BusinessAdminHomePage homePage;
	/**
	 * tests for display of version field in the extension
	 * CDBC-687
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression"}, retryAnalyzer = TestRerun.class)
	public void versionDisplayOnUiTest( )
	{
		String expectedVersion = "v. 2.11.0.0";
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

		homePage.searchForAndNavigateToPage("Extension Management");

		BusinessExtensionsPage extensionsPage = new BusinessExtensionsPage(driver);
		extensionsPage.vertexExtensionDialog.selectListLayout();
		extensionsPage.searchForExtension("Tax Links");
		String versionField = extensionsPage.vertexExtensionDialog.getVersion();

		assertEquals(versionField, expectedVersion);
	}
	@BeforeMethod(alwaysRun = true)
	public void setUpBusinessMgmt(){
		String role="Business Manager";
		homePage = new BusinessAdminHomePage(driver);
		String verifyPage=homePage.verifyHomepageHeader();
		if(!verifyPage.contains(role)){

			//navigate to select role as Business Manager
			homePage.selectSettings();
			homePage.navigateToManagerInSettings(role);
		}
	}
}
