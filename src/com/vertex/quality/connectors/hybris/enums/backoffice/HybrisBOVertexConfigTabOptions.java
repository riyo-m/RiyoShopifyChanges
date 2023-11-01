package com.vertex.quality.connectors.hybris.enums.backoffice;

/**
 * Enum for Vertex Configuration Tab options
 */
public enum HybrisBOVertexConfigTabOptions
{
	ADMINISTRATION("Administration"),
	AUTHENTICATION("Authentication"),
	PROPERTIES("Properties"),
	VERTEX_CUSTOMIZATION("Vertex Customization"),
	POSITIONS_AND_PRICES("Positions and Prices");

	String optionText;

	HybrisBOVertexConfigTabOptions( String optionText )
	{
		this.optionText = optionText;
	}

	public String getOption( )
	{
		return optionText;
	}
}
