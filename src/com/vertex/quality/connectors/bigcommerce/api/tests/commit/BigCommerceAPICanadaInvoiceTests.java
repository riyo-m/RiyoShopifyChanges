package com.vertex.quality.connectors.bigcommerce.api.tests.commit;

import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceCurrency;
import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceTaxRate;
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
 * CBC-759 - Cover Canada test scenarios
 * this test case build quote requests with corresponding Ship from and Ship To address.
 * then sends a commit request and return response with tax results.
 * then we assert tax results.
 *
 * @author vivek-kumar
 */
public class BigCommerceAPICanadaInvoiceTests extends BigCommerceAPIBaseTest {
    private Float tax;
    private String taxRate;
    JsonPath extractor;

    /**
     * CBC-226
     * CBC - Test Case -Create Sales Order with Invoice CANNB to CANNB same Province (HST).
     */
    @Test(groups = {"bigCommerce_regression"})
    public void canadaCANNBToCANNBInvoiceTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.CAN_NEW_BRUNSWICK_ADDRESS_3, BigCommerceTestDataAddress.CAN_NEW_BRUNSWICK_ADDRESS_2,
                false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);

        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        extractor = commitResponse.jsonPath();
        tax = extractor.get(BigCommerceTestData.TAX_JSON_PATH.data);
        taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTaxRate.CAN_NEW_BRUNSWICK.value, taxRate);

        tax = extractor.get(BigCommerceTestData.TAX_JSON_PATH_1.data);
        taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTaxRate.NEW_BRUNSWICK.value, taxRate);
    }

    /**
     * CBC-230
     * CBC - Test Case -Create Sales Order with Invoice CANBC to CANQC different Province (GST/QST).
     */
    @Test(groups = {"bigCommerce_regression"})
    public void canadaCANBCToCANQCInvoiceTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.CAN_QUEBEC_ADDRESS, BigCommerceTestDataAddress.CAN_BRITISH_COLUMBIA_ADDRESS_2,
                false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        extractor = commitResponse.jsonPath();
        tax = extractor.get(BigCommerceTestData.TAX_JSON_PATH.data);
        taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTaxRate.CAN_BC_QC.value, taxRate);

        tax = extractor.get(BigCommerceTestData.TAX_JSON_PATH_1.data);
        taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTaxRate.QUEBEC.value, taxRate);
    }

    /**
     * CBC-229.
     * CBC - Test Case -Create Sales Order with Invoice CANBC to CANON different Province (GST/HST).
     */
    @Test(groups = {"bigCommerce_regression"})
    public void canadaCANBCToCANONInvoiceTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.CAN_ONTARIO_ADDRESS_2, BigCommerceTestDataAddress.CAN_BRITISH_COLUMBIA_ADDRESS_2,
                false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        extractor = commitResponse.jsonPath();
        tax = extractor.get(BigCommerceTestData.TAX_JSON_PATH.data);
        taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTaxRate.CAN_BC_ON.value, taxRate);


        tax = extractor.get(BigCommerceTestData.TAX_JSON_PATH_1.data);
        taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTaxRate.ONTARIO.value, taxRate);
    }

    /**
     * CBC-229
     * CBC - Test Case -Create Sales Order with Invoice CANBC to CANON different Province (GST/HST).
     */
    @Test(groups = {"bigCommerce_regression"})
    public void canadaCANQCToCANBCInvoiceTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.CAN_BRITISH_COLUMBIA_ADDRESS_2, BigCommerceTestDataAddress.CAN_QUEBEC_ADDRESS,
                false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        extractor = commitResponse.jsonPath();
        tax = extractor.get(BigCommerceTestData.TAX_JSON_PATH_1.data);
        taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTaxRate.CAN_QC_BC.value, taxRate);

        tax = extractor.get(BigCommerceTestData.TAX_JSON_PATH_2.data);
        taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTaxRate.QC_BC.value, taxRate);
    }

    /**
     * CBC-225
     * CBC - Test Case -Create Sales Order with Invoice CAN to US.
     */
    @Test(groups = {"bigCommerce_regression"})
    public void commitAndCANToUSInvoiceTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.US_PA_ADDRESS_1, BigCommerceTestDataAddress.CAN_QUEBEC_ADDRESS,
                false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        extractor = commitResponse.jsonPath();
        tax = extractor.get(BigCommerceTestData.TAX_JSON_PATH_1.data);
        taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTaxRate.PA_RATE.value, taxRate);
    }

    /**
     * CBC-225
     * CBC - Test Case -Create Sales Order with Invoice CAN to US.
     */
    @Test(groups = {"bigCommerce_regression"})
    public void commitAndUSToCANInvoiceTest() {
        BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id,
                BigCommerceTestDataAddress.CAN_BRITISH_COLUMBIA_ADDRESS_2, BigCommerceTestDataAddress.US_PA_ADDRESS_1,
                false, shirt);

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);
        Response commitResponse = apiUtil.sendCommitRequest(quoteRequest);
        extractor = commitResponse.jsonPath();
        tax = extractor.get(BigCommerceTestData.TAX_JSON_PATH.data);
        taxRate = String.valueOf(tax);
        Assert.assertEquals(BigCommerceTaxRate.BC.value, taxRate);
    }
}