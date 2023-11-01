package com.vertex.quality.connectors.sitecore.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

/**
 * represents available payment methods in sitecore
 *
 * @author Shiva Mothkula, Daniel Bondi
 */ public enum SitecorePaymentMethod
{
	PAY_CARD("Pay card"),
	ONLINE_PAYMENT("Online payment");

	final private String text;

	@AllArgsConstructor
	@Getter
	/**
	 * represents available card payment methods
	 */ public enum PayCard
	{

		CREDIT_CARD("Credit Card");

		final private String text;
	}
}
