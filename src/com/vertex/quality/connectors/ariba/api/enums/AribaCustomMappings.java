package com.vertex.quality.connectors.ariba.api.enums;

import lombok.Getter;

/**
 * Represents Ariba custom mappings
 *
 * @author hho
 */
@Getter
public enum AribaCustomMappings
{
	NO_CUSTOM_MAPPING(null),
	ARIBA_INTEGER_FLEX_FIELD("TestIntegerVertex"),
	ARIBA_STRING_FLEX_FIELD("TestStringVertex"),
	ARIBA_BOOLEAN_FLEX_FIELD("TestBooleanVertex"),
	ARIBA_MONEY_FLEX_FIELD("TestMoneyFieldVertex"),
	ARIBA_DATE_FLEX_FIELD("TestDateVertex"),
	ARIBA_VENDOR_CODE_FIELD("VendorCodeField"),
	ARIBA_VENDOR_CLASS_FIELD("VendorClassField"),
	ARIBA_USAGE_FIELD("TestingUsageField"),
	ARIBA_USAGE_CLASS_FIELD("TestingUsageClassField");

	private String value;

	AribaCustomMappings( final String value )
	{
		this.value = value;
	}
}
