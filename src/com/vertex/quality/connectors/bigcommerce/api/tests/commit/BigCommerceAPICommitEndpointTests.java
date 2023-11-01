package com.vertex.quality.connectors.bigcommerce.api.tests.commit;

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
 * the test cases for the commit endpoint
 *
 * @author osabha
 */

public class BigCommerceAPICommitEndpointTests extends BigCommerceAPIBaseTest {
    /**
     * CBC-66
     * this test case sends a commit request and then voids it through the void endpoint, to assert getting a no content
     * success code, which indirectly proves that the commit succeeded
     */
    @Test(groups = {"bigCommerce_api", "bigCommerce_regression", "bigCommerce_smoke"})
    public void commitAndVoidTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, BigCommerceTestDataAddress.US_PA_ADDRESS_1,
                BigCommerceTestDataAddress.US_PA_ADDRESS_2, false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);

        String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

        Response voidResponse = apiUtil.sendVoidRequest(invoiceId);
        apiUtil.assertStatus(voidResponse, ResponseCodes.SUCCESS_NO_CONTENT);
    }
}
