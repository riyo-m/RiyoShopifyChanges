package com.vertex.quality.connectors.commercetools.api.enums;

import lombok.Getter;
/**
 * this endpoint is used to load commercetool api base url's
 *
 * @author Mayur.Kumbhar
 */
@Getter
public enum CommerceToolEndpoint
{

	CREATE_CART_CONTROLLER("/carts"),
	CREATE_ORDER_CONTROLLER("/orders"),
	ADDRESS_CLEANSING_CONTROLLER("/connector/api/cleanseAddress"),
	COMMERCETOOL_HEALTH_CONTROLLER("/connector/health"),
	COMMERCETOOL_VERSION_CONTROLLER("/connector/version"),
	COMMERCETOOL_CUSTOM_TYPE("/types");

	private String urlSuffix;

	CommerceToolEndpoint( String endPointUrlSuffix )
	{
		this.urlSuffix = endPointUrlSuffix;
	}
}
