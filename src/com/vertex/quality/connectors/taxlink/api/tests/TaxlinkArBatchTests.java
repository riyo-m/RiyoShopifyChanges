package com.vertex.quality.connectors.taxlink.api.tests;

import com.vertex.quality.connectors.taxlink.api.keywords.WebServiceKeywords;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiInitializer.*;

/**
 * API tests that use Batch transactions per scenario for AR.
 *
 * @author Aman Jain
 */

public class TaxlinkArBatchTests
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
	 * to send notification request to taxlink for ArUsCanInvoice
	 */
	@Test(groups = { "TL_regression" })
	public void arUsCanInvoiceTest( )
	{
		String testName = "ArUsCanInvoice";
		String documentId = "";
		documentId = webservice.sendUploadRequestToUCM("sendUploadToUcmRequest",
			"Vertex_TransactionExtract_222_ArUsCanInvoiceTest");
		webservice.sendNotificationRequest("sendNotificationRequest", documentId);
		webservice.processDocIdInDatabase(documentId, testName);
	}
	/**
	 * A Test to send upload req to UCM server uses the doc-Id received in response
	 * to send notification request to taxlink for ArUsCanCreditMemo
	 */
	@Test(groups = { "TL_regression" })
	public void arUsCanCreditMemoTest( )
	{
		String testName = "ArUsCanCreditMemo";
		String documentId = "";
		documentId = webservice.sendUploadRequestToUCM("sendUploadToUcmRequest",
			"Vertex_TransactionExtract_222_ArUsCanCreditMemoTest");
		webservice.sendNotificationRequest("sendNotificationRequest", documentId);
		webservice.processDocIdInDatabase(documentId, testName);
	}

	/**
	 * A Test to send upload req to UCM server uses the doc-Id received in response
	 * to send notification request to taxlink for ArVatInvoice
	 */
	@Test(groups = { "TL_regression" })
	public void arVatInvoiceTest( )
	{
		String testName = "ArVatInvoice";
		String documentId = "";
		documentId = webservice.sendUploadRequestToUCM("sendUploadToUcmRequest",
				"Vertex_TransactionExtract_222_ArVatInvoiceTest");
		webservice.sendNotificationRequest("sendNotificationRequest", documentId);
		webservice.processDocIdInDatabase(documentId, testName);
	}

	/**
	 * A Test to send upload req to UCM server uses the doc-Id received in response
	 * to send notification request to taxlink for ArVatCreditMemo
	 */
	@Test(groups = { "TL_regression" })
	public void arVatCreditMemoTest( )
	{
		String testName = "ArVatCreditMemo";
		String documentId = "";
		documentId = webservice.sendUploadRequestToUCM("sendUploadToUcmRequest",
				"Vertex_TransactionExtract_222_ArVatCreditMemoTest");
		webservice.sendNotificationRequest("sendNotificationRequest", documentId);
		webservice.processDocIdInDatabase(documentId, testName);
	}
	/**
	 * A Test to send upload req to UCM server uses the doc-Id received in response
	 * to send notification request to taxlink for smoke testing AR invoice processing
	 *
	 */
	@Test(groups = { "TL_smoke" })
	public void arInvVodTest( )
	{
		String testName = "ArInvoiceVod";
		String documentId = "";
		documentId = webservice.sendUploadRequestToUCM("sendUploadToUcmRequest",
			"Vertex_TransactionExtract_222_ArInvVodTest");
		webservice.sendNotificationRequest("sendNotificationRequest", documentId);
		webservice.processDocIdInDatabase(documentId, testName);
	}

	/**
	 * A Test to send upload req to UCM server uses the doc-Id received in response
	 * to send notification request to taxlink for smoke testing AR credit memo processing
	 *
	 */
	@Test(groups = { "TL_smoke" })
	public void arCreditMemoVodTest( )
	{
		String testName = "ArCreditMemoVod";
		String documentId = "";
		documentId = webservice.sendUploadRequestToUCM("sendUploadToUcmRequest",
			"Vertex_TransactionExtract_222_ArCreditMemoVodTest");
		webservice.sendNotificationRequest("sendNotificationRequest", documentId);
		webservice.processDocIdInDatabase(documentId, testName);
	}
}

