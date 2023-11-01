package com.vertex.quality.connectors.oraclecloud.keywords.api;

import com.vertex.quality.connectors.oraclecloud.common.components.*;
import com.vertex.quality.connectors.oraclecloud.common.configuration.OracleSettings;
import com.vertex.quality.connectors.oraclecloud.common.configuration.enums.OracleEndpoints;

import java.util.ArrayList;

/**
 * Keywords for initiating Oracle processes.
 *
 * COERPC-3434
 */
public class ProcessesKeywords
{
	private OracleSettings settings = OracleSettings.getOracleSettingsInstance();

	/**
	 * Kicks off the Load Interface from File Oracle process then the Import
	 * Autoinvoice process.
	 *
	 * @param zipFileName Name of zip file used in resource directory to load a batch
	 *                    of transactions into Oracle.
	 * @param oracleFileName The name used for the supplied zip to store on Oracle.
	 * @param businessUnit Collection of taxation rules used by Oracle ERP. String literals for each
	  *                     business unit such as "VTX_US_BU" and "VTX_CA_BU" are acceptable, as well
	  *                     as their Oracle codes 300000005503393 and 300000006755657 respectively
	 */
	public void importAutoInvoiceAR(String zipFileName, String oracleFileName, String businessUnit, String source) {
		RestWS restService = new RestWS(businessUnit, source);
		restService.sendArAutoInvoiceRequest(zipFileName, oracleFileName);
	}

	/**
	 * Kicks off the Load Interface from File Oracle process then the Import
	 * Payables Invoices process.
	 *
	 * @param zipFileName Name of zip file used in resource directory to load a batch
	 *                    of transactions into Oracle.
	 * @param oracleFileName The name used for the supplied zip to store on Oracle.
	 * @param businessUnit Collection of taxation rules used by Oracle ERP. String literals for each
	 *                     business unit such as "VTX_US_BU" and "VTX_CA_BU" are acceptable, as well
	 *                     as their Oracle codes 300000005503393 and 300000006755657 respectively
	 */
	public void importDataPayablesInvoicesAP(String zipFileName, String oracleFileName,
											 String businessUnit, String source) {
		RestWS restService = new RestWS(businessUnit, source);
		restService.sendImportDataApPayablesInvoicesRequest(zipFileName, oracleFileName);
	}

	/**
	 * Kicks off the Import Payables Invoices job. This keyword should only be used once
	 * data has loaded into the ERP via an Import bulk data job.
	 *
	 * @param businessUnit Collection of taxation rules used by Oracle ERP. String literals for each
	 *                     business unit such as "VTX_US_BU" and "VTX_CA_BU" are acceptable, as well
	 *                     as their Oracle codes 300000005503393 and 300000006755657 respectively
	 */
	public void importPayablesInvoicesAP(String businessUnit, String source) {
		RestWS restService = new RestWS(businessUnit, source);
		restService.sendApPayablesInvoicesRequest("","");
}

	/**
	 * Kicks off the Validate Payables Invoices process.
	 *
	 * @param businessUnit Collection of taxation rules used by Oracle ERP. String literals for each
	 *                     business unit such as "VTX_US_BU" and "VTX_CA_BU" are acceptable, as well
	 *                     as their Oracle codes 300000005503393 and 300000006755657 respectively
	 */
	public void validatePayablesInvoicesAP(String businessUnit) {
		RestWS restService = new RestWS(businessUnit);
		restService.sendApValidatePayablesRequest();
	}

	/**
	 * Kicks off the Partner Transaction Data Extract process.
	 *
	 * @param businessUnit Collection of taxation rules used by Oracle ERP. String literals for each
	 *                     business unit such as "VTX_US_BU" and "VTX_CA_BU" are acceptable, as well
	 *                     as their Oracle codes 300000005503393 and 300000006755657 respectively
	 */
	public void runPartnerTransactionDataExtract(String businessUnit) {
		RestWS restService = new RestWS(businessUnit);
		restService.sendApPartnerTransactionDataExtract();
	}

