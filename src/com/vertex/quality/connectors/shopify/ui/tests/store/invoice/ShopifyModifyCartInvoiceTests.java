package com.vertex.quality.connectors.shopify.ui.tests.store.invoice;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.shopify.api.util.ShopifyApiUtils;
import com.vertex.quality.connectors.shopify.api.util.ShopifyDBUtils;
import com.vertex.quality.connectors.shopify.api.util.ShopifyHMACUtils;
import com.vertex.quality.connectors.shopify.base.ShopifyUIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataAPI;
import com.vertex.quality.connectors.shopify.common.ShopifyDataDB;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * CCMMER-4066 Shopify - Test automation - Part 5
 * Shopify Modifying cart tests
 *
 * @author Shivam.Soni
 */
public class ShopifyModifyCartInvoiceTests extends ShopifyUIBaseTest {

    ShopifyStoreAuthenticationPage storeAuthenticationPage;
    ShopifyStoreLoginPage storeLoginPage;
    ShopifyStoreHeaderPage storeHeaderPage;
    ShopifyStoreCartPage storeCartPage;
    ShopifyStoreSearchResultPage storeSearchResultPage;
    ShopifyStoreItemPage storeItemPage;
    ShopifyStoreShippingInfoPage storeShippingInfoPage;
    ShopifyStorePaymentPage storePaymentPage;
    ShopifyStoreOrderConfirmationPage orderConfirmationPage;
    ShopifyAdminLoginPage adminLoginPage;
    ShopifyAdminHomePage adminHomePage;
    ShopifyAdminOrdersPage adminOrdersPage;
    ShopifyAdminOrderDetailsPage adminOrderDetailsPage;

    Connection connection;
    PreparedStatement statement;
    ResultSet resultSet;

    OkHttpClient httpClient;
    String invoiceHMAC;
    Map<String, String> headers;
    RequestBody requestBody;
    Request request;
    Response response;

    String decodedToken;
    String uiOrderNo;
    String apiOrderID;
    String getOrderEndpoint;
    String orderPayload;

    double tax;

