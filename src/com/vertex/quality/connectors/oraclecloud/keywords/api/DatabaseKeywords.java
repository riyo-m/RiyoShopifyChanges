package com.vertex.quality.connectors.oraclecloud.keywords.api;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.common.components.Database;
import com.vertex.quality.connectors.oraclecloud.common.components.OCloudSQLConnection;
import com.vertex.quality.connectors.oraclecloud.common.configuration.enums.DatabaseCredentials;
import com.vertex.quality.connectors.oraclecloud.common.configuration.enums.OracleEnvironments;
import com.vertex.quality.connectors.oraclecloud.common.utils.OracleUtilities;
import com.vertex.quality.connectors.oraclecloud.common.configuration.DatabaseSettings;
import com.vertex.quality.connectors.oraclecloud.common.configuration.OracleSettings;
import com.vertex.quality.connectors.oraclecloud.common.configuration.enums.DatabaseNames;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudProfileOptions;


import java.sql.*;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.fail;
import static com.vertex.quality.connectors.oraclecloud.common.utils.Template.transactionNumber;
import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;

/**
 * Keywords for performing database-related actions.
 *
 * @author msalomone
 */
public class DatabaseKeywords {

    private OracleUtilities utilities = new OracleUtilities();
    private DatabaseSettings dbSettings = DatabaseSettings.getDatabaseSettingsInstance();
    private OracleSettings oracleSettings = OracleSettings.getOracleSettingsInstance();

    private String todaysDateyyyyMMdd = utilities.getTodaysDate("yyyyMMdd");
    private String todaysDatedd_MMM_yyyy = utilities.getTodaysDate("dd-MMM-yyyy");
    private String fusionInstanceId = getFusionInstanceId();

    /**
     * Verify the new attributes for AP transactions
     *
     * @param invoiceNumber transaction number of the new transaction
     */
    public void apNewAttributeVerification(String invoiceNumber)
    {
        dbSettings.setDatabase(DatabaseNames.XXVERTEX);
        dbSettings.setCredentials(DatabaseCredentials.XXVERTEX);
        Database sqlDatabase = createSqlConnection();
        String query = "select vendor_type from [XXVERTEX].[dbo].[VTX_TL_TRX_REQ_DTL_T] " +
                "where request_header_id in " +
                "(select request_header_id from [XXVERTEX].[dbo].[VTX_TL_TRX_REQ_HDR_T] " +
                "where trx_number = '"+invoiceNumber+"')";

        ResultSet results = sqlDatabase.executeSqlQuery(query);
        String vendorType = "";
        try {
            while (results.next()) {
                vendorType = results.getString("vendor_type");
            }
            if (!vendorType.equals("SUPPLIER") == true)
                fail("Oracle didn't return the Vendor Type as expected. " +vendorType+ " was returned instead");
            else
                VertexLogger.log("Oracle returned the Vendor Type as expected. New Attribute verified for AR" +
                        "Single Transaction", VertexLogLevel.INFO);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            fail("Test failed due to an interaction with the database results.");
        }
    }

    /**
     * Verify the new attributes for AR transactions
     *
     * @param transactionNumber transaction number of the new transaction
     */
    public void arNewAttributeVerification(String transactionNumber)
    {
        dbSettings.setDatabase(DatabaseNames.XXVERTEX);
        dbSettings.setCredentials(DatabaseCredentials.XXVERTEX);
        Database sqlDatabase = createSqlConnection();
        String query = "select customer_po_number from [XXVERTEX].[dbo].[VTX_TL_TRX_REQ_DTL_T] " +
                "where request_header_id in " +
                "(select request_header_id from [XXVERTEX].[dbo].[VTX_TL_TRX_REQ_HDR_T] " +
                "where trx_number = '"+transactionNumber+"')";

        ResultSet results = sqlDatabase.executeSqlQuery(query);
        String poNumber = "";
        try {
            while (results.next()) {
                poNumber = results.getString("customer_po_number");
            }
            if (!poNumber.equals("TestPoNumber123") == true)
                fail("Oracle didn't return the PO Number as expected");
            else
                VertexLogger.log("Oracle returned the PO Number as expected. New Attribute verified for AR" +
                        "Single Transaction", VertexLogLevel.INFO);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            fail("Test failed due to an interaction with the database results.");
        }
    }

