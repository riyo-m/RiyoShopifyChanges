package com.vertex.quality.connectors.hybris.enums.electronics;

/**
 * Data file or Enums that contains wrong zip-codes for address cleansing.
 *
 * @author Shivam.Soni
 */
public enum HybrisAddressCleansingWrongZip {
    INVALID_ZIP_99999("99999"),
    BLANK_ZIP("blank"),
    NULL_ZIP("null"),
    EMPTY_ZIP("empty");

    String zip;

    HybrisAddressCleansingWrongZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }
}
