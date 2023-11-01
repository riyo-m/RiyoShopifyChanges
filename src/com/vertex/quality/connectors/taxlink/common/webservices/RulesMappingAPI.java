package com.vertex.quality.connectors.taxlink.common.webservices;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiBaseTest;
import com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiInitializer;
import com.vertex.quality.connectors.taxlink.api.pojos.RulesMapping;
import com.vertex.quality.connectors.taxlink.common.TaxLinkConstants;
import com.vertex.quality.connectors.taxlink.common.configuration.TaxLinkSettings;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

/**
 * API request builder for Taxlink web services related to pre and post calc rules mapping.
 *
 * @author mgaikwad
 */
public class RulesMappingAPI extends TaxLinkApiBaseTest
{
	public static TaxLinkApiInitializer initializer = TaxLinkApiInitializer.initializeVertextlUIApiTest();
	public static TaxLinkSettings settings = TaxLinkSettings.getTaxLinkSettingsInstance();

	public RulesMappingAPI( )
	{
		requestEndpoint = tlEndpoint.endpoint;
		requestHeadersMap = new HashMap<>();
		queryParamMap = new HashMap<>();
	}

	/**
	 * Build the REST web service header
	 */
	public void buildRequestHeader( )
	{
		requestHeadersMap.put("Content-Type", "application/json");
	}

	/**
	 * adds the required header's key and value in the header map
	 *
	 * @param headerKey   : Name of the Header
	 * @param headerValue : Value of the header
	 */
	public void setReqHeaderParameter( String headerKey, String headerValue )
	{
		if ( headerKey != null )
		{
			requestHeadersMap.put(headerKey, headerValue);
		}
	}

	/**
	 * adds the required header's key and value in the header map
	 *
	 * @param headerKey   : Name of the Header
	 * @param headerValue : Value of the header
	 */
	public void setQueryParameter( String headerKey, String headerValue )
	{
		if ( headerKey != null )
		{
			queryParamMap.put(headerKey, headerValue);
		}
	}

	/**
	 * Send the GET request
	 *
	 * @param pathParam - required path parameters
	 *
	 * @returns restResponse - the response returned from the request.
	 */

	public Response sendGETRequest( String pathParam, String queryParam1, String queryParam2 )
	{
		String url = TaxLinkSettings.getTaxLinkSettingsInstance().taxlink_apiEndPointUrl;
		Response response = sendRequest(pathParam, url, queryParam1, queryParam2);
		return response;
	}

	/**
	 * Sends the GET request with provided path paramters
	 *
	 * @param pathParam
	 *
	 * @return restResponse - response from request hit
	 */
	public Response sendRequest( String pathParam, String endPointUrl, String qParam1, String qParam2 )
	{
		restResponse = given()
			.baseUri(endPointUrl)
			.log()
			.all()
			.auth()
			.preemptive()
			.basic(settings.username, settings.password)
			.contentType(ContentType.JSON)
			.headers(requestHeadersMap)
			.queryParam(qParam1, queryParamMap.get(qParam1))
			.queryParam(qParam2, queryParamMap.get(qParam2))
			.when()
			.get(pathParam)
			.then()
			.log()
			.all()
			.assertThat()
			.statusCode(200)
			.extract()
			.response();
		return restResponse;
	}

	/**
	 * Gets all the pre-rules
	 *
	 * @return boolean
	 **/
	public boolean getAllPreRules( )
	{
		boolean rulesPresentFlag = false;
		buildRequestHeader();
		setQueryParameter(TaxLinkConstants.PHASE_TYPE_PRECALC_QPKey, TaxLinkConstants.PHASE_TYPE_PRECALC_QPVal);
		queryParam1 = TaxLinkConstants.PHASE_TYPE_PRECALC_QPKey;
		queryParam2 = "";

		Response response = sendGETRequest(TaxLinkConstants.RULES_PP, queryParam1, queryParam2);

		boolean preCalcFlag = checkValueIfPresent(response, TaxLinkConstants.PHASE_TYPE_PRECALC_QPKey,
			TaxLinkConstants.PHASE_TYPE_PRECALC_QPVal);

		if ( preCalcFlag )
		{
			rulesPresentFlag = true;
		}
		return rulesPresentFlag;
	}

	/**
	 * Gets condition sets for the pre-rules
	 *
	 * @return boolean
	 **/
	public boolean getConditionSetForPreAndPostRules( )
	{
		boolean conditionsPresentFlag;

		String[] conditionSet = { "VTX DEFAULT CONDITION SET", "VTX_AR_ONLY", "VTX_AP_ONLY", "VTX_PO_ONLY",
			"VTX_OM_ONLY", "VTX_O2C_ONLY", "VTX_P2P_ONLY" };

		buildRequestHeader();
		setQueryParameter(TaxLinkConstants.ENABLED_FLAG_PRECALC_QPKey, TaxLinkConstants.ENABLED_FLAG_PRECALC_QPVal);
		queryParam1 = TaxLinkConstants.ENABLED_FLAG_PRECALC_QPKey;
		queryParam2 = "";

		Response response = sendGETRequest(TaxLinkConstants.CONDITIONSETS_RULES_PP, queryParam1, queryParam2);

		conditionsPresentFlag = checkValuesIfPresent(response, "conditionSetName", conditionSet);
		return conditionsPresentFlag;
	}

