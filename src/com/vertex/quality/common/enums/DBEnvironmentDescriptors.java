package com.vertex.quality.common.enums;

import lombok.Getter;

/**
 * Contains all the environment descriptors for entries in the database's EnvironmentInformation table
 * If the EnvironmentInformation table in the database gets updated, please add the changes here
 *
 * @author ssalisbury
 */
@Getter
public enum DBEnvironmentDescriptors {
    ACUMATICA("acumatica"),
    ACUMATICA_ADMIN("acumatica_admin"),
    ARIBA_CONFIG("ariba_config"),
    ARIBA_PORTAL("ariba_portal"),
    ARIBA_SUPPLIER("ariba_supplier"),
    ARIBA2_0_SUPPLIER("ariba2_supplier"),
    ARIBA2_0_CONFIG("ariba2_config"),
    ARIBA2_0_PORTAL("ariba2_portal"),
    ARIBA2_0_SOAP_API("ariba2_soap_api"),
    ARIBA2_0_STATUS_API("ariba2_status_api"),
    ARIBA2_0_VERSION_API("ariba2_version_api"),
    ARIBA2_0_XML_LOG_RETRIEVER_API("ariba2_xml_log_retriever_api"),
    BIG_COMMERCE_API("big_commerce_api"),
    BIG_COMMERCE_UI("big_commerce_ui"),
    CONCUR("concur"),
    D365_BUSINESS_CENTRAL("d365_business_central"),
    D365_FINANCE_AND_OPERATIONS("d365_finance_and_operations"),
    D365_RETAIL("d365_retail"),
    EPISERVER_ADMIN("episerver_admin"),
    EPISERVER_STOREFRONT("episerver_storefront"),
    HYBRIS_ADMIN("hybris_admin"),
    HYBRIS_BACKOFFICE("hybris_backoffice"),
    HYBRIS_STOREFRONT("hybris_storefront"),
    MAGENTO_M2_ADMIN("magento_m2_admin"),
    MAGENTO_M2_STOREFRONT("magento_m2_storefront"),
    //warning- netsuite legacy is the descriptor for multiple EnvironmentInformation rows
    NETSUITE_LEGACY("netsuite_legacy"),
    SALESFORCE("salesforce"),
    SAP_TAX_SERVICE_QUOTE("quote"),
    KIBO("kibo"),
    ORACLE_ERP_CLOUD("oracle_erp_cloud"),
    ORACLE_ERP_CLOUD_DEV("oracle_erp_cloud_dev"),
    SITECORE("sitecore_admin"),
    WORKDAY("workday_tenant"),
    ORO_STOREFRONT("oro_storefront"),
    ORO_ADMIN("oro_admin"),
    ARIBA_ONE_X("ariba_soap_api"),
    ARIBA_XML_LOG_RETRIEVER_API("ariba_xml_log_retriever_api"),
    SAP_CHAIN_FLOW_TAX_SERVICE_QUOTE("cfa_quote"),
    TRADESHIFT_CONNECTOR("tradeshift_connector"),
    MIRAKL("mirakl"),
    SITECOREXC_ADMIN("SitecoreXC_admin"),
    WOOCOMMERCE_ADMIN("WooCommerce_admin");

    private String descriptor;

    DBEnvironmentDescriptors(final String descriptor) {
        this.descriptor = descriptor;
    }
}
