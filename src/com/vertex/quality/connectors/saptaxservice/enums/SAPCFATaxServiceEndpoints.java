package com.vertex.quality.connectors.saptaxservice.enums;

import com.vertex.quality.common.enums.Endpoint;
import lombok.Getter;

/**
 * The endpoints for the SAP CFA Tax Service API
 *
 * @author unaqvi
 */
@Getter
public enum SAPCFATaxServiceEndpoints implements Endpoint
{
	CFA_QUOTE("quote");

	private String endpoint;

	SAPCFATaxServiceEndpoints(String endpoint)
	{
		this.endpoint = endpoint;
	}

}