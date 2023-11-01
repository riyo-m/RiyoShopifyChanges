package com.vertex.quality.connectors.taxlink.api.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiInitializer;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.common.webservices.RulesMappingAPI;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Container for all test scenarios targetting Taxlink's Post-calc Rules mapping
 * capabilities through API calls.
 *
 * @author mgaikwad
 */

public class TaxLinkAPIPostCalcRulesMappingTests extends RulesMappingAPI
{
	public TaxLinkUIUtilities utils = new TaxLinkUIUtilities();

	/**
	 * Configures test settings.
	 */
	@BeforeClass(alwaysRun = true, groups = { "TaxLink_API_regression" })
	private void setup( ) { TaxLinkApiInitializer.initializeVertextlUIApiTest(); }

	/**
	 * Test to retrieve All Rules for a given Fusion Instance Identifier from Post-calc rules
	 */
	@Test(groups = { "TaxLink_API_regression" })
	public void getAllPostCalcRulesTest( )
	{
		VertexLogger.log("Getting all rules.");
		assertTrue(getAllPostRules());
	}

	/**
	 * Test to retrieve rule functions for a given Fusion Instance Identifier from Post-calc rules
	 */
	@Test(groups = { "TaxLink_API_regression" })
	public void getFunctionsForPostCalcRulesTest( )
	{
		VertexLogger.log("Getting functions for all rules.");
		assertTrue(getFunctionsForPostRules());
	}

	/**
	 * Test to retrieve rule attributes for rules for a Fusion Instance Identifier from Post-calc rules
	 */
	@Test(groups = { "TaxLink_API_regression" })
	public void getRuleAttributesForPostCalcRulesTest( )
	{
		VertexLogger.log("Getting attributes for all rules.");
		assertTrue(getRuleAttributesForPostRules());
	}

	/**
	 * Test to create a new rule for a Fusion Instance Identifier in Post-calc rules
	 */
	@Test(groups = { "TaxLink_API_regression" })
	public void createNewRulePostCalcRulesTest( )
	{
		VertexLogger.log("Creating a new rule..");
		assertTrue(createNewRulePostRules(140, true, "MAP", null, "POSTCALC", utils.randomText(), "1", 5,"1900-01-01", 164
			));
	}
}
