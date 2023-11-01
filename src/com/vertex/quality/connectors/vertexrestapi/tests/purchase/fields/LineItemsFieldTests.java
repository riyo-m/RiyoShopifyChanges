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
 * Tests that involve checking for valid and invalid fields in the lineItems field
 *
 * @author hho
 */
public class LineItemsFieldTests extends VertexRestAPIBaseTest
{
	protected final String FILE_NAME = "AccrualTaxRequest.json";
	protected final String LINE_ITEMS_REQUEST_PATH = "lineItems[0]";
	protected final String LINE_ITEMS_RESPONSE_PATH = "data.lineItems[0]";
	protected final String VALUE_KEY = "value";
	protected final String EXEMPTION_REASON_CODE = "exemptionReasonCode";

	/**
	 * Tests to check that the vendor field in lineItems is valid and present when added
	 * Expected result should be a 200 response with the vendor field in lineItems present
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyVendorFieldIsValidTest( )
	{
		String vendorField = "vendor";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(EXEMPTION_REASON_CODE, VertexRestAPITestData.INTEGER_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(LINE_ITEMS_REQUEST_PATH, vendorField, jsonObject)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.body(LINE_ITEMS_RESPONSE_PATH, hasKey(vendorField));
	}

	/**
	 * Tests to check that the vendor field with a bad field member is invalid
	 * Expected result should be a 400 response with the error message pertaining to unrecognized
	 * field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyVendorFieldWithBadMemberIsInvalidTest( )
	{
		String vendorField = "vendor";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(VertexRestAPITestData.BAD_FIELD_MEMBER_NAME, VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(LINE_ITEMS_REQUEST_PATH, vendorField, jsonObject)
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
	 * Tests to check that the seller field in lineItems is invalid
	 * Expected result should be a 400 response with the error message pertaining to unrecognized
	 * field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifySellerFieldIsInvalidTest( )
	{
		String sellerField = "seller";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("department", VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(LINE_ITEMS_REQUEST_PATH, sellerField, jsonObject)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				containsString(VertexRestAPITestData.UNRECOGNIZED_FIELD_ERROR + "\"" + sellerField + "\""));
	}

	/**
	 * Tests to check that the purchase field in lineItems is valid
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyPurchaseFieldIsValidTest( )
	{
		String purchaseField = "purchase";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("purchaseClass", VertexRestAPITestData.STRING_TEST_VALUE);
		jsonObject.put(VALUE_KEY, VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(LINE_ITEMS_REQUEST_PATH, purchaseField, jsonObject)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(LINE_ITEMS_RESPONSE_PATH, hasKey(purchaseField));
	}

	/**
	 * Tests to check that the product field in lineItems is invalid
	 * Expected result should be a 400 response with the error message pertaining to unrecognized
	 * field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyProductFieldIsInvalidTest( )
	{
		String productField = "product";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("productClass", VertexRestAPITestData.STRING_TEST_VALUE);
		jsonObject.put(VALUE_KEY, VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(LINE_ITEMS_REQUEST_PATH, productField, jsonObject)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				containsString(VertexRestAPITestData.UNRECOGNIZED_FIELD_ERROR + "\"" + productField + "\""));
	}

	/**
	 * Tests to check that the customer field in lineItems is invalid
	 * Expected result should be a 400 response with the error message pertaining to unrecognized
	 * field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyCustomerFieldIsInvalidTest( )
	{
		String customerField = "customer";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(EXEMPTION_REASON_CODE, VertexRestAPITestData.INTEGER_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(LINE_ITEMS_REQUEST_PATH, customerField, jsonObject)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				containsString(VertexRestAPITestData.UNRECOGNIZED_FIELD_ERROR + "\"" + customerField + "\""));
	}

	/**
	 * Tests to check that the chargedTax field in lineItems is valid
	 * Expected result should be a 200 response with the field present
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyChargedTaxFieldIsValidTest( )
	{
		String chargedTaxField = "chargedTax";

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(LINE_ITEMS_REQUEST_PATH, chargedTaxField, VertexRestAPITestData.FLOAT_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(LINE_ITEMS_RESPONSE_PATH, hasEntry(chargedTaxField, VertexRestAPITestData.FLOAT_TEST_VALUE));
	}

	/**
	 * Tests to check that the buyer field in lineItems is valid
	 * Expected result should be a 200 response with the field present
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyBuyerFieldIsValidTest( )
	{
		String buyerField = "buyer";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("department", VertexRestAPITestData.STRING_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(LINE_ITEMS_REQUEST_PATH, buyerField, jsonObject)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(LINE_ITEMS_RESPONSE_PATH, hasKey(buyerField));
	}
}
