package com.vertex.quality.connectors.shopify.api.util;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.shopify.common.ShopifyDataDB;

import java.sql.*;

/**
 * Shopify DB Utils - used to verify data in the Shopify DB
 *
 * @author Shivam.Soni
 */
public class ShopifyDBUtils {

    /**
     * Connects to the Database
     *
     * @param dbURL  URL of the DB
     * @param dbUser Username of DB
     * @param dbPwd  Password of DB
     * @return object of Connection
     */
    public static Connection connectToDB(String dbURL, String dbUser, String dbPwd) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbURL, dbUser, dbPwd);
        } catch (SQLException sql) {
            VertexLogger.log("Check VPN connection: for all Shopify DB vpn.vtxdev.net is must connected");
            sql.printStackTrace();
        }
        return connection;
    }

    /**
     * Connects to DB with default choice
     *
     * @return object of Connection
     */
    public static Connection connectToDB() {
        return connectToDB(ShopifyDataDB.DbUrls.SECONDARY_NODE.text, ShopifyDataDB.DbCredentials.USERNAME.text, ShopifyDataDB.DbCredentials.PASSWORD.text);
    }

    /**
     * Prepares the query for to get shop's access token
     *
     * @param connection object of Connection
     * @param shopURL    Shop URL which access token to be fetched from the DB
     * @return prepared statement
     */
    public static PreparedStatement createQueryForAccessToken(Connection connection, String shopURL) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    ShopifyDataDB.DbQueries.GET_SHOP_ACCESS_TOKEN.text.replace("<<text_replace>>", shopURL));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Prepares the query for to get shop's access token
     *
     * @param connection object of Connection
     * @param shopURL    Shop URL which access token to be fetched from the DB
     * @return prepared statement
     */
    public static PreparedStatement createQueryForToCheckAppEntryAgainstStoreInDB(Connection connection, String shopURL) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    ShopifyDataDB.DbQueries.CHECK_APP_INSTALLED.text.replace("<<text_replace>>", shopURL));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Prepares the query for to get App's Client ID
     *
     * @param connection object of Connection
     * @param shopURL    Shop URL which access token to be fetched from the DB
     * @return prepared statement
     */
    public static PreparedStatement createQueryForClientID(Connection connection, String shopURL) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    ShopifyDataDB.DbQueries.GET_CLIENT_ID.text.replace("<<text_replace>>", shopURL));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Executes the DB Query
     *
     * @param ps object of PreparedStatement
     * @return result set fetched from the DB.
     */
    public static ResultSet executeQuery(PreparedStatement ps) {
        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * Closes the DB Connection
     *
     * @param connection object of connection
     */
    public static void closeDBConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns top 1 result's value of a column
     *
     * @param rs     object of ResultSet
     * @param column value of column which data to be fetched
     * @return value in the String format
     */
    public static String getSingleDataFromResultSet(ResultSet rs, String column) {
        String data = null;
        try {
            while (rs.next()) {
                rs.getString(1);
                data = rs.getString(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}
