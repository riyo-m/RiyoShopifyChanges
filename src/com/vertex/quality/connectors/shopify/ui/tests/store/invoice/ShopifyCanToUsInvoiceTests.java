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
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage.LoginPage;
import com.vertex.quality.connectors.shopify.ui.pages.StoreFront.*;
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
 * CCMMER-4066 Shopify - Test automation - Part 5, Contains all Canada to US invoice tests
 *
 * @author Shivam.Soni
 */
public class ShopifyCanToUsInvoiceTests extends ShopifyUIBaseTest
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
	//	ShopifyStorePaymentPage storePaymentPage;
	PaymentStoreFront quotationPaymentPage;
	StoreFrontContactDetailsPage quotationStoreFrontContactDetailsPage;
	StoreFrontTaxAndPaymentPage quoationStoreFrontTaxAndPaymentPage;
	StoreFrontPage quotationStoreFrontPage;
	double adminTax;
	AdminOrderPage qoutationAdminOrderPage;

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
	 * XRAYSHOP-41 Shopify - Invoice - Test Case -Create Sales Order with Invoice CAN to US
	 */
	@Test(groups = { "shopify_smoke", "shopify_regression", "shopify_invoice" })
	public void taxInvoiceCanToUSTest( ) throws InterruptedException
	{
		// Load storefront
		loadShopifyStore();

		// Handle store authentication
		driver.get(ShopifyDataUI.StoreData.VTX_PROD_STORE.text);

		// Handle store authentication
		storeAuthenticationPage = new ShopifyStoreAuthenticationPage(driver);
		storeAuthenticationPage.enterIntoStoreByStorePassword();

		storeHeaderPage = new ShopifyStoreHeaderPage(driver);
		storeHeaderPage.clickOnSearch();
		quotationStoreFrontPage = new StoreFrontPage(driver);
		quotationStoreFrontPage.goToStore(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);

		quotationStoreFrontContactDetailsPage = new StoreFrontContactDetailsPage(driver);
		quotationStoreFrontContactDetailsPage.enterContactDetails();

		quotationPaymentPage = new PaymentStoreFront(driver);
		quotationPaymentPage.enterCreditCardDetails();

		quoationStoreFrontTaxAndPaymentPage = new StoreFrontTaxAndPaymentPage(driver);
		//		quoationStoreFrontTaxAndPaymentPage.calculatePercentTaxBeforeOrderPlace(13);
		tax = quoationStoreFrontTaxAndPaymentPage.getTaxFrmUIBeforeOrderPlace();

		// Payment, Place order & note down Order No.
		//        storePaymentPage = storeShippingInfoPage.clickContinuePayment();
		//        tax = storePaymentPage.getTaxFromUIBeforeOrderPlace();
		//        assertEquals(tax, storePaymentPage.calculatePercentBasedTaxBeforeOrderPlaceUpRounding(6));
		//        storePaymentPage.enterCreditCardDetails();
		storePaymentPage = new ShopifyStorePaymentPage(driver);
		orderConfirmationPage = storePaymentPage.clickPayNow();
		uiOrderNo = orderConfirmationPage.getOrderNoFromUI();
		VertexLogger.log(uiOrderNo);

		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_PROD_ADMIN_PANEL.text);
		//		Thread.sleep(5000);
		//
		//		loadShopifyAdmin();
		quotationLoginPage = new LoginPage(driver);
		quotationLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_PROD_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_PROD_ADMIN_USER_KEY.text);

		qoutationAdminOrderPage = new AdminOrderPage(driver);
		qoutationAdminOrderPage.fulfillOrderInAdmin(uiOrderNo);

		adminTax = qoutationAdminOrderPage.getTaxFromAdmin();
		System.out.println("Invoice Tax After Fulfillment Tax :" + adminTax);
		assertEquals(tax, adminTax);
Thread.sleep(3000);
		qoutationAdminOrderPage.getFulfilledDialog();

		// Load admin panel
		//        loadShopifyAdmin();
		//
		//        // Login to admin
		//        adminLoginPage = new ShopifyAdminLoginPage(driver);
		//        adminLoginPage.loginToAdminPanel();
		//
		//        // Navigating to order page
		//        adminHomePage = new ShopifyAdminHomePage(driver);
		//        adminHomePage.navigateToLeftPanelOption(ShopifyDataUI.AdminPanelLeftNavigationOptions.ORDERS.text);
		//
		//        // Select order and fulfill the order
		//        adminOrdersPage = new ShopifyAdminOrdersPage(driver);
		//        adminOrdersPage.selectOrder(uiOrderNo);
		//        adminOrdersPage.markFulfillTheOrder();
		//
		//        // Verify order status & tax
		//        adminOrderDetailsPage = adminOrdersPage.openOrderDetail(uiOrderNo);
		//        assertTrue(adminOrderDetailsPage.verifyOrderStatus(uiOrderNo, ShopifyDataUI.ShopifyOrderAndPaymentStatus.FULFILLED.text));
		//        assertEquals(tax, adminOrderDetailsPage.getTaxFromUI());
		adminOrderDetailsPage = new ShopifyAdminOrderDetailsPage(driver);
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
		getOrderEndpoint = getProdOrderURL.replace("<<replace_order_id>>", apiOrderID);
		VertexLogger.log("Get order url: " + getOrderEndpoint);
		request = ShopifyApiUtils.buildGetRequestToFetchOrderPayload(
			getProdOrderURL.replace("<<replace_order_id>>", apiOrderID), decodedToken);
		response = ShopifyApiUtils.executeRequest(httpClient, request);
		assert response.body() != null;
		assertEquals(response.code(), ShopifyDataAPI.ResponseCodes.NOT_FOUND_404.value);
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
			ShopifyDataAPI.ShopifyAPIShopDomains.VTX_PROD_DOMAIN.text);
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
//			assertEquals(apiOrderID, orderFromConnectorResponse);
//			VertexLogger.log("Connector's response : " + orderFromConnectorResponse);
		}
		catch ( IOException io )
		{
			VertexLogger.log("errors in the response validation", VertexLogLevel.ERROR);
			fail("Failed to validate order id in connector's response");
		}
	}
}
