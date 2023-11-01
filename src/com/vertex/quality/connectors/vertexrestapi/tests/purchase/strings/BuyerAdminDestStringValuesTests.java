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
 * Tests valid and edge String cases for the administrativeDestination fields inside the buyer field
 *
 * @author hho
 */
public class BuyerAdminDestStringValuesTests extends VertexRestAPIBaseTest
{
	protected final String ADMIN_DEST_REQUEST_PATH = "buyer.administrativeDestination";
	protected final String ADMIN_DEST_RESPONSE_PATH = "data.buyer.administrativeDestination";
	protected final String FILE_NAME = "BuyerAndVendorFieldsRequest.json";

	protected final String CITY = "city";
	protected final String CITY_SIZE_ERROR_MESSAGE
		= "buyer.administrativeDestination.city - size must be between 1 and 60";

	/**
	 * Tests if putting in the an empty string for city in the administrativeDestination field is
	 * valid
	 * Expected result should be a 200 response with the field omitted
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void adminDestinationCityNoLengthValueIsValidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(ADMIN_DEST_REQUEST_PATH, CITY, VertexRestAPITestData.EMPTY_STRING_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(ADMIN_DEST_RESPONSE_PATH, not(hasKey(CITY)));
	}

	/**
	 * Tests if putting in the minimal string size for city in the administrativeDestination field
	 * is valid
	 * Expected result should be a 200 response with the field valid and present
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void adminDestinationCityMinimalLengthValueIsValidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(ADMIN_DEST_REQUEST_PATH, CITY, VertexRestAPITestData.SIZE_ONE_STRING_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(ADMIN_DEST_RESPONSE_PATH, hasEntry(CITY, VertexRestAPITestData.SIZE_ONE_STRING_VALUE));
	}

	/**
	 * Tests if putting in the max string size for city in the administrativeDestination field is
	 * valid
	 * Expected result should be a 200 response with the field valid and present
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void adminDestinationCityMaxLengthValueIsValidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(ADMIN_DEST_REQUEST_PATH, CITY, VertexRestAPITestData.SIZE_SIXTY_STRING_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(ADMIN_DEST_RESPONSE_PATH, hasEntry(CITY, VertexRestAPITestData.SIZE_SIXTY_STRING_VALUE));
	}

	/**
	 * Tests if putting in the a string over the max size for city in the administrativeDestination
	 * field is invalid
	 * Expected result should be a 400 response with the error message pertaining to long string
	 * size
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void adminDestinationCityOverMaxLengthValueIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(ADMIN_DEST_REQUEST_PATH, CITY, VertexRestAPITestData.SIZE_SIXTY_ONE_STRING_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, equalTo(CITY_SIZE_ERROR_MESSAGE));
	}

	/**
	 * Tests if putting in an integer for city in the administrativeDestination field is valid and
	 * converts it into a string
	 * Expected result should be a 200 response with the field valid and present and the integer as
	 * a string
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void adminDestinationCityIsIntegerValueIsValidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(ADMIN_DEST_REQUEST_PATH, CITY, VertexRestAPITestData.INTEGER_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(ADMIN_DEST_RESPONSE_PATH, hasEntry(CITY, VertexRestAPITestData.INTEGER_TO_STRING_TEST_VALUE));
	}

	/**
	 * Tests if putting in a boolean for city in the administrativeDestination field is valid and
	 * just converts the boolean to a string
	 * Expected result should be a 200 response with the field valid and present with the boolean as
	 * a string
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void adminDestinationCityIsBooleanValueIsValidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(ADMIN_DEST_REQUEST_PATH, CITY, VertexRestAPITestData.BOOLEAN_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(ADMIN_DEST_RESPONSE_PATH, hasEntry(CITY, VertexRestAPITestData.BOOLEAN_TO_STRING_TEST_VALUE));
	}

	/**
	 * Tests if putting null for city in the administrativeDestination field is valid and just omits
	 * the field
	 * Expected result should be a 200 response with the field omitted
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "string" })
	public void adminDestinationCityIsNullValueIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(ADMIN_DEST_REQUEST_PATH, CITY, VertexRestAPITestData.NULL_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(ADMIN_DEST_RESPONSE_PATH, not(hasKey(CITY)));
	}
}