	/**
	 * Kicks off the Import Sales Order job. This keyword should only be used once
	 * data has loaded into the ERP via an Import bulk data job.
	 *
	 * @param businessUnit Collection of taxation rules used by Oracle ERP. String literals for each
	 *                     business unit such as "VTX_US_BU" and "VTX_CA_BU" are acceptable, as well
	 *                     as their Oracle codes 300000005503393 and 300000006755657 respectively
	 */
	public void importDataSalesOrder(String zipFileName, String oracleFileName, String businessUnit, String source) {
		RestWS restService = new RestWS(businessUnit);
		restService.importSalesOrderJob(zipFileName, oracleFileName);
	}

	/**
	 * Retrieves Sales Order data. This keyword should only be used once
	 * data has loaded into the ERP via an Import bulk data job.
	 *
	 */
	public void getSalesOrders() {
		RestWS restService = new RestWS();
		restService.getSalesOrderData();
	}

	/**
	 * Kicks off the Import Orders job for Purchase Orders. This keyword should only be used once
	 * data has loaded into the ERP via an Import bulk data job.
	 *
	 * @param businessUnit Collection of taxation rules used by Oracle ERP. String literals for each
	 *                     business unit such as "VTX_US_BU" and "VTX_CA_BU" are acceptable, as well
	 *                     as their Oracle codes 300000005503393 and 300000006755657 respectively
	 */
	public void importOrders(String zipFileName, String oracleFileName, String businessUnit, String source,
							 String batchId) {
		RestWS restService = new RestWS(businessUnit);
		restService.importOrdersJob(zipFileName, oracleFileName, batchId);
	}

	/**
	 * Kicks off the Import Requisitions job. This keyword should only be used once
	 * data has loaded into the ERP via an Import bulk data job.
	 *
	 * @param businessUnit Collection of taxation rules used by Oracle ERP. String literals for each
	 *                     business unit such as "VTX_US_BU" and "VTX_CA_BU" are acceptable, as well
	 *                     as their Oracle codes 300000005503393 and 300000006755657 respectively
	 */
	public void importRequisitions(String zipFileName, String oracleFileName, String businessUnit, String source,
		String batchId) {
		RestWS restService = new RestWS(businessUnit);
		restService.importRequisitionsJob(zipFileName, oracleFileName, batchId);
	}

	/**
	 * Kicks off the Tax Partner Reporting Sync Extract job. This keyword should only be used once
	 * data has loaded into the ERP.
	 *
	 * @param businessUnit Collection of taxation rules used by Oracle ERP. String literals for each
	 *                     business unit such as "VTX_US_BU" and "VTX_CA_BU" are acceptable, as well
	 *                     as their Oracle codes 300000005503393 and 300000006755657 respectively
	 * @param extractMode BOTH is an acceptable value. Please consult the UI for other options.
	 */
	public void taxPartnerReportingSyncExtract(String businessUnit, String extractMode){
		RestWS restService = new RestWS(businessUnit);
		restService.taxPartnerReportingSyncExtractJob(extractMode);
	}

	/**
	 * Removes a hold on an invoice in the ERP.
	 *
	 * @param holdIds the invoice holds to be removed.
	 */
	public void removeHold(ArrayList<String> holdIds) {
		try {
			settings.setEndpoint(OracleEndpoints.UPDATE_HOLD);
			RestWS restService = new RestWS(settings.environment, settings.endpoint, 200);
			if (holdIds.contains("No holds to release") == false)
				restService.sendRemoveHoldRequest(holdIds);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			settings.setEndpoint(OracleEndpoints.PROCESSES);
		}
	}

	/**
	 * Gets all invoice holds in the ERP restricted
	 * by the supplied limit.
	 */
	public ArrayList<String> getHolds() {
		settings.setEndpoint(OracleEndpoints.GET_ALL_HOLDS);
		RestWS restService = new RestWS(settings.environment, settings.endpoint, 200);
		ArrayList<String> holdIds =	restService.sendGetHoldsRequest();
		return holdIds;
	}
}
