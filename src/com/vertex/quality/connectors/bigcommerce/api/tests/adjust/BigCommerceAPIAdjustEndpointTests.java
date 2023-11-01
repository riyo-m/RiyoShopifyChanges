package com.vertex.quality.connectors.bigcommerce.api.tests.adjust;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceCurrency;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.*;
import com.vertex.quality.connectors.bigcommerce.api.pojos.BigCommerceValidRequestItem;
import com.vertex.quality.connectors.bigcommerce.api.pojos.BigCommerceValidRequestItemPrice;
import com.vertex.quality.connectors.bigcommerce.api.pojos.BigCommerceValidTaxClass;
import com.vertex.quality.connectors.bigcommerce.api.tests.base.BigCommerceAPIBaseTest;
import com.vertex.quality.connectors.bigcommerce.common.enums.BigCommerceTestDataAddress;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * the test cases for the adjust endpoint
 *
 * @author osabha ssalisbury
 */

public class BigCommerceAPIAdjustEndpointTests extends BigCommerceAPIBaseTest
{
	/**
	 * CBC-65
	 * this test case sends a commit request and then get's the invoice id from the response.
	 * then sends an adjustment request for the same invoice with new quote payload.
	 * then asserts for tax calculation according to the new quote.
	 *
	 * @author osabha
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_regression","bigCommerce_smoke"})
	public void commitAndAdjustRequestsTest( )
	{
		final double expectedTaxRate = 0.0;
		final double expectedTaxAmount = 0.0;
		final double expectedLocalTaxRate = 0.0;
		final double expectedLocalTaxAmount = 0.0;

		BigCommerceTestDataAddress paAddress1 = BigCommerceTestDataAddress.US_PA_ADDRESS_1;
		BigCommerceTestDataAddress paAddress2 = BigCommerceTestDataAddress.US_PA_ADDRESS_2;

		BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);
		BigCommerceRequestItem exemptShirt = apiUtil.buildItem(standardLowPriceAmount, true, defaultQuantity, item2Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, paAddress1, paAddress2, false, shirt);
		BigCommerceRequestDocument doc2 = apiUtil.buildDocument(document2Id, paAddress1, paAddress2, false,
			exemptShirt);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);
		BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequest(quote2Id, BigCommerceCurrency.USD,
			customer1Id, doc2);

		String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

		Response adjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId);
		apiUtil.verifyTwoLevelTaxesQuote(adjustResponse, expectedTaxRate, expectedTaxAmount, expectedLocalTaxRate,
			expectedLocalTaxAmount);
	}

	/**
	 * JIRA ticket CBC-81
	 *
	 * creates an invoice, then verifies that that invoice can't be adjusted by an adjust request which only has
	 * the invoice's id in its body, then verifies that that invoice can be adjusted by an adjust request which has
	 * the invoice's id in its query
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_regression" })
	public void adjustRequestInvoiceIdProcessingTest( )
	{
		BigCommerceTestDataAddress paAddress1 = BigCommerceTestDataAddress.US_PA_ADDRESS_1;
		BigCommerceTestDataAddress paAddress2 = BigCommerceTestDataAddress.US_PA_ADDRESS_2;

		BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);
		BigCommerceRequestItem exemptShirt = apiUtil.buildItem(standardLowPriceAmount, true, defaultQuantity, item2Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, paAddress1, paAddress2, false, shirt);
		BigCommerceRequestDocument doc2 = apiUtil.buildDocument(document2Id, paAddress1, paAddress2, false,
			exemptShirt);

		final String adjustedQuoteRequestId = quote3Id;
		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

		//i.e. an address request which has the same id as the newly-generated invoice
		BigCommerceQuoteRequest adjustRequestWithSameId = apiUtil.buildQuoteRequest(invoiceId, BigCommerceCurrency.USD,
			customer1Id, doc2);

		Response badAdjustResponse = apiUtil.sendAdjustRequest(adjustRequestWithSameId, adjustedQuoteRequestId);
		apiUtil.assertStatus(badAdjustResponse, ResponseCodes.SUCCESS);

		BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequest(adjustedQuoteRequestId,
			BigCommerceCurrency.USD, customer1Id, doc2);

		Response goodAdjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId);
		apiUtil.assertStatus(goodAdjustResponse, ResponseCodes.SUCCESS);
	}

	/**
	 * JIRA ticket CBC-82
	 *
	 * creates an invoice and then tries to adjust it by deleting some fields from an item
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_regression" })
	public void itemFieldDeletionTest( )
	{
		BigCommerceTestDataAddress caAddress1 = BigCommerceTestDataAddress.US_CA_ADDRESS_1;
		BigCommerceTestDataAddress caAddress2 = BigCommerceTestDataAddress.US_CA_ADDRESS_2;

		BigCommerceRequestItemPrice glassesPrice = new BigCommerceValidRequestItemPrice(false, 150);

		BigCommerceTaxClass glassesTaxClass = BigCommerceValidTaxClass
			.builder()
			.class_id("0")
			.code("")
			.name("")
			.build();

		BigCommerceRequestItem glasses = apiUtil.buildItem(glassesPrice, false, defaultQuantity, item1Id);
		glasses.setItem_code("ABC-123");
		glasses.setName("Stylish Sunglasses");
		glasses.setTax_class(glassesTaxClass);

		BigCommerceRequestItem trimmedGlasses = BigCommerceValidRequestItem
			.builder()
			.name("Stylish Sunglasses")
			.price(glassesPrice)
			.quantity(1)
			.id(item1Id)
			.item_code("ABC-123")
			.build();

		BigCommerceRequestDocument doc = apiUtil.buildDocument(document1Id, caAddress1, caAddress2, false, glasses);
		BigCommerceRequestDocument trimmedDoc = apiUtil.buildDocument(document2Id, caAddress1, caAddress2, false,
			trimmedGlasses);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc);
		BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequest(quote2Id, BigCommerceCurrency.USD,
			customer1Id, trimmedDoc);

		String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

		Response adjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId);
		apiUtil.assertStatus(adjustResponse, ResponseCodes.BAD_REQUEST);
	}
}
