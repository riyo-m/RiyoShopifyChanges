package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
/**
 * CSAPCT-200...Validate the Tax Calculation Endpoint for Version.
 * this test case sends a request for CommerceTools versions check.
 *
 * @author vivek_kumar
 */
public class CommerceToolsVersionTests extends CommerceToolAPITestUtilities
{

	/**
	 * Test case for CommerceTools Endpoint Url version check.
	 */
	@Test (groups = { "commercetools_api_smoke" })
	public void sendVersionCheckEndpointTests( )
	{
		validateToken();
		final String version = "2.0.0";
		Response response = versionCheckEndpoint(commerceToolsAccessToken);
		assertStatus(response, ResponseCodes.SUCCESS);
		JsonPath extractor = response.jsonPath();
		String commerceToolVersion = extractor.get("version");
		Assert.assertEquals(commerceToolVersion, version);
	}
}
