package com.vertex.quality.connectors.episerver.common.enums;

public enum Status
{
	// @formatter:off
	GOOD("good"),
	BAD("bad");
	// @formatter:on

	public String text;

	Status( String text )
	{
		this.text = text;
	}
}
