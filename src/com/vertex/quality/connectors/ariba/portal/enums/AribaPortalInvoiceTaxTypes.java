package com.vertex.quality.connectors.ariba.portal.enums;

import lombok.Getter;

/**
 * Represents the diffrent tax types for the non po invoice created through the portal site.
 *
 * @author osabha
 */
@Getter
public enum AribaPortalInvoiceTaxTypes
{
	STATE_SALES_TAX("SalesTaxState"),
	CITY_SALES_TAX("SalesTaxCity"),
	COUNTY_SALES_TAX("SalesTaxCounty");

	private String type;

	AribaPortalInvoiceTaxTypes( final String taxType )
	{
		this.type = taxType;
	}
}
