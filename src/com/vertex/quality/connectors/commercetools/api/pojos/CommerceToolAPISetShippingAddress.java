package com.vertex.quality.connectors.commercetools.api.pojos;

import java.util.List;

/**
 * a set shipping address request which can be sent to the connector's API
 *
 * @author Vivek.Kumar
 */
public class CommerceToolAPISetShippingAddress {
    private long version;
    private List<CommerceToolShippingActions> actions;

    public CommerceToolAPISetShippingAddress()
    {
    }

    public Long getVersion() {
        return version;
    }

    public List<CommerceToolShippingActions> getActions() {
        return actions;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setActions(List<CommerceToolShippingActions> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "CommerceToolAPISetShippingAddress{" +
                "version='" + version + '\'' +
                ", actions=" + actions +
                '}';
    }
}