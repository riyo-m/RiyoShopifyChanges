package com.vertex.quality.connectors.dynamics365.nav.enumes;

import com.vertex.quality.connectors.dynamics365.nav.pages.*;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavBasepage;
import lombok.Getter;

/**
 * represents page titles and classes of Business central
 *
 * @author osabha, cgajes
 */
@Getter
public enum NavPageTitles
{
    SALES_QUOTES_LIST_PAGE("Sales Quotes - Microsoft Dynamics NAV", NavSalesQuotesListPage.class),
    SALES_ORDERS_LIST_PAGE("Sales Orders - Microsoft Dynamics NAV", NavSalesOrderListPage.class),
    SALES_RETURN_ORDERS_LIST_PAGE("Sales Return Orders - Microsoft Dynamics NAV", NavSalesReturnOrderListPage.class),
    SALES_CREDIT_MEMO_LIST_PAGE("Sales Credit Memos - Microsoft Dynamics NAV",
            NavSalesCreditMemoListPage.class),
    SALES_INVOICE_LIST_PAGE("Sales Invoices - Microsoft Dynamics NAV", NavSalesInvoiceListPage.class);
    private String pageTitle;
    private Class<? extends NavBasepage> pageClass;

    NavPageTitles( String pageTitle, Class<? extends NavBasepage> pageClass )
    {
        this.pageTitle = pageTitle;
        this.pageClass = pageClass;
    }
}
