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
 * misc negative tests of estimate requests
 *
 * @author osabha ssalisbury
 */
public class BigCommerceAPIEstimateNegativeTests extends BigCommerceAPIBaseTest
{
	/**
	 * this test that we get a 404 status code if we send the wrong store number in the request.
	 * saying that the store wasn't found.
	 */
	@Test(groups = { "bigCommerce_api","bigCommerce_regression" })
	public void wrongStoreNumberNegativeTest( )
	{
		final String badStoreNumber = "99";

		BigCommerceRequestItem car = apiUtil.buildItem(standardHighPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
			BigCommerceTestDataAddress.CAN_ONTARIO_ADDRESS_1, BigCommerceTestDataAddress.US_CA_ADDRESS_1, true, car);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		Response response = apiUtil.sendEstimateRequest(quoteRequest, badStoreNumber);
		apiUtil.assertStatus(response, ResponseCodes.NOT_FOUND);
	}
}
