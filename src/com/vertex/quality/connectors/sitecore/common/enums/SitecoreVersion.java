package com.vertex.quality.connectors.sitecore.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

/**
 * represents sitecore version
 *
 * @author Shiva Mothkula, Daniel Bondi
 */ public enum SitecoreVersion
{
	CORE("Core version"),
	ADAPTER("Adapter Version");

	final private String text;
}
