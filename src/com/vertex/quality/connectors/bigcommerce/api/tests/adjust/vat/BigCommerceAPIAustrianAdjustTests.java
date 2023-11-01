package com.vertex.quality.connectors.bigcommerce.api.tests.adjust.vat;

import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceCurrency;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceQuoteRequest;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestDocument;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestItem;
import com.vertex.quality.connectors.bigcommerce.api.tests.base.BigCommerceAPIBaseTest;
import com.vertex.quality.connectors.bigcommerce.common.enums.BigCommerceTestDataAddress;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * the test cases for the adjust endpoint
 *
 * @author vivek kumar
 */

public class BigCommerceAPIAustrianAdjustTests extends BigCommerceAPIBaseTest
{
    /**
     * CBC-579
     * this test case sends a commit request and then get's the invoice id from the response.
     * then sends an adjustment request for the same invoice with new quote payload.
     * then asserts for tax calculation according to the new quote.
     *
     * @author vivek
     */
    @Test(groups = { "bigCommerce_vat","bigCommerce_smoke","bigCommerce_regression" })
    public void commitAndAdjustEUToEURequestsTest( )
    {
        final String expectedTaxRate = "0.2";


        BigCommerceTestDataAddress euAddress = BigCommerceTestDataAddress.AUSTRIA_MITTLEBERG_ADDRESS;
        BigCommerceTestDataAddress euAddress1 = BigCommerceTestDataAddress.AUSTRIA_MITTLEBERG_ADDRESS;

        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);


        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, euAddress, euAddress1, false, shirt);
        BigCommerceRequestDocument doc2 = apiUtil.buildDocument(document2Id, euAddress, euAddress1, false,
                shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequest(quote2Id, BigCommerceCurrency.USD,
                customer1Id, doc2);

        String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

        Response adjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId);
        JsonPath extractor = adjustResponse.jsonPath();
        Float tax = extractor.get("documents[0].items[0].price.sales_tax_summary[0].rate");
        String taxRate = String.valueOf(tax);
        Assert.assertEquals(expectedTaxRate,taxRate);
    }

    /**
     * JIRA ticket CBC-579
     * this test case sends a commit request and then get's the invoice id from the response.
     * then sends an adjustment request for the same invoice with new quote payload.
     * then asserts for tax calculation according to the new quote.
     *
     * @author vivek
     */
    @Test(groups = { "bigCommerce_vat","bigCommerce_regression" })
    public void adjustRequestUSToEUTest( )
    {
        final String expectedTaxRate = "0.0";


        BigCommerceTestDataAddress euAddress = BigCommerceTestDataAddress.AUSTRIA_MITTLEBERG_ADDRESS;
        BigCommerceTestDataAddress paAddress2 = BigCommerceTestDataAddress.US_PA_ADDRESS_2;

        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, euAddress, paAddress2, false, shirt);
        BigCommerceRequestDocument doc2 = apiUtil.buildDocument(document2Id, euAddress, paAddress2, false,
                shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequest(quote2Id, BigCommerceCurrency.USD,
                customer1Id, doc2);

        String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

        Response adjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId);
        JsonPath extractor = adjustResponse.jsonPath();
        Float tax = extractor.get("documents[0].items[0].price.sales_tax_summary[0].rate");
        String taxRate = String.valueOf(tax);
        Assert.assertEquals(expectedTaxRate, taxRate);
    }

    /**
     * JIRA ticket CBC-579
     * this test case sends a commit request and then get's the invoice id from the response.
     * then sends an adjustment request for the same invoice with new quote payload.
     * then asserts for tax calculation according to the new quote.
     *
     * @author vivek
     */
    @Test(groups = { "bigCommerce_api","bigCommerce_regression" })
    public void adjustRequestEUToUSTest( )
    {
        final String expectedTaxRate = "0.0";


        BigCommerceTestDataAddress euAddress = BigCommerceTestDataAddress.AUSTRIA_MITTLEBERG_ADDRESS;
        BigCommerceTestDataAddress paAddress2 = BigCommerceTestDataAddress.US_PA_ADDRESS_2;

        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, paAddress2, euAddress, false, shirt);
        BigCommerceRequestDocument doc2 = apiUtil.buildDocument(document2Id, paAddress2, euAddress, false,
                shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequest(quote2Id, BigCommerceCurrency.USD,
                customer1Id, doc2);

        String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

        Response adjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId);
        JsonPath extractor = adjustResponse.jsonPath();
        Float tax = extractor.get("documents[0].items[0].price.sales_tax_summary[0].rate");
        String taxRate = String.valueOf(tax);
        Assert.assertEquals(expectedTaxRate, taxRate);
    }
}
