package com.vertex.quality.connectors.commercetools.api.pojos;

import java.util.List;

/**
 * address cleansing request Items which can be sent to the connector's API
 *
 * @author Vivek.Kumar
 */

public class CommerceToolAddressCleansingLists
{
	private List<CommerceToolAddressCleansingActions> lookupList;

	public List<CommerceToolAddressCleansingActions> getLookupList( )
	{
		return lookupList;
	}

	public void setLookupList( final List<CommerceToolAddressCleansingActions> lookupList )
	{
		this.lookupList = lookupList;
	}
}

