package com.vertex.quality.connectors.orocommerce.enums;

import lombok.Getter;

/**
 * class contains all data related to Oro.
 *
 * @author Vivek kumar.
 */
@Getter
public enum OroTestData {

    WRONG_URL("http://badurl.com"),
    VALID_URL("https://oseries9-final.vertexconnectors.com"),
    PRODUCT_NAME("Physician’s 5-Pocket Lab Coat 5GN30"),
    PRODUCT_NAME1("Men’s Basic V-neck, 1-Pocket Light Blue Scrub Top 9OK21"),
    PRODUCT_NAME2("Credit Card Pin Pad Reader 1AB92"),
    PRODUCT_NAME3("Steel Storage Shelves, 65 x 55 in. 5BM69"),
    PRODUCT_NAME4("Pricing Labeler XS56"),
    US_EU_TAX("$0.00"),
    EU_US_TAX("$4.45"),
    JM_JM_TAX("$7.02"),
    ADMIN_URL("http://oro41.vertexconnectors.com/admin/user/login"),
    ADMIN_URL_VERSION_4_2("http://oro42.vertexconnectors.com/admin/user/login"),
    ADMIN_URL_VERSION_5("http://oro5.vertexconnectors.com/admin/user/login"),
    ADMIN_USERNAME("admin"),
    ADMIN_PASSWORD("admin@123"),
    STOREFRONT_URL("http://oro41.vertexconnectors.com/"),
    STOREFRONT_URL_VERSION_4_2("http://oro42.vertexconnectors.com/"),
    STOREFRONT_URL_VERSION_5("http://oro5.vertexconnectors.com/"),
    VAT_REG_NO("FR1234"),
    CAN_US_TAX("$2.81"),
    ACCRUAL_TAX("$2.81"),
    BERLIN_BERLIN_TAX("$8.89"),
    MARSEILLES_BERLIN_TAX("$9.36"),
    FR_EU_SUP_TAX("$7.36"),
    FR_EU_CUS_TAX("$7.36"),
    US_CAN_TAX("$3.28"),
    STATE_TAX("$2.34"),
    NJ_TAX("$3.10"),
    ZERO_TAX("$0.00"),
    UNREGISTERED_CUSTOMER_TAX("$4.45"),
    CANBC_CANON_TAX("$1.30"),
    CANNB_CANNB_TAX("$1.50"),
    CANBC_CANQC_TAX("$1.50"),
    CANQC_CANBC_TAX("$3.78"),
    DIFFERENT_BILLING_SHIPPING_TAX("$4.45"),
    US_US_BULK_ORDER_TAX("$70.52"),
    DISCOUNT_BULK_ORDER_TAX("$32.02"),
    DISCOUNT_AMOUNT_MULTILINE_ORDER_TAX("$19.39"),
    DISCOUNT_PERCENT_MULTILINE_ORDER_TAX("$17.98"),
    DISCOUNT_SHIP_MULTILINE_ORDER_TAX("$19.87"),
    QUANTITY_ONE("1"),
    QUANTITY_TEN("10"),
    QUANTITY_ZERO("0"),
    QUANTITY_FIVE("5"),
    QUANTITY_TWO("2"),
    TEN_PERCENT_ORDER("10PercentOrder"),
    TEN_DOLLAR_ITEM("10DollarItem"),
    ONE_OFF_SHIP("1OFFShip"),
    FIVE_DOLLAR_ORDER("5DollarOrder");

    public String data;

    OroTestData(String data) {
        this.data = data;
    }
}
