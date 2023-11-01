package com.vertex.quality.connectors.vertexrestapi.tests.purchase.strings;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.vertexrestapi.common.enums.EndPoint;
import com.vertex.quality.connectors.vertexrestapi.common.tests.VertexRestAPIBaseTest;
import com.vertex.quality.connectors.vertexrestapi.common.tests.VertexRestAPITest;
import com.vertex.quality.connectors.vertexrestapi.common.utils.VertexRestAPITestData;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

/**
 * Tests to validate normal and edge cases for the division field in buyer
 *
 * @author hho
 */
public class BuyerDivisionStringValuesTests extends VertexRestAPIBaseTest
{
	protected final String BUYER_FIELD_REQUEST_PATH = "buyer";
	protected final String BUYER_FIELD_RESPONSE_PATH = "data.buyer";
	protected final String FILE_NAME = "BuyerAndVendorFieldsRequest.json";
	protected final String DIVISION_SIZE_ERROR_MESSAGE = "buyer.division - size must be between 0 and 40";
	protected final String DIVISION_FIELD = "division";

	/**
	 * Tests if putting in the an empty string for city in the division field is valid
	 * Expected result should be a 200 response with the field omitted
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void divisionNoLengthValueIsValidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(BUYER_FIELD_REQUEST_PATH, DIVISION_FIELD, VertexRestAPITestData.EMPTY_STRING_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(BUYER_FIELD_RESPONSE_PATH, not(hasKey(DIVISION_FIELD)));
	}

	/**
	 * Tests if putting in the minimal string size for city in the division field is valid
	 * Expected result should be a 200 response with the field valid and present
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void divisionMinimalLengthValueIsValidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(BUYER_FIELD_REQUEST_PATH, DIVISION_FIELD, VertexRestAPITestData.SIZE_ONE_STRING_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(BUYER_FIELD_RESPONSE_PATH, hasEntry(DIVISION_FIELD, VertexRestAPITestData.SIZE_ONE_STRING_VALUE));
	}

	/**
	 * Tests if putting in the max string size for city in the division field is valid
	 * Expected result should be a 200 response with the field valid and present
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void divisionMaxLengthValueIsValidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(BUYER_FIELD_REQUEST_PATH, DIVISION_FIELD, VertexRestAPITestData.SIZE_FORTY_STRING_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(BUYER_FIELD_RESPONSE_PATH, hasEntry(DIVISION_FIELD, VertexRestAPITestData.SIZE_FORTY_STRING_VALUE));
	}

	/**
	 * Tests if putting in the a string over the max size for city in the division field is invalid
	 * Expected result should be a 400 response with the error message pertaining to size
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void divisionOverMaxLengthValueIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(BUYER_FIELD_REQUEST_PATH, DIVISION_FIELD, VertexRestAPITestData.SIZE_FORTY_ONE_STRING_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, equalTo(DIVISION_SIZE_ERROR_MESSAGE));
	}

	/**
	 * Tests if putting in an integer for city in the division field is valid and converts it into a
	 * string
	 * Expected result should be a 200 response with the field valid and present and the integer as
	 * a string
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void divisionIsIntegerValueIsValidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(BUYER_FIELD_REQUEST_PATH, DIVISION_FIELD, VertexRestAPITestData.INTEGER_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(BUYER_FIELD_RESPONSE_PATH,
				hasEntry(DIVISION_FIELD, VertexRestAPITestData.INTEGER_TO_STRING_TEST_VALUE));
	}

	/**
	 * Tests if putting in a boolean for city in the division field is valid and just converts the
	 * boolean to a string
	 * Expected result should be a 200 response with the field valid and present and the boolean as
	 * a string
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void divisionIsBooleanValueIsValidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(BUYER_FIELD_REQUEST_PATH, DIVISION_FIELD, VertexRestAPITestData.BOOLEAN_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(BUYER_FIELD_RESPONSE_PATH,
				hasEntry(DIVISION_FIELD, VertexRestAPITestData.BOOLEAN_TO_STRING_TEST_VALUE));
	}

	/**
	 * Tests if putting null for city in the division field is valid and just omits the field
	 * Expected result should be a 200 response with the field omitted
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void divisionIsNullValueIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(BUYER_FIELD_REQUEST_PATH, DIVISION_FIELD, VertexRestAPITestData.NULL_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(BUYER_FIELD_RESPONSE_PATH, not(hasKey(DIVISION_FIELD)));
	}
}
