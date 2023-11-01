package com.vertex.quality.connectors.bigcommerce.api.tests.estimate.singleitem.vat;

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
 * the tests of quote estimate requests which contain only 1 item and includes different addresses.
 *
 * @author vivek kumar
 */

public class BigCommerceAPIAustriaQuoteTests extends BigCommerceAPIBaseTest
{
    /**
     * JIRA ticket CBC-579
     *
     * sending a single Item request , shipping from Austria to Austria , same billing and shipping
     * addresses.
     *
     * @author vivek
     */
    @Test(groups = { "bigCommerce_api","bigCommerce_smoke" })
    public void singleQuoteEUToEUTest( )
    {
        //********************test data*****************//
        final String rate = "0.2";

        BigCommerceRequestItem car = apiUtil.buildItem(standardHighPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.AUSTRIA_MITTLEBERG_ADDRESS, BigCommerceTestDataAddress.AUSTRIA_MITTLEBERG_ADDRESS, true, car);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);

        Response response = apiUtil.sendEstimateRequest(quoteRequest);
        JsonPath extractor = response.jsonPath();
        Float tax = extractor.get("documents[0].items[0].price.sales_tax_summary[0].rate");
        String taxRate = String.valueOf(tax);
        Assert.assertEquals(rate, taxRate);
    }

    /**
     * this test case sends a request for a single item quote USA to Austria shipping addresses ,
     * same billing and shipping
     * addresses.
     */
    @Test(groups = { "bigCommerce_api","bigCommerce_smoke" })
    public void SingleItemQuoteUSToEUTest( )
    {
        //********************test data*****************//
        final String expectedTaxRate = "0.0";

        final double paintPriceAmount = 785.3;
        BigCommerceRequestItem paint = apiUtil.buildItem(paintPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.AUSTRIA_MITTLEBERG_ADDRESS,
                BigCommerceTestDataAddress.US_CA_ADDRESS, false, paint);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);

        Response response = apiUtil.sendEstimateRequest(quoteRequest);
        JsonPath extractor = response.jsonPath();
        Float tax = extractor.get("documents[0].items[0].price.sales_tax_summary[0].rate");
        String taxRate = String.valueOf(tax);
        Assert.assertEquals(expectedTaxRate, taxRate);
    }
    /**
     * this test case sends a request for a single item quote Austria to USA shipping addresses ,
     * same billing and shipping
     * addresses.
     */
    @Test(groups = { "bigCommerce_api","bigCommerce_smoke" })
    public void SingleItemQuoteEUToUSTest( )
    {
        //********************test data*****************//
        final double expectedTaxRate = 0.0;
        final double expectedTaxAmount = 0.0;
        final double expectedLocalTaxRate = 0.06;
        final double expectedLocalTaxAmount = 47.11;

        final double paintPriceAmount = 785.3;
        BigCommerceRequestItem paint = apiUtil.buildItem(paintPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.US_CA_ADDRESS,
                BigCommerceTestDataAddress.AUSTRIA_MITTLEBERG_ADDRESS, false, paint);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);

        Response response = apiUtil.sendEstimateRequest(quoteRequest);
        apiUtil.verifyTwoLevelTaxesQuote(response, expectedTaxRate, expectedTaxAmount, expectedLocalTaxRate,
                expectedLocalTaxAmount);
    }
}
