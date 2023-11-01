package com.vertex.quality.connectors.oraclecloud.enums;

import lombok.Getter;
import org.openqa.selenium.By;

/**
 * Storage container for all data used in relation to
 * the Requisition pages.
 *
 * @author msalomone
 */
@Getter
public enum OracleCloudReqPagesFieldData {

    // Manage Reqs Search Fields
    REQUISITION(By.cssSelector("input[id$='value20::content']"), "value20", null);

    private By locator;
    private String idOrName;
    private String buttonTitle;

    /**
     * Sets various points of information for an area on any requisition-related page.
     *
     * @param loc        the locator for the field
     * @param identifier identifying portion of the field's name or ID
     * @param button     title of any button associated with the field
     */
    OracleCloudReqPagesFieldData( final By loc, final String identifier, final String button )
    {
        this.locator = loc;
        this.idOrName = identifier;
        this.buttonTitle = button;
    }
}
