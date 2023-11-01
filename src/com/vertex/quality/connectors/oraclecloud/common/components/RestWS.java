package com.vertex.quality.connectors.oraclecloud.common.components;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.common.configuration.OracleSettings;
import com.vertex.quality.connectors.oraclecloud.common.utils.OracleWebService;
import io.restassured.response.Response;
import static com.vertex.quality.connectors.oraclecloud.common.utils.Template.transactionNumber;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.fail;

/**
 * Representation of an Oracle REST web service.
 * Jira work: COERPC-3496
 *
 * @author msalomone
 */
public class RestWS extends OracleWebService {

	private OracleSettings settings = OracleSettings.getOracleSettingsInstance();

    private String arAutoInvoiceJob = "/oracle/apps/ess/financials/receivables/transactions/autoInvoices,AutoInvoiceImportEss";
    private String apPayablesInvoicesJob = "/oracle/apps/ess/financials/payables/invoices/transactions,APXIIMPT";
    private String apValidatePayablesJob = "/oracle/apps/ess/financials/payables/invoices/transactions,APXAPRVL";
    private String apPartnerTransactionDataExtractJob = "/oracle/apps/ess/financials/tax/report,PartnerTransactionExtractController";
    private String salesOrderJob = "/oracle/apps/ess/scm/doo/decomposition/receiveTransform/receiveSalesOrder,ImportOrdersJob";
	private String requisitionJob = "/oracle/apps/ess/prc/por/createReq/reqImport,RequisitionImportJob";
	private String purchaseOrderJob = "/oracle/apps/ess/prc/po/pdoi,ImportSPOJob";
	private String taxPartnerReportingSyncExtractJob = "/oracle/apps/ess/financials/tax/report,PartnerReportingSyncExtract";

    private String businessUnit;
    private String source;

	// RestWS fields for refactor.
    private String environment;
    private String endpoint;
    private String url;
    private int status;

    private String jsonRequest;
	private Response response;

    public RestWS(){}

    public RestWS(String bu) {
        this.businessUnit = bu;
    }

    public RestWS(String bu, String source) {
        this.businessUnit = bu;
        this.source = source;
    }

    // This is how RestWS should be utilized.
    public RestWS(String applicationUrl, String serviceEndpoint, int statusCode) {
		this.environment = applicationUrl;
		this.endpoint = serviceEndpoint;
		this.url = applicationUrl+endpoint;
		this.status = statusCode;
	}

	/**
	 * Calls the update invoice hold Oracle web service, and effectively
	 * removes the hold on the corresponding holdId..
	 *
	 * @param holdIds the IDs supplied by Oracle to identify a specific
	 *               invoice hold that will result in removal.
	 */
	public void sendRemoveHoldRequest(ArrayList<String> holdIds) {
    	try {
			URL oracleEndpoint = new URL(url);

			jsonRequest = "{\n" +
					"\"ReleaseName\": \""+"Variance corrected"+"\",\n" +
					"\"ReleaseReason\": \""+"Hold released by tester"+"\"\n" +
					"}";
			for(int i=0; i<holdIds.size(); i++)
			{
				response = given().auth().basic(settings.username, settings.password).
						contentType("application/json").body(jsonRequest).patch(oracleEndpoint+holdIds.get(i));
				VertexLogger.log(jsonRequest, VertexLogLevel.INFO);
				response.then().statusCode(status); // status for GETs is 200
			}

		} catch ( Exception ex ) {
			if(ex instanceof MalformedURLException)
				logMalformedUrlException(ex);
			else if(ex instanceof IOException)
				logIoException(ex);
		}
	}

	/**
	 * Calls the get all holds Oracle web service.
	 */
	public ArrayList<String> sendGetHoldsRequest() {
		ArrayList<String> holdIds = new ArrayList<String>();
		try {
			URL oracleEndpoint = new URL(url);

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("onlyData", "true");
			parameters.put("q", "InvoiceNumber LIKE "+transactionNumber+"*");
			response = given().auth().basic(settings.username, settings.password).
					contentType("application/json").params(parameters).get(oracleEndpoint);
			VertexLogger.log("Getting HoldIds", VertexLogLevel.INFO);
			response.then().statusCode(status); // status for GETs is 200
			String getResponse = response.asPrettyString();
			String holdsReturned = "";

			for (int j = getResponse.length(); j > 8; j--) {
				String countAttribute = getResponse.substring(j - 7, j);
				if (countAttribute.equals("\"count\""))
					holdsReturned = getResponse.substring(j + 2, j + 3);
			}

			if(holdsReturned.equals("0")) {
				VertexLogger.log("No holds returned", VertexLogLevel.INFO);
				holdIds.add("No holds to release");
			}
			else {
				for (int i = 40; i < getResponse.length() - 5; i++) {
					String holdIdAttribute = getResponse.substring(i, i + 6);
					if (holdIdAttribute.equals("HoldId")) {
						holdIds.add(getResponse.substring(i + 9, i + 15));
					}
				}
				VertexLogger.log("Requested holdIds are: " + holdIds, VertexLogLevel.INFO);
			}
    	} catch ( Exception ex ) {
			if(ex instanceof MalformedURLException)
				logMalformedUrlException(ex);
			else if(ex instanceof IOException)
				logIoException(ex);
		}

		return holdIds;
	}

	/**
	 * Logs information on current MUE exception status, prints
	 * the exception stacktrace, and fails the test scenario.
	 *
	 * @param mue malformed url exception that was caught.
	 */
	private void logMalformedUrlException( Exception mue) {
		VertexLogger.log("REST endpoint is not formatted correctly. " +
				"Please verify the endpoint works in a browser, or contact an " +
				"automation engineer.", VertexLogLevel.ERROR);
		mue.printStackTrace();
		fail("Test failed due to malformed REST endpoint.");
	}

