package com.vertex.quality.connectors.shopify.common;

/**
 * Data Class that contains multiple enums for Shopify API automation
 *
 * @author Shivam.Soni
 */
public class ShopifyDataAPI {

    /**
     * API Response codes used in Shopify
     */
    public enum APIMethods {
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        PATCH("PATCH"),
        DELETE("DELETE");

        public String text;

        APIMethods(String text) {
            this.text = text;
        }
    }

    /**
     * API Response codes used in Shopify
     */
    public enum ResponseCodes {
        OK_200(200),
        BAD_DATA_400(400),
        UNAUTHORIZED_401(401),
        NOT_FOUND_404(404),
        INTERNAL_SERVER_ERROR_500(500);

        public int value;

        ResponseCodes(int value) {
            this.value = value;
        }
    }

    /**
     * Shopify API version
     */
    public enum ShopifyVersions {
        // Need to use stable version
        // Reference: https://shopify.slack.com/archives/C04P0CN0Q93/p1688749799553339
        V_2023_7("2023-07");

        public String text;

        ShopifyVersions(String text) {
            this.text = text;
        }
    }

    /**
     * Shopify API URLs
     */
    public enum ShopifyEndpoints {
        PERF_CST_STAGE_HOST("https://shopify-perf.cst-stage.vtxdev.net"),
        CST_STAGE_HOST("https://shopify.cst-stage.vtxdev.net"),
        STAGE_PRIMARY("https://shopify-stage-primary.cst-stage.vtxdev.net"),
        STAGE_SECONDARY("https://shopify-stage-secondary.cst-stage.vtxdev.net"),
        QUOTATION("/calculate_taxes"),
        INVOICE("/api/orders/invoice"),
        REFUNDS(INVOICE.text + "/refunds"),
        HEALTH_CHECK("/health"),
        GDPR_SHOP_REDACT("/gdpr/shop/redact"),
        GDPR_CUSTOMER_REDACT("/gdpr/customers/redact"),
        GDPR_CUSTOMER_DATA("/gdpr/customers/data_request"),

		QUOTATION_BULK_QTY("/calculate_taxes");


        public String text;

        ShopifyEndpoints(String text) {
            this.text = text;
        }
    }

    /**
     * Shopify API Shop/ Store domains
     */
    public enum ShopifyAPIShopDomains {
        VTX_QA_DOMAIN("vtxqa.myshopify.com"),
		VTX_PROD_DOMAIN("prodtestvettexstore.myshopify.com"),
		VTX_PRE_PROD_DOMAIN("vertexpreprodstore.myshopify.com"),
        VERTEX_DEV_STORE_DOMAIN("vertexdevstore.myshopify.com"),
        VERTEX_EKS_DOMAIN("vertex-eks.myshopify.com"),
        VERTEX_US_STORE_DOMAIN("vertexusstore.myshopify.com");

        public String text;

        ShopifyAPIShopDomains(String text) {
            this.text = text;
        }
    }

    /**
     * Shopify API Customer's details
     */
    public enum ShopifyAPICustomerDetail {
        VERTEX_API_USER_EMAIL("api.vertexshopify.com"),
        VERTEX_API_USER_PWD("Test@123"),
        VERTEX_API_USER_ID("7104535101737"),
        VERTEX_API_USER_ORDER_ID("5418320757033"),
        VERTEX_API_USER_PHONE("+1 (123) 456-7890");

        public String text;

        ShopifyAPICustomerDetail(String text) {
            this.text = text;
        }
    }

    /**
     * Shopify API Shop/ Store IDs
     */
    public enum ShopifyAPIShopIDs {
        VTX_QA_ID("75712626985"),
        VERTEX_DEV_STORE_DOMAIN("72394244369"),
        VERTEX_EKS_ID("73794126106"),
        VERTEX_US_STORE_ID("75423088936");

        public String text;

        ShopifyAPIShopIDs(String text) {
            this.text = text;
        }
    }

