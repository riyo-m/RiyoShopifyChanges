package com.vertex.quality.connectors.shopify.api.test.gdpr;

import com.vertex.quality.connectors.shopify.api.util.ShopifyApiRandomValueGenerators;
import com.vertex.quality.connectors.shopify.api.util.ShopifyApiUtils;
import com.vertex.quality.connectors.shopify.api.util.ShopifyHMACUtils;
import com.vertex.quality.connectors.shopify.base.ShopifyAPIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataAPI;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.testng.Assert.assertEquals;

/**
 * CCMMER-4060 Shopify - Test automation - Part 4 - Customer Redact GDPR Tests
 *
 * @author Shivam.Soni
 */
public class ShopifyCustomerRedactGDPRTests extends ShopifyAPIBaseTest {

    OkHttpClient httpClient;
    RequestBody requestBody;
    Request request;
    Response response;
    String requestData;
    String gdprHMAC;
    String fakeShopID;
    String wrongShopDomain;
    String wrongOrderID;
    String fakeCustomerID;
    String wrongEmail;
    String wrongPhone;
    String wrongDataRequestID;

    /**
     * XRAYSHOP-73 Shopify - GDPR - Customer Redact - Test Case - Verify shop redact endpoint with valid data
     */
    @Test(groups = {"shopify_smoke", "shopify_regression", "shopify_gdpr"})
    public void customerRedactValidDataTest() {
        // Making an API call with Valid Request
        requestData = ShopifyApiUtils.replaceDataInCustomerRedactRequest();
        gdprHMAC = ShopifyHMACUtils.generateBase64HMAC(requestData);
        httpClient = ShopifyApiUtils.buildHttpClient();
        requestBody = ShopifyApiUtils.createRequestBodyInJson(requestData);
        request = ShopifyApiUtils.buildPostRequestForGDPRCalls(customerRedactURL, requestBody, gdprHMAC);
        response = ShopifyApiUtils.executeRequest(httpClient, request);
        assert response.body() != null;
        assertEquals(ShopifyDataAPI.ResponseCodes.OK_200.value, response.code());
    }

    /**
     * XRAYSHOP-74 Shopify - GDPR - Customer Redact - Test Case - Verify shop redact endpoint with Invalid data
     */
    @Test(groups = {"shopify_regression", "shopify_gdpr"})
    public void customerRedactInValidDataTest() {
        // Making an API call with decimal Shop ID rather than Integer Shop ID
        fakeShopID = String.valueOf(ThreadLocalRandom.current().nextLong());
        wrongShopDomain = "ss@NotMy-Your-Our-Shopify.com";
        wrongOrderID = String.valueOf(Math.random());
        fakeCustomerID = String.valueOf(ThreadLocalRandom.current().nextLong() + Math.random());
        wrongEmail = "unknown@uu.com";
        wrongPhone = "+1 (abc) def-test";
        wrongDataRequestID = String.valueOf(ThreadLocalRandom.current().nextLong() + Math.random());

        requestData = ShopifyApiUtils.replaceDataInCustomerRedactRequest(ShopifyDataAPI.ShopifyGDPRData.CUSTOMER_REDACT_PAYLOAD.text,
                fakeShopID, wrongShopDomain, fakeCustomerID, wrongEmail, wrongPhone, wrongDataRequestID, wrongOrderID);
        gdprHMAC = ShopifyHMACUtils.generateBase64HMAC(requestData);
        httpClient = ShopifyApiUtils.buildHttpClient();
        requestBody = ShopifyApiUtils.createRequestBodyInJson(requestData);
        request = ShopifyApiUtils.buildPostRequestForGDPRCalls(shopRedactURL, requestBody, gdprHMAC);
        response = ShopifyApiUtils.executeRequest(httpClient, request);
        assert response.body() != null;
        assertEquals(ShopifyDataAPI.ResponseCodes.OK_200.value, response.code());
    }

    /**
     * XRAYSHOP-75 Shopify - GDPR - Customer Redact - Test Case - Verify shop redact endpoint with Special Characters in data
     */
    @Test(groups = {"shopify_regression", "shopify_gdpr"})
    public void customerRedactWithSpecialCharactersInDataTest() {
        // Making an API call with decimal Shop ID rather than Integer Shop ID
        fakeShopID = ShopifyApiRandomValueGenerators.generateRandomSpecialCharacters(15);
        wrongShopDomain = ShopifyApiRandomValueGenerators.generateRandomSpecialCharacters(25);
        wrongOrderID = ShopifyApiRandomValueGenerators.generateRandomSpecialCharacters(5);
        fakeCustomerID = ShopifyApiRandomValueGenerators.generateRandomSpecialCharacters(12);
        wrongEmail = ShopifyApiRandomValueGenerators.generateRandomSpecialCharacters(7);
        wrongPhone = ShopifyApiRandomValueGenerators.generateRandomSpecialCharacters(10);
        wrongDataRequestID = ShopifyApiRandomValueGenerators.generateRandomSpecialCharacters(9);

        requestData = ShopifyApiUtils.replaceDataInCustomerRedactRequest(ShopifyDataAPI.ShopifyGDPRData.CUSTOMER_REDACT_PAYLOAD.text,
                fakeShopID, wrongShopDomain, fakeCustomerID, wrongEmail, wrongPhone, wrongDataRequestID, wrongOrderID);
        gdprHMAC = ShopifyHMACUtils.generateBase64HMAC(requestData);
        httpClient = ShopifyApiUtils.buildHttpClient();
        requestBody = ShopifyApiUtils.createRequestBodyInJson(requestData);
        request = ShopifyApiUtils.buildPostRequestForGDPRCalls(shopRedactURL, requestBody, gdprHMAC);
        response = ShopifyApiUtils.executeRequest(httpClient, request);
        assert response.body() != null;
        assertEquals(ShopifyDataAPI.ResponseCodes.OK_200.value, response.code());
    }

    /**
     * XRAYSHOP-76 Shopify - GDPR - Customer Redact - Test Case - Verify shop redact endpoint with null data
     */
    @Test(groups = {"shopify_regression", "shopify_gdpr"})
    public void customerRedactWithNullDataTest() {
        // Making an API call with decimal Shop ID rather than Integer Shop ID
        gdprHMAC = ShopifyHMACUtils.generateBase64HMAC("");
        httpClient = ShopifyApiUtils.buildHttpClient();
        requestBody = ShopifyApiUtils.createRequestBodyInJson("");
        request = ShopifyApiUtils.buildPostRequestForGDPRCalls(shopRedactURL, requestBody, gdprHMAC);
        response = ShopifyApiUtils.executeRequest(httpClient, request);
        assert response.body() != null;
        assertEquals(ShopifyDataAPI.ResponseCodes.BAD_DATA_400.value, response.code());
    }
}
