package com.vertex.quality.connectors.taxlink.common.configuration;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.api.enums.TaxlinkDatabaseCredentials;
import com.vertex.quality.connectors.taxlink.api.enums.TaxlinkDatabaseEnvironments;
import com.vertex.quality.connectors.taxlink.api.enums.TaxlinkDatabaseNames;

/**
 * All Taxlink related database settings.
 *
 * @author ajain
 */
public class TaxLinkDatabaseSettings
{
	private static TaxLinkDatabaseSettings instance = null;

	public String database;
	private String original_url;
	public String connection_url;
	public String username;
	public String password;

	private TaxLinkDatabaseSettings( )
	{

	}

	/**
	 * Confines the class into a singleton, and returns the only
	 * instance.
	 *
	 * @return instance - the only instance of DatabaseSettings
	 */
	public static TaxLinkDatabaseSettings getDatabaseSettingsInstance( )
	{
		if ( instance == null )
		{
			instance = new TaxLinkDatabaseSettings();
		}

		return instance;
	}

	/**
	 * Assign default values to all Taxlink related database settings.
	 */
	public void initializeDbSettings( )
	{
		VertexLogger.log("Initializing database settings.");
		database = updateDatabase(TaxlinkDatabaseNames.XXVERTEX);
		original_url = updateConUrl(TaxlinkDatabaseEnvironments.OPE187);
		connection_url = String.format(original_url, database);
		username = updateUsername(TaxlinkDatabaseCredentials.XXVERTEX);
		password = updatePassword(TaxlinkDatabaseCredentials.XXVERTEX);
	}

	/**
	 * Updates a setting(s) based on enum value.
	 *
	 * @param dbNames        Taxlink databaseNames
	 * @param newEnvironment Taxlink Database Enviornments
	 * @param newCredential  Taxlink Database Credentials
	 */
	public void update( TaxlinkDatabaseNames dbNames, TaxlinkDatabaseEnvironments newEnvironment,
		TaxlinkDatabaseCredentials newCredential )
	{
		VertexLogger.log("Updating database settings.");
		database = updateDatabase(dbNames);
		connection_url = updateConUrl(newEnvironment);
		username = updateUsername(newCredential);
		password = updatePassword(newCredential);
	}

	/**
	 * Updates the database setting value. This value gets appended
	 * to the connection_url value also supplied.
	 *
	 * @param dbName Taxlink database represented as an Enum.
	 */
	public void setDatabase( TaxlinkDatabaseNames dbName )
	{
		VertexLogger.log("Updating target database.");
		database = updateDatabase(dbName);
		connection_url = String.format(original_url, database);
	}

	/**
	 * Updates the credentials used to log into the SQL server.
	 *
	 * @param credential database credential represented as an Enum.
	 */
	public void setCredentials( TaxlinkDatabaseCredentials credential )
	{
		VertexLogger.log("Updating database login credentials.");
		username = updateUsername(credential);
		password = updatePassword(credential);
	}

	/**
	 * Returns the target database based on the descriptor provided.
	 *
	 * @param dbName Taxlink database represented as an Enum.
	 *
	 * @return databaseName
	 */
	private String updateDatabase( TaxlinkDatabaseNames dbName )
	{
		String databaseName = "";
		switch ( dbName )
		{
			case XXVERTEX:
				databaseName = "XXVERTEX";
				break;
			default:
				databaseName = "XXVERTEX";
		}
		return databaseName;
	}

	/**
	 * Returns the jdbc connection URL based on the descriptor provided.
	 *
	 * @param env database represented as an Enum.
	 *
	 * @return environment
	 */
	private String updateConUrl( TaxlinkDatabaseEnvironments env )
	{
		String environment = "";
		switch ( env )
		{
			case OPE187:
				environment = "jdbc:sqlserver://ope-187-01.cst.vertexinc.com;instance=CLOUDTL;databaseName=%s";
				break;
			case PHLDAPS0110:
				environment = "jdbc:sqlserver://PHLDAPS0110;instance=CLOUDTL;databaseName=%s";
				break;
			default:
				environment = "jdbc:sqlserver://ope-187-01.cst.vertexinc.com;instance=CLOUDTL;databaseName=%s";
		}
		return environment;
	}

	/**
	 * Returns the username associated with the descriptor.
	 *
	 * @param user Pre-existing user credential for database represented as an Enum.
	 *
	 * @return username
	 */
	private String updateUsername( TaxlinkDatabaseCredentials user )
	{
		String username = "";
		switch ( user )
		{
			case XXVERTEX:
				username = "XXVERTEX";
				break;
			default:
				username = "XXVERTEX";
		}
		return username;
	}

	/**
	 * Returns the password associated with the descriptor.
	 *
	 * @param user Pre-existing user credential for database represented as an Enum
	 *
	 * @return password
	 */
	private String updatePassword( TaxlinkDatabaseCredentials user )
	{
		String password = "";
		switch ( user )
		{
			case XXVERTEX:
				password = "xxvertex";
				break;
			default:
				password = "xxvertex";
		}
		return password;
	}
}
