package com.vertex.quality.connectors.taxlink.ui_e2e.tests;

import com.vertex.quality.connectors.taxlink.common.TaxLinkDatabase;
import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.RulesMapping;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkPostCalcRulesMappingPage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the End to End test cases for POST-CALC Rules Mapping Module
 * from POST-CALC Rules Mapping tab in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkPostCalcRulesMappingTests extends TaxLinkBaseTest
{
	TaxLinkDatabase dbPage;

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		postCalcRulesMapping = new TaxLinkPostCalcRulesMappingPage(driver);
		dbPage = new TaxLinkDatabase();
		initialization_Taxlink();
	}

	/**
	 * Add POST-CALC Rules Mapping in Tax link application
	 * Map Function
	 * COERPC-10541
	 */
	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void addPostCalcRuleMapFunction_e2e_Test( ) throws IOException
	{
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		assertTrue(postCalcRulesMapping.disableRules("POSTCALC"));
		String ruleID = postCalcRulesMapping.addMapPostCalcFunctionRule(
			RulesMapping.RuleMappingDetails.defaultConditionSet);
		writeToFile(ruleID);
	}

	/**
	 * Verify taxlink database for POST-CALC Rules Mapping
	 * after creating an invoice in Oracle ERP
	 * Map Function
	 * COERPC-10541
	 */
	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void verifyDBPostCalcRuleMapFunction_e2e_Test( ) throws Exception
	{
		String ruleID = String.valueOf(getFileReadPath().get(0));
		assertTrue(dbPage.db_verifyMapPostCalcRuleInDB(ruleID));
	}

	/**
	 * Add POST-CALC Rules Mapping in Tax link application
	 * Substring Function
	 * COERPC-10540
	 */
	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void addPostCalcRuleSubstringFunction_e2e_Test( ) throws IOException
	{
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		assertTrue(postCalcRulesMapping.disableRules("POSTCALC"));
		String ruleID = postCalcRulesMapping.addSubstringPostCalcFunctionRule(
			RulesMapping.RuleMappingDetails.defaultConditionSet);
		writeToFile(ruleID);
	}

	/**
	 * Verify taxlink database for POST-CALC Rules Mapping
	 * after creating an invoice in Oracle ERP
	 * Substring Function
	 * COERPC-10540
	 */
	@Test(groups = { "taxlink_rulesmapping_regression" })
	public void verifyDBPostCalcRuleSubstringFunction_e2e_Test( ) throws Exception
	{
		String ruleID = String.valueOf(getFileReadPath().get(0));
		assertTrue(dbPage.db_verifySubstringPostCalcRuleInDB(ruleID));
	}

	/**
	 * Method to disable all existing calc rules (Precalc/Postcalc)
	 * for (Precalc/Postcalc) calc rules mapping tab in TaxLink UI
	 * reason being Invoice won't get validated in ERP until and unless
	 * these records are disabled
	 */

	@Test(groups = { "taxlink_rulesmapping_regression", "taxlink_e2e_smoke", "taxlink_ui_e2e_regression" })
	public void disableCalcRulesTest( )
	{
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		assertTrue(postCalcRulesMapping.disableRules("PRECALC"));
		assertTrue(postCalcRulesMapping.disableRules("POSTCALC"));
	}
}
