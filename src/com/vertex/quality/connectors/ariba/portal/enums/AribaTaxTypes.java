package com.vertex.quality.connectors.ariba.portal.enums;

/**
 * Enum representing the potential tax types in the Ariba system
 */
public enum AribaTaxTypes
{
	SALES("SalesTax", false),
	SELLER_USE("SalesTax", false),
	CONSUMERS_USE("SalesTax", false),
	VAT("SalesTax", true),
	VAT_IMPORT("SalesTax", false),
	NONE("SalesTax", false);

	public String name;
	public boolean isDeductible;

	AribaTaxTypes( final String name, boolean isDeductible )
	{
		this.name = name;
		this.isDeductible = isDeductible;
	}
}
