package com.vertex.quality.connectors.vertexrestapi.tests.purchase.fields;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.vertexrestapi.common.enums.EndPoint;
import com.vertex.quality.connectors.vertexrestapi.common.tests.VertexRestAPIBaseTest;
import com.vertex.quality.connectors.vertexrestapi.common.tests.VertexRestAPITest;
import com.vertex.quality.connectors.vertexrestapi.common.utils.VertexRestAPITestData;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.containsString;

/**
 * Test class to check and validate any removed fields that are not present in purchaseRequestType
 * but is present in saleRequestType
 *
 * @author hho
 */
public class RemovedFieldsTests extends VertexRestAPIBaseTest
{
	protected final String FILE_NAME = "AccrualTaxRequest.json";

	/**
	 * Tests to check that the discount field is invalid when added to purchaseRequestType
	 * Expected result should be a 400 response with the error message pertaining to unrecognized
	 * field
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyDiscountFieldIsInvalidTest( )
	{
		String fieldName = "discount";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("discountType", "DiscountAmount");
		jsonObject.put("discountValue", VertexRestAPITestData.DOUBLE_TEST_VALUE);

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, fieldName, jsonObject)
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
}
