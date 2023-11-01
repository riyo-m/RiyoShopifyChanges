package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.enums.RulesMapping;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkPreCalcRulesMappingPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the smoke test cases for Pre Rules Mapping Module Tab containing
 * Rules Mapping tab in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkPreCalcRulesMappingTests extends TaxLinkBaseTest
{
	String ruleName;

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		preRulesMapping = new TaxLinkPreCalcRulesMappingPage(driver);
		utils = new TaxLinkUIUtilities();
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Add Pre-Rules Mapping in Tax link application
	 * Map Function
	 * COERPC-7508
	 */

	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAndVerifyMapFunctionPreRuleTest( )
	{
		assertTrue(preRulesMapping.addAndVerifyMapFunctionPreRule(RulesMapping.RuleMappingDetails.defaultConditionSet));
	}

	/**
	 * Add Pre-Rules Mapping in Tax link application
	 * Upper Function
	 * COERPC-7509
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void addAndVerifyUpperFunctionPreRuleTest( )
	{
		assertTrue(
			preRulesMapping.addAndVerifyUpperFunctionPreRule(RulesMapping.RuleMappingDetails.arOnlyConditionSet));
	}

	/**
	 * Add Pre-Rules Mapping in Tax link application
	 * Lower Function
	 * COERPC-7510
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void addAndVerifyLowerFunctionPreRuleTest( )
	{
		assertTrue(
			preRulesMapping.addAndVerifyLowerFunctionPreRule(RulesMapping.RuleMappingDetails.apOnlyConditionSet));
	}

	/**
	 * Add Pre-Rules Mapping in Tax link application
	 * Concat Function
	 * COERPC-7511
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void addAndVerifyConcatFunctionPreRuleTest( )
	{
		assertTrue(
			preRulesMapping.addAndVerifyConcatFunctionPreRule(RulesMapping.RuleMappingDetails.p2pOnlyConditionSet));
	}

	/**
	 * Add Pre-Rules Mapping in Tax link application
	 * Sub String Function
	 * COERPC-7512
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void addAndVerifySubStringFunctionPreRuleTest( )
	{
		assertTrue(
			preRulesMapping.addAndVerifySubStringFunctionPreRule(RulesMapping.RuleMappingDetails.arOnlyConditionSet));
	}

	/**
	 * Add Pre-Rules Mapping in Tax link application
	 * NVL Function - Place another Source value
	 * COERPC-7513
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void addAndVerifyNvl_placeSourceValFunctionPreRuleTest( )
	{
		assertTrue(preRulesMapping.addAndVerifyNvlFunction_placeSourceValPreRule(
			RulesMapping.RuleMappingDetails.apOnlyConditionSet));
	}

	/**
	 * Add Pre-Rules Mapping in Tax link application
	 * NVL Function - Place a CONSTANT value
	 * COERPC-7548
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void addAndVerifyNvl_placeConstantValFunctionPreRuleTest( ) throws InterruptedException
	{
		assertTrue(preRulesMapping.addAndVerifyNvlFunction_placeConstantValPreRule(
			RulesMapping.RuleMappingDetails.apOnlyConditionSet));
	}

	/**
	 * Add Pre-Rules Mapping in Tax link application
	 * Constant Function
	 * COERPC-7514
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void addAndVerifyConstantFunctionPreRuleTest( )
	{
		assertTrue(
			preRulesMapping.addAndVerifyConstantFunctionPreRule(RulesMapping.RuleMappingDetails.o2cOnlyConditionSet));
	}

	/**
	 * Add Pre-Rules Mapping in Tax link application
	 * Map Address Function
	 * COERPC-7515
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void addAndVerifyMapAddressFunctionPreRuleTest( )
	{
		assertTrue(
			preRulesMapping.addAndVerifyMapAddressFunctionPreRule(RulesMapping.RuleMappingDetails.poOnlyConditionSet));
	}

	/**
	 * Add Pre-Rules Mapping in Tax link application
	 * Split Function
	 * COERPC-7516
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void addAndVerifySplitFunctionPreRuleTest( )
	{
		assertTrue(
			preRulesMapping.addAndVerifySplitFunctionPreRule(RulesMapping.RuleMappingDetails.apOnlyConditionSet));
	}

	/**
	 * Add Pre-Rules Mapping in Tax link application
	 * To Number Function
	 * COERPC-7517
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void addAndVerifyToNumberFunctionPreRuleTest( )
	{
		assertTrue(
			preRulesMapping.addAndVerifyToNumberFunctionPreRule(RulesMapping.RuleMappingDetails.omOnlyConditionSet));
	}

	/**
	 * View Pre-Rules Mapping in Tax link application
	 * COERPC-7273
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void viewPreCalcRulesMappingTest( )
	{
		preRulesMapping.navigateToPreRulesMappingPage();
		ruleName = preRulesMapping.addConstantFunctionPreRuleToViewAndEdit(
			RulesMapping.RuleMappingDetails.defaultConditionSet);
		assertTrue(preRulesMapping.viewPreRulesMapping(ruleName));
	}

	/*
	 * Edit Pre-Rules Mapping in Tax link application
	 * COERPC-7274
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void editPreCalcRulesMappingTest( )
	{
		preRulesMapping.navigateToPreRulesMappingPage();
		ruleName = preRulesMapping.addConstantFunctionPreRuleToViewAndEdit(
			RulesMapping.RuleMappingDetails.defaultConditionSet);
		assertTrue(preRulesMapping.editPreRulesMapping(ruleName));
	}

	/*
	 * Export to CSV Pre-Rules Mapping in Tax link application
	 * COERPC-7277
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void exportToCSVPreRulesMappingTest( ) throws IOException
	{
		assertTrue(preRulesMapping.exportToCSVPreRulesMapping(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Verify default rules(O2C&P2P) for Pre-Rules Mapping in Tax link application
	 * as a result of selected checkbox - Vertex recommended rules on Onboarding screen
	 * COERPC-10582
	 */

	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void verifyVertexRecommendedPreRulesTest( )
	{
		assertTrue(preRulesMapping.verifyDefaultRulesInPreRules());
	}

	/*
	 * Verify Edit Disabled Pre-Rules Mapping in Tax link application
	 * COERPC-10937
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void editDisabledPreCalcRulesMappingTest( )
	{
		preRulesMapping.navigateToPreRulesMappingPage();
		ruleName = preRulesMapping.addConstantFunctionPreRuleToViewAndEdit(
			RulesMapping.RuleMappingDetails.defaultConditionSet);
		assertTrue(preRulesMapping.editDisabledPreCalcRulesMapping(ruleName));
	}
}
