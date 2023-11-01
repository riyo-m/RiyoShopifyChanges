package com.vertex.quality.connectors.hybris.enums.admin;

/***
 * Enum for Tab Names from Admin - Orders Page
 */
public enum HybrisAdminOrdersPageTabNames
{
	PROPERTIES("Properties"),
	POSITIONS_PRICES("Positions and Prices"),
	PAYMENT_DELIVERY("Payment and Delivery"),
	OUTPUT_DOCUMENTS("Output Documents"),
	VOUCHERS("Vouchers"),
	PROMOTIONS("Promotions"),
	PROMOTION_ENG_RESULTS("Promotion Engine Results"),
	COUPONS("Coupons"),
	ORDER_HISTORY("Order history"),
	CONSIGNMENTS("Consignments"),
	RELATED_CRONJOBS("Related CronJobs"),
	FRAUD_REPORTS("Fraud reports"),
	TICKETS("Tickets"),
	ADMINISTRATION("Administration");

	String tabName;

	HybrisAdminOrdersPageTabNames( String tab )
	{
		this.tabName = tab;
	}

	public String getTabName( )
	{
		return tabName;
	}
}