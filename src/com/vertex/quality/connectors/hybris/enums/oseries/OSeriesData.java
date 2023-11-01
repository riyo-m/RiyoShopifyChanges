package com.vertex.quality.connectors.hybris.enums.oseries;

import lombok.Getter;

/**
 * This class contains O-Series Data related to Hybris
 *
 * @author Shivam.Soni
 */
@Getter
public enum OSeriesData {

    // O-Series Credentials
    O_SERIES_USER("hybrisUser"),
    O_SERIES_PASSWORD("vertex"),

    // O-Series Login, Taxation & Cleansing URL(s)
    O_SERIES_URL("https://oseries9-final.vertexconnectors.com/oseries-ui"),
    O_SERIES_TAX_CALCULATION_URL("https://oseries9-final.vertexconnectors.com/vertex-ws/services/CalculateTax70"),
    O_SERIES_ADDRESS_CLEANSING_URL("https://oseries9-final.vertexconnectors.com/vertex-ws/services/LookupTaxAreas70 "),

    // Sample bad URL (Used to validate health-check test cases)
    BAD_URL("https://badurl.com"),

    // Health-check validation messages
    TAX_CALC_BAD_URL_VALIDATION_MSG("Invalid Vertex Endpoint"),
    ADDRESS_CLEANSING_BAD_URL_VALIDATION_MSG("Invalid Address Cleansing Endpoint"),

    // Hybris connector's version(s)
    HYBRIS_CONNECTOR_VERSION_1_0_0("1.0.0"),

    // Flex-fields' source entities
    ENTITY_BILLING_INFO("Billing Info"),
    ENTITY_FULFILLMENT_INFO("Fulfillment Info"),
    ENTITY_ORDER_DATA("Order Data"),
    ENTITY_CUSTOMER_ATTRIBUTES("Customer Attributes"),
    ENTITY_ORDER_ATTRIBUTES("Order Attributes"),
    ENTITY_PRODUCT_ATTRIBUTES("Product Attributes"),

    // Flex-fields' source fields
    FIELD_SHIPPING_METHOD_NAME("shippingMethodName"),
    FIELD_CUSTOMER_ACCOUNT_ID("customerAccountId"),
    FIELD_SUBMITTED_DATE("submittedDate"),
    FIELD_CLOSED_DATE("closedDate"),
    FIELD_LAST_VALIDATION_DATE("lastValidationDate"),
    FIELD_IMPORT_DATE("importDate"),


    // Shipping Terms
    SHIP_TERM_CUS("CUS"),
    SHIP_TERM_DDP("DDP"),
    SHIP_TERM_EXW("EXW"),
    SHIP_TERM_SUP("SUP");

    public String data;

    OSeriesData(String data) {
        this.data = data;
    }
}
