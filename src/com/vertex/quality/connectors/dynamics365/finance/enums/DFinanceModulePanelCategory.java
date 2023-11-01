package com.vertex.quality.connectors.dynamics365.finance.enums;

import lombok.Getter;

/**
 * a dropdown/header for a group of links to pages on the site
 * This is in one of the panels from the left navigation menu
 *
 * Categories which start with 'SUB_' are subcategories inside other categories
 *
 * @author ssalisbury
 */
@Getter
public enum DFinanceModulePanelCategory
{
	//TODO to make this clearer (without examining code it's used in), should each have a list of modules it shows up in?
	// a list of supercategories that it shows up as a subcategory of?

	CHANNELS("Channels", false),
	DECLARATIONS("Declarations", false),
	SALES_TAX("Sales tax", true),
	SETUP("Setup", false),
	SUB_VERTEX("Vertex", true),
	ORGANIZATIONS("Organizations", false),
	ORDERS("Orders", false),
	SUB_ORDERS("Orders", true),
	PURCHASE_ORDERS("Purchase orders", false),
	INVOICES("Invoices", false),
	CUSTOMERS("Customers", false),
	PRODUCTS("Products", false),
	DISTRIBUTED_ORDER_MANAGEMENT("Distributed order management",false),
	BATCH_PROCESSING("Batch processing",true),
	IT("Retail and Commerce IT",false),
	VENDORS("Vendors", false),
	SALES_QUOTATIONS("Sales quotations", false),
	STORES("Stores", true),
	RETAIL_AND_COMMERCE_IT("Retail and Commerce IT", false),
	POS_POSTING("POS posting", true),
	JOURNAL_SETUP("Journal setup",false),
	PURCHASE_REQUISITIONS("Purchase requisitions", false),
	APPROVED_PURCHASE_REQUISITION_PROCESSING("Approved purchase requisition processing", true),
	ITEM_TASKS("Item tasks", false),
	PROJECTS("Projects", false),
	WAREHOUSE("Warehouse", true);
    private boolean isSubCategory;
	private String categoryName;

	DFinanceModulePanelCategory( final String link, final boolean isSubCategory )
	{
		this.categoryName = link;
		this.isSubCategory = isSubCategory;
	}
}
