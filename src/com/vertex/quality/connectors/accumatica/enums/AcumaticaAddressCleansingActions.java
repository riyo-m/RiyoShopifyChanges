package com.vertex.quality.connectors.accumatica.enums;

/**
 * Acumatica Address Cleansing Actions data
 *
 * @author Sai
 */
public enum AcumaticaAddressCleansingActions
{
	CONFIRM("Confirm"),
	IGNORE("Ignore");

	String displayValue;

	AcumaticaAddressCleansingActions( String value )
	{
		this.displayValue = value;
	}

	/**
	 * Get UI display value
	 *
	 * @return
	 */
	public String getValue( )
	{
		return displayValue;
	}

	@Override
	public String toString( )
	{
		return getValue();
	}
}
