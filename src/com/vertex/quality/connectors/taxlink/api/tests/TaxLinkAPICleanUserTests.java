package com.vertex.quality.connectors.taxlink.api.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.common.webservices.UserCleanUpAPI;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 *  API request builder for Taxlink web services related to Clean up of User
 *  to be available on Sync Users Pop Up in Users tab in taxlink.
 *
 *
 * @author mgaikwad
 */

public class TaxLinkAPICleanUserTests extends UserCleanUpAPI
{
	public TaxLinkUIUtilities utils = new TaxLinkUIUtilities();

	/**
	 * Configures test settings.
	 */
	@BeforeClass(alwaysRun = true, groups = { "TaxLink_ui_regression" })
	private void setup( ) { initializer.initializeVertextlAuthApiTest(); }

	/**
	 * Test to clean a user from Taxlink
	 * CORPOD-1788
	 */
	@Test(groups = { "TaxLink_ui_regression" })
	public void cleanupUserTest( )
	{
		VertexLogger.log("Cleaning up the user 'Automation'..");
		assertTrue(cleanUser());
	}
}
