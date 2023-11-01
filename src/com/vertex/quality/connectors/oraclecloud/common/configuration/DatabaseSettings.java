package com.vertex.quality.connectors.oraclecloud.common.configuration;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.common.configuration.enums.*;

/**
 * All Oracle and Taxlink related database settings.
 *
 * @author msalomone
 */
public class DatabaseSettings {
    private static DatabaseSettings instance = null;

    public String database;
    private String original_url;
    public String connection_url;
    public String username;
    public String password;

    private DatabaseSettings() {

    }

    /**
     * Confines the class into a singleton, and returns the only
     * instance.
     * @return instance - the only instance of DatabaseSettings
     */
    public static DatabaseSettings getDatabaseSettingsInstance()
    {
        if (instance == null)
            instance = new DatabaseSettings();

        return instance;
    }

    /**
     * Assign default values to all Oracle/Taxlink related
     * database settings.
     */
    public void initializeDbSettings() {
        VertexLogger.log("Initializing database settings.");
        database = updateDatabase(DatabaseNames.VTX9_JRNL);
        original_url = updateConUrl(DatabaseEnvironments.KOP16);
        connection_url = String.format(original_url, database);
        username = updateUsername(DatabaseCredentials.VERTEX);
        password = updatePassword(DatabaseCredentials.VERTEX);
    }

    /**
     * Updates a setting(s) based on enum value.
     */
    public void update(DatabaseNames dbNames, DatabaseEnvironments newEnvironment, DatabaseCredentials newCredential)
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
     * @param dbName O Series or Taxlink database represented as an Enum.
     * @param dbName O Series or Taxlink server represented as an Enum.
     */
    public void setDatabase(DatabaseNames dbName)
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
    public void setCredentials(DatabaseCredentials credential)
    {
        VertexLogger.log("Updating database login credentials.");
        username = updateUsername(credential);
        password = updatePassword(credential);
    }


    /**
     * Returns the target database based on the descriptor provided.
     *
     * @param dbName O Series or Taxlink database represented as an Enum.
     * @return databaseName
     */
    private String updateDatabase(DatabaseNames dbName) {
        String databaseName = "";
        switch(dbName) {
            case VTX9_JRNL:
                databaseName = "VTX9_JRNL";
                break;
            case XXVERTEX:
                databaseName = "XXVERTEX";
                break;
            default:
                databaseName = "VTX9_JRNL";
        }
        return databaseName;
    }

    /**
     * Returns the jdbc connection URL based on the descriptor provided.
     *
     * @param env O Series database represented as an Enum.
     * @return environment
     */
    private String updateConUrl(DatabaseEnvironments env) {
        String environment = "";
        switch(env) {
            case KOP16:
				environment = "jdbc:sqlserver://ope-187-01.cst.vertexinc.com;instance=CLOUDTL;databaseName=%s";
                break;
            case KOP17:
                environment = "jdbc:sqlserver://KOPAS0017;instance=CLOUDTL;databaseName=%s";
                break;
            case BERAS0411:
                environment = "jdbc:sqlserver://BERAS0411\\CLOUDTL;databaseName=%s";
                break;
			case PHLDAPS0110:
				environment = "jdbc:sqlserver://PHLDAPS0110;instance=CLOUDTL;databaseName=%s";
				break;
            default:
                environment = "jdbc:sqlserver://KOPAS0016;instance=CLOUDTL;databaseName=%s";
        }
        return environment;
    }

    /**
     * Returns the username associated with the descriptor.
     *
     * @param user Pre-existing user credential in any Oracle Cloud team
     *            owned database represented as an Enum.
     * @return username
     */
    private String updateUsername(DatabaseCredentials user) {
        String username = "";
        switch(user) {
            case VERTEX:
                username = "VERTEX";
                break;
            case XXVERTEX:
                username = "XXVERTEX";
                break;
            case XXVERTEX2:
                username = "XXVERTEX";
                break;
            default:
                username = "VERTEX";
        }
        return username;
    }

    /**
     * Returns the password associated with the descriptor.
     *
     * @param user Pre-existing user credential in any Oracle machine represented as an Enum
     * @return password
     */
    private String updatePassword(DatabaseCredentials user) {
        String password = "";
        switch(user) {
            case VERTEX:
                password = "Vertex2016!";
                break;
            case XXVERTEX:
                password = "xxvertex";
                break;
            case XXVERTEX2:
                password = "vertex123";
                break;
            default:
                password = "Vertex2016!";
        }
        return password;
    }
}
