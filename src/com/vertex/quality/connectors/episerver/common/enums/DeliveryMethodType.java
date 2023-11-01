package com.vertex.quality.connectors.episerver.common.enums;

public enum DeliveryMethodType
{
	// @formatter:off
	EXPRESS("Express"),
	FAST("Fast"),
	REGULAR("Regular");
	// @formatter:on

	public String text;

	DeliveryMethodType( String text )
	{
		this.text = text;
	}
}
