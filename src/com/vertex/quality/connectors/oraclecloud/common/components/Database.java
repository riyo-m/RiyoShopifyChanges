package com.vertex.quality.connectors.oraclecloud.common.components;

import java.sql.*;

import static org.testng.Assert.fail;

/**
 * Representation of a database of any type. Currently supported types:
 *      1) SQL
 *
 * @author msalomone
 */
public class Database {

    public Connection connection;

    public Database(Connection dbConnection) {
        this.connection = dbConnection;
    }

    /**
     *
     * @param query sql query to execute in database as a String.
     * @return the queried database information.
     */
    public ResultSet executeSqlQuery(String query) {
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement();
            result = statement.executeQuery(query);
        } catch ( SQLException sqle ) {
            sqle.printStackTrace();
            fail("Problem encountered when executing SQL query.");
        }
        return result;
    }

    /**
     *
     * @param query sql query to execute in database as a String.
     * @return the queried database information.
     */
    public void executeSqlUpdateQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch ( SQLException sqle ) {
            sqle.printStackTrace();
            fail("Problem encountered when executing SQL query.");
        }
    }
}
