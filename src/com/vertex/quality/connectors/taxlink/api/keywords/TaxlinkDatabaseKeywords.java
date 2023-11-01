package com.vertex.quality.connectors.taxlink.api.keywords;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.configuration.SQLConnection;
import com.vertex.quality.connectors.taxlink.common.configuration.TaxLinkDatabaseSettings;
import com.vertex.quality.connectors.taxlink.common.utils.TaxlinkAPIUtilities;

import java.sql.*;
import java.util.*;

import static org.testng.Assert.fail;

/**
 * Keywords for performing database-related actions.
 *
 * @author ajain
 */
public class TaxlinkDatabaseKeywords
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
		resultSet = executeSqlQuery(query);

		String status = "";
		try
		{
			while ( resultSet.next() )
			{
				status = resultSet.getString("DOC_STATUS");
			}
			VertexLogger.log("DOC_STATUS column value: " + status);

			if ( !status.equalsIgnoreCase("PROCESSED") )
			{
				fail("Transaction Errored Out, DOC_STATUS column in database is  " + status);
			}
			else
			{
				statusFlag = true;
				VertexLogger.log("Transaction successfully processed with doc Id " + documentId, VertexLogLevel.INFO);
			}
		}
		catch ( SQLException sqle )
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
	 *
	 * @return columnDataMap column's data
	 */
	public Map<String, String> getRespDataFromDatabase( String documentId )
	{
		String columnName = "";
		String valueInColumn = "";
		int rowNum = 1;
		Map<String, String> columnDataMap = new HashMap<>();

		createSqlConnection();

		String query = "select tax.*\n" +
					   "from vtx_tax_calc_job_dtl_t jd, vtx_erp_trx_input_t trx, vtx_erp_tax_output_t tax\n" +
					   "where jd.job_id = trx.job_id\n" + "and trx.job_id = tax.job_id\n" +
					   "and jd.fusion_instance_id = trx.fusion_instance_id\n" +
					   "and trx.fusion_instance_id = tax.fusion_instance_id\n" +
					   "and trx.event_class_mapping_id = tax.event_class_mapping_id\n" +
					   "and jd.request_trx_ucm_doc_id = ' " + documentId + " '";

		VertexLogger.log("Sending Query " + query);

		resultSet = executeSqlQuery(query);

		try
		{
			Thread.sleep(600000); //Need to provide wait to give enough time to process the data in database
			if ( resultSet != null )
			{

				while ( resultSet.next() )
				{
					ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
					if ( rowNum == 1 )
					{ // Logic: to add 1st row with column Name
						for ( int i = 1 ; i <= resultSetMetaData.getColumnCount() ; i++ )
						{
							columnName = resultSetMetaData.getColumnLabel(i);
							valueInColumn = resultSet.getString(columnName);

							if ( valueInColumn != null && !valueInColumn.isEmpty() )
							{
								columnDataMap.put(columnName, valueInColumn);
								VertexLogger.log("ColumnName: " + columnName + " valueInColumn: " + valueInColumn);
							}
						}
					}
				}
			}
		}
		catch ( SQLException | InterruptedException sqle )
		{
			fail("Test failed due to an interaction with the database results.");
			VertexLogger.log("Fetching data from database failed " + sqle.getMessage());
		}
		finally
		{
			closeDBConnection();
		}
		return columnDataMap;
	}
}