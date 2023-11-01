package com.vertex.quality.connectors.netsuite.common.enums;

import lombok.Getter;

/**
 * Represents the items on the custom list page
 *
 * @author hho
 */
@Getter
public enum NetsuiteCustomList
{
	VERTEX_CUSTOMER_CLASS("Vertex Customer Class"),
	VERTEX_PRODUCT_CLASS("Vertex Product Class");

	private String customListTitle;

	NetsuiteCustomList( String customListTitle )
	{
		this.customListTitle = customListTitle;
	}
}
