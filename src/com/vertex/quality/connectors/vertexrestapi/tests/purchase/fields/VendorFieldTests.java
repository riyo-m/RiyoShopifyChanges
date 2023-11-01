package com.vertex.quality.connectors.vertexrestapi.tests.purchase.fields;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.vertexrestapi.common.enums.EndPoint;
import com.vertex.quality.connectors.vertexrestapi.common.tests.VertexRestAPIBaseTest;
import com.vertex.quality.connectors.vertexrestapi.common.tests.VertexRestAPITest;
import com.vertex.quality.connectors.vertexrestapi.common.utils.VertexRestAPITestData;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

/**
 * Tests that involve checking for the fields and values in the vendor field
 *
 * @author hho
 */
public class VendorFieldTests extends VertexRestAPIBaseTest
{
	protected final String VENDOR_RESPONSE_PATH = "data.vendor";
	protected final String VENDOR_PATH = "vendor";
	protected final String CUSTOMER_PATH = "customer";
	protected final String FILE_NAME = "BuyerAndVendorFieldsRequest.json";
	protected final String CITY = "city";
	protected final String ADMIN_ORIGIN = "administrativeOrigin";
	protected final String PHYSICAL_ORIGIN = "physicalOrigin";
	protected final String VENDOR_CODE = "vendorCode";

	/**
	 * Tests to check that the customerCode field that was present in customer is not present in
	 * vendor
	 * Expected result should be a 400 response with the error message pertaining to unrecognized
	 * field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyCustomerCodeFieldIsInvalidTest( )
	{
		String fieldName = "customerCode";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classCode", VertexRestAPITestData.STRING_TEST_VALUE);
		jsonObject.put("isBusinessIndicator", VertexRestAPITestData.BOOLEAN_TEST_VALUE);
		jsonObject.put("value", VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VENDOR_PATH, fieldName, jsonObject)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				containsString(VertexRestAPITestData.UNRECOGNIZED_FIELD_ERROR + "\"" + fieldName + "\""));
	}

	/**
	 * Tests to check that the customer field that was replaced with vendor is not valid
	 * Expected result should be a 400 response with the error message pertaining to unrecognized
	 * field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyCustomerFieldIsInvalidTest( )
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("exemptionReasonCode", VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, CUSTOMER_PATH, jsonObject)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				containsString(VertexRestAPITestData.UNRECOGNIZED_FIELD_ERROR + "\"" + CUSTOMER_PATH + "\""));
	}

	/**
	 * Tests to check that replacing the vendor field with customer field is not valid
	 * Expected result should be a 400 response with the error message pertaining to unrecognized
	 * field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void replaceVendorWithCustomerFieldAndVerifyInvalidTest( )
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("company", VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, CUSTOMER_PATH, jsonObject)
			.removeJsonField(VENDOR_PATH)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				containsString(VertexRestAPITestData.UNRECOGNIZED_FIELD_ERROR + "\"" + CUSTOMER_PATH + "\""));
	}

	/**
	 * Tests to check that removing the vendor field will result in an error
	 * Expected result should be a 400 response with the error message saying no vendor field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void removeVendorFieldAndVerifyInvalidTest( )
	{
		String noVendor = "no vendor";

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.removeJsonField(VENDOR_PATH)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, equalTo(noVendor));
	}

	/**
	 * Tests to check that the administrativeDestination field that was present in customer is not
	 * present in vendor
	 * Expected result should be a 400 response with the error message pertaining to unrecognized
	 * field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyAdministrativeDestinationFieldIsInvalidTest( )
	{
		String fieldName = "administrativeDestination";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(CITY, VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VENDOR_PATH, fieldName, jsonObject)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				containsString(VertexRestAPITestData.UNRECOGNIZED_FIELD_ERROR + "\"" + fieldName + "\""));
	}

	/**
	 * Tests to check that the administrativeOrigin field in vendor is valid and present when added
	 * Expected result should be a 200 response with the field added
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyAdministrativeOriginFieldIsValidTest( )
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(CITY, VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VENDOR_PATH, ADMIN_ORIGIN, jsonObject)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(VENDOR_RESPONSE_PATH + "." + ADMIN_ORIGIN, hasKey(CITY));
	}

	/**
	 * Tests to check that adding a bad field to administrativeOrigin in vendor is invalid
	 * Expected result should be a 400 response with the error message pertaining to unrecognized
	 * field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyAdministrativeOriginFieldWithBadFieldMemberIsInvalidTest( )
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(VertexRestAPITestData.BAD_FIELD_MEMBER_NAME, VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VENDOR_PATH, ADMIN_ORIGIN, jsonObject)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, containsString(
				VertexRestAPITestData.UNRECOGNIZED_FIELD_ERROR + "\"" + VertexRestAPITestData.BAD_FIELD_MEMBER_NAME +
				"\""));
	}

	/**
	 * Tests to check that the physicalOrigin field in vendor is valid and present when added
	 * Expected result should be a 200 response with the field added
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyPhysicalOriginFieldIsValidTest( )
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(CITY, VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VENDOR_PATH, PHYSICAL_ORIGIN, jsonObject)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(VENDOR_RESPONSE_PATH + "." + PHYSICAL_ORIGIN, hasKey(CITY));
	}

	/**
	 * Tests to check that adding a bad field to physicalOrigin in vendor is invalid
	 * Expected result should be a 400 response with the error message pertaining to unrecognized
	 * field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyPhysicalOriginFieldWithBadFieldMemberIsInvalidTest( )
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(VertexRestAPITestData.BAD_FIELD_MEMBER_NAME, VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VENDOR_PATH, PHYSICAL_ORIGIN, jsonObject)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, containsString(
				VertexRestAPITestData.UNRECOGNIZED_FIELD_ERROR + "\"" + VertexRestAPITestData.BAD_FIELD_MEMBER_NAME +
				"\""));
	}

	/**
	 * Tests to check that the vendorCode field in vendor is valid and present when added
	 * Expected result should be a 200 response with the field added
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyVendorCodeFieldIsValidTest( )
	{
		String fieldMember = "classCode";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(fieldMember, VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VENDOR_PATH, VENDOR_CODE, jsonObject)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(VENDOR_RESPONSE_PATH + "." + VENDOR_CODE, hasKey(fieldMember));
	}

	/**
	 * Tests to check that adding a bad field to vendorCode in vendor is invalid
	 * Expected result should be a 400 response with the error message pertaining to unrecognized
	 * field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyVendorCodeFieldWithBadFieldMemberIsInvalidTest( )
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(VertexRestAPITestData.BAD_FIELD_MEMBER_NAME, VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VENDOR_PATH, VENDOR_CODE, jsonObject)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, containsString(
				VertexRestAPITestData.UNRECOGNIZED_FIELD_ERROR + "\"" + VertexRestAPITestData.BAD_FIELD_MEMBER_NAME +
				"\""));
	}
}
