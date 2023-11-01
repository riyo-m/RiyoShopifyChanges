package com.vertex.quality.connectors.bigcommerce.common.utils;

import com.vertex.quality.common.enums.*;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.api.VertexAPITestUtilities;
import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceCurrency;
import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceEndpoint;
import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceRequestItemType;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.*;
import com.vertex.quality.connectors.bigcommerce.api.pojos.*;
import com.vertex.quality.connectors.bigcommerce.api.pojos.BigCommerceValidRequestDocument.BigCommerceValidRequestDocumentBuilder;
import com.vertex.quality.connectors.bigcommerce.api.utils.BigCommerceConverter;
import com.vertex.quality.connectors.bigcommerce.common.enums.BigCommerceTestDataAddress;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.Matchers.hasItems;
import static org.testng.Assert.assertEquals;

/**
 * a utility class for api testing of big commerce
 *
 * @author ssalisbury
 */
public class BigCommerceAPITestUtilities extends VertexAPITestUtilities {
    public static final DBConnectorNames CONNECTOR_NAME = DBConnectorNames.BIG_COMMERCE;
    public static final DBEnvironmentNames QA_ENVIRONMENT_NAME = DBEnvironmentNames.QA;
    public static final DBEnvironmentDescriptors API_ENVIRONMENT_DESCRIPTOR = DBEnvironmentDescriptors.BIG_COMMERCE_API;

    public static final ContentType DEFAULT_CONTENT_TYPE = ContentType.JSON;
    public static final String DEFAULT_STORE_HASH_NUMBER = "iuvevjf46c";
    public static final String STORE_HASH_HEADER_NAME = "X-BC-Store-Hash";

    public static final String QUOTE_1_ID = "1";
    public static final String QUOTE_2_ID = "2";
    public static final String QUOTE_3_ID = "3";
    public static final String QUOTE_4_ID = "4";
    public static final String QUOTE_5_ID = "5";
    public static final String QUOTE_6_ID = "TestAdjust";
    public static final String DOCUMENT_1_ID = "10";
    public static final String DOCUMENT_2_ID = "11";
    public static final String DOCUMENT_3_ID = "12";
    public static final String DOCUMENT_4_ID = "13";
    public static final String DOCUMENT_5_ID = "14";

    public static final String CUSTOMER_1_ID = "30";
    public static final String CUSTOMER_2_ID = "31";
    public static final String CUSTOMER_3_ID = "32";
    public static final String CUSTOMER_4_ID = "33";
    public static final String CUSTOMER_5_ID = "34";

    public static final String SHIPPING_1_ID = "50";
    public static final String SHIPPING_2_ID = "51";
    public static final String SHIPPING_3_ID = "52";
    public static final String SHIPPING_4_ID = "53";
    public static final String SHIPPING_5_ID = "54";

    public static final String HANDLING_1_ID = "70";
    public static final String HANDLING_2_ID = "71";
    public static final String HANDLING_3_ID = "72";
    public static final String HANDLING_4_ID = "73";
    public static final String HANDLING_5_ID = "74";

    public static final String ITEM_1_ID = "90";
    public static final String ITEM_2_ID = "91";
    public static final String ITEM_3_ID = "92";
    public static final String ITEM_4_ID = "93";
    public static final String ITEM_5_ID = "94";
    public static final String ITEM_6_ID = "95";
    public static final String ITEM_7_ID = "96";
    public static final String ITEM_8_ID = "97";
    public static final String ITEM_9_ID = "98";
    public static final String ITEM_10_ID = "99";

    public static final String WRAPPING_1_ID = "130";
    public static final String WRAPPING_2_ID = "131";
    public static final String WRAPPING_3_ID = "132";
    public static final String WRAPPING_4_ID = "133";
    public static final String WRAPPING_5_ID = "134";

    public static final double STANDARD_LOW_SHIPPING_AMOUNT = 15;
    public static final double STANDARD_LOW_HANDLING_AMOUNT = 5;
    public static final double STANDARD_HIGH_SHIPPING_AMOUNT = 200;
    public static final double STANDARD_HIGH_HANDLING_AMOUNT = 150;
    public static final double STANDARD_LOW_PRICE_AMOUNT = 110;
    public static final double STANDARD_MEDIUM_PRICE_AMOUNT = 200;
    public static final double STANDARD_HIGH_PRICE_AMOUNT = 200000;

