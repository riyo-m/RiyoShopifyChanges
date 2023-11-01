package com.vertex.quality.connectors.taxlink.common;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBaseTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class contains all the methods related to Database of TaxLink test environment
 *
 * @author Shilpi.Verma, mgaikwad
 */

public class TaxLinkDatabase extends TaxLinkBaseTest
{
	protected Connection con = null;
	protected PreparedStatement stmt = null;
	protected ResultSet res = null;

	String sourceValue = null;
	String targetValue = null;
	String transactionNumber = null;
	String appShortName = null;
	String lineChar1 = null;
	String docType = null;
	String db_query_rule;
	String db_query_rule_result1;
	String db_query_rule_result2;
	String transaction_ID;
	String transactionTypeName;
	String billFromPartyNumber;
	String billFromGeoType1;
	String prodCode;
	String result;

	/**
	 * Method to connect to Database
	 *
	 * @param query
	 *
	 * @throws Exception
	 */
	public void dbConnection( String query ) throws Exception
	{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		con = DriverManager.getConnection(TAXLINK_DB_URL, TAXLINK_DB_USERNAME, TAXLINK_DB_PASSWORD);
		stmt = con.prepareStatement(query);
	}

	/**
	 * Method to connect to database for System profile Options base install
	 *
	 * @param query
	 *
	 * @throws Exception
	 */
	public void dbConnection_94( String query ) throws Exception
	{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		con = DriverManager.getConnection(TAXLINK_DB_94_URL, TAXLINK_DB94_USERNAME, TAXLINK_DB94_PASSWORD);
		stmt = con.prepareStatement(query);
	}

	/**
	 * Method to connect to database for Postgres server(demo0072)
	 *
	 * @param query
	 * @throws Exception
	 */
	public void dbConnection_Postgres(String query) throws Exception {
		Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection(TAXLINK_DB_POSTGRES_URL, TAXLINK_DB_POSTGRES_USERNAME, TAXLINK_DB_POSTGRES_PASSWORD);
		stmt = con.prepareStatement(query);
	}

	/**
	 * Method to close the Database connection
	 *
	 * @throws Exception
	 */
	public void closeDBConnection( ) throws Exception
	{
		res.close();
		stmt.close();
		con.close();
	}

	/**
	 * Overridden method of grandparent class to stop opening of browser
	 */
	@Override
	protected void setupTestCase( )
	{

	}

	/**
	 * Method to fetch Instance names present in the test environment
	 *
	 * @throws Exception
	 */
	public void db_fetchInstance( ) throws Exception
	{
		String db_query = "select * from VTX_FUSION_INSTANCES_T";
		dbConnection(db_query);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			VertexLogger.log(res.getString(TaxLinkConstants.FUSION_INSTANCE_NAME));
		}

