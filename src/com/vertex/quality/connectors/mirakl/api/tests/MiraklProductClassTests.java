package com.vertex.quality.connectors.mirakl.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.mirakl.api.base.MiraklAPIBaseTest;
import com.vertex.quality.connectors.mirakl.api.enums.MiraklOperatorsData;
import com.vertex.quality.connectors.mirakl.common.utils.MiraklDeclareGlobals;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test case for product class of Mirakl connector.
 * MIR-58
 *
 * @author rohit-mogane
 */
public class MiraklProductClassTests extends MiraklAPIBaseTest
{
	private String activityId = null, operatorId = null;

	/**
	 * This will check create product class for Mirakl connector
	 * MIR-187
	 */
	@Test(groups = { "mirakl_regression" })
	public void createProductCategoriesTest( )
	{
		operatorId = createSpecificOperatorsEndpoint();
		Response response = productClassEndpoint(operatorId);
		activityId = response
			.getBody()
			.asString();
		MiraklDeclareGlobals
			.getInstance()
			.getArrayList()
			.add(operatorId);
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS.getResponseCode());
	}

	/**
	 * This will check status product class for Mirakl connector
	 * MIR-188
	 */
	@Test(groups = { "mirakl_regression" })
	public void checkProductCategoriesStatusTest( )
	{
		createProductCategoriesTest();
		Response response;
		do
		{
			response = getStatusEndpoint(operatorId, activityId, MiraklOperatorsData.CATEGORIES_ARRAY_KEY.data);
		}
		while ( response
			.getBody()
			.asString()
			.equals(MiraklOperatorsData.ACTIVITY_STATUS_RUNNING.data) );
		Assert.assertEquals(response
			.getBody()
			.asString(), MiraklOperatorsData.ACTIVITY_STATUS_COMPLETED.data);
	}
}
