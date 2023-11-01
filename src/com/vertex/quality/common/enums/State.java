package com.vertex.quality.common.enums;

public enum State
{
	// @formatter:off
	AL("Alabama", "AL"), 
	AK("Alaska", "AK"), 
	AZ("Arizona", "AZ"), 
	AR("Arkansas", "AR"),
	CA("California", "CA"), 
	CO("Colorado", "CO"),
	CAY("Cayo","CAY"),
	CT("Connecticut", "CT"), 
	DE("Delaware", "DE"), 
	DC("District of Columbia", "DC"), 
	FL("Florida", "FL"), 
	GA("Georgia", "GA"), 
	HI("Hawaii", "HI"), 
	ID("Idaho", "ID"), 
	IL("Illinois","IL"), 
	IN("Indiana", "IN"), 
	IA("Iowa", "IA"), 
	KS("Kansas", "KS"), 
	KY("Kentucky", "KY"), 
	LA("Louisiana", "LA"), 
	ME("Maine", "ME"), 
	MD("Maryland", "MD"), 
	MA("Massachusetts","MA"), 
	MI("Michigan", "MI"), 
	MN("Minnesota", "MN"), 
	MS("Mississippi","MS"), 
	MO("Missouri", "MO"), 
	MT("Montana", "MT"), 
	NE("Nebraska","NE"), 
	NV("Nevada", "NV"), 
	NH("New Hampshire", "NH"), 
	NJ("New Jersey","NJ"), 
	NM("New Mexico", "NM"), 
	NY("New York","NY"), 
	NC("North Carolina", "NC"), 
	ND("North Dakota","ND"), 
	OH("Ohio", "OH"), 
	OK("Oklahoma", "OK"), 
	OR("Oregon", "OR"), 
	PA("Pennsylvania","PA"), 
	RI("Rhode Island","RI"), 
	SC("South Carolina","SC"), 
	SD("South Dakota","SD"),
	SI("San Ignacio", "SI"),
	TN("Tennessee","TN"), 
	TX("Texas","TX"), 
	UT("Utah","UT"), 
	VT("Vermont","VT"), 
	VA("Virginia","VA"), 
	WA("Washington","WA"), 
	WV("West Virginia","WV"), 
	WI("Wisconsin","WI"), 
	WY("Wyoming","WY"),
	PR("Puerto Rico","PR"),

	//India States
	CH("Chandigarh", "CH"),
	MH("Maharashtra", "MH"),
	AN("Andaman and Nicobar Islands", "AN"),

	//Pakistan States
	SN("SINDH", "SN"),
	;

	// @formatter:on
	public String fullName;
	public String abbreviation;

	State( String fullName, String abbreviation )
	{
		this.fullName = fullName;
		this.abbreviation = abbreviation;
	}
}