		closeDBConnection();
	}

	/**
	 * Method to query data for BIP job data search
	 *
	 * @return
	 *
	 * @throws Exception
	 */
	public List<LinkedList<String>> db_searchSupplierBIPJob( ) throws Exception
	{
		LinkedList<String> po_supplierID = new LinkedList<>();
		LinkedList<String> partyNumber = new LinkedList<>();
		LinkedList<String> vendorName = new LinkedList<>();
		LinkedList<String> vendorNumber = new LinkedList<>();

		String db_query = "select * from VTX_PO_SUPPLIERS_T where SUPPLIER_TYPE = 'SUPPLIER' ";
		dbConnection(db_query);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			po_supplierID.add(res.getString(TaxLinkConstants.PO_SUPPLIER_ID));
			partyNumber.add(res.getString(TaxLinkConstants.PARTY_NUMBER));
			vendorName.add(res.getString(TaxLinkConstants.VENDOR_NAME));
			vendorNumber.add(res.getString(TaxLinkConstants.VENDOR_NUMBER));
		}

		return Arrays.asList(po_supplierID, partyNumber, vendorName, vendorNumber);
	}

	/**
	 * Method to verify MAP Pre calc rule added in taxlink
	 * if it is applied to the transactions
	 * from the taxlink database
	 *
	 * @param transactionID
	 *
	 * @return boolean
	 *
	 * @throws Exception
	 */
	public boolean db_verifyMapPreCalcRuleInDB( String transactionID ) throws Exception
	{
		boolean mappedFlag;

		db_query_rule = "select hdr.APPLICATION_SHORTNAME ,dtl.line_char1\n" +
						"from VTX_TL_TRX_REQ_DTL_T dtl , VTX_TL_TRX_REQ_HDR_T hdr  \n" +
						"where hdr.FUSION_INSTANCE_ID = ( \n" +
						"select FUSION_INSTANCE_ID from VTX_FUSION_INSTANCES_T\n" +
						"where FUSION_INSTANCE_NAME = 'ecog-dev3-us2'\n" + ")\n" +
						"and hdr.REQUEST_HEADER_ID =  dtl.REQUEST_HEADER_ID \n" +
						"and hdr.FUSION_INSTANCE_ID = dtl.FUSION_INSTANCE_ID\n" + "and hdr.TRX_NUMBER = '" +
						transactionID + "'";
		dbConnection(db_query_rule);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			appShortName = res.getString(TaxLinkConstants.APPLICATION_SHORT_NAME);
			VertexLogger.log(appShortName);
			lineChar1 = res.getString(TaxLinkConstants.LINE_CHAR1);
			VertexLogger.log(lineChar1);
		}

		if ( appShortName.equals(lineChar1) )
		{
			VertexLogger.log("Application short name has been mapped to line char1 in database!!");
			mappedFlag = true;
		}
		else
		{
			mappedFlag = false;
		}
		closeDBConnection();
		return mappedFlag;
	}

	/**
	 * Method to verify UPPER Pre calc rule added in taxlink
	 * if it is applied to the transactions
	 * from the taxlink database
	 *
	 * @param transactionID
	 *
	 * @return boolean
	 *
	 * @throws Exception
	 */
	public boolean db_verifyUpperPreCalcRuleInDB( String transactionID ) throws Exception
	{
		boolean upperFlag;

		db_query_rule = "select hdr.TRX_NUMBER ,dtl.line_char1\n" +
						"from VTX_TL_TRX_REQ_DTL_T dtl , VTX_TL_TRX_REQ_HDR_T hdr  \n" +
						"where hdr.FUSION_INSTANCE_ID = ( \n" +
						"select FUSION_INSTANCE_ID from VTX_FUSION_INSTANCES_T\n" +
						"where FUSION_INSTANCE_NAME = 'ecog-dev3-us2'\n" + ")\n" +
						"and hdr.REQUEST_HEADER_ID =  dtl.REQUEST_HEADER_ID \n" +
						"and hdr.FUSION_INSTANCE_ID = dtl.FUSION_INSTANCE_ID\n" + "and hdr.TRX_NUMBER = '" +
						transactionID + "'";
		dbConnection(db_query_rule);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			transactionNumber = res.getString(TaxLinkConstants.TRX_NUMBER);
			VertexLogger.log("TransactionNumber: " + transactionNumber);
		}

		if ( containsUpperCaseLetters(transactionNumber) )
		{
			VertexLogger.log("Transaction Number string is converted into UPPER case in database!!");
			upperFlag = true;
		}
		else
		{
			upperFlag = false;
		}
		closeDBConnection();
		return upperFlag;
	}

	/**
	 * Method to verify LOWER Pre calc rule added in taxlink
	 * if it is applied to the transactions
	 * from the taxlink database
	 *
	 * @param transactionID
	 *
	 * @return boolean
	 *
	 * @throws Exception
	 */
	public boolean db_verifyLowerPreCalcRuleInDB( String transactionID ) throws Exception
	{
		boolean lowerFlag;

		db_query_rule = "select hdr.TRX_NUMBER ,dtl.line_char1\n" +
						"from VTX_TL_TRX_REQ_DTL_T dtl , VTX_TL_TRX_REQ_HDR_T hdr  \n" +
						"where hdr.FUSION_INSTANCE_ID = ( \n" +
						"select FUSION_INSTANCE_ID from VTX_FUSION_INSTANCES_T\n" +
						"where FUSION_INSTANCE_NAME = 'ecog-dev3-us2'\n" + ")\n" +
						"and hdr.REQUEST_HEADER_ID =  dtl.REQUEST_HEADER_ID \n" +
						"and hdr.FUSION_INSTANCE_ID = dtl.FUSION_INSTANCE_ID\n" + "and hdr.TRX_NUMBER = '" +
						transactionID + "'";
		dbConnection(db_query_rule);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			transactionNumber = res.getString(TaxLinkConstants.TRX_NUMBER);
			VertexLogger.log("TransactionNumber: " + transactionNumber);
		}

		if ( containsLowerCaseLetters(transactionNumber) )
		{
			VertexLogger.log("Transaction number string is present in LOWER case in database!!");
			lowerFlag = true;
		}
		else
		{
			lowerFlag = false;
		}
		closeDBConnection();
		return lowerFlag;
	}

	/**
	 * Method to check if all the characters of teh string are in UPPERCASE
	 *
	 * @param trxNumber
	 *
	 * @return boolean
	 */
	public boolean containsUpperCaseLetters( String trxNumber )
	{
		for ( int i = 0 ; i < trxNumber.length() ; i++ )
		{
			if ( Character.isUpperCase(trxNumber.charAt(i)) )
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Method to check if all the characters of the string are in LOWERCASE
	 *
	 * @param trxNumber
	 *
	 * @return boolean
	 */
	public boolean containsLowerCaseLetters( String trxNumber )
	{
		for ( int i = 0 ; i < trxNumber.length() ; i++ )
		{
			if ( Character.isLowerCase(trxNumber.charAt(i)) )
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Method to verify CONCAT Pre calc rule added in taxlink
	 * if it is applied to the transactions
	 * from the taxlink database
	 *
	 * @param transactionID
	 *
	 * @return boolean
	 *
	 * @throws Exception
	 */
	public boolean db_verifyConcatPreCalcRuleInDB( String transactionID ) throws Exception
	{
		boolean concatFlag;

		db_query_rule = "select a.TRX_NUMBER, a.TRANSACTION_ID, a.TRX_TYPE_NAME\n" + "from VTX_TL_TRX_REQ_HDR_T a\n" +
						"where FUSION_INSTANCE_ID in ( select FUSION_INSTANCE_ID from VTX_FUSION_INSTANCES_T\n" +
						"where FUSION_INSTANCE_NAME = 'ecog-dev3-us2') and TRX_NUMBER = '" + transactionID + "'";

		dbConnection(db_query_rule);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			transactionNumber = res.getString(TaxLinkConstants.TRX_NUMBER);
			VertexLogger.log("TRX_NUMBER : " + transactionNumber);
			transaction_ID = res.getString(TaxLinkConstants.TRANSACTION_ID);
			VertexLogger.log("TRANSACTION_ID : " + transaction_ID);
			transactionTypeName = res.getString(TaxLinkConstants.TRX_TYPE_NAME);
			VertexLogger.log("TRX_TYPE_NAME : " + transactionTypeName);
		}

		String concatenatedTransTypeName = checkConcatenation(transactionNumber, transaction_ID);
		if ( concatenatedTransTypeName.equalsIgnoreCase(transactionTypeName) )
		{
			VertexLogger.log("TRX_TYPE_NAME is a concatenated string of TRX_NUMBER & TRANSACTION_ID in database!!");
			concatFlag = true;
		}
		else
		{
			concatFlag = false;
		}
		closeDBConnection();
		return concatFlag;
	}

	/**
	 * Method to check if two strings are concatenated
	 *
	 * @param transactionNumber,transaction_ID
	 *
	 * @return String
	 */
	public String checkConcatenation( String transactionNumber, String transaction_ID )
	{
		String concatenatedString;
		concatenatedString = transactionNumber + transaction_ID;
		VertexLogger.log("Concatenated string formed : " + concatenatedString);
		return concatenatedString;
	}

	/**
	 * Method to verify CONSTANT Pre calc rule added in taxlink
	 * if it is applied to the transactions
	 * from the taxlink database
	 *
	 * @param transactionID
	 *
	 * @return boolean
	 *
	 * @throws Exception
	 */
	public boolean db_verifyConstantPreCalcRuleInDB( String transactionID ) throws Exception
	{
		boolean constantFlag;

		db_query_rule = "select hdr.TRX_NUMBER ,dtl.line_char1\n" +
						"from VTX_TL_TRX_REQ_DTL_T dtl , VTX_TL_TRX_REQ_HDR_T hdr  \n" +
						"where hdr.FUSION_INSTANCE_ID = ( \n" +
						"select FUSION_INSTANCE_ID from VTX_FUSION_INSTANCES_T\n" +
						"where FUSION_INSTANCE_NAME = 'ecog-dev3-us2'\n" + ")\n" +
						"and hdr.REQUEST_HEADER_ID =  dtl.REQUEST_HEADER_ID \n" +
						"and hdr.FUSION_INSTANCE_ID = dtl.FUSION_INSTANCE_ID\n" + "and hdr.TRX_NUMBER = '" +
						transactionID + "'";

		dbConnection(db_query_rule);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			transactionNumber = res.getString(TaxLinkConstants.TRX_NUMBER);
			VertexLogger.log("TRX_NUMBER : " + transactionNumber);
			lineChar1 = res.getString(TaxLinkConstants.LINE_CHAR1);
			VertexLogger.log("LINE CHAR1 : " + lineChar1);
		}

		if ( lineChar1.equals("Automated invoice") )
		{
			VertexLogger.log("Flexfield code1 field has been replaced with string Automated invoice !!");
			constantFlag = true;
		}
		else
		{
			constantFlag = false;
		}
		closeDBConnection();
		return constantFlag;
	}

	/**
	 * Method to verify NVL with Constant Pre calc rule added in taxlink
	 * if it is applied to the transactions
	 * from the taxlink database
	 *
	 * @param transactionID
	 *
	 * @return boolean
	 *
	 * @throws Exception
	 */
	public boolean db_verifyNVLPreCalcRuleInDB( String transactionID ) throws Exception
	{
		boolean nvlFlag;

		db_query_rule = "select a.BILL_FROM_PARTY_NUMBER, a.BILL_FROM_GEOGRAPHY_TYPE1\n" +
						"from VTX_TL_TRX_REQ_DTL_T a\n" + "where FUSION_INSTANCE_ID in ( \n" +
						"select FUSION_INSTANCE_ID from VTX_FUSION_INSTANCES_T\n" +
						"where FUSION_INSTANCE_NAME = 'ecog-dev3-us2'\n" + ")\n" + "and REQUEST_HEADER_ID in (\n" +
						"select REQUEST_HEADER_ID from VTX_TL_TRX_REQ_HDR_T\n" + "where FUSION_INSTANCE_ID in ( \n" +
						"select FUSION_INSTANCE_ID from VTX_FUSION_INSTANCES_T\n" +
						"where FUSION_INSTANCE_NAME = 'ecog-dev3-us2'\n" + ")\n" + "and TRX_NUMBER = '" +
						transactionID + "'" + ")";

		dbConnection(db_query_rule);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			billFromPartyNumber = res.getString(TaxLinkConstants.BILL_FROM_PARTY_NUMBER);
			VertexLogger.log("BILL_FROM_PARTY_NUMBER : " + billFromPartyNumber);
			billFromGeoType1 = res.getString(TaxLinkConstants.BILL_FROM_GEOGRAPHY_TYPE1);
			VertexLogger.log("BILL_FROM_GEOGRAPHY_TYPE1 : " + billFromGeoType1);
		}

		if ( billFromGeoType1.equals("MGNVL2812") )
		{
			VertexLogger.log("NVL applied in database!!");
			nvlFlag = true;
		}
		else
		{
			nvlFlag = false;
		}
		closeDBConnection();
		return nvlFlag;
	}

	/**
	 * Method to verify Split Pre calc rule added in taxlink
	 * if it is applied to the transactions
	 * from the taxlink database
	 *
	 * @param transactionID
	 *
	 * @return boolean
	 *
	 * @throws Exception
	 */
	public boolean db_verifySplitPreCalcRuleInDB( String transactionID ) throws Exception
	{
		boolean splitFlag;

		db_query_rule = "select a.BILL_FROM_PARTY_NUMBER, a.PRODUCT_CODE\n" + "from VTX_TL_TRX_REQ_DTL_T a\n" +
						"where FUSION_INSTANCE_ID in ( \n" + "select FUSION_INSTANCE_ID from VTX_FUSION_INSTANCES_T\n" +
						"where FUSION_INSTANCE_NAME = 'ecog-dev3-us2'\n" + ")\n" + "and REQUEST_HEADER_ID in (\n" +
						"select REQUEST_HEADER_ID from VTX_TL_TRX_REQ_HDR_T\n" + "where FUSION_INSTANCE_ID in ( \n" +
						"select FUSION_INSTANCE_ID from VTX_FUSION_INSTANCES_T\n" +
						"where FUSION_INSTANCE_NAME = 'ecog-dev3-us2'\n" + ")\n" + "and TRX_NUMBER = '" +
						transactionID + "'" + ")";

		dbConnection(db_query_rule);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			prodCode = res.getString(TaxLinkConstants.PRODUCT_CODE);
			VertexLogger.log("PRODUCT_CODE : " + prodCode);
		}

		if ( prodCode.equals("60041") )
		{
			VertexLogger.log("Split function is applied in database!!");
			splitFlag = true;
		}
		else
		{
			splitFlag = false;
		}
		closeDBConnection();
		return splitFlag;
	}

	/**
	 * Method to verify Substring Pre calc rule added in taxlink
	 * if it is applied to the transactions
	 * from the taxlink database
	 *
	 * @param transactionNumber
	 *
	 * @return boolean
	 *
	 * @throws Exception
	 */
	public boolean db_verifySubstringPreCalcRuleInDB( String transactionNumber ) throws Exception
	{
		boolean substringFlag;
		String orgID = null;
		String shipFromTAID = null;

		db_query_rule = "select hdr.DOCUMENT_TYPE_ID, dtl.INTERNAL_ORGANIZATION_ID, dtl.SHIP_FROM_TAID\n" +
						"from VTX_TL_TRX_REQ_DTL_T dtl , VTX_TL_TRX_REQ_HDR_T hdr  \n" +
						"where hdr.FUSION_INSTANCE_ID = ( \n" +
						"select FUSION_INSTANCE_ID from VTX_FUSION_INSTANCES_T\n" +
						"where FUSION_INSTANCE_NAME = 'ecog-dev3-us2'\n" + ")\n" +
						"and hdr.REQUEST_HEADER_ID =  dtl.REQUEST_HEADER_ID \n" +
						"and hdr.FUSION_INSTANCE_ID = dtl.FUSION_INSTANCE_ID\n" + "and hdr.TRX_NUMBER = '" +
						transactionNumber + "'";

		dbConnection(db_query_rule);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			orgID = res.getString("INTERNAL_ORGANIZATION_ID");
			VertexLogger.log("INTERNAL_ORGANIZATION_ID : " + orgID);
			shipFromTAID = res.getString("SHIP_FROM_TAID");
			VertexLogger.log("SHIP_FROM_TAID : " + shipFromTAID);
		}

		int res = isSubstring(shipFromTAID, orgID);

		if ( res == -1 )
		{
			VertexLogger.log("Not present!!");
			substringFlag = false;
		}
		else
		{
			VertexLogger.log(
				"SHIP FROM TAID has substring value of INTERNAL_ORGANIZATION_ID in database at index: !!" + res);
			substringFlag = true;
		}
		closeDBConnection();
		return substringFlag;
	}

	/**
	 * Method to check if substring is present
	 *
	 * @param s1,s2
	 *
	 * @return int
	 */
	public int isSubstring( String s1, String s2 )
	{
		int j;
		int billToLocIDLen = s1.length();
		int orgIDLen = s2.length();

		for ( int i = 0 ; i <= orgIDLen - billToLocIDLen ; i++ )
		{

			for ( j = 0; j < billToLocIDLen ; j++ )
			{
				if ( s2.charAt(i + j) != s1.charAt(j) )
				{
					break;
				}
			}

			if ( j == billToLocIDLen )
			{
				return i;
			}
		}
		return -1;
	}

	/**
	 * Method to verify MAP Post calc rule added in taxlink
	 * if it is applied to the transactions
	 * from the taxlink database
	 *
	 * @param transactionNumber
	 *
	 * @return boolean
	 *
	 * @throws Exception
	 */
	public boolean db_verifyMapPostCalcRuleInDB( String transactionNumber ) throws Exception
	{
		boolean mappedFlag;
		String transactionID = null;
		String situs = null;
		String attribute2 = null;

		db_query_rule = "select TRANSACTION_ID from VTX_TL_TRX_REQ_HDR_T where TRX_NUMBER='" + transactionNumber + "'";
		dbConnection(db_query_rule);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			transactionID = res.getString(TaxLinkConstants.TRANSACTION_ID);
			VertexLogger.log(transactionID + " is the transaction ID for Transaction Number: " + transactionNumber);
		}

		db_query_rule = "select situs from vtx_tl_trx_resp_tax_t where TRANSACTION_ID = '" + transactionID + "'";
		dbConnection(db_query_rule);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			situs = res.getString(TaxLinkConstants.SITUS);
			VertexLogger.log("Situs in DB: " + situs);
		}

		db_query_rule = "select ATTRIBUTE2 from vtx_erp_tax_output_v where TRX_NUMBER='" + transactionNumber + "'";
		dbConnection(db_query_rule);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			attribute2 = res.getString(TaxLinkConstants.ATTRIBUTE2);
			VertexLogger.log("Attribute2 in DB: " + attribute2);
		}

		assert attribute2 != null;
		if ( attribute2.equals(situs) )
		{
			VertexLogger.log("Situs has been mapped to attribute2 in database!!");
			mappedFlag = true;
		}
		else
		{
			mappedFlag = false;
		}
		closeDBConnection();
		return mappedFlag;
	}

	/**
	 * Method to verify Substring Post calc rule added in taxlink
	 * if it is applied to the transactions
	 * from the taxlink database
	 *
	 * @param transactionNumber
	 *
	 * @return boolean
	 *
	 * @throws Exception
	 */
	public boolean db_verifySubstringPostCalcRuleInDB( String transactionNumber ) throws Exception
	{
		boolean substringFlag;
		String transactionID = null;
		String situs = null;
		String attribute2 = null;

		db_query_rule = "select TRANSACTION_ID from VTX_TL_TRX_REQ_HDR_T where TRX_NUMBER='" + transactionNumber + "'";
		dbConnection(db_query_rule);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			transactionID = res.getString(TaxLinkConstants.TRANSACTION_ID);
			VertexLogger.log(transactionID + " is the transaction ID for Transaction Number: " + transactionNumber);
		}

		db_query_rule = "select situs from vtx_tl_trx_resp_tax_t where TRANSACTION_ID = '" + transactionID + "'";
		dbConnection(db_query_rule);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			situs = res.getString(TaxLinkConstants.SITUS);
			VertexLogger.log("Situs in DB: " + situs);
		}

		db_query_rule = "select ATTRIBUTE2 from vtx_erp_tax_output_v where TRX_NUMBER='" + transactionNumber + "'";
		dbConnection(db_query_rule);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			attribute2 = res.getString(TaxLinkConstants.ATTRIBUTE2);
			VertexLogger.log("Attribute2 in DB: " + attribute2);
		}

		assert situs != null;
		assert attribute2 != null;
		if ( situs.contains(attribute2) )
		{
			VertexLogger.log("Attribute2 has substring value of Situs in database!!");
			substringFlag = true;
		}
		else
		{
			substringFlag = false;
		}
		closeDBConnection();
		return substringFlag;
	}

	/**
	 * Method to return an instance ID for 'ecog-dev3-us2' to filter the INV results
	 * from the taxlink database
	 *
	 * @return int
	 */
	public int db_getFusionInstanceIDFromDB( ) throws Exception
	{
		int instanceID = 0;

		dbConnection(TaxLinkDBQueries.DB_QUERY_INSTANCEID);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			instanceID = res.getInt("FUSION_INSTANCE_ID");
			VertexLogger.log("Fusion Instance ID : " + instanceID);
		}
		return instanceID;
	}

	/**
	 * Method to verify INV Pre calc rule added in taxlink
	 * if it is applied to the transactions - MAP function
	 * from the taxlink database
	 *
	 * @param fusionInstanceID
	 * @param jobID
	 *
	 * @return
	 */
	public boolean db_verifyMapPreCalcInvRuleInDB( int fusionInstanceID, int jobID ) throws Exception
	{
		boolean ruleFlag = false;

		db_query_rule = "select NUMBER1,LINE_NUMBER1 from VTX_TL_INV_REQ_DTL_T where fusion_instance_id =  '" +
						fusionInstanceID + "' and job_id = '" + jobID + "'";
		dbConnection(db_query_rule);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			sourceValue = res.getString("NUMBER1");
			VertexLogger.log(String.valueOf(sourceValue));
			targetValue = res.getString("LINE_NUMBER1");
			VertexLogger.log(targetValue);
		}
		if ( sourceValue.equals(targetValue) )
		{
			VertexLogger.log("Source has been mapped to Target in database!!");
			ruleFlag = true;
		}
		closeDBConnection();
		return ruleFlag;
	}

	/**
	 * Method to verify INV Pre calc rule added in taxlink
	 * if it is applied to the transactions - CONSTANT function
	 * from the taxlink database
	 *
	 * @param fusionInstanceID
	 * @param jobID
	 *
	 * @return
	 */
	public boolean db_verifyConstantPreCalcInvRuleInDB( int fusionInstanceID, int jobID ) throws Exception
	{
		boolean ruleFlag = false;

		db_query_rule = "select LINE_CHAR2 from VTX_TL_INV_REQ_DTL_T where fusion_instance_id = '" + fusionInstanceID +
						"'  and job_id = '" + jobID + "'";
		dbConnection(db_query_rule);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			targetValue = res.getString("LINE_CHAR2");
			VertexLogger.log(targetValue);
		}
		if ( targetValue.equals("INVTRX") )
		{
			VertexLogger.log("Target has been replaced by Constant in database!!");
			ruleFlag = true;
		}
		closeDBConnection();
		return ruleFlag;
	}

	/**
	 * Method to verify INV Pre calc rule added in taxlink
	 * if it is applied to the transactions - SUBSTRING function
	 * from the taxlink database
	 *
	 * @param fusionInstanceID
	 * @param jobID
	 *
	 * @return
	 */
	public boolean db_verifySubstringPreCalcInvRuleInDB( int fusionInstanceID, int jobID ) throws Exception
	{
		boolean ruleFlag;

		db_query_rule = "select Attribute1, LINE_CHAR1 from VTX_TL_INV_REQ_DTL_T where fusion_instance_id = '" +
						fusionInstanceID + "'  and job_id = '" + jobID + "'";
		dbConnection(db_query_rule);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			sourceValue = res.getString("Attribute1");
			VertexLogger.log(String.valueOf(sourceValue));
			targetValue = res.getString("LINE_CHAR1");
			VertexLogger.log(targetValue);
		}

		int res = isSubstring(sourceValue, targetValue);

		if ( res == -1 )
		{
			VertexLogger.log("Not present!!");
			ruleFlag = false;
		}
		else
		{
			VertexLogger.log("LINE_CHAR1 has substring value of Attribute1 in database at index: " + res);
			ruleFlag = true;
		}
		closeDBConnection();
		return ruleFlag;
	}

	/**
	 * Method to verify INV Journal output rule added in taxlink
	 * if it is applied to the transactions, function : Map
	 * from the taxlink database
	 *
	 * @param fusionInstanceID
	 * @param jobId
	 *
	 * @return boolean
	 *
	 * @throws Exception
	 */
	public boolean db_verifyMapJournalInvRuleInDB( int fusionInstanceID, int jobId ) throws Exception
	{
		boolean ruleFlag = false;

		db_query_rule_result1 = "select a.TRX_TYPE_NAME from VTX_TL_INV_REQ_HDR_T a where FUSION_INSTANCE_ID = '" +
								fusionInstanceID + "'  and JOB_ID = '" + jobId + "'";
		dbConnection(db_query_rule_result1);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			sourceValue = res.getString("TRX_TYPE_NAME");
			VertexLogger.log(sourceValue);
		}
		db_query_rule_result2 = "select ATTRIBUTE1  from VTX_INV_GL_OUTPUT_T where fusion_instance_id = '" +
								fusionInstanceID + "' and source_job_id = '" + jobId + "'";
		dbConnection(db_query_rule_result2);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			targetValue = res.getString("ATTRIBUTE1");
			VertexLogger.log(targetValue);
		}

		if ( sourceValue.equals(targetValue) )
		{
			VertexLogger.log("Source has been mapped to Target in database!!");
			ruleFlag = true;
		}
		closeDBConnection();
		return ruleFlag;
	}

	/**
	 * Method to verify INV Project output rule added in taxlink
	 * if it is applied to the transactions, function : MAP
	 * from the taxlink database
	 *
	 * @param fusionInstanceID
	 * @param jobId
	 *
	 * @return boolean
	 *
	 * @throws Exception
	 */
	public boolean db_verifyMapProjectInvRuleInDB( int fusionInstanceID, int jobId ) throws Exception
	{
		boolean ruleFlag = false;

		db_query_rule_result1 = "select a.attribute10 from VTX_TL_INV_REQ_DTL_T a where FUSION_INSTANCE_ID = '" +
								fusionInstanceID + "'  and JOB_ID = '" + jobId + "'";
		dbConnection(db_query_rule_result1);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			sourceValue = res.getString("attribute10");
			VertexLogger.log(sourceValue);
		}
		db_query_rule_result2 = "select ATTRIBUTE2 from VTX_INV_PA_OUTPUT_T where fusion_instance_id = '" +
								fusionInstanceID + "'  and source_job_id = '" + jobId + "'";
		dbConnection(db_query_rule_result2);

		res = stmt.executeQuery();
		while ( res.next() )
		{
			targetValue = res.getString("ATTRIBUTE2");
			VertexLogger.log(targetValue);
		}

		if ( sourceValue.equals(targetValue) )
		{
			VertexLogger.log("Source has been mapped to Target in database!!");
			ruleFlag = true;
		}
		closeDBConnection();
		return ruleFlag;
	}

	/**
	 * Method to put all the configuration in place before verifying inventory rules mapping.
	 * Once instance is cleaned after a test execution, it wipes out the configuration related to INV
	 * from taxlink. This script will add all related configurations on a new instance.
	 * Adds inventory polling job, related profile option changes to Taxlink.
	 *
	 * @return boolean
	 */
	public boolean db_configureInventoryRules( ) throws Exception
	{
		boolean configureFlag = false;
		dbConnection(TaxLinkDBQueries.DB_QUERY_INVRULES);
		res = stmt.executeQuery();
		while ( res.next() )
		{
			result = res.getString(1);
			VertexLogger.log(result);
		}
		if ( !result.isEmpty() || !result.isBlank() || !result.contains("Error"))
		{
			configureFlag = true;
		}
		closeDBConnection();
		return configureFlag;
	}
}
