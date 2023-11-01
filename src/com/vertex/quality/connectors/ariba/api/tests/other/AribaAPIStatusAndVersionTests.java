package com.vertex.quality.connectors.ariba.api.tests.other;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.common.enums.VertexHTTPMethod;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIRequestType;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIType;
import com.vertex.quality.connectors.ariba.api.tests.base.AribaAPIBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Tests for status and version
 *
 * @author hho osabha, vgosavi
 */

public class AribaAPIStatusAndVersionTests extends AribaAPIBaseTest
{

	@Override
	protected AribaAPIType getDefaultAPIType( ) { return null; }

	@Override
	protected AribaAPIRequestType getDefaultAPIRequestType( ) { return null; }
	/**
	 * Gets the status and confirms that it's ok
	 * CARIBA-425
	 */
	@Test(groups = { "ariba_api","ariba_smoke","ariba_regression" })
	public void aribaConnectorStatusTest( )
	{
		apiUtil.setBasicAuth("ariba_connector", "ConnectorAriba01");
		String okStatus = "\"OK\"";
		Response response = apiUtil.sendRequest(VertexHTTPMethod.GET, apiUtil.getStatusUrl());

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode());

		String status = response
			.body()
			.asString();
		assertEquals(status, okStatus);
	}

	/**
	 * Gets the version and confirm that it's at the least version 2.1.0.x
	 * CARIBA-1028
	 */
	@Test(groups = { "ariba_api","ariba_smoke","ariba_regression" })
	public void aribaConnectorVersionTest( )
	{
		String version2x = "2.1.19-SNAPSHOT";
		Response response = apiUtil.sendRequest(VertexHTTPMethod.GET, apiUtil.getVersionUrl());

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode());

		String version = response
			.body()
			.asString();
		assertTrue(version != null);
		assertTrue(version.contains(version2x));
	}
}