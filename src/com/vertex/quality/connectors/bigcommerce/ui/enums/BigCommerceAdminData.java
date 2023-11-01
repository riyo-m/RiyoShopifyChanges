package com.vertex.quality.connectors.bigcommerce.ui.enums;

/**
 * Data for bigcommerce admin.
 *
 * @author rohit.mogane
 */
public enum BigCommerceAdminData
{
	DEFAULT_PRODUCT_NAME("[Sample] Canvas Laundry Cart"),
	CUSTOMER_EMAIL("thomas.demartinis@vertexinc.com"),
	EXPECTED_TAX_ESTIMATE("$6.66"),
	SHIPPING_FIRST_NAME("Marc"),
	SHIPPING_LAST_NAME("Viertel"),
	BILLING_FIRST_NAME("Rosa"),
	BILLING_LAST_NAME("Minski"),
	STATES("states"),
	US_STATES("us-states"),
	CA_STATES("ca-states"),
	IT_STATES("it-states"),
	CA("canada"),
	IT("international"),
	US("united-states"),
	COMPANY_REGISTRATIONS("/bigcommerce/registrations/company_registrations.json");

	public String data;

	BigCommerceAdminData( String data )
	{
		this.data = data;
	}
}
