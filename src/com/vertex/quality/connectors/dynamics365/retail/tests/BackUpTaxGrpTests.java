package com.vertex.quality.connectors.dynamics365.retail.tests;

import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuNames;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceVertexTaxParametersLeftMenuNames;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceHomePage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceSettingsPage;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailPOSRegisterPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class BackUpTaxGrpTests extends DFinanceBaseTest
{
	protected final String vertexPackageName = "Vertex";

	/**
	 * JIRA ticket CD365R-94
	 *
	 * BackUp Tax GRP validation for Dynamics365 Retail
	 */
	@Test(groups = { "Retail_regression" })
	public void backupTaxGrpTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingPage;
		DRetailPOSRegisterPage posRegisterPage = new DRetailPOSRegisterPage(driver);

		//================Data Declaration ===========================
		final String retail = DFinanceLeftMenuNames.RETAIL.getData();
		final String channel = DFinanceLeftMenuNames.CHANNEL_SETUP.getData();
		final String possetup = DFinanceLeftMenuNames.POS_SETUP.getData();
		final String register = DFinanceLeftMenuNames.REGISTER.getData();

		//================script implementation========================
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(retail);
		homePage.collapseAll();
		homePage.expandModuleCategory(channel);
		homePage.expandModuleCategory(possetup);
		settingPage = homePage.selectModuleTabLink(register);
		settingPage.selectCompany("USRT");
		
		posRegisterPage.selectRegister();
		posRegisterPage.clickVetexLite();
		settingPage.clickOnEditButton();
		posRegisterPage.setBackupTaxGRP("TX");
	}

	/**
	 * Retail Retry Interval validation for Dynamics365 Retail
	 */
	@Test(groups = { "Retail_regression"})
	public void retryIntervalTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingPage;

		//================Data Declaration ===========================
		final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
		final String tax = DFinanceLeftMenuNames.TAX.getData();
		final String setup = DFinanceLeftMenuNames.SETUP.getData();
		final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		final String vertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
		final String retail = DFinanceLeftMenuNames.RETAIL.getData();
		final String discount = DFinanceLeftMenuNames.DISCOUNT.getData();
		final String all_discount = DFinanceLeftMenuNames.ALL_DISCOUNT.getData();

		//================script implementation========================
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);

		//Navigate to vertex ta parameter page and enabled rebate tax

		settingPage = homePage.selectModuleTabLink(vertexTaxParameters);
		settingPage.selectSettingsPage(taxGroupSettings);
		settingPage.selectCompany("USRT");
		settingPage.toggleTxBkUpGrp(true);
		settingPage.enterInterval("0");

		//Verify that correct error message displayed
		String msg = settingPage.validateErrorMsg();
		assertTrue(msg.equalsIgnoreCase("Field Commerce Retry Interval must be filled in."),
			"'Error message is not displayed");
		settingPage.enterInterval("4");
		settingPage.clickOnSaveButton();
	}
}
