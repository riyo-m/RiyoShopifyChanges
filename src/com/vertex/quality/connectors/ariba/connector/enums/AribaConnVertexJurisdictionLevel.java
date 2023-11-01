package com.vertex.quality.connectors.ariba.connector.enums;

import lombok.Getter;

/**
 * the different types of tax jurisdictions that O-Series recognizes
 *
 * @author ssalisbury
 */
@Getter
public enum AribaConnVertexJurisdictionLevel
{
	APO("APO"),
	BOROUGH("BOROUGH"),
	CITY("CITY"),
	COUNTRY("COUNTRY"),
	COUNTY("COUNTY"),
	DISTRICT("DISTRICT"),
	FPO("FPO"),
	LOCAL_IMPROVEMENT_DISTRICT("LOCAL_IMPROVEMENT_DISTRICT"),
	PARISH("PARISH"),
	PROVINCE("PROVINCE"),
	SPECIAL_PURPOSE_DISTRICT("SPECIAL_PURPOSE_DISTRICT"),
	STATE("STATE"),
	TERRITORY("TERRITORY"),
	TOWNSHIP("TOWNSHIP"),
	TRANSIT_DISTRICT("TRANSIT_DISTRICT"),
	BLANK("");

	private String val;

	AribaConnVertexJurisdictionLevel( final String jurisdictionLevel )
	{
		this.val = jurisdictionLevel;
	}
}
