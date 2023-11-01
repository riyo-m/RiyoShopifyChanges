package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkInvConditionSetsPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkInvPreCalcRulesMappingPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * this class represents all the smoke test cases for Inventory Condition Sets Tab contained
 * Inventory Rules Mapping Module in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkInvConditionSetsTests extends TaxLinkBaseTest
{
	public String invPreCalcRuleName;
	public String invConditionName;

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		invCondSetRulesMapping = new TaxLinkInvConditionSetsPage(driver);
		invPreCalcRules = new TaxLinkInvPreCalcRulesMappingPage(driver);
		utils = new TaxLinkUIUtilities();
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Add INV Condition Set (i.e. USER level access type)
	 * in Inventory Condition Sets tab in Tax link application
	 * COERPC-11030
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void addAndVerifyInvConditionSetsTest( )
	{
		assertTrue(invCondSetRulesMapping.addAndVerifyINVConditionSetWithEndDate());
		assertTrue(invCondSetRulesMapping.addAndVerifyINVConditionSetWithoutEndDate());
	}

	/**
	 * View INV Condition Sets with User level access type
	 * in Inventory Rules Mapping in Tax link application
	 * COERPC-11031
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void viewUSERInvConditionSetsTest( )
	{
		assertTrue(invCondSetRulesMapping.viewUSERInvConditionSetsRulesMapping());
	}

	/**
	 * Edit INV Condition Sets with User level access type
	 * in Inventory Rules Mapping in Tax link application
	 * COERPC-11032
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void editUSERInvConditionSetsTest( )
	{
		assertTrue(invCondSetRulesMapping.editUSERInvConditionSetsRulesMapping());
	}

	/**
	 * Export to CSV INV Condition sets in Tax link application
	 * COERPC-11033
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void exportToCSVInvConditionSetsTest( ) throws IOException
	{
		assertTrue(invCondSetRulesMapping.exportToCSVInvConditionSets(
			OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Verify Inventory PRECALC Rules tied to the User defined Condition Set in Tax link application
	 * COERPC-11040
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyInvPreCalcRulesForUserDefinedConditionSetTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invPreCalcRuleName = invPreCalcRules.addConstantFunctionInvPreRule("CONSTANT", invConditionName,
			"Inventory Item Category", "AM0006475");
		assertTrue(invCondSetRulesMapping.verifyInvPreCalcRulesForConditionSetTest(invConditionName, invPreCalcRuleName,
			"PRECALC"));
	}

	/**
	 * Verify Inventory PRECALC Rules shows the disabled inv condition set value in View inv pre-calc rule page
	 * COERPC-11041
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyViewInvPreCalcRulesForUserDefinedConditionSetTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invPreCalcRuleName = invPreCalcRules.addConstantFunctionInvPreRule("CONSTANT", invConditionName,
			"Inventory Item Category", "AM0006475");
		invCondSetRulesMapping.disableConditionset(invConditionName);
		invCondSetRulesMapping.clickOnInvPreCalcRulesMapping();
		assertTrue(invPreCalcRules.viewInvPreCalcRulesMapping(invPreCalcRuleName));
	}

	/**
	 * Verify COPY Inventory condition Set functionality in Tax link application
	 * COERPC-11044
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyCopyInvConditionSetTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invPreCalcRuleName = invPreCalcRules.addConstantFunctionInvPreRule("CONSTANT", invConditionName,
			"Inventory Item Category", "AM0006475");
		int rulesCount = invCondSetRulesMapping.getRulesCountForUserDefinedInvConditionSetTest(invConditionName);
		assertTrue(invCondSetRulesMapping.copyUSERInvConditionSetsRulesMapping());
		int rulesCountInCopied = invCondSetRulesMapping.getRulesCountForUserDefinedInvConditionSetTest(
			invConditionName + "-COPY");
		assertEquals(rulesCountInCopied, rulesCount);
	}

	/**
	 * Verify copy functionality to copied inventory condition set with pre calc rules in Tax link application
	 * COERPC-11045
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyInvPreCalcRuleInCopiedInvConditionSetTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invCondSetRulesMapping.getRulesCountForUserDefinedInvConditionSetTest(invConditionName);
		assertTrue(invCondSetRulesMapping.copyUSERInvConditionSetsRulesMapping());
		invPreCalcRuleName = invPreCalcRules.addConstantFunctionInvPreRule("CONSTANT", invConditionName + "-COPY",
			"Inventory Item Category", "AM0006475");
		assertTrue(invCondSetRulesMapping.verifyInvRulesForUserCopiedInvConditionSetTest(invConditionName + "-COPY",
			invPreCalcRuleName));
	}

	/**
	 * Verify functionality when an inv condition set is disabled, all associated Inv rules tied to that Inv condition set gets disabled
	 * COERPC-11046
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyDisabledInvRulesForDisabledInvConditionSetTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invPreCalcRuleName = invPreCalcRules.addConstantFunctionInvPreRule("CONSTANT", invConditionName,
			"Inventory Item Category", "AM0006475");
		invCondSetRulesMapping.disableConditionset(invConditionName);
		assertTrue(invCondSetRulesMapping.verifyPreCalcDisabledInvRulesForDisabledInvConditionSet(invPreCalcRuleName));
	}

	/**
	 * View Inv Condition Set "VTX_INV_ONLY" with System level access type
	 * in INV Rules Mapping in Tax link application
	 * COERPC-11080
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void viewInvSystemConditionSetTest( )
	{
		assertTrue(invCondSetRulesMapping.viewInvSystemConditionSet());
	}

	/**
	 * Verify Inv PRECALC Rule tied to the "VTX_INV_ONLY" Condition Set in Tax link application
	 * COERPC-11079
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyPreCalcRulesForInvConditionSetTest( )
	{
		invCondSetRulesMapping.clickOnTaxCalculationSetUpsDropdown();
		invPreCalcRuleName = invPreCalcRules.addConstantFunctionInvPreRule("CONSTANT", "VTX_INV_ONLY",
			"Inventory Item Category", "AM0006475");
		assertTrue(invCondSetRulesMapping.verifyInvPreCalcRulesForConditionSetTest("VTX_INV_ONLY", invPreCalcRuleName,
			"PRECALC"));
	}
}
