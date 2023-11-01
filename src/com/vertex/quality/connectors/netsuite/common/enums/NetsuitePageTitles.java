package com.vertex.quality.connectors.netsuite.common.enums;

import com.vertex.quality.connectors.netsuite.common.pages.*;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import com.vertex.quality.connectors.netsuite.common.pages.customers.*;
import com.vertex.quality.connectors.netsuite.common.pages.products.*;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsSearchPage;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsSearchResultPage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.NetsuiteOWGeneralPreferencesPage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.NetsuiteOWLocationPage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.NetsuiteOWSubsidiariesListPage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.NetsuiteOWSubsidiaryPage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.customers.NetsuiteOWCustomerPage;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.customers.NetsuiteAPICustomerPage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.*;
import com.vertex.quality.connectors.netsuite.scis.pages.NetsuiteSCISGeneralPreferencesPage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.NetsuiteSCGeneralPreferencesPage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.NetsuiteSCLocationPage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.customers.NetsuiteSCCustomerPage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.*;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.*;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.*;
import lombok.Getter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Contains page titles
 *
 * @author hho
 */
@Getter
public enum NetsuitePageTitles
{
	// Common page titles
	ADDRESS_PAGE("Address", NetsuiteAddressPopupPage.class),
	VERTEX_CUSTOMER_CLASS_PAGE("Vertex Customer Class", NetsuiteCustomerClassPopupPage.class),
	VERTEX_PRODUCT_CLASS_PAGE("Vertex Product Class", NetsuiteProductClassPopupPage.class),
	CONTACT_PAGE("Contact", NetsuiteContactPopupPage.class),
	LOCATION_PAGE("New Location", NetsuiteLocationPopupPage.class),
	SUBSIDIARIES("Subsidiaries - NetSuite (Vertex SuiteTax QA (TSTDRV1847016))", NetsuiteHomepage.class),
	SUBSIDIARY("Subsidiary - NetSuite (Vertex SuiteTax QA (TSTDRV1847016))", NetsuiteHomepage.class),
	HOME("Setup Manager - NetSuite (Vertex SuiteTax QA (TSTDRV1847016))", NetsuiteHomepage.class ),

	//TODO Update/Simplify these page tiltes
	//should only need the page class and the flavor of Netsuite we are using i.e. Single Company or Suitetax
	Default_Page("Home - NetSuite (Vertex SuiteTax QA (TSTDRV1847016))", NetsuiteHomepage.class ),

