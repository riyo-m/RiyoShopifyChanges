package com.vertex.quality.connectors.taxlink.common.webservices;

import com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiBaseTest;
import com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiInitializer;
import com.vertex.quality.connectors.taxlink.common.TaxLinkConstants;
import com.vertex.quality.connectors.taxlink.common.configuration.TaxLinkSettings;
import io.restassured.http.ContentType;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

/**
 * API request builder for Taxlink web services related to clean up of Production
 * instance.
 *
 * @author mgaikwad
 */
public class ProdInstanceCleanUpAPI extends TaxLinkApiBaseTest
{
	public static TaxLinkApiInitializer initializer = TaxLinkApiInitializer.initializeVertextlUIApiTest();
	public static TaxLinkSettings settings = TaxLinkSettings.getTaxLinkSettingsInstance();

	public ProdInstanceCleanUpAPI( )
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
	 * Clean Production instance
	 *
	 * @return
	 */
	public boolean cleanProductionInstance( )
	{
		boolean cleanFlag = false;
		buildRequestHeader();
		setReqHeaderParameter(TaxLinkConstants.USERNAME, TaxLinkConstants.LOGIN_TEST);
		String pathParam = TaxLinkConstants.INSTANCE_PP;

		String endPointUrl = TaxLinkSettings.getTaxLinkSettingsInstance().taxlink_apiEndPointUrl;

		restResponse = given()
			.baseUri(endPointUrl)
			.queryParam("prodInstanceCleanup", "true")
			.log()
			.all()
			.auth()
			.preemptive()
			.basic(settings.username, settings.password)
			.contentType(ContentType.JSON)
			.headers(requestHeadersMap)
			.when()
			.post(pathParam)
			.then()
			.log()
			.all()
			.extract()
			.response();
		if ( restResponse.getStatusCode() == 200 )
		{
			cleanFlag = true;
		}
		return cleanFlag;
	}
}
