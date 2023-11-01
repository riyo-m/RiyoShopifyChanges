package com.vertex.quality.connectors.taxlink.common.configuration;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.testng.Assert.fail;

/**
 * Contains methods to establish database connection
 */
public class SQLConnection
{

	private TaxLinkDatabaseSettings dbsettings = TaxLinkDatabaseSettings.getDatabaseSettingsInstance();

	public SQLConnection( ) { }

	/**
	 * Establishes a connection to a SQL database.
	 *
	 * @return a Connection object
	 */
	public Connection establishConnection( )
	{
		Connection sqlConnection = null;

		try
		{
			VertexLogger.log(
				"Establishing database connection with the following parameters:\n" + dbsettings.connection_url + "\n" +
				dbsettings.username + "\n", VertexLogLevel.INFO);
			sqlConnection = DriverManager.getConnection(dbsettings.connection_url, dbsettings.username,
				dbsettings.password);
		}
		catch ( SQLException sqle )
		{
			sqle.printStackTrace();
			fail("Test failed to establish connection to SQL server.");
		}

		return sqlConnection;
	}
}