	// Single Company page titles
	SINGLE_COMPANY_GENERAL_PREFERENCES_PAGE("General Preferences - NetSuite (Vertex QA - Single Company " +
			"(TSTDRV1118550))", NetsuiteSCGeneralPreferencesPage.class),
	SINGLE_COMPANY_INSTALLED_BUNDLES_PAGE("Installed Bundles - NetSuite (Vertex QA - Single Company)",
		NetsuiteInstalledBundlesPage.class),
	SINGLE_COMPANY_SALES_ORDER_PAGE("Sales Order - NetSuite (Vertex QA - Single Company (TSTDRV1118550))",
		NetsuiteSCSalesOrderPage.class),
	SINGLE_COMPANY_TRANSACTION_SEARCH_PAGE("Transaction Search - NetSuite (Vertex QA - " +
			"Single Company (TSTDRV1118550))", NetsuiteTransactionsSearchPage.class),
	SINGLE_COMPANY_TRANSACTION_SEARCH_RESULTS_PAGE("Transaction Search:  Results - " +
			"NetSuite (Vertex QA - Single Company (TSTDRV1118550))", NetsuiteTransactionsSearchResultPage.class),
	SINGLE_COMPANY_CASH_REFUND_PAGE("Cash Refund - NetSuite (Vertex QA - (Vertex QA - " +
			"Single Company (TSTDRV1118550))", NetsuiteSCCashRefundPage.class),
	SINGLE_COMPANY_CASH_SALE_PAGE("Cash Sale - NetSuite (Vertex QA - Single Company (TSTDRV1118550))",
		NetsuiteSCCashSalePage.class),
	SINGLE_COMPANY_CREDIT_MEMO_PAGE("Credit Memo - NetSuite (Vertex QA - Single Company (TSTDRV1118550))",
		NetsuiteSCCreditMemoPage.class),
	SINGLE_COMPANY_INVOICE_PAGE("Invoice - NetSuite (Vertex QA - Single Company (TSTDRV1118550))",
		NetsuiteSCInvoicePage.class),
	SINGLE_COMPANY_QUOTE_PAGE("Quote - NetSuite (Vertex QA - Single Company (TSTDRV1118550))",
		NetsuiteSCQuotePage.class),
	SINGLE_COMPANY_CUSTOMER_LIST_PAGE("Customers - NetSuite (Vertex QA - Single Company (TSTDRV1118550))",
		NetsuiteCustomerListPage.class),
	SINGLE_COMPANY_CUSTOMER_PAGE("Customer - NetSuite (Vertex QA - Single Company (TSTDRV1118550))",
		NetsuiteSCCustomerPage.class),
	SINGLE_COMPANY_CUSTOM_LISTS_PAGE("Custom Lists - NetSuite (Vertex QA - Single Company (TSTDRV1118550))",
		NetsuiteCustomListsPage.class),
	SINGLE_COMPANY_CUSTOMER_CLASS_LIST_PAGE("Vertex Customer Class List - NetSuite (Vertex QA - " +
			"Single Company (TSTDRV1118550))", NetsuiteCustomerClassListPage.class),
	SINGLE_COMPANY_PRODUCT_CLASS_LIST_PAGE("Vertex Product Class List - NetSuite (Vertex QA - " +
			"Single Company (TSTDRV1118550))", NetsuiteProductClassListPage.class),
	SINGLE_COMPANY_CUSTOMER_CLASS_PAGE("Vertex Customer Class - NetSuite (Vertex QA - " +
			"Single Company (TSTDRV1118550))", NetsuiteCustomerClassPage.class),
	SINGLE_COMPANY_PRODUCT_CLASS_PAGE("Vertex Product Class - NetSuite (Vertex QA - " +
			"Single Company (TSTDRV1118550))", NetsuiteProductClassPage.class),
	SINGLE_COMPANY_CUSTOMER_SEARCH_PAGE("Customer Search - NetSuite (Vertex QA - Single Company (TSTDRV1118550))",
		NetsuiteCustomerSearchPage.class),
	SINGLE_COMPANY_ITEM_LIST_PAGE("Items - NetSuite (Vertex QA - Single Company (TSTDRV1118550))",
		NetsuiteItemListPage.class),
	SINGLE_COMPANY_SELECT_NEW_ITEM_PAGE("New Item - NetSuite (Vertex QA - Single Company (TSTDRV1118550))",
		NetsuiteSelectNewItemPage.class),
	SINGLE_COMPANY_DOWNLOAD_ITEM_PAGE("Download Item - NetSuite (Vertex QA - Single Company (TSTDRV1118550))",
		NetsuiteDownloadItemPage.class),
	SINGLE_COMPANY_GIFT_CERTIFICATE_PAGE("Gift Certificate - NetSuite (Vertex QA - Single Company (TSTDRV1118550))",
		NetsuiteGiftCertificatePage.class),
	SINGLE_COMPANY_PRODUCT_SEARCH_PAGE("Item Search - NetSuite (Vertex QA - Single Company (TSTDRV1118550))",
		NetsuiteProductSearchPage.class),
	SINGLE_COMPANY_INVENTORY_ITEM_PAGE("Inventory Item - NetSuite (Vertex QA - Single Company (TSTDRV1118550))",
		NetsuiteInventoryItemPage.class),
	SINGLE_COMPANY_COMPANY_INFORMATION_PAGE("Company Information - NetSuite (Vertex QA - " +
			"Single Company (TSTDRV1118550))", NetsuiteCompanyInformationPage.class),
	SINGLE_COMPANY_LOCATION_PAGE("Location - NetSuite (Vertex QA - Single Company (TSTDRV1118550))",
		NetsuiteSCLocationPage.class),
	SINGLE_COMPANY_VERTEX_CALL_DETAILS_PAGE("Vertex Call Details - NetSuite (Vertex QA - " +
			"Single Company (TSTDRV1118550))", NetsuiteVertexCallDetailsPage.class),
	SINGLE_COMPANY_LOCATION_LIST_PAGE("Locations - NetSuite (Vertex QA - Single Company (TSTDRV1118550))",
		NetsuiteLocationListPage.class),
	SINGLE_COMPANY_GLOBAL_SEARCH_PAGE("Global Search:  Results - NetSuite (Vertex QA - " +
			"Single Company (TSTDRV1118550))", NetsuiteGlobalSearchResultsPage.class),
	SINGLE_COMPANY_IMPORT_ASSISTANT_PAGE("Import Assistant - NetSuite (Vertex QA - " +
			"Single Company (TSTDRV1118550))", NetsuiteImportAssistantPage.class),
	SINGLE_COMPANY_LIST_SALES_ORDER_PAGE("Sales Orders - NetSuite (Vertex QA - " +
			"Single Company (TSTDRV1118550))", NetsuiteListSalesOrderPage.class),

