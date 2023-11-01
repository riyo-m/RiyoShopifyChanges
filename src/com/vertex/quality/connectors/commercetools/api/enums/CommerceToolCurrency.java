package com.vertex.quality.connectors.commercetools.api.enums;


import lombok.Getter;

/**
 * different currency types used in commercetool create cart request
 *
 * @author Mayur.Kumbhar
 */
@Getter
public enum CommerceToolCurrency {

	USD("USD"),
	EUR("EUR");
	private String currencyName;

	CommerceToolCurrency (final String currencyName)
	{
		this.currencyName=currencyName;
	}
}
