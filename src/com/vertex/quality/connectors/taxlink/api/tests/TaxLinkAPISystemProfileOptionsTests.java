package com.vertex.quality.connectors.taxlink.api.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiInitializer;
import com.vertex.quality.connectors.taxlink.common.TaxLinkConstants;
import com.vertex.quality.connectors.taxlink.common.configuration.TaxLinkSettings;
import com.vertex.quality.connectors.taxlink.common.webservices.SystemProfileOptionsAPI;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * This test class contains all the API tests for System Level Profile Options
 *
 * @author Shilpi.Verma
 */

public class TaxLinkAPISystemProfileOptionsTests extends SystemProfileOptionsAPI
{
	/**
	 * Configures test settings.
	 */
	@BeforeClass(alwaysRun = true, groups = { "TaxLink_API_regression", "taxlink_ui_smoke",
		"taxlink_ui_preProd_smoke" })
	private void setup( )
	{
		TaxLinkApiInitializer.initializeVertextlUIApiTest();
		url = TaxLinkSettings.getTaxLinkSettingsInstance().taxlink_apiEndPointUrl;
	}

	/**
	 * Test to verify summary table of System Profile Options
	 * JIRA Issue: COERPC-10890
	 */
	@Test(groups = { "TaxLink_API_regression", "taxlink_ui_smoke", "taxlink_ui_preProd_smoke" })
	public void getSystemProfileOptionsTest( ) throws Exception {
		VertexLogger.log("Getting summary table records for System Profile Options.");
		sendGetRequest_SystemProfileList(TaxLinkConstants.SYSTEM_PROFILE_OPTIONS_LIST,
			TaxLinkConstants.SYSTEM_PROFILE_OPTIONS_VIEW, url);
	}
}
