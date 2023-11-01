package com.vertex.quality.connectors.accumatica.enums;

/**
 * Main menu names from acumatica application
 */
public enum AcumaticaGlobalMenuOption
{
	ORGANIZATION("ORGANIZATION"),
	FINANCE("FINANCE"),
	DISTRIBUTION("DISTRIBUTION"),
	CONFIGURATION("CONFIGURATION"),
	SYSTEM("SYSTEM");

	String displayValue;

	AcumaticaGlobalMenuOption( String value )
	{
		this.displayValue = value;
	}

	/**
	 * Get UI display value
	 *
	 * @return
	 */
	public String getLabel( )
	{
		return displayValue;
	}

	@Override
	public String toString( )
	{
		return getLabel();
	}
}
