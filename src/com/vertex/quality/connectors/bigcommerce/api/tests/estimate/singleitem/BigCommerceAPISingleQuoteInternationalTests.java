package com.vertex.quality.connectors.bigcommerce.api.tests.estimate.singleitem;

import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceCurrency;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceQuoteRequest;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestDocument;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestItem;
import com.vertex.quality.connectors.bigcommerce.api.tests.base.BigCommerceAPIBaseTest;
import com.vertex.quality.connectors.bigcommerce.common.enums.BigCommerceTestDataAddress;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * the tests of quote estimate requests which contain only 1 item and include addresses outside the USA
 *
 * @author osabha ssalisbury
 */

public class BigCommerceAPISingleQuoteInternationalTests extends BigCommerceAPIBaseTest
{
	/**
	 * JIRA ticket CBC-51
	 *
	 * sending a single Item request , shipping from US to Canada , same billing and shipping
	 * addresses.
	 *
	 * @author osabha
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_regression" })
	public void singleQuoteUSToCanTest( )
	{
		//********************test data*****************//
		final double expectedTaxRate = 0.0;
		final double expectedTaxAmount = 0.0;
		final double expectedLocalTaxRate = 0.0;
		final double expectedLocalTaxAmount = 0.0;

		BigCommerceRequestItem car = apiUtil.buildItem(standardHighPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
			BigCommerceTestDataAddress.CAN_ONTARIO_ADDRESS_1, BigCommerceTestDataAddress.US_CA_ADDRESS_1, true, car);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		Response response = apiUtil.sendEstimateRequest(quoteRequest);
		apiUtil.verifyTwoLevelTaxesQuote(response, expectedTaxRate, expectedTaxAmount, expectedLocalTaxRate,
			expectedLocalTaxAmount);
	}

	/**
	 * this test case sends a request for a single item quote Canada to Canada shipping addresses ,
	 * same billing and shipping
	 * addresses.
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_regression" })
	public void SingleItemQuoteCanToCanTest( )
	{
		//********************test data*****************//
		final double expectedTaxRate = 0.15;
		final double expectedTaxAmount = 117.8;
		final double expectedLocalTaxRate = 0.0;
		final double expectedLocalTaxAmount = 0.0;

		final double paintPriceAmount = 785.3;
		BigCommerceRequestItem paint = apiUtil.buildItem(paintPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
			BigCommerceTestDataAddress.CAN_NEW_BRUNSWICK_ADDRESS_1,
			BigCommerceTestDataAddress.CAN_BRITISH_COLUMBIA_ADDRESS_1, false, paint);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		Response response = apiUtil.sendEstimateRequest(quoteRequest);
		apiUtil.verifyTwoLevelTaxesQuote(response, expectedTaxRate, expectedTaxAmount, expectedLocalTaxRate,
			expectedLocalTaxAmount);
	}
}
