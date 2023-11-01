package com.vertex.quality.connectors.taxlink.api.tests;

import com.vertex.quality.connectors.taxlink.api.keywords.WebServiceKeywords;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiInitializer.*;

/**
 * API tests that use Batch transactions per scenario for AP.
 *
 * @author Aman Jain
 */

public class TaxlinkApBatchTests
{
	private WebServiceKeywords webservice = new WebServiceKeywords();

	/**
	 * Configures test settings.
	 */
	@BeforeClass(alwaysRun = true, groups = { "TL_regression" })
	private void setup( )
	{
		initializeOracleErpApiTest(); // to initialize oerp params
		initializeCalcApiTest();// to initialize taxlink params
		initializeDbSettingsTest(); // to initialize database params
	}

	/**
	 * A Test to send upload req to UCM server uses the doc-Id received in response
	 * to send notification request to taxlink for ApCanadaBu
	 */
	@Test(groups = { "TL_regression" })
	public void apCanadaBuTest( )
	{
		String testName = "ApCanadaBu";
		String documentId = "";
		documentId = webservice.sendUploadRequestToUCM("sendUploadToUcmRequest",
			"Vertex_TransactionExtract_200_ApCanadaBuTest");
		webservice.sendNotificationRequest("sendNotificationRequest", documentId);
		webservice.processDocIdInDatabase(documentId, testName);
	}

	/**
	 * A Test to send upload req to UCM server uses the doc-Id received in response
	 * to send notification request to taxlink for ApVat
	 */
	@Test(groups = { "TL_regression" })
	public void apVatTest( )
	{
		String testName = "ApVat";
		String documentId = "";
		documentId = webservice.sendUploadRequestToUCM("sendUploadToUcmRequest",
			"Vertex_TransactionExtract_200_ApVatTest");
		webservice.sendNotificationRequest("sendNotificationRequest", documentId);
		webservice.processDocIdInDatabase(documentId, testName);
	}

	/**
	 * A Test to send upload req to UCM server uses the doc-Id received in response
	 * to send notification request to taxlink for apUsLineLevel
	 */
	@Test(groups = { "TL_regression" })
	public void apUsLineLevelTest( )
	{
		String testName = "apUsLineLevel";
		String documentId = "";
		documentId = webservice.sendUploadRequestToUCM("sendUploadToUcmRequest",
			"Vertex_TransactionExtract_200_ApUsLineLevelTest");
		webservice.sendNotificationRequest("sendNotificationRequest", documentId);
		webservice.processDocIdInDatabase(documentId, testName);
	}

	/**
	 * A Test to send upload req to UCM server uses the doc-Id received in response
	 * to send notification request to taxlink for apUsDocLevel
	 */
	@Test(groups = { "TL_regression" })
	public void apUsDocLevelTest( )
	{
		String testName = "apUsDocLevel";
		String documentId = "";
		documentId = webservice.sendUploadRequestToUCM("sendUploadToUcmRequest",
			"Vertex_TransactionExtract_200_apUsDocLevelTest");
		webservice.sendNotificationRequest("sendNotificationRequest", documentId);
		webservice.processDocIdInDatabase(documentId, testName);
	}

	/**
	 * A Test to send upload req to UCM server uses the doc-Id received in response
	 * to send notification request to taxlink for smoke testing AP invoice processing
	 */
	@Test(groups = { "TL_smoke" })
	public void apInvVodTest()
	{
		String testName = "ApInvVod";
		String documentId = "";
		documentId = webservice.sendUploadRequestToUCM("sendUploadToUcmRequest",
			"Vertex_TransactionExtract_200_ApInvVodTest");
		webservice.sendNotificationRequest("sendNotificationRequest", documentId);
		webservice.processDocIdInDatabase(documentId, testName);
	}
}

