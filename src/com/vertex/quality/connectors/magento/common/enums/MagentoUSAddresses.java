package com.vertex.quality.connectors.magento.common.enums;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.Province;
import com.vertex.quality.common.enums.State;
import lombok.Getter;

/**
 * US addresses for Address Cleansing Tests for Magento2.
 *
 * @author Shivam.Soni
 */
@Getter
public enum MagentoUSAddresses {

    // Address used are unformatted & some wrong to test Address Cleansing for Magento, if to be used for order then use either clean or Vertex's cleansed addresses.
    Allen("727 Central Expy", null, "727 Central Expy S", null, "Allen", State.TX, null, "75013", "75013-8012", Country.USA),
    Birmingham("2473 Hackworth Road", "Suite 222", "2473 Hackworth Rd Ste 222", null, "Birmingham", State.AL, null, "35214", "35214-1909", Country.USA),
    Bothell("20225 Bothell Everett Hwy", null, "20225 Bothell Everett Hwy", null, "Bothell", State.WA, null, "98012", "98012-8170", Country.USA),
    Durham("4005 Durham-Chapel hill blvd", null, "4005 Durham Chapel Hill Blvd", null, "Durham", State.NC, null, "27707", "27707-2516", Country.USA),
    Gettysburg("1270 York Road", null, "1270 York Rd", null, "Gettysburg", State.PA, null, "17325", "17325-7562", Country.USA),
    LosAngeles("5950 Broadway", null, "5950 S Broadway", null, "Los Angeles", State.CA, null, "90030", "90003-1145", Country.USA),
    SanGabriel("428 Mission Dr", "428 Mission dr11", "428 S Mission Dr 11", null, "San Gabriel", State.CA, null, "91776", "91776-1252", Country.USA);

    public String addressLine1;
    public String addressLine2;
    public String city;
    public String zip5;
    public String cleansedAddressLine1;
    public String cleansedAddressLine2;
    public String zip9;
    public State state;
    public Province province;
    public Country country;

    MagentoUSAddresses( String addressLine1, String addressLine2, String cleansedAddressLine1, String cleansedAddressLine2,
             String city, State state, Province province, String zip5, String zip9, Country country )
    {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.cleansedAddressLine1 = cleansedAddressLine1;
        this.cleansedAddressLine2 = cleansedAddressLine2;
        this.city = city;
        this.state = state;
        this.province = province;
        this.zip5 = zip5;
        this.zip9 = zip9;
        this.country = country;
    }
}
