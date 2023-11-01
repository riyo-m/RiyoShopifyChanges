package com.vertex.quality.connectors.bigcommerce.api.tests.commit.vat;

import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceCurrency;
import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceTestData;
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
 * CBC-579 - Create Sale Order for VAT (Austrian Sub-Division) and Invoice
 * this test case build quote requests with corresponding Ship from and Ship To address.
 * then sends a commit request and return response with VAT tax results.
 * then we assert VAT tax results.
 *
 * @author vivek-kumar
 */
public class BigCommerceAPICzechInvoiceVATTests extends BigCommerceAPIBaseTest {
    /**
     * sends commit request and validate VAT rate as per Ship To Address.
     */
    @Test(groups = {"bigCommerce_vat", "bigCommerce_regression"})
    public void commitAndCzechEUToEUVATTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.CZECH_ADDRESS, BigCommerceTestDataAddress.CZECH_ADDRESS,
                false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        JsonPath extractor = commitResponse.jsonPath();
        Float tax = extractor.get(BigCommerceTestData.TAX_PATH.data);
        String taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTestData.CZECH_TAX_RATE.data, taxRate);
    }

    /**
     * sends commit request and validate VAT rate as per Ship To Address.
     */
    @Test(groups = {"bigCommerce_vat", "bigCommerce_regression"})
    public void commitAndCzechUSToEUVATTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.CZECH_ADDRESS, BigCommerceTestDataAddress.US_CA_ADDRESS,
                false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        JsonPath extractor = commitResponse.jsonPath();
        Float tax = extractor.get(BigCommerceTestData.TAX_PATH.data);
        String taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTestData.US_CZECH_TAX_RATE.data, taxRate);
    }

    /**
     * sends commit request and validate VAT rate as per Ship To Address.
     */
    @Test(groups = {"bigCommerce_vat", "bigCommerce_regression"})
    public void commitAndCzechEUToUSVATTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.US_CA_ADDRESS, BigCommerceTestDataAddress.CZECH_ADDRESS,
                false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        apiUtil.verifyTwoLevelTaxesQuote(commitResponse, BigCommerceTestData.EXPECTED_TAX_RATE.rate, BigCommerceTestData.EXPECTED_TAX_AMOUNT.rate, BigCommerceTestData.EXPECTED_LOCAL_TAX_RATE.rate,
                BigCommerceTestData.EXPECTED_LOCAL_TAX_AMOUNT.rate);
    }
}