package com.vertex.quality.connectors.ariba.api.tests.requisition;

import com.vertex.quality.connectors.ariba.api.common.webservices.AribaSoapResponse;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIAddressTypes;
import com.vertex.quality.connectors.ariba.api.keywords.DataKeywords;
import com.vertex.quality.connectors.ariba.api.tests.base.AribaAPIRequisitionBaseTest;
import com.vertex.quality.connectors.taxlink.api.keywords.WebServiceKeywords;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * Tests the response for the external tax type for requisitions
 *
 * @author hho osabha, vgosavi
 */

public class AribaRequisitionAPIExternalTaxTypeTests extends AribaAPIRequisitionBaseTest
{
	private WebServiceKeywords webservice = new WebServiceKeywords();
	private DataKeywords data = new DataKeywords();
	AribaSoapResponse aribaSoapResponse = new AribaSoapResponse();

	/**
	 * Verifies that the correct external tax type for sales tax is returned
	 * CARIBA-970
	 */
	@Test(groups = { "ariba_api","ariba_smoke","ariba_regression" })
	public void salesTaxExternalTaxTypeTest( )
	{
		String respFileName = "salesTaxExternalTaxTypeTest";
		apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.SUPPLIER, "Philadelphia", "US", null, null, null,
			"19107", "PA");
		apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.SUPPLIER, "Philadelphia", "US", null, null, null,
			"19107", "PA");
		apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.SHIP_TO, "Dallas", "US", "4505 Ridgeside Dr", null, null,
			"75244-4902", "TX");
		apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.BILLING, "Dallas", "US", "4505 Ridgeside Dr", null, null,
			"75244-4902", "TX");
		apiUtil.requestData.setItemDescriptionCommonCommodityCode(1, null, null, "EA");

		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForCategoryTaxTypePercentTaxAmtForSingleLineRequisition(respFileName, "SELLER_USE", "SalesTaxState","8.25","18");
	}

	/**
	 * Verifies that the correct external tax type for Canada's vat tax is returned
	 * CARIBA-971
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void canadianExternalTaxTypeTest( )
	{
		String respFileName = "canadianExternalTaxTypeTest";
		apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.SUPPLIER, "Québec City", "CA",
			"2450 Boulevard Laurier G12a", null, null, "G1V 2L1", "QC");
		apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.SHIP_TO, "Victoria", "CA", "1820 Store St Victoria",
			null, null, "V8T 4R4", "BC");
		apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.BILLING, "Victoria", "CA", "1820 Store St Victoria",
			null, null, "V8T 4R4", "BC");
		apiUtil.requestData.setItemDescriptionCommonCommodityCode(1, null, null, "EA");

		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForCategoryTaxTypePercentTaxAmtForSingleLineRequisition(respFileName, "VAT", "GSTTax","5","12");
	}

	/**
	 * Verifies that the correct external tax type for VAT is returned
	 * CARIBA-969
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void vatExternalTaxTypeTest( )
	{
		String respFileName = "vatExternalTaxTypeTest";

		apiUtil.requestData.setItemDescriptionCommonCommodityCode(1, null, null, "EA");
		apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.SUPPLIER, "Saint-Tropez", "FR", null, null, null,
			"83150", null);
		apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.SHIP_TO, "Paris", "FR", null, null, null, "75007", null);
		apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.BILLING, "Paris", "FR", null, null, null, "75007", null);
		apiUtil.requestData.setItemDescriptionCommonCommodityCode(1, null, null, "EA");

		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForCategoryTaxTypePercentTaxAmtForSingleLineRequisition(respFileName, "VAT", "VATTax","0","0");
	}
}