package com.vertex.quality.common.enums;

import lombok.Getter;

/**
 * Contains all the environment names in the database
 * If the Environments table in the database gets updated, please add the changes here
 *
 * @author hho
 */
@Getter
public enum DBEnvironmentNames
{
	QA(1, "QA"),
	DEV(2, "DEV"),
	PROD(3, "PROD");

	private int id;
	private String environmentName;

	DBEnvironmentNames( final int environmentId, final String environmentName )
	{
		this.id = environmentId;
		this.environmentName = environmentName;
	}
}
