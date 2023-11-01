package com.vertex.quality.connectors.taxlink.common.components;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.configuration.SQLConnection;
import com.vertex.quality.connectors.taxlink.common.configuration.TaxLinkDatabaseSettings;
import com.vertex.quality.connectors.taxlink.common.utils.TaxlinkAPIUtilities;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.testng.Assert.fail;

/**
 * Container for performing database-related actions.
 *
 * @author ajain
 */
public class TaxlinkDatabaseHandler
{
	Connection currConnection = null;
	ResultSet resultSet = null;

	private TaxlinkAPIUtilities utilities = new TaxlinkAPIUtilities();
	private TaxLinkDatabaseSettings dbSettings = TaxLinkDatabaseSettings.getDatabaseSettingsInstance();

	/**
	 * Establishes a connection to a database, and returns the established connection
	 *
	 * @return a connection object for a established connection.
	 */
	private Connection createSqlConnection( )
	{
		SQLConnection sqlConnection = new SQLConnection();
		currConnection = sqlConnection.establishConnection();
		return currConnection;
	}

	/**
	 * Check whether the documentId is processed or not in database
	 *
	 * @param documentId
	 *
	 * @return statusFlag value
	 */
	public boolean checkDocumentStatus( String documentId )
	{
		boolean statusFlag = false;
		createSqlConnection();
		String query = "select DOC_STATUS from VTX_ERP_DOC_INFO_T where DOC_ID='" + documentId + "'";

		String status = "";
		try
		{
			Thread.sleep(60000);

			resultSet = executeSqlQuery(query);

			while ( resultSet.next() )
			{
				status = resultSet.getString("DOC_STATUS");
			}
			VertexLogger.log("DOC_STATUS column value: " + status);

			if ( !status.equalsIgnoreCase("PROCESSED") )
			{
				fail("Transaction Errored Out, DOC_STATUS column in database has " + status + "status");
			}
			else
			{
				statusFlag = true;
				VertexLogger.log("Transaction successfully processed with doc Id " + documentId, VertexLogLevel.INFO);
			}
		}
		catch ( SQLException | InterruptedException sqle )
		{
			sqle.printStackTrace();
			fail("Test failed due to an interaction with the database results.");
		}
		finally
		{
			closeDBConnection();
		}
		return statusFlag;
	}

	/**
	 * Executes SQL query to database
	 *
	 * @param query sql query to execute in database as a String.
	 *
	 * @return the queried database information.
	 */
	public ResultSet executeSqlQuery( String query )
	{
		ResultSet result = null;
		try
		{
			Statement statement = currConnection.createStatement();
			result = statement.executeQuery(query);
		}
		catch ( SQLException sqlException )
		{
			sqlException.printStackTrace();
			fail("Problem encountered when executing SQL query.");
		}
		return result;
	}

	/**
	 * Executes SQL query to database and waits for provided wait time before fetching result set
	 *
	 * @param query sql query to execute in database as a String.
	 * @param waitForMilliSeconds number of milliseconds to wait before execute query
	 *
	 * @return the queried database information.
	 */
	public ResultSet executeSqlQueryAndWait( String query, long waitForMilliSeconds )
	{
		ResultSet result = null;
		try
		{
			Statement statement = currConnection.createStatement();
			Thread.sleep(waitForMilliSeconds);
			result = statement.executeQuery(query);
		}
		catch ( SQLException | InterruptedException sqlException )
		{
			sqlException.printStackTrace();
			fail("Problem encountered when executing SQL query.");
		}
		return result;
	}
	/**
	 * Method to close the Database connection
	 */
	public void closeDBConnection( )
	{
		try
		{
			resultSet.close();
			currConnection.close();
			VertexLogger.log("Connection closed successfully");
		}
		catch ( SQLException excep )
		{
			excep.printStackTrace();
			VertexLogger.log("Unable to close the database connection", VertexLogLevel.ERROR);
		}
	}

