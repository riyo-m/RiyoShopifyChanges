package com.vertex.quality.connectors.kibo.enums;

/**
 * contains all the product categories for the store front.
 *
 * @author osabha
 */
public enum KiboProductCategory
{
	CAMPING("Camping"),
	BAGS("Bags"),
	CLOTHING("Clothing"),
	SHOES("Shoes"),
	ACCESSORIES("Accessories"),
	SALE("Sale");

	public String value;

	KiboProductCategory( String val )
	{
		this.value = val;
	}
}
