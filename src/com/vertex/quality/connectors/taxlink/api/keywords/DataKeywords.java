package com.vertex.quality.connectors.taxlink.api.keywords;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.utils.TaxlinkAPIUtilities;
import com.vertex.quality.connectors.taxlink.common.webservices.*;

import java.io.File;
import java.util.Map;

import static org.testng.Assert.fail;

/**
 * Methods used to manipulate, compare, and process data for Taxlink
 * test cases.
 *
 * @author msalomone, ajain
 */
public class DataKeywords
{
	protected TaxlinkAPIUtilities utilities = new TaxlinkAPIUtilities();
	TaxlinkApSoapResponse taxlinkApSoapResponse = new TaxlinkApSoapResponse();
	TaxlinkPoSoapResponse taxlinkPoSoapResponse = new TaxlinkPoSoapResponse();
	TaxlinkArSoapResponse taxlinkArSoapResponse = new TaxlinkArSoapResponse();
	TaxlinkOmSoapResponse taxlinkOmSoapResponse = new TaxlinkOmSoapResponse();
	TaxlinkReqSoapResponse taxlinkReqSoapResponse = new TaxlinkReqSoapResponse();

	/**
	 * Validate a Taxlink XML response by checking tax values
	 * in specific tags.
	 *
	 * This default checks for 2 amts and 2 rates.
	 *
	 * @param respFileName Name of response File
	 * @param taxAmt       The expected amount of tax used in calculation.
	 * @param taxRate      The expected rate at which the tax was calculated.
	 * @param lineAmt      The expected line Amount used in calculation
	 * @param taxAmt       The expected amount of tax used in calculation for line 2
	 * @param taxRate      The expected rate at which the tax was calculated for line 2
	 * @param lineAmt      The expected line Amount used in calculation for line 2
	 */
	public void validateTaxAmtTaxRateLineAmtForTwoLines( String respFileName, String taxAmt, String taxRate,
		String lineAmt, String taxAmt2, String taxRate2, String lineAmt2 )
	{
		boolean testStatus = false;
		boolean lineOneStatus = false;
		boolean lineTwoStatus = false;

		File responseXml = taxlinkApSoapResponse.getRespXmlFile(respFileName);
		taxlinkApSoapResponse.setTaxAmtTaxRateLineAmtArgs(taxAmt, taxRate, lineAmt, taxAmt2, taxRate2, lineAmt2);

		try
		{
			taxlinkApSoapResponse.readAllTagsValues(responseXml);

			lineOneStatus = taxlinkApSoapResponse.compareValuesInResp(taxAmt, taxRate, lineAmt);
			lineTwoStatus = taxlinkApSoapResponse.compareValuesInResp(taxAmt2, taxRate2, lineAmt2);
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
	 * Validate a Taxlink XML response by checking tax values
	 * in specific tags.
	 *
	 * This default checks for tax Amounts,tax Rate, line Amounts for single line
	 *
	 * @param respFileName Name of response File
	 * @param taxAmt       The expected amount of tax used in calculation.
	 * @param taxRate      the expected rate of tax used in calculation.
	 * @param lineAmt      the expected line amount used in calculation.
	 */
	public void validateTaxAmtTaxRateLineAmtForSingleLine( String respFileName, String taxAmt, String taxRate,
		String lineAmt )
	{
		boolean testStatus = false;

		File responseXml = taxlinkApSoapResponse.getRespXmlFile(respFileName);
		taxlinkApSoapResponse.setTaxAmtTaxRateTaxLineArgs(taxAmt, taxRate, lineAmt);

		try
		{
			taxlinkApSoapResponse.readAllTagsValues(responseXml);
			testStatus = taxlinkApSoapResponse.compareValuesInResp(taxAmt, taxRate, lineAmt);
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
	 * Validate a Taxlink XML response by checking Line Amount values
	 * in respective tags.
	 *
	 * This default checks for values  for line Amounts
	 *
	 * @param respFileName Name of response File
	 * @param lineAmt      The expected amount of tax used in calculation.
	 * @param lineAmt1     The expected amount of tax used in calculation for line 2
	 */
	public void ValidateApLineAmounts( String respFileName, String lineAmt, String lineAmt1 )
	{
		boolean testStatus = false;

		File responseXml = taxlinkApSoapResponse.getRespXmlFile(respFileName);
		taxlinkApSoapResponse.setTwoLineAmtArgs(lineAmt, lineAmt1);

		try
		{
			taxlinkApSoapResponse.readTagsValues(responseXml);
			testStatus = taxlinkApSoapResponse.compareTwoLineAmtInResp(lineAmt, lineAmt1);
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
			fail("Response did'nt contained the expected values in " + respFileName);
		}
	}

	/**
	 * Validate a Taxlink XML response by checking tax values
	 * in specific tags.
	 *
	 * This default checks for 2 amts and 2 rates.
	 *
	 * @param respFileName Name of response File
	 * @param taxAmt       The expected amount of tax used in calculation.
	 * @param taxRate      The expected rate at which the tax was calculated.
	 * @param lineAmt      The expected line Amount used in calculation
	 * @param taxAmt       The expected amount of tax used in calculation for line 2
	 * @param taxRate      The expected rate at which the tax was calculated for line 2
	 * @param lineAmt      The expected line Amount used in calculation for line 2
	 */
	public void validateArTaxAmtTaxRateLineAmtForTwoLines( String respFileName, String taxAmt, String taxRate,
		String lineAmt, String taxAmt2, String taxRate2, String lineAmt2 )
	{
		boolean testStatus = false;
		boolean lineOneStatus = false;
		boolean lineTwoStatus = false;

		File responseXml = taxlinkArSoapResponse.getRespXmlFile(respFileName);
		taxlinkArSoapResponse.setTaxAmtTaxRateLineAmtArgs(taxAmt, taxRate, lineAmt, taxAmt2, taxRate2, lineAmt2);

		try
		{
			taxlinkArSoapResponse.readAllTagsValues(responseXml);

			lineOneStatus = taxlinkArSoapResponse.compareValuesInResp(taxAmt, taxRate, lineAmt);
			lineTwoStatus = taxlinkArSoapResponse.compareValuesInResp(taxAmt2, taxRate2, lineAmt2);
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
	 * Validate a Taxlink XML response by checking tax values
	 * in specific tags.
	 *
	 * This default checks for tax Amounts,tax Rate, line Amounts for single line
	 *
	 * @param respFileName Name of response File
	 * @param taxAmt       The expected amount of tax used in calculation.
	 * @param taxRate      the expected rate of tax used in calculation.
	 * @param lineAmt      the expected line amount used in calculation.
	 */
	public void validateArTaxAmtTaxRateLineAmtForSingleLine( String respFileName, String taxAmt, String taxRate,
		String lineAmt )
	{
		boolean testStatus = false;

		File responseXml = taxlinkArSoapResponse.getRespXmlFile(respFileName);
		taxlinkArSoapResponse.setTaxAmtTaxRateTaxLineArgs(taxAmt, taxRate, lineAmt);

		try
		{
			taxlinkArSoapResponse.readAllTagsValues(responseXml);
			testStatus = taxlinkArSoapResponse.compareValuesInResp(taxAmt, taxRate, lineAmt);
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
	 * Validate a Taxlink XML response by checking tax values
	 * in specific tags.
	 *
	 * This default checks for tax Amounts,tax Rate, line Amounts for single line
	 *
	 * @param respFileName Name of response File
	 * @param taxAmt       The expected amount of tax used in calculation.
	 * @param taxRate      the expected rate of tax used in calculation.
	 * @param lineAmt      the expected line amount used in calculation.
	 */
	public void validatePOTaxAmtTaxRateLineAmtForSingleLine( String respFileName, String taxAmt, String taxRate,
		String lineAmt )
	{
		boolean testStatus = false;

		File responseXml = taxlinkPoSoapResponse.getRespXmlFile(respFileName);
		taxlinkPoSoapResponse.setTaxAmtTaxRateTaxLineArgs(taxAmt, taxRate, lineAmt);

		try
		{
			taxlinkPoSoapResponse.readAllTagsValues(responseXml);
			testStatus = taxlinkPoSoapResponse.compareValuesInResp(taxAmt, taxRate, lineAmt);
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
			fail("Response didn't contained the expected values in " + respFileName + "-resp.xml file");
		}
	}

	/**
	 * Validate a Taxlink XML response by checking tax values
	 * in specific tags.
	 *
	 * This default checks for 2 amts and 2 rates.
	 *
	 * @param respFileName Name of response File
	 * @param taxAmt       The expected amount of tax used in calculation.
	 * @param taxRate      The expected rate at which the tax was calculated.
	 * @param lineAmt      The expected line Amount used in calculation
	 * @param taxAmt       The expected amount of tax used in calculation for line 2
	 * @param taxRate      The expected rate at which the tax was calculated for line 2
	 * @param lineAmt      The expected line Amount used in calculation for line 2
	 */
	public void validatePoTaxAmtTaxRateLineAmtForTwoLines( String respFileName, String taxAmt, String taxRate,
		String lineAmt, String taxAmt2, String taxRate2, String lineAmt2 )
	{
		boolean testStatus = false;
		boolean lineOneStatus = false;
		boolean lineTwoStatus = false;

		File responseXml = taxlinkPoSoapResponse.getRespXmlFile(respFileName);
		taxlinkPoSoapResponse.setTaxAmtTaxRateLineAmtArgs(taxAmt, taxRate, lineAmt, taxAmt2, taxRate2, lineAmt2);

		try
		{
			taxlinkPoSoapResponse.readAllTagsValues(responseXml);

			lineOneStatus = taxlinkPoSoapResponse.compareValuesInResp(taxAmt, taxRate, lineAmt);
			lineTwoStatus = taxlinkPoSoapResponse.compareValuesInResp(taxAmt2, taxRate2, lineAmt2);
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
	 * Validate a Taxlink XML response by checking tax values
	 * in specific tags.
	 *
	 * This default checks for tax Amounts,tax Rate, line Amounts for single line
	 *
	 * @param respFileName Name of response File
	 * @param taxAmt       The expected amount of tax used in calculation.
	 * @param taxRate      the expected rate of tax used in calculation.
	 * @param lineAmt      the expected line amount used in calculation.
	 */
	public void validateOmTaxAmtTaxRateLineAmtForSingleLine( String respFileName, String taxAmt, String taxRate,
		String lineAmt )
	{
		boolean testStatus = false;

		File responseXml = taxlinkOmSoapResponse.getRespXmlFile(respFileName);
		taxlinkOmSoapResponse.setTaxAmtTaxRateTaxLineArgs(taxAmt, taxRate, lineAmt);

		try
		{
			taxlinkOmSoapResponse.readAllTagsValues(responseXml);
			testStatus = taxlinkOmSoapResponse.compareValuesInResp(taxAmt, taxRate, lineAmt);
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
	 * Validate a Taxlink XML response by checking tax values
	 * in specific tags.
	 *
	 * This default checks for 2 amts and 2 rates.
	 *
	 * @param respFileName Name of response File
	 * @param taxAmt       The expected amount of tax used in calculation.
	 * @param taxRate      The expected rate at which the tax was calculated.
	 * @param lineAmt      The expected line Amount used in calculation
	 * @param taxAmt       The expected amount of tax used in calculation for line 2
	 * @param taxRate      The expected rate at which the tax was calculated for line 2
	 * @param lineAmt      The expected line Amount used in calculation for line 2
	 */
	public void validateOmTaxAmtTaxRateLineAmtForTwoLines( String respFileName, String taxAmt, String taxRate,
		String lineAmt, String taxAmt2, String taxRate2, String lineAmt2 )
	{
		boolean testStatus = false;
		boolean lineOneStatus = false;
		boolean lineTwoStatus = false;

		File responseXml = taxlinkOmSoapResponse.getRespXmlFile(respFileName);
		taxlinkOmSoapResponse.setTaxAmtTaxRateLineAmtArgs(taxAmt, taxRate, lineAmt, taxAmt2, taxRate2, lineAmt2);

		try
		{
			taxlinkOmSoapResponse.readAllTagsValues(responseXml);

			lineOneStatus = taxlinkOmSoapResponse.compareValuesInResp(taxAmt, taxRate, lineAmt);
			lineTwoStatus = taxlinkOmSoapResponse.compareValuesInResp(taxAmt2, taxRate2, lineAmt2);
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
	 * Validate a Taxlink XML response by checking tax values
	 * in specific tags.
	 *
	 * This default checks for tax Amounts,tax Rate, line Amounts for single line
	 *
	 * @param respFileName Name of response File
	 * @param taxAmt       The expected amount of tax used in calculation.
	 * @param taxRate      the expected rate of tax used in calculation.
	 * @param lineAmt      the expected line amount used in calculation.
	 */
	public void validateReqTaxAmtTaxRateLineAmtForSingleLine( String respFileName, String taxAmt, String taxRate,
		String lineAmt )
	{
		boolean testStatus = false;

		File responseXml = taxlinkReqSoapResponse.getRespXmlFile(respFileName);
		taxlinkReqSoapResponse.setTaxAmtTaxRateTaxLineArgs(taxAmt, taxRate, lineAmt);

		try
		{
			taxlinkReqSoapResponse.readAllTagsValues(responseXml);
			testStatus = taxlinkReqSoapResponse.compareValuesInResp(taxAmt, taxRate, lineAmt);
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
	 * Validate a Taxlink XML response by checking values in specific tags.
	 *
	 * This default checks for Error message tag in response
	 *
	 * @param respFileName Name of response File
	 * @param errorMsg     The expected message in response.
	 */
	public void validateErrorStringInResp( String respFileName, String errorMsg )
	{
		boolean testStatus = false;

		File responseXml = taxlinkReqSoapResponse.getRespXmlFile(respFileName);
		taxlinkReqSoapResponse.setErrorMessageArgs(errorMsg);

		try
		{
			taxlinkReqSoapResponse.readAllTagsValues(responseXml);
			testStatus = taxlinkReqSoapResponse.compareFromResp(errorMsg);
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
	 * Validates and continues a Taxlink XML response by checking tax values
	 * in specific tags for requisition workflow
	 *
	 * This default checks for tax Amounts,tax Rate, line Amounts for single line
	 *
	 * @param respFileName Name of response File
	 * @param taxAmt       The expected amount of tax used in calculation.
	 * @param taxRate      the expected rate of tax used in calculation.
	 * @param lineAmt      the expected line amount used in calculation.
	 * @param requestName  The name of the Requisition request
	 */
	public void validateAndContinueReqSingleLine( String respFileName, String taxAmt, String taxRate, String lineAmt,
		String requestName )
	{
		boolean testStatus = false;

		File responseXml = taxlinkReqSoapResponse.getRespXmlFile(respFileName);
		taxlinkReqSoapResponse.setTaxAmtTaxRateTaxLineArgs(taxAmt, taxRate, lineAmt);

		try
		{
			taxlinkReqSoapResponse.readAllTagsValues(responseXml);
			testStatus = taxlinkReqSoapResponse.compareValuesInResp(taxAmt, taxRate, lineAmt);
			taxlinkReqSoapResponse.addStatusInMap(requestName, testStatus);
		}
		catch ( Exception ex )
		{
			VertexLogger.log(
				"There was in issue in the getRespXmlFile method. See printed stack trace " + "for details.",
				VertexLogLevel.ERROR);
			ex.printStackTrace();
			fail("Test failed when getting XML and reading tags.");
		}
	}

	/**
	 * Validates the collected test's status and marks the testcase status accordinly
	 */
	public void validateTestStatus( )
	{
		String key = "";
		String value = "";
		int count = 0;
		boolean testStatus = true;
		int recordCount = taxlinkReqSoapResponse.testStatusMap.size();
		Boolean[] status = new Boolean[recordCount];

		for ( Map.Entry mapData : taxlinkReqSoapResponse.testStatusMap.entrySet() )
		{
			key = String.valueOf(mapData.getKey());
			value = String.valueOf(mapData.getValue());

			status[count] = Boolean.valueOf(value);
			count++;

			VertexLogger.log(key + " request's status is " + value);
		}

		for ( int b = 0 ; b < status.length ; b++ )
		{
			if ( status[b] == false )
			{
				testStatus = false;
			}
		}
		if ( !testStatus )
		{
			fail("Response file did'nt contained the expected values in one of the test");
		}
	}
}
