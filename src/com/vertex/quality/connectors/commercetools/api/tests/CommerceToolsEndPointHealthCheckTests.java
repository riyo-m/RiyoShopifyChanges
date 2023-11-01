package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CSAPCT-216...CCT- Perform a Healthcheck - Endpoint URL
 * this test case sends a request for CommerceTools Endpoint health check.
 *
 * @author vivek_kumar
 */

public class CommerceToolsEndPointHealthCheckTests extends CommerceToolAPITestUtilities {

	/**
	 * Test case for CommerceTools Endpoint Url health check .
	 */
	@Test(groups = { "commercetools_api_smoke" })
	public void sendHealthCheckEndpointTests()
	{
		validateToken();
		final String status = "OK";
		Response response = healthCheckEndpoint(commerceToolsAccessToken);
		assertStatus(response, ResponseCodes.SUCCESS);
		JsonPath extractor = response.jsonPath();
		String healthStatus = extractor.get("status");
		Assert.assertEquals(healthStatus, status);
	}
}