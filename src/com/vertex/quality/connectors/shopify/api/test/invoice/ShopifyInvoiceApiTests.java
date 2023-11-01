package com.vertex.quality.connectors.shopify.api.test.invoice;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.shopify.api.util.ShopifyApiRandomValueGenerators;
import com.vertex.quality.connectors.shopify.api.util.ShopifyApiUtils;
import com.vertex.quality.connectors.shopify.api.util.ShopifyDBUtils;
import com.vertex.quality.connectors.shopify.api.util.ShopifyHMACUtils;
import com.vertex.quality.connectors.shopify.base.ShopifyAPIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataAPI;
import com.vertex.quality.connectors.shopify.common.ShopifyDataDB;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.*;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage.LoginPage;
import com.vertex.quality.connectors.shopify.ui.pages.StoreFront.PaymentStoreFront;
import com.vertex.quality.connectors.shopify.ui.pages.StoreFront.StoreFrontPage;
import com.vertex.quality.connectors.shopify.ui.pages.StoreFront.StoreFrontTaxAndPaymentPage;
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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.assertTrue;

/**
 * CCMMER-4287 SHOP - QA and Automation support
 * Shopify invoice API tests
 *
 * @author Shivam.Soni
 */
public class ShopifyInvoiceApiTests extends ShopifyAPIBaseTest
{

	ShopifyStoreAuthenticationPage storeAuthenticationPage;
	LoginPage quotationLoginPage;
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
	StoreFrontPage quotationStoreFrontPage;
	PaymentStoreFront quotationPaymentPage;
	StoreFrontTaxAndPaymentPage quoationStoreFrontTaxAndPaymentPage;

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
	double tax;
	String getOrderEndpoint;

