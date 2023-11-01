package com.vertex.quality.connectors.taxlink.api.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.common.webservices.RulesMappingAPI;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Container for all test scenarios targetting Taxlink's Pre-calc Rules mapping
 * capabilities through API calls.
 *
 * @author mgaikwad
 */

public class TaxLinkAPIPreCalcRulesMappingTests extends RulesMappingAPI
{
	public TaxLinkUIUtilities utils = new TaxLinkUIUtilities();

	/**
	 * Configures test settings.
	 */
	@BeforeClass(alwaysRun = true, groups = { "TaxLink_API_regression" })
	private void setup( ) { initializer.initializeVertextlUIApiTest(); }

	/**
	 * Test to retrieve All Rules for a given Fusion Instance Identifier from Pre-calc rules
	 */
	@Test(groups = {"TaxLink_API_regression"})
	public void getAllPreCalcRulesTest( )
	{
		VertexLogger.log("Getting all rules.");
		assertTrue(getAllPreRules());
	}

	/**
	 * Test to retrieve rule condition sets for a given Fusion Instance Identifier from Pre-calc and post-calc rules
	 */
	@Test(groups = {"TaxLink_API_regression"})
	public void getConditionSetForPreAndPostCalcRulesTest( )
	{
		VertexLogger.log("Getting condition sets for all rules.");
		assertTrue(getConditionSetForPreAndPostRules());
	}

	/**
	 * Test to retrieve rule functions for a given Fusion Instance Identifier from Pre-calc rules
	 */
	@Test(groups = {"TaxLink_API_regression"})
	public void getFunctionsForPreCalcRulesTest( )
	{
		VertexLogger.log("Getting functions for all rules.");
		assertTrue(getFunctionsForPreRules());
	}

	/**
	 * Test to retrieve rule attributes for rules for a Fusion Instance Identifier from Pre-calc rules
	 */
	@Test(groups = {"TaxLink_API_regression"})
	public void getRuleAttributesForPreCalcRulesTest( )
	{
		VertexLogger.log("Getting attributes for all rules.");
		assertTrue(getRuleAttributesForPreRules());
	}

	/**
	 * Test to create a new rule for a Fusion Instance Identifier in Pre-calc rules
	 */
	@Test(groups = {"TaxLink_API_regression"})
	public void createNewRulePreCalcRulesTest( )
	{
		VertexLogger.log("Creating a new rule..");
		assertTrue(
			createNewRulePreRules(140, true, null, "MAP", "PRECALC", utils.randomText(), "1", 5, "1900-01-01", 164));
	}
}
