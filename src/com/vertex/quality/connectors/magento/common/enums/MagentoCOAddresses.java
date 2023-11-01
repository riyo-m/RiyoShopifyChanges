package com.vertex.quality.connectors.magento.common.enums;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import lombok.Getter;

/**
 * US Colorado's addresses for Magento O-Series
 *
 * @author Shivam.Soni
 */
@Getter
public enum MagentoCOAddresses {
    ELIZABETH_ADDRESS("2679 Clearview Drive", "Elizabeth", Country.USA, State.CO, "80107"),
    BOULDER_ADDRESS("2051 Tavern Place", "Boulder", Country.USA, State.CO, "80302"),
    DENVER_ADDRESS("2918 Timberbrook Lane", "Denver", Country.USA, State.CO, "80202");

    public final String addressLine1;
    public final String city;
    public final Country country;
    public final State state;
    public final String zip5;

    MagentoCOAddresses(final String line1, final String city, final Country countryName,
                       final State regionName, final String postalCode) {
        this.addressLine1 = line1;
        this.city = city;
        this.country = countryName;
        this.state = regionName;
        this.zip5 = postalCode;
    }
}
