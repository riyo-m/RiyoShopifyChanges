package com.vertex.quality.connectors.sitecore.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

/**
 * represents sitecore status
 *
 * @author Shiva Mothkula, Daniel Bondi
 */ public enum SitecoreStatus
{
	GOOD("Good"),
	BAD("Bad");

	final private String text;
}
