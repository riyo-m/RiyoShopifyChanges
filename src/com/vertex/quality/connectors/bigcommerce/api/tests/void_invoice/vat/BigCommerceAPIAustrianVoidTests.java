package com.vertex.quality.connectors.bigcommerce.api.tests.void_invoice.vat;

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
 * CBC-579
 * the test cases for the commit endpoint
 *
 * @author vivek
 */

public class BigCommerceAPIAustrianVoidTests extends BigCommerceAPIBaseTest {
    /**
     * CBC-579
     * this test case sends a commit request and then voids it through the void endpoint, to assert getting a no content
     * success code, which indirectly proves that the commit succeeded EU to EU.
     */
    @Test(groups = {"bigCommerce_api", "bigCommerce_smoke", "bigCommerce_regression"})
    public void commitAndVoidEUToEUTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, BigCommerceTestDataAddress.AUSTRIA_MITTLEBERG_ADDRESS,
                BigCommerceTestDataAddress.AUSTRIA_MITTLEBERG_ADDRESS, false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);

        String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

        Response voidResponse = apiUtil.sendVoidRequest(invoiceId);
        apiUtil.assertStatus(voidResponse, ResponseCodes.SUCCESS_NO_CONTENT);
    }

    /**
     * CBC-579
     * this test case sends a commit request and then voids it through the void endpoint, to assert getting a no content
     * success code, which indirectly proves that the commit succeeded EU to US.
     */
    @Test(groups = {"bigCommerce_api", "bigCommerce_regression"})
    public void commitAndVoidEUToUSTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, BigCommerceTestDataAddress.US_CA_ADDRESS,
                BigCommerceTestDataAddress.AUSTRIA_MITTLEBERG_ADDRESS, false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);

        String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

        Response voidResponse = apiUtil.sendVoidRequest(invoiceId);
        apiUtil.assertStatus(voidResponse, ResponseCodes.SUCCESS_NO_CONTENT);
    }

    /**
     * CBC-579
     * this test case sends a commit request and then voids it through the void endpoint, to assert getting a no content
     * success code, which indirectly proves that the commit succeeded US to EU.
     */
    @Test(groups = {"bigCommerce_api", "bigCommerce_regression"})
    public void commitAndVoidUSToEUTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, BigCommerceTestDataAddress.AUSTRIA_MITTLEBERG_ADDRESS,
                BigCommerceTestDataAddress.US_PA_ADDRESS_2, false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);

        String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

        Response voidResponse = apiUtil.sendVoidRequest(invoiceId);
        apiUtil.assertStatus(voidResponse, ResponseCodes.SUCCESS_NO_CONTENT);
    }
}
