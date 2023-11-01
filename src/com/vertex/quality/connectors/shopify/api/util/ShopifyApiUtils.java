package com.vertex.quality.connectors.shopify.api.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.shopify.common.ShopifyDataAPI;
import okhttp3.*;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * API Utilities - contains helper methods to help in making API calls
 *
 * @author Shivam.Soni
 */
public class ShopifyApiUtils {

    /**
     * Builds HTTP client for API Call
     *
     * @return build HTTP client
     */
    public static OkHttpClient buildHttpClient() {
        return new OkHttpClient().newBuilder().build();
    }

    /**
     * Parses the request to desired media type
     *
     * @param mediaType value of media type
     * @return media type in desired format
     */
    public static MediaType parseToMediaType(String mediaType) {
        return MediaType.parse(mediaType);
    }

    /**
     * Parse the Request media type to application/json
     *
     * @return Media type in application/json
     */
    public static MediaType parseMediaToJson() {
        return parseToMediaType(ShopifyDataAPI.ShopifyMediaType.APPLICATION_JSON.text);
    }

    /**
     * Parse the Request media type to text/plain
     *
     * @return Media type in test/plain
     */
    public static MediaType parseMediaToText() {
        return parseToMediaType(ShopifyDataAPI.ShopifyMediaType.TEXT_PLAIN.text);
    }

    /**
     * Prepares the request body in the json format
     * Parameter's sequence: shop id, shop domain
     * Ex: ShopifyApiUtils.replaceDataInRequest("{"shop_id": <<shop_id>>, "shop_domain": <<shop_domain>>}", "12345", "vtxqa.myshopify.com");
     *
     * @param payload         payload
     * @param tobeReplaceData all the data which is to be replaced dynamically
     * @return json request body
     */
    public static String replaceDataInShopRedactRequest(String payload, String... tobeReplaceData) {
        if (tobeReplaceData.length != 2) {
            VertexLogger.log("Wrong parameters passed, kindly check JavaDoc", VertexLogLevel.ERROR);
            Assert.fail("Wrong parameters");
        }
        return payload.replace("<<shop_id>>", tobeReplaceData[0])
                .replace("<<shop_domain>>", tobeReplaceData[1]);
    }

    /**
     * Prepares the request body in the json format
     *
     * @return json request body
     */
    public static String replaceDataInShopRedactRequest() {
        return replaceDataInShopRedactRequest(ShopifyDataAPI.ShopifyGDPRData.SHOP_REDACT_PAYLOAD.text,
                ShopifyDataAPI.ShopifyAPIShopIDs.VTX_QA_ID.text, ShopifyDataAPI.ShopifyAPIShopDomains.VTX_QA_DOMAIN.text);
    }

    /**
     * Prepares the request body in the json format
     * Parameter's sequence: shop id, shop domain, customer_id, customer_email, customer_phone, data_request_id
     *
     * @param payload         payload
     * @param tobeReplaceData all the data which is to be replaced dynamically
     * @return json request body
     */
    public static String replaceDataInCustomerDataRequest(String payload, String... tobeReplaceData) {
        if (tobeReplaceData.length != 7) {
            VertexLogger.log("Wrong parameters passed, kindly check JavaDoc", VertexLogLevel.ERROR);
            Assert.fail("Wrong parameters");
        }
        return payload.replace("<<shop_id>>", tobeReplaceData[0])
                .replace("<<shop_domain>>", tobeReplaceData[1])
                .replace("<<orders_requested>>", tobeReplaceData[2])
                .replace("<<customer_id>>", tobeReplaceData[3])
                .replace("<<customer_email>>", tobeReplaceData[4])
                .replace("<<customer_phone>>", tobeReplaceData[5])
                .replace("<<data_request_id>>", tobeReplaceData[6]);
    }

    /**
     * Prepares the request body in the json format
     *
     * @return json request body
     */
    public static String replaceDataInCustomerDataRequest() {
        return replaceDataInCustomerDataRequest(ShopifyDataAPI.ShopifyGDPRData.CUSTOMER_DATA_REQUEST_PAYLOAD.text,
                ShopifyDataAPI.ShopifyAPIShopIDs.VTX_QA_ID.text, ShopifyDataAPI.ShopifyAPIShopDomains.VTX_QA_DOMAIN.text,
                ShopifyDataAPI.ShopifyAPICustomerDetail.VERTEX_API_USER_ORDER_ID.text,
                ShopifyDataAPI.ShopifyAPICustomerDetail.VERTEX_API_USER_ID.text,
                ShopifyDataAPI.ShopifyAPICustomerDetail.VERTEX_API_USER_EMAIL.text,
                ShopifyDataAPI.ShopifyAPICustomerDetail.VERTEX_API_USER_PHONE.text,
                ShopifyApiRandomValueGenerators.generateRandomNumbers(ShopifyDataAPI.ShopifyRandomValueLength.LONG.text));
    }

