package com.vertex.quality.common.enums;

public enum Country
{
	CAN("CA", "CAN", "Canada"),
	USA("US", "USA", "United States"),
	BZ("BZ", "BLZ", "Belize"),
	HKG("HK", "HKG", "Hong Kong"),
	IND("IN", "IND", "India"),
	FR("FR", "FRA", "France"),
	DE("DE","DEU","Germany"),
	BE("BE","BEL","Belgium"),
	DK("DK","DK","Denmark"),
	GR("GR", "GRC", "Greece"),
	AT("AT", "AUT", "Austria"),
	SG("SG", "SGP", "Singapore"),
	JP("JP", "JPN", "Japan"),
	CRI("CR", "CRI", "Costa Rica"),
	BLZ("BLZ", "BLZ", "Belize"),
	COL("CO", "COL", "Colombia"),
	PH("PH", "PHL", "Philippines"),
	PK("PK", "PAK", "Pakistan"),
	VN("VN", "VNM", "Vietnam");

	public String iso2code;
	public String iso3code;
	public String fullName;

	Country( String iso2code, String iso3code, String fullName )
	{
		this.iso2code = iso2code;
		this.iso3code = iso3code;
		this.fullName = fullName;
	}
}
