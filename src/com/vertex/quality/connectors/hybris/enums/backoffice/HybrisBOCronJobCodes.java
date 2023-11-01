package com.vertex.quality.connectors.hybris.enums.backoffice;

/**
 * Describes the CronJob Codes which are available in Hybris
 */
public enum HybrisBOCronJobCodes
{
	VERTEX_CRON_JOB("PingVertexPerformable");

	String cronJobCode = "";

	HybrisBOCronJobCodes( String code )
	{
		this.cronJobCode = code;
	}

	public String getCronJobCode( )
	{
		return cronJobCode;
	}
}
