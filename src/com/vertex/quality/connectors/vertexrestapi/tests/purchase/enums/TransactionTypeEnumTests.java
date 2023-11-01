package com.vertex.quality.connectors.vertexrestapi.tests.purchase.enums;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.vertexrestapi.common.enums.EndPoint;
import com.vertex.quality.connectors.vertexrestapi.common.tests.VertexRestAPIBaseTest;
import com.vertex.quality.connectors.vertexrestapi.common.tests.VertexRestAPITest;
import com.vertex.quality.connectors.vertexrestapi.common.utils.VertexRestAPITestData;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * This test class includes all tests that involve testing the input and
 * output of the transactionType enum values for purchases in the Vertex Rest API
 *
 * @author hho
 */
public class TransactionTypeEnumTests extends VertexRestAPIBaseTest
{
	protected final String INVALID_TRANSACTION_TYPE_ENUM_MESSAGE
		= "No enum constant com.vertexinc.restapi.model.PurchaseRequestType.TransactionTypeEnum.";
	protected final String TRANSACTION_TYPE_REQUEST_PATH = "transactionType";
	protected final String TRANSACTION_TYPE_RESPONSE_PATH = "data.transactionType";
	protected final String FILE_NAME = "AccrualTaxRequest.json";

	/**
	 * Tests to see if putting PURCHASE for the value of transactionType enum is valid
	 * Expected result should be a 200 response with the field containing PURCHASE
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyPurchaseTransactionTypeIsPurchaseTest( )
	{
		String givenTransactionType = "PURCHASE";
		String expectedTransactionType = givenTransactionType;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, TRANSACTION_TYPE_REQUEST_PATH, givenTransactionType)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(TRANSACTION_TYPE_RESPONSE_PATH, equalTo(expectedTransactionType));
	}

	/**
	 * Tests to see if putting RENTAL for the value of transactionType enum is valid
	 * Expected result should be a 200 response with the field containing RENTAL
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyRentalTransactionTypeIsRentalTest( )
	{
		String givenTransactionType = "RENTAL";
		String expectedTransactionType = givenTransactionType;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, TRANSACTION_TYPE_REQUEST_PATH, givenTransactionType)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(TRANSACTION_TYPE_RESPONSE_PATH, equalTo(expectedTransactionType));
	}

	/**
	 * Tests to see if putting LEASE for the value of transactionType enum is valid
	 * Expected result should be a 200 response with the field containing LEASE
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyLeaseTransactionTypeIsLeaseTest( )
	{
		String givenTransactionType = "LEASE";
		String expectedTransactionType = givenTransactionType;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, TRANSACTION_TYPE_REQUEST_PATH, givenTransactionType)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(TRANSACTION_TYPE_RESPONSE_PATH, equalTo(expectedTransactionType));
	}

	/**
	 * Tests to see if putting an invalid value for transactionType enum is invalid
	 * Expected result should be a 400 response with an error message pertaining to an invalid enum
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyInvalidTransactionTypeValueIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, TRANSACTION_TYPE_REQUEST_PATH,
				VertexRestAPITestData.BAD_ENUM_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, equalTo(
				INVALID_TRANSACTION_TYPE_ENUM_MESSAGE + VertexRestAPITestData.BAD_ENUM_TEST_VALUE.toUpperCase()));
	}

	/**
	 * Tests to see if putting null value for transactionType enum is invalid
	 * Expected result should be a 400 response with an error message saying that the field wasn't
	 * found
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyNullTransactionTypeIsInvalidTest( )
	{
		String noTransactionType = "no transactionType";

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, TRANSACTION_TYPE_REQUEST_PATH,
				VertexRestAPITestData.NULL_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, equalTo(noTransactionType));
	}

	/**
	 * Tests to see if putting an integer value for transactionType enum is invalid
	 * Expected result should be a 400 response with an error message pertaining to an invalid enum
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyIntegerTransactionTypeIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, TRANSACTION_TYPE_REQUEST_PATH,
				VertexRestAPITestData.INTEGER_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				equalTo(INVALID_TRANSACTION_TYPE_ENUM_MESSAGE + VertexRestAPITestData.INTEGER_TEST_VALUE));
	}

	/**
	 * Tests to see if putting a boolean value for transactionType enum is invalid
	 * Expected result should be a 400 response with an error message pertaining to an invalid enum
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyBooleanTransactionTypeIsInvalidTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, TRANSACTION_TYPE_REQUEST_PATH,
				VertexRestAPITestData.BOOLEAN_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, equalTo(INVALID_TRANSACTION_TYPE_ENUM_MESSAGE +
															VertexRestAPITestData.BOOLEAN_TO_STRING_TEST_VALUE.toUpperCase()));
	}

	/**
	 * Tests to see if putting one of the sales value for the transactionType enum is invalid
	 * Expected result should be a 400 response with an error message pertaining to an invalid enum
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifySaleTransactionTypeIsInvalidTest( )
	{
		String givenTransactionType = "SALE";

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, TRANSACTION_TYPE_REQUEST_PATH, givenTransactionType)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				equalTo(INVALID_TRANSACTION_TYPE_ENUM_MESSAGE + givenTransactionType.toUpperCase()));
	}
}
