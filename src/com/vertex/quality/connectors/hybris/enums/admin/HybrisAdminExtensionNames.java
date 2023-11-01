package com.vertex.quality.connectors.hybris.enums.admin;

import lombok.Getter;

/**
 * Test data or Enum file that is containing extension's names of vertex for Hybris connector.
 *
 * @author Shivam.Soni
 */
@Getter
public enum HybrisAdminExtensionNames {

    EXTENSION_VERTEX_ADDRESS_VERIFICATION("vertexaddressverification"),
    EXTENSION_VERTEX_API("vertexapi"),
    EXTENSION_VERTEX_B2B_ADDRESS_VERIFICATION("vertexb2baddressverification"),
    EXTENSION_VERTEX_B2B_API("vertexb2bapi"),
    EXTENSION_VERTEX_BACKOFFICE("vertexbackoffice"),
    EXTENSION_VERTEX_TAX_CALCULATION("vertextaxcalculation"),
    EXTENSION_VERTEX_OCC_TEST_ADDON("vertexocctestaddon"),
    EXTENSION_VERTEX_TEST_DATA("vertextestdata");

    String extensionName;

    /**
     * Parameterized constructor that initializes the variable's value.
     *
     * @param extensionName name of vertex's extension
     */
    HybrisAdminExtensionNames(String extensionName) {
        this.extensionName = extensionName;
    }

    /**
     * Getter method to get variable's value.
     *
     * @return variable's value.
     */
    public String getExtensionName() {
        return extensionName;
    }
}
