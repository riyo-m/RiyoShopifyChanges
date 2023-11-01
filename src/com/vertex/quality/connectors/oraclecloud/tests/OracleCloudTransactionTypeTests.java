package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudPageNavigationData;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudCreateTransactionTypePage;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudManageTransactionTypePage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * This class contains Oracle ERP part of test method to verify Transaction Type import in Taxlink
 *
 * @author mgaikwad
 */
public class OracleCloudTransactionTypeTests extends OracleCloudBaseTest
{
	/**
	 * JIRA Issue: COERPC-8920
	 */
	@Test(groups = { "taxlink_ui_e2e_regression", "taxlink_e2e_smoke" })
	public void manageTransactionTypeTest( ) throws IOException
	{
		loadInitialTestPage_ecogdev3();

		signInToHomePage();

		navigateToTransactionTypePage(OracleCloudPageNavigationData.MANAGE_TRANSACTION_TYPE_PAGE);
		new OracleCloudManageTransactionTypePage(driver).clickOnAddTransactionType();
		String text = new OracleCloudCreateTransactionTypePage(driver).createTransactionType();
		VertexLogger.log(""+text);
		writeToFile(text);
	}
}
