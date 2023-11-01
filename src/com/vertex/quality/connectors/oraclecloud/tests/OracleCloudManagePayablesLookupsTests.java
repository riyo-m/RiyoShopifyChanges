package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudPageNavigationData;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudPayablesLookupsPage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;

import java.io.IOException;

/**
 * This class contains Oracle ERP part of test method to verify Legal Entity import in Taxlink
 *
 * @author Shilpi.Verma
 */
public class OracleCloudManagePayablesLookupsTests extends OracleCloudBaseTest
{
	/**
	 * JIRA Issue: COERPC-8957
	 */
	public void managePayablesLookupsTest( ) throws IOException
	{
		loadInitialTestPage_ecogdev3();

		signInToHomePage();

		navigateToPayablesLookupsPage(OracleCloudPageNavigationData.MANAGE_PAYABLES_LOOKUPS_PAGE);

		String text = new OracleCloudPayablesLookupsPage(driver).addLookup();
		VertexLogger.log(text);

		writeToFile(text);
	}
}
