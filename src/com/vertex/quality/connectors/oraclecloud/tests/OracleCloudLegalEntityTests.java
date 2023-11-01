package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudPageNavigationData;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudLegalEntityPage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * This class contains Oracle ERP part of test method to verify Legal Entity import in Taxlink
 *
 * @author Shilpi.Verma
 */
public class OracleCloudLegalEntityTests extends OracleCloudBaseTest
{
	/**
	 * JIRA Issue: COERPC-8860
	 */
	@Test(groups = { "taxlink_ui_e2e_regression", "taxlink_e2e_smoke" })
	public void manageLegalEntity( ) throws IOException
	{
		loadInitialTestPage_ecogdev3();

		signInToHomePage();

		navigateToLegalEntityPage(OracleCloudPageNavigationData.MANAGE_LEGAL_ENTITY_PAGE);

		String text = new OracleCloudLegalEntityPage(driver).searchLegalEntity();
		VertexLogger.log(text);

		writeToFile(text);
	}
}
