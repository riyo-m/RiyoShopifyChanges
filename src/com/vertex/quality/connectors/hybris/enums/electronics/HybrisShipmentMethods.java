package com.vertex.quality.connectors.hybris.enums.electronics;

/**
 * Enum which holds all Shipment Method names with their Values which are used in test scripts
 *
 * @author Nagaraju Gampa
 */
public enum HybrisShipmentMethods
{
	STANDARD("STANDARD DELIVERY - 3-5 BUSINESS DAYS - $9.99", "standard-net"),
	PREMIUM("PREMIUM DELIVERY - 1-2 BUSINESS DAYS - $16.99", "premium-net"),
	STANDARDCANADA("STANDARD DELIVERY - 3-5 BUSINESS DAYS - $7.49", "standard-net");

	public String name;
	public String value;

	HybrisShipmentMethods( String name, String value )
	{
		this.name = name;
		this.value = value;
	}
}
