package com.vertex.quality.connectors.commercetools.api.pojos;

/**
 * shipping details which can be sent to the connector's API
 *
 * @author Vivek.Kumar
 */
public class CommerceToolShippingDetails {
    private String addressKey;
    private long quantity;

    public String getAddressKey() {
        return addressKey;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setAddressKey(String addressKey) {
        this.addressKey = addressKey;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
