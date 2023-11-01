package com.vertex.quality.connectors.hybris.enums.admin;

/***Enum for Admin Menu Options
 *
 */
public enum HybrisAdminMenuNames
{
	PLATFORM("platform"),
	MONITORING("monitoring"),
	MAINTENANCE("maintenance"),
	CONSOLE("console");

	String menuName;

	HybrisAdminMenuNames( String menu )
	{
		this.menuName = menu;
	}

	public String getMenuName( )
	{
		return menuName;
	}
}
