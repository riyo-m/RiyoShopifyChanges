package com.vertex.quality.connectors.hybris.enums.backoffice;

/**
 * Data file or Enum for vertex customization's flex-fields names & values.
 *
 * @author Shivam.Soni
 */
public enum HybrisFlexFieldsValues {
    FIELD_VALUE_ORDER("Order"),
    FIELD_VALUE_PRODUCT("Product"),
    FIELD_VALUE_ID_1("1"),
    FIELD_VALUE_ID_2("2"),
    FIELD_VALUE_ID_3("3"),
    FIELD_VALUE_ID_4("4"),
    FIELD_VALUE_ID_5("5"),
    FIELD_VALUE_DOUBLE("Double"),
    FIELD_VALUE_INTEGER("Integer"),
    FIELD_VALUE_CODE("code"),
    FIELD_VALUE_VERSION_ID("versionID"),
    FIELD_VALUE_DESCRIPTION("description"),
    FIELD_VALUE_NAME("name"),
    FIELD_VALUE_DATE("date"),
    FIELD_VALUE_CREATION_TIME("creationtime"),
    FIELD_VALUE_MODIFIED_TIME("modifiedtime"),
    FIELD_VALUE_TOTAL_PRICE("totalPrice"),
    FIELD_VALUE_TOTAL_TAX("totalTax"),
    FIELD_VALUE_SUB_TOTAL("subtotal"),
    FIELD_VALUE_STATUS_DISPLAY("statusDisplay");

    String data;

    HybrisFlexFieldsValues(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
