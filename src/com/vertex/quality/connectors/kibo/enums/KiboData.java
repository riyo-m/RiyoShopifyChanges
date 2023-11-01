package com.vertex.quality.connectors.kibo.enums;

/**
 * contains all the product categories for the store front.
 *
 * @author osabha
 */
public enum KiboData {
    CUSTOMER_FIRSTNAME("TesterFirstname"),
    CUSTOMER_LASTNAME("TesterLastname"),
    CUSTOMER_PHONE("1234567890"),
    CUSTOMER_ADDRESS_TYPE_RESIDENTIAL("Residential"),
    CUSTOMER_ADDRESS_TYPE_COMMERCIAL("Commercial"),

    CUSTOMER_CLASS_VTXC("VTXCClass"),
    CUSTOMER_CODE_VTXC("VTXCCode"),
    PRODUCT_CODE_VTXP("vtxpcode"),
    PRODUCT_CLASS_VTXP("VTXPClass"),
    PRICE_AMOUNT("100"),
    PRODUCT_TYPE_BIKE("Bike"),
    REFUND_AMOUNT("0.0"),

    REFUND_REPORTED_ISSUE_DAMAGED("Damaged"),
    REFUND_REPORTED_ISSUE_DEFECTIVE("Defective"),
    REFUND_REPORTED_ISSUE_MISSING("Missing Parts"),
    REFUND_REPORTED_ISSUE_DIFFERENT("Different Expectations"),
    REFUND_REPORTED_ISSUE_LATE("Late"),
    REFUND_REPORTED_ISSUE_NOT_NEEDED("No Longer Wanted"),
    REFUND_REPORTED_ISSUE_OTHER("Other"),
    REFUND_TYPE_REPLACE("Replace"),
    REFUND_TYPE_REFUND("Refund"),

    SHIP_TO_INVALID_ADDRESS_CLEANSING_MSG("Unable to validate address"),
    SHIP_TO_BLANK_ZIP_CLEANSED_MSG("Valid address is:\n" +
            "2473 HACKWORTH RD\n" +
            "BIRMINGHAM, AL 35214-1909\n" +
            "US"),
    SHIP_TO_NULL_ZIP_CLEANSED_MSG("Valid address is:\n" +
            "2955 Market St\n" +
            "Philadelphia, PA 19104-2828\n" +
            "US"),
    SHIP_FROM_BLANK_ZIP_CLEANSED_MSG("2473 HACKWORTH RD\n" +
            "BIRMINGHAM\n" +
            "35214-1909"),
    SHIP_FROM_ADDRESS_CLEANSING_ON_INVALID_ADDRESS_MSG("Address could not be validated, please check your address and try again"),
    SHIP_FROM_EMPTY_ZIP_ADDRESS_CLEANSING_OFF_MSG("Missing or invalid parameter: request Number: -2147219402, Description: Invalid State Code. , Source: clsAMS, HelpContext:"),
    SHIP_FROM_INVALID_ZIP_ADDRESS_CLEANSING_OFF_MSG("Missing or invalid parameter: request Number: -2147219399, Description: Invalid Zip Code. , Source: clsAMS, HelpContext:");

    public String value;

    KiboData(String val) {
        this.value = val;
    }
}
