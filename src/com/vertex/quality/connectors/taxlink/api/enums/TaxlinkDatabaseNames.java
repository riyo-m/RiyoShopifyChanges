package com.vertex.quality.connectors.taxlink.api.enums;

import lombok.Getter;

/**
 * Contains Taxlink database names.
 *
 * @author ajain
 */
@Getter
public enum TaxlinkDatabaseNames
{

	XXVERTEX(1, "XXVERTEX");

	private int id;
	private String databaseName;

	TaxlinkDatabaseNames( int databaseId, String databaseName )
	{
		this.id = databaseId;
		this.databaseName = databaseName;
	}
}
