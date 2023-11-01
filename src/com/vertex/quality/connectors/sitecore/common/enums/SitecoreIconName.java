package com.vertex.quality.connectors.sitecore.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

/**
 * icon name in sitecore page
 *
 * @author Shiva Mothkula, Daniel Bondi
 */ public enum SitecoreIconName
{
	USER_MANAGER("User Manager"),
	CONTENT_EDITOR("Content Editor"),
	VERTEX_O_SERIES_CONNECTOR("Vertex O Series");

	final private String text;
}
