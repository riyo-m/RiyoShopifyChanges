package com.vertex.quality.connectors.dynamics365.business.enums;

import com.vertex.quality.connectors.dynamics365.business.pages.*;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.*;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import lombok.Getter;

/**
 * represent all the page titles and classes that extend Business Edit Sales Base Page
 *
 * @author osabha, cgajes
 */
@Getter
public enum BusinessEditSalesPageTitles
{
	SALES_QUOTES_PAGE("New - Sales Quote - Dynamics 365 Business Central", BusinessSalesQuotesPage.class),
	SALES_ORDERS_PAGE("New - Sales Order - Dynamics 365 Business Central", BusinessSalesOrderPage.class),
	SALES_RETURN_ORDERS_PAGE("New - Sales Return Order - Dynamics 365 Business Central", BusinessSalesReturnOrderPage.class),
	SALES_CREDIT_MEMO_PAGE("New - Sales Credit Memo - Dynamics 365 Business Central",
		BusinessSalesCreditMemoPage.class),
	SALES_INVOICE_PAGE("New - Sales Invoice - Dynamics 365 Business Central", BusinessSalesInvoicePage.class),
	PURCHASE_ORDERS_PAGE("New - Purchase Order - Dynamics 365 Business Central", BusinessPurchaseOrderPage.class),
	PURCHASE_QUOTES_PAGE("New - Purchase Quote - Dynamics 365 Business Central", BusinessPurchaseQuotePage.class),
	PURCHASE_INVOICES_PAGE("New - Purchase Invoice - Dynamics 365 Business Central", BusinessPurchaseInvoicePage.class),
	PURCHASE_CREDIT_MEMOS_PAGE("New - Purchase Credit Memo - Dynamics 365 Business Central", BusinessPurchaseCreditMemoPage.class),
	PURCHASE_RETURN_ORDER_PAGE("New - Purchase Return Order - Dynamics 365 Business Central", BusinessPurchaseReturnOrderPage.class),
	BUSINESS_SERVICE_QUOTES_PAGE("New - Service Quote - Dynamics 365 Business Central",BusinessServiceQuotesPage.class),
	BUSINESS_SERVICE_ORDERS_PAGE("New - Service Order - Dynamics 365 Business Central",BusinessServiceOrdersPage.class),
	BUSINESS_SERVICE_CREDIT_MEMOS_PAGE("New - Service Credit Memo - Dynamics 365 Business Central",BusinessServiceCreditMemoPage.class);

	private String pageTitle;
	private Class<? extends BusinessSalesBasePage> pageClass;

	BusinessEditSalesPageTitles( String pageTitle, Class<? extends BusinessSalesBasePage> pageClass )
	{
		this.pageTitle = pageTitle;
		this.pageClass = pageClass;
	}
}
