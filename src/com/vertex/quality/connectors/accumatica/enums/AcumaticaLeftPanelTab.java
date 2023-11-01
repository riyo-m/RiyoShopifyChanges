package com.vertex.quality.connectors.accumatica.enums;

/**
 * Common tab names from different pages
 *
 * @author Saidulu Kodadala
 */
public enum AcumaticaLeftPanelTab
{
	SOLE_TAB(null),
	WORK_AREA("Work Area"),
	PROCESSES("Processes"),
	REPORTS("Reports"),
	CONFIGURATION("Configuration");

	String tooltipText;

	AcumaticaLeftPanelTab( String value )
	{
		this.tooltipText = value;
	}

	/**
	 * Get UI display value
	 *
	 * @return
	 */
	public String getValue( )
	{
		return tooltipText;
	}

	@Override
	public String toString( )
	{
		return getValue();
	}
}
