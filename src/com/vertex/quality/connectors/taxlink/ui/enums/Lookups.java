package com.vertex.quality.connectors.taxlink.ui.enums;

/**
 * Enums for for Lookups Tab in Taxlink UI.
 *
 * @author mgaikwad
 */

public enum Lookups
{
	LOOKUP_DETAILS("Lookups", "Lookup Type: VTX_CALC_EXCL_TAXES", "View Lookup: VTX_CALC_EXCL_TAXES",
		"Lookup Type: VTX_IMPOSITION_TYPE", "23", "119");

	public String headerLookupsPage;
	public String headerLookupType_VTX_CALC_EXCL_TAXES;
	public String headerLookupType_ViewLookup_VTX_CALC_EXCL_TAXES;
	public String headerLookupType_VTX_IMPOSITION_TYPE;
	public String totalNumberOfLookups;
	public String totalNumberOfLookupsForVTX_IMPOSITION_TYPE;

	Lookups( String headerLookupsPage, String headerLookupType_VTX_CALC_EXCL_TAXES,
		String headerLookupType_ViewLookup_VTX_CALC_EXCL_TAXES, String headerLookupType_VTX_IMPOSITION_TYPE,
		String totalNumberOfLookups, String totalNumberOfLookupsForVTX_IMPOSITION_TYPE )
	{
		this.headerLookupsPage = headerLookupsPage;
		this.headerLookupType_ViewLookup_VTX_CALC_EXCL_TAXES = headerLookupType_ViewLookup_VTX_CALC_EXCL_TAXES;
		this.headerLookupType_VTX_CALC_EXCL_TAXES = headerLookupType_VTX_CALC_EXCL_TAXES;
		this.headerLookupType_VTX_IMPOSITION_TYPE = headerLookupType_VTX_IMPOSITION_TYPE;
		this.totalNumberOfLookups = totalNumberOfLookups;
		this.totalNumberOfLookupsForVTX_IMPOSITION_TYPE = totalNumberOfLookupsForVTX_IMPOSITION_TYPE;
	}
}
