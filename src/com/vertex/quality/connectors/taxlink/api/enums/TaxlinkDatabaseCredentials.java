package com.vertex.quality.connectors.taxlink.api.enums;

import lombok.Getter;

/**
 * Contains credentials for accessing Taxlink databases.
 *
 * @author ajain
 */
@Getter
public enum TaxlinkDatabaseCredentials
{
	XXVERTEX(1, "XXVERTEX");

	private int id;
	private String credentialName;

	TaxlinkDatabaseCredentials( int credentialId, String credentialName )
	{
		this.id = credentialId;
		this.credentialName = credentialName;
	}
}