    /**
     * Get the name of the csv file to be retrieved
     *
     */
    public String getArTrxFileName() {
        dbSettings.setDatabase(DatabaseNames.XXVERTEX);
        dbSettings.setCredentials(DatabaseCredentials.XXVERTEX);
        Database sqlDatabase = createSqlConnection();
        String query = "select request_trx_file_name from vtx_tax_calc_job_dtl_t " +
                "where job_id = (select distinct job_id from vtx_erp_trx_input_t " +
                "where trx_number = 'ZV062221A01')";

        ResultSet results = sqlDatabase.executeSqlQuery(query);
        String trxFileName = "";
        try {
            while (results.next()) {
                trxFileName = results.getString("request_trx_file_name");
            }
            if (trxFileName.equals("") == true)
                fail("");
            else
                VertexLogger.log("Oracle returned the Customer PO Number as expected. New Attribute verified for"
                        + " Requisition Single Transaction", VertexLogLevel.INFO);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            fail("Test failed due to an interaction with the database results.");
        }
        return trxFileName;
    }


    /**
     * Verify the new attributes for Purchase Orders
     *
     * @param transaction_id Transaction id of the new transaction.
     */
    public void omNewAttributeVerification(String transaction_id)
    {
        dbSettings.setDatabase(DatabaseNames.XXVERTEX);
        dbSettings.setCredentials(DatabaseCredentials.XXVERTEX);
        Database sqlDatabase = createSqlConnection();
        String query = "select customer_po_number from [XXVERTEX].[dbo].[VTX_TL_TRX_REQ_DTL_T] " +
                "where request_header_id in " +
                "(select request_header_id from [XXVERTEX].[dbo].[VTX_TL_TRX_REQ_HDR_T] " +
                "where transaction_id = '"+transaction_id+"')";

        ResultSet results = sqlDatabase.executeSqlQuery(query);
        String poNumber = "";
        try {
            while (results.next()) {
                poNumber = results.getString("customer_po_number");
            }
            if (!poNumber.equals(transaction_id) == true)
                fail("Oracle didn't return the PO Number as expected");
            else
                VertexLogger.log("Oracle returned the Customer PO Number as expected. New Attribute verified for"
                        + " Purchase Order Single Transaction", VertexLogLevel.INFO);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            fail("Test failed due to an interaction with the database results.");
        }
    }

    /**
     * Verify the new attributes for Purchase Orders
     *
     * @param pOrderNumber purchase order number of the new transaction.
     */
    public void poNewAttributeVerification(String pOrderNumber)
    {
        dbSettings.setDatabase(DatabaseNames.XXVERTEX);
        dbSettings.setCredentials(DatabaseCredentials.XXVERTEX);
        Database sqlDatabase = createSqlConnection();
        String query = "select customer_po_number from [XXVERTEX].[dbo].[VTX_TL_TRX_REQ_DTL_T] " +
                "where request_header_id in " +
                "(select request_header_id from [XXVERTEX].[dbo].[VTX_TL_TRX_REQ_HDR_T] " +
                "where trx_number = '"+pOrderNumber+"')";

        ResultSet results = sqlDatabase.executeSqlQuery(query);
        String poNumber = "";
        try {
            while (results.next()) {
                poNumber = results.getString("customer_po_number");
            }
            if (!poNumber.equals(pOrderNumber) == true)
                fail("Oracle didn't return the PO Number as expected");
            else
                VertexLogger.log("Oracle returned the Customer PO Number as expected. New Attribute verified for"
                        + " Purchase Order Single Transaction", VertexLogLevel.INFO);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            fail("Test failed due to an interaction with the database results.");
        }
    }

