package com.vertex.quality.connectors.mirakl.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.mirakl.api.base.MiraklAPIBaseTest;
import com.vertex.quality.connectors.mirakl.api.enums.MiraklOperatorsData;
import com.vertex.quality.connectors.mirakl.common.utils.MiraklDeclareGlobals;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test case for shops of Mirakl connector.
 * MIR-59
 *
 * @author rohit-mogane
 */
public class MiraklShopsTests extends MiraklAPIBaseTest
{
	private String activityId = null, operatorId = null;

	/**
	 * This will check create shops for Mirakl connector
	 * MIR-231
	 */
	@Test(groups = { "mirakl_regression" })
	public void syncShopsTest( )
	{
		operatorId = createSpecificOperatorsEndpoint();
		Response response = shopsEndpoint(operatorId);
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
	 * This will check status shops for Mirakl connector
	 * MIR-233
	 */
	@Test(groups = { "mirakl_regression" })
	public void checkShopStatusTest( )
	{
		syncShopsTest();
		Response response;
		do
		{
			response = getStatusEndpoint(operatorId, activityId, MiraklOperatorsData.SHOPS_ARRAY_KEY.data);
		}
		while ( response
			.getBody()
			.asString()
			.equals(MiraklOperatorsData.ACTIVITY_STATUS_RUNNING.data) );
		MiraklDeclareGlobals
			.getInstance()
			.getArrayList()
			.add(operatorId);
		Assert.assertEquals(response
			.getBody()
			.asString(), MiraklOperatorsData.ACTIVITY_STATUS_COMPLETED.data);
	}

	/**
	 * Test to create custom single shop
	 */
	@Test(groups = { "mirakl_regression" })
	public void createCustomShopTest( )
	{
		miraklShopsString = getShopsData();
		Response response = createShopsEndpoint(miraklShopsString);
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS.getResponseCode());
	}

	/**
	 * S20 list all shops get request test
	 */
	@Test(groups = { "mirakl_regression" })
	public void getListAllShopsTest( )
	{
		Response outboundLogsResponse = getShops("3");
		assertEquals(outboundLogsResponse.getStatusCode(), ResponseCodes.SUCCESS.getResponseCode());
	}

	/**
	 * Test to create demo stores
	 * MIR-352
	 */
	@Test(groups = { "mirakl_bulk" })
	public void createShopsTest( )
	{
		createBulkShops(1);
	}
}
