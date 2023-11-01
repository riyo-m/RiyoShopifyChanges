package com.vertex.quality.connectors.saptaxservice.enums;

/**
 * Countries available for the SAP API call
 *
 * @author unaqvi
 */
public enum SAPCountry
{
	UNITED_STATES("United States", "US"),
	CANADA("Canada", "CA"),
	ITALY("Italy", "IT"),
	NETHERLANDS("Netherlands", "NL"),
	SWITZERLAND("Switzerland","CH"),
	FRANCE("France","FR"),
	SAN_MARINO("San Marino","SM"),
	VATICAN("Vatican", "VC"),
	CHINA("China","CN");

	private String fullName;
	private String countryAbbreviation;

	SAPCountry( String fullName, String countryAbbreviation )
	{
		this.fullName = fullName;
		this.countryAbbreviation = countryAbbreviation;
	}

	/**
	 * Gets the country's full name
	 *
	 * @return the country's full name
	 */
	public String getFullName( )
	{
		return this.fullName;
	}

	/**
	 * Gets the country's abbreviation
	 *
	 * @return the country's abbreviation
	 */
	public String getCountryAbbreviation( )
	{
		return this.countryAbbreviation;
	}
}
