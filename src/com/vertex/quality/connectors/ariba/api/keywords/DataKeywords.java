package com.vertex.quality.connectors.ariba.api.keywords;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.api.common.webservices.AribaSoapResponse;
//import com.vertex.quality.connectors.ariba.common.utils.AribaAPITestUtilities;
import java.io.File;
import static org.testng.Assert.fail;

/**
 * Methods used to manipulate, compare, and process data for Ariba test cases.
 *
 * @author vgosavi
 */

public class DataKeywords
{
	//protected AribaAPITestUtilities apiUtil = new AribaAPITestUtilities();
	AribaSoapResponse aribaSoapResponse = new AribaSoapResponse();

	/**
	 * Validate a Ariba XML response by checking tax values in specific tags.
	 *
	 * This default checks for Status, Category, tax Type, and tax Amount for single line
	 *
	 * @param respFileName Name of response File
	 * @param status       The expected External Tax response status.
	 * @param taxType      The expected tax Type.
	 * @param taxAmount    The expected tax amount used in calculation.
	 */
	public void validateResponseForStatusTaxTypeTaxAmtForSingleLineRequisition( String respFileName, String status, String taxType, String taxAmount )
	{
		boolean testStatus = false;

		File responseXml = aribaSoapResponse.getRespXmlFile(respFileName);

		aribaSoapResponse.setStatusTaxTypeTaxAmountForSingleLine(status, taxType, taxAmount);

		try
		{
			aribaSoapResponse.readAllTagsValues(responseXml);
			testStatus = aribaSoapResponse.compareValuesInResp(taxType, taxAmount);
		}
		catch ( Exception ex )
		{
			VertexLogger.log(
					"There was in issue in the getRespXmlFile method. See printed stack trace " + "for details.",
					VertexLogLevel.ERROR);
			ex.printStackTrace();
			fail("Test failed when getting XML and reading tags.");
		}
		if ( !testStatus )
		{
			fail("Response did'nt contained the expected values in " + respFileName + "-resp.xml file");
		}
	}

	/**
	 * Validate a Ariba XML response by checking tax values in specific tags.
	 *
	 * This default checks for 2 amounts and 2 rates.
	 *
	 * @param respFileName  Name of response File
	 * @param category      The expected amount of tax used in calculation for line 2
	 * @param taxType`      The expected rate at which the tax was calculated.
	 * @param taxAmt1       The expected line Amount used in calculation
	 * @param taxAmt2       The expected rate at which the tax was calculated for line 2
     */
	public void validateResponseForStatusTaxTypeTaxAmtForMultipleLineRequisition( String respFileName, String category, String taxType, String taxAmt1, String taxAmt2 )
	{
		boolean testStatus = false;
		boolean lineOneStatus = false;
		boolean lineTwoStatus = false;

		File responseXml = aribaSoapResponse.getRespXmlFile(respFileName);
		aribaSoapResponse.setTaxTypeTaxAmountForMultipleLine(category, taxType, taxAmt1, taxAmt2);

		try
		{
			aribaSoapResponse.readAllTagsValues(responseXml);

			lineOneStatus = aribaSoapResponse.compareValuesInResp(taxType,taxAmt1);
			lineTwoStatus = aribaSoapResponse.compareValuesInResp(taxType,taxAmt2);
			if ( lineOneStatus && lineTwoStatus )
			{
				testStatus = true;
			}
		}
		catch ( Exception ex )
		{
			VertexLogger.log(
				"There was in issue in the getRespXmlFile method. See printed stack trace " + "for details.",
				VertexLogLevel.ERROR);
			ex.printStackTrace();
			fail("Test failed when getting XML and reading tags.");
		}
		if ( !testStatus )
		{
			fail("Response did'nt contained the expected values in " + respFileName + "-resp.xml file");
		}
	}

	/**
	 * Validate a Ariba XML response by checking tax values in specific tags.
	 *
	 * This default checks for tax Amounts,tax Rate, line Amounts for single line
	 *
	 * @param respFileName Name of response File
	 * @param category     the expected category
	 * @param taxType      the expected tax type
	 * @param percent      the expected percent
	 * @param taxAmt       The expected amount of tax used in calculation.
	**/
	public void validateResponseForCategoryTaxTypePercentTaxAmtForSingleLineRequisition( String respFileName, String category, String taxType, String percent, String taxAmt)
	{
		boolean testStatus = false;

		File responseXml = aribaSoapResponse.getRespXmlFile(respFileName);

			aribaSoapResponse.setCategoryTaxTypePercentTaxAmt(category, taxType, percent, taxAmt);

		try
		{
			aribaSoapResponse.readAllTagsValues(responseXml);
			testStatus = aribaSoapResponse.compareValuesInRespForCategory(category, taxType, percent, taxAmt);
		}
		catch ( Exception ex )
		{
			VertexLogger.log(
				"There was in issue in the getRespXmlFile method. See printed stack trace " + "for details.",
				VertexLogLevel.ERROR);
			ex.printStackTrace();
			fail("Test failed when getting XML and reading tags.");
		}
		if ( !testStatus )
		{
			fail("Response did'nt contained the expected values in " + respFileName + "-resp.xml file");
		}
	}

	/**
	 * Validate a Ariba XML response by checking tax values in specific tags.
	 *
	 * This default checks for tax Amounts,tax Rate, line Amounts for single line
	 *
	 * @param respFileName Name of response File
	 * @param AbatementPercent      the expected abatement percent.
	 * @param percent      the expected percent.
	 * @param taxAmt       The expected amount of tax used in calculation.
	 **/

	public void validateResponseForAbatementPercentPercentTaxAmountForSingleLineRequisition( String respFileName, String AbatementPercent, String percent, String taxAmt)
	{
		boolean testStatus = false;

		File responseXml = aribaSoapResponse.getRespXmlFile(respFileName);

		aribaSoapResponse.setAbatementPercentPercentTaxAmt(AbatementPercent, percent, taxAmt);

		try
		{
			aribaSoapResponse.readAllTagsValues(responseXml);
			testStatus = aribaSoapResponse.compareValuesInResp2(AbatementPercent, percent, taxAmt);
		}
		catch ( Exception ex )
		{
			VertexLogger.log(
					"There was in issue in the getRespXmlFile method. See printed stack trace " + "for details.",
					VertexLogLevel.ERROR);
			ex.printStackTrace();
			fail("Test failed when getting XML and reading tags.");
		}
		if ( !testStatus )
		{
			fail("Response did'nt contained the expected values in " + respFileName + "-resp.xml file");
		}
	}
}