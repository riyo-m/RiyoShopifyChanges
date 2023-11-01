package com.vertex.quality.connectors.shopify.base;

import com.vertex.quality.connectors.shopify.api.util.ShopifyDBUtils;
import com.vertex.quality.connectors.shopify.common.ShopifyDataAPI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Shopify API Base Test page that contains common variables & methods for Shopify API Automation
 *
 * @author Shivam.Soni
 */
public class ShopifyAPIBaseTest extends ShopifyBaseTest {

    public static final String baseURL = ShopifyDataAPI.ShopifyEndpoints.PERF_CST_STAGE_HOST.text;
    public static final String quotationURL = baseURL + ShopifyDataAPI.ShopifyEndpoints.QUOTATION.text;
    public static final String getOrderURL = "https://" + ShopifyDataAPI.ShopifyAPIShopDomains.VTX_QA_DOMAIN.text + "/admin/api/" +
            ShopifyDataAPI.ShopifyVersions.V_2023_7.text + "/orders/<<replace_order_id>>.json";
	public static final String getPreProdOrderURL = "https://" + ShopifyDataAPI.ShopifyAPIShopDomains.VTX_PRE_PROD_DOMAIN.text + "/admin/api/" +
											 ShopifyDataAPI.ShopifyVersions.V_2023_7.text + "/orders/<<replace_order_id>>.json";
	public static final String getProdOrderURL = "https://" + ShopifyDataAPI.ShopifyAPIShopDomains.VTX_PROD_DOMAIN.text + "/admin/api/" +
											 ShopifyDataAPI.ShopifyVersions.V_2023_7.text + "/orders/<<replace_order_id>>.json";


	public static final String invoiceURL = baseURL + ShopifyDataAPI.ShopifyEndpoints.INVOICE.text;
    public static final String refundsURL = baseURL + ShopifyDataAPI.ShopifyEndpoints.REFUNDS.text;
    public static final String shopRedactURL = baseURL + ShopifyDataAPI.ShopifyEndpoints.GDPR_SHOP_REDACT.text;
    public static final String customerRedactURL = baseURL + ShopifyDataAPI.ShopifyEndpoints.GDPR_CUSTOMER_REDACT.text;
    public static final String customerDataRequestURL = baseURL + ShopifyDataAPI.ShopifyEndpoints.GDPR_CUSTOMER_DATA.text;

	public static final String quotationBulkQty = baseURL + ShopifyDataAPI.ShopifyEndpoints.QUOTATION_BULK_QTY.text;

	/**
     * Checks in the DB if the app is installed to the store or not?
     *
     * @param storeName store name against which app entry needs to be checked
     * @return true or false based on condition match
     */
    public boolean checkIfAppInstalledOrNot(String storeName) {
        boolean installed = false;
        // Fetching Shop Access Token from the DB
        Connection connection = ShopifyDBUtils.connectToDB();
        PreparedStatement statement = ShopifyDBUtils.createQueryForToCheckAppEntryAgainstStoreInDB(connection, storeName.replaceAll("/", "")
                .replaceAll(":", "").replace("https", "").replace("http", ""));
        ResultSet resultSet = ShopifyDBUtils.executeQuery(statement);
        if (resultSet != null) {
            installed = true;
        }
        ShopifyDBUtils.closeDBConnection(connection);
        return installed;
    }
}
