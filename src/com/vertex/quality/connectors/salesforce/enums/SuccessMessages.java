package com.vertex.quality.connectors.salesforce.enums;

public enum SuccessMessages
{
	AREA_LOOKUP_SUCCESS("Tax Area Lookup URL: Connection Verified"),
	TAX_CALC_SUCCESS("Tax Calculation URL: Connection Verified"),
	CONFIG_SUCCESS("Configurations saved successfully.");

	public String text;

	SuccessMessages( String text )
	{
		this.text = text;
	}
}
