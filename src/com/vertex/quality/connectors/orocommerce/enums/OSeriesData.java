package com.vertex.quality.connectors.orocommerce.enums;

import lombok.Getter;

/**
 * This class contains O-Series Data related to Oro
 *
 * @author Shivam.Soni
 */
@Getter
public enum OSeriesData {

    HEALTH_CHECK_VALID_URL("https://oseries9-final.vertexconnectors.com"),
    O_SERIES_TRUSTED_ID("$OroCommerce"),
    HEALTH_CHECK_INVALID_URL("https://www.badurl.com"),
    HEALTH_CHECK_SUCCESS_MSG("Connection established successfully"),
    HEALTH_CHECK_UN_SUCCESS_MSG("Could not establish connection"),
    ORO_4_1_VERSION("1.4.0"),
    ORO_4_2_VERSION("1.4.2.0"),
    ORO_5_VERSION("2.0.0"),
    O_SERIES_USER("orocommerce-admin"),
    O_SERIES_PASSWORD("adminorocom!"),
    O_SERIES_URL("https://oseries9-final.vertexconnectors.com/oseries-ui"),
    SHIP_TERM_SUP("SUP"),
    SHIP_TERM_CUS("CUS");

    public String data;

    OSeriesData(String data) {
        this.data = data;
    }
}
