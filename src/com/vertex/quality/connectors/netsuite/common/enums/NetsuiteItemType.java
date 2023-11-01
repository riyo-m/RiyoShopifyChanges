package com.vertex.quality.connectors.netsuite.common.enums;

import lombok.Getter;

/**
 * Represents a Netsuite item type
 *
 * @author hho
 */
@Getter
public enum NetsuiteItemType
{
	DISCOUNT("Discount"),
	DOWNLOAD("Download Item"),
	GIFT_CERTIFICATE("Gift Certificate"),
	INVENTORY_ITEM("Inventory Item");

	private String itemType;

	NetsuiteItemType( String itemType )
	{
		this.itemType = itemType;
	}
}
