package com.vertex.quality.connectors.taxlink.api.base;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.api.enums.TaxLinkEndpoints;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class TaxLinkApiBaseTest
{
	public TaxLinkEndpoints tlEndpoint = TaxLinkEndpoints.OERPCLOUDINSTANCES;
	public String requestEndpoint;
	public Response restResponse;
	public Map<String, String> requestHeadersMap;
	public Map<String, String> queryParamMap;
	public String queryParam1 = "";
	public String queryParam2 = "";

	/**
	 * Checks if provided value is present in json response
	 *
	 * @param response  response received
	 * @param nodeName  jsonObject nodeName
	 * @param nodeValue jsonObject's nodeValue
	 *
	 * @return isValuePresent returns true if value is present
	 */
	public boolean checkValueIfPresent( Response response, String nodeName, String nodeValue )
	{
		JsonPath jsonPath = response.jsonPath();
		boolean isValuePresent = false;
		if ( jsonPath != null )
		{
			List<JSONObject> jsonArrayList = jsonPath.getJsonObject(nodeName);
			for ( Object obj : jsonArrayList )
			{
				String objStringValue = obj.toString();
				if ( objStringValue.equalsIgnoreCase(nodeValue) )
				{
					isValuePresent = true;
					break;
				}
			}
		}
		return isValuePresent;
	}

	/**
	 * Checks if provided values are present in json response
	 *
	 * @param response   response received
	 * @param nodeName   jsonObject nodeName
	 * @param nodeValues jsonObject's nodeValues
	 *
	 * @return isValuePresent returns true if values are present
	 */
	public boolean checkValuesIfPresent( Response response, String nodeName, String[] nodeValues )
	{
		JsonPath jsonPath = response.jsonPath();
		boolean isValuePresent = false;
		int i = 0;

		while ( i != nodeValues.length )
		{
			List<JSONObject> jsonArrayList = jsonPath.getJsonObject(nodeName);
			for ( Object obj : jsonArrayList )
			{
				String objStringValue = obj.toString();
				if ( objStringValue.equalsIgnoreCase(nodeValues[i]) )
				{
					VertexLogger.log(nodeName + " is present in response : " + objStringValue);
					isValuePresent = true;
					break;
				}
			}
			i++;
		}
		return isValuePresent;
	}
}
