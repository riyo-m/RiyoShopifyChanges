package com.vertex.quality.connectors.shopify.api.test.Quotation;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.shopify.api.util.ShopifyApiRandomValueGenerators;
import com.vertex.quality.connectors.shopify.api.util.ShopifyApiUtils;
import com.vertex.quality.connectors.shopify.api.util.ShopifyHMACUtils;
import com.vertex.quality.connectors.shopify.base.ShopifyAPIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataAPI;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.testng.annotations.Test;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class ShopifyQuotationAPITest extends ShopifyAPIBaseTest
{
	OkHttpClient httpClient;
	RequestBody requestBody;
	Request request;
	Response response;

	String gdprHMAC;
	String requestData;

	HttpEntity entity;

	String idempotentKey;

	String dateTimeCreatedUTC;

	/**
	 *
	 */
	@Test(groups = {"shopify_regression", "shopify_quotation"})
	public void QuotationBulkQtyRequest() throws IOException
	{
		idempotentKey= ShopifyApiRandomValueGenerators.generateRandomAlphaNumericCharacters();
		dateTimeCreatedUTC = ShopifyApiUtils.getUTCDateTimeStamp();
		requestData = ShopifyApiUtils.replaceDataInQuotationRequest(ShopifyDataAPI.ShopifyGDPRData.QUOTATION_BULK_QTY_PAYLOAD.text,
			idempotentKey, dateTimeCreatedUTC);

		gdprHMAC = ShopifyHMACUtils.generateBase64HMACQuotation(requestData);
//		gdprHMAC = "KZo84V7IDnOxGz6wtk4wNB8makVSPM0KWqoxBdreGrQ=";
		httpClient = ShopifyApiUtils.buildHttpClient();
		requestBody = ShopifyApiUtils.createRequestBodyFromJsonFile(requestData);
		request = ShopifyApiUtils.buildPostRequestForQuotationCalls(quotationBulkQty, requestBody, gdprHMAC);
		response = ShopifyApiUtils.executeRequest(httpClient, request);
		VertexLogger.log(response.body().string());
		assert response.body() != null;
		assertEquals(response.code(), ShopifyDataAPI.ResponseCodes.OK_200.value);

	}



}
