package com.vertex.quality.connectors.hybris.enums.admin;

/**
 * Enum for Admin Submenu Options
 */
public enum HybrisAdminSubMenuNames
{
	EXTENSIONS("extensions"),
	SYSTEM("system"),
	LOGGING("logging"),
	SUPPORT("support");

	String submenuName;

	HybrisAdminSubMenuNames( String submenu )
	{
		this.submenuName = submenu;
	}

	public String getSubMenuName( )
	{
		return submenuName;
	}
}
