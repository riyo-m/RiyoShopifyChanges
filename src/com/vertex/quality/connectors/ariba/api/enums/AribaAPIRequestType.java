package com.vertex.quality.connectors.ariba.api.enums;

import lombok.Getter;

/**
 * Ariba API RequestType field values
 *
 * @author dgorecki
 */
@Getter
public enum AribaAPIRequestType
{
	TAX_CALCULATION("TaxCalculation"),
	ERP_POSTING("ERPPosting"),
	NOT_APPLICABLE("");

	private String value;

	AribaAPIRequestType( final String value )
	{
		this.value = value;
	}
}
