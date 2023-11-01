package com.vertex.quality.connectors.taxlink.ui.enums;

/**
 * Enums for for Retry Jobs Tab in Taxlink UI.
 *
 * @author mgaikwad
 */

public enum Monitoring
{

	MONITORING_DETAILS("Retry Jobs", "Retry Error Documents", "Retry Submit Error Documents",
		"Retry Upload Error Documents", "PTDE Reset", "PTDE_CLEAR_STATUS", "At 03:30 AM, every day", "AP", "AR",
		"Polling Jobs", "Transaction Monitoring Status", "Summary Reports");

	public String headerRetryJobsPage;
	public String firstCommonRetryJobsRecord;
	public String secondCommonRetryJobsRecord;
	public String thirdCommonRetryJobsRecord;
	public String jobNamePTDEResetRetryJobsRecord;
	public String serviceDescRetryJobsRecord;
	public String scheduleRetryJobsRecord;
	public String serviceSubAPRetryJobsRecord;
	public String serviceSubARRetryJobsRecord;
	public String headerPollingJobsPage;
	public String headerTransMonitoringPage;
	public String headerSummaryReportsPage;

	Monitoring( String headerRetryJobsPage, String firstCommonRetryJobsRecord, String secondCommonRetryJobsRecord,
		String thirdCommonRetryJobsRecord, String jobNamePTDEResetRetryJobsRecord, String serviceDescRetryJobsRecord,
		String scheduleRetryJobsRecord, String serviceSubAPRetryJobsRecord, String serviceSubARRetryJobsRecord,
		String headerPollingJobsPage, String headerTransMonitoringPage, String headerSummaryReportsPage )
	{
		this.headerRetryJobsPage = headerRetryJobsPage;
		this.firstCommonRetryJobsRecord = firstCommonRetryJobsRecord;
		this.secondCommonRetryJobsRecord = secondCommonRetryJobsRecord;
		this.thirdCommonRetryJobsRecord = thirdCommonRetryJobsRecord;
		this.jobNamePTDEResetRetryJobsRecord = jobNamePTDEResetRetryJobsRecord;
		this.serviceDescRetryJobsRecord = serviceDescRetryJobsRecord;
		this.scheduleRetryJobsRecord = scheduleRetryJobsRecord;
		this.serviceSubAPRetryJobsRecord = serviceSubAPRetryJobsRecord;
		this.serviceSubARRetryJobsRecord = serviceSubARRetryJobsRecord;
		this.headerPollingJobsPage = headerPollingJobsPage;
		this.headerTransMonitoringPage = headerTransMonitoringPage;
		this.headerSummaryReportsPage = headerSummaryReportsPage;
	}
}

