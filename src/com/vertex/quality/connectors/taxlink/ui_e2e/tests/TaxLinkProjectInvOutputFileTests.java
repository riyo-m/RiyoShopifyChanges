package com.vertex.quality.connectors.taxlink.ui_e2e.tests;

import com.vertex.quality.connectors.taxlink.common.TaxLinkDatabase;
import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBaseTest;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.InventoryRulesMapping;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkProjectInvOutputFilePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkSignOnPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * this class represents smoke test case for Project Output File
 * in Inventory Rules Mapping Module Tab containing in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkProjectInvOutputFileTests extends TaxLinkBaseTest
{
	public TaxLinkDatabase dbPage;
	int fusionInstanceID, jobID;
	String ruleID, jobIDString;

	@BeforeMethod(alwaysRun = true)
	public void loginTestCase( ) throws IOException
	{
		signOnPage = new TaxLinkSignOnPage(driver);
		homePage = new com.vertex.quality.connectors.taxlink.ui_e2e.pages.TaxLinkHomePage(driver);
		projectInvOutputFilePage = new TaxLinkProjectInvOutputFilePage(driver);
		dbPage = new TaxLinkDatabase();
		initialization_Taxlink();
		assertTrue(homePage.selectInstance(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
	}

	/**
	 * Add Project rule - Inventory Rules Mapping in Tax link application
	 * Map Function
	 * COERPC-11123
	 */

	@Test(groups = "taxlink_invrulesmapping_regression")
	public void addAndVerifyMapFunctionProjectRule_e2e_Test( ) throws IOException
	{
		assertTrue(homePage.selectInstance(
			com.vertex.quality.connectors.taxlink.ui_e2e.enums.OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC));
		ruleID = projectInvOutputFilePage.addAndVerifyInvProjectRule("MAP",
			InventoryRulesMapping.INVENTORY_RULES_MAPPING.invConditionSet, "Attribute10",
			"Inventory Project Attribute02");
		writeToFile(ruleID);
	}

	/**
	 * Verify taxlink database for Project output for inv rules mapping
	 * after creating a request in Oracle's UCM server
	 * Map Function
	 * COERPC-11123
	 */
	@Test(groups = { "taxlink_invrulesmapping_regression" })
	public void verifyDBProjectInvRuleMapFunction_e2e_Test( ) throws Exception
	{
		fusionInstanceID = dbPage.db_getFusionInstanceIDFromDB();
		jobIDString = String.valueOf(getFileReadPath().get(0));
		jobID = Integer.parseInt(jobIDString);
		assertTrue(dbPage.db_verifyMapProjectInvRuleInDB(fusionInstanceID, jobID));
	}
}