    /**
     * Verify the new attributes for Requisitions
     *
     * @param reqNumber requisition number of the new transaction
     */
    public void reqNewAttributeVerification(String reqNumber)
    {
        dbSettings.setDatabase(DatabaseNames.XXVERTEX);
        dbSettings.setCredentials(DatabaseCredentials.XXVERTEX);
        Database sqlDatabase = createSqlConnection();
        String query = "select customer_po_number from [XXVERTEX].[dbo].[VTX_TL_TRX_REQ_DTL_T] " +
                "where request_header_id in " +
                "(select request_header_id from [XXVERTEX].[dbo].[VTX_TL_TRX_REQ_HDR_T] " +
                "where trx_number = '"+reqNumber+"')";

        ResultSet results = sqlDatabase.executeSqlQuery(query);
        String poNumber = "";
        try {
            while (results.next()) {
                poNumber = results.getString("customer_po_number");
            }
            if (!poNumber.equals(reqNumber) == true)
                fail("Oracle didn't return the PO Number as expected");
            else
                VertexLogger.log("Oracle returned the Customer PO Number as expected. New Attribute verified for"
                        + " Requisition Single Transaction", VertexLogLevel.INFO);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            fail("Test failed due to an interaction with the database results.");
        }
    }
    
    /**
     * Verifies an invoice previously present in the Taxlink
     * database no longer exists within the VTX9_JRNL table.
     */
    public void checkInvoiceDeleted(String transId) {
        String syncCode = "%AR%";
        dbSettings.setDatabase(DatabaseNames.VTX9_JRNL);
        dbSettings.setCredentials(DatabaseCredentials.VERTEX);
        Database sqlDatabase = createSqlConnection();

        String query = String.format("select li.userTransIdCode, sum(isnull(tof.dt1TaxAmt,0)+isnull(tof.dt2TaxAmt,0)+"+
                "isnull(tof.dt3TaxAmt,0))\n" +
                " TotalTax\n" +
                "  from lineitem li,\n" +
                "       lineitemtaxovrflw tof\n" +
                " where li.userTransIdCode like '%s' and li.transprocdate = %s and transSyncIdCode like '%s'\n"+
                "  and li.transStatusTypeId = 1 and li.sourceid = 12\n" +
                "  and li.lineitemid = tof.lineItemId\n" +
                " group by li.userTransIdCode\n" +
                " order by li.userTransIdCode", transId, todaysDateyyyyMMdd, syncCode);
        ResultSet results = sqlDatabase.executeSqlQuery(query);

        try {
            assertFalse(results.next(), "Deleted invoice record still present in VTX9_JRNL");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            fail("Test failed due to an interaction with the database results.");
        }
    }

    /**
     * Verifies an invoice is present in the Taxlink
     * database TX9_JRNL table after creation in Oracle ERP.
     */
    public void checkInvoiceIsPresent(String transId) {
        String syncCode = "%AR%";
        dbSettings.setDatabase(DatabaseNames.VTX9_JRNL);
        dbSettings.setCredentials(DatabaseCredentials.VERTEX);
        Database sqlDatabase = createSqlConnection();

        String query = String.format("select li.userTransIdCode, sum(isnull(tof.dt1TaxAmt,0)+isnull(tof.dt2TaxAmt,0)+"+
                "isnull(tof.dt3TaxAmt,0))\n" +
                " TotalTax\n" +
                "  from lineitem li,\n" +
                "       lineitemtaxovrflw tof\n" +
                " where li.userTransIdCode like '%s' and li.transprocdate = %s and transSyncIdCode like '%s'\n"+
                "  and li.transStatusTypeId = 1 and li.sourceid = 12\n" +
                "  and li.lineitemid = tof.lineItemId\n" +
                " group by li.userTransIdCode\n" +
                " order by li.userTransIdCode", transId, todaysDateyyyyMMdd, syncCode);
        ResultSet results = sqlDatabase.executeSqlQuery(query);

        try {
            assertTrue(results.next(), "Created invoice not present in VTX9_JRNL before deletion step in test case.");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            fail("Test failed due to an interaction with the database results.");
        }
    }

