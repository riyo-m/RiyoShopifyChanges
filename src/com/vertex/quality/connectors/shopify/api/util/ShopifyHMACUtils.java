package com.vertex.quality.connectors.shopify.api.util;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.shopify.common.ShopifyDataAPI;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Generates HMAC codes in different formats against the String value
 *
 * @author Shivam.Soni
 */
public class ShopifyHMACUtils {

    final static String vertexStageAppSecret = ShopifyDataAPI.ShopifyConnectionDetails.STAGE_APP_CLIENT_SECRET.text;
    final static String preProdAppSecret = ShopifyDataAPI.ShopifyConnectionDetails.PRE_PROD_APP_CLIENT_SECRET.text;

    /**
     * Generates HMAC against json payload in Base64 encoding format
     *
     * @param clientSecret value of client secret to be passed
     * @param jsonPayLoad  value of json payload
     * @return Base64 HMAC
     */
    public static String generateBase64HMAC(String clientSecret, String jsonPayLoad) {
        String hash = "";
        try {
            VertexLogger.log("json payload: " + jsonPayLoad);
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(clientSecret.getBytes(), "HmacSHA256");
            sha256HMAC.init(secret_key);
            hash = Base64.encodeBase64String(sha256HMAC.doFinal(jsonPayLoad.getBytes()));
            VertexLogger.log("Generated Base64 HMAC: " + hash);
        } catch (Exception e) {
            VertexLogger.log(e.getMessage(), VertexLogLevel.ERROR);
        }
        return hash;
    }

    /**
     * Generates HMAC against json payload in Base64 encoding format
     *
     * @param jsonPayLoad value of json payload
     * @return Base64 HMAC
     */
    public static String generateBase64HMAC(String jsonPayLoad) {
        return generateBase64HMAC(preProdAppSecret, jsonPayLoad);
    }

    /**
     * Decodes the Shopify shop access token which is available in the DB as encoded
     *
     * @param encodedValue Encoded value from the DB
     * @return decoded access token for the Shop
     */
    public static String decodeValueInBase64(String encodedValue) {
        return new String(Base64.decodeBase64(encodedValue));
    }

	public static String generateBase64HMACQuotation(String jsonPayLoad) {
		return generateBase64HMAC(vertexStageAppSecret, jsonPayLoad);
	}
}
