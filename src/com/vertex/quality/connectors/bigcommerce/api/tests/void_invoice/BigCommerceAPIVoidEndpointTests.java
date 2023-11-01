package com.vertex.quality.connectors.bigcommerce.api.tests.void_invoice;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceCurrency;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceQuoteRequest;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestDocument;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestItem;
import com.vertex.quality.connectors.bigcommerce.api.tests.base.BigCommerceAPIBaseTest;
import com.vertex.quality.connectors.bigcommerce.common.enums.BigCommerceTestDataAddress;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * the test cases for the void endpoint
 *
 * @author osabha
 */

public class BigCommerceAPIVoidEndpointTests extends BigCommerceAPIBaseTest
{
	/**
	 * CBC-67
	 * this test case sends a commit request and then sends a void request right after it,
	 * then sends another void request for the previously voided invoice and asserts for getting a bad request response
	 * that says the identified invoice was already deleted
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_regression" })
	public void commitThenVoidThenVoidTest( )
	{
		BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, BigCommerceTestDataAddress.US_PA_ADDRESS_1,
			BigCommerceTestDataAddress.US_PA_ADDRESS_2, false, shirt);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		Response estimateResponse = apiUtil.sendEstimateRequest(quoteRequest);
		apiUtil.assertStatus(estimateResponse, ResponseCodes.SUCCESS);

		Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
		apiUtil.assertStatus(commitResponse, ResponseCodes.SUCCESS);

		String invoiceId = commitResponse
			.then()
			.extract()
			.jsonPath()
			.getString("id");

		Response voidResponse = apiUtil.sendVoidRequest(invoiceId);
		apiUtil.assertStatus(voidResponse, ResponseCodes.SUCCESS_NO_CONTENT);

		Response doubledVoidResponse = apiUtil.sendVoidRequest(invoiceId);
		apiUtil.assertStatus(doubledVoidResponse, ResponseCodes.SUCCESS_NO_CONTENT);
	}
}