    /**
     * Prepares the request body in the json format
     * Parameter's sequence: shop id, shop domain, customer_id, customer_email, customer_phone, data_request_id
     *
     * @param payload         payload
     * @param tobeReplaceData all the data which is to be replaced dynamically
     * @return json request body
     */
    public static String replaceDataInCustomerRedactRequest(String payload, String... tobeReplaceData) {
        if (tobeReplaceData.length != 7) {
            VertexLogger.log("Wrong parameters passed, kindly check JavaDoc", VertexLogLevel.ERROR);
            Assert.fail("Wrong parameters");
        }
        return payload.replace("<<shop_id>>", tobeReplaceData[0])
                .replace("<<shop_domain>>", tobeReplaceData[1])
                .replace("<<customer_id>>", tobeReplaceData[2])
                .replace("<<customer_email>>", tobeReplaceData[3])
                .replace("<<customer_phone>>", tobeReplaceData[4])
                .replace("<<data_request_id>>", tobeReplaceData[5])
                .replace("<<orders_to_redact>>", tobeReplaceData[6]);
    }

    /**
     * Prepares the request body in the json format
     *
     * @return json request body
     */
    public static String replaceDataInCustomerRedactRequest() {
        return replaceDataInCustomerRedactRequest(ShopifyDataAPI.ShopifyGDPRData.CUSTOMER_REDACT_PAYLOAD.text,
                ShopifyDataAPI.ShopifyAPIShopIDs.VTX_QA_ID.text, ShopifyDataAPI.ShopifyAPIShopDomains.VTX_QA_DOMAIN.text,
                ShopifyDataAPI.ShopifyAPICustomerDetail.VERTEX_API_USER_ID.text,
                ShopifyDataAPI.ShopifyAPICustomerDetail.VERTEX_API_USER_EMAIL.text,
                ShopifyDataAPI.ShopifyAPICustomerDetail.VERTEX_API_USER_PHONE.text,
                ShopifyApiRandomValueGenerators.generateRandomNumbers(ShopifyDataAPI.ShopifyRandomValueLength.LONG.text),
                ShopifyDataAPI.ShopifyAPICustomerDetail.VERTEX_API_USER_ORDER_ID.text);
    }

    /**
     * Prepares the request body in the json format
     * Parameter's sequence: shop id, shop domain
     * Ex: ShopifyApiUtils.createRequestBodyInJson("12345", "vtxqa.myshopify.com");
     *
     * @param payload payload
     * @return json request body
     */
    public static RequestBody createRequestBodyInJson(String payload) {
        return RequestBody.create(parseMediaToJson(), payload);
    }

    /**
     * Prepares the request body in the text format
     *
     * @param payload payload
     * @return text request body
     */
    public static RequestBody createRequestBodyInText(String payload) {
        return RequestBody.create(parseMediaToText(), payload);
    }

