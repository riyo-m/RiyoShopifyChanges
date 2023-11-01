package com.vertex.quality.connectors.shopify.base;

import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;

/**
 * Shopify UI Base Test page that contains common variables & methods for Shopify UI Automation
 *
 * @author Shivam.Soni
 */
public class ShopifyUIBaseTest extends ShopifyAPIBaseTest {

    static String partnerDashboard = ShopifyDataUI.StoreData.SHOPIFY_PARTNER_DASHBOARD.text;
    static String storeURL = ShopifyDataUI.StoreData.VTX_QA_STORE.text;
    static String adminURL = ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text;

    /**
     * Loads shopify store
     *
     * @param url value of store url
     */
    public void loadShopifyStore(String url) {
        driver.get(url);
    }

    /**
     * Loads shopify store
     */
    public void loadShopifyStore() {
        loadShopifyStore(storeURL);
    }

    /**
     * Loads shopify admin panel
     *
     * @param url value of store url
     */
    public void loadShopifyAdmin(String url) {
        driver.get(url);
    }

    /**
     * Loads shopify admin panel
     */
    public void loadShopifyAdmin() {
        loadShopifyStore(adminURL);
    }

    /**
     * Loads partners dashboard
     */
    public void loadShopifyPartnersDashboard() {
        driver.get(partnerDashboard);
    }
}
