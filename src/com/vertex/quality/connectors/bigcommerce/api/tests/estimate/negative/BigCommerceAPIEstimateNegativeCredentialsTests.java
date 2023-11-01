package com.vertex.quality.connectors.bigcommerce.api.tests.estimate.negative;

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
 * the negative tests of estimate requests with invalid credentials
 *
 * @author osabha ssalisbury
 */
public class BigCommerceAPIEstimateNegativeCredentialsTests extends BigCommerceAPIBaseTest
{
	/**
	 * this test verifies that if we send a request with no credentials we get a 401 response code
	 * that we  didn't send the credentials
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_regression" })
	public void noCredentialsSentNegativeTest( )
	{
		BigCommerceRequestItem car = apiUtil.buildItem(standardHighPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
			BigCommerceTestDataAddress.CAN_ONTARIO_ADDRESS_1, BigCommerceTestDataAddress.US_CA_ADDRESS_1, true, car);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		apiUtil.setBasicAuth(null, null);

		Response response = apiUtil.sendEstimateRequest(quoteRequest);
		apiUtil.assertStatus(response, ResponseCodes.UNAUTHORIZED);
	}

	/**
	 * this test verifies that if we send a request with the wrong credentials we get a 401 status code
	 * that we are unauthorized.
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_regression" })
	public void badCredentialsNegativeTest( )
	{
		//******************************Test Data****************************//
		final String wrongUsername = "mikey";
		final String wrongPassword = "12316";

		BigCommerceRequestItem car = apiUtil.buildItem(standardHighPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
			BigCommerceTestDataAddress.CAN_ONTARIO_ADDRESS_1, BigCommerceTestDataAddress.US_CA_ADDRESS_1, true, car);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		apiUtil.setBasicAuth(wrongUsername, wrongPassword);

		Response response = apiUtil.sendEstimateRequest(quoteRequest);
		apiUtil.assertStatus(response, ResponseCodes.UNAUTHORIZED);
	}
}
