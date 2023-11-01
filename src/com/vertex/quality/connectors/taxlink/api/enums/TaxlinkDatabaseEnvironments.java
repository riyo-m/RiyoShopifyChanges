package com.vertex.quality.connectors.taxlink.api.enums;

import lombok.Getter;

/**
 * Contains all taxlink machine's databases connection URLs.
 *
 * @author ajain
 */
@Getter
public enum TaxlinkDatabaseEnvironments
{

	OPE187(1, "xxvertex"),
	PHLDAPS0110(2, "PHLDAPS0110");

	private int id;
	private String environmentName;

	TaxlinkDatabaseEnvironments( int environmentId, String environmentName )
	{
		this.id = environmentId;
		this.environmentName = environmentName;
	}
}
