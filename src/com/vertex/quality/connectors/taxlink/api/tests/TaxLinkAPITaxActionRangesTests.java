package com.vertex.quality.connectors.taxlink.api.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.TaxLinkConstants;
import com.vertex.quality.connectors.taxlink.common.webservices.TaxActionRangesAPI;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * This class contains all the API tests for Tax Action Ranges
 *
 * @author Shilpi.Verma
 */

public class TaxLinkAPITaxActionRangesTests extends TaxActionRangesAPI
{

	/**
	 * Configures test settings.
	 */
	@BeforeClass(alwaysRun = true, groups = { "TaxLink_API_regression" })
	private void setup( )
	{
		initializer.initializeVertextlUIApiTest();
	}

	/**
	 * Test to verify summary table of tax action ranges
	 */
	@Test(groups = { "TaxLink_API_regression" })
	public void getSummaryTaxActionRangesTest( )
	{
		VertexLogger.log("Getting summary table records for Tax Action Ranges.");
		getSummaryTaxActionRanges();
	}

	/**
	 * Test to verify adding tax actions - overcharged, undercharged, zerocharged
	 */
	@Test(groups = { "TaxLink_API_regression" })
	public void add_TaxActionRangesTest( )
	{
		VertexLogger.log("Add new record for Tax Action Ranges_OVERCHARGED.");
		addTaxActionRanges(TaxLinkConstants.TAX_ACTION_RANGE_OVERCHARGED_TA, "OVERCHARGED", "PAYCHARGED");

		VertexLogger.log("Add new record for Tax Action Ranges_UNDERCHARGED.");
		addTaxActionRanges(TaxLinkConstants.TAX_ACTION_RANGE_UNDERCHARGED_TA, "UNDERCHARGED", "PAYCALCULATED");

		VertexLogger.log("Add new record for Tax Action Ranges_ZEROCHARGED.");
		addTaxActionRanges(TaxLinkConstants.TAX_ACTION_RANGE_ZEROCHARGED_TA, "ZEROCHARGED", "ACCRUEALL");
	}
}
