package com.vertex.quality.connectors.ariba.api.enums;

import lombok.Getter;

/**
 * The phases for the XML logs for Ariba
 *
 * @author hho
 */
@Getter
public enum AribaAPIPhases
{
	ARIBA_REQUEST("Ariba request"),
	O_SERIES_REQUEST("O-series request"),
	O_SERIES_RESPONSE("O-series response"),
	ARIBA_RESPONSE("Ariba response");

	private String phase;

	AribaAPIPhases( final String phase )
	{
		this.phase = phase;
	}
}
