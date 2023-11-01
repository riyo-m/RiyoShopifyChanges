package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.InventoryRulesMapping;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkInvConditionSetsPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkProjectOutputFilePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the smoke test cases for Project Output File
 * in Inventory Rules Mapping Module Tab containing in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkProjectOutputFileTests extends TaxLinkBaseTest
{
	public String ruleName;
	public String invConditionName;

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		invCondSetRulesMapping = new TaxLinkInvConditionSetsPage(driver);
		projectOutputFilePage = new TaxLinkProjectOutputFilePage(driver);
		utils = new TaxLinkUIUtilities();
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Add Project rule - Inventory Rules Mapping in Tax link application
	 * Map Function
	 * COERPC-11091
	 */

	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAndVerifyMapFunctionProjectRuleTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invCondSetRulesMapping.clickOnInvProjectOutputFile();
		assertTrue(projectOutputFilePage.addAndVerifyInvProjectRule("MAP", invConditionName, "Attribute10",
			"Inventory Project Attribute02", null, null));
	}

	/**
	 * Add Project rule - Inventory Rules Mapping in Tax link application
	 * Substring Function
	 * COERPC-11090
	 */

	@Test(groups = "taxlink_ui_regression")
	public void addAndVerifySubstringFunctionProjectRuleTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invCondSetRulesMapping.clickOnInvProjectOutputFile();
		assertTrue(projectOutputFilePage.addAndVerifyInvProjectRule("SUBSTRING", invConditionName, "Attribute11",
			"Inventory Project Attribute02", "2", "4"));
	}

	/**
	 * View Project Output rule - Inventory Rules Mapping in Tax link application
	 * COERPC-11099
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void viewProjectOutputRulesMappingTest( )
	{
		projectOutputFilePage.navigateToInvProjectOutputFile();
		ruleName = projectOutputFilePage.addAndVerifyInvProjectRuleToViewOrEdit("MAP",
			InventoryRulesMapping.RuleMappingDetails.invConditionSet, "Attribute09", "Inventory Project Attribute02",
			null, null);
		assertTrue(projectOutputFilePage.viewProjectOutputRulesMapping(ruleName));
	}

	/**
	 * Edit Project Output rule - Inventory Rules Mapping in Tax link application
	 * COERPC-11100
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void editProjectOutputRulesMappingTest( )
	{
		projectOutputFilePage.navigateToInvProjectOutputFile();
		ruleName = projectOutputFilePage.addAndVerifyInvProjectRuleToViewOrEdit("SUBSTRING",
			InventoryRulesMapping.RuleMappingDetails.invConditionSet, "Attribute13", "Inventory Project Attribute03",
			"2", "4");
		assertTrue(projectOutputFilePage.editProjectOutputRulesMapping(ruleName));
	}

	/**
	 * Export to CSV Project Output rule - Inventory Rules Mapping in Tax link application
	 * COERPC-11101
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void exportToCSVProjectOutputRulesMappingTest( ) throws IOException
	{
		assertTrue(projectOutputFilePage.exportToCSVProjectOutputRulesMapping(
			OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}
}
