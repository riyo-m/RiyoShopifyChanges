package com.vertex.quality.connectors.dynamics365.nav.enumes;

public enum NavMainMenuNavTabs {
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
    POSTED_SALES_INVOICE("Posted Sales Invoices"),
    POSTED_SALES_CREDIT_MEMO("Posted Sales Credit Memo"),
    POSTED_SALES_RETURN_RECEIPT("Posted Sales Return Receipts");

    public String value;

    NavMainMenuNavTabs( String val )
    {
        this.value = val;
    }
}
