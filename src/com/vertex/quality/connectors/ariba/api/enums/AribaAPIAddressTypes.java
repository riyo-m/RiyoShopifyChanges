package com.vertex.quality.connectors.ariba.api.enums;

import lombok.Getter;

/**
 * Ariba API address
 *
 * @author hho
 */
@Getter
public enum AribaAPIAddressTypes
{
	BILLING("Billing"),
	SHIP_FROM("ShipFrom"),
	SHIP_TO("ShipTo"),
	SUPPLIER("Supplier");

	private String addressType;

	AribaAPIAddressTypes( final String addressType )
	{
		this.addressType = addressType;
	}
}
