package com.vertex.quality.connectors.commercetools.api.pojos;

/**
 * shipping actions which can be sent to the connector's API
 *
 * @author Vivek.Kumar
 */
public class CommerceToolShippingActions {
    private String action;
    private CommerceToolShippingAddress address;

    public CommerceToolShippingActions() {
    }

    public String getAction() {
        return action;
    }

    public CommerceToolShippingAddress getAddress() {
        return address;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setAddress(CommerceToolShippingAddress address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CommerceToolShippingActions{" +
                "action='" + action + '\'' +
                ", address=" + address +
                '}';
    }
}
