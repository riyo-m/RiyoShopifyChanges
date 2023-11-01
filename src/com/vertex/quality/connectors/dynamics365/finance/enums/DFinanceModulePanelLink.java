package com.vertex.quality.connectors.dynamics365.finance.enums;

import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import lombok.Getter;

/**
 * link from the left navigation menus to pages on the site
 *
 * @author ssalisbury
 */
@Getter
public enum DFinanceModulePanelLink
{
	//TODO to make this clearer (without examining code it's used in),
	// should each have a list of categories it shows up in?

	VERTEX_XML_INQUIRY("Vertex XML Inquiry", DFinanceXMLInquiryPage.class),
	VERTEX_TAX_PARAMETERS("Vertex tax parameters", DFinanceSettingsPage.class),
	LEGAL_ENTITIES("Legal entities", DFinanceLegalEntitiesPage.class),
	ALL_SALES_ORDERS("All sales orders", DFinanceAllSalesOrdersPage.class),
	ALL_RETURN_ORDERS("All return orders", DFinanceAllSalesOrdersPage.class),
	ALL_PURCHASE_ORDERS("All purchase orders", DFinanceAllPurchaseOrdersPage.class),
	ALL_FREE_TEXT_INVOICES("All free text invoices", DFinanceAllSalesOrdersPage.class),
	ALL_QUOTATIONS("All quotations", DFinanceAllQuotationsPage.class),
	OPEN_VENDOR_INVOICES("Open vendor invoices", DFinanceOpenVendorInvoicesPage.class),
	ALL_CUSTOMERS("All customers", DFinanceAllCustomersPage.class),
	RELEASED_PRODUCTS("Released products", DFinanceReleasedProductsPage.class),
	DOM_PROCESSOR_JOB_SETUP("DOM processor job setup", DFinanceBatchProcessingPage.class),
	DISTRIBUTION_SCHEDULE("Distribution schedule",DFinanceDistributionSchedulePage.class),
	ALL_VENDORS("All vendors", DFinanceAllPurchaseOrdersPage.class),
	SETTLE_AND_POST_SALES_TAX("Settle and post sales tax", DFinanceAllSalesOrdersPage.class),
	STATEMENTS("Statements", DFinanceAllSalesOrdersPage.class),
	DISTRIBUTE_TAX_REQUEST("Distribute tax request", DFinanceAllSalesOrdersPage.class),
	VALIDATE_STORE_TRANSACTIONS("Validate store transactions", DFinanceAllSalesOrdersPage.class),
	VERTEX_DISTRIBUTED_TAX_ERRORS("Vertex distributed tax errors", DFinanceVertexDistributedTaxErrorsPage.class),
	JOURNAL_NAMES("Journal names", DFinanceXMLInquiryPage.class),
	PENDING_VENDOR_INVOICES("Pending vendor invoices", DFinanceOpenVendorInvoicesPage.class),
	ACCOUNTS_PAYABLE_WORKFLOWS("Accounts payable workflows", DFinanceCreatePurchaseOrderPage.class),
	ALL_PURCHASE_REQUISITIONS("All purchase requisitions", DFinanceCreatePurchaseOrderPage.class),
	RELEASE_APPROVED_PURCHASE_REQUISITIONS("Release approved purchase requisitions", DFinanceCreatePurchaseOrderPage.class),
	ALL_PROJECTS("All projects", DFinanceAllProjectsPage.class),
	ITEM_REQUIREMENTS("Item requirements", DFinanceItemRequirements.class),
	ACCOUNTS_RECEIVABLE_PARAMETERS("Accounts receivable parameters",DFinanceAccountsRecParametersPage.class),
	VERTEX_INVOICE_POSTING_QUEUE("Vertex invoice posting queue", DFinanceVertexInvoicePostingQueuePage.class),
	SYNCHRONIZE_ORDERS("Synchronize orders", DFinanceSynchronizeOrdersPage.class),
	WAREHOUSES("Warehouses", DFinanceWarehousesPage.class);
	private String linkName;
	private Class<? extends DFinanceBasePage> linkedPage;

	DFinanceModulePanelLink( final String link, final Class<? extends DFinanceBasePage> page )
	{
		this.linkName = link;
		this.linkedPage = page;
	}
}
