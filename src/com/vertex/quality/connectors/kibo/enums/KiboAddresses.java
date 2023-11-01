package com.vertex.quality.connectors.kibo.enums;

/**
 * contains different Addresses for all test cases uses
 *
 * @author osabha
 */
public enum KiboAddresses {
    CITY_BERWYN("Berwyn"),
    CITY_EXTON("Exton"),
    CITY_DALLAS("Dallas"),
    STATE_PA("PA"),
    ZIP_99999("99999"),
    EMPTY_ZIP("empty"),
    BLANK_ZIP("blank"),
    NULL_ZIP("NULL"),
    ZIP_19312("19312"),
    CALIFORNIA_ADDRESS1("5950 Broadway"),
    CALIFORNIA_CITY("Los Angeles"),
    CALIFORNIA_STATE("CA"),
    CALIFORNIA_ZIP("90030"),
    CALIFORNIA_COUNTRY("United States"),
    CANADA_ADDRESS1("1820 Store St Victoria"),
    CANADA_CITY("Store St Victoria"),
    CANADA_STATE("BC"),
    CANADA_ZIP("V8T 4R4"),
    CANADA_COUNTRY("CANADA"),
    US_ADDRESS1("1270 York Road"),
    US_CITY("Gettysburg"),
    US_STATE("PA"),
    US_ZIP("17325"),
    US_COUNTRY("United States"),
    NJ_STREET("883 Route 1 Edison"),
    NJ_CITY("Route 1 Edison"),
    NJ_STATE(""),
    NJ_ZIP("08817"),
    NJ_COUNTRY("NJ"),
    DE_STREET("AllÃ©e du Stade"),
    DE_CITY("Berlin"),
    DE_STATE(""),
    DE_ZIP("13405"),
    DE_COUNTRY("Germany"),
    WA_STREET("NE 8th St 22833 Sammamish"),
    WA_CITY("Sammamish"),
    WA_STATE("Washington"),
    WA_ZIP("98074"),
    WA_COUNTRY("United States"),
    LA_STREET("5300 St Charles Ave"),
    LA_CITY("New Orleans"),
    LA_STATE("LA"),
    LA_ZIP("70118"),
    LA_COUNTRY("United States");

    public String value;

    KiboAddresses(String val) {
        this.value = val;
    }
}