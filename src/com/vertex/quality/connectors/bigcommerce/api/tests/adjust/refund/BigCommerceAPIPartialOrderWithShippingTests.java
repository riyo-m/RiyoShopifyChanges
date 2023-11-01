package com.vertex.quality.connectors.bigcommerce.api.tests.adjust.refund;

import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceCurrency;
import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceTestData;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceQuoteRequest;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestDocument;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestItem;
import com.vertex.quality.connectors.bigcommerce.api.tests.base.BigCommerceAPIBaseTest;
import com.vertex.quality.connectors.bigcommerce.common.enums.BigCommerceTestDataAddress;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * CBC-177
 * this test case sends a commit request and then get's the invoice id from the response.
 * then sends an adjustment request for the same invoice id and check the reversal transaction.
 * Reversal transaction should appear in the log with Reversal RequestType and Reversal Response Type.
 * <p>
 * CSA-911:- Return Sales order with shipping address to refund with following conditions
 * 1. with in same month
 * 2. 1 day less than 3 months
 * 3. 3 month
 * 4. 3 months(+1 day)
 * 5. 6 months out(Tax is paid to jurisdiction)
 *
 * @author
 */

public class BigCommerceAPIPartialOrderWithShippingTests extends BigCommerceAPIBaseTest
{
	@Test(groups = { "bigCommerce_refund","bigCommerce_smoke","bigCommerce_regression" })
	public void partialOrderWithShippingTest( )
	{
        BigCommerceTestDataAddress paAddress1 = BigCommerceTestDataAddress.US_PA_ADDRESS_1;
        BigCommerceTestDataAddress paAddress2 = BigCommerceTestDataAddress.US_PA_ADDRESS_2;

        BigCommerceTestDataAddress paAddress3 = BigCommerceTestDataAddress.US_PA_ADDRESS_3;
        BigCommerceTestDataAddress paAddress4 = BigCommerceTestDataAddress.US_PA_ADDRESS_4;

        BigCommerceRequestItem shirts = apiUtil.buildItem(standardLowPriceAmount, false, defaultOrderQuantity, item1Id);
        BigCommerceRequestItem returnShirt = apiUtil.buildItem(standardLowPriceAmount, false, refundQuantity, item2Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, paAddress1, paAddress2, false, shirts);
        BigCommerceRequestDocument doc2 = apiUtil.buildDocument(document2Id, paAddress3, paAddress4, false,
                returnShirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);

		String fromDate = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

        String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

        BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequest(invoiceId, BigCommerceCurrency.USD,
                customer1Id, doc2);

        Response adjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId, standardLowPriceAmount);

        Response messageLogResponse = apiUtil.sendReversalGetRequest(fromDate, invoiceId);
        apiUtil.verifyMessageLogKeyword(messageLogResponse, invoiceId);
    }
}