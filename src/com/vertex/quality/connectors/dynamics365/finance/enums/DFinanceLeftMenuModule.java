package com.vertex.quality.connectors.dynamics365.finance.enums;

import lombok.Getter;

/**
 * buttons which open a panel with a collection of links to pages in some section of the site
 *
 * @author ssalisbury
 */
@Getter
public enum DFinanceLeftMenuModule
{
	TAX("Tax"),
	ACCOUNTS_RECEIVABLE("Accounts receivable"),
	ACCOUNTS_PAYABLES("Accounts payable"),
	ORGANIZATION_ADMINISTRATION("Organization administration"),
	SALES_AND_MARKETING("Sales and marketing"),
	PRODUCT_INFORMATION_MANAGEMENT("Product information management"),
	RETAIL_AND_COMMERCE("Retail and Commerce"),
	GENERAL_LEDGER("General ledger"),
	PROCUREMENT_AND_SOURCING("Procurement and sourcing"),
	PROJECT_MANAGEMENT_AND_ACCOUNTING("Project management and accounting"),
	WORKSPACES("Workspaces"),
	WAREHOUSE_MANAGEMENT("Warehouse management");
	private String moduleName;

	DFinanceLeftMenuModule( final String link )
	{
		this.moduleName = link;
	}
}
