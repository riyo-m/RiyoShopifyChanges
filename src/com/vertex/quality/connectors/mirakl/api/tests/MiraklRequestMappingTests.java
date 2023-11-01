package com.vertex.quality.connectors.mirakl.api.tests;

import com.vertex.quality.connectors.mirakl.api.base.MiraklAPIBaseTest;
import com.vertex.quality.connectors.mirakl.common.utils.MiraklDeclareGlobals;
import org.testng.annotations.Test;

/**
 * Test case for request mappings for Mirakl operators.
 *
 * @author Joe-Ciccone
 */
public class MiraklRequestMappingTests extends MiraklAPIBaseTest
{
	String operatorId = null, mappingId = null;

	/**
	 * Test for the creation of field mappings for mirakl operators
	 *
	 * MIR-448
	 */
	@Test(groups = { "mirakl_regression" })
	public void createRequestMappingTest( )
	{
		operatorId = createSpecificOperatorsEndpoint();
		createRequestMapping(operatorId);
		mappingId = getRequestMappingId(operatorId);
		deleteRequestMappingEndpoint(operatorId, mappingId);
		MiraklDeclareGlobals
			.getInstance()
			.getArrayList()
			.add(operatorId);
	}

	/**
	 * Tests that operator field mappings can be retrieved
	 *
	 * MIR-450
	 */
	@Test(groups = { "mirakl_regression" })
	public void getRequestMappingTest( )
	{
		operatorId = createSpecificOperatorsEndpoint();
		createRequestMapping(operatorId);
		getRequestMappingId(operatorId);
		MiraklDeclareGlobals
			.getInstance()
			.getArrayList()
			.add(operatorId);
	}

	/**
	 * Tests that operator field mappings can be deleted
	 *
	 * MIR-449
	 */
	@Test(groups = { "mirakl_regression" })
	public void deleteRequestMappingTest( )
	{
		operatorId = createSpecificOperatorsEndpoint();
		createRequestMapping(operatorId);
		mappingId = getRequestMappingId(operatorId);
		deleteRequestMappingEndpoint(operatorId, mappingId);
		MiraklDeclareGlobals
			.getInstance()
			.getArrayList()
			.add(operatorId);
	}

	/**
	 * Negative test to ensure a mapping cannot be created for an operator that doesn't exist
	 *
	 * MIR-451
	 */
	@Test(groups = { "mirakl_regression" })
	public void createRequestMappingBadOperatorURLTest( )
	{
		String operatorId = createSpecificOperatorsEndpoint();
		createRequestMappingBadOperatorURL(operatorId);
		MiraklDeclareGlobals
			.getInstance()
			.getArrayList()
			.add(operatorId);
	}

	/**
	 * Negative test to ensure that incorrect request payloads cannot be submitted
	 *
	 * MIR-452
	 */
	@Test(groups = { "mirakl_regression" })
	public void createRequestMappingBadOperatorJSONTest( )
	{
		operatorId = createSpecificOperatorsEndpoint();
		createRequestMappingBadOperatorJSON(operatorId);
		MiraklDeclareGlobals
			.getInstance()
			.getArrayList()
			.add(operatorId);
	}
}
