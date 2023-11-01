package com.vertex.quality.connectors.taxlink.ui_e2e.tests;

import com.vertex.quality.connectors.taxlink.common.TaxLinkDatabase;
import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.RulesMapping;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkPostCalcRulesMappingPage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkPreCalcRulesMappingPage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the End-to-End test cases for PRE-CALC Rules Mapping Module
 * from PRE-CALC Rules Mapping tab in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkPreCalcRulesMappingTests extends TaxLinkBaseTest
{
	public TaxLinkDatabase dbPage;

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( )
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		preCalcRulesMapping = new TaxLinkPreCalcRulesMappingPage(driver);
		postCalcRulesMapping = new TaxLinkPostCalcRulesMappingPage(driver);
		dbPage = new TaxLinkDatabase();
		initialization_Taxlink();
	}

	/**
	 * Add PRE-CALC Rules Mapping in Tax link application
	 * Map Function
	 * COERPC-9170
	 */

	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void addPreCalcRuleMapFunction_e2e_Test( ) throws IOException
	{
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		assertTrue(postCalcRulesMapping.disableRules("PRECALC"));
		String ruleID = preCalcRulesMapping.addMapFunctionRule(RulesMapping.RuleMappingDetails.apOnlyConditionSet);
		writeToFile(ruleID);
	}

	/**
	 * Verify taxlink database for PRE-CALC Rules Mapping
	 * after creating an invoice in Oracle ERP
	 * Map Function
	 * COERPC-9170
	 */

	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void verifyDBPreCalcRuleMapFunction_e2e_Test( ) throws Exception
	{
		String ruleID = String.valueOf(getFileReadPath().get(0));
		assertTrue(dbPage.db_verifyMapPreCalcRuleInDB(ruleID));
	}
	
	/**
	 * Add PRE-CALC Rules Mapping in Tax link application
	 * Lower Function
	 * COERPC-9371
	 */

	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void addPreCalcRuleLowerFunction_e2e_Test( ) throws IOException
	{
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		assertTrue(postCalcRulesMapping.disableRules("PRECALC"));
		String ruleID = preCalcRulesMapping.addLowerFunctionRule(RulesMapping.RuleMappingDetails.apOnlyConditionSet);
		writeToFile(ruleID);
	}

	/**
	 * Verify taxlink database for PRE-CALC Rules Mapping
	 * after creating an invoice in Oracle ERP
	 * LOWER Function
	 * COERPC-9371
	 */

	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void verifyDBPreCalcRuleLowerFunction_e2e_Test( ) throws Exception
	{
		String ruleID = String.valueOf(getFileReadPath().get(0));
		assertTrue(dbPage.db_verifyLowerPreCalcRuleInDB(ruleID));
	}

	/**
	 * Add PRE-CALC Rules Mapping in Tax link application
	 * Upper Function
	 * COERPC-9370
	 */

	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void addPreCalcRuleUpperFunction_e2e_Test( ) throws IOException
	{
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		assertTrue(postCalcRulesMapping.disableRules("PRECALC"));
		String ruleID = preCalcRulesMapping.addUpperFunctionRule(RulesMapping.RuleMappingDetails.apOnlyConditionSet);
		writeToFile(ruleID);
	}

	/**
	 * Verify taxlink database for PRE-CALC Rules Mapping
	 * after creating an invoice in Oracle ERP
	 * UPPER Function
	 * COERPC-9370
	 */

	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void verifyDBPreCalcRuleUpperFunction_e2e_Test( ) throws Exception
	{
		String ruleID = String.valueOf(getFileReadPath().get(0));
		assertTrue(dbPage.db_verifyUpperPreCalcRuleInDB(ruleID));
	}


	/**
	 * Add PRE-CALC Rules Mapping in Tax link application
	 * Concat Function
	 * COERPC-9372
	 */

	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void addPreCalcRuleConcatFunction_e2e_Test( ) throws IOException
	{
		homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
		assertTrue(postCalcRulesMapping.disableRules("PRECALC"));
		String ruleID = preCalcRulesMapping.addConcatFunctionRule(RulesMapping.RuleMappingDetails.apOnlyConditionSet);
		writeToFile(ruleID);
	}

	/**
	 * Verify taxlink database for PRE-CALC Rules Mapping
	 * after creating an invoice in Oracle ERP
	 * Concat Function
	 * COERPC-9372
	 */

	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void verifyDBPreCalcRuleConcatFunction_e2e_Test( ) throws Exception
	{
		String ruleID = String.valueOf(getFileReadPath().get(0));
		assertTrue(dbPage.db_verifyConcatPreCalcRuleInDB(ruleID));
	}

	/**
	 * Add PRE-CALC Rules Mapping in Tax link application
	 * Constant Function
	 * COERPC-10482
	 */

	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void addPreCalcRuleConstantFunction_e2e_Test( ) throws IOException
	{
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		assertTrue(postCalcRulesMapping.disableRules("PRECALC"));
		String ruleID = preCalcRulesMapping.addConstantFunctionRule(RulesMapping.RuleMappingDetails.apOnlyConditionSet);
		writeToFile(ruleID);
	}

	/**
	 * Verify taxlink database for PRE-CALC Rules Mapping
	 * after creating an invoice in Oracle ERP
	 * Constant Function
	 * COERPC-10482
	 */

	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void verifyDBPreCalcRuleConstantFunction_e2e_Test( ) throws Exception
	{
		String ruleID = String.valueOf(getFileReadPath().get(0));
		assertTrue(dbPage.db_verifyConstantPreCalcRuleInDB(ruleID));
	}

	/**
	 * Add PRE-CALC Rules Mapping in Tax link application
	 * NVL Function with Constant
	 * COERPC-10483
	 */

	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void addPreCalcRuleNVLFunction_e2e_Test( ) throws IOException
	{
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		assertTrue(postCalcRulesMapping.disableRules("PRECALC"));
		String ruleID = preCalcRulesMapping.addNVLFunctionRule(RulesMapping.RuleMappingDetails.apOnlyConditionSet);
		writeToFile(ruleID);
	}

	/**
	 * Verify taxlink database for PRE-CALC Rules Mapping
	 * after creating an invoice in Oracle ERP
	 * NVL Function
	 * COERPC-10483
	 */

	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void verifyDBPreCalcRuleNVLFunction_e2e_Test( ) throws Exception
	{
		String ruleID = String.valueOf(getFileReadPath().get(0));
		assertTrue(dbPage.db_verifyNVLPreCalcRuleInDB(ruleID));
	}

	/**
	 * Add PRE-CALC Rules Mapping in Tax link application
	 * SPLIT Function
	 * COERPC-10484
	 */

	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void addPreCalcRuleSplitFunction_e2e_Test( ) throws IOException
	{
		homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
		assertTrue(postCalcRulesMapping.disableRules("PRECALC"));
		String ruleID = preCalcRulesMapping.addSplitFunctionRule(RulesMapping.RuleMappingDetails.apOnlyConditionSet);
		writeToFile(ruleID);
	}

	/**
	 * Verify taxlink database for PRE-CALC Rules Mapping
	 * after creating an invoice in Oracle ERP
	 * Split Function
	 * COERPC-10484
	 */

	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void verifyDBPreCalcRuleSplitFunction_e2e_Test( ) throws Exception
	{
		String ruleID = String.valueOf(getFileReadPath().get(0));
		assertTrue(dbPage.db_verifySplitPreCalcRuleInDB(ruleID));
	}

	/**
	 * Add PRE-CALC Rules Mapping in Tax link application
	 * Substring Function
	 * COERPC-10485
	 */

	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void addPreCalcRuleSubstringFunction_e2e_Test( ) throws IOException
	{
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		assertTrue(postCalcRulesMapping.disableRules("PRECALC"));
		String ruleID = preCalcRulesMapping.addSubstringPreCalcFunctionRule(
			RulesMapping.RuleMappingDetails.apOnlyConditionSet);
		writeToFile(ruleID);
	}

	/**
	 * Verify taxlink database for PRE-CALC Rules Mapping
	 * after creating an invoice in Oracle ERP
	 * Substring Function
	 * COERPC-10485
	 */

	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void verifyDBPreCalcRuleSubstringFunction_e2e_Test( ) throws Exception
	{
		String ruleID = String.valueOf(getFileReadPath().get(0));
		assertTrue(dbPage.db_verifySubstringPreCalcRuleInDB(ruleID));
	}
}