    /**
     * Shopify API Connection details
     */
    public enum ShopifyConnectionDetails {
        STAGE_APP_CLIENT_ID("2f6476dd4e202ceb22c678daaad59b0c"),
        STAGE_APP_CLIENT_SECRET("e07638cc684e6ec3594e9ec8c488c648"),
		//e07638cc684e6ec3594e9ec8c488c648
        PRE_PROD_APP_CLIENT_ID("d595446bcfb28527436d9238266b82c7"),
        PRE_PROD_APP_CLIENT_SECRET("078fe59479f908b20c91363139c53a21");
        public String text;

        ShopifyConnectionDetails(String text) {
            this.text = text;
        }
    }

    /**
     * Shopify API Media Types
     */
    public enum ShopifyMediaType {
        APPLICATION_JSON("application/json"),
        TEXT_PLAIN("text/plain");

        public String text;

        ShopifyMediaType(String text) {
            this.text = text;
        }
    }

    /**
     * Shopify API's cookie values
     */
    public enum ShopifyAPICookieData {
        GET_ORDER_COOKIE_VALUE("_master_udr=eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaEpJaWs0TWpNd01UWTFOUzFtTmpsbExUUTVOVGN0T0dabU55MWlORGs0WldGaU5qbGxOVFFHT2daRlJnPT0iLCJleHAiOiIyMDI1LTA1LTEyVDE5OjUyOjUwLjQ0MloiLCJwdXIiOiJjb29raWUuX21hc3Rlcl91ZHIifX0%3D--c7765b794731870a7a5f94ceca9f296947947e36; _secure_admin_session_id=fe12e837ac9d9b301b14eecc5d2a8192; _secure_admin_session_id_csrf=fe12e837ac9d9b301b14eecc5d2a8192");

        public String text;

        ShopifyAPICookieData(String text) {
            this.text = text;
        }
    }

    /**
     * Shopify API header's names
     */
    public enum ShopifyAPIHeadersNames {
        CONTENT_TYPE("Content-Type"),
        HMAC_SHA256("X-Shopify-Hmac-SHA256"),
        ACCESS_TOKEN("X-Shopify-Access-Token"),
        COOKIE("Cookie"),
        CONNECTION("Connection"),
        KEEP_ALIVE("keep-alive"),
        SHOPIFY_API_VERSION("x-shopify-api-version"),
        SHOPIFY_SHOP_DOMAIN("x-shopify-shop-domain"),
        SHOPIFY_ORDER_ID("x-shopify-order-id"),
		SHOPIFY_REQUEST_ID("X-Shopify-Request-Id");
        public String text;

        ShopifyAPIHeadersNames(String text) {
            this.text = text;
        }
    }

    /**
     * Shopify API Data
     */
    public enum ShopifyGDPRData {
        CUSTOMER_DATA_REQUEST_PAYLOAD("{\n" +
                "  \"shop_id\": <<shop_id>>,\n" +
                "  \"shop_domain\": \"<<shop_domain>>\",\n" +
                "  \"orders_requested\": [<<orders_requested>>],\n" +
                "  \"customer\": {\n" +
                "    \"id\": <<customer_id>>,\n" +
                "    \"email\": \"<<customer_email>>\",\n" +
                "    \"phone\": \"<<customer_phone>>\"\n" +
                "  },\n" +
                "  \"data_request\": {\n" +
                "    \"id\": <<data_request_id>>\n" +
                "  }\n" +
                "}"),
        CUSTOMER_REDACT_PAYLOAD("{\n" +
                "  \"shop_id\": <<shop_id>>,\n" +
                "  \"shop_domain\": \"<<shop_domain>>\",\n" +
                "  \"customer\": {\n" +
                "    \"id\": <<customer_id>>,\n" +
                "    \"email\": \"<<customer_email>>\",\n" +
                "    \"phone\": \"<<customer_phone>>\"\n" +
                "  },\n" +
                "  \"orders_to_redact\": [<<orders_to_redact>>]" +
                "}"),
        SHOP_REDACT_PAYLOAD("{\"shop_id\":<<shop_id>>,\"shop_domain\":\"<<shop_domain>>\"}"),

