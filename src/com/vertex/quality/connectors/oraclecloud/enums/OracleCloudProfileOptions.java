package com.vertex.quality.connectors.oraclecloud.enums;

import lombok.Getter;

/**
 * Enumerations of valid Taxlink profile options. Profile options
 * are typically updated directly via SQL calls to the Taxlink
 * database. Use these enums when updating profile option
 * values.
 *
 * @author msalomone
 */
@Getter
public enum OracleCloudProfileOptions {

    // Taxlink Profile Options
    ISUPPLIER_CHARGE_ENABLEMENT("VTX_ISUPP_VENDCHRG_ENAB_FLG");

    public String name;

    /**
     * Sets various points of information for an area on any requisition-related page.
     *
     * @param name The profile option name as displayed in the database.
     */
    OracleCloudProfileOptions(String name)
    {
        this.name = name;
    }
}
