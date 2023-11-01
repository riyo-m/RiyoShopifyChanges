package com.vertex.quality.connectors.accumatica.enums;

import lombok.Getter;

/**
 * this specifies the options for the credit verification dropdown
 */
@Getter
public enum AcumaticaCreditVerification
{
	DAYS_PAST_DUE("Days Past Due"),
	CREDIT_LIMIT("Credit Limit"),
	LIMIT_AND_DAYS_PAST_DUE("Limit and Days Past Due"),
	DISABLED("Disabled");

	private String value;

	AcumaticaCreditVerification( final String verificationName )
	{
		this.value = verificationName;
	}
}
