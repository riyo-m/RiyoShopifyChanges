package com.vertex.quality.connectors.episerver.common.enums;

import lombok.Getter;

/**
 * This class contains O-Series Data related to Episerver
 *
 * @author Shivam.Soni
 */
@Getter
public enum OSeriesData {

    // O-Series Credentials
    O_SERIES_USER("episerver-test"),
    O_SERIES_PASSWORD("episerver123"),

    // O-Series Login, Taxation & Cleansing URL(s)
    O_SERIES_URL("https://oseries9-final.vertexconnectors.com/oseries-ui"),
    O_SERIES_TAX_CALCULATION_URL("https://oseries9-final.vertexconnectors.com/vertex-ws/services/CalculateTax90"),
    O_SERIES_ADDRESS_CLEANSING_URL("https://oseries9-final.vertexconnectors.com/vertex-ws/services/LookupTaxAreas90"),

    // Sample bad URL (Used to validate health-check test cases)
    BAD_URL("https://badurl.com"),

    // Episerver connector's version(s)
    EPI_CORE_VERSION_2_0_1_0("2.0.1.0"),
    EPI_CORE_VERSION_2_0_2_0("2.0.2.0"),
    EPI_CORE_VERSION_2_0_3_0("2.0.3.0"),
    EPI_CORE_VERSION_2_0_4_0("2.0.4.0"),
    EPI_ADAPTER_VERSION_3_2_2_0("3.2.2.0"),
    EPI_ADAPTER_VERSION_3_2_3_1("3.2.3.1"),
    EPI_ADAPTER_VERSION_3_2_4_0("3.2.4.0"),
    EPI_ADAPTER_VERSION_3_2_5_0("3.3.0.0"),

    // Episerver customer code-class & product code-class
    EPI_CUSTOMER_CODE("VTXCCode"),
    EPI_CUSTOMER_CLASS("VTXCClass"),
    EPI_PRODUCT_CODE("VTXPCode"),
    EPI_PRODUCT_CLASS("VTXPClass"),

    // Epi-server's company details
    EPI_REGISTERED_TAX_PAYER("100"),
    EPI_NOT_REGISTERED_TAX_PAYER("test_company"),

    // Shipping Terms
    SHIP_TERM_CUS("CUS"),
    SHIP_TERM_DDP("DDP"),
    SHIP_TERM_EXW("EXW"),
    SHIP_TERM_SUP("SUP");

    public String data;

    OSeriesData(String data) {
        this.data = data;
    }
}
