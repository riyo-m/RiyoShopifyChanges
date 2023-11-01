package com.vertex.quality.connectors.taxlink.ui.enums;

/**
 * Enums for Clean Up Instance tab
 *
 * @author mgaikwad
 */

public enum CleanUpInstance
{
	Details("Cleanup Instance", "Cleanup Instance", "The below table shows all active Vertex Accelerator instances.",
		"The Production instance is not available for cleanup.\n" +
		"The user must have the FI_ADMIN role to cleanup an instance.", "ecog-dev1-us2", "test2", "logintest",
		"This will delete all configuration and BIP data relevant to this instance. Once you proceed, data cannot be restored. Do you want to continue?");

	public String headerCleanUpInstancePage;
	public String headerCleanUpInstanceSummaryPage;

	public String summaryPageChildTitle;

	public String pageFirstChildTitle;

	public String prodInstance;
	public String cloudInstance;
	public String user;
	public String popUpMessage;

	CleanUpInstance( String headerCleanUpInstanceSummaryPage, String headerCleanUpInstancePage,
		String summaryPageChildTitle, String pageFirstChildTitle, String prodInstance, String cloudInstance,
		String user, String popUpMessage )
	{
		this.headerCleanUpInstanceSummaryPage = headerCleanUpInstanceSummaryPage;
		this.headerCleanUpInstancePage = headerCleanUpInstancePage;
		this.summaryPageChildTitle = summaryPageChildTitle;
		this.pageFirstChildTitle = pageFirstChildTitle;
		this.prodInstance = prodInstance;
		this.cloudInstance = cloudInstance;
		this.user = user;
		this.popUpMessage = popUpMessage;
	}
}