		QUOTATION_BULK_QTY_PAYLOAD("{\"idempotent_key\":\"<<idempotent_Key>>\",\"request\":{\"tax_included\":false,\"datetime_created_utc\":\"<<date_time_created>>\",\"currency_code\":\"CAD\"},\"cart\":{\"buyer_identity\":{\"tax_exempt\":false,\"customer\":{\"id\":\"6919198867738\"},\"purchasing_company\":null},\"billing_address\":{\"address1\":\"1270 York Road\",\"city\":\"Gettysburg\",\"zip\":\"17325\",\"address2\":null,\"country_code\":\"US\",\"province_code\":\"PA\"},\"delivery_groups\":[{\"id\":\"05b63f9e002a970b7d05c851aab2d30e\",\"selected_delivery_option\":{\"subtotal_amount\":{\"amount\":\"1.0\",\"currency_code\":\"CAD\"},\"total_amount\":{\"amount\":\"1.0\",\"currency_code\":\"CAD\"},\"delivery_method_type\":\"SHIPPING\"},\"origin_address\":{\"address1\":\"123 Sweet Street\",\"city\":\"Carmangay\",\"zip\":\"T0L 0N0\",\"address2\":null,\"country_code\":\"CA\",\"province_code\":\"AB\"},\"delivery_address\":{\"address1\":\"1270 York Road\",\"city\":\"Gettysburg\",\"zip\":\"17325\",\"address2\":null,\"country_code\":\"US\",\"province_code\":\"PA\"},\"cart_lines\":[{\"id\":\"ccebfdf4e2da4ee8c663612ef657ed09\",\"quantity\":5,\"cost\":{\"amount_per_quantity\":{\"amount\":\"44.0\",\"currency_code\":\"CAD\"},\"subtotal_amount\":{\"amount\":\"220.0\",\"currency_code\":\"CAD\"},\"total_amount\":{\"amount\":\"220.0\",\"currency_code\":\"CAD\"}},\"discount_allocations\":[],\"merchandise\":{\"id\":\"44781448593690\",\"sku\":\"sku-hosted-1\",\"product\":{\"id\":\"44781448593690\",\"handle\":\"The 3p Fulfilled Snowboard - Default Title\",\"is_gift_card\":false,\"metafields\":[]},\"requires_shipping\":true,\"tax_exempt\":false,\"metafields\":[],\"__type\":\"PRODUCT_VARIANT\"}}]}]},\"shop\":{\"billing_address\":{\"address1\":null,\"city\":null,\"zip\":null,\"address2\":null,\"country_code\":\"IN\",\"province_code\":null}}}");
        public String text;

        ShopifyGDPRData(String text) {
            this.text = text;
        }
    }

    /**
     * Shopify API Messages
     */
    public enum ShopifyAPIMessages {
        GDPR_SUCCESS("Successfully logged request"),
        GDPR_FAIL("Database operation failed :"),
        BAD_INVOICE_DATA("Bad Invoice Request :200 Not Found: \"{\"messages\":\"Not Found\"}\""),
        UNFULFILLED_ORDER_MSG("{\"idempotent_key\":null,\"currency\":null,\"delivery_group_taxes\":[],\"taxes\":[],\"errors\":[{\"code\":\"BAD_DATA\",\"message\":\"Index 0 out of bounds for length 0\"}]}"),
        HMAC_VALIDATION_FAIL("HMAC Validation Failed..");

        public String text;

        ShopifyAPIMessages(String text) {
            this.text = text;
        }
    }

    /**
     * Shopify API Messages
     */
    public enum ShopifyRandomValueLength {
        SMALL("small"),
        MEDIUM("medium"),
        LONG("long");

        public String text;

        ShopifyRandomValueLength(String text) {
            this.text = text;
        }
    }

	public enum ShopifyQuotationData{
				QUOTATION_TAX_INCLUDED("false"),
			CUSTOMER_ID("6919198867738"),
			VERTEX_API_USER_ID("7104535101737"),
			VERTEX_API_USER_ORDER_ID("5418320757033"),
			VERTEX_API_USER_PHONE("+1 (123) 456-7890");

			public String text;

		ShopifyQuotationData(String text) {
				this.text = text;
			}
		}

	}

