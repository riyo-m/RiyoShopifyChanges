package com.vertex.quality.connectors.bigcommerce.api.tests.estimate.negative;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceCurrency;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceAddress;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceQuoteRequest;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestDocument;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestItem;
import com.vertex.quality.connectors.bigcommerce.api.pojos.BigCommerceValidAddress;
import com.vertex.quality.connectors.bigcommerce.api.tests.base.BigCommerceAPIBaseTest;
import com.vertex.quality.connectors.bigcommerce.common.enums.BigCommerceTestDataAddress;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * the negative tests of estimate requests with invalid addresses
 *
 * @author osabha ssalisbury
 */
public class BigCommerceAPIEstimateNegativeAddressesTests extends BigCommerceAPIBaseTest
{
	/**
	 * this test verifies that if we null all the address fields values
	 * we get a 400 status code, that we sent a bad request.
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_regression" })
	public void nullAddressFieldsNegativeTest( )
	{
		final double sunglassesPriceAmount = 200;
		BigCommerceRequestItem sunglasses = apiUtil.buildItem(sunglassesPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, null, null, false, sunglasses);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		Response response = apiUtil.sendEstimateRequest(quoteRequest);
		apiUtil.assertStatus(response, ResponseCodes.BAD_REQUEST);
	}

	/**
	 * this test verifies that if we delete some address fields( especially, country name and code)
	 * we will get 400 status code, that we sent a bad request.
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_regression" })
	public void deletingAddressFieldsNegativeTest( )
	{
		final double carPriceAmount = 200000.50;
		BigCommerceRequestItem car = apiUtil.buildItem(carPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
			BigCommerceTestDataAddress.CAN_ONTARIO_BAD_ADDRESS, BigCommerceTestDataAddress.US_CA_ADDRESS_1, false, car);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		Response response = apiUtil.sendEstimateRequest(quoteRequest);
		apiUtil.assertStatus(response, ResponseCodes.BAD_REQUEST);
	}

	/**
	 * sending un-matching zip and city to O-series.
	 * checking if it will process is or say bad request.
	 * O-series configs are supposed to let this pass, and handle the transaction from the city level.
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_regression" })
	public void wrongCityZipNegativeTest( )
	{
		//******************************Test Data****************************//
		BigCommerceAddress billingAddress = BigCommerceValidAddress
			.builder()
			.line1("100 Queen St W")
			.city("Toronto")
			.country_name("Canada")
			.country_code("CAN")
			.region_name("Ontario")
			.postal_code("M5H 2N1")
			.build();

		BigCommerceRequestItem car = apiUtil.buildItem(standardHighPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil
			.startBuildDocument(document1Id, true, car)
			.billing_address(billingAddress)
			.destination_address(BigCommerceTestDataAddress.CAN_ONTARIO_ADDRESS_1.buildPojo())
			.origin_address(BigCommerceTestDataAddress.US_CA_ADDRESS_1.buildPojo())
			.build();

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		Response response = apiUtil.sendEstimateRequest(quoteRequest);
		apiUtil.assertStatus(response, ResponseCodes.SUCCESS);
	}

	/**
	 * JIRA ticket CBC-96
	 * checks that the connector handles addresses which have empty line1 and line2 fields in a stable way
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_regression" })
	public void incompleteAddressHandlingNegativeTest( )
	{
		BigCommerceAddress brokenBillingAddress = BigCommerceTestDataAddress.US_AZ_ADDRESS_1.buildPojo();
		brokenBillingAddress.setLine1(null);
		brokenBillingAddress.setLine2(null);

		BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil
			.startBuildDocument(document1Id, false, shirt)
			.billing_address(brokenBillingAddress)
			.destination_address(BigCommerceTestDataAddress.US_AZ_ADDRESS_2.buildPojo())
			.origin_address(BigCommerceTestDataAddress.US_AZ_ADDRESS_3.buildPojo())
			.build();

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		Response response = apiUtil.sendEstimateRequest(quoteRequest);
		apiUtil.assertStatus(response, ResponseCodes.SUCCESS);
	}
}
