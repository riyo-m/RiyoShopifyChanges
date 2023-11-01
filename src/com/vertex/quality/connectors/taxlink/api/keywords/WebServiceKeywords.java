package com.vertex.quality.connectors.taxlink.api.keywords;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.components.TaxlinkDatabaseHandler;
import com.vertex.quality.connectors.taxlink.common.utils.TaxlinkAPIUtilities;
import com.vertex.quality.connectors.taxlink.common.webservices.*;

/**
 * Methods that interact with Taxlink webservices to be used in test cases.
 *
 * @author msalomone, ajain
 */
public class WebServiceKeywords
{
	CsvHandlerKeywords CsvHandlerKeywords = new CsvHandlerKeywords();
	private TaxlinkAPIUtilities utilities = new TaxlinkAPIUtilities();

	/**
	 * Sends a SOAP request for a realtime single
	 * AP transaction.
	 *
	 * @param testRequest Name of XML file that serves as the realtime request
	 *                    to Taxlink's calc endpoint.
	 */
	public void sendApSingleRequest( String testRequest, String testName )
	{
		TaxlinkApSoapRequest apRequest = new TaxlinkApSoapRequest();
		apRequest.setArgs(testName); // old trxId: "300000029086453"
		apRequest.createRequest(testRequest);
		apRequest.sendRequest();
		apRequest.writeXmlResponseToFile(testRequest);
	}

	/**
	 * Sends a SOAP request for a realtime single
	 * AR transaction.
	 *
	 * @param testRequest Name of XML file that serves as the realtime request
	 *                    to Taxlink's calc endpoint.
	 */
	public void sendArSingleRequest( String testRequest, String testName )
	{
		TaxlinkArSoapRequest arRequest = new TaxlinkArSoapRequest();
		arRequest.setArgs(testName);
		arRequest.createRequest(testRequest);
		arRequest.sendRequest();
		arRequest.writeXmlResponseToFile(testRequest);
	}

	/**
	 * Sends a SOAP request for a realtime single
	 * PO transaction.
	 *
	 * @param testRequest Name of XML file that serves as the realtime request
	 *                    to Taxlink's calc endpoint.
	 */
	public void sendPoSingleRequest( String testRequest, String testName )
	{
		TaxlinkPoSoapRequest poRequest = new TaxlinkPoSoapRequest();
		poRequest.setArgs(testName);
		poRequest.createRequest(testRequest);
		poRequest.sendRequest();
		poRequest.writeXmlResponseToFile(testRequest);
	}

	/**
	 * Sends a SOAP request for a realtime single
	 * OM transaction.
	 *
	 * @param testRequest Name of XML file that serves as the realtime request
	 *                    to Taxlink's calc endpoint.
	 */
	public void sendOmSingleRequest( String testRequest, String testName )
	{
		TaxlinkOmSoapRequest omRequest = new TaxlinkOmSoapRequest();
		omRequest.setArgs(testName);
		omRequest.createRequest(testRequest);
		omRequest.sendRequest();
		omRequest.writeXmlResponseToFile(testRequest);
	}

	/**
	 * Sends a SOAP request for a realtime single
	 * Requisition transaction.
	 *
	 * @param testRequest Name of XML file that serves as the realtime request
	 *                    to Taxlink's calc endpoint.
	 */
	public void sendReqBatchReqAsSingleRequest( String testRequest, String reqName )
	{
		TaxlinkReqSoapRequest reqRequest = new TaxlinkReqSoapRequest();
		reqRequest.setArgs(reqName);
		reqRequest.createRequest(testRequest);
		reqRequest.sendRequest();
		reqRequest.writeXmlResponseToFile(testRequest);
	}

	/**
	 * Sends a notification SOAP request to taxlink
	 *
	 * @param testRequest Name of XML file that serves as the request
	 *                    to Taxlink's calc endpoint.
	 * @param documentId  document id that needs to be updated
	 */
	public void sendNotificationRequest( String testRequest, String documentId )
	{
		TaxlinkNotificationRequest notificationRequest = new TaxlinkNotificationRequest();
		VertexLogger.log("Document Id is " + documentId);
		notificationRequest.setArgs(documentId);
		notificationRequest.createRequest(testRequest);
		VertexLogger.log("Sending Notification Request to Taxlink");
		notificationRequest.sendNotificationRequest();
	}

	/**
	 * Send upload Request to OERP UCM server and gets the documentId
	 *
	 * @param testRequest Name of XML file that serves as the request
	 *                    to calc endpoint.
	 * @param fileName    filename that needs to be updated
	 */
	public String sendUploadRequestToUCM( String testRequest, String fileName )
	{
		String documentId = "";
		UploadToUCMRequest uploadToUCMRequest = new UploadToUCMRequest();
		uploadToUCMRequest.setArgs(fileName);
		uploadToUCMRequest.createRequest(fileName, testRequest);
		VertexLogger.log("Sending upload request to UCM");
		uploadToUCMRequest.sendRequest();
		documentId = uploadToUCMRequest.getDocumentIdFromResponse();
		uploadToUCMRequest.writeXmlResponseToFile(testRequest);
		return documentId;
	}

	/**
	 * Processes the document in database using docId
	 * and generates the result csv file
	 *
	 * @param documentId
	 * @param testName
	 */
	public void processDocIdInDatabase( String documentId, String testName )
	{
		boolean trxProcessed;
		String resultFileName = testName + "_Results.csv";
		String baselineFileName = testName + "_Baseline.csv";

		TaxlinkDatabaseHandler databaseHandler = new TaxlinkDatabaseHandler();
		VertexLogger.log("Checking the Doc Status in database");
		trxProcessed = databaseHandler.checkDocumentStatus(documentId);
		if ( trxProcessed )
		{
			VertexLogger.log("Fetching details from Response and generating result csv file");
			databaseHandler.generateResultFromResponse(documentId, resultFileName);

			if ( resultFileName != null && baselineFileName != null )
			{
				CsvHandlerKeywords.compareCSVFileData(resultFileName, baselineFileName);
			}
		}
	}
}
