package com.vertex.quality.connectors.netsuite.common.enums;

import lombok.Getter;

/**
 * Contains the commonly used navigation menu titles
 *
 * @author hho
 */
@Getter
public enum NetsuiteNavigationMenuTitles
{
	// data-titles for needed menus and submenus under "Customization"
	CUSTOMIZATION_MENU("Customization"),
	SUITEBUNDLER("SuiteBundler"),
	SEARCH_AND_INSTALL_BUNDLES("Search & Install Bundles"),
	LIST_INSTALLED_BUNDLES("List"),
	LIST_RECORDS_FIELDS("Lists, Records, & Fields"),
	LISTS_CLASSES("Lists"),
	ENTITY_FIELDS("Entity Fields"),

	// data-titles for needed menus and submenus under "Setup" to reach the general
	// preferences page
	SETUP_MENU("Setup"),
	COMPANY("Company"),
	GENERAL_PREFERENCES("General Preferences"),
	SUBSIDIARIES("Subsidiaries"),
	COMPANY_INFORMATION("Company Information"),
	LOCATIONS("Locations"),
	IMPORT_EXPORT("Import/Export"),
	IMPORT_CSV_RECORDS("Import CSV Records"),

	// data-titles for needed menus and submenus under "Transactions"
	TRANSACTIONS("Transactions"),
	SALES("Sales"),
	ENTER_SALES_ORDER("Enter Sales Orders"),
	LIST("List"),
	SEARCH_SALES_ORDER("Search"),
	EMPLOYEES("Employees"),
	PREPARE_QUOTES("Prepare Quotes"),
	ISSUE_CREDIT_MEMOS("Issue Credit Memos"),
	CREATE_INVOICES("Create Invoices"),
	ENTER_EXPENSE_REPORTS("Enter Expense Reports"),
	ENTER_CASH_SALES("Enter Cash Sales"),
	PURCHASES("Purchases"),
	BLANKET_ORDER("Enter Blanket Purchase Order"),
	PURCHASE_ORDER("Enter Purchase Orders"),

	// data-titles for needed menus and submenus under "Lists"
	LISTS("Lists"),
	RELATIONSHIPS("Relationships"),
	CUSTOMERS("Customers"),
	ACCOUNTING("Accounting"),
	ITEMS("Items"),
	NEW("New"),
	SEARCH("Search"),
	VERTEX_INVOICE_POSTING("Vertex Invoice Posting"),
	SCHEDULE_VERTEX_BATCH_PROCESS("Schedule Vertex Batch Process");

	private String title;

	NetsuiteNavigationMenuTitles( String title )
	{
		this.title = title;
	}
}
