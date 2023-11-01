package com.vertex.quality.connectors.sitecore.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

/**
 * represents different payments at checkout
 *
 * @author Shiva Mothkula, Daniel Bondi
 */ public enum SitecoreAmount
{
	SUBTOTAL("SUBTOTAL"),
	SHIPPING_COST("SHIPPING COST"),
	TAX("TAX"),
	TOTAL("TOTAL");

	private final String text;
}