	/**
	 * Gets functions for the pre-rules
	 *
	 * @return boolean
	 */
	public boolean getFunctionsForPreRules( )
	{
		boolean functionPresentFlag;
		String[] functions = { "MAP", "UPPER", "LOWER", "CONCAT", "SUBSTRING", "NVL", "CONSTANT", "MAPADDRESS", "SPLIT",
			"TONUMBER" };

		buildRequestHeader();

		Response response = sendGETRequest(TaxLinkConstants.FUNCTIONS_RULES_PP, queryParam1, queryParam2);

		functionPresentFlag = checkValuesIfPresent(response, "functionCode", functions);
		return functionPresentFlag;
	}

	/**
	 * Gets Rule attributes for the pre-rules
	 *
	 * @return boolean
	 */
	public boolean getRuleAttributesForPreRules( )
	{
		boolean attributesPresentFlag;
		String[] attributes = { "accrueOnReceiptFlag", "adjustedDocDate", "adjustedDocNumber",
			"taxlinkTransactionRequestHeader.applicationId", "taxlinkTransactionRequestHeader.applicationShortName",
			"appliedFromDocNumber" };

		buildRequestHeader();
		setQueryParameter(TaxLinkConstants.PHASE_TYPE_PRECALC_QPKey, TaxLinkConstants.PHASE_TYPE_PRECALC_QPVal);
		setQueryParameter(TaxLinkConstants.DATASOURCE_PRECALC_QPKey, TaxLinkConstants.DATASOURCE_PRECALC_QPVal);
		queryParam1 = TaxLinkConstants.PHASE_TYPE_PRECALC_QPKey;
		queryParam2 = TaxLinkConstants.DATASOURCE_PRECALC_QPKey;

		Response response = sendGETRequest(TaxLinkConstants.ATTRIBUTES_RULES_SRC_PP, queryParam1, queryParam2);
		attributesPresentFlag = checkValuesIfPresent(response, "attributeName", attributes);
		return attributesPresentFlag;
	}

	/**
	 * Posts New Rule in the pre-rules
	 *
	 * @return boolean
	 */
	public boolean createNewRulePreRules( int conditionSetId, boolean enabledFlag, String endDate, String functionCode,
		String phaseType, String ruleName, String ruleOrder, int sourceAttributeId, String startDate,
		int targetAttributeId )
	{
		boolean ruleCreatedFlag = false;
		String ruleNameFromResp;

		buildRequestHeader();
		setReqHeaderParameter(TaxLinkConstants.USERNAME, TaxLinkConstants.LOGIN_TEST);
		String pathParam = TaxLinkConstants.RULES_PP;

		RulesMapping rulesMapping = new RulesMapping();
		rulesMapping.setConditionSetId(conditionSetId);
		rulesMapping.setConditionString(null);
		rulesMapping.setConstantValue(null);
		rulesMapping.setEnabledFlag(enabledFlag);
		rulesMapping.setEndDate(endDate);
		rulesMapping.setFunctionCode(functionCode);
		rulesMapping.setPhaseType(phaseType);
		rulesMapping.setRuleName(ruleName);
		rulesMapping.setRuleOrder(ruleOrder);
		rulesMapping.setSourceAttributeId(sourceAttributeId);
		rulesMapping.setSourceAttributeId1(null);
		rulesMapping.setStartDate(startDate);
		rulesMapping.setTargetAttributeId(targetAttributeId);

		String endPointUrl = TaxLinkSettings.getTaxLinkSettingsInstance().taxlink_apiEndPointUrl;

		restResponse = given()
			.baseUri(endPointUrl)
			.log()
			.all()
			.auth()
			.preemptive()
			.basic(settings.username, settings.password)
			.contentType(ContentType.JSON)
			.headers(requestHeadersMap)
			.body(rulesMapping)
			.when()
			.post(pathParam)
			.then()
			.log()
			.all()
			.assertThat()
			.statusCode(200)
			.extract()
			.response();

		JsonPath jsonPath = restResponse.jsonPath();
		ruleNameFromResp = jsonPath.getString("ruleName");
		VertexLogger.log("Rule created with rule name: " + ruleNameFromResp);

		if ( ruleName.equals(ruleNameFromResp) )
		{
			VertexLogger.log("Rule verified in the response: " + ruleName);
			ruleCreatedFlag = true;
		}
		return ruleCreatedFlag;
	}

