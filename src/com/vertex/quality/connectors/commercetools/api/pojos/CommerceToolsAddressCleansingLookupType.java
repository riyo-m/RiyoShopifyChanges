package com.vertex.quality.connectors.commercetools.api.pojos;


/***
 * Lookup type request for Address cleansing
 * @author Mohit.Nirwan
 */

public class CommerceToolsAddressCleansingLookupType {


    private Integer taxAreaId;
    private String externalJurisdictionCode;
    private String externalJurisdictionCountry;
    private String lookupId;
    private String asOfDate;
    private CommerceToolsAddress address;

    public CommerceToolsAddress getAddress() {
        return address;
    }

    public void setAddress(CommerceToolsAddress commerceToolsAddress) {
        this.address = commerceToolsAddress;
    }

    public Integer getTaxAreaId() {
        return taxAreaId;
    }

    public void setTaxAreaId(Integer taxAreaId) {
        this.taxAreaId = taxAreaId;
    }

    public String getExternalJurisdictionCode() {
        return externalJurisdictionCode;
    }

    public void setExternalJurisdictionCode(String externalJurisdictionCode) {
        this.externalJurisdictionCode = externalJurisdictionCode;
    }

    public String getExternalJurisdictionCountry() {
        return externalJurisdictionCountry;
    }

    public void setExternalJurisdictionCountry(String externalJurisdictionCountry) {
        this.externalJurisdictionCountry = externalJurisdictionCountry;
    }

    public String getLookupId() {
        return lookupId;
    }

    public void setLookupId(String lookupId) {
        this.lookupId = lookupId;
    }

    public String getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(String asOfDate) {
        this.asOfDate = asOfDate;
    }

    @Override
    public String toString() {
        return "AddressCleansingLookupType{" +
                "taxAreaId=" + taxAreaId +
                ", externalJurisdictionCode='" + externalJurisdictionCode + '\'' +
                ", externalJurisdictionCountry='" + externalJurisdictionCountry + '\'' +
                ", lookupId='" + lookupId + '\'' +
                ", asOfDate='" + asOfDate + '\'' +
                ", address=" + address +
                '}';
    }
}
