package com.vertex.quality.connectors.sitecore.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

/**
 * represents available shipping methods
 *
 * @author Shiva Mothkula, Daniel Bondi
 */ public enum SitecoreShippingMethod
{
	SHIP_ITEMS("Ship items"),
	PICKUP_ITEMS("Pick up items in store");

	final private String text;

	/**
	 * represents available shipping types
	 */
	@AllArgsConstructor
	@Getter
	public enum ShipItems
	{
		BY_GROUND("By Ground"),
		BY_AIR("By Air");

		final private String text;
	}

	/**
	 * represents available pickup types
	 */
	@AllArgsConstructor
	@Getter
	public enum PickupItems
	{
		IN_STORE_PICKUP("In-Store Pickup");

		final private String text;
	}
}
