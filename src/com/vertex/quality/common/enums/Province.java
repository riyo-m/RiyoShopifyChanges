package com.vertex.quality.common.enums;

public enum Province
{
	BC("British Columbia", "BC"),
	ON("Ontario", "ON"),
	QC("Quebec", "QC"),
	NB("New Brunswick", "NB");

	public String fullName;
	public String abbreviation;

	Province( String fullName, String abbreviation )
	{
		this.fullName = fullName;
		this.abbreviation = abbreviation;
	}
}
