package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.enums.RulesMapping;
import com.vertex.quality.connectors.taxlink.ui.pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * this class represents all the smoke test cases for Condition Sets Tab contained
 * Rules Mapping Module in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkConditionSetsTests extends TaxLinkBaseTest
{
	public String ruleName, preCalcRuleName, postCalcRuleName;
	public String conditionName;

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		preRulesMapping = new TaxLinkPreCalcRulesMappingPage(driver);
		postRulesMapping = new TaxLinkPostCalcRulesMappingPage(driver);
		condSetRulesMapping = new TaxLinkConditionSetsPage(driver);
		utils = new TaxLinkUIUtilities();
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * View Condition Sets with System level access type
	 * in Rules Mapping in Tax link application
	 * COERPC-7275
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void viewSYSTEMConditionSetsTest( )
	{
		assertTrue(condSetRulesMapping.viewSYSTEMConditionSetsRulesMapping());
	}

	/**
	 * Add Condition Set (i.e. USER level access type)
	 * in Condition Sets tab in Tax link application
	 * COERPC-8758
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void addAndVerifyConditionSetsTest( )
	{
		assertTrue(condSetRulesMapping.addAndVerifyConditionSetWithEndDate());
		assertTrue(condSetRulesMapping.addAndVerifyConditionSetWithoutEndDate());
	}

	/**
	 * View Condition Sets with User level access type
	 * in Rules Mapping in Tax link application
	 * COERPC-8759
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void viewUSERConditionSetsTest( )
	{
		assertTrue(condSetRulesMapping.viewUSERConditionSetsRulesMapping());
	}

	/**
	 * Edit Condition Sets with User level access type
	 * in Rules Mapping in Tax link application
	 * COERPC-8760
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void editUSERConditionSetsTest( )
	{
		assertTrue(condSetRulesMapping.editUSERConditionSetsRulesMapping());
	}

	/**
	 * Export to CSV Rules Mapping in Tax link application
	 * COERPC-7278
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void exportToCSVConditionSetsTest( ) throws IOException
	{
		assertTrue(condSetRulesMapping.exportToCSVConditionSets(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Verify PRECALC Rules tied to the Default Condition Set in Tax link application
	 * COERPC-10639
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void verifyPreCalcRulesForDefaultConditionSetTest( )
	{
		preRulesMapping.navigateToPreRulesMappingPage();
		ruleName = preRulesMapping.addConstantFunctionPreRuleToViewAndEdit(
			RulesMapping.RuleMappingDetails.defaultConditionSet);
		assertTrue(condSetRulesMapping.verifyRulesForDefaultConditionSetTest(ruleName, "PRECALC"));
	}

	/**
	 * Verify PRECALC Rules tied to the Default Condition Set in Tax link application
	 * COERPC-10640
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void verifyPostCalcRulesForDefaultConditionSetTest( )
	{
		preRulesMapping.navigateToPreRulesMappingPage();
		ruleName = postRulesMapping.addMapFunctionPostRule(RulesMapping.RuleMappingDetails.defaultConditionSet);
		assertTrue(condSetRulesMapping.verifyRulesForDefaultConditionSetTest(ruleName, "POSTCALC"));
	}

	/**
	 * Verify PRECALC Rules tied to the User defined Condition Set in Tax link application
	 * COERPC-10641
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void verifyPreCalcRulesForUserDefinedConditionSetTest( )
	{
		conditionName = condSetRulesMapping.addAndVerifyConditionSet();
		ruleName = preRulesMapping.addConstantFunctionPreRuleToViewAndEdit(conditionName);
		assertTrue(condSetRulesMapping.verifyRulesForUserDefinedConditionSetTest(conditionName, ruleName, "PRECALC"));
	}

	/**
	 * Verify POSTCALC Rules tied to the User defined Condition Set in Tax link application
	 * COERPC-10642
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void verifyPostCalcRulesForUserDefinedConditionSetTest( )
	{
		conditionName = condSetRulesMapping.addAndVerifyConditionSet();
		ruleName = postRulesMapping.addMapFunctionPostRule(conditionName);
		assertTrue(condSetRulesMapping.verifyRulesForUserDefinedConditionSetTest(conditionName, ruleName, "POSTCALC"));
	}

	/**
	 * Verify PRECALC Rules shows the disabled condition set value in View pre-calc rule page
	 *  COERPC-11043
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void verifyViewPreCalcRulesForUserDefinedConditionSetTest( )
	{
		conditionName = condSetRulesMapping.addAndVerifyConditionSet();
		ruleName = preRulesMapping.addConstantFunctionPreRuleToViewAndEdit(conditionName);
		condSetRulesMapping.disableConditionset(conditionName);
		condSetRulesMapping.clickOnPreCalcRules();
		assertTrue(preRulesMapping.viewPreRulesMapping(ruleName));
	}

	/**
	 * Verify POSTCALC Rules shows the disabled condition set value
	 * in View post-calc rule page in Tax link application
	 *
	 * COERPC-11044
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void verifyViewPostCalcRulesForUserDefinedConditionSetTest( )
	{
		conditionName = condSetRulesMapping.addAndVerifyConditionSet();
		ruleName = postRulesMapping.addMapFunctionPostRule(conditionName);
		condSetRulesMapping.disableConditionset(conditionName);
		condSetRulesMapping.clickOnPostCalcRules();
		assertTrue(postRulesMapping.viewPostRulesMapping(ruleName));
	}

	/**
	 * Verify COPY condition Set functionality in Tax link application
	 * COERPC-10908
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void verifyCopyConditionSetTest( )
	{
		conditionName = condSetRulesMapping.addAndVerifyConditionSet();
		ruleName = postRulesMapping.addMapFunctionPostRule(conditionName);
		int rulesCount = condSetRulesMapping.getRulesCountForUserDefinedConditionSetTest(conditionName);
		assertTrue(condSetRulesMapping.copyUSERConditionSetsRulesMapping());
		int rulesCountInCopied = condSetRulesMapping.getRulesCountForUserDefinedConditionSetTest(
			conditionName + "-COPY");
		assertEquals(rulesCountInCopied, rulesCount);
	}

	/**
	 * Verify copy functionality to copied condition set with post calc rules in Tax link application
	 * COERPC-10927
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void verifyPostCalcRuleInCopiedConditionSetTest( )
	{
		conditionName = condSetRulesMapping.addAndVerifyConditionSet();
		condSetRulesMapping.getRulesCountForUserDefinedConditionSetTest(conditionName);
		assertTrue(condSetRulesMapping.copyUSERConditionSetsRulesMapping());
		ruleName = postRulesMapping.addMapFunctionPostRule(conditionName + "-COPY");
		assertTrue(condSetRulesMapping.verifyRulesForUserCopiedConditionSetTest(conditionName + "-COPY", ruleName));
	}

	/**
	 * Verify copy functionality to copied condition set with pre calc rules in Tax link application
	 * COERPC-10926
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyPreCalcRuleInCopiedConditionSetTest( )
	{
		conditionName = condSetRulesMapping.addAndVerifyConditionSet();
		condSetRulesMapping.getRulesCountForUserDefinedConditionSetTest(conditionName);
		assertTrue(condSetRulesMapping.copyUSERConditionSetsRulesMapping());
		ruleName = preRulesMapping.addConstantFunctionPreRuleToViewAndEdit(conditionName + "-COPY");
		assertTrue(condSetRulesMapping.verifyRulesForUserCopiedConditionSetTest(conditionName + "-COPY", ruleName));
	}

	/**
	 * Verify functionality when a condition set is disabled, all associated rules tied to that condition set gets disabled
	 * COERPC-10935
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void verifyDisabledRulesForDisabledConditionSetTest( )
	{
		conditionName = condSetRulesMapping.addAndVerifyConditionSet();
		preCalcRuleName = preRulesMapping.addConstantFunctionPreRuleToViewAndEdit(conditionName);
		postCalcRuleName = postRulesMapping.addMapFunctionPostRule(conditionName);
		condSetRulesMapping.disableConditionset(conditionName);
		assertTrue(condSetRulesMapping.verifyPreCalcDisabledRulesForDisabledConditionSet(preCalcRuleName));
		assertTrue(condSetRulesMapping.verifyPostCalcDisabledRulesForDisabledConditionSet(postCalcRuleName));
	}
}
