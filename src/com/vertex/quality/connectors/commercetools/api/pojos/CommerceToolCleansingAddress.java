package com.vertex.quality.connectors.commercetools.api.pojos;

/**
 * address cleansing request Items which can be sent to the connector's API
 *
 * @author Vivek.Kumar
 */
public class CommerceToolCleansingAddress
{
	private String streetAddress1;
	private String streetAddress2;
	private String city;
	private String mainDivision;
	private String subDivision;
	private String postalCode;
	private String country;

	public String getStreetAddress1( )
	{
		return streetAddress1;
	}

	public String getStreetAddress2( )
	{
		return streetAddress2;
	}

	public String getCity( )
	{
		return city;
	}

	public String getMainDivision( )
	{
		return mainDivision;
	}

	public String getSubDivision( )
	{
		return subDivision;
	}

	public String getPostalCode( )
	{
		return postalCode;
	}

	public String getCountry( )
	{
		return country;
	}

	public void setStreetAddress1( final String streetAddress1 )
	{
		this.streetAddress1 = streetAddress1;
	}

	public void setStreetAddress2( final String streetAddress2 )
	{
		this.streetAddress2 = streetAddress2;
	}

	public void setCity( final String city )
	{
		this.city = city;
	}

	public void setMainDivision( final String mainDivision )
	{
		this.mainDivision = mainDivision;
	}

	public void setSubDivision( final String subDivision )
	{
		this.subDivision = subDivision;
	}

	public void setPostalCode( final String postalCode )
	{
		this.postalCode = postalCode;
	}

	public void setCountry( final String country )
	{
		this.country = country;
	}
}
