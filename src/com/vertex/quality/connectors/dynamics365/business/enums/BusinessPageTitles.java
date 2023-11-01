package com.vertex.quality.connectors.dynamics365.business.enums;

import com.vertex.quality.connectors.dynamics365.business.pages.*;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.*;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import lombok.Getter;

/**
 * represents page titles and classes of Business central
 *
 * @author osabha, cgajes
 */
@Getter
public enum BusinessPageTitles
{
	SALES_QUOTES_LIST_PAGE("Sales Quotes - Dynamics 365 Business Central", BusinessSalesQuotesListPage.class),
	SALES_ORDERS_LIST_PAGE("Sales Orders - Dynamics 365 Business Central", BusinessSalesOrderListPage.class),
	SALES_RETURN_ORDERS_LIST_PAGE("Sales Return Orders - Dynamics 365 Business Central", BusinessSalesReturnOrderListPage.class),
	SALES_CREDIT_MEMO_LIST_PAGE("Sales Credit Memos - Dynamics 365 Business Central",
		BusinessSalesCreditMemoListPage.class),
	SALES_INVOICE_LIST_PAGE("Sales Invoices - Dynamics 365 Business Central", BusinessSalesInvoiceListPage.class),
	SETUP_EXTENSIONS_MANUAL_SETUP_PAGE("Manual Setup - Dynamics 365 Business Central", BusinessManualSetupPage.class),
	SETUP_EXTENSIONS_EXTENSIONS_PAGE("Extensions - Dynamics 365 Business Central", BusinessExtensionsPage.class),

	PURCHASE_QUOTES_LIST_PAGE("Purchase Quotes - Dynamics 365 Business Central",BusinessPurchaseQuotesListPage.class),
	PURCHASE_ORDERS_LIST_PAGE("Purchase Orders - Dynamics 365 Business Central", BusinessPurchaseOrdersListPage.class),
	PURCHASE_INVOICES_LIST_PAGE("Purchase Invoices - Dynamics 365 Business Central", BusinessPurchaseInvoicesListPage.class),
	PURCHASE_CREDIT_MEMOS_LIST_PAGE("Purchase Credit Memos - Dynamics 365 Business Central", BusinessPurchaseCreditMemoListPage.class),
	PURCHASE_RETURN_ORDERS_LIST_PAGE("Purchase Return Orders - Dynamics 365 Business Central", BusinessPurchaseReturnOrdersListPage.class),
	BUSINESS_SERVICE_QUOTES_LIST_PAGE("Service Quotes - Dynamics 365 Business Central",BusinessServiceQuotesListPage.class),
	BUSINESS_SERVICE_ORDERS_LIST_PAGE("Service Orders - Dynamics 365 Business Central",BusinessServiceOrderListPage.class),
	BUSINESS_SERVICE_CREDIT_MEMOS_LIST_PAGE("Service Credit Memos - Dynamics 365 Business Central",BusinessServiceCreditMemoListPage.class);

	private String pageTitle;
	private Class<? extends BusinessBasePage> pageClass;

	BusinessPageTitles( String pageTitle, Class<? extends BusinessBasePage> pageClass )
	{
		this.pageTitle = pageTitle;
		this.pageClass = pageClass;
	}
}
