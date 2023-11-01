package com.vertex.quality.connectors.mirakl.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.mirakl.api.base.MiraklAPIBaseTest;
import com.vertex.quality.connectors.mirakl.api.enums.MiraklOperatorsData;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test case for health check of Mirakl connector.
 * MIR-62
 *
 * @author rohit-mogane
 */
public class MiraklHealthCheckTests extends MiraklAPIBaseTest
{
	private String activityId = null;

	/**
	 * This test will check health status for Mirakl connector
	 * MIR-138
	 */
	@Test(groups = { "mirakl_smoke" })
	public void healthCheckTest( )
	{
		Response response = healthCheckEndpoint();
		JsonPath statusValues = response.jsonPath();
		Assert.assertEquals(statusValues.get("connectorVersion"), MiraklOperatorsData.MIRAKL_VERSION.data);
		Assert.assertEquals(statusValues.get("databaseStatus"), MiraklOperatorsData.MIRAKL_DATABASE_STATUS.data);
	}

	/**
	 * This test will perform a private health status check for an operator in the Mirakl connector
	 * MIR-248
	 */
	@Test(groups = { "mirakl_smoke" })
	public void privateHealthCheckOperatorTest( )
	{
		String miraklOperatorsString = getOperatorsData();
		Response response = createOperatorsEndpoint(miraklOperatorsString);
		activityId = response
			.getBody()
			.asString();
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.CREATED.getResponseCode());
		String status = privateHealthCheckOperatorEndpoint(activityId);
		Assert.assertEquals(status, MiraklOperatorsData.MIRAKL_STATUS.data);
		deleteOperatorsEndpoint(activityId);
	}

	/**
	 * This test will perform a private health status check for the Mirakl connector
	 * MIR-248
	 */
	@Test(groups = { "mirakl_smoke" })
	public void privateHealthCheckStatusTest( )
	{
		String status = privateHealthCheckStatusEndpoint();
		Assert.assertEquals(status, MiraklOperatorsData.MIRAKL_STATUS.data);
	}
}
