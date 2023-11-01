package com.vertex.quality.connectors.vertexrestapi.common.tests;

import com.vertex.quality.common.utils.misc.VertexJsonFileUtils;
import com.vertex.quality.connectors.vertexrestapi.common.enums.EndPoint;
import com.vertex.quality.connectors.vertexrestapi.common.utils.VertexRestAPIJsonUtils;
import com.vertex.quality.connectors.vertexrestapi.common.utils.VertexRestAPITestUtilities;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;

/**
 * This class is used to build tests easily.
 *
 * @author hho
 */
public class VertexRestAPITest extends VertexRestAPIBaseTest
{
	private String jsonString;
	private EndPoint endPoint;

	private VertexRestAPITest( final VertexRestAPITestUtilities apiUtil, final String jsonString,
		final EndPoint endPoint )
	{
		this.apiUtil = apiUtil;
		this.jsonString = jsonString;
		this.endPoint = endPoint;
	}

	public VertexRestAPITest( )
	{
	}

	/**
	 * Creates the request based on the parameters given so far
	 *
	 * @return the built request
	 */
	public Response createRequest( )
	{
		return apiUtil.buildRequestBasedOnEndPointAndString(this.endPoint, this.jsonString);
	}

	/**
	 * This inner class is the class that actually sets up the test case.
	 * It sets required parameters such as what the input of the test should be.
	 * Any JSON file that is passed in is not modified, it is converted into a string and the
	 * resulting string is modified
	 *
	 * @author hho
	 */
	public static class Builder
	{
		private String jsonString;

		private VertexRestAPITestUtilities apiUtil;
		private String fileName;
		private EndPoint endPoint;

		/**
		 * The test case builder. This builder requires 2 default parameters in order to
		 * successfully run any test case
		 *
		 * @param endPoint the end point to send to
		 * @param filename the JSON file name (this file is never modified)
		 */
		public Builder( final VertexRestAPITestUtilities apiUtil, final EndPoint endPoint, final String filename )
		{
			this.apiUtil = apiUtil;
			this.endPoint = endPoint;
			this.fileName = filename;

			this.jsonString = VertexRestAPIJsonUtils.getRestApiStringFromJsonFile(endPoint, fileName);
		}

		/**
		 * Adds a JSON field to the given JSON file before sending
		 *
		 * @param jsonPath              the path to add the field to, excludes the field's name
		 * @param fieldName             the field's name
		 * @param fieldMembersAndValues the field's members and its values
		 */
		public Builder addJsonField( final String jsonPath, final String fieldName,
			final JSONObject fieldMembersAndValues )
		{
			jsonString = VertexJsonFileUtils.addJsonFieldsToJsonString(jsonString, jsonPath, fieldName,
				fieldMembersAndValues);

			return this;
		}

		/**
		 * Adds a JSON field to the given JSON file before sending. This can be used to replace or
		 * add a new field
		 *
		 * @param jsonPath   the path to add the field to, excludes the field's name
		 * @param fieldName  the field's name
		 * @param fieldValue the field's value
		 */
		public Builder addJsonField( final String jsonPath, final String fieldName, final Object fieldValue )
		{
			jsonString = VertexJsonFileUtils.addJsonFieldsToJsonString(jsonString, jsonPath, fieldName, fieldValue);

			return this;
		}

		/**
		 * Removes a JSON field from the given JSON file before sending
		 *
		 * @param fullJsonPath the path to remove the field from, including the field's name
		 */
		public Builder removeJsonField( String fullJsonPath )
		{
			jsonString = VertexJsonFileUtils.removeJsonFieldsFromJsonString(jsonString, fullJsonPath);

			return this;
		}

		/**
		 * Builds this test case
		 *
		 * @return the test case
		 */
		public VertexRestAPITest build( )
		{
			return new VertexRestAPITest(this.apiUtil, jsonString, this.endPoint);
		}
	}
}
