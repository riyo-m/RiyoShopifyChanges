package com.vertex.quality.connectors.taxlink.common.webservices;

import com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiBaseTest;
import com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiInitializer;
import com.vertex.quality.connectors.taxlink.api.pojos.TaxActionRanges;
import com.vertex.quality.connectors.taxlink.common.TaxLinkConstants;
import com.vertex.quality.connectors.taxlink.common.configuration.TaxLinkSettings;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * This class contains all the methods for Tax Action Ranges API tests
 *
 * @author Shilpi.Verma
 */

public class TaxActionRangesAPI extends TaxLinkApiBaseTest
{
	public static TaxLinkApiInitializer initializer = TaxLinkApiInitializer.initializeVertextlUIApiTest();
	public static TaxLinkSettings settings = TaxLinkSettings.getTaxLinkSettingsInstance();

	public TaxActionRangesAPI( )
	{
		requestEndpoint = tlEndpoint.endpoint;
	}

	/**
	 * Sends GET request for fetching details based on path parameters and end points
	 *
	 * @param pathParam
	 * @param endPointUrl
	 *
	 * @return
	 */
	public Response sendGetRequest_TaxActionRanges( String pathParam, String endPointUrl )
	{
		restResponse = given()
			.baseUri(endPointUrl)
			.log()
			.headers()
			.auth()
			.preemptive()
			.basic(settings.username, settings.password)
			.contentType(ContentType.JSON)
			.when()
			.get(pathParam)
			.then()
			.log()
			.status()
			.assertThat()
			.statusCode(200)
			.extract()
			.response();

		return restResponse;
	}

	/**
	 * Send the GET request
	 *
	 * @param pathParam - required path parameters
	 *
	 * @returns restResponse - the response returned from the request.
	 */

	public Response sendGETRequest_Summary( String pathParam )
	{
		String url = TaxLinkSettings.getTaxLinkSettingsInstance().taxlink_apiEndPointUrl;
		Response response = sendRequest_SummaryTable(pathParam, url);

		return response;
	}

	/**
	 * Sends the GET request with provided path paramters
	 *
	 * @param pathParam
	 *
	 * @return restResponse - response from request hit
	 */
	public Response sendRequest_SummaryTable( String pathParam, String endPointUrl )
	{
		restResponse = sendGetRequest_TaxActionRanges(pathParam, endPointUrl);

		List<String> keys = Arrays.asList("enabledFlag", "endDate", "startDate", "holdFlag", "holdReasonCode",
			"rangeFromValue", "rangeToValue", "taxResultAction");

		JSONArray JSONResponseBody = new JSONArray(restResponse
			.body()
			.asString());

		assertFalse(JSONResponseBody
			.getJSONObject(0)
			.toString()
			.isEmpty());

		assertFalse(JSONResponseBody
			.getJSONObject(0)
			.get("taxActionSetupHeader")
			.toString()
			.isEmpty());

		assertTrue(JSONResponseBody
			.getJSONObject(0)
			.keySet()
			.containsAll(keys));

		return restResponse;
	}

	/**
	 * send GET request for fetching details, required for adding tax actions
	 *
	 * @param pathParam
	 *
	 * @return
	 */
	public Response sendGETRequest_AddTaxActionRanges( String pathParam )
	{
		String url = TaxLinkSettings.getTaxLinkSettingsInstance().taxlink_apiEndPointUrl;
		Response response = sendGetRequest_TaxActionRanges(pathParam, url);

		return response;
	}

	/**
	 * send POST request for adding tax actions
	 *
	 * @param pathParam
	 * @param res1
	 * @param res2
	 * @param res3
	 * @param res4
	 * @param res5
	 * @param taxDeterminationResultCode
	 * @param taxResultAction
	 *
	 * @return
	 */
	public Response sendPostRequest_AddTaxActionRanges( String pathParam, Response res1, Response res2, Response res3,
		Response res4, Response res5, String taxDeterminationResultCode, String taxResultAction )
	{
		String url = TaxLinkSettings.getTaxLinkSettingsInstance().taxlink_apiEndPointUrl;
		Response response = sendRequest_AddTaxActionRange(pathParam, url, res1, res2, res3, res4, res5,
			taxDeterminationResultCode, taxResultAction);

		return response;
	}

	/**
	 * Method to get particular key-value from JSON array
	 *
	 * @param jsonArrayStr
	 * @param key
	 *
	 * @return
	 */
	public List<String> getValuesForGivenKey( String jsonArrayStr, String key )
	{
		JSONArray jsonArray = new JSONArray(jsonArrayStr);

		return IntStream
			.range(0, jsonArray.length())
			.mapToObj(index -> ((JSONObject) jsonArray.get(index)).optString(key))
			.collect(Collectors.toList());
	}

