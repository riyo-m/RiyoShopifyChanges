package com.vertex.quality.connectors.netsuite.common.enums;

import lombok.Getter;

/**
 * Contains Netsuite Vendors
 *
 * @author ravunuri
 */
@Getter
public enum NetsuiteVendor
{
	VENDOR_3M("3M"),
	VENDOR_PA("Vendor PA"),
	VENDOR_SUPPLY_CA("Honeycomb Holdings Inc."),
	VENDOR_LATAM_BZBZ("LatinAmerica_BZBZ"),
	VENDOR_LATAM_CRCO("LatinAmerica_CRCO"),
	VENDOR_APAC_CGST_SGST("AsiaPacific_CGST_SGST"),
	VENDOR_APAC_INTERSTATE("AsiaPacific_InterState"),
	VENDOR_APAC_INTERSTATE_REVERSE("AsiaPacific_InterState_Reverse"),
	VENDOR_FRANCE("Vendor_France"),
	VENDOR_GERMANY("German Company"),
	VENDOR_GREECE("Greek_Company"),
	VENDOR_AUSTRIA("Austrian Company"),
	VENDOR_BELGIUM("Vendor_Belgian"),
	VENDOR_HONG_KONG("Hong Kong Company");

	private String vendorName;

	NetsuiteVendor(String vendorName )
	{
		this.vendorName = vendorName;
	}
}
