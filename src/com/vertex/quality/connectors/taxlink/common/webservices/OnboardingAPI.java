package com.vertex.quality.connectors.taxlink.common.webservices;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiBaseTest;
import com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiInitializer;
import com.vertex.quality.connectors.taxlink.common.TaxLinkConstants;
import com.vertex.quality.connectors.taxlink.common.configuration.TaxLinkSettings;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

/**
 * API request builder for Taxlink web services related to onboarding.
 *
 * @author msalomone
 */
public class OnboardingAPI extends TaxLinkApiBaseTest
{
	public static TaxLinkApiInitializer initializer = TaxLinkApiInitializer.initializeVertextlUIApiTest();
	public static TaxLinkSettings settings = TaxLinkSettings.getTaxLinkSettingsInstance();

	public OnboardingAPI( )
	{
		requestEndpoint = tlEndpoint.endpoint;
		requestHeadersMap = new HashMap<>();
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
	 * @param headerKey : Name of the Header
	 * @param headerValue : Value of the header
	 */
	public void setReqHeaderParameter(String headerKey, String headerValue ){
		if(headerKey!=null){
			requestHeadersMap.put(headerKey, headerValue);
		}
	}

    /**
     * Send the GET request
     *
	 * @param pathParam - required path parameters
     * @returns restResponse - the response returned from the request.
     */
	public Response sendGETRequest(String pathParam)
	{
		String url = TaxLinkSettings.getTaxLinkSettingsInstance().taxlink_apiEndPointUrl;
		Response response = sendRequest(pathParam, url);
		return response;
	}

	/**Sends the GET request with provided path paramters
	 * @param  pathParam
	 * @param  endPointUrl
	 * @return restResponse - response from request hit
	 * */
	private Response sendRequest( String pathParam, String endPointUrl )
	{
		restResponse = given().baseUri(endPointUrl).log().all().auth().preemptive().basic(settings.username, settings.password)
							  .contentType(ContentType.JSON).headers(requestHeadersMap)
							  .when()
							  .get(pathParam)
							  .then()
							  .extract().response();
		return restResponse;
	}

	/**
	 * Retrieve details pertaining to a specific fusion instance
	 * onboarded in Taxlink
	 *
	 * @return onboardedInstance
	 */
	public String getOnboardedInstance( )
	{
		buildRequestHeader();
		Response response = sendGETRequest(TaxLinkConstants.ORACLE_ERP_CLD_DEV3_PP);
		VertexLogger.log(response.asPrettyString());

		int statusCode = response.getStatusCode();
		VertexLogger.log("Status Code : " + statusCode);

		String onboardedInstance = "";
		JsonPath jsonPath = response.jsonPath();
		if ( jsonPath != null )
		{
			onboardedInstance = jsonPath.get("oracleERPCloudInstances[0].instanceIdentifier");
			VertexLogger.log("OnboardedInstance : " + onboardedInstance);
		}
		return onboardedInstance;
	}

	/**
	 * Gets the Oseries Url
	 *
	 * @return endPointUrl
	 **/
	public String getOSeriesUrl( )
	{
		buildRequestHeader();
		setReqHeaderParameter(TaxLinkConstants.HOST_TYPE, TaxLinkConstants.PC);

		Response response = sendGETRequest(TaxLinkConstants.ERP_OSERIES_URL_PP);
		VertexLogger.log(response.asPrettyString());

		int statusCode = response.getStatusCode();
		VertexLogger.log("Status Code : " + statusCode);

		String endPointUrl = "";
		JsonPath jsonPathEvaluator = response.jsonPath();
		if ( jsonPathEvaluator != null )
		{
			endPointUrl = jsonPathEvaluator.get("oseriesUrl");
		}
		return endPointUrl;
	}

	/**
	 * Gets the Oracle Erp Data center data
	 *
	 * @return valuePresentFlag
	 */
	public boolean getOracleErpDataCenters( )
	{
		boolean valuePresentFlag = false;
		buildRequestHeader();

		Response response = sendGETRequest(TaxLinkConstants.ORACLE_ERP_DATACENTER_PP);
		VertexLogger.log(response.asPrettyString());

		int statusCode = response.getStatusCode();
		VertexLogger.log("Status Code : " + statusCode);

		valuePresentFlag = checkValueIfPresent(response, TaxLinkConstants.DATA_CENTER_LONG_NAME,
			TaxLinkConstants.CHICAGO_US_COMM_2);

		return valuePresentFlag;
	}

	/**
	 * Gets the Customer Name
	 *
	 * @return customername
	 **/
	public String getCustomerName( )
	{
		buildRequestHeader();
		setReqHeaderParameter(TaxLinkConstants.USERNAME, TaxLinkConstants.LOGIN_TEST);

		Response response = sendGETRequest(TaxLinkConstants.ERP_CUSTOMERNAME_PP);
		VertexLogger.log(response.asPrettyString());

		int statusCode = response.getStatusCode();
		VertexLogger.log("Status Code : " + statusCode);

		String customername = response
			.getBody()
			.asString();
		return customername;
	}

	/**
	 * Gets the Services Name
	 *
	 * @return servicesPresentFlag value
	 */
	public boolean getServiceName( )
	{
		boolean servicesPresentFlag = false;
		buildRequestHeader();

		Response response = sendGETRequest(TaxLinkConstants.ORACLE_ERP_APP_PP);
		VertexLogger.log(response.asPrettyString());

		int statusCode = response.getStatusCode();
		VertexLogger.log("Status Code : " + statusCode);

		boolean acc_ReceivableFlag = checkValueIfPresent(response, TaxLinkConstants.SERVICE_DESCRIPTION,
			TaxLinkConstants.ACC_RECEIVABLE);
		boolean acc_PayableFlag = checkValueIfPresent(response, TaxLinkConstants.SERVICE_DESCRIPTION,
			TaxLinkConstants.ACC_PAYABLE);
		boolean purchasingFlag = checkValueIfPresent(response, TaxLinkConstants.SERVICE_DESCRIPTION,
			TaxLinkConstants.PURCHASING);
		boolean orderManagementFlag = checkValueIfPresent(response, TaxLinkConstants.SERVICE_DESCRIPTION,
			TaxLinkConstants.ORDER_MANAGEMENT);

		if ( acc_ReceivableFlag && acc_PayableFlag && purchasingFlag && orderManagementFlag )
		{
			servicesPresentFlag = true;
		}
		return servicesPresentFlag;
	}
}
