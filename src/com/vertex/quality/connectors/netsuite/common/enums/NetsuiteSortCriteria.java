package com.vertex.quality.connectors.netsuite.common.enums;

import lombok.Getter;

/**
 * Sort criteria in Netsuite
 *
 * @author hho
 */
@Getter
public enum NetsuiteSortCriteria
{
	RECENTLY_MODIFIED("Recently Modified");

	private String sortCriteria;

	NetsuiteSortCriteria( String sortCriteria )
	{
		this.sortCriteria = sortCriteria;
	}
}
