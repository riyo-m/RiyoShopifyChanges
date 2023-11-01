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
 * Tests to validate normal and edge cases for the exemptionReasonCode field in vendor
 *
 * @author hho
 */
public class VendorExemptionReasonCodeStringValuesTests extends VertexRestAPIBaseTest
{
	protected final String VENDOR_FIELD_REQUEST_PATH = "vendor";
	protected final String VENDOR_FIELD_RESPONSE_PATH = "data.vendor";
	protected final String FILE_NAME = "BuyerAndVendorFieldsRequest.json";
	protected final String EXEMPTION_REASON_CODE_SIZE_ERROR_MESSAGE
		= "vendor.exemptionReasonCode - size must be between 1 and 4";
	protected final String EXEMPTION_REASON_CODE_FIELD = "exemptionReasonCode";

	/**
	 * Tests if putting in the an empty string for city in the exemptionReasonCode field is valid
	 * Expected result should be a 200 response with the field omitted
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void exemptionReasonCodeNoLengthValueIsValidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VENDOR_FIELD_REQUEST_PATH, EXEMPTION_REASON_CODE_FIELD,
				VertexRestAPITestData.EMPTY_STRING_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(VENDOR_FIELD_RESPONSE_PATH, not(hasKey(EXEMPTION_REASON_CODE_FIELD)));
	}

	/**
	 * Tests if putting in the minimal string size for city in the exemptionReasonCode field is
	 * valid
	 * Expected result should be a 200 response with the field valid and present
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void exemptionReasonCodeMinimalLengthValueIsValidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VENDOR_FIELD_REQUEST_PATH, EXEMPTION_REASON_CODE_FIELD,
				VertexRestAPITestData.SIZE_ONE_STRING_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(VENDOR_FIELD_RESPONSE_PATH,
				hasEntry(EXEMPTION_REASON_CODE_FIELD, VertexRestAPITestData.SIZE_ONE_STRING_VALUE));
	}

	/**
	 * Tests if putting in the max string size for city in the exemptionReasonCode field is valid
	 * Expected result should be a 200 response with the field valid and present
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void exemptionReasonCodeMaxLengthValueIsValidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VENDOR_FIELD_REQUEST_PATH, EXEMPTION_REASON_CODE_FIELD,
				VertexRestAPITestData.SIZE_FOUR_STRING_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(VENDOR_FIELD_RESPONSE_PATH,
				hasEntry(EXEMPTION_REASON_CODE_FIELD, VertexRestAPITestData.SIZE_FOUR_STRING_VALUE));
	}

	/**
	 * Tests if putting in the a string over the max size for city in the exemptionReasonCode field
	 * is invalid
	 * Expected result should be a 400 response with the error message pertaining to size
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void exemptionReasonCodeOverMaxLengthValueIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VENDOR_FIELD_REQUEST_PATH, EXEMPTION_REASON_CODE_FIELD,
				VertexRestAPITestData.SIZE_FIVE_STRING_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, equalTo(EXEMPTION_REASON_CODE_SIZE_ERROR_MESSAGE));
	}

	/**
	 * Tests if putting in an integer for city in the exemptionReasonCode field is valid and
	 * converts it into a string
	 * Expected result should be a 200 response with the field valid and present and the integer as
	 * a string
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void exemptionReasonCodeIsIntegerValueIsValidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VENDOR_FIELD_REQUEST_PATH, EXEMPTION_REASON_CODE_FIELD,
				VertexRestAPITestData.INTEGER_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(VENDOR_FIELD_RESPONSE_PATH,
				hasEntry(EXEMPTION_REASON_CODE_FIELD, VertexRestAPITestData.INTEGER_TO_STRING_TEST_VALUE));
	}

	/**
	 * Tests if putting in a boolean for city in the exemptionReasonCode field is valid and just
	 * converts the boolean to a string
	 * Expected result should be a 200 response with the field valid and present and the boolean as
	 * a string
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void exemptionReasonCodeIsBooleanValueIsValidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VENDOR_FIELD_REQUEST_PATH, EXEMPTION_REASON_CODE_FIELD,
				VertexRestAPITestData.BOOLEAN_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(VENDOR_FIELD_RESPONSE_PATH,
				hasEntry(EXEMPTION_REASON_CODE_FIELD, VertexRestAPITestData.BOOLEAN_TO_STRING_TEST_VALUE));
	}

	/**
	 * Tests if putting null for city in the exemptionReasonCode field is valid and just omits the
	 * field
	 * Expected result should be a 200 response with the field omitted
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void exemptionReasonCodeIsNullValueIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VENDOR_FIELD_REQUEST_PATH, EXEMPTION_REASON_CODE_FIELD, VertexRestAPITestData.NULL_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(VENDOR_FIELD_RESPONSE_PATH, not(hasKey(EXEMPTION_REASON_CODE_FIELD)));
	}
}
