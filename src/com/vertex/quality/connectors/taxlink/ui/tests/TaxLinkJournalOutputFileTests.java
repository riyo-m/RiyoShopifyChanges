package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.InventoryRulesMapping;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkInvConditionSetsPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkJournalOutputFilePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the smoke test cases for Journal Output File
 * in Inventory Rules Mapping Module Tab containing in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkJournalOutputFileTests extends TaxLinkBaseTest
{
	public String ruleName;
	public String invConditionName;

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		invCondSetRulesMapping = new TaxLinkInvConditionSetsPage(driver);
		journalOutputFilePage = new TaxLinkJournalOutputFilePage(driver);
		utils = new TaxLinkUIUtilities();
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Add Journal rule - Inventory Rules Mapping in Tax link application
	 * Map Function
	 * COERPC-11088
	 */

	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAndVerifyMapFunctionJournalRuleTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invCondSetRulesMapping.clickOnInvJournalOutputFile();
		assertTrue(
			journalOutputFilePage.addAndVerifyInvJournalRule("MAP", invConditionName, "Attribute10", "GL Attribute01",
				null, null));
	}

	/**
	 * Add Journal rule - Inventory Rules Mapping in Tax link application
	 * Substring Function
	 * COERPC-11089
	 */

	@Test(groups = "taxlink_ui_regression")
	public void addAndVerifySubstringFunctionJournalRuleTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invCondSetRulesMapping.clickOnInvJournalOutputFile();
		assertTrue(
			journalOutputFilePage.addAndVerifyInvJournalRule("SUBSTRING", invConditionName, "Inventory Product Code",
				"GL Attribute category", "2", "4"));
	}

	/**
	 * View Journal rule - Inventory Rules Mapping in Tax link application
	 * COERPC-11094
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void viewJournalOutputRulesMappingTest( )
	{
		journalOutputFilePage.navigateToInvJournalOutputFile();
		ruleName = journalOutputFilePage.addAndVerifyInvJournalRuleToViewOrEdit("MAP",
			InventoryRulesMapping.RuleMappingDetails.invConditionSet, "Attribute10", "GL Attribute01", null, null);
		assertTrue(journalOutputFilePage.viewJournalOutputRulesMapping(ruleName));
	}

	/**
	 * Edit Journal Output rule - Inventory Rules Mapping in Tax link application
	 * COERPC-11093
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void editJournalOutputRulesMappingTest( )
	{
		journalOutputFilePage.navigateToInvJournalOutputFile();
		ruleName = journalOutputFilePage.addAndVerifyInvJournalRuleToViewOrEdit("SUBSTRING",
			InventoryRulesMapping.RuleMappingDetails.invConditionSet, "Inventory Product Code", "GL Attribute category",
			"2", "4");
		assertTrue(journalOutputFilePage.editJournalOutputRulesMapping(ruleName));
	}

	/**
	 * Export to CSV Journal Output rule - Inventory Rules Mapping in Tax link application
	 * COERPC-11092
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void exportToCSVJournalOutputRulesMappingTest( ) throws IOException
	{
		assertTrue(journalOutputFilePage.exportToCSVJournalOutputRulesMapping(
			OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}
}
