package com.vertex.quality.connectors.hybris.enums.backoffice;

/**
 * Enum for Base Store Electronics Store Tab options
 */
public enum HybrisBOBaseStoreTabOptions
{
	PROPERTIES("Properties"),
	LOCATIONS("Locations"),
	VERTEX_CUSTOMIZATION("Vertex Customization"),
	ADMINISTRATION("Administration");

	String optionText;

	HybrisBOBaseStoreTabOptions( String optionText )
	{
		this.optionText = optionText;
	}

	public String getoption( )
	{
		return optionText;
	}
}