	/**
	 * Gets response data from database
	 *
	 * @param documentId
	 */
	public void generateResultFromResponse( String documentId, String resultFileName )
	{
		createSqlConnection();

		String query =
			"select TAX.TRX_NUMBER, TAX.TRX_LINE_NUMBER LINE_NUMBER, TAX.TAX_REGIME_CODE, TAX.TAX, TAX.TAX_RATE_CODE, TAX.TAX_JURISDICTION_CODE, TAX.TAX_AMT, TAX.TAX_RATE, TAX.TAXABLE_AMT,\n" +
			"TAX.SELF_ASSESSED_FLAG, TAX.PROVIDER_REC_RATE, TAX.ATTRIBUTE11, TAX.ATTRIBUTE12, TAX.ATTRIBUTE13, TAX.ATTRIBUTE14, TAX.ATTRIBUTE15, TAX.TRX_DATE\n" +
			"from vtx_tax_calc_job_dtl_t jd, vtx_erp_trx_input_t trx, vtx_erp_tax_output_t tax\n" +
			"where jd.job_id = trx.job_id\n" + "and trx.job_id = tax.job_id\n" +
			"and jd.fusion_instance_id = trx.fusion_instance_id\n" +
			"and trx.fusion_instance_id = tax.fusion_instance_id\n" +
			"and trx.event_class_mapping_id = tax.event_class_mapping_id\n" + "and trx.trx_id = tax.trx_id\n" +
			"and trx.trx_line_id = tax.trx_line_id\n" + "and tax.TRX_NUMBER is NOT NULL\n" +
			"and jd.request_trx_ucm_doc_id = '" + documentId + "'";

		VertexLogger.log("Sending Query " + query);

		resultSet = executeSqlQueryAndWait(query,300000);

		processResultsetData(resultSet,resultFileName);
	}

	/**
	 * Processes Result Set Data and generates Result Csv file used for comparison
	 *
	 * @param resultSet
	 * @param resultFileName
	 */
	private void processResultsetData( ResultSet resultSet, String resultFileName )
	{
		int rowNum = 1;
		boolean onlyDataFlag = false;
		ArrayList dataAsArrList = null;

		Map<String, String> columnDataMap = new LinkedHashMap<>();
		try
		{
			Thread.sleep(60000); //Need to provide wait to give enough time to process the data in database
			if ( resultSet != null )
			{
				while ( resultSet.next() )
				{
					rowNum = resultSet.getRow();
					if ( rowNum == 1 )
					{
						addDataInMap(resultSet, columnDataMap);
						onlyDataFlag = false;// Setting this flag as false as need to add column name and value in csv
					}
					else
					{
						onlyDataFlag = true; // Setting this flag as true as we need to add only column data's in csv
						addDataInMap(resultSet, columnDataMap);
					}
					dataAsArrList = utilities.convertMapToList(columnDataMap, rowNum);
					utilities.addDataIntoCsvFile(dataAsArrList, onlyDataFlag, resultFileName);
				}
			}
		}
		catch ( SQLException | InterruptedException | IOException sqle )
		{
			VertexLogger.log("Fetching data from database failed " + sqle.getMessage());
			fail("Test failed due to an interaction with the database results.");
		}
		finally
		{
			closeDBConnection();
		}
	}

	/**
	 * Iterates over the resultset data
	 * and adds respective data in key value format in columnDataMap
	 *
	 * @param resultSet
	 * @param columnDataMap
	 */
	private void addDataInMap( ResultSet resultSet, Map<String, String> columnDataMap ) throws SQLException
	{
		String columnName;
		String valueInColumn;
		String character_N = "N";
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		for ( int i = 1 ; i <= resultSetMetaData.getColumnCount() ; i++ )
		{
			columnName = resultSetMetaData.getColumnLabel(i);
			valueInColumn = resultSet.getString(columnName);
			if ( valueInColumn == null )
			{
				columnDataMap.put(columnName.trim(), character_N.trim());
			}
			else
			{
				columnDataMap.put(columnName.trim(), valueInColumn.trim());
			}
		}
	}
}