    /**
     * XRAYSHOP-48 Shopify - Invoice - Test Case - Create Sales Invoice change location
     */
    @Test(groups = {"shopify_regression", "shopify_invoice"})
    public void taxInvoiceChangeLocationTest() {
        // Load storefront
        loadShopifyStore();

        // Handle store authentication
        storeAuthenticationPage = new ShopifyStoreAuthenticationPage(driver);
        storeAuthenticationPage.enterIntoStoreByStorePassword();

        // Login to store
        storeHeaderPage = new ShopifyStoreHeaderPage(driver);
        storeHeaderPage.clickOnLoginAccount();
        storeLoginPage = new ShopifyStoreLoginPage(driver);
        storeLoginPage.loginToStore();
        storeHeaderPage.gotoStoreHomePage();

        // Delete all the cart items if any available in the cart
        if (storeHeaderPage.getCartItemCount() > 0) {
            storeCartPage = storeHeaderPage.clickOnCartIcon();
            storeCartPage.deleteAllCartItems();
        }

        // Search the product, open product & add to the cart
        storeHeaderPage.clickOnSearch();
        storeSearchResultPage = storeHeaderPage.searchInEntireStore(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
        storeItemPage = storeSearchResultPage.clickOnSearchedResult(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
        storeItemPage.clickAddToCart();
        storeCartPage = storeHeaderPage.clickOnCartIcon();

        // Checkout, enter destination address, select shipping method
        storeShippingInfoPage = storeCartPage.clickOnCheckout();
        storeShippingInfoPage.setShippingAddress(true, Address.LosAngeles.country.fullName, Address.LosAngeles.addressLine1,
                Address.LosAngeles.city, Address.LosAngeles.state.fullName, Address.LosAngeles.zip5);
        storeShippingInfoPage.clickOnContinueShipping();
        storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.STANDARD.text);

        // Payment, Place order & note down Order No.
        storePaymentPage = storeShippingInfoPage.clickContinuePayment();
        assertEquals(storePaymentPage.getTaxFromUIBeforeOrderPlace(), storePaymentPage.calculatePercentBasedTaxBeforeOrderPlace(9.5));

        // Navigate to home page
        loadShopifyStore();

        // Search the product, open product & add to the cart
        storeHeaderPage.clickOnSearch();
        storeSearchResultPage = storeHeaderPage.searchInEntireStore(ShopifyDataUI.Products.COLLECTION_SNOWBOARD_HYDROGEN.text);
        storeItemPage = storeSearchResultPage.clickOnSearchedResult(ShopifyDataUI.Products.COLLECTION_SNOWBOARD_HYDROGEN.text);
        storeItemPage.clickAddToCart();
        storeCartPage = storeHeaderPage.clickOnCartIcon();

        // Checkout, enter destination address, select shipping method
        storeShippingInfoPage = storeCartPage.clickOnCheckout();
        storeShippingInfoPage.setShippingAddress(true, Address.Louisiana.country.fullName, Address.Louisiana.addressLine1,
                Address.Louisiana.city, Address.Louisiana.state.fullName, Address.Louisiana.zip5);
        storeShippingInfoPage.clickOnContinueShipping();
        storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.STANDARD.text);

        // Payment, Place order & note down Order No.
        storePaymentPage = storeShippingInfoPage.clickContinuePayment();
        tax = storePaymentPage.getTaxFromUIBeforeOrderPlace();
        assertEquals(tax, storePaymentPage.calculatePercentBasedTaxBeforeOrderPlace(9.45));
        storePaymentPage.enterCreditCardDetails();
        orderConfirmationPage = storePaymentPage.clickPayNow();
        uiOrderNo = orderConfirmationPage.getOrderNoFromUI();
        VertexLogger.log(uiOrderNo);

        // Load admin panel
        loadShopifyAdmin();

        // Login to admin
        adminLoginPage = new ShopifyAdminLoginPage(driver);
        adminLoginPage.loginToAdminPanel();

        // Navigating to order page
        adminHomePage = new ShopifyAdminHomePage(driver);
        adminHomePage.navigateToLeftPanelOption(ShopifyDataUI.AdminPanelLeftNavigationOptions.ORDERS.text);

        // Select order and fulfill the order
        adminOrdersPage = new ShopifyAdminOrdersPage(driver);
        adminOrdersPage.selectOrder(uiOrderNo);
        adminOrdersPage.markFulfillTheOrder();

        // Verify order status & tax
        adminOrderDetailsPage = adminOrdersPage.openOrderDetail(uiOrderNo);
        assertTrue(adminOrderDetailsPage.verifyOrderStatus(uiOrderNo, ShopifyDataUI.ShopifyOrderAndPaymentStatus.FULFILLED.text));
        assertEquals(tax, adminOrderDetailsPage.getTaxFromUI());


        // Fetching an Order's ID for API call
        apiOrderID = adminOrderDetailsPage.getOrderIdFromUIForAPICall(uiOrderNo);
        VertexLogger.log("Order ID from URL: " + apiOrderID);

        // Fetching Shop Access Token from the DB
        connection = ShopifyDBUtils.connectToDB();
        statement = ShopifyDBUtils.createQueryForAccessToken(connection, ShopifyDataDB.DbShopURLs.VTX_QA.text);
        resultSet = ShopifyDBUtils.executeQuery(statement);
        ShopifyDBUtils.closeDBConnection(connection);

        // Decoding the encoded access token from the DB
        decodedToken = ShopifyHMACUtils.decodeValueInBase64(
                ShopifyDBUtils.getSingleDataFromResultSet(resultSet, ShopifyDataDB.DbColumns.SHOPIFY_ACCESS_TOKEN.text));
        VertexLogger.log("Shop access token: " + decodedToken);

        // Making a GET call to Shopify to fetch Order's payload
        httpClient = ShopifyApiUtils.buildHttpClient();
        getOrderEndpoint = getOrderURL.replace("<<replace_order_id>>", apiOrderID);
        VertexLogger.log("Get order url: " + getOrderEndpoint);
        request = ShopifyApiUtils.buildGetRequestToFetchOrderPayload(getOrderURL.replace("<<replace_order_id>>", apiOrderID), decodedToken);
        response = ShopifyApiUtils.executeRequest(httpClient, request);
        assert response.body() != null;
        assertEquals(response.code(), ShopifyDataAPI.ResponseCodes.OK_200.value);
        try {
            orderPayload = response.body().string();
            VertexLogger.log("Shopify order payload: " + orderPayload);
        } catch (IOException io) {
            VertexLogger.log("Error occurred in getting order payload call", VertexLogLevel.ERROR);
            io.getStackTrace();
        }

        // Making a POST call to Vertex Connector for Invoice
        invoiceHMAC = ShopifyHMACUtils.generateBase64HMAC(orderPayload);
        requestBody = ShopifyApiUtils.createRequestBodyInJson(orderPayload);

        headers = new HashMap<>();
        headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.ACCESS_TOKEN.text, decodedToken);
        headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.HMAC_SHA256.text, invoiceHMAC);
        headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_SHOP_DOMAIN.text, ShopifyDataAPI.ShopifyAPIShopDomains.VTX_QA_DOMAIN.text);
        headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_ORDER_ID.text, apiOrderID);

        request = ShopifyApiUtils.buildPostRequestForInvoiceCalls(invoiceURL, headers, requestBody);
        response = ShopifyApiUtils.executeRequest(httpClient, request);
        assert response.body() != null;
        assertEquals(ShopifyDataAPI.ResponseCodes.OK_200.value, response.code());
        try {
            String orderFromConnectorResponse = response.body().string();
            assertEquals(apiOrderID, orderFromConnectorResponse);
            VertexLogger.log("Connector's response : " + orderFromConnectorResponse);
        } catch (IOException io) {
            VertexLogger.log("errors in the response validation", VertexLogLevel.ERROR);
            fail("Failed to validate order id in connector's response");
        }
    }

    /**
     * XRAYSHOP-47 Shopify - Invoice - Test Case - End-to-End Test Create Sales Order and add/delete lines and change quantity and location
     */
    @Test(groups = {"shopify_regression", "shopify_invoice"})
    public void taxInvoiceAddDeleteItemsTest() {
        // Load storefront
        loadShopifyStore();

        // Handle store authentication
        storeAuthenticationPage = new ShopifyStoreAuthenticationPage(driver);
        storeAuthenticationPage.enterIntoStoreByStorePassword();

        // Login to store
        storeHeaderPage = new ShopifyStoreHeaderPage(driver);
        storeHeaderPage.clickOnLoginAccount();
        storeLoginPage = new ShopifyStoreLoginPage(driver);
        storeLoginPage.loginToStore();
        storeHeaderPage.gotoStoreHomePage();

        // Delete all the cart items if any available in the cart
        if (storeHeaderPage.getCartItemCount() > 0) {
            storeCartPage = storeHeaderPage.clickOnCartIcon();
            storeCartPage.deleteAllCartItems();
        }

        // Search the product, open product & add to the cart
        storeHeaderPage.clickOnSearch();
        storeSearchResultPage = storeHeaderPage.searchInEntireStore(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
        storeItemPage = storeSearchResultPage.clickOnSearchedResult(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
        storeItemPage.clickAddToCart();
        storeCartPage = storeHeaderPage.clickOnCartIcon();

        // Checkout, enter destination address, select shipping method
        storeShippingInfoPage = storeCartPage.clickOnCheckout();
        storeShippingInfoPage.setShippingAddress(true, Address.LosAngeles.country.fullName, Address.LosAngeles.addressLine1,
                Address.LosAngeles.city, Address.LosAngeles.state.fullName, Address.LosAngeles.zip5);
        storeShippingInfoPage.clickOnContinueShipping();
        storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.STANDARD.text);

        // Payment, Place order & note down Order No.
        storePaymentPage = storeShippingInfoPage.clickContinuePayment();
        assertEquals(storePaymentPage.getTaxFromUIBeforeOrderPlace(), storePaymentPage.calculatePercentBasedTaxBeforeOrderPlace(9.5));

        // Navigate to home page
        loadShopifyStore();
        storeHeaderPage.gotoStoreHomePage();

        // Search the product, open product & add to the cart
        storeHeaderPage.clickOnSearch();
        storeSearchResultPage = storeHeaderPage.searchInEntireStore(ShopifyDataUI.Products.COLLECTION_SNOWBOARD_HYDROGEN.text);
        storeItemPage = storeSearchResultPage.clickOnSearchedResult(ShopifyDataUI.Products.COLLECTION_SNOWBOARD_HYDROGEN.text);
        storeItemPage.clickAddToCart();
        storeCartPage = storeHeaderPage.clickOnCartIcon();

        // Delete an item from the cart.
        storeCartPage.deleteCartItem(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);

        // Checkout, enter destination address, select shipping method
        storeShippingInfoPage = storeCartPage.clickOnCheckout();
        storeShippingInfoPage.setShippingAddress(true, Address.Louisiana.country.fullName, Address.Louisiana.addressLine1,
                Address.Louisiana.city, Address.Louisiana.state.fullName, Address.Louisiana.zip5);
        storeShippingInfoPage.clickOnContinueShipping();
        storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.STANDARD.text);

        // Payment, Place order & note down Order No.
        storePaymentPage = storeShippingInfoPage.clickContinuePayment();
        tax = storePaymentPage.getTaxFromUIBeforeOrderPlace();
        assertEquals(tax, storePaymentPage.calculatePercentBasedTaxBeforeOrderPlace(9.45));
        storePaymentPage.enterCreditCardDetails();
        orderConfirmationPage = storePaymentPage.clickPayNow();
        uiOrderNo = orderConfirmationPage.getOrderNoFromUI();
        VertexLogger.log(uiOrderNo);

        // Load admin panel
        loadShopifyAdmin();

        // Login to admin
        adminLoginPage = new ShopifyAdminLoginPage(driver);
        adminLoginPage.loginToAdminPanel();

        // Navigating to order page
        adminHomePage = new ShopifyAdminHomePage(driver);
        adminHomePage.navigateToLeftPanelOption(ShopifyDataUI.AdminPanelLeftNavigationOptions.ORDERS.text);

        // Select order and fulfill the order
        adminOrdersPage = new ShopifyAdminOrdersPage(driver);
        adminOrdersPage.selectOrder(uiOrderNo);
        adminOrdersPage.markFulfillTheOrder();

        // Verify order status & tax
        adminOrderDetailsPage = adminOrdersPage.openOrderDetail(uiOrderNo);
        assertTrue(adminOrderDetailsPage.verifyOrderStatus(uiOrderNo, ShopifyDataUI.ShopifyOrderAndPaymentStatus.FULFILLED.text));
        assertEquals(tax, adminOrderDetailsPage.getTaxFromUI());


        // Fetching an Order's ID for API call
        apiOrderID = adminOrderDetailsPage.getOrderIdFromUIForAPICall(uiOrderNo);
        VertexLogger.log("Order ID from URL: " + apiOrderID);

        // Fetching Shop Access Token from the DB
        connection = ShopifyDBUtils.connectToDB();
        statement = ShopifyDBUtils.createQueryForAccessToken(connection, ShopifyDataDB.DbShopURLs.VTX_QA.text);
        resultSet = ShopifyDBUtils.executeQuery(statement);
        ShopifyDBUtils.closeDBConnection(connection);

        // Decoding the encoded access token from the DB
        decodedToken = ShopifyHMACUtils.decodeValueInBase64(
                ShopifyDBUtils.getSingleDataFromResultSet(resultSet, ShopifyDataDB.DbColumns.SHOPIFY_ACCESS_TOKEN.text));
        VertexLogger.log("Shop access token: " + decodedToken);

        // Making a GET call to Shopify to fetch Order's payload
        httpClient = ShopifyApiUtils.buildHttpClient();
        getOrderEndpoint = getOrderURL.replace("<<replace_order_id>>", apiOrderID);
        VertexLogger.log("Get order url: " + getOrderEndpoint);
        request = ShopifyApiUtils.buildGetRequestToFetchOrderPayload(getOrderURL.replace("<<replace_order_id>>", apiOrderID), decodedToken);
        response = ShopifyApiUtils.executeRequest(httpClient, request);
        assert response.body() != null;
        assertEquals(response.code(), ShopifyDataAPI.ResponseCodes.OK_200.value);
        try {
            orderPayload = response.body().string();
            VertexLogger.log("Shopify order payload: " + orderPayload);
        } catch (IOException io) {
            VertexLogger.log("Error occurred in getting order payload call", VertexLogLevel.ERROR);
            io.getStackTrace();
        }

        // Making a POST call to Vertex Connector for Invoice
        invoiceHMAC = ShopifyHMACUtils.generateBase64HMAC(orderPayload);
        requestBody = ShopifyApiUtils.createRequestBodyInJson(orderPayload);

        headers = new HashMap<>();
        headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.ACCESS_TOKEN.text, decodedToken);
        headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.HMAC_SHA256.text, invoiceHMAC);
        headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_SHOP_DOMAIN.text, ShopifyDataAPI.ShopifyAPIShopDomains.VTX_QA_DOMAIN.text);
        headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_ORDER_ID.text, apiOrderID);

        request = ShopifyApiUtils.buildPostRequestForInvoiceCalls(invoiceURL, headers, requestBody);
        response = ShopifyApiUtils.executeRequest(httpClient, request);
        assert response.body() != null;
        assertEquals(ShopifyDataAPI.ResponseCodes.OK_200.value, response.code());
        try {
            String orderFromConnectorResponse = response.body().string();
            assertEquals(apiOrderID, orderFromConnectorResponse);
            VertexLogger.log("Connector's response : " + orderFromConnectorResponse);
        } catch (IOException io) {
            VertexLogger.log("errors in the response validation", VertexLogLevel.ERROR);
            fail("Failed to validate order id in connector's response");
        }
    }
}
