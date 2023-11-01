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
 * CCMMER-4060 Shopify - Test automation - Part 4 - Shop Redact GDPR Tests
 *
 * @author Shivam.Soni
 */
public class ShopifyShopRedactGDPRTests extends ShopifyAPIBaseTest {

    OkHttpClient httpClient;
    RequestBody requestBody;
    Request request;
    Response response;
    String requestData;
    String gdprHMAC;
    String fakeShopID;

    /**
     * XRAYSHOP-68 Shopify - GDPR - Shop Redact - Test Case - Verify shop redact endpoint with valid data
     */
    @Test(groups = {"shopify_smoke", "shopify_regression", "shopify_gdpr"})
    public void shopRedactValidDataTest() {
        // Making an API call with Valid Request
        requestData = ShopifyApiUtils.replaceDataInShopRedactRequest();
        gdprHMAC = ShopifyHMACUtils.generateBase64HMAC(requestData);
        httpClient = ShopifyApiUtils.buildHttpClient();
        requestBody = ShopifyApiUtils.createRequestBodyInJson(requestData);
        request = ShopifyApiUtils.buildPostRequestForGDPRCalls(shopRedactURL, requestBody, gdprHMAC);
        response = ShopifyApiUtils.executeRequest(httpClient, request);
        assert response.body() != null;
        assertEquals(ShopifyDataAPI.ResponseCodes.OK_200.value, response.code());
    }

    /**
     * XRAYSHOP-70 Shopify - GDPR - Shop Redact - Test Case - Verify shop redact endpoint with Invalid data
     */
    @Test(groups = {"shopify_regression", "shopify_gdpr"})
    public void shopRedactInValidDataTest() {
        // Making an API call with In valid Request
        fakeShopID = String.valueOf(ThreadLocalRandom.current().nextLong());
        requestData = ShopifyApiUtils.replaceDataInShopRedactRequest(ShopifyDataAPI.ShopifyGDPRData.SHOP_REDACT_PAYLOAD.text,
                fakeShopID, ShopifyDataAPI.ShopifyAPIShopDomains.VTX_QA_DOMAIN.text);
        gdprHMAC = ShopifyHMACUtils.generateBase64HMAC(requestData);
        httpClient = ShopifyApiUtils.buildHttpClient();
        requestBody = ShopifyApiUtils.createRequestBodyInJson(requestData);
        request = ShopifyApiUtils.buildPostRequestForGDPRCalls(shopRedactURL, requestBody, gdprHMAC);
        response = ShopifyApiUtils.executeRequest(httpClient, request);
        assert response.body() != null;
        assertEquals(ShopifyDataAPI.ResponseCodes.OK_200.value, response.code());
    }

    /**
     * XRAYSHOP-71 Shopify - GDPR - Shop Redact - Test Case - Verify shop redact endpoint with Special Characters in data
     */
    @Test(groups = {"shopify_regression", "shopify_gdpr"})
    public void shopRedactWithSpecialCharactersInDataTest() {
        // Making an API call with Valid Request but special characters in the request
        fakeShopID = ShopifyApiRandomValueGenerators.generateRandomSpecialCharacters(15);
        requestData = ShopifyApiUtils.replaceDataInShopRedactRequest(ShopifyDataAPI.ShopifyGDPRData.SHOP_REDACT_PAYLOAD.text,
                fakeShopID, ShopifyApiRandomValueGenerators.generateRandomSpecialCharacters(50));
        gdprHMAC = ShopifyHMACUtils.generateBase64HMAC(requestData);
        httpClient = ShopifyApiUtils.buildHttpClient();
        requestBody = ShopifyApiUtils.createRequestBodyInJson(requestData);
        request = ShopifyApiUtils.buildPostRequestForGDPRCalls(shopRedactURL, requestBody, gdprHMAC);
        response = ShopifyApiUtils.executeRequest(httpClient, request);
        assert response.body() != null;
        assertEquals(ShopifyDataAPI.ResponseCodes.OK_200.value, response.code());
    }

    /**
     * XRAYSHOP-72 Shopify - GDPR - Shop Redact - Test Case - Verify shop redact endpoint with null data
     */
    @Test(groups = {"shopify_regression", "shopify_gdpr"})
    public void shopRedactWithNullDataTest() {
        // Making an API call with empty request body
        gdprHMAC = ShopifyHMACUtils.generateBase64HMAC("");
        httpClient = ShopifyApiUtils.buildHttpClient();
        requestBody = ShopifyApiUtils.createRequestBodyInJson("");
        request = ShopifyApiUtils.buildPostRequestForGDPRCalls(shopRedactURL, requestBody, gdprHMAC);
        response = ShopifyApiUtils.executeRequest(httpClient, request);
        assert response.body() != null;
        assertEquals(ShopifyDataAPI.ResponseCodes.BAD_DATA_400.value, response.code());
    }
}
