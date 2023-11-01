package com.vertex.quality.connectors.commercetools.api.pojos;

import java.io.Serializable;

/***
 *  address for Address Cleansing Api
 * @author Mohit.Nirwan
 */

public class CommerceToolsAddress implements Serializable {

    private String streetAddress1;

    private String streetAddress2;

    private String city;

    private String mainDivision;

    private String subDivision;

    private String postalCode;

    private String state;

    private String country;

    public String getStreetAddress1() {
        return streetAddress1;
    }

    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMainDivision() {
        return mainDivision;
    }

    public void setMainDivision(String mainDivision) {
        this.mainDivision = mainDivision;
    }

    public String getSubDivision() {
        return subDivision;
    }

    public void setSubDivision(String subDivision) {
        this.subDivision = subDivision;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "CommerceToolsAddress{" +
                "streetAddress1='" + streetAddress1 + '\'' +
                ", streetAddress2='" + streetAddress2 + '\'' +
                ", city='" + city + '\'' +
                ", mainDivision='" + mainDivision + '\'' +
                ", subDivision='" + subDivision + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
