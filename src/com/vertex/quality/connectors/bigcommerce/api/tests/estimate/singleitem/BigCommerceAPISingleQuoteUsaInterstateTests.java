package com.vertex.quality.connectors.bigcommerce.api.tests.estimate.singleitem;

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
 * the tests of quote estimate requests which contain only 1 item and include addresses from multiple USA states
 *
 * @author osabha ssalisbury
 */

public class BigCommerceAPISingleQuoteUsaInterstateTests extends BigCommerceAPIBaseTest
{
	/**
	 * this test case sends a request for a single item quote interstate, same billing and shipping
	 * addresses.
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_smoke" })
	public void singleQuoteInterstateTest( )
	{
		//********************test data*****************//
		final double expectedTaxRate = 0.06;
		final double expectedTaxAmount = 15.0;

		final int shirtPriceAmount = 250;
		BigCommerceRequestItem shirt = apiUtil.buildItem(shirtPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, BigCommerceTestDataAddress.US_PA_ADDRESS_1,
			BigCommerceTestDataAddress.US_CA_ADDRESS_1, false, shirt);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		Response response = apiUtil.sendEstimateRequest(quoteRequest);
		apiUtil.assertStatus(response, ResponseCodes.SUCCESS);

		apiUtil.assertResponseDoubleEquals(response, item1FedTaxRatePath, expectedTaxRate);
		apiUtil.assertResponseDoubleEquals(response, item1FedTaxAmountPath, expectedTaxAmount);
	}

	/**
	 * single item quote request from Pennsylvania to California
	 * must return 7 jurisdiction taxes.
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_regression" })
	public void singleQuoteInterstatePaToCaTest( )
	{
		final double expectedTaxRate = 0.06;
		final double expectedTaxAmount = 12000;
		final double expectedLocalTaxRate = 0.0125;
		final double expectedLocalTaxAmount = 2500;

		BigCommerceRequestItem car = apiUtil.buildItem(standardHighPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, BigCommerceTestDataAddress.US_CA_ADDRESS_2,
			BigCommerceTestDataAddress.US_PA_ADDRESS_3, true, car);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		Response response = apiUtil.sendEstimateRequest(quoteRequest);
		apiUtil.verifyTwoLevelTaxesQuote(response, expectedTaxRate, expectedTaxAmount, expectedLocalTaxRate,
			expectedLocalTaxAmount);
	}

	/**
	 * JIRA ticket CBC-98
	 *
	 * this case accepts certain taxability code for the customer and asserts and adjusted tax rates for them.
	 *
	 * @author osabha
	 */
	@Test(groups = { "bigCommerce_api"})
	public void customerTaxabilityCodeTest( )
	{
		final double expectedTaxRate = 0.0;
		final double expectedTaxAmount = 0.0;
		final double expectedLocalTaxRate = 0.0;
		final double expectedLocalTaxAmount = 0.0;

		final String exemptTaxCode = "tax_exempt";

		BigCommerceRequestItem car = apiUtil.buildItem(standardHighPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, BigCommerceTestDataAddress.US_CA_ADDRESS_2,
			BigCommerceTestDataAddress.US_PA_ADDRESS_3, true, car);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);
		quoteRequest
			.retrieveCustomer()
			.setTaxability_code(exemptTaxCode);

		Response response = apiUtil.sendEstimateRequest(quoteRequest);
		apiUtil.verifyTwoLevelTaxesQuote(response, expectedTaxRate, expectedTaxAmount, expectedLocalTaxRate,
			expectedLocalTaxAmount);
	}
}
