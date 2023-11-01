package com.vertex.quality.connectors.accumatica.enums;

import com.vertex.quality.connectors.accumatica.pages.*;
import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaPostSignOnPage;

/**
 * Left menu names(Sub menu options) from acumatica application
 *
 * @author Saidulu Kodadala
 */
public enum AcumaticaLeftPanelLink
{
	//TODO fill in category linked Page classes

	//ORGANIZATION GLOBAL TAB

	//	Customer Management submenu tab
	LEADS("Leads", "ENTER", AcumaticaLeftPanelTab.WORK_AREA, null),
	OPPORTUNITIES("Opportunities", "ENTER", AcumaticaLeftPanelTab.WORK_AREA, null),
	CONTACTS("Contacts", "MANAGE", AcumaticaLeftPanelTab.WORK_AREA, null),
	BUSINESS_ACCOUNTS("Business Accounts", "MANAGE", AcumaticaLeftPanelTab.WORK_AREA, null),
	ACCOUNT_LOCATIONS("Account Locations", "MANAGE", AcumaticaLeftPanelTab.WORK_AREA, null),

	//	Organization Structure submenu tab
	BRANCHES("Branches", "CONFIGURE", AcumaticaLeftPanelTab.SOLE_TAB, null),

	//FINANCE GLOBAL TAB

	//	Cash Management submenu tab
	TRANSACTIONS("Transactions", "ENTER", AcumaticaLeftPanelTab.WORK_AREA, null),

	//	Accounts Receivable submenu tab
	INVOICE_AND_MEMOS("Invoice and Memos", "ENTER", AcumaticaLeftPanelTab.WORK_AREA, null),
	CASH_SALES("Cash Sales", "ENTER", AcumaticaLeftPanelTab.WORK_AREA, null),
	CUSTOMERS("Customers", "MANAGE", AcumaticaLeftPanelTab.WORK_AREA, AcumaticaCustomersPage.class),
	CUSTOMER_LOCATIONS("Customer Locations", "MANAGE", AcumaticaLeftPanelTab.WORK_AREA, null),
	CUSTOMER_CLASSES("Customer Classes", "SETUP", AcumaticaLeftPanelTab.CONFIGURATION,
		AcumaticaCustomerClassesPage.class),
	DISCOUNT_CODES("Discount Codes", "MANAGE", AcumaticaLeftPanelTab.CONFIGURATION, null),

	//	Taxes submenu tab
	VERTEX_SETUP("Vertex Setup", "VERTEX INTEGRATION", AcumaticaLeftPanelTab.CONFIGURATION,
		AcumaticaVertexSetupPage.class),

	//DISTRIBUTION GLOBAL TAB

	//	Inventory submenu tab
	STOCK_ITEMS("Stock Items", "MANAGE", AcumaticaLeftPanelTab.WORK_AREA, null),
	ITEM_CLASSES("Item Classes", "MANAGE", AcumaticaLeftPanelTab.CONFIGURATION, AcumaticaItemClassesPage.class),

	//	Sales Orders submenu tab
	SALES_ORDERS("Sales Orders", "ENTER", AcumaticaLeftPanelTab.WORK_AREA, AcumaticaSalesOrdersPage.class),
	SHIPMENTS("Shipments", "ENTER", AcumaticaLeftPanelTab.WORK_AREA, null),
	INVOICE("Invoices", "ENTER", AcumaticaLeftPanelTab.WORK_AREA, null),

	//CONFIGURATION GLOBAL TAB

	//Common Settings submenu tab
	ENABLE_DISABLE_FEATURES("Enable/Disable Features", "LICENSING", AcumaticaLeftPanelTab.SOLE_TAB,
		AcumaticaEnableDisableFeaturesPage.class),

	//SYSTEM GLOBAL TAB

	//	Customization submenu tab
	CUSTOMIZATION_PROJECTS("Customization Projects", "MANAGE", AcumaticaLeftPanelTab.SOLE_TAB,
		AcumaticaCustomizationProjectsPage.class);

	final String displayValue;
	final String category;
	final AcumaticaLeftPanelTab tab;
	final Class<? extends AcumaticaPostSignOnPage> linkedPage;

	AcumaticaLeftPanelLink( final String value, final String categoryName, final AcumaticaLeftPanelTab tab,
		final Class<? extends AcumaticaPostSignOnPage> link )
	{
		this.displayValue = value;
		this.category = categoryName;
		this.tab = tab;
		this.linkedPage = link;
	}

	/**
	 * Get UI display value
	 *
	 * @return UI display value
	 */
	public String getLabel( )
	{
		return displayValue;
	}

	public String getSection( )
	{
		return category;
	}

	public AcumaticaLeftPanelTab getTab( )
	{
		return tab;
	}

	public Class<? extends AcumaticaPostSignOnPage> getLinkedPage( )
	{
		return linkedPage;
	}

	@Override
	public String toString( )
	{
		return getLabel();
	}
}