    /**
     * Confirm invoices exist in the O Series VTX9_JRNL database.
     * Can be used for ARBILLINGSYNC conifrmation.
     */
    public void confirmInvoicesInOsrJournal() {
        dbSettings.setDatabase(DatabaseNames.VTX9_JRNL);
        dbSettings.setCredentials(DatabaseCredentials.VERTEX);
        Database sqlDatabase = createSqlConnection();
        String query = String.format("select r.fieldValues, l.* from VTX9_JRNL.dbo.lineitem l LEFT OUTER JOIN " +
                "VTX9_JRNL.dbo.LineItemReturnsField r " +
                "ON l.lineitemid  = r.lineitemid" +
                "where l.transprocdate = %s" +
                "and l.userTransIdCode in ('229022','229023')" +
                "order by lineitemid", todaysDateyyyyMMdd);
        ResultSet results = sqlDatabase.executeSqlQuery(query);

        try {
            while (results.next()) {
                results.first();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            fail("Test failed due to an interaction with the database results.");
        }
    }

    /**
     * Flip the Profile option value for Line Level Testing
     *
     * @param profileOptionValue its should be set to Y to enable Level Testing.
     *                           its should be N otherwise.
     */
    public void flipUSLineLevelSetting(String profileOptionValue)
    {
        dbSettings.setDatabase(DatabaseNames.XXVERTEX);
        dbSettings.setCredentials(DatabaseCredentials.XXVERTEX);
        oracleSettings.setInstance(OracleEnvironments.ECOGTEST);
        Database sqlDatabase = createSqlConnection();
        String query = "update vtx_profile_option_values_t" +
                " set profile_option_value = '" + profileOptionValue + "'" +
                " where profile_option_id = (select profile_option_id from vtx_profile_options_t" +
                " where profile_option_name = 'VTX_LINE_LEVEL_ENABLED_FLAG')" +
                "   and fusion_instance_id = " +
                "(select fusion_instance_id from vtx_fusion_instances_t where fusion_instance_name = '"+ oracleSettings.instance_name +"')";

        sqlDatabase.executeSqlUpdateQuery(query);
    }

    /**
     * Returns monetary conversion rate for two currencies from the database.
     * This value is the conversion rate for the currency used at the tax
     * destination.
     * If no conversion rate has been defined for a combo then the
     * expected default value is 1.000000.
     *
     * @param fusionId The TRANSACTION_ID value as it appears in the db.
     * @param transactionId The TRANSACTION_ID value as it appears in the db.
     * @param requestHeaderId The REQUEST_HEADER_ID value as it appears in the db.
     *
     * @return currConvRate The numeric conversion rate between two currencies as a String.
     */
    public String getDestConversionRate(String fusionId, String transactionId, String requestHeaderId) {
        dbSettings.setDatabase(DatabaseNames.XXVERTEX);
        dbSettings.setCredentials(DatabaseCredentials.XXVERTEX);

        String fusionIdToUse = fusionInstanceId;
        if(fusionId != null)
            fusionIdToUse = fusionId;

        Database sqlDatabase = createSqlConnection();
        String query = "select CURR_CONV_RATE_DEST from vtx_tl_trx_req_dtl_t where TRANSACTION_ID = "+transactionId+
                " and FUSION_INSTANCE_ID = "+fusionIdToUse+" and REQUEST_HEADER_ID = "+requestHeaderId;

        ResultSet results = sqlDatabase.executeSqlQuery(query);
        String currConvRate = "";

        try {
            while (results.next()) {
                currConvRate = results.getString("CURR_CONV_RATE_DEST");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            fail("Test failed due to an interaction with the database results.");
        }
        VertexLogger.log("Curr conv rate: "+currConvRate, VertexLogLevel.INFO);
        return currConvRate;
    }

    /**
     * Searches and returns FUSION_INSTANCE_ID value for TRX_NUMBER.
     *
     * @param trxNum the transaction number as displayed in UI.
     *
     * @return fusionId ID number linked to a particular fusion as a String.
     */
    public String getFusionIdFromTrxNum(String trxNum) {
        dbSettings.setDatabase(DatabaseNames.XXVERTEX);
        dbSettings.setCredentials(DatabaseCredentials.XXVERTEX);

        Database sqlDatabase = createSqlConnection();
        String query = "select FUSION_INSTANCE_ID from vtx_tl_trx_req_hdr_t where trx_number = '"+trxNum+"'";

        ResultSet results = sqlDatabase.executeSqlQuery(query);
        String columnLabel = "FUSION_INSTANCE_ID";
        String fusionId = "";

        try {
            while (results.next()) {
                fusionId = results.getString(columnLabel);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            fail("Test failed due to an interaction with the database results.");
        }
        VertexLogger.log("Fusion instance Id: "+fusionId, VertexLogLevel.INFO);
        return fusionId;
    }

    /**
     * Get invoice data using a combination of parameters including
     * TRX_NUMBER, application_id, and LAST_UPDATE_DATE.
     */
    public void getInvoiceInfo() {
        dbSettings.setDatabase(DatabaseNames.XXVERTEX);
        dbSettings.setCredentials(DatabaseCredentials.XXVERTEX);
        Database sqlDatabase = createSqlConnection();
        String query = "SELECT *" +
                "  FROM VTX_TL_TRX_REQ_HDR_T" +
                "  where application_id = 222 and LAST_UPDATE_DATE >= '"+todaysDatedd_MMM_yyyy+"'" +
                "    and transaction_id in (378733, 378734)";
        ResultSet results = sqlDatabase.executeSqlQuery(query);

        try {
            while (results.next()) {
                results.getString(1);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            fail("Test failed due to an interaction with the database results.");
        }
    }

    /**
     * Returns the TRANSACTION_ID database value for a given transaction number.
     *
     * @param trxNumber the transaction number as displayed in UI.
     *
     * @return transactionId (String) The transaction ID assigned to the provided transaction number.
     */
    public String getInvoiceTransId(String trxNumber) {
        dbSettings.setDatabase(DatabaseNames.XXVERTEX);
        dbSettings.setCredentials(DatabaseCredentials.XXVERTEX);

        Database sqlDatabase = createSqlConnection();
        String query = "select TRANSACTION_ID from vtx_tl_trx_req_hdr_t where trx_number = '"+trxNumber+"'";
        ResultSet results = sqlDatabase.executeSqlQuery(query);
        String transactionId = "";

        try {
            while (results.next()) {
                transactionId = results.getString("TRANSACTION_ID");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            fail("Test failed due to an interaction with the database results.");
        }
        VertexLogger.log("Transaction Id: "+transactionId, VertexLogLevel.INFO);
        return transactionId;
    }

    /**
     * Returns the REQUEST_HEADER_ID database value for a given transaction number.
     *
     * @param trxNumber the transaction number as displayed in UI.
     *
     * @return requestHeaderId (String) The header ID assigned to the provided transaction number.
     */
    public String getInvoiceRequestHeaderId(String trxNumber) {
        dbSettings.setDatabase(DatabaseNames.XXVERTEX);
        dbSettings.setCredentials(DatabaseCredentials.XXVERTEX);

        Database sqlDatabase = createSqlConnection();
        String query = "select REQUEST_HEADER_ID from vtx_tl_trx_req_hdr_t where trx_number = '"+trxNumber+"'";
        ResultSet results = sqlDatabase.executeSqlQuery(query);
        String requestHeaderId = "";

        try {
            while (results.next()) {
                requestHeaderId = results.getString("REQUEST_HEADER_ID");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            fail("Test failed due to an interaction with the database results.");
        }
        VertexLogger.log("Request header Id:"+requestHeaderId, VertexLogLevel.INFO);
        return requestHeaderId;
    }

    /**
     * Changes the fusion instance ID for a given db option.
     * Some known IDs:
     * ecog-test-us2: 2
     * ecog-dev1-us2: 9
     *
     * @param value Id number as a string (example: 2)
     * @param trxNumber Transaction number associated with fusion ID.
     */
    public void updateFusionId(String value, String trxNumber) {
        if(value == null) {
            if (oracleSettings.instance_name.equals("ecog-test-us2"))
                value = "2";
            else if (oracleSettings.instance_name.equals("ecog-dev1-us2"))
                value = "9";
        }

        Database sqlDatabase = createSqlConnection();
        String query = "update VTX_TL_TRX_REQ_HDR_T set FUSION_INSTANCE_ID = '"+value+"'" +
        " where TRX_NUMBER = '"+trxNumber+"'";
        sqlDatabase.executeSqlUpdateQuery(query);
    }

    /**
     * Update invoice data using a combination of parameters including
     * TRX_NUMBER, application_id, and LAST_UPDATE_DATE
     */
    public void updateInvoiceInfo() {
        Database sqlDatabase = createSqlConnection();
        String query = "update VTX_TL_TRX_REQ_HDR_T set TRX_NUMBER = '"+transactionNumber+"'" +
                "where TRX_NUMBER = '229023' and application_id = 222" +
                "   and LAST_UPDATE_DATE >= '"+todaysDatedd_MMM_yyyy+"'";
        sqlDatabase.executeSqlUpdateQuery(query);
    }

    /**
     * Calls the Taxlink database to update the value of the provided
     * Taxlink profile option.
     *
     * @param profileOption Taxlink profile option.
     * @param value Value to insert into database for given option.
     */
    public void updateProfileOption(OracleCloudProfileOptions profileOption, String value) {
        dbSettings.setDatabase(DatabaseNames.XXVERTEX);
        dbSettings.setCredentials(DatabaseCredentials.XXVERTEX);
        Database sqlDatabase = createSqlConnection();
        String query = "update vtx_profile_option_values_t" +
                " set profile_option_value = '" + value + "'" +
                " where profile_option_id = (select profile_option_id from vtx_profile_options_t" +
                " where profile_option_name = '" + profileOption.name + "')" +
                "   and fusion_instance_id = " +
                "(select fusion_instance_id from vtx_fusion_instances_t where fusion_instance_name = '"+
                oracleSettings.instance_name +"')";

        sqlDatabase.executeSqlUpdateQuery(query);
    }

    /**
     * Confirms that the Billing Sync call is no longer made.
     *
     * Verifies that the TRANSSTATUSTYPEID value is 1 and
     * TRANSSYNCIND is 0 in the VTX9_JRNL.lineitem table for all
     * transactions containing the supplied prefix.
     */
    public void verifyBillingSyncNotCalled() {
        String transStatus = "transStatusTypeId";
        String transSync = "transSyncInd";
        String userTransId = "userTransIdCode";

        dbSettings.setDatabase(DatabaseNames.VTX9_JRNL);
        dbSettings.setCredentials(DatabaseCredentials.VERTEX);
        Database sqlDatabase = createSqlConnection();
        String query = "select *" +
                " from lineitem" +
                " where UsertransIDCode like '"+transactionNumber+"%'";
        ResultSet results = sqlDatabase.executeSqlQuery(query);

        try {
            while (results.next()) {
                assertTrue(results.getString(transStatus).equals("1"), "transStatusTypeId is not 1 for invoice in " +
                        "results. Invoice num: "+results.getString(userTransId));
                assertTrue(results.getString(transSync).equals("0"), "transSyncInd is not 0 for invoice in " +
                        "results. Invoice num: "+results.getString(userTransId));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            fail("Test failed due to an interaction with the database results.");
        }
    }

    /**
     * Determines if the current date for AR transactions is
     * present in the VTX9_JRNL LineItem table.
     */
    public void verifyRulesMapping() {
        Database sqlDatabase = createSqlConnection();
        String query = "select * from VTX9_JRNL.dbo.LineItem \n" +
                "where transprocdate = "+todaysDateyyyyMMdd+"\n" +
                "   and transsyncidcode like '%AR'";
        ResultSet results = sqlDatabase.executeSqlQuery(query);

        try {
            if (!results.next()) {
                VertexLogger.log("No transactions' transprocdate field contain today's date in the VTX9 " +
                        "journal.", VertexLogLevel.DEBUG);
                fail("Test failed due to rules mapping not confirmed in database.");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            fail("Test failed due to an interaction with the database results.");
        }
    }

    /**
     * Establishes a connection to a database, and returns the new database
     * representation.
     * @return a database object with an established connection.
     */
    private Database createSqlConnection() {
        OCloudSQLConnection oCloudSQLConnection = new OCloudSQLConnection();
        Connection currConnection = oCloudSQLConnection.establishConnection();
        Database database = new Database(currConnection);

        return database;
    }

    /**
     * Helper method. Return numeric representation of the current fusion instance determined by
     * the instance name specified in OracleSettings.
     *
     * @return fusionInstanceId The numerical Fusion Instance ID represented as a String.
     */
    private String getFusionInstanceId() {
        initializeApiTest();
        String fusionInstanceId = "";

        if (oracleSettings.instance_name.equals("ecog-test-us2"))
            fusionInstanceId = "2";
        else if (oracleSettings.instance_name.equals("ecog-dev1-us2"))
            fusionInstanceId = "9";

        return fusionInstanceId;
    }

    /**
     * To enable or disable 'VTX_ENABLE_TAX_ACTION_OVERRIDE' flag value in 'VTX_PROFILE_OPTION_VALUES_T' table
     * Y
     * @param profileOptionValue it should be set to Y to enable the flag
     *                           it should be N otherwise.
     */
    public void taxActionOverrideFlagSetting(String profileOptionValue)
    {
        dbSettings.setDatabase(DatabaseNames.XXVERTEX);
        dbSettings.setCredentials(DatabaseCredentials.XXVERTEX);
        oracleSettings.setInstance(OracleEnvironments.ECOGTEST);
        Database sqlDatabase = createSqlConnection();
        String query = "update vtx_profile_option_values_t" +
                " set profile_option_value = '" + profileOptionValue + "'" +
                " where profile_option_id = (select profile_option_id from vtx_profile_options_t" +
                " where profile_option_name = 'VTX_ENABLE_TAX_ACTION_OVERRIDE')" +
                "   and fusion_instance_id = " +
                "(select fusion_instance_id from vtx_fusion_instances_t where fusion_instance_name = '"+ oracleSettings.instance_name +"')";

        sqlDatabase.executeSqlUpdateQuery(query);
    }

    /**
     * To enable or disable 'VTX_USE_CTRL_AMT_AS_VENCHRGTAX' flag value in 'VTX_PROFILE_OPTION_VALUES_T' table
     * Y
     * @param profileOptionValue it should be set to Y to enable the flag
     *                           it should be N otherwise.
     */
    public void taxControlAmountFlagSetting(String profileOptionValue){
        dbSettings.setDatabase(DatabaseNames.XXVERTEX);
        dbSettings.setCredentials(DatabaseCredentials.XXVERTEX);
        oracleSettings.setInstance(OracleEnvironments.ECOGTEST);
        Database sqlDatabase = createSqlConnection();
        String query = "update vtx_profile_option_values_t" +
                " set profile_option_value = '" + profileOptionValue + "'" +
                " where profile_option_id = (select profile_option_id from vtx_profile_options_t" +
                " where profile_option_name = 'VTX_APPLY_TAX_ACTN_FOR_CTRLAMT')" +
                "   and fusion_instance_id = " +
                "(select fusion_instance_id from vtx_fusion_instances_t where fusion_instance_name = '"+ oracleSettings.instance_name +"')";


    }

}