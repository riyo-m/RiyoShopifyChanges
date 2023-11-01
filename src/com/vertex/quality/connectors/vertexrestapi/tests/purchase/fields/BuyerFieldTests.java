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
 * Tests that involve checking for the fields and values in the buyer field
 *
 * @author hho
 */
public class BuyerFieldTests extends VertexRestAPIBaseTest
{
	protected final String SELLER_PATH = "seller";
	protected final String BUYER_PATH = "buyer";
	protected final String BUYER_RESPONSE_PATH = "data.buyer";
	protected final String FILE_NAME = "BuyerAndVendorFieldsRequest.json";
	protected final String COUNTRY = "country";
	protected final String DESTINATION = "destination";

	/**
	 * Tests to check that the physicalOrigin field that was present in seller is not present in
	 * buyer
	 * Expected result should be a 400 response with the error message pertaining to unrecognized
	 * field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyPhysicalOriginFieldIsInvalidTest( )
	{
		String fieldName = "physicalOrigin";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("taxAreaId", 390030140);
		jsonObject.put("locationCustomsStatus", "BONDED_WAREHOUSE");

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(BUYER_PATH, fieldName, jsonObject)
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
	 * Tests to check if the seller field is not present in purchaseRequestType anymore
	 * Expected result should be a 400 response with the error message pertaining to unrecognized
	 * field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifySellerFieldIsInvalidTest( )
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("company", VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, SELLER_PATH, jsonObject)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				containsString(VertexRestAPITestData.UNRECOGNIZED_FIELD_ERROR + "\"" + SELLER_PATH + "\""));
	}

	/**
	 * Tests to check if replacing the buyer field with seller field in purchaseRequestType is
	 * invalid
	 * Expected result should be a 400 response with the error message pertaining to unrecognized
	 * field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void replaceBuyerWithSellerFieldAndVerifyInvalidTest( )
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("company", VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, SELLER_PATH, jsonObject)
			.removeJsonField(BUYER_PATH)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				containsString(VertexRestAPITestData.UNRECOGNIZED_FIELD_ERROR + "\"" + SELLER_PATH + "\""));
	}

	/**
	 * Tests to check that removing the buyer field will prompt an appropriate error
	 * Expected result should be a 400 response with the error message pertaining to no buyer
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void removeBuyerFieldAndVerifyInvalidTest( )
	{
		String noBuyer = "no buyer";

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.removeJsonField(BUYER_PATH)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, equalTo(noBuyer));
	}

	/**
	 * Tests to check that the administrativeOrigin field that was present in seller is not present
	 * in buyer
	 * Expected result should be a 400 response with the error message pertaining to unrecognized
	 * field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyAdministrativeOriginFieldIsInvalidTest( )
	{
		String fieldName = "administrativeOrigin";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(COUNTRY, VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(BUYER_PATH, fieldName, jsonObject)
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
	 * Tests to check that the destination field, which is new in buyer is valid
	 * Expected result should be a 200 response with the country field present
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyDestinationFieldIsValidTest( )
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(COUNTRY, VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(BUYER_PATH, DESTINATION, jsonObject)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(BUYER_RESPONSE_PATH + "." + DESTINATION, hasKey(COUNTRY));
	}

	/**
	 * Tests to verify that the destination field that has an invalid field member is invalid
	 * Expected result should be a 400 response with the error message pertaining to unrecognized
	 * field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyDestinationFieldWithBadFieldMemberIsInvalidTest( )
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(VertexRestAPITestData.BAD_FIELD_MEMBER_NAME, VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(BUYER_PATH, DESTINATION, jsonObject)
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
