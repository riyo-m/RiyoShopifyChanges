package com.vertex.quality.connectors.taxlink.ui.tests;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkInvConditionSetsPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkInvPreCalcRulesMappingPage;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the regression test cases for Inventory Pre-calc Rules Mapping Module Tab present in
 * Inventory Rules Mapping tab in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkInvPreCalcRulesMappingTests extends TaxLinkBaseTest
{
	public String invConditionName;
	public String invPreCalcRuleName;

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		invPreCalcRules = new TaxLinkInvPreCalcRulesMappingPage(driver);
		invCondSetRulesMapping = new TaxLinkInvConditionSetsPage(driver);
		initialization();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Add Pre-Calc Inventory Rule in Tax link application
	 * Map Function
	 * COERPC-11062
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAndVerifyMapFunctionInvPreCalcRuleTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invCondSetRulesMapping.clickOnInvPreCalcRulesMapping();
		assertTrue(
			invPreCalcRules.addAndVerifyInvPreCalcRule("MAP", invConditionName, "Inventory Application ID", null, null,
				"Line Number01", null));
	}

	/**
	 * Add Pre-Calc Inventory Rule in Tax link application
	 * Upper Function
	 * COERPC-11063
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void addAndVerifyUpperFunctionInvPreCalcRuleTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invCondSetRulesMapping.clickOnInvPreCalcRulesMapping();
		assertTrue(invPreCalcRules.addAndVerifyInvPreCalcRule("UPPER", invConditionName, "Project Name", null, null,
			"Inventory Company Code", null));
	}

	/**
	 * Add Pre-Calc Inventory Rule in Tax link application
	 * Lower Function
	 * COERPC-11064
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void addAndVerifyLowerFunctionInvPreCalcRuleTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invCondSetRulesMapping.clickOnInvPreCalcRulesMapping();
		assertTrue(invPreCalcRules.addAndVerifyInvPreCalcRule("LOWER", invConditionName, "Expenditure Type", null, null,
			"Inventory Division Code", null));
	}

	/**
	 * Add Pre-Calc Inventory Rule in Tax link application
	 * Constant Function
	 * COERPC-11065
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void addAndVerifyConstantFunctionInvPreCalcRuleTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invCondSetRulesMapping.clickOnInvPreCalcRulesMapping();
		assertTrue(invPreCalcRules.addAndVerifyInvPreCalcRule("CONSTANT", invConditionName, null, null, null,
			"Physical Origin Province", "AM0006475"));
	}

	/**
	 * Add Pre-Calc Inventory Rule in Tax link application
	 * Concat Function
	 * COERPC-11066
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAndVerifyConcatFunctionInvPreCalcRuleTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invCondSetRulesMapping.clickOnInvPreCalcRulesMapping();
		assertTrue(invPreCalcRules.addAndVerifyInvPreCalcRule("CONCAT", invConditionName, null, "Number02", "Number03",
			"Physical Origin Postal Code", null));
	}

	/**
	 * Add Pre-Calc Inventory Rule in Tax link application
	 * Substring Function
	 * COERPC-11067
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAndVerifySubstringFunctionInvPreCalcRuleTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invCondSetRulesMapping.clickOnInvPreCalcRulesMapping();
		assertTrue(invPreCalcRules.addAndVerifyInvPreCalcRule("SUBSTRING", invConditionName, "Number04", null, null,
			"Line Number05", null));
	}

	/**
	 * Add Pre-Calc Inventory Rule in Tax link application
	 * Split Function
	 * COERPC-11068
	 */
	@Test(groups = { "taxlink_ui_regression", "taxlink_ui_smoke" })
	public void addAndVerifySplitFunctionInvPreCalcRuleTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invCondSetRulesMapping.clickOnInvPreCalcRulesMapping();
		assertTrue(
			invPreCalcRules.addAndVerifyInvPreCalcRule("SPLIT", invConditionName, "XLA Inventory Segments", null, null,
				"Inventory Division Code", null));
	}

	/**
	 * Add INV Pre-Rules Mapping in Tax link application
	 * NVL Function - Place another Source value
	 * COERPC-11073
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void addAndVerifyNvl_placeSourceValFunctionINVPreRuleTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invCondSetRulesMapping.clickOnInvPreCalcRulesMapping();
		assertTrue(invPreCalcRules.addAndVerifyNvlFunction_placeSourceValPreRule("NVL", invConditionName,
			"Inventory Product Code", "Expenditure Type", "Physical Origin Province"));
	}

	/**
	 * Add INV Pre-Rules Mapping in Tax link application
	 * NVL Function - Place a CONSTANT value
	 * COERPC-11074
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void addAndVerifyNvl_placeConstantValFunctionINVPreRuleTest( ) throws InterruptedException
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invCondSetRulesMapping.clickOnInvPreCalcRulesMapping();
		assertTrue(invPreCalcRules.addAndVerifyNvlFunction_placeConstantValPreRule("NVL", invConditionName,
			"Inventory Product Code", "MGNVL2702", "Physical Origin Province"));
	}

	/**
	 * View Inv Pre-Rules Mapping in Tax link application
	 * COERPC-11075
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void viewInvPreCalcRulesMappingTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invPreCalcRuleName = invPreCalcRules.addConstantFunctionInvPreRule("CONSTANT", invConditionName,
			"Inventory Item Category", "AM0006475");
		assertTrue(invPreCalcRules.viewPreCalcInvRulesMapping(invPreCalcRuleName));
	}

	/**
	 * Edit Inv Pre-Calc Rules Mapping in Tax link application
	 * COERPC-11076
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void editInvPreCalcRulesMappingTest( )
	{
		invConditionName = invCondSetRulesMapping.addAndVerifyINVConditionSet();
		invPreCalcRuleName = invPreCalcRules.addConstantFunctionInvPreRule("CONSTANT", invConditionName,
			"Inventory Item Category", "AM0006475");
		assertTrue(invPreCalcRules.editPreCalcInvRulesMapping(invPreCalcRuleName));
	}

	/**
	 * Export to CSV Pre-Calc Inv Rules Mapping in Tax link application
	 * COERPC-11077
	 */
	@Test(groups = { "taxlink_ui_regression" })
	public void exportToCSVInvPreCalcRulesMappingTest( ) throws IOException
	{
		assertTrue(
			invPreCalcRules.exportToCSVPreCalcInvRulesMapping(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}
}
