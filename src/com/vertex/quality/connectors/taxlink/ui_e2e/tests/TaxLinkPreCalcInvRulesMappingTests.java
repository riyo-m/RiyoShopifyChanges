package com.vertex.quality.connectors.taxlink.ui_e2e.tests;

import com.vertex.quality.connectors.taxlink.common.TaxLinkDatabase;
import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.InventoryRulesMapping;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkHomePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkPreCalcInvRulesMappingPage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the End-to-End test cases for PRE-CALC INV Rules Mapping Module
 * from PRE-CALC INV Rules Mapping tab in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkPreCalcInvRulesMappingTests extends TaxLinkBaseTest
{
	public TaxLinkDatabase dbPage;
	int fusionInstanceID, jobID;
	String ruleID, jobIDString;

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new TaxLinkHomePage(driver);
		preCalcInvRulesMapping = new TaxLinkPreCalcInvRulesMappingPage(driver);
		dbPage = new TaxLinkDatabase();
		initialization_Taxlink();
	}

	/**
	 * Configure Inventory settings in database of Tax link application
	 */

	@Test(groups = { "taxlink_invrulesmapping_regression" })
	public void configureDBInventorySettingsTest( ) throws Exception
	{
		assertTrue(dbPage.db_configureInventoryRules());
	}

	/**
	 * Add PRE-CALC INV Rules Mapping in Tax link application
	 * Map Function
	 * COERPC-11119
	 */

	@Test(groups = { "taxlink_invrulesmapping_regression" })
	public void addPreCalcInvRuleMapFunction_e2e_Test( ) throws IOException
	{
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		ruleID = preCalcInvRulesMapping.addAndVerifyInvPreCalcRule("MAP",
			InventoryRulesMapping.INVENTORY_RULES_MAPPING.invConditionSet, "Number01", "Line Number01", null);
		writeToFile(ruleID);
	}

	/**
	 * Verify taxlink database for PRE-CALC INV Rules Mapping
	 * after creating a request in Oracle's UCM server
	 * Map Function
	 * COERPC-11119
	 */
	@Test(groups = { "taxlink_invrulesmapping_regression" })
	public void verifyDBPreCalcInvRuleMapFunction_e2e_Test( ) throws Exception
	{
		fusionInstanceID = dbPage.db_getFusionInstanceIDFromDB();
		jobIDString = String.valueOf(getFileReadPath().get(0));
		jobID = Integer.parseInt(jobIDString);
		assertTrue(dbPage.db_verifyMapPreCalcInvRuleInDB(fusionInstanceID, jobID));
	}

	/**
	 * Add PRE-CALC INV Rules Mapping in Tax link application
	 * Constant Function
	 * COERPC-11120
	 */

	@Test(groups = { "taxlink_invrulesmapping_regression" })
	public void addPreCalcRuleConstantFunction_e2e_Test( ) throws IOException
	{
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		ruleID = preCalcInvRulesMapping.addAndVerifyInvPreCalcRule("CONSTANT",
			InventoryRulesMapping.INVENTORY_RULES_MAPPING.invConditionSet, null, "Line Char02", "INVTRX");
		writeToFile(ruleID);
	}

	/**
	 * Verify taxlink database for PRE-CALC INV Rules Mapping
	 * after creating a request in Oracle's UCM server
	 * Constant Function
	 * COERPC-11120
	 */
	@Test(groups = { "taxlink_invrulesmapping_regression" })
	public void verifyDBPreCalcInvRuleConstantFunction_e2e_Test( ) throws Exception
	{
		fusionInstanceID = dbPage.db_getFusionInstanceIDFromDB();
		jobIDString = String.valueOf(getFileReadPath().get(0));
		jobID = Integer.parseInt(jobIDString);
		assertTrue(dbPage.db_verifyConstantPreCalcInvRuleInDB(fusionInstanceID, jobID));
	}

	/**
	 * Add PRE-CALC INV Rules Mapping in Tax link application
	 * Substring Function
	 * COERPC-11121
	 */

	@Test(groups = { "taxlink_invrulesmapping_regression" })
	public void addPreCalcRuleSubstringFunction_e2e_Test( ) throws IOException
	{
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		ruleID = preCalcInvRulesMapping.addAndVerifyInvPreCalcRule("SUBSTRING",
			InventoryRulesMapping.INVENTORY_RULES_MAPPING.invConditionSet, "Attribute01", "Line Char01", null);
		writeToFile(ruleID);
	}

	/**
	 * Verify taxlink database for PRE-CALC INV Rules Mapping
	 * after creating a request in Oracle's UCM server
	 * Substring Function
	 * COERPC-11121
	 */
	@Test(groups = { "taxlink_invrulesmapping_regression" })
	public void verifyDBPreCalcInvRuleSubstringFunction_e2e_Test( ) throws Exception
	{
		fusionInstanceID = dbPage.db_getFusionInstanceIDFromDB();
		jobIDString = String.valueOf(getFileReadPath().get(0));
		jobID = Integer.parseInt(jobIDString);
		assertTrue(dbPage.db_verifySubstringPreCalcInvRuleInDB(fusionInstanceID, jobID));
	}
}
