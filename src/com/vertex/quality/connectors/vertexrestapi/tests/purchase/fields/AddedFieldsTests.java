package com.vertex.quality.connectors.vertexrestapi.tests.purchase.fields;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.vertexrestapi.common.enums.EndPoint;
import com.vertex.quality.connectors.vertexrestapi.common.tests.VertexRestAPIBaseTest;
import com.vertex.quality.connectors.vertexrestapi.common.tests.VertexRestAPITest;
import com.vertex.quality.connectors.vertexrestapi.common.utils.VertexRestAPITestData;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.hasEntry;

/**
 * Test class to check and validate any new fields that were added in purchaseRequestType that were
 * not present in saleRequestType
 *
 * @author hho
 */
public class AddedFieldsTests extends VertexRestAPIBaseTest
{
	protected final String FILE_NAME = "AccrualTaxRequest.json";

	/**
	 * Tests to check that the chargedTax field is valid and present when added to
	 * purchaseRequestType
	 * Expected result should be a 200 response with the field added
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "fields" })
	public void verifyChargedTaxFieldIsValidTest( )
	{
		String fieldName = "chargedTax";

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, fieldName, VertexRestAPITestData.FLOAT_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ROOT_RESPONSE_PATH,
				hasEntry(fieldName, VertexRestAPITestData.FLOAT_TEST_VALUE));
	}
}
