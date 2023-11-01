package com.vertex.quality.connectors.bigcommerce.api.tests.estimate;

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
 * the test cases for quote requests which contain multiple shipment and items
 *
 * @author osabha
 */
public class BigCommerceAPIMultipleShippingAndItemsTests extends BigCommerceAPIBaseTest {
    @Test(groups = {"bigCommerce_api", "bigCommerce_regression"})
    public void multipleShipmentTest() {
        final double expectedTaxRate = 0;
        final double expectedLocalTaxRate = 0;
        final double expectedTaxAmount = 0;
        final double expectedLocalTaxAmount = 0;
        final double expectedTaxRateDoc2 = 0.06;
        final double expectedTaxAmountDoc2 = 12000;

        final String doc2Item1TaxRatePath = "documents[1].items[0].price.sales_tax_summary[0].rate";
        final String doc2Item1TaxAmountPath = "documents[1].items[0].price.sales_tax_summary[0].amount";

        BigCommerceTestDataAddress ontarioAddress1 = BigCommerceTestDataAddress.CAN_ONTARIO_ADDRESS_1;
        BigCommerceTestDataAddress paAddress1 = BigCommerceTestDataAddress.US_PA_ADDRESS_1;
        BigCommerceTestDataAddress caliAddress1 = BigCommerceTestDataAddress.US_CA_ADDRESS_1;

        BigCommerceRequestItem car1 = apiUtil.buildItem(standardHighPriceAmount, false, defaultQuantity, item1Id);
        BigCommerceRequestItem car2 = apiUtil.buildItem(standardHighPriceAmount, false, defaultQuantity, item2Id);

        BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, ontarioAddress1, caliAddress1, true, car1);

        BigCommerceRequestDocument doc2 = apiUtil
                .startBuildDocument(document2Id, true, car2)
                .billing_address(ontarioAddress1.buildPojo())
                .destination_address(paAddress1.buildPojo())
                .origin_address(caliAddress1.buildPojo())
                .build();

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1, doc2);

        Response response = apiUtil.sendEstimateRequest(quoteRequest);
        apiUtil.verifyTwoLevelTaxesQuote(response, expectedTaxRate, expectedTaxAmount, expectedLocalTaxRate,
                expectedLocalTaxAmount);

        apiUtil.assertResponseDoubleEquals(response, doc2Item1TaxRatePath, expectedTaxRateDoc2);
        apiUtil.assertResponseDoubleEquals(response, doc2Item1TaxAmountPath, expectedTaxAmountDoc2);
    }
    /**
     * the test cases for estimate requests which contain multiple items
     *
     */
    @Test(groups = {"bigCommerce_api","bigCommerce_regression"})
    public void multipleItemsTest() {
        final int shirtPriceAmount = 300;

        BigCommerceRequestItem car = apiUtil.buildItem(standardHighPriceAmount, false, defaultQuantity, item1Id);
        BigCommerceRequestItem shirt = apiUtil.buildItem(shirtPriceAmount, false, defaultQuantity, item2Id);

        BigCommerceRequestDocument doc1 = apiUtil
                .startBuildDocument(document1Id, true, car, shirt)
                .billing_address(BigCommerceTestDataAddress.CAN_ONTARIO_ADDRESS_1.buildPojo())
                .destination_address(BigCommerceTestDataAddress.US_PA_ADDRESS_1.buildPojo())
                .origin_address(BigCommerceTestDataAddress.US_CA_ADDRESS_1.buildPojo())
                .build();

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);

        Response response = apiUtil.sendEstimateRequest(quoteRequest);
        apiUtil.assertStatus(response, ResponseCodes.SUCCESS);
    }

    /**
     * multiple items , some exempt and some standard.
     */
    @Test(groups = {"bigCommerce_api","bigCommerce_regression"})
    public void multipleItemsDifferentClassesTest() {
        final int shirtPriceAmount = 300;

        BigCommerceRequestItem car = apiUtil.buildItem(standardHighPriceAmount, false, defaultQuantity, item1Id);
        BigCommerceRequestItem shirt = apiUtil.buildItem(shirtPriceAmount, true, defaultQuantity, item2Id);

        BigCommerceRequestDocument doc1 = apiUtil
                .startBuildDocument(document1Id, true, car, shirt)
                .billing_address(BigCommerceTestDataAddress.CAN_ONTARIO_ADDRESS_1.buildPojo())
                .destination_address(BigCommerceTestDataAddress.US_PA_ADDRESS_1.buildPojo())
                .origin_address(BigCommerceTestDataAddress.US_CA_ADDRESS_1.buildPojo())
                .build();

        BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
                doc1);

        Response response = apiUtil.sendEstimateRequest(quoteRequest);
        apiUtil.assertStatus(response, ResponseCodes.SUCCESS);
    }
}
