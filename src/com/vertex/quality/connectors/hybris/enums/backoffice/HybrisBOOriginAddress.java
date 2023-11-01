package com.vertex.quality.connectors.hybris.enums.backoffice;

/**
 * Enum which holds all Origin Addresses
 *
 * @author Nagaraju Gampa
 */
public enum HybrisBOOriginAddress {
    GETTYSBURG("York Road", "1270", "17325", "Gettysburg", "United States[US]"),
    BERWYN("Old Cassatt Rd", "1041", "19312", "Berwyn", "United States[US]"),
    HIGHLAND_RANCH("Skyreach Rd", "10599", "80126-5635", "Highland Ranch", "United States[US]"),
    LOS_ANGELES("Broadway", "5950", "90030", "Los Angeles", "United States[US]"),
    QUEBEC("Boulevard Laurier G12a", "2450", "G1V 2L1", "Quebec City", "Canada[CAN]"),
    VICTORIA("Store St", "1820", "V8T 4R4", "Victoria", "Canada[CAN]"),
    GRAND_MANAN("Bancroft Point Rd", "11", "E5G 4C1", "Grand Manan", "Canada[CAN]"),
    BERLIN_ORANIENSTRASSE("Oranienstraße", "138", "10969", "Berlin", "Germany[DE]"),
    BERLIN_ALLE("Allée du Stade", "", "13405", "Berlin", "Germany[DE]"),
    PARIS("Port de la Bourdonnais", "", "75007", "Paris", "France[FR]"),
    SINGAPORE("Upper Jurong Road", "510", "638365", "Singapore", "Singapore[SG]"),
    TYSONS_ADDRESS("Chain Bridge Rd", "1961", "22102", "Tysons", "United States[US]"),
    EASTON_ADDRESS("Van Buren Rd", "1108", "18045", "Easton", "United States[US]");

    public String stName;
    public String stNumber;
    public String postalCode;
    public String town;
    public String country;

    HybrisBOOriginAddress(String name, String number, String postCode, String town, String country) {
        this.stName = name;
        this.stNumber = number;
        this.postalCode = postCode;
        this.town = town;
        this.country = country;
    }
}