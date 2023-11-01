package com.vertex.quality.connectors.bigcommerce.api.tests.adjust;

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
public class BigCommerceAdjustEndpointEndToEndTests extends BigCommerceAPIBaseTest {
    /**
     * CBC-217 CBC - Test Case - End to End Test Create Sales Order and add/delete lines and change quantity and location.
     */
    @Test(groups = {"bigCommerce_regression"})
    public void commitAndEndToEndTest() {
        JsonPath extractor;
        Float tax;
        String taxRate;
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);
        BigCommerceRequestItem changedAddressShirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity,
                item2Id);
        BigCommerceRequestItem shirtQuantityTen = apiUtil.buildItem(standardHighPriceAmount, false, quantityTen, item3Id);
        BigCommerceRequestItem shirt3 = apiUtil.buildItem(standardHighPriceAmount, false, defaultQuantity, item4Id);
        BigCommerceRequestItem shirtQuantityFive = apiUtil.buildItem(standardHighPriceAmount, false, quantityFive, item5Id);
        BigCommerceRequestItem shirtQuantityThree = apiUtil.buildItem(standardHighPriceAmount, false, quantityThree, item5Id);
        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.US_CA_ADDRESS_2, BigCommerceTestDataAddress.US_PA_ADDRESS_1,
                false, shirt);
        BigCommerceRequestDocument doc2 = apiUtil.buildDocument(document2Id, BigCommerceTestDataAddress.NEW_ORLEANS, BigCommerceTestDataAddress.US_PA_ADDRESS_1, false,
                changedAddressShirt);
        BigCommerceRequestDocument doc3 = apiUtil.buildDocument(document3Id, BigCommerceTestDataAddress.NEW_ORLEANS, BigCommerceTestDataAddress.US_PA_ADDRESS_1, false,
                shirtQuantityTen);
        BigCommerceRequestDocument doc4 = apiUtil.buildDocument(document4Id, BigCommerceTestDataAddress.CO_ADDRESS, BigCommerceTestDataAddress.US_PA_ADDRESS_1, false,
                shirt3);
        BigCommerceRequestDocument doc5 = apiUtil.buildDocument(document5Id, BigCommerceTestDataAddress.NEW_ORLEANS, BigCommerceTestDataAddress.US_PA_ADDRESS_1, false,
                shirtQuantityFive);
        BigCommerceRequestDocument doc6 = apiUtil.buildDocument(document2Id, BigCommerceTestDataAddress.NEW_ORLEANS, BigCommerceTestDataAddress.US_PA_ADDRESS_1, false,
                shirtQuantityThree);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        String invoiceId = apiUtil.commitNewInvoice(quoteRequest);
        extractor = commitResponse.jsonPath();
        tax = extractor.get(BigCommerceTestData.TAX_PATH.data);
        taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTestData.LA_TAX_RATE.data, taxRate);
        BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequest(invoiceId, BigCommerceCurrency.USD,
                customer1Id, doc2, doc3, doc4);
        Response adjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId);
        extractor = adjustResponse.jsonPath();
        tax = extractor.get(BigCommerceTestData.TAX_PATH_1.data);
        taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTestData.ORLEANS_TAX.data, taxRate);
        tax = extractor.get(BigCommerceTestData.TAX_PATH_2.data);
        taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTestData.ORLEANS_TAX.data, taxRate);
        tax = extractor.get(BigCommerceTestData.TAX_PATH_3.data);
        taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTestData.CO_TAX.data, taxRate);
        BigCommerceQuoteRequest adjustRequest1 = apiUtil.buildQuoteRequest(invoiceId, BigCommerceCurrency.USD,
                customer1Id, doc5, doc4, doc6);
        Response adjustResponse1 = apiUtil.sendAdjustRequest(adjustRequest1, invoiceId);
        extractor = adjustResponse1.jsonPath();
        tax = extractor.get(BigCommerceTestData.TAX_PATH_1.data);
        String taxRate4 = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTestData.ORLEANS_TAX.data, taxRate4);
        tax = extractor.get(BigCommerceTestData.TAX_PATH_2.data);
        taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTestData.CO_TAX.data, taxRate);

    }
}