    public static final int DEFAULT_QUANTITY = 1;
    public static final int DEFAULT_ORDER_QUANTITY = 3;
    public static final int REFUND_QUANTITY = 1;
    public static final int QUANTITY_TEN = 10;
    public static final int QUANTITY_FIVE = 5;
    public static final int QUANTITY_THREE = 3;

    public static final String ID_PATH_PARAM = "id";
    public static final String ID_QUERY_PARAM = "id";
    public static final String TRUSTED_ID_QUERY_PARAM = "trustedId";
    public static final String ID_QUERY_FROMDATE = "from";
    public static final String ID_QUERY_DOCUMENT_NUMBER = "documentNumber";

    public static final String ITEM_1_FED_TAX_RATE_PATH = "documents[0].items[0].price.sales_tax_summary[0].rate";
    public static final String ITEM_1_LOCAL_TAX_RATE_PATH = "documents[0].items[0].price.sales_tax_summary[1].rate";
    public static final String ITEM_1_FED_TAX_AMOUNT_PATH = "documents[0].items[0].price.sales_tax_summary[0].amount";
    public static final String ITEM_1_LOCAL_TAX_AMOUNT_PATH = "documents[0].items[0].price.sales_tax_summary[1].amount";

    protected BigCommerceConverter jsonConverter;

    public BigCommerceAPITestUtilities() {
        this.jsonConverter = new BigCommerceConverter();
        loadAPIAccessInformation();
    }

