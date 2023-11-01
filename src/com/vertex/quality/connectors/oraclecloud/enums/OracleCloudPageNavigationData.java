package com.vertex.quality.connectors.oraclecloud.enums;

import com.vertex.quality.connectors.oraclecloud.pages.*;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import lombok.Getter;

/**
 * Holds data for the pages accessible through links
 * on the navigation panel or other webpages
 *
 * @author cgajes
 */
@Getter
public enum OracleCloudPageNavigationData
{

	// NAVIGATION PANEL
	RECEIVABLES_BILLING_PAGE("Billing", OracleCloudReceivablesBillingPage.class),
	PAYABLES_INVOICES("Invoices", OracleCloudPayablesInvoicesPage.class),
	PAYABLES_PAYMENTS("Payments", OracleCloudPayablesPaymentsPage.class),
	PAYABLES_PAYMENTS_DASHBOARD("Payables Dashboard", OracleCloudPayablesPaymentsDashboardPage.class),
	PROCUREMENT_PURCHASE_ORDERS("Purchase Orders", OracleCloudProcurementPurchaseOrdersPage.class),
	PROCUREMENT_PURCHASE_REQUISITIONS("Purchase Requisitions", OracleCloudProcurementPurchaseReqsPage.class),
	ORDERMANAGEMENT_OM("Order Management", OracleCloudOrderManagementPage.class),
	ENTERPRISE_SETUP_MAINTENANCE("Setup and Maintenance", OracleCloudSetupAndMaintenancePage.class),

	// PAGES
	CREATE_TRANSACTION_PAGE("Create Transaction", OracleCloudCreateTransactionPage.class),

	MANAGE_TRANSACTION_PAGE("Manage Transactions", OracleCloudManageTransactionsPage.class),

	CREATE_INVOICE_PAGE("Create Invoice", OracleCloudCreateInvoicePage.class),

	MANAGE_INVOICES_PAGE("Manage Invoices", OracleCloudManageInvoicesPage.class),

	CREATE_PURCHASE_ORDER_PAGE("Create Order", OracleCloudCreatePurchaseOrderPage.class),

	MANAGE_PURCHASE_ORDERS_PAGE("Manage Orders", OracleCloudManagePurchaseOrdersPage.class),

	MANAGE_REQUISITIONS_PAGE("Manage Requisitions", OracleCloudManageReqsPage.class),

	CREATE_ORDER_PAGE("Create Order", OracleCloudCreateOrderPage.class),

	MANAGE_ORDERS_PAGE("Manage Orders", OracleCloudManageOrdersPage.class),

	MANAGE_LEGAL_ENTITY_PAGE("Manage Legal Entity", OracleCloudLegalEntityPage.class),

	MANAGE_BUSINESS_UNIT_PAGE("Manage Business Unit", OracleCloudManageBusinessUnitPage.class),

	MANAGE_TRANSACTION_TYPE_PAGE("Manage Transaction Type", OracleCloudManageTransactionTypePage.class),

	MANAGE_TRANSACTION_SOURCE_PAGE("Manage Transaction Source", OracleCloudManageTransactionSourcePage.class),

	MANAGE_PAYABLES_LOOKUPS_PAGE("Manage Payables Lookups", OracleCloudPayablesLookupsPage.class);

	private String pageName;
	private Class pageClass;

	/**
	 * Sets the name and class of a page,
	 * when an attempt is being made to access the page
	 * via a link
	 *
	 * @param linkText the displayed text for the link to select
	 * @param newPage  the class for the page attempting to access
	 */
	OracleCloudPageNavigationData( String linkText, Class<? extends OracleCloudBasePage> newPage )
	{
		this.pageName = linkText;
		this.pageClass = newPage;
	}
}
