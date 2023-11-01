package com.vertex.quality.connectors.kibo.enums;

/**
 * contains all the strings for Discounts to be passed to a discount selecting method
 *
 * @author osabha
 */
public enum KiboDiscounts {
    DISCOUNT1("15 OFF SHOES,", "15$ OFF ITEM OF SHOES CATEGORY "),
    DISCOUNT2("10% OFF Order,", "10% OFF THE ENTIRE ORDER"),
    DISCOUNT3("5 percent off Shipping for line ltem,", "5 % off Shipping for line ltem"),
    DISCOUNT4("Summer SALE,", "SUMMER SALE 10% OFF THE ENTIRE ORDER"),
    DISCOUNT_10PERCENT_ORDER("10% Discount,", "Coupon: 10PercentOrdersd"),
    DISCOUNT_10DOLLAR_ITEM("10DollarItem Discount,", "Coupon: 10DollarItem"),
    DISCOUNT_1OFF_SHIP("1OFFShip Discount,", "Coupon: 1OFFShip"),
    DISCOUNT_5DOLLAR_ORDER("5DollarOrder,", "Coupon: 5DollarOrder"),
    DISCOUNT_5PERCENT_ITEM("5PercentItem,", "Coupon: 5PercentItem");

    public String value;
    protected String displayName;

    KiboDiscounts(String val, String displayName) {
        this.value = val;
        this.displayName = displayName;
    }
}
