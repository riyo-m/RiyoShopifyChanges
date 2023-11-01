package com.vertex.quality.connectors.ariba.api.enums;

import lombok.Getter;

/**
 * represents all the log messages titles and their index when calling a unique document id
 *
 * @author osabha
 */
@Getter
public enum AribaAPILogMessages
{
	IR_ARIBA_REQUEST("0"),
	IR_OSERIES_REQUEST("1"),
	IR_OSERIES_RESPONSE("2"),
	IR_ARIBA_RESPONSE("3"),
	POSTING_ARIBA_REQUEST("4"),
	POSTING_OSERIES_REQUEST("5"),
	POSTING_OSERIES_RESPONSE("6"),
	POSTING_ARIBA_RESPONSE_NO_ACCRUAL("7"),
	ACCRUAL_REQUEST("7"),
	ACCRUAL_RESPONSE("8"),
	ACCRUAL_REQUEST_POSTING_CALL_ONLY("4"),
	POSTING_ARIBA_RESPONSE("9");

	private String value;

	AribaAPILogMessages( final String value )
	{
		this.value = value;
	}
}
