package com.vertex.quality.connectors.taxlink.ui.enums;

/**
 * Enums for Business Unit tab
 *
 * @author mgaikwad
 */
public enum BusinessUnit
{
	BU_DETAILS("INDIA", "Business Units", "View Business Unit", "Edit Business Unit");

	public String selectCountryName;
	public String headerBusinessUnitPage;
	public String headerViewBusinessUnitPage;
	public String headerEditBusinessUnitPage;

	BusinessUnit( String selectCountryName, String headerBusinessUnitPage, String headerViewBusinessUnitPage,
		String headerEditBusinessUnitPage )
	{
		this.selectCountryName = selectCountryName;
		this.headerBusinessUnitPage = headerBusinessUnitPage;
		this.headerViewBusinessUnitPage = headerViewBusinessUnitPage;
		this.headerEditBusinessUnitPage = headerEditBusinessUnitPage;
	}
}
