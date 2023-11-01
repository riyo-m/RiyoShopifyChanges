package com.vertex.quality.connectors.bigcommerce.api.enums;

import lombok.Getter;

/**
 * the possible address types for the address object in the request document
 *
 * @author osabha ssalisbury
 */
@Getter
public enum BigCommerceRequestDocAddressType
{
	COMMERCIAL("COMMERCIAL "),
	RESIDENTIAL("RESIDENTIAL");

	private String name;

	BigCommerceRequestDocAddressType( final String typeName )
	{
		this.name = typeName;
	}
}
