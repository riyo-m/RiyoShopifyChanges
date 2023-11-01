package com.vertex.quality.connectors.hybris.enums.electronics;

/**
 * Test data or Enum that containing discount coupons for Hybris
 *
 * @author Shivam.Soni
 */
public enum HybrisDiscountCoupons {
    COUPON_5_DOLLAR_ORDER("5DollarOrder"),
    COUPON_10_DOLLAR_ORDER("10DollarOrder"),
    COUPON_5_PERCENT_ORDER("5PercentOrder"),
    COUPON_10_PERCENT_ORDER("10PercentOrder");

    String couponName;

    /**
     * Parameterized constructor that initializes the variable's value.
     *
     * @param couponName version of vertex's service.
     */
    HybrisDiscountCoupons(String couponName) {
        this.couponName = couponName;
    }

    /**
     * Getter method to get variable's value.
     *
     * @return variable's value.
     */
    public String getCouponName() {
        return couponName;
    }
}