    @Override
    public void loadAPIAccessInformation() {
        try {
            EnvironmentInformation environmentInfo = SQLConnection.getEnvironmentInformation(CONNECTOR_NAME,
                    QA_ENVIRONMENT_NAME, API_ENVIRONMENT_DESCRIPTOR);
            EnvironmentCredentials credentials = SQLConnection.getEnvironmentCredentials(environmentInfo);
            baseUrl = environmentInfo.getEnvironmentUrl();
            String username = credentials.getUsername();
            String password = credentials.getPassword();

            setBasicAuth(username, password);
        } catch (Exception e) {
            VertexLogger.log(
                    "failed to fetch the environment information/credentials for a test of the Big Commerce API",
                    VertexLogLevel.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Overloads {@link #buildItem(BigCommerceRequestItemPrice, boolean, int, String)}
     * to generate a standard price object for the item with the given monetary amount
     */
    public BigCommerceRequestItem buildItem(final double itemPriceAmount, final boolean isTaxExempt,
                                            final int quantity, final String itemId) {
        BigCommerceRequestItemPrice itemPrice = new BigCommerceValidRequestItemPrice(false, itemPriceAmount);
        BigCommerceRequestItem item = buildItem(itemPrice, isTaxExempt, quantity, itemId);
        return item;
    }

    /**
     * helper method to set all the different item fields, goes into the document request.
     *
     * @param itemPrice   price of the item
     * @param isTaxExempt is the item tax exempt or not
     * @param quantity    quantity of the item purchased
     * @param itemId      item id
     * @return a complete item request as a pojo containing all the info about the item.
     */
    public BigCommerceRequestItem buildItem(final BigCommerceRequestItemPrice itemPrice, final boolean isTaxExempt,
                                            final int quantity, final String itemId) {
        BigCommerceRequestItem item = BigCommerceValidRequestItem
                .builder()
                .name("item")
                .price(itemPrice)
                .quantity(quantity)
                .id(itemId)
                .type(BigCommerceRequestItemType.ITEM.getName())
                .tax_exempt(isTaxExempt)
                .build();
        return item;
    }

    /**
     * Simplified version of {@link #buildShipping(double, int, String)} which generates a shipping item using
     * some default values, including a low value for the shipping fee
     */
    public BigCommerceRequestItem buildStandardLowShipping() {
        BigCommerceRequestItem lowShippingItem = buildShipping(STANDARD_LOW_SHIPPING_AMOUNT, 1, SHIPPING_1_ID);
        return lowShippingItem;
    }

    /**
     * Simplified version of {@link #buildShipping(double, int, String)} which generates a shipping item using
     * some default values, including a high value for the shipping fee
     */
    public BigCommerceRequestItem buildStandardHighShipping() {
        BigCommerceRequestItem highShippingItem = buildShipping(STANDARD_HIGH_SHIPPING_AMOUNT, 1, SHIPPING_3_ID);
        return highShippingItem;
    }

    /**
     * Simplified version of {@link #buildHandling(double, int, String)} which generates a handling item using
     * some default values, including a low value for the handling fee
     */
    public BigCommerceRequestItem buildStandardLowHandling() {
        BigCommerceRequestItem lowHandlingItem = buildHandling(STANDARD_LOW_HANDLING_AMOUNT, 1, HANDLING_1_ID);
        return lowHandlingItem;
    }

    /**
     * Simplified version of {@link #buildHandling(double, int, String)} which generates a handling item using
     * some default values, including a high value for the handling fee
     */
    public BigCommerceRequestItem buildStandardHighHandling() {
        BigCommerceRequestItem highHandlingItem = buildHandling(STANDARD_HIGH_HANDLING_AMOUNT, 1, HANDLING_2_ID);
        return highHandlingItem;
    }

    /**
     * Overloads {@link #buildShipping(BigCommerceRequestItemPrice, int, String)} to create a standard item price
     * object with the given price for the shipping item
     *
     * @author ssalisbury
     */
    public BigCommerceRequestItem buildShipping(final double priceAmount, final int quantity, final String id) {
        BigCommerceRequestItem shippingItem = null;

        BigCommerceRequestItemPrice shippingPrice = new BigCommerceValidRequestItemPrice(false, priceAmount);

        shippingItem = buildShipping(shippingPrice, quantity, id);

        return shippingItem;
    }

    /**
     * Helper method to setUp all the shipping fields in the quote request.
     *
     * @param shippingCost cost of the shipping
     * @param quantity     number of items the shipping price applies to
     * @param id           Id for the shipping
     * @return new instance of the BigCommerceItemRequest carrying all the shipping details as a pojo
     */
    public BigCommerceRequestItem buildShipping(final BigCommerceRequestItemPrice shippingCost, final int quantity,
                                                final String id) {
        BigCommerceRequestItem shipping = BigCommerceValidRequestItem
                .builder()
                .price(shippingCost)
                .quantity(quantity)
                .type(BigCommerceRequestItemType.SHIPPING.getName())
                .id(id)
                .build();
        return shipping;
    }

    /**
     * Overloads {@link #buildHandling(BigCommerceRequestItemPrice, int, String)} to create a standard item price
     * object with the given price for the handling item
     *
     * @author ssalisbury
     */
    public BigCommerceRequestItem buildHandling(final double priceAmount, final int quantity, final String id) {
        BigCommerceRequestItem handlingItem = null;

        BigCommerceRequestItemPrice handlingPrice = new BigCommerceValidRequestItemPrice(false, priceAmount);

        handlingItem = buildHandling(handlingPrice, quantity, id);

        return handlingItem;
    }

    /**
     * Helper method to setUp all the Handling fields in the quote request.
     *
     * @param handlingCost cost of the handling
     * @param quantity     number of items the handling price applies to
     * @param id           Id for the handling
     * @return new instance of the BigCommerceItemRequest carrying all the handling details as a pojo
     */
    public BigCommerceRequestItem buildHandling(final BigCommerceRequestItemPrice handlingCost, final int quantity,
                                                final String id) {
        BigCommerceRequestItem handling = BigCommerceValidRequestItem
                .builder()
                .price(handlingCost)
                .quantity(quantity)
                .type(BigCommerceRequestItemType.HANDLING.getName())
                .id(id)
                .build();

        return handling;
    }

    /**
     * puts together a request document using some assumptions and default values
     *
     * @param documentId     the id of the document
     * @param buyerAddress   the physical and administrative destination address for the shipment
     * @param sellerAddress  the physical origin address for the shipment
     * @param isShippingHigh whether to charge the higher default shipping/handling amounts or the lower default ones
     * @param items          the items in the shipment
     * @return a request document describing this shipment
     */
    public BigCommerceRequestDocument buildDocument(final String documentId,
                                                    final BigCommerceTestDataAddress buyerAddress, final BigCommerceTestDataAddress sellerAddress,
                                                    final boolean isShippingHigh, final BigCommerceRequestItem... items) {
        BigCommerceValidRequestDocumentBuilder documentBuilder = startBuildDocument(documentId, isShippingHigh, items);

        BigCommerceAddress buyerAddressPojo = null;
        BigCommerceAddress sellerAddressPojo = null;

        if (buyerAddress != null) {
            buyerAddressPojo = buyerAddress.buildPojo();
        }
        if (sellerAddress != null) {
            sellerAddressPojo = sellerAddress.buildPojo();
        }

        BigCommerceRequestDocument document = documentBuilder
                .billing_address(buyerAddressPojo)
                .destination_address(buyerAddressPojo)
                .origin_address(sellerAddressPojo)
                .build();

        return document;
    }

    /**
     * starts the process of building a request document by filling in the major non-address fields and
     * returns a builder so that the three address fields can be filled in with builder methods (which clearly
     * label each address, avoiding the confusion that's possible with a sequence of 3 objects of the same type in a
     * constructor's arguments list)
     *
     * @param documentId     the id of the document
     * @param isShippingHigh whether to charge the higher default shipping/handling amounts or the lower default ones
     * @param items          the items in the shipment
     * @return a builder object representing a partly-assembled document for a shipment
     */
    public BigCommerceValidRequestDocumentBuilder startBuildDocument(final String documentId,
                                                                     final boolean isShippingHigh, final BigCommerceRequestItem... items) {
        BigCommerceRequestItem shippingItem;
        BigCommerceRequestItem handlingItem;

        if (isShippingHigh) {
            shippingItem = buildStandardHighShipping();
            handlingItem = buildStandardHighHandling();
        } else {
            shippingItem = buildStandardLowShipping();
            handlingItem = buildStandardLowHandling();
        }

        BigCommerceValidRequestDocumentBuilder documentBuilder = BigCommerceValidRequestDocument
                .builder()
                .id(documentId)
                .shipping(shippingItem)
                .handling(handlingItem)
                .items(Arrays.asList(items));

        return documentBuilder;
    }

    /**
     * Overloads {@link #buildQuoteRequest(String, BigCommerceCurrency, BigCommerceQuoteRequestCustomer,
     * BigCommerceRequestDocument...)}
     * to generate a standard customer for the quote request with the given customerId value
     */
    public BigCommerceQuoteRequest buildQuoteRequest(final String quoteId, final BigCommerceCurrency currency,
                                                     final String customerId, final BigCommerceRequestDocument... docs) {
        BigCommerceQuoteRequestCustomer customer = BigCommerceValidQuoteRequestCustomer
                .builder()
                .customer_id(customerId)
                .build();

        BigCommerceQuoteRequest quoteRequest = buildQuoteRequest(quoteId, currency, customer, docs);

        return quoteRequest;
    }

    /**
     * Overloads {@link #buildQuoteRequest(String, BigCommerceCurrency, BigCommerceQuoteRequestCustomer,
     * BigCommerceRequestDocument...)}
     * to generate a standard customer for the quote request with the given customerId value and date
     */
    public BigCommerceQuoteRequest buildQuoteRequestWithDate(final String quoteId, final BigCommerceCurrency currency,
                                                             final String customerId, final String transactionDate, final BigCommerceRequestDocument... docs) {
        BigCommerceQuoteRequestCustomer customer = BigCommerceValidQuoteRequestCustomer
                .builder()
                .customer_id(customerId)
                .build();

        BigCommerceQuoteRequest quoteRequest = buildQuoteRequestWithDate(quoteId, currency, customer, transactionDate, docs);

        return quoteRequest;
    }

    /**
     * helper method to set the quote request different fields.
     *
     * @param customer the buying customer
     * @param currency currency in which the transaction is handled
     * @param quoteId  id of the quote
     * @param docs     document request containing all the item requests and shipment info.
     * @return a complete quote request pojo
     */
    public BigCommerceQuoteRequest buildQuoteRequest(final String quoteId, final BigCommerceCurrency currency,
                                                     final BigCommerceQuoteRequestCustomer customer, final BigCommerceRequestDocument... docs) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        String transactionDate = sdf.format(date);

        BigCommerceQuoteRequest quoteRequest = BigCommerceValidQuoteRequest
                .builder()
                .currency_code(currency.getName())
                .customer(customer)
                .id(quoteId)
                .transaction_date(transactionDate)
                .documents(Arrays.asList(docs))
                .build();

        return quoteRequest;
    }


    /**
     * helper method to set the quote request different fields.
     *
     * @param customer the buying customer
     * @param currency currency in which the transaction is handled
     * @param quoteId  id of the quote
     * @param docs     document request containing all the item requests and shipment info.
     * @return a complete quote request pojo
     */
    public BigCommerceQuoteRequest buildQuoteRequestWithDate(final String quoteId, final BigCommerceCurrency currency,
                                                             final BigCommerceQuoteRequestCustomer customer, final String transactionDate, final BigCommerceRequestDocument... docs) {

        BigCommerceQuoteRequest quoteRequest = BigCommerceValidQuoteRequest
                .builder()
                .currency_code(currency.getName())
                .customer(customer)
                .id(quoteId)
                .transaction_date(transactionDate)
                .documents(Arrays.asList(docs))
                .build();

        return quoteRequest;
    }

    /**
     * sends a commit request to the connector and retrieves the id of the invoice created in big commerce's system
     * by that commit request
     *
     * @param quoteRequest the commit request that should create an invoice
     * @return the id of the created invoice
     */
    public String commitNewInvoice(final BigCommerceQuoteRequest quoteRequest) {
        Response commitResponse = sendCommitRequest(quoteRequest);
        assertStatus(commitResponse, ResponseCodes.SUCCESS);
        String invoiceId = commitResponse
                .then()
                .extract()
                .jsonPath()
                .getString("id");

        VertexLogger.log(invoiceId);

        return invoiceId;
    }

    /**
     * Overloads {@link #sendEstimateRequest(BigCommerceQuoteRequest, String)}
     * to use the default store identification header
     */
    public Response sendEstimateRequest(final BigCommerceQuoteRequest jsonBody) {
        Response quoteResponse = sendEstimateRequest(jsonBody, DEFAULT_STORE_HASH_NUMBER);
        return quoteResponse;
    }

    /**
     * Alters {@link #sendQuoteRequest(BigCommerceEndpoint, BigCommerceQuoteRequest, String)}
     * to specifically target the estimate endpoint
     */
    public Response sendEstimateRequest(final BigCommerceQuoteRequest jsonBody, final String storeHashNumber) {
        Response quoteResponse = sendQuoteRequest(BigCommerceEndpoint.ESTIMATE_API_CONTROLLER, jsonBody,
                storeHashNumber);
        return quoteResponse;
    }

    /**
     * Overloads {@link #sendCommitRequest(BigCommerceQuoteRequest, String)}
     * to use the default store identification header
     */
    public Response sendCommitRequest(final BigCommerceQuoteRequest jsonBody) {
        Response quoteResponse = sendCommitRequest(jsonBody, DEFAULT_STORE_HASH_NUMBER);
        return quoteResponse;
    }

    /**
     * Alters {@link #sendQuoteRequest(BigCommerceEndpoint, BigCommerceQuoteRequest, String)}
     * to specifically target the commit endpoint
     */
    public Response sendCommitRequest(final BigCommerceQuoteRequest jsonBody, final String storeHashNumber) {
        Response quoteResponse = sendQuoteRequest(BigCommerceEndpoint.COMMIT_API_CONTROLLER, jsonBody, storeHashNumber);
        return quoteResponse;
    }

    /**
     * Creates the quote request, sends it, then gets back the response
     * this is for the commit or estimate endpoints
     *
     * @param jsonBody        the pojo which ontains the information that will form the body of the request
     * @param storeHashNumber store number to send in the request header.
     * @return the response to the created request
     */
    protected Response sendQuoteRequest(final BigCommerceEndpoint endpoint, final BigCommerceQuoteRequest jsonBody,
                                        final String storeHashNumber) {
        String storeHashNumberString = String.valueOf(storeHashNumber);
        String jsonBodyString = jsonConverter.convertToJsonString(jsonBody);

        final String requestUrl = String.format("%s%s", baseUrl, endpoint.getUrlSuffix());

        Response quoteResponse = generateRequestSpecification()
                .header(STORE_HASH_HEADER_NAME, storeHashNumberString)
                .body(jsonBodyString)
                .when()
                .post(requestUrl);

        return quoteResponse;
    }

    /**
     * Overloads {@link #sendVoidRequest(String, String)}
     * to use the default store identification header
     */
    public Response sendVoidRequest(final String invoiceId) {
        Response quoteResponse = sendVoidRequest(DEFAULT_STORE_HASH_NUMBER, invoiceId);
        return quoteResponse;
    }

    /**
     * Creates the void request, sends it, then gets back the response
     *
     * @param storeHashNumber store number to send in the request header.
     * @param invoiceId       the invoice id to look up in the tax journal.
     * @return the response to the created request
     */
    public Response sendVoidRequest(final String storeHashNumber, final String invoiceId) {
        final BigCommerceEndpoint voidEndpoint = BigCommerceEndpoint.VOID_API_CONTROLLER;

        String storeHashNumberString = String.valueOf(storeHashNumber);

        final String requestUrl = String.format("%s%s", baseUrl, voidEndpoint.getUrlSuffix());

        Response voidResponse = generateRequestSpecification()
                .queryParam(ID_QUERY_PARAM, invoiceId)
                .header(STORE_HASH_HEADER_NAME, storeHashNumberString)
                .when()
                .post(requestUrl);

        return voidResponse;
    }

    /**
     * Overloads {@link #sendAdjustRequest(BigCommerceQuoteRequest, String, String)}
     * to use the default store identification header
     */
    public Response sendAdjustRequest(final BigCommerceQuoteRequest jsonBody, final String invoiceId) {
        Response quoteResponse = sendAdjustRequest(jsonBody, DEFAULT_STORE_HASH_NUMBER, invoiceId);
        return quoteResponse;
    }

    /**
     * Creates the adjust request, sends it, then gets back the response
     *
     * @param jsonBody        the pojo which ontains the information that will form the body of the request
     * @param storeHashNumber store number to send in the request header.
     * @param invoiceId       the invoice id to look up in the tax journal.
     * @return the response to the created request
     */
    public Response sendAdjustRequest(final BigCommerceQuoteRequest jsonBody, final String storeHashNumber,
                                      final String invoiceId) {
        final BigCommerceEndpoint adjustEndpoint = BigCommerceEndpoint.ADJUST_API_CONTROLLER;

        String storeHashNumberString = String.valueOf(storeHashNumber);
        String jsonBodyString = jsonConverter.convertToJsonString(jsonBody);

        final String requestUrl = String.format("%s%s", baseUrl, adjustEndpoint.getUrlSuffix());

        Response adjustResponse = generateRequestSpecification()
                .queryParam(ID_QUERY_PARAM, invoiceId)
                .header(STORE_HASH_HEADER_NAME, storeHashNumberString)
                .body(jsonBodyString)
                .when()
                .post(requestUrl);

        return adjustResponse;
    }

    /**
     * sends a request to get the list of connector configurations with the given trusted id
     *
     * @param trustedId the identification which all of the desired connector configurations share
     * @return the list of connector configurations which have the given trusted id
     */
    public Response sendConfigsGetRequest(final String trustedId) {
        final BigCommerceEndpoint configEndpoint = BigCommerceEndpoint.CONFIG_API_CONTROLLER;

        final String requestUrl = String.format("%s%s", baseUrl, configEndpoint.getUrlSuffix());

        Response getConfigsResponse = generateRequestSpecification()
                .queryParam(TRUSTED_ID_QUERY_PARAM, trustedId)
                .when()
                .get(requestUrl);

        return getConfigsResponse;
    }

    /**
     * sends a request to get a particular configuration that the connector currently has
     *
     * @param configId which of the connector's configurations should be fetched
     * @return the details of a configuration that the connector currently has
     */
    public Response sendConfigGetRequest(final String configId) {
        final BigCommerceEndpoint configEndpoint = BigCommerceEndpoint.CONFIG_API_CONTROLLER;

        final String requestUrl = String.format("%s%s/{id}", baseUrl, configEndpoint.getUrlSuffix());

        Response getConfigsResponse = generateRequestSpecification()
                .pathParam(ID_PATH_PARAM, configId)
                .when()
                .get(requestUrl);

        return getConfigsResponse;
    }

    /**
     * sends a request to add a new configuration to the connector
     *
     * @param newConfig the new configuration which will be added
     * @return a response indicating whether the configuration was successfully created
     */
    public Response sendConfigPostRequest(final BigCommerceConfigResource newConfig) {
        final BigCommerceEndpoint configEndpoint = BigCommerceEndpoint.CONFIG_API_CONTROLLER;

        final String requestUrl = String.format("%s%s", baseUrl, configEndpoint.getUrlSuffix());
        final String jsonBodyString = jsonConverter.convertToJsonString(newConfig);

        Response getConfigsResponse = generateRequestSpecification()
                .body(jsonBodyString)
                .when()
                .post(requestUrl);

        return getConfigsResponse;
    }

    /**
     * sends a request to change a configuration which the connector has stored
     *
     * @param configId      which of the connector's configurations should be modified
     * @param changedConfig the modified version of that configuration
     * @return a response indicating whether the configuration was successfully modified
     */
    public Response sendConfigPutRequest(final String configId, final BigCommerceConfigResource changedConfig) {
        final BigCommerceEndpoint configEndpoint = BigCommerceEndpoint.CONFIG_API_CONTROLLER;

        final String requestUrl = String.format("%s%s", baseUrl, configEndpoint.getUrlSuffix());
        final String jsonBodyString = jsonConverter.convertToJsonString(changedConfig);

        Response getConfigsResponse = generateRequestSpecification()
                .queryParam(ID_QUERY_PARAM, configId)
                .body(jsonBodyString)
                .when()
                .put(requestUrl);

        return getConfigsResponse;
    }

    /**
     * sends a request to delete a configuration which the connector has stored
     *
     * @param configId which of the connector's configurations should be deleted
     * @return a response indicating whether the configuration was successfully deleted
     */
    public Response sendConfigDeleteRequest(final String configId) {
        final BigCommerceEndpoint configEndpoint = BigCommerceEndpoint.CONFIG_API_CONTROLLER;

        final String requestUrl = String.format("%s%s", baseUrl, configEndpoint.getUrlSuffix());

        Response getConfigsResponse = generateRequestSpecification()
                .queryParam(ID_QUERY_PARAM, configId)
                .when()
                .put(requestUrl);

        return getConfigsResponse;
    }

    /**
     * asserts that the double value at the given path in the response is equal to the expected double value
     *
     * @param response      the JSON response containing the desired double value
     * @param jsonPath      the path from that response's root to the desired double value
     * @param expectedValue the value which that desired double value in the response should have
     */
    public void assertResponseDoubleEquals(final Response response, final String jsonPath, final double expectedValue) {
        double actualDoubleValue = response
                .then()
                .extract()
                .jsonPath()
                .getDouble(jsonPath);
        assertEquals(expectedValue, actualDoubleValue);
    }

    /**
     * this asserts that the given REST response carries the expected status code
     *
     * @param response       the REST response being examined
     * @param expectedStatus the status which that REST response should have
     */
    public void assertStatus(final Response response, final ResponseCodes expectedStatus) {
        response
                .then()
                .assertThat()
                .statusCode(expectedStatus.getResponseCode());
    }

    /**
     * sends an estimate request and checks that the returned quote has the expected tax rates and amounts
     * at a higher jurisdiction and also at a lower jurisdiction
     *
     * @param quoteResponse          tax calculations for a potential order
     * @param expectedTaxRate        the percentile rate at which the order should be taxed by the higher jurisdiction
     * @param expectedTaxAmount      the amount of tax which the higher jurisdiction should require for the order
     * @param expectedLocalTaxRate   the percentile rate at which the order should be taxed by the local jurisdiction
     * @param expectedLocalTaxAmount the amount of tax which the local jurisdiction should require for the order
     */
    public Response verifyTwoLevelTaxesQuote(final Response quoteResponse, final double expectedTaxRate,
                                             final double expectedTaxAmount, final double expectedLocalTaxRate, final double expectedLocalTaxAmount) {
        assertStatus(quoteResponse, ResponseCodes.SUCCESS);

        assertResponseDoubleEquals(quoteResponse, ITEM_1_FED_TAX_RATE_PATH, expectedTaxRate);
        assertResponseDoubleEquals(quoteResponse, ITEM_1_FED_TAX_AMOUNT_PATH, expectedTaxAmount);
        assertResponseDoubleEquals(quoteResponse, ITEM_1_LOCAL_TAX_RATE_PATH, expectedLocalTaxRate);
        assertResponseDoubleEquals(quoteResponse, ITEM_1_LOCAL_TAX_AMOUNT_PATH, expectedLocalTaxAmount);

        return quoteResponse;
    }

    @Override
    protected RequestSpecification generateRequestSpecification() {
        RequestSpecification initialRequestSpecification = super
                .generateRequestSpecification()
                .contentType(DEFAULT_CONTENT_TYPE);
        return initialRequestSpecification;
    }

    /**
     * Verify the Message log keyword and document number for reversal
     * transaction type.
     *
     * @author mayur-kumbhar
     */
    public void verifyMessageLogKeyword(final Response messageLog, String invoiceID) {
        messageLog.then()
                .assertThat()
                .body("content.keyword", hasItems("ReversalRequestType"));
    }

    /**
     * Verify the Message log keyword and document number for reversal
     * transaction type after Tax Purge.
     *
     * @author rohit-mogane
     */
    public void verifyMessageLogForPurge(final Response messageLog, String invoiceID) {
        messageLog.then()
                .assertThat()
                .body("content.keyword", hasItems("ReversalRequestType"));
        VertexLogger.log("For this invoice ID " + invoiceID + ", Reversal Response is not generated, as Tax Purge is done for the same ID");
    }


    public Response sendMessageLogGetRequest(String fromDate) {

    	final BigCommerceEndpoint configPoint = BigCommerceEndpoint.LOG_API_CONTROLLER;

        final String requestUrl = String.format("%s%s", baseUrl, configPoint.getUrlSuffix());

        Response getConfigsResponse = generateRequestSpecification()
                .queryParam(ID_QUERY_FROMDATE, fromDate)
                .when()
                .get(requestUrl);
        return getConfigsResponse;
    }

    /**
     * Calculates current date time in UTC format
     *
     * @author mayur-kumbhar
     */
    public String currentDateTime(int day, int oneDay, int lastDay) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, day);
        if (oneDay != 0) {
            cal.add(Calendar.DATE, oneDay);
        }
        if (lastDay == -1) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        Date toDate1 = cal.getTime();
        String fromDate = dateFormat.format(toDate1);
        return fromDate;
    }


    public Response sendAdjustRequest(final BigCommerceQuoteRequest jsonBody, final String invoiceId, double amount) {
        Response quoteResponse = sendAdjustRequest(jsonBody, DEFAULT_STORE_HASH_NUMBER, invoiceId);
        return quoteResponse;
    }

    protected Response sendQuoteRequest(final BigCommerceEndpoint endpoint, final BigCommerceQuoteRequest jsonBody,
                                        final int storeHashNumber, final double refundQuantity) {
        String storeHashNumberString = String.valueOf(storeHashNumber);
        String jsonBodyString = jsonConverter.convertToJsonString(jsonBody);

        final String requestUrl = String.format("%s%s", baseUrl, endpoint.getUrlSuffix());

        Response quoteResponse = generateRequestSpecification()
                .header(STORE_HASH_HEADER_NAME, storeHashNumberString)
                .body(jsonBodyString)
                .when()
                .post(requestUrl);

        return quoteResponse;
    }

    /**
     * Sends message log request with from time and document number
     *
     * @author rohit-mogane
     */
    public Response sendReversalGetRequest(final String fromTime, final String documentNumber) {

		final BigCommerceEndpoint configPoint = BigCommerceEndpoint.LOG_API_CONTROLLER;

		final String requestUrl = String.format("%s%s", baseUrl, configPoint.getUrlSuffix());
        Response response = generateRequestSpecification()
                .queryParam(ID_QUERY_FROMDATE, fromTime)
                .queryParam(ID_QUERY_DOCUMENT_NUMBER, documentNumber)
                .when()
                .get(requestUrl);
        return response;
    }

    /**
     * Sends the endpoint url for healthcheck.
     *
     * @author-Mayur-Kumbhar
     */
    public Response healthCheckEndpoint() {
        final BigCommerceEndpoint configEndpoint = BigCommerceEndpoint.HEALTHCHECK_API_ENDPOINT;
        final String requestUrl = String.format("%s%s", baseUrl, configEndpoint.getUrlSuffix());
        Response healthCheckResponse = generateRequestSpecification()
                .when()
                .get(requestUrl);
        return healthCheckResponse;

    }

}
