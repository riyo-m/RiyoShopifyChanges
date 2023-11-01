package com.vertex.quality.connectors.mirakl.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.mirakl.api.base.MiraklAPIBaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test case for countries of Mirakl connector.
 * MIR-61
 *
 * @author rohit-mogane
 */
public class MiraklCountriesTests extends MiraklAPIBaseTest
{
	private String miraklCountriesString = null;

	/**
	 * This test create countries for Mirakl connector
	 * MIR-182
	 */
	@Test(groups = { "mirakl_regression" })
	public void createCountriesTest( )
	{
		miraklCountriesString = getCountriesData();
		Response response = createCountriesEndpoint(miraklCountriesString);
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.CREATED.getResponseCode());
	}

	/**
	 * This test will check status of countries for Mirakl connector
	 * MIR-183
	 */
	@Test(groups = { "mirakl_regression" }, priority = 1)
	public void getCountriesTest( )
	{
		Response response = getCountriesEndpoint();
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS.getResponseCode());
	}
}
