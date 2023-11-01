package com.vertex.quality.connectors.sitecore.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

/**
 * item categories available in sitecore
 *
 * @author Shiva Mothkula, Daniel Bondi
 */ public enum SitecoreItemCategory
{
	APPAREL_SHOES("Apparel & Shoes"),
	BOOKS("Books"),
	COMPUTERS("Computers"),
	DIGITAL_DOWNLOADS("Digital downloads"),
	ELECTRONICS("Electronics"),
	GIFT_CARDS("Gift Cards"),
	JEWELRY("Jewelry");

	final private String text;
}