    /**
     * Build final request for Shop redact
     *
     * @param url  API endpoint
     * @param body request body
     * @param hmac hmac key value
     * @return built request
     */
    public static Request buildPostRequestForGDPRCalls(String url, RequestBody body, String hmac) {
        return new Request.Builder()
                .url(url)
                .method(ShopifyDataAPI.APIMethods.POST.text, body)
                .addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.CONTENT_TYPE.text, ShopifyDataAPI.ShopifyMediaType.APPLICATION_JSON.text)
                .addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.HMAC_SHA256.text, hmac)
                .build();
    }

    /**
     * Build final request for Shop redact
     *
     * @param url         API endpoint
     * @param body        request body
     * @param headersData value of all the headers
     * @return built request
     */
    public static Request buildPostRequestForInvoiceCalls(String url, Map<String, String> headersData, RequestBody body) {
        return new Request.Builder()
                .url(url)
                .method(ShopifyDataAPI.APIMethods.POST.text, body)
                .addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.CONTENT_TYPE.text, ShopifyDataAPI.ShopifyMediaType.APPLICATION_JSON.text)
                .addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.HMAC_SHA256.text, headersData.get(ShopifyDataAPI.ShopifyAPIHeadersNames.HMAC_SHA256.text))
                .addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_API_VERSION.text, ShopifyDataAPI.ShopifyVersions.V_2023_7.text)
                .addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.ACCESS_TOKEN.text, headersData.get(ShopifyDataAPI.ShopifyAPIHeadersNames.ACCESS_TOKEN.text))
                .addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_SHOP_DOMAIN.text, headersData.get(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_SHOP_DOMAIN.text))
                .addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_ORDER_ID.text, headersData.get(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_ORDER_ID.text))
                .build();
    }

    /**
     * Build final request for Shop redact
     *
     * @param url         API endpoint
     * @param body        request body
     * @param headersData value of all the headers
     * @return built request
     */
    public static Request buildPostRequestForRefundsAdjustmentCalls(String url, Map<String, String> headersData, RequestBody body) {
        return new Request.Builder()
                .url(url)
                .method(ShopifyDataAPI.APIMethods.POST.text, body)
                .addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.CONTENT_TYPE.text, ShopifyDataAPI.ShopifyMediaType.APPLICATION_JSON.text)
                .addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.HMAC_SHA256.text, headersData.get(ShopifyDataAPI.ShopifyAPIHeadersNames.HMAC_SHA256.text))
                .addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_API_VERSION.text, ShopifyDataAPI.ShopifyVersions.V_2023_7.text)
                .addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.ACCESS_TOKEN.text, headersData.get(ShopifyDataAPI.ShopifyAPIHeadersNames.ACCESS_TOKEN.text))
                .addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_SHOP_DOMAIN.text, headersData.get(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_SHOP_DOMAIN.text))
                .build();
    }

    /**
     * Builds final request to Get Order's json payload
     *
     * @param url          API endpoint
     * @param decodedToken decoded access token
     * @return built request
     */
    public static Request buildGetRequestToFetchOrderPayload(String url, String decodedToken) {
        return new Request.Builder()
                .url(url).get()
                .addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.ACCESS_TOKEN.text, decodedToken)
                .addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.COOKIE.text,
                        ShopifyDataAPI.ShopifyAPICookieData.GET_ORDER_COOKIE_VALUE.text)
                .build();

    }

    /**
     * Builds final request to Get Order's json payload
     *
     * @param url          API endpoint
     * @return built request
     */
    public static Request buildGetRequestToCheckHealth(String url) {
        return new Request.Builder()
                .url(url).get()
                .build();
    }

    /**
     * Executes the API call
     *
     * @param httpClient initialized object on OkHttpClient
     * @param request    initialized object on Request
     * @return API response
     */
    public static Response executeRequest(OkHttpClient httpClient, Request request) {
        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
        } catch (IOException io) {
            VertexLogger.log(io.getMessage(), VertexLogLevel.ERROR);
        }
        return response;
    }

	public static String getUTCDateTimeStamp(){
		Date date = new Date(System.currentTimeMillis());

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormat.format(date);

	}


	public static RequestBody createRequestBodyFromJsonFile(String payload) throws IOException
	{
		return RequestBody.create(parseMediaToJson(), payload);
	}

	public static String ConvertJsonFileToJsonString() throws IOException
	{
		File f = new File(System.getProperty("user.dir")+"/src/com/vertex/quality/connectors/shopify/api/payload/Quotation_Bulk_Qty.json");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.readValue(f, ObjectNode.class);
		JsonNode rootNode = objectMapper.readTree(f);
		ObjectNode objectNode = (ObjectNode) rootNode;
		objectNode.put("idempotent_key", ShopifyApiRandomValueGenerators.generateRandomAlphaNumericCharacters());
		objectNode.with("request").put("datetime_created_utc", ShopifyApiUtils.getUTCDateTimeStamp());
		objectMapper.writeValue(f, rootNode);
		String jsonContent = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/src/com/vertex/quality/connectors/shopify/api/payload/Quotation_Bulk_Qty.json")));
		return jsonContent;
	}

	public static Request buildPostRequestForQuotationCalls(String url, RequestBody body, String hmac)
		throws IOException
	{
		return new Request.Builder()
			.url("https://shopify-stage-primary.cst-stage.vtxdev.net/calculate_taxes")



			.addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.CONTENT_TYPE.text, ShopifyDataAPI.ShopifyMediaType.APPLICATION_JSON.text)
			.addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.HMAC_SHA256.text, hmac)
			.addHeader(ShopifyDataAPI.ShopifyAPIShopDomains.VTX_QA_DOMAIN.text, "vtxqa.myshopify.com")
			.addHeader(ShopifyDataAPI.ShopifyAPIHeadersNames.SHOPIFY_REQUEST_ID.text, "requestid")
			.method(ShopifyDataAPI.APIMethods.POST.text, body)
			.build();
	}


	public static String replaceDataInQuotationRequest(String payload, String... tobeReplaceData) {
		return payload.replace("<<idempotent_Key>>", tobeReplaceData[0])
					  .replace("<<date_time_created>>", tobeReplaceData[1]);
	}


}
