package com.vertex.quality.connectors.hybris.enums.backoffice;

/**
 * Data file or Enum for vertex's configuration properties & values.
 *
 * @author Shivam.Soni
 */
public enum HybrisPropertiesAndValues {
    ALWAYS_ACCEPT_CLEANSED_ADDRESS("Always Accept Cleansed Address"),
    ALLOW_VERTEX_CLEANSED_ADDRESS("Allow Vertex Cleansed Address"),
    ENABLE_TAX_INVOICING("Enable Tax Invoicing"),

    PROPERTY_VALUE_TRUE("True"),
    PROPERTY_VALUE_FALSE("False");

    String data;

    HybrisPropertiesAndValues(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
