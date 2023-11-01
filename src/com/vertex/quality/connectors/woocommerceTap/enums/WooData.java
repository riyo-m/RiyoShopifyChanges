package com.vertex.quality.connectors.woocommerceTap.enums;

import lombok.Getter;

/**
 * Data for address, product lists, product quantities, Taxes for Test cases, coupon codes and contact details
 *
 * @author Shivam.Soni
 */
@Getter
public enum WooData {

    US_CA_LA(new String[]{"United States (US)", "5950 Broadway", "Los Angeles", "California", "90030"}),
    US_CA_UC(new String[]{"United States (US)", "100 Universal City Plaza", "University City", "California", "91608-1002"}),
    US_WA_SM(new String[]{"United States (US)", "NE 8th St 22833", "Sammamish", "Washington", "98074"}),
    US_LA_NO(new String[]{"United States (US)", "5300 St Charles Ave", "New Orleans", "Louisiana", "70118"}),
    US_CO_HR(new String[]{"United States (US)", "10599 Skyreach Rd", "Highlands Ranch", "Colorado", "80126-5635"}),
    US_CO_CS(new String[]{"United States (US)", "1575 Space Center Drive", "Colorado Springs", "Colorado", "80915"}),
    US_PA_GTY(new String[]{"United States (US)", "1270 York Rd", "Gettysburg", "Pennsylvania", "17325-7562"}),
    US_PA_BER(new String[]{"United States (US)", "1041 Old Cassatt Rd", "Berwyn", "Pennsylvania", "19312"}),
    US_UT_SLC(new String[]{"United States (US)", "4924 S Murray Blvd", "Salt Lake City", "Utah", "84123"}),
    US_WI_MD(new String[]{"United States (US)", "1440 Monroe Street", "Madison", "Wisconsin", "53711"}),
    US_NJ_ED(new String[]{"United States (US)", "883 Route 1", "Edison", "New Jersey", "08817-4677"}),
    US_TX_DL(new String[]{"United States (US)", "4505 Ridgeside Dr", "Dallas", "Texas", "75244-4902"}),
    US_DE_WI(new String[]{"United States (US)", "100 N Orange St", "Wilmington", "Delaware", "19801"}),
    US_AL_JN(new String[]{"United States (US)", "6525 Glacier Hwy", "Juneau", "Alaska", "99801"}),
    DE_BERLIN_13405(new String[]{"Germany", "Allée du Stade", "Berlin", "Berlin", "13405"}),
    DE_BERLIN_10696(new String[]{"Germany", "Oranienstraße 138", "Berlin", "10969"}),
    FR_PARIS_75007(new String[]{"France", "Port de la Bourdonnais", "Paris", "75007"}),
    GR_ANALIPSI_85900(new String[]{"Greece", "Paralia Schoinonta", "Analipsi", "Thessaly", "85900"}),
    AT_MITTELBERG_6993(new String[]{"Austria", "Moosstraße 7", "Mittelberg", "6993"}),
    CAN_BC_VICTORIA(new String[]{"Canada", "1820 Store St", "Victoria", "British Columbia", "V8T 4R4"}),
    CAN_QB_QC(new String[]{"Canada", "2450 Boulevard Laurier G12a", "Quebec City", "Quebec", "G1V 2L1"}),
    CAN_NB_GM(new String[]{"Canada", "11 Bancroft Point Rd", "Grand Manan", "New Brunswick", "E5G 4C1"}),
    CAN_NB_QU(new String[]{"Canada", "Pettingill Rd", "Quispamsis", "New Brunswick", "E2E 6B1"}),
    CAN_ON_OT(new String[]{"Canada", "375 Bank St", "Ottawa", "Ontario", "K2P 1Y2"}),

