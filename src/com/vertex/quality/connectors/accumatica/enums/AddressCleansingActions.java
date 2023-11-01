package com.vertex.quality.connectors.accumatica.enums;

public enum AddressCleansingActions
{
	// @formatter:off
	CONFIRM("Confirm"),
	IGNORE("Ignore");
	// @formatter:on

	String dataName;

	AddressCleansingActions( String data )
	{
		this.dataName = data;
	}

	public String getData( )
	{
		return dataName;
	}
}
