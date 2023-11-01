package com.vertex.quality.connectors.bigcommerce.api.enums;

import lombok.Getter;

/**
 * the types of items that can be included in a quote request
 *
 * @author osabha ssalisbury
 */
@Getter
public enum BigCommerceRequestItemType
{
	ITEM("item"),
	WRAPPING("wrapping"),
	HANDLING("handling"),
	SHIPPING("shipping"),
	REFUND("refund");

	private String name;

	BigCommerceRequestItemType( final String typeName )
	{
		this.name = typeName;
	}
}
