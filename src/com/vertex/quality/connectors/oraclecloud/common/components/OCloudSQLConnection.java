package com.vertex.quality.connectors.oraclecloud.common.components;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.common.configuration.OracleSettings;
import com.vertex.quality.connectors.oraclecloud.common.configuration.DatabaseSettings;

import java.sql.*;

import static org.testng.Assert.fail;

/**
 *
 */
public class OCloudSQLConnection {

    private DatabaseSettings dbsettings = DatabaseSettings.getDatabaseSettingsInstance();

    public OCloudSQLConnection() { }

    /**
     * Supplies necessary arguments for establishing a connection to a
     * SQL database.
     * @return a Connection object
     */
    public Connection establishConnection() {
        Connection oCloudSQLConnection = null;

        try {
           VertexLogger.log("Establishing database connection with the following parameters:\n" +
                           dbsettings.connection_url+"\n"+dbsettings.username+"\n", VertexLogLevel.INFO);
           oCloudSQLConnection  = DriverManager.getConnection(dbsettings.connection_url, dbsettings.username,
                   dbsettings.password);
        } catch ( SQLException sqle ) {
            sqle.printStackTrace();
            fail("Test failed to establish connection to SQL server.");
        }

        return oCloudSQLConnection;
    }
}
