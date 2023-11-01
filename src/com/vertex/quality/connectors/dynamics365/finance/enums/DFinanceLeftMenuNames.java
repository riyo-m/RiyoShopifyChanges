package com.vertex.quality.connectors.dynamics365.finance.enums;

import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceAllPurchaseOrdersPage;

/**
 * D365 Left menu names
 *
 * @author Saidulu Kodadala
 */
public enum DFinanceLeftMenuNames
{
	TAX("Tax"),
	ACCOUNTS_RECEIVABLE("Accounts receivable"),
	ACCOUNTS_PAYABLES("Accounts payable"),
	SALES_AND_MARKETING("Sales and marketing"),
	SETUP("Setup"),
	VERTEX("Vertex"),
	VERTEX_XML_INQUIRY("Vertex XML Inquiry"),
	VERTEX_TAX_PARAMETERS("Vertex tax parameters"),
	VERTEX_FLEX_FIELDS("Vertex flex fields"),
	ALL_SALES_ORDERS("All sales orders"),
	ALL_PURCHASE_ORDERS("All purchase orders"),
	ORDERS("Orders"),
	VENDORS("Vendors"),
	ALL_VENDORS("All vendors"),
	PURCHASE_ORDERS("Purchase orders"),
	INVOICES("Invoices"),
	OPEN_VENDOR_INVOICES("Open vendor invoices"),
	PENDING_VENDOR_INVOICES("Pending vendor invoices"),
	ACCOUNTS_PAYABLE_WORKFLOWS("Accounts payable workflows"),
	CUSTOMERS("Customers"),
	ALL_CUSTOMERS("All customers"),
	PRODUCT_INFORMATION_MANAGEMENT("Product information management"),
	PRODUCTS("Products"),
	RELEASED_PRODUCTS("Released products"),
	CHANNEL_SETUP("Channel setup"),
	POS_SETUP("POS setup"),
	REGISTER("Registers"),
	RETAIL("Retail and Commerce"),
	DISCOUNT("Pricing and discounts"),
	ALL_DISCOUNT("All discounts"),
	PROJECT_MANAGEMENT("Project management and accounting"),
	QUOTATIONS("Quotations"),
	PROJECT_QUOTATIONS("Project quotations"),
	INVOICE_JOURNAL("Invoice journal"),
	INVOICE_APPROVAL("Invoice approval"),
	INVOICE_REGISTER("Invoice register"),
	INVOICE_POOL("Invoice pool"),
	GENERAL_LEDGER("General ledger"),
	JOURNAL_SETUP("Journal setup"),
	JOURNAL_NAMES("Journal names"),
	VENDOR_INVOICE_ENTRY("Vendor invoice entry"),
	WORKSPACES("Workspaces"),
	PROCUREMENT_AND_SOURCING("Procurement and sourcing"),
	PURCHASE_REQUISITIONS("Purchase requisitions"),
	ALL_PURCHASE_REQUISITIONS("All purchase requisition"),
	PROCUREMENT_AND_SOURCING_PARAMETERS("Procurement and sourcing parameters");


	String linkName;

	DFinanceLeftMenuNames( final String link )
	{
		this.linkName = link;
	}

	/**
	 * Get the data from enum
	 *
	 * @return
	 */
	public String getData( )
	{
		return linkName;
	}
}
