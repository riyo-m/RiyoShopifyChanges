package com.vertex.quality.connectors.hybris.enums.admin;

import lombok.Getter;

/**
 * Test data or Enum file that is containing different versions different services of vertex for Hybris connector.
 *
 * @author Shivam.Soni
 */
@Getter
public enum HybrisConnectorVersions {
    VERSION_1_0_8_0("1.0.8.0"),
    VERSION_2011_8("2011.8"),
    VERSION_2015_2("2015.2");

    String version;

    /**
     * Parameterized constructor that initializes the variable's value.
     *
     * @param version version of vertex's service.
     */
    HybrisConnectorVersions(String version) {
        this.version = version;
    }

    /**
     * Getter method to get variable's value.
     *
     * @return variable's value.
     */
    public String getVersion() {
        return version;
    }
}
