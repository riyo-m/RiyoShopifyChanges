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
 * the tests of quote estimate requests which contain only 1 item and contain addresses from only one USA state
 *
 * @author osabha ssalisbury
 */

public class BigCommerceAPISingleQuoteUsaIntrastateTests extends BigCommerceAPIBaseTest
{
	/**
	 * this test case sends a request for a single item quote intraState, same billing and shipping
	 * addresses.
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_regression" })
	public void singleQuoteIntraStateTest( )
	{
		//********************test data*****************//
		final double expectedTaxRate = 0.06;
		final double expectedTaxAmount = 6.6;
		final double expectedLocalTaxRate = 0.02;
		final double expectedLocalTaxAmount = 2.2;

		BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, BigCommerceTestDataAddress.US_PA_ADDRESS_1,
			BigCommerceTestDataAddress.US_PA_ADDRESS_2, false, shirt);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		Response response = apiUtil.sendEstimateRequest(quoteRequest);
		apiUtil.verifyTwoLevelTaxesQuote(response, expectedTaxRate, expectedTaxAmount, expectedLocalTaxRate,
			expectedLocalTaxAmount);
	}

	/**
	 * this test case sends a request for a single item quote intrastate, same billing and shipping
	 * addresses.
	 * tax exempt customer
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_regression" })
	public void singleQuoteIntrastateTaxExemptProdTest( )
	{
		//********************test data*****************//
		final double expectedTaxRate = 0.0;
		final double expectedTaxAmount = 0.0;
		final double expectedLocalTaxRate = 0.0;
		final double expectedLocalTaxAmount = 0.0;

		BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, true, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, BigCommerceTestDataAddress.US_PA_ADDRESS_1,
			BigCommerceTestDataAddress.US_PA_ADDRESS_2, false, shirt);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		Response response = apiUtil.sendEstimateRequest(quoteRequest);
		apiUtil.verifyTwoLevelTaxesQuote(response, expectedTaxRate, expectedTaxAmount, expectedLocalTaxRate,
			expectedLocalTaxAmount);
	}
}
