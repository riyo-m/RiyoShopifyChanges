package com.vertex.quality.connectors.taxlink.api.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.common.webservices.ProdInstanceCleanUpAPI;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Container for all test scenarios targeting Tax link's Production instance
 * clean up through API calls.
 *
 * @author mgaikwad
 */

public class TaxLinkAPICleanProdInstanceTests extends ProdInstanceCleanUpAPI
{
	public TaxLinkUIUtilities utils = new TaxLinkUIUtilities();

	/**
	 * Configures test settings.
	 */
	@BeforeClass(alwaysRun = true, groups = { "TaxLink_ui_regression" })
	private void setup( ) { initializer.initializeVertextlUIApiTest(); }

	/**
	 * Test to clean a Production Fusion Instance from Taxlink
	 * CORPOD-1824
	 */
	@Test(groups = { "TaxLink_ui_regression" })
	public void cleanupProductionInstanceTest( )
	{
		VertexLogger.log("Cleaning up the production instance..");
		assertTrue(cleanProductionInstance());
	}
}
