package com.vertex.quality.connectors.episerver.common.enums;

public enum CreditCardDetails
{
	// @formatter:off
	NAME("EpiAutoCCName"),
	CARD_NUMBER("4111111111111114"),
	SECURITY_CODE("123"),
	ADDRESS_LINE1("100 Universal City Plaza"),
	YEAR("2024"),
	MONTH("4");
	// @formatter:on

	public String text;

	CreditCardDetails( String text )
	{
		this.text = text;
	}
}
