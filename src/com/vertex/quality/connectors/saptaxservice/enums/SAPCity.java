package com.vertex.quality.connectors.saptaxservice.enums;

/**
 * Represents the cities in the world
 *
 * @author unaqvi
 */
public enum SAPCity {
	NEW_BRUNSWICK("New Brunswick", "NB"),
	QUEBEC("Quebec", "QC"),
	NUNAVUT("Nunavut", "NU"),
	NEWFOUNDLAND_LABRADOR("Newfoundland and Labrador", "NL"),
	SASKATCHEWAN("Saskatchewan", "SK"),
	YUKON("Yukon", "YT"),
	NOVA_SCOTIA("Nova Scotia", "NS"),
	PENNSYLVANIA("Pennsylvania", "PA"),
	ROMA("Roma", "RM"),
	MILANO("Milano", "MI"),
	ZURICH("Zurich","ZC"),
	PARIS("Paris","PR"),
	HERTOGENSBOSCH("'s-Hertogenbosch","SH"),
	LE_HAVRE("Le Havre","LH"),
	SAN_MARINO("San Marino","SM"),
	VATICAN_CITY("Vatican","VC"),
	YUHANG_QU("Yuhang Qu","YQ");

	private String fullName;
	private String abbreviation;

	SAPCity(String fullName, String abbreviation) {
		this.fullName = fullName;
		this.abbreviation = abbreviation;

	}

	/**
	 * Gets the full name of the city
	 *
	 * @return the full name of the city
	 */
	public String getFullName() {
		return this.fullName;
	}

	/**
	 * Gets the city's abbreviation
	 *
	 * @return the city's abbreviation
	 */
	public String getAbbreviation() {
		return this.abbreviation;
	}
}