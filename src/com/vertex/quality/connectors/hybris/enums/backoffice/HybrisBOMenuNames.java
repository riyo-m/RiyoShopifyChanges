package com.vertex.quality.connectors.hybris.enums.backoffice;

/**
 * Describes the BackOffice Menu Names
 */
public enum HybrisBOMenuNames
{
	LOGOUT("Logout");

	String menuName = "";

	HybrisBOMenuNames( String name )
	{
		this.menuName = name;
	}

	public String getMenuName( )
	{
		return menuName;
	}
}
