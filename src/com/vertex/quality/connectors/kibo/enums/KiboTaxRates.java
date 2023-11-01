package com.vertex.quality.connectors.kibo.enums;

/**
 * contains different Addresses for all test cases uses
 *
 * @author osabha
 */

public enum KiboTaxRates {
    EXPECTED_TAX("$1.42"),
    CHECK_NUMBER("123456789"),
    ALASKA_TAX("2.38"),
    CA_TAX("2.38"),
    CA_EXPECTED_TAXES("$119.20"),
    CALIFORNIA_TAX("$134.43"),
    CA_EXPECTED_TAX("$1.43"),
    CA_EXEMPTED_TAX("$0.00"),
    CA_EXPECTED_EXEMPT("$1.43"),
    DE_TAX("$0.00"),
    ALASKA_TAX_RATE("$70.75"),
    EXPECTED_TAXES("$138.11"),
    WA_EXPECTED_TAX("$124.55"),
    DE_EXPECTED_TAX("$247.67"),
    LA_EXPECTED_TAX("$1.43"),
    CA_EXPECTED_ORDER_AMOUNT("$605.60"),
    KIBO_QUANTITY("10"),
    KIBO_QUANTITY5("5"),
    KIBO_QUANTITY1("1"),
    KIBO_QUANTITY2("2"),
    KIBO_QUANTITY3("3"),
    DISCOUNT_VALUE(""),

    DISCOUNT_FLAT_5DOLLAR("$5.00");

    public String value;

    KiboTaxRates(String val) {
        this.value = val;
    }
}