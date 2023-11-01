package com.vertex.quality.common.enums;

public enum VertexConfigurations
{
	US_TAX_GROUP("US"),
	COMPANY_NAME_OF_SELLER("ERP1"),
	DELIVERY_COST_TAX_CLASS("FREIGHT");

	String text;

	VertexConfigurations( String text )
	{
		this.text = text;
	}

	public String gettext( )
	{
		return text;
	}
}
