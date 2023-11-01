package com.vertex.quality.connectors.taxlink.ui.enums;

/**
 * Enums for all Supplies tabs.
 *
 * @author msalomone
 */
public enum Supplies
{

	// AR Tax Calculation Exclusions enums
	SUPPLIES("AR / OM Tax Calculation Exclusions", "Add AR / OM Tax Exclusion");

	public String pageHeaderText_AR_TAX_CALC_EXCLUSIONS;
	public String pageHeaderText_ADD_AR_EXCLUSION_HEADER;

	Supplies( String pageHeaderText_AR_TAX_CALC_EXCLUSIONS, String pageHeaderText_ADD_AR_EXCLUSION_HEADER )
	{

		this.pageHeaderText_AR_TAX_CALC_EXCLUSIONS = pageHeaderText_AR_TAX_CALC_EXCLUSIONS;
		this.pageHeaderText_ADD_AR_EXCLUSION_HEADER = pageHeaderText_ADD_AR_EXCLUSION_HEADER;
	}
}
