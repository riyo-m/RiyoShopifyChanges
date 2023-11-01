package com.vertex.quality.connectors.hybris.enums.admin;

/***
 * Enum for Menu Headings of Extensions page
 */
public enum HybrisAdminExtensionHeadings
{
	NAME("Name"),
	VERSION("Version"),
	CORE("Core"),
	HMC("HMC"),
	WEB("Web");

	String menuName = "";

	HybrisAdminExtensionHeadings( String headingName )
	{
		this.menuName = headingName;
	}

	public String getMenuName( )
	{
		return menuName;
	}
}
