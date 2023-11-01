package com.vertex.quality.connectors.episerver.common.enums;

/**
 * Enum or Data file that contains payment options for Episerver
 *
 * @author Shivam.Soni
 */
public enum PaymentMethodType {
    CREDIT_CARD("Credit Card"),
    CASH_ON_DELIVERY("Cash on delivery"),
    AUTHORIZE_PAY_BY_CREDIT_CARd("Authorize - Pay By Credit Card");

    public String text;

    PaymentMethodType(String text) {
        this.text = text;
    }
}
