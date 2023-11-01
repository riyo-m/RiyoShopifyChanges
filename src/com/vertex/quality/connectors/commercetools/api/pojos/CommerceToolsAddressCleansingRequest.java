package com.vertex.quality.connectors.commercetools.api.pojos;

import java.util.List;

/***
 *  Request  for address cleansing API
 *
 * @author Mohit.Nirwan
 */


public class CommerceToolsAddressCleansingRequest {

    private List<CommerceToolsAddressCleansingLookupType> lookupList;

    public List<CommerceToolsAddressCleansingLookupType> getLookupList() {
        return lookupList;
    }

    public void setLookupList(List<CommerceToolsAddressCleansingLookupType> lookupList) {
        this.lookupList = lookupList;
    }

    @Override
    public String toString() {
        return "AddressCleansingLookupType{" +
                "lookupList=" + lookupList +
                '}';
    }
}
