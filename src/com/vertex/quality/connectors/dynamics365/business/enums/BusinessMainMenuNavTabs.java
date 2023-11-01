package com.vertex.quality.connectors.dynamics365.business.enums;

/**
 * home page main nav panel menu names.
 *
 * @author osabha
 */
public enum BusinessMainMenuNavTabs
{
	FINANCE("Finance"),
	CASH_MANAGEMENT("Cash Management"),
	SALES("Sales"),
	PURCHASING("Purchasing"),
	APPROVALS("Approvals"),
	SELF_SERVICE("Self-Service"),
	SETUP_EXTENSIONS("Setup & Extensions"),
	INTELLIGENT_CLOUD_INSIGHTS("Intelligent Cloud Insights"),
	ORDER("Order"),
	QUOTE("Quote"),
	INVOICE("Invoice"),
	CREDIT_MEMO("Credit Memo"),
	SALES_RETURN_ORDER("Sales Return Orders"),
	RETURN_ORDER("Return Order"),
	POSTED_SALES_INVOICE("Posted Sales Invoice"),
	POSTED_SERVICE_INVOICE("Posted Service Invoice"),
	POSTED_SALES_CREDIT_MEMO("Posted Sales Credit Memo"),
	POSTED_SALES_RETURN_RECEIPT("Posted Sales Return Receipts"),
	SERVICE_MANAGEMENT("Service Management");

	public String value;

	BusinessMainMenuNavTabs( String val )
	{
		this.value = val;
	}
}
