package com.vertex.quality.connectors.taxlink.api.tests;

import com.vertex.quality.connectors.taxlink.api.keywords.DataKeywords;
import com.vertex.quality.connectors.taxlink.api.keywords.WebServiceKeywords;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiInitializer.initializeCalcApiTest;

/**
 * API tests that use singular transactions per scenario for Requisition.
 *
 * @author Nithin.Mathew, Aman Jain
 */

public class TaxlinkRequisitionSingleTransactionTests {
    private WebServiceKeywords webservice = new WebServiceKeywords();
    private DataKeywords data = new DataKeywords();

    /**
     * Configures test settings.
     */
    @BeforeClass(alwaysRun = true, groups = { "TL_regression" })
    private void setup( ) { initializeCalcApiTest(); }

    /**
     * Test for crate a purchase requisition.
     */
    @Test(groups = { "TL_regression" })
    public void reqCreateRequisitionTest(){
        webservice.sendPoSingleRequest("req-createrequisition","REQCREATEREQUISITION");
        data.validateReqTaxAmtTaxRateLineAmtForSingleLine("req-createrequisition","0.09","6","1.45709745429726");
    }

	/**
	 * Test for Requisition Batch workflow
	 */
	@Test(groups = { "TL_regression" })
	public void requisitionBatchTest( )
	{
		webservice.sendReqBatchReqAsSingleRequest("7991-RequisitionRequest", "7991");
		data.validateAndContinueReqSingleLine("7991-RequisitionRequest", "9", "9", "100","7991");

		webservice.sendReqBatchReqAsSingleRequest("7992-RequisitionRequest", "7992");
		data.validateAndContinueReqSingleLine("7992-RequisitionRequest", "6", "6", "100","7992");

		webservice.sendReqBatchReqAsSingleRequest("7993-RequisitionRequest", "7993");
		data.validateAndContinueReqSingleLine("7993-RequisitionRequest", "500", "7", "7500","7993");

		webservice.sendReqBatchReqAsSingleRequest("7994-RequisitionRequest", "7994");
		data.validateAndContinueReqSingleLine("7994-RequisitionRequest", "88.75", "8.875", "1000","7994");

		webservice.sendReqBatchReqAsSingleRequest("7995-RequisitionRequest", "7995");
		data.validateAndContinueReqSingleLine("7995-RequisitionRequest", "6.35", "6.35", "100","7995");

		webservice.sendReqBatchReqAsSingleRequest("7996-RequisitionRequest", "7996");
		data.validateAndContinueReqSingleLine("7996-RequisitionRequest", "14", "7", "200","7996");

		webservice.sendReqBatchReqAsSingleRequest("7997-RequisitionRequest", "7997");
		data.validateAndContinueReqSingleLine("7997-RequisitionRequest", "88.75", "8.875", "1000","7997");

		webservice.sendReqBatchReqAsSingleRequest("7998-RequisitionRequest", "7998");
		data.validateAndContinueReqSingleLine("7998-RequisitionRequest", "6.35", "6.35", "100","7998");

		webservice.sendReqBatchReqAsSingleRequest("7999-RequisitionRequest", "7999");
		data.validateAndContinueReqSingleLine("7999-RequisitionRequest", "8.88", "8.875", "100","7999");

		webservice.sendReqBatchReqAsSingleRequest("8000-RequisitionRequest", "8000");
		data.validateAndContinueReqSingleLine("8000-RequisitionRequest", "14", "7", "200","8000");

		webservice.sendReqBatchReqAsSingleRequest("8001-RequisitionRequest", "8001");
		data.validateAndContinueReqSingleLine("8001-RequisitionRequest", "8.88", "8.875", "100","8001");

		webservice.sendReqBatchReqAsSingleRequest("8002-RequisitionRequest", "8002");
		data.validateAndContinueReqSingleLine("8002-RequisitionRequest", "780", "12", "10000","8002");

		webservice.sendReqBatchReqAsSingleRequest("8003-RequisitionRequest", "8003");
		data.validateAndContinueReqSingleLine("8003-RequisitionRequest", "9.25", "9.25", "100","8003");

		webservice.sendReqBatchReqAsSingleRequest("8004-RequisitionRequest", "8004");
		data.validateAndContinueReqSingleLine("8004-RequisitionRequest", "780", "12", "10000","8004");

		webservice.sendReqBatchReqAsSingleRequest("8005-RequisitionRequest", "8005");
		data.validateAndContinueReqSingleLine("8005-RequisitionRequest", "6.35", "6.35", "100","8005");

		webservice.sendReqBatchReqAsSingleRequest("8006-RequisitionRequest", "8006");
		data.validateAndContinueReqSingleLine("8006-RequisitionRequest", "500", "7", "7500","8006");

		data.validateTestStatus();
	}

	/**
	 * Test for Requisition Batch workflow for smoke testing
	 */
	@Test(groups = { "TL_smoke" })
	public void reqVodTest( )
	{
		webservice.sendReqBatchReqAsSingleRequest("8064-RequisitionRequestVOD", "8064");
		data.validateAndContinueReqSingleLine("8064-RequisitionRequestVOD", "8.88", "8.875", "100","8064");

		webservice.sendReqBatchReqAsSingleRequest("8065-RequisitionRequestVOD", "8065");
		data.validateAndContinueReqSingleLine("8065-RequisitionRequestVOD", "14", "7", "200","8065");

		webservice.sendReqBatchReqAsSingleRequest("8066-RequisitionRequestVOD", "8066");
		data.validateAndContinueReqSingleLine("8066-RequisitionRequestVOD", "14", "7", "200","8066");

		webservice.sendReqBatchReqAsSingleRequest("8067-RequisitionRequestVOD", "8067");
		data.validateAndContinueReqSingleLine("8067-RequisitionRequestVOD", "8.88", "8.875", "100","8067");

		webservice.sendReqBatchReqAsSingleRequest("8068-RequisitionRequestVOD", "8068");
		data.validateAndContinueReqSingleLine("8068-RequisitionRequestVOD", "6.35", "6.35", "100","8068");

		webservice.sendReqBatchReqAsSingleRequest("8069-RequisitionRequestVOD", "8069");
		data.validateAndContinueReqSingleLine("8066-RequisitionRequestVOD", "14", "7", "200","8069");

		webservice.sendReqBatchReqAsSingleRequest("8070-RequisitionRequestVOD", "8070");
		data.validateAndContinueReqSingleLine("8070-RequisitionRequestVOD", "8.88", "8.875", "100","8070");

		webservice.sendReqBatchReqAsSingleRequest("8071-RequisitionRequestVOD", "8071");
		data.validateAndContinueReqSingleLine("8071-RequisitionRequestVOD", "8.88", "8.875", "100","8071");

		webservice.sendReqBatchReqAsSingleRequest("8072-RequisitionRequestVOD", "8072");
		data.validateAndContinueReqSingleLine("8072-RequisitionRequestVOD", "500", "7", "7500","8072");

		webservice.sendReqBatchReqAsSingleRequest("8073-RequisitionRequestVOD", "8073");
		data.validateAndContinueReqSingleLine("8073-RequisitionRequestVOD", "7.03", "28.125", "100","8073");

		webservice.sendReqBatchReqAsSingleRequest("8074-RequisitionRequestVOD", "8074");
		data.validateAndContinueReqSingleLine("8074-RequisitionRequestVOD", "500", "7", "7500","8074");

		data.validateTestStatus();
	}
}

