package com.vertex.quality.connectors.saptaxservice.enums;

/**
 * Represents the states and provinces in the world
 *
 * @author unaqvi
 */
public enum SAPRegion
{
	ROME("Rome","RM"),
	MILAN("Milan","MI"),
	ALBERTA("Alberta", "AB"),
	BRITISH_COLUMBIA("British Columbia", "BC"),
	NEW_BRUNSWICK("New Brunswick", "NB"),
	QUEBEC("Quebec", "QC"),
	NUNAVUT("Nunavut", "NU"),
	NEWFOUNDLAND_LABRADOR("Newfoundland and Labrador", "NL"),
	SASKATCHEWAN("Saskatchewan", "SK"),
	YUKON("Yukon", "YT"),
	NOVA_SCOTIA("Nova Scotia", "NS"),
	PENNSYLVANIA("Pennsylvania", "PA"),
	NORTH_BRABANT("North Brabant","NB"),
	CANTON_OF_ZURICH("Canton of Zurich","CZ"),
	ISLAND_OF_FRANCE("Ile-de-France","LR"),
	NORMANDY("Normandy","NY"),
	NULL("null","null");

	private String fullName;
	private String abbreviation;

	SAPRegion( String fullName, String abbreviation )
	{
		this.fullName = fullName;
		this.abbreviation = abbreviation;
	}

	/**
	 * Gets the full name of the country
	 *
	 * @return the full name of the country
	 */
	public String getFullName( )
	{
		return this.fullName;
	}

	/**
	 * Gets the province's abbreviation
	 *
	 * @return the province's abbreviation
	 */
	public String getAbbreviation( )
	{
		return this.abbreviation;
	}
}
