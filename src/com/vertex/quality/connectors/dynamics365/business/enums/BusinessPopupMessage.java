package com.vertex.quality.connectors.dynamics365.business.enums;


/**
 * This enums for popup message
 */
public enum BusinessPopupMessage {

    AddressCleansingCustomAddressPopupMessage("Ship-to address was changed but taxes have not been recalculated. Would you like to recalculate taxes now?");

    public String value;

    BusinessPopupMessage( String val )
    {
        this.value = val;
    }
}
