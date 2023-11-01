package com.vertex.quality.connectors.commercetools.api.pojos;

/**
 * a create cart request which can be sent to the connector's API
 *
 * @author Vivek.Kumar
 */

public class CommerceToolCreateCart {
    private String currency;
    private String taxMode;
    private String customerId;

    public CommerceToolCreateCart() {}

    public String getCurrency() {
        return currency;
    }

    public String getTaxMode() {
        return taxMode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setTaxMode(String taxMode) {
        this.taxMode = taxMode;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
