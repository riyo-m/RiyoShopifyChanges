package com.vertex.quality.connectors.salesforce.enums;

public class NavigateMenu
{
	public enum Vertex
	{
		VERTEX_CONFIGURATION("Vertex Configuration"),
		VERTEX_CPQ_CONFIGURATION("Vertex CPQ Configuration"),
		VERTEX_BILLING_CONFIGURATION("Billing Settings"),
		VERTEX_OB2B_CONFIGURATION("CC Vertex Config"),
		VERTEX_LB2B_CONFIGURATION("Vertex D2C Configuration"),
		VERTEX_LOM_CONFIGURATION("Vertex LOM Configuration"),
		CONFIGURATION_TAB("Configuration"),
		ADDRESSES_TAB("Addresses"),
		FIELD_MAPPINGS_TAB("Field Mappings"),
		LOGS_TAB("Logs"),
		VALIDITY_CHECK_TAB("Validity Check");

        public String text;

		Vertex( String text )
		{
			this.text = text;
		}
	}

	public enum Sales
	{
		ACCOUNTS_TAB("Account"),
		PRODUCTS_TAB("Product2"),
		OPPORTUNITIES_TAB("Opportunity"),
		QUOTES_TAB("Quotes"),
		INVOICE_SCHEDULERS_TAB("Invoice Scheduler"),
		MORE_TABS("MoreTabs"),
		ORDERS_TAB("Orders"),
		INVOICES_TAB("Invoices"),
		CREDIT_NOTES_TAB("Credit Notes");
        public String text;

		Sales( String text )
		{
			this.text = text;
		}
	}

	public enum Commerce
	{
		CONTACTS_TAB("Contacts"),
		CARTS_TAB("All Active Carts"),
		LAUREN_BOYLE_TAB("Lauren Boyle"),
		QA_B2B_VAT_TAB("QA_B2B_VAT TestAutomation"),
		JULIE_THOMPSON_TAB("Julie Thompson");
		public String text;

		Commerce( String text )
		{
			this.text = text;
		}
	}

	public enum OrderManagement
	{
		ACCOUNTS_TAB("Accounts"),
		ORDERS_TAB("Orders"),
		ORDER_SUMMARIES_TAB("Order Summaries"),
		FULFILLMENT_ORDER_TAB("Fulfillment Orders"),
		INVOICES_TAB("Invoices"),
		CREDIT_MEMOS_TAB("Credit Memos");
		public String text;

		OrderManagement(String text) {this.text = text; }
	}

	public enum AppMenu
	{
		VERTEX("Vertex"),
		SALES("Sales"),
		CPQ("Salesforce CPQ"),
		BILLING("Salesforce Billing"),
		B2B_COMMERCE("B2B Commerce"),
		COMMERCE("Commerce"),
		ORDERMANAGEMENT("Order Management");
		public String text;

		AppMenu( String text )
		{
			this.text = text;
		}
	}

	public enum FieldMappingTabs
	{
		QUOTE("Quote"),
		INVOICE("Invoice");
		public String text;

		FieldMappingTabs( String text )
		{
			this.text = text;
		}
	}
}
