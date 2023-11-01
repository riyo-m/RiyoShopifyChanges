package com.vertex.quality.connectors.dynamics365.nav.enumes;

import com.vertex.quality.connectors.dynamics365.nav.pages.*;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavSalesBasePage;
import lombok.Getter;

@Getter
public enum NavEditSalesPageTitles {
    SALES_QUOTES_PAGE("New - Sales Quote - Microsoft Dynamics NAV", NavSalesQuotesPage .class),
    SALES_ORDERS_PAGE("New - Sales Order - Microsoft Dynamics NAV", NavSalesOrderPage .class),
    SALES_RETURN_ORDERS_PAGE("New - Sales Return Order - Microsoft Dynamics NAV", NavSalesReturnOrderPage.class),
    SALES_CREDIT_MEMO_PAGE("New - Sales Credit Memo - Microsoft Dynamics NAV",
            NavSalesCreditMemoPage .class),
    SALES_INVOICE_PAGE("New - Sales Invoice - Microsoft Dynamics NAV", NavSalesInvoicePage .class);

    private String pageTitle;
    private Class<? extends NavSalesBasePage> pageClass;

    NavEditSalesPageTitles( String pageTitle, Class<? extends NavSalesBasePage> pageClass )
    {
        this.pageTitle = pageTitle;
        this.pageClass = pageClass;
    }
}

