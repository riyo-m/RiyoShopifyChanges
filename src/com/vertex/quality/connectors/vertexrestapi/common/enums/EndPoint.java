package com.vertex.quality.connectors.vertexrestapi.common.enums;

/**
 * Represents the available endpoints for the Vertex Rest API
 *
 * @author hho
 */
public enum EndPoint
{
	// @formatter:off

	ADDRESS_LOOKUP("addresslookup"),
	PURCHASE("purchase"),
	SALE("sale");

	// @formatter:on

	protected String type;

	EndPoint( String type )
	{
		this.type = type;
	}

	/**
	 * Gets the endpoint type
	 *
	 * @return the endpoint type in String form
	 */
	public String getEndPoint( )
	{
		return type;
	}
}
