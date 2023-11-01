package com.vertex.quality.connectors.commercetools.api.pojos;

/**
 * post US address which can be sent to the connector's API
 *
 * @author Vivek.Kumar
 */
public class CommerceToolPostUsAddress {
    private String projectKey;
    private String username;
    private String password;
    private String trustedId;
    private String companyCode;
    private String clientId;
    private String clientSecret;
    private String host;
    private String addressCleansingEnabled;
    private String active;
    private String addressCleansingURL;
    private String taxCalculationEndpointURL;
    private CommerceToolSellerAddress sellerAddress;
    private CommerceToolDispatcherAddress dispatcherAddress;
    private String autoInvoicingEnabled;
    private String loggingEnabled;

    public String getProjectKey() {
        return projectKey;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTrustedId() {
        return trustedId;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getHost() {
        return host;
    }

    public String getAddressCleansingEnabled() {
        return addressCleansingEnabled;
    }

    public String getActive() {
        return active;
    }

    public String getAddressCleansingURL() {
        return addressCleansingURL;
    }

    public String getTaxCalculationEndpointURL() {
        return taxCalculationEndpointURL;
    }

    public CommerceToolSellerAddress getSellerAddress() {
        return sellerAddress;
    }

    public CommerceToolDispatcherAddress getDispatcherAddress() {
        return dispatcherAddress;
    }

    public String getAutoInvoicingEnabled() {
        return autoInvoicingEnabled;
    }

    public String getLoggingEnabled() {
        return loggingEnabled;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTrustedId(String trustedId) {
        this.trustedId = trustedId;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setAddressCleansingEnabled(String addressCleansingEnabled) {
        this.addressCleansingEnabled = addressCleansingEnabled;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public void setAddressCleansingURL(String addressCleansingURL) {
        this.addressCleansingURL = addressCleansingURL;
    }

    public void setTaxCalculationEndpointURL(String taxCalculationEndpointURL) {
        this.taxCalculationEndpointURL = taxCalculationEndpointURL;
    }

    public void setSellerAddress(CommerceToolSellerAddress sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public void setDispatcherAddress(CommerceToolDispatcherAddress dispatcherAddress) {
        this.dispatcherAddress = dispatcherAddress;
    }

    public void setAutoInvoicingEnabled(String autoInvoicingEnabled) {
        this.autoInvoicingEnabled = autoInvoicingEnabled;
    }

    public void setLoggingEnabled(String loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }
}
