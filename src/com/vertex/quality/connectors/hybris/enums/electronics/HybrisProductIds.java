package com.vertex.quality.connectors.hybris.enums.electronics;

/***
 * Describes all ProductID's
 */
public enum HybrisProductIds
{
	POWERSHOTID("ID1934793"),
	MONOPODID("ID1099285"),
	TMAXP3200ID("ID779868"),
	DSLRA330ID("ID2934302");

	String id;

	HybrisProductIds( String id )
	{
		this.id = id;
	}

	public String getproductID( )
	{
		return id;
	}
}
