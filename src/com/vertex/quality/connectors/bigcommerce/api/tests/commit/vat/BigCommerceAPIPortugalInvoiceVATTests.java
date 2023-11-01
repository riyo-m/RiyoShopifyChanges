package com.vertex.quality.connectors.bigcommerce.api.tests.commit.vat;

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
 * CBC-428 - Create Sale Order for VAT (Austrian Sub-Division) and Invoice
 * this test case build quote requests with corresponding Ship from and Ship To address.
 * then sends a commit request and return response with VAT tax results.
 * then we assert VAT tax results.
 *
 * @author vivek-kumar
 */
public class BigCommerceAPIPortugalInvoiceVATTests extends BigCommerceAPIBaseTest
{
    /**
     * sends commit request and validate VAT rate as per Ship To Address.
     */
    @Test(groups = { "bigCommerce_vat","bigCommerce_regression" })
    public void commitAndPortugalEUToEUVATTest( )
    {
        final String rate = "0.22";
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.PORTUGESE_ADDRESS, BigCommerceTestDataAddress.PORTUGESE_ADDRESS,
                false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        JsonPath extractor = commitResponse.jsonPath();
        Float tax = extractor.get("documents[0].items[0].price.sales_tax_summary[0].rate");
        System.out.println(tax);
        String taxRate = String.valueOf(tax);
        Assert.assertEquals(rate, taxRate);
    }
    /**
     * sends commit request and validate VAT rate as per Ship To Address.
     */
    @Test(groups = { "bigCommerce_vat","bigCommerce_regression" })
    public void commitAndPortugalUSToEUVATTest( )
    {
        final String rate = "0.0";
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.PORTUGESE_ADDRESS, BigCommerceTestDataAddress.US_CA_ADDRESS,
                false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        JsonPath extractor = commitResponse.jsonPath();
        Float tax = extractor.get("documents[0].items[0].price.sales_tax_summary[0].rate");
        System.out.println(tax);
        String taxRate = String.valueOf(tax);
        Assert.assertEquals(rate, taxRate);
    }
    /**
     * sends commit request and validate VAT rate as per Ship To Address.
     */
    @Test(groups = { "bigCommerce_vat","bigCommerce_regression" })
    public void commitAndPortugalEUToUSVATTest( )
    {
        final double expectedTaxRate = 0.0;
        final double expectedTaxAmount = 0.0;
        final double expectedLocalTaxRate = 0.06;
        final double expectedLocalTaxAmount = 6.6;
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.US_CA_ADDRESS, BigCommerceTestDataAddress.PORTUGESE_ADDRESS,
                false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        apiUtil.verifyTwoLevelTaxesQuote(commitResponse, expectedTaxRate, expectedTaxAmount, expectedLocalTaxRate,
                expectedLocalTaxAmount);
    }
}