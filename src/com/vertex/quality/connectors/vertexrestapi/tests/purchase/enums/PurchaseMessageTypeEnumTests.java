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
 * output of the purchaseMessage enum values for purchases in the Vertex Rest API
 *
 * @author hho
 */
public class PurchaseMessageTypeEnumTests extends VertexRestAPIBaseTest
{
	protected final String INVALID_PURCHASE_MESSAGE_ENUM_VALUE
		= "No enum constant com.vertexinc.restapi.model.PurchaseRequestType.PurchaseMessageTypeEnum.";
	protected final String PURCHASE_MESSAGE_RESPONSE_PATH = "data.purchaseMessage";
	protected final String PURCHASE_MESSAGE_TYPE_REQUEST_PATH = "purchaseMessageType";
	protected final String FILE_NAME = "AccrualTaxRequest.json";

	/**
	 * Tests to see if putting ACCRUAL for the value of purchaseMessageType enum is valid
	 * Expected result should be a 200 response with the field containing ACCRUAL
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyAccrualMessageIsAccrualMessageTest( )
	{
		String givenMessageType = "ACCRUAL";
		String expectedMessageType = givenMessageType;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, PURCHASE_MESSAGE_TYPE_REQUEST_PATH, givenMessageType)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(PURCHASE_MESSAGE_RESPONSE_PATH, equalTo(expectedMessageType));
	}

	/**
	 * Tests to see if putting PURCHASE_ORDER for the value of purchaseMessageType enum is valid
	 * Expected result should be a 200 response with the field containing PURCHASE_ORDER
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyPurchaseMessageIsPurchaseMessageTest( )
	{
		String givenMessageType = "PURCHASE_ORDER";
		String expectedMessageType = givenMessageType;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, PURCHASE_MESSAGE_TYPE_REQUEST_PATH, givenMessageType)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(PURCHASE_MESSAGE_RESPONSE_PATH, equalTo(expectedMessageType));
	}

	/**
	 * Tests to see if putting INVOICE_VERIFICATION for the value of purchaseMessageType enum is
	 * valid
	 * Expected result should be a 200 response with the field containing INVOICE_VERIFICATION
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyInvoiceMessageIsInvoiceMessageTest( )
	{
		String givenMessageType = "INVOICE_VERIFICATION";
		String expectedMessageType = givenMessageType;

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, PURCHASE_MESSAGE_TYPE_REQUEST_PATH, givenMessageType)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.assertThat()
			.body(PURCHASE_MESSAGE_RESPONSE_PATH, equalTo(expectedMessageType));
	}

	/**
	 * Tests to see if putting ACCRUAL for the value of purchaseMessageType enum is valid
	 * NOT SURE
	 * Expected result should be a 400(?) response with an error messaging pertaining to no taxes
	 * inputted
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyDistributeMessageIsDistributeMessageTest( )
	{
		String givenMessageType = "DISTRIBUTE_TAX";

		String errorMessage
			= "No input total taxes have been provided for this distribute tax transaction. Please verify the details for this transaction.\n";

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, PURCHASE_MESSAGE_TYPE_REQUEST_PATH, givenMessageType)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, equalTo(errorMessage));
	}

	/**
	 * Tests to see if putting an invalid value for the purchaseMessageType enum is invalid
	 * Expected result should be a 400 response with an error messaging pertaining to no enum value
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyInvalidMessageIsInvalidMessageTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, PURCHASE_MESSAGE_TYPE_REQUEST_PATH,
				VertexRestAPITestData.BAD_ENUM_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				equalTo(INVALID_PURCHASE_MESSAGE_ENUM_VALUE + VertexRestAPITestData.BAD_ENUM_TEST_VALUE.toUpperCase()));
	}

	/**
	 * Tests to see that if the given purchaseMessageType is null, the purchaseMessage for the
	 * response is invalid
	 * Expected result should be a 400 response with an error messaging pertaining to saying
	 * purchaseMessageType field is missing
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyNullMessageIsInvalidMessageTest( )
	{
		String noPurchaseField = "no purchaseMessageType";

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, PURCHASE_MESSAGE_TYPE_REQUEST_PATH,
				VertexRestAPITestData.NULL_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, equalTo(noPurchaseField));
	}

	/**
	 * Tests to see that if the given purchaseMessageType is an integer, the purchaseMessage for the
	 * response is invalid
	 * Expected result should be a 400 response with an error messaging pertaining to no enum value
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyIntegerMessageIsInvalidMessageTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, PURCHASE_MESSAGE_TYPE_REQUEST_PATH,
				VertexRestAPITestData.INTEGER_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH,
				equalTo(INVALID_PURCHASE_MESSAGE_ENUM_VALUE + VertexRestAPITestData.INTEGER_TEST_VALUE));
	}

	/**
	 * Tests to see that if the given purchaseMessageType is a boolean, the purchaseMessage for the
	 * response is invalid
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyBooleanMessageIsInvalidMessageTest( )
	{
		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, PURCHASE_MESSAGE_TYPE_REQUEST_PATH,
				VertexRestAPITestData.BOOLEAN_TEST_VALUE)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, equalTo(INVALID_PURCHASE_MESSAGE_ENUM_VALUE +
															VertexRestAPITestData.BOOLEAN_TO_STRING_TEST_VALUE.toUpperCase()));
	}

	/**
	 * Tests to see that if the given purchaseMessageType is one of the saleMessageType value,
	 * QUOTATION, it should not be a valid response
	 * Expected result should be a 400 response with an error messaging pertaining to no enum value
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifyQuotationMessageIsInvalidTest( )
	{
		String givenMessageType = "QUOTATION";

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, PURCHASE_MESSAGE_TYPE_REQUEST_PATH, givenMessageType)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, equalTo(INVALID_PURCHASE_MESSAGE_ENUM_VALUE + givenMessageType));
	}

	/**
	 * Tests to see that if the given purchaseMessageType is one of the saleMessageType value,
	 * INVOICE, it should not be a valid response
	 * Expected result should be a 400 response with an error messaging pertaining to no enum value
	 */
	@Test(groups = { "vertex_rest_api", "purchase", "enums" })
	public void verifySalesInvoiceMessageIsInvalidTest( )
	{
		String givenMessageType = "INVOICE";

		Response response = new VertexRestAPITest.Builder(apiUtil, EndPoint.PURCHASE, FILE_NAME)
			.addJsonField(VertexRestAPITestData.ROOT_PATH, PURCHASE_MESSAGE_TYPE_REQUEST_PATH, givenMessageType)
			.build()
			.createRequest();

		response
			.then()
			.statusCode(ResponseCodes.BAD_REQUEST.getResponseCode())
			.and()
			.assertThat()
			.body(VertexRestAPITestData.ERROR_PATH, equalTo(INVALID_PURCHASE_MESSAGE_ENUM_VALUE + givenMessageType));
	}
}
