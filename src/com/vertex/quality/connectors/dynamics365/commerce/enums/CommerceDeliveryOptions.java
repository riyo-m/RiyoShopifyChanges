package com.vertex.quality.connectors.dynamics365.commerce.enums;

/**
 * Represents the delivery options for E-Commerce
 */
public enum CommerceDeliveryOptions {
    STANDARD("Standard"),
    OVERNIGHT("Standard overnight"),
    TWO_DAY("2 Day Guaranteed Delivery");

    public final String value;
    private CommerceDeliveryOptions(String value) {
        this.value = value;
    }
}
