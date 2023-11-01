package com.vertex.quality.common.utils;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.pojos.OSeriesConfiguration;

import java.sql.*;

/**
 * Creates the connection to the SQL database and retrieve data from it
 *
 * @author hho
 */
public class SQLConnection
{
	private static final String CONNECTION_URL
		= "jdbc:sqlserver://connector-dev-sql.vertexconnectors.com;databaseName=AutomationData";
	private static final String DB_USERNAME = "testAutomation";
	private static final String DB_PASSWORD = "$testConnector01$";
	private static final String CONNECTORS_TABLE_NAME = "Connectors";
	private static final String ENVIRONMENT_CREDENTIALS_TABLE_NAME = "EnvironmentCredentials";
	private static final String ENVIRONMENT_INFORMATION_TABLE_NAME = "EnvironmentInformation";
	private static final String ENVIRONMENTS_TABLE_NAME = "Environments";
	private static final String OSERIES_CONFIGURATION_TABLE_NAME = "OSeriesConfiguration";

	private SQLConnection( ) throws SQLException
	{
	}

	/**
	 * Gets the main identifying information for a specific environment
	 *
	 * @param connector             which connector the environment is an instance of
	 * @param environment           which type of environment this environment is
	 * @param environmentDescriptor this environment's descriptor
	 *
	 * @return the main identifying information for this particular environment
	 */
	public static EnvironmentInformation getEnvironmentInformation( final DBConnectorNames connector,
		final DBEnvironmentNames environment, final DBEnvironmentDescriptors environmentDescriptor ) throws SQLException
	{
		EnvironmentInformation environmentInformation = null;
		String query = String.format(
			"SELECT * FROM %s WHERE connector_id = '%s' AND environment_id = '%s' AND environment_descriptor = '%s'",
			ENVIRONMENT_INFORMATION_TABLE_NAME, connector.getId(), environment.getId(),
			environmentDescriptor.getDescriptor());
		try ( Connection sqlConnection = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD) )
		{
			try ( Statement statement = sqlConnection.createStatement() ; ResultSet resultSet = statement.executeQuery(
				query) )
			{
				if ( resultSet.next() )
				{
					String environmentInformationIdValue = resultSet.getString("environment_information_id");
					String connectorIdValue = resultSet.getString("connector_id");
					String environmentIdValue = resultSet.getString("environment_id");
					String environmentDescriptorValue = resultSet.getString("environment_descriptor");
					String environmentUrlValue = resultSet.getString("environment_url");
					environmentInformation = EnvironmentInformation
						.builder()
						.environmentInformationId(environmentInformationIdValue)
						.connectorId(connectorIdValue)
						.environmentId(environmentIdValue)
						.environmentDescriptor(environmentDescriptorValue)
						.environmentUrl(environmentUrlValue)
						.build();
				} else {
					throw new IllegalStateException("unable to find environment information with " + query);
				}
			}
		}
		return environmentInformation;
	}

	/**
	 * Retrieves the credentials for a particular environment
	 *
	 * @param environmentInformation the information in the database that describes this particular environment
	 *
	 * @return the credentials for a particular environment
	 */
	public static EnvironmentCredentials getEnvironmentCredentials(
		final EnvironmentInformation environmentInformation ) throws SQLException
	{
		EnvironmentCredentials environmentCredentials = null;

		int connectorId = Integer.parseInt(environmentInformation.getConnectorId());
		int environmentId = Integer.parseInt(environmentInformation.getEnvironmentId());
		int environmentInformationId = Integer.parseInt(environmentInformation.getEnvironmentInformationId());

		String query = String.format(
			"SELECT * FROM %s WHERE connector_id = '%s' AND environment_id = '%s' AND environment_information_id = '%s'",
			ENVIRONMENT_CREDENTIALS_TABLE_NAME, connectorId, environmentId, environmentInformationId);
		try ( Connection sqlConnection = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD) )
		{
			try ( Statement statement = sqlConnection.createStatement() ; ResultSet resultSet = statement.executeQuery(
				query) )
			{
				if ( resultSet.next() )
				{
					String environmentCredentialIdValue = resultSet.getString("environment_credential_id");
					String connectorIdValue = resultSet.getString("connector_id");
					String environmentIdValue = resultSet.getString("environment_id");
					String environmentInformationIdValue = resultSet.getString("environment_information_id");
					String username = resultSet.getString("username");
					String password = resultSet.getString("password");
					String authenticationAnswer1 = resultSet.getString("authentication_answer_1");
					String authenticationAnswer2 = resultSet.getString("authentication_answer_2");
					String authenticationAnswer3 = resultSet.getString("authentication_answer_3");
					environmentCredentials = EnvironmentCredentials
						.builder()
						.environmentCredentialId(environmentCredentialIdValue)
						.connectorId(connectorIdValue)
						.environmentId(environmentIdValue)
						.environmentInformationId(environmentInformationIdValue)
						.username(username)
						.password(password)
						.authenticationAnswer1(authenticationAnswer1)
						.authenticationAnswer2(authenticationAnswer2)
						.authenticationAnswer3(authenticationAnswer3)
						.build();
				}
			}
		}

		return environmentCredentials;
	}

	public static DBConnectorNames getOSeriesDefaults()
	{
		String OSeriesValue = System.getProperty("oseries");
		if (OSeriesValue == null)
			OSeriesValue ="OSeries_onPrem9";
		return DBConnectorNames.valueOf(OSeriesValue);
	}

	/**
	 * Gets the OSeries Configuration values for a particular connector
	 *
	 * @param connector the connector
	 *
	 * @return the OSeries configuration values for that connector
	 */
	public static OSeriesConfiguration getOSeriesConfiguration( final DBConnectorNames connector ) throws SQLException
	{
		OSeriesConfiguration oseriesConfiguration = null;
		String query = String.format("SELECT * FROM %s WHERE connector_id = '%s'", OSERIES_CONFIGURATION_TABLE_NAME,
			connector.getId());
		try ( Connection sqlConnection = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD) )
		{
			try ( Statement statement = sqlConnection.createStatement() ; ResultSet resultSet = statement.executeQuery(
				query) )
			{
				if ( resultSet.next() )
				{
					String oseriesConfigurationId = resultSet.getString("oseries_configuration_id");
					String connectorIdValue = resultSet.getString("connector_id");
					String oseriesUsername = resultSet.getString("oseries_username");
					String oseriesPassword = resultSet.getString("oseries_password");
					String trustedId = resultSet.getString("trusted_id");
					String companyCode = resultSet.getString("company_code");
					String taxServiceUrl = resultSet.getString("tax_service_url");
					String addressServiceUrl = resultSet.getString("address_service_url");
					oseriesConfiguration = OSeriesConfiguration
						.builder()
						.oseriesConfigurationId(oseriesConfigurationId)
						.connectorId(connectorIdValue)
						.oseriesUsername(oseriesUsername)
						.oseriesPassword(oseriesPassword)
						.trustedId(trustedId)
						.companyCode(companyCode)
						.taxServiceUrl(taxServiceUrl)
						.addressServiceUrl(addressServiceUrl)
						.build();
				}
			}
		}
		return oseriesConfiguration;
	}

	/**
	 * gets the order number stored in the Ariba purchase orders table
	 *
	 * @return order number
	 *
	 * @throws SQLException
	 */
	public static String retrieveOrderNumber( String testName ) throws SQLException
	{
		String orderNumber = null;

		String query = String.format("SELECT * FROM AribaPurchaseOrders WHERE testname='" + testName + "'");
		try ( Connection sqlConnection = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD) )
		{
			try ( Statement statement = sqlConnection.createStatement() ; ResultSet resultSet = statement.executeQuery(
				query) )

			{
				if ( resultSet.next() )
				{
					orderNumber = resultSet.getString("ordernumber");
				}
			}
		}

		return orderNumber;
	}

	/**
	 * deletes a specific  rows from the table
	 *
	 * @throws SQLException
	 */
	public static void deleteOrderFromTable( String testName ) throws SQLException
	{
		String query = String.format("DELETE  FROM AribaPurchaseOrders WHERE testname='" + testName + "'");
		try ( Connection sqlConnection = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD) )
		{
			Statement statement = sqlConnection.createStatement();
			statement.executeQuery(query);
		}
	}

	/**
	 * inserts a new row into the ariba purchase orders table
	 *
	 * @param orderNumber
	 * @param timeStamp   of the order
	 *
	 * @throws SQLException
	 */
	public static void storeOrderNumber( String orderNumber, String timeStamp, String testName ) throws SQLException
	{
		String query = String.format(
			"INSERT INTO AribaPurchaseOrders (ordernumber,ordertime,testname) VALUES ('" + orderNumber + "','" +
			timeStamp + "','" + testName + "')");
		try ( Connection sqlConnection = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD) )
		{
			Statement statement = sqlConnection.createStatement();
			statement.executeQuery(query);
		}
	}

	/**
	 * stores the created invoice id on a given test name line
	 *
	 * @param invoiceId invoice id
	 * @param testName  name of the test we want to associate the invoice id to
	 *
	 * @throws SQLException
	 */
	public static void storeInvoiceId( String invoiceId, String testName ) throws SQLException
	{
		String query = String.format(
			"UPDATE AribaPurchaseOrders SET invoiceid='" + invoiceId + "' WHERE testname='" + testName + "'");
		try ( Connection sqlConnection = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD) )
		{
			Statement statement = sqlConnection.createStatement();
			statement.executeQuery(query);
		}
	}

	/**
	 * gets the invoice ID of a  stored Purchase Order in the Ariba purchase orders table
	 *
	 * @param testName name of the test case that created invoice ID
	 *
	 * @return Invoice ID
	 *
	 * @throws SQLException
	 */
	public static String retrieveInvoiceId( String testName ) throws SQLException
	{
		String invoiceId = null;

		String query = String.format("SELECT * FROM AribaPurchaseOrders WHERE testname='" + testName + "'");
		try ( Connection sqlConnection = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD) )
		{
			try ( Statement statement = sqlConnection.createStatement() ; ResultSet resultSet = statement.executeQuery(
				query) )

			{
				if ( resultSet.next() )
				{
					invoiceId = resultSet.getString("invoiceid");
				}
			}
		}

		return invoiceId;
	}
}