	/**
	 * XRAYSHOP-87 Shopify - Invoice - Test Case - make API call with valid order ID and valid payload
	 */
	//	@Test(groups = { "shopify_regression", "shopify_invoice" })
	public void shopifyInvoiceWithValidOrderDataTest( ) throws InterruptedException
	{
		String orderPayload = null;

		// Load storefront
		driver.get(ShopifyDataUI.StoreData.VTX_PROD_STORE.text);

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
		//        if (storeHeaderPage.getCartItemCount() > 0) {
		//            storeCartPage = storeHeaderPage.clickOnCartIcon();
		//            storeCartPage.deleteAllCartItems();
		//        }

		// Search the product, open product & add to the cart

		//		storeHeaderPage.clickOnSearch();
		//		storeSearchResultPage = storeHeaderPage.searchInEntireStore(
		//			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		//		storeItemPage = storeSearchResultPage.clickOnSearchedResult(
		//			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		//		storeItemPage.clickAddToCart();
		////		       storeCartPage = storeHeaderPage.clickOnCartIcon();
		//		Thread.sleep(3000);
		//		// Checkout, enter destination address, select shipping method
		//		storeShippingInfoPage = new ShopifyStoreShippingInfoPage(driver);
		//		storeShippingInfoPage = storeCartPage.clickOnCheckout();
		quotationStoreFrontPage = new StoreFrontPage(driver);
		quotationStoreFrontPage.goToStore(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		//		storeShippingInfoPage.setShippingAddress(true, Address.VertexKOPAddress.country.fullName,
		//			Address.VertexKOPAddress.addressLine1, Address.VertexKOPAddress.city,
		//			Address.VertexKOPAddress.state.fullName, Address.VertexKOPAddress.zip5);
		//		storeShippingInfoPage.clickOnContinueShipping();
		//		storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.STANDARD.text);

		// Payment, Place order & note down Order No.
		//		storePaymentPage = storeShippingInfoPage.clickContinuePayment();
		quotationPaymentPage = new PaymentStoreFront(driver);
		quotationPaymentPage.enterCreditCardDetails();

		//		tax = storePaymentPage.getTaxFromUIBeforeOrderPlace();
		//		assertEquals(tax, storePaymentPage.calculatePercentBasedTaxBeforeOrderPlaceUpRounding(6));
		//		storePaymentPage.enterCreditCardDetails();

		//		uiOrderNo = orderConfirmationPage.getOrderNoFromUI();

		//		quoationStoreFrontTaxAndPaymentPage = new StoreFrontTaxAndPaymentPage(driver);
		orderConfirmationPage = new ShopifyStoreOrderConfirmationPage(driver);
		storePaymentPage = new ShopifyStorePaymentPage(driver);
		storePaymentPage.clickPayNow();
		uiOrderNo = orderConfirmationPage.getOrderNoFromUI();
		//		quoationStoreFrontTaxAndPaymentPage.calculatePercentTaxBeforeOrderPlace(13);
		//		tax = quoationStoreFrontTaxAndPaymentPage.getTaxFrmUIBeforeOrderPlace();
		//		System.out.println("Quotation Tax Before Fulfillment Tax :" + tax);
		VertexLogger.log("Order number from UI: " + uiOrderNo);

		// Load admin panel
		driver.get(ShopifyDataUI.AdminPanelData.VTX_PROD_ADMIN_PANEL.text);

		// Login to admin
//		adminLoginPage = new ShopifyAdminLoginPage(driver);
//		adminLoginPage.loginToAdminPanel();
		quotationLoginPage = new LoginPage(driver);
		quotationLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_PROD_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_PROD_ADMIN_USER_KEY.text);
		// Navigating to order page
		adminHomePage = new ShopifyAdminHomePage(driver);
		adminHomePage.navigateToLeftPanelOption(ShopifyDataUI.AdminPanelLeftNavigationOptions.ORDERS.text);

		// Select order and fulfill the order
		adminOrdersPage = new ShopifyAdminOrdersPage(driver);
		adminOrdersPage.selectOrder(uiOrderNo);
		adminOrdersPage.markFulfillTheOrder();

		// Verify order status & tax
		adminOrderDetailsPage = adminOrdersPage.openOrderDetail(uiOrderNo);
		assertTrue(adminOrderDetailsPage.verifyOrderStatus(uiOrderNo,
			ShopifyDataUI.ShopifyOrderAndPaymentStatus.FULFILLED.text));
		//		assertEquals(tax, adminOrderDetailsPage.getTaxFromUI());

		// Fetching an Order's ID for API call
		apiOrderID = adminOrderDetailsPage.getOrderIdFromUIForAPICall(uiOrderNo);
		VertexLogger.log("Order ID from URL: " + apiOrderID);

		// Fetching Shop Access Token from the DB
		connection = ShopifyDBUtils.connectToDB();
		statement = ShopifyDBUtils.createQueryForAccessToken(connection, ShopifyDataDB.DbShopURLs.VTX_PROD.text);
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
		request = ShopifyApiUtils.buildGetRequestToFetchOrderPayload(
			getOrderURL.replace("<<replace_order_id>>", apiOrderID), decodedToken);
		response = ShopifyApiUtils.executeRequest(httpClient, request);
		assert response.body() != null;
		assertEquals(response.code(), ShopifyDataAPI.ResponseCodes.OK_200.value);
		try
		{
			orderPayload = response
				.body()
				.string();
			VertexLogger.log("Shopify order payload: " + orderPayload);
		}
		catch ( IOException io )
		{
			VertexLogger.log("Error occurred in getting order payload call", VertexLogLevel.ERROR);
			io.getStackTrace();
		}

		// Making a POST call to Vertex Connector for Invoice
		invoiceHMAC = ShopifyHMACUtils.generateBase64HMAC(orderPayload);
		requestBody = ShopifyApiUtils.createRequestBodyInJson(orderPayload);

		headers = new HashMap<>();
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.ACCESS_TOKEN.text, decodedToken);
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.HMAC_SHA256.text, invoiceHMAC);
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_SHOP_DOMAIN.text,
			ShopifyDataAPI.ShopifyAPIShopDomains.VTX_QA_DOMAIN.text);
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_ORDER_ID.text, apiOrderID);

		request = ShopifyApiUtils.buildPostRequestForInvoiceCalls(invoiceURL, headers, requestBody);
		response = ShopifyApiUtils.executeRequest(httpClient, request);
		assert response.body() != null;
		assertEquals(ShopifyDataAPI.ResponseCodes.OK_200.value, response.code());
		try
		{
			String orderFromConnectorResponse = response
				.body()
				.string();
			assertEquals(apiOrderID, orderFromConnectorResponse);
			VertexLogger.log("Connector's response : " + orderFromConnectorResponse);
		}
		catch ( IOException io )
		{
			VertexLogger.log("errors in the response validation", VertexLogLevel.ERROR);
			fail("Failed to validate order id in connector's response");
		}
	}

	/**
	 * XRAYSHOP-88 Shopify - Invoice - Test Case - make API call with valid order ID and Invalid payload
	 */
	@Test(groups = { "shopify_regression", "shopify_invoice" })
	public void shopifyInvoiceWithValidOrderIDInvalidPayloadTest( ) throws InterruptedException
	{
		String orderPayload = null;

		// Load storefront
		driver.get(ShopifyDataUI.StoreData.VTX_QA_STORE.text);

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
		if ( storeHeaderPage.getCartItemCount() > 0 )
		{
			storeCartPage = storeHeaderPage.clickOnCartIcon();
			storeCartPage.deleteAllCartItems();
		}

		// Search the product, open product & add to the cart
		storeHeaderPage.clickOnSearch();
		storeSearchResultPage = storeHeaderPage.searchInEntireStore(
			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		storeItemPage = storeSearchResultPage.clickOnSearchedResult(
			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		storeItemPage.clickAddToCart();
		storeCartPage = storeHeaderPage.clickOnCartIcon();

		// Checkout, enter destination address, select shipping method
		storeShippingInfoPage = storeCartPage.clickOnCheckout();
		storeShippingInfoPage.setShippingAddress(true, Address.Victoria.country.fullName, Address.Victoria.addressLine1,
			Address.Victoria.city, Address.Victoria.province.fullName, Address.Victoria.zip5);
		storeShippingInfoPage.clickOnContinueShipping();
		storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.INTERNATIONAL.text);

		// Payment, Place order & note down Order No.
		storePaymentPage = storeShippingInfoPage.clickContinuePayment();
		tax = storePaymentPage.getTaxFromUIBeforeOrderPlace();
		assertEquals(tax, storePaymentPage.calculatePercentBasedTaxBeforeOrderPlace(12));
		storePaymentPage.enterCreditCardDetails();
		orderConfirmationPage = storePaymentPage.clickPayNow();
		uiOrderNo = orderConfirmationPage.getOrderNoFromUI();
		VertexLogger.log("Order number from UI: " + uiOrderNo);

		// Load admin panel
		driver.get(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);

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
		assertTrue(adminOrderDetailsPage.verifyOrderStatus(uiOrderNo,
			ShopifyDataUI.ShopifyOrderAndPaymentStatus.FULFILLED.text));
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
		request = ShopifyApiUtils.buildGetRequestToFetchOrderPayload(
			getOrderURL.replace("<<replace_order_id>>", apiOrderID), decodedToken);
		response = ShopifyApiUtils.executeRequest(httpClient, request);
		assert response.body() != null;
		assertEquals(response.code(), ShopifyDataAPI.ResponseCodes.OK_200.value);
		try
		{
			orderPayload = response
				.body()
				.string();
			VertexLogger.log("Shopify order payload: " + orderPayload);
		}
		catch ( IOException io )
		{
			VertexLogger.log("Error occurred in getting order payload call", VertexLogLevel.ERROR);
			io.getStackTrace();
		}

		// Making a POST call to Vertex Connector for Invoice
		String wrongPayload = ShopifyDataAPI.ShopifyGDPRData.CUSTOMER_REDACT_PAYLOAD.text;
		invoiceHMAC = ShopifyHMACUtils.generateBase64HMAC(wrongPayload);
		requestBody = ShopifyApiUtils.createRequestBodyInJson(wrongPayload);

		headers = new HashMap<>();
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.ACCESS_TOKEN.text, decodedToken);
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.HMAC_SHA256.text, invoiceHMAC);
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_SHOP_DOMAIN.text,
			ShopifyDataAPI.ShopifyAPIShopDomains.VTX_QA_DOMAIN.text);
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_ORDER_ID.text, apiOrderID);

		request = ShopifyApiUtils.buildPostRequestForInvoiceCalls(invoiceURL, headers, requestBody);
		response = ShopifyApiUtils.executeRequest(httpClient, request);
		assert response.body() != null;
		assertEquals(ShopifyDataAPI.ResponseCodes.OK_200.value, response.code());
		try
		{
			String orderFromConnectorResponse = response
				.body()
				.string();
			assertEquals(apiOrderID, orderFromConnectorResponse);
			VertexLogger.log("Connector's response : " + orderFromConnectorResponse);
		}
		catch ( IOException io )
		{
			VertexLogger.log("errors in the order id validation", VertexLogLevel.ERROR);
			fail("Failed to validate order id in connector's response");
		}
	}

	/**
	 * XRAYSHOP-89 Shopify - Invoice - Test Case - make API call with Invalid order ID and valid payload
	 */
	@Test(groups = { "shopify_regression", "shopify_invoice" })
	public void shopifyInvoiceWithValidOrderPayloadInvalidOrderIDTest( ) throws InterruptedException
	{
		String orderPayload = null;

		// Load storefront
		driver.get(ShopifyDataUI.StoreData.VTX_QA_STORE.text);

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
		if ( storeHeaderPage.getCartItemCount() > 0 )
		{
			storeCartPage = storeHeaderPage.clickOnCartIcon();
			storeCartPage.deleteAllCartItems();
		}

		// Search the product, open product & add to the cart
		storeHeaderPage.clickOnSearch();
		storeSearchResultPage = storeHeaderPage.searchInEntireStore(
			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		storeItemPage = storeSearchResultPage.clickOnSearchedResult(
			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		storeItemPage.clickAddToCart();
		storeCartPage = storeHeaderPage.clickOnCartIcon();

		// Checkout, enter destination address, select shipping method
		storeShippingInfoPage = storeCartPage.clickOnCheckout();
		storeShippingInfoPage.setShippingAddress(true, Address.Ottawa.country.fullName, Address.Ottawa.addressLine1,
			Address.Ottawa.city, Address.Ottawa.province.fullName, Address.Ottawa.zip5);
		storeShippingInfoPage.clickOnContinueShipping();
		storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.INTERNATIONAL.text);

		// Payment, Place order & note down Order No.
		storePaymentPage = storeShippingInfoPage.clickContinuePayment();
		tax = storePaymentPage.getTaxFromUIBeforeOrderPlace();
		assertEquals(tax, storePaymentPage.calculatePercentBasedTaxBeforeOrderPlace(13));
		storePaymentPage.enterCreditCardDetails();
		orderConfirmationPage = storePaymentPage.clickPayNow();
		uiOrderNo = orderConfirmationPage.getOrderNoFromUI();
		VertexLogger.log("Order number from UI: " + uiOrderNo);

		// Load admin panel
		driver.get(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);

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
		assertTrue(adminOrderDetailsPage.verifyOrderStatus(uiOrderNo,
			ShopifyDataUI.ShopifyOrderAndPaymentStatus.FULFILLED.text));
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
		request = ShopifyApiUtils.buildGetRequestToFetchOrderPayload(
			getOrderURL.replace("<<replace_order_id>>", apiOrderID), decodedToken);
		response = ShopifyApiUtils.executeRequest(httpClient, request);
		assert response.body() != null;
		assertEquals(response.code(), ShopifyDataAPI.ResponseCodes.OK_200.value);
		try
		{
			orderPayload = response
				.body()
				.string();
			VertexLogger.log("Shopify order payload: " + orderPayload);
		}
		catch ( IOException io )
		{
			VertexLogger.log("Error occurred in getting order payload call", VertexLogLevel.ERROR);
			io.getStackTrace();
		}

		// Making a POST call to Vertex Connector for Invoice
		invoiceHMAC = ShopifyHMACUtils.generateBase64HMAC(orderPayload);
		requestBody = ShopifyApiUtils.createRequestBodyInJson(orderPayload);

		headers = new HashMap<>();
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.ACCESS_TOKEN.text, decodedToken);
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.HMAC_SHA256.text, invoiceHMAC);
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_SHOP_DOMAIN.text,
			ShopifyDataAPI.ShopifyAPIShopDomains.VTX_QA_DOMAIN.text);
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_ORDER_ID.text,
			ShopifyApiRandomValueGenerators.generateRandomNumbers(ShopifyDataAPI.ShopifyRandomValueLength.LONG.text));

		request = ShopifyApiUtils.buildPostRequestForInvoiceCalls(invoiceURL, headers, requestBody);
		response = ShopifyApiUtils.executeRequest(httpClient, request);
		assert response.body() != null;
		assertEquals(ShopifyDataAPI.ResponseCodes.OK_200.value, response.code());
		try
		{
			String orderFromConnectorResponse = response
				.body()
				.string();
			assertEquals(orderFromConnectorResponse, ShopifyDataAPI.ShopifyAPIMessages.BAD_INVOICE_DATA.text);
			VertexLogger.log("Connector's response : " + orderFromConnectorResponse);
		}
		catch ( IOException io )
		{
			VertexLogger.log("errors in the order id validation", VertexLogLevel.ERROR);
			fail("Failed to validate connector's error message in response");
		}
	}

	/**
	 * XRAYSHOP-90 Shopify - Invoice - Test Case - make API call with valid order ID and empty payload
	 */
	@Test(groups = { "shopify_regression", "shopify_invoice" })
	public void shopifyInvoiceWithEmptyPayloadValidOrderIDTest( ) throws InterruptedException
	{
		String orderPayload = null;

		// Load storefront
		driver.get(ShopifyDataUI.StoreData.VTX_QA_STORE.text);

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
		if ( storeHeaderPage.getCartItemCount() > 0 )
		{
			storeCartPage = storeHeaderPage.clickOnCartIcon();
			storeCartPage.deleteAllCartItems();
		}

		// Search the product, open product & add to the cart
		storeHeaderPage.clickOnSearch();
		storeSearchResultPage = storeHeaderPage.searchInEntireStore(
			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		storeItemPage = storeSearchResultPage.clickOnSearchedResult(
			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		storeItemPage.clickAddToCart();
		storeCartPage = storeHeaderPage.clickOnCartIcon();

		// Checkout, enter destination address, select shipping method
		storeShippingInfoPage = storeCartPage.clickOnCheckout();
		storeShippingInfoPage.setShippingAddress(true, Address.Miramar.country.fullName, Address.Miramar.addressLine1,
			Address.Miramar.city, Address.Miramar.state.fullName, Address.Miramar.zip5);
		storeShippingInfoPage.clickOnContinueShipping();
		storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.STANDARD.text);

		// Payment, Place order & note down Order No.
		storePaymentPage = storeShippingInfoPage.clickContinuePayment();
		tax = storePaymentPage.getTaxFromUIBeforeOrderPlace();
		assertEquals(tax, storePaymentPage.calculatePercentBasedTaxBeforeOrderPlace(7));
		storePaymentPage.enterCreditCardDetails();
		orderConfirmationPage = storePaymentPage.clickPayNow();
		uiOrderNo = orderConfirmationPage.getOrderNoFromUI();
		VertexLogger.log("Order number from UI: " + uiOrderNo);

		// Load admin panel
		driver.get(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);

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
		assertTrue(adminOrderDetailsPage.verifyOrderStatus(uiOrderNo,
			ShopifyDataUI.ShopifyOrderAndPaymentStatus.FULFILLED.text));
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
		request = ShopifyApiUtils.buildGetRequestToFetchOrderPayload(
			getOrderURL.replace("<<replace_order_id>>", apiOrderID), decodedToken);
		response = ShopifyApiUtils.executeRequest(httpClient, request);
		assert response.body() != null;
		assertEquals(response.code(), ShopifyDataAPI.ResponseCodes.OK_200.value);
		try
		{
			orderPayload = response
				.body()
				.string();
			VertexLogger.log("Shopify order payload: " + orderPayload);
		}
		catch ( IOException io )
		{
			VertexLogger.log("Error occurred in getting order payload call", VertexLogLevel.ERROR);
			io.getStackTrace();
		}

		// Making a POST call to Vertex Connector for Invoice
		invoiceHMAC = ShopifyHMACUtils.generateBase64HMAC("");
		requestBody = ShopifyApiUtils.createRequestBodyInJson("");

		headers = new HashMap<>();
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.ACCESS_TOKEN.text, decodedToken);
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.HMAC_SHA256.text, invoiceHMAC);
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_SHOP_DOMAIN.text,
			ShopifyDataAPI.ShopifyAPIShopDomains.VTX_QA_DOMAIN.text);
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_ORDER_ID.text,
			ShopifyApiRandomValueGenerators.generateRandomNumbers(ShopifyDataAPI.ShopifyRandomValueLength.LONG.text));

		request = ShopifyApiUtils.buildPostRequestForInvoiceCalls(invoiceURL, headers, requestBody);
		response = ShopifyApiUtils.executeRequest(httpClient, request);
		assert response.body() != null;
		assertEquals(ShopifyDataAPI.ResponseCodes.BAD_DATA_400.value, response.code());
	}

	/**
	 * XRAYSHOP-91 Shopify - Invoice - Test Case - make API call with valid order ID and valid payload for unfulfilled
	 * order
	 */
	@Test(groups = { "shopify_regression", "shopify_invoice" })
	public void shopifyInvoiceWithValidOrderDataUnfulfilledOrderTest( ) throws InterruptedException
	{
		String orderPayload = null;

		// Load storefront
		driver.get(ShopifyDataUI.StoreData.VTX_QA_STORE.text);

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
		if ( storeHeaderPage.getCartItemCount() > 0 )
		{
			storeCartPage = storeHeaderPage.clickOnCartIcon();
			storeCartPage.deleteAllCartItems();
		}

		// Search the product, open product & add to the cart
		storeHeaderPage.clickOnSearch();
		storeSearchResultPage = storeHeaderPage.searchInEntireStore(
			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		storeItemPage = storeSearchResultPage.clickOnSearchedResult(
			ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		storeItemPage.clickAddToCart();
		storeCartPage = storeHeaderPage.clickOnCartIcon();

		// Checkout, enter destination address, select shipping method
		storeShippingInfoPage = storeCartPage.clickOnCheckout();
		storeShippingInfoPage.setShippingAddress(true, Address.Northbrook.country.fullName,
			Address.Northbrook.addressLine1, Address.Northbrook.city, Address.Northbrook.province.fullName,
			Address.Northbrook.zip5);
		storeShippingInfoPage.clickOnContinueShipping();
		storeShippingInfoPage.selectShippingMethod(ShopifyDataUI.ShippingMethods.INTERNATIONAL.text);

		// Payment, Place order & note down Order No.
		storePaymentPage = storeShippingInfoPage.clickContinuePayment();
		tax = storePaymentPage.getTaxFromUIBeforeOrderPlace();
		assertEquals(tax, storePaymentPage.calculatePercentBasedTaxBeforeOrderPlace(13));
		storePaymentPage.enterCreditCardDetails();
		orderConfirmationPage = storePaymentPage.clickPayNow();
		uiOrderNo = orderConfirmationPage.getOrderNoFromUI();
		VertexLogger.log("Order number from UI: " + uiOrderNo);

		// Load admin panel
		driver.get(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);

		// Login to admin
		adminLoginPage = new ShopifyAdminLoginPage(driver);
		adminLoginPage.loginToAdminPanel();

		// Navigating to order page
		adminHomePage = new ShopifyAdminHomePage(driver);
		adminHomePage.navigateToLeftPanelOption(ShopifyDataUI.AdminPanelLeftNavigationOptions.ORDERS.text);

		// Open order & verify order status & tax
		adminOrdersPage = new ShopifyAdminOrdersPage(driver);
		adminOrderDetailsPage = adminOrdersPage.openOrderDetail(uiOrderNo);
		assertTrue(adminOrderDetailsPage.verifyOrderStatus(uiOrderNo,
			ShopifyDataUI.ShopifyOrderAndPaymentStatus.UNFULFILLED.text));
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
		request = ShopifyApiUtils.buildGetRequestToFetchOrderPayload(
			getOrderURL.replace("<<replace_order_id>>", apiOrderID), decodedToken);
		response = ShopifyApiUtils.executeRequest(httpClient, request);
		assert response.body() != null;
		assertEquals(response.code(), ShopifyDataAPI.ResponseCodes.OK_200.value);
		try
		{
			orderPayload = response
				.body()
				.string();
			VertexLogger.log("Shopify order payload: " + orderPayload);
		}
		catch ( IOException io )
		{
			VertexLogger.log("Error occurred in getting order payload call", VertexLogLevel.ERROR);
			io.getStackTrace();
		}

		// Making a POST call to Vertex Connector for Invoice
		invoiceHMAC = ShopifyHMACUtils.generateBase64HMAC(orderPayload);
		requestBody = ShopifyApiUtils.createRequestBodyInJson(orderPayload);

		headers = new HashMap<>();
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.ACCESS_TOKEN.text, decodedToken);
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.HMAC_SHA256.text, invoiceHMAC);
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_SHOP_DOMAIN.text,
			ShopifyDataAPI.ShopifyAPIShopDomains.VTX_QA_DOMAIN.text);
		headers.put(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_ORDER_ID.text, apiOrderID);

		request = ShopifyApiUtils.buildPostRequestForInvoiceCalls(invoiceURL, headers, requestBody);
		response = ShopifyApiUtils.executeRequest(httpClient, request);
		assert response.body() != null;
		assertEquals(ShopifyDataAPI.ResponseCodes.BAD_DATA_400.value, response.code());
		try
		{
			String orderFromConnectorResponse = response
				.body()
				.string();
			VertexLogger.log("Connector responded order id : " + orderFromConnectorResponse);
			assertEquals(ShopifyDataAPI.ShopifyAPIMessages.UNFULFILLED_ORDER_MSG.text, orderFromConnectorResponse);
			VertexLogger.log("Connector's response' : " + orderFromConnectorResponse);
		}
		catch ( IOException io )
		{
			VertexLogger.log("errors in the response validation", VertexLogLevel.ERROR);
			fail("Failed to validate order id in connector's response");
		}
	}
}
