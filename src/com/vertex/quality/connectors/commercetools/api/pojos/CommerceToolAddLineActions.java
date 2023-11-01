package com.vertex.quality.connectors.commercetools.api.pojos;

/**
 * an add line request actions which can be sent to the connector's API
 *
 * @author Vivek.Kumar
 */
public class CommerceToolAddLineActions {
    private String action;
    private String productId;
    private long variantId;
    private long quantity;
    private String taxMode;
    private CommerceToolShippingDetails shippingDetails;

    public String getAction() {
        return action;
    }

    public String getProductId() {
        return productId;
    }

    public long getVariantId() {
        return variantId;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getTaxMode() {
        return taxMode;
    }

    public CommerceToolShippingDetails getShippingDetails() {
        return shippingDetails;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setVariantId(long variantId) {
        this.variantId = variantId;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setTaxMode(String taxMode) {
        this.taxMode = taxMode;
    }

    public void setShippingDetails(CommerceToolShippingDetails shippingDetails) {
        this.shippingDetails = shippingDetails;
    }
}
