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
 * Tests for valid and invalid cases for the deliveryTerm enum
 *
 * @author hho
 */
public class DeliveryTermEnumTests extends VertexRestAPIBaseTest
{
	protected final String DELIVERY_TERM_REQUEST_PATH = "deliveryTerm";
	protected final String DELIVERY_TERM_RESPONSE_PATH = "data.deliveryTerm";
	protected final String FILE_NAME = "AccrualTaxRequest.json";
	protected final String INVALID_DELIVERY_TERM_VALUE
		= "No enum constant com.vertexinc.restapi.model.PurchaseRequestType.DeliveryTermEnum.";

	/**
	 * Tests to see if putting EXW for the value of deliveryTerm enum is valid
	 * Expected result should be a 200 response with the field containing EXW
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyEXWDeliveryTermIsValidTest( )
	{
		String givenDeliveryTermValue = "EXW";
		String expectedDeliveryTermValue = givenDeliveryTermValue;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, DELIVERY_TERM_REQUEST_PATH, givenDeliveryTermValue)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(DELIVERY_TERM_RESPONSE_PATH, equalTo(expectedDeliveryTermValue));
	}

	/**
	 * Tests to see if putting FCA for the value of deliveryTerm enum is valid
	 * Expected result should be a 200 response with the field containing FCA
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyFCADeliveryTermIsValidTest( )
	{
		String givenDeliveryTermValue = "FCA";
		String expectedDeliveryTermValue = givenDeliveryTermValue;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, DELIVERY_TERM_REQUEST_PATH, givenDeliveryTermValue)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(DELIVERY_TERM_RESPONSE_PATH, equalTo(expectedDeliveryTermValue));
	}

	/**
	 * Tests to see if putting FAS for the value of deliveryTerm enum is valid
	 * Expected result should be a 200 response with the field containing FAS
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyFASDeliveryTermIsValidTest( )
	{
		String givenDeliveryTermValue = "FAS";
		String expectedDeliveryTermValue = givenDeliveryTermValue;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, DELIVERY_TERM_REQUEST_PATH, givenDeliveryTermValue)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(DELIVERY_TERM_RESPONSE_PATH, equalTo(expectedDeliveryTermValue));
	}

	/**
	 * Tests to see if putting FOB for the value of deliveryTerm enum is valid
	 * Expected result should be a 200 response with the field containing FOB
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyFOBDeliveryTermIsValidTest( )
	{
		String givenDeliveryTermValue = "FOB";
		String expectedDeliveryTermValue = givenDeliveryTermValue;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, DELIVERY_TERM_REQUEST_PATH, givenDeliveryTermValue)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(DELIVERY_TERM_RESPONSE_PATH, equalTo(expectedDeliveryTermValue));
	}

	/**
	 * Tests to see if putting CFR for the value of deliveryTerm enum is valid
	 * Expected result should be a 200 response with the field containing CFR
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyCFRDeliveryTermIsValidTest( )
	{
		String givenDeliveryTermValue = "CFR";
		String expectedDeliveryTermValue = givenDeliveryTermValue;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, DELIVERY_TERM_REQUEST_PATH, givenDeliveryTermValue)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(DELIVERY_TERM_RESPONSE_PATH, equalTo(expectedDeliveryTermValue));
	}

	/**
	 * Tests to see if putting CIF for the value of deliveryTerm enum is valid
	 * Expected result should be a 200 response with the field containing CIF
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyCIFDeliveryTermIsValidTest( )
	{
		String givenDeliveryTermValue = "CIF";
		String expectedDeliveryTermValue = givenDeliveryTermValue;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, DELIVERY_TERM_REQUEST_PATH, givenDeliveryTermValue)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(DELIVERY_TERM_RESPONSE_PATH, equalTo(expectedDeliveryTermValue));
	}

	/**
	 * Tests to see if putting CPT for the value of deliveryTerm enum is valid
	 * Expected result should be a 200 response with the field containing CPT
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyCPTDeliveryTermIsValidTest( )
	{
		String givenDeliveryTermValue = "CPT";
		String expectedDeliveryTermValue = givenDeliveryTermValue;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, DELIVERY_TERM_REQUEST_PATH, givenDeliveryTermValue)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(DELIVERY_TERM_RESPONSE_PATH, equalTo(expectedDeliveryTermValue));
	}

	/**
	 * Tests to see if putting CIP for the value of deliveryTerm enum is valid
	 * Expected result should be a 200 response with the field containing CIP
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyCIPDeliveryTermIsValidTest( )
	{
		String givenDeliveryTermValue = "CIP";
		String expectedDeliveryTermValue = givenDeliveryTermValue;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, DELIVERY_TERM_REQUEST_PATH, givenDeliveryTermValue)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(DELIVERY_TERM_RESPONSE_PATH, equalTo(expectedDeliveryTermValue));
	}

	/**
	 * Tests to see if putting DDP for the value of deliveryTerm enum is valid
	 * Expected result should be a 200 response with the field containing DDP
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyDDPDeliveryTermIsValidTest( )
	{
		String givenDeliveryTermValue = "DDP";
		String expectedDeliveryTermValue = givenDeliveryTermValue;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, DELIVERY_TERM_REQUEST_PATH, givenDeliveryTermValue)
			.build()
			.createRequest();
		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(DELIVERY_TERM_RESPONSE_PATH, equalTo(expectedDeliveryTermValue));
	}

	/**
	 * Tests to see if putting DAP for the value of deliveryTerm enum is valid
	 * Expected result should be a 200 response with the field containing DAP
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyDAPDeliveryTermIsValidTest( )
	{
		String givenDeliveryTermValue = "DAP";
		String expectedDeliveryTermValue = givenDeliveryTermValue;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, DELIVERY_TERM_REQUEST_PATH, givenDeliveryTermValue)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(DELIVERY_TERM_RESPONSE_PATH, equalTo(expectedDeliveryTermValue));
	}

	/**
	 * Tests to see if putting DAT for the value of deliveryTerm enum is valid
	 * Expected result should be a 200 response with the field containing DAT
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyDATDeliveryTermIsValidTest( )
	{
		String givenDeliveryTermValue = "DAT";
		String expectedDeliveryTermValue = givenDeliveryTermValue;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, DELIVERY_TERM_REQUEST_PATH, givenDeliveryTermValue)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(DELIVERY_TERM_RESPONSE_PATH, equalTo(expectedDeliveryTermValue));
	}

	/**
	 * Tests to see if putting SUP for the value of deliveryTerm enum is valid
	 * Expected result should be a 200 response with the field containing SUP
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifySUPDeliveryTermIsValidTest( )
	{
		String givenDeliveryTermValue = "SUP";
		String expectedDeliveryTermValue = givenDeliveryTermValue;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, DELIVERY_TERM_REQUEST_PATH, givenDeliveryTermValue)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(DELIVERY_TERM_RESPONSE_PATH, equalTo(expectedDeliveryTermValue));
	}

	/**
	 * Tests to see if putting CUS for the value of deliveryTerm enum is valid
	 * Expected result should be a 200 response with the field containing CUS
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyCUSDeliveryTermIsValidTest( )
	{
		String givenDeliveryTermValue = "CUS";
		String expectedDeliveryTermValue = givenDeliveryTermValue;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, DELIVERY_TERM_REQUEST_PATH, givenDeliveryTermValue)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(DELIVERY_TERM_RESPONSE_PATH, equalTo(expectedDeliveryTermValue));
	}

	/**
	 * Tests to see if putting an invalid deliveryTerm value will result in an error
	 * Expected result should be a 400 response with an error message pertaining to invalid enum
	 * value
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyInvalidDeliveryTermValueIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, DELIVERY_TERM_REQUEST_PATH,
				VertexRestAPITestData.BAD_ENUM_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				equalTo(INVALID_DELIVERY_TERM_VALUE + VertexRestAPITestData.BAD_ENUM_TEST_VALUE.toUpperCase()));
	}

	/**
	 * Tests to see if putting a null deliveryTerm value will result in an error
	 * Expected result should be a 200 response with the deliveryTerm field omitted since it is an
	 * optional field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyNullDeliveryTermValueIsValidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, DELIVERY_TERM_REQUEST_PATH, VertexRestAPITestData.NULL_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(DELIVERY_TERM_RESPONSE_PATH, not(hasKey(DELIVERY_TERM_REQUEST_PATH)));
	}

	/**
	 * Tests to see if putting an integer value for deliveryTerm will result in an error
	 * Expected result should be a 400 response with an error message pertaining to invalid enum
	 * value
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyIntegerDeliveryTermValueIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, DELIVERY_TERM_REQUEST_PATH,
				VertexRestAPITestData.INTEGER_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				equalTo(INVALID_DELIVERY_TERM_VALUE + VertexRestAPITestData.INTEGER_TEST_VALUE));
	}

	/**
	 * Tests to see if putting a boolean value for deliveryTerm will result in an error
	 * Expected result should be a 400 response with an error message pertaining to invalid enum
	 * value
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyBooleanDeliveryTermValueIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, DELIVERY_TERM_REQUEST_PATH,
				VertexRestAPITestData.BOOLEAN_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, equalTo(
				INVALID_DELIVERY_TERM_VALUE + VertexRestAPITestData.BOOLEAN_TO_STRING_TEST_VALUE.toUpperCase()));
	}
}
