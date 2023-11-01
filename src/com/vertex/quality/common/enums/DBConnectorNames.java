package com.vertex.quality.common.enums;

import lombok.Getter;

/**
 * Contains all the connector names in the database
 * If the Connectors table in the database gets updated, please add the changes here
 *
 * @author hho
 */
@Getter
public enum DBConnectorNames {
    ARIBA(11, "Ariba"),
    NETSUITE_ONE_WORLD(5, "NetSuite One World"),
    NETSUITE_SINGLE_COMPANY(4, "NetSuite Single Company"),
    NETSUITE_SUITE_TAX(28, "NetSuite Suite Tax"),
    SAP_TAX_SERVICE(6, "SAP Tax Service"),
    BIG_COMMERCE(12, "Big Commerce"),
    D365_BUSINESS_CENTRAL(15, "D365 Business Central"),
    D365_FINANCE_AND_OPERATIONS(16, "D365 Finance and Operations"),
    D365_RETAIL(23, "D365 Retail"),
    CONCUR(17, "Concur"),
    KIBO(18, "Kibo"),
    ORACLE_ERP_CLOUD(19, "Oracle ERP Cloud"),
    SALESFORCE(24, "Salesforce"),
    SITECORE(20, "Sitecore"),
    ORO_COMMERCE(22, "Oro Commerce"),
    WORKDAY(21, "Workday"),
    CHAIN_FLOW_ACCELERATOR(26, "Chain Flow Accelerator"),
    TRADESHIFT(27, "Tradeshift"),
    MIRAKL(31, "Mirakl"),
    SITECORE_XC(32, "SitecoreXC"),
    WOOCOMMERCE(33, "WooCommerce"),
    OSeries_Cloud9(28, "OSeries_Cloud9"),
    OSeries_onPrem9(29, "OSeries_onPrem9"),
    OSeries_onPrem8(30, "OSeries_onPrem8");

    private int id;
    private String connectorName;

    DBConnectorNames(final int connectorId, final String connectorName) {
        this.id = connectorId;
        this.connectorName = connectorName;
    }
}
