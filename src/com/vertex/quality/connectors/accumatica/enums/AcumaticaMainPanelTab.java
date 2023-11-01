package com.vertex.quality.connectors.accumatica.enums;

/**
 * Common tab names from different pages
 *
 * @author Saidulu Kodadala
 */
public enum AcumaticaMainPanelTab
{
	//TODO possibly move these things to inner enums in the page classes that have them, because this seems to jumble together stuff from a bunch of different pages

	// @formatter:off
	DELIVERY_SETTINGS("Delivery Settings"),
	GENERAL_INFO("General Info"),
	FINANCIAL_SETTINGS("Financial Settings"),
	SHIPPING_SETTINGS("Shipping Settings"),
	TAX_DETAILS("Tax Details"),
	TOTALS("Totals"),
	PRICE_COST_INFO("Price/Cost Info"),
	WAREHOUSE_DETAILS("Warehouse Details");
	// @formatter:on

	String displayValue;

	AcumaticaMainPanelTab( String value )
	{
		this.displayValue = value;
	}

	/**
	 * Get UI display value
	 *
	 * @return
	 */
	public String getValue( )
	{
		return displayValue;
	}

	@Override
	public String toString( )
	{
		return getValue();
	}
}
