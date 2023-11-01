package com.vertex.quality.connectors.bigcommerce.api.enums;

import lombok.Getter;

/**
 * the different currency types used in quote requests
 *
 * @author osabha ssalisbury
 */
@Getter
public enum BigCommerceCurrency
{
	USD("USD"),
	AUD("AUD"),
	EUR("EUR");

	private String name;

	BigCommerceCurrency( final String currencyName )
	{
		this.name = currencyName;
	}
}
