package com.vertex.quality.connectors.saptaxservice.enums;

import com.vertex.quality.common.enums.Endpoint;
import lombok.Getter;

/**
 * The endpoints for the SAP Tax Service API
 *
 * @author hho
 */
@Getter
public enum SAPTaxServiceEndpoints implements Endpoint
{
	QUOTE("quote");

	private String endpoint;

	SAPTaxServiceEndpoints( String endpoint )
	{
		this.endpoint = endpoint;
	}
}
