package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.enums.RulesMapping;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkPostCalcRulesMappingPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the smoke test cases for Post Rules Mapping Module Tab containing
 * Rules Mapping tab in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkPostCalcRulesMappingTests extends TaxLinkBaseTest
{
	String ruleName;

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		postRulesMapping = new TaxLinkPostCalcRulesMappingPage(driver);
		utils = new TaxLinkUIUtilities();
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Add Post-Rules Mapping in Tax link application
	 * Map Function
	 * COERPC-9019
	 */

	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAndVerifyMapFunctionPostRuleTest( )
	{
		assertTrue(
			postRulesMapping.addAndVerifyMapFunctionPostRule(RulesMapping.RuleMappingDetails.defaultConditionSet));
	}

	/**
	 * Add Post-Rules Mapping in Tax link application
	 * Substring Function
	 * COERPC-9020
	 */

	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAndVerifySubStringFunctionPostRule( )
	{
		assertTrue(postRulesMapping.addAndVerifySubStringFunctionPostRule(
			RulesMapping.RuleMappingDetails.defaultConditionSet));
	}

	/**
	 * View Post-Rules Mapping in Tax link application
	 * COERPC-9021
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void viewPostRulesMappingTest( )
	{
		postRulesMapping.navigateToPostRulesMappingPage();
		ruleName = postRulesMapping.addMapFunctionPostRule(RulesMapping.RuleMappingDetails.defaultConditionSet);
		assertTrue(postRulesMapping.viewPostRulesMapping(ruleName));
	}

	/*
	 * Edit Post-Rules Mapping in Tax link application
	 * COERPC-9022
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void editPostRulesMappingTest( )
	{
		postRulesMapping.navigateToPostRulesMappingPage();
		ruleName = postRulesMapping.addMapFunctionPostRule(RulesMapping.RuleMappingDetails.defaultConditionSet);
		assertTrue(postRulesMapping.editPostRulesMapping(ruleName));
	}

	/*
	 * Export to CSV Post-Rules Mapping in Tax link application
	 * COERPC-9023
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void exportToCSVPostRulesMappingTest( ) throws IOException
	{
		assertTrue(postRulesMapping.exportToCSVPostRulesMapping(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/*
	 * Edit Disabled Post-Rules Mapping in Tax link application
	 * COERPC-10938
	 */

	@Test(groups = { "taxlink_ui_regression" })
	public void editDisabledPostRulesMappingTest( )
	{
		postRulesMapping.navigateToPostRulesMappingPage();
		ruleName = postRulesMapping.addMapFunctionPostRule(RulesMapping.RuleMappingDetails.defaultConditionSet);
		assertTrue(postRulesMapping.editDisabledPostCalcRulesMapping(ruleName));
	}
}
