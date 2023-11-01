package com.vertex.quality.common.enums;

public enum CreditCard
{
	TYPE("Visa"),
	NUMBER("4111111111111111"),
	NAME("Auto Test"),
	EXPIRY_MONTH("12"),
	EXPIRY_YEAR("2024"),
	CODE("123");

	public String text;

	CreditCard( String text )
	{
		this.text = text;
	}
}
