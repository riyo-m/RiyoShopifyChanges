package com.vertex.quality.connectors.vertexrestapi.common.tests;

import com.vertex.quality.common.tests.VertexAPIBaseTest;
import com.vertex.quality.connectors.vertexrestapi.common.utils.VertexRestAPITestUtilities;

/**
 * Base test for Vertex Rest API
 * Used mainly to retrieve the authentication token so that we can send our
 * requests. Other methods in VertexUIBaseTest will be overridden since we do
 * not need any sort of driver service.
 *
 * @author hho
 */
public abstract class VertexRestAPIBaseTest extends VertexAPIBaseTest
{
	protected VertexRestAPITestUtilities apiUtil;

	//don't modify this dynamically- its value is based on the value in the api test utility object
	protected String baseUrl;

	/**
	 * Retrieves a new api token from the server before every test so requests can be made successfully.
	 */
	@Override
	protected void setupTestCase( )
	{
		this.apiUtil = new VertexRestAPITestUtilities();
		this.baseUrl = apiUtil.getBaseUrl();
	}
}
