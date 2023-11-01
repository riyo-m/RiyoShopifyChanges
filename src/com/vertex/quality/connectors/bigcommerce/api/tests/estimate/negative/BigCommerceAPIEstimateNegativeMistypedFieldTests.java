package com.vertex.quality.connectors.bigcommerce.api.tests.estimate.negative;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceCurrency;
import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceRequestItemType;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceQuoteRequest;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestDocument;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestItem;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestItemPrice;
import com.vertex.quality.connectors.bigcommerce.api.pojos.BigCommerceValidRequestItemPrice;
import com.vertex.quality.connectors.bigcommerce.api.pojos.invalid.BigCommerceQuoteRequestCustomerWithBoolTaxCode;
import com.vertex.quality.connectors.bigcommerce.api.pojos.invalid.BigCommerceRequestItemWithDoubleWrapping;
import com.vertex.quality.connectors.bigcommerce.api.tests.base.BigCommerceAPIBaseTest;
import com.vertex.quality.connectors.bigcommerce.common.enums.BigCommerceTestDataAddress;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * negative tests of estimate requests where some field in the request has a value of a different data type
 * than the API expects to find in that field, according to the Swagger documentation
 *
 * @author osabha ssalisbury
 */
public class BigCommerceAPIEstimateNegativeMistypedFieldTests extends BigCommerceAPIBaseTest
{
	/**
	 * JIRA ticket CBC-93
	 *
	 * tests that the connector returns a Bad Request error message when it's passed a quote request with a customer
	 * whose taxability_code field has a boolean-datatype value in it rather than a string, because the Swagger
	 * documentation of the connector API specifies that that field's value should be a string
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "bigCommerce_apiDisabled","bigCommerce_regression" })
	public void customerBooleanTaxCodeNegativeTest( )
	{
		BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, true, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, BigCommerceTestDataAddress.US_PA_ADDRESS_1,
			BigCommerceTestDataAddress.US_PA_ADDRESS_2, false, shirt);

		BigCommerceQuoteRequestCustomerWithBoolTaxCode badCustomer = new BigCommerceQuoteRequestCustomerWithBoolTaxCode(
			customer1Id, false);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, badCustomer,
			doc1);

		Response response = apiUtil.sendEstimateRequest(quoteRequest);
		apiUtil.assertStatus(response, ResponseCodes.SUCCESS);
	}

	/**
	 * JIRA ticket CBC-94
	 *
	 * tests that the connector returns a Bad Request error message when it's passed a quote request with an item
	 * whose wrapping field has a double-datatype value in it rather than either nothing or another item JSON object,
	 * because the Swagger documentation of the connector API specifies that
	 * that field's value should be an 'item' JSON object
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_regression" })
	public void wrappingFieldDoubleValueNegativeTest( )
	{
		BigCommerceRequestItemPrice carPrice = new BigCommerceValidRequestItemPrice(false, standardHighPriceAmount);

		BigCommerceRequestItemWithDoubleWrapping badCar = BigCommerceRequestItemWithDoubleWrapping
			.builder()
			.name("item")
			.price(carPrice)
			.quantity(1)
			.id(item1Id)
			.type(BigCommerceRequestItemType.ITEM.getName())
			.tax_exempt(false)
			.wrapping(15.5)
			.build();

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, BigCommerceTestDataAddress.US_CA_ADDRESS_2,
			BigCommerceTestDataAddress.CAN_ONTARIO_ADDRESS_1, true, badCar);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		Response response = apiUtil.sendEstimateRequest(quoteRequest);
		apiUtil.assertStatus(response, ResponseCodes.BAD_REQUEST);
	}
}
