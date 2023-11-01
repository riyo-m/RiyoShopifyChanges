package com.vertex.quality.connectors.commercetools.api.pojos;

/**
 * address cleansing request Items which can be sent to the connector's API
 *
 * @author Vivek.Kumar
 */
public class CommerceToolAddressCleansingActions
{
	private String asOfDate;
	private CommerceToolCleansingAddress address;

	public String getAsOfDate( )
	{
		return asOfDate;
	}

	public CommerceToolCleansingAddress getAddress( )
	{
		return address;
	}

	public void setAsOfDate( final String asOfDate )
	{
		this.asOfDate = asOfDate;
	}

	public void setAddress( final CommerceToolCleansingAddress address )
	{
		this.address = address;
	}
}