	// One World page titles
	ONE_WORLD_GENERAL_PREFERENCES_PAGE("General Preferences - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteOWGeneralPreferencesPage.class),
	ONE_WORLD_INSTALLED_BUNDLES_PAGE("Installed Bundles - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteInstalledBundlesPage.class),
	ONE_WORLD_SALES_ORDER_PAGE("Sales Order - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteOWSalesOrderPage.class),
	ONE_WORLD_TRANSACTION_SEARCH_PAGE("Transaction Search - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteTransactionsSearchPage.class),
	ONE_WORLD_TRANSACTION_SEARCH_RESULTS_PAGE(
		"Transaction Search:  Results - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteTransactionsSearchResultPage.class),
	ONE_WORLD_CASH_REFUND_PAGE("Cash Refund - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteOWCashRefundPage.class),
	ONE_WORLD_CASH_SALE_PAGE("Cash Sale - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteOWCashSalePage.class),
	ONE_WORLD_CREDIT_MEMO_PAGE("Credit Memo - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteOWCreditMemoPage.class),
	ONE_WORLD_INVOICE_PAGE("Invoice - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))", NetsuiteOWInvoicePage.class),
	ONE_WORLD_QUOTE_PAGE("Quote - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))", NetsuiteOWQuotePage.class),
	ONE_WORLD_CUSTOMER_LIST_PAGE("Customers - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteCustomerListPage.class),
	ONE_WORLD_CUSTOMER_PAGE("Customer - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteOWCustomerPage.class),
	ONE_WORLD_CUSTOM_LISTS_PAGE("Custom Lists - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteCustomListsPage.class),
	ONE_WORLD_CUSTOMER_CLASS_LIST_PAGE("Vertex Customer Class List - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteCustomerClassListPage.class),
	ONE_WORLD_PRODUCT_CLASS_LIST_PAGE("Vertex Product Class List - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteProductClassListPage.class),
	ONE_WORLD_CUSTOMER_CLASS_PAGE("Vertex Customer Class - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteCustomerClassPage.class),
	ONE_WORLD_PRODUCT_CLASS_PAGE("Vertex Product Class - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteProductClassPage.class),
	ONE_WORLD_CUSTOMER_SEARCH_PAGE("Customer Search - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteCustomerSearchPage.class),
	ONE_WORLD_ITEM_LIST_PAGE("Items - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))", NetsuiteItemListPage.class),
	ONE_WORLD_SELECT_NEW_ITEM_PAGE("New Item - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteSelectNewItemPage.class),
	ONE_WORLD_DOWNLOAD_ITEM_PAGE("Download Item - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteDownloadItemPage.class),
	ONE_WORLD_GIFT_CERTIFICATE_PAGE("Gift Certificate - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteGiftCertificatePage.class),
	ONE_WORLD_PRODUCT_SEARCH_PAGE("Item Search - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteProductSearchPage.class),
	ONE_WORLD_INVENTORY_ITEM_PAGE("Inventory Item - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteInventoryItemPage.class),
	ONE_WORLD_COMPANY_INFORMATION_PAGE("Company Information - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteCompanyInformationPage.class),
	ONE_WORLD_SUBSIDIARY_PAGE("Subsidiary - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteOWSubsidiaryPage.class),
	ONE_WORLD_SUBSIDIARIES_LIST_PAGE("Subsidiaries - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteOWSubsidiariesListPage.class),
	ONE_WORLD_LOCATION_PAGE("Location - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteOWLocationPage.class),
	ONE_WORLD_VERTEX_CALL_DETAILS_PAGE("Vertex Call Details - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteVertexCallDetailsPage.class),
	ONE_WORLD_LOCATION_LIST_PAGE("Locations - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteLocationListPage.class),
	ONE_WORLD_GLOBAL_SEARCH_PAGE("Global Search:  Results - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))",
		NetsuiteGlobalSearchResultsPage.class),


	API_GENERAL_PREFERENCES_PAGE("General Preferences - NetSuite (Vertex SuiteTax QA (TSTDRV1847016))",
			NetsuiteAPIGeneralPreferencesPage.class),
	API_INVOICE_PAGE("Invoice - NetSuite (Vertex SuiteTax QA (TSTDRV1847016))",
			NetsuiteAPIInvoicePage.class),
	API_CREDIT_MEMO_PAGE("Credit Memo - NetSuite (Vertex SuiteTax QA (TSTDRV1847016))",
			NetsuiteAPICreditMemoPage.class),
	API_SALES_ORDER_PAGE("Sales Order - NetSuite (Vertex SuiteTax QA (TSTDRV1847016))",
			NetsuiteAPISalesOrderPage.class),
	API_PURCHASE_ORDER_PAGE("Purchase Order - NetSuite (Vertex SuiteTax QA (TSTDRV1847016))",
						 NetsuiteAPIPurchaseOrderPage.class),
	API_CASH_SALE_PAGE("Cash Sale - NetSuite (Vertex SuiteTax QA (TSTDRV1847016))",
			NetsuiteAPIInvoicePage.class),
	API_QUOTE_PAGE("Quote - NetSuite (Vertex SuiteTax QA (TSTDRV1847016))", NetsuiteAPISalesOrderPage.class),
	API_EXPENSE_REPORT_PAGE("Expense Report - NetSuite (Vertex SuiteTax QA (TSTDRV1847016))",
			NetsuiteAPIExpenseReportsPage.class),
	API_CASH_REFUND_PAGE("Cash Refund - NetSuite (Vertex SuiteTax QA (TSTDRV1847016))",
			NetsuiteAPIInvoicePage.class),
	API_CUSTOMER_PAGE("Customer - NetSuite (Vertex SuiteTax QA (TSTDRV1847016))",
		NetsuiteAPICustomerPage.class),

	//Leading environments
	API_LEADING_GENERAL_PREFERENCES_PAGE("General Preferences - NetSuite (Vertex SuiteTax QA (tstdrv2156815))",
			NetsuiteAPIGeneralPreferencesPage.class),
	API_LEADING_INVOICE_PAGE("Invoice - NetSuite (Vertex SuiteTax QA (tstdrv2156815))",
			NetsuiteAPIInvoicePage.class),
	API_LEADING_CREDIT_MEMO_PAGE("Credit Memo - NetSuite (Vertex SuiteTax QA (tstdrv2156815))",
			NetsuiteAPICreditMemoPage.class),
	API_LEADING_SALES_ORDER_PAGE("Sales Order - NetSuite (Vertex SuiteTax QA (tstdrv2156815))",
			NetsuiteAPISalesOrderPage.class),
	API_LEADING_PURCHASE_ORDER_PAGE("Purchase Order - NetSuite (Vertex SuiteTax QA (tstdrv2156815))",
			NetsuiteAPIPurchaseOrderPage.class),
	API_LEADING_CASH_SALE_PAGE("Cash Sale - NetSuite (Vertex SuiteTax QA (tstdrv2156815))",
			NetsuiteAPIInvoicePage.class),
	API_LEADING_QUOTE_PAGE("Quote - NetSuite (Vertex SuiteTax QA (tstdrv2156815))",
			NetsuiteAPISalesOrderPage.class),
	API_LEADING_EXPENSE_REPORT_PAGE("Expense Report - NetSuite (Vertex SuiteTax QA (tstdrv2156815))",
			NetsuiteAPIExpenseReportsPage.class),


	NEW_GENERAL_PREFERENCES_PAGE("General Preferences - NetSuite (VERTEX QA OW TRAILING New Install)",
								 NetsuiteAPIGeneralPreferencesPage.class),
	NEW_INVOICE_PAGE("Invoice - NetSuite (VERTEX QA OW TRAILING New Install)",
					 NetsuiteAPIInvoicePage.class),
	NEW_CREDIT_MEMO_PAGE("Credit Memo - NetSuite (VERTEX QA OW TRAILING New Install)",
						 NetsuiteAPICreditMemoPage.class),
	NEW_SALES_ORDER_PAGE("Sales Order - NetSuite (VERTEX QA OW TRAILING New Install)",
						 NetsuiteAPISalesOrderPage.class),
	NEW_PURCHASE_ORDER_PAGE("Purchase Order - NetSuite (VERTEX QA OW TRAILING New Install)",
							NetsuiteAPIPurchaseOrderPage.class),

	//SCIS environment
	SCIS_GENERAL_PREFERENCES_PAGE("General Preferences - NetSuite (Vertex SCIS/SCA QA (TSTDRV2366937))",
		NetsuiteSCISGeneralPreferencesPage.class);

	private String pageTitle;
	private Class<? extends NetsuitePage> pageClass;
	private Set<String> flavors;

	NetsuitePageTitles( String pageTitle, Class<? extends NetsuitePage> pageClass )
	{
		this.pageTitle = pageTitle;
		this.pageClass = pageClass;
		this.flavors = new HashSet<>(Arrays.asList("OneWorld","Single Company","SuiteTax","SCIS"));
	}
}
