package com.vertex.quality.connectors.commercetools.api.pojos;

/**
 * a create order request which can be sent to the connector's API
 *
 * @author Vivek.Kumar
 */
public class CommerceToolCreateOrder {
    private String id;
    private long version;
    private String orderNumber;
    public CommerceToolCreateOrder() {}

    public String getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
