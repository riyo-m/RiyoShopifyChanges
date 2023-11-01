package com.vertex.quality.connectors.shopify.ui.tests.admin.Invoice;

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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class InvoiceColoradoStoreFront extends ShopifyUIBaseTest
{
	LoginPage quotationLoginPage;
	ShopifyStoreAuthenticationPage storeAuthenticationPage;
	ShopifyStoreHeaderPage storeHeaderPage;
	ShopifyStoreCartPage storeCartPage;
	StoreFrontPage quotationStoreFrontPage;
	PaymentStoreFront quotationPaymentPage;
	StoreFrontContactDetailsPage quotationStoreFrontContactDetailsPage;
	StoreFrontTaxAndPaymentPage quoationStoreFrontTaxAndPaymentPage;
	ShopifyStoreOrderConfirmationPage quoationShopifyStoreOrderConfirmationPage;
	OrderConfirmationPage qoutationOrderConfirmationPage;
	String uiOrderNo;
	ShopifyStoreLoginPage storeLoginPage;

	double adminTax;
	AdminOrderPage qoutationAdminOrderPage;
	InStoreFrontRefundPage quotationRefundPage;
	double refundAmount;
	double tax;

	Connection connection;
	PreparedStatement statement;
	ResultSet resultSet;

	OkHttpClient httpClient;
	String invoiceHMAC;
	Map<String, String> headers;
	RequestBody requestBody;
	Request request;
	Response response;

	String apiOrderID;
	String getOrderEndpoint;
	String orderPayload;
	String decodedToken;

	@Test(groups = { "shopify_smoke", "shopify_regression", "shopify_invoice" })
	public void shopifyAdminStoreFront( ) throws InterruptedException
	{
		//		loadShopifyStore();
		//		storeAuthenticationPage = new ShopifyStoreAuthenticationPage(driver);
		//		storeAuthenticationPage.enterIntoStoreByStorePassword();

		//		if (storeHeaderPage.getCartItemCount() > 0) {
		//			storeCartPage = storeHeaderPage.clickOnCartIcon();
		//			storeCartPage.deleteAllCartItems();
		//		}
		driver.get(ShopifyDataUI.StoreData.VTX_PRE_PROD_STORE.text);

		// Handle store authentication
		storeAuthenticationPage = new ShopifyStoreAuthenticationPage(driver);
		storeAuthenticationPage.enterIntoStoreByStorePassword();

		// Login to store
		//		storeHeaderPage = new ShopifyStoreHeaderPage(driver);
		//		storeHeaderPage.clickOnLoginAccount();
		//		storeLoginPage = new ShopifyStoreLoginPage(driver);
		//		storeLoginPage.loginToStore();
		//		storeHeaderPage.gotoStoreHomePage();

		quotationStoreFrontPage = new StoreFrontPage(driver);
		quotationStoreFrontPage.goToStore(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);

		quotationStoreFrontContactDetailsPage = new StoreFrontContactDetailsPage(driver);
		quotationStoreFrontContactDetailsPage.enterColoradoCAddressDetails();

		quotationPaymentPage = new PaymentStoreFront(driver);
		quotationPaymentPage.enterCreditCardDetails();

		quoationStoreFrontTaxAndPaymentPage = new StoreFrontTaxAndPaymentPage(driver);
		//		quoationStoreFrontTaxAndPaymentPage.calculatePercentTaxBeforeOrderPlace(13);
		tax = quoationStoreFrontTaxAndPaymentPage.getTaxFrmUIBeforeOrderPlace();
		System.out.println("Quotation Tax Before Fulfillment Tax :" + tax);

		quoationStoreFrontTaxAndPaymentPage.clickOrderPayNow();
		//		quoationShopifyStoreOrderConfirmationPage = quoationStoreFrontTaxAndPaymentPage.clickOrderPayNow();
		Thread.sleep(10000);
		qoutationOrderConfirmationPage = new OrderConfirmationPage(driver);
		qoutationOrderConfirmationPage.getOrderConFromUI();
		Thread.sleep(3000);
		qoutationOrderConfirmationPage.orderIdFromUi();

		VertexLogger.log(uiOrderNo);
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_PRE_PROD.text);
		//		Thread.sleep(5000);
		//
		//		loadShopifyAdmin();
		quotationLoginPage = new LoginPage(driver);
		quotationLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		qoutationAdminOrderPage = new AdminOrderPage(driver);
		qoutationAdminOrderPage.fulfillOrderInAdmin(uiOrderNo);

		adminTax = qoutationAdminOrderPage.getTaxFromAdmin();
		System.out.println("Invoice Tax After Fulfillment Tax :" + adminTax);
		assertEquals(tax, adminTax);

		qoutationAdminOrderPage.getFulfilledDialog();

		apiOrderID = qoutationOrderConfirmationPage.getOrderIdFromUIForAPICall(uiOrderNo);
		VertexLogger.log("Order ID from URL: " + apiOrderID);

		//		 Fetching Shop Access Token from the DB
		connection = ShopifyDBUtils.connectToDB();
		statement = ShopifyDBUtils.createQueryForAccessToken(connection, ShopifyDataDB.DbShopURLs.VTX_PRE_PROD.text);
		resultSet = ShopifyDBUtils.executeQuery(statement);
		ShopifyDBUtils.closeDBConnection(connection);

		// Decoding the encoded access token from the DB
		decodedToken = ShopifyHMACUtils.decodeValueInBase64(
			ShopifyDBUtils.getSingleDataFromResultSet(resultSet, ShopifyDataDB.DbColumns.SHOPIFY_ACCESS_TOKEN.text));
		VertexLogger.log("Shop access token: " + decodedToken);

		// Making a GET call to Shopify to fetch Order's payload
		httpClient = ShopifyApiUtils.buildHttpClient();
		getOrderEndpoint = getPreProdOrderURL.replace("<<replace_order_id>>", apiOrderID);
		VertexLogger.log("Get order url: " + getOrderEndpoint);
		request = ShopifyApiUtils.buildGetRequestToFetchOrderPayload(
			getPreProdOrderURL.replace("<<replace_order_id>>", apiOrderID), decodedToken);
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
			ShopifyDataAPI.ShopifyAPIShopDomains.VTX_PRE_PROD_DOMAIN.text);
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
}
