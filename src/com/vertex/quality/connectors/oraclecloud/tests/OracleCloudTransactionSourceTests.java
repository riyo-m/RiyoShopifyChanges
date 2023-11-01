package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudPageNavigationData;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudCreateTransactionSourcePage;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudManageTransactionSourcePage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * This class contains Oracle ERP part of test method to verify Transaction Source import in Taxlink
 *
 * @author mgaikwad
 */
public class OracleCloudTransactionSourceTests extends OracleCloudBaseTest
{
	/**
	 * JIRA Issue: COERPC-8936
	 */
	@Test(groups = { "taxlink_ui_e2e_regression", "taxlink_e2e_smoke" })
	public void manageTransactionSourceTest( ) throws IOException
	{
		loadInitialTestPage_ecogdev3();

		signInToHomePage();

		navigateToTransactionSourcePage(OracleCloudPageNavigationData.MANAGE_TRANSACTION_SOURCE_PAGE);
		new OracleCloudManageTransactionSourcePage(driver).clickOnAddTransactionSource();
		String text = new OracleCloudCreateTransactionSourcePage(driver).createTransactionSource();

		writeToFile(text);
	}
}
