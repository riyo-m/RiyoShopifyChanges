package com.vertex.quality.connectors.hybris.enums.electronics;

/**
 * Data or Enum file which contains validation messages for Ship To Address Cleansing functionality.
 *
 * @author Shivam.Soni
 */
public enum HybrisAddressCleansingMSG {
    INVALID_ZIP_MSG("2955 Market St\n" +
            "                    \n" +
            "                    \n" +
            "                                Philadelphia, 99999\n" +
            "                        \n" +
            "                                United States, Pennsylvania"),
    BLANK_ZIP_MSG("2473 Hackworth Rd\n" +
            "                    \n" +
            "                    \n" +
            "                                Birmingham, empty\n" +
            "                        \n" +
            "                                United States, Alabama"),
    NULL_ZIP_MSG("2955 Market St\n" +
            "                    \n" +
            "                    \n" +
            "                                Philadelphia, null\n" +
            "                        \n" +
            "                                United States, Pennsylvania"),
    INVALID_CITY_ZIP_MSG("2955 Market St\n" +
            "                    \n" +
            "                    \n" +
            "                                Chester, 19104\n" +
            "                        \n" +
            "                                United States, Pennsylvania"),
    INVALID_STATE_ZIP_MSG("2955 Market St\n" +
            "                    \n" +
            "                    \n" +
            "                                Philadelphia, 19104\n" +
            "                        \n" +
            "                                United States, Delaware"),
    EMPTY_ZIP_MSG("2955 Market St\n" +
            "                    \n" +
            "                    \n" +
            "                                Philadelphia, empty\n" +
            "                        \n" +
            "                                United States, Pennsylvania");

    String message;

    HybrisAddressCleansingMSG(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
