package com.vertex.quality.connectors.ariba.api.enums;

import lombok.Getter;

/**
 * Ariba valid Tax Types
 *
 * @author dgorecki
 */
@Getter
public enum AribaAPITaxType
{
	SALES_TAX("SalesTax"),
	VAT_TAX("VATTax"),
	GST_TAX("GSTTax"),
	PST_TAX("PSTTax"),
	HST_TAX("HSTTax"),
	QST_TAX("QSTTax"),
	WHT_TAX("WHTTax"),
	USE_TAX("UseTax"),
	SALES_STATE("SalesTaxState"),
	SALES_CITY("SalesTaxCity"),
	SALES_DISTRICT("SalesTaxDistrict");

	private String value;

	AribaAPITaxType( final String value )
	{
		this.value = value;
	}
}
