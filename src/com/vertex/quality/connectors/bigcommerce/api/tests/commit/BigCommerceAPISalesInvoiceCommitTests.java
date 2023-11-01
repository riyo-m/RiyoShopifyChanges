package com.vertex.quality.connectors.bigcommerce.api.tests.commit;

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
 * CBC-213 - CBC - Test Automation - Sales - Create Invoice Request.
 * this test case build quote requests with corresponding Ship from and Ship To address.
 * then sends a commit request and return response with tax results.
 * then we assert tax results.
 *
 * @author vivek-kumar
 */
public class BigCommerceAPISalesInvoiceCommitTests extends BigCommerceAPIBaseTest {
    /**
     * CBC-219 CBC - Test Case - Create Invoice with quantity 10
     * sends commit request and validate rate as per Ship To Address with quantity item 10.
     */
    @Test(groups = {"bigCommerce_regression", "bigCommerce_smoke"})
    public void commitAndQuantityTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, quantityTen, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.WA_SAMMAMISH_ADDRESS, BigCommerceTestDataAddress.US_PA_ADDRESS_1,
                false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        JsonPath extractor = commitResponse.jsonPath();
        Float tax = extractor.get(BigCommerceTestData.TAX_PATH.data);
        String taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTestData.WA_TAX_RATE.data, taxRate);
    }

    /**
     * CBC-218 CBC - Test Case - Create Sales Invoice only
     * sends commit request and create invoice only.
     */
    @Test(groups = {"bigCommerce_regression"})
    public void commitAndCreateInvoiceTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, quantityTen, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.US_PA_ADDRESS_4, BigCommerceTestDataAddress.US_PA_ADDRESS_1,
                false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        JsonPath extractor = commitResponse.jsonPath();
        Float tax = extractor.get(BigCommerceTestData.TAX_PATH.data);
        String taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTestData.LA_TAX_RATE.data, taxRate);
    }

    /**
     * CBC-221 CBC - Test Case -Create Sales Order different ship and bill
     * sends commit request and create invoice only.
     */
    @Test(groups = {"bigCommerce_regression"})
    public void commitAndDiffShipBillAddressTest() {
        BigCommerceTestDataAddress paAddress1 = BigCommerceTestDataAddress.US_PA_ADDRESS_1;
        BigCommerceTestDataAddress caliAddress1 = BigCommerceTestDataAddress.US_CA_ADDRESS_2;
        BigCommerceTestDataAddress deAddress = BigCommerceTestDataAddress.DE_ADDRESS;
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, quantityTen, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil
                .startBuildDocument(document2Id, true, shirt)
                .billing_address(deAddress.buildPojo())
                .destination_address(caliAddress1.buildPojo())
                .origin_address(paAddress1.buildPojo())
                .build();

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        JsonPath extractor = commitResponse.jsonPath();
        Float tax = extractor.get(BigCommerceTestData.TAX_PATH.data);
        String taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTestData.CALIFORNIA_TAX_RATE.data, taxRate);
    }

    /**
     * CBC-214 CBC - CBC - Test Case - Create Sale Order where the Customer is registered in O Series and the Financial System
     * sends commit request and create invoice only.
     */
    @Test(groups = {"bigCommerce_regression"})
    public void commitAndRegisteredFinancialSystemTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, quantityTen, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.US_PA_ADDRESS_4, BigCommerceTestDataAddress.US_PA_ADDRESS_1,
                false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        JsonPath extractor = commitResponse.jsonPath();
        Float tax = extractor.get(BigCommerceTestData.TAX_PATH.data);
        String taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTestData.LA_TAX_RATE.data, taxRate);
    }

    /**
     * CBC-222 CBC - Test Case -Create Sales Order with taxpayer not registered in O Series or the Financial System.
     * sends commit request and create invoice only.
     */
    @Test(groups = {"bigCommerce_regression"})
    public void commitAndNotRegisteredFinancialSystemTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, quantityTen, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.US_PA_ADDRESS_4, BigCommerceTestDataAddress.US_PA_ADDRESS_1,
                false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer2Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        JsonPath extractor = commitResponse.jsonPath();
        Float tax = extractor.get(BigCommerceTestData.TAX_PATH.data);
        String taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTestData.LA_TAX_RATE.data, taxRate);
    }

    /**
     * CBC-223 CBC - Test Case -Create Sales Order with NJ no tax on clothing, no shipping tax.
     * sends commit request and create invoice only.
     */
    @Test(groups = {"bigCommerce_regression"})
    public void commitAndNJNoTaxTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, true, quantityTen, item3Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.NEWJERSEY_ADDRESS, BigCommerceTestDataAddress.US_CA_ADDRESS_2,
                false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer2Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        JsonPath extractor = commitResponse.jsonPath();
        Float tax = extractor.get(BigCommerceTestData.TAX_PATH.data);
        String taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTestData.NJ_NO_TAX.data, taxRate);
    }
}