    US_PA_INVALID_ZIP(new String[]{"United States (US)", "1270 York Rd", "Gettysburg", "Pennsylvania", "99999"}),
    US_PA_BLANK_ZIP(new String[]{"United States (US)", "1270 York Rd", "Gettysburg", "Pennsylvania", "blank"}),
    US_PA_INVALID_CITY_ZIP(new String[]{"United States (US)", "2955 Market St.", "Chester", "Pennsylvania", "19104"}),
    US_PA_EMPTY_ZIP(new String[]{"United States (US)", "2955 Market St.", "Philadelphia", "Pennsylvania", "empty"}),
    US_PA_INVALID_STATE_ZIP(new String[]{"United States (US)", "2955 Market St.", "Philadelphia", "Delaware", "19104"}),
    US_PA_NULL_ZIP(new String[]{"United States (US)", "2955 Market St.", "Philadelphia", "Pennsylvania", "null"}),
    US_PA_PITTSBURGH(new String[]{"United States (US)", "1000 Airport Blvd", "Pittsburgh", "Pennsylvania", "15231"}),

    PRODUCTS_TWO_HOODIES(new String[]{"Hoodie with Logo", "Hoodie with Zipper"}),
    PRODUCTS_TWO_HOODIES_BELT(new String[]{"Hoodie with Logo", "Hoodie with Zipper", "Belt"}),
    PRODUCTS_BELT_TWO_HOODIES(new String[]{"Belt", "Hoodie with Zipper", "Hoodie with Logo"}),
    PRODUCT_HOODIE(new String[]{"Hoodie with Zipper"}),

    QUANTITY_ONE(new String[]{"1"}),
    QUANTITY_FIVE(new String[]{"5"}),
    QUANTITY_THREE(new String[]{"3"}),
    QUANTITIES_FIVE_ONE(new String[]{"5", "1"}),
    QUANTITIES_SIX_TWO(new String[]{"6", "2"}),
    QUANTITIES_THREE_ONE(new String[]{"3", "1"}),
    QUANTITIES_TEN_ONE(new String[]{"10", "1"}),
    QUANTITIES_FIVE_ONE_TWO(new String[]{"5", "1", "2"}),
    QUANTITIES_FIVE_THREE_THREE(new String[]{"5", "3", "3"}),

    PRODUCT_BEANIE("Beanie"),

    TAX_TC_WOO_358("$25.80"),
    TAX_TC_WOO_359("$28.94"),
    TAX_TC_WOO_360("$27.55"),
    TAX_TC_WOO_361("$28.03"),
    TAX_TC_WOO_362("$28.41"),
    TAX_TC_WOO_363("$36.46"),
    TAX_TC_WOO_364("$63.08"),
    TAX_TC_WOO_365("$39.19"),
    TAX_TC_WOO_593("$28.41"),
    TAX_TC_WOO_594_1("$25.80"),
    TAX_TC_WOO_594_2("$27.07"),
    TAX_TC_WOO_337("$3.18"),
    TAX_TC_WOO_338("$1.26"),
    TAX_TC_WOO_339("$2.88"),
    TAX_TC_WOO_341("$2.16"),
    TAX_TC_WOO_343("$2.34"),
    TAX_TC_WOO_348("$2.40"),
    TAX_TC_WOO_CAN_CAN_COMMON("$2.70"),
    TAX_TC_WOO_US_US_COMMON("$4.56"),
    TAX_TC_WOO_330_1("$51.31"),
    TAX_TC_WOO_330_2("$44.53"),
    TAX_TC_WOO_331_1("$51.31"),
    TAX_TC_WOO_331_2("$47.15"),

    TEN_DOLLAR_ITEM_DISCOUNT("$10.00"),
    FIVE_DOLLAR_ITEM_DISCOUNT("$5.00"),
    ONE_OFF_SHIP_DISCOUNT("$1.00"),
    TEN_DOLLAR_ITEM("10DollarItem"),
    TEN_PERCENT_ORDER("10PercentOrder"),
    ONE_OFF_SHIP("1OFFShip"),
    FIVE_PERCENT_ORDER("5PercentOrder"),
    FIVE_PERCENT_ITEM("5PercentItem"),
    FIVE_DOLLAR_ORDER("5DollarOrder"),
    FIRST_NAME("TesterFirstName"),
    LAST_NAME("TesterLastName"),
    CONTACT_NO("4561237890"),
    EMAIL("tester@test.com");

    public String[] data;
    public String value;

    WooData(String[] data) {
        this.data = data;
    }

    WooData(String value) {
        this.value = value;
    }
}