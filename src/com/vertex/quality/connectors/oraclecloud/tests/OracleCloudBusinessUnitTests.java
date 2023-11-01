package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudPageNavigationData;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudManageBusinessUnitPage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * This class contains Oracle ERP part of test method to verify Business Unit import in Taxlink
 *
 * @author mgaikwad
 */
public class OracleCloudBusinessUnitTests extends OracleCloudBaseTest
{
	/**
	 * JIRA Issue: COERPC-8877
	 */
	@Test(groups = { "taxlink_ui_e2e_regression", "taxlink_e2e_smoke" })
	public void manageBusinessUnitTest( ) throws IOException
	{
		loadInitialTestPage_ecogdev3();

		signInToHomePage();

		navigateToBusinessUnitPage(OracleCloudPageNavigationData.MANAGE_BUSINESS_UNIT_PAGE);

		String text = new OracleCloudManageBusinessUnitPage(driver).searchBusinessUnit();

		writeToFile(text);
	}
}
