package com.vertex.quality.connectors.bigcommerce.api.enums;

import lombok.Getter;

/**
 * the endpoints for the big commerce api off of its base URL
 *
 * @author osabha ssalisbury
 */
@Getter
public enum BigCommerceEndpoint
{
	ESTIMATE_API_CONTROLLER("/estimate"),
	CONFIG_API_CONTROLLER("/stores/configs"),
	COMMIT_API_CONTROLLER("/commit"),
	ADJUST_API_CONTROLLER("/adjust"),
	VOID_API_CONTROLLER("/void"),
	STORE_API_CONTROLLER("/stores/"),
	LOG_API_CONTROLLER("/stores/iuvevjf46c/logs"),

	HEALTHCHECK_API_ENDPOINT("/health");


	private String urlSuffix;

	BigCommerceEndpoint( String endpointUrlSuffix )
	{
		this.urlSuffix = endpointUrlSuffix;
	}
}
