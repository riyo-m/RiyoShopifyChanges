package com.vertex.quality.connectors.vertexrestapi.tests.purchase.enums;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.vertexrestapi.common.enums.EndPoint;
import com.vertex.quality.connectors.vertexrestapi.common.tests.VertexRestAPIBaseTest;
import com.vertex.quality.connectors.vertexrestapi.common.tests.VertexRestAPITest;
import com.vertex.quality.connectors.vertexrestapi.common.utils.VertexRestAPITestData;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

/**
 * Tests for valid and invalid cases for the simplificationCode enum
 *
 * @author hho
 */
public class SimplificationCodeEnumTests extends VertexRestAPIBaseTest
{
	protected final String FILE_NAME = "AccrualTaxRequest.json";
	protected final String SIMPLIFICATION_CODE_REQUEST_PATH = "simplificationCode";
	protected final String SIMPLIFICATION_CODE_RESPONSE_PATH = "data.simplificationCode";
	protected final String INVALID_SIMPLIFICATION_CODE_VALUE
		= "No enum constant com.vertexinc.restapi.model.PurchaseRequestType.SimplificationCodeEnum.";

	/**
	 * Tests to see if putting CONSIGNMENT for the value of simplificationCode enum is valid
	 * Expected result should be a 200 response with the field containing CONSIGNMENT
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyConsignmentSimplificationCodeIsValidTest( )
	{
		String givenSimplificationCodeValue = "CONSIGNMENT";
		String expectedSimplificationCodeValue = givenSimplificationCodeValue;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, SIMPLIFICATION_CODE_REQUEST_PATH,
				givenSimplificationCodeValue)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(SIMPLIFICATION_CODE_RESPONSE_PATH, equalTo(expectedSimplificationCodeValue));
	}

	/**
	 * Tests to see if putting CALL_OFF for the value of simplificationCode enum is valid
	 * Expected result should be a 200 response with the field containing CALL_OFF
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyCallOffSimplificationCodeIsValidTest( )
	{
		String givenSimplificationCodeValue = "CALL_OFF";
		String expectedSimplificationCodeValue = givenSimplificationCodeValue;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, SIMPLIFICATION_CODE_REQUEST_PATH,
				givenSimplificationCodeValue)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(SIMPLIFICATION_CODE_RESPONSE_PATH, equalTo(expectedSimplificationCodeValue));
	}

	/**
	 * Tests to see if putting TRIANGULATION for the value of simplificationCode enum is valid
	 * Expected result should be a 200 response with the field containing TRIANGULATION
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyTriangulationSimplificationCodeIsValidTest( )
	{
		String givenSimplificationCodeValue = "TRIANGULATION";
		String expectedSimplificationCodeValue = givenSimplificationCodeValue;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, SIMPLIFICATION_CODE_REQUEST_PATH,
				givenSimplificationCodeValue)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(SIMPLIFICATION_CODE_RESPONSE_PATH, equalTo(expectedSimplificationCodeValue));
	}

	/**
	 * Tests to see if putting REGISTRATION_GROUP for the value of simplificationCode enum is valid
	 * Expected result should be a 200 response with the field containing REGISTRATION_GROUP
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyRegistrationGroupSimplificationCodeIsValidTest( )
	{
		String givenSimplificationCodeValue = "REGISTRATION_GROUP";
		String expectedSimplificationCodeValue = givenSimplificationCodeValue;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, SIMPLIFICATION_CODE_REQUEST_PATH,
				givenSimplificationCodeValue)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(SIMPLIFICATION_CODE_RESPONSE_PATH, equalTo(expectedSimplificationCodeValue));
	}

	/**
	 * Tests to see if putting an invalid value for simplificationCode will result in an error
	 * Expected result should be a 400 response with an error message pertaining to invalid enum
	 * value
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyInvalidSimplificationCodeValueIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, SIMPLIFICATION_CODE_REQUEST_PATH,
				VertexRestAPITestData.BAD_ENUM_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				equalTo(INVALID_SIMPLIFICATION_CODE_VALUE + VertexRestAPITestData.BAD_ENUM_TEST_VALUE.toUpperCase()));
	}

	/**
	 * Tests to see if putting null for simplificationCode will result in a success
	 * Expected result should be a 200 response with a success message since the field is optional,
	 * the field is removed
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyNullSimplificationCodeValueIsValidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, SIMPLIFICATION_CODE_REQUEST_PATH,
				VertexRestAPITestData.NULL_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(SIMPLIFICATION_CODE_RESPONSE_PATH, not(hasKey(SIMPLIFICATION_CODE_REQUEST_PATH)));
	}

	/**
	 * Tests to see if putting an integer for simplificationCode will result in an error
	 * Expected result should be a 400 response with an error message pertaining to invalid enum
	 * value
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyIntegerSimplificationCodeValueIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, SIMPLIFICATION_CODE_REQUEST_PATH,
				VertexRestAPITestData.INTEGER_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				equalTo(INVALID_SIMPLIFICATION_CODE_VALUE + VertexRestAPITestData.INTEGER_TEST_VALUE));
	}

	/**
	 * Tests to see if putting a boolean for simplificationCode will result in an error
	 * Expected result should be a 400 response with an error message pertaining to invalid enum
	 * value
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyBooleanSimplificationCodeValueIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, SIMPLIFICATION_CODE_REQUEST_PATH,
				VertexRestAPITestData.BOOLEAN_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, equalTo(
				INVALID_SIMPLIFICATION_CODE_VALUE + VertexRestAPITestData.BOOLEAN_TO_STRING_TEST_VALUE.toUpperCase()));
	}
}
