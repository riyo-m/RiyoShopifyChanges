package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.common.utils.OracleUtilities;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudInvRulesMappingPage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * This class contains Oracle ERP part of test method to verify inventory rules
 * passed through the excel file "KOPAS0016_Vertex_InventoryTrxExtract"
 * to validate the rules present in Taxlink package in DB
 * (i.e. com\\vertex\\quality\\connectors\\taxlink\\ui_e2e\\tests\\TaxLinkPreCalcInvRulesMappingTests.java)
 *
 * @author mgaikwad
 */
public class OracleCloudInvRulesMappingTests extends OracleCloudBaseTest
{
	private OracleUtilities utilities = new OracleUtilities();

	/**
	 * Create AP invoice with Rule name passed from taxlink text file
	 * COERPC-11119
	 */
	@Test(groups = { "taxlink_invrulesmapping_regression" })
	public void createUCMRequestInvRulesMappingTest( ) throws Exception
	{
		String jobID = null, jobIDTrimmed = null;
		String type = "Document - Any generic document";
		String securityGroup = "FAFusionImportExport";
		String accountName = "fin$/tax$/export$";
		String filePath
			= "C:\\connector-quality-java\\ConnectorQuality\\resources\\csvfiles\\oracle\\KOPAS0016_Vertex_InventoryTrxExtract.csv";
		String randChar = utilities.getRandomDigit();
		String titleInForm = "KOPAS0016_" + "Vertex_InventoryTrxExtract" + randChar;
		loadInitialUCMPage();

		signIntoUCMServer();

		OracleCloudInvRulesMappingPage invRulesMappingPage = new OracleCloudInvRulesMappingPage(driver);
		if ( invRulesMappingPage.addContentCheckIn(titleInForm, type, securityGroup,accountName, filePath) )
		{
			VertexLogger.log("Title: " + titleInForm);
			VertexLogger.log("Check-in successful!!");
			jobID = invRulesMappingPage.validateJobId(titleInForm, securityGroup);
			if ( jobID != null )
			{
				jobIDTrimmed = jobID.replaceAll("\\s", "");
				VertexLogger.log("Validation of Job ID is successful!!");
				assertTrue(true, "Validation of Job ID is successful!!");
			}
		}
		writeToFile(jobIDTrimmed);
	}
}