package com.vertex.quality.connectors.ariba.connector.enums;

import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnCustomFieldMappingPage;
import lombok.Getter;

/**
 * the Ariba Data Types that can be chosen on {@link AribaConnCustomFieldMappingPage}
 *
 * @author ssalisbury
 */
@Getter
public enum AribaConnAribaFieldType
{
	BOOLEAN("Boolean"),
	DATE("Date"),
	INTEGER("Integer"),
	MONEY("Money"),
	STRING("String");

	private final String value;

	AribaConnAribaFieldType( final String fieldName )
	{
		this.value = fieldName;
	}
}