	/**
	 * send POST request for adding tax actions
	 *
	 * @param pathParam
	 * @param endPointUrl
	 * @param res1
	 * @param res2
	 * @param res3
	 * @param res4
	 * @param res5
	 * @param taxDeterminationResultCode
	 * @param taxResultAction
	 *
	 * @return
	 */
	public Response sendRequest_AddTaxActionRange( String pathParam, String endPointUrl, Response res1, Response res2,
		Response res3, Response res4, Response res5, String taxDeterminationResultCode, String taxResultAction )
	{

		List<String> leName_list = getValuesForGivenKey(res1
			.body()
			.asString(), "lookupCodeDesc");
		List<String> leId_list = getValuesForGivenKey(res2
			.body()
			.asString(), "lookupCode");

		List<String> buName_list = getValuesForGivenKey(res3
			.body()
			.asString(), "businessUnitName");
		List<String> buId_list = getValuesForGivenKey(res4
			.body()
			.asString(), "businessUnitId");

		List<String> ta_apInvSrc_list = getValuesForGivenKey(res5
			.body()
			.asString(), "lookupCode");

		TaxActionRanges taPojo = new TaxActionRanges();
		taPojo.setBusinessUnitId(buId_list.get(0));
		taPojo.setBusinessUnitName(buName_list.get(0));
		taPojo.setCountryCode(null);
		taPojo.setEnabledFlag("Y");
		taPojo.setEndDate(null);
		taPojo.setFusionInstanceId(null);
		taPojo.setHoldFlag("Y");
		taPojo.setHoldReasonCode(null);
		taPojo.setInvoiceSource(ta_apInvSrc_list.get(0));
		taPojo.setLegalEntityId(leId_list.get(0));
		taPojo.setLegalEntityName(leName_list.get(0));
		taPojo.setRangeFromValue("1");
		taPojo.setRangeToValue("100");
		taPojo.setRangeType("AMOUNT");
		taPojo.setStartDate("1900-01-01");
		taPojo.setTaxActionSetupDtlId(null);
		taPojo.setTaxActionSetupHdrId(null);
		taPojo.setTaxDeterminationResultCode(taxDeterminationResultCode);
		taPojo.setTaxResultAction(taxResultAction);

		List<TaxActionRanges> jsonArray = new ArrayList<>();
		jsonArray.add(taPojo);

		restResponse = given()
			.baseUri(endPointUrl)
			.log()
			.all()
			.auth()
			.preemptive()
			.basic(settings.username, settings.password)
			.contentType(ContentType.JSON)
			.body(jsonArray)
			.when()
			.post(pathParam)
			.then()
			.log()
			.all()
			.assertThat()
			.statusCode(200)
			.extract()
			.response();

		JSONArray JSONResponseBody = new JSONArray(restResponse
			.body()
			.asString());

		Map<Integer, String> resArr = new HashMap<>();
		resArr.put(1, "enabledFlag");
		resArr.put(2, "rangeFromValue");
		resArr.put(3, "rangeToValue");
		resArr.put(4, "taxResultAction");

		if ( JSONResponseBody
				 .getJSONObject(0)
				 .get(resArr.get(1))
				 .equals("Y") && JSONResponseBody
				 .getJSONObject(0)
				 .get(resArr.get(2))
				 .equals(1) && JSONResponseBody
				 .getJSONObject(0)
				 .get(resArr.get(3))
				 .equals(100) && JSONResponseBody
				 .getJSONObject(0)
				 .get(resArr.get(4))
				 .equals(taxResultAction) )
		{
			assertTrue(JSONResponseBody
				.getJSONObject(0)
				.getJSONObject("taxActionSetupHeader")
				.get("vtxTaxDetResultCode")
				.equals(taxDeterminationResultCode));
		}

		return restResponse;
	}

	/**
	 * Gets summary table of Tax Action Ranges
	 *
	 * @return boolean
	 **/
	public void getSummaryTaxActionRanges( )
	{

		sendGETRequest_Summary(TaxLinkConstants.TAX_ACTION_RANGE_SUMMARY);
	}

	/**
	 * Adds new records for Tax Actions
	 *
	 * @param s1
	 * @param taxDeterminationResultCode
	 * @param taxResultAction
	 */
	public void addTaxActionRanges( String s1, String taxDeterminationResultCode, String taxResultAction )
	{
		Response le_response = sendGETRequest_AddTaxActionRanges(TaxLinkConstants.TAX_ACTION_RANGE_LE_LIST);
		Response bu_response = sendGETRequest_AddTaxActionRanges(TaxLinkConstants.TAX_ACTION_RANGE_BU_LIST);
		Response apInvSrc_response = sendGETRequest_AddTaxActionRanges(
			TaxLinkConstants.TAX_ACTION_RANGE_AP_INV_SRC_LIST);

		sendPostRequest_AddTaxActionRanges(TaxLinkConstants.TAX_ACTION_RANGE_SUMMARY, le_response, le_response,
			bu_response, bu_response, apInvSrc_response, taxDeterminationResultCode, taxResultAction);
	}
}
