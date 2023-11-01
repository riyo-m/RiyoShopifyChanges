package com.vertex.quality.connectors.shopify.common;

/**
 * Data Class that contains multiple enums for Shopify backend/ DB verification automation
 *
 * @author Shivam.Soni
 */
public class ShopifyDataDB {

    /**
     * Shopify Database server URLs & Driver URLs
     */
    public enum DbUrls {
        PRIMARY_NODE("jdbc:postgresql://connectors-mercury-stage-primary.cluster-clephakrkyme.us-east-2.rds.amazonaws.com:5432/shopify-stage"),
        SECONDARY_NODE("jdbc:postgresql://connectors-mercury-stage-secondary.cluster-ro-cmbyhrfl2j8q.us-west-2.rds.amazonaws.com:5432/shopify-stage");

        public String text;

        DbUrls(String text) {
            this.text = text;
        }
    }

    /**
     * Shopify DB Connection details
     */
    public enum DbCredentials {
        USERNAME("shopify-stage"),
        PASSWORD("shopify-stage-c0nnect0rsDbPwSuffix");

        public String text;

        DbCredentials(String text) {
            this.text = text;
        }
    }

    /**
     * Shopify DB Queries
     */
    public enum DbQueries {
        CHECK_APP_INSTALLED("select *  from shopify_config sc where shop_url = '<<text_replace>>';"),
        GET_CLIENT_ID("select client_id from shopify_config sc where shop_url = '<<text_replace>>';"),
        GET_SHOP_ACCESS_TOKEN("select shopify_access_token from shopify_config sc where shop_url = '<<text_replace>>';");

        public String text;

        DbQueries(String text) {
            this.text = text;
        }
    }

    /**
     * Shopify DB Column names
     */
    public enum DbColumns {
        APP_CLIENT_ID("client_id"),
        SHOPIFY_ACCESS_TOKEN("shopify_access_token");

        public String text;

        DbColumns(String text) {
            this.text = text;
        }
    }

    /**
     * Shopify DB Shop URLs
     */
    public enum DbShopURLs {
        VTX_QA("vtxqa.myshopify.com"),
		VTX_PROD("prodtestvettexstore.myshopify.com"),
		VTX_PRE_PROD("vertexpreprodstore.myshopify.com");

        public String text;

        DbShopURLs(String text) {
            this.text = text;
        }
    }
}
