package com.vertex.quality.common.utils;

import com.vertex.quality.common.enums.Endpoint;
import com.vertex.quality.common.utils.api.VertexPojoToJsonUtil;
import com.vertex.quality.common.utils.misc.VertexJsonFileUtils;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;

import static io.restassured.RestAssured.given;

/**
 * Utility to create API requests
 *
 * @author hho
 */
public class APIRequestBuilder
{
	public static class Builder
	{
		private String baseURL;
		private String jsonString;
		private Endpoint endpoint;

		public Builder( String baseURL, Endpoint endpoint, Object pojoInstance )
		{
			this.baseURL = baseURL;
			this.endpoint = endpoint;

			JSONObject jsonObject = VertexPojoToJsonUtil.toJson(pojoInstance);
			this.jsonString = jsonObject.toJSONString();
		}

		/**
		 * Adds or replaces the JSON field key with a new value of the given JSON object
		 *
		 * @param jsonPath              the path to add the new value to, excludes the field's (key) name
		 * @param fieldName             the field's name (the key in the key:value pair)
		 * @param fieldMembersAndValues the JSON object
		 */
		public Builder addOrReplaceJsonObject( String jsonPath, String fieldName, JSONObject fieldMembersAndValues )
		{
			jsonString = VertexJsonFileUtils.addJsonFieldsToJsonString(jsonString, jsonPath, fieldName,
				fieldMembersAndValues);

			return this;
		}

		/**
		 * Adds or replaces the JSON field key with a new value of the given JSON field value
		 *
		 * @param jsonPath   the path to add the new value to, excludes the field's (key) name
		 * @param fieldName  the field's name (the key in the key:value pair)
		 * @param fieldValue the field's value
		 */
		public Builder addOrReplaceJsonField( String jsonPath, String fieldName, Object fieldValue )
		{
			jsonString = VertexJsonFileUtils.addJsonFieldsToJsonString(jsonString, jsonPath, fieldName, fieldValue);

			return this;
		}

		/**
		 * Removes a JSON field
		 *
		 * @param fullJsonPath the path to remove the field from, including the field's name
		 */
		public Builder removeJsonField( String fullJsonPath )
		{
			jsonString = VertexJsonFileUtils.removeJsonFieldsFromJsonString(jsonString, fullJsonPath);

			return this;
		}

		/**
		 * Creates the request, sends it, then gets back the response
		 *
		 * @return the response to the created request
		 */
		public Response createRequestAndGetResponse( )
		{
			return given()
				.filter(new RequestLoggingFilter())
				.filter(new ResponseLoggingFilter())
				.contentType(ContentType.JSON)
				.body(this.jsonString)
				.when()
				.post(baseURL + "/" + this.endpoint.getEndpoint());
		}
	}
}