	/**
	 * Logs information on current IO exception status, prints
	 * the exception stacktrace, and fails the test scenario.
	 *
	 * @param ioe IO exception that was caught.
	 */
	private void logIoException( Exception ioe ) {
		VertexLogger.log("There was an issue reading the csv from the " +
				"zip file path provided. Please double-check the path.", VertexLogLevel.ERROR);
		ioe.printStackTrace();
		fail("Test failed due to missing csv when zipping.");
	}

    /**
     * Send REST request read from an xml file.
     *
     * @param zipFileName .zip filename stored in ConnectorQuality/resources/csvfiles/oracle
     *                    (the csv must be zipped for proper data encoding)
     * @param oracleFileName Name of Oracle file to use from the target cloud environment.
     */
    public void sendArAutoInvoiceRequest(String zipFileName, String oracleFileName)
    {
        this.sendRestRequest("importBulkData", zipFileName, oracleFileName,
                arAutoInvoiceJob,"AR_AutoInvoice", businessUnit, source, "", "");
    }

    /**
     * Send REST request read from an xml file. This will kick off the Load File from Interface
     * job followed by Import Payables Invoices.
     *
     * @param zipFileName .zip filename stored in ConnectorQuality/resources/csvfiles/oracle
     *                    (the csv must be zipped for proper data encoding)
     * @param oracleFileName Name of Oracle file to use from the target cloud environment.
     *
     */
    public void sendImportDataApPayablesInvoicesRequest(String zipFileName, String oracleFileName)
    {
        this.sendRestRequest("importBulkData", zipFileName, oracleFileName,
                apPayablesInvoicesJob, "AP_PayablesInvoices", businessUnit, source, "", "");
    }

    /**
     * Send REST request read from an xml file. This will only kick off the
     * Import Payables Invoices job. This method should only be called after
     * an import bulk data operation is complete.
     *
     * @param zipFileName .zip filename stored in ConnectorQuality/resources/csvfiles/oracle
     *                    (the csv must be zipped for proper data encoding)
     * @param oracleFileName Name of Oracle file to use from the target cloud environment.
     */
    public void sendApPayablesInvoicesRequest(String zipFileName, String oracleFileName)
    {
        this.sendRestRequest("submitESSJobRequest", zipFileName, oracleFileName,
                apPayablesInvoicesJob, "AP_PayablesInvoices", businessUnit, source, "", "");
    }

    /**
     * Send REST request read from an xml file. This will only kick off the
     * Validate Payables Invoices job. This method should only be called after
     * an import bulk data operation is complete.
     */
    public void sendApValidatePayablesRequest()
	{
		this.sendRestRequest("submitESSJobRequest", "", "",
                apValidatePayablesJob, "AP_ValidateInvoices", businessUnit, "", "",
				"");
	}

    /**
     * Send REST request read from an xml file. This will only kick off the
     * Partner Transaction data Extract job. This method should only be called after
     * an import bulk data operation is complete, and other appropriate steps are
     * taken to ensure that transaction data is in the ERP.
     */
	public void sendApPartnerTransactionDataExtract() {
		this.sendRestRequest("submitESSJobRequest", "", "",
                apPartnerTransactionDataExtractJob, "PartnerDataExtract", businessUnit, "",
				"", "");
	}

	/**
	 * Send REST request read from an xml file. This will only kick off the
	 * Import Sales Order job. This method should only be called after
	 * an import bulk data operation is complete.
	 */
	public void importSalesOrderJob(String zipFileName, String oracleFileName)
	{
		this.sendRestRequest("importBulkData", zipFileName, oracleFileName,
			salesOrderJob, "OM_importSalesOrder", businessUnit, "", "", "");
	}

	/**
	 * Send REST request. This will only retrieve the
	 * Sales Order data. This method should only be called after
	 * an import bulk data operation is complete.
	 */
	public void getSalesOrderData()
	{
		this.sendRestRequest("salesOrderRequest", "", "",
			"", "", "", "", "", "");
	}

	/**
	 * Send REST request. This will only kick off the
	 * Import Orders job for Purchase Orders. This method should only be called after
	 * an import bulk data operation is complete.
	 */
	public void importOrdersJob(String zipFileName, String oracleFileName, String batchId)
	{
		this.sendRestRequest("importBulkData", zipFileName, oracleFileName,
			purchaseOrderJob, "PO_importOrder", businessUnit, "", "", batchId);
	}

	/**
	 * Send REST request read from an xml file. This will only kick off the
	 * Import Requisitions job. This method should only be called after
	 * an import bulk data operation is complete.
	 */
	public void importRequisitionsJob(String zipFileName, String oracleFileName,String batchId)
	{
		this.sendRestRequest("importBulkData", zipFileName, oracleFileName,
			requisitionJob, "REQ_importRequisitions", businessUnit, "", "", batchId);
	}

	/**
	 * Send REST request read from an xml file. This will only kick off the
	 * Tax Partner Reporting Sync Extract Job. This method should only be called after
	 * appropriate steps are taken to ensure that Tax Partner data is in the ERP.
	 */
	public void taxPartnerReportingSyncExtractJob(String extractMode)
	{
		this.sendRestRequest("submitESSJobRequest", "", "",
			taxPartnerReportingSyncExtractJob, "TPRSE_report", businessUnit, "", extractMode, "");
	}
}
