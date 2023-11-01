package com.vertex.quality.connectors.hybris.enums.backoffice;

/***Enum for BackOffice Left Navigation Menus
 *
 */
public enum HybrisBONavTreeOptions
{
	HOME("Home"),
	INBOX("Inbox"),
	SYSTEM("System"),
	BACKGROUND_PROCESSES("Background Processes"),
	CRON_JOBS("CronJobs"),
	BASE_COMMERCE("Base Commerce"),
	BASE_STORE("Base Store"),
	VERTEX("Vertex"),
	VERTEX_CONFIGURATION("Vertex Configuration"),
	ORDER("Order"),
	ORDERS("Orders");

	String menuName;

	HybrisBONavTreeOptions( String menu )
	{
		this.menuName = menu;
	}

	public String getMenuName( )
	{
		return menuName;
	}
}
