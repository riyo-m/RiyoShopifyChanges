package com.vertex.quality.connectors.bigcommerce.api.tests.config;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.bigcommerce.api.tests.base.BigCommerceAPIBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * basic tests of the config endpoint's methods for fetching the connector's configurations
 *
 * @author ssalisbury
 */
public class BigCommerceAPIConfigMiscTests extends BigCommerceAPIBaseTest
{
	protected static final String defaultTrustedId = "2473771143855743";

	/**
	 * tests that the connector can be queried for all configurations which share a given trusted id
	 */
	@Test(groups = { "bigCommerce_apiDisabled","bigCommerce_config", "bigCommerce_regression" })
	public void getConfigsTest( )
	{
		Response response = apiUtil.sendConfigsGetRequest(defaultTrustedId);
		apiUtil.assertStatus(response, ResponseCodes.SUCCESS);
		List<String> storeConfigs = response
			.then()
			.extract()
			.jsonPath()
			.getList("$");
		boolean receivedConfigs = !storeConfigs.isEmpty();
		assertTrue(receivedConfigs);
		//TODO check whether any configs are actually contained in the request body
		// I think this kind of thing makes it start looking potentially worthwhile to make pojos
		// representing the response structures so the responses can be deserialized
		// and validated in more complicated cases without relying on details of
		// string literal json paths
	}

	/**
	 * tests that the connector can be queried for a particular one of its configurations
	 */
	@Test(groups = { "bigCommerce_api"})
	public void getConfigTest( )
	{
		final String oneByteConfigId = "7";
		Response response1 = apiUtil.sendConfigGetRequest(oneByteConfigId);
		apiUtil.assertStatus(response1, ResponseCodes.SUCCESS);

		final String twoByteConfigId = "9";
		Response response2 = apiUtil.sendConfigGetRequest(twoByteConfigId);
		apiUtil.assertStatus(response2, ResponseCodes.NOT_FOUND);
	}
}
