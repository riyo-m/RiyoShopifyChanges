package com.vertex.quality.connectors.sitecorexc.common.enums;

import lombok.Getter;

/**
 * Data for SiteCoreXC
 *
 * @author Shivam.Soni
 */
@Getter
public enum SiteCoreXCData {

    COMMERCE_URL_10("https://sc1010.sc/sitecore/"),
    COMMERCE_URL_30("https://sc1030.sc/sitecore/"),
    COMMERCE_LOGIN_USERNAME("admin"),
    COMMERCE_LOGIN_PASSWORD("admin"),

    STORE_LOGIN_URL_10("https://sc1010.sc/login"),
    STORE_LOGIN_URL_30("https://sc1030.sc/login"),
    STORE_LOGIN_USERNAME("test123@test.com"),
    STORE_LOGIN_PASSWORD("test123"),

    COMMERCE_APP_CONTENT_EDITOR("Content Editor"),

    CONTENT_EDITOR_HOME("Home"),

    LEFT_PANEL_sitecore("sitecore"),
    LEFT_PANEL_CONTENT("Content"),
    LEFT_PANEL_SITECORE("Sitecore"),
    LEFT_PANEL_STOREFRONT("Storefront"),
    LEFT_PANEL_SETTINGS("Settings"),
    LEFT_PANEL_COMMERCE("Commerce"),
    LEFT_PANEL_VERTEX("Vertex"),
    LEFT_PANEL_WAREHOUSE_CONFIG("Warehouse Configuration"),

    WAREHOUSE_NAME_US("US"),
    WAREHOUSE_NAME_CA("CA"),

    SMART_WIFI_BUNDLE("Smart WiFi Bundle"),
    SMART_WIFI_DYNAMIC_BUNDLE("Smart WiFi Dynamic Bundle"),
    HABITAT_TOUCHSCREEN_THERMOSTAT("Habitat Sentinel Touchscreen Thermostat"),
    HABITAT_FLIP_450("Habitat Flip 450"),
    REFRIGERATOR_FLIP_PHONE_BUNDLE("Refrigerator & Flip Phone Bundle"),
    COMMODITY_CODE_PRODUCT("6042276"),
    FLEX_FIELD_PRODUCT("6042260"),

    DEFAULT_CUSTOMER_NAME("Automation Tester"),

    DELIVERY_SHIP_ITEMS("Ship items"),

    NEXT_DAY_AIR_SHIP_OPTION("Next Day Air"),
    GROUND_SHIP_OPTION("Ground"),
    STANDARD_SHIP_OPTION("Standard"),
    STANDARD_OVERNIGHT("Standard Overnight"),

    CONTENT_EDITOR_PUBLISH_ITEM("Publish Item"),

    TEN_DOLLAR_ORDER("10DollarOrder"),
    TEN_DOLLAR_ITEM("10DollarItem"),
    TEN_PERCENT_ORDER("10PercentOrder"),
    FREE_SHIPPING("FreeShipping"),
    FIVE_PERCENT_ORDER("5PercentOrder"),
    FIVE_PERCENT_ITEM("5PercentItem"),
    FIVE_DOLLAR_ORDER("5DollarOrder"),

    QUANTITY_10("10"),
    QUANTITY_5("5"),

    EXPECTED_PAGE_TITLE("Vertex Service - Health Check Page");

    public String data;

    SiteCoreXCData(String data) {
        this.data = data;
    }
}