	/**
	 * Gets all the post-rules
	 *
	 * @return boolean
	 **/
	public boolean getAllPostRules( )
	{
		boolean rulesPresentFlag = false;
		buildRequestHeader();
		setQueryParameter(TaxLinkConstants.PHASE_TYPE_POSTCALC_QPKey, TaxLinkConstants.PHASE_TYPE_POSTCALC_QPVal);
		queryParam1 = TaxLinkConstants.PHASE_TYPE_POSTCALC_QPKey;
		queryParam2 = "";

		Response response = sendGETRequest(TaxLinkConstants.RULES_PP, queryParam1, queryParam2);

		boolean postCalcFlag = checkValueIfPresent(response, TaxLinkConstants.PHASE_TYPE_POSTCALC_QPKey,
			TaxLinkConstants.PHASE_TYPE_POSTCALC_QPVal);

		if ( postCalcFlag )
		{
			rulesPresentFlag = true;
		}
		return rulesPresentFlag;
	}

	/**
	 * Gets functions for the post-rules
	 *
	 * @return boolean
	 */
	public boolean getFunctionsForPostRules( )
	{
		boolean functionPresentFlag;
		String[] functions = { "MAP", "SUBSTRING" };

		buildRequestHeader();

		Response response = sendGETRequest(TaxLinkConstants.FUNCTIONS_RULES_PP, queryParam1, queryParam2);

		functionPresentFlag = checkValuesIfPresent(response, "functionCode", functions);
		return functionPresentFlag;
	}

	/**
	 * Gets Rule attributes for the post-rules
	 *
	 * @return boolean
	 */
	public boolean getRuleAttributesForPostRules( )
	{
		boolean attributesPresentFlag;
		String[] attributes = { "Admin Destination Tax Area Id", "Admin Origin Tax Area Id", "Destination Tax Area Id",
			"Flexcode 18", "Flexcode 19", "Flexcode 9", "Physical Origin Tax Area Id", "Situs" };

		buildRequestHeader();
		setQueryParameter(TaxLinkConstants.PHASE_TYPE_POSTCALC_QPKey, TaxLinkConstants.PHASE_TYPE_POSTCALC_QPVal);
		setQueryParameter(TaxLinkConstants.DATASOURCE_POSTCALC_QPKey, TaxLinkConstants.DATASOURCE_POSTCALC_QPVal);
		queryParam1 = TaxLinkConstants.PHASE_TYPE_POSTCALC_QPKey;
		queryParam2 = TaxLinkConstants.DATASOURCE_POSTCALC_QPKey;

		Response response = sendGETRequest(TaxLinkConstants.ATTRIBUTES_RULES_SRC_PP, queryParam1, queryParam2);
		attributesPresentFlag = checkValuesIfPresent(response, "attributeDisplayName", attributes);
		return attributesPresentFlag;
	}

	/**
	 * Posts New Rule in the post-rules
	 *
	 * @return boolean
	 */
	public boolean createNewRulePostRules( int conditionSetId, boolean enabledFlag, String functionCode, String endDate,
		String phaseType, String ruleName, String ruleOrder, int sourceAttributeId, String startDate,
		int targetAttributeId )
	{
		boolean ruleCreatedFlag = false;
		String ruleNameFromResp;

		buildRequestHeader();
		setReqHeaderParameter(TaxLinkConstants.USERNAME, TaxLinkConstants.LOGIN_TEST);

		String pathParam = TaxLinkConstants.RULES_PP;

		RulesMapping rulesMapping = new RulesMapping();
		rulesMapping.setConditionSetId(conditionSetId);
		rulesMapping.setConditionString(null);
		rulesMapping.setConstantValue(null);
		rulesMapping.setEnabledFlag(enabledFlag);
		rulesMapping.setEndDate(endDate);
		rulesMapping.setFunctionCode(functionCode);
		rulesMapping.setPhaseType(phaseType);
		rulesMapping.setRuleName(ruleName);
		rulesMapping.setRuleOrder(ruleOrder);
		rulesMapping.setSourceAttributeId(sourceAttributeId);
		rulesMapping.setSourceAttributeId1(null);
		rulesMapping.setStartDate(startDate);
		rulesMapping.setTargetAttributeId(targetAttributeId);

		String endPointUrl = TaxLinkSettings.getTaxLinkSettingsInstance().taxlink_apiEndPointUrl;

		restResponse = given()
			.baseUri(endPointUrl)
			.log()
			.all()
			.auth()
			.preemptive()
			.basic(settings.username, settings.password)
			.contentType(ContentType.JSON)
			.headers(requestHeadersMap)
			.body(rulesMapping)
			.when()
			.post(pathParam)
			.then()
			.log()
			.all()
			.assertThat()
			.statusCode(200)
			.extract()
			.response();

		JsonPath jsonPath = restResponse.jsonPath();
		ruleNameFromResp = jsonPath.getString("ruleName");
		VertexLogger.log("Rule created with rule name: " + ruleNameFromResp);

		if ( ruleName.equals(ruleNameFromResp) )
		{
			VertexLogger.log("Rule verified in the response: " + ruleName);
			ruleCreatedFlag = true;
		}
		return ruleCreatedFlag;
	}
}
