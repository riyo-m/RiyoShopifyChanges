package com.vertex.quality.connectors.mirakl.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.mirakl.api.base.MiraklAPIBaseTest;
import com.vertex.quality.connectors.mirakl.common.utils.MiraklDeclareGlobals;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test case for operators of Mirakl connector.
 *
 * @author rohit-mogane
 */
public class MiraklOperatorsTests extends MiraklAPIBaseTest
{
	private String operatorId = null;

	/**
	 * This test will check whether it returns the list of operators for Mirakl connector
	 * MIR-174
	 */
	@Test(groups = { "mirakl_regression" })
	public void getOperatorsTest( )
	{
		Response response = getOperatorsEndpoint();
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS.getResponseCode());
	}

	/**
	 * This test will create operators for Mirakl connector
	 * MIR-175
	 */
	@Test(groups = { "mirakl_regression" })
	public void createOperatorsTest( )
	{
		miraklOperatorsString = getOperatorsData();
		Response response = createOperatorsEndpoint(miraklOperatorsString);
		operatorId = response
			.getBody()
			.asString();
		MiraklDeclareGlobals
			.getInstance()
			.getArrayList()
			.add(operatorId);
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.CREATED.getResponseCode());
	}

	/**
	 * This test will update the list of operators for Mirakl connector
	 * MIR-176
	 */
	@Test(groups = { "mirakl_regression" })
	public void updateOperatorsTest( )
	{
		createOperatorsTest();
		Response response = updateOperatorsEndpoint(miraklOperatorsString, operatorId);
		MiraklDeclareGlobals
			.getInstance()
			.getArrayList()
			.add(operatorId);
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS.getResponseCode());
	}

	/**
	 * This test will delete the specific operator for Mirakl connector
	 * MIR-176
	 */
	@Test(groups = { "mirakl_regression" })
	public void deleteOperatorsTest( )
	{
		createOperatorsTest();
		Response response = deleteOperatorsEndpoint(operatorId);
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS_NO_CONTENT.getResponseCode());
	}

	/**
	 * Operators will be cleaned up created though regression suite
	 */
	@Test(groups = { "mirakl_regression" }, priority = 25)
	public void wholeCleanUpOperatorsTest( )
	{
		deleteMiraklGlobalOperators(MiraklDeclareGlobals
			.getInstance()
			.getArrayList());
		VertexLogger.log("Clean up completed!");
	}
}
