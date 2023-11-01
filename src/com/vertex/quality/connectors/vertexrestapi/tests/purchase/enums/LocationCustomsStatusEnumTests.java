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
 * This test class includes all tests that involve testing the input and
 * output of the locationCustomsStatus enum values for purchases in the Vertex Rest API
 *
 * @author hho
 */
public class LocationCustomsStatusEnumTests extends VertexRestAPIBaseTest
{
	protected final String INVALID_ENUM_MESSAGE
		= "No enum constant com.vertexinc.restapi.model.LocationType.LocationCustomsStatusEnum.";
	protected final String DESTINATION_REQUEST_FIELD = "buyer.destination";
	protected final String DESINTATION_RESPONSE_FIELD = "buyer.destination";
	protected final String LOCATION_CUSTOMS_STATUS_REQUEST_PATH = "locationCustomsStatus";
	protected final String LOCATION_CUSTOMS_STATUS_RESPONSE_PATH = "data.buyer.destination.locationCustomsStatus";
	protected final String FILE_NAME = "LocationCustomsStatus.json";

	/**
	 * Tests to see if putting FREE_CIRCULATION for the value of locationCustomsStatus enum is valid
	 * Expected result should be a 200 response with the field containing FREE_CIRCULATION
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyLocationCustomsStatusIsFreeCirculationTest( )
	{
		String givenLocationsCustomStatus = "FREE_CIRCULATION";
		String expectedLocationCustomsStatus = givenLocationsCustomStatus;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(DESTINATION_REQUEST_FIELD, LOCATION_CUSTOMS_STATUS_REQUEST_PATH, givenLocationsCustomStatus)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(LOCATION_CUSTOMS_STATUS_RESPONSE_PATH, equalTo(expectedLocationCustomsStatus));
	}

	/**
	 * Tests to see if putting BONDED_WAREHOUSE for the value of locationCustomsStatus enum is valid
	 * Expected result should be a 200 response with the field containing BONDED_WAREHOUSE
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyLocationCustomsStatusIsBondedWarehouseTest( )
	{
		String givenLocationsCustomStatus = "BONDED_WAREHOUSE";
		String expectedLocationCustomsStatus = givenLocationsCustomStatus;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(DESTINATION_REQUEST_FIELD, LOCATION_CUSTOMS_STATUS_REQUEST_PATH, givenLocationsCustomStatus)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(LOCATION_CUSTOMS_STATUS_RESPONSE_PATH, equalTo(expectedLocationCustomsStatus));
	}

	/**
	 * Tests to see if putting FREE_TRADE_ZONE for the value of locationCustomsStatus enum is valid
	 * Expected result should be a 200 response with the field containing FREE_TRADE_ZONE
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyLocationCustomsStatusIsFreeTradeZoneTest( )
	{
		String givenLocationsCustomStatus = "FREE_TRADE_ZONE";
		String expectedLocationCustomsStatus = givenLocationsCustomStatus;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(DESTINATION_REQUEST_FIELD, LOCATION_CUSTOMS_STATUS_REQUEST_PATH, givenLocationsCustomStatus)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(LOCATION_CUSTOMS_STATUS_RESPONSE_PATH, equalTo(expectedLocationCustomsStatus));
	}

	/**
	 * Tests to see if putting TEMPORARY_IMPORT for the value of locationCustomsStatus enum is valid
	 * Expected result should be a 200 response with the field containing TEMPORARY_IMPORT
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyLocationCustomsStatusIsTemporaryImportTest( )
	{
		String givenLocationsCustomStatus = "TEMPORARY_IMPORT";
		String expectedLocationCustomsStatus = givenLocationsCustomStatus;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(DESTINATION_REQUEST_FIELD, LOCATION_CUSTOMS_STATUS_REQUEST_PATH, givenLocationsCustomStatus)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(LOCATION_CUSTOMS_STATUS_RESPONSE_PATH, equalTo(expectedLocationCustomsStatus));
	}

	/**
	 * Tests to see if putting INWARD_PROCESSING_RELIEF for the value of locationCustomsStatus enum
	 * is valid
	 * Expected result should be a 200 response with the field containing INWARD_PROCESSING_RELIEF
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyLocationCustomsStatusIsInwardProcessingTest( )
	{
		String givenLocationsCustomStatus = "INWARD_PROCESSING_RELIEF";
		String expectedLocationCustomsStatus = givenLocationsCustomStatus;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(DESTINATION_REQUEST_FIELD, LOCATION_CUSTOMS_STATUS_REQUEST_PATH, givenLocationsCustomStatus)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(LOCATION_CUSTOMS_STATUS_RESPONSE_PATH, equalTo(expectedLocationCustomsStatus));
	}

	/**
	 * Tests to see if putting OUTWARD_PROCESSING_RELIEF for the value of locationCustomsStatus enum
	 * is valid
	 * Expected result should be a 200 response with the field containing OUTWARD_PROCESSING_RELIEF
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyLocationCustomsStatusIsOutwardProcessingTest( )
	{
		String givenLocationsCustomStatus = "OUTWARD_PROCESSING_RELIEF";
		String expectedLocationCustomsStatus = givenLocationsCustomStatus;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(DESTINATION_REQUEST_FIELD, LOCATION_CUSTOMS_STATUS_REQUEST_PATH, givenLocationsCustomStatus)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(LOCATION_CUSTOMS_STATUS_RESPONSE_PATH, equalTo(expectedLocationCustomsStatus));
	}

	/**
	 * Tests to see if putting an invalid value for the locationCustomsStatus enum is valid
	 * Expected result should be a 400 response with the error message pertaining to invalid enum
	 * value
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyInvalidLocationCustomsStatusValueIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(DESTINATION_REQUEST_FIELD, LOCATION_CUSTOMS_STATUS_REQUEST_PATH,
				VertexRestAPITestData.BAD_ENUM_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				equalTo(INVALID_ENUM_MESSAGE + VertexRestAPITestData.BAD_ENUM_TEST_VALUE.toUpperCase()));
	}

	/**
	 * Tests to see if putting a null value for the locationCustomsStatus enum is valid
	 * Expected result should be a 200 response with the field not present
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyNullLocationCustomsStatusIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(DESTINATION_REQUEST_FIELD, LOCATION_CUSTOMS_STATUS_REQUEST_PATH,
				VertexRestAPITestData.NULL_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(DESINTATION_RESPONSE_FIELD, not(hasKey(LOCATION_CUSTOMS_STATUS_REQUEST_PATH)));
	}

	/**
	 * Tests to see if putting an integer value for the locationCustomsStatus enum is valid
	 * Expected result should be a 400 response with the error message pertaining to invalid enum
	 * value
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyIntegerLocationCustomsStatusIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(DESTINATION_REQUEST_FIELD, LOCATION_CUSTOMS_STATUS_REQUEST_PATH,
				VertexRestAPITestData.INTEGER_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				equalTo(INVALID_ENUM_MESSAGE + VertexRestAPITestData.INTEGER_TEST_VALUE));
	}

	/**
	 * Tests to see if putting a boolean value for the locationCustomsStatus enum is valid
	 * Expected result should be a 400 response with the error message pertaining to invalid enum
	 * value
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyBooleanLocationCustomsStatusIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(DESTINATION_REQUEST_FIELD, LOCATION_CUSTOMS_STATUS_REQUEST_PATH,
				VertexRestAPITestData.BOOLEAN_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				equalTo(INVALID_ENUM_MESSAGE + VertexRestAPITestData.BOOLEAN_TO_STRING_TEST_VALUE.toUpperCase()));
	}
}
